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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PersonDto> getPerson(@RequestParam int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<String> createPerson(@RequestBody PersonDto person) {
        service.save(person.toPerson());
        return ResponseEntity.status(HttpStatus.CREATED).body("Создано!");
    }

    @SneakyThrows
    @PutMapping
    public ResponseEntity<String> edit(@RequestBody PersonDto person) {
        try {
            service.update(person);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Сохранения изменены!");
    }

    @SneakyThrows
    @GetMapping("/search")
    public ResponseEntity<List<PersonDto>> search(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) Integer age,
                                                  @RequestParam(required = false) String email,
                                                  @RequestParam(required = false) String inn,
                                                  @RequestParam(required = false) String organizationdata,
                                                  @RequestParam int page) {
        if (page <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        PersonSpecification specification = new PersonSpecificationBuilder()
                .withName(name)
                .withAge(age)
                .withEmail(email)
                .withInn(inn)
                .withOrganizationData(organizationdata)
                .build();

        Pageable pageable = PageRequest.of(page - 1, 5);
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(specification, pageable));
    }

}
