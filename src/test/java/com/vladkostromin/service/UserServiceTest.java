package com.vladkostromin.service;

import com.vladkostromin.model.User;
import com.vladkostromin.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    private User user;


    @BeforeEach
    public void setUpEvent() {
        user = new User(1, "UserTestName", new ArrayList<>());
    }

    @Test
    public void testGetFile_Found() {
        when(userRepository.findById(user.getId())).thenReturn(user);
        User resultUser = userService.getUserById(user.getId());
        Assertions.assertEquals(user, resultUser);
        verify(userRepository).findById(user.getId());

    }

    @Test
    public void testGetFileById_NotFound() {
        when(userRepository.findById(user.getId())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUserById(user.getId()));
    }

    @Test
    public void testCreateFile() {
        when(userRepository.save(user)).thenReturn(user);
        User saveUser = userService.saveUser(user);
        Assertions.assertEquals(user, saveUser);
        verify(userRepository).save(user);
    }

    @Test
    public void testUpdateFile_Found() {
        when(userRepository.update(user)).thenReturn(user);
        User updatedUser = userService.updateUser(user);
        Assertions.assertEquals(user, updatedUser);
        verify(userRepository).update(user);
    }

    @Test
    public void testUpdateFile_NotFound() {
        when(userRepository.update(user)).thenReturn(null);
        Assertions.assertThrows(Exception.class, () -> userService.updateUser(user));
    }

    @Test
    public void testGetAllFiles() {
        List<User> users = List.of(user, user, user, user);
        when(userRepository.getAll()).thenReturn(users);
        List<User> resultList = userService.getAllUsers();
        Assertions.assertEquals(users, resultList);
        verify(userRepository).getAll();
    }

    @Test
    public void testDeleteFile() {
        when(userRepository.deleteById(user.getId())).thenReturn(user);
        User deletedUser = userService.deleteUser(user.getId());
        Assertions.assertEquals(user, deletedUser);
        verify(userRepository).deleteById(user.getId());
    }
    @Test
    public void testDeleteFile_NotFound() {
        when(userRepository.deleteById(user.getId())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.deleteUser(user.getId()));
    }
}
