package com.Perfulandia_2025.Perfulandia_2025.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository cliRepository;

    // Metodo Listar
    public List<ClienteModel> findAll() {
        return cliRepository.findAll();
    }

    // Metodo Guardar
    public ClienteModel save(ClienteModel climod) {
        return cliRepository.save(climod);
    }

    // Metodo Eliminar
    public void delete(Integer id) {
        cliRepository.deleteById(id);
    }

    // Metodo para Actualizar
    public ClienteModel findById(Integer id) {
        Optional<ClienteModel> cliente = cliRepository.findById(id);
        return cliente.orElse(null);
    }
}
