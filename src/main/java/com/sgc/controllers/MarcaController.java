package com.sgc.controllers;

import com.sgc.domains.Marca;
import com.sgc.services.MarcaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/marca")
public class MarcaController {

    @Autowired
    private MarcaServiceImpl marcaService;

    @GetMapping
    public ResponseEntity<?> getMarcas(){
        List<Marca> listaDeMarcas = marcaService.getMarcas();
        return ResponseEntity.ok(listaDeMarcas);
    }

    @GetMapping("/{idMarca}")
    public ResponseEntity<?> getMarcaPorId(@PathVariable int idMarca){
        return marcaService.getMarcaById(idMarca).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/{nombreMarca}")
    public ResponseEntity<?> buscarMarcaPorNombre(@PathVariable String nombreMarca) {
        List<Marca> resultados = marcaService.getMarcaPorNombre(nombreMarca);
        return ResponseEntity.ok(resultados);
    }

    @PostMapping
    public ResponseEntity<?> postMarca(@Valid @RequestBody Marca marca){
        Marca nuevaMarca = marcaService.createMarca(marca);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idMarca}").buildAndExpand(nuevaMarca.getIdMarca()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idMarca}")
    public ResponseEntity<?> putMarca(@PathVariable Integer idMarca, @Valid @RequestBody Marca marca){
        return marcaService.updateMarca(idMarca, marca).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{idMarca}")
    public ResponseEntity<?> patchMarca(@PathVariable Integer idMarca, @Valid @RequestBody Marca marca) {
        return marcaService.patchMarca(idMarca, marca).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idMarca}")
    public ResponseEntity<?> deleteMarca(@PathVariable int idMarca){
        boolean isDelted = marcaService.deleteMarca(idMarca);
        return isDelted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}