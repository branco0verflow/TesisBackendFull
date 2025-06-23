package com.sgc.dtos;

import java.time.LocalTime;

public class DisponibilidadDTO {
    private Integer idMecanico;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public DisponibilidadDTO(Integer idMecanico, LocalTime horaInicio, LocalTime horaFin) {
        this.idMecanico = idMecanico;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Integer getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(Integer idMecanico) {
        this.idMecanico = idMecanico;
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

