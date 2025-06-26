package com.Perfulandia_2025.Perfulandia_2025.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.requestDTO.ClienteRequestDTO;
import com.Perfulandia_2025.Perfulandia_2025.service.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteController {

    @Autowired
    private ClienteService cliserv;

    @GetMapping
    public ResponseEntity<List<ClienteModel>> listar() {
        List<ClienteModel> cliList = cliserv.findAll();
        if(cliList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cliList);
    }

    @PostMapping
    public ResponseEntity<ClienteModel> guardar(@RequestBody ClienteRequestDTO clisave) {
        ClienteModel newCliente = new ClienteModel();
        newCliente.setNombre(clisave.getNombre());
        newCliente.setCorreo(clisave.getCorreo());
        newCliente.setActivo(clisave.getActivo());
        newCliente = cliserv.save(newCliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> actualizar(@PathVariable Integer id, @RequestBody ClienteRequestDTO cliUpdate) {
        try {
            ClienteModel cli = cliserv.findById(id);
            if (cli == null) {
                return ResponseEntity.notFound().build();
            }
            cli.setNombre(cliUpdate.getNombre());
            cli.setCorreo(cliUpdate.getCorreo());
            cli.setActivo(cliUpdate.getActivo());
            cliserv.save(cli);
            return ResponseEntity.ok(cli);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            cliserv.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

