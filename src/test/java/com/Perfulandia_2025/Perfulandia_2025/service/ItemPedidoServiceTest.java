package com.Perfulandia_2025.Perfulandia_2025.service;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ItemPedido;
import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import com.Perfulandia_2025.Perfulandia_2025.repository.ItemPedidoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.PedidoReabastecimientoRepository;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.ItemPedidoRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.ItemPedidoResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemPedidoServiceTest {

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @Mock
    private PedidoReabastecimientoRepository pedidoReabastecimientoRepository;

    @InjectMocks
    private ItemPedidoService itemPedidoService;

    private ItemPedido itemPedido;
    private ItemPedidoRequestDTO itemPedidoRequestDTO;
    private ItemPedidoResponseDTO itemPedidoResponseDTO;

    @BeforeEach
    void setUp() {
        itemPedido = new ItemPedido(1L, "Perfume A", 10, BigDecimal.valueOf(50.00), "PROD001", null);
        itemPedidoRequestDTO = new ItemPedidoRequestDTO("Perfume A", 10, BigDecimal.valueOf(50.00), "PROD001");
        itemPedidoResponseDTO = new ItemPedidoResponseDTO(1L, null, "Perfume A", 10, BigDecimal.valueOf(50.00), "PROD001");
    }

    @Test
    @DisplayName("Debería obtener todos los items de pedido")
    void shouldGetAllItemsPedido() {
        when(itemPedidoRepository.findAll()).thenReturn(Arrays.asList(itemPedido));
        List<ItemPedidoResponseDTO> result = itemPedidoService.getAllItemsPedido();
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getDescripcion()).isEqualTo(itemPedido.getDescripcion());
        verify(itemPedidoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería crear un nuevo item de pedido exitosamente")
    void shouldCreateItemPedidoSuccessfully() {
        when(itemPedidoRepository.save(any(ItemPedido.class))).thenReturn(itemPedido);
        ItemPedido createdItem = itemPedidoService.createItemPedido(itemPedidoRequestDTO);
        assertThat(createdItem).isNotNull();
        assertThat(createdItem.getDescripcion()).isEqualTo(itemPedidoRequestDTO.getDescripcion());
        assertThat(createdItem.getCantidadSolicitada()).isEqualTo(itemPedidoRequestDTO.getCantidadSolicitada());
        verify(itemPedidoRepository, times(1)).save(any(ItemPedido.class));
    }

    @Test
    @DisplayName("Debería obtener un item de pedido por ID")
    void shouldGetItemPedidoById() {
        when(itemPedidoRepository.findById(1L)).thenReturn(Optional.of(itemPedido));
        Optional<ItemPedidoResponseDTO> result = itemPedidoService.getItemPedidoById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getDescripcion()).isEqualTo(itemPedido.getDescripcion());
        verify(itemPedidoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería retornar Optional.empty si el item de pedido no se encuentra")
    void shouldReturnEmptyWhenItemPedidoNotFound() {
        when(itemPedidoRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<ItemPedidoResponseDTO> result = itemPedidoService.getItemPedidoById(99L);
        assertThat(result).isNotPresent();
        verify(itemPedidoRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debería actualizar un item de pedido existente")
    void shouldUpdateItemPedido() {
        ItemPedidoRequestDTO updateDTO = new ItemPedidoRequestDTO("Perfume B (Actualizado)", 15, BigDecimal.valueOf(55.00), "PROD002");
        ItemPedido existingItem = new ItemPedido(1L, "Perfume A", 10, BigDecimal.valueOf(50.00), "PROD001", null);
        ItemPedido updatedItem = new ItemPedido(1L, "Perfume B (Actualizado)", 15, BigDecimal.valueOf(55.00), "PROD002", null);
        when(itemPedidoRepository.findById(1L)).thenReturn(Optional.of(existingItem));
        when(itemPedidoRepository.save(any(ItemPedido.class))).thenReturn(updatedItem);
        ItemPedidoResponseDTO result = itemPedidoService.updateItemPedido(1L, updateDTO);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getDescripcion()).isEqualTo("Perfume B (Actualizado)");
        assertThat(result.getCantidadSolicitada()).isEqualTo(15);
        verify(itemPedidoRepository, times(1)).findById(1L);
        verify(itemPedidoRepository, times(1)).save(any(ItemPedido.class));
    }

    @Test
    @DisplayName("Debería lanzar RuntimeException si el item a actualizar no se encuentra")
    void shouldThrowExceptionWhenUpdatingNonExistentItemPedido() {
        ItemPedidoRequestDTO updateDTO = new ItemPedidoRequestDTO("Perfume B", 15, BigDecimal.valueOf(55.00), "PROD002");
        when(itemPedidoRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                itemPedidoService.updateItemPedido(99L, updateDTO)
        );
        assertThat(exception.getMessage()).contains("Item de pedido no encontrado con id: 99");
        verify(itemPedidoRepository, times(1)).findById(99L);
        verify(itemPedidoRepository, never()).save(any(ItemPedido.class));
    }

    @Test
    @DisplayName("Debería eliminar un item de pedido exitosamente")
    void shouldDeleteItemPedidoSuccessfully() {
        doNothing().when(itemPedidoRepository).deleteById(1L);
        itemPedidoService.deleteItemPedido(1L);
        verify(itemPedidoRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Debería obtener items de pedido por ID de PedidoReabastecimiento")
    void shouldGetItemsByPedidoReabastecimientoId() {
        PedidoReabastecimiento pedido = new PedidoReabastecimiento(100L, null, LocalDate.now(), LocalDate.now().plusDays(5), "Pendiente", 100, null);
        ItemPedido item1 = new ItemPedido(1L, "Perfume A", 5, BigDecimal.valueOf(20.00), "P001", pedido);
        ItemPedido item2 = new ItemPedido(2L, "Perfume B", 3, BigDecimal.valueOf(30.00), "P002", pedido);
        when(itemPedidoRepository.findByPedidoReabastecimientoId(100L)).thenReturn(Arrays.asList(item1, item2));
        List<ItemPedidoResponseDTO> result = itemPedidoService.getItemsByPedidoReabastecimientoId(100L);
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getDescripcion()).isEqualTo("Perfume A");
        assertThat(result.get(1).getDescripcion()).isEqualTo("Perfume B");
        verify(itemPedidoRepository, times(1)).findByPedidoReabastecimientoId(100L);
    }

    @Test
    @DisplayName("Debería convertir ItemPedidoRequestDTO a ItemPedido entity")
    void shouldConvertRequestDTOToEntity() {
        ItemPedido result = itemPedidoService.convertToEntity(itemPedidoRequestDTO);
        assertThat(result).isNotNull();
        assertThat(result.getDescripcion()).isEqualTo(itemPedidoRequestDTO.getDescripcion());
        assertThat(result.getCantidadSolicitada()).isEqualTo(itemPedidoRequestDTO.getCantidadSolicitada());
        assertThat(result.getPrecioUnitario()).isEqualTo(itemPedidoRequestDTO.getPrecioUnitario());
        assertThat(result.getCodigoProducto()).isEqualTo(itemPedidoRequestDTO.getCodigoProducto());
    }

    @Test
    @DisplayName("Debería convertir ItemPedido entity a ItemPedidoResponseDTO")
    void shouldConvertEntityToResponseDTO() {
        ItemPedidoResponseDTO result = itemPedidoService.convertToResponseDTO(itemPedido);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(itemPedido.getId());
        assertThat(result.getDescripcion()).isEqualTo(itemPedido.getDescripcion());
        assertThat(result.getCantidadSolicitada()).isEqualTo(itemPedido.getCantidadSolicitada());
        assertThat(result.getPrecioUnitario()).isEqualTo(itemPedido.getPrecioUnitario());
        assertThat(result.getCodigoProducto()).isEqualTo(itemPedido.getCodigoProducto());
    }
}
