package com.pointerfaz.modelo;

/**
 * Clase EstudianteGraduado que hereda de Estudiante
 * Demuestra herencia multinivel y polimorfismo
 */
public class EstudianteGraduado extends Estudiante {
    
    // Atributos específicos del estudiante graduado
    private String tituloObtenido;
    private String fechaGraduacion;
    private String programaPosgrado; // Maestría, Doctorado, etc.
    private String empresaTrabajo;
    
    /**
     * Constructor por defecto
     */
    public EstudianteGraduado() {
        super(); // Llama al constructor de Estudiante
        setEstado("Graduado"); // Cambia el estado automáticamente
    }
    
    /**
     * Constructor básico para graduado
     * 
     * @param nombres nombres del graduado
     * @param apellidos apellidos del graduado
     * @param email correo electrónico
     * @param codigo código estudiantil
     * @param tituloObtenido título que obtuvo
     */
    public EstudianteGraduado(String nombres, String apellidos, String email, String codigo, String tituloObtenido) {
        super(nombres, apellidos, email, codigo);
        this.tituloObtenido = tituloObtenido;
        setEstado("Graduado");
    }
    
    /**
     * Constructor completo
     * 
     * @param id identificador único
     * @param nombres nombres del graduado
     * @param apellidos apellidos del graduado
     * @param email correo electrónico
     * @param telefono número de teléfono
     * @param codigo código estudiantil
     * @param carrera carrera de la cual se graduó
     * @param tituloObtenido título obtenido
     * @param fechaGraduacion fecha de graduación
     */
    public EstudianteGraduado(int id, String nombres, String apellidos, String email, String telefono,
                             String codigo, String carrera, String tituloObtenido, String fechaGraduacion) {
        super(id, nombres, apellidos, email, telefono, codigo, carrera, 0); // 0 semestres porque ya se graduó
        this.tituloObtenido = tituloObtenido;
        this.fechaGraduacion = fechaGraduacion;
        setEstado("Graduado");
    }
    
    // Métodos getter y setter específicos
    
    public String getTituloObtenido() {
        return tituloObtenido;
    }
    
    public void setTituloObtenido(String tituloObtenido) {
        this.tituloObtenido = tituloObtenido;
    }
    
    public String getFechaGraduacion() {
        return fechaGraduacion;
    }
    
    public void setFechaGraduacion(String fechaGraduacion) {
        this.fechaGraduacion = fechaGraduacion;
    }
    
    public String getProgramaPosgrado() {
        return programaPosgrado;
    }
    
    public void setProgramaPosgrado(String programaPosgrado) {
        this.programaPosgrado = programaPosgrado;
    }
    
    public String getEmpresaTrabajo() {
        return empresaTrabajo;
    }
    
    public void setEmpresaTrabajo(String empresaTrabajo) {
        this.empresaTrabajo = empresaTrabajo;
    }
    
    /**
     * Método sobrescrito - Los graduados tienen mayor límite de préstamos
     * @return cantidad máxima de libros permitidos (mayor que estudiantes regulares)
     */
    @Override
    public int getLimitePrestamos() {
        return 5; // Graduados pueden prestar hasta 5 libros
    }
    
    /**
     * Método para verificar si tiene estudios de posgrado activos
     * @return true si está en posgrado, false en caso contrario
     */
    public boolean tienePosgrado() {
        return programaPosgrado != null && !programaPosgrado.trim().isEmpty();
    }
    
    /**
     * Método para obtener descripción completa del graduado
     * @return información profesional del graduado
     */
    public String getDescripcionProfesional() {
        StringBuilder desc = new StringBuilder();
        desc.append(tituloObtenido);
        if (programaPosgrado != null && !programaPosgrado.trim().isEmpty()) {
            desc.append(" - ").append(programaPosgrado);
        }
        if (empresaTrabajo != null && !empresaTrabajo.trim().isEmpty()) {
            desc.append(" (").append(empresaTrabajo).append(")");
        }
        return desc.toString();
    }
    
    /**
     * Método toString sobrescrito
     */
    @Override
    public String toString() {
        return "EstudianteGraduado{" +
                "tituloObtenido='" + tituloObtenido + '\'' +
                ", fechaGraduacion='" + fechaGraduacion + '\'' +
                ", programaPosgrado='" + programaPosgrado + '\'' +
                ", empresaTrabajo='" + empresaTrabajo + '\'' +
                ", " + super.toString() +
                '}';
    }
}