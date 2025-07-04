package com.api.diversity.config;

import com.api.diversity.model.*;
import com.api.diversity.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private RubroRepository rubroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        crearRoles();
        crearRubros();
        crearAdministrador();
        crearDatosEjemplo();
    }

    private void crearRoles() {
        if (rolRepository.count() == 0) {
            Rol administrador = new Rol();
            administrador.setNombreRol("Administrador");
            administrador.setDescripcion("Acceso total al sistema");
            administrador.setEstado("Activo");
            rolRepository.save(administrador);

            Rol supervisor = new Rol();
            supervisor.setNombreRol("Supervisor");
            supervisor.setDescripcion("Puede aprobar entradas y salidas");
            supervisor.setEstado("Activo");
            rolRepository.save(supervisor);

            Rol operador = new Rol();
            operador.setNombreRol("Operador");
            operador.setDescripcion("Puede registrar entradas y salidas");
            operador.setEstado("Activo");
            rolRepository.save(operador);
        }
    }

    private void crearRubros() {
        if (rubroRepository.count() == 0) {
            Rubro pinateria = new Rubro();
            pinateria.setNombre("Piñatería y Artículos de Fiesta");
            pinateria.setCode("PIN-FIE");
            pinateria.setDescripcion(
                    "Productos para celebraciones, piñatas, globos, decoración y artículos de fiesta en general.");
            pinateria.setEstado("Activo");
            rubroRepository.save(pinateria);

            Rubro libreria = new Rubro();
            libreria.setNombre("Librería y Útiles de Oficina");
            libreria.setCode("LIB-OFI");
            libreria.setDescripcion(
                    "Artículos de escritura, papelería, cuadernos, archivadores y suministros de oficina.");
            libreria.setEstado("Activo");
            rubroRepository.save(libreria);

            Rubro seguridad = new Rubro();
            seguridad.setNombre("Sistemas de Seguridad y CCTV");
            seguridad.setCode("SEG-CAM");
            seguridad.setDescripcion("Cámaras de seguridad, DVRs, NVRs y accesorios de vigilancia electrónica.");
            seguridad.setEstado("Activo");
            rubroRepository.save(seguridad);
        }
    }

    private void crearAdministrador() {
        if (usuarioRepository.count() == 0) {
            Rol rolAdmin = rolRepository.findByNombreRol("Administrador")
                    .orElseThrow(() -> new RuntimeException("Rol Administrador no encontrado"));

            Rubro rubroPinateria = rubroRepository.findByCode("PIN-FIE")
                    .orElseThrow(() -> new RuntimeException("Rubro Piñatería no encontrado"));

            Usuario admin = new Usuario();
            admin.setNombreUsuario("admin");
            admin.setEmail("admin@diversity.com");
            admin.setNombreCompleto("Administrador del Sistema");
            admin.setRol(rolAdmin);
            admin.setRubro(rubroPinateria);
            admin.setContrasena(passwordEncoder.encode("admin123"));
            admin.setEstado("Activo");
            usuarioRepository.save(admin);
        }
    }

    private void crearDatosEjemplo() {
        if (categoriaRepository.count() == 0) {
            crearCategoriasEjemplo();
        }

        if (productoRepository.count() == 0) {
            crearProductosEjemplo();
        }

        if (proveedorRepository.count() == 0) {
            crearProveedoresEjemplo();
        }

        if (clienteRepository.count() == 0) {
            crearClientesEjemplo();
        }
    }

    private void crearCategoriasEjemplo() {
        Rubro pinateria = rubroRepository.findByCode("PIN-FIE").orElse(null);
        Rubro libreria = rubroRepository.findByCode("LIB-OFI").orElse(null);
        Rubro seguridad = rubroRepository.findByCode("SEG-CAM").orElse(null);

        Usuario admin = usuarioRepository.findByNombreUsuario("admin").orElse(null);

        if (pinateria != null && admin != null) {
            Categoria pinatas = new Categoria();
            pinatas.setRubro(pinateria);
            pinatas.setNombreCategoria("Piñatas");
            pinatas.setDescripcion("Piñatas de diferentes tamaños y diseños");
            pinatas.setEstado("Activo");
            pinatas.setCreatedBy(admin);
            categoriaRepository.save(pinatas);

            Categoria globos = new Categoria();
            globos.setRubro(pinateria);
            globos.setNombreCategoria("Globos");
            globos.setDescripcion("Globos de látex y metalizados");
            globos.setEstado("Activo");
            globos.setCreatedBy(admin);
            categoriaRepository.save(globos);
        }

        if (libreria != null && admin != null) {
            Categoria cuadernos = new Categoria();
            cuadernos.setRubro(libreria);
            cuadernos.setNombreCategoria("Cuadernos");
            cuadernos.setDescripcion("Cuadernos de diferentes tamaños y tipos");
            cuadernos.setEstado("Activo");
            cuadernos.setCreatedBy(admin);
            categoriaRepository.save(cuadernos);
        }
    }

    private void crearProductosEjemplo() {
        Categoria pinatas = categoriaRepository.findByNombreCategoria("Piñatas").orElse(null);
        Categoria globos = categoriaRepository.findByNombreCategoria("Globos").orElse(null);
        Categoria cuadernos = categoriaRepository.findByNombreCategoria("Cuadernos").orElse(null);

        Usuario admin = usuarioRepository.findByNombreUsuario("admin").orElse(null);

        if (pinatas != null && admin != null) {
            Producto pinataMickey = new Producto();
            pinataMickey.setCodigoProducto("PIN001");
            pinataMickey.setNombreProducto("Piñata Mickey Mouse");
            pinataMickey.setDescripcion("Piñata de Mickey Mouse tamaño grande");
            pinataMickey.setCategoria(pinatas);
            pinataMickey.setPrecioCompra(new BigDecimal("25.00"));
            pinataMickey.setPrecioVenta(new BigDecimal("35.00"));
            pinataMickey.setStockActual(10);
            pinataMickey.setStockMinimo(5);
            pinataMickey.setStockMaximo(20);
            pinataMickey.setEstado("Activo");
            pinataMickey.setCreatedBy(admin);
            productoRepository.save(pinataMickey);
        }

        if (globos != null && admin != null) {
            Producto globosDorados = new Producto();
            globosDorados.setCodigoProducto("GLO001");
            globosDorados.setNombreProducto("Globos Metalizados Dorados");
            globosDorados.setDescripcion("Pack de 50 globos metalizados dorados");
            globosDorados.setCategoria(globos);
            globosDorados.setPrecioCompra(new BigDecimal("15.00"));
            globosDorados.setPrecioVenta(new BigDecimal("25.00"));
            globosDorados.setStockActual(20);
            globosDorados.setStockMinimo(10);
            globosDorados.setStockMaximo(50);
            globosDorados.setEstado("Activo");
            globosDorados.setCreatedBy(admin);
            productoRepository.save(globosDorados);
        }

        if (cuadernos != null && admin != null) {
            Producto cuadernoA4 = new Producto();
            cuadernoA4.setCodigoProducto("CUA001");
            cuadernoA4.setNombreProducto("Cuaderno A4 100 hojas");
            cuadernoA4.setDescripcion("Cuaderno universitario A4 con 100 hojas");
            cuadernoA4.setCategoria(cuadernos);
            cuadernoA4.setPrecioCompra(new BigDecimal("5.00"));
            cuadernoA4.setPrecioVenta(new BigDecimal("8.00"));
            cuadernoA4.setStockActual(50);
            cuadernoA4.setStockMinimo(20);
            cuadernoA4.setStockMaximo(100);
            cuadernoA4.setEstado("Activo");
            cuadernoA4.setCreatedBy(admin);
            productoRepository.save(cuadernoA4);
        }
    }

    private void crearProveedoresEjemplo() {
        Proveedor proveedor1 = new Proveedor();
        proveedor1.setRazonSocial("Distribuidora ABC S.A.C.");
        proveedor1.setRuc("20123456789");
        proveedor1.setDireccion("Av. Industrial 123, Lima");
        proveedor1.setTelefono("999888777");
        proveedor1.setEmail("ventas@distribuidoraabc.com");
        proveedor1.setEstado("Activo");
        proveedorRepository.save(proveedor1);

        Proveedor proveedor2 = new Proveedor();
        proveedor2.setRazonSocial("Importadora XYZ E.I.R.L.");
        proveedor2.setRuc("20567890123");
        proveedor2.setDireccion("Jr. Comercio 456, Callao");
        proveedor2.setTelefono("888777666");
        proveedor2.setEmail("info@importadoraxyz.com");
        proveedor2.setEstado("Activo");
        proveedorRepository.save(proveedor2);
    }

    private void crearClientesEjemplo() {
        Cliente cliente1 = new Cliente();
        cliente1.setNombreCliente("Juan Pérez García");
        cliente1.setTipoDocumento(Cliente.TipoDocumento.DNI);
        cliente1.setNumeroDocumento("12345678");
        cliente1.setDireccion("Av. Arequipa 123, Lima");
        cliente1.setTelefono("999888777");
        cliente1.setEmail("juan.perez@email.com");
        cliente1.setEstado("Activo");
        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNombreCliente("Empresa ABC S.A.C.");
        cliente2.setTipoDocumento(Cliente.TipoDocumento.RUC);
        cliente2.setNumeroDocumento("20123456789");
        cliente2.setDireccion("Av. Javier Prado 789, Lima");
        cliente2.setTelefono("777666555");
        cliente2.setEmail("ventas@empresaabc.com");
        cliente2.setEstado("Activo");
        clienteRepository.save(cliente2);
    }
}