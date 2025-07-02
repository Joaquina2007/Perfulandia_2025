package com.Perfulandia_2025.Perfulandia_2025.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedido")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoReabastecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @Column
    private LocalDate fechaPedido;

    @Column
    private LocalDate fechaEntregaEstimada;

    @Column
    private String estadoPedido;

    @Column
    private Integer totalPedido;

    @OneToMany(mappedBy = "pedidoReabastecimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPedido> itemPedidos;

    public void addItemPedido(ItemPedido itemPedido) {
        if (this.itemPedidos == null) {
            this.itemPedidos = new ArrayList<>();
        }
        this.itemPedidos.add(itemPedido);
        itemPedido.setPedidoReabastecimiento(this);
    }

}
