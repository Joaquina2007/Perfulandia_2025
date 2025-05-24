package com.Perfulandia_2025.Perfulandia_2025.service;

import aj.org.objectweb.asm.commons.Remapper;
import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import com.Perfulandia_2025.Perfulandia_2025.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> getAllProveedores(){
        return proveedorRepository.findAll();
    }

    public Proveedor createProveedor(Proveedor proveedor){
        return proveedorRepository.save(proveedor);
    }

    public Proveedor updateProveedor(Long id, Proveedor proveedorDetails){
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

}
