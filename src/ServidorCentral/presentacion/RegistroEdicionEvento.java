package ServidorCentral.presentacion;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ServidorCentral.logica.*;
import net.miginfocom.swing.MigLayout;

public class RegistroEdicionEvento extends JInternalFrame {
    private static final long serialVersionUID = 1L;
    private JTextField textFieldCodigo;

    public RegistroEdicionEvento(IControllerEvento ICE, IControllerUsuario ICU) {
        super("Registro a Edicion de Evento", false, false, false, false);
        setSize(400, 300);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        getContentPane().setLayout(new MigLayout("", "[115px][][][][][][][10px][][][28px][24px][][][130px,grow]",
                "[14px][22px][14px][22px][14px][22px][14px][22px][][30px][][][][33px]"));

        JLabel lblEvento = new JLabel("Seleccione un evento:");
        getContentPane().add(lblEvento, "cell 0 0,alignx left,aligny center");
        JComboBox<String> comboBoxEvento = new JComboBox<>();
        getContentPane().add(comboBoxEvento, "cell 14 0,alignx right,aligny center");

        JLabel lblEdicion = new JLabel("Seleccione una edición:");
        getContentPane().add(lblEdicion, "cell 0 2,alignx left,aligny center");
        JComboBox<String> comboBoxEdicion = new JComboBox<>();
        getContentPane().add(comboBoxEdicion, "cell 14 2,alignx right,aligny center");

        JLabel lblTipoRegistro = new JLabel("Seleccione un Tipo de registro:");
        getContentPane().add(lblTipoRegistro, "cell 0 4 11 1,alignx left,aligny center");
        JComboBox<String> comboBoxRegistro = new JComboBox<>();
        getContentPane().add(comboBoxRegistro, "cell 11 4 4 1,alignx right,aligny center");

        JLabel lblAsistente = new JLabel("Seleccione asistente:");
        getContentPane().add(lblAsistente, "cell 0 6,alignx left,aligny center");
        JComboBox<String> comboBoxAsistente = new JComboBox<>();
        getContentPane().add(comboBoxAsistente, "cell 14 6,alignx right,aligny center");

        JLabel lblCodigo = new JLabel("Codigo:");
        getContentPane().add(lblCodigo, "cell 0 8");
        textFieldCodigo = new JTextField();
        getContentPane().add(textFieldCodigo, "cell 14 8,growx");
        textFieldCodigo.setColumns(10);

        JButton btnRegistrar = new JButton("Registrar asistente");
        btnRegistrar.setEnabled(false); // deshabilitado inicialmente
        getContentPane().add(btnRegistrar, "cell 0 12");

        JButton btnCancelar = new JButton("Cancelar");
        getContentPane().add(btnCancelar, "cell 14 12");

        // Deshabilitar combos inicialmente
        comboBoxEdicion.setEnabled(false);
        comboBoxRegistro.setEnabled(false);
        comboBoxAsistente.setEnabled(false);

        // Llenar combo de eventos
        for (Evento ev : ICE.listarEventos()) {
            comboBoxEvento.addItem(ev.getNombre());
        }

        // Función para habilitar botón Registrar
        Runnable actualizarBoton = () -> {
            boolean habilitar = comboBoxEvento.getSelectedItem() != null &&
                    comboBoxEdicion.getSelectedItem() != null &&
                    comboBoxRegistro.getSelectedItem() != null &&
                    comboBoxAsistente.getSelectedItem() != null &&
                    !textFieldCodigo.getText().trim().isEmpty();
            btnRegistrar.setEnabled(habilitar);
        };

        // Acción de seleccionar evento
        comboBoxEvento.addActionListener(e -> {
            comboBoxEdicion.removeAllItems();
            comboBoxRegistro.removeAllItems();
            comboBoxAsistente.removeAllItems();
            comboBoxRegistro.setEnabled(false);
            comboBoxAsistente.setEnabled(false);

            String eventoSel = (String) comboBoxEvento.getSelectedItem();
            if (eventoSel != null) {
                Evento ev = ICE.getEvento(eventoSel);
                if (ev != null) {
                    for (Edicion ed : ev.getEdiciones()) {
                        comboBoxEdicion.addItem(ed.getNombre());
                    }
                    comboBoxEdicion.setEnabled(true);
                }
            }
            actualizarBoton.run();
        });

        // Acción de seleccionar edición
        comboBoxEdicion.addActionListener(e -> {
            comboBoxRegistro.removeAllItems();
            comboBoxAsistente.removeAllItems();
            comboBoxAsistente.setEnabled(false);

            String nombreEdicion = (String) comboBoxEdicion.getSelectedItem();
            String nombreEvento = (String) comboBoxEvento.getSelectedItem();
            if (nombreEdicion != null && nombreEvento != null) {
            	DTEdicion ed = ICE.consultaEdicionDeEvento(nombreEvento, nombreEdicion);
                if (ed != null) {
                    for (DTTipoRegistro tr : ed.getTipoRegistros()) {
                        comboBoxRegistro.addItem(tr.getNombre());
                    }

                    comboBoxRegistro.setEnabled(true);
                }
            }
            actualizarBoton.run();
        });

        // Acción de seleccionar tipo de registro
        comboBoxRegistro.addActionListener(e -> {
            comboBoxAsistente.removeAllItems();

            String tipoR = (String) comboBoxRegistro.getSelectedItem();
            if (tipoR != null) {
                for (Asistente a : ICU.getAsistentes()) { // O filtrar según disponibilidad
                    comboBoxAsistente.addItem(a.getNickname());
                }
                comboBoxAsistente.setEnabled(true);
            } else {
                comboBoxAsistente.setEnabled(false);
            }
            actualizarBoton.run();
        });

        // Acción de seleccionar asistente
        comboBoxAsistente.addActionListener(e -> actualizarBoton.run());

        // DocumentListener para código
        textFieldCodigo.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { actualizarBoton.run(); }
            public void removeUpdate(DocumentEvent e) { actualizarBoton.run(); }
            public void changedUpdate(DocumentEvent e) { actualizarBoton.run(); }
        });

        // Acción botón Registrar
        btnRegistrar.addActionListener(e -> {
            String edicionSel = (String) comboBoxEdicion.getSelectedItem();
            String tipoRegistroSel = (String) comboBoxRegistro.getSelectedItem();
            String asistenteSel = (String) comboBoxAsistente.getSelectedItem();
            String codigo = textFieldCodigo.getText().trim();

            try {
                ICE.altaRegistro(edicionSel, asistenteSel, tipoRegistroSel, codigo);
                JOptionPane.showMessageDialog(this, "Registro exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Limpiar después de registrar
                comboBoxEvento.setSelectedIndex(-1);
                comboBoxEdicion.removeAllItems();
                comboBoxRegistro.removeAllItems();
                comboBoxAsistente.removeAllItems();
                textFieldCodigo.setText("");
                comboBoxEdicion.setEnabled(false);
                comboBoxRegistro.setEnabled(false);
                comboBoxAsistente.setEnabled(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción botón Cancelar
        btnCancelar.addActionListener(e -> this.dispose());
    }
}