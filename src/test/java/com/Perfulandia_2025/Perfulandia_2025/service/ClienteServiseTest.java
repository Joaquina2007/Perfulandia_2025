package com.Perfulandia_2025.Perfulandia_2025.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class ClienteServiceTest {

    // aqui para qie el cliente  puede se probaso
    @Autowired
    private ClienteService clienteService;

    // aqui crear un mock para comprobar el comportamiento
    @MockBean
    private ClienteRepository clienteRepository;

    @Test
    public void testFindAll() {
        // aqui definir el comportamiento.
        when(clienteRepository.findAll()).thenReturn(List.of(new ClienteModel(1, "cristopher barrueto", "cristopher@duoc.com", true)));

        // aqui llama al metodo
        List<ClienteModel> clientes = clienteService.findAll();

        // aquii verifica
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        ClienteModel cliente = new ClienteModel(id, "cristopher barrueto", "cristopher@duoc.com", true);
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        // llamar el servicio
        ClienteModel found = clienteService.findById(id);

        // aqui que no sea nulo .
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testSave() {
        ClienteModel cliente = new ClienteModel(1, "cristopher barrueto", "cristopher@duoc.com", true);

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Llama al método save() del servicio.
        ClienteModel saved = clienteService.save(cliente);

        // aque que coincida con el nombre esperado.
        assertNotNull(saved);
        assertEquals("cristopher barrueto", saved.getNombre());
    }

    @Test
    public void testDeleteById() {
        Integer id = 1;

        // 
        doNothing().when(clienteRepository).deleteById(id);
        clienteService.delete(id);

        verify(clienteRepository, times(1)).deleteById(id);
    }
}
