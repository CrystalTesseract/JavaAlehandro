package org.alexander.project.repository;

import org.alexander.project.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataBaseJpaRepository extends JpaRepository<Person, Integer> {
}
