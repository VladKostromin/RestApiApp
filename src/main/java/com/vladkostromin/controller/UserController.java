package com.vladkostromin.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.List;

public class UserController extends HttpServlet {


    private final static UserService userService = new UserService(new UserHibernateImpl());

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String getAllParam = req.getParameter("get_all_users");
        if(getAllParam == null) {
            Integer id = Integer.parseInt(req.getParameter("user_id"));
            if(id == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            User user = userService.getUserById(id);
            if(user == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            jsonSerializeBuilder(resp, user);
        } else {
            List<User> users = userService.getAllUsers();
            for (User u : users) {
                jsonSerializeBuilder(resp, u);
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("user_id"));
        String nameToUpdate = req.getParameter("name");
        if(id == null || nameToUpdate.equals(null)) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        User userToUpdate = userService.getUserById(id);
        userToUpdate.setName(nameToUpdate);
        User updatedUser = userService.updateUser(userToUpdate);

        jsonSerializeBuilder(resp, updatedUser);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        if(name == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        User user = userService.saveUser(new User(null, name, new ArrayList<>()));

        jsonSerializeBuilder(resp, user);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("user_id"));
        User deletedUser = userService.deleteUser(id);

        jsonSerializeBuilder(resp, deletedUser);
    }

    @Override
    public void destroy() {
    }

    private void jsonSerializeBuilder(HttpServletResponse resp, User user) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(UserSerializer.class,new UserSerializer());
        gsonBuilder.registerTypeAdapter(Event.class, new EventSerializer());
        Gson gson = gsonBuilder.create();

        String jsonResponse = gson.toJson(user);
        resp.getWriter().println(jsonResponse);
    }
}
