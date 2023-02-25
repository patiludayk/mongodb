package com.uday.mongodb.controller;

import com.uday.mongodb.model.User;
import com.uday.mongodb.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class UserController {

    private UserServiceImpl repoService;

    @Autowired
    public UserController(UserServiceImpl repoService) {
        this.repoService = repoService;
    }

    @PostMapping("save")
    public List<String> saveUser(@RequestBody List<User> users) {
        return repoService.saveUser(users);
    }

    @GetMapping(value = {"get", "get/{id}"})
    public List<User> getUser(@PathVariable("id") Optional<String> userId) {
        return repoService.getUser(userId);
    }


}
