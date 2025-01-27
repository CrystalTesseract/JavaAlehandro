package org.alexander.project.repository.spec;

import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.alexander.project.entity.Person;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PersonSpecification implements Specification<Person> {
    private String name;
    private Integer age;
    private String email;
    private String inn;
    private String organizationdata = null;

    public PersonSpecification(String name, Integer age, String email, String inn, String organizationdata) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.inn = inn;
        this.organizationdata = organizationdata;
    }

    @Override
    public Predicate toPredicate(Root root, @Nullable CriteriaQuery query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (name != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (age != null) {
            predicates.add(criteriaBuilder.equal(root.get("age"), age));
        }

        if (email != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }

        if (inn != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("inn")), "%" + inn.toLowerCase() + "%"));
        }

        if (organizationdata != null) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("organizationdata")), "%" + organizationdata.toLowerCase() + "%"));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
