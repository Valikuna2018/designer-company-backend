package com.example.designercompanybackend.service;

import com.example.designercompanybackend.dto.ArticleDto;
import com.example.designercompanybackend.model.Article;
import com.example.designercompanybackend.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository repo;

    public ArticleService(ArticleRepository repo) {
        this.repo = repo;
    }

    public List<Article> listAll() {
        return repo.findAll();
    }

    public Article create(ArticleDto dto) {
        if (dto.getSlug() == null || dto.getSlug().isBlank()) {
            throw new RuntimeException("slug is required");
        }

        String slug = dto.getSlug().trim();
        if (repo.existsBySlug(slug)) {
            throw new RuntimeException("slug already exists");
        }

        Article a = new Article();
        a.setTitle(dto.getTitle());
        a.setSlug(slug);
        a.setContent(dto.getContent());
        a.setCoverImageUrl(dto.getCoverImageUrl());
        a.setPublished(dto.getPublished() != null && dto.getPublished());

        return repo.save(a);
    }

    public Article update(Long id, ArticleDto dto) {
        Article a = repo.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));

        if (dto.getTitle() != null) a.setTitle(dto.getTitle());
        if (dto.getContent() != null) a.setContent(dto.getContent());
        if (dto.getCoverImageUrl() != null) a.setCoverImageUrl(dto.getCoverImageUrl());
        if (dto.getPublished() != null) a.setPublished(dto.getPublished());

        if (dto.getSlug() != null && !dto.getSlug().isBlank()) {
            String newSlug = dto.getSlug().trim();
            if (!newSlug.equals(a.getSlug()) && repo.existsBySlug(newSlug)) {
                throw new RuntimeException("slug already exists");
            }
            a.setSlug(newSlug);
        }

        return repo.save(a);
    }

    public Article publish(Long id) {
        Article a = repo.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        a.setPublished(true);
        return repo.save(a);
    }

    public Article unpublish(Long id) {
        Article a = repo.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        a.setPublished(false);
        return repo.save(a);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Article not found");
        }
        repo.deleteById(id);
    }

    public List<Article> listPublished() {
        return repo.findByPublishedTrueOrderByCreatedAtDesc();
    }

    public Article getPublishedBySlug(String slug) {
        Article a = repo.findBySlug(slug).orElseThrow(() -> new RuntimeException("Article not found"));
        if (!a.isPublished()) {
            throw new RuntimeException("Article not found");
        }
        return a;
    }
}