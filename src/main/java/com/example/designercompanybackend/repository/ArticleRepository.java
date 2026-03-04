package com.example.designercompanybackend.repository;

import com.example.designercompanybackend.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findBySlug(String slug);
    boolean existsBySlug(String slug);

    List<Article> findByPublishedTrueOrderByCreatedAtDesc();
}