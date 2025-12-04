package com.pointerfaz.dao;

import com.pointerfaz.db.ConnectionDB;
import com.pointerfaz.modelo.Libro;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO para gestionar libros en la base de datos
 * Siguiendo el patrón del profesor
 */
public class LibroDAO {
    
    /**
     * Agregar nuevo libro
     */
    public void agregarLibro(Libro libro) {
        try {
            String sql = "INSERT INTO libros (isbn, titulo, autor, editorial, categoria, anio_publicacion, numero_paginas, ubicacion, estado, es_referencia) VALUES (?,?,?,?,?,?,?,?,?,?)";
            Connection connection = ConnectionDB.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, libro.getIsbn());
            statement.setString(2, libro.getTitulo());
            statement.setString(3, libro.getAutor());
            statement.setString(4, libro.getEditorial());
            statement.setString(5, libro.getCategoria());
            statement.setInt(6, libro.getAnioPublicacion());
            statement.setInt(7, libro.getNumeroPaginas());
            statement.setString(8, libro.getUbicacion());
            statement.setString(9, libro.getEstado());
            statement.setBoolean(10, libro.isEsReferencia());
            
            statement.executeUpdate();
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Listar todos los libros
     */
    public ArrayList<Libro> listarLibros() {
        ArrayList<Libro> list = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM libros";
            Connection connection = ConnectionDB.conectar();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                Libro libro = new Libro();
                libro.setId(resultSet.getInt("id"));
                libro.setIsbn(resultSet.getString("isbn"));
                libro.setTitulo(resultSet.getString("titulo"));
                libro.setAutor(resultSet.getString("autor"));
                libro.setEditorial(resultSet.getString("editorial"));
                libro.setCategoria(resultSet.getString("categoria"));
                libro.setAnioPublicacion(resultSet.getInt("anio_publicacion"));
                libro.setNumeroPaginas(resultSet.getInt("numero_paginas"));
                libro.setUbicacion(resultSet.getString("ubicacion"));
                libro.setEstado(resultSet.getString("estado"));
                libro.setEsReferencia(resultSet.getBoolean("es_referencia"));
                
                list.add(libro);
            }
            
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
    }
    
    /**
     * Buscar libro por ID
     */
    public Libro buscarLibro(int id) {
        try {
            String sql = "SELECT * FROM libros WHERE id = ?";
            Connection connection = ConnectionDB.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                Libro libro = new Libro();
                libro.setId(resultSet.getInt("id"));
                libro.setIsbn(resultSet.getString("isbn"));
                libro.setTitulo(resultSet.getString("titulo"));
                libro.setAutor(resultSet.getString("autor"));
                libro.setEditorial(resultSet.getString("editorial"));
                libro.setCategoria(resultSet.getString("categoria"));
                libro.setAnioPublicacion(resultSet.getInt("anio_publicacion"));
                libro.setNumeroPaginas(resultSet.getInt("numero_paginas"));
                libro.setUbicacion(resultSet.getString("ubicacion"));
                libro.setEstado(resultSet.getString("estado"));
                libro.setEsReferencia(resultSet.getBoolean("es_referencia"));
                
                connection.close();
                return libro;
            }
            
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Buscar libros por título
     */
    public ArrayList<Libro> buscarLibrosPorTitulo(String titulo) {
        ArrayList<Libro> list = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM libros WHERE titulo LIKE ?";
            Connection connection = ConnectionDB.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + titulo + "%");
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Libro libro = new Libro();
                libro.setId(resultSet.getInt("id"));
                libro.setIsbn(resultSet.getString("isbn"));
                libro.setTitulo(resultSet.getString("titulo"));
                libro.setAutor(resultSet.getString("autor"));
                libro.setEditorial(resultSet.getString("editorial"));
                libro.setCategoria(resultSet.getString("categoria"));
                libro.setAnioPublicacion(resultSet.getInt("anio_publicacion"));
                libro.setNumeroPaginas(resultSet.getInt("numero_paginas"));
                libro.setUbicacion(resultSet.getString("ubicacion"));
                libro.setEstado(resultSet.getString("estado"));
                libro.setEsReferencia(resultSet.getBoolean("es_referencia"));
                
                list.add(libro);
            }
            
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
    }
    
    /**
     * Editar libro
     */
    public void editarLibro(int id, Libro nuevoLibro) {
        try {
            String sql = "UPDATE libros SET isbn = ?, titulo = ?, autor = ?, editorial = ?, categoria = ?, anio_publicacion = ?, numero_paginas = ?, ubicacion = ?, estado = ?, es_referencia = ? WHERE id = ?";
            Connection connection = ConnectionDB.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, nuevoLibro.getIsbn());
            statement.setString(2, nuevoLibro.getTitulo());
            statement.setString(3, nuevoLibro.getAutor());
            statement.setString(4, nuevoLibro.getEditorial());
            statement.setString(5, nuevoLibro.getCategoria());
            statement.setInt(6, nuevoLibro.getAnioPublicacion());
            statement.setInt(7, nuevoLibro.getNumeroPaginas());
            statement.setString(8, nuevoLibro.getUbicacion());
            statement.setString(9, nuevoLibro.getEstado());
            statement.setBoolean(10, nuevoLibro.isEsReferencia());
            statement.setInt(11, id);
            
            statement.executeUpdate();
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Eliminar libro
     */
    public void eliminarLibro(int id) {
        try {
            String sql = "DELETE FROM libros WHERE id = ?";
            Connection connection = ConnectionDB.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Actualizar estado del libro
     */
    public void actualizarEstadoLibro(int id, String estado) {
        try {
            String sql = "UPDATE libros SET estado = ? WHERE id = ?";
            Connection connection = ConnectionDB.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, estado);
            statement.setInt(2, id);
            statement.executeUpdate();
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
