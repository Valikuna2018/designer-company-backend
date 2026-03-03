package com.example.designercompanybackend.controller;


import com.example.designercompanybackend.dto.SiteInfoDto;
import com.example.designercompanybackend.model.SiteInfo;
import com.example.designercompanybackend.repository.SiteInfoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/site-info")
@CrossOrigin(origins = "*")
public class SiteInfoAdminController {

    private final SiteInfoRepository repo;

    public SiteInfoAdminController(SiteInfoRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SiteInfoDto dto) {
        String key = dto.getKeyName().toUpperCase();
        if (repo.existsByKeyName(key)) {
            return ResponseEntity.badRequest().body("Key already exists");
        }
        SiteInfo saved = repo.save(new SiteInfo(key, dto.getValue()));
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{keyName}")
    public ResponseEntity<?> update(@PathVariable String keyName, @RequestBody SiteInfoDto dto) {
        String key = keyName.toUpperCase();
        SiteInfo info = repo.findByKeyName(key).orElse(new SiteInfo(key, null));
        info.setValue(dto.getValue());
        return ResponseEntity.ok(repo.save(info));
    }

    @DeleteMapping("/{keyName}")
    public ResponseEntity<?> delete(@PathVariable String keyName) {
        String key = keyName.toUpperCase();
        SiteInfo info = repo.findByKeyName(key).orElseThrow();
        repo.delete(info);
        return ResponseEntity.ok("Deleted");
    }
}