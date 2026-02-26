package com.example.designercompanybackend.controller;

import com.example.designercompanybackend.dto.AuthResponseDto;
import com.example.designercompanybackend.dto.LoginRequestDto;
import com.example.designercompanybackend.security.jwt.JwtUtil;
import com.example.designercompanybackend.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          AdminService adminService,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {
        logger.info("Login attempt for admin: {}", dto.getUsername());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // generate JWT (same style as your old project)
            String jwt = jwtUtil.generateToken(userDetails);

            List<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            logger.info("Admin '{}' authenticated successfully", userDetails.getUsername());

            return ResponseEntity.ok(new AuthResponseDto(jwt, userDetails.getUsername(), roles));

        } catch (Exception e) {
            logger.warn("Failed login attempt for admin '{}': {}", dto.getUsername(), e.getMessage());
            throw e;
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (authentication != null) ? authentication.getName() : "unknown";
        SecurityContextHolder.getContext().setAuthentication(null);

        logger.info("Admin '{}' logged out", username);
        return ResponseEntity.ok("Logged out. Please delete the client-side JWT.");
    }

    // OPTIONAL: create new admin (only if you want it)
    // IMPORTANT: this endpoint must be protected in SecurityConfig (only ADMIN can call it)
    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody LoginRequestDto dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        adminService.createAdmin(dto.getUsername(), dto.getPassword());
        return ResponseEntity.ok("Admin registered successfully!");
    }
}