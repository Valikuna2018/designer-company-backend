package com.example.designercompanybackend.controller;

import com.example.designercompanybackend.model.Project;
import com.example.designercompanybackend.model.ProjectImage;
import com.example.designercompanybackend.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectPublicController {

    private final ProjectService projectService;

    public ProjectPublicController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> listPublished() {
        return projectService.listPublished();
    }

    @GetMapping("/{slug}")
    public Project getBySlug(@PathVariable String slug) {
        return projectService.getPublishedBySlug(slug);
    }

    @GetMapping("/{projectId}/images")
    public List<ProjectImage> images(@PathVariable Long projectId) {
        return projectService.getImages(projectId);
    }
}