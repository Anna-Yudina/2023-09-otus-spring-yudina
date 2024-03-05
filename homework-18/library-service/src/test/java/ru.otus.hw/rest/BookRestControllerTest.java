package ru.otus.hw.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.BookService;
import java.util.List;
import java.util.stream.Collectors;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(BookRestController.class)
public class BookRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldReturnCorrectBooksList() throws Exception {
        List<Book> books = List.of(new Book(1L, "BookTitle1", new Author(1L, "AuthorName1"),
                        new Genre(1L, "genre1")),
                new Book(2L, "BookTitle2", new Author(2L, "Author2"),
                        new Genre(2L, "genre2")));

        given(bookService.findAll()).willReturn(books);

        List<BookDto> booksDto = books.stream()
                .map(BookDto::fromDomainObject)
                .collect(Collectors.toList());

        mvc.perform(get("/api/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(booksDto)));
    }

    @Test
    public void shouldDeleteBook() throws Exception {
        Book book = new Book(1L, "BookTitle1", new Author(1L, "AuthorName1"),
                new Genre(1L, "genre1"));
        given(bookService.deleteById(1L)).willReturn(book);

        mvc.perform(delete("/api/books/{id}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteById(1L);
    }

    @Test
    public void shouldCreateBook() throws Exception {
        Book expectedBook = new Book(1L, "BookTitle1", new Author(1L, "AuthorName1"),
                new Genre(1L, "genre1"));

        BookDto bookCreateDto = new BookDto();
        bookCreateDto.setTitle("BookTitle1");
        bookCreateDto.setAuthor(new Author(1L, "AuthorName1"));
        bookCreateDto.setGenre(new Genre(1L, "genre1"));

        BookDto expectedBookDto = BookDto.fromDomainObject(expectedBook);

        given(bookService.insert(1L, "BookTitle1", 1L)).willReturn(expectedBook);

        String expectedResult = objectMapper.writeValueAsString(expectedBookDto);

        mvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCreateDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));

        verify(bookService, times(1)).insert(1L, "BookTitle1", 1L);
    }

    @Test
    public void shouldEditBook() throws Exception {
        Book expectedBook = new Book(1L, "BookTitle1", new Author(1L, "AuthorName1"),
                new Genre(1L, "genre1"));

        BookDto bookEditDto = new BookDto();
        bookEditDto.setTitle("BookTitle1");
        bookEditDto.setAuthor(new Author(1L, "AuthorName1"));
        bookEditDto.setGenre(new Genre(1L, "genre1"));

        BookDto expectedBookDto = BookDto.fromDomainObject(expectedBook);

        given(bookService.update(1L, 1L, "BookTitle1", 1L)).willReturn(expectedBook);

        String expectedResult = objectMapper.writeValueAsString(expectedBookDto);

        mvc.perform(put("/api/books/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookEditDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));

        verify(bookService, times(1)).update(1L, 1L, "BookTitle1", 1L);
    }
}
