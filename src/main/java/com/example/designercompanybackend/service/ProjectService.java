package com.example.designercompanybackend.service;

import com.example.designercompanybackend.dto.ProjectDto;
import com.example.designercompanybackend.dto.ProjectImageDto;
import com.example.designercompanybackend.model.Project;
import com.example.designercompanybackend.model.ProjectImage;
import com.example.designercompanybackend.repository.ProjectImageRepository;
import com.example.designercompanybackend.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepo;
    private final ProjectImageRepository imageRepo;

    public ProjectService(ProjectRepository projectRepo, ProjectImageRepository imageRepo) {
        this.projectRepo = projectRepo;
        this.imageRepo = imageRepo;
    }

    // Public
    public List<Project> listPublished() {
        return projectRepo.findByPublishedTrueOrderByCreatedAtDesc();
    }

    public Project getPublishedBySlug(String slug) {
        Project p = projectRepo.findBySlug(slug).orElseThrow(() -> new RuntimeException("Project not found"));
        if (!p.isPublished()) throw new RuntimeException("Project not found");
        return p;
    }

    public List<ProjectImage> getImages(Long projectId) {
        return imageRepo.findByProjectIdOrderByDisplayOrderAsc(projectId);
    }

    // Admin
    public List<Project> listAll() {
        return projectRepo.findAll();
    }

    public Project create(ProjectDto dto) {
        if (dto.getSlug() == null || dto.getSlug().isBlank()) {
            throw new RuntimeException("slug is required");
        }
        String slug = dto.getSlug().trim();
        if (projectRepo.existsBySlug(slug)) {
            throw new RuntimeException("slug already exists");
        }

        Project p = new Project();
        p.setTitle(dto.getTitle());
        p.setSlug(slug);
        p.setDescription(dto.getDescription());
        p.setCoverImageUrl(dto.getCoverImageUrl());
        p.setPublished(dto.getPublished() != null && dto.getPublished());

        return projectRepo.save(p);
    }

    public Project update(Long id, ProjectDto dto) {
        Project p = projectRepo.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));

        if (dto.getTitle() != null) p.setTitle(dto.getTitle());
        if (dto.getDescription() != null) p.setDescription(dto.getDescription());
        if (dto.getCoverImageUrl() != null) p.setCoverImageUrl(dto.getCoverImageUrl());
        if (dto.getPublished() != null) p.setPublished(dto.getPublished());

        if (dto.getSlug() != null && !dto.getSlug().isBlank()) {
            String newSlug = dto.getSlug().trim();
            if (!newSlug.equals(p.getSlug()) && projectRepo.existsBySlug(newSlug)) {
                throw new RuntimeException("slug already exists");
            }
            p.setSlug(newSlug);
        }

        return projectRepo.save(p);
    }

    public void delete(Long id) {
        if (!projectRepo.existsById(id)) throw new RuntimeException("Project not found");
        projectRepo.deleteById(id);
    }

    public Project publish(Long id) {
        Project p = projectRepo.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        p.setPublished(true);
        return projectRepo.save(p);
    }

    public Project unpublish(Long id) {
        Project p = projectRepo.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        p.setPublished(false);
        return projectRepo.save(p);
    }

    @Transactional
    public ProjectImage addImage(Long projectId, ProjectImageDto dto) {
        Project p = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        int order = (dto.getDisplayOrder() != null) ? dto.getDisplayOrder() : 0;

        ProjectImage img = new ProjectImage(p, dto.getImageUrl(), order);
        return imageRepo.save(img);
    }

    public void deleteImage(Long imageId) {
        if (!imageRepo.existsById(imageId)) throw new RuntimeException("Image not found");
        imageRepo.deleteById(imageId);
    }
}