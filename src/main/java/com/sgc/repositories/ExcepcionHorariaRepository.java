package com.sgc.repositories;

import com.sgc.domains.ExcepcionHoraria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ExcepcionHorariaRepository extends JpaRepository<ExcepcionHoraria, Integer> {
    boolean existsByFecha(LocalDate fecha);
}
