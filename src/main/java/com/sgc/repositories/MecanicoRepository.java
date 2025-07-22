package com.sgc.repositories;

import com.sgc.domains.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MecanicoRepository extends JpaRepository<Mecanico, Integer> {

    Optional<Mecanico> findByEmailMecanico(String email);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(
            value = "DELETE FROM mecanico_tipo_tarea WHERE idtipotarea = :idTipoTarea",
            nativeQuery = true
    )
    void deleteTipoTareaRelationsFromMecanicos(@Param("idTipoTarea") Integer idTipoTarea);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(
            value = "DELETE FROM mecanico_tipo_tarea WHERE idmecanico = :idMecanico",
            nativeQuery = true
    )
    void deleteMecanicosRelationsFromTipoTarea(@Param("idMecanico") Integer idMecanico);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("DELETE FROM Tarea t WHERE t.mecanico.id = :idMecanico")
    void updateTareaOnMecanicoDelete(@Param("idMecanico") Integer idMecanico);
}
