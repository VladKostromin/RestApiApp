package com.vladkostromin.service;

import com.vladkostromin.model.User;
import com.vladkostromin.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Integer id) {
        User user = userRepository.findById(id);
        if(user == null) throw new EntityNotFoundException("user with " + id + " not found");
        return user;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        User updatedUser = userRepository.update(user);
        if(updatedUser == null) throw new EntityNotFoundException("user with " + user.getId() + " not found");
        return updatedUser;
    }

    public List<User> getAllUsers() {
        return userRepository.getAll();
    }
    public User deleteUser(Integer id) {
        User deletedUser = userRepository.deleteById(id);
        if(deletedUser == null) throw new EntityNotFoundException("user with " + id + " not found");
        return deletedUser;
    }
}
