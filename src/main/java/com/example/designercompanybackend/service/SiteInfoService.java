package com.example.designercompanybackend.service;

import com.example.designercompanybackend.dto.SiteInfoDto;
import com.example.designercompanybackend.model.SiteInfo;
import com.example.designercompanybackend.repository.SiteInfoRepository;
import org.springframework.stereotype.Service;

@Service
public class SiteInfoService {

    private final SiteInfoRepository repo;

    public SiteInfoService(SiteInfoRepository repo) {
        this.repo = repo;
    }

    public SiteInfo create(SiteInfoDto dto) {

        String key = dto.getKeyName().toUpperCase();

        if (repo.existsByKeyName(key)) {
            throw new RuntimeException("Key already exists");
        }

        SiteInfo info = new SiteInfo(key, dto.getValue());

        return repo.save(info);
    }

    public SiteInfo update(String keyName, SiteInfoDto dto) {

        String key = keyName.toUpperCase();

        SiteInfo info = repo.findByKeyName(key)
                .orElse(new SiteInfo(key, null));

        info.setValue(dto.getValue());

        return repo.save(info);
    }

    public void delete(String keyName) {

        String key = keyName.toUpperCase();

        SiteInfo info = repo.findByKeyName(key)
                .orElseThrow(() -> new RuntimeException("Key not found"));

        repo.delete(info);
    }
}