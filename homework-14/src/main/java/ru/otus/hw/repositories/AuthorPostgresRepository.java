package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.jpa.AuthorPostgres;

public interface AuthorPostgresRepository extends CrudRepository<AuthorPostgres, Long> {
}
