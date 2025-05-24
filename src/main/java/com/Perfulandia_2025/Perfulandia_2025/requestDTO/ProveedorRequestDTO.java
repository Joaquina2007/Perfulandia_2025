package com.Perfulandia_2025.Perfulandia_2025.requestDTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorRequestDTO {

    private String nombre;
    private String direccion;
    private int telefono;
    private String identificacion;
    private Boolean estadoProveedor;

}
