package com.pointerfaz.modelo;

/**
 * Clase Profesor que hereda de Persona
 * Demuestra herencia paralela y especialización
 */
public class Profesor extends Persona {
    
    // Atributos específicos del profesor (private para encapsulamiento)
    private String codigoEmpleado;
    private String departamento;
    private String especialidad;
    private String tipoContrato; // Tiempo completo, Cátedra, Medio tiempo
    private String titulo; // Licenciado, Magister, Doctor
    private int aniosExperiencia;
    
    /**
     * Constructor por defecto
     */
    public Profesor() {
        super(); // Llama al constructor de la clase padre
        this.tipoContrato = "Cátedra"; // Tipo por defecto
    }
    
    /**
     * Constructor básico
     * 
     * @param nombres nombres del profesor
     * @param apellidos apellidos del profesor
     * @param email correo electrónico
     * @param codigoEmpleado código de empleado
     * @param departamento departamento al que pertenece
     */
    public Profesor(String nombres, String apellidos, String email, String codigoEmpleado, String departamento) {
        super(nombres, apellidos, email);
        this.codigoEmpleado = codigoEmpleado;
        this.departamento = departamento;
        this.tipoContrato = "Cátedra";
    }
    
    /**
     * Constructor completo
     * 
     * @param id identificador único
     * @param nombres nombres del profesor
     * @param apellidos apellidos del profesor
     * @param email correo electrónico
     * @param telefono número de teléfono
     * @param codigoEmpleado código de empleado
     * @param departamento departamento
     * @param especialidad área de especialidad
     * @param tipoContrato tipo de contrato
     * @param titulo título académico
     * @param aniosExperiencia años de experiencia
     */
    public Profesor(int id, String nombres, String apellidos, String email, String telefono,
                   String codigoEmpleado, String departamento, String especialidad, 
                   String tipoContrato, String titulo, int aniosExperiencia) {
        super(id, nombres, apellidos, email, telefono);
        this.codigoEmpleado = codigoEmpleado;
        this.departamento = departamento;
        this.especialidad = especialidad;
        this.tipoContrato = tipoContrato;
        this.titulo = titulo;
        this.aniosExperiencia = aniosExperiencia;
    }
    
    // Métodos getter y setter específicos
    
    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }
    
    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }
    
    public String getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    public String getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    public String getTipoContrato() {
        return tipoContrato;
    }
    
    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public int getAniosExperiencia() {
        return aniosExperiencia;
    }
    
    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }
    
    /**
     * Método para obtener el límite de préstamos para profesores
     * @return cantidad máxima de libros permitidos
     */
    public int getLimitePrestamos() {
        // Los profesores tienen mayor límite según su tipo de contrato
        switch (tipoContrato) {
            case "Tiempo completo":
                return 10;
            case "Medio tiempo":
                return 7;
            case "Cátedra":
                return 5;
            default:
                return 5;
        }
    }
    
    /**
     * Método para obtener el tiempo de préstamo en días
     * @return días de préstamo permitidos
     */
    public int getDiasPrestamo() {
        return 30; // Profesores pueden prestar por 30 días
    }
    
    /**
     * Método para verificar si es profesor de tiempo completo
     * @return true si es tiempo completo, false en caso contrario
     */
    public boolean esTiempoCompleto() {
        return "Tiempo completo".equals(tipoContrato);
    }
    
    /**
     * Método para obtener descripción académica
     * @return información académica del profesor
     */
    public String getDescripcionAcademica() {
        StringBuilder desc = new StringBuilder();
        if (titulo != null && !titulo.trim().isEmpty()) {
            desc.append(titulo).append(" ");
        }
        desc.append(getNombreCompleto());
        if (especialidad != null && !especialidad.trim().isEmpty()) {
            desc.append(" - ").append(especialidad);
        }
        return desc.toString();
    }
    
    /**
     * Método toString sobrescrito
     */
    @Override
    public String toString() {
        return "Profesor{" +
                "codigoEmpleado='" + codigoEmpleado + '\'' +
                ", departamento='" + departamento + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", tipoContrato='" + tipoContrato + '\'' +
                ", titulo='" + titulo + '\'' +
                ", aniosExperiencia=" + aniosExperiencia +
                ", " + super.toString() +
                '}';
    }
}