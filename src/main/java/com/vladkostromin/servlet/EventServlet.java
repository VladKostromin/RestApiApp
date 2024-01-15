package com.vladkostromin.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vladkostromin.controller.EventController;
import com.vladkostromin.controller.FileController;
import com.vladkostromin.controller.UserController;
import com.vladkostromin.jsonserializer.EventSerializer;
import com.vladkostromin.model.Event;
import com.vladkostromin.model.File;
import com.vladkostromin.model.User;
import com.vladkostromin.repository.HibernateImpl.EventHibernateImpl;
import com.vladkostromin.repository.HibernateImpl.FileHibernateImpl;
import com.vladkostromin.repository.HibernateImpl.UserHibernateImpl;
import com.vladkostromin.service.EventService;
import com.vladkostromin.service.FileService;
import com.vladkostromin.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EventServlet extends HttpServlet {

    private final static FileService fileService = new FileService(new FileHibernateImpl());
    private final static FileController fileController = new FileController(fileService);

    private final static EventService eventService = new EventService(new EventHibernateImpl());
    private final static EventController eventController = new EventController(eventService);

    private final static UserService userService = new UserService(new UserHibernateImpl());
    private final static UserController userController = new UserController(userService);

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("event_id"));
        if(id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        Event event = eventController.getEvent(id);
        System.out.println(event);

        jsonSerializeBuilder(resp, event);

    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer eventId = Integer.parseInt(req.getParameter("event_id"));
        Integer fileId = Integer.parseInt(req.getParameter("file_id"));
        Integer userId = Integer.parseInt(req.getParameter("user_id"));

        File fileToUpdate = fileController.getFile(fileId);
        User userToUpdate = userController.getUser(userId);

        Event eventToUpdate = eventController.getEvent(eventId);

        if(fileToUpdate != null) eventToUpdate.setFile(fileToUpdate);
        if(userToUpdate != null) eventToUpdate.setUser(userToUpdate);

        Event updatedEvent = eventController.updateEvent(eventToUpdate);

        jsonSerializeBuilder(resp, updatedEvent);


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("event_id"));
        Event deletedEvent = eventController.deleteEvent(id);

        jsonSerializeBuilder(resp, deletedEvent);
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    private void jsonSerializeBuilder(HttpServletResponse resp, Event event) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Event.class, new EventSerializer());

        Gson gson = gsonBuilder.create();
        String jsonResponse = gson.toJson(event);
        resp.getWriter().println(jsonResponse);
    }


}
