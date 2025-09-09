package com.grabieckacper.ecommerce.shared.service;

import com.grabieckacper.ecommerce.shared.model.File;
import com.grabieckacper.ecommerce.shared.repository.FileRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File getFileById(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Resource createResource(File file) throws FileNotFoundException {
        java.io.File resource = new java.io.File(file.getPath());

        if (!resource.exists()) {
            throw new FileNotFoundException();
        }

        return new FileSystemResource(resource);
    }
}
