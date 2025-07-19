package com.sgc.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idreserva")
    private Integer idReserva;

    @Column(name = "fecha_creada_reserva", nullable = false)
    @NotNull(message = "La fecha de creación es obligatoria")
    private Date fechaCreadaReserva;

    @Column(name = "fecha_cita_reserva", nullable = false)
    @NotNull(message = "La fecha de cita es obligatoria")
    private Date fechaCitaReserva;

    @Column(name = "hora_inicio_reserva", nullable = false)
    @NotNull(message = "La hora de inicio reserva es obligatoria")
    private Time horaInicioReserva;

    @Column(name = "hora_fin_reserva", nullable = false)
    @NotNull(message = "La hora de fin reserva es obligatoria")
    private Time horaFinReserva;

    @Column(name = "comentarios_reserva", length = 500)
    private String comentariosReserva;

    @ManyToOne
    @JoinColumn(name = "idcliente", nullable = false)
    @NotNull(message = "El cliente es obligatorio")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "idvehiculo", nullable = false)
    @NotNull(message = "El vehículo es obligatorio")
    private Vehiculo vehiculo;

    @ManyToMany
    @JoinTable(
            name = "reserva_tipo_tarea",
            joinColumns = @JoinColumn(name = "idreserva"),
            inverseJoinColumns = @JoinColumn(name = "idtipotarea")
    )
    private List<TipoTarea> tipoTarea;

    @ManyToOne
    @JoinColumn(name = "idestado", nullable = false)
    @NotNull(message = "El estado es obligatorio")
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "idmecanico", nullable = false)
    @NotNull(message = "El mecánico es obligatorio")
    private Mecanico mecanico;

    @Column(name = "recordatorio_enviado")
    private boolean recordatorioEnviado = false;


    public Reserva() {}

    @PrePersist
    protected void onCreate() {
        this.fechaCreadaReserva = Date.valueOf(LocalDate.now()); // yyyy-MM-dd sin hora BB
    }

    public Reserva(Integer idReserva, Date fechaCreadaReserva, Date fechaCitaReserva, Time horaInicioReserva, Time horaFinReserva,
                   String comentariosReserva, Cliente Cliente, Vehiculo vehiculo,
                   List<TipoTarea> tipoTarea, Estado estado, Mecanico mecanico) {
        this.idReserva = idReserva;
        this.fechaCreadaReserva = fechaCreadaReserva;
        this.fechaCitaReserva = fechaCitaReserva;
        this.horaInicioReserva = horaInicioReserva;
        this.horaFinReserva = horaFinReserva;
        this.comentariosReserva = comentariosReserva;
        this.cliente = Cliente;
        this.vehiculo = vehiculo;
        this.tipoTarea = tipoTarea;
        this.estado = estado;
        this.mecanico = mecanico;
    }



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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<TipoTarea> getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(List<TipoTarea> tipoTarea) {
        this.tipoTarea = tipoTarea;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public  Mecanico getMecanico() {
        return mecanico;
    }

    public void setMecanico(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    public boolean isRecordatorioEnviado() {
        return recordatorioEnviado;
    }

    public void setRecordatorioEnviado(boolean recordatorioEnviado) {
        this.recordatorioEnviado = recordatorioEnviado;
    }

}
