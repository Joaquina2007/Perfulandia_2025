package com.Perfulandia_2025.Perfulandia_2025.repository;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {

    List<ItemPedido> findByPedidoReabastecimientoId(Long pedidoReabastecimientoId);

}
