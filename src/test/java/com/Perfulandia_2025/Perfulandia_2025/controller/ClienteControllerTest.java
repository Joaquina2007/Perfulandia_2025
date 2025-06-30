package com.Perfulandia_2025.Perfulandia_2025.controller;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Para simular peticiones HTTP

    @MockBean
    private ClienteService clienteService;  // Mock del servicio Cliente

    @Autowired
    private ObjectMapper objectMapper;  // Para convertir objetos a JSON

    private ClienteModel cliente;

    @BeforeEach
    void setUp() {
        // Configuramos un cliente de ejemplo antes de cada test
        cliente = new ClienteModel();
        cliente.setId(1);
        cliente.setNombre("Cristopher Barrueto");
        cliente.setCorreo("cristopher.antonio@duoc.com");
        cliente.setActivo(true);
    }

    @Test
    public void testGetAllClientes() throws Exception {
        // Definimos que cuando findAll() sea llamado, retorne una lista con el cliente
        when(clienteService.findAll()).thenReturn(List.of(cliente));

        // Ejecutamos GET /api/v1/cliente y verificamos status y contenido JSON
        mockMvc.perform(get("/api/v1/cliente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Cristopher Barrueto"))
                .andExpect(jsonPath("$[0].correo").value("cristopher.antonio@duoc.com"))
                .andExpect(jsonPath("$[0].activo").value(true));
    }

    @Test
    public void testGetClienteById() throws Exception {
        // Mock para findById(1) que devuelve el cliente configurado
        when(clienteService.findById(1)).thenReturn(cliente);

        // Ejecutamos GET /api/v1/cliente/1 y verificamos la respuesta JSON
        mockMvc.perform(get("/api/v1/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Cristopher Barrueto"))
                .andExpect(jsonPath("$.correo").value("cristopher.antonio@duoc.com"))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    public void testCreateCliente() throws Exception {
        // Mock para save, que devuelve el cliente cuando se guarda uno nuevo
        when(clienteService.save(any(ClienteModel.class))).thenReturn(cliente);

        // Ejecutamos POST /api/v1/cliente enviando JSON y verificamos respuesta
        mockMvc.perform(post("/api/v1/cliente")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Cristopher Barrueto"))
                .andExpect(jsonPath("$.correo").value("cristopher.antonio@duoc.com"))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    public void testUpdateCliente() throws Exception {
        // Mock para findById(1) y save al actualizar un cliente
        when(clienteService.findById(1)).thenReturn(cliente);
        when(clienteService.save(any(ClienteModel.class))).thenReturn(cliente);

        // Ejecutamos PUT /api/v1/cliente/1 con JSON y validamos la respuesta
        mockMvc.perform(put("/api/v1/cliente/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Cristopher Barrueto"))
                .andExpect(jsonPath("$.correo").value("cristopher.antonio@duoc.com"))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    public void testDeleteCliente() throws Exception {
        // Mock para delete que no hace nada
        doNothing().when(clienteService).delete(1);

        // Ejecutamos DELETE /api/v1/cliente/1 y esperamos status 204 No Content
        mockMvc.perform(delete("/api/v1/cliente/1"))
                .andExpect(status().isNoContent());
    }
}
