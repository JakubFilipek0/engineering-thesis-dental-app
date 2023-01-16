package com.example.studia.controller;

import com.example.studia.model.PasswordHistory;
import com.example.studia.model.User;
import com.example.studia.repository.PasswordHistoryRepository;
import com.example.studia.security.CurrentUser;
import com.example.studia.security.SecurityContext;
import com.example.studia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class SecurityController {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    CurrentUser currentUser;

    @Autowired
    SecurityContext securityContext;

    @Autowired
    PasswordHistoryRepository passwordHistoryRepository;

    @GetMapping(value = "/login")
    public String login() {
        return "security/login.html";
    }

    @GetMapping(value = "/logout")
    public String logout() {
        return "security/logout.html";
    }

    @GetMapping(value = "/register")
    public String register(Model model, boolean ifUserExist, boolean passwordTooSimple) {
        model.addAttribute("newAccount", new User());
        model.addAttribute("ifUserExist", ifUserExist);
        model.addAttribute("passwordTooSimple", passwordTooSimple);
        return "security/register.html";
    }

    @PostMapping(value = "/register/save")
    public String saveNewAccount(User user, Model model) {
        List<User> existingUsers = userService.getUsers();
        for (User userLoop : existingUsers) {
            // czy taki użytkownik istnieje
            if (userLoop.getUsername().equals(user.getUsername()) || userLoop.getEmail().equals(user.getEmail())) {
                return register(model, true, false);
            }
            // hasło complexity
            if (!checkPasswordComplexity(user.getPassword())) {
                return register(model, false, true);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
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
        return "redirect:/login";
    }

    @GetMapping(value = "/editProfile/{userId}")
    public String editProfile(@PathVariable(value = "userId") Long userId,
                              Model model,
                              boolean passwordComplexity,
                              boolean oldPasswordMatch,
                              boolean passwordPreviouslyUsed) {
        //System.out.println(userId);
        User user = userService.getUser(userId);
        //System.out.println(user.getUserId() + " " + user.getName());
        model.addAttribute("user", user);
        model.addAttribute("passwordComplexity", passwordComplexity);
        model.addAttribute("oldPasswordMatch", oldPasswordMatch);
        model.addAttribute("passwordPreviouslyUsed", passwordPreviouslyUsed);
        return "user/editProfile.html";
    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam(value = "newPassword") String newPassword,
                           @RequestParam(value = "oldPassword") String oldPassword,
                           Model model) {
        if (!newPassword.isEmpty() && !oldPassword.isEmpty()) {
            if (!passwordEncoder.matches(oldPassword, currentUser.getCurrentUser().getPassword())) {
                return editProfile(currentUser.getCurrentUserId(), model, false, true, false);
            }
            if (checkIfPasswordPreviouslyUsed(newPassword, user)) {
                return editProfile(currentUser.getCurrentUserId(), model, false, false, true);
            }
            if (!checkPasswordComplexity(newPassword)) {
                return editProfile(currentUser.getCurrentUserId(), model, true, false, false);
            } else {
                user.setPassword(passwordEncoder.encode(newPassword));
                userService.setUser(user);

                PasswordHistory passwordHistory = passwordHistoryRepository.findByUser(user);
                if (passwordHistory.getPointer() >= 9) {
                    passwordHistory.setPointer(-1);
                }

                List<String> passwordList = passwordHistory.getPasswordsList();

                passwordHistory.setPointer(passwordHistory.getPointer() + 1);
                passwordList.set(passwordHistory.getPointer(), user.getPassword());
                passwordHistoryRepository.save(passwordHistory);

                System.out.println("Pointer = " + passwordHistory.getPointer());
            }
        }
        return "redirect:/redirect";
    }

    public boolean checkPasswordComplexity(String password) {
        if (password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[0-9].*") &&
                password.length() >= 10) {
            return true;
        }
        return false;
    }

    public boolean checkIfPasswordPreviouslyUsed(String password, User user) {
        PasswordHistory passwordHistory = passwordHistoryRepository.findByUser(user);
        List<String> passwordsList = passwordHistory.getPasswordsList();

        for (String s : passwordsList) {
            if (passwordEncoder.matches(password, s)) {
                return true;
            }
        }
        return false;
    }
}