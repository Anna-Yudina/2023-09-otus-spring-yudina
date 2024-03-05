package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.service.AuthorService;
import ru.otus.hw.service.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PageController {

    private final AuthorService authorService;

    private final BookService bookService;

    @GetMapping("/authors")
    public String authorPage(Model model) {
        List<AuthorDto> authors = authorService.getAllAuthors();
        log.info("authors: " + authors);
        model.addAttribute("authors", authors);
        return "authors";
    }

    @GetMapping("/books")
    public String bookPage(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        log.info("books: " + books);
        model.addAttribute("books", books);
        return "books";
    }
}
