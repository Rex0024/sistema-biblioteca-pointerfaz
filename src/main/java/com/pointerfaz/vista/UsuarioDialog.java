package com.pointerfaz.vista;

import com.pointerfaz.modelo.Estudiante;
import com.pointerfaz.modelo.EstudianteGraduado;
import com.pointerfaz.modelo.Profesor;
import com.pointerfaz.modelo.Persona;
import com.pointerfaz.util.Constantes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diálogo para agregar/editar usuarios
 * Formulario completo y elegante
 */
public class UsuarioDialog extends JDialog {
    
    private JComboBox<String> cmbTipoUsuario;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextField txtCodigo;
    private JTextField txtCarrera;
    private JTextField txtSemestre;
    private JTextField txtDepartamento;
    private JTextField txtEspecialidad;
    private JTextField txtTipoContrato;
    private JTextField txtTitulo;
    private JTextField txtAniosExperiencia;
    
    private JPanel panelEspecifico;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private Persona usuarioEditando;
    private boolean guardado = false;
    
    /**
     * Constructor para agregar nuevo usuario
     */
    public UsuarioDialog(JFrame parent) {
        super(parent, "Agregar Usuario", true);
        this.usuarioEditando = null;
        inicializarComponentes();
        configurarDialog();
    }
    
    /**
     * Constructor para editar usuario existente
     */
    public UsuarioDialog(JFrame parent, Persona usuario) {
        super(parent, "Editar Usuario", true);
        this.usuarioEditando = usuario;
        inicializarComponentes();
        cargarDatosUsuario();
        configurarDialog();
    }
    
    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        // Título
        JLabel lblTitulo = new JLabel(usuarioEditando == null ? "➕ Nuevo Usuario" : "✏️ Editar Usuario");
        lblTitulo.setFont(Constantes.FUENTE_SUBTITULO);
        lblTitulo.setForeground(Constantes.COLOR_PRIMARIO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel de datos básicos
        JPanel panelBasico = new JPanel(new GridLayout(6, 2, 10, 10));
        panelBasico.setBackground(Constantes.COLOR_FONDO_SECUNDARIO);
        panelBasico.setBorder(BorderFactory.createTitledBorder("Datos Básicos"));
        
        // Tipo de usuario
        panelBasico.add(new JLabel("Tipo de Usuario:"));
        cmbTipoUsuario = new JComboBox<>(new String[]{"Estudiante", "EstudianteGraduado", "Profesor"});
        cmbTipoUsuario.addActionListener(e -> actualizarPanelEspecifico());
        panelBasico.add(cmbTipoUsuario);
        
        // Nombres
        panelBasico.add(new JLabel("Nombres:"));
        txtNombres = new JTextField();
        panelBasico.add(txtNombres);
        
        // Apellidos
        panelBasico.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panelBasico.add(txtApellidos);
        
        // Email
        panelBasico.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelBasico.add(txtEmail);
        
        // Teléfono
        panelBasico.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelBasico.add(txtTelefono);
        
        // Código
        panelBasico.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelBasico.add(txtCodigo);
        
        // Panel específico (cambia según el tipo)
        panelEspecifico = new JPanel();
        panelEspecifico.setBackground(Constantes.COLOR_FONDO_SECUNDARIO);
        actualizarPanelEspecifico();
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(Constantes.COLOR_FONDO_PRINCIPAL);
        
        btnGuardar = new JButton("💾 Guardar");
        btnGuardar.setBackground(Constantes.COLOR_EXITO);
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(Constantes.FUENTE_BOTON);
        btnGuardar.addActionListener(e -> guardarUsuario());
        
        btnCancelar = new JButton("❌ Cancelar");
        btnCancelar.setBackground(Constantes.COLOR_ERROR);
        btnCancelar.setForeground(Color.WHITE);
        btnCancelar.setFont(Constantes.FUENTE_BOTON);
        btnCancelar.addActionListener(e -> dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        // Ensamblar
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelBasico, BorderLayout.CENTER);
        panelPrincipal.add(panelEspecifico, BorderLayout.EAST);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }
    
    private void actualizarPanelEspecifico() {
        panelEspecifico.removeAll();
        String tipo = (String) cmbTipoUsuario.getSelectedItem();
        
        if ("Estudiante".equals(tipo) || "EstudianteGraduado".equals(tipo)) {
            panelEspecifico.setLayout(new GridLayout(3, 2, 10, 10));
            panelEspecifico.setBorder(BorderFactory.createTitledBorder("Datos de Estudiante"));
            
            panelEspecifico.add(new JLabel("Carrera:"));
            txtCarrera = new JTextField();
            panelEspecifico.add(txtCarrera);
            
            panelEspecifico.add(new JLabel("Semestre:"));
            txtSemestre = new JTextField();
            panelEspecifico.add(txtSemestre);
            
        } else if ("Profesor".equals(tipo)) {
            panelEspecifico.setLayout(new GridLayout(5, 2, 10, 10));
            panelEspecifico.setBorder(BorderFactory.createTitledBorder("Datos de Profesor"));
            
            panelEspecifico.add(new JLabel("Departamento:"));
            txtDepartamento = new JTextField();
            panelEspecifico.add(txtDepartamento);
            
            panelEspecifico.add(new JLabel("Especialidad:"));
            txtEspecialidad = new JTextField();
            panelEspecifico.add(txtEspecialidad);
            
            panelEspecifico.add(new JLabel("Tipo Contrato:"));
            txtTipoContrato = new JTextField();
            panelEspecifico.add(txtTipoContrato);
            
            panelEspecifico.add(new JLabel("Título:"));
            txtTitulo = new JTextField();
            panelEspecifico.add(txtTitulo);
            
            panelEspecifico.add(new JLabel("Años Experiencia:"));
            txtAniosExperiencia = new JTextField();
            panelEspecifico.add(txtAniosExperiencia);
        }
        
        panelEspecifico.revalidate();
        panelEspecifico.repaint();
    }
    
    private void cargarDatosUsuario() {
        if (usuarioEditando != null) {
            txtNombres.setText(usuarioEditando.getNombres());
            txtApellidos.setText(usuarioEditando.getApellidos());
            txtEmail.setText(usuarioEditando.getEmail());
            txtTelefono.setText(usuarioEditando.getTelefono());
            
            if (usuarioEditando instanceof EstudianteGraduado) {
                cmbTipoUsuario.setSelectedItem("EstudianteGraduado");
                EstudianteGraduado grad = (EstudianteGraduado) usuarioEditando;
                txtCodigo.setText(grad.getCodigo());
                actualizarPanelEspecifico();
                txtCarrera.setText(grad.getCarrera());
                txtSemestre.setText(String.valueOf(grad.getSemestre()));
            } else if (usuarioEditando instanceof Estudiante) {
                cmbTipoUsuario.setSelectedItem("Estudiante");
                Estudiante est = (Estudiante) usuarioEditando;
                txtCodigo.setText(est.getCodigo());
                actualizarPanelEspecifico();
                txtCarrera.setText(est.getCarrera());
                txtSemestre.setText(String.valueOf(est.getSemestre()));
            } else if (usuarioEditando instanceof Profesor) {
                cmbTipoUsuario.setSelectedItem("Profesor");
                Profesor prof = (Profesor) usuarioEditando;
                txtCodigo.setText(prof.getCodigoEmpleado());
                actualizarPanelEspecifico();
                txtDepartamento.setText(prof.getDepartamento());
                txtEspecialidad.setText(prof.getEspecialidad());
                txtTipoContrato.setText(prof.getTipoContrato());
                txtTitulo.setText(prof.getTitulo());
                txtAniosExperiencia.setText(String.valueOf(prof.getAniosExperiencia()));
            }
        }
    }
    
    private void guardarUsuario() {
        // Validar campos básicos
        if (txtNombres.getText().trim().isEmpty() || 
            txtApellidos.getText().trim().isEmpty() || 
            txtEmail.getText().trim().isEmpty() ||
            txtCodigo.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Por favor complete todos los campos obligatorios", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        guardado = true;
        dispose();
    }
    
    public Persona getUsuario() {
        if (!guardado) return null;
        
        String tipo = (String) cmbTipoUsuario.getSelectedItem();
        int id = usuarioEditando != null ? usuarioEditando.getId() : 0;
        
        if ("Estudiante".equals(tipo)) {
            Estudiante estudiante = new Estudiante();
            estudiante.setId(id);
            estudiante.setNombres(txtNombres.getText().trim());
            estudiante.setApellidos(txtApellidos.getText().trim());
            estudiante.setEmail(txtEmail.getText().trim());
            estudiante.setTelefono(txtTelefono.getText().trim());
            estudiante.setCodigo(txtCodigo.getText().trim());
            estudiante.setCarrera(txtCarrera.getText().trim());
            if (!txtSemestre.getText().trim().isEmpty()) {
                estudiante.setSemestre(Integer.parseInt(txtSemestre.getText().trim()));
            }
            return estudiante;
            
        } else if ("EstudianteGraduado".equals(tipo)) {
            EstudianteGraduado graduado = new EstudianteGraduado();
            graduado.setId(id);
            graduado.setNombres(txtNombres.getText().trim());
            graduado.setApellidos(txtApellidos.getText().trim());
            graduado.setEmail(txtEmail.getText().trim());
            graduado.setTelefono(txtTelefono.getText().trim());
            graduado.setCodigo(txtCodigo.getText().trim());
            graduado.setCarrera(txtCarrera.getText().trim());
            return graduado;
            
        } else if ("Profesor".equals(tipo)) {
            Profesor profesor = new Profesor();
            profesor.setId(id);
            profesor.setNombres(txtNombres.getText().trim());
            profesor.setApellidos(txtApellidos.getText().trim());
            profesor.setEmail(txtEmail.getText().trim());
            profesor.setTelefono(txtTelefono.getText().trim());
            profesor.setCodigoEmpleado(txtCodigo.getText().trim());
            profesor.setDepartamento(txtDepartamento.getText().trim());
            profesor.setEspecialidad(txtEspecialidad.getText().trim());
            profesor.setTipoContrato(txtTipoContrato.getText().trim());
            profesor.setTitulo(txtTitulo.getText().trim());
            if (!txtAniosExperiencia.getText().trim().isEmpty()) {
                profesor.setAniosExperiencia(Integer.parseInt(txtAniosExperiencia.getText().trim()));
            }
            return profesor;
        }
        
        return null;
    }
    
    public boolean fueGuardado() {
        return guardado;
    }
    
    private void configurarDialog() {
        setSize(700, 500);
        setLocationRelativeTo(getParent());
        setResizable(true);
        setMinimumSize(new Dimension(600, 400));
    }
}