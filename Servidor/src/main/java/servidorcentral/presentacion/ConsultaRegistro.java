package servidorcentral.presentacion;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import servidorcentral.logica.Asistente;
import servidorcentral.logica.DTRegistroDetallado;
import servidorcentral.logica.IControllerUsuario;

public class ConsultaRegistro extends JInternalFrame {
	private JTextField textFieldfinicio;
	private JTextField textField1costo;
	private IControllerUsuario icu;
	private JComboBox<String> comboBoxUsuario;
	private JComboBox<String> comboBoxRegistro;
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField1;
	private JTextField textField2;

	public ConsultaRegistro(IControllerUsuario icu) {
		this.icu = icu;
		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Consultar registro");
		setSize(700, 400);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelCombos = new JPanel();
		getContentPane().add(panelCombos, BorderLayout.NORTH);
		panelCombos.setLayout(new GridLayout(1, 3, 0, 0));

		JPanel panel = new JPanel();
		panelCombos.add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblSelecioneUnEvento = new JLabel("Selecione un usuario:");
		panel.add(lblSelecioneUnEvento, BorderLayout.NORTH);

		comboBoxUsuario = new JComboBox<>();
		panel.add(comboBoxUsuario, BorderLayout.SOUTH);

		JPanel panel_1 = new JPanel();
		panelCombos.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JLabel lblSelecioneUnaEdicin = new JLabel("Selecione un registro:");
		panel_1.add(lblSelecioneUnaEdicin, BorderLayout.NORTH);

		comboBoxRegistro = new JComboBox<>();
		comboBoxRegistro.setEnabled(false);
		panel_1.add(comboBoxRegistro, BorderLayout.SOUTH);

		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3, BorderLayout.CENTER);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 71, 579 };
		gbl_panel_3.rowHeights = new int[] { 50, 50, 50, 50, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.3, 1.0 };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };

		panel_3.setLayout(gbl_panel_3);

		JLabel lblNewLabel = new JLabel("Fecha inicio:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_3.add(lblNewLabel, gbc_lblNewLabel);

		textFieldfinicio = new JTextField();
		textFieldfinicio.setEditable(false);
		GridBagConstraints gbc_textField_finicio = new GridBagConstraints();
		gbc_textField_finicio.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_finicio.insets = new Insets(0, 0, 5, 0);
		gbc_textField_finicio.gridx = 1;
		gbc_textField_finicio.gridy = 0;
		panel_3.add(textFieldfinicio, gbc_textField_finicio);
		textFieldfinicio.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Costo:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel_3.add(lblNewLabel_1, gbc_lblNewLabel_1);

		textField1costo = new JTextField();
		textField1costo.setEditable(false);
		GridBagConstraints gbc_textField_1_costo = new GridBagConstraints();
		gbc_textField_1_costo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1_costo.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1_costo.gridx = 1;
		gbc_textField_1_costo.gridy = 1;
		panel_3.add(textField1costo, gbc_textField_1_costo);
		textField1costo.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Evento: ");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel_3.add(lblNewLabel_2, gbc_lblNewLabel_2);

		textField = new JTextField();
		textField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		panel_3.add(textField, gbc_textField);
		textField.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Edicion:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		panel_3.add(lblNewLabel_3, gbc_lblNewLabel_3);

		textField1 = new JTextField();
		textField1.setEditable(false);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 3;
		panel_3.add(textField1, gbc_textField_1);
		textField1.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Tipo de registro:");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 4;
		panel_3.add(lblNewLabel_4, gbc_lblNewLabel_4);

		textField2 = new JTextField();
		textField2.setEditable(false);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 4;
		panel_3.add(textField2, gbc_textField_2);
		textField2.setColumns(10);

		comboBoxUsuario.addActionListener(e -> {
			String usuarioSeleccionado = (String) comboBoxUsuario.getSelectedItem();
			if (usuarioSeleccionado != null) {
				listarRegistrosAsistente(usuarioSeleccionado);
				if (comboBoxRegistro.getSelectedItem() == null) {
					textFieldfinicio.setText("");
					textField1costo.setText("");
					textField.setText("");
					textField1.setText("");
					textField2.setText("");
				}
			} else {
				comboBoxRegistro.setEnabled(false);
			}

		});
		comboBoxRegistro.addActionListener(e -> {
			String registroSeleccionado = (String) comboBoxRegistro.getSelectedItem();
			String usuarioSeleccionado = (String) comboBoxUsuario.getSelectedItem();
			if (registroSeleccionado != null) {

				DTRegistroDetallado dto = icu.getRegistroDetalle(registroSeleccionado, usuarioSeleccionado);

				textFieldfinicio.setText(dto.getfRegistro().toString());
				textField1costo.setText(String.valueOf(dto.getCosto()));
				textField.setText(dto.getNombreEvento());
				textField1.setText(dto.getNombreEdicion());
				textField2.setText(dto.getTipoRegistro());
			}
		});

	}

	public void cargarUsuarios() {
		comboBoxUsuario.removeAllItems();
		for (Asistente asistente : icu.getAsistentes()) {
			comboBoxUsuario.addItem(asistente.getNickname());
		}
		if (comboBoxUsuario.getItemCount() > 0) {
			comboBoxUsuario.setSelectedIndex(0);
			listarRegistrosAsistente((String) comboBoxUsuario.getSelectedItem());
		}
		
	}

	private void listarRegistrosAsistente(String usuarioSeleccionado) {
		List<String> registros = icu.getAsistenteRegistro(usuarioSeleccionado);
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(registros.toArray(new String[0]));
		comboBoxRegistro.setModel(model);
		if (!registros.isEmpty()) {
			comboBoxRegistro.setEnabled(true);
			comboBoxRegistro.setSelectedIndex(0);
	}else {
			comboBoxRegistro.setEnabled(false);
		}
		
	}

	public static ConsultaRegistro crearYMostrar(IControllerUsuario controllerU, String nombreUsuario,
			String nombreEdicion, JDesktopPane desktopPane) {
		ConsultaRegistro cre = new ConsultaRegistro(controllerU);
		if (desktopPane != null) {
			desktopPane.add(cre);
			cre.setVisible(true);
			cre.setLocation((desktopPane.getWidth() - cre.getWidth()) / 2,
					(desktopPane.getHeight() - cre.getHeight()) / 2);
		}
		cre.comboBoxUsuario.removeAllItems();
		for (Asistente asistente : controllerU.getAsistentes()) {
			cre.comboBoxUsuario.addItem(asistente.getNickname());
		}

		if (nombreUsuario != null) {
			cre.comboBoxUsuario.setSelectedItem(nombreUsuario);

			List<String> registros = controllerU.getAsistenteRegistro(nombreUsuario);
			cre.comboBoxRegistro.removeAllItems();
			for (String r : registros) {
				cre.comboBoxRegistro.addItem(r);
			}
			cre.comboBoxRegistro.setEnabled(!registros.isEmpty());

			if (nombreEdicion != null && registros.contains(nombreEdicion)) {
				cre.comboBoxRegistro.setSelectedItem(nombreEdicion);

				DTRegistroDetallado dto = controllerU.getRegistroDetalle(nombreEdicion, nombreUsuario);
				cre.textFieldfinicio.setText(dto.getfRegistro().toString());
				cre.textField1costo.setText(String.valueOf(dto.getCosto()));
				cre.textField.setText(dto.getNombreEvento());
				cre.textField1.setText(dto.getNombreEdicion());
				cre.textField2.setText(dto.getTipoRegistro());
			}
		}

		return cre;
	}

}