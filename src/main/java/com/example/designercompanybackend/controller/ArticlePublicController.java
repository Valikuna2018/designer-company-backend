package com.example.designercompanybackend.controller;

import com.example.designercompanybackend.model.Article;
import com.example.designercompanybackend.repository.ArticleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
public class ArticlePublicController {

    private final ArticleRepository repo;

    public ArticlePublicController(ArticleRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Article> listPublished() {
        return repo.findByPublishedTrueOrderByCreatedAtDesc();
    }

    @GetMapping("/{slug}")
    public Article getBySlug(@PathVariable String slug) {
        Article a = repo.findBySlug(slug).orElseThrow();
        if (!a.isPublished()) {
            // hide unpublished from public
            throw new RuntimeException("Article not found");
        }
        return a;
    }
}