package com.example.designercompanybackend.service;

import com.example.designercompanybackend.dto.ArticleDto;
import com.example.designercompanybackend.model.Article;
import com.example.designercompanybackend.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository repo;
    private final FileStorageService fileStorageService;

    public ArticleService(ArticleRepository repo, FileStorageService fileStorageService) {
        this.repo = repo;
        this.fileStorageService = fileStorageService;
    }

    public List<Article> listAll() {
        return repo.findAll();
    }

    public List<Article> listPublished() {
        return repo.findByPublishedTrueOrderByCreatedAtDesc();
    }

    public Article getPublishedBySlug(String slug) {
        Article article = repo.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        if (!article.isPublished()) {
            throw new RuntimeException("Article not found");
        }

        return article;
    }

    public Article create(ArticleDto dto) {
        if (dto.getSlug() == null || dto.getSlug().isBlank()) {
            throw new RuntimeException("slug is required");
        }

        String slug = dto.getSlug().trim();

        if (repo.existsBySlug(slug)) {
            throw new RuntimeException("slug already exists");
        }

        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setSlug(slug);
        article.setContent(dto.getContent());
        article.setPublished(dto.getPublished() != null && dto.getPublished());

        MultipartFile coverImage = dto.getCoverImage();
        if (coverImage != null && !coverImage.isEmpty()) {
            String fileUrl = fileStorageService.saveFile(coverImage);
            article.setCoverImageUrl(fileUrl);
        }

        return repo.save(article);
    }

    public Article update(Long id, ArticleDto dto) {
        Article article = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        if (dto.getTitle() != null) {
            article.setTitle(dto.getTitle());
        }

        if (dto.getContent() != null) {
            article.setContent(dto.getContent());
        }

        if (dto.getPublished() != null) {
            article.setPublished(dto.getPublished());
        }

        if (dto.getSlug() != null && !dto.getSlug().isBlank()) {
            String newSlug = dto.getSlug().trim();

            if (!newSlug.equals(article.getSlug()) && repo.existsBySlug(newSlug)) {
                throw new RuntimeException("slug already exists");
            }

            article.setSlug(newSlug);
        }

        MultipartFile coverImage = dto.getCoverImage();
        if (coverImage != null && !coverImage.isEmpty()) {
            String fileUrl = fileStorageService.saveFile(coverImage);
            article.setCoverImageUrl(fileUrl);
        }

        return repo.save(article);
    }

    public Article publish(Long id) {
        Article article = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        article.setPublished(true);
        return repo.save(article);
    }

    public Article unpublish(Long id) {
        Article article = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        article.setPublished(false);
        return repo.save(article);
    }

    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Article not found");
        }

        repo.deleteById(id);
    }
}