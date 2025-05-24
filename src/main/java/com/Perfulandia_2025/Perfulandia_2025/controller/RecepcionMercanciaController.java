package com.Perfulandia_2025.Perfulandia_2025.controller;

import com.Perfulandia_2025.Perfulandia_2025.modelo.RecepcionMercancia;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.RecepcionMercanciaRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.responseDTO.RecepcionMercanciaResponseDTO;
import com.Perfulandia_2025.Perfulandia_2025.service.RecepcionMercanciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recepciones")
public class RecepcionMercanciaController {

    @Autowired
    private RecepcionMercanciaService recepcionService;

    @GetMapping
    public ResponseEntity<List<RecepcionMercanciaResponseDTO>> getAllRecepciones() {
        List<RecepcionMercanciaResponseDTO> recepciones = recepcionService.getAllRecepciones();
        return new ResponseEntity<>(recepciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecepcionMercanciaResponseDTO> getRecepcionById(@PathVariable Long id) {
        return recepcionService.getRecepcionById(id).map(recepcionDTO -> new ResponseEntity<>(recepcionDTO, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/pedido/{pedidoId}")
    public ResponseEntity<?> registrarRecepcion(@PathVariable Long pedidoId, @RequestBody RecepcionMercanciaRequestDTO recepcionRequestDTO) {
        try {
            RecepcionMercanciaResponseDTO newRecepcion = recepcionService.registrarRecepcion(pedidoId, recepcionRequestDTO);
            return new ResponseEntity<>(newRecepcion, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecepcion(@PathVariable Long id, @RequestBody RecepcionMercanciaRequestDTO recepcionRequestDTO) {
        try {
            RecepcionMercanciaResponseDTO updatedRecepcion = recepcionService.updateRecepcion(id, recepcionRequestDTO);
            return new ResponseEntity<>(updatedRecepcion, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecepcion(@PathVariable Long id) {
        try {
            recepcionService.deleteRecepcion(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
