package com.Perfulandia_2025.Perfulandia_2025.service;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ItemPedido;
import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import com.Perfulandia_2025.Perfulandia_2025.repository.ItemPedidoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.PedidoReabastecimientoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.ProveedorRepository;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.ItemPedidoRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.PedidoReabastecimientoRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.PedidoReabastecimientoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoReabastecimientoService {

    private final PedidoReabastecimientoRepository pedidoReabastecimientoRepository;
    private final ProveedorRepository proveedorRepository;
    private final ItemPedidoRepository itemPedidoRepository;

    @Autowired
    public PedidoReabastecimientoService(
            PedidoReabastecimientoRepository pedidoReabastecimientoRepository,
            ProveedorRepository proveedorRepository,
            ItemPedidoRepository itemPedidoRepository
            ) {
        this.pedidoReabastecimientoRepository = pedidoReabastecimientoRepository;
        this.proveedorRepository = proveedorRepository;
        this.itemPedidoRepository = itemPedidoRepository;
    }

    private PedidoReabastecimiento convertToEntity(PedidoReabastecimientoRequestDTO dto) {
        PedidoReabastecimiento pedido = new PedidoReabastecimiento();
        pedido.setFechaEntregaEstimada(dto.getFechaEntregaEstimada());
        pedido.setEstadoPedido(dto.getEstadoPedido());
        pedido.setTotalPedido(dto.getTotalPedido());
        return pedido;
    }

    private PedidoReabastecimientoResponseDTO convertToResponseDTO(PedidoReabastecimiento pedido) {
        PedidoReabastecimientoResponseDTO dto = new PedidoReabastecimientoResponseDTO();
        dto.setId(pedido.getId());
        dto.setFechaPedido(pedido.getFechaPedido());
        dto.setFechaEntregaEstimada(pedido.getFechaEntregaEstimada());
        dto.setEstadoPedido(pedido.getEstadoPedido());
        dto.setTotalPedido(pedido.getTotalPedido());
        return dto;
    }

    private ItemPedido convertItemPedidoRequestDTOToEntity(ItemPedidoRequestDTO dto) {
        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setCantidadSolicitada(dto.getCantidadSolicitada());
        itemPedido.setPrecioUnitario(dto.getPrecioUnitario());
        return itemPedido;
    }

    public List<PedidoReabastecimientoResponseDTO> getAllPedidos() {
        return pedidoReabastecimientoRepository.findAll().stream().map(this::convertToResponseDTO).collect(Collectors.toList());
    }

    public Optional<PedidoReabastecimientoResponseDTO> getPedidoById(Long id) {
        return pedidoReabastecimientoRepository.findById(id).map(this::convertToResponseDTO);
    }

    @Transactional
    public PedidoReabastecimientoResponseDTO createPedido(PedidoReabastecimientoRequestDTO requestDTO) {
        Proveedor proveedor = proveedorRepository.findById(requestDTO.getProveedor()).orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + requestDTO.getProveedor()));
        PedidoReabastecimiento pedido = convertToEntity(requestDTO);
        pedido.setProveedor(proveedor);
        pedido.setFechaPedido(LocalDate.now());

        if (pedido.getEstadoPedido() == null || pedido.getEstadoPedido().isEmpty()) {
            pedido.setEstadoPedido("Pendiente");
        }
        PedidoReabastecimiento savedPedido = pedidoReabastecimientoRepository.save(pedido);
        if (pedido.getItemPedidos() != null && !pedido.getItemPedidos().isEmpty()) {
            for (ItemPedido item : pedido.getItemPedidos()) {
                item.setPedidoReabastecimiento(savedPedido);
                itemPedidoRepository.save(item);
            }
        }
        return convertToResponseDTO(savedPedido);
    }

    public List<PedidoReabastecimientoResponseDTO> getPedidosByProveedor(Long proveedorId) {
        List<PedidoReabastecimiento> pedidosEntidades = pedidoReabastecimientoRepository.findByProveedorId(proveedorId);
        return pedidosEntidades.stream().map(this::convertToResponseDTO).toList();
    }

    @Transactional
    public PedidoReabastecimientoResponseDTO updatePedido(Long id, PedidoReabastecimientoRequestDTO requestDTO) {
        PedidoReabastecimiento pedido = pedidoReabastecimientoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido de reabastecimiento no encontrado con id: " + id));
        if (requestDTO.getFechaEntregaEstimada() != null){
            pedido.setFechaEntregaEstimada(requestDTO.getFechaEntregaEstimada());
        }
        if (requestDTO.getEstadoPedido() != null){
            pedido.setEstadoPedido(requestDTO.getEstadoPedido());
        }
        if (requestDTO.getTotalPedido() != null){
            pedido.setTotalPedido(requestDTO.getTotalPedido());
        }
        if (requestDTO.getItemPedidos() != null) {
            itemPedidoRepository.deleteAll(pedido.getItemPedidos());
            List<ItemPedido> newItems = requestDTO.getItemPedidos().stream().map(this::convertItemPedidoRequestDTOToEntity).collect(Collectors.toList());
            for (ItemPedido newItem : newItems) {
                newItem.setPedidoReabastecimiento(pedido);
                itemPedidoRepository.save(newItem);
            }
            pedido.setItemPedidos(newItems);
        }
        PedidoReabastecimiento updatedPedido = pedidoReabastecimientoRepository.save(pedido);
        return convertToResponseDTO(updatedPedido);
    }

    @Transactional
    public void deletePedido(Long id) {
        itemPedidoRepository.deleteAll(itemPedidoRepository.findByPedidoReabastecimientoId(id));
        pedidoReabastecimientoRepository.deleteById(id);
    }

}
