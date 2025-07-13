package com.sgc.dtos;

import java.sql.Time;
import java.util.Date;

public class SeguimientoReservaDTO {

    private Integer idCliente;
    private Integer idVehiculo;
    private String nombreCliente;
    private String apellidoCliente;
    private String modeloVehiculo;
    private String marcaVehiculo;
    private String matricula;
    private Date fechaCita;
    private Time horaInicio;
    private Time horaFin;
    private String estado;
    private String descripcion;

    public SeguimientoReservaDTO(
            Integer idCliente,
            Integer idVehiculo,
            String nombreCliente,
            String apellidoCliente,
            String modeloVehiculo,
            String marcaVehiculo,
            String matricula,
            Date fechaCita,
            Time horaInicio,
            Time horaFin,
            String estado,
            String descripcion
    ) {
        this.idCliente = idCliente;
        this.idVehiculo = idVehiculo;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.modeloVehiculo = modeloVehiculo;
        this.marcaVehiculo = marcaVehiculo;
        this.matricula = matricula;
        this.fechaCita = fechaCita;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    // Getters y setters

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

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getModeloVehiculo() {
        return modeloVehiculo;
    }

    public void setModeloVehiculo(String modeloVehiculo) {
        this.modeloVehiculo = modeloVehiculo;
    }

    public String getMarcaVehiculo() {
        return marcaVehiculo;
    }

    public void setMarcaVehiculo(String marcaVehiculo) {
        this.marcaVehiculo = marcaVehiculo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

