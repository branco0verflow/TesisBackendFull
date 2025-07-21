package com.sgc.controllers;

import com.sgc.dtos.DisponibilidadDTO;
import com.sgc.dtos.ProximaDisponibilidadDTO;
import com.sgc.dtos.TimeRangeDTO;
import com.sgc.services.DisponibilidadService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/disponibilidad")
public class DisponibilidadController {

    private final DisponibilidadService disponibilidadService;

    public DisponibilidadController(DisponibilidadService disponibilidadService) {
        this.disponibilidadService = disponibilidadService;
    }

    @GetMapping("/proxima-disponibilidad")
    public ResponseEntity<List<ProximaDisponibilidadDTO>> getProximaDisponibilidad(
            @RequestParam int minutosRequeridos,
            @RequestParam(defaultValue = "35") int limiteDias) {

        List<ProximaDisponibilidadDTO> disponibilidad = disponibilidadService.buscarProximaDisponibilidadPorMecanico(minutosRequeridos, limiteDias);
        return ResponseEntity.ok(disponibilidad);
    }


    @GetMapping
    public ResponseEntity<List<LocalDate>> obtenerDiasDisponibles(
            @RequestParam List<Integer> ids,
            @RequestParam(defaultValue = "35") int limiteDias
    ) {
        List<LocalDate> dias = disponibilidadService.buscarDiasDisponibles(ids, limiteDias);
        return ResponseEntity.ok(dias);
    }

    @GetMapping("/horas")
    public ResponseEntity<List<DisponibilidadDTO>> obtenerHorasDisponibles(
            @RequestParam List<Integer> ids,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha
    ) {
        List<DisponibilidadDTO> horarios = disponibilidadService.buscarHorasDisponibles(ids, fecha);
        return ResponseEntity.ok(horarios);
    }

}
