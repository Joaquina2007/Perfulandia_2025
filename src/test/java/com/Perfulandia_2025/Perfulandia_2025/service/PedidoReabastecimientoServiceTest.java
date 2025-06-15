package com.Perfulandia_2025.Perfulandia_2025.service;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ItemPedido;
import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import com.Perfulandia_2025.Perfulandia_2025.repository.ItemPedidoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.PedidoReabastecimientoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.ProveedorRepository;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.ItemPedidoRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.PedidoReabastecimientoRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.ItemPedidoResponseDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.PedidoReabastecimientoResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoReabastecimientoServiceTest {

    @Mock
    private PedidoReabastecimientoRepository pedidoReabastecimientoRepository;

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private ItemPedidoRepository itemPedidoRepository;

    @InjectMocks
    private PedidoReabastecimientoService pedidoReabastecimientoService;

    private PedidoReabastecimiento pedido;
    private PedidoReabastecimientoRequestDTO pedidoRequestDTO;
    private PedidoReabastecimientoResponseDTO pedidoResponseDTO;
    private Proveedor proveedor;
    private ItemPedido itemPedido1;
    private ItemPedidoRequestDTO itemPedidoRequestDTO1;

    @BeforeEach
    void setUp() {
        proveedor = new Proveedor(1L, "Proveedor Test", "Dir", 123, "IDN", LocalDate.now(), true);
        itemPedido1 = new ItemPedido(10L, "Perfume A", 5, BigDecimal.valueOf(50.00), "PA001", null);
        itemPedidoRequestDTO1 = new ItemPedidoRequestDTO("Perfume A", 5, BigDecimal.valueOf(50.00), "PA001");
        List<ItemPedido> items = new ArrayList<>();
        items.add(itemPedido1);
        pedido = new PedidoReabastecimiento(1L, proveedor, LocalDate.now(), LocalDate.now().plusDays(7), "Pendiente", 250, items);
        pedidoRequestDTO = new PedidoReabastecimientoRequestDTO(
                proveedor.getId(),
                LocalDate.now().plusDays(7),
                "Pendiente",
                250,
                Arrays.asList(itemPedidoRequestDTO1)
        );
        pedidoResponseDTO = new PedidoReabastecimientoResponseDTO(
                1L,
                proveedor.getId(),
                proveedor.getNombre(),
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                "Pendiente",
                250,
                Arrays.asList(new ItemPedidoResponseDTO(10L, pedido, "Perfume A", 5, BigDecimal.valueOf(50.00), "PA001"))
        );
    }

    @Test
    @DisplayName("Debería obtener todos los pedidos de reabastecimiento")
    void shouldGetAllPedidos() {
        when(pedidoReabastecimientoRepository.findAll()).thenReturn(Arrays.asList(pedido));
        List<PedidoReabastecimientoResponseDTO> result = pedidoReabastecimientoService.getAllPedidos();
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(pedido.getId());
        verify(pedidoReabastecimientoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener un pedido de reabastecimiento por ID")
    void shouldGetPedidoById() {
        when(pedidoReabastecimientoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        Optional<PedidoReabastecimientoResponseDTO> result = pedidoReabastecimientoService.getPedidoById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(pedidoReabastecimientoRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería retornar Optional.empty si el pedido no se encuentra")
    void shouldReturnEmptyWhenPedidoNotFound() {
        when(pedidoReabastecimientoRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<PedidoReabastecimientoResponseDTO> result = pedidoReabastecimientoService.getPedidoById(99L);
        assertThat(result).isNotPresent();
        verify(pedidoReabastecimientoRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debería crear un nuevo pedido de reabastecimiento exitosamente")
    void shouldCreatePedidoSuccessfully() {
        when(proveedorRepository.findById(proveedor.getId())).thenReturn(Optional.of(proveedor));
        when(pedidoReabastecimientoRepository.save(any(PedidoReabastecimiento.class))).thenReturn(pedido);
        when(itemPedidoRepository.save(any(ItemPedido.class))).thenReturn(itemPedido1);
        PedidoReabastecimientoResponseDTO createdPedido = pedidoReabastecimientoService.createPedido(pedidoRequestDTO);
        assertThat(createdPedido).isNotNull();
        assertThat(createdPedido.getProveedor()).isEqualTo(proveedor.getId());
        assertThat(createdPedido.getEstadoPedido()).isEqualTo("Pendiente");
        assertThat(createdPedido.getItemPedidos()).hasSize(1);
        verify(proveedorRepository, times(1)).findById(proveedor.getId());
        verify(pedidoReabastecimientoRepository, times(1)).save(any(PedidoReabastecimiento.class));
        verify(itemPedidoRepository, times(1)).save(any(ItemPedido.class));
    }

    @Test
    @DisplayName("Debería lanzar RuntimeException al crear pedido si el proveedor no es encontrado")
    void shouldThrowExceptionWhenCreatingPedidoIfProveedorNotFound() {
        when(proveedorRepository.findById(anyLong())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                pedidoReabastecimientoService.createPedido(pedidoRequestDTO)
        );
        assertThat(exception.getMessage()).contains("Proveedor no encontrado con ID:");
        verify(proveedorRepository, times(1)).findById(anyLong());
        verify(pedidoReabastecimientoRepository, never()).save(any(PedidoReabastecimiento.class));
    }

    @Test
    @DisplayName("Debería obtener pedidos por ID de proveedor")
    void shouldGetPedidosByProveedor() {
        when(pedidoReabastecimientoRepository.findByProveedorId(proveedor.getId())).thenReturn(Arrays.asList(pedido));
        List<PedidoReabastecimientoResponseDTO> result = pedidoReabastecimientoService.getPedidosByProveedor(proveedor.getId());
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getProveedor()).isEqualTo(proveedor.getId());
        verify(pedidoReabastecimientoRepository, times(1)).findByProveedorId(proveedor.getId());
    }

    @Test
    @DisplayName("Debería actualizar un pedido de reabastecimiento existente")
    void shouldUpdatePedido() {
        PedidoReabastecimientoRequestDTO updateDTO = new PedidoReabastecimientoRequestDTO(
                proveedor.getId(),
                LocalDate.now().plusDays(10),
                "Completado",
                300,
                Arrays.asList(new ItemPedidoRequestDTO("Nuevo Item", 1, BigDecimal.valueOf(100), "NI001"))
        );
        PedidoReabastecimiento existingPedido = new PedidoReabastecimiento(1L, proveedor, LocalDate.now(), LocalDate.now().plusDays(7), "Pendiente", 250, new ArrayList<>());
        when(pedidoReabastecimientoRepository.findById(1L)).thenReturn(Optional.of(existingPedido));
        when(pedidoReabastecimientoRepository.save(any(PedidoReabastecimiento.class))).thenReturn(pedido);
        when(itemPedidoRepository.save(any(ItemPedido.class))).thenReturn(new ItemPedido());
        PedidoReabastecimientoResponseDTO result = pedidoReabastecimientoService.updatePedido(1L, updateDTO);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEstadoPedido()).isEqualTo("Completado");
        assertThat(result.getTotalPedido()).isEqualTo(300);
        assertThat(result.getItemPedidos()).hasSize(1);
        verify(pedidoReabastecimientoRepository, times(1)).findById(1L);
        verify(itemPedidoRepository, times(1)).deleteAll(anyList());
        verify(itemPedidoRepository, times(1)).save(any(ItemPedido.class));
        verify(pedidoReabastecimientoRepository, times(1)).save(any(PedidoReabastecimiento.class));
    }

    @Test
    @DisplayName("Debería lanzar RuntimeException si el pedido a actualizar no se encuentra")
    void shouldThrowExceptionWhenUpdatingNonExistentPedido() {
        when(pedidoReabastecimientoRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                pedidoReabastecimientoService.updatePedido(99L, pedidoRequestDTO)
        );
        assertThat(exception.getMessage()).contains("Pedido de reabastecimiento no encontrado con id: 99");
        verify(pedidoReabastecimientoRepository, times(1)).findById(99L);
        verify(pedidoReabastecimientoRepository, never()).save(any(PedidoReabastecimiento.class));
    }

    @Test
    @DisplayName("Debería eliminar un pedido de reabastecimiento exitosamente")
    void shouldDeletePedidoSuccessfully() {
        when(itemPedidoRepository.findByPedidoReabastecimientoId(1L)).thenReturn(Arrays.asList(itemPedido1));
        doNothing().when(itemPedidoRepository).deleteAll(anyList());
        doNothing().when(pedidoReabastecimientoRepository).deleteById(1L);
        pedidoReabastecimientoService.deletePedido(1L);
        verify(itemPedidoRepository, times(1)).findByPedidoReabastecimientoId(1L);
        verify(itemPedidoRepository, times(1)).deleteAll(anyList());
        verify(pedidoReabastecimientoRepository, times(1)).deleteById(1L);
    }
}
