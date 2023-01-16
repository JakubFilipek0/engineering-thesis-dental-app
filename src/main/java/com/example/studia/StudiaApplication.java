package com.example.studia;

import com.example.studia.model.PasswordHistory;
import com.example.studia.model.User;
import com.example.studia.repository.PasswordHistoryRepository;
import com.example.studia.service.AppointmentService;
import com.example.studia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class StudiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudiaApplication.class, args);
	}

	@Autowired
	UserService userService;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Autowired
	AppointmentService appointmentService;

//	@Autowired
//	PasswordHistoryRepository passwordHistoryRepository;

	@Bean
	CommandLineRunner runner() {
		return args -> {
//			User admin = new User("admin", "Jan", "Kowalski", "admin", "admin");
//			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
//			admin.setRole("ROLE_ADMIN");
//			userService.setUser(admin);
//
//			User user = new User("user", "Jakub", "Filipek", "user", "user");
//			user.setPassword(passwordEncoder.encode(user.getPassword()));
//			user.setRole("ROLE_USER");
//			userService.setUser(user);
//
//			User user2 = new User("kuba", "Kuba", "F", "kuba", "kuba");
//			user2.setPassword(passwordEncoder.encode(user2.getPassword()));
//			user2.setRole("ROLE_USER");
//			userService.setUser(user2);
//
//			User dentist = new User("dentist", "Jan", "Kowal", "dentist", "dentist");
//			dentist.setPassword(passwordEncoder.encode(dentist.getPassword()));
//			dentist.setRole("ROLE_DENTIST");
//			userService.setUser(dentist);
//
//			User dentist2 = new User("krzysztof", "Krzysztof", "Nowak", "dentist", "dentist");
//			dentist2.setPassword(passwordEncoder.encode(dentist2.getPassword()));
//			dentist2.setRole("ROLE_DENTIST");
//			userService.setUser(dentist2);

//			PasswordHistory passwordHistory = new PasswordHistory();
//			List<String> passwords = new ArrayList<>();
//			passwords.add("123123");
//			passwords.add("qweqweqwe");
//			passwordHistory.setPasswordsList(passwords);
//			passwordHistoryRepository.save(passwordHistory);
		};
	}

}
