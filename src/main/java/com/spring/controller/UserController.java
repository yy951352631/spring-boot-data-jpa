package com.spring.controller;

import com.spring.entity.User;
import com.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wtq
 * @date 2019/8/19 - 14:17
 */
@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/user/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        User user = userRepository.getOne(id);
        return user;
    }

    @GetMapping(value = "/user")
    public User getUser(User user) {
        userRepository.save(user);
        return user;
    }
}
