package ServidorCentral.presentacion;

import javax.swing.*;
import java.util.List;
import ServidorCentral.logica.Patrocinio;
import ServidorCentral.logica.DTPatrocinio;
import ServidorCentral.logica.Edicion;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.IControllerEvento;
import net.miginfocom.swing.MigLayout;

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
		getContentPane().setLayout(new MigLayout("", "[100px][10px][10px][235px]", "[25px][25px][25px][25px][25px][25px][25px][25px][25px][25px]"));

		// --- Combos ---
		JLabel lblEvento = new JLabel("Evento:");
		getContentPane().add(lblEvento, "cell 0 0,grow");

		comboEventos = new JComboBox<>();
		comboEventos.setEnabled(false); // Fijo
		getContentPane().add(comboEventos, "cell 2 0 2 1,grow");

		JLabel lblEdicion = new JLabel("Edición:");
		getContentPane().add(lblEdicion, "cell 0 1,grow");

		comboEdiciones = new JComboBox<>();
		comboEdiciones.setEnabled(false); // Fijo
		getContentPane().add(comboEdiciones, "cell 2 1 2 1,grow");

		JLabel lblPatrocinio = new JLabel("Patrocinio:");
		getContentPane().add(lblPatrocinio, "cell 0 2,grow");

		comboPatrocinios = new JComboBox<>();
		comboPatrocinios.setEnabled(false); // Fijo
		getContentPane().add(comboPatrocinios, "cell 2 2 2 1,grow");

		comboEventos.addActionListener(e -> {
			String nombreEvento = (String) comboEventos.getSelectedItem();
			if (nombreEvento != null && !nombreEvento.equals("Sin eventos")) {
				comboEdiciones.setEnabled(true);
				try {
					cargarEdiciones(nombreEvento);
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

		// --- Campos de Patrocinio ---
		int y = 150;
		int labelWidth = 120;
		int tfWidth = 200;
		int height = 25;

		JLabel lblCodigo = new JLabel("Código:");
		getContentPane().add(lblCodigo, "cell 0 3 3 1,grow");

		tfCodigo = new JTextField();
		tfCodigo.setEditable(false);
		getContentPane().add(tfCodigo, "cell 3 3,grow");

		JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
		getContentPane().add(lblFechaInicio, "cell 0 4 3 1,grow");

		tfFechaInicio = new JTextField();
		tfFechaInicio.setEditable(false);
		getContentPane().add(tfFechaInicio, "cell 3 4,grow");

		JLabel lblRegistroGratuito = new JLabel("Registros Gratuitos:");
		getContentPane().add(lblRegistroGratuito, "cell 0 5 3 1,grow");

		tfRegistroGratuito = new JTextField();
		tfRegistroGratuito.setEditable(false);
		getContentPane().add(tfRegistroGratuito, "cell 3 5,grow");

		JLabel lblMonto = new JLabel("Monto:");
		getContentPane().add(lblMonto, "cell 0 6 3 1,grow");

		tfMonto = new JTextField();
		tfMonto.setEditable(false);
		getContentPane().add(tfMonto, "cell 3 6,grow");

		JLabel lblNivel = new JLabel("Nivel:");
		getContentPane().add(lblNivel, "cell 0 7 3 1,grow");

		tfNivel = new JTextField();
		tfNivel.setEditable(false);
		getContentPane().add(tfNivel, "cell 3 7,grow");

		JLabel lblInstitucion = new JLabel("Institución:");
		getContentPane().add(lblInstitucion, "cell 0 8 3 1,grow");

		tfInstitucion = new JTextField();
		tfInstitucion.setEditable(false);
		getContentPane().add(tfInstitucion, "cell 3 8,grow");

		JLabel lblTipoRegistro = new JLabel("Tipo Registro:");
		getContentPane().add(lblTipoRegistro, "cell 0 9 3 1,grow");

		tfTipoRegistro = new JTextField();
		tfTipoRegistro.setEditable(false);
		getContentPane().add(tfTipoRegistro, "cell 3 9,grow");
	}

	// Carga los datos en los campos y combos, combos quedan fijos
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
			for (Evento e : eventos) {
				nombres.add(e.getNombre());
			}

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
				for (Edicion ed : ediciones) {
					nombresEdiciones.add(ed.getNombre());
				}

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
				for (Patrocinio p : patrocinios) {
					codigosPatrocinios.add(p.getCodigo());
				}

				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
						codigosPatrocinios.toArray(new String[0]));
				comboPatrocinios.setModel(model);
			}
		}
	}
}
