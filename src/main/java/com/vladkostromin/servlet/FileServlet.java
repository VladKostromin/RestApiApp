package com.vladkostromin.servlet;

import com.google.gson.Gson;
import com.vladkostromin.controller.EventController;
import com.vladkostromin.controller.FileController;
import com.vladkostromin.controller.UserController;
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

public class FileServlet extends HttpServlet {

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
        Integer fileId = Integer.parseInt(req.getParameter("file_id"));
        Integer userId = Integer.parseInt(req.getParameter("user_id"));

        if(fileId == null || userId == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        File file = fileController.getFile(fileId);
        User user = userController.getUser(userId);
        eventController.createEvent(new Event(null, user, file));

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonResponse = new Gson().toJson(file);
        resp.getWriter().println(jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("file_id"));
        String nameToUpdate = req.getParameter("name");
        String pathToUpdate = req.getParameter("file_path");
        if(id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        File fileToUpdate = fileController.getFile(id);
        if(nameToUpdate != null) fileToUpdate.setName(nameToUpdate);
        if(pathToUpdate != null) fileToUpdate.setFilePath(pathToUpdate);

        File updatedFile = fileController.updateFile(fileToUpdate);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonResponse = new Gson().toJson(updatedFile);
        resp.getWriter().println(jsonResponse);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String filePath = req.getParameter("file_path");

        File createdFile = fileController.createFile(new File(null, name, filePath));

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonResponse = new Gson().toJson(createdFile);
        resp.getWriter().println(jsonResponse);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer id = Integer.parseInt(req.getParameter("file_id"));
        if(id == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        File deletedFile = fileController.deleteFile(id);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonResponse = new Gson().toJson(deletedFile);
        resp.getWriter().println(jsonResponse);
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}
