package com.sgc.services;

import com.sgc.domains.MensajeReservaBuilder;
import com.sgc.domains.Reserva;
import com.sgc.repositories.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecordatorioService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private NotificacionService notificacionService;

    // Ejecutar todos los días a las 9:00am
    @Scheduled(cron = "0 0 9 * * *", zone = "America/Montevideo")
    @Transactional
    public void enviarRecordatoriosDiarios() {
        LocalDate fechaObjetivo = LocalDate.now().plusDays(1);
        List<Reserva> reservas = reservaRepository.findByFechaCitaReservaAndRecordatorioEnviadoFalse(fechaObjetivo);

        for (Reserva reserva : reservas) {
            if (reserva.getCliente() != null && reserva.getHoraInicioReserva() != null) {
                notificacionService.enviarSMS(
                        reserva.getCliente().getTelefonoCliente(),
                        "Hola " + reserva.getCliente().getNombreCliente() +
                                ", le recordamos su reserva para el " +
                                MensajeReservaBuilder.formatFecha(fechaObjetivo) +
                                " a las " + MensajeReservaBuilder.formatHora(reserva.getHoraInicioReserva().toLocalTime()) +
                                ". Si no podrá asistir, por favor contáctenos. – Taller Videsol"
                );
                // marcar como enviado (lo necesito para evitar duplicados)
                reserva.setRecordatorioEnviado(true);
            }
        }

        // guardo cambios de todas las avisadas, notificadas y guardadas como R.E. = true
        reservaRepository.saveAll(reservas);
    }

}

