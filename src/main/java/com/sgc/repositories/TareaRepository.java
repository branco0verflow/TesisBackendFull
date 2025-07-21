package com.sgc.repositories;

import com.sgc.domains.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    @Query("SELECT t FROM Tarea t WHERE t.mecanico.id = :idMecanico AND t.fechaTarea = :fecha ORDER BY t.horaIngresoTarea")
    List<Tarea> findByMecanicoAndFecha(@Param("idMecanico") Integer idMecanico, @Param("fecha") LocalDate fecha);

    List<Tarea> findByFechaTareaOrderByHoraIngresoTarea(LocalDate fecha);

    Optional<Tarea> findByReservaIdReserva(Integer idReserva);

    List<Tarea> findByEstado_IdEstado(Integer idEstado);


}
