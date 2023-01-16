package com.example.studia.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@RequiredArgsConstructor
public class ScheduleDentists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleDentistsId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date scheduleDate;
    private String scheduleHour;
    private String state;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dentistId")
    private User dentist;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToOne(mappedBy = "scheduleDentists")
    private Appointment appointment;

    public ScheduleDentists(Date scheduleDate, String scheduleHour, String state, User dentist) {
        this.scheduleDate = scheduleDate;
        this.scheduleHour = scheduleHour;
        this.state = state;
        this.dentist = dentist;
    }

    public Long getScheduleDentistsId() {
        return scheduleDentistsId;
    }

    public void setScheduleDentistsId(Long scheduleDentistsId) {
        this.scheduleDentistsId = scheduleDentistsId;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getScheduleHour() {
        return scheduleHour;
    }

    public void setScheduleHour(String scheduleHour) {
        this.scheduleHour = scheduleHour;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User getDentist() {
        return dentist;
    }

    public void setDentist(User dentist) {
        this.dentist = dentist;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
