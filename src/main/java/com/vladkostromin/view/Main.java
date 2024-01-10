package com.vladkostromin.view;

import com.vladkostromin.controller.EventController;
import com.vladkostromin.controller.FileController;
import com.vladkostromin.controller.UserController;
import com.vladkostromin.model.Event;
import com.vladkostromin.model.User;
import com.vladkostromin.repository.HibernateImpl.EventHibernateImpl;
import com.vladkostromin.repository.HibernateImpl.FileHibernateImpl;
import com.vladkostromin.repository.HibernateImpl.UserHibernateImpl;
import com.vladkostromin.service.EventService;
import com.vladkostromin.service.FileService;
import com.vladkostromin.service.UserService;

public class Main {

    private final static FileService fileService = new FileService(new FileHibernateImpl());
    private final static FileController fileController = new FileController(fileService);

    private final static EventService eventService = new EventService(new EventHibernateImpl());
    private final static EventController eventController = new EventController(eventService);

    private final static UserService userService = new UserService(new UserHibernateImpl());
    private final static UserController userController = new UserController(userService);
    public static void main(String[] args) {
        User user = userController.getUser(1);
        System.out.println(user);
        Event event = eventController.getEvent(1);
        System.out.println(event);
    }
}
