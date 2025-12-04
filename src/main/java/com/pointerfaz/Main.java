package com.pointerfaz;

import com.pointerfaz.vista.LoginFrame;
import com.pointerfaz.db.ConnectionDB;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal del Sistema de Gestión de Biblioteca
 * Punto de entrada de la aplicación
 */
public class Main {
    
    /**
     * Método principal de entrada de la aplicación
     * 
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        System.out.println("🚀 Iniciando Sistema de Gestión de Biblioteca...");
        System.out.println("📚 PointerFaz Library Management System");
        System.out.println("");
        
        // Probar conexión a la base de datos
        System.out.println("🔌 Probando conexión a MySQL...");
        if (ConnectionDB.conectar() != null) {
            System.out.println("✅ Conexión a base de datos exitosa!");
        } else {
            System.out.println("❌ Error al conectar con la base de datos");
        }
        System.out.println("");
        
        // Configurar Look and Feel del sistema para mejor apariencia
        configurarLookAndFeel();
        
        // Inicializar la aplicación en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("🔧 Inicializando interfaz gráfica...");
                
                // Crear y mostrar la ventana de login
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
                
                System.out.println("✅ Sistema iniciado exitosamente!");
                System.out.println("🔐 Esperando autenticación del usuario...");
                
            } catch (Exception e) {
                System.err.println("❌ Error al inicializar la aplicación:");
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Configura el Look and Feel del sistema
     */
    private static void configurarLookAndFeel() {
        try {
            // Usar Nimbus Look and Feel para mejor apariencia
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            System.out.println("🎨 Look and Feel Nimbus configurado correctamente");
            
        } catch (Exception e) {
            System.err.println("⚠️ No se pudo configurar Nimbus Look and Feel:");
            e.printStackTrace();
            
            // Usar el Look and Feel por defecto en caso de error
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                System.out.println("🎨 Usando Look and Feel Metal por defecto");
            } catch (Exception ex) {
                System.err.println("❌ Error crítico con Look and Feel:");
                ex.printStackTrace();
            }
        }
    }
}
