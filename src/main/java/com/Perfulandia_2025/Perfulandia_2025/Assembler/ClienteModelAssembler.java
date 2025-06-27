package com.Perfulandia_2025.Perfulandia_2025.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.Perfulandia_2025.Perfulandia_2025.controller.ClienteController;
import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<ClienteModel, EntityModel<ClienteModel>> {

    @Override
    public EntityModel<ClienteModel> toModel(ClienteModel cliente) {
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteController.class).listar()).withRel("clientes"),
                linkTo(methodOn(ClienteController.class).actualizar(cliente.getId(), null)).withSelfRel()
        );
    }
}

