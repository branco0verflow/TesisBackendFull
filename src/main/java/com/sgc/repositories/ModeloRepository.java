package com.sgc.repositories;

import com.sgc.domains.Marca;
import com.sgc.domains.Modelo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Integer> {
    @Query("SELECT m FROM Modelo m WHERE m.marca.id = :idMarca")
    Page<Modelo> findByIdMarca(@Param("idMarca") Integer idMarca, Pageable pageable);
    List<Modelo> findByNombreModeloContainingIgnoreCaseAndMarca(String nombreModelo, Marca marca);
}