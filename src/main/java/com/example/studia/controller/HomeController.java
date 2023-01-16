package com.example.studia.controller;

import com.example.studia.model.Appointment;
import com.example.studia.model.User;
import com.example.studia.security.CurrentUser;
import com.example.studia.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CurrentUser currentUser;

    @Autowired
    AppointmentService appointmentService;

    User user;

    @GetMapping(value = "/redirect")
    public String redirect() {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
        authorities = details.getAuthorities();
        user = currentUser.getCurrentUser();
        if (authorities.toString().equals("[ROLE_USER]")) {
            return "redirect:/user";
        }
        else if (authorities.toString().equals("[ROLE_ADMIN]")){
            return "redirect:/admin";
        }
        else {
            return "redirect:/dentist";
        }
    }

    @GetMapping(value = "/")
    public String home(Model model) {
        if (user != null) {
            model.addAttribute("userId", user.getUserId());
            System.out.println(user.getUserId());
        }
        List<Appointment> appointments = appointmentService.getAppointments();
        model.addAttribute("appointments", appointments);

        return "home.html";
    }

    @GetMapping(value = "/aboutUs")
    public String aboutUs() {
        return "aboutUs.html";
    }
}
