package com.sgc.controllers;

import com.sgc.domains.TipoTarea;
import com.sgc.services.TipoTareaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tipotarea")
public class TipoTareaController {

    @Autowired
    private TipoTareaServiceImpl tipoTareaService;

    @GetMapping
    public ResponseEntity<List<TipoTarea>> getTipoTareas() {
        List<TipoTarea> listaTipoTarea = tipoTareaService.getTipoTarea();
        return ResponseEntity.ok(listaTipoTarea);
    }

    @GetMapping("/{idTipoTarea}")
    public ResponseEntity<?> getTipoTareaById(@PathVariable Integer idTipoTarea) {
        return tipoTareaService.getTipoTareaById(idTipoTarea)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createTipoTarea(@Valid @RequestBody TipoTarea tipoTarea) {
        TipoTarea nueva = tipoTareaService.createTipoTarea(tipoTarea);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idTipoTarea}").buildAndExpand(nueva.getIdTipoTarea()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idTipoTarea}")
    public ResponseEntity<?> updateTipoTarea(@PathVariable Integer idTipoTarea, @Valid @RequestBody TipoTarea tipoTarea) {
        return tipoTareaService.updateTipoTarea(idTipoTarea, tipoTarea)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{idTipoTarea}")
    public ResponseEntity<?> putCliente(@PathVariable Integer idTipoTarea, @Valid @RequestBody TipoTarea tipoTarea) {
        return tipoTareaService.patchTipoTarea(idTipoTarea, tipoTarea)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idTipoTarea}")
    public ResponseEntity<?> deleteTipoTarea(@PathVariable Integer idTipoTarea) {
        boolean deleted = tipoTareaService.deleteTipoTarea(idTipoTarea);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
