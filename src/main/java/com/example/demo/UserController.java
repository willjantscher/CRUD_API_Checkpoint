package com.example.demo;
//./gradlew bootRun

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository repository;
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/user")
    public String getUser() {
        User user = this.repository.findById(1l).get();
        return user.toString();
    }

    @GetMapping("/users")
    public String getUsers() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(User.class, new UserSerializerGet());
        mapper.registerModule(module);

        ArrayList<String> output = new ArrayList<>();
        Iterable<User> users = this.repository.findAll();
        for (User user : users) {
            String userString = mapper.writeValueAsString(user);
            output.add(userString);
        }

        return output.toString();
    }

    @PostMapping("/users")
    public String postUser(@RequestBody User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(User.class, new UserSerializerGet());
        mapper.registerModule(module);

        User newUser =  this.repository.save(user);
        return mapper.writeValueAsString(newUser);
    }
//TEST NEEDED STILL
    @GetMapping("/users/{id}")
    public String getUserById(@PathVariable Long id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(User.class, new UserSerializerGet());
        mapper.registerModule(module);

        User userById = this.repository.findById(id).get();

        return mapper.writeValueAsString(userById);
    }
//TEST NEEDED STILL
    @PatchMapping("/users/{id}")
    public String patchUserById(@PathVariable Long id, @RequestBody User user) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(User.class, new UserSerializerPostPatch());
        mapper.registerModule(module);
        user.setId(id);
        User oldUser = this.repository.findById(id).get();
        if (user.getPassword() != null && user.getEmail() != null) {
            User updatedUser = this.repository.save(user);
        } else if(user.getEmail() == null) {
            user.setEmail(oldUser.getEmail());
            User updatedUser = this.repository.save(user);
        } else if(user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        }
        User updatedUser = this.repository.save(user);
        return mapper.writeValueAsString(updatedUser);
    }
//TEST NEEDED STILL
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        this.repository.deleteById(id);
        long remainingUsers = this.repository.count();
        return "{\n" +
                "  \"count\":" + remainingUsers + "\n" +
                "}\n"
                ;
    }
    //TEST NEEDED STILL
    @PostMapping("/users/authenticate")
    public String postAuthenticate(@RequestBody User user) {
        //create custom method in userRepository

        User dbUser = this.repository.findByEmail(user.getEmail());
        String output;
        if (dbUser.getPassword().equals(user.getPassword())) {
            output =
                    "{\n" +
                    "  \"authenticated\": true,\n" +
                    "  \"user\": {\n" +
                    "    \"id\": " +dbUser.getId() + ",\n" +
                    "    \"email\": \"" + dbUser.getEmail() + "\n" +
                    "  }\n" +
                    "}";
        } else {
            output = "{\n" +
                    "  \"authenticated\": false\n" +
                    "}";
        }
        return output;
    }
}
