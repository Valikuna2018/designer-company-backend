package com.example.designercompanybackend.controller;

import com.example.designercompanybackend.dto.ArticleDto;
import com.example.designercompanybackend.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/articles")
@CrossOrigin(origins = "*")
public class ArticleAdminController {

    private final ArticleService articleService;

    public ArticleAdminController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<?> listAll() {
        return ResponseEntity.ok(articleService.listAll());
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> create(@ModelAttribute ArticleDto dto) {
        return ResponseEntity.ok(articleService.create(dto));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @ModelAttribute ArticleDto dto) {
        return ResponseEntity.ok(articleService.update(id, dto));
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<?> publish(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.publish(id));
    }

    @PutMapping("/{id}/unpublish")
    public ResponseEntity<?> unpublish(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.unpublish(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.ok("Deleted");
    }
}