package com.in28minute.jpa.hibernate.jpahibernate.entity;

import javax.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    // Student owning side relationship
    @OneToOne(fetch = FetchType.LAZY)
    private Passport passport;

    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Passport getPassport() {
        return passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    @Override
    public String toString() {
        return String.format("[Student %s] ",name);
    }
}
