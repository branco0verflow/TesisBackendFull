package com.sgc.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "marca")
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmarca")
    private Integer idMarca;

    @Column(name = "nombremarca", length = 50, nullable = false, unique = true)
    @NotEmpty(message = "El nombre es obligatorio")
    private String nombreMarca;

    public Integer getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }

    public Marca(){

    }

    public Marca(Integer idMarca, String nombreMarca) {
        this.idMarca = idMarca;
        this.nombreMarca = nombreMarca;
    }
}
