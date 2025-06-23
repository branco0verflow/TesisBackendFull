package com.sgc.controllers;

import com.sgc.domains.Mecanico;
import com.sgc.dtos.MecanicoDTO;
import com.sgc.services.MecanicoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/mecanico")
public class MecanicoController {

    @Autowired
    private MecanicoServiceImpl mecanicoService;

    @GetMapping
    public ResponseEntity<?> getMecanicos(){
        List<Mecanico> listaDeMecanicos = mecanicoService.getMecanicos();
        return ResponseEntity.ok(listaDeMecanicos);
    }

    @GetMapping("/{idMecanico}")
    public ResponseEntity<?> getMecanicoPorId(@PathVariable Integer idMecanico){
        return mecanicoService.getMecanicoById(idMecanico).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> postMecanico(@Valid @RequestBody MecanicoDTO mecanicoDTO){
        Mecanico nuevoMecanico = mecanicoService.createMecanico(mecanicoDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idMecanico}").buildAndExpand(nuevoMecanico.getIdMecanico()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<?> putMecanico(@PathVariable Integer idMecanico, @Valid @RequestBody MecanicoDTO mecanicoDTO){
        return mecanicoService.updateMecanico(idMecanico, mecanicoDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping
    public ResponseEntity<?> patchMecanico(@PathVariable Integer idMecanico, @Valid  @RequestBody MecanicoDTO mecanicoDTO){
        return mecanicoService.patchMecanico(idMecanico, mecanicoDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMecanico(@PathVariable Integer idMecanico){
        boolean isDeleted = mecanicoService.deleteMecanico(idMecanico);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
