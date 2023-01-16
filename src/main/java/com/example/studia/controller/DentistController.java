package com.example.studia.controller;

import com.example.studia.model.*;
import com.example.studia.security.CurrentUser;
import com.example.studia.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/dentist")
public class DentistController {

    @Autowired
    CurrentUser currentUser;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    ScheduleDentistsService scheduleDentistsService;

    @Autowired
    OpinionService opinionService;

    @Autowired
    TeethService teethService;

    List<String> defaultHourList = Arrays.asList("9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00",
            "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00",
            "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30");

    @GetMapping(value = "/{pageNumber}")
    public String getOnePage(Model model,
                             @PathVariable(name = "pageNumber") int currentPage,
                             @RequestParam(name = "sortField") String sortField,
                             @RequestParam(name = "sortDir") String sortDir) {

        String status2 = "OCZEKUJĄCY";
        Page<Appointment> page = appointmentService.findPaginatedByDentistAndStatus(currentPage, currentUser.getCurrentUser(), status2, sortField, sortDir);
        User user = currentUser.getCurrentUser();
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

        String currentUser = user.getName() + " " + user.getSurname();
        // Wyświetlenie na profilu imienia
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userId", user.getUserId());

        return "dentist/dentistHome.html";
    }

    @GetMapping
    public String dentistHome(Model model) {
        model.addAttribute("number", 1);
        return getOnePage(model, 1, "dateOfAppointment", "asc");
    }

    @PostMapping(value = "/schedule/save")
    public String scheduleDentistSave(ScheduleDentists scheduleDentists) {
        scheduleDentists.setState("OPEN");
        User user = userService.getUser(currentUser.getCurrentUserId());
        scheduleDentists.setDentist(user);
        scheduleDentistsService.setScheduleDentists(scheduleDentists);
        System.out.println("Data po zapisie: " + scheduleDentists.getScheduleDate());
        return "redirect:/dentist/mySchedule";
    }

    @GetMapping(value = "/mySchedule")
    public String mySchedule(Model model) {
        return getOnePage2(model, 1, "scheduleDate", "asc");
    }

    @GetMapping(value = "/mySchedule/{pageNumber}")
    public String getOnePage2(Model model, @PathVariable(name = "pageNumber") int currentPage,
                              @RequestParam(name = "sortField") String sortField, @RequestParam(name = "sortDir") String sortDir) {
        User user = currentUser.getCurrentUser();
        Page<ScheduleDentists> page = scheduleDentistsService
                .findPaginatedByDentist(currentPage, user, sortField, sortDir);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<ScheduleDentists> scheduleDentists = page.getContent();

        System.out.println("Total pages: " + totalPages);
        System.out.println("Total items 2: " + totalItems);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("schedule", scheduleDentists);
        model.addAttribute("userId", currentUser.getCurrentUserId());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("hourList", defaultHourList);
        model.addAttribute("scheduleForm", new ScheduleDentists());

        return "dentist/dentistMySchedule.html";
    }


    @GetMapping("/mySchedule/delete/{scheduleDentistsId}")
    public String myScheduleDelete(@PathVariable(value = "scheduleDentistsId") Long scheduleDentistsId) {
        scheduleDentistsService.deleteScheduleDentists(scheduleDentistsId);
        return "redirect:/dentist/mySchedule";
    }

    @GetMapping(value = "/deleteAppointment/{appointmentId}")
    public String deleteAppointment(@PathVariable(name = "appointmentId") Long appointmentId) {

        Appointment appointment = appointmentService.getAppointment(appointmentId);
        ScheduleDentists scheduleDentists = appointment.getScheduleDentists();
        scheduleDentists.setState("OPEN");
        scheduleDentists.setUser(null);
        appointment.setStatus("ODWOŁANO");
        appointment.setScheduleDentists(null);

        appointmentService.setAppointment(appointment);
        scheduleDentistsService.setScheduleDentists(scheduleDentists);

        return "redirect:/redirect";
    }

    Long temporaryAppointment;

    @GetMapping(value = "/appointment/details/{appointmentId}")
    private String appointmentDetails(@PathVariable(name = "appointmentId") Long appointmentId, Model model) {
        Appointment appointment = appointmentService.getAppointment(appointmentId);
        temporaryAppointment = appointmentId;
        model.addAttribute("appointments", appointment);
        model.addAttribute("userId", currentUser.getCurrentUserId());

        return "dentist/dentistAppointmentDetails.html";
    }

    @GetMapping(value = "/appointment/details/save")
    public String endAppointment() {
        Appointment appointment = appointmentService.getAppointment(temporaryAppointment);
        ScheduleDentists appointmentScheduleDentists = appointment.getScheduleDentists();

        appointment.setScheduleDentists(null);
        scheduleDentistsService.deleteScheduleDentists(appointmentScheduleDentists.getScheduleDentistsId());
        appointment.setStatus("ODBYTA");

        appointmentService.setAppointment(appointment);

        return "redirect:/redirect";
    }

    Long temporaryTeethId;

    @GetMapping(value = "/appointment/details/teeth/{teethId}")
    public String appointmentDetailsTeeth(@PathVariable(name = "teethId") Long teethId, Model model) {
        temporaryTeethId = teethId;

        List<Teeth> teethListAll = teethService.getTooth();
        List<Teeth> teethUserMatch = new ArrayList<>();
        List<Teeth> teethTeethMatch = new ArrayList<>();
        //Zebranie wszystkich zębów i stworzenie pustej listy

        Appointment appointment = appointmentService.getAppointment(temporaryAppointment);

        for (Teeth teeth : teethListAll) {
            if (teeth.getAppointment().getUser().equals(appointment.getUser())) {
                teethUserMatch.add(teeth);
                System.out.println("User match: " + teeth.getAppointment().getUser());
            }
        }

        for (Teeth teeth : teethUserMatch) {
            if (teeth.getTeethId().equals(temporaryTeethId)) {
                teethTeethMatch.add(teeth);
                System.out.println("Teeth ID match: " + teeth.getTeethId());
            }
        }

        for (Teeth teeth : teethTeethMatch) {
            System.out.println("Final list | Teeth ID: " + teeth.getTeethId());
        }

        model.addAttribute("teethList", teethTeethMatch);
        model.addAttribute("teeth", new Teeth());
        model.addAttribute("userId", currentUser.getCurrentUserId());
        return "dentist/dentistAppointmentDetailsTeeth.html";
    }

    @PostMapping(value = "/appointment/details/teeth/save")
    public String appointmentDetailsTeethSave(Teeth teeth) {
        Appointment appointment = appointmentService.getAppointment(temporaryAppointment);
        teeth.setAppointment(appointment);
        teeth.setTeethId(temporaryTeethId);
        teethService.setTeeth(teeth);
        return "redirect:/dentist/appointment/details/teeth/" + temporaryTeethId;
    }
}