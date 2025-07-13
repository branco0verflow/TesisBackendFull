package com.sgc.controllers;

import com.sgc.domains.Tarea;
import com.sgc.dtos.TareaDTO;
import com.sgc.services.TareaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
                .body(new TareaDTO(nuevaTarea)); // Devuelve el objeto creado
    }



    @PutMapping("/{idTarea}") // No parece necesario actualizar T-ODO el objeto, ya que datos que no se alteren
    public ResponseEntity<?> putTarea(@PathVariable Integer idTarea, @Valid @RequestBody TareaDTO tareaDTO) {
        return tareaService.updateTarea(idTarea, tareaDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{idTarea}")
    public ResponseEntity<?> patchTarea(@PathVariable Integer idTarea, @Valid @RequestBody TareaDTO tareaDTO) {
        return tareaService.patchTarea(idTarea, tareaDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idTarea}")
    public ResponseEntity<?> deleteTarea(@PathVariable int idTarea) {
        try {
            boolean eliminado = tareaService.eliminarTareaSegura(idTarea);
            return eliminado
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar la tarea: " + e.getMessage());
        }
    }


}
