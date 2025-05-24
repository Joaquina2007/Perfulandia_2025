package com.Perfulandia_2025.Perfulandia_2025.service;

import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import com.Perfulandia_2025.Perfulandia_2025.modelo.RecepcionMercancia;
import com.Perfulandia_2025.Perfulandia_2025.repository.PedidoReabastecimientoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.RecepcionMercanciaRepository; // ¡NUEVA IMPORTACIÓN!
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RecepcionMercanciaService {

    @Autowired
    private PedidoReabastecimientoRepository pedidoReabastecimientoRepository;

    @Autowired
    private RecepcionMercanciaRepository recepcionMercanciaRepository;

    public Optional<RecepcionMercancia> getRecepcionById(Long id){
        return recepcionMercanciaRepository.findById(id);
    }

    @Transactional
    public RecepcionMercancia registrarRecepcion(Long pedidoId, RecepcionMercancia recepcion) {
        PedidoReabastecimiento pedido = pedidoReabastecimientoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido de reabastecimiento no encontrado con id: " + pedidoId));

        recepcion.setPedidoReabastecimiento(pedido);
        recepcion.setFechaRecepcion(LocalDate.now());

        if (recepcion.getEstadoRecepcion() == null || recepcion.getEstadoRecepcion().isEmpty()) {
            recepcion.setEstadoRecepcion("Pendiente de validación");
        }
        RecepcionMercancia recepcionGuardada = recepcionMercanciaRepository.save(recepcion);
        return recepcionGuardada;
    }

    public RecepcionMercancia updateRecepcion(Long id, RecepcionMercancia recepcionDetails) {
        RecepcionMercancia recepcion = recepcionMercanciaRepository.findById(id).orElseThrow(() -> new RuntimeException("Recepción de mercancía no encontrada con id: " + id));

        recepcion.setPedidoReabastecimiento(recepcionDetails.getPedidoReabastecimiento());
        recepcion.setRecibidoPor(recepcionDetails.getRecibidoPor());
        recepcion.setNotasRecepcion(recepcionDetails.getNotasRecepcion());
        recepcion.setEstadoRecepcion(recepcionDetails.getEstadoRecepcion());
        return recepcionMercanciaRepository.save(recepcion);
    }

    public void deleteRecepcion(Long id) {
        recepcionMercanciaRepository.deleteById(id);
    }


    public List<RecepcionMercancia> getAllRecepciones() {
        return recepcionMercanciaRepository.findAll();
    }
}
