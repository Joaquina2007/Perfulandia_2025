package com.Perfulandia_2025.Perfulandia_2025.service;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ItemPedido;
import com.Perfulandia_2025.Perfulandia_2025.repository.ItemPedidoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.PedidoReabastecimientoRepository;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.ItemPedidoRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.ItemPedidoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private PedidoReabastecimientoRepository pedidoReabastecimientoRepository;

    public ItemPedido convertToEntity(ItemPedidoRequestDTO dto) {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setDescripcion(dto.getDescripcion());
        itemPedido.setCantidadSolicitada(dto.getCantidadSolicitada());
        itemPedido.setPrecioUnitario(dto.getPrecioUnitario());
        itemPedido.setCodigoProducto(dto.getCodigoProducto());
        return itemPedido;
    }

    public ItemPedidoResponseDTO convertToResponseDTO(ItemPedido itemPedido) {
        ItemPedidoResponseDTO dto = new ItemPedidoResponseDTO();
        dto.setId(itemPedido.getId());
        dto.setDescripcion(itemPedido.getDescripcion());
        dto.setCantidadSolicitada(itemPedido.getCantidadSolicitada());
        dto.setPrecioUnitario(itemPedido.getPrecioUnitario());
        dto.setCodigoProducto(itemPedido.getCodigoProducto());
        return dto;
    }

    public List<ItemPedidoResponseDTO> getAllItemsPedido() {
        return itemPedidoRepository.findAll().stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Optional<ItemPedidoResponseDTO> getItemPedidoById(Long id) {
        return itemPedidoRepository.findById(id).map(this::convertToResponseDTO);
    }

    public ItemPedidoResponseDTO updateItemPedido(Long id, ItemPedidoRequestDTO requestDTO) {
        ItemPedido itemPedido = itemPedidoRepository.findById(id).orElseThrow(() -> new RuntimeException("Item de pedido no encontrado con id: " + id));
        if (requestDTO.getDescripcion() != null) itemPedido.setDescripcion(requestDTO.getDescripcion());
        if (requestDTO.getCantidadSolicitada() != null) itemPedido.setCantidadSolicitada(requestDTO.getCantidadSolicitada());
        if (requestDTO.getPrecioUnitario() != null) itemPedido.setPrecioUnitario(requestDTO.getPrecioUnitario());
        if (requestDTO.getCodigoProducto() != null) itemPedido.setCodigoProducto(requestDTO.getCodigoProducto());
        ItemPedido updatedItem = itemPedidoRepository.save(itemPedido);
        return convertToResponseDTO(updatedItem);
    }

    public void deleteItemPedido(Long id){
        itemPedidoRepository.deleteById(id);
    }

    public List<ItemPedidoResponseDTO> getItemsByPedidoReabastecimientoId(Long pedidoId) {
        return itemPedidoRepository.findByPedidoReabastecimientoId(pedidoId).stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

}
