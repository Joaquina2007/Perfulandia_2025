package com.Perfulandia_2025.Perfulandia_2025.responseDTO;

import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoReabastecimientoResponseDTO {

    private Long id;
    private Proveedor proveedor;
    private String nombreProveedor;
    private LocalDate fechaPedido;
    private LocalDate fechaEntregaEstimada;
    private String estadoPedido;
    private int totalPedido;
    private List<ItemPedidoResponseDTO> itemPedidos;

}
