package com.example.demo;
//./gradlew bootRun

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository repository;
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    public String getUsers() {
        Optional<User> user = this.repository.findById(1l);
        User user1 = user.get();
        return user1.toString();
    }
}
