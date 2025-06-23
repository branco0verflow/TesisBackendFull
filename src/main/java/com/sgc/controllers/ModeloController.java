package com.sgc.controllers;

import com.sgc.domains.Modelo;
import com.sgc.services.ModeloServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/modelo")
public class ModeloController {

    @Autowired
    private ModeloServiceImpl modeloService;

    @GetMapping
    public ResponseEntity<?> getModelos() {
        List<Modelo> listaDeModelos = modeloService.getModelos();
        return ResponseEntity.ok(listaDeModelos);
    }

    @GetMapping("/{idModelo}")
    public ResponseEntity<?> getModeloPorId(@PathVariable int idModelo) {
        return modeloService.getModeloById(idModelo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/marca/{idMarca}")
    public ResponseEntity<?> getModelosPorMarca(@PathVariable Integer idMarca) {
        List<Modelo> modelos = modeloService.getModelosByMarcaId(idMarca);
        if (modelos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(modelos);
    }

    @GetMapping("/buscar")          // Creado por BB sin Push
    public ResponseEntity<?> buscarModelos(
            @RequestParam String nombre,
            @RequestParam Integer idMarca) {
        List<Modelo> modelos = modeloService.buscarPorNombreYMarca(nombre, idMarca);
        return ResponseEntity.ok(modelos);
    }


    @PostMapping
    public ResponseEntity<?> postModelo(@Valid @RequestBody Modelo modelo) {
        Modelo nuevoModelo = modeloService.createModelo(modelo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{idModelo}")
                .buildAndExpand(nuevoModelo.getIdModelo())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idModelo}")
    public ResponseEntity<?> putModelo(@PathVariable Integer idModelo, @Valid @RequestBody Modelo modelo) {
        return modeloService.updateModelo(idModelo, modelo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{idModelo}")
    public ResponseEntity<?> patchModelo(@PathVariable Integer idModelo, @Valid @RequestBody Modelo modelo) {
        return modeloService.patchModelo(idModelo, modelo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idModelo}")
    public ResponseEntity<?> deleteModelo(@PathVariable int idModelo) {
        boolean isDeleted = modeloService.deleteModelo(idModelo);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}