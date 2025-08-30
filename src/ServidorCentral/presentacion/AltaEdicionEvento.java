package ServidorCentral.presentacion;

import ServidorCentral.logica.*;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public class AltaEdicionEvento extends JInternalFrame {

    private JComboBox<Evento> comboEvento;
    private JComboBox<Organizador> comboOrganizador;
    private JTextField txtNombre, txtSigla, txtCiudad, txtPais;
    private JFormattedTextField txtFechaIni, txtFechaFin, txtFechaAlta;
    private JButton btnAceptar, btnCancelar;

    private IControllerEvento controller;
    private List<Evento> eventos;
    private List<Organizador> organizadores;

    public AltaEdicionEvento(IControllerEvento ice) {
        this.controller = ice;
        
        setClosable(true);   
        setIconifiable(true); 
        setMaximizable(true); 
        setResizable(true);  
        this.eventos = controller.listarEventos();
        this.organizadores = controller.listarOrganizadores();

        setTitle("Alta de Edición de Evento");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        // --- COMPONENTES ---
        JLabel labelEvento = new JLabel("Evento:");
        labelEvento.setBounds(10, 46, 63, 32);
        labelEvento.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        getContentPane().add(labelEvento);

        comboEvento = new JComboBox<>();
        comboEvento.addItem(null); // "Ninguno" por defecto
        for (Evento ev : eventos) comboEvento.addItem(ev);
        comboEvento.setBounds(70, 47, 239, 32);
        comboEvento.setRenderer(new DefaultListCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value == null ? "Ninguno" : value.toString());
                return this;
            }
        });
        getContentPane().add(comboEvento);

        JLabel labelOrg = new JLabel("Organizador:");
        labelOrg.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelOrg.setBounds(10, 85, 89, 32);
        getContentPane().add(labelOrg);

        comboOrganizador = new JComboBox<>();
        comboOrganizador.addItem(null); // "Ninguno" por defecto
        for (Organizador org : organizadores) comboOrganizador.addItem(org);
        comboOrganizador.setBounds(100, 86, 239, 32);
        comboOrganizador.setRenderer(new DefaultListCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value == null ? "Ninguno" : value.toString());
                return this;
            }
        });
        getContentPane().add(comboOrganizador);

        // --- Campos de texto ---
        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelNombre.setBounds(14, 127, 63, 32);
        getContentPane().add(labelNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(70, 129, 147, 32);
        getContentPane().add(txtNombre);

        JLabel labelSigla = new JLabel("Sigla:");
        labelSigla.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelSigla.setBounds(14, 190, 52, 32);
        getContentPane().add(labelSigla);

        txtSigla = new JTextField();
        txtSigla.setBounds(70, 190, 89, 32);
        getContentPane().add(txtSigla);

        JLabel labelCiudad = new JLabel("Ciudad:");
        labelCiudad.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelCiudad.setBounds(180, 190, 73, 32);
        getContentPane().add(labelCiudad);

        txtCiudad = new JTextField();
        txtCiudad.setBounds(240, 190, 147, 32);
        getContentPane().add(txtCiudad);

        JLabel labelPais = new JLabel("País:");
        labelPais.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelPais.setBounds(14, 230, 41, 32);
        getContentPane().add(labelPais);

        txtPais = new JTextField();
        txtPais.setBounds(70, 230, 153, 32);
        getContentPane().add(txtPais);

        // --- FECHAS ---
        txtFechaIni = crearCampoFecha("##/##/####", 140, 270, 120, 25);
        getContentPane().add(txtFechaIni);
        JLabel lblFechaIni = new JLabel("Fecha Inicio:");
        lblFechaIni.setBounds(14, 270, 85, 25);
        lblFechaIni.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        getContentPane().add(lblFechaIni);

        txtFechaFin = crearCampoFecha("##/##/####", 140, 310, 120, 25);
        getContentPane().add(txtFechaFin);
        JLabel lblFechaFin = new JLabel("Fecha Fin :");
        lblFechaFin.setBounds(14, 310, 73, 25);
        lblFechaFin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        getContentPane().add(lblFechaFin);

        txtFechaAlta = crearCampoFecha("##/##/####", 140, 350, 120, 25);
        getContentPane().add(txtFechaAlta);
        JLabel lblFechaAlta = new JLabel("Fecha Alta :");
        lblFechaAlta.setBounds(14, 350, 85, 25);
        lblFechaAlta.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        getContentPane().add(lblFechaAlta);

        // --- Botones ---
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 400, 196, 32);
        getContentPane().add(btnAceptar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(258, 400, 196, 32);
        getContentPane().add(btnCancelar);

        // --- Título ---
        JTextArea titulo = new JTextArea();
        titulo.setBackground(new Color(240, 240, 240));
        titulo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        titulo.setText("Crear Edicion de Evento ");
        titulo.setBounds(136, 10, 280, 37);
        getContentPane().add(titulo);

        // --- ACCIONES ---
        btnCancelar.addActionListener(e -> dispose());

        btnAceptar.addActionListener(e -> {
            try {
                if (!validarNombre()) return;

                String sigla = txtSigla.getText().trim();
                String ciudad = txtCiudad.getText().trim();
                String pais = txtPais.getText().trim();

                if (sigla.isEmpty() || ciudad.isEmpty() || pais.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe completar todos los campos obligatorios",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Evento evento = (Evento) comboEvento.getSelectedItem();       // puede ser null = "Ninguno"
                Organizador org = (Organizador) comboOrganizador.getSelectedItem(); // puede ser null = "Ninguno"

                LocalDate fIni = parseFecha(txtFechaIni.getText());
                LocalDate fFin = parseFecha(txtFechaFin.getText());
                LocalDate fAlta = parseFecha(txtFechaAlta.getText());

                controller.altaEdicionDeEvento(
                        txtNombre.getText().trim(),
                        sigla,
                        ciudad,
                        pais,
                        fIni,
                        fFin,
                        evento,   // null permitido
                        org       // null permitido
                );

                JOptionPane.showMessageDialog(this, "Edición creada con éxito");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto o error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // --- MÉTODO DE VALIDACIÓN DE NOMBRE ---
    private boolean validarNombre() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        for (Edicion e1 : controller.listarEdiciones()) {
            if (e1.getNombre().equalsIgnoreCase(nombre)) {
                JOptionPane.showMessageDialog(this, "El nombre ya está en uso",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private JFormattedTextField crearCampoFecha(String formato, int x, int y, int ancho, int alto) {
        try {
            MaskFormatter mascara = new MaskFormatter(formato);
            mascara.setPlaceholderCharacter('_');
            JFormattedTextField campo = new JFormattedTextField(mascara);
            campo.setBounds(x, y, ancho, alto);
            campo.setEditable(true);
            campo.setFocusLostBehavior(JFormattedTextField.PERSIST);
            return campo;
        } catch (ParseException e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    private LocalDate parseFecha(String texto) {
        String[] partes = texto.split("/");
        if (partes.length != 3) throw new IllegalArgumentException("Fecha inválida");
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int anio = Integer.parseInt(partes[2]);
        return LocalDate.of(anio, mes, dia);
    }

    private static final long serialVersionUID = 1L;
}
