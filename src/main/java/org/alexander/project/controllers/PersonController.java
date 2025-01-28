package org.alexander.project.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.alexander.project.controllers.dto.PersonDto;
import org.alexander.project.repository.spec.PersonSpecification;
import org.alexander.project.repository.spec.PersonSpecificationBuilder;
import org.alexander.project.service.PersonService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "person_methods")
@RestController
@RequestMapping("/v1/persons")
@RequiredArgsConstructor
public class PersonController {
    private final PersonService service;


    @SneakyThrows
    @GetMapping
    public PersonDto getPerson(@RequestParam int id) {
        return service.findById(id);
    }

    @SneakyThrows
    @PostMapping
    public String createPerson(@RequestBody PersonDto person) {
        service.save(person.toPerson());
        return "Создано!";
    }

    @SneakyThrows
    @PutMapping
    public String edit(@RequestBody PersonDto person) {
        service.update(person);
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
        return service.findAll(specification, pageable);
    }

}
