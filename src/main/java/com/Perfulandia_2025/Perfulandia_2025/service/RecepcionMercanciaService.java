package com.Perfulandia_2025.Perfulandia_2025.service;

import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import com.Perfulandia_2025.Perfulandia_2025.modelo.RecepcionMercancia;
import com.Perfulandia_2025.Perfulandia_2025.repository.PedidoReabastecimientoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.RecepcionMercanciaRepository; // ¡NUEVA IMPORTACIÓN!
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.RecepcionMercanciaRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.RecepcionMercanciaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecepcionMercanciaService {

    @Autowired
    private PedidoReabastecimientoRepository pedidoReabastecimientoRepository;

    @Autowired
    private RecepcionMercanciaRepository recepcionMercanciaRepository;

    private RecepcionMercancia convertToEntity(RecepcionMercanciaRequestDTO dto) {
        RecepcionMercancia recepcion = new RecepcionMercancia();
        recepcion.setRecibidoPor(dto.getRecibidoPor());
        recepcion.setNotasRecepcion(dto.getNotasRecepcion());
        recepcion.setEstadoRecepcion(dto.getEstadoRecepcion());
        return recepcion;
    }

    private RecepcionMercanciaResponseDTO convertToResponseDTO(RecepcionMercancia recepcion) {
        RecepcionMercanciaResponseDTO dto = new RecepcionMercanciaResponseDTO();
        dto.setId(recepcion.getId());
        dto.setFechaRecepcion(recepcion.getFechaRecepcion());
        dto.setRecibidoPor(recepcion.getRecibidoPor());
        dto.setNotasRecepcion(recepcion.getNotasRecepcion());
        dto.setEstadoRecepcion(recepcion.getEstadoRecepcion());
        return dto;
    }

    public Optional<RecepcionMercanciaResponseDTO> getRecepcionById(Long id) {
        return recepcionMercanciaRepository.findById(id).map(this::convertToResponseDTO);
    }

    @Transactional
    public RecepcionMercanciaResponseDTO registrarRecepcion(Long pedidoId, RecepcionMercanciaRequestDTO requestDTO) {
        if (requestDTO.getRecibidoPor() == null || requestDTO.getRecibidoPor().trim().isEmpty()) {
            throw new IllegalArgumentException("La persona que recibió la mercancía es obligatoria.");
        }
        PedidoReabastecimiento pedido = pedidoReabastecimientoRepository.findById(pedidoId).orElseThrow(() -> new RuntimeException("Pedido de reabastecimiento no encontrado con id: " + pedidoId));
        RecepcionMercancia recepcion = convertToEntity(requestDTO);
        recepcion.setPedidoReabastecimiento(pedido);
        recepcion.setFechaRecepcion(LocalDate.now());
        RecepcionMercancia savedRecepcion = recepcionMercanciaRepository.save(recepcion);
        return convertToResponseDTO(savedRecepcion);
    }

    public RecepcionMercanciaResponseDTO updateRecepcion(Long id, RecepcionMercanciaRequestDTO requestDTO) {
        RecepcionMercancia recepcion = recepcionMercanciaRepository.findById(id).orElseThrow(() -> new RuntimeException("Recepción de mercancía no encontrada con id: " + id));
        if (requestDTO.getRecibidoPor() != null) recepcion.setRecibidoPor(requestDTO.getRecibidoPor());
        if (requestDTO.getNotasRecepcion() != null) recepcion.setNotasRecepcion(requestDTO.getNotasRecepcion());
        if (requestDTO.getEstadoRecepcion() != null) recepcion.setEstadoRecepcion(requestDTO.getEstadoRecepcion());
        RecepcionMercancia updatedRecepcion = recepcionMercanciaRepository.save(recepcion);
        return convertToResponseDTO(updatedRecepcion);
    }

    public void deleteRecepcion(Long id) {
        recepcionMercanciaRepository.deleteById(id);
    }


    public List<RecepcionMercanciaResponseDTO> getAllRecepciones() {
        return recepcionMercanciaRepository.findAll().stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }
}
