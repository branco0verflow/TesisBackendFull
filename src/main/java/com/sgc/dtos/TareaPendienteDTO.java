package com.sgc.dtos;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class TareaPendienteDTO {
    private Integer idTarea;
    private LocalDate fecha;
    private Time hora;

    public TareaPendienteDTO(Integer idTarea, LocalDate fecha, Time hora) {
        this.idTarea = idTarea;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Integer getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Integer idTarea) {
        this.idTarea = idTarea;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }
}
