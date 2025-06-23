package com.sgc.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "vehiculo")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idvehiculo")
    private Integer idVehiculo;

    @ManyToOne
    @JoinColumn(name = "idmodelo", nullable = false)
    private Modelo modelo;

    @Column(name = "nro_chasis_vehiculo", length = 50, nullable = false)
    @NotEmpty(message = "El número de chasis es obligatorio")
    private String nroChasisVehiculo;

    @Column(name = "nro_motor_vehiculo", length = 50, nullable = false)
    @NotEmpty(message = "El número de motor es obligatorio")
    private String nroMotorVehiculo;

    @Column(name = "ano_vehiculo", nullable = false)
    @NotNull(message = "El año del vehículo es obligatorio")
    private Integer anoVehiculo;

    @Column(name = "cilindrada_vehiculo", nullable = false)
    @NotNull(message = "La cilindrada del vehículo es obligatoria")
    private Integer cilindradaVehiculo;

    @Column(name = "kilometraje_vehiculo", nullable = false)
    @NotNull(message = "El kilometraje es obligatorio")
    private Integer kilometrajeVehiculo;

    @Column(name = "matricula_vehiculo", length = 50, nullable = false)
    @NotNull(message = "El kilometraje es obligatorio")
    private String matriculaVehiculo;

    public Vehiculo() {}

    public Vehiculo(Integer idVehiculo, Modelo modelo, String nroChasisVehiculo, String nroMotorVehiculo,
                    Integer anoVehiculo, Integer cilindradaVehiculo, Integer kilometrajeVehiculo, String matriculaVehiculo) {
        this.idVehiculo = idVehiculo;
        this.modelo = modelo;
        this.nroChasisVehiculo = nroChasisVehiculo;
        this.nroMotorVehiculo = nroMotorVehiculo;
        this.anoVehiculo = anoVehiculo;
        this.cilindradaVehiculo = cilindradaVehiculo;
        this.kilometrajeVehiculo = kilometrajeVehiculo;
        this.matriculaVehiculo = matriculaVehiculo;
    }

    // Getters y Setters
    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public String getNroChasisVehiculo() {
        return nroChasisVehiculo;
    }

    public void setNroChasisVehiculo(String nroChasisVehiculo) {
        this.nroChasisVehiculo = nroChasisVehiculo;
    }

    public String getNroMotorVehiculo() {
        return nroMotorVehiculo;
    }

    public void setNroMotorVehiculo(String nroMotorVehiculo) {
        this.nroMotorVehiculo = nroMotorVehiculo;
    }

    public Integer getAnoVehiculo() {
        return anoVehiculo;
    }

    public void setAnoVehiculo(Integer anoVehiculo) {
        this.anoVehiculo = anoVehiculo;
    }

    public Integer getCilindradaVehiculo() {
        return cilindradaVehiculo;
    }

    public void setCilindradaVehiculo(Integer cilindradaVehiculo) {
        this.cilindradaVehiculo = cilindradaVehiculo;
    }

    public Integer getKilometrajeVehiculo() {
        return kilometrajeVehiculo;
    }

    public void setKilometrajeVehiculo(Integer kilometrajeVehiculo) {
        this.kilometrajeVehiculo = kilometrajeVehiculo;
    }

    public String getMatriculaVehiculo() { return matriculaVehiculo; }

    public void setMatriculaVehiculo(String matriculaVehiculo) { this.matriculaVehiculo = matriculaVehiculo; }
}