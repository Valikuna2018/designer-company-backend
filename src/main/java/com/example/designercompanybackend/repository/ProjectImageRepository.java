package com.example.designercompanybackend.repository;

import com.example.designercompanybackend.model.ProjectImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectImageRepository extends JpaRepository<ProjectImage, Long> {
    List<ProjectImage> findByProjectIdOrderByDisplayOrderAsc(Long projectId);
}