package org.alexander.project.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.alexander.project.entity.Person;
import org.alexander.project.repository.DataBaseJpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.alexander.project.storage.Storage.nextId;

@RestController
@RequestMapping("/personService")
@RequiredArgsConstructor
public class PersonRequestController { //Тут пометочка с вопросом: нужно делать слой "/api/" между адрессом и персон сервисом? Если да, то делать через наследование от класса, который будет лежать в пакете (api или controllers?)?
    private final DataBaseJpaRepository db;

    @PersistenceContext
    private EntityManager entityManager;



    @SneakyThrows
    @GetMapping("/get_by_id")
    public Person getPerson(@RequestParam int id){
        Person person = db.findById(id).orElseThrow(() -> new IllegalArgumentException("Person not found"));
        return person;
    }
    @SneakyThrows
    @PostMapping("/create")
    public String createPerson(@RequestBody Person person){
        person.setId(nextId());
        db.save(person);
        return "Создано!";
    }
    @SneakyThrows
    @PutMapping("/edit")
    public String edit(@RequestBody Person person){
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
    public List<Person> search(@RequestParam String searchCriteria, @RequestParam String searchParam, @RequestParam int page){
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> cq = cb.createQuery(Person.class);
        Root<Person> root = cq.from(Person.class);
        Predicate predicate = cb.like(root.get(searchCriteria), searchParam);
        cq.where(predicate);
        TypedQuery<Person> tq = entityManager.createQuery(cq);
        tq.setFirstResult((page - 1) * 5);
        tq.setMaxResults(5);
        return tq.getResultList();
    }

}
