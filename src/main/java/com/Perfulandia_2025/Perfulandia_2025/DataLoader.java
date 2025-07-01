package com.Perfulandia_2025.Perfulandia_2025;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ItemPedido;
import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import com.Perfulandia_2025.Perfulandia_2025.modelo.RecepcionMercancia;
import com.Perfulandia_2025.Perfulandia_2025.repository.*;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Random;

@Component
public abstract class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;
    private ItemPedidoRepository itemPedidoRepository;
    private PedidoReabastecimientoRepository pedidoReabastecimientoRepository;
    private ProveedorRepository proveedorRepository;
    private RecepcionMercanciaRepository recepcionMercanciaRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        System.out.println("--- Iniciando la carga de datos para Perfulandia_2025 ---");

        // --- Carga de 10 clientes de prueba --- (Funcionalidad existente)
        System.out.println("Cargando 10 clientes de prueba...");
        for (int i = 0; i < 10; i++) {
            ClienteModel cliente = new ClienteModel();
            cliente.setNombre(faker.name().fullName());
            cliente.setCorreo(faker.internet().emailAddress());
            cliente.setActivo(random.nextBoolean());
            clienteRepository.save(cliente);
        }
        System.out.println("Clientes cargados.");
    }
}
