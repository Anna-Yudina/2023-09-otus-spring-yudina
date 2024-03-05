package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentService commentService;

    @GetMapping("/api/books/{bookId}/comments")
    public List<CommentDto> getAllCommentsByBookId(@PathVariable("bookId") long bookId) {
        return commentService.findCommentsByBookId(bookId)
                .stream().map(CommentDto::fromDomainObject).collect(Collectors.toList());
    }

    @DeleteMapping("/api/books/{bookId}/comments/{commentId}")
    public CommentDto deleteAuthorById(@PathVariable("bookId") long bookId,
                                      @PathVariable("commentId") long commentId) {
        return CommentDto.fromDomainObject(commentService.deleteById(commentId));
    }

    @PostMapping("/api/books/{bookId}/comments")
    public CommentDto saveComment(@PathVariable("bookId") long bookId,
                                  @RequestBody CommentDto commentDto) {
        return CommentDto.fromDomainObject(commentService.insert(commentDto.getText(), bookId));
    }
}
