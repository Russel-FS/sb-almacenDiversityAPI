

-- en esta bd solo se agregó los insert y encryptacion de contraseñas
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
SHOW COLUMNS FROM Usuarios;

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