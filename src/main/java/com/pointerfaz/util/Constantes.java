package com.pointerfaz.util;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * Constantes para el diseño elegante de la interfaz
 * Define colores, fuentes y estilos modernos
 */
public class Constantes {
    
    // COLORES MODERNOS Y ELEGANTES
    public static final Color COLOR_PRIMARIO = new Color(41, 128, 185);      // Azul profesional
    public static final Color COLOR_SECUNDARIO = new Color(52, 152, 219);    // Azul más claro
    public static final Color COLOR_ACCENT = new Color(230, 126, 34);        // Naranja elegante
    public static final Color COLOR_EXITO = new Color(39, 174, 96);          // Verde éxito
    public static final Color COLOR_ERROR = new Color(231, 76, 60);          // Rojo error
    public static final Color COLOR_WARNING = new Color(241, 196, 15);       // Amarillo advertencia
    
    // COLORES DE FONDO
    public static final Color COLOR_FONDO_PRINCIPAL = new Color(236, 240, 241); // Gris muy claro
    public static final Color COLOR_FONDO_SECUNDARIO = new Color(255, 255, 255); // Blanco
    public static final Color COLOR_FONDO_OSCURO = new Color(44, 62, 80);        // Azul oscuro
    public static final Color COLOR_FONDO = COLOR_FONDO_PRINCIPAL; // Alias para compatibilidad
    
    // COLORES DE TEXTO
    public static final Color COLOR_TEXTO_PRIMARIO = new Color(44, 62, 80);   // Azul oscuro
    public static final Color COLOR_TEXTO_SECUNDARIO = new Color(127, 140, 141); // Gris
    public static final Color COLOR_TEXTO_BLANCO = new Color(255, 255, 255);  // Blanco
    public static final Color COLOR_TEXTO = COLOR_TEXTO_PRIMARIO; // Alias para compatibilidad
    
    // COLORES DE BORDES
    public static final Color COLOR_BORDE = new Color(189, 195, 199); // Gris claro
    
    // FUENTES ELEGANTES
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FUENTE_PEQUENA = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);
    
    // DIMENSIONES ESTÁNDAR
    public static final int ANCHO_VENTANA_LOGIN = 700;
    public static final int ALTO_VENTANA_LOGIN = 650;
    public static final int ANCHO_VENTANA_PRINCIPAL = 1200;
    public static final int ALTO_VENTANA_PRINCIPAL = 800;
    
    public static final int ALTURA_BOTON = 35;
    public static final int ALTURA_CAMPO_TEXTO = 30;
    public static final int MARGEN_ESTANDAR = 20;
    public static final int MARGEN_PEQUENO = 10;
    
    // BORDES ELEGANTES
    public static final Border BORDE_CAMPO_TEXTO = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1),
        BorderFactory.createEmptyBorder(5, 10, 5, 10)
    );
    
    public static final Border BORDE_PANEL = BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
        BorderFactory.createEmptyBorder(15, 15, 15, 15)
    );
    
    public static final Border BORDE_TITULO = BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_PRIMARIO),
        BorderFactory.createEmptyBorder(10, 0, 10, 0)
    );
    
    // TEXTOS DE LA APLICACIÓN
    public static final String TITULO_APLICACION = "Sistema de Gestión de Biblioteca";
    public static final String SUBTITULO_LOGIN = "Acceso al Sistema";
    public static final String VERSION = "v1.0.0";
    
    // MENSAJES DEL SISTEMA
    public static final String MSG_BIENVENIDA = "¡Bienvenido al Sistema!";
    public static final String MSG_LOGIN_EXITOSO = "Acceso concedido exitosamente";
    public static final String MSG_LOGIN_FALLIDO = "Email o contraseña incorrectos";
    public static final String MSG_CAMPOS_VACIOS = "Por favor complete todos los campos";
    
    // TÍTULOS DE PESTAÑAS
    public static final String TAB_USUARIOS = "👥 Usuarios";
    public static final String TAB_LIBROS = "📚 Libros";
    public static final String TAB_PRESTAMOS = "📋 Préstamos";
    public static final String TAB_REPORTES = "📊 Reportes";
    
    // CONFIGURACIÓN DE TABLAS
    public static final Color COLOR_HEADER_TABLA = COLOR_PRIMARIO;
    public static final Color COLOR_FILA_PAR = new Color(248, 249, 250);
    public static final Color COLOR_FILA_IMPAR = COLOR_FONDO_SECUNDARIO;
    public static final Color COLOR_SELECCION = new Color(174, 214, 241);
    
    // ICONOS Y SÍMBOLOS (usando caracteres Unicode)
    public static final String ICONO_USUARIO = "👤";
    public static final String ICONO_LIBRO = "📖";
    public static final String ICONO_PRESTAMO = "📋";
    public static final String ICONO_BUSCAR = "🔍";
    public static final String ICONO_AGREGAR = "➕";
    public static final String ICONO_EDITAR = "✏️";
    public static final String ICONO_ELIMINAR = "🗑️";
    public static final String ICONO_RENOVAR = "🔄";
    public static final String ICONO_DEVOLVER = "↩️";
    public static final String ICONO_EXITO = "✅";
    public static final String ICONO_ERROR = "❌";
    public static final String ICONO_WARNING = "⚠️";
    public static final String ICONO_INFO = "ℹ️";
}