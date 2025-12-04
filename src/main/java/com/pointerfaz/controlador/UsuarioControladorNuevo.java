package com.pointerfaz.controlador;

import com.pointerfaz.dao.UsuarioDAO;
import com.pointerfaz.modelo.Estudiante;
import com.pointerfaz.modelo.EstudianteGraduado;
import com.pointerfaz.modelo.Profesor;
import com.pointerfaz.modelo.Persona;
import java.util.ArrayList;

/**
 * Controlador NUEVO para gestionar usuarios de la biblioteca
 * USA SOLO DAO - SIN ARRAYLIST PRIVADO
 * Conectado 100% a la base de datos MySQL
 */
public class UsuarioControladorNuevo {
    
    private UsuarioDAO usuarioDAO;
    
    /**
     * Constructor - inicializa SOLO el DAO
     */
    public UsuarioControladorNuevo() {
        usuarioDAO = new UsuarioDAO();
    }
    
    /**
     * Agregar nuevo usuario a la BASE DE DATOS
     * @param usuario usuario a agregar
     */
    public void agregarUsuario(Persona usuario) {
        usuarioDAO.agregarUsuario(usuario);
    }
    
    /**
     * Listar todos los usuarios desde la BASE DE DATOS
     * @return lista de usuarios
     */
    public ArrayList<Persona> listarUsuarios() {
        return usuarioDAO.listarUsuarios();
    }
    
    /**
     * Buscar usuario por ID en la BASE DE DATOS
     * @param id ID del usuario a buscar
     * @return usuario encontrado o null
     */
    public Persona buscarUsuario(int id) {
        return usuarioDAO.buscarUsuario(id);
    }
    
    /**
     * Buscar usuario por email desde la BASE DE DATOS
     * @param email email a buscar
     * @return usuario encontrado o null
     */
    public Persona buscarUsuarioPorEmail(String email) {
        for (Persona usuario : usuarioDAO.listarUsuarios()) {
            if (usuario.getEmail() != null && usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Buscar estudiante por código desde la BASE DE DATOS
     * @param codigo código del estudiante
     * @return estudiante encontrado o null
     */
    public Estudiante buscarEstudiantePorCodigo(String codigo) {
        for (Persona usuario : usuarioDAO.listarUsuarios()) {
            if (usuario instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) usuario;
                if (estudiante.getCodigo() != null && estudiante.getCodigo().equals(codigo)) {
                    return estudiante;
                }
            }
        }
        return null;
    }
    
    /**
     * Buscar profesor por código de empleado desde la BASE DE DATOS
     * @param codigoEmpleado código del empleado
     * @return profesor encontrado o null
     */
    public Profesor buscarProfesorPorCodigo(String codigoEmpleado) {
        for (Persona usuario : usuarioDAO.listarUsuarios()) {
            if (usuario instanceof Profesor) {
                Profesor profesor = (Profesor) usuario;
                if (profesor.getCodigoEmpleado() != null && profesor.getCodigoEmpleado().equals(codigoEmpleado)) {
                    return profesor;
                }
            }
        }
        return null;
    }
    
    /**
     * Editar usuario existente en la BASE DE DATOS
     * @param id ID del usuario a editar
     * @param nuevoUsuario datos nuevos del usuario
     */
    public void editarUsuario(int id, Persona nuevoUsuario) {
        // El DAO no tiene método editarUsuario todavía
        // Por ahora solo validamos que existe
        Persona usuario = usuarioDAO.buscarUsuario(id);
        if (usuario != null && nuevoUsuario != null) {
            // Aquí se podría implementar la actualización en el DAO
            // Por ahora dejamos la validación
        }
    }
    
    /**
     * Eliminar usuario de la BASE DE DATOS
     * @param id ID del usuario a eliminar
     */
    public void eliminarUsuario(int id) {
        usuarioDAO.eliminarUsuario(id);
    }
    
    /**
     * Autenticar usuario para login desde la BASE DE DATOS
     * @param email email del usuario
     * @param password contraseña
     * @return usuario autenticado o null
     */
    public Persona autenticar(String email, String password) {
        return usuarioDAO.authenticar(email, password);
    }
    
    /**
     * Método alternativo de autenticación (mantiene compatibilidad)
     * @param email email del usuario
     * @param password contraseña
     * @return usuario autenticado o null
     */
    public Persona authenticar(String email, String password) {
        return usuarioDAO.authenticar(email, password);
    }
    
    /**
     * Obtener estudiantes activos desde la BASE DE DATOS
     * @return lista de estudiantes activos
     */
    public ArrayList<Estudiante> obtenerEstudiantesActivos() {
        ArrayList<Estudiante> estudiantesActivos = new ArrayList<>();
        for (Persona usuario : usuarioDAO.listarUsuarios()) {
            if (usuario instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) usuario;
                if (estudiante.isActivo()) {
                    estudiantesActivos.add(estudiante);
                }
            }
        }
        return estudiantesActivos;
    }
    
    /**
     * Obtener profesores por departamento desde la BASE DE DATOS
     * @param departamento departamento a filtrar
     * @return lista de profesores del departamento
     */
    public ArrayList<Profesor> obtenerProfesoresPorDepartamento(String departamento) {
        ArrayList<Profesor> profesoresDepartamento = new ArrayList<>();
        for (Persona usuario : usuarioDAO.listarUsuarios()) {
            if (usuario instanceof Profesor) {
                Profesor profesor = (Profesor) usuario;
                if (profesor.getDepartamento() != null && profesor.getDepartamento().equals(departamento)) {
                    profesoresDepartamento.add(profesor);
                }
            }
        }
        return profesoresDepartamento;
    }
    
    /**
     * Contar usuarios por tipo desde la BASE DE DATOS
     * @param tipo tipo de usuario ("Estudiante", "Profesor", "EstudianteGraduado")
     * @return cantidad de usuarios del tipo especificado
     */
    public int contarUsuariosPorTipo(String tipo) {
        int count = 0;
        for (Persona usuario : usuarioDAO.listarUsuarios()) {
            switch (tipo) {
                case "Estudiante":
                    if (usuario instanceof Estudiante && !(usuario instanceof EstudianteGraduado)) {
                        count++;
                    }
                    break;
                case "EstudianteGraduado":
                    if (usuario instanceof EstudianteGraduado) {
                        count++;
                    }
                    break;
                case "Profesor":
                    if (usuario instanceof Profesor) {
                        count++;
                    }
                    break;
            }
        }
        return count;
    }
}
