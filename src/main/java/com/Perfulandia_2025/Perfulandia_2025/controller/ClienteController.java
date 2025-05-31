package com.Perfulandia_2025.Perfulandia_2025.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.service.ClienteService;

import java.util.List;

//http://localhost:8080/api/v1/inventario
@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteController {

    @Autowired

    private ClienteService cliserv;

    @GetMapping
    public ResponseEntity<List<ClienteModel>> listar() {
        List<ClienteModel> cliList = cliserv.findAll();
        if(cliList.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }return ResponseEntity.ok(cliList);
    }

    @PostMapping
    public ResponseEntity<ClienteModel> guardar(@RequestBody ClienteModel clisave) {
        ClienteModel newCliente = cliserv.save(clisave);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> actualizar(@PathVariable Integer id,@RequestBody ClienteModel cliDelete) {
        try {
            ClienteModel cli = cliserv.findById(id);
            cli.setId(id);
            cli.setNombre(cliDelete.getNombre());
            cli.setCorreo(cliDelete.getCorreo());
            cli.setActivo(cliDelete.getActivo());
            cliserv.save(cli);
            return ResponseEntity.ok(cliDelete);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try{
            cliserv.delete(id);
            return  ResponseEntity.noContent().build();
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
