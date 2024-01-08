package com.vladkostromin.controller;

import com.vladkostromin.model.User;
import com.vladkostromin.service.UserService;

import java.util.List;

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public User getUser(Integer id) {
        return userService.getUserById(id);
    }

    public User createUser(User user) {
        return userService.saveUser(user);
    }

    public User updateUser(User user) {
        return userService.updateUser(user);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public User deleteUser(Integer id) {
        return userService.deleteUser(id);
    }
}
