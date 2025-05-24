package com.Perfulandia_2025.Perfulandia_2025.controller;

import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.PedidoReabastecimientoRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.PedidoReabastecimientoResponseDTO;
import com.Perfulandia_2025.Perfulandia_2025.service.PedidoReabastecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoReabastecimientoController {

    private final PedidoReabastecimientoService pedidoService;

    @Autowired
    public PedidoReabastecimientoController(PedidoReabastecimientoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping
    public ResponseEntity<List<PedidoReabastecimientoResponseDTO>> getAllPedidos(){
        List<PedidoReabastecimientoResponseDTO> pedidos = pedidoService.getAllPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoReabastecimientoResponseDTO> getPedidoById(@PathVariable Long id) {
        return pedidoService.getPedidoById(id).map(pedidoDTO -> new ResponseEntity<>(pedidoDTO, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<PedidoReabastecimientoResponseDTO>> getPedidosByProveedor(@PathVariable Long proveedorId) {
        List<PedidoReabastecimientoResponseDTO> pedidos = pedidoService.getPedidosByProveedor(proveedorId);
        if (pedidos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PedidoReabastecimientoResponseDTO> createPedido(@RequestBody PedidoReabastecimientoRequestDTO pedidoRequestDTO) {
        try {
            PedidoReabastecimientoResponseDTO newPedido = pedidoService.createPedido(pedidoRequestDTO);
            return new ResponseEntity<>(newPedido, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            System.err.println("Error al crear pedido: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoReabastecimientoResponseDTO> updatePedido(@PathVariable Long id, @RequestBody PedidoReabastecimientoRequestDTO pedidoDetailsRequestDTO) {
        try {
            PedidoReabastecimientoResponseDTO updatedPedido = pedidoService.updatePedido(id, pedidoDetailsRequestDTO);
            return new ResponseEntity<>(updatedPedido, HttpStatus.OK);
        } catch (RuntimeException e) {
            System.err.println("Error al actualizar pedido con ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        try {
            pedidoService.deletePedido(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            System.err.println("Error al eliminar pedido con ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
