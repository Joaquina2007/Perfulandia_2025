package com.Perfulandia_2025.Perfulandia_2025.controller;

import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.ProveedorRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.service.ProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProveedorController.class)
class ProveedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProveedorService proveedorService;

    private Proveedor proveedor;
    private ProveedorRequestDTO proveedorRequestDTO;

    @BeforeEach
    void setUp() {
        proveedor = new Proveedor(1L, "Proveedor Test", "Calle Falsa 123", 987654321, "RUC123456789", LocalDate.now(), true);
        proveedorRequestDTO = new ProveedorRequestDTO("Proveedor Test", "Calle Falsa 123", 987654321, "RUC123456789", true);
    }

    @Test
    @DisplayName("GET /api/proveedores")
    void shouldGetAllProveedores() throws Exception {
        when(proveedorService.getAllProveedores()).thenReturn(Arrays.asList(proveedor));
        mockMvc.perform(get("/api/proveedores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(proveedor.getId()))
                .andExpect(jsonPath("$[0].nombre").value(proveedor.getNombre()));
        verify(proveedorService, times(1)).getAllProveedores();
    }

    @Test
    @DisplayName("POST /api/proveedores")
    void shouldCreateProveedor() throws Exception {
        when(proveedorService.createProveedor(any(ProveedorRequestDTO.class))).thenReturn(proveedor);
        mockMvc.perform(post("/api/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(proveedorRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(proveedor.getId()))
                .andExpect(jsonPath("$.nombre").value(proveedor.getNombre()));
        verify(proveedorService, times(1)).createProveedor(any(ProveedorRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/proveedores")
    void shouldReturnBadRequestWhenCreateProveedorInvalid() throws Exception {
        when(proveedorService.createProveedor(any(ProveedorRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("Identificación es requerida"));
        ProveedorRequestDTO invalidDTO = new ProveedorRequestDTO("Proveedor Invalid", "Dir", 123, null, true);
        mockMvc.perform(post("/api/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Identificación es requerida"));
        verify(proveedorService, times(1)).createProveedor(any(ProveedorRequestDTO.class));
    }

    @Test
    @DisplayName("PUT /api/proveedores/{id}")
    void shouldUpdateProveedor() throws Exception {
        ProveedorRequestDTO updatedRequestDTO = new ProveedorRequestDTO("Proveedor Actualizado", "Nueva Dir", 999888777, "RUC000000000", false);
        Proveedor updatedProveedor = new Proveedor(1L, "Proveedor Actualizado", "Nueva Dir", 999888777, "RUC000000000", LocalDate.now(), false);
        when(proveedorService.updateProveedor(eq(1L), any(ProveedorRequestDTO.class))).thenReturn(updatedProveedor);
        mockMvc.perform(put("/api/proveedores/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedProveedor.getId()))
                .andExpect(jsonPath("$.nombre").value(updatedProveedor.getNombre()));
        verify(proveedorService, times(1)).updateProveedor(eq(1L), any(ProveedorRequestDTO.class));
    }

    @Test
    @DisplayName("PUT /api/proveedores/{id}")
    void shouldReturnNotFoundWhenUpdateProveedorNotFound() throws Exception {
        ProveedorRequestDTO updatedRequestDTO = new ProveedorRequestDTO("Inexistente", "Dir", 123, "IDN", true);
        when(proveedorService.updateProveedor(eq(99L), any(ProveedorRequestDTO.class)))
                .thenThrow(new RuntimeException("Proveedor no encontrado con id: 99"));
        mockMvc.perform(put("/api/proveedores/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Proveedor no encontrado con id: 99"));
        verify(proveedorService, times(1)).updateProveedor(eq(99L), any(ProveedorRequestDTO.class));
    }

    @Test
    @DisplayName("DELETE /api/proveedores/{id}")
    void shouldDeleteProveedor() throws Exception {
        doNothing().when(proveedorService).deleteProveedor(1L);
        mockMvc.perform(delete("/api/proveedores/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(proveedorService, times(1)).deleteProveedor(1L);
    }

    @Test
    @DisplayName("DELETE /api/proveedores/{id}")
    void shouldReturnNotFoundWhenDeleteProveedorNotFound() throws Exception {
        doThrow(new RuntimeException("Proveedor no encontrado")).when(proveedorService).deleteProveedor(99L);
        mockMvc.perform(delete("/api/proveedores/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(proveedorService, times(1)).deleteProveedor(99L);
    }
}
