package com.pointerfaz.dao;

import com.pointerfaz.db.ConnectionDB;
import com.pointerfaz.modelo.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * DAO para gestionar usuarios en la base de datos
 * Siguiendo el patrón del profesor
 */
public class UsuarioDAO {
    
    /**
     * Agregar nuevo usuario (Estudiante, Profesor o EstudianteGraduado)
     */
    public void agregarUsuario(Persona persona) {
        try {
            // Primero insertar en tabla personas
            String sqlPersona = "INSERT INTO personas (nombres, apellidos, email, telefono, tipo_usuario, password) VALUES (?,?,?,?,?,?)";
            Connection connection = ConnectionDB.conectar();
            PreparedStatement statement = connection.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, persona.getNombres());
            statement.setString(2, persona.getApellidos());
            statement.setString(3, persona.getEmail());
            statement.setString(4, persona.getTelefono());
            
            // Determinar tipo de usuario y password
            String tipoUsuario = "";
            String password = "";
            
            if (persona instanceof EstudianteGraduado) {
                tipoUsuario = "EstudianteGraduado";
                password = ((Estudiante) persona).getCodigo();
            } else if (persona instanceof Estudiante) {
                tipoUsuario = "Estudiante";
                password = ((Estudiante) persona).getCodigo();
            } else if (persona instanceof Profesor) {
                tipoUsuario = "Profesor";
                password = ((Profesor) persona).getCodigoEmpleado();
            }
            
            statement.setString(5, tipoUsuario);
            statement.setString(6, password);
            
            statement.executeUpdate();
            
            // Obtener el ID generado
            ResultSet rs = statement.getGeneratedKeys();
            int idGenerado = 0;
            if (rs.next()) {
                idGenerado = rs.getInt(1);
                persona.setId(idGenerado);
            }
            
            // Insertar en tabla específica según tipo
            if (persona instanceof Estudiante) {
                insertarEstudiante((Estudiante) persona, idGenerado);
                
                if (persona instanceof EstudianteGraduado) {
                    insertarEstudianteGraduado((EstudianteGraduado) persona, idGenerado);
                }
            } else if (persona instanceof Profesor) {
                insertarProfesor((Profesor) persona, idGenerado);
            }
            
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    private void insertarEstudiante(Estudiante estudiante, int id) throws SQLException {
        String sql = "INSERT INTO estudiantes (id, codigo, carrera, semestre, estado) VALUES (?,?,?,?,?)";
        Connection connection = ConnectionDB.conectar();
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setInt(1, id);
        statement.setString(2, estudiante.getCodigo());
        statement.setString(3, estudiante.getCarrera());
        statement.setInt(4, estudiante.getSemestre());
        statement.setString(5, estudiante.getEstado());
        
        statement.executeUpdate();
        connection.close();
    }
    
    private void insertarEstudianteGraduado(EstudianteGraduado graduado, int id) throws SQLException {
        String sql = "INSERT INTO estudiantes_graduados (id, titulo_obtenido, fecha_graduacion, programa_posgrado, empresa_trabajo) VALUES (?,?,?,?,?)";
        Connection connection = ConnectionDB.conectar();
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setInt(1, id);
        statement.setString(2, graduado.getTituloObtenido());
        statement.setString(3, graduado.getFechaGraduacion());
        statement.setString(4, graduado.getProgramaPosgrado());
        statement.setString(5, graduado.getEmpresaTrabajo());
        
        statement.executeUpdate();
        connection.close();
    }
    
    private void insertarProfesor(Profesor profesor, int id) throws SQLException {
        String sql = "INSERT INTO profesores (id, codigo_empleado, departamento, especialidad, tipo_contrato, titulo, anios_experiencia) VALUES (?,?,?,?,?,?,?)";
        Connection connection = ConnectionDB.conectar();
        PreparedStatement statement = connection.prepareStatement(sql);
        
        statement.setInt(1, id);
        statement.setString(2, profesor.getCodigoEmpleado());
        statement.setString(3, profesor.getDepartamento());
        statement.setString(4, profesor.getEspecialidad());
        statement.setString(5, profesor.getTipoContrato());
        statement.setString(6, profesor.getTitulo());
        statement.setInt(7, profesor.getAniosExperiencia());
        
        statement.executeUpdate();
        connection.close();
    }
    
    /**
     * Listar todos los usuarios
     */
    public ArrayList<Persona> listarUsuarios() {
        ArrayList<Persona> list = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM personas";
            Connection connection = ConnectionDB.conectar();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String tipoUsuario = resultSet.getString("tipo_usuario");
                
                Persona persona = null;
                
                if (tipoUsuario.equals("Estudiante") || tipoUsuario.equals("EstudianteGraduado")) {
                    persona = cargarEstudiante(id, tipoUsuario);
                } else if (tipoUsuario.equals("Profesor")) {
                    persona = cargarProfesor(id);
                }
                
                if (persona != null) {
                    list.add(persona);
                }
            }
            
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return list;
    }
    
    private Estudiante cargarEstudiante(int id, String tipoUsuario) throws SQLException {
        String sql = "SELECT p.*, e.* FROM personas p INNER JOIN estudiantes e ON p.id = e.id WHERE p.id = ?";
        Connection connection = ConnectionDB.conectar();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        
        Estudiante estudiante = null;
        
        if (rs.next()) {
            if (tipoUsuario.equals("EstudianteGraduado")) {
                estudiante = new EstudianteGraduado();
                cargarDatosGraduado((EstudianteGraduado) estudiante, id);
            } else {
                estudiante = new Estudiante();
            }
            
            estudiante.setId(rs.getInt("id"));
            estudiante.setNombres(rs.getString("nombres"));
            estudiante.setApellidos(rs.getString("apellidos"));
            estudiante.setEmail(rs.getString("email"));
            estudiante.setTelefono(rs.getString("telefono"));
            estudiante.setCodigo(rs.getString("codigo"));
            estudiante.setCarrera(rs.getString("carrera"));
            estudiante.setSemestre(rs.getInt("semestre"));
            estudiante.setEstado(rs.getString("estado"));
        }
        
        connection.close();
        return estudiante;
    }
    
    private void cargarDatosGraduado(EstudianteGraduado graduado, int id) throws SQLException {
        String sql = "SELECT * FROM estudiantes_graduados WHERE id = ?";
        Connection connection = ConnectionDB.conectar();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        
        if (rs.next()) {
            graduado.setTituloObtenido(rs.getString("titulo_obtenido"));
            graduado.setFechaGraduacion(rs.getString("fecha_graduacion"));
            graduado.setProgramaPosgrado(rs.getString("programa_posgrado"));
            graduado.setEmpresaTrabajo(rs.getString("empresa_trabajo"));
        }
        
        connection.close();
    }
    
    private Profesor cargarProfesor(int id) throws SQLException {
        String sql = "SELECT p.*, pr.* FROM personas p INNER JOIN profesores pr ON p.id = pr.id WHERE p.id = ?";
        Connection connection = ConnectionDB.conectar();
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        
        Profesor profesor = null;
        
        if (rs.next()) {
            profesor = new Profesor();
            profesor.setId(rs.getInt("id"));
            profesor.setNombres(rs.getString("nombres"));
            profesor.setApellidos(rs.getString("apellidos"));
            profesor.setEmail(rs.getString("email"));
            profesor.setTelefono(rs.getString("telefono"));
            profesor.setCodigoEmpleado(rs.getString("codigo_empleado"));
            profesor.setDepartamento(rs.getString("departamento"));
            profesor.setEspecialidad(rs.getString("especialidad"));
            profesor.setTipoContrato(rs.getString("tipo_contrato"));
            profesor.setTitulo(rs.getString("titulo"));
            profesor.setAniosExperiencia(rs.getInt("anios_experiencia"));
        }
        
        connection.close();
        return profesor;
    }
    
    /**
     * Buscar usuario por email y password (para login)
     */
    public Persona authenticar(String email, String password) {
        try {
            String sql = "SELECT * FROM personas WHERE email = ? AND password = ?";
            Connection connection = ConnectionDB.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                String tipoUsuario = rs.getString("tipo_usuario");
                
                Persona persona = null;
                
                if (tipoUsuario.equals("Estudiante") || tipoUsuario.equals("EstudianteGraduado")) {
                    persona = cargarEstudiante(id, tipoUsuario);
                } else if (tipoUsuario.equals("Profesor")) {
                    persona = cargarProfesor(id);
                }
                
                connection.close();
                return persona;
            }
            
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Buscar usuario por ID
     */
    public Persona buscarUsuario(int id) {
        try {
            String sql = "SELECT * FROM personas WHERE id = ?";
            Connection connection = ConnectionDB.conectar();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                String tipoUsuario = rs.getString("tipo_usuario");
                
                Persona persona = null;
                
                if (tipoUsuario.equals("Estudiante") || tipoUsuario.equals("EstudianteGraduado")) {
                    persona = cargarEstudiante(id, tipoUsuario);
                } else if (tipoUsuario.equals("Profesor")) {
                    persona = cargarProfesor(id);
                }
                
                connection.close();
                return persona;
            }
            
            connection.close();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Eliminar usuario
     */
    public void eliminarUsuario(int id) {
        try {
            String sql = "DELETE FROM personas WHERE id = ?";
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
