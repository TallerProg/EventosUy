package src.ServidorCentral.presentacion;

import javax.swing.*;
import java.awt.*;

import src.ServidorCentral.logica.*;

public class ConsultaEdicionEvento extends JInternalFrame {

	private static final long serialVersionUID = -2318682568078151267L;

	private IControllerEvento controller;

	private JTextField NombreEdicion, CiudadEdicion, SiglaEdicion, PaisEdicion, FAltaEdicion, FInicioEdicion,
			FFinEdicion;
	private JComboBox<String> comboEventos, comboEdiciones, comboTiposRegistro, comboRegistros, comboPatrocinios,
			comboOrganizadores;

	public ConsultaEdicionEvento(IControllerEvento ice) {
		super("Consulta de Edición de Evento", true, true, true, true);
		this.controller = ice;
		setSize(530, 450);

		initComponents();
		cargarEventos();
	}

	private void initComponents() {
	    JPanel panel = new JPanel(new GridBagLayout());
	    getContentPane().add(panel, BorderLayout.CENTER);

	    int row = 0;

	    JLabel lblTitulo = new JLabel("Datos de la Edición");
	    GridBagConstraints gbcTitulo = new GridBagConstraints();
	    gbcTitulo.gridx = 0;
	    gbcTitulo.gridy = row;
	    gbcTitulo.gridwidth = 4;
	    gbcTitulo.anchor = GridBagConstraints.CENTER;
	    gbcTitulo.insets = new Insets(5, 5, 5, 5);
	    panel.add(lblTitulo, gbcTitulo);
	    row++;

	    GridBagConstraints gbcLblEvento = new GridBagConstraints();
	    gbcLblEvento.gridx = 0;
	    gbcLblEvento.gridy = row;
	    gbcLblEvento.anchor = GridBagConstraints.WEST;
	    gbcLblEvento.insets = new Insets(5, 5, 5, 5);
	    panel.add(new JLabel("Seleccionar Evento"), gbcLblEvento);

	    comboEventos = new JComboBox<>();
	    GridBagConstraints gbcComboEvento = new GridBagConstraints();
	    gbcComboEvento.gridx = 1;
	    gbcComboEvento.gridy = row;
	    gbcComboEvento.gridwidth = 3;
	    gbcComboEvento.fill = GridBagConstraints.HORIZONTAL;
	    gbcComboEvento.insets = new Insets(5, 5, 5, 5);
	    panel.add(comboEventos, gbcComboEvento);
	    row++;

	    GridBagConstraints gbcLblEdicion = new GridBagConstraints();
	    gbcLblEdicion.gridx = 0;
	    gbcLblEdicion.gridy = row;
	    gbcLblEdicion.anchor = GridBagConstraints.WEST;
	    gbcLblEdicion.insets = new Insets(5, 5, 5, 5);
	    panel.add(new JLabel("Seleccionar Edición"), gbcLblEdicion);

	    comboEdiciones = new JComboBox<>();
	    GridBagConstraints gbcComboEdicion = new GridBagConstraints();
	    gbcComboEdicion.gridx = 1;
	    gbcComboEdicion.gridy = row;
	    gbcComboEdicion.gridwidth = 3;
	    gbcComboEdicion.fill = GridBagConstraints.HORIZONTAL;
	    gbcComboEdicion.insets = new Insets(5, 5, 5, 5);
	    panel.add(comboEdiciones, gbcComboEdicion);
	    row++;

	    GridBagConstraints gbcLblNombre = new GridBagConstraints();
	    gbcLblNombre.gridx = 0;
	    gbcLblNombre.gridy = row;
	    gbcLblNombre.anchor = GridBagConstraints.WEST;
	    gbcLblNombre.insets = new Insets(5, 5, 5, 5);
	    panel.add(new JLabel("Nombre :"), gbcLblNombre);

	    NombreEdicion = new JTextField();
	    NombreEdicion.setEditable(false);
	    GridBagConstraints gbcNombre = new GridBagConstraints();
	    gbcNombre.gridx = 1;
	    gbcNombre.gridy = row;
	    gbcNombre.fill = GridBagConstraints.HORIZONTAL;
	    gbcNombre.insets = new Insets(5, 5, 5, 5);
	    panel.add(NombreEdicion, gbcNombre);

	    GridBagConstraints gbcLblSigla = new GridBagConstraints();
	    gbcLblSigla.gridx = 2;
	    gbcLblSigla.gridy = row;
	    gbcLblSigla.anchor = GridBagConstraints.WEST;
	    gbcLblSigla.insets = new Insets(5, 5, 5, 5);
	    panel.add(new JLabel("Sigla :"), gbcLblSigla);

	    SiglaEdicion = new JTextField();
	    SiglaEdicion.setEditable(false);
	    GridBagConstraints gbcSigla = new GridBagConstraints();
	    gbcSigla.gridx = 3;
	    gbcSigla.gridy = row;
	    gbcSigla.fill = GridBagConstraints.HORIZONTAL;
	    gbcSigla.insets = new Insets(5, 5, 5, 5);
	    panel.add(SiglaEdicion, gbcSigla);
	    row++;

	    GridBagConstraints gbcLblCiudad = new GridBagConstraints();
	    gbcLblCiudad.gridx = 0;
	    gbcLblCiudad.gridy = row;
	    gbcLblCiudad.anchor = GridBagConstraints.WEST;
	    gbcLblCiudad.insets = new Insets(5, 5, 5, 5);
	    panel.add(new JLabel("Ciudad :"), gbcLblCiudad);

	    CiudadEdicion = new JTextField();
	    CiudadEdicion.setEditable(false);
	    GridBagConstraints gbcCiudad = new GridBagConstraints();
	    gbcCiudad.gridx = 1;
	    gbcCiudad.gridy = row;
	    gbcCiudad.fill = GridBagConstraints.HORIZONTAL;
	    gbcCiudad.insets = new Insets(5, 5, 5, 5);
	    panel.add(CiudadEdicion, gbcCiudad);

	    GridBagConstraints gbcLblPais = new GridBagConstraints();
	    gbcLblPais.gridx = 2;
	    gbcLblPais.gridy = row;
	    gbcLblPais.anchor = GridBagConstraints.WEST;
	    gbcLblPais.insets = new Insets(5, 5, 5, 5);
	    panel.add(new JLabel("Pais :"), gbcLblPais);

	    PaisEdicion = new JTextField();
	    PaisEdicion.setEditable(false);
	    GridBagConstraints gbcPais = new GridBagConstraints();
	    gbcPais.gridx = 3;
	    gbcPais.gridy = row;
	    gbcPais.fill = GridBagConstraints.HORIZONTAL;
	    gbcPais.insets = new Insets(5, 5, 5, 5);
	    panel.add(PaisEdicion, gbcPais);
	    row++;

	    GridBagConstraints gbcLblAlta = new GridBagConstraints();
	    gbcLblAlta.gridx = 0;
	    gbcLblAlta.gridy = row;
	    gbcLblAlta.anchor = GridBagConstraints.WEST;
	    gbcLblAlta.insets = new Insets(5, 5, 5, 5);
	    panel.add(new JLabel("Fecha de Alta :"), gbcLblAlta);

	    FAltaEdicion = new JTextField();
	    FAltaEdicion.setEditable(false);
	    GridBagConstraints gbcAlta = new GridBagConstraints();
	    gbcAlta.gridx = 1;
	    gbcAlta.gridy = row;
	    gbcAlta.fill = GridBagConstraints.HORIZONTAL;
	    gbcAlta.insets = new Insets(5, 5, 5, 5);
	    panel.add(FAltaEdicion, gbcAlta);

	    GridBagConstraints gbcLblInicio = new GridBagConstraints();
	    gbcLblInicio.gridx = 2;
	    gbcLblInicio.gridy = row;
	    gbcLblInicio.anchor = GridBagConstraints.WEST;
	    gbcLblInicio.insets = new Insets(5, 5, 5, 5);
	    panel.add(new JLabel("Fecha de Inicio :"), gbcLblInicio);

	    FInicioEdicion = new JTextField();
	    FInicioEdicion.setEditable(false);
	    GridBagConstraints gbcInicio = new GridBagConstraints();
	    gbcInicio.gridx = 3;
	    gbcInicio.gridy = row;
	    gbcInicio.fill = GridBagConstraints.HORIZONTAL;
	    gbcInicio.insets = new Insets(5, 5, 5, 5);
	    panel.add(FInicioEdicion, gbcInicio);
	    row++;

	    GridBagConstraints gbcLblFin = new GridBagConstraints();
	    gbcLblFin.gridx = 0;
	    gbcLblFin.gridy = row;
	    gbcLblFin.anchor = GridBagConstraints.WEST;
	    gbcLblFin.insets = new Insets(5, 5, 5, 5);
	    panel.add(new JLabel("Fecha de Finalización :"), gbcLblFin);

	    FFinEdicion = new JTextField();
	    FFinEdicion.setEditable(false);
	    GridBagConstraints gbcFin = new GridBagConstraints();
	    gbcFin.gridx = 1;
	    gbcFin.gridy = row;
	    gbcFin.fill = GridBagConstraints.HORIZONTAL;
	    gbcFin.insets = new Insets(5, 5, 5, 5);
	    panel.add(FFinEdicion, gbcFin);
	    row++;

	    GridBagConstraints gbcLblTipo = new GridBagConstraints();
	    gbcLblTipo.gridx = 0;
	    gbcLblTipo.gridy = row;
	    gbcLblTipo.anchor = GridBagConstraints.WEST;
	    gbcLblTipo.insets = new Insets(5, 5, 5, 5);
	    panel.add(new JLabel("Tipos de Registro :"), gbcLblTipo);

	    comboTiposRegistro = new JComboBox<>();
	    GridBagConstraints gbcComboTipo = new GridBagConstraints();
	    gbcComboTipo.gridx = 1;
	    gbcComboTipo.gridy = row;
	    gbcComboTipo.fill = GridBagConstraints.HORIZONTAL;
	    gbcComboTipo.insets = new Insets(5, 5, 5, 5);
	    panel.add(comboTiposRegistro, gbcComboTipo);

	    GridBagConstraints gbcLblRegistro = new GridBagConstraints();
	    gbcLblRegistro.gridx = 2;
	    gbcLblRegistro.gridy = row;
	    gbcLblRegistro.anchor = GridBagConstraints.WEST;
	    gbcLblRegistro.insets = new Insets(5, 5, 5, 5);
	    panel.add(new JLabel("Registros :"), gbcLblRegistro);

	    comboRegistros = new JComboBox<>();
	    GridBagConstraints gbcComboRegistro = new GridBagConstraints();
	    gbcComboRegistro.gridx = 3;
	    gbcComboRegistro.gridy = row;
	    gbcComboRegistro.fill = GridBagConstraints.HORIZONTAL;
	    gbcComboRegistro.insets = new Insets(5, 5, 5, 5);
	    panel.add(comboRegistros, gbcComboRegistro);
	    row++;

	    GridBagConstraints gbcLblPatro = new GridBagConstraints();
	    gbcLblPatro.gridx = 0;
	    gbcLblPatro.gridy = row;
	    gbcLblPatro.anchor = GridBagConstraints.WEST;
	    gbcLblPatro.insets = new Insets(5, 5, 5, 5);
	    panel.add(new JLabel("Patrocinios :"), gbcLblPatro);

	    comboPatrocinios = new JComboBox<>();
	    GridBagConstraints gbcComboPatro = new GridBagConstraints();
	    gbcComboPatro.gridx = 1;
	    gbcComboPatro.gridy = row;
	    gbcComboPatro.fill = GridBagConstraints.HORIZONTAL;
	    gbcComboPatro.insets = new Insets(5, 5, 5, 5);
	    panel.add(comboPatrocinios, gbcComboPatro);

	    GridBagConstraints gbcLblOrg = new GridBagConstraints();
	    gbcLblOrg.gridx = 2;
	    gbcLblOrg.gridy = row;
	    gbcLblOrg.anchor = GridBagConstraints.WEST;
	    gbcLblOrg.insets = new Insets(5, 5, 5, 5);
	    panel.add(new JLabel("Organizadores :"), gbcLblOrg);

	    comboOrganizadores = new JComboBox<>();
	    GridBagConstraints gbcComboOrg = new GridBagConstraints();
	    gbcComboOrg.gridx = 3;
	    gbcComboOrg.gridy = row;
	    gbcComboOrg.fill = GridBagConstraints.HORIZONTAL;
	    gbcComboOrg.insets = new Insets(5, 5, 5, 5);
	    panel.add(comboOrganizadores, gbcComboOrg);
	    row++;

	    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
	    JButton btnVerTipoRegistro = new JButton("Ver Tipo de Registro");
	    JButton btnVerPatrocinio = new JButton("Ver Patrocinio");
	    panelBotones.add(btnVerTipoRegistro);
	    panelBotones.add(btnVerPatrocinio);

	    GridBagConstraints gbcPanelBotones = new GridBagConstraints();
	    gbcPanelBotones.gridx = 0;
	    gbcPanelBotones.gridy = row;
	    gbcPanelBotones.gridwidth = 4;
	    gbcPanelBotones.insets = new Insets(10, 5, 5, 5);
	    panel.add(panelBotones, gbcPanelBotones);
	    row++;

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
				ctr.setLocation((this.getDesktopPane().getWidth() - ctr.getWidth()) / 2,
						(this.getDesktopPane().getHeight() - ctr.getHeight()) / 2);
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
		if (dtEd == null)
			return;

		NombreEdicion.setText(dtEd.getNombre());
		SiglaEdicion.setText(dtEd.getSigla());
		CiudadEdicion.setText(dtEd.getCiudad());
		PaisEdicion.setText(dtEd.getPais());
		FAltaEdicion.setText(dtEd.getfAlta().toString());
		FInicioEdicion.setText(dtEd.getfInicio().toString());
		FFinEdicion.setText(dtEd.getfFin().toString());

		comboTiposRegistro.removeAllItems();
		for (DTTipoRegistro tr : dtEd.getTipoRegistros())
			comboTiposRegistro.addItem(tr.getNombre());

		comboRegistros.removeAllItems();
		for (DTRegistro reg : dtEd.getRegistros())
			comboRegistros.addItem(reg.getAsistenteNickname());

		comboPatrocinios.removeAllItems();
		for (DTPatrocinio pat : dtEd.getPatrocinios())
			comboPatrocinios.addItem(pat.getCodigo());

		comboOrganizadores.removeAllItems();
		for (DTOrganizador org : dtEd.getOrganizadores())
			comboOrganizadores.addItem(org.getNickname());
	}

	public void cargarEventos() {
		comboEventos.removeAllItems();
		for (Evento ev : controller.listarEventos())
			comboEventos.addItem(ev.getNombre());
		if (comboEventos.getItemCount() > 0) {
			comboEventos.setSelectedIndex(0);
			cargarEdiciones((String) comboEventos.getSelectedItem());
		}
	}

	private void cargarEdiciones(String nombreEvento) {
		comboEdiciones.removeAllItems();
		for (String nombreEd : controller.listarEdicionesDeEvento(nombreEvento))
			comboEdiciones.addItem(nombreEd);
		if (comboEdiciones.getItemCount() > 0) {
			comboEdiciones.setSelectedIndex(0);
			DTEdicion dtEd = controller.consultaEdicionDeEvento(nombreEvento,
					(String) comboEdiciones.getSelectedItem());
			mostrarDatosEdicion(dtEd);
		}
	}

	public static ConsultaEdicionEvento crearYMostrar(IControllerEvento controller, String nombreEvento,
			String nombreEdicion, JDesktopPane desktopPane) {
		ConsultaEdicionEvento cee = new ConsultaEdicionEvento(controller);
		if (desktopPane != null) {
			desktopPane.add(cee);
			cee.setVisible(true);
			cee.setLocation((desktopPane.getWidth() - cee.getWidth()) / 2,
					(desktopPane.getHeight() - cee.getHeight()) / 2);
		}

		cee.cargarEventos();

		if (nombreEvento != null) {
			cee.comboEventos.setSelectedItem(nombreEvento);
			cee.cargarEdiciones(nombreEvento);
			if (nombreEdicion != null)
				cee.comboEdiciones.setSelectedItem(nombreEdicion);
		}

		return cee;
	}
}
