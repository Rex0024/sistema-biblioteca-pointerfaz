package com.pointerfaz.dao;

import com.pointerfaz.db.ConnectionDB;
import com.pointerfaz.modelo.Prestamo;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * DAO para gestionar préstamos en la base de datos
 * Siguiendo el patrón del profesor
 */
public class PrestamoDAO {
    
    /**
     * Agregar nuevo préstamo
     */
    public void agregarPrestamo(Prestamo prestamo) {
        try {
            String sql = "INSERT INTO prestamos (libro_id, usuario_id, tipo_usuario, fecha_prestamo, fecha_devolucion_esperada, estado, observaciones, multa) VALUES (?,?,?,?,?,?,?,?)";
            Connection connection = ConnectionDB.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setInt(1, prestamo.getLibroId());
            statement.setInt(2, prestamo.getUsuarioId());
            statement.setString(3, prestamo.getTipoUsuario());
            statement.setDate(4, Date.valueOf(prestamo.getFechaPrestamo()));
            statement.setDate(5, Date.valueOf(prestamo.getFechaDevolucionEsperada()));
            statement.setString(6, prestamo.getEstado());
            statement.setString(7, prestamo.getObservaciones());
            statement.setDouble(8, prestamo.getMulta());
            
            statement.executeUpdate();
            connection.close();
            
            // Actualizar estado del libro a "Prestado"
            LibroDAO libroDAO = new LibroDAO();
            libroDAO.actualizarEstadoLibro(prestamo.getLibroId(), "Prestado");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Listar todos los préstamos
     */
    public ArrayList<Prestamo> listarPrestamos() {
        ArrayList<Prestamo> list = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM prestamos";
            Connection connection = ConnectionDB.conectar();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setId(resultSet.getInt("id"));
                prestamo.setLibroId(resultSet.getInt("libro_id"));
                prestamo.setUsuarioId(resultSet.getInt("usuario_id"));
                prestamo.setTipoUsuario(resultSet.getString("tipo_usuario"));
                
                Date fechaPrestamo = resultSet.getDate("fecha_prestamo");
                if (fechaPrestamo != null) {
                    prestamo.setFechaPrestamo(fechaPrestamo.toLocalDate());
                }
                
                Date fechaDevEsperada = resultSet.getDate("fecha_devolucion_esperada");
                if (fechaDevEsperada != null) {
                    prestamo.setFechaDevolucionEsperada(fechaDevEsperada.toLocalDate());
                }
                
                Date fechaDevReal = resultSet.getDate("fecha_devolucion_real");
                if (fechaDevReal != null) {
                    prestamo.setFechaDevolucionReal(fechaDevReal.toLocalDate());
                }
                
                prestamo.setEstado(resultSet.getString("estado"));
                prestamo.setObservaciones(resultSet.getString("observaciones"));
                prestamo.setMulta(resultSet.getDouble("multa"));
                
                list.add(prestamo);
            }
            
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
    }
    
    /**
     * Buscar préstamo por ID
     */
    public Prestamo buscarPrestamo(int id) {
        try {
            String sql = "SELECT * FROM prestamos WHERE id = ?";
            Connection connection = ConnectionDB.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setId(resultSet.getInt("id"));
                prestamo.setLibroId(resultSet.getInt("libro_id"));
                prestamo.setUsuarioId(resultSet.getInt("usuario_id"));
                prestamo.setTipoUsuario(resultSet.getString("tipo_usuario"));
                
                Date fechaPrestamo = resultSet.getDate("fecha_prestamo");
                if (fechaPrestamo != null) {
                    prestamo.setFechaPrestamo(fechaPrestamo.toLocalDate());
                }
                
                Date fechaDevEsperada = resultSet.getDate("fecha_devolucion_esperada");
                if (fechaDevEsperada != null) {
                    prestamo.setFechaDevolucionEsperada(fechaDevEsperada.toLocalDate());
                }
                
                Date fechaDevReal = resultSet.getDate("fecha_devolucion_real");
                if (fechaDevReal != null) {
                    prestamo.setFechaDevolucionReal(fechaDevReal.toLocalDate());
                }
                
                prestamo.setEstado(resultSet.getString("estado"));
                prestamo.setObservaciones(resultSet.getString("observaciones"));
                prestamo.setMulta(resultSet.getDouble("multa"));
                
                connection.close();
                return prestamo;
            }
            
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Devolver libro (actualizar préstamo)
     * @return true si se procesó exitosamente, false en caso contrario
     */
    public boolean devolverLibro(int prestamoId) {
        try {
            String sql = "UPDATE prestamos SET fecha_devolucion_real = ?, estado = ? WHERE id = ?";
            Connection connection = ConnectionDB.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setString(2, "Devuelto");
            statement.setInt(3, prestamoId);
            
            int rowsAffected = statement.executeUpdate();
            
            if (rowsAffected > 0) {
                // Obtener el libro_id del préstamo
                Prestamo prestamo = buscarPrestamo(prestamoId);
                if (prestamo != null) {
                    // Actualizar estado del libro a "Disponible"
                    LibroDAO libroDAO = new LibroDAO();
                    libroDAO.actualizarEstadoLibro(prestamo.getLibroId(), "Disponible");
                }
                connection.close();
                return true;
            }
            
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    /**
     * Renovar préstamo
     * @return true si se renovó exitosamente, false en caso contrario
     */
    public boolean renovarPrestamo(int prestamoId, int diasAdicionales) {
        try {
            Prestamo prestamo = buscarPrestamo(prestamoId);
            if (prestamo != null && "Activo".equals(prestamo.getEstado()) && !prestamo.estaVencido()) {
                LocalDate nuevaFechaDevolucion = prestamo.getFechaDevolucionEsperada().plusDays(diasAdicionales);
                
                String sql = "UPDATE prestamos SET fecha_devolucion_esperada = ?, estado = ? WHERE id = ?";
                Connection connection = ConnectionDB.conectar();
                PreparedStatement statement = connection.prepareStatement(sql);
                
                statement.setDate(1, Date.valueOf(nuevaFechaDevolucion));
                statement.setString(2, "Renovado");
                statement.setInt(3, prestamoId);
                
                int rowsAffected = statement.executeUpdate();
                connection.close();
                
                return rowsAffected > 0;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    /**
     * Listar préstamos activos
     */
    public ArrayList<Prestamo> listarPrestamosActivos() {
        ArrayList<Prestamo> list = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM prestamos WHERE estado = 'Activo' OR estado = 'Renovado'";
            Connection connection = ConnectionDB.conectar();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                Prestamo prestamo = new Prestamo();
                prestamo.setId(resultSet.getInt("id"));
                prestamo.setLibroId(resultSet.getInt("libro_id"));
                prestamo.setUsuarioId(resultSet.getInt("usuario_id"));
                prestamo.setTipoUsuario(resultSet.getString("tipo_usuario"));
                
                Date fechaPrestamo = resultSet.getDate("fecha_prestamo");
                if (fechaPrestamo != null) {
                    prestamo.setFechaPrestamo(fechaPrestamo.toLocalDate());
                }
                
                Date fechaDevEsperada = resultSet.getDate("fecha_devolucion_esperada");
                if (fechaDevEsperada != null) {
                    prestamo.setFechaDevolucionEsperada(fechaDevEsperada.toLocalDate());
                }
                
                Date fechaDevReal = resultSet.getDate("fecha_devolucion_real");
                if (fechaDevReal != null) {
                    prestamo.setFechaDevolucionReal(fechaDevReal.toLocalDate());
                }
                
                prestamo.setEstado(resultSet.getString("estado"));
                prestamo.setObservaciones(resultSet.getString("observaciones"));
                prestamo.setMulta(resultSet.getDouble("multa"));
                
                list.add(prestamo);
            }
            
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
    }
    
    /**
     * Eliminar préstamo
     */
    public void eliminarPrestamo(int id) {
        try {
            String sql = "DELETE FROM prestamos WHERE id = ?";
            Connection connection = ConnectionDB.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
