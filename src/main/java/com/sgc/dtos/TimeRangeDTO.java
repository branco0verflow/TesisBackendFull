package com.sgc.dtos;

import java.time.LocalTime;

public class TimeRangeDTO {
    private String inicio;
    private String fin;

    public TimeRangeDTO(LocalTime inicio, LocalTime fin) {
        this.inicio = inicio.toString();
        this.fin = fin.toString();
    }

    public String getInicio() {
        return inicio;
    }

    public String getFin() {
        return fin;
    }
}

