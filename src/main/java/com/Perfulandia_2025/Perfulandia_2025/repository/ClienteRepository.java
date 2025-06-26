package com.Perfulandia_2025.Perfulandia_2025.repository;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.ClienteResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Integer> {

    @Query("SELECT new com.Perfulandia_2025.Perfulandia_2025.responseDTO.ClienteResponseDTO(c) FROM ClienteModel c")
    List<ClienteResponseDTO> obtenerTodosClientesDTO();
}
