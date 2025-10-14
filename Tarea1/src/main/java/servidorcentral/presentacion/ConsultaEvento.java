package servidorcentral.presentacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

import servidorcentral.logica.Categoria;
import servidorcentral.logica.DTevento;
import servidorcentral.logica.Edicion;
import servidorcentral.logica.Evento;
import servidorcentral.logica.IControllerEvento;

public class ConsultaEvento extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private IControllerEvento controlEvento;
	private JTextField textFieldnombre;
	private JTextField textFieldsigla;
	private JTextArea textAreadescripcion;
	private JTextField textFieldfAlta;
	private JComboBox<String> comboBoxEvento;
	private JComboBox<String> comboBoxcategorias;
	private JComboBox<String> comboBoxediciones;

	public ConsultaEvento(IControllerEvento icu, JDesktopPane desktopPane) {
		controlEvento = icu;
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Consultar Evento");
		setSize(800, 500);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelCombos = new JPanel();
		getContentPane().add(panelCombos, BorderLayout.NORTH);
		panelCombos.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblSelecioneUnEvento = new JLabel("Selecione un evento:");
		panelCombos.add(lblSelecioneUnEvento);

		comboBoxEvento = new JComboBox<>();
		comboBoxEvento.setPreferredSize(new Dimension(200, 25));
		panelCombos.add(comboBoxEvento);

		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3, BorderLayout.CENTER);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 71, 579 };
		gbl_panel_3.rowHeights = new int[] { 50, 50, 50, 50, 50, 50 };
		gbl_panel_3.columnWeights = new double[] { 0.3, 1.0 };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };

		panel_3.setLayout(gbl_panel_3);

		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 0;
		panel_3.add(lblNombre, gbc_lblNombre);

		textFieldnombre = new JTextField();
		textFieldnombre.setEditable(false);
		GridBagConstraints gbc_textField_nombre = new GridBagConstraints();
		gbc_textField_nombre.insets = new Insets(0, 0, 5, 0);
		gbc_textField_nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_nombre.gridx = 1;
		gbc_textField_nombre.gridy = 0;
		panel_3.add(textFieldnombre, gbc_textField_nombre);
		textFieldnombre.setColumns(10);

		JLabel lblSigla = new JLabel("Sigla:");
		GridBagConstraints gbc_lblSigla = new GridBagConstraints();
		gbc_lblSigla.anchor = GridBagConstraints.EAST;
		gbc_lblSigla.insets = new Insets(0, 0, 5, 5);
		gbc_lblSigla.gridx = 0;
		gbc_lblSigla.gridy = 1;
		panel_3.add(lblSigla, gbc_lblSigla);

		textFieldsigla = new JTextField();
		textFieldsigla.setEditable(false);
		GridBagConstraints gbc_textField_Sigla = new GridBagConstraints();
		gbc_textField_Sigla.insets = new Insets(0, 0, 5, 0);
		gbc_textField_Sigla.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Sigla.gridx = 1;
		gbc_textField_Sigla.gridy = 1;
		panel_3.add(textFieldsigla, gbc_textField_Sigla);
		textFieldsigla.setColumns(10);

		JLabel lblDes = new JLabel("Descripción:");
		GridBagConstraints gbc_lblDes = new GridBagConstraints();
		gbc_lblDes.anchor = GridBagConstraints.EAST;
		gbc_lblDes.insets = new Insets(0, 0, 5, 5);
		gbc_lblDes.gridx = 0;
		gbc_lblDes.gridy = 2;
		panel_3.add(lblDes, gbc_lblDes);

		textAreadescripcion = new JTextArea(3, 30); 
		textAreadescripcion.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		textAreadescripcion.setEditable(false);
		textAreadescripcion.setLineWrap(true);  
		textAreadescripcion.setWrapStyleWord(true); 
		JScrollPane scrollDescripcion = new JScrollPane(textAreadescripcion);
		scrollDescripcion.setEnabled(false);
		GridBagConstraints gbc_textArea_Descripcion = new GridBagConstraints();
		gbc_textArea_Descripcion.insets = new Insets(0, 0, 5, 0);
		gbc_textArea_Descripcion.fill = GridBagConstraints.BOTH; 
		gbc_textArea_Descripcion.gridx = 1;
		gbc_textArea_Descripcion.gridy = 2;

		panel_3.add(scrollDescripcion, gbc_textArea_Descripcion);
		

		JLabel lblFechaDeAlta = new JLabel("Fecha de alta:");
		GridBagConstraints gbc_lblFechaDeAlta = new GridBagConstraints();
		gbc_lblFechaDeAlta.anchor = GridBagConstraints.EAST;
		gbc_lblFechaDeAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaDeAlta.gridx = 0;
		gbc_lblFechaDeAlta.gridy = 3;
		panel_3.add(lblFechaDeAlta, gbc_lblFechaDeAlta);

		textFieldfAlta = new JTextField();
		textFieldfAlta.setEditable(false);
		GridBagConstraints gbc_textField_FAlta = new GridBagConstraints();
		gbc_textField_FAlta.insets = new Insets(0, 0, 5, 0);
		gbc_textField_FAlta.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_FAlta.gridx = 1;
		gbc_textField_FAlta.gridy = 3;
		panel_3.add(textFieldfAlta, gbc_textField_FAlta);
		textFieldfAlta.setColumns(10);

		JLabel lblNewLabel = new JLabel("Categorias:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 4;
		panel_3.add(lblNewLabel, gbc_lblNewLabel);

		comboBoxcategorias = new JComboBox<>();
		GridBagConstraints gbc_comboBox_Categorias = new GridBagConstraints();
		gbc_comboBox_Categorias.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_Categorias.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_Categorias.gridx = 1;
		gbc_comboBox_Categorias.gridy = 4;
		panel_3.add(comboBoxcategorias, gbc_comboBox_Categorias);

		JLabel lblNewLabel_1 = new JLabel("Ediciones:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 5;
		panel_3.add(lblNewLabel_1, gbc_lblNewLabel_1);

		comboBoxediciones = new JComboBox<>();
		GridBagConstraints gbc_comboBox_Ediciones = new GridBagConstraints();
		gbc_comboBox_Ediciones.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_Ediciones.gridx = 1;
		gbc_comboBox_Ediciones.gridy = 5;
		panel_3.add(comboBoxediciones, gbc_comboBox_Ediciones);

		JPanel panel_btn = new JPanel();
		getContentPane().add(panel_btn, BorderLayout.SOUTH);

		JButton btnVerEdicion = new JButton("Ver detalles edición");
		panel_btn.add(btnVerEdicion);

		comboBoxEvento.addActionListener(e -> {
			cargarDatos();
		});

		btnVerEdicion.addActionListener(e -> {
			String nombreEdicionSeleccionada = (String) comboBoxediciones.getSelectedItem();
			String nombreEventoSeleccionado = (String) comboBoxEvento.getSelectedItem();
			if (nombreEdicionSeleccionada != null && !nombreEdicionSeleccionada.equals("Sin ediciones")) {
				ConsultaEdicionEvento.crearYMostrar(controlEvento, nombreEventoSeleccionado, nombreEdicionSeleccionada,
						desktopPane);
			}
		});

	}
	public void cargarDatos() {
		String nombreEventoSeleccionado = (String) comboBoxEvento.getSelectedItem();
		limpiarCamposTexto();
		if (nombreEventoSeleccionado != null) {
			DTevento dte = controlEvento.consultaEvento(nombreEventoSeleccionado);
			textFieldnombre.setText(dte.getNombre());
			textFieldsigla.setText(dte.getSigla());
			textAreadescripcion.setText(dte.getDescripcion());
			textFieldfAlta.setText(dte.getFAlta().toString());
			cargarCategorias(dte.getCategorias());
			cargarEdiciones(dte.getEdiciones());
		}
	}
	public void consultaEventocargar() {
		List<Evento> eventos = controlEvento.listarEventos();
		List<String> nombres = new java.util.ArrayList<>();
		for (Evento e : eventos) {
			nombres.add(e.getNombre());
		}

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
		comboBoxEvento.setModel(model);
		cargarDatos();

	}

	private void cargarCategorias(List<Categoria> categorias) {
		if (categorias.isEmpty()) {
			comboBoxcategorias.removeAllItems();
			comboBoxcategorias.addItem("Sin categorías");
			comboBoxcategorias.setEnabled(false);
		} else {
			List<String> nombres = new java.util.ArrayList<>();
			for (Categoria c : categorias) {
				nombres.add(c.getNombre());
			}
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
			comboBoxcategorias.setEnabled(true);
			comboBoxcategorias.setModel(model);
		}
	}

	private void cargarEdiciones(List<Edicion> ediciones) {
		if (ediciones.isEmpty()) {
			comboBoxediciones.removeAllItems();
			comboBoxediciones.addItem("Sin ediciones");
			comboBoxediciones.setEnabled(false);
		} else {
			List<String> nombres = new java.util.ArrayList<>();
			for (Edicion ed : ediciones) {
				nombres.add(ed.getNombre());
			}
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
			comboBoxediciones.setEnabled(true);
			comboBoxediciones.setModel(model);
		}
	}

	private void limpiarCamposTexto() {
		textFieldnombre.setText("");
		textFieldsigla.setText("");
		textAreadescripcion.setText("");
		textFieldfAlta.setText("");
	}
	/**
	 * 
	 */

}