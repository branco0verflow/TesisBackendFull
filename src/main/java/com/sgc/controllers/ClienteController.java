package com.sgc.controllers;

import com.sgc.domains.Cliente;
import com.sgc.services.ClienteServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteServiceImpl clienteService;

    @GetMapping
    public ResponseEntity<?> getClientes() {
        List<Cliente> listaClientes = clienteService.getClientes();
        return ResponseEntity.ok(listaClientes);
    }

    @GetMapping("/{idCliente}")
    public ResponseEntity<?> getClientePorId(@PathVariable Integer idCliente) {
        return clienteService.getClienteById(idCliente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> postCliente(@Valid @RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.createCliente(cliente);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{idCliente}")
                .buildAndExpand(nuevoCliente.getIdCliente())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{idCliente}")
    public ResponseEntity<?> putCliente(@PathVariable Integer idCliente, @Valid @RequestBody Cliente cliente) {
        return clienteService.updateCliente(idCliente, cliente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{idCliente}")
    public ResponseEntity<?> patchCliente(@PathVariable Integer idCliente, @Valid @RequestBody Cliente cliente) {
        return clienteService.patchCliente(idCliente, cliente)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idCliente}")
    public ResponseEntity<?> deleteCliente(@PathVariable Integer idCliente) {
        boolean deleted = clienteService.deleteCliente(idCliente);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
