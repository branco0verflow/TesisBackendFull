package com.sgc.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class TareaDTO {

    private Integer idTarea;


    private Date fechaCreadaTarea;

    @NotNull(message = "La fecha de la tarea es obligatoria")
    private LocalDate fechaTarea;

    @NotNull(message = "La hora de ingreso es obligatoria")
    private Time horaIngresoTarea;

    private Time horaFinTarea;

    @Size(max = 800, message = "La descripción no puede superar los 500 caracteres")
    private String descripcionTarea;

    @NotNull(message = "Debe indicar si es reserva o no")
    private Boolean esReservaTarea;


    private Integer idReserva;

    @NotNull(message = "El ID del mecánico es obligatorio")
    private Integer idMecanico;

    @NotNull(message = "El ID del estado es obligatorio")
    private Integer idEstado;

    @NotNull(message = "El ID del administrador es obligatorio")
    private Integer idAdmin;



    // Getters y Setters

    public Integer getIdTarea() {
        return idTarea;
    }

    public void setIdTarea(Integer idTarea) {
        this.idTarea = idTarea;
    }

    public Date getFechaCreadaTarea() {
        return fechaCreadaTarea;
    }

    public void setFechaCreadaTarea(Date fechaCreadaTarea) {
        this.fechaCreadaTarea = fechaCreadaTarea;
    }

    public LocalDate getFechaTarea() {
        return fechaTarea;
    }

    public void setFechaTarea(LocalDate fechaTarea) {
        this.fechaTarea = fechaTarea;
    }

    public Time getHoraIngresoTarea() {
        return horaIngresoTarea;
    }

    public void setHoraIngresoTarea(Time horaIngresoTarea) {
        this.horaIngresoTarea = horaIngresoTarea;
    }

    public Time getHoraFinTarea() {
        return horaFinTarea;
    }

    public void setHoraFinTarea(Time horaFinTarea) {
        this.horaFinTarea = horaFinTarea;
    }

    public String getDescripcionTarea() {
        return descripcionTarea;
    }

    public void setDescripcionTarea(String descripcionTarea) {
        this.descripcionTarea = descripcionTarea;
    }

    public Boolean getEsReservaTarea() {
        return esReservaTarea;
    }

    public void setEsReservaTarea(Boolean esReservaTarea) {
        this.esReservaTarea = esReservaTarea;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Integer getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(Integer idMecanico) {
        this.idMecanico = idMecanico;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Integer idAdmin) {
        this.idAdmin = idAdmin;
    }


}