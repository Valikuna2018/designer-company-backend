package com.example.designercompanybackend.controller;

import com.example.designercompanybackend.dto.SiteInfoDto;
import com.example.designercompanybackend.model.SiteInfo;
import com.example.designercompanybackend.service.SiteInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/site-info")
@CrossOrigin(origins = "*")
public class SiteInfoAdminController {

    private final SiteInfoService siteInfoService;

    public SiteInfoAdminController(SiteInfoService siteInfoService) {
        this.siteInfoService = siteInfoService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SiteInfoDto dto) {
        SiteInfo saved = siteInfoService.create(dto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{keyName}")
    public ResponseEntity<?> update(@PathVariable String keyName,
                                    @RequestBody SiteInfoDto dto) {
        return ResponseEntity.ok(siteInfoService.update(keyName, dto));
    }

    @DeleteMapping("/{keyName}")
    public ResponseEntity<?> delete(@PathVariable String keyName) {
        siteInfoService.delete(keyName);
        return ResponseEntity.ok("Deleted");
    }
}