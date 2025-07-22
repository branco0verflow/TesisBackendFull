package com.sgc.services;

import com.sgc.domains.Administrador;
import com.sgc.repositories.AdministradorRepository;
import com.sgc.repositories.TareaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorServiceImpl {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TareaRepository tareaRepository;


    public List<Administrador> getAdministrador() {
        return administradorRepository.findAll();
    }

    public Optional<Administrador> getAdministradorPorId(Integer id) {
        return administradorRepository.findById(id);
    }

    public Administrador createAdministrador(Administrador administrador) {
        String encryptedPassword = passwordEncoder.encode(administrador.getPasswordAdmin());
        administrador.setPasswordAdmin(encryptedPassword);
        return administradorRepository.save(administrador);
    }

    public Optional<Administrador> updateAdministrador(Integer idAdministrador, Administrador administrador) {
        return administradorRepository.findById(idAdministrador).map(existente -> {
            existente.setNombreAdmin(administrador.getNombreAdmin());
            existente.setApellidoAdmin(administrador.getApellidoAdmin());
            existente.setEmailAdmin(administrador.getEmailAdmin());
            String encryptedPassword = passwordEncoder.encode(administrador.getPasswordAdmin());
            existente.setPasswordAdmin(encryptedPassword);
            return administradorRepository.save(existente);
        });
    }

    public Optional<Administrador> patchAdministrador(Integer idAdministrador, Administrador administrador) {
        return administradorRepository.findById(idAdministrador)
                .map(existente -> {
                    if(administrador.getNombreAdmin() != null){
                        existente.setNombreAdmin(administrador.getNombreAdmin());
                    }
                    if(administrador.getApellidoAdmin() != null){
                        existente.setApellidoAdmin(administrador.getApellidoAdmin());
                    }
                    if(administrador.getEmailAdmin() != null){
                        existente.setEmailAdmin(administrador.getEmailAdmin());
                    }
                    if(administrador.getPasswordAdmin() != null){
                        String encryptedPassword = passwordEncoder.encode(administrador.getPasswordAdmin());
                        existente.setPasswordAdmin(encryptedPassword);
                    }
                    return administradorRepository.save(existente);
                });
    }

    @Transactional
    public boolean deleteAdministrador(Integer idAdministrador) {
        if (!administradorRepository.existsById(idAdministrador)) {
            return false;
        }

        tareaRepository.updateTareasOnAdminDelete(idAdministrador);
        administradorRepository.deleteById(idAdministrador);
        return true;
    }
}
