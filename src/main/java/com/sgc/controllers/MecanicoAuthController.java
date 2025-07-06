package com.sgc.controllers;

import com.sgc.security.MecanicoUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/mecanico")
public class MecanicoAuthController {

    @GetMapping("/me")
    public ResponseEntity<?> getMecanicoInfo(Authentication auth) {
        if (auth == null || !(auth.getPrincipal() instanceof MecanicoUserDetails userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "No autenticado"));
        }

        return ResponseEntity.ok(Map.of(
                "idMecanico", userDetails.getId(),
                "nombreMecanico", userDetails.getNombre(),
                "apellidoMecanico", userDetails.getApellido()
        ));
    }
}
