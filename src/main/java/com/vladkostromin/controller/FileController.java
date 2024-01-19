package com.vladkostromin.controller;

import com.google.gson.Gson;
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
import java.util.List;

public class FileController extends HttpServlet {

    private final static FileService fileService = new FileService(new FileHibernateImpl());

    private final static EventService eventService = new EventService(new EventHibernateImpl());

    private final static UserService userService = new UserService(new UserHibernateImpl());

    @Override
    public void init() throws ServletException {
        super.init();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String getAllParam = req.getParameter("get_all_files");
        if(getAllParam == null) {
            Integer fileId = Integer.parseInt(req.getParameter("file_id"));
            Integer userId = Integer.parseInt(req.getParameter("user_id"));
            if(fileId == null || userId == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            File file = fileService.getFileById(fileId);
            User user = userService.getUserById(userId);
            eventService.saveEvent(new Event(null, user, file));

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            String jsonResponse = new Gson().toJson(file);
            resp.getWriter().println(jsonResponse);
        } else {
            List<File> fileList = fileService.getAllFiles();
            for (File f : fileList) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");

                String jsonResponse = new Gson().toJson(f);
                resp.getWriter().println(jsonResponse);
            }

        }


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
        File fileToUpdate = fileService.getFileById(id);
        if(nameToUpdate != null) fileToUpdate.setName(nameToUpdate);
        if(pathToUpdate != null) fileToUpdate.setFilePath(pathToUpdate);

        File updatedFile = fileService.updateFile(fileToUpdate);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String jsonResponse = new Gson().toJson(updatedFile);
        resp.getWriter().println(jsonResponse);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String filePath = req.getParameter("file_path");

        File createdFile = fileService.saveFile(new File(null, name, filePath));

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
        File deletedFile = fileService.deleteFile(id);

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
