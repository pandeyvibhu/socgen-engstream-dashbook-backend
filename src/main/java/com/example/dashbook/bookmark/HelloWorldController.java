package com.example.dashbook.bookmark;

import com.example.dashbook.bookmark.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class HelloWorldController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String helloWorld() {
        return "Hello World!";
    }

    @PostMapping("/createUser")
    public void create(@RequestBody User user){
        userRepository.save(user);
    }


    @GetMapping("/user/{id}")
    public Optional<User> get(@PathVariable("id") int id){
        return userRepository.findById(id);
    }

}
