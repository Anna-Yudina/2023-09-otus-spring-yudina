package ru.otus.hw.converters;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.Comment;

@Component
public class CommentConverter {

    public String commentTextToString(Comment comment) {
        return "Comment: \"%s\"".formatted(comment.getText());
    }

    public String  (Comment comment) {
        return "Name's book: \"%s\" Comment: \"%s\"".formatted(comment.getBook().getTitle(), comment.getText());
    }
}
