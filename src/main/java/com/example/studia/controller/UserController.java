package com.example.studia.controller;

import com.example.studia.model.Appointment;
import com.example.studia.model.Opinion;
import com.example.studia.model.ScheduleDentists;
import com.example.studia.model.User;
import com.example.studia.security.CurrentUser;
import com.example.studia.service.AppointmentService;
import com.example.studia.service.OpinionService;
import com.example.studia.service.ScheduleDentistsService;
import com.example.studia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/user")
public class UserController {

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

    @GetMapping
    public String userHome(Model model) {
        return getOnePage(model, 1, "dateOfAppointment", "asc");
    }

    @GetMapping(value = "/{pageNumber}")
    public String getOnePage(Model model, @PathVariable(name = "pageNumber") int currentPage,
                             @RequestParam(name = "sortField") String sortField, @RequestParam(name = "sortDir") String sortDir) {
        String status2 = "OCZEKUJĄCY";
        Page<Appointment> page = appointmentService.findPaginatedByUserAndStatus(currentPage, currentUser.getCurrentUser(), status2, sortField, sortDir);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<Appointment> appointments = page.getContent();

        System.out.println("Total pages: " + totalPages + "\nTotal items: " + totalItems);

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("appointments", appointments);

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        User user = currentUser.getCurrentUser();
        String currentUser = user.getName() + " " + user.getSurname();
        // Wyświetlenie na profilu imienia
        model.addAttribute("currentUser", currentUser);

        model.addAttribute("userId", user.getUserId());

        return "user/userHome.html";
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

    Long appointmentOpinionId;

    @GetMapping(value = "/opinion/{appointmentId}")
    public String setOpinion(Model model, @PathVariable(name = "appointmentId") Long appointmentId) {
        appointmentOpinionId = appointmentId;
        model.addAttribute("opinion", new Opinion());
        return "user/userOpinion.html";
    }

    @PostMapping(value = "/opinion/save")
    public String saveOpinion(@ModelAttribute(name = "opinion") Opinion opinion) {
        System.out.println(opinion.getOpinionId() + " " + opinion.getRate() + " " + opinion.getContent());
        Appointment appointment = appointmentService.getAppointment(appointmentOpinionId);

        opinion.setUser(currentUser.getCurrentUser());
        opinionService.setOpinion(opinion);

        appointment.setOpinion(opinion);
        appointmentService.setAppointment(appointment);

        return "redirect:/redirect";
    }

    @GetMapping(value = "/opinion/delete/{appointmentId}")
    public String deleteOpinion(@PathVariable(name = "appointmentId") Long appointmentId) {
        Appointment appointment = appointmentService.getAppointment(appointmentId);
        Opinion opinion = appointment.getOpinion();

        appointment.setOpinion(null);
        appointmentService.setAppointment(appointment);

        opinionService.deleteOpinion(opinion.getOpinionId());

        return "redirect:/redirect";
    }
}
