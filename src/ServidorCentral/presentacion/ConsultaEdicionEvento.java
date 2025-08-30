package ServidorCentral.presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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

    public ConsultaEdicionEvento(IControllerEvento ice) {
        this.controller = ice;
        getContentPane().setLayout(null);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        // Labels
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

        // Labels campos
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblNombre.setBounds(28, 153, 69, 20);
        getContentPane().add(lblNombre);

        JLabel lblSigla = new JLabel("Sigla:");
        lblSigla.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblSigla.setBounds(306, 153, 45, 20);
        getContentPane().add(lblSigla);

        JLabel lblCiudad = new JLabel("Ciudad:");
        lblCiudad.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblCiudad.setBounds(28, 202, 69, 20);
        getContentPane().add(lblCiudad);

        JLabel lblPais = new JLabel("Pais:");
        lblPais.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblPais.setBounds(306, 202, 45, 20);
        getContentPane().add(lblPais);

        JLabel lblFAlta = new JLabel("Fecha de Alta:");
        lblFAlta.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblFAlta.setBounds(28, 255, 101, 20);
        getContentPane().add(lblFAlta);

        JLabel lblFInicio = new JLabel("Fecha de Inicio:");
        lblFInicio.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblFInicio.setBounds(280, 255, 107, 20);
        getContentPane().add(lblFInicio);

        JLabel lblFFin = new JLabel("Fecha de Fin:");
        lblFFin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblFFin.setBounds(28, 312, 101, 20);
        getContentPane().add(lblFFin);

        JLabel lblOrganizador = new JLabel("Organizador:");
        lblOrganizador.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblOrganizador.setBounds(280, 312, 101, 20);
        getContentPane().add(lblOrganizador);

        JLabel lblTipoRegistro = new JLabel("Tipos de Registro:");
        lblTipoRegistro.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblTipoRegistro.setBounds(28, 350, 120, 20);
        getContentPane().add(lblTipoRegistro);

        JLabel lblRegistros = new JLabel("Registros:");
        lblRegistros.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblRegistros.setBounds(297, 350, 101, 20);
        getContentPane().add(lblRegistros);

        JLabel lblPatrocinios = new JLabel("Patrocinios:");
        lblPatrocinios.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblPatrocinios.setBounds(28, 393, 86, 20);
        getContentPane().add(lblPatrocinios);

        // ComboBoxes
        comboEventos = new JComboBox<>();
        comboEventos.setBounds(171, 21, 219, 28);
        getContentPane().add(comboEventos);

        comboEdiciones = new JComboBox<>();
        comboEdiciones.setBounds(171, 60, 219, 28);
        getContentPane().add(comboEdiciones);

        for (Evento ev : controller.listarEventos()) {
            comboEventos.addItem(ev.getNombre());
        }

        // Listener evento -> cargar ediciones
        comboEventos.addActionListener(e -> {
            String nombreEvento = (String) comboEventos.getSelectedItem();
            comboEdiciones.removeAllItems();
            for (String nombreEd : controller.listarEdicionesDeEvento(nombreEvento)) {
                comboEdiciones.addItem(nombreEd);
            }
        });

        // Listener edición -> mostrar datos
        comboEdiciones.addActionListener(e -> {
            String nombreEvento = (String) comboEventos.getSelectedItem();
            String nombreEdicion = (String) comboEdiciones.getSelectedItem();
            if (nombreEdicion != null) {
                DTEdicion dtEd = controller.consultaEdicionDeEvento(nombreEvento, nombreEdicion);
                mostrarDatosEdicion(dtEd);
            }
        });

        // Campos de texto
        NombreEdicion = new JTextField(); NombreEdicion.setEditable(false); NombreEdicion.setBounds(98, 151, 144, 28); getContentPane().add(NombreEdicion);
        CiudadEdicion = new JTextField(); CiudadEdicion.setEditable(false); CiudadEdicion.setBounds(109, 200, 144, 28); getContentPane().add(CiudadEdicion);
        SiglaEdicion = new JTextField(); SiglaEdicion.setEditable(false); SiglaEdicion.setBounds(385, 145, 86, 28); getContentPane().add(SiglaEdicion);
        PaisEdicion = new JTextField(); PaisEdicion.setEditable(false); PaisEdicion.setBounds(385, 200, 144, 28); getContentPane().add(PaisEdicion);
        FAltaEdicion = new JTextField(); FAltaEdicion.setEditable(false); FAltaEdicion.setBounds(123, 251, 130, 28); getContentPane().add(FAltaEdicion);
        FInicioEdicion = new JTextField(); FInicioEdicion.setEditable(false); FInicioEdicion.setBounds(385, 251, 120, 28); getContentPane().add(FInicioEdicion);
        FFinEdicion = new JTextField(); FFinEdicion.setEditable(false); FFinEdicion.setBounds(123, 304, 130, 28); getContentPane().add(FFinEdicion);

        // ComboBoxes Edición
        comboTiposRegistro = new JComboBox<>(); comboTiposRegistro.setBounds(150, 347, 137, 28); getContentPane().add(comboTiposRegistro);
        comboRegistros = new JComboBox<>(); comboRegistros.setBounds(373, 347, 161, 28); getContentPane().add(comboRegistros);
        comboPatrocinios = new JComboBox<>(); comboPatrocinios.setBounds(123, 390, 164, 28); getContentPane().add(comboPatrocinios);
        comboOrganizadores = new JComboBox<>(); comboOrganizadores.setBounds(373, 309, 130, 28); getContentPane().add(comboOrganizadores);
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

        // Limpiar listeners antiguos antes de agregar nuevos
        for (ActionListener al : comboTiposRegistro.getActionListeners()) comboTiposRegistro.removeActionListener(al);
        for (ActionListener al : comboPatrocinios.getActionListeners()) comboPatrocinios.removeActionListener(al);

        // Tipos de registro
        comboTiposRegistro.removeAllItems();
        for (DTTipoRegistro tr : dtEd.getTipoRegistros()) {
            comboTiposRegistro.addItem(tr.getNombre());
        }
        comboTiposRegistro.addActionListener(e -> {
            String nombreTipo = (String) comboTiposRegistro.getSelectedItem();
            String nombreEdicion = (String) comboEdiciones.getSelectedItem();
            String nombreEvento = (String) comboEventos.getSelectedItem();

            if (nombreTipo != null && nombreEdicion != null) {
                ConsultaTipoRegistro crear = ConsultaTipoRegistro.crearYMostrar(controller, nombreEdicion, nombreTipo);
            }
        });
        // Registros
        comboRegistros.removeAllItems();
        for (DTRegistro reg : dtEd.getRegistros()) {
            comboRegistros.addItem(reg.getAsistenteNickname());
        }

        // Patrocinios
        comboPatrocinios.removeAllItems();
        for (DTPatrocinio pat : dtEd.getPatrocinios()) {
            comboPatrocinios.addItem(pat.getCodigo());
        }
        comboPatrocinios.addActionListener(e -> {
            String nombreEdicion = (String) comboEdiciones.getSelectedItem();
            String codigoPat = (String) comboPatrocinios.getSelectedItem();
            if (nombreEdicion != null && codigoPat != null) {
                DTPatrocinio dtPat = controller.consultaPatrocinio(nombreEdicion, codigoPat);
                ConsultaPatrocinio cp = new ConsultaPatrocinio(controller);
                cp.cargarDatos(dtPat);
                cp.setVisible(true);
            }
        });


        // Organizadores
        comboOrganizadores.removeAllItems();
        for (DTOrganizador org : dtEd.getOrganizadores()) {
            comboOrganizadores.addItem(org.getNickname());
        }
    }
       
    public static ConsultaEdicionEvento crearYMostrar(IControllerEvento controller, String nombreEvento, String nombreEdicion,JDesktopPane desktopPane) {
    	ConsultaEdicionEvento ctr = new ConsultaEdicionEvento(controller); // constructor normal
    	DTEdicion dtEd = controller.consultaEdicionDeEvento(nombreEvento, nombreEdicion);
    	ctr.comboEventos.setSelectedItem(nombreEvento);
    	ctr.comboEventos.setSelectedItem(nombreEdicion);
	    ctr.mostrarDatosEdicion(dtEd);
	    desktopPane.add(ctr);
	    ctr.setSize(600, 500);
	    ctr.setVisible(true);
	    return ctr;
	}
    
    public void cargarDatos(DTEdicion dtTipo) {
	    if (dtTipo == null) return;
	    mostrarDatosEdicion(dtTipo);
	}

	public static ConsultaEdicionEvento crearYMostrar(IControllerEvento controller, String nombreEvento, String nombreEdicion) {
		ConsultaEdicionEvento cee = new ConsultaEdicionEvento(controller); // constructor normal
	    DTEdicion dtTipo = controller.consultaEdicionDeEvento(nombreEvento, nombreEdicion);
	    cee.cargarDatos(dtTipo);
	    cee.setVisible(true);
	    return cee;
	}
}
