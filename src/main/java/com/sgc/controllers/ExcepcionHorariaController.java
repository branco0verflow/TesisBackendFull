package com.sgc.controllers;

import com.sgc.domains.ExcepcionHoraria;
import com.sgc.repositories.ExcepcionHorariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/excepciones")
@CrossOrigin(origins = "*")
public class ExcepcionHorariaController {

    @Autowired
    private ExcepcionHorariaRepository repo;

    @PostMapping
    public ResponseEntity<Void> agregar(@RequestBody ExcepcionHoraria excepcion) {
        repo.save(excepcion);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<ExcepcionHoraria> listar() {
        return repo.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


