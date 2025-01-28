package org.alexander.project.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


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

    @Column(name = "organizationdata")
    private String organizationdata;

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
        this.organizationdata = null;

    }

    public Person() {

    }

    public Person(int id, String name, int age, String email, String inn) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.inn = inn;
        this.organizationdata = null;
    }


}