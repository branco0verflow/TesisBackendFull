package com.sgc.repositories;

import com.sgc.domains.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    Optional<Vehiculo> findByNroChasisVehiculo(String chasis); // necesito rescatar al vehículo si ya existe para evitar duplicados

}
