package com.sgc.services;

import com.sgc.domains.*;
import com.sgc.dtos.DisponibilidadDTO;
import com.sgc.dtos.ProximaDisponibilidadDTO;
import com.sgc.dtos.TimeRangeDTO;
import com.sgc.repositories.MecanicoRepository;
import com.sgc.repositories.TareaRepository;
import com.sgc.repositories.TipoTareaRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
        List<TipoTarea> tareas = tipoTareaRepo.findAllById(idTipoTareas);
        int minutosTotales = tareas.stream()
                .mapToInt(TipoTarea::getTiempoMinutosTipoTarea)
                .sum();

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

        List<LocalDate> diasDisponibles = new ArrayList<>();
        LocalDate hoy = LocalDate.now().plusDays(2);

        for (int i = 0; i < limiteDias; i++) {
            LocalDate dia = hoy.plusDays(i);
            List<Tarea> tareasDelDia = tareaRepo.findByMecanicoAndFecha(mecanico.getIdMecanico(), dia);
            List<TimeRange> huecos = calcularHuecos(tareasDelDia);

            Optional<TimeRange> bloque = encontrarBloqueAcumulado(huecos, minutosTotales);
            if (bloque.isPresent()) {
                diasDisponibles.add(dia);
            }
        }

        return diasDisponibles;
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

            Optional<TimeRange> bloque = encontrarBloqueAcumulado(huecos, minutosTotales);
            bloque.ifPresent(b -> resultados.add(
                    new DisponibilidadDTO(mecanico.getIdMecanico(), b.getInicio(), b.getFin())
            ));
        }

        return resultados;
    }

    public List<ProximaDisponibilidadDTO> buscarProximaDisponibilidadPorMecanico(int minutosRequeridos, int limiteDias) {
        List<Mecanico> mecanicos = mecanicoRepo.findAll();
        List<ProximaDisponibilidadDTO> resultados = new ArrayList<>();
        LocalDate hoy = LocalDate.now().plusDays(2);

        for (Mecanico mecanico : mecanicos) {
            for (int i = 0; i < limiteDias; i++) {
                LocalDate fecha = hoy.plusDays(i);
                List<Tarea> tareasDelDia = tareaRepo.findByMecanicoAndFecha(mecanico.getIdMecanico(), fecha);
                List<TimeRange> huecos = calcularHuecos(tareasDelDia);

                Optional<TimeRange> bloque = encontrarBloqueAcumulado(huecos, minutosRequeridos);
                if (bloque.isPresent()) {
                    resultados.add(new ProximaDisponibilidadDTO(
                            mecanico.getIdMecanico(),
                            mecanico.getNombreMecanico(),
                            fecha,
                            bloque.get().getInicio(),
                            bloque.get().getFin()
                    ));
                    break;
                }
            }
        }

        return resultados;
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

    private Optional<TimeRange> encontrarBloqueAcumulado(List<TimeRange> huecos, int minutosNecesarios) {
        for (int i = 0; i < huecos.size(); i++) {
            LocalTime inicio = huecos.get(i).getInicio();
            LocalTime cursor = inicio;
            int acumulado = 0;

            for (int j = i; j < huecos.size(); j++) {
                TimeRange bloque = huecos.get(j);

                if (cursor.isBefore(bloque.getInicio())) {
                    cursor = bloque.getInicio();
                }

                int disponibles = (int) Duration.between(cursor, bloque.getFin()).toMinutes();
                if (disponibles <= 0) continue;

                acumulado += disponibles;

                if (acumulado >= minutosNecesarios) {
                    LocalTime fin = cursor.plusMinutes(minutosNecesarios - (acumulado - disponibles));
                    return Optional.of(new TimeRange(inicio, fin));
                }

                cursor = bloque.getFin();
            }
        }

        return Optional.empty();
    }
}


