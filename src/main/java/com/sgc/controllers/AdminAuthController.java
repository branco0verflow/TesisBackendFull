package com.sgc.controllers;

import com.sgc.security.AdministradorUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminAuthController {

    @GetMapping("/me")
    public ResponseEntity<?> getAdminInfo(Authentication auth) {
        if (auth == null || !(auth.getPrincipal() instanceof AdministradorUserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "No autenticado"));
        }

        AdministradorUserDetails userDetails = (AdministradorUserDetails) auth.getPrincipal();
        return ResponseEntity.ok(Map.of("idAdmin", userDetails.getId())); // 👈 esto devuelve JSON
    }

}

