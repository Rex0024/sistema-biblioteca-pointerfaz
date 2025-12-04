package com.pointerfaz.vista;

import com.pointerfaz.controlador.UsuarioControladorNuevo;
import com.pointerfaz.modelo.Persona;
import com.pointerfaz.util.Constantes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * LoginFrame - Ventana de autenticación elegante
 * Hereda de JFrame para demostrar herencia
 * Diseño moderno y profesional
 */
public class LoginFrame extends JFrame {
    
    // Componentes de la interfaz (private para encapsulamiento)
    private JTextField txtEmail;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblEmail;
    private JLabel lblPassword;
    private JPanel panelPrincipal;
    private JPanel panelFormulario;
    private JPanel panelBotones;
    private UsuarioControlador usuarioControlador;
    
    /**
     * Constructor que inicializa la ventana de login
     */
    public LoginFrame() {
        usuarioControlador = new UsuarioControladorNuevo();
        inicializarComponentes();
        configurarVentana();
        configurarEventos();
        aplicarEstilosModernos();
    }
    
    /**
     * Inicializa todos los componentes de la interfaz
     */
    private void inicializarComponentes() {
        // Panel principal con diseño elegante
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(20, 20));
        panelPrincipal.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Título principal
        lblTitulo = new JLabel(Constantes.TITULO_APLICACION, SwingConstants.CENTER);
        lblTitulo.setFont(Constantes.FUENTE_TITULO);
        lblTitulo.setForeground(Constantes.COLOR_PRIMARIO);
        lblTitulo.setBorder(Constantes.BORDE_TITULO);
        
        // Subtítulo
        lblSubtitulo = new JLabel(Constantes.SUBTITULO_LOGIN, SwingConstants.CENTER);
        lblSubtitulo.setFont(Constantes.FUENTE_SUBTITULO);
        lblSubtitulo.setForeground(Constantes.COLOR_TEXTO_SECUNDARIO);
        
        // Panel del formulario con layout más simple y confiable
        panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(4, 1, 10, 10)); // 4 filas, 1 columna
        panelFormulario.setBackground(Constantes.COLOR_FONDO_SECUNDARIO);
        panelFormulario.setBorder(Constantes.BORDE_PANEL);
        
        // Panel contenedor centralizado para el formulario
        JPanel panelContenedorFormulario = new JPanel(new GridBagLayout());
        panelContenedorFormulario.setBackground(Constantes.COLOR_FONDO_SECUNDARIO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Etiqueta Email
        lblEmail = new JLabel(Constantes.ICONO_USUARIO + " Email:");
        lblEmail.setFont(Constantes.FUENTE_NORMAL);
        lblEmail.setForeground(Constantes.COLOR_TEXTO_PRIMARIO);
        
        // Campo Email
        txtEmail = new JTextField();
        txtEmail.setFont(Constantes.FUENTE_NORMAL);
        txtEmail.setBorder(Constantes.BORDE_CAMPO_TEXTO);
        txtEmail.setPreferredSize(new Dimension(350, 40));
        txtEmail.setMaximumSize(new Dimension(400, 40)); // Tamaño máximo limitado
        txtEmail.setText(""); // Campo vacío para que el usuario pueda escribir
        txtEmail.setBackground(Color.WHITE);
        txtEmail.setOpaque(true);
        txtEmail.setVisible(true);
        
        // Etiqueta Password
        lblPassword = new JLabel("🔒 Contraseña:");
        lblPassword.setFont(Constantes.FUENTE_NORMAL);
        lblPassword.setForeground(Constantes.COLOR_TEXTO_PRIMARIO);
        
        // Campo Password
        txtPassword = new JPasswordField();
        txtPassword.setFont(Constantes.FUENTE_NORMAL);
        txtPassword.setBorder(Constantes.BORDE_CAMPO_TEXTO);
        txtPassword.setPreferredSize(new Dimension(350, 40));
        txtPassword.setMaximumSize(new Dimension(400, 40)); // Tamaño máximo limitado
        txtPassword.setText(""); // Campo vacío para que el usuario pueda escribir
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setOpaque(true);
        txtPassword.setVisible(true);
        
        // Agregar componentes al formulario
        panelFormulario.add(lblEmail);
        panelFormulario.add(txtEmail);
        panelFormulario.add(lblPassword);
        panelFormulario.add(txtPassword);
        
        // Panel de botones
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 25));
        panelBotones.setBackground(Constantes.COLOR_FONDO_SECUNDARIO);
        
        // Botón Login elegante
        btnLogin = new JButton("🚀 Ingresar al Sistema");
        btnLogin.setFont(Constantes.FUENTE_BOTON);
        btnLogin.setPreferredSize(new Dimension(250, 45));
        
        panelBotones.add(btnLogin);
        
        // Panel de ayuda con credenciales de prueba
        JPanel panelAyuda = new JPanel();
        panelAyuda.setLayout(new BoxLayout(panelAyuda, BoxLayout.Y_AXIS));
        panelAyuda.setBackground(new Color(240, 248, 255));
        panelAyuda.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Constantes.COLOR_SECUNDARIO, 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        panelAyuda.setPreferredSize(new Dimension(380, 100));
        
        JLabel lblAyudaTitulo = new JLabel("💡 Credenciales de Prueba:");
        lblAyudaTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAyudaTitulo.setForeground(Constantes.COLOR_PRIMARIO);
        lblAyudaTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblAyuda1 = new JLabel("📧 ana.garcia@universidad.edu | 🔑 EST001");
        lblAyuda1.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblAyuda1.setForeground(Constantes.COLOR_TEXTO_SECUNDARIO);
        lblAyuda1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblAyuda2 = new JLabel("📧 juan.martinez@universidad.edu | 🔑 PROF001");
        lblAyuda2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblAyuda2.setForeground(Constantes.COLOR_TEXTO_SECUNDARIO);
        lblAyuda2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panelAyuda.add(lblAyudaTitulo);
        panelAyuda.add(Box.createVerticalStrut(8));
        panelAyuda.add(lblAyuda1);
        panelAyuda.add(Box.createVerticalStrut(3));
        panelAyuda.add(lblAyuda2);
        
        panelBotones.add(panelAyuda);
        
        // Ensamblar la interfaz
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 10));
        panelSuperior.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        panelSuperior.add(lblTitulo, BorderLayout.NORTH);
        panelSuperior.add(lblSubtitulo, BorderLayout.SOUTH);
        
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    /**
     * Configura las propiedades de la ventana
     */
    private void configurarVentana() {
        setTitle(Constantes.TITULO_APLICACION + " - Login");
        setSize(Constantes.ANCHO_VENTANA_LOGIN, Constantes.ALTO_VENTANA_LOGIN);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en pantalla
        setResizable(true); // Permitir redimensionar la ventana
        setMinimumSize(new Dimension(500, 450)); // Tamaño mínimo más grande
        
        // Icono de la ventana (usando un ícono simple)
        setIconImage(Toolkit.getDefaultToolkit().createImage(""));
    }
    
    /**
     * Configura los eventos de los componentes
     */
    private void configurarEventos() {
        // ActionListener para el botón login (siguiendo patrón del profesor)
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                procesarLogin();
            }
        });
        
        // Permitir login con Enter en el campo password
        txtPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarLogin();
            }
        });
        
        // Permitir login con Enter en el campo email
        txtEmail.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtPassword.requestFocus();
            }
        });
    }
    
    /**
     * Aplica estilos modernos y elegantes
     */
    private void aplicarEstilosModernos() {
        // Estilo del botón login
        btnLogin.setBackground(Constantes.COLOR_PRIMARIO);
        btnLogin.setForeground(Constantes.COLOR_TEXTO_BLANCO);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover para el botón
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(Constantes.COLOR_SECUNDARIO);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(Constantes.COLOR_PRIMARIO);
            }
        });
        
        // Estilo de los campos de texto
        txtEmail.setBackground(Color.WHITE);
        txtEmail.setForeground(Constantes.COLOR_TEXTO_PRIMARIO);
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setForeground(Constantes.COLOR_TEXTO_PRIMARIO);
        
        // Focus listeners para efectos visuales
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmail.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Constantes.COLOR_ACCENT, 2),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmail.setBorder(Constantes.BORDE_CAMPO_TEXTO);
            }
        });
        
        txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassword.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Constantes.COLOR_ACCENT, 2),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassword.setBorder(Constantes.BORDE_CAMPO_TEXTO);
            }
        });
    }
    
    /**
     * Procesa el login del usuario (siguiendo patrón del profesor)
     */
    private void procesarLogin() {
        String email = txtEmail.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        // Validar campos vacíos
        if (email.isEmpty() || password.isEmpty()) {
            mostrarMensaje(Constantes.MSG_CAMPOS_VACIOS, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Intentar autenticación (igual que en el ejemplo del profesor)
        Persona usuario = usuarioControlador.authenticar(email, password);
        
        if (usuario != null) {
            // Login exitoso
            mostrarMensaje(Constantes.MSG_BIENVENIDA + "\n" + usuario.getNombreCompleto(), 
                          "Acceso Concedido", JOptionPane.INFORMATION_MESSAGE);
            
            abrirVentanaPrincipal(usuario);
        } else {
            // Login fallido
            mostrarMensaje(Constantes.MSG_LOGIN_FALLIDO, "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
            limpiarCampos();
        }
    }
    
    /**
     * Abre la ventana principal del sistema (igual que el ejemplo del profesor)
     * @param usuario usuario autenticado
     */
    private void abrirVentanaPrincipal(Persona usuario) {
        this.dispose(); // Cerrar ventana de login
        
        // Crear y mostrar ventana principal
        SwingUtilities.invokeLater(() -> {
            new BibliotecaMainFrame(usuario).setVisible(true);
        });
    }
    
    /**
     * Muestra mensajes elegantes al usuario
     * @param mensaje mensaje a mostrar
     * @param titulo título del diálogo
     * @param tipo tipo de mensaje
     */
    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
    
    /**
     * Limpia los campos del formulario
     */
    private void limpiarCampos() {
        txtEmail.setText("");
        txtPassword.setText("");
        txtEmail.requestFocus();
    }
    
    /**
     * Método main para testing del LoginFrame
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        // Configurar Look and Feel moderno
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            // Usar el look and feel por defecto si no se puede cargar Nimbus
        }
        
        // Ejecutar en el hilo de eventos de Swing (como en el ejemplo del profesor)
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}