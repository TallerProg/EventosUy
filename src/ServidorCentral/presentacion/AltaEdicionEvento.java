package ServidorCentral.presentacion;

import ServidorCentral.logica.*;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public class AltaEdicionEvento extends JInternalFrame {

    private JComboBox<String> comboEvento;
    private JComboBox<String> comboOrganizador;
    private JTextField txtNombre, txtSigla, txtCiudad, txtPais;
    private JFormattedTextField txtFechaIni, txtFechaFin, txtFechaAlta;
    private JButton btnAceptar, btnCancelar;

    private IControllerEvento controller;
    private List<Evento> eventos;
    private List<Organizador> organizadores;

    private int row = 0;

    public AltaEdicionEvento(IControllerEvento ice) {
        this.controller = ice;

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Alta de Edición de Evento");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {120, 250, 0};
        gridBagLayout.rowHeights = new int[20];
        gridBagLayout.columnWeights = new double[] {0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[20];
        getContentPane().setLayout(gridBagLayout);

        JLabel labelEvento = new JLabel("Evento:");
        GridBagConstraints gbc_labelEvento = new GridBagConstraints();
        gbc_labelEvento.insets = new Insets(5,5,5,5);
        gbc_labelEvento.gridx = 0;
        gbc_labelEvento.gridy = row;
        gbc_labelEvento.anchor = GridBagConstraints.EAST;
        getContentPane().add(labelEvento, gbc_labelEvento);

        comboEvento = new JComboBox<>();
        GridBagConstraints gbc_comboEvento = new GridBagConstraints();
        gbc_comboEvento.insets = new Insets(5,5,5,5);
        gbc_comboEvento.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboEvento.gridx = 1;
        gbc_comboEvento.gridy = row;
        getContentPane().add(comboEvento, gbc_comboEvento);
        row++;

        JLabel labelOrg = new JLabel("Organizador:");
        GridBagConstraints gbc_labelOrg = new GridBagConstraints();
        gbc_labelOrg.insets = new Insets(5,5,5,5);
        gbc_labelOrg.gridx = 0;
        gbc_labelOrg.gridy = row;
        gbc_labelOrg.anchor = GridBagConstraints.EAST;
        getContentPane().add(labelOrg, gbc_labelOrg);

        comboOrganizador = new JComboBox<>();
        GridBagConstraints gbc_comboOrg = new GridBagConstraints();
        gbc_comboOrg.insets = new Insets(5,5,5,5);
        gbc_comboOrg.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboOrg.gridx = 1;
        gbc_comboOrg.gridy = row;
        getContentPane().add(comboOrganizador, gbc_comboOrg);
        row++;

        JLabel labelNombre = new JLabel("Nombre:");
        GridBagConstraints gbc_labelNombre = new GridBagConstraints();
        gbc_labelNombre.insets = new Insets(5,5,5,5);
        gbc_labelNombre.gridx = 0;
        gbc_labelNombre.gridy = row;
        gbc_labelNombre.anchor = GridBagConstraints.EAST;
        getContentPane().add(labelNombre, gbc_labelNombre);

        txtNombre = new JTextField();
        GridBagConstraints gbc_txtNombre = new GridBagConstraints();
        gbc_txtNombre.insets = new Insets(5,5,5,5);
        gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNombre.gridx = 1;
        gbc_txtNombre.gridy = row;
        getContentPane().add(txtNombre, gbc_txtNombre);
        row++;

        JLabel labelSigla = new JLabel("Sigla:");
        GridBagConstraints gbc_labelSigla = new GridBagConstraints();
        gbc_labelSigla.insets = new Insets(5,5,5,5);
        gbc_labelSigla.gridx = 0;
        gbc_labelSigla.gridy = row;
        gbc_labelSigla.anchor = GridBagConstraints.EAST;
        getContentPane().add(labelSigla, gbc_labelSigla);

        txtSigla = new JTextField();
        GridBagConstraints gbc_txtSigla = new GridBagConstraints();
        gbc_txtSigla.insets = new Insets(5,5,5,5);
        gbc_txtSigla.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtSigla.gridx = 1;
        gbc_txtSigla.gridy = row;
        getContentPane().add(txtSigla, gbc_txtSigla);
        row++;

        JLabel labelCiudad = new JLabel("Ciudad:");
        GridBagConstraints gbc_labelCiudad = new GridBagConstraints();
        gbc_labelCiudad.insets = new Insets(5,5,5,5);
        gbc_labelCiudad.gridx = 0;
        gbc_labelCiudad.gridy = row;
        gbc_labelCiudad.anchor = GridBagConstraints.EAST;
        getContentPane().add(labelCiudad, gbc_labelCiudad);

        txtCiudad = new JTextField();
        GridBagConstraints gbc_txtCiudad = new GridBagConstraints();
        gbc_txtCiudad.insets = new Insets(5,5,5,5);
        gbc_txtCiudad.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtCiudad.gridx = 1;
        gbc_txtCiudad.gridy = row;
        getContentPane().add(txtCiudad, gbc_txtCiudad);
        row++;

        JLabel labelPais = new JLabel("País:");
        GridBagConstraints gbc_labelPais = new GridBagConstraints();
        gbc_labelPais.insets = new Insets(5,5,5,5);
        gbc_labelPais.gridx = 0;
        gbc_labelPais.gridy = row;
        gbc_labelPais.anchor = GridBagConstraints.EAST;
        getContentPane().add(labelPais, gbc_labelPais);

        txtPais = new JTextField();
        GridBagConstraints gbc_txtPais = new GridBagConstraints();
        gbc_txtPais.insets = new Insets(5,5,5,5);
        gbc_txtPais.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtPais.gridx = 1;
        gbc_txtPais.gridy = row;
        getContentPane().add(txtPais, gbc_txtPais);
        row++;

        txtFechaIni = crearCampoFecha("##/##/####");
        GridBagConstraints gbc_txtFechaIni = new GridBagConstraints();
        gbc_txtFechaIni.insets = new Insets(5,5,5,5);
        gbc_txtFechaIni.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtFechaIni.gridx = 1;
        gbc_txtFechaIni.gridy = row;
        getContentPane().add(txtFechaIni, gbc_txtFechaIni);

        JLabel lblFechaIni = new JLabel("Fecha Inicio:");
        GridBagConstraints gbc_lblFechaIni = new GridBagConstraints();
        gbc_lblFechaIni.insets = new Insets(5,5,5,5);
        gbc_lblFechaIni.gridx = 0;
        gbc_lblFechaIni.gridy = row;
        gbc_lblFechaIni.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblFechaIni, gbc_lblFechaIni);
        row++;

        txtFechaFin = crearCampoFecha("##/##/####");
        GridBagConstraints gbc_txtFechaFin = new GridBagConstraints();
        gbc_txtFechaFin.insets = new Insets(5,5,5,5);
        gbc_txtFechaFin.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtFechaFin.gridx = 1;
        gbc_txtFechaFin.gridy = row;
        getContentPane().add(txtFechaFin, gbc_txtFechaFin);

        JLabel lblFechaFin = new JLabel("Fecha Fin:");
        GridBagConstraints gbc_lblFechaFin = new GridBagConstraints();
        gbc_lblFechaFin.insets = new Insets(5,5,5,5);
        gbc_lblFechaFin.gridx = 0;
        gbc_lblFechaFin.gridy = row;
        gbc_lblFechaFin.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblFechaFin, gbc_lblFechaFin);
        row++;

        txtFechaAlta = crearCampoFecha("##/##/####");
        GridBagConstraints gbc_txtFechaAlta = new GridBagConstraints();
        gbc_txtFechaAlta.insets = new Insets(5,5,5,5);
        gbc_txtFechaAlta.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtFechaAlta.gridx = 1;
        gbc_txtFechaAlta.gridy = row;
        getContentPane().add(txtFechaAlta, gbc_txtFechaAlta);

        JLabel lblFechaAlta = new JLabel("Fecha Alta:");
        GridBagConstraints gbc_lblFechaAlta = new GridBagConstraints();
        gbc_lblFechaAlta.insets = new Insets(5,5,5,5);
        gbc_lblFechaAlta.gridx = 0;
        gbc_lblFechaAlta.gridy = row;
        gbc_lblFechaAlta.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblFechaAlta, gbc_lblFechaAlta);
        row++;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        GridBagConstraints gbc_panelBotones = new GridBagConstraints();
        gbc_panelBotones.insets = new Insets(10,5,5,5);
        gbc_panelBotones.gridx = 0;
        gbc_panelBotones.gridy = row;
        gbc_panelBotones.gridwidth = 2;
        gbc_panelBotones.anchor = GridBagConstraints.CENTER;
        getContentPane().add(panelBotones, gbc_panelBotones);
        row++;

        cargarEventos();
        cargarOrganizadores();

        btnCancelar.addActionListener(e -> {
			txtNombre.setText("");
			txtSigla.setText("");
			txtCiudad.setText("");
			txtPais.setText("");
			txtFechaIni.setText("");
			txtFechaFin.setText("");
			txtFechaAlta.setText("");
			comboEvento.setSelectedIndex(0);
			comboOrganizador.setSelectedIndex(0);
			dispose();
        });

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

                String nombreEventoSeleccionado = (String) comboEvento.getSelectedItem();
                String nombreOrganizadorSeleccionado = (String) comboOrganizador.getSelectedItem();

                Evento evento = controller.obtenerEventoPorNombre(nombreEventoSeleccionado);
                Organizador org = controller.obtenerOrganizadorPorNombre(nombreOrganizadorSeleccionado);

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
                        evento,
                        org
                );
                
                txtNombre.setText("");
                txtSigla.setText("");
                txtCiudad.setText("");
                txtPais.setText("");
                txtFechaIni.setText("");
                txtFechaFin.setText("");
                txtFechaAlta.setText("");
                comboEvento.setSelectedIndex(0);
                comboOrganizador.setSelectedIndex(0);

                JOptionPane.showMessageDialog(this, "Edición creada con éxito");
                dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto o error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    void cargarEventos() {
        eventos = controller.listarEventos();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Evento e : eventos) {
            model.addElement(e.getNombre());
        }
        comboEvento.setModel(model);
    }

    void cargarOrganizadores() {
        organizadores = controller.listarOrganizadores();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Organizador o : organizadores) {
            model.addElement(o.getNombre());
        }
        comboOrganizador.setModel(model);
    }

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

    private JFormattedTextField crearCampoFecha(String formato) {
        try {
            MaskFormatter mascara = new MaskFormatter(formato);
            mascara.setPlaceholderCharacter('_');
            JFormattedTextField campo = new JFormattedTextField(mascara);
            campo.setFont(new Font("Tahoma", Font.PLAIN, 10));
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



