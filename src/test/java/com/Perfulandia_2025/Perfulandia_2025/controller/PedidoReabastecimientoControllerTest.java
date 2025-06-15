package com.Perfulandia_2025.Perfulandia_2025.controller;

import com.Perfulandia_2025.Perfulandia_2025.requestDTO.ItemPedidoRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.PedidoReabastecimientoRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.ItemPedidoResponseDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.PedidoReabastecimientoResponseDTO;
import com.Perfulandia_2025.Perfulandia_2025.service.PedidoReabastecimientoService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoReabastecimientoController.class)
class PedidoReabastecimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @MockBean
    private PedidoReabastecimientoService pedidoService;

    private PedidoReabastecimientoRequestDTO pedidoRequestDTO;
    private PedidoReabastecimientoResponseDTO pedidoResponseDTO;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        pedidoRequestDTO = new PedidoReabastecimientoRequestDTO(1L, LocalDate.now().plusDays(7), "Pendiente", 100,
                Arrays.asList(new ItemPedidoRequestDTO("Item X", 2, BigDecimal.valueOf(50.0), "SKU123"))
        );
        pedidoResponseDTO = new PedidoReabastecimientoResponseDTO(
                1L,
                1L,
                "Proveedor A",
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                "Pendiente",
                100,
                Arrays.asList(new ItemPedidoResponseDTO(10L, null, "Item X", 2, BigDecimal.valueOf(50.0), "SKU123"))
        );
    }

    @Test
    @DisplayName("GET /api/pedidos")
    void shouldGetAllPedidos() throws Exception {
        when(pedidoService.getAllPedidos()).thenReturn(Arrays.asList(pedidoResponseDTO));
        mockMvc.perform(get("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(pedidoResponseDTO.getId()))
                .andExpect(jsonPath("$[0].estadoPedido").value(pedidoResponseDTO.getEstadoPedido()));
        verify(pedidoService, times(1)).getAllPedidos();
    }

    @Test
    @DisplayName("GET /api/pedidos/{id}")
    void shouldGetPedidoById() throws Exception {
        when(pedidoService.getPedidoById(1L)).thenReturn(Optional.of(pedidoResponseDTO));
        mockMvc.perform(get("/api/pedidos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(pedidoResponseDTO.getId()))
                .andExpect(jsonPath("$.estadoPedido").value(pedidoResponseDTO.getEstadoPedido()));
        verify(pedidoService, times(1)).getPedidoById(1L);
    }

    @Test
    @DisplayName("GET /api/pedidos/{id}")
    void shouldReturnNotFoundWhenGetPedidoByIdNotFound() throws Exception {
        when(pedidoService.getPedidoById(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/pedidos/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(pedidoService, times(1)).getPedidoById(99L);
    }

    @Test
    @DisplayName("GET /api/pedidos/proveedor/{proveedorId}")
    void shouldGetPedidosByProveedor() throws Exception {
        when(pedidoService.getPedidosByProveedor(1L)).thenReturn(Arrays.asList(pedidoResponseDTO));
        mockMvc.perform(get("/api/pedidos/proveedor/{proveedorId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].proveedor").value(pedidoResponseDTO.getProveedor()));
        verify(pedidoService, times(1)).getPedidosByProveedor(1L);
    }

    @Test
    @DisplayName("GET /api/pedidos/proveedor/{proveedorId}")
    void shouldReturnNoContentWhenGetPedidosByProveedorEmpty() throws Exception {
        when(pedidoService.getPedidosByProveedor(1L)).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/pedidos/proveedor/{proveedorId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(pedidoService, times(1)).getPedidosByProveedor(1L);
    }

    @Test
    @DisplayName("POST /api/pedidos")
    void shouldCreatePedido() throws Exception {
        when(pedidoService.createPedido(any(PedidoReabastecimientoRequestDTO.class))).thenReturn(pedidoResponseDTO);
        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(pedidoResponseDTO.getId()))
                .andExpect(jsonPath("$.estadoPedido").value(pedidoResponseDTO.getEstadoPedido()));
        verify(pedidoService, times(1)).createPedido(any(PedidoReabastecimientoRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/pedidos")
    void shouldReturnBadRequestWhenCreatePedidoFails() throws Exception {
        when(pedidoService.createPedido(any(PedidoReabastecimientoRequestDTO.class)))
                .thenThrow(new RuntimeException("Proveedor no encontrado."));
        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequestDTO)))
                .andExpect(status().isBadRequest());
        verify(pedidoService, times(1)).createPedido(any(PedidoReabastecimientoRequestDTO.class));
    }

    @Test
    @DisplayName("PUT /api/pedidos/{id}")
    void shouldUpdatePedido() throws Exception {
        PedidoReabastecimientoRequestDTO updateRequestDTO = new PedidoReabastecimientoRequestDTO(
                1L,
                LocalDate.now().plusDays(10),
                "Completado",
                120,
                Collections.emptyList()
        );
        PedidoReabastecimientoResponseDTO updatedResponseDTO = new PedidoReabastecimientoResponseDTO(
                1L,
                1L,
                "Proveedor A",
                LocalDate.now(),
                LocalDate.now().plusDays(10),
                "Completado",
                120,
                Collections.emptyList()
        );
        when(pedidoService.updatePedido(eq(1L), any(PedidoReabastecimientoRequestDTO.class))).thenReturn(updatedResponseDTO);
        mockMvc.perform(put("/api/pedidos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedResponseDTO.getId()))
                .andExpect(jsonPath("$.estadoPedido").value(updatedResponseDTO.getEstadoPedido()));
        verify(pedidoService, times(1)).updatePedido(eq(1L), any(PedidoReabastecimientoRequestDTO.class));
    }

    @Test
    @DisplayName("PUT /api/pedidos/{id}")
    void shouldReturnNotFoundWhenUpdatePedidoNotFound() throws Exception {
        when(pedidoService.updatePedido(eq(99L), any(PedidoReabastecimientoRequestDTO.class)))
                .thenThrow(new RuntimeException("Pedido de reabastecimiento no encontrado con id: 99"));
        mockMvc.perform(put("/api/pedidos/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoRequestDTO)))
                .andExpect(status().isNotFound());
        verify(pedidoService, times(1)).updatePedido(eq(99L), any(PedidoReabastecimientoRequestDTO.class));
    }

    @Test
    @DisplayName("DELETE /api/pedidos/{id}")
    void shouldDeletePedido() throws Exception {
        doNothing().when(pedidoService).deletePedido(1L);
        mockMvc.perform(delete("/api/pedidos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(pedidoService, times(1)).deletePedido(1L);
    }

    @Test
    @DisplayName("DELETE /api/pedidos/{id}")
    void shouldReturnNotFoundWhenDeletePedidoNotFound() throws Exception {
        doThrow(new RuntimeException("Pedido no encontrado")).when(pedidoService).deletePedido(99L);
        mockMvc.perform(delete("/api/pedidos/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(pedidoService, times(1)).deletePedido(99L);
    }
}