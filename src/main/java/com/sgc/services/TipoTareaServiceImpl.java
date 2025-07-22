package com.sgc.services;

import com.sgc.domains.TipoTarea;
import com.sgc.repositories.MecanicoRepository;
import com.sgc.repositories.ReservaRepository;
import com.sgc.repositories.TipoTareaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoTareaServiceImpl {

    @Autowired
    private TipoTareaRepository tipoTareaRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MecanicoRepository mecanicoRepository;

    public List<TipoTarea> getTipoTarea() {
        return tipoTareaRepository.findAll();
    }

    public Optional<TipoTarea> getTipoTareaById(Integer id) {
        return tipoTareaRepository.findById(id);
    }

    public TipoTarea createTipoTarea(TipoTarea tipoTarea) {
        return tipoTareaRepository.save(tipoTarea);
    }

    public Optional<TipoTarea> updateTipoTarea(Integer idTipoTarea, TipoTarea tipoTareaActualizada) {
        return tipoTareaRepository.findById(idTipoTarea).map(existente -> {
            existente.setNombreTipoTarea(tipoTareaActualizada.getNombreTipoTarea());
            existente.setTiempoMinutosTipoTarea(tipoTareaActualizada.getTiempoMinutosTipoTarea());
            return tipoTareaRepository.save(existente);
        });
    }

    public Optional<TipoTarea> patchTipoTarea(Integer idTipoTarea, TipoTarea tipoTarea) {
        return tipoTareaRepository.findById(idTipoTarea)
                .map(existente -> {
                    if (tipoTarea.getNombreTipoTarea() != null) existente.setNombreTipoTarea(tipoTarea.getNombreTipoTarea());
                    if (tipoTarea.getTiempoMinutosTipoTarea() != null) existente.setTiempoMinutosTipoTarea(tipoTarea.getTiempoMinutosTipoTarea());
                    return tipoTareaRepository.save(existente);
                });
    }

    @Transactional
    public boolean deleteTipoTarea(Integer idTipoTarea) {
        if (!tipoTareaRepository.existsById(idTipoTarea)) {
            return false;
        }

        mecanicoRepository.deleteTipoTareaRelationsFromMecanicos(idTipoTarea);
        reservaRepository.deleteTipoTareaRelationsFromReservas(idTipoTarea);

        tipoTareaRepository.deleteById(idTipoTarea);
        return true;
    }
}
