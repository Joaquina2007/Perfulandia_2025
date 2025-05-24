package com.Perfulandia_2025.Perfulandia_2025.requestDTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoRequestDTO {

    private String descripcion;
    private int cantidadSolicitada;
    private int precioUnitario;
    private String codigoProducto;

}
