package com.sgc.controllers;

import com.sgc.domains.Vehiculo;
import com.sgc.dtos.VehiculoPatchDTO;
import com.sgc.services.VehiculoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vehiculo")
public class VehiculoController {

    @Autowired
    private VehiculoServiceImpl vehiculoService;

    @GetMapping
    public ResponseEntity<?> getVehiculos() {
        List<Vehiculo> listaDeVehiculos = vehiculoService.getVehiculo();
        return ResponseEntity.ok(listaDeVehiculos);
    }

    @GetMapping("/{idVehiculo}")
    public ResponseEntity<?> getVehiculoPorId(@PathVariable int idVehiculo) {
        return vehiculoService.getVehiculoById(idVehiculo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> postVehiculo(@Valid @RequestBody Vehiculo vehiculo) {
        Vehiculo nuevoVehiculo = vehiculoService.createVehiculo(vehiculo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idVehiculo}").buildAndExpand(nuevoVehiculo.getIdVehiculo()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idVehiculo}")
    public ResponseEntity<?> putVehiculo(@PathVariable Integer idVehiculo, @Valid @RequestBody Vehiculo vehiculo) {
        return vehiculoService.updateVehiculo(idVehiculo, vehiculo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{idVehiculo}")
    public ResponseEntity<?> patchVehiculo(@PathVariable Integer idVehiculo, @Valid @RequestBody Vehiculo vehiculo) {
        return vehiculoService.patchVehiculo(idVehiculo, vehiculo).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/nuevoKilometraje/{idVehiculo}")
    public ResponseEntity<?> patchVehiculo(@PathVariable Integer idVehiculo,
                                           @RequestBody VehiculoPatchDTO dto) {
        return vehiculoService.patchKilometraje(idVehiculo, dto.getKilometrajeVehiculo())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{idVehiculo}")
    public ResponseEntity<?> deleteVehiculo(@PathVariable int idVehiculo) {
        boolean isDeleted = vehiculoService.deleteVehiculo(idVehiculo);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
