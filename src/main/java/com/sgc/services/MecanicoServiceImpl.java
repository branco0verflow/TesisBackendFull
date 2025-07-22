package com.sgc.services;

import com.sgc.domains.Mecanico;
import com.sgc.domains.TipoTarea;
import com.sgc.dtos.MecanicoDTO;
import com.sgc.repositories.MecanicoRepository;
import com.sgc.repositories.ReservaRepository;
import com.sgc.repositories.TipoTareaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MecanicoServiceImpl {
    @Autowired
    private MecanicoRepository mecanicoRepository;

    @Autowired
    private TipoTareaRepository tipoTareaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<MecanicoDTO> getMecanicosDTO(){ return mecanicoRepository.findAll().stream().map(this::mapEntityToDTO).toList();}

    public List<Mecanico> getMecanicos(){ return mecanicoRepository.findAll();}

    public Optional<MecanicoDTO> getMecanicoDTOById(Integer idMecanico) {
        return mecanicoRepository.findById(idMecanico)
                .map(this::mapEntityToDTO);
    }

    private MecanicoDTO mapEntityToDTO(Mecanico mecanico) {
        MecanicoDTO dto = new MecanicoDTO();
        dto.setIdMecanico(mecanico.getIdMecanico());
        dto.setNombreMecanico(mecanico.getNombreMecanico());
        dto.setApellidoMecanico(mecanico.getApellidoMecanico());
        dto.setEmailMecanico(mecanico.getEmailMecanico());
        dto.setActivoMecanico(mecanico.getActivoMecanico());
        dto.setTipoTareaIds(
                mecanico.getTipoTarea().stream()
                        .map(TipoTarea::getIdTipoTarea)
                        .toList()
        );
        return dto;
    }

    //Se empieza a usar la devolucion de mecanicoDTO
    public Optional<Mecanico> getMecanicoById(Integer idMecanico){ return mecanicoRepository.findById(idMecanico);}

    public Mecanico createMecanico(MecanicoDTO mecanicoDTO){
        Mecanico mecanico = new Mecanico();
        mapDTOToEntity(mecanicoDTO, mecanico);
        return mecanicoRepository.save(mecanico);
    }

    public Optional<Mecanico> updateMecanico(Integer idMecanico, MecanicoDTO mecanicoDTO){
        return mecanicoRepository.findById(idMecanico)
                .map(extistente -> {
                    mapDTOToEntity(mecanicoDTO, extistente);
                    return mecanicoRepository.save(extistente);
                });
    }

    @Transactional
    public Optional<Mecanico> patchMecanico(Integer idMecanico, MecanicoDTO mecanicoDTO){
        return mecanicoRepository.findById(idMecanico)
                .map(existente -> {
                    if(mecanicoDTO.getNombreMecanico() != null) existente.setNombreMecanico(mecanicoDTO.getNombreMecanico());
                    if(mecanicoDTO.getApellidoMecanico() != null) existente.setApellidoMecanico(mecanicoDTO.getApellidoMecanico());
                    if(mecanicoDTO.getEmailMecanico() != null) existente.setEmailMecanico(mecanicoDTO.getEmailMecanico());
                    if(mecanicoDTO.getActivoMecanico() != null) existente.setActivoMecanico(mecanicoDTO.getActivoMecanico());
                    if(mecanicoDTO.getTipoTareaIds() != null && !mecanicoDTO.getTipoTareaIds().isEmpty()){
                        List<TipoTarea> tipoTareas = tipoTareaRepository.findAllById(mecanicoDTO.getTipoTareaIds());
                        existente.setTipoTarea(tipoTareas);
                    }
                    if (mecanicoDTO.getPasswordMecanico() != null && !mecanicoDTO.getPasswordMecanico().isEmpty()) {
                        String encryptedPassword = passwordEncoder.encode(mecanicoDTO.getPasswordMecanico());
                        existente.setPasswordMecanico(encryptedPassword);
                    }
                    return mecanicoRepository.save(existente);
                });
    }

    @Transactional
    public boolean deleteMecanico(Integer idMecanico){
        if(!mecanicoRepository.existsById(idMecanico)){
            return false;
        }

        mecanicoRepository.deleteMecanicosRelationsFromTipoTarea(idMecanico);
        mecanicoRepository.updateTareaOnMecanicoDelete(idMecanico);
        reservaRepository.updateReservanOnMecanicoDelete(idMecanico);
        mecanicoRepository.deleteById(idMecanico);
        return true;
    }

    private void mapDTOToEntity(MecanicoDTO mecanicoDTO, Mecanico mecanico){
        mecanico.setNombreMecanico(mecanicoDTO.getNombreMecanico());
        mecanico.setApellidoMecanico(mecanicoDTO.getApellidoMecanico());
        mecanico.setEmailMecanico(mecanicoDTO.getEmailMecanico());
        String encryptedPassword = passwordEncoder.encode(mecanicoDTO.getPasswordMecanico());
        mecanico.setPasswordMecanico(encryptedPassword);
        mecanico.setActivoMecanico(mecanicoDTO.getActivoMecanico());
        List<TipoTarea> tipoTareas = tipoTareaRepository.findAllById(mecanicoDTO.getTipoTareaIds());
        mecanico.setTipoTarea(tipoTareas);
    }
}
