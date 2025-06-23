package com.sgc.repositories;

import com.sgc.domains.TipoTarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTareaRepository extends JpaRepository<TipoTarea, Integer> {
}
