package com.pointerfaz.controlador;

import com.pointerfaz.dao.LibroDAO;
import com.pointerfaz.modelo.Libro;
import java.util.ArrayList;

/**
 * Controlador NUEVO para gestionar libros de la biblioteca
 * USA SOLO DAO - SIN ARRAYLIST PRIVADO
 * Conectado 100% a la base de datos MySQL
 */

public class LibroControladorNuevo {
    
    private LibroDAO libroDAO;
    
    /**
     * Constructor - inicializa SOLO el DAO
     */
    public LibroControladorNuevo() {
        libroDAO = new LibroDAO();
    }
    
    /**
     * Agregar nuevo libro a la BASE DE DATOS
     */
    public void agregarLibro(Libro libro) {
        libroDAO.agregarLibro(libro);
    }
    
    /**
     * Listar todos los libros desde la BASE DE DATOS
     */
    public ArrayList<Libro> listarLibros() {
        return libroDAO.listarLibros();
    }
    
    /**
     * Buscar libro por ID en la BASE DE DATOS
     * @param id ID del libro a buscar
     * @return libro encontrado o null
     */
    public Libro buscarLibro(int id) {
        return libroDAO.buscarLibro(id);
    }
    
    /**
     * Buscar libro por ISBN en la BASE DE DATOS
     * @param isbn ISBN del libro
     * @return libro encontrado o null
     */
    public Libro buscarLibroPorISBN(String isbn) {
        for (Libro libro : libroDAO.listarLibros()) {
            if (libro.getIsbn() != null && libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        return null;
    }
    
    /**
     * Buscar libros por título (búsqueda parcial) en la BASE DE DATOS
     * @param titulo título o parte del título
     * @return lista de libros que coinciden
     */
    public ArrayList<Libro> buscarLibrosPorTitulo(String titulo) {
        return libroDAO.buscarLibrosPorTitulo(titulo);
    }
    
    /**
     * Buscar libros por autor (búsqueda parcial) en la BASE DE DATOS
     * @param autor autor o parte del nombre
     * @return lista de libros del autor
     */
    public ArrayList<Libro> buscarLibrosPorAutor(String autor) {
        ArrayList<Libro> librosEncontrados = new ArrayList<>();
        for (Libro libro : libroDAO.listarLibros()) {
            if (libro.getAutor() != null && 
                libro.getAutor().toLowerCase().contains(autor.toLowerCase())) {
                librosEncontrados.add(libro);
            }
        }
        return librosEncontrados;
    }
    
    /**
     * Buscar libros por categoría en la BASE DE DATOS
     * @param categoria categoría del libro
     * @return lista de libros de la categoría
     */
    public ArrayList<Libro> buscarLibrosPorCategoria(String categoria) {
        ArrayList<Libro> librosEncontrados = new ArrayList<>();
        for (Libro libro : libroDAO.listarLibros()) {
            if (libro.getCategoria() != null && libro.getCategoria().equals(categoria)) {
                librosEncontrados.add(libro);
            }
        }
        return librosEncontrados;
    }
    
    /**
     * Editar libro existente en la BASE DE DATOS
     * @param id ID del libro a editar
     * @param nuevoLibro datos nuevos del libro
     */
    public void editarLibro(int id, Libro nuevoLibro) {
        libroDAO.editarLibro(id, nuevoLibro);
    }
    
    /**
     * Eliminar libro de la BASE DE DATOS
     * @param id ID del libro a eliminar
     */
    public void eliminarLibro(int id) {
        libroDAO.eliminarLibro(id);
    }
    
    /**
     * Obtener libros disponibles para préstamo desde la BASE DE DATOS
     * @return lista de libros disponibles
     */
    public ArrayList<Libro> obtenerLibrosDisponibles() {
        ArrayList<Libro> librosDisponibles = new ArrayList<>();
        for (Libro libro : libroDAO.listarLibros()) {
            if (libro.estaDisponible()) {
                librosDisponibles.add(libro);
            }
        }
        return librosDisponibles;
    }
    
    /**
     * Obtener libros prestados desde la BASE DE DATOS
     * @return lista de libros prestados
     */
    public ArrayList<Libro> obtenerLibrosPrestados() {
        ArrayList<Libro> librosPrestados = new ArrayList<>();
        for (Libro libro : libroDAO.listarLibros()) {
            if ("Prestado".equals(libro.getEstado())) {
                librosPrestados.add(libro);
            }
        }
        return librosPrestados;
    }
    
    /**
     * Obtener libros de referencia desde la BASE DE DATOS
     * @return lista de libros de referencia
     */
    public ArrayList<Libro> obtenerLibrosReferencia() {
        ArrayList<Libro> librosReferencia = new ArrayList<>();
        for (Libro libro : libroDAO.listarLibros()) {
            if (libro.isEsReferencia()) {
                librosReferencia.add(libro);
            }
        }
        return librosReferencia;
    }
    
    /**
     * Obtener todas las categorías disponibles desde la BASE DE DATOS
     * @return lista de categorías únicas
     */
    public ArrayList<String> obtenerCategorias() {
        ArrayList<String> categorias = new ArrayList<>();
        for (Libro libro : libroDAO.listarLibros()) {
            if (libro.getCategoria() != null && !categorias.contains(libro.getCategoria())) {
                categorias.add(libro.getCategoria());
            }
        }
        return categorias;
    }
    
    /**
     * Cambiar estado de un libro en la BASE DE DATOS
     * @param id ID del libro
     * @param nuevoEstado nuevo estado del libro
     */
    public void cambiarEstadoLibro(int id, String nuevoEstado) {
        libroDAO.actualizarEstadoLibro(id, nuevoEstado);
    }
    
    /**
     * Contar libros por estado desde la BASE DE DATOS
     * @param estado estado a contar
     * @return cantidad de libros en ese estado
     */
    public int contarLibrosPorEstado(String estado) {
        int count = 0;
        for (Libro libro : libroDAO.listarLibros()) {
            if (libro.getEstado() != null && libro.getEstado().equals(estado)) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Contar libros por categoría desde la BASE DE DATOS
     * @param categoria categoría a contar
     * @return cantidad de libros en esa categoría
     */
    public int contarLibrosPorCategoria(String categoria) {
        int count = 0;
        for (Libro libro : libroDAO.listarLibros()) {
            if (libro.getCategoria() != null && libro.getCategoria().equals(categoria)) {
                count++;
            }
        }
        return count;
    }
}
