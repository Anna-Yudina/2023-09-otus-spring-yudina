package ru.otus.hw.rest;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepositoryData;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class CommentRestController {

    private final CommentRepositoryData commentRepository;

    @GetMapping("/api/books/{bookId}/comments")
    public Flux<CommentDto> getAllCommentsByBookId(@PathVariable("bookId") long bookId) {
        return commentRepository.findCommentsByBookId(bookId).map(CommentDto::fromDomainObject);
    }

    @DeleteMapping("/api/books/{bookId}/comments/{commentId}")
    public Mono<Void> deleteCommentById(@PathVariable("bookId") long bookId,
                                      @PathVariable("commentId") long commentId) {
        return commentRepository.deleteById(commentId);
    }

    @PostMapping("/api/books/{bookId}/comments")
    public Mono<Comment> saveComment(@PathVariable("bookId") long bookId,
                                     @RequestBody CommentDto commentDto) {
        return commentRepository.save(commentDto.getText(), bookId);
    }
}
