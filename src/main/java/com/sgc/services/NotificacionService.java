package com.sgc.services;

import com.sgc.domains.MensajeReservaBuilder;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class NotificacionService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String fromPhone;

    @PostConstruct
    public void init() {
        Twilio.init(accountSid, authToken);
    }

    public void enviarSMS(String numeroDestino, String mensaje) {
        Message.creator(
                new PhoneNumber(numeroDestino),
                new PhoneNumber(fromPhone),
                mensaje
        ).create();
    }

    public void notificarReservaCreada(String numeroDestino, String nombreCliente, LocalDate fecha, LocalTime hora) {
        String mensaje = MensajeReservaBuilder.buildMensajeCreacion(nombreCliente, fecha, hora);
        enviarSMS(numeroDestino, mensaje);
    }

    public void notificarReservaModificada(String numeroDestino, String nombreCliente, LocalDate fecha, LocalTime hora) {
        String mensaje = MensajeReservaBuilder.buildMensajeModificacion(nombreCliente, fecha, hora);
        enviarSMS(numeroDestino, mensaje);
    }

    public void notificarReservaCanceladaPorTaller(String numeroDestino, String nombreCliente, LocalDate fecha, LocalTime hora) {
        String mensaje = "Hola " + nombreCliente + ", su reserva para el " +
                MensajeReservaBuilder.formatFecha(fecha) +
                " a las " + MensajeReservaBuilder.formatHora(hora) +
                " ha sido cancelada por el taller. Si desea reagendar, comuníquese con nosotros. – Taller Videsol";
        enviarSMS(numeroDestino, mensaje);
    }

    public void notificarReservaCanceladaPorCliente(String numeroDestino, String nombreCliente, LocalDate fecha, LocalTime hora) {
        String mensaje = "Hola " + nombreCliente + ", confirmamos la cancelación de su reserva para el " +
                MensajeReservaBuilder.formatFecha(fecha) +
                " a las " + MensajeReservaBuilder.formatHora(hora) +
                ". ¡Gracias por avisarnos! – Taller Videsol";
        enviarSMS(numeroDestino, mensaje);
    }

}
