package com.Perfulandia_2025.Perfulandia_2025.service;

import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import com.Perfulandia_2025.Perfulandia_2025.modelo.RecepcionMercancia;
import com.Perfulandia_2025.Perfulandia_2025.repository.PedidoReabastecimientoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.RecepcionMercanciaRepository;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.RecepcionMercanciaRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.RecepcionMercanciaResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecepcionMercanciaServiceTest {

    @Mock
    private RecepcionMercanciaRepository recepcionMercanciaRepository;

    @Mock
    private PedidoReabastecimientoRepository pedidoReabastecimientoRepository;

    @InjectMocks
    private RecepcionMercanciaService recepcionMercanciaService;

    private RecepcionMercancia recepcion;
    private RecepcionMercanciaRequestDTO recepcionRequestDTO;
    private RecepcionMercanciaResponseDTO recepcionResponseDTO;
    private PedidoReabastecimiento pedido;

    @BeforeEach
    void setUp() {
        pedido = new PedidoReabastecimiento(1L, null, LocalDate.now(), LocalDate.now().plusDays(5), "Enviado", 100, null);
        recepcion = new RecepcionMercancia(1L, pedido, LocalDate.now(), "Juan Pérez", "Todo correcto", "Completada");
        recepcionRequestDTO = new RecepcionMercanciaRequestDTO("Juan Pérez", "Todo correcto", "Completada");
        recepcionResponseDTO = new RecepcionMercanciaResponseDTO(1L, pedido, LocalDate.now(), "Juan Pérez", "Todo correcto", "Completada");
    }

    @Test
    @DisplayName("Debería obtener todas las recepciones de mercancía")
    void shouldGetAllRecepciones() {
        when(recepcionMercanciaRepository.findAll()).thenReturn(Arrays.asList(recepcion));
        List<RecepcionMercanciaResponseDTO> result = recepcionMercanciaService.getAllRecepciones();
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRecibidoPor()).isEqualTo(recepcion.getRecibidoPor());
        verify(recepcionMercanciaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener una recepción de mercancía por ID")
    void shouldGetRecepcionById() {
        when(recepcionMercanciaRepository.findById(1L)).thenReturn(Optional.of(recepcion));
        Optional<RecepcionMercanciaResponseDTO> result = recepcionMercanciaService.getRecepcionById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(recepcionMercanciaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería retornar Optional.empty si la recepción no se encuentra")
    void shouldReturnEmptyWhenRecepcionNotFound() {
        when(recepcionMercanciaRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<RecepcionMercanciaResponseDTO> result = recepcionMercanciaService.getRecepcionById(99L);
        assertThat(result).isNotPresent();
        verify(recepcionMercanciaRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Debería registrar una recepción de mercancía exitosamente")
    void shouldRegisterRecepcionSuccessfully() {
        when(pedidoReabastecimientoRepository.findById(pedido.getId())).thenReturn(Optional.of(pedido));
        when(recepcionMercanciaRepository.save(any(RecepcionMercancia.class))).thenReturn(recepcion);
        RecepcionMercanciaResponseDTO createdRecepcion = recepcionMercanciaService.registrarRecepcion(pedido.getId(), recepcionRequestDTO);
        assertThat(createdRecepcion).isNotNull();
        assertThat(createdRecepcion.getRecibidoPor()).isEqualTo(recepcionRequestDTO.getRecibidoPor());
        assertThat(createdRecepcion.getPedidoReabastecimiento().getId()).isEqualTo(pedido.getId());
        verify(pedidoReabastecimientoRepository, times(1)).findById(pedido.getId());
        verify(recepcionMercanciaRepository, times(1)).save(any(RecepcionMercancia.class));
    }

    @Test
    @DisplayName("Debería lanzar IllegalArgumentException si 'recibidoPor' es nulo o vacío")
    void shouldThrowIllegalArgumentExceptionWhenRecibidoPorIsNull() {
        recepcionRequestDTO.setRecibidoPor(null);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                recepcionMercanciaService.registrarRecepcion(pedido.getId(), recepcionRequestDTO)
        );
        assertThat(exception.getMessage()).contains("La persona que recibió la mercancía es obligatoria.");
        verify(pedidoReabastecimientoRepository, never()).findById(anyLong());
        verify(recepcionMercanciaRepository, never()).save(any(RecepcionMercancia.class));
    }

    @Test
    @DisplayName("Debería lanzar RuntimeException al registrar si el pedido no es encontrado")
    void shouldThrowExceptionWhenRegisteringIfPedidoNotFound() {
        when(pedidoReabastecimientoRepository.findById(anyLong())).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                recepcionMercanciaService.registrarRecepcion(99L, recepcionRequestDTO)
        );
        assertThat(exception.getMessage()).contains("Pedido de reabastecimiento no encontrado con id: 99");
        verify(pedidoReabastecimientoRepository, times(1)).findById(99L);
        verify(recepcionMercanciaRepository, never()).save(any(RecepcionMercancia.class));
    }

    @Test
    @DisplayName("Debería actualizar una recepción de mercancía existente")
    void shouldUpdateRecepcion() {
        RecepcionMercanciaRequestDTO updateDTO = new RecepcionMercanciaRequestDTO("Ana Garcés", "Con detalles", "Parcialmente Recibida");
        RecepcionMercancia existingRecepcion = new RecepcionMercancia(1L, pedido, LocalDate.now(), "Juan Pérez", "Todo correcto", "Completada");
        RecepcionMercancia updatedRecepcion = new RecepcionMercancia(1L, pedido, LocalDate.now(), "Ana Garcés", "Con detalles", "Parcialmente Recibida");
        when(recepcionMercanciaRepository.findById(1L)).thenReturn(Optional.of(existingRecepcion));
        when(recepcionMercanciaRepository.save(any(RecepcionMercancia.class))).thenReturn(updatedRecepcion);
        RecepcionMercanciaResponseDTO result = recepcionMercanciaService.updateRecepcion(1L, updateDTO);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getRecibidoPor()).isEqualTo("Ana Garcés");
        assertThat(result.getNotasRecepcion()).isEqualTo("Con detalles");
        verify(recepcionMercanciaRepository, times(1)).findById(1L);
        verify(recepcionMercanciaRepository, times(1)).save(any(RecepcionMercancia.class));
    }

    @Test
    @DisplayName("Debería lanzar RuntimeException si la recepción a actualizar no se encuentra")
    void shouldThrowExceptionWhenUpdatingNonExistentRecepcion() {
        when(recepcionMercanciaRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                recepcionMercanciaService.updateRecepcion(99L, recepcionRequestDTO)
        );
        assertThat(exception.getMessage()).contains("Recepción de mercancía no encontrada con id: 99");
        verify(recepcionMercanciaRepository, times(1)).findById(99L);
        verify(recepcionMercanciaRepository, never()).save(any(RecepcionMercancia.class));
    }

    @Test
    @DisplayName("Debería eliminar una recepción de mercancía exitosamente")
    void shouldDeleteRecepcionSuccessfully() {
        doNothing().when(recepcionMercanciaRepository).deleteById(1L);
        recepcionMercanciaService.deleteRecepcion(1L);
        verify(recepcionMercanciaRepository, times(1)).deleteById(1L);
    }
}
