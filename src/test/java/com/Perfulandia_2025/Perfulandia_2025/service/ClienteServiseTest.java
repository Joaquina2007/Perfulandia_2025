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

    // Inyecta el servicio de Cliente para ser probado.
    @Autowired
    private ClienteService clienteService;

    // Crea un mock del repositorio de Cliente para simular su comportamiento.
    @MockBean
    private ClienteRepository clienteRepository;

    @Test
    public void testFindAll() {
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con un Cliente.
        when(clienteRepository.findAll()).thenReturn(List.of(new ClienteModel(1, "Juan Pérez", "juan.perez@example.com", true)));

        // Llama al método findAll() del servicio.
        List<ClienteModel> clientes = clienteService.findAll();

        // Verifica que la lista devuelta no sea nula y contenga exactamente un Cliente.
        assertNotNull(clientes);
        assertEquals(1, clientes.size());
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        ClienteModel cliente = new ClienteModel(id, "Juan Pérez", "juan.perez@example.com", true);

        // Define el comportamiento del mock: cuando se llame a findById() con 1, devuelve un Cliente opcional.
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        // Llama al método findById() del servicio.
        ClienteModel found = clienteService.findById(id);

        // Verifica que el Cliente devuelto no sea nulo y que su id coincida con el id esperado.
        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void testSave() {
        ClienteModel cliente = new ClienteModel(1, "Juan Pérez", "juan.perez@example.com", true);

        // Define el comportamiento del mock: cuando se llame a save(), devuelve el Cliente proporcionado.
        when(clienteRepository.save(cliente)).thenReturn(cliente);

        // Llama al método save() del servicio.
        ClienteModel saved = clienteService.save(cliente);

        // Verifica que el Cliente guardado no sea nulo y que su nombre coincida con el nombre esperado.
        assertNotNull(saved);
        assertEquals("Juan Pérez", saved.getNombre());
    }

    @Test
    public void testDeleteById() {
        Integer id = 1;

        // Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada.
        doNothing().when(clienteRepository).deleteById(id);

        // Llama al método delete() del servicio.
        clienteService.delete(id);

        // Verifica que el método deleteById() del repositorio se haya llamado exactamente una vez con el id proporcionado.
        verify(clienteRepository, times(1)).deleteById(id);
    }
}
