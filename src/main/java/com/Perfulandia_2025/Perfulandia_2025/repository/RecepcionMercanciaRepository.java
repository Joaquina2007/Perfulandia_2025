package com.Perfulandia_2025.Perfulandia_2025.repository;

import com.Perfulandia_2025.Perfulandia_2025.modelo.RecepcionMercancia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface RecepcionMercanciaRepository extends JpaRepository<RecepcionMercancia, Long> {

    List<RecepcionMercancia> findByPedidoReabastecimientoId(Long pedidoReabastecimientoId);
    List<RecepcionMercancia> findByFechaRecepcionBetween(Date startDate, Date endDate);

}
