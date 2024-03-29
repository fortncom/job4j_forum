package ru.job4j.forum.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.forum.Main;
import ru.job4j.forum.model.Authority;
import ru.job4j.forum.model.Post;
import ru.job4j.forum.model.User;
import ru.job4j.forum.service.PostService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostControlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService service;

    @Test
    @WithMockUser
    public void shouldRedirectToCreatePage() throws Exception {
        this.mockMvc.perform(get("/create"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("create"));
    }

    @Test
    @WithMockUser
    public void shouldRedirectToPostPage() throws Exception {
        Authority authority = Authority.of("USER");
        User user = User.of("root", "12345");
        user.setAuthority(authority);
        Post post = Post.of("Продаю машину", "Продаю машину ладу.");
        service.save(user);
        service.save(post);
        this.mockMvc.perform(get("/post").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("post"));
    }

    @Test
    @WithMockUser
    public void shouldRedirectToEditPage() throws Exception {
        Authority authority = Authority.of("USER");
        User user = User.of("root", "12345");
        user.setAuthority(authority);
        Post post = Post.of("Продаю машину", "Продаю машину ладу.");
        service.save(user);
        service.save(post);
        this.mockMvc.perform(get("/edit").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("edit"));
    }

    @Test
    @WithMockUser
    public void whenSaveShouldReturnToDefaultPage() throws Exception {
        this.mockMvc.perform(post("/save")
                .param("id", "0").param("name", "Продаю машину")
                .param("description", "Продаю машину ладу."))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Post> argument = ArgumentCaptor.forClass(Post.class);
        verify(service).save(argument.capture());
        assertThat(argument.getValue().getName(), is("Продаю машину"));
    }
}