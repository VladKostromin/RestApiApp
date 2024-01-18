package com.vladkostromin.service;

import com.vladkostromin.model.File;
import com.vladkostromin.repository.FileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {
    @Mock
    private FileRepository fileRepository;
    @InjectMocks
    private FileService fileService;

    private File file;

    @BeforeEach
    public void setUpEvent() {
        file = new File(1, "testFileName", "/src/test/testfiles");
    }

    @Test
    public void testGetFile_Found() {
        when(fileRepository.findById(file.getId())).thenReturn(file);
        File resultFile = fileService.getFileById(file.getId());
        Assertions.assertEquals(file, resultFile);
        verify(fileRepository).findById(file.getId());

    }

    @Test
    public void testGetFileById_NotFound() {
        when(fileRepository.findById(file.getId())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () -> fileService.getFileById(file.getId()));
    }

    @Test
    public void testCreateFile() {
        when(fileRepository.save(file)).thenReturn(file);
        File savedFile = fileService.saveFile(file);
        Assertions.assertEquals(file, savedFile);
        verify(fileRepository).save(file);
    }

    @Test
    public void testUpdateFile_Found() {
        when(fileRepository.update(file)).thenReturn(file);
        File updatedFile = fileService.updateFile(file);
        Assertions.assertEquals(file, updatedFile);
        verify(fileRepository).update(file);
    }

    @Test
    public void testUpdateFile_NotFound() {
        when(fileRepository.update(file)).thenReturn(null);
        Assertions.assertThrows(Exception.class, () -> fileService.updateFile(file));
    }

    @Test
    public void testGetAllFiles() {
        List<File> files = List.of(file, file, file, file);
        when(fileRepository.getAll()).thenReturn(files);
        List<File> resultList = fileService.getAllFiles();
        Assertions.assertEquals(files, resultList);
        verify(fileRepository).getAll();
    }

    @Test
    public void testDeleteFile() {
        when(fileRepository.deleteById(file.getId())).thenReturn(file);
        File deletedFile = fileService.deleteFile(file.getId());
        Assertions.assertEquals(file, deletedFile);
        verify(fileRepository).deleteById(file.getId());
    }
    @Test
    public void testDeleteFile_NotFound() {
        when(fileRepository.deleteById(file.getId())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () -> fileService.deleteFile(file.getId()));
    }
}
