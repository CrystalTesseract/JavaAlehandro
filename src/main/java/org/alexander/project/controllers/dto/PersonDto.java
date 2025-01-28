package org.alexander.project.controllers.dto;

import lombok.Data;
import org.alexander.project.entity.Person;

@Data
public class PersonDto {
    private int id;
    private String name;
    private int age;
    private String email;
    private String inn;
    private String organizationdata;

    public Person toPerson() {
        return new Person(
                this.id,
                this.name,
                this.age,
                this.email,
                this.inn
        );
    }

    public PersonDto(Person person) {
        this.id = person.getId();
        this.name = person.getName();
        this.age = person.getAge();
        this.email = person.getEmail();
        this.inn = person.getInn();
    }

    public PersonDto() {
    }

    public PersonDto(int id, String name, int age, String email, String inn) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.inn = inn;
    }
}
