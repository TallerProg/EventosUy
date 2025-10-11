package ServidorCentral.presentacion;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import ServidorCentral.logica.DTTipoRegistro;
import ServidorCentral.logica.Edicion;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.TipoRegistro;

public class ConsultaTipoRegistro extends JInternalFrame {
	private JTextField textFieldNombre;
	private JTextArea textField1desc;
	private JTextField textField2costo;
	private JTextField textField3cupo;
	private JComboBox<String> comboBoxEvento;
	private JComboBox<String> comboBoxEdicion;
	private JComboBox<String> comboBoxTipoRegistro;
	private IControllerEvento controlEvento;

	private static final long serialVersionUID = 1L;

	public ConsultaTipoRegistro(IControllerEvento icu) {
		controlEvento = icu;
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Consultar tipo registro");
		setSize(700, 400);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelCombos = new JPanel();
		getContentPane().add(panelCombos, BorderLayout.NORTH);
		panelCombos.setLayout(new GridLayout(1, 3, 0, 0));

		JPanel panel = new JPanel();
		panelCombos.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblSelecioneUnEvento = new JLabel("Selecione un evento:");
		panel.add(lblSelecioneUnEvento, BorderLayout.NORTH);

		comboBoxEvento = new JComboBox<>();
		panel.add(comboBoxEvento, BorderLayout.SOUTH);

		JPanel panel_1 = new JPanel();
		panelCombos.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JLabel lblSelecioneUnaEdicin = new JLabel("Selecione una edición:");
		panel_1.add(lblSelecioneUnaEdicin, BorderLayout.NORTH);

		comboBoxEdicion = new JComboBox<>();
		comboBoxEdicion.setEnabled(false);
		panel_1.add(comboBoxEdicion, BorderLayout.SOUTH);

		JPanel panel_2 = new JPanel();
		panelCombos.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JLabel lblSelecioneElTipo = new JLabel("Selecione el tipo de registro:");
		panel_2.add(lblSelecioneElTipo, BorderLayout.NORTH);

		comboBoxTipoRegistro = new JComboBox<>();
		comboBoxTipoRegistro.setEnabled(false);
		panel_2.add(comboBoxTipoRegistro, BorderLayout.SOUTH);

		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3, BorderLayout.CENTER);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 71, 579 };
		gbl_panel_3.rowHeights = new int[] { 50, 50, 50, 50 };
		gbl_panel_3.columnWeights = new double[] { 0.3, 1.0 };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };

		panel_3.setLayout(gbl_panel_3);

		JLabel lblNewLabel = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_3.add(lblNewLabel, gbc_lblNewLabel);

		textFieldNombre = new JTextField();
		textFieldNombre.setEditable(false);
		GridBagConstraints gbc_textField_nombre = new GridBagConstraints();
		gbc_textField_nombre.insets = new Insets(0, 0, 5, 0);
		gbc_textField_nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_nombre.gridx = 1;
		gbc_textField_nombre.gridy = 0;
		panel_3.add(textFieldNombre, gbc_textField_nombre);
		textFieldNombre.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Descripción:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel_3.add(lblNewLabel_1, gbc_lblNewLabel_1);
		textField1desc = new JTextArea(3, 30);
		textField1desc.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		textField1desc.setEditable(false);
		textField1desc.setLineWrap(true);
		textField1desc.setWrapStyleWord(true);
		JScrollPane scrollDescripcion = new JScrollPane(textField1desc);
		GridBagConstraints gbc_textField_1_desc = new GridBagConstraints();
		gbc_textField_1_desc.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1_desc.fill = GridBagConstraints.BOTH;
		gbc_textField_1_desc.gridx = 1;
		gbc_textField_1_desc.gridy = 1;

		panel_3.add(scrollDescripcion, gbc_textField_1_desc);

		JLabel lblNewLabel_2 = new JLabel("Costo:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel_3.add(lblNewLabel_2, gbc_lblNewLabel_2);

		textField2costo = new JTextField();
		textField2costo.setEditable(false);
		GridBagConstraints gbc_textField_2_costo = new GridBagConstraints();
		gbc_textField_2_costo.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2_costo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2_costo.gridx = 1;
		gbc_textField_2_costo.gridy = 2;
		panel_3.add(textField2costo, gbc_textField_2_costo);
		textField2costo.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Cupo:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		panel_3.add(lblNewLabel_3, gbc_lblNewLabel_3);

		textField3cupo = new JTextField();
		textField3cupo.setEditable(false);
		GridBagConstraints gbc_textField_3_cupo = new GridBagConstraints();
		gbc_textField_3_cupo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3_cupo.gridx = 1;
		gbc_textField_3_cupo.gridy = 3;
		panel_3.add(textField3cupo, gbc_textField_3_cupo);
		textField3cupo.setColumns(10);

		comboBoxEvento.addActionListener(e -> {
			String nombreEventoSeleccionado = (String) comboBoxEvento.getSelectedItem();
			if (nombreEventoSeleccionado != null && !nombreEventoSeleccionado.equals("Sin eventos")) {
				comboBoxEdicion.setEnabled(true);
				try {
					cargarEdiciones(nombreEventoSeleccionado);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				comboBoxTipoRegistro.setEnabled(false);
				comboBoxTipoRegistro.removeAllItems();
				limpiarCamposTexto();
			} else {
				comboBoxEdicion.setEnabled(false);
				comboBoxEdicion.removeAllItems();
				comboBoxTipoRegistro.setEnabled(false);
				comboBoxTipoRegistro.removeAllItems();
			}
		});

		comboBoxEdicion.addActionListener(e -> {
			String nombreEdicionSeleccionado = (String) comboBoxEdicion.getSelectedItem();
			limpiarCamposTexto();
			if (nombreEdicionSeleccionado != null) {
				comboBoxTipoRegistro.setEnabled(true);
				try {
					cargarTipoRegistros(nombreEdicionSeleccionado);
					String nombreTipoRegistroSeleccionado = (String) comboBoxTipoRegistro.getSelectedItem();
					cargarDatos(nombreEdicionSeleccionado, nombreTipoRegistroSeleccionado);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			} else {
				comboBoxTipoRegistro.setEnabled(false);
				comboBoxTipoRegistro.removeAllItems();
			}
		});

		comboBoxTipoRegistro.addActionListener(e -> {
			limpiarCamposTexto();
			String nombreEdicionSeleccionado = (String) comboBoxEdicion.getSelectedItem();
			String nombreTipoRegistroSeleccionado = (String) comboBoxTipoRegistro.getSelectedItem();
			if (nombreEdicionSeleccionado != null && nombreTipoRegistroSeleccionado != null) {
				try {
					cargarDatos(nombreEdicionSeleccionado, nombreTipoRegistroSeleccionado);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
	}

	public void cargarEventos() {
		List<Evento> eventos = controlEvento.listarEventos();
		if (eventos.isEmpty()) {
			comboBoxEvento.removeAllItems();
			comboBoxEvento.addItem("Sin eventos");
			comboBoxEvento.setEnabled(false);
		} else {
			comboBoxEvento.setEnabled(true);
			List<String> nombres = new java.util.ArrayList<>();
			for (Evento e : eventos) {
				nombres.add(e.getNombre());
			}

			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
			comboBoxEvento.setModel(model);
		}

	}

	public void cargarEdiciones(String nombreEvento) {
		Evento evento = controlEvento.getEvento(nombreEvento);
		if (evento != null) {
			List<Edicion> ediciones = evento.getEdiciones();
			if (ediciones.isEmpty()) {
				comboBoxEdicion.removeAllItems();
				comboBoxEdicion.addItem("Sin ediciones");
				comboBoxEdicion.setEnabled(false);
			} else {
				List<String> nombresEdiciones = new java.util.ArrayList<>();
				for (Edicion ed : ediciones) {
					nombresEdiciones.add(ed.getNombre());
				}

				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
						nombresEdiciones.toArray(new String[0]));
				comboBoxEdicion.setModel(model);
			}
		}
	}

	public void cargarTipoRegistros(String nombreEdicion) {
		Edicion edicion = controlEvento.findEdicion(nombreEdicion);
		if (edicion != null) {
			List<TipoRegistro> tipoR = edicion.getTipoRegistros();
			if (tipoR.isEmpty()) {
				comboBoxTipoRegistro.removeAllItems();
				comboBoxTipoRegistro.addItem("Sin Tipo Registros");
				comboBoxTipoRegistro.setEnabled(false);
			} else {
				List<String> nombreRegistros = new java.util.ArrayList<>();
				for (TipoRegistro tr : tipoR) {
					nombreRegistros.add(tr.getNombre());
				}

				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nombreRegistros.toArray(new String[0]));
				comboBoxTipoRegistro.setModel(model);
			}
		}
	}

	private void limpiarCamposTexto() {
		textFieldNombre.setText("");
		textField1desc.setText("");
		textField2costo.setText("");
		textField3cupo.setText("");
	}

	public void cargarDatos(String nombreEdicionSeleccionado, String nombreTipoRegistroSeleccionado ) {

		DTTipoRegistro dt = controlEvento.consultaTipoRegistro(nombreEdicionSeleccionado, nombreTipoRegistroSeleccionado);
		if (dt != null) {
		textFieldNombre.setText(dt.getNombre());
		textField1desc.setText(dt.getDescripcion());
		textField2costo.setText(String.valueOf(dt.getCosto()));
		textField3cupo.setText(String.valueOf(dt.getCupo()));
		}
	}

	public void cargarDatos(DTTipoRegistro dtTipo, String nombreEvento, String nombreEdicion) {
		if (dtTipo == null)
			return;

		textFieldNombre.setText(dtTipo.getNombre());
		textField1desc.setText(dtTipo.getDescripcion());
		textField2costo.setText(String.valueOf(dtTipo.getCosto()));
		textField3cupo.setText(String.valueOf(dtTipo.getCupo()));

		comboBoxEvento.removeAllItems();
		comboBoxEvento.addItem(nombreEvento);
		comboBoxEvento.setSelectedItem(nombreEvento);
		comboBoxEvento.setEnabled(false);

		comboBoxEdicion.removeAllItems();
		comboBoxEdicion.addItem(nombreEdicion);
		comboBoxEdicion.setSelectedItem(nombreEdicion);
		comboBoxEdicion.setEnabled(false);

		comboBoxTipoRegistro.removeAllItems();
		comboBoxTipoRegistro.addItem(dtTipo.getNombre());
		comboBoxTipoRegistro.setSelectedItem(dtTipo.getNombre());
		comboBoxTipoRegistro.setEnabled(false);
	}

	public static ConsultaTipoRegistro crearYMostrar(IControllerEvento controller, String nombreEdicion,
			String nombreTipoR) {
		ConsultaTipoRegistro ctr = new ConsultaTipoRegistro(controller);
		DTTipoRegistro dtTipo = controller.consultaTipoRegistro(nombreEdicion, nombreTipoR);

		String nombreEvento = controller.findEdicion(nombreEdicion).getEvento().getNombre();

		ctr.cargarDatos(dtTipo, nombreEvento, nombreEdicion);
		ctr.setVisible(true);
		return ctr;
	}

}