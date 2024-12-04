package org.alexander.project.entity;


import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Data
@Entity
@Table(name = "person")
public class Person {


    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age = 0;

    @Column(name = "email")
    private String email;

    @Column(name = "inn")
    private String inn;

    @Column(name = "organizationData")
    private String organizationData;

    public Person(String name, int age) {

        this.name = name;
        this.age = age;
    }

    public Person(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public void clear() {
        this.name = null;
        this.age = 0;
        this.email = null;
        this.inn = null;
        this.organizationData = null;

    }

    public Person() {

    }
}