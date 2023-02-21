package com.uday.mongodb.service;

import com.uday.mongodb.model.User;
import com.uday.mongodb.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public String saveUser(User user) {
        final User save = repository.save(user);
        return user.getUserId();
    }

    public User getUser(String id) {

        final User user = repository.findUserByUserId(id);

        return user;
    }

}
