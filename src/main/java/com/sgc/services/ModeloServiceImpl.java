package com.sgc.services;

import com.sgc.domains.Marca;
import com.sgc.domains.Modelo;
import com.sgc.repositories.MarcaRepository;
import com.sgc.repositories.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModeloServiceImpl {

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Modelo> getModelos() {
        return modeloRepository.findAll();
    }

    public Optional<Modelo> getModeloById(int idModelo) {
        return modeloRepository.findById(idModelo);
    }

    public List<Modelo> getModelosByMarcaId(Integer idMarca) {
        return modeloRepository.findByIdMarca(idMarca);
    }

    public List<Modelo> buscarPorNombreYMarca(String nombreModelo, Integer idmarca) { // Creado por BB no hice push
        Marca marca = marcaRepository.findById(idmarca)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        return modeloRepository.findByNombreModeloContainingIgnoreCaseAndMarca(nombreModelo, marca);
    }


    public Modelo createModelo(Modelo modelo) {
        Marca marca = marcaRepository.findById(modelo.getMarca().getIdMarca())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        modelo.setMarca(marca);
        return modeloRepository.save(modelo);
    }

    public Optional<Modelo> updateModelo(Integer idModelo, Modelo modelo) {
        return modeloRepository.findById(idModelo)
                .map(existente -> {
                    existente.setNombreModelo(modelo.getNombreModelo());
                    existente.setMarca(modelo.getMarca());
                    return modeloRepository.save(existente);
                });
    }

    public Optional<Modelo> patchModelo(Integer id, Modelo modelo) {
        return modeloRepository.findById(id)
                .map(existente -> {
                    if (modelo.getNombreModelo() != null) {
                        existente.setNombreModelo(modelo.getNombreModelo());
                    }
                    if (modelo.getMarca() != null) {
                        existente.setMarca(modelo.getMarca());
                    }
                    return modeloRepository.save(existente);
                });
    }

    public boolean deleteModelo(Integer id) {
        if (!modeloRepository.existsById(id)) {
            return false;
        }
        modeloRepository.deleteById(id);
        return true;
    }
}