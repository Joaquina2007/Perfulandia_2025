package com.Perfulandia_2025.Perfulandia_2025.repository;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.ClienteResponseDTO; // Este es tu import actual
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

    @Query("select new com.Perfulandia_2025.Perfulandia_2025.responseDTO.ClienteResponseDTO(c) " + // ¡Cambiado aquí!
            "from ClienteModel c where c.activo = true")
    List<ClienteResponseDTO> findClientesActivos();
}