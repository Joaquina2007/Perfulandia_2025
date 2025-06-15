package com.Perfulandia_2025.Perfulandia_2025.controller;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ItemPedido;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.ItemPedidoRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.ItemPedidoResponseDTO;
import com.Perfulandia_2025.Perfulandia_2025.service.ItemPedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemPedidoController.class)
class ItemPedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemPedidoService itemPedidoService;

    private ItemPedido itemPedido;
    private ItemPedidoRequestDTO itemPedidoRequestDTO;
    private ItemPedidoResponseDTO itemPedidoResponseDTO;

    @BeforeEach
    void setUp() {
        itemPedido = new ItemPedido(1L, "Perfume de Rosas", 5, BigDecimal.valueOf(100.00), "PROD001", null);
        itemPedidoRequestDTO = new ItemPedidoRequestDTO("Perfume de Rosas", 5, BigDecimal.valueOf(100.00), "PROD001");
        itemPedidoResponseDTO = new ItemPedidoResponseDTO(1L, null, "Perfume de Rosas", 5, BigDecimal.valueOf(100.00), "PROD001");
    }

    @Test
    @DisplayName("GET /api/items-pedido - Debería retornar todos los items de pedido")
    void shouldGetAllItemsPedido() throws Exception {
        when(itemPedidoService.getAllItemsPedido()).thenReturn(Arrays.asList(itemPedidoResponseDTO));
        mockMvc.perform(get("/api/items-pedido")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(itemPedidoResponseDTO.getId()))
                .andExpect(jsonPath("$[0].descripcion").value(itemPedidoResponseDTO.getDescripcion()));
        verify(itemPedidoService, times(1)).getAllItemsPedido();
    }

    @Test
    @DisplayName("GET /api/items-pedido/{id}")
    void shouldGetItemPedidoById() throws Exception {
        when(itemPedidoService.getItemPedidoById(1L)).thenReturn(Optional.of(itemPedidoResponseDTO));
        mockMvc.perform(get("/api/items-pedido/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemPedidoResponseDTO.getId()))
                .andExpect(jsonPath("$.descripcion").value(itemPedidoResponseDTO.getDescripcion()));
        verify(itemPedidoService, times(1)).getItemPedidoById(1L);
    }

    @Test
    @DisplayName("GET /api/items-pedido/{id}")
    void shouldReturnNotFoundWhenGetItemPedidoByIdNotFound() throws Exception {
        when(itemPedidoService.getItemPedidoById(99L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/items-pedido/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(itemPedidoService, times(1)).getItemPedidoById(99L);
    }

    @Test
    @DisplayName("POST /api/items-pedido")
    void shouldCreateItemPedido() throws Exception {
        when(itemPedidoService.createItemPedido(any(com.Perfulandia_2025.Perfulandia_2025.requestDTO.ProveedorRequestDTO.class))).thenReturn(itemPedido);
        mockMvc.perform(post("/api/items-pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemPedidoRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(itemPedido.getId()))
                .andExpect(jsonPath("$.descripcion").value(itemPedido.getDescripcion()));
        verify(itemPedidoService, times(1)).createItemPedido(any(com.Perfulandia_2025.Perfulandia_2025.requestDTO.ProveedorRequestDTO.class));
    }

    @Test
    @DisplayName("POST /api/items-pedido")
    void shouldReturnBadRequestWhenCreateItemPedidoInvalid() throws Exception {
        when(itemPedidoService.createItemPedido(any(com.Perfulandia_2025.Perfulandia_2025.requestDTO.ProveedorRequestDTO.class)))
                .thenThrow(new IllegalArgumentException("Descripción es requerida"));
        mockMvc.perform(post("/api/items-pedido")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new ItemPedidoRequestDTO(null, 1, BigDecimal.TEN, "COD001"))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Descripción es requerida"));
        verify(itemPedidoService, times(1)).createItemPedido(any(com.Perfulandia_2025.Perfulandia_2025.requestDTO.ProveedorRequestDTO.class));
    }

    @Test
    @DisplayName("PUT /api/items-pedido/{id}")
    void shouldUpdateItemPedido() throws Exception {
        ItemPedidoRequestDTO updatedRequestDTO = new ItemPedidoRequestDTO("Perfume de Rosas (Actualizado)", 6, BigDecimal.valueOf(110.00), "PROD001");
        ItemPedidoResponseDTO updatedResponseDTO = new ItemPedidoResponseDTO(1L, null, "Perfume de Rosas (Actualizado)", 6, BigDecimal.valueOf(110.00), "PROD001");
        when(itemPedidoService.updateItemPedido(eq(1L), any(ItemPedidoRequestDTO.class))).thenReturn(updatedResponseDTO);
        mockMvc.perform(put("/api/items-pedido/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedResponseDTO.getId()))
                .andExpect(jsonPath("$.descripcion").value(updatedResponseDTO.getDescripcion()));
        verify(itemPedidoService, times(1)).updateItemPedido(eq(1L), any(ItemPedidoRequestDTO.class));
    }

    @Test
    @DisplayName("PUT /api/items-pedido/{id}")
    void shouldReturnNotFoundWhenUpdateItemPedidoNotFound() throws Exception {
        ItemPedidoRequestDTO updatedRequestDTO = new ItemPedidoRequestDTO("Perfume inexistente", 1, BigDecimal.TEN, "PROD999");
        when(itemPedidoService.updateItemPedido(eq(99L), any(ItemPedidoRequestDTO.class)))
                .thenThrow(new RuntimeException("Item de pedido no encontrado con id: 99"));
        mockMvc.perform(put("/api/items-pedido/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRequestDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Item de pedido no encontrado con id: 99"));
        verify(itemPedidoService, times(1)).updateItemPedido(eq(99L), any(ItemPedidoRequestDTO.class));
    }

    @Test
    @DisplayName("DELETE /api/items-pedido/{id}")
    void shouldDeleteItemPedido() throws Exception {
        doNothing().when(itemPedidoService).deleteItemPedido(1L);
        mockMvc.perform(delete("/api/items-pedido/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(itemPedidoService, times(1)).deleteItemPedido(1L);
    }

    @Test
    @DisplayName("DELETE /api/items-pedido/{id}")
    void shouldReturnNotFoundWhenDeleteItemPedidoNotFound() throws Exception {
        doThrow(new RuntimeException("Item no encontrado")).when(itemPedidoService).deleteItemPedido(99L);
        mockMvc.perform(delete("/api/items-pedido/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(itemPedidoService, times(1)).deleteItemPedido(99L);
    }

    @Test
    @DisplayName("GET /api/items-pedido/pedido/{pedidoId}o")
    void shouldGetItemsByPedido() throws Exception {
        Long pedidoId = 100L;
        ItemPedidoResponseDTO item1 = new ItemPedidoResponseDTO(1L, null, "Item 1", 2, BigDecimal.TEN, "COD1");
        ItemPedidoResponseDTO item2 = new ItemPedidoResponseDTO(2L, null, "Item 2", 3, BigDecimal.valueOf(20), "COD2");
        when(itemPedidoService.getItemsByPedidoReabastecimientoId(pedidoId)).thenReturn(Arrays.asList(item1, item2));
        mockMvc.perform(get("/api/items-pedido/pedido/{pedidoId}", pedidoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(item1.getId()))
                .andExpect(jsonPath("$[1].id").value(item2.getId()));
        verify(itemPedidoService, times(1)).getItemsByPedidoReabastecimientoId(pedidoId);
    }
}
