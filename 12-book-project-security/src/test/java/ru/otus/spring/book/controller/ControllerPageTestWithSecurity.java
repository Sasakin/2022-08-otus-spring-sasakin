package ru.otus.spring.book.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.spring.book.page.controller.PageController;
import ru.otus.spring.book.security.error.ExtAccessDeniedHandler;
import ru.otus.spring.book.services.UserService;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PageController.class)
@Import({ExtAccessDeniedHandler.class})
public class ControllerPageTestWithSecurity {

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService userService;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    public void testPublicPages() throws Exception {
        mvc.perform(get("/").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
        mvc.perform(get("/public").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
        mvc.perform(get("/login").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
    }

    @Test
    public void testRedirectIfNotAccess() throws Exception {
        mvc.perform(get("/list").contentType(MediaType.TEXT_HTML))
                .andExpect(redirectedUrlPattern("**/login"));
        mvc.perform(get("/book/edit").contentType(MediaType.TEXT_HTML))
                .andExpect(redirectedUrlPattern("**/login"));
        mvc.perform(get("/book/add").contentType(MediaType.TEXT_HTML))
                .andExpect(redirectedUrlPattern("**/login"));
        mvc.perform(get("/book/delete").contentType(MediaType.TEXT_HTML))
                .andExpect(redirectedUrlPattern("**/login"));
        mvc.perform(get("/admin").contentType(MediaType.TEXT_HTML))
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @WithMockUser(username = "user", password = "password", authorities = {"USER"})
    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/list").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
        mvc.perform(get("/book/edit").param("id","1").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
        mvc.perform(get("/book/add").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());
        mvc.perform(get("/book/delete").contentType(MediaType.TEXT_HTML))
                .andExpect(status().isOk());

    }

    @WithMockUser(username = "user", password = "password", authorities = {"USER"})
    @Test
    public void givenAuthRequestOnAdminPageShwldAccessDenied() throws Exception {
        mvc.perform(get("/admin").contentType(MediaType.TEXT_HTML))
                .andExpect(redirectedUrl("/403"))
                .andExpect(status().isFound());
    }

}
