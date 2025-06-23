package com.sgc.repositories;

import com.sgc.domains.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Integer> {
    @Query("SELECT m FROM Marca m WHERE LOWER(m.nombreMarca) LIKE LOWER(CONCAT('%', :nombreMarca, '%'))")
    List<Marca> buscarPorNombre(@Param("nombreMarca") String nombreMarca);
}