package com.sgc.services;

import com.sgc.domains.Marca;
import com.sgc.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaServiceImpl {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> getMarcaPorNombre(String nombreMarca) {
        return marcaRepository.buscarPorNombre(nombreMarca);
    }

    public List<Marca> getMarcas() {
        return marcaRepository.findAll();
    }

    public Optional<Marca> getMarcaById(int idMarca){
        return marcaRepository.findById(idMarca);
    }

    public Marca createMarca(Marca marca) {
        return marcaRepository.save(marca);
    }

    public Optional<Marca> updateMarca(Integer idMarca, Marca marca) {
        return marcaRepository.findById(idMarca).map(existente -> { existente.setNombreMarca(marca.getNombreMarca());return marcaRepository.save(existente);});
    }

    public Optional<Marca> patchMarca(Integer idMarca, Marca marca) {
        return marcaRepository.findById(idMarca)
                .map(existente -> {
                    if(marca.getNombreMarca() != null){
                        existente.setNombreMarca(marca.getNombreMarca());
                    }
                    return marcaRepository.save(existente);
                });
    }

    public boolean deleteMarca(Integer idMarca) {
        if (!marcaRepository.existsById(idMarca)) {
            return false;
        }
        marcaRepository.deleteById(idMarca);
        return true;
    }

}
