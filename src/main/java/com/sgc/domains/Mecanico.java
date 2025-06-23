package com.sgc.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
@Table(name = "mecanico")
public class Mecanico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmecanico")
    private Integer idMecanico;

    @Column(name = "nombremecanico", length = 50, nullable = false)
    @NotEmpty(message = "El nombre es obligatorio")
    private String nombreMecanico;

    @Column(name = "apellidomecanico", length = 50, nullable = false)
    @NotEmpty(message = "El apellido es obligatorio")
    private String apellidoMecanico;

    @Column(name = "emailmecanico", length = 50, nullable = false, unique = true)
    @NotEmpty(message = "El email es obligatorio")
    @Email(message = "El formato del email es inválido")
    @Pattern(
            regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}$",
            message = "El formato del email es inválido"
    )
    private String emailMecanico;

    @Column(name = "activomecanico", nullable = false)
    private Boolean activoMecanico;

    @Column(name = "passwordmecanico", length = 255, nullable = false)
    @NotEmpty(message = "La contraseña es obligatoria")
    private String passwordMecanico;

    @ManyToMany
    @JoinTable(
            name = "mecanico_tipo_tarea",
            joinColumns = @JoinColumn(name = "idmecanico"),
            inverseJoinColumns = @JoinColumn(name = "idtipotarea")
    )
    private List<TipoTarea> tipoTarea;

    public Mecanico(Integer idMecanico, String nombreMecanico, String apellidoMecanico, String emailMecanico, Boolean activoMecanico, String passwordMecanico, List<TipoTarea> tipoTareaMecanico) {
        this.idMecanico = idMecanico;
        this.nombreMecanico = nombreMecanico;
        this.apellidoMecanico = apellidoMecanico;
        this.emailMecanico = emailMecanico;
        this.activoMecanico = activoMecanico;
        this.passwordMecanico = passwordMecanico;
        this.tipoTarea = tipoTareaMecanico;
    }

    public Mecanico() {};

    public Integer getIdMecanico() {
        return idMecanico;
    }

    public void setIdMecanico(Integer idMecanico) {
        this.idMecanico = idMecanico;
    }

    public String getNombreMecanico() {
        return nombreMecanico;
    }

    public void setNombreMecanico(String nombreMecanico) {
        this.nombreMecanico = nombreMecanico;
    }

    public String getApellidoMecanico() {
        return apellidoMecanico;
    }

    public void setApellidoMecanico(String apellidoMecanico) {
        this.apellidoMecanico = apellidoMecanico;
    }

    public String getEmailMecanico() {
        return emailMecanico;
    }

    public void setEmailMecanico(String emailMecanico) {
        this.emailMecanico = emailMecanico;
    }

    public Boolean getActivoMecanico() {
        return activoMecanico;
    }

    public void setActivoMecanico(Boolean activoMecanico) {
        this.activoMecanico = activoMecanico;
    }

    public String getPasswordMecanico() {
        return passwordMecanico;
    }

    public void setPasswordMecanico(String passwordMecanico) {
        this.passwordMecanico = passwordMecanico;
    }

    public List<TipoTarea> getTipoTarea() {
        return tipoTarea;
    }

    public void setTipoTarea(List<TipoTarea> tipoTarea) {
        this.tipoTarea = tipoTarea;
    }
}
