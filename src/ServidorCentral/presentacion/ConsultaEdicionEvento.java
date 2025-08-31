package ServidorCentral.presentacion;

import javax.swing.*;
import java.awt.*;

import ServidorCentral.logica.*;

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
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		int row = 0;

		// Título
		JLabel lblTitulo = new JLabel("Datos de la Edición");
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		panel.add(lblTitulo, gbc);
		row++;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.WEST;

		// Selección de Evento
		gbc.gridx = 0;
		gbc.gridy = row;
		panel.add(new JLabel("Seleccionar Evento"), gbc);
		comboEventos = new JComboBox<>();
		gbc.gridx = 1;
		gbc.gridy = row;
		gbc.gridwidth = 3;
		panel.add(comboEventos, gbc);
		row++;
		gbc.gridwidth = 1;

		gbc.gridx = 0;
		gbc.gridy = row;
		panel.add(new JLabel("Seleccionar Edición"), gbc);
		comboEdiciones = new JComboBox<>();
		gbc.gridx = 1;
		gbc.gridy = row;
		gbc.gridwidth = 3;
		panel.add(comboEdiciones, gbc);
		row++;
		gbc.gridwidth = 1;

		gbc.gridx = 0;
		gbc.gridy = row;
		panel.add(new JLabel("Nombre :"), gbc);
		NombreEdicion = new JTextField();
		NombreEdicion.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = row;
		panel.add(NombreEdicion, gbc);

		gbc.gridx = 2;
		gbc.gridy = row;
		panel.add(new JLabel("Sigla :"), gbc);
		SiglaEdicion = new JTextField();
		SiglaEdicion.setEditable(false);
		gbc.gridx = 3;
		gbc.gridy = row;
		panel.add(SiglaEdicion, gbc);
		row++;

		gbc.gridx = 0;
		gbc.gridy = row;
		panel.add(new JLabel("Ciudad :"), gbc);
		CiudadEdicion = new JTextField();
		CiudadEdicion.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = row;
		panel.add(CiudadEdicion, gbc);

		gbc.gridx = 2;
		gbc.gridy = row;
		panel.add(new JLabel("Pais :"), gbc);
		PaisEdicion = new JTextField();
		PaisEdicion.setEditable(false);
		gbc.gridx = 3;
		gbc.gridy = row;
		panel.add(PaisEdicion, gbc);
		row++;

		gbc.gridx = 0;
		gbc.gridy = row;
		panel.add(new JLabel("Fecha de Alta :"), gbc);
		FAltaEdicion = new JTextField();
		FAltaEdicion.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = row;
		panel.add(FAltaEdicion, gbc);

		gbc.gridx = 2;
		gbc.gridy = row;
		panel.add(new JLabel("Fecha de Inicio :"), gbc);
		FInicioEdicion = new JTextField();
		FInicioEdicion.setEditable(false);
		gbc.gridx = 3;
		gbc.gridy = row;
		panel.add(FInicioEdicion, gbc);
		row++;

		gbc.gridx = 0;
		gbc.gridy = row;
		panel.add(new JLabel("Fecha de Finalización :"), gbc);
		FFinEdicion = new JTextField();
		FFinEdicion.setEditable(false);
		gbc.gridx = 1;
		gbc.gridy = row;
		panel.add(FFinEdicion, gbc);
		row++;

		gbc.gridx = 0;
		gbc.gridy = row;
		panel.add(new JLabel("Tipos de Registro :"), gbc);
		comboTiposRegistro = new JComboBox<>();
		gbc.gridx = 1;
		gbc.gridy = row;
		panel.add(comboTiposRegistro, gbc);

		gbc.gridx = 2;
		gbc.gridy = row;
		panel.add(new JLabel("Registros :"), gbc);
		comboRegistros = new JComboBox<>();
		gbc.gridx = 3;
		gbc.gridy = row;
		panel.add(comboRegistros, gbc);
		row++;

		gbc.gridx = 0;
		gbc.gridy = row;
		panel.add(new JLabel("Patrocinios :"), gbc);
		comboPatrocinios = new JComboBox<>();
		gbc.gridx = 1;
		gbc.gridy = row;
		panel.add(comboPatrocinios, gbc);

		gbc.gridx = 2;
		gbc.gridy = row;
		panel.add(new JLabel("Organizadores :"), gbc);
		comboOrganizadores = new JComboBox<>();
		gbc.gridx = 3;
		gbc.gridy = row;
		panel.add(comboOrganizadores, gbc);
		row++;

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		JButton btnVerTipoRegistro = new JButton("Ver Tipo de Registro");
		JButton btnVerPatrocinio = new JButton("Ver Patrocinio");
		panelBotones.add(btnVerTipoRegistro);
		panelBotones.add(btnVerPatrocinio);

		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.gridwidth = 4;
		panel.add(panelBotones, gbc);
		row++;
		gbc.gridwidth = 1;

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
