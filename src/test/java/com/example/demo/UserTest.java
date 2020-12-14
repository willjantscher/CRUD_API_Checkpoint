package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    @Test
    public void testUser() {
        User user = new User();
        user.setEmail("john@example.com");
        user.setId(5l);
        user.setPassword("1234");
        assertEquals("john@example.com", user.getEmail());
        assertEquals(5l, user.getId());
        assertEquals("1234", user.getPassword());
    }

}
