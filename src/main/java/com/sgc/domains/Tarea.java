package com.sgc.domains;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;


@Entity
@Table(name = "tarea")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtarea")
    private Integer idTarea;

    @Column(name = "fecha_creada_tarea")
    private Date fechaCreadaTarea;

    @Column(name = "fecha_tarea")
    private LocalDate fechaTarea;

    @Column(name = "hora_ingreso_tarea")
    private Time horaIngresoTarea;

    @Column(name = "hora_fin_tarea")
    private Time horaFinTarea;

    @Column(name = "descripcion_tarea", length = 800)
    private String descripcionTarea;

    @Column(name = "es_reserva_tarea")
    private Boolean esReservaTarea;

    @ManyToOne
    @JoinColumn(name = "idreserva")
    private Reserva reserva;

    @ManyToOne
    @JoinColumn(name = "idmecanico")
    private Mecanico mecanico;

    @ManyToOne
    @JoinColumn(name = "idestado")
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "idadmin")
    private Administrador administrador;



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

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public void setMecanico(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }


    @PrePersist
    private void inicializarYValidarAntesDeGuardar() {


        if (Boolean.TRUE.equals(esReservaTarea) && reserva == null) {
            throw new IllegalStateException("Si la tarea es una reserva, debe tener una reserva asociada.");
        }

        if (estado == null) {
            Estado estadoAsignado = new Estado();
            estadoAsignado.setIdEstado(2); // Id 2 hace referencia al Estado Confirmado ya que es creado por una administradora.
            this.estado = estadoAsignado;
        }

        if (administrador == null) {
            throw new IllegalStateException("La tarea debe tener un administrador asignado.");
        }
    }


}