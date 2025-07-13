package com.sgc.dtos;

public class ClientePatchDTO {

    private String telefonoCliente;

    public ClientePatchDTO() {
    }

    public ClientePatchDTO(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }
}

