package com.sgc.dtos;

public class VehiculoPatchDTO {

    private Integer kilometrajeVehiculo;

    public VehiculoPatchDTO() {
    }

    public VehiculoPatchDTO(Integer kilometrajeVehiculo) {
        this.kilometrajeVehiculo = kilometrajeVehiculo;
    }

    public Integer getKilometrajeVehiculo() {
        return kilometrajeVehiculo;
    }

    public void setKilometrajeVehiculo(Integer kilometrajeVehiculo) {
        this.kilometrajeVehiculo = kilometrajeVehiculo;
    }
}