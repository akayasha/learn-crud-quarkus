package com.example;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.Date;

@Entity(name = "student")
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "nim", nullable = false, unique = true)
    private String nim;

    @Column(name = "name")
    private String name;

    @Column(name = "classes", nullable = false)
    private String classes;

    @Column(name = "dob")
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate dob;

    @Column(name = "phoneNo")
    private String phoneNo;

    @Column(name = "email")
    @Email
    private String email;

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
