package com.example.studia.controller;

import com.example.studia.model.Appointment;
import com.example.studia.model.ScheduleDentists;
import com.example.studia.repository.ScheduleDentistsRepository;
import com.example.studia.security.CurrentUser;
import com.example.studia.service.AppointmentService;
import com.example.studia.service.ScheduleDentistsService;
import com.example.studia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/appointment")
public class AppointmentController {

    @Autowired
    CurrentUser currentUser;

    @Autowired
    UserService userService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    ScheduleDentistsService scheduleDentistsService;

    ScheduleDentists scheduleDentists;
    @Autowired
    private ScheduleDentistsRepository scheduleDentistsRepository;

    @GetMapping(value = "/chooseDentist/{pageNumber}")
    public String chooseDentist(Model model,
                                @PathVariable(name = "pageNumber") int currentPage,
                                @RequestParam(name = "sortField") String sortField,
                                @RequestParam(name = "sortDir") String sortDir,
                                @RequestParam(name = "selectedDate") String date) throws ParseException {
        Page<ScheduleDentists> page = scheduleDentistsService.findPaginatedByStatus(currentPage, "OPEN", sortField, sortDir);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String format;
        Date chooseDate;

        if (date == null) {
            Date date1 = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            date1 = calendar.getTime();
            format = dateFormat.format(date1);
            chooseDate = dateFormat.parse(format);
        }
        else {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            chooseDate = dateFormat1.parse(date);
        }

        List<ScheduleDentists> help = scheduleDentistsService.getScheduleDentistsAll();
        List<ScheduleDentists> equalDates = new ArrayList<>();
        for (ScheduleDentists dentists : help) {
            String tempDate = String.valueOf(dentists.getScheduleDate());
            Date dateAll = dateFormat.parse(tempDate);
            if (dateAll.equals(chooseDate)) {
                System.out.println("Równe: " + chooseDate + " = " + dateAll);
                if (dentists.getState().equals("OPEN")) {
                    equalDates.add(dentists);
                }
            }
        }
        System.out.println("Ilośc równych dat: " + equalDates.size());


        int chooseDateScheduleDentistNumber = equalDates.size();
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<ScheduleDentists> scheduleDentistsListHelp = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("schedule", equalDates);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("numberScheduleDentists", chooseDateScheduleDentistNumber);
        model.addAttribute("dateSchedule", chooseDate);
        model.addAttribute("userId", currentUser.getCurrentUserId());

        return "appointment/appointmentTable.html";
    }

    @GetMapping(value = "/chooseDentist")
    public String chooseDentistStart(Model model) throws ParseException {
        return chooseDentist(model, 1, "scheduleDate", "asc", null);
    }

    @GetMapping(value = "/chooseDentist/details/{scheduleDentistsId}")
    public String chooseDentistDetails(@PathVariable(name = "scheduleDentistsId") Long scheduleDentistsId, Model model) {
        scheduleDentists = scheduleDentistsService.getScheduleDentists(scheduleDentistsId);
        model.addAttribute("appointment", new Appointment());
        return "appointment/appointmentDetails.html";
    }

    @PostMapping(value = "/save")
    public String saveAppointment(Appointment appointment, @RequestParam(value = "image") MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        if (!file.isEmpty()) {
            String path = System.getProperty("user.dir") + "/uploads" + "/" + fileName;
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(path)));
            stream.write(file.getBytes());
            stream.close();
            appointment.setAppointmentAttachment(fileName);
        }

        appointment.setDateOfAppointment(scheduleDentists.getScheduleDate());
        appointment.setHourOfAppointment(scheduleDentists.getScheduleHour());
        appointment.setStatus("OCZEKUJĄCY");
        appointment.setUser(currentUser.getCurrentUser());
        appointment.setDentist(scheduleDentists.getDentist());

        scheduleDentists.setState("RESERVED");
        scheduleDentists.setUser(currentUser.getCurrentUser());

        scheduleDentists.setAppointment(appointment);
        appointment.setScheduleDentists(scheduleDentists);

        appointmentService.setAppointment(appointment);
        scheduleDentistsService.setScheduleDentists(scheduleDentists);

        return "redirect:/redirect";
    }


    @GetMapping(value = "/history/{pageNumber}")
    public String getUserHistory(Model model, @PathVariable(name = "pageNumber") int currentPage,
                                 @RequestParam(name = "sortField") String sortField, @RequestParam(name = "sortDir") String sortDir) {
        String status = "ODBYTA";
        String status2 = "ODWOŁANO";
        model.addAttribute("userId", currentUser.getCurrentUserId());

        Page<Appointment> page = null;

        if (currentUser.getCurrentUser().getRole().equals("ROLE_DENTIST")) {
            page = appointmentService.findPaginatedByDentistAndStatusDouble(currentPage, currentUser.getCurrentUser(), status, status2, sortField, sortDir);
//            page = appointmentService.findPaginatedByDentistAndStatus(currentPage, currentUser.getCurrentUser(), status);
            System.out.println(currentUser.getCurrentUser().getRole());
        }

        if (currentUser.getCurrentUser().getRole().equals("ROLE_USER")) {
            page = appointmentService.findPaginatedByUserAndStatusDouble(currentPage, currentUser.getCurrentUser(), status, status2, sortField, sortDir);
//            page = appointmentService.findPaginatedByUserAndStatus(currentPage, currentUser.getCurrentUser(), status);
            System.out.println(currentUser.getCurrentUser().getRole());
        }

        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Appointment> appointments = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("appointments", appointments);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("userId", currentUser.getCurrentUserId());
        return "appointment/appointmentHistory.html";
    }

    @GetMapping(value = "/history")
    public String historyInit(Model model) {
        model.addAttribute("number", 1);
        return getUserHistory(model, 1, "dateOfAppointment", "asc");
    }
}
