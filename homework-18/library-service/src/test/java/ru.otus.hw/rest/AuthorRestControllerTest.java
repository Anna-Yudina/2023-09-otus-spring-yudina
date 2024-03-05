package ru.otus.hw.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.services.AuthorService;
import java.util.List;
import java.util.stream.Collectors;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.times;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorRestController.class)
public class AuthorRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnCorrectAuthorsList() throws Exception {
        List<Author> authors = List.of(new Author(1, "Author1"), new Author(2, "Author2"));

        List<AuthorDto> authorsDto = authors.stream()
                .map(AuthorDto::fromDomainObject)
                .collect(Collectors.toList());

        given(authorService.findAll())
                .willReturn(authors);

        mvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(authorsDto)));
    }

    @Test
    public void shouldDeleteAuthor() throws Exception {
        Author author = new Author(1, "Author1");
        given(authorService.deleteById(1L)).willReturn(author);

        mvc.perform(delete("/api/authors/{id}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(authorService, times(1)).deleteById(1L);
    }

    @Test
    public void shouldCreateAuthor() throws Exception {
        Author expectedAuthor = new Author(1L, "Author1");

        AuthorDto authorCreateDto = new AuthorDto();
        authorCreateDto.setFullName("Author1");

        AuthorDto expectedAuthorDto = AuthorDto.fromDomainObject(expectedAuthor);

        given(authorService.insert("Author1")).willReturn(expectedAuthor);

        String expectedResult = objectMapper.writeValueAsString(expectedAuthorDto);

        mvc.perform(post("/api/authors")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorCreateDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));

        verify(authorService, times(1)).insert("Author1");
    }

    @Test
    public void shouldEditAuthor() throws Exception {
        Author expectedAuthor = new Author(1L, "Author1");

        AuthorDto authorEditDto = new AuthorDto();
        authorEditDto.setFullName("Author1");

        AuthorDto expectedAuthorDto = AuthorDto.fromDomainObject(expectedAuthor);

        given(authorService.update(1L, "Author1")).willReturn(expectedAuthor);

        String expectedResult = objectMapper.writeValueAsString(expectedAuthorDto);

        mvc.perform(put("/api/authors/{id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorEditDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));

        verify(authorService, times(1)).update(1L, "Author1");
    }
}
