package com.pointerfaz.modelo;

/**
 * Clase Estudiante que hereda de Persona
 * Demuestra herencia, modificadores de acceso y constructores
 */
public class Estudiante extends Persona {
    
    // Atributos específicos del estudiante (private para encapsulamiento)
    private String codigo;
    private String carrera;
    private int semestre;
    private String estado; // Activo, Inactivo, Graduado
    
    /**
     * Constructor por defecto
     */
    public Estudiante() {
        super(); // Llama al constructor de la clase padre
        this.estado = "Activo"; // Estado por defecto
    }
    
    /**
     * Constructor parametrizado básico
     * 
     * @param nombres nombres del estudiante
     * @param apellidos apellidos del estudiante
     * @param email correo electrónico
     * @param codigo código estudiantil
     */
    public Estudiante(String nombres, String apellidos, String email, String codigo) {
        super(nombres, apellidos, email); // Usa constructor de Persona
        this.codigo = codigo;
        this.estado = "Activo";
    }
    
    /**
     * Constructor completo
     * 
     * @param id identificador único
     * @param nombres nombres del estudiante
     * @param apellidos apellidos del estudiante
     * @param email correo electrónico
     * @param telefono número de teléfono
     * @param codigo código estudiantil
     * @param carrera carrera que estudia
     * @param semestre semestre actual
     */
    public Estudiante(int id, String nombres, String apellidos, String email, String telefono, 
                     String codigo, String carrera, int semestre) {
        super(id, nombres, apellidos, email, telefono); // Usa constructor completo de Persona
        this.codigo = codigo;
        this.carrera = carrera;
        this.semestre = semestre;
        this.estado = "Activo";
    }
    
    // Métodos getter y setter específicos de Estudiante
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getCarrera() {
        return carrera;
    }
    
    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    
    public int getSemestre() {
        return semestre;
    }
    
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /**
     * Método para obtener el límite de libros que puede prestar un estudiante
     * @return cantidad máxima de libros permitidos
     */
    public int getLimitePrestamos() {
        return 3; // Estudiantes pueden prestar hasta 3 libros
    }
    
    /**
     * Método para verificar si el estudiante está activo
     * @return true si está activo, false en caso contrario
     */
    public boolean isActivo() {
        return "Activo".equals(estado);
    }
    
    /**
     * Método toString sobrescrito para mostrar información completa
     */
    @Override
    public String toString() {
        return "Estudiante{" +
                "codigo='" + codigo + '\'' +
                ", carrera='" + carrera + '\'' +
                ", semestre=" + semestre +
                ", estado='" + estado + '\'' +
                ", " + super.toString() +
                '}';
    }
}