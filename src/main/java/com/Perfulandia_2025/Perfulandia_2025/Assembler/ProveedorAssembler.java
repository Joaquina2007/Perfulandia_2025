package com.Perfulandia_2025.Perfulandia_2025.Assembler;

import com.Perfulandia_2025.Perfulandia_2025.controller.ProveedorController;
import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProveedorAssembler implements RepresentationModelAssembler<Proveedor, EntityModel<Proveedor>> {

    @Override
    public EntityModel<Proveedor> toModel(Proveedor proveedor) {
        return EntityModel.of(proveedor,
                linkTo(methodOn(ProveedorController.class).getAllProveedores()).withRel("proveedor")); // Nombre del método confirmado
    }
}
