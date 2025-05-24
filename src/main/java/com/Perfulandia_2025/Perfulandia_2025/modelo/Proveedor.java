package com.Perfulandia_2025.Perfulandia_2025.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "proveedor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String direccion;

    @Column
    private Integer telefono;

    @Column
    private String identificacion;

    @Column
    private LocalDate fechaRegistro;

    @Column
    private Boolean estadoProveedor;

}
