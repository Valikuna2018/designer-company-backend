package com.example.designercompanybackend.repository;

import com.example.designercompanybackend.model.SiteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteInfoRepository extends JpaRepository<SiteInfo, Long> {
    Optional<SiteInfo> findByKeyName(String keyName);
    boolean existsByKeyName(String keyName);
}
