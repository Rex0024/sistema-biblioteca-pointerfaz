package com.pointerfaz.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Clase Prestamo para gestionar préstamos de libros
 * Demuestra manejo de fechas y lógica de negocio
 */
public class Prestamo {
    
    // Atributos private para encapsulamiento
    private int id;
    private int libroId;
    private int usuarioId; // ID de Estudiante o Profesor
    private String tipoUsuario; // "Estudiante", "Profesor", "EstudianteGraduado"
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private String estado; // "Activo", "Devuelto", "Vencido", "Renovado"
    private String observaciones;
    private double multa;
    
    // Formatters para fechas
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /**
     * Constructor por defecto
     */
    public Prestamo() {
        this.fechaPrestamo = LocalDate.now();
        this.estado = "Activo";
        this.multa = 0.0;
    }
    
    /**
     * Constructor básico
     * 
     * @param libroId ID del libro prestado
     * @param usuarioId ID del usuario que presta
     * @param tipoUsuario tipo de usuario (Estudiante, Profesor, etc.)
     * @param diasPrestamo días de duración del préstamo
     */
    public Prestamo(int libroId, int usuarioId, String tipoUsuario, int diasPrestamo) {
        this.libroId = libroId;
        this.usuarioId = usuarioId;
        this.tipoUsuario = tipoUsuario;
        this.fechaPrestamo = LocalDate.now();
        this.fechaDevolucionEsperada = fechaPrestamo.plusDays(diasPrestamo);
        this.estado = "Activo";
        this.multa = 0.0;
    }
    
    /**
     * Constructor completo
     * 
     * @param id identificador único
     * @param libroId ID del libro
     * @param usuarioId ID del usuario
     * @param tipoUsuario tipo de usuario
     * @param fechaPrestamo fecha de préstamo
     * @param fechaDevolucionEsperada fecha esperada de devolución
     */
    public Prestamo(int id, int libroId, int usuarioId, String tipoUsuario, 
                   LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada) {
        this.id = id;
        this.libroId = libroId;
        this.usuarioId = usuarioId;
        this.tipoUsuario = tipoUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.estado = "Activo";
        this.multa = 0.0;
    }
    
    // Métodos getter y setter
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getLibroId() {
        return libroId;
    }
    
    public void setLibroId(int libroId) {
        this.libroId = libroId;
    }
    
    public int getUsuarioId() {
        return usuarioId;
    }
    
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
    
    public String getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }
    
    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }
    
    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }
    
    public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }
    
    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }
    
    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public double getMulta() {
        return multa;
    }
    
    public void setMulta(double multa) {
        this.multa = multa;
    }
    
    // Métodos de utilidad para fechas
    
    /**
     * Obtiene la fecha de préstamo como String
     * @return fecha formateada
     */
    public String getFechaPrestamoString() {
        return fechaPrestamo != null ? fechaPrestamo.format(FORMATTER) : "";
    }
    
    /**
     * Obtiene la fecha de devolución esperada como String
     * @return fecha formateada
     */
    public String getFechaDevolucionEsperadaString() {
        return fechaDevolucionEsperada != null ? fechaDevolucionEsperada.format(FORMATTER) : "";
    }
    
    /**
     * Obtiene la fecha de devolución real como String
     * @return fecha formateada
     */
    public String getFechaDevolucionRealString() {
        return fechaDevolucionReal != null ? fechaDevolucionReal.format(FORMATTER) : "";
    }
    
    // Métodos de lógica de negocio
    
    /**
     * Verifica si el préstamo está vencido
     * @return true si está vencido, false en caso contrario
     */
    public boolean estaVencido() {
        if (fechaDevolucionReal != null) {
            return false; // Ya fue devuelto
        }
        return LocalDate.now().isAfter(fechaDevolucionEsperada);
    }
    
    /**
     * Calcula los días de retraso
     * @return número de días de retraso
     */
    public long getDiasRetraso() {
        if (fechaDevolucionReal != null) {
            // Si ya fue devuelto, calcular retraso basado en fecha real vs esperada
            return Math.max(0, ChronoUnit.DAYS.between(fechaDevolucionEsperada, fechaDevolucionReal));
        } else {
            // Si no ha sido devuelto, calcular retraso basado en fecha actual
            return Math.max(0, ChronoUnit.DAYS.between(fechaDevolucionEsperada, LocalDate.now()));
        }
    }
    
    /**
     * Calcula la multa por retraso
     * @param multaPorDia valor de multa por día de retraso
     * @return monto total de multa
     */
    public double calcularMulta(double multaPorDia) {
        long diasRetraso = getDiasRetraso();
        return diasRetraso * multaPorDia;
    }
    
    /**
     * Procesa la devolución del libro
     */
    public void procesarDevolucion() {
        this.fechaDevolucionReal = LocalDate.now();
        this.estado = "Devuelto";
        
        // Calcular multa si hay retraso
        if (getDiasRetraso() > 0) {
            this.multa = calcularMulta(1000); // $1000 por día de retraso
        }
    }
    
    /**
     * Renueva el préstamo extendiendo la fecha de devolución
     * @param diasExtension días adicionales
     */
    public void renovarPrestamo(int diasExtension) {
        if ("Activo".equals(estado) && !estaVencido()) {
            this.fechaDevolucionEsperada = fechaDevolucionEsperada.plusDays(diasExtension);
            this.estado = "Renovado";
        }
    }
    
    /**
     * Obtiene el estado descriptivo del préstamo
     * @return descripción del estado actual
     */
    public String getEstadoDescriptivo() {
        if ("Devuelto".equals(estado)) {
            return "Devuelto" + (multa > 0 ? " (Con multa)" : "");
        } else if (estaVencido()) {
            return "Vencido (" + getDiasRetraso() + " días)";
        } else {
            return estado;
        }
    }
    
    /**
     * Método toString sobrescrito
     */
    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", libroId=" + libroId +
                ", usuarioId=" + usuarioId +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucionEsperada=" + fechaDevolucionEsperada +
                ", fechaDevolucionReal=" + fechaDevolucionReal +
                ", estado='" + estado + '\'' +
                ", multa=" + multa +
                '}';
    }
}