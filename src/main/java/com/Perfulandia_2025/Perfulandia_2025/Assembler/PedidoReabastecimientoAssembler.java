package com.Perfulandia_2025.Perfulandia_2025.Assembler;

import com.Perfulandia_2025.Perfulandia_2025.controller.PedidoReabastecimientoController;
import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PedidoReabastecimientoAssembler implements RepresentationModelAssembler<PedidoReabastecimiento, EntityModel<PedidoReabastecimiento>> {

    @Override
    public EntityModel<PedidoReabastecimiento> toModel(PedidoReabastecimiento pedidoReabastecimiento) {
        return EntityModel.of(pedidoReabastecimiento,
                linkTo(methodOn(PedidoReabastecimientoController.class).getPedidoById(pedidoReabastecimiento.getId())).withSelfRel(),
                linkTo(methodOn(PedidoReabastecimientoController.class).getAllPedidos()).withRel("pedidoReabastecimiento")); // Nombre del método confirmado
    }
}
