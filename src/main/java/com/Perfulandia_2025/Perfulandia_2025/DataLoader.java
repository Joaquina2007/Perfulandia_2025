package com.Perfulandia_2025.Perfulandia_2025;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ItemPedido;
import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import com.Perfulandia_2025.Perfulandia_2025.modelo.RecepcionMercancia;
import com.Perfulandia_2025.Perfulandia_2025.repository.ItemPedidoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.PedidoReabastecimientoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.ProveedorRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.RecepcionMercanciaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

//@Component
public class DataLoader implements CommandLineRunner {

    private final ProveedorRepository proveedorRepository;
    private final PedidoReabastecimientoRepository pedidoReabastecimientoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final RecepcionMercanciaRepository recepcionMercanciaRepository;

    public DataLoader(ProveedorRepository proveedorRepository,
                      PedidoReabastecimientoRepository pedidoReabastecimientoRepository,
                      ItemPedidoRepository itemPedidoRepository,
                      RecepcionMercanciaRepository recepcionMercanciaRepository) {
        this.proveedorRepository = proveedorRepository;
        this.pedidoReabastecimientoRepository = pedidoReabastecimientoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.recepcionMercanciaRepository = recepcionMercanciaRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("Cargando datos iniciales...");

        Proveedor proveedor1 = new Proveedor(null, "Fragancia S.A.", "Av. Central 123", 987654321, "FRAG-SA-001", LocalDate.of(2023, 1, 15), true);
        proveedor1 = proveedorRepository.save(proveedor1);
        System.out.println("Proveedor cargado: " + proveedor1.getNombre());

        PedidoReabastecimiento pedido1 = new PedidoReabastecimiento(null, proveedor1, LocalDate.now(), LocalDate.now().plusDays(10), "Pendiente", 0, null);
        pedido1 = pedidoReabastecimientoRepository.save(pedido1);
        System.out.println("Pedido de Reabastecimiento cargado (ID: " + pedido1.getId() + ")");

        ItemPedido item1 = new ItemPedido(null, "Perfume Amaderado (M)", 10, BigDecimal.valueOf(75.50), "P_AMA_M", pedido1);
        ItemPedido item2 = new ItemPedido(null, "Loción Floral (F)", 15, BigDecimal.valueOf(50.00), "L_FLO_F", pedido1);
        item1 = itemPedidoRepository.save(item1);
        item2 = itemPedidoRepository.save(item2);
        pedido1.setItemPedidos(Arrays.asList(item1, item2));
        pedido1.setTotalPedido(item1.getCantidadSolicitada() * item1.getPrecioUnitario().intValue() + item2.getCantidadSolicitada() * item2.getPrecioUnitario().intValue());
        pedidoReabastecimientoRepository.save(pedido1);
        System.out.println("Items de pedido cargados para el Pedido (ID: " + pedido1.getId() + ")");

        RecepcionMercancia recepcion1 = new RecepcionMercancia(null, pedido1, LocalDate.now().plusDays(5), "María García", "Cajas en buen estado, 1 artículo faltante.", "Parcial");
        recepcion1 = recepcionMercanciaRepository.save(recepcion1);
        System.out.println("Recepción de Mercancía cargada (ID: " + recepcion1.getId() + ")");

        Proveedor proveedor2 = new Proveedor(null, "Esencias Global S.A.", "Ruta Sur km 5", 912345678, "ESG-SA-002", LocalDate.of(2024, 3, 1), true);
        proveedor2 = proveedorRepository.save(proveedor2);
        System.out.println("Proveedor cargado: " + proveedor2.getNombre());

        PedidoReabastecimiento pedido2 = new PedidoReabastecimiento(null, proveedor2, LocalDate.now(), LocalDate.now().plusDays(20), "En Proceso", 0, null);
        pedido2 = pedidoReabastecimientoRepository.save(pedido2);
        System.out.println("Pedido de Reabastecimiento cargado (ID: " + pedido2.getId() + ")");

        ItemPedido item3 = new ItemPedido(null, "Set de Muestras (Unisex)", 5, BigDecimal.valueOf(120.00), "SET_MUE", pedido2);
        item3 = itemPedidoRepository.save(item3);
        pedido2.setItemPedidos(Arrays.asList(item3));
        pedido2.setTotalPedido(item3.getCantidadSolicitada() * item3.getPrecioUnitario().intValue());
        pedidoReabastecimientoRepository.save(pedido2);
        System.out.println("Datos iniciales cargados completamente.");
    }
}
