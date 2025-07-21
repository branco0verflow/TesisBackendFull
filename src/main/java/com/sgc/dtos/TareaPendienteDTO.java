package com.sgc.dtos;

import java.sql.Date;
import java.sql.Time;

public class TareaPendienteDTO {
    private Integer idTarea;
    private Date fecha;
    private Time hora;

    public TareaPendienteDTO(Integer idTarea, Date fecha, Time hora) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }
}
