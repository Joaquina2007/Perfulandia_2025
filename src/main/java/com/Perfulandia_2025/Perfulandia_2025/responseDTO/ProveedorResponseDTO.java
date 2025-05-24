package com.Perfulandia_2025.Perfulandia_2025.responseDTO;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ProveedorResponseDTO {

    private Long id;
    private String nombre;
    private String direccion;
    private int telefono;
    private String identificacion;
    private Boolean estadoProveedor;
    private LocalDate fechaRegistro;

}
