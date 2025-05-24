package com.Perfulandia_2025.Perfulandia_2025.responseDTO;

import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecepcionMercanciaResponseDTO {

    private Long id;
    private PedidoReabastecimiento pedidoReabastecimiento;
    private LocalDate fechaRecepcion;
    private String recibidoPor;
    private String notasRecepcion;
    private String estadoRecepcion;

}
