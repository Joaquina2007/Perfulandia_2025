package com.Perfulandia_2025.Perfulandia_2025.Assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.Perfulandia_2025.Perfulandia_2025.controller.ItemPedidoController;
import com.Perfulandia_2025.Perfulandia_2025.model.ItemPedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ItemPedidoAssembler implements RepresentationModelAssembler<ItemPedido, EntityModel<ItemPedido>> {

    @Override
    public EntityModel<ItemPedido> toModel(ItemPedido itemPedido) {
        return EntityModel.of(itemPedido,
                linkTo(methodOn(ItemPedidoController.class).getItemPedidoById(itemPedido.getId())).withSelfRel(),
                linkTo(methodOn(ItemPedidoController.class).getAllItemsPedido()).withRel("itemsPedidos")); // Nombre del método confirmado
    }
}
