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
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/books")
    public String bookPage(Model model) {
        List<Book> books = bookService.findAll();
        BookDto bookDto = new BookDto();
        List<Author> authors = authorService.findAll();
        List<Genre> genres = genreService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("bookDto", bookDto);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "books";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model) {
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @PostMapping("/books")
    public String createBook(@ModelAttribute("bookDto") @Valid BookDto book,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Book> books = bookService.findAll();
            List<Author> authors = authorService.findAll();
            List<Genre> genres = genreService.findAll();
            model.addAttribute("books", books);
            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);
            return "books";
        }
        bookService.insert(book.getAuthor().getId(), book.getTitle(), book.getGenre().getId());
        return "redirect:/books";
    }

    @GetMapping("/books/edit/{id}")
    public String editBook(@PathVariable("id") long id, Model model) {
        List<Author> authors = authorService.findAll();
        List<Genre> genres = genreService.findAll();
        Book book = bookService.findById(id).orElseThrow(() -> new NotFoundException("Book not found"));
        model.addAttribute("book", BookDto.fromDomainObject(book));
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "edit-book";
    }

    @PostMapping("/books/edit/{id}")
    public String updateBook(@ModelAttribute("book") @Valid BookDto bookDto,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<Author> authors = authorService.findAll();
            List<Genre> genres = genreService.findAll();
            model.addAttribute("authors", authors);
            model.addAttribute("genres", genres);
            return "edit-book";
        }

        bookService.update(bookDto.getId(), bookDto.getAuthor().getId(), bookDto.getTitle(), bookDto.getGenre().getId());
        return "redirect:/books";
    }
}
