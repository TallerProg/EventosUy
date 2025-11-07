package servidorcentral.presentacion;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import servidorcentral.logica.Categoria;
import servidorcentral.logica.IControllerEvento;

public class AltaEvento extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private final IControllerEvento controlEvento;

    private JTextField textFieldNombre;
    private JTextField textFieldSigla;
    private JTextArea textAreaDescripcion;
    private JButton btnAceptar;
    private JButton btnCancelar;

    private JPanel panelCategorias;
    private List<JCheckBox> checkBoxesCategorias;

    private int row = 0;

    public AltaEvento(IControllerEvento ice) {
        this.controlEvento = ice;

        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setTitle("Alta de Evento");
        setBounds(10, 10, 500, 450);

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 120, 250, 0 };
        gridBagLayout.rowHeights = new int[] { 30, 30, 60, 100, 30 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0, 0, 0, 0, 0 };
        getContentPane().setLayout(gridBagLayout);

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
        gbc_textNombre.insets = new Insets(5, 5, 5, 60);
        gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_textNombre.gridx = 1;
        gbc_textNombre.gridy = row;
        getContentPane().add(textFieldNombre, gbc_textNombre);
        row++;

        // Sigla
        JLabel lblSigla = new JLabel("Sigla:");
        GridBagConstraints gbc_lblSigla = new GridBagConstraints();
        gbc_lblSigla.insets = new Insets(5, 5, 5, 5);
        gbc_lblSigla.gridx = 0;
        gbc_lblSigla.gridy = row;
        gbc_lblSigla.anchor = GridBagConstraints.EAST;
        getContentPane().add(lblSigla, gbc_lblSigla);

        textFieldSigla = new JTextField();
        GridBagConstraints gbc_textSigla = new GridBagConstraints();
        gbc_textSigla.insets = new Insets(5, 5, 5, 60);
        gbc_textSigla.fill = GridBagConstraints.HORIZONTAL;
        gbc_textSigla.gridx = 1;
        gbc_textSigla.gridy = row;
        getContentPane().add(textFieldSigla, gbc_textSigla);
        row++;

        // Descripción
        JLabel lblDesc = new JLabel("Descripción:");
        GridBagConstraints gbc_lblDesc = new GridBagConstraints();
        gbc_lblDesc.insets = new Insets(5, 5, 5, 5);
        gbc_lblDesc.gridx = 0;
        gbc_lblDesc.gridy = row;
        gbc_lblDesc.anchor = GridBagConstraints.NORTHEAST;
        getContentPane().add(lblDesc, gbc_lblDesc);

        textAreaDescripcion = new JTextArea(4, 20);
        JScrollPane scrollDesc = new JScrollPane(textAreaDescripcion);
        GridBagConstraints gbc_textDesc = new GridBagConstraints();
        gbc_textDesc.insets = new Insets(5, 5, 5, 60);
        gbc_textDesc.fill = GridBagConstraints.BOTH;
        gbc_textDesc.gridx = 1;
        gbc_textDesc.gridy = row;
        getContentPane().add(scrollDesc, gbc_textDesc);
        row++;

        // Categorías
        JLabel lblCategorias = new JLabel("Categorías:");
        GridBagConstraints gbc_lblCategorias = new GridBagConstraints();
        gbc_lblCategorias.insets = new Insets(5, 5, 5, 5);
        gbc_lblCategorias.gridx = 0;
        gbc_lblCategorias.gridy = row;
        gbc_lblCategorias.anchor = GridBagConstraints.NORTHEAST;
        getContentPane().add(lblCategorias, gbc_lblCategorias);

        panelCategorias = new JPanel();
        panelCategorias.setLayout(new BoxLayout(panelCategorias, BoxLayout.Y_AXIS));
        JScrollPane scrollCategorias = new JScrollPane(panelCategorias);
        GridBagConstraints gbc_listCategorias = new GridBagConstraints();
        gbc_listCategorias.insets = new Insets(5, 5, 5, 60);
        gbc_listCategorias.fill = GridBagConstraints.BOTH;
        gbc_listCategorias.gridx = 1;
        gbc_listCategorias.gridy = row;
        getContentPane().add(scrollCategorias, gbc_listCategorias);
        row++;

        checkBoxesCategorias = new ArrayList<>();

        // Botones
        Dimension mismoTamaño = new Dimension(100, 25);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setPreferredSize(mismoTamaño);
        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.insets = new Insets(10, 20, 5, 80);
        gbc_btnCancelar.gridx = 1;
        gbc_btnCancelar.gridy = row;
        gbc_btnCancelar.weightx = 0;
        gbc_btnCancelar.anchor = GridBagConstraints.LINE_END;
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        btnAceptar = new JButton("Aceptar");
        btnAceptar.setPreferredSize(mismoTamaño);
        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.insets = new Insets(10, 50, 5, 20);
        gbc_btnAceptar.gridx = 1;
        gbc_btnAceptar.gridy = row;
        gbc_btnAceptar.weightx = 0;
        gbc_btnAceptar.anchor = GridBagConstraints.LINE_START;
        getContentPane().add(btnAceptar, gbc_btnAceptar);

        // Listeners
        btnCancelar.addActionListener(e -> {
            limpiarFormulario();
            this.setVisible(false);
        });

        btnAceptar.addActionListener(e -> {
            String nombre = textFieldNombre.getText().trim();
            String sigla = textFieldSigla.getText().trim();
            String descripcion = textAreaDescripcion.getText().trim();

            List<String> categoriasSeleccionadas = new ArrayList<>();
            for (JCheckBox check : checkBoxesCategorias) {
                if (check.isSelected()) {
                    categoriasSeleccionadas.add(check.getText());
                }
            }

            if (nombre.isEmpty() || sigla.isEmpty() || descripcion.isEmpty() || categoriasSeleccionadas.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe completar todos los campos y seleccionar al menos una categoría.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate fechaAlta = LocalDate.now();

                List<Categoria> listaCategorias = new ArrayList<>();
                for (String nombreCat : categoriasSeleccionadas) {
                    for (Categoria c : controlEvento.getCategorias()) {
                        if (c.getNombre().equals(nombreCat)) {
                            listaCategorias.add(c);
                            break;
                        }
                    }
                }

                controlEvento.altaEvento(nombre, descripcion, fechaAlta, sigla, listaCategorias, null);

                limpiarFormulario();
                JOptionPane.showMessageDialog(this,
                        "Evento creado correctamente.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // Carga inicial (por si ya había datos cargados antes de abrir el frame)
        cargarCategorias();
    }

    private void cargarCategorias() {
        panelCategorias.removeAll();
        checkBoxesCategorias.clear();

        List<Categoria> categorias = controlEvento.getCategorias();
        if (categorias != null) {
            for (Categoria c : categorias) {
                JCheckBox checkBox = new JCheckBox(c.getNombre());
                checkBoxesCategorias.add(checkBox);
                panelCategorias.add(checkBox);
            }
        }

        panelCategorias.revalidate();
        panelCategorias.repaint();
    }

    private void limpiarFormulario() {
        textFieldNombre.setText("");
        textFieldSigla.setText("");
        textAreaDescripcion.setText("");
        for (JCheckBox check : checkBoxesCategorias) {
            check.setSelected(false);
        }
    }

    public void recargarCategorias() {
        cargarCategorias();
    }
}
