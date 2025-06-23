package com.sgc.controllers;

import com.sgc.domains.Administrador;
import com.sgc.services.AdministradorServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/administrador")
public class AdministradorController {

    @Autowired
    private AdministradorServiceImpl administradorService;

    @GetMapping
    public ResponseEntity<?> getAdministradores() {
        List<Administrador> listaDeAdministradores = administradorService.getAdministrador();
        return ResponseEntity.ok(listaDeAdministradores);
    }

    @GetMapping("/{idAdministrador}")
    public ResponseEntity<?> getAdministradorPorId(@PathVariable Integer idAdministrador) {
        return administradorService.getAdministradorPorId(idAdministrador)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> postAdministrador(@Valid @RequestBody Administrador administrador) {
        Administrador nuevoAdministrador = administradorService.createAdministrador(administrador);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idAdministrador}").buildAndExpand(nuevoAdministrador.getIdAdmin()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idAdministrador}")
    public ResponseEntity<?> putAdministrador(@PathVariable Integer idAdministrador, @Valid  @RequestBody Administrador administrador) {
        return administradorService.updateAdministrador(idAdministrador, administrador).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{idAdministrador}") //Para modificar partes de los datos
    public ResponseEntity<?> patchMarca(@PathVariable Integer idAdministrador, @Valid  @RequestBody Administrador administrador) {
        return administradorService.patchAdministrador(idAdministrador, administrador).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idAdministrador}")
    public ResponseEntity<?> deleteAdministrador(@PathVariable Integer idAdministrador) {
        boolean isDelted = administradorService.deleteAdministrador(idAdministrador);
        return isDelted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
