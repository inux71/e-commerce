package com.grabieckacper.ecommerce.shared.controller;

import com.grabieckacper.ecommerce.shared.model.File;
import com.grabieckacper.ecommerce.shared.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> getFile(@PathVariable Long id) throws FileNotFoundException {
        File file = fileService.getFileById(id);
        Resource resource = fileService.createResource(file);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, file.getMimeType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
}
