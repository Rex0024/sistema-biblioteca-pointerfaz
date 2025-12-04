package com.pointerfaz.controlador;

import com.pointerfaz.dao.PrestamoDAO;
import com.pointerfaz.dao.LibroDAO;
import com.pointerfaz.dao.UsuarioDAO;
import com.pointerfaz.modelo.Prestamo;
import com.pointerfaz.modelo.Libro;
import com.pointerfaz.modelo.Persona;
import com.pointerfaz.modelo.Estudiante;
import com.pointerfaz.modelo.Profesor;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Controlador NUEVO para gestionar préstamos de libros
 * USA SOLO DAO - SIN ARRAYLIST PRIVADO
 * Conectado 100% a la base de datos MySQL
 */
public class PrestamoControladorNuevo {
    
    private PrestamoDAO prestamoDAO;
    private LibroDAO libroDAO;
    private UsuarioDAO usuarioDAO;
    
    /**
     * Constructor - inicializa SOLO los DAOs
     */
    public PrestamoControladorNuevo() {
        prestamoDAO = new PrestamoDAO();
        libroDAO = new LibroDAO();
        usuarioDAO = new UsuarioDAO();
    }
    
    /**
     * Crear nuevo préstamo en la BASE DE DATOS
     * @param libroId ID del libro a prestar
     * @param usuarioId ID del usuario que presta
     * @return true si se creó exitosamente, false en caso contrario
     */
    public boolean crearPrestamo(int libroId, int usuarioId) {
        // Verificar que el libro existe y está disponible
        Libro libro = libroDAO.buscarLibro(libroId);
        if (libro == null || !libro.estaDisponible()) {
            return false;
        }
        
        // Verificar que el usuario existe
        Persona usuario = usuarioDAO.buscarUsuario(usuarioId);
        if (usuario == null) {
            return false;
        }
        
        // Verificar límite de préstamos del usuario
        if (!puedePrestarMasLibros(usuarioId)) {
            return false;
        }
        
        // Determinar días de préstamo según tipo de usuario
        int diasPrestamo = obtenerDiasPrestamo(usuario);
        String tipoUsuario = usuario.getClass().getSimpleName();
        
        // Crear nuevo préstamo
        Prestamo nuevoPrestamo = new Prestamo(0, libroId, usuarioId, tipoUsuario, 
                                             LocalDate.now(), LocalDate.now().plusDays(diasPrestamo));
        
        // Agregar a la base de datos
        prestamoDAO.agregarPrestamo(nuevoPrestamo);
        
        // Cambiar estado del libro en la base de datos
        libroDAO.actualizarEstadoLibro(libroId, "Prestado");
        
        return true;
    }
    
    /**
     * Listar todos los préstamos desde la BASE DE DATOS
     * @return lista de préstamos
     */
    public ArrayList<Prestamo> listarPrestamos() {
        return prestamoDAO.listarPrestamos();
    }
    
    /**
     * Buscar préstamo por ID en la BASE DE DATOS
     * @param id ID del préstamo a buscar
     * @return préstamo encontrado o null
     */
    public Prestamo buscarPrestamo(int id) {
        return prestamoDAO.buscarPrestamo(id);
    }
    
    /**
     * Obtener préstamos activos de un usuario desde la BASE DE DATOS
     * @param usuarioId ID del usuario
     * @return lista de préstamos activos
     */
    public ArrayList<Prestamo> obtenerPrestamosUsuario(int usuarioId) {
        ArrayList<Prestamo> prestamosUsuario = new ArrayList<>();
        for (Prestamo prestamo : prestamoDAO.listarPrestamos()) {
            if (prestamo.getUsuarioId() == usuarioId && "Activo".equals(prestamo.getEstado())) {
                prestamosUsuario.add(prestamo);
            }
        }
        return prestamosUsuario;
    }
    
    /**
     * Obtener préstamos de un libro específico desde la BASE DE DATOS
     * @param libroId ID del libro
     * @return lista de préstamos del libro
     */
    public ArrayList<Prestamo> obtenerPrestamosLibro(int libroId) {
        ArrayList<Prestamo> prestamosLibro = new ArrayList<>();
        for (Prestamo prestamo : prestamoDAO.listarPrestamos()) {
            if (prestamo.getLibroId() == libroId) {
                prestamosLibro.add(prestamo);
            }
        }
        return prestamosLibro;
    }
    
    /**
     * Procesar devolución de libro en la BASE DE DATOS
     * @param prestamoId ID del préstamo
     * @return true si se procesó exitosamente
     */
    public boolean procesarDevolucion(int prestamoId) {
        Prestamo prestamo = prestamoDAO.buscarPrestamo(prestamoId);
        
        if (prestamo != null && "Activo".equals(prestamo.getEstado())) {
            // Procesar devolución en la base de datos
            boolean resultado = prestamoDAO.devolverLibro(prestamoId);
            
            if (resultado) {
                // El DAO ya actualiza el estado del libro automáticamente
                return true;
            }
        }
        return false;
    }
    
    /**
     * Renovar préstamo (extender fecha de devolución) en la BASE DE DATOS
     * @param prestamoId ID del préstamo
     * @param diasExtension días adicionales
     * @return true si se renovó exitosamente
     */
    public boolean renovarPrestamo(int prestamoId, int diasExtension) {
        Prestamo prestamo = prestamoDAO.buscarPrestamo(prestamoId);
        
        if (prestamo != null && "Activo".equals(prestamo.getEstado()) && !prestamo.estaVencido()) {
            return prestamoDAO.renovarPrestamo(prestamoId, diasExtension);
        }
        return false;
    }
    
    /**
     * Obtener préstamos vencidos desde la BASE DE DATOS
     * @return lista de préstamos vencidos
     */
    public ArrayList<Prestamo> obtenerPrestamosVencidos() {
        ArrayList<Prestamo> prestamosVencidos = new ArrayList<>();
        for (Prestamo prestamo : prestamoDAO.listarPrestamos()) {
            if (prestamo.estaVencido()) {
                prestamosVencidos.add(prestamo);
            }
        }
        return prestamosVencidos;
    }
    
    /**
     * Obtener préstamos próximos a vencer desde la BASE DE DATOS
     * @param dias días antes del vencimiento
     * @return lista de préstamos próximos a vencer
     */
    public ArrayList<Prestamo> obtenerPrestamosProximosVencer(int dias) {
        ArrayList<Prestamo> prestamosProximos = new ArrayList<>();
        LocalDate fechaLimite = LocalDate.now().plusDays(dias);
        
        for (Prestamo prestamo : prestamoDAO.listarPrestamos()) {
            if ("Activo".equals(prestamo.getEstado()) && 
                prestamo.getFechaDevolucionEsperada().isBefore(fechaLimite)) {
                prestamosProximos.add(prestamo);
            }
        }
        return prestamosProximos;
    }
    
    /**
     * Verificar si un usuario puede prestar más libros
     * @param usuarioId ID del usuario
     * @return true si puede prestar más libros
     */
    public boolean puedePrestarMasLibros(int usuarioId) {
        Persona usuario = usuarioDAO.buscarUsuario(usuarioId);
        if (usuario == null) {
            return false;
        }
        
        int prestamosActivos = obtenerPrestamosUsuario(usuarioId).size();
        int limiteUsuario = obtenerLimitePrestamos(usuario);
        
        return prestamosActivos < limiteUsuario;
    }
    
    /**
     * Obtener límite de préstamos según tipo de usuario
     * @param usuario usuario a evaluar
     * @return límite de préstamos
     */
    private int obtenerLimitePrestamos(Persona usuario) {
        if (usuario instanceof Estudiante) {
            return ((Estudiante) usuario).getLimitePrestamos();
        } else if (usuario instanceof Profesor) {
            return ((Profesor) usuario).getLimitePrestamos();
        }
        return 2; // Límite por defecto
    }
    
    /**
     * Obtener días de préstamo según tipo de usuario
     * @param usuario usuario a evaluar
     * @return días de préstamo permitidos
     */
    private int obtenerDiasPrestamo(Persona usuario) {
        if (usuario instanceof Profesor) {
            return ((Profesor) usuario).getDiasPrestamo(); // 30 días
        } else if (usuario instanceof Estudiante) {
            return 15; // 15 días para estudiantes
        }
        return 7; // 7 días por defecto
    }
    
    /**
     * Calcular multa total de un usuario desde la BASE DE DATOS
     * @param usuarioId ID del usuario
     * @return monto total de multas
     */
    public double calcularMultaUsuario(int usuarioId) {
        double multaTotal = 0.0;
        for (Prestamo prestamo : prestamoDAO.listarPrestamos()) {
            if (prestamo.getUsuarioId() == usuarioId) {
                multaTotal += prestamo.getMulta();
            }
        }
        return multaTotal;
    }
    
    /**
     * Obtener estadísticas de préstamos desde la BASE DE DATOS
     * @return array con [total, activos, vencidos, devueltos]
     */
    public int[] obtenerEstadisticas() {
        ArrayList<Prestamo> prestamos = prestamoDAO.listarPrestamos();
        int total = prestamos.size();
        int activos = 0;
        int vencidos = 0;
        int devueltos = 0;
        
        for (Prestamo prestamo : prestamos) {
            switch (prestamo.getEstado()) {
                case "Activo":
                    if (prestamo.estaVencido()) {
                        vencidos++;
                    } else {
                        activos++;
                    }
                    break;
                case "Devuelto":
                    devueltos++;
                    break;
            }
        }
        
        return new int[]{total, activos, vencidos, devueltos};
    }
    
    /**
     * Eliminar préstamo de la BASE DE DATOS
     * @param id ID del préstamo a eliminar
     */
    public void eliminarPrestamo(int id) {
        Prestamo prestamo = prestamoDAO.buscarPrestamo(id);
        
        if (prestamo != null) {
            // Si el préstamo está activo, devolver el libro
            if ("Activo".equals(prestamo.getEstado())) {
                libroDAO.actualizarEstadoLibro(prestamo.getLibroId(), "Disponible");
            }
            prestamoDAO.eliminarPrestamo(id);
        }
    }
    
    /**
     * Agrega un nuevo préstamo directamente a la BASE DE DATOS
     * 
     * @param prestamo Préstamo a agregar
     */
    public void agregarPrestamo(Prestamo prestamo) {
        if (prestamo != null) {
            prestamoDAO.agregarPrestamo(prestamo);
        }
    }
    
    /**
     * Edita un préstamo existente en la BASE DE DATOS
     * 
     * @param id ID del préstamo a editar
     * @param prestamoActualizado Préstamo con los datos actualizados
     */
    public void editarPrestamo(int id, Prestamo prestamoActualizado) {
        if (prestamoActualizado != null) {
            // En este caso, el DAO no tiene método editarPrestamo específico
            // pero podríamos usar devolverLibro o renovarPrestamo según el caso
            // Por ahora, solo actualizamos si existe
            Prestamo prestamo = prestamoDAO.buscarPrestamo(id);
            if (prestamo != null) {
                // Aquí se podría implementar la lógica de actualización
                // Por ahora dejamos la validación
            }
        }
    }
}
