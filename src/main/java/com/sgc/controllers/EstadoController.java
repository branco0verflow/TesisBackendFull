package com.sgc.controllers;

import com.sgc.domains.Estado;
import com.sgc.services.EstadoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/estado")
public class EstadoController {

    @Autowired
    private EstadoServiceImpl estadoService;

    @GetMapping
    public ResponseEntity<?> getEstados() {
        List<Estado> lista = estadoService.getEstados();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{idEstado}")
    public ResponseEntity<?> getEstadoById(@PathVariable Integer idEstado) {
        return estadoService.getEstadoById(idEstado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> postEstado(@Valid @RequestBody Estado estado) {
        Estado nuevo = estadoService.createEstado(estado);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idEstado}")
                .buildAndExpand(nuevo.getIdEstado())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idEstado}")
    public ResponseEntity<?> putEstado(@PathVariable Integer idEstado, @Valid @RequestBody Estado estado) {
        return estadoService.updateEstado(idEstado, estado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{idEstado}")
    public ResponseEntity<?> patchEstado(@PathVariable Integer idEstado, @Valid @RequestBody Estado estado) {
        return estadoService.patchEstado(idEstado, estado)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idEstado}")
    public ResponseEntity<?> deleteEstado(@PathVariable Integer idEstado) {
        boolean deleted = estadoService.deleteEstado(idEstado);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
