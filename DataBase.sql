DROP DATABASE IF EXISTS diversity_inventory;

CREATE DATABASE IF NOT EXISTS diversity_inventory;

USE diversity_inventory;

-- Tabla de Roles
CREATE TABLE Roles (
    ID_Rol BIGINT AUTO_INCREMENT PRIMARY KEY,
    Nombre_Rol VARCHAR(50) NOT NULL UNIQUE,
    Descripcion TEXT,
    Estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
    Fecha_Creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT UQ_Rol_Nombre UNIQUE (Nombre_Rol)
);

-- Tabla de Rubros
CREATE TABLE Rubros (
    ID_Rubro BIGINT AUTO_INCREMENT PRIMARY KEY,
    Nombre VARCHAR(80) NOT NULL,
    Code VARCHAR(50) NOT NULL,
    Descripcion VARCHAR(255),
    Estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
    PublicId VARCHAR(100),
    ImagenUrl VARCHAR(255),
    Fecha_Creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    Fecha_Modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT UQ_Rubros_Code_Nombre UNIQUE (Code, Nombre)
);

-- Tabla de Usuarios
CREATE TABLE Usuarios (
    ID_Usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    Nombre_Usuario VARCHAR(50) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Nombre_Completo VARCHAR(100) NOT NULL,
    ID_Rol BIGINT NOT NULL,
    ID_Rubro BIGINT NOT NULL,
    Contraseña VARCHAR(255) NOT NULL,
    UrlImagen VARCHAR(255),
    PublicId VARCHAR(100),
    Estado ENUM(
        'Activo',
        'Inactivo',
        'Bloqueado'
    ) DEFAULT 'Activo',
    Ultimo_Acceso DATETIME,
    Fecha_Creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    Fecha_Modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (ID_Rol) REFERENCES Roles (ID_Rol),
    FOREIGN KEY (ID_Rubro) REFERENCES Rubros (ID_Rubro),
    CONSTRAINT UQ_Usuario_Nombre UNIQUE (Nombre_Usuario),
    CONSTRAINT UQ_Usuario_Email UNIQUE (Email)
);

-- Tabla de Categorías
CREATE TABLE Categorias (
    ID_Categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    ID_Rubro BIGINT NOT NULL,
    Nombre_Categoria VARCHAR(100) NOT NULL,
    Descripcion TEXT,
    Estado ENUM(
        'Activo',
        'Inactivo',
        'Eliminado'
    ) DEFAULT 'Activo',
    Fecha_Creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    Fecha_Modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_by BIGINT,
    FOREIGN KEY (ID_Rubro) REFERENCES Rubros (ID_Rubro),
    FOREIGN KEY (created_by) REFERENCES Usuarios (ID_Usuario),
    FOREIGN KEY (updated_by) REFERENCES Usuarios (ID_Usuario),
    CONSTRAINT UQ_Categoria_Nombre_Rubro UNIQUE (Nombre_Categoria, ID_Rubro)
);

-- Tabla de Productos
CREATE TABLE Productos (
    ID_Producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    Codigo_Producto VARCHAR(50) NOT NULL,
    Nombre_Producto VARCHAR(100) NOT NULL,
    Descripcion TEXT,
    ID_Categoria BIGINT NOT NULL,
    Precio_Compra DECIMAL(10, 2) NOT NULL,
    Precio_Venta DECIMAL(10, 2) NOT NULL,
    Stock_Actual INT NOT NULL DEFAULT 0,
    Stock_Minimo INT NOT NULL DEFAULT 0,
    Stock_Maximo INT NOT NULL DEFAULT 0,
    url_imagen VARCHAR(255),
    public_id VARCHAR(100),
    Estado ENUM(
        'Activo',
        'Inactivo',
        'Agotado',
        'Eliminado'
    ) DEFAULT 'Activo',
    Fecha_Creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    Fecha_Modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by BIGINT NOT NULL,
    updated_by BIGINT,
    FOREIGN KEY (ID_Categoria) REFERENCES Categorias (ID_Categoria),
    FOREIGN KEY (created_by) REFERENCES Usuarios (ID_Usuario),
    FOREIGN KEY (updated_by) REFERENCES Usuarios (ID_Usuario),
    CONSTRAINT UQ_Producto_Codigo UNIQUE (Codigo_Producto)
);

-- Tabla de Proveedores
CREATE TABLE Proveedores (
    ID_Proveedor BIGINT AUTO_INCREMENT PRIMARY KEY,
    Razon_Social VARCHAR(100) NOT NULL,
    RUC VARCHAR(11) NOT NULL,
    Direccion TEXT,
    Telefono VARCHAR(20),
    Email VARCHAR(100),
    Estado ENUM('Activo', 'Inactivo') DEFAULT 'Activo',
    Fecha_Creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    Fecha_Modificacion DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT UQ_Proveedor_RUC UNIQUE (RUC),
    CONSTRAINT UQ_Proveedor_Email UNIQUE (Email)
);

-- Tabla de Entradas
CREATE TABLE Entradas (
    ID_Entrada BIGINT AUTO_INCREMENT PRIMARY KEY,
    Numero_Factura VARCHAR(50) NOT NULL,
    ID_Proveedor BIGINT NOT NULL,
    Fecha_Entrada DATETIME DEFAULT CURRENT_TIMESTAMP,
    Costo_Total DECIMAL(10, 2) NOT NULL CHECK (Costo_Total >= 0),
    Estado ENUM(
        'Pendiente',
        'Completado',
        'Anulado'
    ) DEFAULT 'Pendiente',
    ID_Usuario_Registro BIGINT NOT NULL,
    ID_Usuario_Aprobacion BIGINT,
    Fecha_Aprobacion DATETIME,
    Observaciones TEXT,
    FOREIGN KEY (ID_Proveedor) REFERENCES Proveedores (ID_Proveedor),
    FOREIGN KEY (ID_Usuario_Registro) REFERENCES Usuarios (ID_Usuario),
    FOREIGN KEY (ID_Usuario_Aprobacion) REFERENCES Usuarios (ID_Usuario),
    CONSTRAINT UQ_Entrada_Factura UNIQUE (Numero_Factura, ID_Proveedor)
);

-- Tabla de Detalle_Entrada
CREATE TABLE Detalle_Entrada (
    ID_Detalle_Entrada BIGINT AUTO_INCREMENT PRIMARY KEY,
    ID_Entrada BIGINT NOT NULL,
    ID_Producto BIGINT NOT NULL,
    Cantidad INT NOT NULL CHECK (Cantidad > 0),
    Precio_Unitario DECIMAL(10, 2) NOT NULL CHECK (Precio_Unitario >= 0),
    Subtotal DECIMAL(10, 2) NOT NULL CHECK (Subtotal >= 0),
    ID_Usuario_Registro BIGINT NOT NULL,
    Fecha_Registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    Estado ENUM('Activo', 'Anulado') DEFAULT 'Activo',
    FOREIGN KEY (ID_Entrada) REFERENCES Entradas (ID_Entrada),
    FOREIGN KEY (ID_Producto) REFERENCES Productos (ID_Producto),
    FOREIGN KEY (ID_Usuario_Registro) REFERENCES Usuarios (ID_Usuario),
    CONSTRAINT UQ_Detalle_Entrada_Producto UNIQUE (ID_Entrada, ID_Producto)
);

-- Tabla de Salidas
CREATE TABLE Salidas (
    ID_Salida BIGINT AUTO_INCREMENT PRIMARY KEY,
    Numero_Documento VARCHAR(50) NOT NULL,
    Tipo_Documento ENUM(
        'Boleta',
        'Factura',
        'Nota de Venta'
    ) NOT NULL,
    Fecha_Salida DATETIME DEFAULT CURRENT_TIMESTAMP,
    Motivo_Salida VARCHAR(100),
    Total_Venta DECIMAL(10, 2) NOT NULL CHECK (Total_Venta >= 0),
    Estado ENUM(
        'Pendiente',
        'Completado',
        'Anulado'
    ) DEFAULT 'Pendiente',
    ID_Usuario_Registro BIGINT NOT NULL,
    ID_Usuario_Aprobacion BIGINT,
    Fecha_Aprobacion DATETIME,
    Observaciones TEXT,
    FOREIGN KEY (ID_Usuario_Registro) REFERENCES Usuarios (ID_Usuario),
    FOREIGN KEY (ID_Usuario_Aprobacion) REFERENCES Usuarios (ID_Usuario),
    CONSTRAINT UQ_Salida_Documento UNIQUE (
        Numero_Documento,
        Tipo_Documento
    )
);

-- Tabla de Detalle_Salida
CREATE TABLE Detalle_Salida (
    ID_Detalle_Salida BIGINT AUTO_INCREMENT PRIMARY KEY,
    ID_Salida BIGINT NOT NULL,
    ID_Producto BIGINT NOT NULL,
    Cantidad INT NOT NULL CHECK (Cantidad > 0),
    Precio_Unitario DECIMAL(10, 2) NOT NULL CHECK (Precio_Unitario >= 0),
    Subtotal DECIMAL(10, 2) NOT NULL CHECK (Subtotal >= 0),
    ID_Usuario_Registro BIGINT NOT NULL,
    Fecha_Registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    Estado ENUM('Activo', 'Anulado') DEFAULT 'Activo',
    FOREIGN KEY (ID_Salida) REFERENCES Salidas (ID_Salida),
    FOREIGN KEY (ID_Producto) REFERENCES Productos (ID_Producto),
    FOREIGN KEY (ID_Usuario_Registro) REFERENCES Usuarios (ID_Usuario),
    CONSTRAINT UQ_Detalle_Salida_Producto UNIQUE (ID_Salida, ID_Producto)
);

-- Vista de Inventario
CREATE VIEW Vista_Inventario AS
SELECT
    p.ID_Producto,
    p.Codigo_Producto,
    p.Nombre_Producto,
    p.Descripcion,
    c.Nombre_Categoria,
    r.Nombre,
    p.Stock_Actual,
    p.Stock_Minimo,
    p.Stock_Maximo,
    p.Precio_Compra,
    p.Precio_Venta,
    (
        p.Stock_Actual * p.Precio_Compra
    ) as Valor_Total_Inventario,
    (
        p.Stock_Actual * p.Precio_Venta
    ) as Valor_Total_Venta,
    CASE
        WHEN p.Stock_Actual <= p.Stock_Minimo THEN 'Stock Bajo'
        WHEN p.Stock_Actual >= p.Stock_Maximo THEN 'Stock Alto'
        ELSE 'Stock Normal'
    END as Estado_Stock,
    p.Estado as Estado_Producto,
    p.Fecha_Creacion,
    p.Fecha_Modificacion as Ultima_Actualizacion
FROM
    Productos p
    INNER JOIN Categorias c ON p.ID_Categoria = c.ID_Categoria
    INNER JOIN Rubros r ON c.ID_Rubro = r.ID_Rubro
WHERE
    p.Estado != 'Inactivo';

-- Inserción de roles básicos
INSERT INTO
    Roles (Nombre_Rol, Descripcion)
VALUES (
        'Administrador',
        'Acceso total al sistema'
    ),
    (
        'Supervisor',
        'Puede aprobar entradas y salidas'
    ),
    (
        'Operador',
        'Puede registrar entradas y salidas'
    );
    -- Inserccion de rubros 
INSERT INTO Rubros (Nombre, Code, Descripcion, Estado)
VALUES
    ('Piñatería y Artículos de Fiesta', 'PIN-FIE', 'Productos para celebraciones, piñatas, globos, decoración y artículos de fiesta en general.', 'Activo'),
    ('Librería y Útiles de Oficina', 'LIB-OFI', 'Artículos de escritura, papelería, cuadernos, archivadores y suministros de oficina.', 'Activo'),
    ('Sistemas de Seguridad y CCTV', 'SEG-CAM', 'Cámaras de seguridad, DVRs, NVRs y accesorios de vigilancia electrónica.', 'Activo')
ON DUPLICATE KEY UPDATE Nombre=VALUES(Nombre); 

--inserccion de usuarios
INSERT INTO Usuarios (Nombre_Usuario, Email, Nombre_Completo, ID_Rol, ID_Rubro, Contraseña, Estado)
VALUES
    (
        'bfuertes', 
        'betzabet.fuertes@jcdiversity.com', 
        'Betzabet Fuertes Apaza', 
        1, -- ID_Rol: 1 (Administrador)
        1, -- ID_Rubro: 1 (Asignada al rubro principal, Piñatería)
        'adminpass123', -- Contraseña temporal
        'Activo'
    ),
    (
        'pcanaquiri', 
        'priscila.canaquiri@jcdiversity.com', 
        'Priscila Canaquiri Amias', 
        2, -- ID_Rol: 2 (Supervisor)
        2, -- ID_Rubro: 2 (Asignada a Librería)
        'superpass123', -- Contraseña temporal
        'Activo'
    ),
    (
        'rflores', 
        'russel.flores@jcdiversity.com', 
        'Russel Flores Solano', 
        2, -- ID_Rol: 2 (Supervisor)
        3, -- ID_Rubro: 3 (Asignado a Cámaras de Seguridad)
        'superpass456', -- Contraseña temporal
        'Activo'
    ),
    (
        'gmalca', 
        'guillermo.malca@jcdiversity.com', 
        'Guillermo Malca Tucto', 
        3, -- ID_Rol: 3 (Operador)
        1, -- ID_Rubro: 1 (Asignado a Piñatería)
        'operadorpass123', -- Contraseña temporal
        'Activo'
    ),
    (
        'spalacios', 
        'sahel.palacios@jcdiversity.com', 
        'Sahel Palacios Paucar', 
        3, -- ID_Rol: 3 (Operador)
        2, -- ID_Rubro: 2 (Asignado a Librería)
        'operadorpass456', -- Contraseña temporal
        'Activo'
    );
    
    USE diversity_inventory;

--actualizacion de usuarios
UPDATE Usuarios 
SET Contraseña = '$2a$12$PmazmUDQucTJSXmG6r0zaeXwvBwN7uiVjbVvV1hXE0ezbKobOfc/q' -- Contraseña: adminpass123
WHERE Email = 'betzabet.fuertes@jcdiversity.com';

UPDATE Usuarios 
SET Contraseña = '$2a$12$3uPXbC.tCEMnMkrNi7AM4e4rCjzvhB68g/4NSPCd1RGdvKwGu66uW' -- Contraseña: superpass123
WHERE Email = 'priscila.canaquiri@jcdiversity.com';

UPDATE Usuarios 
SET Contraseña = '$2a$10$7Z8q.8Hn.X1E8c.5y9v0q.eG6b2q.3f.l.9K8m.p.b.o3h7H4n.c' -- Contraseña: superpass456
WHERE Email = 'russel.flores@jcdiversity.com';

UPDATE Usuarios 
SET Contraseña = '$2a$12$SMj.iAPTbyp6BMYcz6LoXuygc.fzDrHwpn0aHr4DPpfJZT720yWsq' -- Contraseña: operadorpass123
WHERE Email = 'guillermo.malca@jcdiversity.com';

UPDATE Usuarios 
SET Contraseña = '$2a$12$nzNXLGWTBLK.xprKJj3QQOw/oI45zpNcw4bLTIhM1lzhcRz8yqMte' -- Contraseña: operadorpass456
WHERE Email = 'sahel.palacios@jcdiversity.com';

----- MALCA -----------


-- Inserción de Categorías
INSERT INTO Categorias (ID_Rubro, Nombre_Categoria, Descripcion, created_by)
VALUES
    (1, 'Globos y Decoración', 'Globos de látex, metálicos, números y letras.', 4), -- Rubro: Piñatería, Creado por: gmalca (Operador)
    (1, 'Velas y Toppers', 'Velas de cumpleaños, bengalas y toppers para tortas.', 4),
    (2, 'Cuadernos y Agendas', 'Cuadernos escolares, universitarios y agendas ejecutivas.', 5), -- Rubro: Librería, Creado por: spalacios (Operador)
    (2, 'Escritura y Corrección', 'Lapiceros, lápices, correctores y marcadores.', 5),
    (3, 'Cámaras IP', 'Cámaras de vigilancia con conexión a red.', 3), -- Rubro: Seguridad, Creado por: rflores (Supervisor)
    (3, 'Kits de Videovigilancia', 'Paquetes completos de DVR/NVR con cámaras y accesorios.', 3);

-- Inserción de Productos
INSERT INTO Productos (Codigo_Producto, Nombre_Producto, Descripcion, ID_Categoria, Precio_Compra, Precio_Venta, Stock_Actual, Stock_Minimo, Stock_Maximo, created_by)
VALUES
    ('GLO-LAT-001', 'Bolsa de Globos de Látex #9 (50 un.)', 'Bolsa de 50 globos de colores surtidos.', 1, 5.00, 8.50, 100, 20, 200, 4),
    ('TOP-NUM-005', 'Topper Dorado Número 5', 'Topper para torta con el número 5, acabado dorado brillante.', 2, 2.50, 5.00, 50, 10, 100, 4),
    ('CUA-COL-001', 'Cuaderno College A4 Cuadriculado', 'Cuaderno A4 con 100 hojas cuadriculadas.', 3, 4.00, 7.00, 200, 50, 500, 5),
    ('CAM-IP-WIFI-01', 'Cámara IP Wifi 1080p', 'Cámara de seguridad Wifi con visión nocturna y audio bidireccional.', 5, 80.00, 150.00, 30, 5, 50, 3);