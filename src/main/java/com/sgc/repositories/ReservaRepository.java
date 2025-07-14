package com.sgc.repositories;

import com.sgc.domains.Cliente;
import com.sgc.domains.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
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




}

