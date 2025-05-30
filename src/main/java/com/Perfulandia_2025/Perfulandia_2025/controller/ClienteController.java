package com.Perfulandia_2025.Perfulandia_2025.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @GetMapping
    public List<String> getAllClientes() {
        return List.of("Cliente 1", "Cliente 2", "Cliente 3");
    }
    //crear clientes

    @GetMapping("/{id}")
    public String getClienteById(@PathVariable String id) {
        return "Cliente with ID: " + id;
    }

    @PostMapping
    public String createCliente(@RequestBody String cliente) {
        return "Created cliente: " + cliente;
    }

    @PutMapping("/{id}")
    public String updateCliente(@PathVariable String id, @RequestBody String cliente) {
        return "Updated cliente with ID " + id + " to: " + cliente;
    }

    @DeleteMapping("/{id}")
    public String deleteCliente(@PathVariable String id) {
        return "Deleted cliente with ID: " + id;
    }
}
