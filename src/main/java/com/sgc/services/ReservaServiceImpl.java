package com.sgc.services;

import com.sgc.domains.*;
import com.sgc.dtos.ClienteDTO;
import com.sgc.dtos.ReservaDTO;
import com.sgc.dtos.ReservaNuevaDTO;
import com.sgc.dtos.VehiculoDTO;
import com.sgc.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaServiceImpl {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private TipoTareaRepository tipoTareaRepository;

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private MecanicoRepository mecanicoRepository;

    @Autowired
    private TareaServiceImpl tareaService;

    @Autowired
    private TareaRepository tareaRepository;

    @Autowired
    private NotificacionService notificacionService;


    public List<Reserva> getReserva() {
        return reservaRepository.findAll();
    }

    public Optional<Reserva> getReservaById(int idReserva) {
        return reservaRepository.findById(idReserva);
    }

    @Transactional
    public Reserva createReserva(ReservaDTO dto) {
        Reserva reserva = new Reserva();


        reserva.setFechaCreadaReserva(java.sql.Date.valueOf(LocalDate.now()));
        reserva.setFechaCitaReserva(dto.getFechaCitaReserva());
        reserva.setHoraInicioReserva(dto.getHoraInicioReserva());
        reserva.setHoraFinReserva(dto.getHoraFinReserva());
        reserva.setComentariosReserva(dto.getComentariosReserva());

        // Buscar o crear Cliente
        ClienteDTO clienteDTO = dto.getCliente();
        Cliente cliente = clienteRepository.findByDocumentoCliente(clienteDTO.getDocumentoCliente())
                .orElseGet(() -> {
                    Cliente nuevo = new Cliente();
                    nuevo.setNombreCliente(clienteDTO.getNombreCliente());
                    nuevo.setApellidoCliente(clienteDTO.getApellidoCliente());
                    nuevo.setDocumentoCliente(clienteDTO.getDocumentoCliente());
                    nuevo.setEmailCliente(clienteDTO.getEmailCliente());
                    nuevo.setTelefonoCliente(clienteDTO.getTelefonoCliente());
                    nuevo.setDireccionCliente(clienteDTO.getDireccionCliente());
                    return clienteRepository.save(nuevo);
                });
        reserva.setCliente(cliente);

        // Buscar o crear Vehículo
        VehiculoDTO vehiculoDTO = dto.getVehiculo();
        Vehiculo vehiculo = vehiculoRepository.findByNroChasisVehiculo(vehiculoDTO.getNroChasisVehiculo())
                .orElseGet(() -> {
                    Vehiculo nuevo = new Vehiculo();
                    Modelo modelo = new Modelo();
                    modelo.setIdModelo(vehiculoDTO.getIdModelo());
                    nuevo.setModelo(modelo); // asumir que el modelo ya existe
                    nuevo.setNroChasisVehiculo(vehiculoDTO.getNroChasisVehiculo());
                    nuevo.setNroMotorVehiculo(vehiculoDTO.getNroMotorVehiculo());
                    nuevo.setAnoVehiculo(vehiculoDTO.getAnoVehiculo());
                    nuevo.setCilindradaVehiculo(vehiculoDTO.getCilindradaVehiculo());
                    nuevo.setKilometrajeVehiculo(vehiculoDTO.getKilometrajeVehiculo());
                    nuevo.setMatriculaVehiculo(vehiculoDTO.getMatriculaVehiculo());
                    return vehiculoRepository.save(nuevo);
                });
        reserva.setVehiculo(vehiculo);

        // Tipo de Tarea
        List<TipoTarea> tipoTareas = tipoTareaRepository.findAllById(dto.getIdsTipoTarea());
        reserva.setTipoTarea(tipoTareas);

        // Estado
        Estado estado = estadoRepository.findById(dto.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        reserva.setEstado(estado);

        Mecanico mecanico = mecanicoRepository.findById(dto.getIdMecanico()).orElseThrow(() -> new RuntimeException("Mecánico no encontrado"));
        reserva.setMecanico(mecanico);

        tareaService.createTareaFromReserva(reserva); // Aca se crea la Tarea de forma automática, si no queda el espacio disponible en la interfaz de admin. BB

        notificacionService.enviarSMS(cliente.getTelefonoCliente(),
                "Hola " + cliente.getNombreCliente() +
                        ", su reserva fue confirmada para el día " + reserva.getFechaCitaReserva() +
                        " a las " + reserva.getHoraInicioReserva() + ".");

        return reservaRepository.save(reserva);
    }

    // Esto resuelve poder crear Reservas desde CrearReservaSeguimiento.js (para reservas con Clientes y vehículos ya rehistrados)
    public Reserva createReservaDesdeIds(ReservaNuevaDTO dto) {

        boolean yaTieneReservaFutura = reservaRepository
                .existsByVehiculo_IdVehiculoAndFechaCitaReservaGreaterThanEqual(dto.getIdVehiculo(), LocalDate.now());


        if (yaTieneReservaFutura) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este vehículo ya tiene una reserva pendiente.");
        }

        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        Vehiculo vehiculo = vehiculoRepository.findById(dto.getIdVehiculo())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));
        Mecanico mecanico = mecanicoRepository.findById(dto.getIdMecanico())
                .orElseThrow(() -> new RuntimeException("Mecánico no encontrado"));
        Estado estado = estadoRepository.findById(dto.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        List<TipoTarea> tareas = tipoTareaRepository.findAllById(dto.getIdsTipoTarea());

        Reserva reserva = new Reserva();
        reserva.setFechaCreadaReserva(Date.valueOf(LocalDate.now()));
        reserva.setFechaCitaReserva(dto.getFechaCitaReserva());
        reserva.setHoraInicioReserva(dto.getHoraInicioReserva());
        reserva.setHoraFinReserva(dto.getHoraFinReserva());
        reserva.setComentariosReserva(dto.getComentariosReserva());
        reserva.setCliente(cliente);
        reserva.setVehiculo(vehiculo);
        reserva.setMecanico(mecanico);
        reserva.setEstado(estado);
        reserva.setTipoTarea(tareas);

        // primero guardar la reserva
        Reserva reservaGuardada = reservaRepository.save(reserva);

        // Después crear la tarea asociada con reserva ya persistida
        tareaService.createTareaFromReserva(reservaGuardada);

        notificacionService.enviarSMS(cliente.getTelefonoCliente(),
                "Hola " + cliente.getNombreCliente() +
                        ", su reserva fue confirmada para el día " + reserva.getFechaCitaReserva() +
                        " a las " + reserva.getHoraInicioReserva() + ".");


        return reservaGuardada;
    }

    @Transactional
    public boolean eliminarReservaYTarea(Integer idReserva) {
        Optional<Reserva> optionalReserva = reservaRepository.findById(idReserva);
        if (optionalReserva.isEmpty()) return false;

        Reserva reserva = optionalReserva.get();


        // Así desvinculo las relaciones en la tabla reserva_tipo_tarea (importante)
        if (reserva.getTipoTarea() != null) {
            reserva.getTipoTarea().clear();
        }

        // buscar y eliminar la tarea asociada
        Optional<Tarea> tareaOptional = tareaRepository.findByReservaIdReserva(idReserva);
        tareaOptional.ifPresent(tarea -> tareaService.eliminarTareaSegura(tarea.getIdTarea()));

        // eliminar la reserva
        reservaRepository.delete(reserva);
        return true;
    }




    public List<Reserva> obtenerReservasPorVehiculo(Integer idVehiculo) {
        return reservaRepository.findByVehiculoIdVehiculo(idVehiculo);
    }

    public boolean tieneReservaActivaOFutura(Integer idVehiculo) {
        LocalDate hoy = LocalDate.now();

        return reservaRepository.findByVehiculoIdVehiculo(idVehiculo).stream()
                .anyMatch(r -> !r.getFechaCitaReserva().toLocalDate().isBefore(hoy));
    }



    private Cliente findOrCreateCliente(ClienteDTO dto) {
        return clienteRepository.findByDocumentoCliente(dto.getDocumentoCliente())
                .or(() -> clienteRepository.findByEmailCliente(dto.getEmailCliente()))
                .orElseGet(() -> {
                    Cliente nuevo = new Cliente();
                    nuevo.setNombreCliente(dto.getNombreCliente());
                    nuevo.setApellidoCliente(dto.getApellidoCliente());
                    nuevo.setDocumentoCliente(dto.getDocumentoCliente());
                    nuevo.setEmailCliente(dto.getEmailCliente());
                    nuevo.setTelefonoCliente(dto.getTelefonoCliente());
                    nuevo.setDireccionCliente(dto.getDireccionCliente());
                    return clienteRepository.save(nuevo);
                });
    }


    private Vehiculo findOrCreateVehiculo(VehiculoDTO dto) {
        return vehiculoRepository.findByNroChasisVehiculo(dto.getNroChasisVehiculo())
                .orElseGet(() -> {
                    Vehiculo nuevo = new Vehiculo();
                    Modelo modelo = new Modelo();
                    modelo.setIdModelo(dto.getIdModelo());
                    nuevo.setModelo(modelo); // Asignación por referencia
                    nuevo.setNroChasisVehiculo(dto.getNroChasisVehiculo());
                    nuevo.setNroMotorVehiculo(dto.getNroMotorVehiculo());
                    nuevo.setAnoVehiculo(dto.getAnoVehiculo());
                    nuevo.setCilindradaVehiculo(dto.getCilindradaVehiculo());
                    nuevo.setKilometrajeVehiculo(dto.getKilometrajeVehiculo());
                    nuevo.setMatriculaVehiculo(dto.getMatriculaVehiculo());
                    return vehiculoRepository.save(nuevo);
                });
    }




    public Optional<Reserva> updateReserva(Integer idReserva, ReservaDTO dto) {
        return reservaRepository.findById(idReserva).map(existente -> {
            mapDTOToEntity(dto, existente);
            return reservaRepository.save(existente);
        });
    }

    public Optional<Reserva> patchReserva(Integer idReserva, ReservaDTO dto) {
        return reservaRepository.findById(idReserva).map(existente -> {
            if (dto.getFechaCreadaReserva() != null)
                existente.setFechaCreadaReserva(dto.getFechaCreadaReserva());

            if (dto.getFechaCitaReserva() != null)
                existente.setFechaCitaReserva(dto.getFechaCitaReserva());

            if (dto.getHoraInicioReserva() != null)
                existente.setHoraInicioReserva(dto.getHoraInicioReserva());

            if (dto.getHoraFinReserva() != null)
                existente.setHoraFinReserva(dto.getHoraFinReserva());

            if (dto.getComentariosReserva() != null)
                existente.setComentariosReserva(dto.getComentariosReserva());

            // Cliente
            if (dto.getCliente() != null) {
                ClienteDTO clienteDTO = dto.getCliente();
                Cliente cliente = clienteRepository.findByDocumentoCliente(clienteDTO.getDocumentoCliente())
                        .orElseGet(() -> {
                            Cliente nuevo = new Cliente();
                            nuevo.setNombreCliente(clienteDTO.getNombreCliente());
                            nuevo.setApellidoCliente(clienteDTO.getApellidoCliente());
                            nuevo.setDocumentoCliente(clienteDTO.getDocumentoCliente());
                            nuevo.setEmailCliente(clienteDTO.getEmailCliente());
                            nuevo.setTelefonoCliente(clienteDTO.getTelefonoCliente());
                            nuevo.setDireccionCliente(clienteDTO.getDireccionCliente());
                            return clienteRepository.save(nuevo);
                        });
                existente.setCliente(cliente);
            }

            // Vehículo
            if (dto.getVehiculo() != null) {
                VehiculoDTO vehiculoDTO = dto.getVehiculo();
                Vehiculo vehiculo = vehiculoRepository.findByNroChasisVehiculo(vehiculoDTO.getNroChasisVehiculo())
                        .orElseGet(() -> {
                            Vehiculo nuevo = new Vehiculo();
                            nuevo.setNroChasisVehiculo(vehiculoDTO.getNroChasisVehiculo());
                            nuevo.setNroMotorVehiculo(vehiculoDTO.getNroMotorVehiculo());
                            nuevo.setAnoVehiculo(vehiculoDTO.getAnoVehiculo());
                            nuevo.setCilindradaVehiculo(vehiculoDTO.getCilindradaVehiculo());
                            nuevo.setKilometrajeVehiculo(vehiculoDTO.getKilometrajeVehiculo());
                            nuevo.setMatriculaVehiculo(vehiculoDTO.getMatriculaVehiculo());

                            Modelo modelo = new Modelo();
                            modelo.setIdModelo(vehiculoDTO.getIdModelo());
                            nuevo.setModelo(modelo);

                            return vehiculoRepository.save(nuevo);
                        });
                existente.setVehiculo(vehiculo);
            }

            // Tipo tareas
            if (dto.getIdsTipoTarea() != null && !dto.getIdsTipoTarea().isEmpty()) {
                List<TipoTarea> tipoTareas = tipoTareaRepository.findAllById(dto.getIdsTipoTarea());
                existente.setTipoTarea(tipoTareas);
            }

            // Estado
            if (dto.getIdEstado() != null) {
                Estado estado = estadoRepository.findById(dto.getIdEstado())
                        .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
                existente.setEstado(estado);
            }

            if (dto.getIdMecanico() != null) {
                Mecanico mecanico = mecanicoRepository.findById(dto.getIdMecanico()).orElseThrow(() -> new RuntimeException("Mecánico no encontrado"));
                existente.setMecanico(mecanico);
            }

            return reservaRepository.save(existente);
        });
    }


    public boolean deleteReserva(Integer id) {
        if (!reservaRepository.existsById(id)) {
            return false;
        }
        reservaRepository.deleteById(id);
        return true;
    }

    private void mapDTOToEntity(ReservaDTO dto, Reserva reserva) {
        reserva.setFechaCreadaReserva(dto.getFechaCreadaReserva());
        reserva.setFechaCitaReserva(dto.getFechaCitaReserva());
        reserva.setHoraInicioReserva(dto.getHoraInicioReserva());
        reserva.setHoraFinReserva(dto.getHoraFinReserva());
        reserva.setComentariosReserva(dto.getComentariosReserva());

        // CLIENTE
        ClienteDTO clienteDTO = dto.getCliente();
        Cliente cliente = clienteRepository.findByDocumentoCliente(clienteDTO.getDocumentoCliente())
                .orElseGet(() -> {
                    Cliente nuevo = new Cliente();
                    nuevo.setNombreCliente(clienteDTO.getNombreCliente());
                    nuevo.setApellidoCliente(clienteDTO.getApellidoCliente());
                    nuevo.setDocumentoCliente(clienteDTO.getDocumentoCliente());
                    nuevo.setEmailCliente(clienteDTO.getEmailCliente());
                    nuevo.setTelefonoCliente(clienteDTO.getTelefonoCliente());
                    nuevo.setDireccionCliente(clienteDTO.getDireccionCliente());
                    return clienteRepository.save(nuevo);
                });
        reserva.setCliente(cliente);

        // VEHÍCULO
        VehiculoDTO vehiculoDTO = dto.getVehiculo();
        Vehiculo vehiculo = vehiculoRepository.findByNroChasisVehiculo(vehiculoDTO.getNroChasisVehiculo())
                .orElseGet(() -> {
                    Vehiculo nuevo = new Vehiculo();
                    nuevo.setNroChasisVehiculo(vehiculoDTO.getNroChasisVehiculo());
                    nuevo.setNroMotorVehiculo(vehiculoDTO.getNroMotorVehiculo());
                    nuevo.setAnoVehiculo(vehiculoDTO.getAnoVehiculo());
                    nuevo.setCilindradaVehiculo(vehiculoDTO.getCilindradaVehiculo());
                    nuevo.setKilometrajeVehiculo(vehiculoDTO.getKilometrajeVehiculo());
                    nuevo.setMatriculaVehiculo(vehiculoDTO.getMatriculaVehiculo());

                    Modelo modelo = new Modelo();
                    modelo.setIdModelo(vehiculoDTO.getIdModelo());
                    nuevo.setModelo(modelo);

                    return vehiculoRepository.save(nuevo);
                });
        reserva.setVehiculo(vehiculo);

        // TIPO TAREAS
        List<TipoTarea> tipoTareas = tipoTareaRepository.findAllById(dto.getIdsTipoTarea());
        reserva.setTipoTarea(tipoTareas);

        // ESTADO
        Estado estado = estadoRepository.findById(dto.getIdEstado())
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        reserva.setEstado(estado);


        Mecanico mecanico = mecanicoRepository.findById(dto.getIdMecanico())
                .orElseThrow(() -> new RuntimeException("Mecánico no encontrado"));
        reserva.setMecanico(mecanico);

    }



}
