package com.sgc.services;

import com.sgc.domains.Reserva;
import com.sgc.dtos.SeguimientoReservaDTO;
import com.sgc.exceptions.RecursoNoEncontradoException;
import com.sgc.repositories.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SeguimientoService {

    private final ReservaRepository reservaRepository;

    public SeguimientoService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public List<SeguimientoReservaDTO> buscarReservas(String documento, String email) {

        List<Reserva> reservas = reservaRepository.findSeguimiento(documento, email);

        if (reservas.isEmpty()) {
            throw new RecursoNoEncontradoException("Cliente no encontrado o sin reservas");
        }

        return reservas.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private SeguimientoReservaDTO toDto(Reserva r) {
        var c = r.getCliente();
        var v = r.getVehiculo();

        return new SeguimientoReservaDTO(
                c.getNombreCliente(),
                c.getApellidoCliente(),
                v.getModelo().getNombreModelo(),
                v.getModelo().getMarca().getNombreMarca(),
                v.getMatriculaVehiculo(),
                r.getFechaCitaReserva(),
                r.getHoraInicioReserva(),
                r.getHoraFinReserva(),
                r.getEstado().getNombreEstado(),
                r.getComentariosReserva()
        );
    }
}



