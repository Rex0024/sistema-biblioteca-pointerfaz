package com.pointerfaz.modelo;

/**
 * Clase base Persona que implementa encapsulamiento y constructores
 * Demuestra modificadores de acceso y herencia
 */
public class Persona {
    
    // Atributos private para demostrar encapsulamiento
    private int id;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    
    /**
     * Constructor por defecto
     */
    public Persona() {
        // Constructor vacío siguiendo el estilo del profesor
    }
    
    /**
     * Constructor parametrizado completo
     * 
     * @param id identificador único
     * @param nombres nombres de la persona
     * @param apellidos apellidos de la persona
     * @param email correo electrónico
     * @param telefono número de teléfono
     */
    public Persona(int id, String nombres, String apellidos, String email, String telefono) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
    }
    
    /**
     * Constructor básico para casos comunes
     * 
     * @param nombres nombres de la persona
     * @param apellidos apellidos de la persona
     * @param email correo electrónico
     */
    public Persona(String nombres, String apellidos, String email) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
    }
    
    // Métodos getter y setter para demostrar encapsulamiento
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    /**
     * Método para obtener el nombre completo
     * @return nombres y apellidos concatenados
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
    
    /**
     * Método toString para mostrar información de la persona
     */
    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}