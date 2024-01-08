package com.vladkostromin.view;

import com.vladkostromin.controller.UserController;
import com.vladkostromin.model.Event;
import com.vladkostromin.model.User;
import com.vladkostromin.repository.HibernateImpl.UserHibernateImpl;
import com.vladkostromin.service.UserService;

import java.util.ArrayList;

public class Main {

    private static final UserService userService = new UserService(new UserHibernateImpl());
    private static final UserController userController = new UserController(userService);

    public static void main(String[] args) {
//        User user = new User(null, "John Snow", new ArrayList<>());
//        User userToCreate = userController.createUser(user);
//        System.out.println(userToCreate);
        User test = userController.getUser(1);
        System.out.println(test);
    }
}
