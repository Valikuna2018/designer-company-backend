package com.example.designercompanybackend.controller;

import com.example.designercompanybackend.dto.ArticleDto;
import com.example.designercompanybackend.model.Article;
import com.example.designercompanybackend.repository.ArticleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/articles")
@CrossOrigin(origins = "*")
public class ArticleAdminController {

    private final ArticleRepository repo;

    public ArticleAdminController(ArticleRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Article> listAll() {
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ArticleDto dto) {
        if (dto.getSlug() == null || dto.getSlug().isBlank()) {
            return ResponseEntity.badRequest().body("slug is required");
        }
        String slug = dto.getSlug().trim();
        if (repo.existsBySlug(slug)) {
            return ResponseEntity.badRequest().body("slug already exists");
        }

        Article a = new Article();
        a.setTitle(dto.getTitle());
        a.setSlug(slug);
        a.setContent(dto.getContent());
        a.setCoverImageUrl(dto.getCoverImageUrl());
        a.setPublished(dto.getPublished() != null && dto.getPublished());

        return ResponseEntity.ok(repo.save(a));
    }

    @PutMapping("/{id}")
    public Article update(@PathVariable Long id, @RequestBody ArticleDto dto) {
        Article a = repo.findById(id).orElseThrow();

        if (dto.getTitle() != null) a.setTitle(dto.getTitle());
        if (dto.getContent() != null) a.setContent(dto.getContent());
        if (dto.getCoverImageUrl() != null) a.setCoverImageUrl(dto.getCoverImageUrl());
        if (dto.getPublished() != null) a.setPublished(dto.getPublished());

        if (dto.getSlug() != null) a.setSlug(dto.getSlug().trim());

        return repo.save(a);
    }

    @PutMapping("/{id}/publish")
    public Article publish(@PathVariable Long id) {
        Article a = repo.findById(id).orElseThrow();
        a.setPublished(true);
        return repo.save(a);
    }

    @PutMapping("/{id}/unpublish")
    public Article unpublish(@PathVariable Long id) {
        Article a = repo.findById(id).orElseThrow();
        a.setPublished(false);
        return repo.save(a);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}