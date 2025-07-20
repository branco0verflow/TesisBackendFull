package com.sgc.dtos;

import java.time.LocalDate;
import java.util.List;

public class RetenRequest {
    private List<Integer> idsMecanicos;
    private LocalDate desde;
    private LocalDate hasta;


    public List<Integer> getIdsMecanicos() {
        return idsMecanicos;
    }

    public void setIdsMecanicos(List<Integer> idsMecanicos) {
        this.idsMecanicos = idsMecanicos;
    }

    public LocalDate getDesde() {
        return desde;
    }

    public void setDesde(LocalDate desde) {
        this.desde = desde;
    }

    public LocalDate getHasta() {
        return hasta;
    }

    public void setHasta(LocalDate hasta) {
        this.hasta = hasta;
    }
}

