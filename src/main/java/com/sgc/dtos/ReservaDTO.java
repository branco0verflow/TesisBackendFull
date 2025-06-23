package com.sgc.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;
import java.sql.Time;

import java.util.List;

public class ReservaDTO {

    private Integer idReserva;

    private Date fechaCreadaReserva;

    @NotNull(message = "La fecha de la cita es obligatoria")
    private Date fechaCitaReserva;

    @NotNull(message = "La hora de inicio es obligatoria")
    private Time horaInicioReserva;

    @NotNull(message = "La hora de fin es obligatoria")
    private Time horaFinReserva;

    @Size(max = 800, message = "Los comentarios no pueden superar los 800 caracteres")
    private String comentariosReserva;

    @NotNull(message = "Los datos del cliente son obligatorios")
    private ClienteDTO cliente;

    @NotNull(message = "Los datos del vehículo son obligatorios")
    private VehiculoDTO vehiculo;

    @NotNull(message = "Debe incluir al menos un tipo de tarea")
    private List<Integer> idsTipoTarea;

    @NotNull(message = "El estado es obligatorio")
    private Integer idEstado;

    @NotNull(message = "El mecánico a cargo es obligatorio")
    private Integer idMecanico;

    // Getters y setters

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Date getFechaCreadaReserva() {
        return fechaCreadaReserva;
    }

    public void setFechaCreadaReserva(Date fechaCreadaReserva) {
        this.fechaCreadaReserva = fechaCreadaReserva;
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

    public Time getHoraFinReserva() { return horaFinReserva; }

    public void setHoraFinReserva(Time horaFinReserva) { this.horaFinReserva = horaFinReserva; }

    public String getComentariosReserva() {
        return comentariosReserva;
    }

    public void setComentariosReserva(String comentariosReserva) {
        this.comentariosReserva = comentariosReserva;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public VehiculoDTO getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(VehiculoDTO vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<Integer> getIdsTipoTarea() {
        return idsTipoTarea;
    }

    public void setIdsTipoTarea(List<Integer> tipoTareaIds) {
        this.idsTipoTarea = tipoTareaIds;
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

    public void setIdMecanico(Integer mecanico) {
        this.idMecanico = mecanico;
    }


}
