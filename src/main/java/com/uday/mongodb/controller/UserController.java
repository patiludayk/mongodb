package com.uday.mongodb.controller;

import com.uday.mongodb.model.User;
import com.uday.mongodb.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserServiceImpl repoService;

    @Autowired
    public UserController(UserServiceImpl repoService){
        this.repoService = repoService;
    }

    @PostMapping("save")
    public String saveUser(@RequestBody User user){
        return repoService.saveUser(user);
    }

    @GetMapping("get/{id}")
    public User getUser(@PathVariable("id") String userId){
        return repoService.getUser(userId);
    }

}
