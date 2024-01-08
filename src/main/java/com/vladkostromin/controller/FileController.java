package com.vladkostromin.controller;

import com.vladkostromin.model.File;
import com.vladkostromin.service.FileService;

import java.util.List;

public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    public File getFile(Integer id) {
        return fileService.getFileByUd(id);
    }

    public File createFile(File file) {
        return fileService.saveFile(file);
    }

    public File updateFile(File file) {
        return fileService.updateFile(file);
    }

    public List<File> getAllFiles() {
        return fileService.getAllFiles();
    }

    public File deleteFile(Integer id) {
        return fileService.deleteFile(id);
    }
}
