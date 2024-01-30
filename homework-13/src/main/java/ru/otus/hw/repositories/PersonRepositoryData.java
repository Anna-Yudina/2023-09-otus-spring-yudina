package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Person;

import java.util.Optional;

public interface PersonRepositoryData extends JpaRepository<Person, Long> {

    @EntityGraph(value = "person-authorities-entity-graph")
    Optional<Person> findByUsername(String username);
}
