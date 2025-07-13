package com.sgc.dtos;

import java.sql.Date;
import java.sql.Time;
import java.util.List;


public class ReservaNuevaDTO {

    private Date fechaCitaReserva;
    private Time horaInicioReserva;
    private Time horaFinReserva;
    private String comentariosReserva;
    private Integer idCliente;
    private Integer idVehiculo;
    private List<Integer> idsTipoTarea;
    private Integer idEstado;
    private Integer idMecanico;

    public ReservaNuevaDTO(){

    }


    public Date getFechaCitaReserva() {
        return fechaCitaReserva;
    }

    public void setFechaCitaReserva(Date fechaCitaReserva) {
        this.fechaCitaReserva = fechaCitaReserva;
    }

    public Time getHoraInicioReserva() {
        return horaInicioReserva;
    }

    public void setHoraInicioReserva(Time horaInicioReserva) {
        this.horaInicioReserva = horaInicioReserva;
    }

    public Time getHoraFinReserva() {
        return horaFinReserva;
    }

    public void setHoraFinReserva(Time horaFinReserva) {
        this.horaFinReserva = horaFinReserva;
    }

    public String getComentariosReserva() {
        return comentariosReserva;
    }

    public void setComentariosReserva(String comentariosReserva) {
        this.comentariosReserva = comentariosReserva;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public List<Integer> getIdsTipoTarea() {
        return idsTipoTarea;
    }

    public void setIdsTipoTarea(List<Integer> idsTipoTarea) {
        this.idsTipoTarea = idsTipoTarea;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(Integer idMecanico) {
        this.idMecanico = idMecanico;
    }
}
