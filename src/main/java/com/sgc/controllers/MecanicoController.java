package com.sgc.controllers;

import com.sgc.domains.Mecanico;
import com.sgc.dtos.MecanicoDTO;
import com.sgc.services.MecanicoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    @GetMapping("/DTO")
    public ResponseEntity<?> getMecanicosDTO(){
        List<MecanicoDTO> listaDeMecanicos = mecanicoService.getMecanicosDTO();
        return ResponseEntity.ok(listaDeMecanicos);
    }

    @GetMapping("/{idMecanico}")
    public ResponseEntity<?> getMecanicoPorId(@PathVariable Integer idMecanico){
        return mecanicoService.getMecanicoById(idMecanico).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/DTO/{idMecanico}")
    public ResponseEntity<?> getMecanicoDTOPorId(@PathVariable Integer idMecanico){
        return mecanicoService.getMecanicoDTOById(idMecanico).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> postMecanico(@Validated(MecanicoDTO.OnCreate.class) @RequestBody MecanicoDTO mecanicoDTO){
        Mecanico nuevoMecanico = mecanicoService.createMecanico(mecanicoDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idMecanico}").buildAndExpand(nuevoMecanico.getIdMecanico()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idMecanico}")
    public ResponseEntity<?> putMecanico(@PathVariable Integer idMecanico, @Valid @RequestBody MecanicoDTO mecanicoDTO){
        return mecanicoService.updateMecanico(idMecanico, mecanicoDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{idMecanico}")
    public ResponseEntity<?> patchMecanico(@PathVariable Integer idMecanico, @Valid  @RequestBody MecanicoDTO mecanicoDTO){
        System.out.println("idMecanico: " + idMecanico.toString());
        System.out.println("mecanicoDTO: " + mecanicoDTO.toString());
        return mecanicoService.patchMecanico(idMecanico, mecanicoDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idMecanico}")
    public ResponseEntity<?> deleteMecanico(@PathVariable Integer idMecanico){
        System.out.println("idMecanico: " + idMecanico.toString());
        boolean isDeleted = mecanicoService.deleteMecanico(idMecanico);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
