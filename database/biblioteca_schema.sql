-- ========================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS
-- Sistema de Gestión de Biblioteca PointerFaz
-- ========================================

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS biblioteca_pointerfaz;
USE biblioteca_pointerfaz;

-- ========================================
-- TABLA: personas
-- Tabla base para todos los usuarios del sistema
-- ========================================
CREATE TABLE IF NOT EXISTS personas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    telefono VARCHAR(20),
    tipo_usuario ENUM('estudiante', 'profesor') NOT NULL,
    password VARCHAR(255) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_tipo_usuario (tipo_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABLA: estudiantes
-- Información específica de estudiantes
-- ========================================
CREATE TABLE IF NOT EXISTS estudiantes (
    id INT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    carrera VARCHAR(100) NOT NULL,
    semestre INT NOT NULL,
    estado ENUM('activo', 'inactivo', 'graduado') DEFAULT 'activo',
    FOREIGN KEY (id) REFERENCES personas(id) ON DELETE CASCADE,
    INDEX idx_codigo (codigo),
    INDEX idx_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABLA: estudiantes_graduados
-- Información adicional para estudiantes graduados
-- ========================================
CREATE TABLE IF NOT EXISTS estudiantes_graduados (
    id INT PRIMARY KEY,
    titulo_obtenido VARCHAR(150) NOT NULL,
    fecha_graduacion DATE NOT NULL,
    programa_posgrado VARCHAR(150),
    empresa_trabajo VARCHAR(150),
    FOREIGN KEY (id) REFERENCES estudiantes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABLA: profesores
-- Información específica de profesores
-- ========================================
CREATE TABLE IF NOT EXISTS profesores (
    id INT PRIMARY KEY,
    codigo_empleado VARCHAR(20) NOT NULL UNIQUE,
    departamento VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100),
    tipo_contrato ENUM('tiempo_completo', 'medio_tiempo', 'hora_catedra') NOT NULL,
    titulo VARCHAR(100),
    anios_experiencia INT DEFAULT 0,
    FOREIGN KEY (id) REFERENCES personas(id) ON DELETE CASCADE,
    INDEX idx_codigo_empleado (codigo_empleado),
    INDEX idx_departamento (departamento)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABLA: libros
-- Catálogo de libros de la biblioteca
-- ========================================
CREATE TABLE IF NOT EXISTS libros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    editorial VARCHAR(100),
    categoria VARCHAR(50) NOT NULL,
    anio_publicacion INT,
    numero_paginas INT,
    ubicacion VARCHAR(50),
    estado ENUM('disponible', 'prestado', 'en_mantenimiento', 'perdido') DEFAULT 'disponible',
    es_referencia BOOLEAN DEFAULT FALSE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_isbn (isbn),
    INDEX idx_titulo (titulo),
    INDEX idx_autor (autor),
    INDEX idx_categoria (categoria),
    INDEX idx_estado (estado)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABLA: prestamos
-- Registro de préstamos de libros
-- ========================================
CREATE TABLE IF NOT EXISTS prestamos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libro_id INT NOT NULL,
    usuario_id INT NOT NULL,
    tipo_usuario ENUM('estudiante', 'profesor') NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion_esperada DATE NOT NULL,
    fecha_devolucion_real DATE,
    estado ENUM('activo', 'devuelto', 'vencido') DEFAULT 'activo',
    observaciones TEXT,
    multa DECIMAL(10,2) DEFAULT 0.00,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (libro_id) REFERENCES libros(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES personas(id) ON DELETE CASCADE,
    INDEX idx_libro_id (libro_id),
    INDEX idx_usuario_id (usuario_id),
    INDEX idx_estado (estado),
    INDEX idx_fecha_prestamo (fecha_prestamo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- VISTAS ÚTILES
-- ========================================

-- Vista para listar todos los estudiantes con su información completa
CREATE OR REPLACE VIEW vista_estudiantes AS
SELECT 
    p.id,
    p.nombres,
    p.apellidos,
    p.email,
    p.telefono,
    e.codigo,
    e.carrera,
    e.semestre,
    e.estado,
    eg.titulo_obtenido,
    eg.fecha_graduacion
FROM personas p
JOIN estudiantes e ON p.id = e.id
LEFT JOIN estudiantes_graduados eg ON e.id = eg.id
WHERE p.tipo_usuario = 'estudiante';

-- Vista para listar todos los profesores con su información completa
CREATE OR REPLACE VIEW vista_profesores AS
SELECT 
    p.id,
    p.nombres,
    p.apellidos,
    p.email,
    p.telefono,
    pr.codigo_empleado,
    pr.departamento,
    pr.especialidad,
    pr.tipo_contrato,
    pr.titulo,
    pr.anios_experiencia
FROM personas p
JOIN profesores pr ON p.id = pr.id
WHERE p.tipo_usuario = 'profesor';

-- Vista para préstamos activos con información completa
CREATE OR REPLACE VIEW vista_prestamos_activos AS
SELECT 
    pr.id,
    l.titulo AS libro_titulo,
    l.isbn,
    p.nombres,
    p.apellidos,
    p.email,
    pr.fecha_prestamo,
    pr.fecha_devolucion_esperada,
    pr.estado,
    DATEDIFF(CURDATE(), pr.fecha_devolucion_esperada) AS dias_retraso
FROM prestamos pr
JOIN libros l ON pr.libro_id = l.id
JOIN personas p ON pr.usuario_id = p.id
WHERE pr.estado IN ('activo', 'vencido');

-- ========================================
-- TRIGGERS
-- ========================================

-- Trigger para actualizar estado del libro cuando se registra un préstamo
DELIMITER //
CREATE TRIGGER after_prestamo_insert
AFTER INSERT ON prestamos
FOR EACH ROW
BEGIN
    UPDATE libros 
    SET estado = 'prestado' 
    WHERE id = NEW.libro_id;
END//
DELIMITER ;

-- Trigger para actualizar estado del libro cuando se devuelve
DELIMITER //
CREATE TRIGGER after_prestamo_update
AFTER UPDATE ON prestamos
FOR EACH ROW
BEGIN
    IF NEW.estado = 'devuelto' AND OLD.estado != 'devuelto' THEN
        UPDATE libros 
        SET estado = 'disponible' 
        WHERE id = NEW.libro_id;
    END IF;
END//
DELIMITER ;

-- ========================================
-- PROCEDIMIENTOS ALMACENADOS
-- ========================================

-- Procedimiento para obtener estadísticas de la biblioteca
DELIMITER //
CREATE PROCEDURE sp_estadisticas_biblioteca()
BEGIN
    SELECT 
        (SELECT COUNT(*) FROM libros) AS total_libros,
        (SELECT COUNT(*) FROM libros WHERE estado = 'disponible') AS libros_disponibles,
        (SELECT COUNT(*) FROM libros WHERE estado = 'prestado') AS libros_prestados,
        (SELECT COUNT(*) FROM personas WHERE tipo_usuario = 'estudiante') AS total_estudiantes,
        (SELECT COUNT(*) FROM personas WHERE tipo_usuario = 'profesor') AS total_profesores,
        (SELECT COUNT(*) FROM prestamos WHERE estado = 'activo') AS prestamos_activos,
        (SELECT COUNT(*) FROM prestamos WHERE estado = 'vencido') AS prestamos_vencidos;
END//
DELIMITER ;

-- ========================================
-- MENSAJE FINAL
-- ========================================
SELECT 'Base de datos biblioteca_pointerfaz creada exitosamente!' AS Mensaje;
SELECT 'Ejecuta ahora el archivo biblioteca_datos_iniciales.sql para cargar datos de prueba.' AS ProximoPaso;
