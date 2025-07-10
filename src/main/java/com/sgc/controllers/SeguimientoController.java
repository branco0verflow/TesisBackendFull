package com.sgc.controllers;

import com.sgc.dtos.SeguimientoReservaDTO;
import com.sgc.services.SeguimientoService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Validated
@RestController
@RequestMapping("/seguimiento")
public class SeguimientoController {

    private final SeguimientoService seguimientoService;

    public SeguimientoController(SeguimientoService seguimientoService) {
        this.seguimientoService = seguimientoService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SeguimientoReservaDTO>> obtenerReservas(
            @RequestParam @NotBlank String documento,
            @RequestParam @Email String email) {

        List<SeguimientoReservaDTO> reservas =
                seguimientoService.buscarReservas(documento, email);

        return reservas.isEmpty()
                ? ResponseEntity.noContent().build()   // 204
                : ResponseEntity.ok(reservas);         // 200
    }
}
