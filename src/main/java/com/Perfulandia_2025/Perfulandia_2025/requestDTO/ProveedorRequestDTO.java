package com.Perfulandia_2025.Perfulandia_2025.requestDTO;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorRequestDTO {

    private String nombre;
    private String direccion;
    private int telefono;
    private String identificacion;
    private Boolean estadoProveedor;

    public String getDescripcion() {
    return getDescripcion();
    }

    public Integer getCantidadSolicitada() {
        return getCantidadSolicitada();
    }

    public BigDecimal getPrecioUnitario() {
        return getPrecioUnitario();
    }

    public String getCodigoProducto() {
        return getCodigoProducto();
    }
}
