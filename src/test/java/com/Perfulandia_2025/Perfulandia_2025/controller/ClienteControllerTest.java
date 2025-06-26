package com.Perfulandia_2025.Perfulandia_2025.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc; //realiza peticiones HTTP en las pruebas
    @MockBean
    private ClienteService clienteService; // mock del servicio de Cliente
    @Autowired
    private ObjectMapper objectMapper; // onvertir objeos Jav a JSO y vicevrsa
    private ClienteModel cliente;
    @BeforeEach
    void setUp() {
        // apunte se configura el cliente antes de cada prueba
        cliente = new ClienteModel();
        cliente.setId(1);
        cliente.setNombre("Cristopher Barrueto");
        cliente.setCorreo("cristopher.antonio@duoc.com");
        cliente.setActivo(true);
    }
    @Test
    public void testGetAllClientes() throws Exception {
        //como recodatorio aqui define el comportamiento del mok
        when(clienteService.findAll()).thenReturn(List.of(cliente));
        mockMvc.perform(get("/api/v1/cliente"))
                .andExpect(status().isOk()) // Aqui veririfica el resultado
                .andExpect(jsonPath("$[0].id").value(1)) // aqui verifiva qie sea 1
                .andExpect(jsonPath("$[0].nombre").value("Cristopher Barrueto")) // aqui el nombre
                .andExpect(jsonPath("$[0].correo").value("cristopher.antonio@duoc.com")) // el corrreo
                .andExpect(jsonPath("$[0].activo").value(true)); // y que este activo etc
    }

    @Test
    public void testGetClienteById() throws Exception {
        // aqui definir comportamiento del mok pendidentee
        when(clienteService.findById(1)).thenReturn(cliente);
        // swui probar que si arrija la respuesrta correcta  al hacer el get
        mockMvc.perform(get("/api/v1/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Cristopher Barrueto"))
                .andExpect(jsonPath("$.correo").value("cristopher.antonio@duoc.com"))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    public void testCreateCliente() throws Exception {
        // aqui que guarde
        when(clienteService.save(any(ClienteModel.class))).thenReturn(cliente);
        mockMvc.perform(post("/api/v1/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente))) // aqui probar para pasarlo a jasom
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Cristopher Barrueto"))
                .andExpect(jsonPath("$.correo").value("cristopher.antonio@duoc.com"))
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test
    public void testUpdateCliente() throws Exception {
        // aqui para llamar el save prioridad
        when(clienteService.save(any(ClienteModel.class))).thenReturn(cliente);

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
//aqui elimina
        doNothing().when(clienteService).delete(1);
        mockMvc.perform(delete("/api/v1/cliente/1"));
    }
}
