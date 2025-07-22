package com.sgc.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class MecanicoDTO {

    public interface OnCreate {}

    private Integer idMecanico;

    @NotEmpty(message = "El nombre es obligatorio")
    private String nombreMecanico;

    @NotEmpty(message = "El apellido es obligatorio")
    private String apellidoMecanico;

    @NotEmpty(message = "El email es obligatorio")
    @Email(message = "El formato del email es inválido")
    @Pattern(
            regexp = "^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}$",
            message = "El formato del email es inválido"
    )
    private String emailMecanico;

    private Boolean activoMecanico;

    @NotEmpty(message = "La contraseña es obligatoria", groups = OnCreate.class)
    private String passwordMecanico;

    @NotNull(message = "Debe incluir al menos una tarea")
    private List<Integer> tipoTareaIds;

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

    public List<Integer> getTipoTareaIds() {
        return tipoTareaIds;
    }

    public void setTipoTareaIds(List<Integer> tipoTareaIds) {
        this.tipoTareaIds = tipoTareaIds;
    }
}
