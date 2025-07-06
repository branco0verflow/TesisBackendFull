package com.sgc.repositories;

import com.sgc.domains.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MecanicoRepository extends JpaRepository<Mecanico, Integer> {

    Optional<Mecanico> findByEmailMecanico(String email);
}
