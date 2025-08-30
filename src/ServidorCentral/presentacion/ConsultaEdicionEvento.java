package ServidorCentral.presentacion;

import javax.swing.*;
import java.awt.*;

import ServidorCentral.logica.DTEdicion;
import ServidorCentral.logica.DTOrganizador;
import ServidorCentral.logica.DTPatrocinio;
import ServidorCentral.logica.DTRegistro;
import ServidorCentral.logica.DTTipoRegistro;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.IControllerEvento;

public class ConsultaEdicionEvento extends JInternalFrame {

    private static final long serialVersionUID = -2318682568078151267L;

    private IControllerEvento controller;

    private JTextField NombreEdicion, CiudadEdicion, SiglaEdicion, PaisEdicion, FAltaEdicion, FInicioEdicion, FFinEdicion;
    private JComboBox<String> comboEventos, comboEdiciones, comboTiposRegistro, comboRegistros, comboPatrocinios, comboOrganizadores;
    private JLabel lblPais;
    private JLabel lblFechaDeInicio;
    private JLabel lblOrganizadores;
    private JLabel lblRegistros;
    private JLabel lblTiposDeRegitro;
    private JLabel lblPatrocinios;

    public ConsultaEdicionEvento(IControllerEvento ice) {
    	setTitle("Consulta de Edicion de Evento");
        this.controller = ice;
        getContentPane().setLayout(null);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setSize(600, 500);

        initComponents();
        cargarEventos();
    }

    private void initComponents() {
        // Labels y Combos
        JLabel lblDatosEdicion = new JLabel("Datos de la Edicion");
        lblDatosEdicion.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblDatosEdicion.setBounds(171, 98, 261, 28);
        getContentPane().add(lblDatosEdicion);

        JLabel lblSeleccionarEvento = new JLabel("Seleccionar Evento");
        lblSeleccionarEvento.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblSeleccionarEvento.setBounds(28, 24, 120, 20);
        getContentPane().add(lblSeleccionarEvento);

        JLabel lblSeleccionarEdicion = new JLabel("Seleccionar Edición");
        lblSeleccionarEdicion.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblSeleccionarEdicion.setBounds(28, 60, 120, 20);
        getContentPane().add(lblSeleccionarEdicion);

        NombreEdicion = new JTextField(); NombreEdicion.setEditable(false); NombreEdicion.setBounds(123, 145, 164, 28); getContentPane().add(NombreEdicion);
        CiudadEdicion = new JTextField(); CiudadEdicion.setEditable(false); CiudadEdicion.setBounds(109, 200, 144, 28); getContentPane().add(CiudadEdicion);
        SiglaEdicion = new JTextField(); SiglaEdicion.setEditable(false); SiglaEdicion.setBounds(385, 145, 86, 28); getContentPane().add(SiglaEdicion);
        PaisEdicion = new JTextField(); PaisEdicion.setEditable(false); PaisEdicion.setBounds(385, 200, 144, 28); getContentPane().add(PaisEdicion);
        FAltaEdicion = new JTextField(); FAltaEdicion.setEditable(false); FAltaEdicion.setBounds(136, 251, 130, 28); getContentPane().add(FAltaEdicion);
        FInicioEdicion = new JTextField(); FInicioEdicion.setEditable(false); FInicioEdicion.setBounds(443, 251, 120, 28); getContentPane().add(FInicioEdicion);
        FFinEdicion = new JTextField(); FFinEdicion.setEditable(false); FFinEdicion.setBounds(157, 310, 130, 28); getContentPane().add(FFinEdicion);

        comboEventos = new JComboBox<>(); comboEventos.setBounds(171, 21, 219, 28); getContentPane().add(comboEventos);
        comboEdiciones = new JComboBox<>(); comboEdiciones.setBounds(171, 60, 219, 28); getContentPane().add(comboEdiciones);
        comboTiposRegistro = new JComboBox<>(); comboTiposRegistro.setBounds(150, 347, 137, 28); getContentPane().add(comboTiposRegistro);
        comboRegistros = new JComboBox<>(); comboRegistros.setBounds(402, 347, 161, 28); getContentPane().add(comboRegistros);
        comboPatrocinios = new JComboBox<>(); comboPatrocinios.setBounds(123, 390, 164, 28); getContentPane().add(comboPatrocinios);
        comboOrganizadores = new JComboBox<>(); comboOrganizadores.setBounds(422, 309, 130, 28); getContentPane().add(comboOrganizadores);

        JLabel lblNewLabel = new JLabel("Nombre :");
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblNewLabel.setBounds(31, 147, 82, 21);
        getContentPane().add(lblNewLabel);

        JLabel lblCiudad = new JLabel("Ciudad :");
        lblCiudad.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblCiudad.setBounds(17, 200, 82, 21);
        getContentPane().add(lblCiudad);

        JLabel lblFechaDeAlta = new JLabel("Fecha de Alta :");
        lblFechaDeAlta.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblFechaDeAlta.setBounds(17, 258, 109, 21);
        getContentPane().add(lblFechaDeAlta);

        JLabel lblFechaDeFinalizacion = new JLabel("Fecha de Finalizacion :");
        lblFechaDeFinalizacion.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblFechaDeFinalizacion.setBounds(17, 311, 144, 21);
        getContentPane().add(lblFechaDeFinalizacion);

        JLabel lblSigla = new JLabel("Sigla :");
        lblSigla.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblSigla.setBounds(315, 147, 49, 21);
        getContentPane().add(lblSigla);

        lblPais = new JLabel("Pais :");
        lblPais.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblPais.setBounds(315, 202, 49, 21);
        getContentPane().add(lblPais);

        lblFechaDeInicio = new JLabel("Fecha de Inicio :");
        lblFechaDeInicio.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblFechaDeInicio.setBounds(297, 253, 120, 21);
        getContentPane().add(lblFechaDeInicio);

        lblOrganizadores = new JLabel("Organizadores :");
        lblOrganizadores.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblOrganizadores.setBounds(315, 317, 97, 21);
        getContentPane().add(lblOrganizadores);

        lblRegistros = new JLabel("Registros :");
        lblRegistros.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblRegistros.setBounds(315, 350, 82, 21);
        getContentPane().add(lblRegistros);

        lblTiposDeRegitro = new JLabel("Tipos de Regitro :");
        lblTiposDeRegitro.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblTiposDeRegitro.setBounds(20, 350, 120, 21);
        getContentPane().add(lblTiposDeRegitro);

        lblPatrocinios = new JLabel("Patrocinios :");
        lblPatrocinios.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblPatrocinios.setBounds(31, 393, 82, 21);
        getContentPane().add(lblPatrocinios);

        JButton btnVerTipoRegistro = new JButton("Ver Tipo de Registro");
        btnVerTipoRegistro.setBounds(95, 428, 180, 28);
        getContentPane().add(btnVerTipoRegistro);

        JButton btnVerPatrocinio = new JButton("Ver Patrocinio");
        btnVerPatrocinio.setBounds(339, 428, 150, 28);
        getContentPane().add(btnVerPatrocinio);

        comboEventos.addActionListener(e -> {
            String nombreEvento = (String) comboEventos.getSelectedItem();
            if (nombreEvento != null) {
                cargarEdiciones(nombreEvento);
            }
        });

        comboEdiciones.addActionListener(e -> {
            String nombreEvento = (String) comboEventos.getSelectedItem();
            String nombreEdicion = (String) comboEdiciones.getSelectedItem();
            if (nombreEdicion != null) {
                DTEdicion dtEd = controller.consultaEdicionDeEvento(nombreEvento, nombreEdicion);
                mostrarDatosEdicion(dtEd);
            }
        });

        btnVerTipoRegistro.addActionListener(e -> {
            String nombreTipo = (String) comboTiposRegistro.getSelectedItem();
            String nombreEdicion = (String) comboEdiciones.getSelectedItem();

            if (nombreTipo != null && nombreEdicion != null) {
                ConsultaTipoRegistro ctr = ConsultaTipoRegistro.crearYMostrar(controller, nombreEdicion, nombreTipo);
                this.getDesktopPane().add(ctr);
                ctr.setLocation(
                    (this.getDesktopPane().getWidth() - ctr.getWidth()) / 2,
                    (this.getDesktopPane().getHeight() - ctr.getHeight()) / 2
                );
                ctr.toFront();
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un tipo de registro primero.");
            }
        });



        btnVerPatrocinio.addActionListener(e -> {
            String codigoPat = (String) comboPatrocinios.getSelectedItem();
            String nombreEdicion = (String) comboEdiciones.getSelectedItem();

            if (codigoPat != null && nombreEdicion != null) {
                DTPatrocinio dtPat = controller.consultaPatrocinio(nombreEdicion, codigoPat);
                ConsultaPatrocinio cp = ConsultaPatrocinio.crearYMostrar(controller, dtPat, this.getDesktopPane());
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un patrocinio primero.");
            }
        });

    }

    private void mostrarDatosEdicion(DTEdicion dtEd) {
        if (dtEd == null) return;

        NombreEdicion.setText(dtEd.getNombre());
        SiglaEdicion.setText(dtEd.getSigla());
        CiudadEdicion.setText(dtEd.getCiudad());
        PaisEdicion.setText(dtEd.getPais());
        FAltaEdicion.setText(dtEd.getfAlta().toString());
        FInicioEdicion.setText(dtEd.getfInicio().toString());
        FFinEdicion.setText(dtEd.getfFin().toString());

        comboTiposRegistro.removeAllItems();
        for (DTTipoRegistro tr : dtEd.getTipoRegistros()) {
            comboTiposRegistro.addItem(tr.getNombre());
        }

        comboRegistros.removeAllItems();
        for (DTRegistro reg : dtEd.getRegistros()) {
            comboRegistros.addItem(reg.getAsistenteNickname());
        }

        comboPatrocinios.removeAllItems();
        for (DTPatrocinio pat : dtEd.getPatrocinios()) {
            comboPatrocinios.addItem(pat.getCodigo());
        }

        comboOrganizadores.removeAllItems();
        for (DTOrganizador org : dtEd.getOrganizadores()) {
            comboOrganizadores.addItem(org.getNickname());
        }
    }

    public void cargarEventos() {
        comboEventos.removeAllItems();
        for (Evento ev : controller.listarEventos()) {
            comboEventos.addItem(ev.getNombre());
        }
        if (comboEventos.getItemCount() > 0) {
            comboEventos.setSelectedIndex(0);
            cargarEdiciones((String) comboEventos.getSelectedItem());
        }
    }

    private void cargarEdiciones(String nombreEvento) {
        comboEdiciones.removeAllItems();
        for (String nombreEd : controller.listarEdicionesDeEvento(nombreEvento)) {
            comboEdiciones.addItem(nombreEd);
        }
        if (comboEdiciones.getItemCount() > 0) {
            comboEdiciones.setSelectedIndex(0);
            DTEdicion dtEd = controller.consultaEdicionDeEvento(nombreEvento, (String) comboEdiciones.getSelectedItem());
            mostrarDatosEdicion(dtEd);
        }
    }

    public static ConsultaEdicionEvento crearYMostrar(IControllerEvento controller, String nombreEvento, String nombreEdicion, JDesktopPane desktopPane) {
        ConsultaEdicionEvento cee = new ConsultaEdicionEvento(controller);
        if (desktopPane != null) {
            desktopPane.add(cee);
            cee.setVisible(true);
            cee.setLocation(
                (desktopPane.getWidth() - cee.getWidth()) / 2,
                (desktopPane.getHeight() - cee.getHeight()) / 2
            );
        }

        cee.cargarEventos();

        if (nombreEvento != null) {
            cee.comboEventos.setSelectedItem(nombreEvento);
            cee.cargarEdiciones(nombreEvento);
            if (nombreEdicion != null) {
                cee.comboEdiciones.setSelectedItem(nombreEdicion);
            }
        }

        return cee;
    }
}
