package com.Perfulandia_2025.Perfulandia_2025.responseDTO;

import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoResponseDTO {

    private Long id;
    private PedidoReabastecimiento pedidoReabastecimiento;
    private String descripcion;
    private Integer cantidadSolicitada;
    private BigDecimal precioUnitario;
    private String codigoProducto;

}
