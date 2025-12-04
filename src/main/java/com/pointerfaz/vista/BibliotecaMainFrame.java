package com.pointerfaz.vista;

import com.pointerfaz.controlador.UsuarioControladorNuevo;
import com.pointerfaz.controlador.LibroControladorNuevo;
import com.pointerfaz.controlador.PrestamoControladorNuevo;
import com.pointerfaz.modelo.Libro;
import com.pointerfaz.modelo.Persona;
import com.pointerfaz.modelo.Prestamo;
import com.pointerfaz.util.Constantes;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;


/**
 * Ventana principal del sistema de biblioteca
 * Interfaz moderna con pestañas y diseño elegante
 */
public class BibliotecaMainFrame extends JFrame {
    
    // Controladores (private para encapsulamiento)
    private UsuarioControladorNuevo usuarioControlador;
    private LibroControladorNuevo libroControlador;
    private PrestamoControladorNuevo prestamoControlador;
    
    // Usuario actual
    private Persona usuarioActual;
    
    // Componentes principales
    private JTabbedPane tabbedPane;
    private JPanel panelUsuarios;
    private JPanel panelLibros;
    private JPanel panelPrestamos;
    private JPanel panelReportes;
    
    // Componentes de usuarios
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTablaUsuarios;
    private JTextField txtBuscarUsuario;
    private JButton btnAgregarUsuario;
    private JButton btnEditarUsuario;
    private JButton btnEliminarUsuario;
    
    // Componentes de libros
    private JTable tablaLibros;
    private DefaultTableModel modeloTablaLibros;
    private JTextField txtBuscarLibro;
    private JButton btnAgregarLibro;
    private JButton btnEditarLibro;
    private JButton btnEliminarLibro;
    
    // Componentes de préstamos
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTablaPrestamos;
    private JButton btnNuevoPrestamo;
    private JButton btnDevolverLibro;
    private JButton btnRenovarPrestamo;
    
    // Panel de información
    private JLabel lblUsuarioActual;
    private JLabel lblEstadisticas;
    
    /**
     * Constructor principal
     * @param usuario usuario que accedió al sistema
     */
    public BibliotecaMainFrame(Persona usuario) {
        this.usuarioActual = usuario;
        inicializarControladores();
        inicializarComponentes();
        configurarVentana();
        configurarEventos();
        aplicarEstilosModernos();
        cargarDatos();
    }
    
    /**
     * Inicializa los controladores del sistema
     */
    private void inicializarControladores() {
        usuarioControlador = new UsuarioControladorNuevo();
        libroControlador = new LibroControladorNuevo();
        prestamoControlador = new PrestamoControladorNuevo();
    }
    
    /**
     * Inicializa todos los componentes de la interfaz
     */
    private void inicializarComponentes() {
        // Panel principal
        setLayout(new BorderLayout());
        
        // Crear barra superior con información del usuario
        crearBarraSuperior();
        
        // Crear panel con pestañas
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(Constantes.FUENTE_NORMAL);
        
        // Crear pestañas
        crearPestanaUsuarios();
        crearPestanaLibros();
        crearPestanaPrestamos();
        crearPestanaReportes();
        
        // Agregar pestañas al panel
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    /**
     * Crea la barra superior con información del usuario
     */
    private void crearBarraSuperior() {
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Constantes.COLOR_PRIMARIO);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        panelSuperior.setPreferredSize(new Dimension(0, 70));
        
        // Información del usuario
        lblUsuarioActual = new JLabel("👤 " + usuarioActual.getNombreCompleto() + 
                                    " (" + usuarioActual.getClass().getSimpleName() + ")");
        lblUsuarioActual.setFont(Constantes.FUENTE_SUBTITULO);
        lblUsuarioActual.setForeground(Constantes.COLOR_TEXTO_BLANCO);
        
        // Título de la aplicación
        JLabel lblTitulo = new JLabel(Constantes.TITULO_APLICACION);
        lblTitulo.setFont(Constantes.FUENTE_TITULO);
        lblTitulo.setForeground(Constantes.COLOR_TEXTO_BLANCO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Botón de cerrar sesión
        JButton btnCerrarSesion = new JButton("🚪 Cerrar Sesión");
        btnCerrarSesion.setFont(Constantes.FUENTE_NORMAL);
        btnCerrarSesion.setBackground(Constantes.COLOR_ERROR);
        btnCerrarSesion.setForeground(Constantes.COLOR_TEXTO_BLANCO);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setBorderPainted(false);
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        
        panelSuperior.add(lblUsuarioActual, BorderLayout.WEST);
        panelSuperior.add(lblTitulo, BorderLayout.CENTER);
        panelSuperior.add(btnCerrarSesion, BorderLayout.EAST);
        
        add(panelSuperior, BorderLayout.NORTH);
    }
    
    /**
     * Crea la pestaña de gestión de usuarios
     */
    private void crearPestanaUsuarios() {
        panelUsuarios = new JPanel(new BorderLayout(10, 10));
        panelUsuarios.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelUsuarios.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        // Panel superior con título y búsqueda
        JPanel panelSuperiorUsuarios = new JPanel(new BorderLayout(10, 10));
        panelSuperiorUsuarios.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        JLabel lblTituloUsuarios = new JLabel("👥 Gestión de Usuarios");
        lblTituloUsuarios.setFont(Constantes.FUENTE_SUBTITULO);
        lblTituloUsuarios.setForeground(Constantes.COLOR_PRIMARIO);
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusqueda.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        JLabel lblBuscar = new JLabel("🔍 Buscar:");
        lblBuscar.setFont(Constantes.FUENTE_NORMAL);
        txtBuscarUsuario = new JTextField(20);
        txtBuscarUsuario.setFont(Constantes.FUENTE_NORMAL);
        txtBuscarUsuario.setBorder(Constantes.BORDE_CAMPO_TEXTO);
        
        panelBusqueda.add(lblBuscar);
        panelBusqueda.add(txtBuscarUsuario);
        
        panelSuperiorUsuarios.add(lblTituloUsuarios, BorderLayout.WEST);
        panelSuperiorUsuarios.add(panelBusqueda, BorderLayout.EAST);
        
        // Tabla de usuarios elegante
        String[] columnasUsuarios = {"ID", "Tipo", "Código", "Nombres", "Apellidos", "Email", "Estado"};
        modeloTablaUsuarios = new DefaultTableModel(columnasUsuarios, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabla no editable
            }
        };
        
        tablaUsuarios = new JTable(modeloTablaUsuarios);
        configurarTabla(tablaUsuarios);
        
        JScrollPane scrollUsuarios = new JScrollPane(tablaUsuarios);
        scrollUsuarios.setBorder(BorderFactory.createLineBorder(Constantes.COLOR_SECUNDARIO, 1));
        
        // Panel de botones
        JPanel panelBotonesUsuarios = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotonesUsuarios.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        btnAgregarUsuario = crearBotonElegante("➕ Agregar Usuario", Constantes.COLOR_EXITO);
        btnEditarUsuario = crearBotonElegante("✏️ Editar Usuario", Constantes.COLOR_ACCENT);
        btnEliminarUsuario = crearBotonElegante("🗑️ Eliminar Usuario", Constantes.COLOR_ERROR);
        
        panelBotonesUsuarios.add(btnAgregarUsuario);
        panelBotonesUsuarios.add(btnEditarUsuario);
        panelBotonesUsuarios.add(btnEliminarUsuario);
        
        panelUsuarios.add(panelSuperiorUsuarios, BorderLayout.NORTH);
        panelUsuarios.add(scrollUsuarios, BorderLayout.CENTER);
        panelUsuarios.add(panelBotonesUsuarios, BorderLayout.SOUTH);
        
        tabbedPane.addTab(Constantes.TAB_USUARIOS, panelUsuarios);
    }
    
    /**
     * Crea la pestaña de gestión de libros
     */
    private void crearPestanaLibros() {
        panelLibros = new JPanel(new BorderLayout(10, 10));
        panelLibros.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelLibros.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        // Panel superior
        JPanel panelSuperiorLibros = new JPanel(new BorderLayout(10, 10));
        panelSuperiorLibros.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        JLabel lblTituloLibros = new JLabel("📚 Gestión de Libros");
        lblTituloLibros.setFont(Constantes.FUENTE_SUBTITULO);
        lblTituloLibros.setForeground(Constantes.COLOR_PRIMARIO);
        
        // Panel de búsqueda
        JPanel panelBusquedaLibros = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBusquedaLibros.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        JLabel lblBuscarLibro = new JLabel("🔍 Buscar:");
        lblBuscarLibro.setFont(Constantes.FUENTE_NORMAL);
        txtBuscarLibro = new JTextField(20);
        txtBuscarLibro.setFont(Constantes.FUENTE_NORMAL);
        txtBuscarLibro.setBorder(Constantes.BORDE_CAMPO_TEXTO);
        
        panelBusquedaLibros.add(lblBuscarLibro);
        panelBusquedaLibros.add(txtBuscarLibro);
        
        panelSuperiorLibros.add(lblTituloLibros, BorderLayout.WEST);
        panelSuperiorLibros.add(panelBusquedaLibros, BorderLayout.EAST);
        
        // Tabla de libros
        String[] columnasLibros = {"ID", "ISBN", "Título", "Autor", "Categoría", "Año", "Estado"};
        modeloTablaLibros = new DefaultTableModel(columnasLibros, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaLibros = new JTable(modeloTablaLibros);
        configurarTabla(tablaLibros);
        
        JScrollPane scrollLibros = new JScrollPane(tablaLibros);
        scrollLibros.setBorder(BorderFactory.createLineBorder(Constantes.COLOR_SECUNDARIO, 1));
        
        // Panel de botones
        JPanel panelBotonesLibros = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotonesLibros.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        btnAgregarLibro = crearBotonElegante("➕ Agregar Libro", Constantes.COLOR_EXITO);
        btnEditarLibro = crearBotonElegante("✏️ Editar Libro", Constantes.COLOR_ACCENT);
        btnEliminarLibro = crearBotonElegante("🗑️ Eliminar Libro", Constantes.COLOR_ERROR);
        
        panelBotonesLibros.add(btnAgregarLibro);
        panelBotonesLibros.add(btnEditarLibro);
        panelBotonesLibros.add(btnEliminarLibro);
        
        panelLibros.add(panelSuperiorLibros, BorderLayout.NORTH);
        panelLibros.add(scrollLibros, BorderLayout.CENTER);
        panelLibros.add(panelBotonesLibros, BorderLayout.SOUTH);
        
        tabbedPane.addTab(Constantes.TAB_LIBROS, panelLibros);
    }
    
    /**
     * Crea la pestaña de gestión de préstamos
     */
    private void crearPestanaPrestamos() {
        panelPrestamos = new JPanel(new BorderLayout(10, 10));
        panelPrestamos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrestamos.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        // Panel superior
        JPanel panelSuperiorPrestamos = new JPanel(new BorderLayout(10, 10));
        panelSuperiorPrestamos.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        JLabel lblTituloPrestamos = new JLabel("📋 Gestión de Préstamos");
        lblTituloPrestamos.setFont(Constantes.FUENTE_SUBTITULO);
        lblTituloPrestamos.setForeground(Constantes.COLOR_PRIMARIO);
        
        panelSuperiorPrestamos.add(lblTituloPrestamos, BorderLayout.WEST);
        
        // Tabla de préstamos
        String[] columnasPrestamos = {"ID", "Libro", "Usuario", "Tipo", "F. Préstamo", "F. Devolución", "Estado"};
        modeloTablaPrestamos = new DefaultTableModel(columnasPrestamos, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaPrestamos = new JTable(modeloTablaPrestamos);
        configurarTabla(tablaPrestamos);
        
        JScrollPane scrollPrestamos = new JScrollPane(tablaPrestamos);
        scrollPrestamos.setBorder(BorderFactory.createLineBorder(Constantes.COLOR_SECUNDARIO, 1));
        
        // Panel de botones
        JPanel panelBotonesPrestamos = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotonesPrestamos.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        btnNuevoPrestamo = crearBotonElegante("➕ Nuevo Préstamo", Constantes.COLOR_EXITO);
        btnDevolverLibro = crearBotonElegante("↩️ Devolver Libro", Constantes.COLOR_ACCENT);
        btnRenovarPrestamo = crearBotonElegante("🔄 Renovar Préstamo", Constantes.COLOR_WARNING);
        
        panelBotonesPrestamos.add(btnNuevoPrestamo);
        panelBotonesPrestamos.add(btnDevolverLibro);
        panelBotonesPrestamos.add(btnRenovarPrestamo);
        
        panelPrestamos.add(panelSuperiorPrestamos, BorderLayout.NORTH);
        panelPrestamos.add(scrollPrestamos, BorderLayout.CENTER);
        panelPrestamos.add(panelBotonesPrestamos, BorderLayout.SOUTH);
        
        tabbedPane.addTab(Constantes.TAB_PRESTAMOS, panelPrestamos);
    }
    
    /**
     * Crea la pestaña de reportes y estadísticas avanzada
     */
    private void crearPestanaReportes() {
        panelReportes = new JPanel(new BorderLayout(10, 10));
        panelReportes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelReportes.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        // Panel superior con título y botones de acción
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        JLabel lblTituloReportes = new JLabel("📊 Reportes y Estadísticas Avanzadas");
        lblTituloReportes.setFont(Constantes.FUENTE_SUBTITULO);
        lblTituloReportes.setForeground(Constantes.COLOR_PRIMARIO);
        
        // Panel de botones de acción
        JPanel panelBotonesReporte = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotonesReporte.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        JButton btnActualizarReportes = crearBotonElegante("� Actualizar", Constantes.COLOR_ACCENT);
        JButton btnExportarReporte = crearBotonElegante("📄 Exportar", Constantes.COLOR_EXITO);
        
        panelBotonesReporte.add(btnActualizarReportes);
        panelBotonesReporte.add(btnExportarReporte);
        
        panelSuperior.add(lblTituloReportes, BorderLayout.WEST);
        panelSuperior.add(panelBotonesReporte, BorderLayout.EAST);
        
        // Panel central con pestañas de reportes
        JTabbedPane tabbedReportes = new JTabbedPane();
        tabbedReportes.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        // Pestaña 1: Estadísticas Generales
        JPanel panelEstadisticasGenerales = crearPanelEstadisticasGenerales();
        tabbedReportes.addTab("📈 Estadísticas", panelEstadisticasGenerales);
        
        // Pestaña 2: Reportes de Usuarios
        JPanel panelReportesUsuarios = crearPanelReportesUsuarios();
        tabbedReportes.addTab("👥 Usuarios", panelReportesUsuarios);
        
        // Pestaña 3: Reportes de Libros
        JPanel panelReportesLibros = crearPanelReportesLibros();
        tabbedReportes.addTab("📚 Libros", panelReportesLibros);
        
        // Pestaña 4: Reportes de Préstamos
        JPanel panelReportesPrestamos = crearPanelReportesPrestamos();
        tabbedReportes.addTab("📋 Préstamos", panelReportesPrestamos);
        
        panelReportes.add(panelSuperior, BorderLayout.NORTH);
        panelReportes.add(tabbedReportes, BorderLayout.CENTER);
        
        // Event listeners para botones
        btnActualizarReportes.addActionListener(e -> actualizarReportes());
        btnExportarReporte.addActionListener(e -> exportarReporte());
        
        tabbedPane.addTab(Constantes.TAB_REPORTES, panelReportes);
    }
    
    /**
     * Crea una tarjeta de estadística elegante
     */
    private JPanel crearTarjetaEstadistica(String titulo, String valor, Color color) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(Constantes.FUENTE_NORMAL);
        lblTitulo.setForeground(Constantes.COLOR_TEXTO_SECUNDARIO);
        
        JLabel lblValor = new JLabel(valor, SwingConstants.CENTER);
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValor.setForeground(color);
        
        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(lblValor, BorderLayout.CENTER);
        
        return tarjeta;
    }
    
    /**
     * Crea un botón con estilo elegante
     */
    private JButton crearBotonElegante(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setFont(Constantes.FUENTE_BOTON);
        boton.setBackground(color);
        boton.setForeground(Constantes.COLOR_TEXTO_BLANCO);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(160, Constantes.ALTURA_BOTON));
        
        // Efecto hover
        Color colorOriginal = color;
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorOriginal.brighter());
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorOriginal);
            }
        });
        
        return boton;
    }
    
    /**
     * Configura el estilo de una tabla
     */
    private void configurarTabla(JTable tabla) {
        tabla.setFont(Constantes.FUENTE_NORMAL);
        tabla.setRowHeight(30);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setShowGrid(true);
        tabla.setGridColor(new Color(220, 220, 220));
        
        // Configurar header
        JTableHeader header = tabla.getTableHeader();
        header.setFont(Constantes.FUENTE_BOTON);
        header.setBackground(Constantes.COLOR_HEADER_TABLA);
        header.setForeground(Constantes.COLOR_TEXTO_BLANCO);
        header.setPreferredSize(new Dimension(0, 35));
        
        // Configurar renderer para filas alternadas
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    c.setBackground(Constantes.COLOR_SELECCION);
                } else {
                    c.setBackground(row % 2 == 0 ? Constantes.COLOR_FILA_PAR : Constantes.COLOR_FILA_IMPAR);
                }
                
                return c;
            }
        });
    }
    
    /**
     * Configura las propiedades de la ventana
     */
    private void configurarVentana() {
        setTitle(Constantes.TITULO_APLICACION);
        setSize(Constantes.ANCHO_VENTANA_PRINCIPAL, Constantes.ALTO_VENTANA_PRINCIPAL);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true); // Permitir redimensionar
        setMinimumSize(new Dimension(800, 600)); // Tamaño mínimo
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar ventana
    }
    
    /**
     * Configura los eventos de los componentes
     */
    private void configurarEventos() {
        // Eventos de usuarios (implementación completa)
        btnAgregarUsuario.addActionListener(e -> agregarUsuario());
        btnEditarUsuario.addActionListener(e -> editarUsuario());
        btnEliminarUsuario.addActionListener(e -> eliminarUsuario());
        
        // Búsqueda en tiempo real para usuarios
        txtBuscarUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarUsuarios();
            }
        });
        
        // Eventos de libros
        btnAgregarLibro.addActionListener(e -> agregarLibro());
        btnEditarLibro.addActionListener(e -> editarLibro());
        btnEliminarLibro.addActionListener(e -> eliminarLibro());
        
        // Búsqueda en tiempo real para libros
        txtBuscarLibro.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                buscarLibros();
            }
        });
        
        // Eventos de préstamos
        btnNuevoPrestamo.addActionListener(e -> nuevoPrestamo());
        btnDevolverLibro.addActionListener(e -> devolverLibro());
        btnRenovarPrestamo.addActionListener(e -> renovarPrestamo());
    }
    
    /**
     * Aplica estilos modernos a la interfaz
     */
    private void aplicarEstilosModernos() {
        // Configurar estilo del TabbedPane
        tabbedPane.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        tabbedPane.setForeground(Constantes.COLOR_TEXTO_PRIMARIO);
        
        // Configurar Look and Feel
        try {
            UIManager.put("TabbedPane.selected", Constantes.COLOR_PRIMARIO);
            UIManager.put("TabbedPane.focus", Constantes.COLOR_SECUNDARIO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Carga los datos iniciales en las tablas
     */
    private void cargarDatos() {
        cargarUsuarios();
        cargarLibros();
        cargarPrestamos();
    }
    
    /**
     * Carga usuarios en la tabla (siguiendo patrón del profesor)
     */
    private void cargarUsuarios() {
        modeloTablaUsuarios.setRowCount(0);
        
        usuarioControlador.listarUsuarios().forEach(usuario -> {
            Object[] fila = {
                usuario.getId(),
                usuario.getClass().getSimpleName(),
                usuario instanceof com.pointerfaz.modelo.Estudiante ? 
                    ((com.pointerfaz.modelo.Estudiante) usuario).getCodigo() : 
                    (usuario instanceof com.pointerfaz.modelo.Profesor ? 
                        ((com.pointerfaz.modelo.Profesor) usuario).getCodigoEmpleado() : "N/A"),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario instanceof com.pointerfaz.modelo.Estudiante ? 
                    ((com.pointerfaz.modelo.Estudiante) usuario).getEstado() : "Activo"
            };
            modeloTablaUsuarios.addRow(fila);
        });
    }
    
    /**
     * Carga libros en la tabla
     */
    private void cargarLibros() {
        modeloTablaLibros.setRowCount(0);
        
        libroControlador.listarLibros().forEach(libro -> {
            Object[] fila = {
                libro.getId(),
                libro.getIsbn(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getCategoria(),
                libro.getAnioPublicacion(),
                libro.getEstado()
            };
            modeloTablaLibros.addRow(fila);
        });
    }
    
    /**
     * Carga préstamos en la tabla
     */
    private void cargarPrestamos() {
        modeloTablaPrestamos.setRowCount(0);
        
        prestamoControlador.listarPrestamos().forEach(prestamo -> {
            Object[] fila = {
                prestamo.getId(),
                "Libro ID: " + prestamo.getLibroId(),
                "Usuario ID: " + prestamo.getUsuarioId(),
                prestamo.getTipoUsuario(),
                prestamo.getFechaPrestamoString(),
                prestamo.getFechaDevolucionEsperadaString(),
                prestamo.getEstadoDescriptivo()
            };
            modeloTablaPrestamos.addRow(fila);
        });
    }
    
    /**
     * Cierra la sesión actual
     */
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this, 
            "¿Está seguro que desea cerrar sesión?", 
            "Cerrar Sesión", 
            JOptionPane.YES_NO_OPTION);
        
        if (opcion == JOptionPane.YES_OPTION) {
            this.dispose();
            SwingUtilities.invokeLater(() -> {
                new LoginFrame().setVisible(true);
            });
        }
    }
    
    /**
     * Muestra mensajes al usuario
     */
    private void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    
    // =============== MÉTODOS DE FUNCIONALIDAD DE USUARIOS ===============
    
    /**
     * Agregar nuevo usuario
     */
    private void agregarUsuario() {
        UsuarioDialog dialog = new UsuarioDialog(this);
        dialog.setVisible(true);
        
        if (dialog.fueGuardado()) {
            Persona nuevoUsuario = dialog.getUsuario();
            if (nuevoUsuario != null) {
                // Asignar nuevo ID
                int nuevoId = usuarioControlador.listarUsuarios().size() + 1;
                nuevoUsuario.setId(nuevoId);
                
                // Agregar al controlador
                usuarioControlador.agregarUsuario(nuevoUsuario);
                
                // Recargar tabla
                cargarUsuarios();
                
                // Mensaje de éxito
                JOptionPane.showMessageDialog(this, 
                    Constantes.ICONO_EXITO + " Usuario agregado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * Editar usuario seleccionado
     */
    private void editarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                Constantes.ICONO_WARNING + " Por favor seleccione un usuario para editar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener ID del usuario seleccionado
        int idUsuario = (Integer) tablaUsuarios.getValueAt(filaSeleccionada, 0);
        Persona usuarioEditar = usuarioControlador.buscarUsuario(idUsuario);
        
        if (usuarioEditar != null) {
            UsuarioDialog dialog = new UsuarioDialog(this, usuarioEditar);
            dialog.setVisible(true);
            
            if (dialog.fueGuardado()) {
                Persona usuarioEditado = dialog.getUsuario();
                if (usuarioEditado != null) {
                    // Mantener el ID original
                    usuarioEditado.setId(idUsuario);
                    
                    // Editar en el controlador
                    usuarioControlador.editarUsuario(idUsuario, usuarioEditado);
                    
                    // Recargar tabla
                    cargarUsuarios();
                    
                    // Mensaje de éxito
                    JOptionPane.showMessageDialog(this, 
                        Constantes.ICONO_EXITO + " Usuario editado exitosamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    /**
     * Eliminar usuario seleccionado
     */
    private void eliminarUsuario() {
        int filaSeleccionada = tablaUsuarios.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                Constantes.ICONO_WARNING + " Por favor seleccione un usuario para eliminar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener datos del usuario
        int idUsuario = (Integer) tablaUsuarios.getValueAt(filaSeleccionada, 0);
        String nombreCompleto = tablaUsuarios.getValueAt(filaSeleccionada, 3) + " " + 
                               tablaUsuarios.getValueAt(filaSeleccionada, 4);
        
        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            Constantes.ICONO_WARNING + " ¿Está seguro que desea eliminar al usuario:\n" + nombreCompleto + "?\n\nEsta acción no se puede deshacer.", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Eliminar del controlador
            usuarioControlador.eliminarUsuario(idUsuario);
            
            // Recargar tabla
            cargarUsuarios();
            
            // Mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                Constantes.ICONO_EXITO + " Usuario eliminado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Buscar usuarios en tiempo real
     */
    private void buscarUsuarios() {
        String textoBusqueda = txtBuscarUsuario.getText().trim().toLowerCase();
        
        // Limpiar tabla
        modeloTablaUsuarios.setRowCount(0);
        
        // Si no hay texto de búsqueda, mostrar todos
        if (textoBusqueda.isEmpty()) {
            cargarUsuarios();
            return;
        }
        
        // Filtrar usuarios que coincidan con la búsqueda
        usuarioControlador.listarUsuarios().forEach(usuario -> {
            boolean coincide = false;
            
            // Buscar en nombres
            if (usuario.getNombres() != null && 
                usuario.getNombres().toLowerCase().contains(textoBusqueda)) {
                coincide = true;
            }
            
            // Buscar en apellidos
            if (usuario.getApellidos() != null && 
                usuario.getApellidos().toLowerCase().contains(textoBusqueda)) {
                coincide = true;
            }
            
            // Buscar en email
            if (usuario.getEmail() != null && 
                usuario.getEmail().toLowerCase().contains(textoBusqueda)) {
                coincide = true;
            }
            
            // Buscar en código
            String codigo = "";
            if (usuario instanceof com.pointerfaz.modelo.Estudiante) {
                codigo = ((com.pointerfaz.modelo.Estudiante) usuario).getCodigo();
            } else if (usuario instanceof com.pointerfaz.modelo.Profesor) {
                codigo = ((com.pointerfaz.modelo.Profesor) usuario).getCodigoEmpleado();
            }
            if (codigo != null && codigo.toLowerCase().contains(textoBusqueda)) {
                coincide = true;
            }
            
            // Si coincide, agregar a la tabla
            if (coincide) {
                Object[] fila = {
                    usuario.getId(),
                    usuario.getClass().getSimpleName(),
                    codigo,
                    usuario.getNombres(),
                    usuario.getApellidos(),
                    usuario.getEmail(),
                    usuario instanceof com.pointerfaz.modelo.Estudiante ? 
                        ((com.pointerfaz.modelo.Estudiante) usuario).getEstado() : "Activo"
                };
                modeloTablaUsuarios.addRow(fila);
            }
        });
    }
    
    /**
     * Buscar libros en tiempo real
     */
    private void buscarLibros() {
        String textoBusqueda = txtBuscarLibro.getText().trim().toLowerCase();
        
        // Limpiar tabla
        modeloTablaLibros.setRowCount(0);
        
        // Si no hay texto de búsqueda, mostrar todos
        if (textoBusqueda.isEmpty()) {
            cargarLibros();
            return;
        }
        
        // Filtrar libros que coincidan con la búsqueda
        libroControlador.listarLibros().forEach(libro -> {
            boolean coincide = false;
            
            // Buscar en título
            if (libro.getTitulo() != null && 
                libro.getTitulo().toLowerCase().contains(textoBusqueda)) {
                coincide = true;
            }
            
            // Buscar en autor
            if (libro.getAutor() != null && 
                libro.getAutor().toLowerCase().contains(textoBusqueda)) {
                coincide = true;
            }
            
            // Buscar en ISBN
            if (libro.getIsbn() != null && 
                libro.getIsbn().toLowerCase().contains(textoBusqueda)) {
                coincide = true;
            }
            
            // Buscar en categoría
            if (libro.getCategoria() != null && 
                libro.getCategoria().toLowerCase().contains(textoBusqueda)) {
                coincide = true;
            }
            
            // Si coincide, agregar a la tabla
            if (coincide) {
                Object[] fila = {
                    libro.getId(),
                    libro.getIsbn(),
                    libro.getTitulo(),
                    libro.getAutor(),
                    libro.getCategoria(),
                    libro.getAnioPublicacion(),
                    libro.getEstado()
                };
                modeloTablaLibros.addRow(fila);
            }
        });
    }
    
    // =============== MÉTODOS DE FUNCIONALIDAD DE LIBROS ===============
    
    /**
     * Agregar nuevo libro
     */
    private void agregarLibro() {
        LibroDialog dialog = new LibroDialog(this);
        dialog.setVisible(true);
        
        if (dialog.fueGuardado()) {
            Libro nuevoLibro = dialog.getLibro();
            if (nuevoLibro != null) {
                // Asignar nuevo ID
                int nuevoId = libroControlador.listarLibros().size() + 1;
                nuevoLibro.setId(nuevoId);
                
                // Agregar al controlador
                libroControlador.agregarLibro(nuevoLibro);
                
                // Recargar tabla
                cargarLibros();
                
                // Mensaje de éxito
                JOptionPane.showMessageDialog(this, 
                    Constantes.ICONO_EXITO + " Libro agregado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * Editar libro seleccionado
     */
    private void editarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                Constantes.ICONO_WARNING + " Por favor seleccione un libro para editar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener ID del libro seleccionado
        int idLibro = (Integer) tablaLibros.getValueAt(filaSeleccionada, 0);
        Libro libroEditar = libroControlador.buscarLibro(idLibro);
        
        if (libroEditar != null) {
            LibroDialog dialog = new LibroDialog(this, libroEditar);
            dialog.setVisible(true);
            
            if (dialog.fueGuardado()) {
                Libro libroEditado = dialog.getLibro();
                if (libroEditado != null) {
                    // Mantener el ID original
                    libroEditado.setId(idLibro);
                    
                    // Editar en el controlador
                    libroControlador.editarLibro(idLibro, libroEditado);
                    
                    // Recargar tabla
                    cargarLibros();
                    
                    // Mensaje de éxito
                    JOptionPane.showMessageDialog(this, 
                        Constantes.ICONO_EXITO + " Libro editado exitosamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    /**
     * Eliminar libro seleccionado
     */
    private void eliminarLibro() {
        int filaSeleccionada = tablaLibros.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                Constantes.ICONO_WARNING + " Por favor seleccione un libro para eliminar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener datos del libro
        int idLibro = (Integer) tablaLibros.getValueAt(filaSeleccionada, 0);
        String tituloLibro = (String) tablaLibros.getValueAt(filaSeleccionada, 2);
        String autorLibro = (String) tablaLibros.getValueAt(filaSeleccionada, 3);
        
        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(this, 
            Constantes.ICONO_WARNING + " ¿Está seguro que desea eliminar el libro:\n\"" + tituloLibro + "\" de " + autorLibro + "?\n\nEsta acción no se puede deshacer.", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            // Eliminar del controlador
            libroControlador.eliminarLibro(idLibro);
            
            // Recargar tabla
            cargarLibros();
            
            // Mensaje de éxito
            JOptionPane.showMessageDialog(this, 
                Constantes.ICONO_EXITO + " Libro eliminado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // =============== MÉTODOS DE FUNCIONALIDAD DE PRÉSTAMOS ===============
    
    /**
     * Crear nuevo préstamo
     */
    private void nuevoPrestamo() {
        PrestamoDialog dialog = new PrestamoDialog(this, usuarioControlador, libroControlador, prestamoControlador);
        dialog.setVisible(true);
        
        if (dialog.fueGuardado()) {
            Prestamo nuevoPrestamo = dialog.getPrestamo();
            if (nuevoPrestamo != null) {
                // Asignar nuevo ID
                int nuevoId = prestamoControlador.listarPrestamos().size() + 1;
                nuevoPrestamo.setId(nuevoId);
                
                // Agregar al controlador
                prestamoControlador.agregarPrestamo(nuevoPrestamo);
                
                // Actualizar estado del libro a "Prestado"
                Libro libro = libroControlador.buscarLibro(nuevoPrestamo.getLibroId());
                if (libro != null) {
                    libro.setEstado("Prestado");
                    libroControlador.editarLibro(libro.getId(), libro);
                }
                
                // Recargar tablas
                cargarPrestamos();
                cargarLibros(); // Para actualizar el estado del libro
                
                // Mensaje de éxito
                JOptionPane.showMessageDialog(this, 
                    Constantes.ICONO_EXITO + " Préstamo creado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * Devolver libro seleccionado
     */
    private void devolverLibro() {
        int filaSeleccionada = tablaPrestamos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                Constantes.ICONO_WARNING + " Por favor seleccione un préstamo para devolver", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Verificar que el préstamo esté activo
        String estadoPrestamo = (String) tablaPrestamos.getValueAt(filaSeleccionada, 6); // Columna estado
        if (!"Activo".equals(estadoPrestamo)) {
            JOptionPane.showMessageDialog(this, 
                Constantes.ICONO_WARNING + " Solo se pueden devolver préstamos activos", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener ID del préstamo seleccionado
        int idPrestamo = (Integer) tablaPrestamos.getValueAt(filaSeleccionada, 0);
        Prestamo prestamoDevolver = prestamoControlador.buscarPrestamo(idPrestamo);
        
        if (prestamoDevolver != null) {
            PrestamoDialog dialog = new PrestamoDialog(this, usuarioControlador, libroControlador, 
                                                      prestamoControlador, prestamoDevolver, "DEVOLVER");
            dialog.setVisible(true);
            
            if (dialog.fueGuardado()) {
                Prestamo prestamoDevuelto = dialog.getPrestamo();
                if (prestamoDevuelto != null) {
                    // Actualizar en el controlador
                    prestamoControlador.editarPrestamo(idPrestamo, prestamoDevuelto);
                    
                    // Actualizar estado del libro a "Disponible"
                    Libro libro = libroControlador.buscarLibro(prestamoDevuelto.getLibroId());
                    if (libro != null) {
                        libro.setEstado("Disponible");
                        libroControlador.editarLibro(libro.getId(), libro);
                    }
                    
                    // Recargar tablas
                    cargarPrestamos();
                    cargarLibros(); // Para actualizar el estado del libro
                    
                    // Mensaje de éxito
                    JOptionPane.showMessageDialog(this, 
                        Constantes.ICONO_EXITO + " Libro devuelto exitosamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    /**
     * Renovar préstamo seleccionado
     */
    private void renovarPrestamo() {
        int filaSeleccionada = tablaPrestamos.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                Constantes.ICONO_WARNING + " Por favor seleccione un préstamo para renovar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Verificar que el préstamo esté activo
        String estadoPrestamo = (String) tablaPrestamos.getValueAt(filaSeleccionada, 6); // Columna estado
        if (!"Activo".equals(estadoPrestamo)) {
            JOptionPane.showMessageDialog(this, 
                Constantes.ICONO_WARNING + " Solo se pueden renovar préstamos activos", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener ID del préstamo seleccionado
        int idPrestamo = (Integer) tablaPrestamos.getValueAt(filaSeleccionada, 0);
        Prestamo prestamoRenovar = prestamoControlador.buscarPrestamo(idPrestamo);
        
        if (prestamoRenovar != null) {
            PrestamoDialog dialog = new PrestamoDialog(this, usuarioControlador, libroControlador, 
                                                      prestamoControlador, prestamoRenovar, "RENOVAR");
            dialog.setVisible(true);
            
            if (dialog.fueGuardado()) {
                Prestamo prestamoRenovado = dialog.getPrestamo();
                if (prestamoRenovado != null) {
                    // Actualizar en el controlador
                    prestamoControlador.editarPrestamo(idPrestamo, prestamoRenovado);
                    
                    // Recargar tabla
                    cargarPrestamos();
                    
                    // Mensaje de éxito
                    JOptionPane.showMessageDialog(this, 
                        Constantes.ICONO_EXITO + " Préstamo renovado exitosamente", 
                        "Éxito", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    // =============== MÉTODOS DE REPORTES AVANZADOS ===============
    
    /**
     * Crea el panel de estadísticas generales
     */
    private JPanel crearPanelEstadisticasGenerales() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        // Panel superior con tarjetas de estadísticas principales
        JPanel panelTarjetas = new JPanel(new GridLayout(2, 4, 15, 15));
        panelTarjetas.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        panelTarjetas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Calcular estadísticas
        int totalUsuarios = usuarioControlador.listarUsuarios().size();
        int totalLibros = libroControlador.listarLibros().size();
        int totalPrestamos = prestamoControlador.listarPrestamos().size();
        int prestamosActivos = (int) prestamoControlador.listarPrestamos().stream()
            .filter(p -> "Activo".equals(p.getEstado())).count();
        int prestamosVencidos = prestamoControlador.obtenerPrestamosVencidos().size();
        int librosDisponibles = (int) libroControlador.listarLibros().stream()
            .filter(l -> "Disponible".equals(l.getEstado())).count();
        int librosPrestados = (int) libroControlador.listarLibros().stream()
            .filter(l -> "Prestado".equals(l.getEstado())).count();
        int prestamosDevueltos = (int) prestamoControlador.listarPrestamos().stream()
            .filter(p -> "Devuelto".equals(p.getEstado())).count();
        
        // Crear tarjetas de estadísticas
        panelTarjetas.add(crearTarjetaEstadistica("👥 Total Usuarios", String.valueOf(totalUsuarios), Constantes.COLOR_PRIMARIO));
        panelTarjetas.add(crearTarjetaEstadistica("📚 Total Libros", String.valueOf(totalLibros), Constantes.COLOR_EXITO));
        panelTarjetas.add(crearTarjetaEstadistica("📋 Total Préstamos", String.valueOf(totalPrestamos), Constantes.COLOR_ACCENT));
        panelTarjetas.add(crearTarjetaEstadistica("✅ Préstamos Activos", String.valueOf(prestamosActivos), Constantes.COLOR_EXITO));
        panelTarjetas.add(crearTarjetaEstadistica("⚠️ Préstamos Vencidos", String.valueOf(prestamosVencidos), Constantes.COLOR_ERROR));
        panelTarjetas.add(crearTarjetaEstadistica("📖 Libros Disponibles", String.valueOf(librosDisponibles), Constantes.COLOR_EXITO));
        panelTarjetas.add(crearTarjetaEstadistica("📝 Libros Prestados", String.valueOf(librosPrestados), Constantes.COLOR_WARNING));
        panelTarjetas.add(crearTarjetaEstadistica("↩️ Préstamos Devueltos", String.valueOf(prestamosDevueltos), Constantes.COLOR_SECUNDARIO));
        
        // Panel inferior con análisis de rendimiento
        JPanel panelAnalisis = new JPanel(new BorderLayout());
        panelAnalisis.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        panelAnalisis.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Constantes.COLOR_PRIMARIO, 2), 
            "📊 Análisis de Rendimiento"));
        
        JTextArea txtAnalisis = new JTextArea(8, 50);
        txtAnalisis.setEditable(false);
        txtAnalisis.setBackground(Color.WHITE);
        txtAnalisis.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        txtAnalisis.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Generar análisis
        StringBuilder analisis = new StringBuilder();
        analisis.append("=== ANÁLISIS DEL SISTEMA ===\n\n");
        analisis.append(String.format("• Usuarios registrados: %d\n", totalUsuarios));
        analisis.append(String.format("• Libros en catálogo: %d\n", totalLibros));
        analisis.append(String.format("• Préstamos procesados: %d\n\n", totalPrestamos));
        
        analisis.append("=== ESTADO ACTUAL ===\n");
        analisis.append(String.format("• Préstamos activos: %d (%.1f%%)\n", prestamosActivos, 
            totalPrestamos > 0 ? (prestamosActivos * 100.0 / totalPrestamos) : 0));
        analisis.append(String.format("• Préstamos vencidos: %d (%.1f%%)\n", prestamosVencidos,
            totalPrestamos > 0 ? (prestamosVencidos * 100.0 / totalPrestamos) : 0));
        analisis.append(String.format("• Préstamos devueltos: %d (%.1f%%)\n\n", prestamosDevueltos,
            totalPrestamos > 0 ? (prestamosDevueltos * 100.0 / totalPrestamos) : 0));
        
        analisis.append("=== DISPONIBILIDAD DE LIBROS ===\n");
        analisis.append(String.format("• Libros disponibles: %d (%.1f%%)\n", librosDisponibles,
            totalLibros > 0 ? (librosDisponibles * 100.0 / totalLibros) : 0));
        analisis.append(String.format("• Libros prestados: %d (%.1f%%)\n", librosPrestados,
            totalLibros > 0 ? (librosPrestados * 100.0 / totalLibros) : 0));
        
        analisis.append("\n=== RECOMENDACIONES ===\n");
        if (prestamosVencidos > 0) {
            analisis.append("⚠️ Atención: Hay préstamos vencidos que requieren seguimiento.\n");
        }
        if (librosDisponibles < totalLibros * 0.3) {
            analisis.append("📚 Considerar adquisición de nuevos libros.\n");
        }
        if (prestamosActivos > totalLibros * 0.8) {
            analisis.append("📈 Alta demanda - sistema funcionando eficientemente.\n");
        }
        
        txtAnalisis.setText(analisis.toString());
        
        JScrollPane scrollAnalisis = new JScrollPane(txtAnalisis);
        scrollAnalisis.setPreferredSize(new Dimension(600, 200));
        panelAnalisis.add(scrollAnalisis, BorderLayout.CENTER);
        
        panel.add(panelTarjetas, BorderLayout.NORTH);
        panel.add(panelAnalisis, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea el panel de reportes de usuarios
     */
    private JPanel crearPanelReportesUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        // Panel superior con estadísticas de usuarios
        JPanel panelEstadisticasUsuarios = new JPanel(new GridLayout(1, 3, 10, 10));
        panelEstadisticasUsuarios.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        panelEstadisticasUsuarios.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Calcular estadísticas por tipo de usuario
        long estudiantes = usuarioControlador.listarUsuarios().stream()
            .filter(u -> u.getClass().getSimpleName().equals("Estudiante")).count();
        long estudiantesGraduados = usuarioControlador.listarUsuarios().stream()
            .filter(u -> u.getClass().getSimpleName().equals("EstudianteGraduado")).count();
        long profesores = usuarioControlador.listarUsuarios().stream()
            .filter(u -> u.getClass().getSimpleName().equals("Profesor")).count();
        
        panelEstadisticasUsuarios.add(crearTarjetaEstadistica("🎓 Estudiantes", String.valueOf(estudiantes), Constantes.COLOR_PRIMARIO));
        panelEstadisticasUsuarios.add(crearTarjetaEstadistica("👨‍🎓 Graduados", String.valueOf(estudiantesGraduados), Constantes.COLOR_ACCENT));
        panelEstadisticasUsuarios.add(crearTarjetaEstadistica("👨‍🏫 Profesores", String.valueOf(profesores), Constantes.COLOR_EXITO));
        
        // Tabla detallada de usuarios
        JPanel panelTablaUsuarios = new JPanel(new BorderLayout());
        panelTablaUsuarios.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Constantes.COLOR_PRIMARIO, 2), 
            "👥 Detalle de Usuarios"));
        
        DefaultTableModel modeloUsuariosReporte = new DefaultTableModel();
        modeloUsuariosReporte.addColumn("Tipo");
        modeloUsuariosReporte.addColumn("Nombre Completo");
        modeloUsuariosReporte.addColumn("Email");
        modeloUsuariosReporte.addColumn("Código");
        modeloUsuariosReporte.addColumn("Préstamos Activos");
        
        usuarioControlador.listarUsuarios().forEach(usuario -> {
            String codigo = "";
            if (usuario instanceof com.pointerfaz.modelo.Estudiante) {
                codigo = ((com.pointerfaz.modelo.Estudiante) usuario).getCodigo();
            } else if (usuario instanceof com.pointerfaz.modelo.Profesor) {
                codigo = ((com.pointerfaz.modelo.Profesor) usuario).getCodigoEmpleado();
            }
            
            long prestamosActivosUsuario = prestamoControlador.listarPrestamos().stream()
                .filter(p -> p.getUsuarioId() == usuario.getId() && "Activo".equals(p.getEstado()))
                .count();
            
            Object[] fila = {
                usuario.getClass().getSimpleName(),
                usuario.getNombres() + " " + usuario.getApellidos(),
                usuario.getEmail(),
                codigo,
                prestamosActivosUsuario
            };
            modeloUsuariosReporte.addRow(fila);
        });
        
        JTable tablaUsuariosReporte = new JTable(modeloUsuariosReporte);
        estilizarTabla(tablaUsuariosReporte);
        
        JScrollPane scrollUsuariosReporte = new JScrollPane(tablaUsuariosReporte);
        scrollUsuariosReporte.setPreferredSize(new Dimension(700, 300));
        panelTablaUsuarios.add(scrollUsuariosReporte, BorderLayout.CENTER);
        
        panel.add(panelEstadisticasUsuarios, BorderLayout.NORTH);
        panel.add(panelTablaUsuarios, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea el panel de reportes de libros
     */
    private JPanel crearPanelReportesLibros() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        // Panel superior con estadísticas de libros por estado
        JPanel panelEstadisticasLibros = new JPanel(new GridLayout(1, 4, 10, 10));
        panelEstadisticasLibros.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        panelEstadisticasLibros.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Calcular estadísticas por estado
        long disponibles = libroControlador.listarLibros().stream()
            .filter(l -> "Disponible".equals(l.getEstado())).count();
        long prestados = libroControlador.listarLibros().stream()
            .filter(l -> "Prestado".equals(l.getEstado())).count();
        long enReparacion = libroControlador.listarLibros().stream()
            .filter(l -> "En Reparación".equals(l.getEstado())).count();
        long perdidos = libroControlador.listarLibros().stream()
            .filter(l -> "Perdido".equals(l.getEstado())).count();
        
        panelEstadisticasLibros.add(crearTarjetaEstadistica("✅ Disponibles", String.valueOf(disponibles), Constantes.COLOR_EXITO));
        panelEstadisticasLibros.add(crearTarjetaEstadistica("📝 Prestados", String.valueOf(prestados), Constantes.COLOR_WARNING));
        panelEstadisticasLibros.add(crearTarjetaEstadistica("🔧 En Reparación", String.valueOf(enReparacion), Constantes.COLOR_ACCENT));
        panelEstadisticasLibros.add(crearTarjetaEstadistica("❌ Perdidos", String.valueOf(perdidos), Constantes.COLOR_ERROR));
        
        // Tabla detallada de libros
        JPanel panelTablaLibros = new JPanel(new BorderLayout());
        panelTablaLibros.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Constantes.COLOR_PRIMARIO, 2), 
            "📚 Inventario Detallado"));
        
        DefaultTableModel modeloLibrosReporte = new DefaultTableModel();
        modeloLibrosReporte.addColumn("Título");
        modeloLibrosReporte.addColumn("Autor");
        modeloLibrosReporte.addColumn("Categoría");
        modeloLibrosReporte.addColumn("Año");
        modeloLibrosReporte.addColumn("Estado");
        modeloLibrosReporte.addColumn("Veces Prestado");
        
        libroControlador.listarLibros().forEach(libro -> {
            long vecesPrestado = prestamoControlador.listarPrestamos().stream()
                .filter(p -> p.getLibroId() == libro.getId())
                .count();
            
            Object[] fila = {
                libro.getTitulo(),
                libro.getAutor(),
                libro.getCategoria(),
                libro.getAnioPublicacion(),
                libro.getEstado(),
                vecesPrestado
            };
            modeloLibrosReporte.addRow(fila);
        });
        
        JTable tablaLibrosReporte = new JTable(modeloLibrosReporte);
        estilizarTabla(tablaLibrosReporte);
        
        JScrollPane scrollLibrosReporte = new JScrollPane(tablaLibrosReporte);
        scrollLibrosReporte.setPreferredSize(new Dimension(700, 300));
        panelTablaLibros.add(scrollLibrosReporte, BorderLayout.CENTER);
        
        panel.add(panelEstadisticasLibros, BorderLayout.NORTH);
        panel.add(panelTablaLibros, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea el panel de reportes de préstamos
     */
    private JPanel crearPanelReportesPrestamos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        // Panel superior dividido en dos secciones
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 20, 10));
        panelSuperior.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Sección izquierda: Préstamos por estado
        JPanel panelEstadosPrestamos = new JPanel(new GridLayout(2, 2, 10, 10));
        panelEstadosPrestamos.setBorder(BorderFactory.createTitledBorder("Estados de Préstamos"));
        panelEstadosPrestamos.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        long activos = prestamoControlador.listarPrestamos().stream()
            .filter(p -> "Activo".equals(p.getEstado())).count();
        long devueltos = prestamoControlador.listarPrestamos().stream()
            .filter(p -> "Devuelto".equals(p.getEstado())).count();
        long vencidos = prestamoControlador.obtenerPrestamosVencidos().size();
        long renovados = prestamoControlador.listarPrestamos().stream()
            .filter(p -> "Renovado".equals(p.getEstado())).count();
        
        panelEstadosPrestamos.add(crearTarjetaEstadisticaPequena("✅ Activos", String.valueOf(activos), Constantes.COLOR_EXITO));
        panelEstadosPrestamos.add(crearTarjetaEstadisticaPequena("↩️ Devueltos", String.valueOf(devueltos), Constantes.COLOR_SECUNDARIO));
        panelEstadosPrestamos.add(crearTarjetaEstadisticaPequena("⚠️ Vencidos", String.valueOf(vencidos), Constantes.COLOR_ERROR));
        panelEstadosPrestamos.add(crearTarjetaEstadisticaPequena("🔄 Renovados", String.valueOf(renovados), Constantes.COLOR_WARNING));
        
        // Sección derecha: Próximos a vencer
        JPanel panelProximosVencer = new JPanel(new BorderLayout());
        panelProximosVencer.setBorder(BorderFactory.createTitledBorder("⏰ Próximos a Vencer (7 días)"));
        panelProximosVencer.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        DefaultTableModel modeloProximosVencer = new DefaultTableModel();
        modeloProximosVencer.addColumn("Usuario");
        modeloProximosVencer.addColumn("Libro");
        modeloProximosVencer.addColumn("Vence");
        
        prestamoControlador.obtenerPrestamosProximosVencer(7).forEach(prestamo -> {
            Persona usuario = usuarioControlador.buscarUsuario(prestamo.getUsuarioId());
            Libro libro = libroControlador.buscarLibro(prestamo.getLibroId());
            
            Object[] fila = {
                usuario != null ? usuario.getNombres() + " " + usuario.getApellidos() : "Usuario no encontrado",
                libro != null ? libro.getTitulo() : "Libro no encontrado",
                prestamo.getFechaDevolucionEsperadaString()
            };
            modeloProximosVencer.addRow(fila);
        });
        
        JTable tablaProximosVencer = new JTable(modeloProximosVencer);
        estilizarTabla(tablaProximosVencer);
        tablaProximosVencer.setPreferredScrollableViewportSize(new Dimension(350, 150));
        
        JScrollPane scrollProximosVencer = new JScrollPane(tablaProximosVencer);
        panelProximosVencer.add(scrollProximosVencer, BorderLayout.CENTER);
        
        panelSuperior.add(panelEstadosPrestamos);
        panelSuperior.add(panelProximosVencer);
        
        // Panel inferior: Tabla completa de préstamos
        JPanel panelTablaPrestamos = new JPanel(new BorderLayout());
        panelTablaPrestamos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Constantes.COLOR_PRIMARIO, 2), 
            "📋 Historial Completo de Préstamos"));
        
        DefaultTableModel modeloPrestamosReporte = new DefaultTableModel();
        modeloPrestamosReporte.addColumn("ID");
        modeloPrestamosReporte.addColumn("Usuario");
        modeloPrestamosReporte.addColumn("Libro");
        modeloPrestamosReporte.addColumn("Fecha Préstamo");
        modeloPrestamosReporte.addColumn("Fecha Esperada");
        modeloPrestamosReporte.addColumn("Fecha Real");
        modeloPrestamosReporte.addColumn("Estado");
        
        prestamoControlador.listarPrestamos().forEach(prestamo -> {
            Persona usuario = usuarioControlador.buscarUsuario(prestamo.getUsuarioId());
            Libro libro = libroControlador.buscarLibro(prestamo.getLibroId());
            
            Object[] fila = {
                prestamo.getId(),
                usuario != null ? usuario.getNombres() + " " + usuario.getApellidos() : "N/A",
                libro != null ? libro.getTitulo() : "N/A",
                prestamo.getFechaPrestamoString(),
                prestamo.getFechaDevolucionEsperadaString(),
                prestamo.getFechaDevolucionRealString() != null ? prestamo.getFechaDevolucionRealString() : "Pendiente",
                prestamo.getEstado()
            };
            modeloPrestamosReporte.addRow(fila);
        });
        
        JTable tablaPrestamosReporte = new JTable(modeloPrestamosReporte);
        estilizarTabla(tablaPrestamosReporte);
        
        JScrollPane scrollPrestamosReporte = new JScrollPane(tablaPrestamosReporte);
        scrollPrestamosReporte.setPreferredSize(new Dimension(700, 200));
        panelTablaPrestamos.add(scrollPrestamosReporte, BorderLayout.CENTER);
        
        panel.add(panelSuperior, BorderLayout.NORTH);
        panel.add(panelTablaPrestamos, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Crea una tarjeta de estadística pequeña
     */
    private JPanel crearTarjetaEstadisticaPequena(String titulo, String valor, Color color) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        lblTitulo.setForeground(color);
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        
        JLabel lblValor = new JLabel(valor);
        lblValor.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        lblValor.setForeground(color);
        lblValor.setHorizontalAlignment(JLabel.CENTER);
        
        tarjeta.add(lblTitulo, BorderLayout.NORTH);
        tarjeta.add(lblValor, BorderLayout.CENTER);
        
        return tarjeta;
    }
    
    /**
     * Estiliza una tabla con el formato estándar
     */
    private void estilizarTabla(JTable tabla) {
        tabla.setFont(Constantes.FUENTE_NORMAL);
        tabla.setRowHeight(25);
        tabla.setGridColor(Constantes.COLOR_BORDE);
        tabla.setSelectionBackground(Constantes.COLOR_SELECCION);
        tabla.setSelectionForeground(Color.BLACK);
        tabla.setShowGrid(true);
        tabla.getTableHeader().setFont(Constantes.FUENTE_BOTON);
        tabla.getTableHeader().setBackground(Constantes.COLOR_HEADER_TABLA);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
    
    /**
     * Actualiza todos los reportes
     */
    private void actualizarReportes() {
        // Recrear la pestaña de reportes
        int indiceReportes = tabbedPane.indexOfTab(Constantes.TAB_REPORTES);
        if (indiceReportes != -1) {
            tabbedPane.removeTabAt(indiceReportes);
            crearPestanaReportes();
            tabbedPane.setSelectedIndex(indiceReportes);
        }
        
        JOptionPane.showMessageDialog(this,
            Constantes.ICONO_EXITO + " Reportes actualizados exitosamente",
            "Actualización Completa",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Exporta el reporte actual
     */
    private void exportarReporte() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE SISTEMA DE BIBLIOTECA ===\n");
        reporte.append("Fecha: ").append(java.time.LocalDate.now()).append("\n\n");
        
        // Estadísticas generales
        reporte.append("ESTADÍSTICAS GENERALES:\n");
        reporte.append("- Total Usuarios: ").append(usuarioControlador.listarUsuarios().size()).append("\n");
        reporte.append("- Total Libros: ").append(libroControlador.listarLibros().size()).append("\n");
        reporte.append("- Total Préstamos: ").append(prestamoControlador.listarPrestamos().size()).append("\n");
        reporte.append("- Préstamos Vencidos: ").append(prestamoControlador.obtenerPrestamosVencidos().size()).append("\n\n");
        
        // Mostrar reporte en ventana
        JTextArea areaReporte = new JTextArea(reporte.toString());
        areaReporte.setEditable(false);
        areaReporte.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        
        JScrollPane scrollReporte = new JScrollPane(areaReporte);
        scrollReporte.setPreferredSize(new Dimension(600, 400));
        
        JOptionPane.showMessageDialog(this, scrollReporte,
            "📄 Reporte Exportado", JOptionPane.INFORMATION_MESSAGE);
    }
}