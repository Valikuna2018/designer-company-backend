package com.example.designercompanybackend.service;

import com.example.designercompanybackend.model.user.Admin;
import com.example.designercompanybackend.repository.user.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public void createAdmin(String username, String encodedPassword) {
        if (adminRepository.existsByUsername(username)) {
            throw new RuntimeException("Admin already exists");
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(encodedPassword);
        admin.setRole("ADMIN");
        admin.setEnabled(true);

        adminRepository.save(admin);
    }
}