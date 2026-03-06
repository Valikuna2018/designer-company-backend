package com.example.designercompanybackend.controller;

import org.springframework.web.multipart.MultipartFile;
import com.example.designercompanybackend.dto.ProjectDto;
import com.example.designercompanybackend.dto.ProjectImageDto;
import com.example.designercompanybackend.model.Project;
import com.example.designercompanybackend.model.ProjectImage;
import com.example.designercompanybackend.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/projects")
@CrossOrigin(origins = "*")
public class ProjectAdminController {

    private final ProjectService projectService;

    public ProjectAdminController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> listAll() {
        return projectService.listAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProjectDto dto) {
        return ResponseEntity.ok(projectService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProjectDto dto) {
        return ResponseEntity.ok(projectService.update(id, dto));
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<?> publish(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.publish(id));
    }

    @PutMapping("/{id}/unpublish")
    public ResponseEntity<?> unpublish(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.unpublish(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    @PostMapping("/{projectId}/images")
    public ResponseEntity<?> addImage(@PathVariable Long projectId, @RequestBody ProjectImageDto dto) {
        ProjectImage img = projectService.addImage(projectId, dto);
        return ResponseEntity.ok(img);
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable Long imageId) {
        projectService.deleteImage(imageId);
        return ResponseEntity.ok("Deleted");
    }

    @PostMapping(value = "/{projectId}/upload-image", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadImage(@PathVariable Long projectId,
                                         @RequestPart("file") MultipartFile file,
                                         @RequestParam(value = "displayOrder", required = false) Integer displayOrder) {
        return ResponseEntity.ok(projectService.uploadImage(projectId, file, displayOrder));
    }
}