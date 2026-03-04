package com.example.designercompanybackend.dto;

public class ArticleDto {
    private String title;
    private String slug;
    private String content;
    private String coverImageUrl;
    private Boolean published;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCoverImageUrl() { return coverImageUrl; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }

    public Boolean getPublished() { return published; }
    public void setPublished(Boolean published) { this.published = published; }
}