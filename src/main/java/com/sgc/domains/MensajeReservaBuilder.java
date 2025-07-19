package com.sgc.domains;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class MensajeReservaBuilder {

    private static final DateTimeFormatter HORA_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final Locale LOCALE_URUGUAY = new Locale("es", "UY");

    public static String formatFecha(LocalDate fecha) {
        String diaSemana = fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, LOCALE_URUGUAY);
        String mes = fecha.getMonth().getDisplayName(TextStyle.FULL, LOCALE_URUGUAY);
        return capitalize(diaSemana) + " " + fecha.getDayOfMonth() + " de " + capitalize(mes) + " de " + fecha.getYear();
    }

    public static String formatHora(LocalTime hora) {
        return hora.format(HORA_FORMATTER);
    }

    public static String buildMensajeCreacion(String nombreCliente, LocalDate fecha, LocalTime hora) {
        return "Hola " + nombreCliente + ", su reserva fue confirmada para el día " +
                formatFecha(fecha) + " a las " + formatHora(hora) + ". Asista con 30 minutos de anticipación. Gracias - Taller Videsol";
    }

    public static String buildMensajeModificacion(String nombreCliente, LocalDate nuevaFecha, LocalTime nuevaHora) {
        return "Hola " + nombreCliente + ", su reserva fue modificada. La nueva cita es el " +
                formatFecha(nuevaFecha) + " a las " + formatHora(nuevaHora) + ".";
    }

    private static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase(LOCALE_URUGUAY) + input.substring(1);
    }
}
