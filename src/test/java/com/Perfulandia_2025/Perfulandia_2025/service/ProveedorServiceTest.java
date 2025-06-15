package com.Perfulandia_2025.Perfulandia_2025.service;

import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import com.Perfulandia_2025.Perfulandia_2025.repository.ProveedorRepository;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.ProveedorRequestDTO;
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
class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @InjectMocks
    private ProveedorService proveedorService;

    private Proveedor proveedor;
    private ProveedorRequestDTO proveedorRequestDTO;

    @BeforeEach
    void setUp() {
        proveedor = new Proveedor(1L, "Proveedor A", "Direccion 1", 123456789, "IDN-001", LocalDate.now(), true);
        proveedorRequestDTO = new ProveedorRequestDTO("Proveedor A", "Direccion 1", 123456789, "IDN-001", true);
    }

    @Test
    @DisplayName("Debería obtener todos los proveedores")
    void shouldGetAllProveedores() {
        when(proveedorRepository.findAll()).thenReturn(Arrays.asList(proveedor));
        List<Proveedor> result = proveedorService.getAllProveedores();
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNombre()).isEqualTo(proveedor.getNombre());
        verify(proveedorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería crear un nuevo proveedor exitosamente")
    void shouldCreateProveedorSuccessfully() {
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedor);
        Proveedor createdProveedor = proveedorService.createProveedor(proveedorRequestDTO);
        assertThat(createdProveedor).isNotNull();
        assertThat(createdProveedor.getNombre()).isEqualTo(proveedorRequestDTO.getNombre());
        assertThat(createdProveedor.getIdentificacion()).isEqualTo(proveedorRequestDTO.getIdentificacion());
        verify(proveedorRepository, times(1)).save(any(Proveedor.class));
    }

    @Test
    @DisplayName("Debería actualizar un proveedor existente")
    void shouldUpdateProveedor() {
        ProveedorRequestDTO updateDTO = new ProveedorRequestDTO("Proveedor B (Actualizado)", "Nueva Direccion", 987654321, "IDN-002", false);
        Proveedor existingProveedor = new Proveedor(1L, "Proveedor A", "Direccion 1", 123456789, "IDN-001", LocalDate.now(), true);
        Proveedor updatedProveedor = new Proveedor(1L, "Proveedor B (Actualizado)", "Nueva Direccion", 987654321, "IDN-002", LocalDate.now(), false);
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(existingProveedor));
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(updatedProveedor);
        Proveedor result = proveedorService.updateProveedor(1L, updateDTO);
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNombre()).isEqualTo("Proveedor B (Actualizado)");
        assertThat(result.getTelefono()).isEqualTo(987654321);
        verify(proveedorRepository, times(1)).findById(1L);
        verify(proveedorRepository, times(1)).save(any(Proveedor.class));
    }

    @Test
    @DisplayName("Debería lanzar RuntimeException si el proveedor a actualizar no se encuentra")
    void shouldThrowExceptionWhenUpdatingNonExistentProveedor() {
        ProveedorRequestDTO updateDTO = new ProveedorRequestDTO("Proveedor Inexistente", "Dir", 123, "IDN-999", true);
        when(proveedorRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () ->
                proveedorService.updateProveedor(99L, updateDTO)
        );
        verify(proveedorRepository, times(1)).findById(99L);
        verify(proveedorRepository, never()).save(any(Proveedor.class));
    }

    @Test
    @DisplayName("Debería eliminar un proveedor exitosamente")
    void shouldDeleteProveedorSuccessfully() {
        doNothing().when(proveedorRepository).deleteById(1L);
        proveedorService.deleteProveedor(1L);
        verify(proveedorRepository, times(1)).deleteById(1L);
    }
}
