package com.example.designercompanybackend.security.service;

import com.example.designercompanybackend.model.user.Admin;
import com.example.designercompanybackend.repository.user.AdminRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepo;

    public UserDetailsServiceImpl(AdminRepository adminRepo) {
        this.adminRepo = adminRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));

        if (!admin.isEnabled()) {
            throw new UsernameNotFoundException("Admin disabled");
        }

        // Spring expects ROLE_ prefix
        String role = "ROLE_" + admin.getRole(); // ADMIN -> ROLE_ADMIN

        return new org.springframework.security.core.userdetails.User(
                admin.getUsername(),
                admin.getPassword(),
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}