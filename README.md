# Sistema de Gestión de Biblioteca - PointerFaz

Sistema completo de gestión de biblioteca desarrollado en Java con interfaz gráfica Swing y base de datos MySQL.

## 🚀 Características

- **Gestión de Usuarios**: Estudiantes, Estudiantes Graduados y Profesores
- **Gestión de Libros**: CRUD completo con información detallada
- **Sistema de Préstamos**: Control de préstamos, devoluciones y renovaciones
- **Base de Datos MySQL**: Persistencia completa con arquitectura DAO
- **Interfaz Gráfica**: Swing con Look and Feel Nimbus
- **Arquitectura MVC**: Modelo-Vista-Controlador

## 📋 Requisitos

- **Java 17** o superior
- **Maven 3.6+**
- **IntelliJ IDEA Community** (para ejecución final)
- **VS Code** con Extension Pack for Java (para desarrollo)

## Desarrollo en VS Code

1. Abre el proyecto en VS Code
2. Las extensiones de Java se instalarán automáticamente
3. Usa GitHub Copilot para asistencia de IA
4. Desarrolla tu aplicación en `src/main/java/com/pointerfaz/`

## Comandos Útiles

### Compilar

- **Java JDK 21** o superior
- **MySQL 8.0** o superior
- **Maven** (opcional, el proyecto incluye dependencias)

## 🗄️ Configuración de Base de Datos

### 1. Crear la base de datos

```sql
CREATE DATABASE biblioteca_pointerfaz;
USE biblioteca_pointerfaz;
```

### 2. Ejecutar el script de creación de tablas

El proyecto incluye el script SQL completo en `database/schema.sql` que crea:
- Tabla `personas`
- Tabla `estudiantes`
- Tabla `estudiantes_graduados`
- Tabla `profesores`
- Tabla `libros`
- Tabla `prestamos`

### 3. Configurar conexión

Edita el archivo `src/main/java/com/pointerfaz/db/ConnectionDB.java`:

```java
private static final String URL = "jdbc:mysql://127.0.0.1:3306/biblioteca_pointerfaz";
private static final String USER = "root";
private static final String PASSWORD = "";
```

## ⚙️ Instalación y Ejecución

### Opción 1: Compilación Manual

```bash
# Compilar
javac -encoding UTF-8 -cp "lib\mysql-connector-j-8.0.33.jar" -d target/classes -sourcepath src/main/java (Get-ChildItem -Path "src/main/java" -Filter "*.java" -Recurse | Select-Object -ExpandProperty FullName)

# Ejecutar
java -cp "target/classes;lib\mysql-connector-j-8.0.33.jar" com.pointerfaz.Main
```

### Opción 2: Maven (si tienes Maven instalado)

```bash
mvn compile
mvn exec:java
```

## 📁 Estructura del Proyecto

```
sistema-biblioteca-pointerfaz/
├── src/
│   └── main/
│       └── java/
│           └── com/pointerfaz/
│               ├── Main.java
│               ├── controlador/
│               │   ├── LibroControladorNuevo.java
│               │   ├── PrestamoControladorNuevo.java
│               │   └── UsuarioControladorNuevo.java
│               ├── dao/
│               │   ├── LibroDAO.java
│               │   ├── PrestamoDAO.java
│               │   └── UsuarioDAO.java
│               ├── db/
│               │   └── ConnectionDB.java
│               ├── modelo/
│               │   ├── Persona.java
│               │   ├── Estudiante.java
│               │   ├── EstudianteGraduado.java
│               │   ├── Profesor.java
│               │   ├── Libro.java
│               │   └── Prestamo.java
│               ├── vista/
│               │   ├── BibliotecaMainFrame.java
│               │   ├── LoginFrame.java
│               │   ├── LibroDialog.java
│               │   ├── PrestamoDialog.java
│               │   └── UsuarioDialog.java
│               └── util/
│                   └── Constantes.java
├── lib/
│   └── mysql-connector-j-8.0.33.jar
├── pom.xml
└── README.md
```

## 👥 Usuarios de Prueba

Una vez ejecutado el script de datos iniciales, puedes usar:

**Estudiantes:**
- Email: `juan.perez@universidad.edu` / Password: `pass123`
- Email: `maria.garcia@universidad.edu` / Password: `pass123`

**Profesores:**
- Email: `carlos.rodriguez@universidad.edu` / Password: `pass123`
- Email: `ana.martinez@universidad.edu` / Password: `pass123`

## 🛠️ Tecnologías Utilizadas

- **Lenguaje**: Java 21
- **GUI**: Java Swing con Nimbus Look and Feel
- **Base de Datos**: MySQL 8
- **Driver**: MySQL Connector/J 8.0.33
- **Arquitectura**: MVC (Modelo-Vista-Controlador)
- **Patrón**: DAO (Data Access Object)

## 📝 Funcionalidades

### Gestión de Usuarios
- Registro de Estudiantes, Estudiantes Graduados y Profesores
- Búsqueda y filtrado de usuarios
- Edición y eliminación de usuarios
- Sistema de autenticación

### Gestión de Libros
- Agregar, editar y eliminar libros
- Búsqueda por título, autor, ISBN
- Control de disponibilidad
- Clasificación por categorías

### Sistema de Préstamos
- Registrar préstamos
- Devoluciones
- Renovaciones
- Control de fechas y multas
- Historial completo

## 👨‍💻 Autor

**Rex0024**
- GitHub: [@Rex0024](https://github.com/Rex0024)

## 📄 Licencia

Este proyecto es de uso educativo.
