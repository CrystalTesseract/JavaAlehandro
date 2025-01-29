package org.alexander.project.service;

import lombok.RequiredArgsConstructor;
import org.alexander.project.controllers.dto.PersonDto;
import org.alexander.project.entity.Person;
import org.alexander.project.repository.DataBaseJpaRepository;
import org.alexander.project.repository.spec.PersonSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.alexander.project.storage.Storage.nextId;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final DataBaseJpaRepository db;

    public PersonDto findById(int id){
        return new PersonDto(db.findById(id).orElseThrow(() -> new IllegalArgumentException("Person not found")));
    }
    public List<PersonDto> findAll(PersonSpecification specification, Pageable pageable){
        return db.findAll(specification, pageable).getContent();
    }
    public void save(Person person){
        person.setId(nextId());
        db.save(person);
    }
    public void update(PersonDto personDto){
        Person localPerson = db.findById(personDto.getId()).orElseThrow(() -> new IllegalArgumentException("Person not found"));
        localPerson.setName(personDto.getName());
        localPerson.setAge(personDto.getAge());
        localPerson.setInn(personDto.getInn());
        localPerson.setEmail(personDto.getEmail());
        localPerson.setOrganizationdata(personDto.getOrganizationdata());
        db.save(localPerson);
    }
}
