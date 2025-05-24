package com.Perfulandia_2025.Perfulandia_2025.modelo;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String descripcion;

    @Column
    private int cantidadSolicitada;

    @Column
    private double precioUnitario;

    @Column
    private String codigoProducto;

    @ManyToOne
    @JoinColumn(name = "pedido_reabastecimiento_id")
    private PedidoReabastecimiento pedidoReabastecimiento;

}
