package com.sgc.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcliente")
    private Integer idCliente;

    @Column(name = "nombrecliente", length = 50, nullable = false)
    @NotEmpty(message = "El nombre es obligatorio")
    private String nombreCliente;

    @Column(name = "apellidocliente", length = 50, nullable = false)
    @NotEmpty(message = "El apellido es obligatorio")
    private String apellidoCliente;

    @Column(name = "documentocliente", columnDefinition = "CHAR(20)", nullable = false)
    @NotEmpty(message = "El documento es obligatorio")
    private String documentoCliente;

    @Column(name = "emailcliente", length = 50, unique = true, nullable = false)
    @NotEmpty(message = "El email es obligatorio")
    @Email(message = "El formato del email es inválido")
    @Pattern(
            regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}$",
            message = "El formato del email es inválido"
    )
    private String emailCliente;

    @Column(name = "telefonocliente", columnDefinition = "CHAR(20)", nullable = false)
    @NotEmpty(message = "El teléfono es obligatorio")
    private String telefonoCliente;

    @Column(name = "direccioncliente", length = 100, nullable = false)
    @NotEmpty(message = "La dirección es obligatoria")
    private String direccionCliente;

    // Constructores
    public Cliente() {}

    public Cliente(Integer idCliente, String nombreCliente, String apellidoCliente, String documentoCliente,
                   String emailCliente, String telefonoCliente, String direccionCliente) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.documentoCliente = documentoCliente;
        this.emailCliente = emailCliente;
        this.telefonoCliente = telefonoCliente;
        this.direccionCliente = direccionCliente;
    }

    // Getters y Setters
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
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

    public String getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(String documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }
}
