package com.pointerfaz.modelo;

/**
 * Clase Libro para gestionar información de libros en la biblioteca
 * Demuestra encapsulamiento y métodos de utilidad
 */
public class Libro {
    
    // Atributos private para demostrar encapsulamiento
    private int id;
    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
    private String categoria;
    private int anioPublicacion;
    private int numeroPaginas;
    private String ubicacion; // Estante donde se encuentra
    private String estado; // Disponible, Prestado, Mantenimiento, Perdido
    private boolean esReferencia; // Si es libro de consulta (no se presta)
    
    /**
     * Constructor por defecto
     */
    public Libro() {
        this.estado = "Disponible"; // Estado por defecto
        this.esReferencia = false;
    }
    
    /**
     * Constructor básico
     * 
     * @param titulo título del libro
     * @param autor autor del libro
     * @param isbn código ISBN
     */
    public Libro(String titulo, String autor, String isbn) {
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.estado = "Disponible";
        this.esReferencia = false;
    }
    
    /**
     * Constructor completo
     * 
     * @param id identificador único
     * @param isbn código ISBN
     * @param titulo título del libro
     * @param autor autor del libro
     * @param editorial editorial
     * @param categoria categoría o género
     * @param anioPublicacion año de publicación
     * @param numeroPaginas número de páginas
     * @param ubicacion ubicación física
     */
    public Libro(int id, String isbn, String titulo, String autor, String editorial, 
                String categoria, int anioPublicacion, int numeroPaginas, String ubicacion) {
        this.id = id;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.categoria = categoria;
        this.anioPublicacion = anioPublicacion;
        this.numeroPaginas = numeroPaginas;
        this.ubicacion = ubicacion;
        this.estado = "Disponible";
        this.esReferencia = false;
    }
    
    // Métodos getter y setter
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public String getEditorial() {
        return editorial;
    }
    
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public int getAnioPublicacion() {
        return anioPublicacion;
    }
    
    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }
    
    public int getNumeroPaginas() {
        return numeroPaginas;
    }
    
    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }
    
    public String getUbicacion() {
        return ubicacion;
    }
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public boolean isEsReferencia() {
        return esReferencia;
    }
    
    public void setEsReferencia(boolean esReferencia) {
        this.esReferencia = esReferencia;
    }
    
    // Métodos de utilidad
    
    /**
     * Verifica si el libro está disponible para préstamo
     * @return true si está disponible, false en caso contrario
     */
    public boolean estaDisponible() {
        return "Disponible".equals(estado) && !esReferencia;
    }
    
    /**
     * Marca el libro como prestado
     */
    public void prestar() {
        if (estaDisponible()) {
            this.estado = "Prestado";
        }
    }
    
    /**
     * Marca el libro como devuelto
     */
    public void devolver() {
        if ("Prestado".equals(estado)) {
            this.estado = "Disponible";
        }
    }
    
    /**
     * Obtiene información básica del libro para mostrar
     * @return información resumida del libro
     */
    public String getInformacionBasica() {
        return titulo + " - " + autor + " (" + anioPublicacion + ")";
    }
    
    /**
     * Obtiene descripción completa del libro
     * @return información detallada del libro
     */
    public String getDescripcionCompleta() {
        StringBuilder desc = new StringBuilder();
        desc.append("\"").append(titulo).append("\"");
        desc.append(" por ").append(autor);
        if (editorial != null && !editorial.trim().isEmpty()) {
            desc.append(", ").append(editorial);
        }
        desc.append(" (").append(anioPublicacion).append(")");
        if (categoria != null && !categoria.trim().isEmpty()) {
            desc.append(" - ").append(categoria);
        }
        return desc.toString();
    }
    
    /**
     * Método toString sobrescrito
     */
    @Override
    public String toString() {
        return "Libro{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editorial='" + editorial + '\'' +
                ", categoria='" + categoria + '\'' +
                ", anioPublicacion=" + anioPublicacion +
                ", estado='" + estado + '\'' +
                ", esReferencia=" + esReferencia +
                '}';
    }
}