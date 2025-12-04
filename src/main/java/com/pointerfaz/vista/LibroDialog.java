package com.pointerfaz.vista;

import com.pointerfaz.modelo.Libro;
import com.pointerfaz.util.Constantes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diálogo para agregar y editar libros
 * Demuestra el uso de Swing y validación de formularios
 */
public class LibroDialog extends JDialog {
    private JTextField txtIsbn;
    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtCategoria;
    private JTextField txtAnioPublicacion;
    private JComboBox<String> cbEstado;
    
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private Libro libro;
    private boolean guardado = false;
    
    /**
     * Constructor para agregar nuevo libro
     */
    public LibroDialog(Frame parent) {
        this(parent, null);
    }
    
    /**
     * Constructor para editar libro existente
     */
    public LibroDialog(Frame parent, Libro libro) {
        super(parent, libro == null ? "Agregar Libro" : "Editar Libro", true);
        this.libro = libro;
        
        initializeComponents();
        setupLayout();
        setupEventListeners();
        
        if (libro != null) {
            cargarDatosLibro();
        }
        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(550, 520);
        setLocationRelativeTo(parent);
        setResizable(false);
    }
    
    /**
     * Inicializa los componentes del diálogo
     */
    private void initializeComponents() {
        // Campos de texto
        txtIsbn = new JTextField(20);
        txtTitulo = new JTextField(20);
        txtAutor = new JTextField(20);
        txtCategoria = new JTextField(20);
        txtAnioPublicacion = new JTextField(20);
        
        // ComboBox para estado
        cbEstado = new JComboBox<>(new String[]{"Disponible", "Prestado", "En Reparación", "Perdido"});
        
        // Botones
        btnGuardar = new JButton("💾 Guardar");
        btnCancelar = new JButton("❌ Cancelar");
        
        // Estilizar componentes
        estilizarComponentes();
    }
    
    /**
     * Aplica estilos a los componentes
     */
    private void estilizarComponentes() {
        Font fuente = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        
        // Estilizar campos de texto
        JTextField[] campos = {txtIsbn, txtTitulo, txtAutor, txtCategoria, txtAnioPublicacion};
        for (JTextField campo : campos) {
            campo.setFont(fuente);
            campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Constantes.COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
        }
        
        // Estilizar ComboBox
        cbEstado.setFont(fuente);
        cbEstado.setBackground(Color.WHITE);
        
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
        JLabel lblTitulo = new JLabel(libro == null ? "📚 Agregar Nuevo Libro" : "📝 Editar Libro");
        lblTitulo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        lblTitulo.setForeground(Constantes.COLOR_PRIMARIO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lblTitulo);
        panelPrincipal.add(Box.createVerticalStrut(20));
        
        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Constantes.COLOR_FONDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // ISBN
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(crearLabel("ISBN*:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelFormulario.add(txtIsbn, gbc);
        
        // Título
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelFormulario.add(crearLabel("Título*:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelFormulario.add(txtTitulo, gbc);
        
        // Autor
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelFormulario.add(crearLabel("Autor*:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelFormulario.add(txtAutor, gbc);
        
        // Categoría
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelFormulario.add(crearLabel("Categoría*:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelFormulario.add(txtCategoria, gbc);
        
        // Año de Publicación
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelFormulario.add(crearLabel("Año Publicación*:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelFormulario.add(txtAnioPublicacion, gbc);
        
        // Estado
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panelFormulario.add(crearLabel("Estado:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panelFormulario.add(cbEstado, gbc);
        
        panelPrincipal.add(panelFormulario);
        panelPrincipal.add(Box.createVerticalStrut(30));
        
        // Panel de botones con mejor layout
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panelBotones.setBackground(Constantes.COLOR_FONDO);
        panelBotones.setPreferredSize(new Dimension(450, 80));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        panelPrincipal.add(panelBotones);
        panelPrincipal.add(Box.createVerticalStrut(15));
        
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Nota de campos obligatorios
        JLabel lblNota = new JLabel("* Campos obligatorios");
        lblNota.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        lblNota.setForeground(Constantes.COLOR_ERROR);
        lblNota.setBorder(new EmptyBorder(10, 20, 10, 20));
        add(lblNota, BorderLayout.SOUTH);
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
                guardarLibro();
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
     * Carga los datos del libro en el formulario (para edición)
     */
    private void cargarDatosLibro() {
        if (libro != null) {
            txtIsbn.setText(libro.getIsbn());
            txtTitulo.setText(libro.getTitulo());
            txtAutor.setText(libro.getAutor());
            txtCategoria.setText(libro.getCategoria());
            txtAnioPublicacion.setText(String.valueOf(libro.getAnioPublicacion()));
            cbEstado.setSelectedItem(libro.getEstado());
        }
    }
    
    /**
     * Valida y guarda el libro
     */
    private void guardarLibro() {
        // Validación de campos obligatorios
        if (txtIsbn.getText().trim().isEmpty()) {
            mostrarError("El ISBN es obligatorio");
            txtIsbn.requestFocus();
            return;
        }
        
        if (txtTitulo.getText().trim().isEmpty()) {
            mostrarError("El título es obligatorio");
            txtTitulo.requestFocus();
            return;
        }
        
        if (txtAutor.getText().trim().isEmpty()) {
            mostrarError("El autor es obligatorio");
            txtAutor.requestFocus();
            return;
        }
        
        if (txtCategoria.getText().trim().isEmpty()) {
            mostrarError("La categoría es obligatoria");
            txtCategoria.requestFocus();
            return;
        }
        
        if (txtAnioPublicacion.getText().trim().isEmpty()) {
            mostrarError("El año de publicación es obligatorio");
            txtAnioPublicacion.requestFocus();
            return;
        }
        
        // Validar año de publicación
        int anioPublicacion;
        try {
            anioPublicacion = Integer.parseInt(txtAnioPublicacion.getText().trim());
            if (anioPublicacion < 1000 || anioPublicacion > 2025) {
                mostrarError("El año de publicación debe estar entre 1000 y 2025");
                txtAnioPublicacion.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            mostrarError("El año de publicación debe ser un número válido");
            txtAnioPublicacion.requestFocus();
            return;
        }
        
        // Crear o actualizar libro
        if (libro == null) {
            libro = new Libro();
        }
        
        libro.setIsbn(txtIsbn.getText().trim());
        libro.setTitulo(txtTitulo.getText().trim());
        libro.setAutor(txtAutor.getText().trim());
        libro.setCategoria(txtCategoria.getText().trim());
        libro.setAnioPublicacion(anioPublicacion);
        libro.setEstado((String) cbEstado.getSelectedItem());
        
        guardado = true;
        dispose();
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
     * Verifica si el libro fue guardado
     */
    public boolean fueGuardado() {
        return guardado;
    }
    
    /**
     * Obtiene el libro creado o editado
     */
    public Libro getLibro() {
        return libro;
    }
}