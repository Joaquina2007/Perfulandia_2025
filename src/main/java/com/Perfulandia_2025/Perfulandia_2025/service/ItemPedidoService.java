package com.Perfulandia_2025.Perfulandia_2025.service;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ItemPedido;
import com.Perfulandia_2025.Perfulandia_2025.repository.ItemPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ItemPedidoService {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public List<ItemPedido> getAllItemPedido(){
        return itemPedidoRepository.findAll();
    }

    public Optional<ItemPedido> getItemPedidoById(Long id){
        return itemPedidoRepository.findById(id);
    }

    public ItemPedido updateItemPedido(Long id, ItemPedido itemPedidoDetails){

        itemPedidoDetails.setDescripcion(itemPedidoDetails.getDescripcion());
        itemPedidoDetails.setCantidadSolicitada(itemPedidoDetails.getCantidadSolicitada());
        itemPedidoDetails.setPrecioUnitario(itemPedidoDetails.getPrecioUnitario());
        itemPedidoDetails.setCodigoProducto(itemPedidoDetails.getCodigoProducto());

        return itemPedidoRepository.save(itemPedidoDetails);
    }

    public void deleteItemPedido(Long id){
        itemPedidoRepository.deleteById(id);
    }

    public List<ItemPedido> getItemsByPedidoReabastecimientoId(Long pedidoId) {
        return itemPedidoRepository.findByPedidoReabastecimientoId(pedidoId);
    }

}
