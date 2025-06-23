package com.sgc.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class VehiculoDTO {

    private Integer idVehiculo;

    @NotNull(message = "El ID del modelo es obligatorio")
    private Integer idModelo;

    @NotEmpty(message = "El número de chasis es obligatorio")
    private String nroChasisVehiculo;

    @NotEmpty(message = "El número de motor es obligatorio")
    private String nroMotorVehiculo;

    @NotNull(message = "El año del vehículo es obligatorio")
    private Integer anoVehiculo;

    @NotNull(message = "La cilindrada del vehículo es obligatoria")
    private Integer cilindradaVehiculo;

    @NotNull(message = "El kilometraje es obligatorio")
    private Integer kilometrajeVehiculo;

    @NotEmpty(message = "La matrícula es obligatoria")
    private String matriculaVehiculo;


    public @NotNull(message = "El ID del modelo es obligatorio") Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(@NotNull(message = "El ID del modelo es obligatorio") Integer idModelo) {
        this.idModelo = idModelo;
    }

    public @NotEmpty(message = "El número de chasis es obligatorio") String getNroChasisVehiculo() {
        return nroChasisVehiculo;
    }

    public void setNroChasisVehiculo(@NotEmpty(message = "El número de chasis es obligatorio") String nroChasisVehiculo) {
        this.nroChasisVehiculo = nroChasisVehiculo;
    }

    public @NotEmpty(message = "El número de motor es obligatorio") String getNroMotorVehiculo() {
        return nroMotorVehiculo;
    }

    public void setNroMotorVehiculo(@NotEmpty(message = "El número de motor es obligatorio") String nroMotorVehiculo) {
        this.nroMotorVehiculo = nroMotorVehiculo;
    }

    public @NotNull(message = "El año del vehículo es obligatorio") Integer getAnoVehiculo() {
        return anoVehiculo;
    }

    public void setAnoVehiculo(@NotNull(message = "El año del vehículo es obligatorio") Integer anoVehiculo) {
        this.anoVehiculo = anoVehiculo;
    }

    public @NotNull(message = "La cilindrada del vehículo es obligatoria") Integer getCilindradaVehiculo() {
        return cilindradaVehiculo;
    }

    public void setCilindradaVehiculo(@NotNull(message = "La cilindrada del vehículo es obligatoria") Integer cilindradaVehiculo) {
        this.cilindradaVehiculo = cilindradaVehiculo;
    }

    public @NotNull(message = "El kilometraje es obligatorio") Integer getKilometrajeVehiculo() {
        return kilometrajeVehiculo;
    }

    public void setKilometrajeVehiculo(@NotNull(message = "El kilometraje es obligatorio") Integer kilometrajeVehiculo) {
        this.kilometrajeVehiculo = kilometrajeVehiculo;
    }

    public @NotEmpty(message = "La matrícula es obligatoria") String getMatriculaVehiculo() {
        return matriculaVehiculo;
    }

    public void setMatriculaVehiculo(@NotEmpty(message = "La matrícula es obligatoria") String matriculaVehiculo) {
        this.matriculaVehiculo = matriculaVehiculo;
    }
}

