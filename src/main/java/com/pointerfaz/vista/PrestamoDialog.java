package com.pointerfaz.vista;

import com.pointerfaz.controlador.LibroControladorNuevo;
import com.pointerfaz.controlador.PrestamoControladorNuevo;
import com.pointerfaz.controlador.UsuarioControladorNuevo;
import com.pointerfaz.modelo.Libro;
import com.pointerfaz.modelo.Persona;
import com.pointerfaz.modelo.Prestamo;
import com.pointerfaz.util.Constantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Diálogo para gestionar préstamos (nuevo, devolver, renovar)
 * Integra usuarios y libros para operaciones completas
 */
public class PrestamoDialog extends JDialog {
    private JComboBox<ComboItem> cbUsuarios;
    private JComboBox<ComboItem> cbLibros;
    private JTextField txtFechaPrestamo;
    private JTextField txtFechaDevolucion;
    private JTextArea txtObservaciones;
    
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private UsuarioControladorNuevo usuarioControlador;
    private LibroControladorNuevo libroControlador;
    private PrestamoControladorNuevo prestamoControlador;
    
    private Prestamo prestamo;
    private boolean guardado = false;
    private String tipoOperacion; // "NUEVO", "DEVOLVER", "RENOVAR"
    
    /**
     * Constructor para nuevo préstamo
     */
    public PrestamoDialog(Frame parent, UsuarioControladorNuevo usuarioControlador, 
                         LibroControladorNuevo libroControlador, PrestamoControladorNuevo prestamoControlador) {
        this(parent, usuarioControlador, libroControlador, prestamoControlador, null, "NUEVO");
    }
    
    /**
     * Constructor para devolver o renovar préstamo
     */
    public PrestamoDialog(Frame parent, UsuarioControladorNuevo usuarioControlador, 
                         LibroControladorNuevo libroControlador, PrestamoControladorNuevo prestamoControlador,
                         Prestamo prestamo, String tipoOperacion) {
        super(parent, getTituloSegunOperacion(tipoOperacion), true);
        
        this.usuarioControlador = usuarioControlador;
        this.libroControlador = libroControlador;
        this.prestamoControlador = prestamoControlador;
        this.prestamo = prestamo;
        this.tipoOperacion = tipoOperacion;
        
        initializeComponents();
        setupLayout();
        setupEventListeners();
        cargarDatos();
        
        if (prestamo != null) {
            cargarDatosPrestamo();
        }
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    /**
     * Obtiene el título según el tipo de operación
     */
    private static String getTituloSegunOperacion(String tipoOperacion) {
        switch (tipoOperacion) {
            case "NUEVO": return "Nuevo Préstamo";
            case "DEVOLVER": return "Devolver Libro";
            case "RENOVAR": return "Renovar Préstamo";
            default: return "Gestionar Préstamo";
        }
    }
    
    /**
     * Inicializa los componentes del diálogo
     */
    private void initializeComponents() {
        // ComboBoxes
        cbUsuarios = new JComboBox<>();
        cbLibros = new JComboBox<>();
        
        // Campos de texto
        txtFechaPrestamo = new JTextField(15);
        txtFechaDevolucion = new JTextField(15);
        txtObservaciones = new JTextArea(4, 30);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        
        // Botones
        btnGuardar = new JButton(getTextoBotonGuardar());
        btnCancelar = new JButton("❌ Cancelar");
        
        // Configurar fechas por defecto
        LocalDate hoy = LocalDate.now();
        LocalDate fechaDevolucion = hoy.plusDays(15); // 15 días de préstamo
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtFechaPrestamo.setText(hoy.format(formatter));
        txtFechaDevolucion.setText(fechaDevolucion.format(formatter));
        
        // Estilizar componentes
        estilizarComponentes();
    }
    
    /**
     * Obtiene el texto del botón guardar según la operación
     */
    private String getTextoBotonGuardar() {
        switch (tipoOperacion) {
            case "NUEVO": return "📚 Prestar Libro";
            case "DEVOLVER": return "↩️ Devolver";
            case "RENOVAR": return "🔄 Renovar";
            default: return "💾 Guardar";
        }
    }
    
    /**
     * Aplica estilos a los componentes
     */
    private void estilizarComponentes() {
        Font fuente = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        
        // Estilizar ComboBoxes
        cbUsuarios.setFont(fuente);
        cbUsuarios.setBackground(Color.WHITE);
        cbLibros.setFont(fuente);
        cbLibros.setBackground(Color.WHITE);
        
        // Estilizar campos de texto
        JTextField[] campos = {txtFechaPrestamo, txtFechaDevolucion};
        for (JTextField campo : campos) {
            campo.setFont(fuente);
            campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Constantes.COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
        }
        
        // Estilizar área de texto
        txtObservaciones.setFont(fuente);
        txtObservaciones.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Constantes.COLOR_BORDE, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Estilizar botones
        btnGuardar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        btnGuardar.setBackground(Constantes.COLOR_EXITO);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnCancelar.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        btnCancelar.setBackground(Constantes.COLOR_ERROR);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Configuraciones específicas según operación
        if ("DEVOLVER".equals(tipoOperacion)) {
            cbUsuarios.setEnabled(false);
            cbLibros.setEnabled(false);
            txtFechaPrestamo.setEnabled(false);
        } else if ("RENOVAR".equals(tipoOperacion)) {
            cbUsuarios.setEnabled(false);
            cbLibros.setEnabled(false);
            txtFechaPrestamo.setEnabled(false);
        }
    }
    
    /**
     * Configura el layout del diálogo
     */
    private void setupLayout() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Constantes.COLOR_FONDO);
        
        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(Constantes.COLOR_FONDO);
        panelPrincipal.setBorder(new EmptyBorder(25, 25, 25, 25));
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        
        // Título
        JLabel lblTitulo = new JLabel(getIconoTitulo() + " " + getTituloSegunOperacion(tipoOperacion));
        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        lblTitulo.setForeground(Constantes.COLOR_PRIMARIO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createVerticalStrut(20));
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Constantes.COLOR_FONDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(crearLabel("Usuario*:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelFormulario.add(cbUsuarios, gbc);
        
        // Libro
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelFormulario.add(crearLabel("Libro*:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelFormulario.add(cbLibros, gbc);
        
        // Fecha de Préstamo
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelFormulario.add(crearLabel("Fecha Préstamo*:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelFormulario.add(txtFechaPrestamo, gbc);
        
        // Fecha de Devolución
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelFormulario.add(crearLabel("Fecha Devolución*:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelFormulario.add(txtFechaDevolucion, gbc);
        
        // Observaciones
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panelFormulario.add(crearLabel("Observaciones:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JScrollPane scrollObservaciones = new JScrollPane(txtObservaciones);
        scrollObservaciones.setPreferredSize(new Dimension(300, 100));
        panelFormulario.add(scrollObservaciones, gbc);
        
        panelPrincipal.add(panelFormulario);
        panelPrincipal.add(Box.createVerticalStrut(30));
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setBackground(Constantes.COLOR_FONDO);
        panelBotones.setPreferredSize(new Dimension(500, 80));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(Box.createVerticalStrut(15));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Nota de campos obligatorios
        JLabel lblNota = new JLabel("* Campos obligatorios");
        lblNota.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        lblNota.setForeground(Constantes.COLOR_ERROR);
        lblNota.setBorder(new EmptyBorder(10, 25, 10, 25));
        add(lblNota, BorderLayout.SOUTH);
    }
    
    /**
     * Obtiene el icono según el tipo de operación
     */
    private String getIconoTitulo() {
        switch (tipoOperacion) {
            case "NUEVO": return "📚";
            case "DEVOLVER": return "↩️";
            case "RENOVAR": return "🔄";
            default: return "📋";
        }
    }
    
    /**
     * Crea un label estilizado
     */
    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        label.setForeground(Constantes.COLOR_TEXTO);
        return label;
    }
    
    /**
     * Configura los event listeners
     */
    private void setupEventListeners() {
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPrestamo();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    /**
     * Carga los datos iniciales (usuarios y libros)
     */
    private void cargarDatos() {
        // Cargar usuarios
        cbUsuarios.removeAllItems();
        List<Persona> usuarios = usuarioControlador.listarUsuarios();
        for (Persona usuario : usuarios) {
            String nombreCompleto = usuario.getNombres() + " " + usuario.getApellidos();
            String tipoUsuario = usuario.getClass().getSimpleName();
            cbUsuarios.addItem(new ComboItem(usuario.getId(), nombreCompleto + " (" + tipoUsuario + ")"));
        }
        
        // Cargar libros (solo disponibles para nuevos préstamos)
        cbLibros.removeAllItems();
        List<Libro> libros = libroControlador.listarLibros();
        for (Libro libro : libros) {
            if ("NUEVO".equals(tipoOperacion) && "Disponible".equals(libro.getEstado())) {
                String descripcion = libro.getTitulo() + " - " + libro.getAutor() + " (ISBN: " + libro.getIsbn() + ")";
                cbLibros.addItem(new ComboItem(libro.getId(), descripcion));
            } else if (!"NUEVO".equals(tipoOperacion)) {
                String descripcion = libro.getTitulo() + " - " + libro.getAutor() + " (ISBN: " + libro.getIsbn() + ")";
                cbLibros.addItem(new ComboItem(libro.getId(), descripcion));
            }
        }
    }
    
    /**
     * Carga los datos del préstamo (para edición)
     */
    private void cargarDatosPrestamo() {
        if (prestamo != null) {
            // Seleccionar usuario
            for (int i = 0; i < cbUsuarios.getItemCount(); i++) {
                ComboItem item = cbUsuarios.getItemAt(i);
                if (item.getId() == prestamo.getUsuarioId()) {
                    cbUsuarios.setSelectedIndex(i);
                    break;
                }
            }
            
            // Seleccionar libro
            for (int i = 0; i < cbLibros.getItemCount(); i++) {
                ComboItem item = cbLibros.getItemAt(i);
                if (item.getId() == prestamo.getLibroId()) {
                    cbLibros.setSelectedIndex(i);
                    break;
                }
            }
            
            // Cargar fechas
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if (prestamo.getFechaPrestamo() != null) {
                txtFechaPrestamo.setText(prestamo.getFechaPrestamo().format(formatter));
            }
            if (prestamo.getFechaDevolucionEsperada() != null) {
                txtFechaDevolucion.setText(prestamo.getFechaDevolucionEsperada().format(formatter));
            }
            
            // Cargar observaciones
            if (prestamo.getObservaciones() != null) {
                txtObservaciones.setText(prestamo.getObservaciones());
            }
            
            // Para renovar, establecer nueva fecha de devolución
            if ("RENOVAR".equals(tipoOperacion)) {
                LocalDate nuevaFechaDevolucion = LocalDate.now().plusDays(15);
                txtFechaDevolucion.setText(nuevaFechaDevolucion.format(formatter));
                txtFechaDevolucion.setEnabled(true);
            }
        }
    }
    
    /**
     * Valida y guarda el préstamo
     */
    private void guardarPrestamo() {
        // Validaciones básicas
        if (cbUsuarios.getSelectedItem() == null) {
            mostrarError("Debe seleccionar un usuario");
            return;
        }
        
        if (cbLibros.getSelectedItem() == null) {
            mostrarError("Debe seleccionar un libro");
            return;
        }
        
        if (txtFechaPrestamo.getText().trim().isEmpty()) {
            mostrarError("La fecha de préstamo es obligatoria");
            return;
        }
        
        if (txtFechaDevolucion.getText().trim().isEmpty()) {
            mostrarError("La fecha de devolución es obligatoria");
            return;
        }
        
        try {
            // Parsear fechas
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaPrestamo = LocalDate.parse(txtFechaPrestamo.getText().trim(), formatter);
            LocalDate fechaDevolucion = LocalDate.parse(txtFechaDevolucion.getText().trim(), formatter);
            
            if (fechaDevolucion.isBefore(fechaPrestamo)) {
                mostrarError("La fecha de devolución no puede ser anterior a la fecha de préstamo");
                return;
            }
            
            // Obtener IDs seleccionados
            ComboItem usuarioSeleccionado = (ComboItem) cbUsuarios.getSelectedItem();
            ComboItem libroSeleccionado = (ComboItem) cbLibros.getSelectedItem();
            
            // Procesar según tipo de operación
            switch (tipoOperacion) {
                case "NUEVO":
                    crearNuevoPrestamo(usuarioSeleccionado.getId(), libroSeleccionado.getId(), 
                                     fechaPrestamo, fechaDevolucion);
                    break;
                case "DEVOLVER":
                    devolverLibro();
                    break;
                case "RENOVAR":
                    renovarPrestamo(fechaDevolucion);
                    break;
            }
            
        } catch (Exception e) {
            mostrarError("Error en el formato de fecha. Use el formato dd/MM/yyyy");
        }
    }
    
    /**
     * Crea un nuevo préstamo
     */
    private void crearNuevoPrestamo(int idUsuario, int idLibro, LocalDate fechaPrestamo, LocalDate fechaDevolucion) {
        prestamo = new Prestamo();
        prestamo.setUsuarioId(idUsuario);
        prestamo.setLibroId(idLibro);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucionEsperada(fechaDevolucion);
        prestamo.setEstado("Activo");
        prestamo.setObservaciones(txtObservaciones.getText().trim());
        
        guardado = true;
        dispose();
    }
    
    /**
     * Devuelve un libro
     */
    private void devolverLibro() {
        if (prestamo != null) {
            prestamo.setFechaDevolucionReal(LocalDate.now());
            prestamo.setEstado("Devuelto");
            prestamo.setObservaciones(txtObservaciones.getText().trim());
            guardado = true;
            dispose();
        }
    }
    
    /**
     * Renueva un préstamo
     */
    private void renovarPrestamo(LocalDate nuevaFechaDevolucion) {
        if (prestamo != null) {
            prestamo.setFechaDevolucionEsperada(nuevaFechaDevolucion);
            prestamo.setObservaciones(txtObservaciones.getText().trim());
            guardado = true;
            dispose();
        }
    }
    
    /**
     * Muestra un mensaje de error
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, 
            Constantes.ICONO_ERROR + " " + mensaje, 
            "Error de Validación", 
            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Verifica si el préstamo fue guardado
     */
    public boolean fueGuardado() {
        return guardado;
    }
    
    /**
     * Obtiene el préstamo creado o editado
     */
    public Prestamo getPrestamo() {
        return prestamo;
    }
    
    /**
     * Clase auxiliar para items del ComboBox
     */
    private static class ComboItem {
        private int id;
        private String descripcion;
        
        public ComboItem(int id, String descripcion) {
            this.id = id;
            this.descripcion = descripcion;
        }
        
        public int getId() {
            return id;
        }
        
        @Override
        public String toString() {
            return descripcion;
        }
    }
}