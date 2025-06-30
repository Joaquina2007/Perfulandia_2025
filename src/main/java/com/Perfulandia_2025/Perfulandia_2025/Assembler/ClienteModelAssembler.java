package com.Perfulandia_2025.Perfulandia_2025.Assembler;

import com.Perfulandia_2025.Perfulandia_2025.controller.ClienteController;
import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Clase ensambladora para el modelo ClienteModel.
 * Implementa RepresentationModelAssembler para crear EntityModel con enlaces HATEOAS.
 */
@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteModel, EntityModel<ClienteModel>> {

    /**
     * Convierte un objeto ClienteModel en un EntityModel con enlaces HATEOAS.
     *
     * @param cliente El objeto ClienteModel a convertir.
     * @return Un EntityModel que representa al cliente con enlaces.
     */
    @Override
    public EntityModel<ClienteModel> toModel(ClienteModel cliente) {
        // Verifica si el cliente es nulo y lanza una excepción si es necesario
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente no puede ser nulo para construir el EntityModel.");
        }

        // Crea y devuelve el EntityModel con enlaces a las operaciones relacionadas
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteController.class).getAllClientes()).withRel("clientes"), // Enlace a la lista de clientes
                linkTo(methodOn(ClienteController.class).updateCliente(Integer.valueOf(String.valueOf(cliente.getId())), null)).withSelfRel() // Enlace a la actualización del cliente
        );
    }
}

