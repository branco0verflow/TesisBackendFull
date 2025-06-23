package com.sgc.services;

import com.sgc.domains.Modelo;
import com.sgc.domains.Vehiculo;
import com.sgc.repositories.ModeloRepository;
import com.sgc.repositories.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoServiceImpl {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    public List<Vehiculo> getVehiculo() {
        return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> getVehiculoById(int id) {
        return vehiculoRepository.findById(id);
    }

    public Vehiculo createVehiculo(Vehiculo vehiculo) {
        Modelo modelo = modeloRepository.findById(vehiculo.getModelo().getIdModelo())
                .orElseThrow(() -> new RuntimeException("Modelo no encontrado"));
        vehiculo.setModelo(modelo);
        return vehiculoRepository.save(vehiculo);
    }

    public Optional<Vehiculo> updateVehiculo(Integer idVehiculo, Vehiculo vehiculo) {
        return vehiculoRepository.findById(idVehiculo)
                .map(existente -> {
                    existente.setModelo(vehiculo.getModelo());
                    existente.setNroChasisVehiculo(vehiculo.getNroChasisVehiculo());
                    existente.setNroMotorVehiculo(vehiculo.getNroMotorVehiculo());
                    existente.setAnoVehiculo(vehiculo.getAnoVehiculo());
                    existente.setCilindradaVehiculo(vehiculo.getCilindradaVehiculo());
                    existente.setKilometrajeVehiculo(vehiculo.getKilometrajeVehiculo());
                    existente.setMatriculaVehiculo(vehiculo.getMatriculaVehiculo());
                    return vehiculoRepository.save(existente);
                });
    }

    public Optional<Vehiculo> patchVehiculo(Integer idVehiculo, Vehiculo vehiculo) {
        return vehiculoRepository.findById(idVehiculo)
                .map(existente -> {
                    if (vehiculo.getModelo() != null) existente.setModelo(vehiculo.getModelo());
                    if (vehiculo.getNroChasisVehiculo() != null) existente.setNroChasisVehiculo(vehiculo.getNroChasisVehiculo());
                    if (vehiculo.getNroMotorVehiculo() != null) existente.setNroMotorVehiculo(vehiculo.getNroMotorVehiculo());
                    if (vehiculo.getAnoVehiculo() != null) existente.setAnoVehiculo(vehiculo.getAnoVehiculo());
                    if (vehiculo.getCilindradaVehiculo() != null) existente.setCilindradaVehiculo(vehiculo.getCilindradaVehiculo());
                    if (vehiculo.getKilometrajeVehiculo() != null) existente.setKilometrajeVehiculo(vehiculo.getKilometrajeVehiculo());
                    if (vehiculo.getMatriculaVehiculo() != null) existente.setMatriculaVehiculo(vehiculo.getMatriculaVehiculo());
                    return vehiculoRepository.save(existente);
                });
    }

    public boolean deleteVehiculo(Integer id) {
        if (!vehiculoRepository.existsById(id)) {
            return false;
        }
        vehiculoRepository.deleteById(id);
        return true;
    }
}
