package ServidorCentral.presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ServidorCentral.logica.Edicion;
import ServidorCentral.logica.EstadoEdicion;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.IControllerEvento;

public class AceptarRechazarEdicion extends JInternalFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final IControllerEvento ce;

    private JLabel lblEventos;
    private JComboBox<String> comboEventos;

    private JLabel lblEdiciones;
    private JComboBox<String> comboEdiciones;

    private JButton btnAceptar ;
    private JButton btnRechazar;

    public AceptarRechazarEdicion(IControllerEvento ce) {
        this.ce = ce;

        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setClosable(true);
        setTitle("Aceptar /Rechazar edición de evento");
        setBounds(10, 40, 600, 200);

        getContentPane().setLayout(null);

        // --- Evento ---
        lblEventos = new JLabel("Evento:");
        lblEventos.setBounds(25, 20, 150, 25);
        getContentPane().add(lblEventos);

        comboEventos = new JComboBox<>();
        comboEventos.setBounds(200, 20, 360, 25);
        getContentPane().add(comboEventos);

        // --- Ediciones (Ingresadas) ---
        lblEdiciones = new JLabel("Ediciones (Ingresadas):");
        lblEdiciones.setBounds(25, 60, 170, 25);
        getContentPane().add(lblEdiciones);

        comboEdiciones = new JComboBox<>();
        comboEdiciones.setBounds(200, 60, 360, 25);
        getContentPane().add(comboEdiciones);

        // --- Botones ---
        btnAceptar  = new JButton("Aceptar  edición");
        btnAceptar .setBounds(80, 110, 170, 25);
        getContentPane().add(btnAceptar );

        btnRechazar = new JButton("Rechazar edición");
        btnRechazar.setBounds(330, 110, 170, 25);
        getContentPane().add(btnRechazar);

        // Listeners
        comboEventos.addActionListener(e -> recargarEdicionesIngresadas());

        btnAceptar .addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String ed = (String) comboEdiciones.getSelectedItem();
                if (ed == null) {
                    JOptionPane.showMessageDialog(AceptarRechazarEdicion.this,
                            "Seleccioná una edición.", "Aviso",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                try {
                    ce.aceptarRechazarEdicion(ed, true);
                    JOptionPane.showMessageDialog(AceptarRechazarEdicion.this, "Edición aceptada.");
                    recargarEdicionesIngresadas();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AceptarRechazarEdicion.this,
                            "Error al Aceptar : " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRechazar.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                String ed = (String) comboEdiciones.getSelectedItem();
                if (ed == null) {
                    JOptionPane.showMessageDialog(AceptarRechazarEdicion.this,
                            "Seleccioná una edición.", "Aviso",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                try {
                    ce.aceptarRechazarEdicion(ed, false);
                    JOptionPane.showMessageDialog(AceptarRechazarEdicion.this, "Edición rechazada.");
                    recargarEdicionesIngresadas();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AceptarRechazarEdicion.this,
                            "Error al rechazar: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Carga inicial
        cargarEventos();
        actualizarEstadoBotones();
    }

    public void cargarEventos() {
        try {
            String[] nombres = ce.listarEventos().stream()
                    .map(Evento::getNombre)
                    .toArray(String[]::new);
            comboEventos.setModel(new DefaultComboBoxModel<>(nombres));
            if (nombres.length > 0) {
                comboEventos.setSelectedIndex(0);
            }
            recargarEdicionesIngresadas();
        } catch (Exception ex) {
            ex.printStackTrace();
            comboEventos.setModel(new DefaultComboBoxModel<>(new String[0]));
            comboEdiciones.setModel(new DefaultComboBoxModel<>(new String[0]));
        }
        actualizarEstadoBotones();
    }

    private void recargarEdicionesIngresadas() {
        String eventoSel = (String) comboEventos.getSelectedItem();
        if (eventoSel == null || eventoSel.isEmpty()) {
            comboEdiciones.setModel(new DefaultComboBoxModel<>(new String[0]));
            actualizarEstadoBotones();
            return;
        }
        try {
            String[] edIngresadas = ce.listarEdiciones().stream()
                    .filter(ed -> {
                        String nomEv = ed.getEvento() != null ? ed.getEvento().getNombre() : null;
                        return eventoSel.equals(nomEv) && ed.getEstado() == EstadoEdicion.Ingresada;
                    })
                    .map(Edicion::getNombre)
                    .toArray(String[]::new);

            comboEdiciones.setModel(new DefaultComboBoxModel<>(edIngresadas));
            if (edIngresadas.length > 0) comboEdiciones.setSelectedIndex(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            comboEdiciones.setModel(new DefaultComboBoxModel<>(new String[0]));
        }
        actualizarEstadoBotones();
    }

    private void actualizarEstadoBotones() {
        boolean hayEd = comboEdiciones.getItemCount() > 0;
        btnAceptar .setEnabled(hayEd);
        btnRechazar.setEnabled(hayEd);
    }
}
