package com.sgc.security;

import com.sgc.domains.Mecanico;
import com.sgc.repositories.MecanicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MecanicoDetailsService implements UserDetailsService {

    @Autowired
    private MecanicoRepository mecanicoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Mecanico mecanico = mecanicoRepository.findByEmailMecanico(email)
                .orElseThrow(() -> new UsernameNotFoundException("Mecánico no encontrado"));
        return new MecanicoUserDetails(mecanico);
    }
}