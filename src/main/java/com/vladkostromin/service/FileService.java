package com.vladkostromin.service;

import com.vladkostromin.model.File;
import com.vladkostromin.repository.FileRepository;

import java.util.List;

public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File getFileByUd(Integer id) {
        return fileRepository.findById(id);
    }

    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    public File updateFile(File file) {
        return fileRepository.update(file);
    }

    public List<File> getAllFiles() {
        return fileRepository.getAll();
    }
    public File deleteFile(Integer id) {
        return fileRepository.deleteById(id);
    }
}
