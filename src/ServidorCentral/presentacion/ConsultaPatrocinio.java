package ServidorCentral.presentacion;

import javax.swing.*;
import java.awt.*;
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
        comboEventos.setBounds(140, 20, 200, 25);
        getContentPane().add(comboEventos);
        
        JLabel lblEdicion = new JLabel("Edición:");
        lblEdicion.setBounds(30, 60, 100, 25);
        getContentPane().add(lblEdicion);
        
        comboEdiciones = new JComboBox<>();
        comboEdiciones.setBounds(140, 60, 200, 25);
        comboEdiciones.setEnabled(false);
        getContentPane().add(comboEdiciones);
        
        JLabel lblPatrocinio = new JLabel("Patrocinio:");
        lblPatrocinio.setBounds(30, 100, 100, 25);
        getContentPane().add(lblPatrocinio);
        
        comboPatrocinios = new JComboBox<>();
        comboPatrocinios.setBounds(140, 100, 200, 25);
        comboPatrocinios.setEnabled(false);
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
        
        // --- Lógica combos ---
        cargarEventos();
        
        comboEventos.addActionListener(e -> {
            String nombreEvento = (String) comboEventos.getSelectedItem();
            if (nombreEvento != null) {
                comboEdiciones.setEnabled(true);
                cargarEdiciones(nombreEvento);
            } else {
                comboEdiciones.setEnabled(false);
                comboEdiciones.removeAllItems();
                comboPatrocinios.setEnabled(false);
                comboPatrocinios.removeAllItems();
            }
        });
        
        comboEdiciones.addActionListener(e -> {
            String nombreEdicion = (String) comboEdiciones.getSelectedItem();
            if (nombreEdicion != null) {
                comboPatrocinios.setEnabled(true);
                cargarPatrocinios(nombreEdicion);
            } else {
                comboPatrocinios.setEnabled(false);
                comboPatrocinios.removeAllItems();
            }
        });
        
        comboPatrocinios.addActionListener(e -> {
            String nombreEdicion = (String) comboEdiciones.getSelectedItem();
            String codigoPat = (String) comboPatrocinios.getSelectedItem();
            if (nombreEdicion != null && codigoPat != null) {
                mostrarPatrocinio(controller.consultaPatrocinio(nombreEdicion, codigoPat));
            }
        });
    }
    
    private void cargarEventos() {
        comboEventos.removeAllItems();
        List<ServidorCentral.logica.Evento> eventos = controller.listarEventos();
        for (ServidorCentral.logica.Evento e : eventos) {
            comboEventos.addItem(e.getNombre());
        }
    }
    
    private void cargarEdiciones(String nombreEvento) {
        comboEdiciones.removeAllItems();
        List<String> ediciones = controller.listarEdicionesDeEvento(nombreEvento);
        for (String ed : ediciones) {
            comboEdiciones.addItem(ed);
        }
    }
    
    private void cargarPatrocinios(String nombreEdicion) {
        comboPatrocinios.removeAllItems();
        List<DTPatrocinio> patrocinios = controller.listarPatrociniosDeEdicion(nombreEdicion);
        for (DTPatrocinio p : patrocinios) {
            comboPatrocinios.addItem(p.getCodigo());
        }
    }
    
    private void mostrarPatrocinio(DTPatrocinio p) {
        if (p == null) return;
        tfCodigo.setText(p.getCodigo());
        tfFechaInicio.setText(p.getFInicio().toString());
        tfRegistroGratuito.setText(String.valueOf(p.getRegistroGratuito()));
        tfMonto.setText(String.valueOf(p.getMonto()));
        tfNivel.setText(String.valueOf(p.getNivel()));
        tfInstitucion.setText(p.getInstitucion());
        tfTipoRegistro.setText(p.getTipoRegistro());
    }
 // Método público para cargar los datos directamente
    public void cargarDatos(DTPatrocinio dtPat) {
        if (dtPat == null) return;
        tfCodigo.setText(dtPat.getCodigo());
        tfFechaInicio.setText(dtPat.getFInicio().toString());
        tfRegistroGratuito.setText(String.valueOf(dtPat.getRegistroGratuito()));
        tfMonto.setText(String.valueOf(dtPat.getMonto()));
        tfNivel.setText(String.valueOf(dtPat.getNivel()));
        tfInstitucion.setText(dtPat.getInstitucion());
        tfTipoRegistro.setText(dtPat.getTipoRegistro());
        
        // Opcional: actualizar los combos si querés reflejar evento/edición
        comboEventos.setSelectedItem(dtPat.getEdicion()); // si tu DTPatrocinio tuviera referencia al evento también podrías setearlo
    }

}
