package com.Perfulandia_2025.Perfulandia_2025.controller;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias para ClienteController usando WebMvcTest.
 * Simula peticiones HTTP sin levantar el servidor.
 */
@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simula las peticiones HTTP

    @MockBean
    private ClienteService clienteService; // Mock del servicio que será inyectado al controlador

    @Autowired
    private ObjectMapper objectMapper; // Convierte objetos Java <-> JSON

    private ClienteModel cliente;

    @BeforeEach
    void setUp() {
        // Creamos un cliente de prueba para usarlo en los test
        cliente = new ClienteModel();
        cliente.setId(1);
        cliente.setNombre("Cristopher Barrueto");
        cliente.setCorreo("cristopher.antonio@duoc.com");
        cliente.setActivo(true);
    }

    @Test
    @DisplayName("Debe retornar un cliente por su ID")
    public void testGetClienteById() throws Exception {
        // Simulamos que al buscar por ID 1, se retorne el cliente
        when(clienteService.findById(1)).thenReturn(cliente);

        // Simulamos la petición GET y verificamos los valores JSON de respuesta
        mockMvc.perform(get("/api/v1/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Cristopher Barrueto"))
                .andExpect(jsonPath("$.correo").value("cristopher.antonio@duoc.com"))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    @DisplayName("Debe crear un nuevo cliente")
    public void testCreateCliente() throws Exception {
        // Simulamos que al guardar un cliente, el servicio lo retorna con ID
        when(clienteService.save(any(ClienteModel.class))).thenReturn(cliente);

        // Simulamos la petición POST con un cliente en formato JSON y verificamos la respuesta
        mockMvc.perform(post("/api/v1/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Cristopher Barrueto"))
                .andExpect(jsonPath("$.correo").value("cristopher.antonio@duoc.com"))
                .andExpect(jsonPath("$.activo").value(true));
    }
}

