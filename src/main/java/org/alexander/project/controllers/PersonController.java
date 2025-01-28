package org.alexander.project.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.alexander.project.controllers.dto.PersonDto;
import org.alexander.project.entity.Person;
import org.alexander.project.repository.DataBaseJpaRepository;
import org.alexander.project.repository.spec.PersonSpecification;
import org.alexander.project.repository.spec.PersonSpecificationBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.alexander.project.storage.Storage.nextId;

@Tag(name = "person_methods")
@RestController
@RequestMapping("/v1/persons")
@RequiredArgsConstructor
public class PersonController {
    private final DataBaseJpaRepository db;


    @SneakyThrows
    @GetMapping
    public PersonDto getPerson(@RequestParam int id) {
        return new PersonDto(db.findById(id).orElseThrow(() -> new IllegalArgumentException("Person not found")));
    }

    @SneakyThrows
    @PostMapping
    public String createPerson(@RequestBody PersonDto person) {
        person.setId(nextId());
        db.save(person.toPerson());
        return "Создано!";
    }

    @SneakyThrows
    @PutMapping
    public String edit(@RequestBody PersonDto person) {
        Person localPerson = db.findById(person.getId()).orElseThrow(() -> new IllegalArgumentException("Person not found"));
        localPerson.setName(person.getName());
        localPerson.setAge(person.getAge());
        localPerson.setInn(person.getInn());
        localPerson.setEmail(person.getEmail());
        localPerson.setOrganizationdata(person.getOrganizationdata());
        db.save(localPerson);
        return "Сохранения изменены!";
    }

    @SneakyThrows
    @GetMapping("/search")
    public List<PersonDto> search(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) Integer age,
                                  @RequestParam(required = false) String email,
                                  @RequestParam(required = false) String inn,
                                  @RequestParam(required = false) String organizationdata,
                                  @RequestParam int page) {
        PersonSpecification specification = new PersonSpecificationBuilder()
                .withName(name)
                .withAge(age)
                .withEmail(email)
                .withInn(inn)
                .withOrganizationData(organizationdata)
                .build();

        Pageable pageable = PageRequest.of(page - 1, 5);
        return db.findAll(specification, pageable).getContent();
    }

}
