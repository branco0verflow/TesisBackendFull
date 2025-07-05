package com.sgc.controllers;

import com.sgc.security.AdministradorUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    @GetMapping("/me")
    public ResponseEntity<?> getAdminInfo(Authentication auth) {
        AdministradorUserDetails userDetails = (AdministradorUserDetails) auth.getPrincipal();
        return ResponseEntity.ok(Map.of("idAdmin", userDetails.getId()));
    }
}

