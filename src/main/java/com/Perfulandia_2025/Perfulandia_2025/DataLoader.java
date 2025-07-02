package com.Perfulandia_2025.Perfulandia_2025;

import com.Perfulandia_2025.Perfulandia_2025.modelo.ClienteModel;
import com.Perfulandia_2025.Perfulandia_2025.modelo.ItemPedido;
import com.Perfulandia_2025.Perfulandia_2025.modelo.PedidoReabastecimiento;
import com.Perfulandia_2025.Perfulandia_2025.modelo.Proveedor;
import com.Perfulandia_2025.Perfulandia_2025.modelo.RecepcionMercancia;
import com.Perfulandia_2025.Perfulandia_2025.repository.ClienteRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.ItemPedidoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.PedidoReabastecimientoRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.ProveedorRepository;
import com.Perfulandia_2025.Perfulandia_2025.repository.RecepcionMercanciaRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired // Se agregó Autowired para los nuevos repositorios
    private ItemPedidoRepository itemPedidoRepository;
    @Autowired // Se agregó Autowired para los nuevos repositorios
    private PedidoReabastecimientoRepository pedidoReabastecimientoRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;
    @Autowired
    private RecepcionMercanciaRepository recepcionMercanciaRepository;

    @Override
    @Transactional
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

        for (int i = 0; i < 10; i++) {
            PedidoReabastecimiento pedidoReabastecimiento = new PedidoReabastecimiento();
            //pedidoReabastecimiento.setId((long) (i + 1));
            pedidoReabastecimiento.setEstadoPedido(faker.educator().course());
            pedidoReabastecimiento.setTotalPedido(faker.number().numberBetween(100, 50000));

            int numItems = random.nextInt(3) + 1;
            for (int j = 0; j < numItems; j++) {
                ItemPedido itemPedido = new ItemPedido();
                //itemPedido.setId((long) (i + 1));
                itemPedido.setCodigoProducto(faker.code().asin());
                itemPedido.setDescripcion(faker.educator().course());
                itemPedido.setPrecioUnitario(new BigDecimal(faker.commerce().price()));
                pedidoReabastecimiento.addItemPedido(itemPedido);
            }
            pedidoReabastecimientoRepository.save(pedidoReabastecimiento);

        }

        for (int i = 0; i < 10; i++) {
            Proveedor proveedor = new Proveedor();
            //proveedor.setId((long) (i + 1));
            proveedor.setEstadoProveedor(random.nextBoolean());
            proveedor.setIdentificacion(faker.educator().course());
            proveedor.setNombre(faker.name().fullName());
            proveedor.setDireccion(faker.educator().course());
            proveedor.setTelefono(faker.number().numberBetween(100000000, 999999999));
            proveedor.setFechaRegistro(LocalDate.now());
            proveedorRepository.save(proveedor);
        }

        for (int i = 0; i < 10; i++) {
            RecepcionMercancia recepcionMercancia = new RecepcionMercancia();
            //recepcionMercancia.setId((long) (i + 1));
            recepcionMercancia.setEstadoRecepcion(faker.educator().course());
            recepcionMercancia.setNotasRecepcion(faker.educator().course());
            recepcionMercancia.setFechaRecepcion(LocalDate.now());
            recepcionMercancia.setRecibidoPor(faker.educator().course());
            recepcionMercanciaRepository.save(recepcionMercancia);
        }

    }

}