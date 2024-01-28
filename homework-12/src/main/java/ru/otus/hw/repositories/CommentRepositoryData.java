package ru.otus.hw.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.otus.hw.models.Comment;

public interface CommentRepositoryData extends CrudRepository<Comment, Long> {
}
