package com.pointerfaz.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase ConnectionDB para gestionar la conexión a MySQL
 * Siguiendo el patrón del profesor
 */
public class ConnectionDB {
    
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/biblioteca_pointerfaz";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    /**
     * Método estático para conectar a la base de datos
     * @return Connection objeto de conexión o null si falla
     */
    public static Connection conectar() {
        
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (SQLException e) {
            
            System.out.println(e.getMessage());
            
            return null;
        }
    }
}
