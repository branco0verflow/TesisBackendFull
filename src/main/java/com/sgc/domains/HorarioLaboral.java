package com.sgc.domains;

import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.util.List;

@Component
public class HorarioLaboral {
    public static final List<TimeRange> JORNADA = List.of(
            new TimeRange(LocalTime.of(8, 0), LocalTime.of(12, 0)),
            new TimeRange(LocalTime.of(13, 0), LocalTime.of(17, 0))
    );

    public List<TimeRange> getJornadas() {
        return List.of(
                new TimeRange(LocalTime.of(8, 0), LocalTime.of(12, 0)),
                new TimeRange(LocalTime.of(13, 0), LocalTime.of(17, 0))
        );
    }

}




