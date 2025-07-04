# Sistema de Inventario Diversity

## Descripción

Sistema de gestión de inventario desarrollado en Spring Boot para la empresa Diversity. Permite gestionar productos, categorías, proveedores, clientes, entradas y salidas de inventario.

## Tecnologías Utilizadas

- **Backend**: Spring Boot 3.x
- **Base de Datos**: MySQL 8.0
- **ORM**: Hibernate/JPA
- **Seguridad**: Spring Security con BCrypt
- **Lombok**: Para reducir código boilerplate
- **Maven**: Gestión de dependencias

## Estructura del Proyecto

### Modelos (Entidades)

- **Usuario**: Gestión de usuarios del sistema
- **Rol**: Roles de usuario (Administrador, Supervisor, Operador)
- **Rubro**: Rubros de negocio (Piñatería, Librería, Sistemas de Seguridad)
- **Categoria**: Categorías dentro de cada rubro
- **Producto**: Productos del inventario
- **Proveedor**: Proveedores de productos
- **Cliente**: Clientes para las ventas
- **Entrada**: Entradas de inventario (compras)
- **DetalleEntrada**: Detalles de las entradas
- **Salida**: Salidas de inventario (ventas)
- **DetalleSalida**: Detalles de las salidas

### Capas de la Aplicación

- **Controllers**: Endpoints REST API
- **Services**: Lógica de negocio
- **Repositories**: Acceso a datos
- **DTOs**: Objetos de transferencia de datos
- **Mappers**: Conversión entre entidades y DTOs

## Configuración de la Base de Datos

### Requisitos

- MySQL 8.0 o superior
- Usuario con permisos de creación de base de datos

### Instalación

1. Ejecutar el script `DataBase.sql` en MySQL
2. Configurar las credenciales en `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/diversity_inventory?useSSL=false&serverTimezone=UTC
spring.datasource.username=tu_usuario
spring.datasource.password=tu_password
```

## Endpoints de la API

### Usuarios

- `GET /api/usuarios/listar` - Listar todos los usuarios
- `GET /api/usuarios/buscar/{id}` - Buscar usuario por ID
- `POST /api/usuarios/registrar` - Registrar nuevo usuario
- `PUT /api/usuarios/editar/{id}` - Editar usuario
- `DELETE /api/usuarios/eliminar/{id}` - Eliminar usuario

### Roles

- `GET /api/roles/listar` - Listar todos los roles
- `GET /api/roles/buscar/{id}` - Buscar rol por ID
- `POST /api/roles/registrar` - Registrar nuevo rol
- `PUT /api/roles/editar/{id}` - Editar rol
- `DELETE /api/roles/eliminar/{id}` - Eliminar rol

### Rubros

- `GET /api/rubros/listar` - Listar todos los rubros
- `GET /api/rubros/buscar/{id}` - Buscar rubro por ID
- `POST /api/rubros/registrar` - Registrar nuevo rubro
- `PUT /api/rubros/editar/{id}` - Editar rubro
- `DELETE /api/rubros/eliminar/{id}` - Eliminar rubro

### Categorías

- `GET /api/categorias/listar` - Listar todas las categorías
- `GET /api/categorias/listar-por-rubro/{idRubro}` - Listar categorías por rubro
- `GET /api/categorias/buscar/{id}` - Buscar categoría por ID
- `POST /api/categorias/registrar` - Registrar nueva categoría
- `PUT /api/categorias/editar/{id}` - Editar categoría
- `DELETE /api/categorias/eliminar/{id}` - Eliminar categoría

### Productos

- `GET /api/productos/listar` - Listar todos los productos
- `GET /api/productos/listar-por-categoria/{idCategoria}` - Listar productos por categoría
- `GET /api/productos/listar-stock-bajo` - Listar productos con stock bajo
- `GET /api/productos/buscar/{id}` - Buscar producto por ID
- `GET /api/productos/buscar-por-codigo/{codigo}` - Buscar producto por código
- `POST /api/productos/registrar` - Registrar nuevo producto
- `PUT /api/productos/editar/{id}` - Editar producto
- `DELETE /api/productos/eliminar/{id}` - Eliminar producto

### Proveedores

- `GET /api/proveedores/listar` - Listar todos los proveedores
- `GET /api/proveedores/buscar/{id}` - Buscar proveedor por ID
- `POST /api/proveedores/registrar` - Registrar nuevo proveedor
- `PUT /api/proveedores/editar/{id}` - Editar proveedor
- `DELETE /api/proveedores/eliminar/{id}` - Eliminar proveedor

### Clientes

- `GET /api/clientes/listar` - Listar todos los clientes
- `GET /api/clientes/buscar/{id}` - Buscar cliente por ID
- `POST /api/clientes/registrar` - Registrar nuevo cliente
- `PUT /api/clientes/editar/{id}` - Editar cliente
- `DELETE /api/clientes/eliminar/{id}` - Eliminar cliente

### Entradas

- `GET /api/entradas/listar` - Listar todas las entradas
- `GET /api/entradas/listar-por-proveedor/{idProveedor}` - Listar entradas por proveedor
- `GET /api/entradas/listar-por-estado/{estado}` - Listar entradas por estado
- `GET /api/entradas/buscar/{id}` - Buscar entrada por ID
- `POST /api/entradas/registrar` - Registrar nueva entrada
- `PUT /api/entradas/editar/{id}` - Editar entrada
- `PUT /api/entradas/aprobar/{id}` - Aprobar entrada
- `PUT /api/entradas/anular/{id}` - Anular entrada
- `DELETE /api/entradas/eliminar/{id}` - Eliminar entrada

### Salidas

- `GET /api/salidas/listar` - Listar todas las salidas
- `GET /api/salidas/listar-por-cliente/{idCliente}` - Listar salidas por cliente
- `GET /api/salidas/listar-por-estado/{estado}` - Listar salidas por estado
- `GET /api/salidas/listar-por-tipo-documento/{tipoDocumento}` - Listar salidas por tipo de documento
- `GET /api/salidas/buscar/{id}` - Buscar salida por ID
- `POST /api/salidas/registrar` - Registrar nueva salida
- `PUT /api/salidas/editar/{id}` - Editar salida
- `PUT /api/salidas/aprobar/{id}` - Aprobar salida
- `PUT /api/salidas/anular/{id}` - Anular salida
- `DELETE /api/salidas/eliminar/{id}` - Eliminar salida

## Usuario Administrador por Defecto

Al iniciar la aplicación por primera vez, se creará automáticamente un usuario administrador:

- **Usuario**: admin
- **Email**: admin@diversity.com
- **Contraseña**: admin123
- **Rol**: Administrador
- **Rubro**: Piñatería y Artículos de Fiesta

### Datos Iniciales

El sistema también creará automáticamente:

- **Roles**: Administrador, Supervisor, Operador
- **Rubros**: Piñatería, Librería, Sistemas de Seguridad
- **Categorías**: Piñatas, Globos, Cuadernos
- **Productos**: Ejemplos de productos para cada categoría
- **Proveedores**: 2 proveedores de ejemplo
- **Clientes**: 2 clientes de ejemplo

## Características del Sistema

### Gestión de Inventario

- Control de stock mínimo y máximo
- Alertas de stock bajo
- Cálculo automático de valores de inventario
- Estados de productos (Activo, Inactivo, Agotado, Eliminado)

### Seguridad

- Autenticación con Spring Security
- Encriptación de contraseñas con BCrypt
- Control de acceso basado en roles
- Validación de datos en todas las operaciones

### Validaciones

- Validación de documentos únicos (RUC, DNI, etc.)
- Validación de emails únicos
- Validación de códigos de producto únicos
- Validación de nombres de categoría por rubro

### Auditoría

- Registro de fechas de creación y modificación
- Registro de usuarios que crean y modifican registros
- Estados de auditoría para entradas y salidas

## Instalación y Ejecución

### Prerrequisitos

- Java 17 o superior
- Maven 3.6 o superior
- MySQL 8.0 o superior

### Pasos de Instalación

1. Clonar el repositorio
2. Configurar la base de datos ejecutando `DataBase.sql`
3. Configurar las credenciales en `application.properties`
4. Ejecutar `mvn clean install`
5. Ejecutar `mvn spring-boot:run`

### Acceso a la Aplicación

- **URL**: http://localhost:8081
- **Puerto**: 8081 (configurable en application.properties)

## Estructura de la Base de Datos

### Tablas Principales

- **Roles**: Gestión de roles de usuario
- **Rubros**: Rubros de negocio
- **Usuarios**: Usuarios del sistema
- **Categorias**: Categorías de productos
- **Productos**: Productos del inventario
- **Proveedores**: Proveedores de productos
- **Clientes**: Clientes para ventas
- **Entradas**: Entradas de inventario
- **Detalle_Entrada**: Detalles de entradas
- **Salidas**: Salidas de inventario
- **Detalle_Salida**: Detalles de salidas

### Vistas

- **Vista_Inventario**: Vista consolidada del inventario con cálculos automáticos

## Contribución

Para contribuir al proyecto:

1. Crear una rama para tu feature
2. Implementar los cambios
3. Crear un pull request
4. Revisar y aprobar los cambios

## Licencia

Este proyecto es propiedad de Diversity y está destinado para uso interno de la empresa.
