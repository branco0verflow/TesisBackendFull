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

@Repository
public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    @Query("SELECT t FROM Tarea t WHERE t.mecanico.id = :idMecanico AND t.fechaTarea = :fecha ORDER BY t.horaIngresoTarea")
    List<Tarea> findByMecanicoAndFecha(@Param("idMecanico") Integer idMecanico, @Param("fecha") LocalDate fecha);


    /*

     Consulta los días con cantidad de tareas, recibiendo la fecha mínima como parámetro
    @Query("SELECT t.fechaTarea, COUNT(t) " +
            "FROM Tarea t " +
            "WHERE t.fechaTarea >= :fechaMinima " +
            "GROUP BY t.fechaTarea " +
            "ORDER BY t.fechaTarea ASC")
    List<Object[]> findDiasConCantidadDeTareas(Date fechaMinima);

    // Consulta las tareas de un día específico ordenadas por hora
    @Query("SELECT t FROM Tarea t " +
            "WHERE t.fechaTarea = :fecha " +
            "ORDER BY t.horaIngresoTarea ASC")
    List<Tarea> findTareasByFechaOrderByHora(Date fecha);

    */

}
