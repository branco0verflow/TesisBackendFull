package com.sgc.services;

import com.sgc.domains.Marca;
import com.sgc.domains.Mecanico;
import com.sgc.domains.Modelo;
import com.sgc.domains.TipoTarea;
import com.sgc.dtos.MecanicoDTO;
import com.sgc.dtos.ModeloDTO;
import com.sgc.repositories.MarcaRepository;
import com.sgc.repositories.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModeloServiceImpl {

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    public Page<Modelo> getModelos(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return modeloRepository.findAll(pageable);
    }

    public Optional<Modelo> getModeloById(int idModelo) {
        return modeloRepository.findById(idModelo);
    }

    public Page<Modelo> getModelosByMarcaId(Integer idMarca, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return  modeloRepository.findByIdMarca(idMarca, pageable);
    }

    public List<Modelo> buscarPorNombreYMarca(String nombreModelo, Integer idmarca) { // Creado por BB no hice push
        Marca marca = marcaRepository.findById(idmarca)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        return modeloRepository.findByNombreModeloContainingIgnoreCaseAndMarca(nombreModelo, marca);
    }


    public Modelo createModelo(ModeloDTO modeloDTO) {
        Modelo modelo = new Modelo();
        mapDTOToEntity(modeloDTO, modelo);
        return modeloRepository.save(modelo);
    }

    public Optional<Modelo> updateModelo(Integer idModelo, ModeloDTO modeloDTO){
        return modeloRepository.findById(idModelo)
                .map(extistente -> {
                    mapDTOToEntity(modeloDTO, extistente);
                    return modeloRepository.save(extistente);
                });
    }

    public Optional<Modelo> patchModelo(Integer idModelo, ModeloDTO modeloDTO){
        return modeloRepository.findById(idModelo)
                .map(existente -> {
                    if(modeloDTO.getNombreModelo() != null) existente.setNombreModelo(modeloDTO.getNombreModelo());
                    if(modeloDTO.getIdMarca() != null) {
                        Marca marca = marcaRepository.findById(modeloDTO.getIdMarca()).orElseThrow(() -> new RuntimeException("Marca no encontrada"));
                        existente.setMarca(marca);
                    }
                    return modeloRepository.save(existente);
                });
    }

//    public Optional<Modelo> updateModelo(Integer idModelo, Modelo modelo) {
//        return modeloRepository.findById(idModelo)
//                .map(existente -> {
//                    existente.setNombreModelo(modelo.getNombreModelo());
//                    existente.setMarca(modelo.getMarca());
//                    return modeloRepository.save(existente);
//                });
//    }

//    public Optional<Modelo> patchModelo(Integer id, Modelo modelo) {
//        return modeloRepository.findById(id)
//                .map(existente -> {
//                    if (modelo.getNombreModelo() != null) {
//                        existente.setNombreModelo(modelo.getNombreModelo());
//                    }
//                    if (modelo.getMarca() != null) {
//                        existente.setMarca(modelo.getMarca());
//                    }
//                    return modeloRepository.save(existente);
//                });
//    }

    public boolean deleteModelo(Integer id) {
        if (!modeloRepository.existsById(id)) {
            return false;
        }
        modeloRepository.deleteById(id);
        return true;
    }

    private void mapDTOToEntity(ModeloDTO modeloDTO, Modelo modelo){
        modelo.setNombreModelo(modeloDTO.getNombreModelo());
        Marca marca = marcaRepository.findById(modeloDTO.getIdMarca()).orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        modelo.setMarca(marca);
    }
}