package ServidorCentral.presentacion;

import ServidorCentral.logica.*;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ModificarUsuario extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private IControllerUsuario controlUsr;
    private IControllerInstitucion controlIns;

    private JComboBox<Usuario> comboUsuarios;
    private JComboBox<String> comboTipoUsuario;

    private JTextField textFieldNickName;
    private JTextField textFieldCorreo;
    private JTextField textFieldNombre;

    private JLabel lblApellido;
    private JTextField textFieldApellido;
    private JLabel lblFecha;
    private JTextField textFieldFechaNacimiento;

    private JLabel lblDescripcion;
    private JTextField textFieldDescripcion;
    private JLabel lblURL;
    private JTextField textFieldURL;

    private JLabel lblInstitucion;
    private JComboBox<String> comboInstitucion;

    private JButton btnAceptar;
    private JButton btnCancelar;

    public ModificarUsuario(IControllerUsuario icu, IControllerInstitucion ici) {
        controlUsr = icu;
        controlIns = ici;

        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setTitle("Modificar Usuario");
        setBounds(10, 10, 500, 460);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 120, 250, 0 };
        gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        getContentPane().setLayout(gridBagLayout);

        int row = 0;

        JLabel lblSeleccion = new JLabel("Seleccione Usuario:");
        GridBagConstraints gbc_lblSeleccion = new GridBagConstraints();
        gbc_lblSeleccion.insets = new Insets(5, 5, 5, 5);
        gbc_lblSeleccion.gridx = 0;
        gbc_lblSeleccion.gridy = row;
        gbc_lblSeleccion.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblSeleccion, gbc_lblSeleccion);

        comboUsuarios = new JComboBox<>();
        comboUsuarios.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Usuario u) {
                    setText(u.getNickname());
                }
                return this;
            }
        });

        GridBagConstraints gbc_comboUsuarios = new GridBagConstraints();
        gbc_comboUsuarios.insets = new Insets(5, 5, 5, 5);
        gbc_comboUsuarios.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboUsuarios.gridx = 1;
        gbc_comboUsuarios.gridy = row;
        getContentPane().add(comboUsuarios, gbc_comboUsuarios);
        row++;

        // Tipo de usuario
        JLabel lblTipo = new JLabel("Tipo de Usuario:");
        GridBagConstraints gbc_lblTipo = new GridBagConstraints();
        gbc_lblTipo.insets = new Insets(5, 5, 5, 5);
        gbc_lblTipo.gridx = 0;
        gbc_lblTipo.gridy = row;
        gbc_lblTipo.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblTipo, gbc_lblTipo);

        comboTipoUsuario = new JComboBox<>(new String[] { "Asistente", "Organizador" });
        comboTipoUsuario.setEnabled(false);
        GridBagConstraints gbc_comboTipo = new GridBagConstraints();
        gbc_comboTipo.insets = new Insets(5, 5, 5, 5);
        gbc_comboTipo.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboTipo.gridx = 1;
        gbc_comboTipo.gridy = row;
        getContentPane().add(comboTipoUsuario, gbc_comboTipo);
        row++;

        // Nickname
        JLabel lblNick = new JLabel("Nickname:");
        GridBagConstraints gbc_lblNick = new GridBagConstraints();
        gbc_lblNick.insets = new Insets(5, 5, 5, 5);
        gbc_lblNick.gridx = 0;
        gbc_lblNick.gridy = row;
        gbc_lblNick.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblNick, gbc_lblNick);

        textFieldNickName = new JTextField();
        textFieldNickName.setEditable(false);
        GridBagConstraints gbc_textNick = new GridBagConstraints();
        gbc_textNick.insets = new Insets(5, 5, 5, 5);
        gbc_textNick.fill = GridBagConstraints.HORIZONTAL;
        gbc_textNick.gridx = 1;
        gbc_textNick.gridy = row;
        getContentPane().add(textFieldNickName, gbc_textNick);
        row++;

        // Correo
        JLabel lblCorreo = new JLabel("Correo:");
        GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
        gbc_lblCorreo.insets = new Insets(5, 5, 5, 5);
        gbc_lblCorreo.gridx = 0;
        gbc_lblCorreo.gridy = row;
        gbc_lblCorreo.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblCorreo, gbc_lblCorreo);

        textFieldCorreo = new JTextField();
        GridBagConstraints gbc_textCorreo = new GridBagConstraints();
        gbc_textCorreo.insets = new Insets(5, 5, 5, 5);
        gbc_textCorreo.fill = GridBagConstraints.HORIZONTAL;
        gbc_textCorreo.gridx = 1;
        gbc_textCorreo.gridy = row;
        getContentPane().add(textFieldCorreo, gbc_textCorreo);
        row++;

        // Nombre
        JLabel lblNombre = new JLabel("Nombre:");
        GridBagConstraints gbc_lblNombre = new GridBagConstraints();
        gbc_lblNombre.insets = new Insets(5, 5, 5, 5);
        gbc_lblNombre.gridx = 0;
        gbc_lblNombre.gridy = row;
        gbc_lblNombre.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblNombre, gbc_lblNombre);

        textFieldNombre = new JTextField();
        GridBagConstraints gbc_textNombre = new GridBagConstraints();
        gbc_textNombre.insets = new Insets(5, 5, 5, 5);
        gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_textNombre.gridx = 1;
        gbc_textNombre.gridy = row;
        getContentPane().add(textFieldNombre, gbc_textNombre);
        row++;

        // Campos dinámicos
        lblApellido = new JLabel("Apellido:");
        textFieldApellido = new JTextField();
        addField(lblApellido, textFieldApellido, row++);
        lblApellido.setVisible(false);
        textFieldApellido.setVisible(false);

        lblFecha = new JLabel("Fecha de Nac.:");
        textFieldFechaNacimiento = new JTextField();
        addField(lblFecha, textFieldFechaNacimiento, row++);
        lblFecha.setVisible(false);
        textFieldFechaNacimiento.setVisible(false);

        lblDescripcion = new JLabel("Descripción:");
        textFieldDescripcion = new JTextField();
        addField(lblDescripcion, textFieldDescripcion, row++);
        lblDescripcion.setVisible(false);
        textFieldDescripcion.setVisible(false);

        lblURL = new JLabel("URL (opcional):");
        textFieldURL = new JTextField();
        addField(lblURL, textFieldURL, row++);
        lblURL.setVisible(false);
        textFieldURL.setVisible(false);

        lblInstitucion = new JLabel("Institución:");
        comboInstitucion = new JComboBox<>();
        addField(lblInstitucion, comboInstitucion, row++);
        lblInstitucion.setVisible(false);
        comboInstitucion.setVisible(false);

        // Botones
        btnAceptar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");

        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.insets = new Insets(10, 50, 5, 20);
        gbc_btnAceptar.gridx = 1;
        gbc_btnAceptar.gridy = row;
        gbc_btnAceptar.anchor = GridBagConstraints.LINE_START;
        btnAceptar.setPreferredSize(new Dimension(100, 25));
        getContentPane().add(btnAceptar, gbc_btnAceptar);

        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.insets = new Insets(10, 20, 5, 80);
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = row;
        gbc_btnCancelar.anchor = GridBagConstraints.LINE_END;
        btnCancelar.setPreferredSize(new Dimension(100, 25));
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        // Listeners
        btnCancelar.addActionListener(e -> this.setVisible(false));
        comboUsuarios.addActionListener(e -> actualizarCampos());
        btnAceptar.addActionListener(e -> modificarUsuario());
    }

    private void addField(JLabel label, JComponent field, int row) {
        GridBagConstraints gbc_lbl = new GridBagConstraints();
        gbc_lbl.insets = new Insets(5, 5, 5, 5);
        gbc_lbl.gridx = 0;
        gbc_lbl.gridy = row;
        gbc_lbl.anchor = GridBagConstraints.EAST;
        getContentPane().add(label, gbc_lbl);

        GridBagConstraints gbc_field = new GridBagConstraints();
        gbc_field.insets = new Insets(5, 5, 5, 5);
        gbc_field.fill = GridBagConstraints.HORIZONTAL;
        gbc_field.gridx = 1;
        gbc_field.gridy = row;
        getContentPane().add(field, gbc_field);
    }

    private void actualizarCampos() {
        Usuario u = (Usuario) comboUsuarios.getSelectedItem();
        if (u == null) return;

        lblApellido.setVisible(false);
        textFieldApellido.setVisible(false);
        lblFecha.setVisible(false);
        textFieldFechaNacimiento.setVisible(false);
        lblDescripcion.setVisible(false);
        textFieldDescripcion.setVisible(false);
        lblURL.setVisible(false);
        textFieldURL.setVisible(false);
        lblInstitucion.setVisible(false);
        comboInstitucion.setVisible(false);

        textFieldNickName.setText(u.getNickname());
        textFieldCorreo.setText(u.getCorreo());
        textFieldNombre.setText(u.getNombre());

        if (u instanceof Asistente a) {
            comboTipoUsuario.setSelectedItem("Asistente");
            lblApellido.setVisible(true);
            textFieldApellido.setVisible(true);
            textFieldApellido.setText(a.getApellido());

            lblFecha.setVisible(true);
            textFieldFechaNacimiento.setVisible(true);
            textFieldFechaNacimiento.setText(a.getfNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            lblInstitucion.setVisible(true);
            comboInstitucion.setVisible(true);
            comboInstitucion.removeAllItems();
            comboInstitucion.addItem("Ninguna");
            for (Institucion ins : controlIns.getInstituciones()) {
                comboInstitucion.addItem(ins.getNombre());
            }
            comboInstitucion.setSelectedItem(a.getInstitucion() != null ? a.getInstitucion().getNombre() : "Ninguna");

        } else if (u instanceof Organizador o) {
            comboTipoUsuario.setSelectedItem("Organizador");
            lblDescripcion.setVisible(true);
            textFieldDescripcion.setVisible(true);
            textFieldDescripcion.setText(o.getDescripcion());

            lblURL.setVisible(true);
            textFieldURL.setVisible(true);
            textFieldURL.setText(o.getUrl() != null ? o.getUrl() : "");
        }
    }

    private void modificarUsuario() {
        Usuario u = (Usuario) comboUsuarios.getSelectedItem();
        if (u == null) return;

        try {
            u.setCorreo(textFieldCorreo.getText().trim());
            u.setNombre(textFieldNombre.getText().trim());

            if (u instanceof Asistente a) {
                a.setApellido(textFieldApellido.getText().trim());
                LocalDate fecha = LocalDate.parse(textFieldFechaNacimiento.getText().trim(),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                a.setfNacimiento(fecha);
                String instName = (String) comboInstitucion.getSelectedItem();
                a.setInstitucion(instName.equals("Ninguna") ? null : controlIns.findInstitucion(instName));

            } else if (u instanceof Organizador o) {
                o.setDescripcion(textFieldDescripcion.getText().trim());
                String url = textFieldURL.getText().trim();
                o.setUrl(url.isEmpty() ? null : url);
            }

            controlUsr.modificarUsuario1(u);
            JOptionPane.showMessageDialog(this, "Usuario modificado correctamente");
            this.setVisible(false);

        } catch (DateTimeParseException dtpe) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cargarUsuarios() {
        comboUsuarios.removeAllItems();
        List<Usuario> usuarios = controlUsr.listarUsuarios();
        for (Usuario u : usuarios) {
            comboUsuarios.addItem(u);
        }
    }
}
