package com.saniya.namedayofweek;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class BirthInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private LocalDate date;

    private String day;

    private String Malename;

    private String Femalename;

    private String characteristics;


    @ManyToMany
    private Set<User> users;

    public BirthInfo() {
    }

    public BirthInfo(LocalDate date, String day, String malename, String femalename, String characteristics) {
        this.date = date;
        this.day = day;
        this.Malename = malename;
        this.Femalename = femalename;
        this.characteristics = characteristics;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMalename() {
        return Malename;
    }

    public void setMalename(String malename) {
        Malename = malename;
    }

    public String getFemalename() {
        return Femalename;
    }

    public void setFemalename(String femalename) {
        Femalename = femalename;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUser(Set<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        this.users.add(user);
    }
}
