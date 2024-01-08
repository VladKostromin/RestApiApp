package com.vladkostromin.servlet;


import com.google.gson.Gson;
import com.vladkostromin.controller.UserController;
import com.vladkostromin.model.User;
import com.vladkostromin.repository.HibernateImpl.UserHibernateImpl;
import com.vladkostromin.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {


    private final UserService userService = new UserService(new UserHibernateImpl());
    private final UserController userController = new UserController(userService);

    @Override
    public void init() throws ServletException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
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

        String jsonResponse = new Gson().toJson(user);
        resp.getWriter().println(jsonResponse);
        System.out.println(jsonResponse);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void destroy() {
    }
}
