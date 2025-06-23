package com.sgc.domains;

import java.time.Duration;
import java.time.LocalTime;

public class TimeRange {
    private LocalTime inicio;
    private LocalTime fin;

    public LocalTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalTime inicio) {
        this.inicio = inicio;
    }

    public LocalTime getFin() {
        return fin;
    }

    public void setFin(LocalTime fin) {
        this.fin = fin;
    }


    public TimeRange(){

    }

    public TimeRange(LocalTime inicio, LocalTime fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    public long duracionEnMinutos() {
        return Duration.between(inicio, fin).toMinutes();
    }
}