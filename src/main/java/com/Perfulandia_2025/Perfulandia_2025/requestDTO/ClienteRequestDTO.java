package com.Perfulandia_2025.Perfulandia_2025.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClienteRequestDTO {

    private Long id;
    private String nombre;
    private String email;

}