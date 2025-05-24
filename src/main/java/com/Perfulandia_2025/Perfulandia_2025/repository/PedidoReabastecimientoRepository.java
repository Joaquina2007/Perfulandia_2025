package com.Perfulandia_2025.Perfulandia_2025.repository;

import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PedidoReabastecimientoRepository extends JpaRepository<PedidoReabastecimiento, Long> {

    List<PedidoReabastecimiento> findByProveedorId(Long proveedorId);
    List<PedidoReabastecimiento> findByEstadoPedido(String estadoPedido);
    List<PedidoReabastecimiento> findByFechaPedidoBetween(Date startDate, Date endDate);

}
