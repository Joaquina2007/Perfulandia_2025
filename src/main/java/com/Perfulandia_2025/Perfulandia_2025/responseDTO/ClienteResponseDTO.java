package com.Perfulandia_2025.Perfulandia_2025.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {

    private Integer id;
    private String nombre;
    private String correo;
    private Boolean activo;

    public ClienteResponseDTO(com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel cliente) {
        this.id = cliente.getId();
        this.nombre = cliente.getNombre();
        this.correo = cliente.getCorreo();
        this.activo = cliente.getActivo();
    }
}
