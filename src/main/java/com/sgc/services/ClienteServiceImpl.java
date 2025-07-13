package com.sgc.services;

import com.sgc.domains.Cliente;
import com.sgc.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> getClienteById(Integer idCliente) {
        return clienteRepository.findById(idCliente);
    }

    public Cliente createCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> updateCliente(Integer idCliente, Cliente cliente) {
        return clienteRepository.findById(idCliente)
                .map(existente -> {
                    existente.setNombreCliente(cliente.getNombreCliente());
                    existente.setApellidoCliente(cliente.getApellidoCliente());
                    existente.setDocumentoCliente(cliente.getDocumentoCliente());
                    existente.setEmailCliente(cliente.getEmailCliente());
                    existente.setTelefonoCliente(cliente.getTelefonoCliente());
                    existente.setDireccionCliente(cliente.getDireccionCliente());
                    return clienteRepository.save(existente);
                });
    }

    public Optional<Cliente> patchCliente(Integer idCliente, Cliente cliente) {
        return clienteRepository.findById(idCliente)
                .map(existente -> {
                    if (cliente.getNombreCliente() != null) existente.setNombreCliente(cliente.getNombreCliente());
                    if (cliente.getApellidoCliente() != null) existente.setApellidoCliente(cliente.getApellidoCliente());
                    if (cliente.getDocumentoCliente() != null) existente.setDocumentoCliente(cliente.getDocumentoCliente());
                    if (cliente.getEmailCliente() != null) existente.setEmailCliente(cliente.getEmailCliente());
                    if (cliente.getTelefonoCliente() != null) existente.setTelefonoCliente(cliente.getTelefonoCliente());
                    if (cliente.getDireccionCliente() != null) existente.setDireccionCliente(cliente.getDireccionCliente());
                    return clienteRepository.save(existente);
                });
    }

    public Optional<Cliente> patchTelefono(Integer idCliente, String nuevoTelefono) {
        return clienteRepository.findById(idCliente).map(cliente -> {
            cliente.setTelefonoCliente(nuevoTelefono);
            return clienteRepository.save(cliente);
        });
    }


    public boolean deleteCliente(Integer idCliente) {
        if (!clienteRepository.existsById(idCliente)) {
            return false;
        }
        clienteRepository.deleteById(idCliente);
        return true;
    }
}