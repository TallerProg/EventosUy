package ServidorCentral.presentacion;

import javax.swing.*;

import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.Edicion;

import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;

public class AltaTipoRegistro extends JInternalFrame {

    private static final long serialVersionUID = 1L;
    private IControllerEvento controlEvento;

    private JComboBox<Evento> comboBoxEvento;
    private JComboBox<Edicion> comboBoxEdicion;
    private JTextArea textAreaDescripcion;
    private JTextField textFieldNombre;
    private JTextField textFieldCosto;
    private JTextField textFieldCupo;
    private JButton btnAceptar;
    private JButton btnCancelar;

    public AltaTipoRegistro(IControllerEvento ice) {
        controlEvento = ice;

        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setTitle("Alta de Tipo de Registro");
        setBounds(10, 10, 505, 460);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{100, 292, 0, 120, 0};
        gridBagLayout.rowHeights = new int[]{60, 5, 0, 0, 65, 0, 0, 0, 60, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        JLabel lblEvento = new JLabel("Evento:");
        GridBagConstraints gbc_lblEvento = new GridBagConstraints();
        gbc_lblEvento.anchor = GridBagConstraints.EAST;
        gbc_lblEvento.fill = GridBagConstraints.VERTICAL;
        gbc_lblEvento.insets = new Insets(0, 0, 5, 5);
        gbc_lblEvento.gridx = 0;
        gbc_lblEvento.gridy = 1;
        getContentPane().add(lblEvento, gbc_lblEvento);

        comboBoxEvento = new JComboBox<>();
        GridBagConstraints gbc_comboBoxEvento = new GridBagConstraints();
        gbc_comboBoxEvento.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxEvento.fill = GridBagConstraints.BOTH;
        gbc_comboBoxEvento.gridx = 1;
        gbc_comboBoxEvento.gridy = 1;
        getContentPane().add(comboBoxEvento, gbc_comboBoxEvento);

        JLabel lblEdicion = new JLabel("Edición:");
        GridBagConstraints gbc_lblEdicion = new GridBagConstraints();
        gbc_lblEdicion.anchor = GridBagConstraints.EAST;
        gbc_lblEdicion.insets = new Insets(0, 0, 5, 5);
        gbc_lblEdicion.gridx = 0;
        gbc_lblEdicion.gridy = 2;
        getContentPane().add(lblEdicion, gbc_lblEdicion);

        comboBoxEdicion = new JComboBox<>();
        GridBagConstraints gbc_comboBoxEdicion = new GridBagConstraints();
        gbc_comboBoxEdicion.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxEdicion.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxEdicion.gridx = 1;
        gbc_comboBoxEdicion.gridy = 2;
        getContentPane().add(comboBoxEdicion, gbc_comboBoxEdicion);

        JLabel lblNombre = new JLabel("Nombre:");
        GridBagConstraints gbc_lblNombre = new GridBagConstraints();
        gbc_lblNombre.anchor = GridBagConstraints.EAST;
        gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
        gbc_lblNombre.gridx = 0;
        gbc_lblNombre.gridy = 3;
        getContentPane().add(lblNombre, gbc_lblNombre);

        textFieldNombre = new JTextField();
        GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
        gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldNombre.gridx = 1;
        gbc_textFieldNombre.gridy = 3;
        getContentPane().add(textFieldNombre, gbc_textFieldNombre);
        textFieldNombre.setColumns(10);

        JLabel lblDescripcion = new JLabel("Descripción:");
        GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
        gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
        gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescripcion.gridx = 0;
        gbc_lblDescripcion.gridy = 4;
        getContentPane().add(lblDescripcion, gbc_lblDescripcion);

        textAreaDescripcion = new JTextArea();
        JScrollPane scrollPaneDescripcion = new JScrollPane(textAreaDescripcion);
        GridBagConstraints gbc_scrollPaneDescripcion = new GridBagConstraints();
        gbc_scrollPaneDescripcion.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPaneDescripcion.fill = GridBagConstraints.BOTH;
        gbc_scrollPaneDescripcion.gridx = 1;
        gbc_scrollPaneDescripcion.gridy = 4;
        getContentPane().add(scrollPaneDescripcion, gbc_scrollPaneDescripcion);

        JLabel lblCosto = new JLabel("Costo:");
        GridBagConstraints gbc_lblCosto = new GridBagConstraints();
        gbc_lblCosto.anchor = GridBagConstraints.EAST;
        gbc_lblCosto.insets = new Insets(0, 0, 5, 5);
        gbc_lblCosto.gridx = 0;
        gbc_lblCosto.gridy = 5;
        getContentPane().add(lblCosto, gbc_lblCosto);

        textFieldCosto = new JTextField();
        GridBagConstraints gbc_textFieldCosto = new GridBagConstraints();
        gbc_textFieldCosto.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldCosto.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldCosto.gridx = 1;
        gbc_textFieldCosto.gridy = 5;
        getContentPane().add(textFieldCosto, gbc_textFieldCosto);

        JLabel lblCupo = new JLabel("Cupo:");
        GridBagConstraints gbc_lblCupo = new GridBagConstraints();
        gbc_lblCupo.anchor = GridBagConstraints.EAST;
        gbc_lblCupo.insets = new Insets(0, 0, 5, 5);
        gbc_lblCupo.gridx = 0;
        gbc_lblCupo.gridy = 6;
        getContentPane().add(lblCupo, gbc_lblCupo);

        textFieldCupo = new JTextField();
        GridBagConstraints gbc_textFieldCupo = new GridBagConstraints();
        gbc_textFieldCupo.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldCupo.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldCupo.gridx = 1;
        gbc_textFieldCupo.gridy = 6;
        getContentPane().add(textFieldCupo, gbc_textFieldCupo);

        // Botones con espacio entre medio, Aceptar primero
        JPanel panelBotones = new JPanel(new GridBagLayout());
        GridBagConstraints gbc_panelBotones = new GridBagConstraints();
        gbc_panelBotones.insets = new Insets(10, 0, 5, 0);
        gbc_panelBotones.gridx = 1;
        gbc_panelBotones.gridy = 7;
        gbc_panelBotones.gridwidth = 2;
        gbc_panelBotones.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().add(panelBotones, gbc_panelBotones);

        btnAceptar = new JButton("Aceptar");
        btnCancelar = new JButton("Cancelar");

        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.insets = new Insets(0, 0, 0, 20); // espacio a la derecha
        gbc_btnAceptar.gridx = 0;
        gbc_btnAceptar.gridy = 0;
        gbc_btnAceptar.anchor = GridBagConstraints.LINE_END;
        btnAceptar.setPreferredSize(new Dimension(100, 25));
        panelBotones.add(btnAceptar, gbc_btnAceptar);

        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = 0;
        gbc_btnCancelar.anchor = GridBagConstraints.LINE_START;
        btnCancelar.setPreferredSize(new Dimension(100, 25));
        panelBotones.add(btnCancelar, gbc_btnCancelar);

        //CARGAS
        cargarEventos();

        comboBoxEvento.addActionListener(e -> cargarEdiciones());

        //LISTENERS
        btnCancelar.addActionListener(e -> {
            textFieldNombre.setText("");
            textAreaDescripcion.setText("");
            textFieldCosto.setText("");
            textFieldCupo.setText("");
            comboBoxEvento.setSelectedIndex(-1);
            comboBoxEdicion.removeAllItems();
            comboBoxEdicion.setEnabled(false);
            this.setVisible(false);
        });

        btnAceptar.addActionListener(e -> {
            String nombre = textFieldNombre.getText().trim();
            String descripcion = textAreaDescripcion.getText().trim();
            String costoStr = textFieldCosto.getText().trim();
            String cupoStr = textFieldCupo.getText().trim();
            Evento eventoSeleccionado = (Evento) comboBoxEvento.getSelectedItem();
            Edicion edicionSeleccionada = (Edicion) comboBoxEdicion.getSelectedItem();

            if (nombre.isEmpty() || descripcion.isEmpty() || costoStr.isEmpty() || cupoStr.isEmpty() ||
                    eventoSeleccionado == null || edicionSeleccionada == null) {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar un evento, una edición y completar todos los campos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Float costo;
            Integer cupo;
            try {
                costo = Float.parseFloat(costoStr);
                if (costo < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Costo inválido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                cupo = Integer.parseInt(cupoStr);
                if (cupo < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Cupo inválido", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //ALTA
            try {
                controlEvento.altaTipoRegistro(nombre, descripcion, costo, cupo, edicionSeleccionada);
                JOptionPane.showMessageDialog(this, "Tipo de registro creado correctamente");
                textFieldNombre.setText("");
                textAreaDescripcion.setText("");
                textFieldCosto.setText("");
                textFieldCupo.setText("");
                this.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void cargarEventos() {
        List<Evento> eventos = controlEvento.listarEventos();
        DefaultComboBoxModel<Evento> model = new DefaultComboBoxModel<>();
        for (Evento e : eventos) {
            model.addElement(e);
        }
        comboBoxEvento.setModel(model);
        comboBoxEvento.setSelectedIndex(-1);
        comboBoxEdicion.setEnabled(false);
    }

    private void cargarEdiciones() {
        comboBoxEdicion.removeAllItems();
        Evento eventoSeleccionado = (Evento) comboBoxEvento.getSelectedItem();
        if (eventoSeleccionado != null) {
            List<Edicion> ediciones = eventoSeleccionado.getEdiciones();
            if (ediciones != null && !ediciones.isEmpty()) {
                DefaultComboBoxModel<Edicion> modelEd = new DefaultComboBoxModel<>();
                for (Edicion ed : ediciones) {
                    modelEd.addElement(ed);
                }
                comboBoxEdicion.setModel(modelEd);
                comboBoxEdicion.setEnabled(true);
            } else {
                comboBoxEdicion.setEnabled(false);
            }
        } else {
            comboBoxEdicion.setEnabled(false);
        }
    }
}

