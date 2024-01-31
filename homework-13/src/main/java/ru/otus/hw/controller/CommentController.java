package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    private final BookService bookService;

    @GetMapping("/books/{bookId}/comments/delete/{commentId}")
    public String deleteComment(@PathVariable("bookId") long bookId,
                                @PathVariable("commentId") long commentId,
                                Model model) {
        commentService.deleteById(commentId);
        return "redirect:/books/" + bookId + "/comments";
    }

    @GetMapping("/books/{id}/comments")
    public String bookComments(@PathVariable("id") long id, Model model) {
        List<Comment> comments = commentService.findCommentsByBookId(id);
        Book book = bookService.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        model.addAttribute("comments", comments);
        model.addAttribute("book", book);
        CommentDto commentDto = new CommentDto();
        commentDto.setBook(book);
        model.addAttribute("commentDto", commentDto);
        return "comments";
    }

    @PostMapping("/books/{id}/comments")
    public String createComment(@ModelAttribute("commentDto") @Valid CommentDto commentDto,
                                BindingResult bindingResult, @PathVariable("id") long id,
                                Model model) {
        if (bindingResult.hasErrors()) {
            List<Comment> comments = commentService.findCommentsByBookId(id);
            Book book = bookService.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
            model.addAttribute("comments", comments);
            model.addAttribute("book", book);
            return "comments";
        }
        commentService.insert(commentDto.getText(), id);
        return "redirect:/books/" + id + "/comments";
    }
}
