package ru.otus.hw.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.BookController;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;
import ru.otus.hw.services.PersonService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(BookController.class)
@WithMockUser("yudina")
@Import(SecurityConfiguration.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private PersonService userService;

    @Test
    void shouldReturnCorrectBooksList() throws Exception {
        List<Book> books = List.of(new Book(1L, "BookTitle1", new Author(1L, "AuthorName1"),
                        new Genre(1L, "genre1")),
                new Book(2L, "BookTitle2", new Author(2L, "Author2"),
                        new Genre(2L, "genre2")));
        given(bookService.findAll()).willReturn(books);

        mvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attributeExists("bookDto"))
                .andExpect(model().attribute("books", books))
                .andExpect(content().string(containsString("BookTitle1")))
                .andExpect(content().string(containsString("BookTitle2")));
    }

    @Test
    public void shouldDeleteBookAndRedirectToBooksList() throws Exception {
        mvc.perform(get("/books/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/books"));

        verify(bookService, times(1))
                .deleteById(eq(1L));
    }

    @Test
    public void shouldCreateBookAndRedirectToBooksList() throws Exception {
        BookDto book = new BookDto();
        book.setTitle("BookTitle1");
        book.setAuthor(new Author(1L, "AuthorName1"));
        book.setGenre(new Genre(1L, "genre1"));

        mvc.perform(post("/books")
                        .flashAttr("bookDto", book))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/books"));

        verify(bookService, times(1)).insert(eq(1L), eq("BookTitle1"), eq(1L));
    }

    @Test
    public void shouldNotCreateBookIfTitleIsNull() throws Exception {
        BookDto book = new BookDto();
        book.setTitle(null);
        book.setAuthor(new Author(1L, "AuthorName1"));
        book.setGenre(new Genre(1L, "genre1"));

        mvc.perform(post("/books")
                        .flashAttr("bookDto", book))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("bookDto", "title"));
    }

    @Test
    void shouldReturnEditBookPage() throws Exception {
        Book book = new Book(3L, "BookTitle3", new Author(1L, "AuthorName1"),
                new Genre(1L, "genre1"));
        given(bookService.findById(3L)).willReturn(Optional.of(book));

        mvc.perform(get("/books/edit/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"))
                .andExpect(content().string(containsString("BookTitle3")));
    }

    @Test
    public void shouldEditBookAndRedirectToBooksList() throws Exception {
        String newBookTitle = "somebody title";
        BookDto book = new BookDto();
        book.setId(1L);
        book.setTitle(newBookTitle);
        book.setAuthor(new Author(1, "AuthorName1"));
        book.setGenre(new Genre(1, "genre1"));

        mvc.perform(post("/books/edit/1")
                        .flashAttr("book", book))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/books"));

        verify(bookService, times(1))
                .update(eq(1L), eq(1L), eq(newBookTitle), eq(1L));
    }
}
