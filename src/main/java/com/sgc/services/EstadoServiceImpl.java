package com.sgc.services;

import com.sgc.domains.Estado;
import com.sgc.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoServiceImpl {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> getEstados() {
        return estadoRepository.findAll();
    }

    public Optional<Estado> getEstadoById(Integer idEstado) {
        return estadoRepository.findById(idEstado);
    }

    public Estado createEstado(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Optional<Estado> updateEstado(Integer idEstado, Estado estado) {
        return estadoRepository.findById(idEstado).map(existente -> {
            existente.setNombreEstado(estado.getNombreEstado());
            return estadoRepository.save(existente);
        });
    }

    public Optional<Estado> patchEstado(Integer idEstado, Estado estado) {
        return estadoRepository.findById(idEstado).map(existente -> {
            if (estado.getNombreEstado() != null) {
                existente.setNombreEstado(estado.getNombreEstado());
            }
            return estadoRepository.save(existente);
        });
    }

    public boolean deleteEstado(Integer id) {
        if (!estadoRepository.existsById(id)) {
            return false;
        }
        estadoRepository.deleteById(id);
        return true;
    }
}
