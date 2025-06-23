package com.sgc.dtos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class ClienteDTO {

    private Integer idCliente;

    @NotEmpty(message = "El nombre es obligatorio")
    private String nombreCliente;

    @NotEmpty(message = "El apellido es obligatorio")
    private String apellidoCliente;

    @NotEmpty(message = "El documento es obligatorio")
    private String documentoCliente;

    @NotEmpty(message = "El email es obligatorio")
    @Email(message = "El formato del email es inválido")
    @Pattern(
            regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}$",
            message = "El formato del email es inválido"
    )
    private String emailCliente;

    @NotEmpty(message = "El teléfono es obligatorio")
    private String telefonoCliente;

    @NotEmpty(message = "La dirección es obligatoria")
    private String direccionCliente;

    // Getters

    public @NotEmpty(message = "El nombre es obligatorio") String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(@NotEmpty(message = "El nombre es obligatorio") String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public @NotEmpty(message = "El apellido es obligatorio") String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(@NotEmpty(message = "El apellido es obligatorio") String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public @NotEmpty(message = "El documento es obligatorio") String getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(@NotEmpty(message = "El documento es obligatorio") String documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public @NotEmpty(message = "El email es obligatorio") String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(@NotEmpty(message = "El email es obligatorio") String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public @NotEmpty(message = "El teléfono es obligatorio") String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(@NotEmpty(message = "El teléfono es obligatorio") String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public @NotEmpty(message = "La dirección es obligatoria") String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(@NotEmpty(message = "La dirección es obligatoria") String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }
}
