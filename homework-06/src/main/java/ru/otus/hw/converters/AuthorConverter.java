package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.Author;

@Component
public class AuthorConverter {
    public String authorToString(Author author) {
        return "id: %d, fullName: %s".formatted(author.getId(), author.getFullName());
    }

    public String authorFullNameToString(Author author) {
        return "Author fullName: %s".formatted(author.getFullName());
    }
}
