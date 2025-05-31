package com.Perfulandia_2025.Perfulandia_2025.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.repository.ClienteRepository;

import java.util.List;

@Transactional
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository cliRepository;

    //Metodo Listar
    public List<ClienteModel> findAll(){
        return cliRepository.findAll();

    }
    //Metodo Guardar
    public ClienteModel save(ClienteModel climod){
        return cliRepository.save(climod);
    }
    //Metodo Eliminar
    public void delete(long id){
        cliRepository.deleteById(id);
    }
    //Metodo para Actualizar
    public ClienteModel findById(long id){
        return cliRepository.findById(id).get();
    }




}