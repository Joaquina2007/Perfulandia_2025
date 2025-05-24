package com.Perfulandia_2025.Perfulandia_2025.controller;

import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import com.Perfulandia_2025.Perfulandia_2025.service.PedidoReabastecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoReabastecimientoController {

    @Autowired
    private PedidoReabastecimientoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoReabastecimiento>> getAllPedidos(){
        List<PedidoReabastecimiento> pedidos = pedidoService.getAllPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoReabastecimiento> getPedidoById(@PathVariable Long id) {
        return pedidoService.getPedidoById(id)
                .map(pedido -> new ResponseEntity<>(pedido, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<PedidoReabastecimiento>> getPedidosByProveedor(@PathVariable Long proveedorId) {
        List<PedidoReabastecimiento> pedidos = pedidoService.getPedidosByProveedor(proveedorId);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PedidoReabastecimiento> createPedido(@RequestBody PedidoReabastecimiento pedido) {
        try {
            PedidoReabastecimiento newPedido = pedidoService.createPedido(pedido);
            return new ResponseEntity<>(newPedido, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PedidoReabastecimiento> updatePedido(@PathVariable Long id, @RequestBody PedidoReabastecimiento pedidoDetails) {
        try {
            PedidoReabastecimiento updatedPedido = pedidoService.updatePedido(id, pedidoDetails);
            return new ResponseEntity<>(updatedPedido, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePedido(@PathVariable Long id) {
        try {
            pedidoService.deletePedido(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
