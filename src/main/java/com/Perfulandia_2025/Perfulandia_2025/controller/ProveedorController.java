package com.Perfulandia_2025.Perfulandia_2025.controller;

import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.ProveedorRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.ProveedorResponseDTO;
import com.Perfulandia_2025.Perfulandia_2025.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<Proveedor>> getAllProveedores() {
        List<Proveedor> proveedores = proveedorService.getAllProveedores();
        return new ResponseEntity<>(proveedores, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProveedor(@RequestBody ProveedorRequestDTO proveedorRequestDTO) {
        try {
            Proveedor newProveedor = proveedorService.createProveedor(proveedorRequestDTO);
            return new ResponseEntity<>(newProveedor, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) { // Captura excepciones de validación manual del servicio
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) { // Captura otras excepciones de negocio (ej. RUC duplicado)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT); // 409 Conflict para duplicados
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProveedor(@PathVariable Long id, @RequestBody ProveedorRequestDTO proveedorRequestDTO) {
        try {
            Proveedor updatedProveedor = proveedorService.updateProveedor(id, proveedorRequestDTO);
            return new ResponseEntity<>(updatedProveedor, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) { // Manejo de "Proveedor no encontrado"
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable Long id) {
        try {
            proveedorService.deleteProveedor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
