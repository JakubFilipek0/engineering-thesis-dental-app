package com.example.studia.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String name;
    private String surname;
    private String password;
    private String email;
    private String role = "ROLE_USER";
    private Boolean enabled = true;
    @OneToMany(mappedBy = "dentist")
    private List<Blog> dentistBlog;

    @OneToMany(mappedBy = "user")
    private List<Appointment> userAppointment;

    @OneToMany(mappedBy = "dentist")
    private List<Appointment> dentistAppointment;

    @OneToMany(mappedBy = "dentist")
    private List<ScheduleDentists> scheduleDentistsDentist;

    @OneToMany(mappedBy = "user")
    private List<ScheduleDentists> scheduleDentistsUser;

    @OneToMany(mappedBy = "user")
    private List<Opinion> myOpinion;

    @OneToOne(mappedBy = "user")
    private PasswordHistory passwordHistory;

    public User() {
    }

    public User(String username, String name, String surname, String password, String email) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }

    public User(String username, String name, String surname, String password, String email, String role) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Blog> getDentistBlog() {
        return dentistBlog;
    }

    public void setDentistBlog(List<Blog> dentistBlog) {
        this.dentistBlog = dentistBlog;
    }

    public List<Appointment> getUserAppointment() {
        return userAppointment;
    }

    public void setUserAppointment(List<Appointment> userAppointment) {
        this.userAppointment = userAppointment;
    }

    public List<Appointment> getDentistAppointment() {
        return dentistAppointment;
    }

    public void setDentistAppointment(List<Appointment> dentistAppointment) {
        this.dentistAppointment = dentistAppointment;
    }

    public List<ScheduleDentists> getScheduleDentistsDentist() {
        return scheduleDentistsDentist;
    }

    public void setScheduleDentistsDentist(List<ScheduleDentists> scheduleDentistsDentist) {
        this.scheduleDentistsDentist = scheduleDentistsDentist;
    }

    public List<ScheduleDentists> getScheduleDentistsUser() {
        return scheduleDentistsUser;
    }

    public void setScheduleDentistsUser(List<ScheduleDentists> scheduleDentistsUser) {
        this.scheduleDentistsUser = scheduleDentistsUser;
    }

    public List<Opinion> getMyOpinion() {
        return myOpinion;
    }

    public void setMyOpinion(List<Opinion> myOpinion) {
        this.myOpinion = myOpinion;
    }

    public PasswordHistory getPasswordHistory() {
        return passwordHistory;
    }

    public void setPasswordHistory(PasswordHistory passwordHistory) {
        this.passwordHistory = passwordHistory;
    }
}
