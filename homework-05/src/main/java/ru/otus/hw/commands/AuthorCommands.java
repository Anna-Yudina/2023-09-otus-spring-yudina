package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.services.AuthorService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    @ShellMethod(value = "Find all authors", key = "aa")
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find autor by id", key = "aid")
    public String findAuthorById(long id) {
        return authorService.findById(id)
                .map(authorConverter::authorToString)
                .orElse("Author with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Insert author", key = "ains")
    public String insertAuthor(String author) {
        var savedAuthor = authorService.insert(author);
        return authorConverter.authorToString(savedAuthor);
    }

    @ShellMethod(value = "Delete author by id", key = "adelid")
    public void update(long id) {
        authorService.deleteById(id);
    }
}
