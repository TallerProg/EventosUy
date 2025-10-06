package src.ServidorCentral.presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import src.ServidorCentral.logica.Patrocinio;
import src.ServidorCentral.logica.DTPatrocinio;
import src.ServidorCentral.logica.Edicion;
import src.ServidorCentral.logica.Evento;
import src.ServidorCentral.logica.IControllerEvento;

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
		setSize(600, 400);
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);

		initComponents();
	}

	private void initComponents() {

		JPanel panel = new JPanel(new GridBagLayout());
		getContentPane().add(panel, BorderLayout.CENTER);
		GridBagConstraints gbcPanel = new GridBagConstraints();
		gbcPanel.insets = new Insets(5, 5, 5, 5);
		gbcPanel.fill = GridBagConstraints.HORIZONTAL;

		int row = 0;

		JLabel lblEvento = new JLabel("Evento:");
		GridBagConstraints gbcEvento = new GridBagConstraints();
		gbcEvento.gridx = 0;
		gbcEvento.gridy = row;
		panel.add(lblEvento, gbcEvento);

		comboEventos = new JComboBox<>();
		GridBagConstraints gbcComboEventos = new GridBagConstraints();
		comboEventos.setEnabled(false); 
		gbcComboEventos.gridx = 1;
		gbcComboEventos.gridy = row;
		gbcComboEventos.gridwidth = 2;
		panel.add(comboEventos, gbcComboEventos);
		row++;
		gbcComboEventos.gridwidth = 1;

		JLabel lblEdicion = new JLabel("Edición:");
		GridBagConstraints gbcEdicion = new GridBagConstraints();
		gbcEdicion.gridx = 0;
		gbcEdicion.gridy = row;
		panel.add(lblEdicion, gbcEdicion);

		comboEdiciones = new JComboBox<>();
		GridBagConstraints gbccomboEdicion = new GridBagConstraints();
		comboEdiciones.setEnabled(false); 
		gbccomboEdicion.gridx = 1;
		gbccomboEdicion.gridy = row;
		gbccomboEdicion.gridwidth = 2;
		panel.add(comboEdiciones, gbccomboEdicion);
		row++;
		gbccomboEdicion.gridwidth = 1;

		JLabel lblPatrocinio = new JLabel("Patrocinio:");
		GridBagConstraints gbcPatrocinio = new GridBagConstraints();
		gbcPatrocinio.gridx = 0;
		gbcPatrocinio.gridy = row;
		panel.add(lblPatrocinio, gbcPatrocinio);

		comboPatrocinios = new JComboBox<>();
		GridBagConstraints gbcComboPatrocinio = new GridBagConstraints();
		comboPatrocinios.setEnabled(false); 
		gbcComboPatrocinio.gridx = 1;
		gbcComboPatrocinio.gridy = row;
		gbcComboPatrocinio.gridwidth = 2;
		panel.add(comboPatrocinios, gbcComboPatrocinio);
		row++;
		gbcComboPatrocinio.gridwidth = 1;

		comboEventos.addActionListener(e -> {
			String nombreEvento = (String) comboEventos.getSelectedItem();
			if (nombreEvento != null && !nombreEvento.equals("Sin eventos")) {
				comboEdiciones.setEnabled(true);
				try {
					comboEdiciones.removeAllItems();
					cargarEdiciones(nombreEvento);
					comboPatrocinios.removeAllItems();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Error al cargar ediciones: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		comboEdiciones.addActionListener(e -> {
			String nombreEdicion = (String) comboEdiciones.getSelectedItem();
			if (nombreEdicion != null && !nombreEdicion.equals("Sin ediciones")) {
				comboPatrocinios.setEnabled(true);
				try {
					comboPatrocinios.removeAllItems();
					cargarPatrocinios(nombreEdicion);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Error al cargar patrocinios: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		comboPatrocinios.addActionListener(e -> {
			String codigoPatrocinioString = (String) comboPatrocinios.getSelectedItem();
			String edicion = (String) comboEdiciones.getSelectedItem();
			if (codigoPatrocinioString != null && !codigoPatrocinioString.equals("Sin patrocinios")) {
				try {
					DTPatrocinio patrocinio = controller.consultaPatrocinio(edicion, codigoPatrocinioString);
					if (patrocinio != null) {
						cargarDatos(patrocinio);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "Error al cargar datos del patrocinio: " + ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		row++; 
		String[] labels = { "Código:", "Fecha Inicio:", "Registros Gratuitos:", "Monto:", "Nivel:", "Institución:",
		        "Tipo Registro:" };
		JTextField[] fields = new JTextField[7];

		tfCodigo = new JTextField();
		tfCodigo.setEditable(false);
		fields[0] = tfCodigo;

		tfFechaInicio = new JTextField();
		tfFechaInicio.setEditable(false);
		fields[1] = tfFechaInicio;

		tfRegistroGratuito = new JTextField();
		tfRegistroGratuito.setEditable(false);
		fields[2] = tfRegistroGratuito;

		tfMonto = new JTextField();
		tfMonto.setEditable(false);
		fields[3] = tfMonto;

		tfNivel = new JTextField();
		tfNivel.setEditable(false);
		fields[4] = tfNivel;

		tfInstitucion = new JTextField();
		tfInstitucion.setEditable(false);
		fields[5] = tfInstitucion;

		tfTipoRegistro = new JTextField();
		tfTipoRegistro.setEditable(false);
		fields[6] = tfTipoRegistro;

		for (int i = 0; i < labels.length; i++) {
		    GridBagConstraints gbcLabel = new GridBagConstraints();
		    gbcLabel.gridx = 0;
		    gbcLabel.gridy = row;
		    gbcLabel.anchor = GridBagConstraints.WEST;
		    gbcLabel.insets = new Insets(5, 5, 5, 5);
		    panel.add(new JLabel(labels[i]), gbcLabel);

		    GridBagConstraints gbcField = new GridBagConstraints();
		    gbcField.gridx = 1;
		    gbcField.gridy = row;
		    gbcField.gridwidth = 2;
		    gbcField.fill = GridBagConstraints.HORIZONTAL;
		    gbcField.weightx = 1.0; 
		    gbcField.insets = new Insets(5, 5, 5, 5);
		    panel.add(fields[i], gbcField);

		    row++;
		}

	}

	public void cargarDatos(DTPatrocinio dtPat) {
		if (dtPat == null)
			return;

		tfCodigo.setText(dtPat.getCodigo());
		tfFechaInicio.setText(dtPat.getFInicio().toString());
		tfRegistroGratuito.setText(String.valueOf(dtPat.getRegistroGratuito()));
		tfMonto.setText(String.valueOf(dtPat.getMonto()));
		tfNivel.setText(String.valueOf(dtPat.getNivel()));
		tfInstitucion.setText(dtPat.getInstitucion());
		tfTipoRegistro.setText(dtPat.getTipoRegistro());
	}

	public static ConsultaPatrocinio crearYMostrar(IControllerEvento controller, DTPatrocinio dtPat,
			JDesktopPane desktopPane) {
		ConsultaPatrocinio cp = new ConsultaPatrocinio(controller);
		cp.cargarDatos(dtPat);
		if (desktopPane != null) {
			desktopPane.add(cp);
			cp.setVisible(true);
			cp.setLocation((desktopPane.getWidth() - cp.getWidth()) / 2,
					(desktopPane.getHeight() - cp.getHeight()) / 2);
		}
		return cp;
	}

	public void cargarEventos() {
		List<Evento> eventos = controller.listarEventos();
		if (eventos.isEmpty()) {
			comboEventos.addItem("Sin eventos");
			comboEventos.setEnabled(false);
		} else {
			comboEventos.setEnabled(true);
			List<String> nombres = new java.util.ArrayList<>();
			for (Evento e : eventos)
				nombres.add(e.getNombre());

			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
			comboEventos.setModel(model);
		}
	}

	public void cargarEdiciones(String nombreEvento) {
		Evento evento = controller.getEvento(nombreEvento);
		if (evento != null) {
			List<Edicion> ediciones = evento.getEdiciones();
			if (ediciones.isEmpty()) {
				comboEdiciones.removeAllItems();
				comboEdiciones.addItem("Sin ediciones");
				comboEdiciones.setEnabled(false);
			} else {
				List<String> nombresEdiciones = new java.util.ArrayList<>();
				for (Edicion ed : ediciones)
					nombresEdiciones.add(ed.getNombre());

				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
						nombresEdiciones.toArray(new String[0]));
				comboEdiciones.setModel(model);
			}
		}
	}

	public void cargarPatrocinios(String nombreEdicion) {
		Edicion edicion = controller.findEdicion(nombreEdicion);
		if (edicion != null) {
			List<Patrocinio> patrocinios = edicion.getPatrocinios();
			if (patrocinios.isEmpty()) {
				comboPatrocinios.removeAllItems();
				comboPatrocinios.addItem("Sin patrocinios");
				comboPatrocinios.setEnabled(false);
			} else {
				List<String> codigosPatrocinios = new java.util.ArrayList<>();
				for (Patrocinio p : patrocinios)
					codigosPatrocinios.add(p.getCodigo());

				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
						codigosPatrocinios.toArray(new String[0]));
				comboPatrocinios.setModel(model);
			}
		}
	}
}
