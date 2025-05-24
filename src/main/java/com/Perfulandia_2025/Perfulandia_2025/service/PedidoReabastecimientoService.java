package com.Perfulandia_2025.Perfulandia_2025.service;

import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import com.Perfulandia_2025.Perfulandia_2025.repository.ItemPedidoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.PedidoReabastecimientoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoReabastecimientoService {

    @Autowired
    private PedidoReabastecimientoRepository pedidoReabastecimientoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public List<PedidoReabastecimiento> getAllPedidos(){
        return pedidoReabastecimientoRepository.findAll();
    }

    public Optional<PedidoReabastecimiento> getPedidoById(Long id){
        return pedidoReabastecimientoRepository.findById(id);
    }

    @Transactional
    public PedidoReabastecimiento createPedido(PedidoReabastecimiento pedido) {
        Proveedor proveedor = proveedorRepository.findById(pedido.getProveedor().getId()).orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + pedido.getProveedor().getId()));
        pedido.setProveedor(proveedor);
        pedido.setFechaPedido(LocalDate.now());
        if (pedido.getEstadoPedido() == null || pedido.getEstadoPedido().isEmpty()) {
            pedido.setEstadoPedido("Pendiente");
        }
        PedidoReabastecimiento savedPedido = pedidoReabastecimientoRepository.save(pedido);

        if (pedido.getItemPedidos() != null && !pedido.getItemPedidos().isEmpty()) {
            pedido.getItemPedidos().forEach(item -> {
                item.setPedidoReabastecimiento(savedPedido);
                itemPedidoRepository.save(item);
            });
            savedPedido.setItemPedidos(pedido.getItemPedidos());
        }
        return savedPedido;
    }

    public List<PedidoReabastecimiento> getPedidosByProveedor(Long proveedorId) {
        return pedidoReabastecimientoRepository.findByProveedorId(proveedorId);
    }

    @Transactional
    public PedidoReabastecimiento updatePedido(Long id, PedidoReabastecimiento pedidoDetails) {
        PedidoReabastecimiento pedido = pedidoReabastecimientoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido de reabastecimiento no encontrado con id: " + id));
        pedido.setFechaEntregaEstimada(pedidoDetails.getFechaEntregaEstimada());
        pedido.setEstadoPedido(pedidoDetails.getEstadoPedido());
        pedido.setTotalPedido(pedidoDetails.getTotalPedido());

        if (pedidoDetails.getItemPedidos() != null) {
            itemPedidoRepository.deleteAll(pedido.getItemPedidos());
            pedidoDetails.getItemPedidos().forEach(item -> {
                item.setPedidoReabastecimiento(pedido);
                itemPedidoRepository.save(item);
            });
            pedido.setItemPedidos(pedidoDetails.getItemPedidos());
        }
        return pedidoReabastecimientoRepository.save(pedido);
    }

    @Transactional
    public void deletePedido(Long id) {
        pedidoReabastecimientoRepository.deleteById(id);
    }

}
