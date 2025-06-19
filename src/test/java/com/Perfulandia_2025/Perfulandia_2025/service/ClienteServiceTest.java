package com.Perfulandia_2025.Perfulandia_2025.service;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @MockBean
    private ClienteRepository clienteRepository;

    @Test
    public void testFindAll() {
        ClienteModel cliente = new ClienteModel();
        cliente.setId((int) 0x1);
        cliente.setNombre("Cristopher");
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        List<ClienteModel> clientes = clienteService.findAll();
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
        assertEquals("Cristopher", clientes.get(0).getNombre());
    }

    @Test
    public void testFindById() {
        ClienteModel cliente = new ClienteModel();
        cliente.setNombre("Cristopher");
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        ClienteModel result = clienteService.findById(1L);
        assertNotNull(result);
        assertEquals(Long.valueOf(1L), result.getId());
        assertEquals("Cristopher", result.getNombre());
    }

    @Test
    public void testFindById_NotFound() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.findById(99L);
        });
        assertTrue(exception.getMessage().contains("Cliente no encontrado con id: 99"));
    }

    @Test
    public void testSave() {
        ClienteModel cliente = new ClienteModel();
        cliente.setNombre("Cristopher");
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        ClienteModel saved = clienteService.save(cliente);
        assertNotNull(saved);
        assertEquals("Cristopher", saved.getNombre());
    }

    @Test
    public void testDelete() {
        Long id = 1L;
        doNothing().when(clienteRepository).deleteById(id);
        clienteService.delete(id);
    }
}