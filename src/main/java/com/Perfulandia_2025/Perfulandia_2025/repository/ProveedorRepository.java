package com.Perfulandia_2025.Perfulandia_2025.repository;

import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    Proveedor findByIdentificacion(String identificacion);
}
