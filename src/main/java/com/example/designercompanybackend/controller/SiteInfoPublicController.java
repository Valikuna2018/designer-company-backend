package com.example.designercompanybackend.controller;

import com.example.designercompanybackend.model.SiteInfo;
import com.example.designercompanybackend.repository.SiteInfoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/site-info")
@CrossOrigin(origins = "*")
public class SiteInfoPublicController {

    private final SiteInfoRepository repo;

    public SiteInfoPublicController(SiteInfoRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<SiteInfo> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{keyName}")
    public SiteInfo getByKey(@PathVariable String keyName) {
        return repo.findByKeyName(keyName.toUpperCase()).orElseThrow();
    }
}