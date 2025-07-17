package com.sgc.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProximaDisponibilidadDTO {
    private int idMecanico;
    private String nombreMecanico;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public ProximaDisponibilidadDTO(int idMecanico, String nombreMecanico, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        this.idMecanico = idMecanico;
        this.nombreMecanico = nombreMecanico;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }


    public int getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(int idMecanico) {
        this.idMecanico = idMecanico;
    }

    public String getNombreMecanico() {
        return nombreMecanico;
    }

    public void setNombreMecanico(String nombreMecanico) {
        this.nombreMecanico = nombreMecanico;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
}
