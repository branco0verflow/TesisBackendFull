package com.sgc.repositories;

import com.sgc.domains.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByDocumentoCliente(String cedulaCliente); // necesito rescatar al cliente existente por su cédula BB

    Optional<Cliente> findByEmailCliente(String emailCliente); // necesito verificar porque el Email es único y debo evitar duplicados. BB


}
