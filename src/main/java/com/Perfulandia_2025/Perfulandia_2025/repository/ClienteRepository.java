package com.Perfulandia_2025.Perfulandia_2025.repository;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.ClienteResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface ClienteRepository extends JpaRepository<ClienteModel, Long> {

    @Query("select new com.Perfulandia_2025.Perfulandia_2025.dto.response.ClienteResponseDTO(c) " +
            "from ClienteModel c where c.activo = true")
    List<ClienteResponseDTO> findClientesActivos();
}
