package com.sgc.security;

import com.sgc.domains.Mecanico;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class MecanicoUserDetails implements UserDetails {

    private final Mecanico mecanico;

    public MecanicoUserDetails(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    public Integer getId() {
        return mecanico.getIdMecanico();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_MECANICO"));
    }

    @Override
    public String getPassword() {
        return mecanico.getPasswordMecanico();
    }

    @Override
    public String getUsername() {
        return mecanico.getEmailMecanico();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
