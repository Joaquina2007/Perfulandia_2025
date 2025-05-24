package com.Perfulandia_2025.Perfulandia_2025.controller;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ItemPedido;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.ItemPedidoRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.ItemPedidoResponseDTO;
import com.Perfulandia_2025.Perfulandia_2025.service.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items-pedido")
public class ItemPedidoController {

    @Autowired
    private ItemPedidoService itemPedidoService;

    @GetMapping
    public ResponseEntity<List<ItemPedidoResponseDTO>> getAllItemsPedido() {
        List<ItemPedidoResponseDTO> items = itemPedidoService.getAllItemsPedido();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoResponseDTO> getItemPedidoById(@PathVariable Long id) {
        return itemPedidoService.getItemPedidoById(id).map(itemDTO -> new ResponseEntity<>(itemDTO, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<ItemPedidoResponseDTO>> getItemsByPedido(@PathVariable Long pedidoId) {
        List<ItemPedidoResponseDTO> items = itemPedidoService.getItemsByPedidoReabastecimientoId(pedidoId);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateItemPedido(@PathVariable Long id, @RequestBody ItemPedidoRequestDTO itemPedidoRequestDTO) {
        try {
            ItemPedidoResponseDTO updatedItem = itemPedidoService.updateItemPedido(id, itemPedidoRequestDTO);
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemPedido(@PathVariable Long id) {
        try {
            itemPedidoService.deleteItemPedido(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
