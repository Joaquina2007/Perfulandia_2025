package com.Perfulandia_2025.Perfulandia_2025.Assembler;

import com.Perfulandia_2025.Perfulandia_2025.controller.RecepcionMercanciaController;
import com.Perfulandia_2025.Perfulandia_2025.modelo.RecepcionMercancia;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RecepcionMercanciaAssembler implements RepresentationModelAssembler<RecepcionMercancia, EntityModel<RecepcionMercancia>> {

    @Override
    public EntityModel<RecepcionMercancia> toModel(RecepcionMercancia recepcionMercancia) {
        return EntityModel.of(recepcionMercancia,
                linkTo(methodOn(RecepcionMercanciaController.class).getRecepcionById(recepcionMercancia.getId())).withSelfRel(),
                linkTo(methodOn(RecepcionMercanciaController.class).getAllRecepciones()).withRel("recepcionMercancia")); // Nombre del método confirmado
    }
}