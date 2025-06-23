package com.sgc.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "tipotarea")
public class TipoTarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idtipotarea")
    private Integer idTipoTarea;

    @NotBlank(message = "El nombre del tipo de tarea es obligatorio")
    @Column(name = "nombretipotarea", nullable = false, length = 50)
    private String nombreTipoTarea;

    @NotNull(message = "El tiempo en minutos es obligatorio")
    @Min(value = 1, message = "El tiempo debe ser mayor a 0")
    @Column(name = "tiempominutostipotarea", nullable = false)
    private Integer tiempoMinutosTipoTarea;

    public TipoTarea(){

    }

    public TipoTarea(Integer idTipoTarea, String nombreTipoTarea, Integer tiempoMinutosTipoTarea){
        this.idTipoTarea = idTipoTarea;
        this.nombreTipoTarea = nombreTipoTarea;
        this.tiempoMinutosTipoTarea = tiempoMinutosTipoTarea;
    }

    // Getters y Setters

    public Integer getIdTipoTarea() {
        return idTipoTarea;
    }

    public void setIdTipoTarea(Integer idTipoTarea) {
        this.idTipoTarea = idTipoTarea;
    }

    public String getNombreTipoTarea() {
        return nombreTipoTarea;
    }

    public void setNombreTipoTarea(String nombreTipoTarea) {
        this.nombreTipoTarea = nombreTipoTarea;
    }

    public Integer getTiempoMinutosTipoTarea() {
        return tiempoMinutosTipoTarea;
    }

    public void setTiempoMinutosTipoTarea(Integer tiempoMinutosTipoTarea) {
        this.tiempoMinutosTipoTarea = tiempoMinutosTipoTarea;
    }
}
