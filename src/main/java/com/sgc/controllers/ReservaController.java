package com.sgc.controllers;

import com.sgc.domains.Reserva;
import com.sgc.dtos.ReservaDTO;
import com.sgc.services.ReservaServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaServiceImpl reservaService;

    @GetMapping
    public ResponseEntity<?> getReservas() {
        List<Reserva> listaDeReservas = reservaService.getReserva();
        return ResponseEntity.ok(listaDeReservas);
    }

    @GetMapping("/{idReserva}")
    public ResponseEntity<?> getReservaPorId(@PathVariable int idReserva) {
        return reservaService.getReservaById(idReserva).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> postReserva(@Valid @RequestBody ReservaDTO reservaDTO) {
        try {
            Reserva nuevaReserva = reservaService.createReserva(reservaDTO);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{idReserva}")
                    .buildAndExpand(nuevaReserva.getIdReserva())
                    .toUri();
            return ResponseEntity.created(location).build();
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear la reserva: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }


    @PutMapping("/{idReserva}")
    public ResponseEntity<?> putReserva(@PathVariable Integer idReserva, @Valid @RequestBody ReservaDTO reservaDTO) {
        return reservaService.updateReserva(idReserva, reservaDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{idReserva}")
    public ResponseEntity<?> patchReserva(@PathVariable Integer idReserva, @Valid @RequestBody ReservaDTO reservaDTO) {
        return reservaService.patchReserva(idReserva, reservaDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idReserva}")
    public ResponseEntity<?> deleteReserva(@PathVariable int idReserva) {
        boolean isDeleted = reservaService.deleteReserva(idReserva);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
