package com.sgc.services;

import com.sgc.domains.*;
import com.sgc.dtos.TareaDTO;
import com.sgc.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TareaServiceImpl {

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MecanicoRepository mecanicoRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private NotificacionService notificacionService;


    public List<Tarea> getTareas() {
        return tareaRepository.findAll();
    }

    public Optional<Tarea> getTareaById(Integer idTarea) {
        return tareaRepository.findById(idTarea);
    }

    public Tarea createTarea(TareaDTO dto) {
        Tarea tarea = new Tarea();
        mapDTOToEntity(dto, tarea);
        return tareaRepository.save(tarea);
    }

    public Tarea createTareaFromReserva(Reserva reserva) {
        Tarea tarea = new Tarea();
        tarea.setFechaCreadaTarea(java.sql.Date.valueOf(LocalDate.now()));
        tarea.setFechaTarea(reserva.getFechaCitaReserva().toLocalDate());
        tarea.setHoraIngresoTarea(reserva.getHoraInicioReserva());
        tarea.setHoraFinTarea(reserva.getHoraFinReserva());
        tarea.setDescripcionTarea(reserva.getComentariosReserva());
        tarea.setEsReservaTarea(true);
        tarea.setReserva(reserva);
        tarea.setEstado(reserva.getEstado());
        tarea.setMecanico(reserva.getMecanico());


        Administrador admin = administradorRepository.findById(1) // vamos a ponerle un admin Default, esto hay que corregirlo BB
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
        tarea.setAdministrador(admin);

        return tareaRepository.save(tarea);
    }

    public List<TareaDTO> getTareasByFecha(LocalDate fecha) {
        List<Tarea> tareas = tareaRepository.findByFechaTareaOrderByHoraIngresoTarea(fecha);
        return tareas.stream()
                .map(TareaDTO::new)
                .collect(Collectors.toList());
    }


    public Optional<Tarea> updateTarea(Integer idTarea, TareaDTO dto) {
        return tareaRepository.findById(idTarea).map(existente -> {
            mapDTOToEntity(dto, existente);
            return tareaRepository.save(existente);
        });
    }

    public Optional<Tarea> patchTarea(Integer idTarea, TareaDTO dto) {
        return tareaRepository.findById(idTarea).map(tarea -> {

            LocalDate fechaAnterior = tarea.getFechaTarea();
            LocalTime horaAnterior = tarea.getHoraIngresoTarea().toLocalTime();

            if (dto.getFechaCreadaTarea() != null)
                tarea.setFechaCreadaTarea(dto.getFechaCreadaTarea());

            if (dto.getFechaTarea() != null)
                tarea.setFechaTarea(dto.getFechaTarea());

            if (dto.getHoraIngresoTarea() != null)
                tarea.setHoraIngresoTarea(dto.getHoraIngresoTarea());

            if (dto.getHoraFinTarea() != null)
                tarea.setHoraFinTarea(dto.getHoraFinTarea());

            if (dto.getDescripcionTarea() != null)
                tarea.setDescripcionTarea(dto.getDescripcionTarea());

            if (dto.getEsReservaTarea() != null)
                tarea.setEsReservaTarea(dto.getEsReservaTarea());

            if (dto.getIdReserva() != null) {
                Reserva reserva = reservaRepository.findById(dto.getIdReserva())
                        .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
                tarea.setReserva(reserva);
            }

            if (dto.getIdMecanico() != null) {
                Mecanico mecanico = mecanicoRepository.findById(dto.getIdMecanico())
                        .orElseThrow(() -> new RuntimeException("Mecánico no encontrado"));
                tarea.setMecanico(mecanico);
            }

            if (dto.getIdEstado() != null) {
                Estado estado = estadoRepository.findById(dto.getIdEstado())
                        .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
                tarea.setEstado(estado);
            }

            if (dto.getIdAdmin() != null) {
                Administrador administrador = administradorRepository.findById(dto.getIdAdmin())
                        .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
                tarea.setAdministrador(administrador);
            }

            Tarea tareaActualizada = tareaRepository.save(tarea);

            // Si la tarea está asociada a una reserva y fue modificada en fecha u hora, sincronizar y notificar
            if (Boolean.TRUE.equals(tarea.getEsReservaTarea()) && tarea.getReserva() != null) {
                Reserva reserva = tarea.getReserva();

                boolean cambioFecha = dto.getFechaTarea() != null &&
                        !Objects.equals(reserva.getFechaCitaReserva().toLocalDate(), tarea.getFechaTarea());

                boolean cambioHora = dto.getHoraIngresoTarea() != null &&
                        !Objects.equals(reserva.getHoraInicioReserva().toLocalTime(), tarea.getHoraIngresoTarea().toLocalTime());

                if (cambioFecha || cambioHora) {
                    // Sincronizar reserva
                    reserva.setFechaCitaReserva(Date.valueOf(tarea.getFechaTarea()));
                    reserva.setHoraInicioReserva(Time.valueOf(tarea.getHoraIngresoTarea().toLocalTime()));
                    reservaRepository.save(reserva);

                    // Notificar al cliente
                    Cliente cliente = reserva.getCliente();
                    notificacionService.notificarReservaModificada(
                            cliente.getTelefonoCliente(),
                            cliente.getNombreCliente(),
                            tarea.getFechaTarea(),
                            tarea.getHoraIngresoTarea().toLocalTime()
                    );
                }
            }

            return tareaActualizada;
        });
    }




    @Transactional
    public boolean eliminarTareaSegura(int idTarea) {
        Optional<Tarea> optionalTarea = tareaRepository.findById(idTarea);
        if (optionalTarea.isEmpty()) return false;

        Tarea tarea = optionalTarea.get();

        // Si tiene reserva asociada, eliminarla también
        if (tarea.getEsReservaTarea() != null && tarea.getEsReservaTarea() && tarea.getReserva() != null) {
            Reserva reserva = tarea.getReserva();

            // Guardar datos necesarios para notificación antes de eliminar
            String nombreCliente = reserva.getCliente().getNombreCliente();
            String numeroCliente = reserva.getCliente().getTelefonoCliente();
            LocalDate fecha = reserva.getFechaCitaReserva().toLocalDate();
            LocalTime hora = reserva.getHoraInicioReserva().toLocalTime();

            // Desvincular por las dudas, errores por este motivo
            if (reserva.getTipoTarea() != null) {
                reserva.getTipoTarea().clear();
            }
            reserva.setCliente(null);
            reserva.setVehiculo(null);
            reserva.setMecanico(null);
            reserva.setEstado(null);

            // Eliminar reserva
            reservaRepository.delete(reserva);

            // Enviar notificación
            notificacionService.notificarReservaCanceladaPorTaller(
                    numeroCliente,
                    nombreCliente,
                    fecha,
                    hora
            );
        }

        // Desvincular campos de la tarea antes de eliminar
        tarea.setReserva(null);
        tarea.setMecanico(null);
        tarea.setEstado(null);
        tarea.setAdministrador(null);

        tareaRepository.delete(tarea);
        return true;
    }



    // No se usa este metodo, ver borrar BB
    public boolean deleteTarea(Integer idTarea) {
        if (!tareaRepository.existsById(idTarea)) {
            return false;
        }
        tareaRepository.deleteById(idTarea);
        return true;
    }

    private void mapDTOToEntity(TareaDTO dto, Tarea tarea) {
        tarea.setFechaCreadaTarea(
                dto.getFechaCreadaTarea() != null ?
                        dto.getFechaCreadaTarea() :
                        new java.sql.Date(System.currentTimeMillis())
        );

        tarea.setFechaTarea(dto.getFechaTarea());
        tarea.setHoraIngresoTarea(dto.getHoraIngresoTarea());
        tarea.setHoraFinTarea(dto.getHoraFinTarea());
        tarea.setDescripcionTarea(dto.getDescripcionTarea());
        tarea.setEsReservaTarea(dto.getEsReservaTarea());

        if (dto.getEsReservaTarea() != null && dto.getEsReservaTarea()) {
            if (dto.getIdReserva() == null) {
                throw new RuntimeException("Se debe asociar una reserva si esReservaTarea es true");
            }

            Reserva reserva = reservaRepository.findById(dto.getIdReserva())
                    .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
            tarea.setReserva(reserva);
        }


        if (dto.getIdMecanico() != null) {
            Mecanico mecanico = mecanicoRepository.findById(dto.getIdMecanico())
                    .orElseThrow(() -> new RuntimeException("Mecánico no encontrado"));
            tarea.setMecanico(mecanico);
        }

        if (dto.getIdEstado() != null) {
            Estado estado = estadoRepository.findById(dto.getIdEstado())
                    .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
            tarea.setEstado(estado);
        }

        if (dto.getIdAdmin() != null) {
            Administrador administrador = administradorRepository.findById(dto.getIdAdmin())
                    .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
            tarea.setAdministrador(administrador);
        }
        System.out.println("Existe admin? " + administradorRepository.existsById(dto.getIdAdmin()));
        System.out.println("Existe estado? " + estadoRepository.existsById(dto.getIdEstado()));
        System.out.println("Existe mecanico? " + mecanicoRepository.existsById(dto.getIdMecanico()));


    }

}
