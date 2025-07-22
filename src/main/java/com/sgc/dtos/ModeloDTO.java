package com.sgc.dtos;

import jakarta.validation.constraints.NotNull;

public class ModeloDTO {
    private Integer idModelo;

    @NotNull(message = "El nombre del modelo es obligatorio")
    private String nombreModelo;

    @NotNull(message = "La marca es obligatoria")
    private Integer idMarca;

    public Integer getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Integer idModelo) {
        this.idModelo = idModelo;
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }
}
