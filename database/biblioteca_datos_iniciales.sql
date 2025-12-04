-- ========================================
-- SCRIPT DE DATOS INICIALES
-- Sistema de Gestión de Biblioteca PointerFaz
-- ========================================

USE biblioteca_pointerfaz;

-- Limpiar datos existentes (opcional)
-- SET FOREIGN_KEY_CHECKS = 0;
-- TRUNCATE TABLE prestamos;
-- TRUNCATE TABLE estudiantes_graduados;
-- TRUNCATE TABLE profesores;
-- TRUNCATE TABLE estudiantes;
-- TRUNCATE TABLE libros;
-- TRUNCATE TABLE personas;
-- SET FOREIGN_KEY_CHECKS = 1;

-- ========================================
-- INSERTAR ESTUDIANTES
-- ========================================

-- Estudiante 1: Juan Pérez
INSERT INTO personas (nombres, apellidos, email, telefono, tipo_usuario, password) 
VALUES ('Juan', 'Pérez', 'juan.perez@universidad.edu', '555-0101', 'estudiante', 'pass123');

INSERT INTO estudiantes (id, codigo, carrera, semestre, estado) 
VALUES (LAST_INSERT_ID(), 'EST2024001', 'Ingeniería de Sistemas', 5, 'activo');

-- Estudiante 2: María García
INSERT INTO personas (nombres, apellidos, email, telefono, tipo_usuario, password) 
VALUES ('María', 'García', 'maria.garcia@universidad.edu', '555-0102', 'estudiante', 'pass123');

INSERT INTO estudiantes (id, codigo, carrera, semestre, estado) 
VALUES (LAST_INSERT_ID(), 'EST2024002', 'Ingeniería de Software', 3, 'activo');

-- Estudiante 3: Pedro Martínez (Graduado)
INSERT INTO personas (nombres, apellidos, email, telefono, tipo_usuario, password) 
VALUES ('Pedro', 'Martínez', 'pedro.martinez@universidad.edu', '555-0103', 'estudiante', 'pass123');

INSERT INTO estudiantes (id, codigo, carrera, semestre, estado) 
VALUES (LAST_INSERT_ID(), 'EST2020001', 'Ingeniería de Sistemas', 10, 'graduado');

INSERT INTO estudiantes_graduados (id, titulo_obtenido, fecha_graduacion, programa_posgrado, empresa_trabajo) 
VALUES (LAST_INSERT_ID(), 'Ingeniero de Sistemas', '2023-12-15', 'Maestría en Ciencias de la Computación', 'TechCorp Solutions');

-- Estudiante 4: Laura Rodríguez
INSERT INTO personas (nombres, apellidos, email, telefono, tipo_usuario, password) 
VALUES ('Laura', 'Rodríguez', 'laura.rodriguez@universidad.edu', '555-0104', 'estudiante', 'pass123');

INSERT INTO estudiantes (id, codigo, carrera, semestre, estado) 
VALUES (LAST_INSERT_ID(), 'EST2024003', 'Ingeniería Informática', 6, 'activo');

-- ========================================
-- INSERTAR PROFESORES
-- ========================================

-- Profesor 1: Carlos Rodríguez
INSERT INTO personas (nombres, apellidos, email, telefono, tipo_usuario, password) 
VALUES ('Carlos', 'Rodríguez', 'carlos.rodriguez@universidad.edu', '555-0201', 'profesor', 'pass123');

INSERT INTO profesores (id, codigo_empleado, departamento, especialidad, tipo_contrato, titulo, anios_experiencia) 
VALUES (LAST_INSERT_ID(), 'PROF001', 'Ciencias de la Computación', 'Bases de Datos', 'tiempo_completo', 'PhD en Ciencias de la Computación', 15);

-- Profesor 2: Ana Martínez
INSERT INTO personas (nombres, apellidos, email, telefono, tipo_usuario, password) 
VALUES ('Ana', 'Martínez', 'ana.martinez@universidad.edu', '555-0202', 'profesor', 'pass123');

INSERT INTO profesores (id, codigo_empleado, departamento, especialidad, tipo_contrato, titulo, anios_experiencia) 
VALUES (LAST_INSERT_ID(), 'PROF002', 'Ingeniería de Software', 'Programación', 'tiempo_completo', 'Magíster en Ingeniería de Software', 10);

-- ========================================
-- INSERTAR LIBROS
-- ========================================

INSERT INTO libros (isbn, titulo, autor, editorial, categoria, anio_publicacion, numero_paginas, ubicacion, estado, es_referencia) VALUES
('978-0134685991', 'Effective Java', 'Joshua Bloch', 'Addison-Wesley', 'Programación', 2018, 412, 'A-101', 'disponible', FALSE),
('978-0132350884', 'Clean Code', 'Robert C. Martin', 'Prentice Hall', 'Programación', 2008, 464, 'A-102', 'disponible', FALSE),
('978-0201633610', 'Design Patterns', 'Gang of Four', 'Addison-Wesley', 'Programación', 1994, 395, 'A-103', 'disponible', TRUE),
('978-0137081073', 'The Clean Coder', 'Robert C. Martin', 'Prentice Hall', 'Desarrollo Profesional', 2011, 256, 'B-201', 'disponible', FALSE),
('978-0596007126', 'Head First Design Patterns', 'Freeman & Freeman', 'O\'Reilly Media', 'Programación', 2004, 694, 'A-104', 'disponible', FALSE),
('978-0321125217', 'Domain-Driven Design', 'Eric Evans', 'Addison-Wesley', 'Arquitectura', 2003, 560, 'B-202', 'disponible', TRUE),
('978-0134494166', 'Clean Architecture', 'Robert C. Martin', 'Prentice Hall', 'Arquitectura', 2017, 432, 'B-203', 'disponible', FALSE),
('978-0132143011', 'Introducción a los Algoritmos', 'Cormen et al.', 'MIT Press', 'Algoritmos', 2009, 1312, 'C-301', 'disponible', TRUE);

-- ========================================
-- INSERTAR PRÉSTAMOS
-- ========================================

-- Préstamo 1: Juan Pérez - Effective Java (Activo)
INSERT INTO prestamos (libro_id, usuario_id, tipo_usuario, fecha_prestamo, fecha_devolucion_esperada, estado) 
VALUES (
    (SELECT id FROM libros WHERE isbn = '978-0134685991'),
    (SELECT id FROM personas WHERE email = 'juan.perez@universidad.edu'),
    'estudiante',
    CURDATE() - INTERVAL 5 DAY,
    CURDATE() + INTERVAL 10 DAY,
    'activo'
);

-- Préstamo 2: María García - Clean Code (Activo)
INSERT INTO prestamos (libro_id, usuario_id, tipo_usuario, fecha_prestamo, fecha_devolucion_esperada, estado) 
VALUES (
    (SELECT id FROM libros WHERE isbn = '978-0132350884'),
    (SELECT id FROM personas WHERE email = 'maria.garcia@universidad.edu'),
    'estudiante',
    CURDATE() - INTERVAL 3 DAY,
    CURDATE() + INTERVAL 12 DAY,
    'activo'
);

-- Préstamo 3: Carlos Rodríguez (Profesor) - Domain-Driven Design (Activo)
INSERT INTO prestamos (libro_id, usuario_id, tipo_usuario, fecha_prestamo, fecha_devolucion_esperada, estado) 
VALUES (
    (SELECT id FROM libros WHERE isbn = '978-0321125217'),
    (SELECT id FROM personas WHERE email = 'carlos.rodriguez@universidad.edu'),
    'profesor',
    CURDATE() - INTERVAL 7 DAY,
    CURDATE() + INTERVAL 23 DAY,
    'activo'
);

-- Préstamo 4: Laura Rodríguez - The Clean Coder (Devuelto)
INSERT INTO prestamos (libro_id, usuario_id, tipo_usuario, fecha_prestamo, fecha_devolucion_esperada, fecha_devolucion_real, estado) 
VALUES (
    (SELECT id FROM libros WHERE isbn = '978-0137081073'),
    (SELECT id FROM personas WHERE email = 'laura.rodriguez@universidad.edu'),
    'estudiante',
    CURDATE() - INTERVAL 20 DAY,
    CURDATE() - INTERVAL 5 DAY,
    CURDATE() - INTERVAL 3 DAY,
    'devuelto'
);

-- Préstamo 5: Pedro Martínez - Head First Design Patterns (Devuelto)
INSERT INTO prestamos (libro_id, usuario_id, tipo_usuario, fecha_prestamo, fecha_devolucion_esperada, fecha_devolucion_real, estado) 
VALUES (
    (SELECT id FROM libros WHERE isbn = '978-0596007126'),
    (SELECT id FROM personas WHERE email = 'pedro.martinez@universidad.edu'),
    'estudiante',
    CURDATE() - INTERVAL 30 DAY,
    CURDATE() - INTERVAL 15 DAY,
    CURDATE() - INTERVAL 12 DAY,
    'devuelto'
);

-- Préstamo 6: Ana Martínez (Profesor) - Clean Architecture (Vencido con multa)
INSERT INTO prestamos (libro_id, usuario_id, tipo_usuario, fecha_prestamo, fecha_devolucion_esperada, estado, multa, observaciones) 
VALUES (
    (SELECT id FROM libros WHERE isbn = '978-0134494166'),
    (SELECT id FROM personas WHERE email = 'ana.martinez@universidad.edu'),
    'profesor',
    CURDATE() - INTERVAL 45 DAY,
    CURDATE() - INTERVAL 15 DAY,
    'vencido',
    15.00,
    'Préstamo vencido. Multa: $0.50 por día de retraso.'
);

-- ========================================
-- VERIFICACIÓN DE DATOS
-- ========================================

SELECT '=== RESUMEN DE DATOS INSERTADOS ===' AS '';

SELECT 
    'Personas' AS Tabla,
    COUNT(*) AS Total
FROM personas

UNION ALL

SELECT 
    'Estudiantes',
    COUNT(*)
FROM estudiantes

UNION ALL

SELECT 
    'Estudiantes Graduados',
    COUNT(*)
FROM estudiantes_graduados

UNION ALL

SELECT 
    'Profesores',
    COUNT(*)
FROM profesores

UNION ALL

SELECT 
    'Libros',
    COUNT(*)
FROM libros

UNION ALL

SELECT 
    'Préstamos',
    COUNT(*)
FROM prestamos;

-- Mostrar algunos datos de ejemplo
SELECT '\n=== USUARIOS REGISTRADOS ===' AS '';
SELECT 
    CONCAT(nombres, ' ', apellidos) AS Usuario,
    email,
    tipo_usuario AS Tipo,
    'pass123' AS Password
FROM personas
ORDER BY tipo_usuario, nombres;

SELECT '\n=== LIBROS DISPONIBLES ===' AS '';
SELECT 
    titulo AS Título,
    autor AS Autor,
    categoria AS Categoría,
    estado AS Estado
FROM libros
WHERE estado = 'disponible'
ORDER BY categoria, titulo;

SELECT '\n=== PRÉSTAMOS ACTIVOS ===' AS '';
SELECT 
    l.titulo AS Libro,
    CONCAT(p.nombres, ' ', p.apellidos) AS Usuario,
    pr.fecha_prestamo AS 'Fecha Préstamo',
    pr.fecha_devolucion_esperada AS 'Fecha Devolución',
    pr.estado AS Estado
FROM prestamos pr
JOIN libros l ON pr.libro_id = l.id
JOIN personas p ON pr.usuario_id = p.id
WHERE pr.estado IN ('activo', 'vencido')
ORDER BY pr.fecha_prestamo DESC;

SELECT '\n✅ Datos iniciales cargados exitosamente!' AS Mensaje;
SELECT 'Usa las credenciales mostradas arriba para hacer login en el sistema.' AS Instrucciones;
