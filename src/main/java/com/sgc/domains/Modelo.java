package com.sgc.domains;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "modelo")
public class Modelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmodelo")
    private Integer idModelo;

    @Column(name = "nombremodelo", length = 50, nullable = false)
    @NotEmpty(message = "El nombre es obligatorio")
    private String nombreModelo;

    @ManyToOne
    @JoinColumn(name = "idmarca", nullable = false)
    private Marca marca;

    public Modelo() {
    }

    public Modelo(Integer idModelo, String nombreModelo, Marca marca) {
        this.idModelo = idModelo;
        this.nombreModelo = nombreModelo;
        this.marca = marca;
    }

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

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
