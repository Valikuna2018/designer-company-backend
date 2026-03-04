package com.example.designercompanybackend.controller;

import com.example.designercompanybackend.model.Article;
import com.example.designercompanybackend.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
public class ArticlePublicController {

    private final ArticleService articleService;

    public ArticlePublicController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public List<Article> listPublished() {
        return articleService.listPublished();
    }

    @GetMapping("/{slug}")
    public Article getBySlug(@PathVariable String slug) {
        return articleService.getPublishedBySlug(slug);
    }
}