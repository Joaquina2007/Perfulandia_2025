package com.Perfulandia_2025.Perfulandia_2025.controller;

import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.RecepcionMercanciaRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.RecepcionMercanciaResponseDTO;
import com.Perfulandia_2025.Perfulandia_2025.service.RecepcionMercanciaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RecepcionMercanciaController.class)
class RecepcionMercanciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private RecepcionMercanciaService recepcionService;

    private RecepcionMercanciaRequestDTO recepcionRequestDTO;
    private RecepcionMercanciaResponseDTO recepcionResponseDTO;
    private PedidoReabastecimiento pedido;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        pedido = new PedidoReabastecimiento(1L, null, LocalDate.now(), LocalDate.now().plusDays(5), "Enviado", 100, null);
        recepcionRequestDTO = new RecepcionMercanciaRequestDTO("Carlos Gomez", "Sin novedades", "Registrada");
        recepcionResponseDTO = new RecepcionMercanciaResponseDTO(1L, pedido, LocalDate.now(), "Carlos Gomez", "Sin novedades", "Registrada");
    }

    @Test
    @DisplayName("GET /api/recepciones")
    void shouldGetAllRecepciones() throws Exception {
        when(recepcionService.getAllRecepciones()).thenReturn(Arrays.asList(recepcionResponseDTO));
        mockMvc.perform(get("/api/recepciones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(recepcionResponseDTO.getId()))
                .andExpect(jsonPath("$[0].recibidoPor").value(recepcionResponseDTO.getRecibidoPor()));
        verify(recepcionService, times(1)).getAllRecepciones();
    }

    @Test
    @DisplayName("GET /api/recepciones/{id}")
    void shouldGetRecepcionById() throws Exception {
        when(recepcionService.getRecepcionById(1L)).thenReturn(Optional.of(recepcionResponseDTO));
        mockMvc.perform(get("/api/recepciones/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recepcionResponseDTO.getId()))
                .andExpect(jsonPath("$.recibidoPor").value(recepcionResponseDTO.getRecibidoPor()));
        verify(recepcionService, times(1)).getRecepcionById(1L);
    }

    @Test
    @DisplayName("GET /api/recepciones/{id}")
    void shouldReturnNotFoundWhenGetRecepcionByIdNotFound() throws Exception {
        when(recepcionService.getRecepcionById(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/recepciones/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(recepcionService, times(1)).getRecepcionById(99L);
    }

    @Test
    @DisplayName("POST /api/recepciones/pedido/{pedidoId}")
    void shouldRegistrarRecepcion() throws Exception {
        when(recepcionService.registrarRecepcion(eq(1L), any(RecepcionMercanciaRequestDTO.class))).thenReturn(recepcionResponseDTO);
        mockMvc.perform(post("/api/recepciones/pedido/{pedidoId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recepcionRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(recepcionResponseDTO.getId()))
                .andExpect(jsonPath("$.recibidoPor").value(recepcionResponseDTO.getRecibidoPor()));
        verify(recepcionService, times(1)).registrarRecepcion(eq(1L), any(RecepcionMercanciaRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/recepciones/pedido/{pedidoId}")
    void shouldReturnBadRequestWhenRegistrarRecepcionInvalid() throws Exception {
        when(recepcionService.registrarRecepcion(eq(1L), any(RecepcionMercanciaRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("La persona que recibió la mercancía es obligatoria."));
        RecepcionMercanciaRequestDTO invalidDTO = new RecepcionMercanciaRequestDTO(null, "Notas", "Estado");
        mockMvc.perform(post("/api/recepciones/pedido/{pedidoId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("La persona que recibió la mercancía es obligatoria."));
        verify(recepcionService, times(1)).registrarRecepcion(eq(1L), any(RecepcionMercanciaRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/recepciones/pedido/{pedidoId}")
    void shouldReturnInternalServerErrorWhenRegistrarRecepcionServiceError() throws Exception {
        when(recepcionService.registrarRecepcion(eq(1L), any(RecepcionMercanciaRequestDTO.class)))
                .thenThrow(new RuntimeException("Error inesperado en el servicio"));
        mockMvc.perform(post("/api/recepciones/pedido/{pedidoId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recepcionRequestDTO)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error inesperado en el servicio"));
        verify(recepcionService, times(1)).registrarRecepcion(eq(1L), any(RecepcionMercanciaRequestDTO.class));
    }

    @Test
    @DisplayName("PUT /api/recepciones/{id}")
    void shouldUpdateRecepcion() throws Exception {
        RecepcionMercanciaRequestDTO updateRequestDTO = new RecepcionMercanciaRequestDTO("María López", "Mercancía dañada", "Recibida con observaciones");
        RecepcionMercanciaResponseDTO updatedResponseDTO = new RecepcionMercanciaResponseDTO(1L, pedido, LocalDate.now(), "María López", "Mercancía dañada", "Recibida con observaciones");
        when(recepcionService.updateRecepcion(eq(1L), any(RecepcionMercanciaRequestDTO.class))).thenReturn(updatedResponseDTO);
        mockMvc.perform(put("/api/recepciones/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedResponseDTO.getId()))
                .andExpect(jsonPath("$.recibidoPor").value(updatedResponseDTO.getRecibidoPor()));
        verify(recepcionService, times(1)).updateRecepcion(eq(1L), any(RecepcionMercanciaRequestDTO.class));
    }

    @Test
    @DisplayName("PUT /api/recepciones/{id}")
    void shouldReturnNotFoundWhenUpdateRecepcionNotFound() throws Exception {
        when(recepcionService.updateRecepcion(eq(99L), any(RecepcionMercanciaRequestDTO.class)))
                .thenThrow(new RuntimeException("Recepción de mercancía no encontrada con id: 99"));
        mockMvc.perform(put("/api/recepciones/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recepcionRequestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Recepción de mercancía no encontrada con id: 99"));
        verify(recepcionService, times(1)).updateRecepcion(eq(99L), any(RecepcionMercanciaRequestDTO.class));
    }

    @Test
    @DisplayName("DELETE /api/recepciones/{id}")
    void shouldDeleteRecepcion() throws Exception {
        doNothing().when(recepcionService).deleteRecepcion(1L);
        mockMvc.perform(delete("/api/recepciones/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(recepcionService, times(1)).deleteRecepcion(1L);
    }

    @Test
    @DisplayName("DELETE /api/recepciones/{id}")
    void shouldReturnNotFoundWhenDeleteRecepcionNotFound() throws Exception {
        doThrow(new RuntimeException("Recepción no encontrada")).when(recepcionService).deleteRecepcion(99L);
        mockMvc.perform(delete("/api/recepciones/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(recepcionService, times(1)).deleteRecepcion(99L);
    }
}