package com.Perfulandia_2025.Perfulandia_2025.requestDTO;

import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoRequestDTO {

    private String descripcion;
    private Integer cantidadSolicitada;
    private BigDecimal precioUnitario;
    private String codigoProducto;

}
