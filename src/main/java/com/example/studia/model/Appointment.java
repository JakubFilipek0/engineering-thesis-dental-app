package com.example.studia.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfAppointment;
    private String hourOfAppointment;
    private String purpose;
    private String comment;
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dentistId")
    private User dentist;
    private String finalCost;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule")
    private ScheduleDentists scheduleDentists;

    @OneToOne
    @JoinColumn(name = "opinion")
    private Opinion opinion;

    @OneToMany(mappedBy = "appointment")
    private List<Teeth> teeth;

    private String appointmentAttachment;

    public Appointment(Date date, String s, String powód, String komentarz, String oczekujący) {
    }

    public Appointment() {
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getDentist() {
        return dentist;
    }

    public void setDentist(User dentist) {
        this.dentist = dentist;
    }

    public String getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(String finalCost) {
        this.finalCost = finalCost;
    }

    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public String getHourOfAppointment() {
        return hourOfAppointment;
    }

    public void setHourOfAppointment(String hourOfAppointment) {
        this.hourOfAppointment = hourOfAppointment;
    }

    public ScheduleDentists getScheduleDentists() {
        return scheduleDentists;
    }

    public void setScheduleDentists(ScheduleDentists scheduleDentists) {
        this.scheduleDentists = scheduleDentists;
    }

    public Opinion getOpinion() {
        return opinion;
    }

    public void setOpinion(Opinion opinion) {
        this.opinion = opinion;
    }

    public List<Teeth> getTeeth() {
        return teeth;
    }

    public void setTeeth(List<Teeth> teeth) {
        this.teeth = teeth;
    }

    public String getAppointmentAttachment() {
        return appointmentAttachment;
    }

    public void setAppointmentAttachment(String appointmentAttachment) {
        this.appointmentAttachment = appointmentAttachment;
    }
}
