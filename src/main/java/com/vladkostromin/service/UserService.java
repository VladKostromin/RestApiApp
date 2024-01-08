package com.vladkostromin.service;

import com.vladkostromin.model.User;
import com.vladkostromin.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.update(user);
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }
    public User deleteUser(Integer id) {
        return userRepository.deleteById(id);
    }
}
