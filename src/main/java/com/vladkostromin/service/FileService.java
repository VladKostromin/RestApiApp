package com.vladkostromin.service;

import com.vladkostromin.model.File;
import com.vladkostromin.repository.FileRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File getFileById(Integer id) {
        File file = fileRepository.findById(id);
        if(file == null) throw new EntityNotFoundException("file with " + id + " not found");
        return file;
    }

    public File saveFile(File file) {
        return fileRepository.save(file);
    }

    public File updateFile(File file) {
        File updatedFile = fileRepository.update(file);
        if(updatedFile == null) throw new EntityNotFoundException("file with " + file.getId() + " not found");
        return updatedFile;
    }

    public List<File> getAllFiles() {
        return fileRepository.getAll();
    }
    public File deleteFile(Integer id) {
        File deletedFile = fileRepository.deleteById(id);
        if(deletedFile == null) throw new EntityNotFoundException("File with " + id + " not found");
        return deletedFile;
    }
}
