package com.Perfulandia_2025.Perfulandia_2025.requestDTO;

import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PedidoReabastecimientoRequestDTO {

    private Proveedor proveedor;
    private LocalDate fechaEntregaEstimada;
    private String estadoPedido;
    private int totalPedido;
    private List<ItemPedidoRequestDTO> itemPedidos;

}
