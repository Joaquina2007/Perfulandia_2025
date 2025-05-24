package com.Perfulandia_2025.Perfulandia_2025.modelo;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "recepcion")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecepcionMercancia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_reabastecimiento_id")
    private PedidoReabastecimiento pedidoReabastecimiento;

    @Column
    private LocalDate fechaRecepcion;

    @Column
    private String recibidoPor;

    @Column
    private String notasRecepcion;

    @Column
    private String estadoRecepcion;

}
