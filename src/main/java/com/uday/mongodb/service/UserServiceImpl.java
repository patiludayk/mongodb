package com.uday.mongodb.service;

import com.uday.mongodb.model.User;
import com.uday.mongodb.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public List<String> saveUser(List<User> users) {
        if (users.size() > 1) {
            final List<User> userList = repository.findAll();
            return users.stream().map(user -> repository.save(user)).map(savedUser -> savedUser.getUserId()).collect(Collectors.toList());
            //final List<User> userList = repository.saveAll(users);
            //return userList.stream().map(user -> user.getUserId()).collect(Collectors.toList());
        }
        return Arrays.asList(repository.save(users.get(0)).getUserId());
    }

    public List<User> getUser(Optional<String> id) {
        if (!id.isPresent()) {
            return repository.findAll();
        }
        return Arrays.asList(repository.findUserByUserId(id.get()));
    }

}
