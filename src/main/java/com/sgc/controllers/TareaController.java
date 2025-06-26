package com.sgc.controllers;

import com.sgc.domains.Tarea;
import com.sgc.dtos.TareaDTO;
import com.sgc.services.TareaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tarea")
public class TareaController {

    @Autowired
    private TareaServiceImpl tareaService;

    // CRUD básico

    @GetMapping
    public ResponseEntity<?> getTareas() {
        List<Tarea> listaDeTareas = tareaService.getTareas();
        return ResponseEntity.ok(listaDeTareas);
    }

    @GetMapping("/{idTarea}")
    public ResponseEntity<?> getTareaPorId(@PathVariable int idTarea) {
        return tareaService.getTareaById(idTarea).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<TareaDTO>> getTareaPorFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<TareaDTO> tareas = tareaService.getTareasByFecha(fecha);
        return ResponseEntity.ok(tareas);
    }


    @PostMapping
    public ResponseEntity<TareaDTO> postTarea(@Valid @RequestBody TareaDTO tareaDTO) {
        Tarea nuevaTarea = tareaService.createTarea(tareaDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idTarea}")
                .buildAndExpand(nuevaTarea.getIdTarea())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(new TareaDTO(nuevaTarea)); // ✅ Devuelve el objeto creado
    }


    /*@PostMapping("/fecha/{fecha}") // No está funcionando, ver BB
    public ResponseEntity<?> crearTareaConFecha(@PathVariable LocalDate fecha, @RequestBody TareaDTO dto) {
        dto.setFechaTarea(fecha);
        Tarea nuevaTarea = tareaService.createTarea(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idTarea}").buildAndExpand(nuevaTarea.getIdTarea()).toUri();
        return ResponseEntity.created(location).build();
    }*/


    @PutMapping("/{idTarea}")
    public ResponseEntity<?> putTarea(@PathVariable Integer idTarea, @Valid @RequestBody TareaDTO tareaDTO) {
        return tareaService.updateTarea(idTarea, tareaDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{idTarea}")
    public ResponseEntity<?> patchTarea(@PathVariable Integer idTarea, @Valid @RequestBody TareaDTO tareaDTO) {
        return tareaService.patchTarea(idTarea, tareaDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idTarea}")
    public ResponseEntity<?> deleteTarea(@PathVariable int idTarea) {
        boolean isDeleted = tareaService.deleteTarea(idTarea);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

/*
    @GetMapping("/diasdisponibles")
    public ResponseEntity<?> getDiasDisponibles() {
        List<Object[]> dias = tareaService.getDiasDisponibles();
        return ResponseEntity.ok(dias);
    }


    @GetMapping("/horarios")
    public ResponseEntity<?> getHorariosOcupadosPorDia(@RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Tarea> tareas = tareaService.getHorariosOcupadosPorDia(fecha);
        return ResponseEntity.ok(tareas);
    }

    */
}
