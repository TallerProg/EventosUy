package ServidorCentral.presentacion;

import ServidorCentral.logica.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ModificarUsuario extends JInternalFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IControllerUsuario controlUsr;

    private JComboBox<Usuario> comboUsuarios;
    private JComboBox<String> comboTipoUsuario;
    private JTextField textFieldNickName;
    private JTextField textFieldCorreo;
    private JTextField textFieldNombre;
    private JTextField textFieldApellido;
    private JTextField textFieldFechaNacimiento;
    private JTextField textFieldDescripcion;
    private JTextField textFieldURL;
    private JComboBox<String> comboInstitucion;
    private JButton btnAceptar;
    private JButton btnCancelar;

    private int row = 0;

    public ModificarUsuario(IControllerUsuario icu) {
        controlUsr = icu;

        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setTitle("Modificar Usuario");
        setBounds(10, 10, 500, 450);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{120, 250};
        gridBagLayout.rowHeights = new int[10];
        gridBagLayout.columnWeights = new double[]{0.0, 1.0};
        gridBagLayout.rowWeights = new double[10];
        getContentPane().setLayout(gridBagLayout);

        JLabel lblSeleccion = new JLabel("Seleccione un Usuario:");
        GridBagConstraints gbc_lblSeleccion = new GridBagConstraints();
        gbc_lblSeleccion.insets = new Insets(5,5,5,5);
        gbc_lblSeleccion.gridx = 0; gbc_lblSeleccion.gridy = row;
        gbc_lblSeleccion.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblSeleccion, gbc_lblSeleccion);

        comboUsuarios = new JComboBox<>();
        List<Usuario> usuarios = controlUsr.listarUsuarios();
        for (Usuario u : usuarios) comboUsuarios.addItem(u);

        GridBagConstraints gbc_comboUsuarios = new GridBagConstraints();
        gbc_comboUsuarios.insets = new Insets(5,5,5,5);
        gbc_comboUsuarios.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboUsuarios.gridx = 1; gbc_comboUsuarios.gridy = row;
        getContentPane().add(comboUsuarios, gbc_comboUsuarios);
        row++;

        JLabel lblTipo = new JLabel("Tipo de Usuario:");
        GridBagConstraints gbc_lblTipo = new GridBagConstraints();
        gbc_lblTipo.insets = new Insets(5,5,5,5);
        gbc_lblTipo.gridx = 0; gbc_lblTipo.gridy = row;
        gbc_lblTipo.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblTipo, gbc_lblTipo);

        comboTipoUsuario = new JComboBox<>(new String[]{"Asistente","Organizador"});
        comboTipoUsuario.setEnabled(false); 
        GridBagConstraints gbc_comboTipo = new GridBagConstraints();
        gbc_comboTipo.insets = new Insets(5,5,5,5);
        gbc_comboTipo.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboTipo.gridx = 1; gbc_comboTipo.gridy = row;
        getContentPane().add(comboTipoUsuario, gbc_comboTipo);
        row++;

        textFieldNickName = crearCampo("Nickname:", row++); 
        textFieldNickName.setEditable(false); 
        textFieldCorreo = crearCampo("Correo:", row++);
        textFieldCorreo.setEditable(false); 
        textFieldNombre = crearCampo("Nombre:", row++);

        textFieldApellido = new JTextField();
        textFieldFechaNacimiento = new JTextField();
        textFieldDescripcion = new JTextField();
        textFieldURL = new JTextField();

        comboInstitucion = new JComboBox<>();
        comboInstitucion.addItem("Ninguna");
        for (Institucion ins : ManejadorInstitucion.getInstance().listarInstituciones()) {
            comboInstitucion.addItem(ins.getNombre());
        }

        // --- Botones ---
        btnAceptar = new JButton("Modificar");
        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.insets = new Insets(10,5,5,5);
        gbc_btnAceptar.gridx = 0; gbc_btnAceptar.gridy = 8; gbc_btnAceptar.gridwidth = 1;
        getContentPane().add(btnAceptar, gbc_btnAceptar);

        btnCancelar = new JButton("Cancelar");
        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.insets = new Insets(10,5,5,5);
        gbc_btnCancelar.gridx = 1; gbc_btnCancelar.gridy = 8;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        btnCancelar.addActionListener(e -> dispose());

        comboUsuarios.addActionListener(e -> {
            Usuario u = (Usuario) comboUsuarios.getSelectedItem();
            if (u != null) mostrarCamposSegunTipo(u);
        });

        btnAceptar.addActionListener(e -> {
            Usuario u = (Usuario) comboUsuarios.getSelectedItem();
            if (u == null) return;

            boolean modificado = false;

            if (!u.getNombre().equals(textFieldNombre.getText())) {
                u.setNombre(textFieldNombre.getText());
                modificado = true;
            }
            if (!u.getCorreo().equals(textFieldCorreo.getText())) {
                u.setCorreo(textFieldCorreo.getText());
                modificado = true;
            }

            if (u instanceof Asistente) {
                Asistente a = (Asistente) u;
                if (!a.getApellido().equals(textFieldApellido.getText())) {
                    a.setApellido(textFieldApellido.getText());
                    modificado = true;
                }
                try {
                    LocalDate fNac = LocalDate.parse(textFieldFechaNacimiento.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    if (!a.getfNacimiento().equals(fNac)) {
                        a.setfNacimiento(fNac);
                        modificado = true;
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Fecha inválida", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String instName = (String) comboInstitucion.getSelectedItem();
                a.setInstitucion(instName.equals("Ninguna") ? null : ManejadorInstitucion.getInstance().buscarPorNombre(instName));
            } else if (u instanceof Organizador) {
                Organizador o = (Organizador) u;
                if (!o.getDescripcion().equals(textFieldDescripcion.getText())) {
                    o.setDescripcion(textFieldDescripcion.getText());
                    modificado = true;
                }
                String url = textFieldURL.getText().isEmpty() ? null : textFieldURL.getText();
                if (o.getUrl() == null || !o.getUrl().equals(url)) {
                    o.setUrl(url);
                    modificado = true;
                }
            }

            try {
                controlUsr.modificarUsuario1(u);
                if (modificado) JOptionPane.showMessageDialog(this, "Usuario modificado correctamente");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al modificar usuario: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JTextField crearCampo(String labelText, int row) {
        JLabel lbl = new JLabel(labelText);
        GridBagConstraints gbc_lbl = new GridBagConstraints();
        gbc_lbl.insets = new Insets(5,5,5,5);
        gbc_lbl.gridx = 0; gbc_lbl.gridy = row;
        gbc_lbl.anchor = GridBagConstraints.EAST;
        getContentPane().add(lbl, gbc_lbl);

        JTextField tf = new JTextField();
        GridBagConstraints gbc_tf = new GridBagConstraints();
        gbc_tf.insets = new Insets(5,5,5,5);
        gbc_tf.fill = GridBagConstraints.HORIZONTAL;
        gbc_tf.gridx = 1; gbc_tf.gridy = row;
        getContentPane().add(tf, gbc_tf);
        return tf;
    }

    private void mostrarCamposSegunTipo(Usuario u) {
        textFieldApellido.setVisible(false);
        textFieldFechaNacimiento.setVisible(false);
        textFieldDescripcion.setVisible(false);
        textFieldURL.setVisible(false);
        comboInstitucion.setVisible(false);

        if (u instanceof Asistente) {
            comboTipoUsuario.setSelectedItem("Asistente");

            GridBagConstraints gbcAp = new GridBagConstraints();
            gbcAp.insets = new Insets(5,5,5,5);
            gbcAp.gridx = 0; gbcAp.gridy = row;
            gbcAp.anchor = GridBagConstraints.EAST;
            getContentPane().add(new JLabel("Apellido:"), gbcAp);

            gbcAp = new GridBagConstraints();
            gbcAp.insets = new Insets(5,5,5,5);
            gbcAp.fill = GridBagConstraints.HORIZONTAL;
            gbcAp.gridx = 1; gbcAp.gridy = row;
            getContentPane().add(textFieldApellido, gbcAp);
            textFieldApellido.setText(((Asistente) u).getApellido());
            textFieldApellido.setVisible(true);
            row++;

            GridBagConstraints gbcFecha = new GridBagConstraints();
            gbcFecha.insets = new Insets(5,5,5,5);
            gbcFecha.gridx = 0; gbcFecha.gridy = row;
            gbcFecha.anchor = GridBagConstraints.EAST;
            getContentPane().add(new JLabel("Fecha Nac.:"), gbcFecha);

            gbcFecha = new GridBagConstraints();
            gbcFecha.insets = new Insets(5,5,5,5);
            gbcFecha.fill = GridBagConstraints.HORIZONTAL;
            gbcFecha.gridx = 1; gbcFecha.gridy = row;
            getContentPane().add(textFieldFechaNacimiento, gbcFecha);
            textFieldFechaNacimiento.setText(((Asistente) u).getfNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            textFieldFechaNacimiento.setVisible(true);
            row++;

            GridBagConstraints gbcInst = new GridBagConstraints();
            gbcInst.insets = new Insets(5,5,5,5);
            gbcInst.gridx = 0; gbcInst.gridy = row;
            gbcInst.anchor = GridBagConstraints.EAST;
            getContentPane().add(new JLabel("Institución:"), gbcInst);

            gbcInst = new GridBagConstraints();
            gbcInst.insets = new Insets(5,5,5,5);
            gbcInst.fill = GridBagConstraints.HORIZONTAL;
            gbcInst.gridx = 1; gbcInst.gridy = row;
            getContentPane().add(comboInstitucion, gbcInst);
            comboInstitucion.setSelectedItem(((Asistente) u).getInstitucion() != null ? ((Asistente) u).getInstitucion().getNombre() : "Ninguna");
            comboInstitucion.setVisible(true);
            row++;
        } else if (u instanceof Organizador) {
            comboTipoUsuario.setSelectedItem("Organizador");

            GridBagConstraints gbcDesc = new GridBagConstraints();
            gbcDesc.insets = new Insets(5,5,5,5);
            gbcDesc.gridx = 0; gbcDesc.gridy = row;
            gbcDesc.anchor = GridBagConstraints.EAST;
            getContentPane().add(new JLabel("Descripción:"), gbcDesc);

            gbcDesc = new GridBagConstraints();
            gbcDesc.insets = new Insets(5,5,5,5);
            gbcDesc.fill = GridBagConstraints.HORIZONTAL;
            gbcDesc.gridx = 1; gbcDesc.gridy = row;
            getContentPane().add(textFieldDescripcion, gbcDesc);
            textFieldDescripcion.setText(((Organizador) u).getDescripcion());
            textFieldDescripcion.setVisible(true);
            row++;

            GridBagConstraints gbcURL = new GridBagConstraints();
            gbcURL.insets = new Insets(5,5,5,5);
            gbcURL.gridx = 0; gbcURL.gridy = row;
            gbcURL.anchor = GridBagConstraints.EAST;
            getContentPane().add(new JLabel("URL:"), gbcURL);

            gbcURL = new GridBagConstraints();
            gbcURL.insets = new Insets(5,5,5,5);
            gbcURL.fill = GridBagConstraints.HORIZONTAL;
            gbcURL.gridx = 1; gbcURL.gridy = row;
            getContentPane().add(textFieldURL, gbcURL);
            textFieldURL.setText(((Organizador) u).getUrl() != null ? ((Organizador) u).getUrl() : "");
            textFieldURL.setVisible(true);
            row++;
        }

        getContentPane().revalidate();
        getContentPane().repaint();
    }
}

