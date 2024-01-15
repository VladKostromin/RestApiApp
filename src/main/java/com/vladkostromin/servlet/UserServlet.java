package com.vladkostromin.servlet;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vladkostromin.controller.UserController;
import com.vladkostromin.jsonserializer.EventSerializer;
import com.vladkostromin.jsonserializer.UserSerializer;
import com.vladkostromin.model.Event;
import com.vladkostromin.model.User;
import com.vladkostromin.repository.HibernateImpl.UserHibernateImpl;
import com.vladkostromin.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class UserServlet extends HttpServlet {


    private final static UserService userService = new UserService(new UserHibernateImpl());
    private final static UserController userController = new UserController(userService);

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("user_id"));
        if(id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        User user = userController.getUser(id);
        if(user == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(UserSerializer.class,new UserSerializer());
        gsonBuilder.registerTypeAdapter(Event.class, new EventSerializer());
        Gson gson = gsonBuilder.create();

        String jsonResponse = gson.toJson(user);
        resp.getWriter().println(jsonResponse);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("user_id"));
        String nameToUpdate = req.getParameter("name");
        if(id == null || nameToUpdate.equals(null)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        User userToUpdate = userController.getUser(id);
        userToUpdate.setName(nameToUpdate);
        User updatedUser = userController.updateUser(userToUpdate);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonResponse = new Gson().toJson(updatedUser);
        resp.getWriter().println(jsonResponse);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        if(name == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        User user = userController.createUser(new User(null, name, new ArrayList<>()));
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonResponse = new Gson().toJson(user);
        resp.getWriter().println(jsonResponse);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("user_id"));
        User deletedUser = userController.deleteUser(id);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonResponse = new Gson().toJson(deletedUser);
        resp.getWriter().println(jsonResponse);
    }

    @Override
    public void destroy() {
    }
}
