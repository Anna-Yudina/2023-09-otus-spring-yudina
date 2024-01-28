package ru.otus.hw.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.AuthorController;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.models.Author;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.UserService;
import java.util.List;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
@Import(SecurityConfiguration.class)
@WithMockUser("yudina")
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private UserService userService;

    @Test
    void shouldReturnCorrectAuthorsList() throws Exception {
        List<Author> authors = List.of(new Author(1, "Author1"), new Author(2, "Author2"));
        given(authorService.findAll()).willReturn(authors);

        mvc.perform(get("/authors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("authorDto"))
                .andExpect(model().attribute("authors", authors))
                .andExpect(content().string(containsString("Author1")))
                .andExpect(content().string(containsString("Author2")));
    }

    @Test
    public void shouldDeleteAuthorAndRedirectToAuthorsList() throws Exception {
        mvc.perform(get("/authors/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/authors"));

        verify(authorService, times(1))
                .deleteById(eq(1L));
    }

    @Test
    @WithMockUser("yudina")
    public void shouldCreateAuthorAndRedirectToAuthorsList() throws Exception {
        AuthorDto author = new AuthorDto(1, "Author1");

        mvc.perform(post("/authors")
                        .flashAttr("authorDto", author))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/authors"));

        verify(authorService, times(1)).insert(eq("Author1"));
    }

    @Test
    public void shouldEditAuthorAndRedirectToAuthorsList() throws Exception {
        String newAuthorName = "somebody name";
        AuthorDto author = new AuthorDto(1, newAuthorName);

        mvc.perform(post("/authors/edit/1")
                        .flashAttr("author", author))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/authors"));

        verify(authorService, times(1))
                .update(eq(1L), eq(newAuthorName));
    }
}
