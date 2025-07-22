package com.sgc.repositories;

import com.sgc.domains.Cliente;
import com.sgc.domains.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Integer> {


    @Query("""
       SELECT r
       FROM   Reserva r
              LEFT JOIN FETCH r.mecanico m
              JOIN  FETCH r.vehiculo v
              JOIN  FETCH v.modelo mo
              JOIN  FETCH mo.marca
       WHERE  r.cliente.documentoCliente = :doc
         AND  LOWER(TRIM(r.cliente.emailCliente)) = LOWER(TRIM(:email))
       """)
    List<Reserva> findSeguimiento(@Param("doc") String doc, @Param("email") String email);



    List<Reserva> findByVehiculoIdVehiculo(Integer idVehiculo);



    boolean existsByVehiculo_IdVehiculoAndFechaCitaReservaGreaterThanEqual(Integer idVehiculo, LocalDate fecha);


    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(
            value = "DELETE FROM reserva_tipo_tarea WHERE idtipotarea = :idTipoTarea",
            nativeQuery = true
    )
    void deleteTipoTareaRelationsFromReservas(@Param("idTipoTarea") Integer idTipoTarea);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Reserva t SET t.mecanico = null WHERE t.mecanico.id = :idMecanico")
    void updateReservanOnMecanicoDelete(@Param("idMecanico") Integer idMecanico);

    List<Reserva> findByFechaCitaReservaAndRecordatorioEnviadoFalse(LocalDate fechaCitaReserva);
    // Necesario para envío de mensajes recordatorios.
}