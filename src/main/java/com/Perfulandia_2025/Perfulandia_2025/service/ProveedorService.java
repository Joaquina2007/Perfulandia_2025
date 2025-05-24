package com.Perfulandia_2025.Perfulandia_2025.service;

import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import com.Perfulandia_2025.Perfulandia_2025.repository.ProveedorRepository;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.ProveedorRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.ProveedorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Autowired
    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<Proveedor> getAllProveedores(){
        return proveedorRepository.findAll();
    }

    @Transactional
    public Proveedor createProveedor(ProveedorRequestDTO requestDTO){
        Proveedor proveedorParaGuardar = convertToEntity(requestDTO);
        return proveedorRepository.save(proveedorParaGuardar);
    }

    public Proveedor updateProveedor(Long id, ProveedorRequestDTO proveedorDetails){
        Proveedor proveedor = proveedorRepository.findById(id).orElseThrow();
        proveedor.setNombre(proveedorDetails.getNombre());
        proveedor.setDireccion(proveedorDetails.getDireccion());
        proveedor.setTelefono(proveedorDetails.getTelefono());
        proveedor.setIdentificacion(proveedorDetails.getIdentificacion());
        proveedor.setEstadoProveedor(proveedorDetails.getEstadoProveedor());
        return proveedorRepository.save(proveedor);
    }

    public void deleteProveedor(Long id){
        proveedorRepository.deleteById(id);
    }

    private Proveedor convertToEntity(ProveedorRequestDTO dto) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(dto.getNombre());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setIdentificacion(dto.getIdentificacion());
        if (dto.getEstadoProveedor() != null) {
            proveedor.setEstadoProveedor(dto.getEstadoProveedor());
        }
        return proveedor;
    }


    private ProveedorResponseDTO convertToResponseDTO(Proveedor proveedor) {
        ProveedorResponseDTO dto = new ProveedorResponseDTO();
        dto.setId(proveedor.getId());
        dto.setNombre(proveedor.getNombre());
        dto.setDireccion(proveedor.getDireccion());
        dto.setTelefono(proveedor.getTelefono());
        dto.setIdentificacion(proveedor.getIdentificacion());
        dto.setEstadoProveedor(proveedor.getEstadoProveedor());
        dto.setFechaRegistro(proveedor.getFechaRegistro());
        return dto;
    }

}
