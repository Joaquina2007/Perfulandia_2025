package com.Perfulandia_2025.Perfulandia_2025.controller;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.repository.ClienteRepository;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.ClienteResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void testObtenerTodosClientesDTO() {
        ClienteModel cliente1 = new ClienteModel();
        cliente1.setNombre("Cristopher");
        cliente1.setCorreo("cristopher@example.com");
        clienteRepository.save(cliente1);

        ClienteModel cliente2 = new ClienteModel();
        cliente2.setNombre("Constanza");
        cliente2.setCorreo("constanza@example.com");
        clienteRepository.save(cliente2);


        List<ClienteResponseDTO> clientesDTO = clienteRepository.obtenerTodosClientesDTO();


        assertThat(clientesDTO).isNotNull();
        assertThat(clientesDTO.size()).isEqualTo(2);


        assertThat(clientesDTO)
                .extracting(ClienteResponseDTO::getNombre)
                .containsExactlyInAnyOrder("Cristopher", "Constanza");
    }
}
