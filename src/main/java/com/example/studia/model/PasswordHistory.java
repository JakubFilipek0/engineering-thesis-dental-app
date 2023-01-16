package com.example.studia.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PasswordHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection(targetClass = String.class)
    private List<String> passwordsList = new ArrayList<String>();
    private int pointer;
    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getPasswordsList() {
        return passwordsList;
    }

    public void setPasswordsList(List<String> passwordsList) {
        this.passwordsList = passwordsList;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
