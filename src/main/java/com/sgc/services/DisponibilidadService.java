package com.sgc.services;

import com.sgc.domains.*;
import com.sgc.dtos.DisponibilidadDTO;
import com.sgc.dtos.TimeRangeDTO;
import com.sgc.repositories.MecanicoRepository;
import com.sgc.repositories.TareaRepository;
import com.sgc.repositories.TipoTareaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DisponibilidadService {

    private final TipoTareaRepository tipoTareaRepo;
    private final MecanicoRepository mecanicoRepo;
    private final TareaRepository tareaRepo;
    private final HorarioLaboral horarioLaboral;

    public DisponibilidadService(
            TipoTareaRepository tipoTareaRepo,
            MecanicoRepository mecanicoRepo,
            TareaRepository tareaRepo,
            HorarioLaboral horarioLaboral
    ) {
        this.tipoTareaRepo = tipoTareaRepo;
        this.mecanicoRepo = mecanicoRepo;
        this.tareaRepo = tareaRepo;
        this.horarioLaboral = horarioLaboral;
    }

    public List<LocalDate> buscarDiasDisponibles(List<Integer> idTipoTareas, int limiteDias) {
        // 1. Obtener las tareas seleccionadas
        List<TipoTarea> tareas = tipoTareaRepo.findAllById(idTipoTareas);
        int minutosTotales = tareas.stream()
                .mapToInt(TipoTarea::getTiempoMinutosTipoTarea)
                .sum();

        // 2. Buscar un mecánico que sepa hacer todas las tareas
        List<Mecanico> mecanicos = mecanicoRepo.findAll();

        Optional<Mecanico> mecanicoOpt = mecanicos.stream()
                .filter(m -> {
                    Set<Integer> tareasQueHace = m.getTipoTarea().stream()
                            .map(TipoTarea::getIdTipoTarea)
                            .collect(Collectors.toSet());
                    return tareasQueHace.containsAll(idTipoTareas);
                })
                .findFirst();

        if (mecanicoOpt.isEmpty()) {
            return List.of(); // No hay mecánico compatible
        }

        Mecanico mecanico = mecanicoOpt.get();

        // 3. Buscar huecos por día
        List<LocalDate> diasDisponibles = new ArrayList<>();
        LocalDate hoy = LocalDate.now().plusDays(2);

        for (int i = 0; i < limiteDias; i++) {
            LocalDate dia = hoy.plusDays(i);
            List<Tarea> tareasDelDia = tareaRepo.findByMecanicoAndFecha(mecanico.getIdMecanico(), dia);

            List<TimeRange> huecos = calcularHuecos(tareasDelDia);

            boolean hayHueco = huecos.stream()
                    .anyMatch(h -> h.duracionEnMinutos() >= minutosTotales);

            if (hayHueco) {
                diasDisponibles.add(dia);
            }
        }

        return diasDisponibles;
    }

    private List<TimeRange> calcularHuecos(List<Tarea> tareasDelDia) {
        List<TimeRange> huecos = new ArrayList<>();

        List<TimeRange> tareasOcupadas = tareasDelDia.stream()
                .map(t -> new TimeRange(
                        t.getHoraIngresoTarea().toLocalTime(),
                        t.getHoraFinTarea().toLocalTime()))
                .sorted(Comparator.comparing(TimeRange::getInicio))
                .toList();

        for (TimeRange jornada : horarioLaboral.getJornadas()) {
            LocalTime cursor = jornada.getInicio();

            for (TimeRange ocupada : tareasOcupadas) {
                if (cursor.isBefore(ocupada.getInicio())) {
                    TimeRange libre = new TimeRange(cursor, ocupada.getInicio());
                    if (libre.duracionEnMinutos() > 0) {
                        huecos.add(libre);
                    }
                }
                cursor = ocupada.getFin().isAfter(cursor) ? ocupada.getFin() : cursor;
            }

            if (cursor.isBefore(jornada.getFin())) {
                TimeRange libre = new TimeRange(cursor, jornada.getFin());
                if (libre.duracionEnMinutos() > 0) {
                    huecos.add(libre);
                }
            }
        }


        return huecos;
    }


    public List<DisponibilidadDTO> buscarHorasDisponibles(List<Integer> idTipoTareas, LocalDate fecha) {
        List<TipoTarea> tareas = tipoTareaRepo.findAllById(idTipoTareas);
        int minutosTotales = tareas.stream()
                .mapToInt(TipoTarea::getTiempoMinutosTipoTarea)
                .sum();

        List<Mecanico> mecanicos = mecanicoRepo.findAll();

        List<Mecanico> mecanicosCompatibles = mecanicos.stream()
                .filter(m -> {
                    Set<Integer> tareasQueHace = m.getTipoTarea().stream()
                            .map(TipoTarea::getIdTipoTarea)
                            .collect(Collectors.toSet());
                    return tareasQueHace.containsAll(idTipoTareas);
                })
                .collect(Collectors.toList());

        List<DisponibilidadDTO> resultados = new ArrayList<>();

        for (Mecanico mecanico : mecanicosCompatibles) {
            List<Tarea> tareasDelDia = tareaRepo.findByMecanicoAndFecha(mecanico.getIdMecanico(), fecha);
            List<TimeRange> huecos = calcularHuecos(tareasDelDia);

            for (int i = 0; i < huecos.size(); i++) {
                LocalTime inicio = huecos.get(i).getInicio();
                int acumulado = 0;
                LocalTime cursor = inicio;

                for (int j = i; j < huecos.size(); j++) {
                    TimeRange bloque = huecos.get(j);

                    if (cursor.isBefore(bloque.getInicio())) {
                        cursor = bloque.getInicio();
                    }

                    int minutosRestantesEnBloque = (int) java.time.Duration.between(cursor, bloque.getFin()).toMinutes();
                    if (minutosRestantesEnBloque <= 0) continue;

                    acumulado += minutosRestantesEnBloque;

                    if (acumulado >= minutosTotales) {
                        LocalTime fin = cursor.plusMinutes(minutosTotales - (acumulado - minutosRestantesEnBloque));
                        resultados.add(new DisponibilidadDTO(mecanico.getIdMecanico(), inicio, fin));
                        break;
                    }

                    cursor = bloque.getFin();
                }
            }

            if (!resultados.isEmpty()) break; // opcional: si solo querés mostrar disponibilidad de un mecánico
        }

        return resultados;
    }






}

