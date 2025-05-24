package com.Perfulandia_2025.Perfulandia_2025.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    private List<String> clientes = new ArrayList<>(List.of("Cliente 1", "Cliente 2", "Cliente 3"));

    public List<String> getAllClientes() {
        return clientes;
    }

    public String getClienteById(int id) {
        if (id < 0 || id >= clientes.size()) {
            return "Cliente not found";
        }
        return clientes.get(id);
    }

    public String createCliente(String cliente) {
        clientes.add(cliente);
        return cliente;
    }

    public String updateCliente(int id, String cliente) {
        if (id < 0 || id >= clientes.size()) {
            return "Cliente not found";
        }
        clientes.set(id, cliente);
        return cliente;
    }

    public String deleteCliente(int id) {
        if (id < 0 || id >= clientes.size()) {
            return "Cliente not found";
        }
        return clientes.remove(id);
    }
}