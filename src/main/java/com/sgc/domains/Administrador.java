package com.sgc.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "administrador")
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idadmin")
    private Integer idAdmin;

    @Column(name = "nombreadmin", length = 50, nullable = false)
    @NotEmpty(message = "El nombre es obligatorio")
    private String nombreAdmin;

    @Column(name = "apellidoadmin", length = 50, nullable = false)
    @NotEmpty(message = "El apellido es obligatorio")
    private String apellidoAdmin;

    @Column(name = "emailadmin", length = 50, nullable = false, unique = true)
    @NotEmpty(message = "El email es obligatorio")
    @Email(message = "El formato del email es inválido")
    @Pattern(
            regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}$",
            message = "El formato del email es inválido"
    )
    private String emailAdmin;

    @Column(name = "passwordadmin", length = 255, nullable = false)
    @NotEmpty(message = "La contraseña es obligatoria")
    private String passwordAdmin;

    public Administrador() {
    }

    public Administrador(Integer idAdmin, String nombreAdmin, String apellidoAdmin, String emailAdmin, String passwordAdmin) {
        this.idAdmin = idAdmin;
        this.nombreAdmin = nombreAdmin;
        this.apellidoAdmin = apellidoAdmin;
        this.emailAdmin = emailAdmin;
        this.passwordAdmin = passwordAdmin;
    }

    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Integer idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getNombreAdmin() {
        return nombreAdmin;
    }

    public void setNombreAdmin(String nombreAdmin) {
        this.nombreAdmin = nombreAdmin;
    }

    public String getApellidoAdmin() {
        return apellidoAdmin;
    }

    public void setApellidoAdmin(String apellidoAdmin) {
        this.apellidoAdmin = apellidoAdmin;
    }

    public String getEmailAdmin() {
        return emailAdmin;
    }

    public void setEmailAdmin(String emailAdmin) {
        this.emailAdmin = emailAdmin;
    }

    public String getPasswordAdmin() {
        return passwordAdmin;
    }

    public void setPasswordAdmin(String passwordAdmin) {
        this.passwordAdmin = passwordAdmin;
    }
}
