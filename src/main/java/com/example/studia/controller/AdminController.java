package com.example.studia.controller;

import com.example.studia.model.Appointment;
import com.example.studia.model.PasswordHistory;
import com.example.studia.model.ScheduleDentists;
import com.example.studia.model.User;
import com.example.studia.repository.PasswordHistoryRepository;
import com.example.studia.service.AppointmentService;
import com.example.studia.service.ScheduleDentistsService;
import com.example.studia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordHistoryRepository passwordHistoryRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    ScheduleDentistsService scheduleDentistsService;

    @Autowired
    AppointmentService appointmentService;

    @GetMapping()
    public String adminHome(Model model,
                            boolean passwordTooSimple,
                            boolean userExist) {
        List<User> dentistList = userService.getDentistList();
        model.addAttribute("dentistList", dentistList);
        model.addAttribute("newDentist", new User());
        model.addAttribute("passwordTooSimple", passwordTooSimple);
        model.addAttribute("userExist", userExist);
        return "admin/adminHome.html";
    }

    @PostMapping(value = "/saveNewDentist")
    public String saveNewDentist(User user, Model model) {
        List<User> existingUsers = userService.getUsers();
        if (!checkPasswordComplexity(user.getPassword())) {
            return adminHome(model, true, false);
        }

        for (User userLoop : existingUsers) {
            if (userLoop.getUsername().equals(user.getUsername()) || userLoop.getEmail().equals(user.getEmail())) {
                return adminHome(model, false, true);
            }
        }

        user.setRole("ROLE_DENTIST");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.setUser(user);

        PasswordHistory passwordHistoryUser = new PasswordHistory();
        passwordHistoryUser.setPointer(0); // wskaźnik
        passwordHistoryUser.setUser(user); // jaki User
        List<String> passwordUser = new ArrayList<>();
        passwordUser.add(user.getPassword());
        passwordUser.add("1"); passwordUser.add("2");passwordUser.add("3");passwordUser.add("4");passwordUser.add("5");
        passwordUser.add("6");passwordUser.add("7");passwordUser.add("8");passwordUser.add("9");
        passwordHistoryUser.setPasswordsList(passwordUser); // pierwsze hasło do listy

        passwordHistoryRepository.save(passwordHistoryUser);

        return "redirect:/admin";
    }

//    @GetMapping(value = "/deleteDentist/{userId}")
//    public String deleteDentist(@PathVariable(name = "userId") Long userId) {
//        User user = userService.getUser(userId);
//        PasswordHistory passwordHistory = passwordHistoryRepository.findByUser(user);
//        passwordHistoryRepository.deleteById(passwordHistory.getId());
//
//        List<ScheduleDentists> scheduleDentistsList = scheduleDentistsService.findByDentist(user);
//        for (ScheduleDentists dentists : scheduleDentistsList) {
//            scheduleDentistsService.deleteScheduleDentists(dentists.getScheduleDentistsId());
//        }
//
//        List<Appointment> appointmentList = appointmentService.
//
//        userService.deleteUser(userId);
//        return "redirect:/admin";
//    }

    public boolean checkPasswordComplexity(String password) {
        if (password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*") &&
                password.length() >= 10) {
            return true;
        }
        return false;
    }
}
