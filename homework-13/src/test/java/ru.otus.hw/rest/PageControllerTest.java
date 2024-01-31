package ru.otus.hw.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controller.PageController;
import ru.otus.hw.security.SecurityConfiguration;
import ru.otus.hw.services.PersonService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PageController.class)
@Import(SecurityConfiguration.class)
public class PageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonService userService;

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    public void givenAuthRequestAdmin_shouldSucceed() throws Exception {
        mvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "yudina",
            authorities = {"ROLE_USER"}
    )
    @Test
    public void givenAuthRequestUser_shouldNotSucceed() throws Exception {
        mvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void givenAuthRequestAnonymous_shouldSucceed() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
}
