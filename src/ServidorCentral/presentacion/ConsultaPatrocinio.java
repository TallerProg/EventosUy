package ServidorCentral.presentacion;

import javax.swing.*;
import java.util.List;
import ServidorCentral.logica.DTPatrocinio;
import ServidorCentral.logica.IControllerEvento;

public class ConsultaPatrocinio extends JInternalFrame {

    private static final long serialVersionUID = 1L;

    private IControllerEvento controller;

    private JComboBox<String> comboEventos;
    private JComboBox<String> comboEdiciones;
    private JComboBox<String> comboPatrocinios;

    private JTextField tfCodigo, tfFechaInicio, tfRegistroGratuito, tfMonto, tfNivel, tfInstitucion, tfTipoRegistro;

    public ConsultaPatrocinio(IControllerEvento controller) {
        this.controller = controller;
        setTitle("Consulta de Patrocinio");
        setSize(700, 400);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        getContentPane().setLayout(null);

        // --- Combos ---
        JLabel lblEvento = new JLabel("Evento:");
        lblEvento.setBounds(30, 20, 100, 25);
        getContentPane().add(lblEvento);

        comboEventos = new JComboBox<>();
        comboEventos.setBounds(140, 20, 245, 25);
        comboEventos.setEnabled(false); // Fijo
        getContentPane().add(comboEventos);

        JLabel lblEdicion = new JLabel("Edición:");
        lblEdicion.setBounds(30, 60, 100, 25);
        getContentPane().add(lblEdicion);

        comboEdiciones = new JComboBox<>();
        comboEdiciones.setBounds(140, 60, 245, 25);
        comboEdiciones.setEnabled(false); // Fijo
        getContentPane().add(comboEdiciones);

        JLabel lblPatrocinio = new JLabel("Patrocinio:");
        lblPatrocinio.setBounds(30, 100, 100, 25);
        getContentPane().add(lblPatrocinio);

        comboPatrocinios = new JComboBox<>();
        comboPatrocinios.setBounds(140, 100, 210, 25);
        comboPatrocinios.setEnabled(false); // Fijo
        getContentPane().add(comboPatrocinios);

        // --- Campos de Patrocinio ---
        int y = 150;
        int labelWidth = 120;
        int tfWidth = 200;
        int height = 25;

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(30, y, labelWidth, height);
        getContentPane().add(lblCodigo);

        tfCodigo = new JTextField(); tfCodigo.setEditable(false);
        tfCodigo.setBounds(150, y, tfWidth, height);
        getContentPane().add(tfCodigo);

        JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
        lblFechaInicio.setBounds(30, y + 30, labelWidth, height);
        getContentPane().add(lblFechaInicio);

        tfFechaInicio = new JTextField(); tfFechaInicio.setEditable(false);
        tfFechaInicio.setBounds(150, y + 30, tfWidth, height);
        getContentPane().add(tfFechaInicio);

        JLabel lblRegistroGratuito = new JLabel("Registros Gratuitos:");
        lblRegistroGratuito.setBounds(30, y + 60, labelWidth, height);
        getContentPane().add(lblRegistroGratuito);

        tfRegistroGratuito = new JTextField(); tfRegistroGratuito.setEditable(false);
        tfRegistroGratuito.setBounds(150, y + 60, tfWidth, height);
        getContentPane().add(tfRegistroGratuito);

        JLabel lblMonto = new JLabel("Monto:");
        lblMonto.setBounds(30, y + 90, labelWidth, height);
        getContentPane().add(lblMonto);

        tfMonto = new JTextField(); tfMonto.setEditable(false);
        tfMonto.setBounds(150, y + 90, tfWidth, height);
        getContentPane().add(tfMonto);

        JLabel lblNivel = new JLabel("Nivel:");
        lblNivel.setBounds(30, y + 120, labelWidth, height);
        getContentPane().add(lblNivel);

        tfNivel = new JTextField(); tfNivel.setEditable(false);
        tfNivel.setBounds(150, y + 120, tfWidth, height);
        getContentPane().add(tfNivel);

        JLabel lblInstitucion = new JLabel("Institución:");
        lblInstitucion.setBounds(30, y + 150, labelWidth, height);
        getContentPane().add(lblInstitucion);

        tfInstitucion = new JTextField(); tfInstitucion.setEditable(false);
        tfInstitucion.setBounds(150, y + 150, tfWidth, height);
        getContentPane().add(tfInstitucion);

        JLabel lblTipoRegistro = new JLabel("Tipo Registro:");
        lblTipoRegistro.setBounds(30, y + 180, labelWidth, height);
        getContentPane().add(lblTipoRegistro);

        tfTipoRegistro = new JTextField(); tfTipoRegistro.setEditable(false);
        tfTipoRegistro.setBounds(150, y + 180, tfWidth, height);
        getContentPane().add(tfTipoRegistro);
    }

    // Carga los datos en los campos y combos, combos quedan fijos
    public void cargarDatos(DTPatrocinio dtPat) {
        if (dtPat == null) return;

        tfCodigo.setText(dtPat.getCodigo());
        tfFechaInicio.setText(dtPat.getFInicio().toString());
        tfRegistroGratuito.setText(String.valueOf(dtPat.getRegistroGratuito()));
        tfMonto.setText(String.valueOf(dtPat.getMonto()));
        tfNivel.setText(String.valueOf(dtPat.getNivel()));
        tfInstitucion.setText(dtPat.getInstitucion());
        tfTipoRegistro.setText(dtPat.getTipoRegistro());

        comboEventos.removeAllItems();
        comboEventos.addItem(dtPat.getEdicion());
        comboEventos.setSelectedIndex(0);

        comboEdiciones.removeAllItems();
        comboEdiciones.addItem(dtPat.getEdicion());
        comboEdiciones.setSelectedIndex(0);

        comboPatrocinios.removeAllItems();
        comboPatrocinios.addItem(dtPat.getCodigo());
        comboPatrocinios.setSelectedIndex(0);
    }

    // Método para crear y mostrar la ventana
    public static ConsultaPatrocinio crearYMostrar(IControllerEvento controller, DTPatrocinio dtPat, JDesktopPane desktopPane) {
        ConsultaPatrocinio cp = new ConsultaPatrocinio(controller);
        cp.cargarDatos(dtPat);
        if (desktopPane != null) {
            desktopPane.add(cp);
            cp.setVisible(true);
            cp.setLocation(
                (desktopPane.getWidth() - cp.getWidth()) / 2,
                (desktopPane.getHeight() - cp.getHeight()) / 2
            );
        }
        return cp;
    }
}
