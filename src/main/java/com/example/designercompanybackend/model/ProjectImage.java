package com.example.designercompanybackend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "project_images")
public class ProjectImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="project_id", nullable=false)
    private Project project;

    @Column(nullable=false)
    private String imageUrl;

    @Column(nullable=false)
    private int displayOrder = 0;

    public ProjectImage() {}

    public ProjectImage(Project project, String imageUrl, int displayOrder) {
        this.project = project;
        this.imageUrl = imageUrl;
        this.displayOrder = displayOrder;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }
}