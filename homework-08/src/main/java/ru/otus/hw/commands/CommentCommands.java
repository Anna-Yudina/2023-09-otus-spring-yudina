package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.CommentService;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    @ShellMethod(value = "Find comments by book id", key = "csbid")
    public String findCommentsByBookId(String bookId) {
        try {
            return commentService.findCommentsByBookId(bookId).stream()
                    .map(commentConverter::commentTextAndBookNameToString)
                    .collect(Collectors.joining("," + System.lineSeparator()));
        } catch (EntityNotFoundException e) {
            return "Book with id %s not found".formatted(bookId);
        }
    }

    @ShellMethod(value = "Delete comment by id", key = "cdelid")
    public String update(String id) {
        try {
            return commentConverter.commentTextToString(commentService.deleteById(id)) + " is deleted";
        } catch (EntityNotFoundException e) {
            return "Comment with id %s not found".formatted(id);
        }
    }

    @ShellMethod(value = "Insert comment", key = "cins")
    public String insert(String comment, String bookId) {
        try {
            return commentConverter.commentTextAndBookNameToString(commentService.insert(comment, bookId));
        } catch (EntityNotFoundException e) {
            return "Book with id %s not found".formatted(bookId);
        }
    }
}
