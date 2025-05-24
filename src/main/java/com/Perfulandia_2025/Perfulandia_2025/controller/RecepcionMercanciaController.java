package com.Perfulandia_2025.Perfulandia_2025.controller;

import com.Perfulandia_2025.Perfulandia_2025.modelo.RecepcionMercancia;
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
    public ResponseEntity<List<RecepcionMercancia>> getAllRecepciones() {
        List<RecepcionMercancia> recepciones = recepcionService.getAllRecepciones();
        return new ResponseEntity<>(recepciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecepcionMercancia> getRecepcionById(@PathVariable Long id) {
        return recepcionService.getRecepcionById(id).map(recepcion -> new ResponseEntity<>(recepcion, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/pedido/{pedidoId}")
    public ResponseEntity<RecepcionMercancia> registrarRecepcion(@PathVariable Long pedidoId, @RequestBody RecepcionMercancia recepcion) {
        try {
            RecepcionMercancia newRecepcion = recepcionService.registrarRecepcion(pedidoId, recepcion);
            return new ResponseEntity<>(newRecepcion, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecepcionMercancia> updateRecepcion(@PathVariable Long id, @RequestBody RecepcionMercancia recepcionDetails) {
        try {
            RecepcionMercancia updatedRecepcion = recepcionService.updateRecepcion(id, recepcionDetails);
            return new ResponseEntity<>(updatedRecepcion, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
