package com.example.demo;
//docker container must be up and running for these tests if using

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository repository;

    @Test
    @Transactional
    @Rollback
    public void testGetUser() throws Exception {
        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("something-secret");
        repository.save(user);

        MockHttpServletRequestBuilder request = get("/user")
                .contentType(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("User{id=1, email='john@example.com', password='123'}"));
    }

    @Test
    @Transactional
    @Rollback
    public void testGetUsers() throws Exception {
        User user1 = new User();
        user1.setEmail("john@example.com");
        user1.setPassword("something-secret");
        repository.save(user1);

        User user2 = new User();
        user2.setEmail("eliza@example.com");
        user2.setPassword("something-secret");
        repository.save(user2);

        MockHttpServletRequestBuilder request = get("/users")
                .contentType(MediaType.APPLICATION_JSON);
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].email", equalTo(user1.getEmail())))
                .andExpect(jsonPath("$[0].id", equalTo("1")));
    }

    @Test
    @Transactional
    @Rollback
    public void testPostUser() throws Exception {
        MockHttpServletRequestBuilder request = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("  {\n" +
                        "    \"email\": \"john@example.com\",\n" +
                        "    \"password\": \"something-secret\"\n" +
                        "  }");
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("{\"email\":\"john@example.com\",\"password\":\"something-secret\"}"));
    }

}
