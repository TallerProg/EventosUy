package ServidorCentral.presentacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.*;

import ServidorCentral.logica.Asistente;
import ServidorCentral.logica.DTUsuarioListaConsulta;
import ServidorCentral.logica.Edicion;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.Organizador;
import ServidorCentral.logica.Usuario;

public class ConsultarUsuario extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private IControllerUsuario controlUsuario;
	private IControllerEvento controlEvento;

	private JComboBox<String> comboBoxUsuario;
	private JComboBox<String> comboBoxEdiciones;
	private JTextField textFieldNickname;
	private JTextField textFieldCorreo;
	private JTextField textFieldNombre;
	private JLabel lblApellido;
	private JTextField textFieldApellido;
	private JLabel lblFechaNacimiento;
	private JTextField textFieldFechaNacimiento;
	private JLabel lblDescripcion;
	private JTextField textFieldDescripcion;
	private JLabel lblURL;
	private JTextField textFieldURL;

	private JPanel panelDatos;
	private JButton btnVerDetalles;

	public ConsultarUsuario(IControllerUsuario icu, IControllerEvento ice, JDesktopPane desktopPane) {
		controlUsuario = icu;
		controlEvento = ice;

		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setClosable(true);
		setTitle("Consultar Usuario");
		setSize(700, 400);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panelCombos = new JPanel();
		getContentPane().add(panelCombos, BorderLayout.NORTH);
		comboBoxUsuario = new JComboBox<>();
		comboBoxUsuario.setPreferredSize(new Dimension(200, 25));
		panelCombos.add(new JLabel("Seleccione un usuario:"));
		panelCombos.add(comboBoxUsuario);

		panelDatos = new JPanel();
		getContentPane().add(panelDatos, BorderLayout.CENTER);
		GridBagLayout gbl_panelDatos = new GridBagLayout();
		gbl_panelDatos.columnWidths = new int[] { 120, 400 };
		gbl_panelDatos.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30 };
		gbl_panelDatos.columnWeights = new double[] { 0.0, 1.0 };
		gbl_panelDatos.rowWeights = new double[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		panelDatos.setLayout(gbl_panelDatos);

		textFieldNickname = addRow("Nickname:", panelDatos, 0);
		textFieldCorreo = addRow("Correo:", panelDatos, 1);
		textFieldNombre = addRow("Nombre:", panelDatos, 2);

		lblApellido = new JLabel("Apellido:");
		textFieldApellido = new JTextField();
		textFieldApellido.setEditable(false);
		addLabelField(lblApellido, textFieldApellido, panelDatos, 3);

		lblFechaNacimiento = new JLabel("Fecha Nac.:");
		textFieldFechaNacimiento = new JTextField();
		textFieldFechaNacimiento.setEditable(false);
		addLabelField(lblFechaNacimiento, textFieldFechaNacimiento, panelDatos, 4);

		lblDescripcion = new JLabel("Descripción:");
		textFieldDescripcion = new JTextField();
		textFieldDescripcion.setEditable(false);
		addLabelField(lblDescripcion, textFieldDescripcion, panelDatos, 3);

		lblURL = new JLabel("URL:");
		textFieldURL = new JTextField();
		textFieldURL.setEditable(false);
		addLabelField(lblURL, textFieldURL, panelDatos, 4);

		JLabel lblEdiciones = new JLabel("Ediciones:");
		GridBagConstraints gbc_lblEdiciones = new GridBagConstraints();
		gbc_lblEdiciones.anchor = GridBagConstraints.EAST;
		gbc_lblEdiciones.insets = new Insets(0, 0, 0, 5);
		gbc_lblEdiciones.gridx = 0;
		gbc_lblEdiciones.gridy = 5;
		panelDatos.add(lblEdiciones, gbc_lblEdiciones);

		comboBoxEdiciones = new JComboBox<>();
		GridBagConstraints gbc_comboEdiciones = new GridBagConstraints();
		gbc_comboEdiciones.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboEdiciones.gridx = 1;
		gbc_comboEdiciones.gridy = 5;
		panelDatos.add(comboBoxEdiciones, gbc_comboEdiciones);

		btnVerDetalles = new JButton("Ver detalles");
		btnVerDetalles.setVisible(false);
		GridBagConstraints gbc_btnVerDetalles = new GridBagConstraints();
		gbc_btnVerDetalles.gridx = 1;
		gbc_btnVerDetalles.gridy = 6;
		gbc_btnVerDetalles.anchor = GridBagConstraints.WEST;
		gbc_btnVerDetalles.insets = new Insets(10, 0, 0, 0);
		panelDatos.add(btnVerDetalles, gbc_btnVerDetalles);

		lblApellido.setVisible(false);
		textFieldApellido.setVisible(false);
		lblFechaNacimiento.setVisible(false);
		textFieldFechaNacimiento.setVisible(false);
		lblDescripcion.setVisible(false);
		textFieldDescripcion.setVisible(false);
		lblURL.setVisible(false);
		textFieldURL.setVisible(false);

		comboBoxUsuario.addActionListener(e -> {
			String nicknameSeleccionado = (String) comboBoxUsuario.getSelectedItem();
			limpiarCampos();

			if (nicknameSeleccionado != null) {
				DTUsuarioListaConsulta dt = controlUsuario.ConsultaDeUsuario(nicknameSeleccionado);
				if (dt == null)
					return;

				textFieldNickname.setText(dt.getNickname());
				textFieldCorreo.setText(dt.getCorreo());
				textFieldNombre.setText(dt.getNombre());

				Usuario usuario = controlUsuario.getUsuario(nicknameSeleccionado);
				if (usuario instanceof Asistente) {
					lblApellido.setVisible(true);
					textFieldApellido.setVisible(true);
					lblFechaNacimiento.setVisible(true);
					textFieldFechaNacimiento.setVisible(true);
					textFieldApellido.setText(dt.getApellido());
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					textFieldFechaNacimiento.setText(dt.getFNacimiento().format(formatter));

					lblDescripcion.setVisible(false);
					textFieldDescripcion.setVisible(false);
					lblURL.setVisible(false);
					textFieldURL.setVisible(false);
					btnVerDetalles.setText("Ver detalles registro");
					btnVerDetalles.setVisible(true);
				} else if (usuario instanceof Organizador) {
					lblDescripcion.setVisible(true);
					textFieldDescripcion.setVisible(true);
					lblURL.setVisible(true);
					textFieldURL.setVisible(true);
					textFieldDescripcion.setText(dt.getDescripcion());
					textFieldURL.setText(dt.getUrl());

					lblApellido.setVisible(false);
					textFieldApellido.setVisible(false);
					lblFechaNacimiento.setVisible(false);
					textFieldFechaNacimiento.setVisible(false);
					btnVerDetalles.setText("Ver detalles edición de evento");
					btnVerDetalles.setVisible(true);
				} else {
					btnVerDetalles.setVisible(false);
				}

				cargarEdiciones(dt.getEdiciones());
				panelDatos.revalidate();
				panelDatos.repaint();
			}
		});

		btnVerDetalles.addActionListener(e -> {
			String nomUsuarioSeleccionado = (String) comboBoxUsuario.getSelectedItem();
			String nomEdicionSeleccionada = (String) comboBoxEdiciones.getSelectedItem();

			if (controlUsuario.getUsuario(nomUsuarioSeleccionado) == null
					|| controlEvento.findEdicion(nomEdicionSeleccionada) == null)
				return;

			if (controlUsuario.getUsuario(nomUsuarioSeleccionado) instanceof Asistente) {
				ConsultaRegistro.crearYMostrar(controlUsuario, nomUsuarioSeleccionado,
						controlEvento.findEdicion(nomEdicionSeleccionada).getEvento().getNombre(), desktopPane);
			} else if (controlUsuario.getUsuario(nomUsuarioSeleccionado) instanceof Organizador) {
				ConsultaEdicionEvento.crearYMostrar(controlEvento,
						controlEvento.findEdicion(nomEdicionSeleccionada).getEvento().getNombre(),
						nomEdicionSeleccionada, desktopPane);
			}
		});
	}

	private JTextField addRow(String label, JPanel panel, int row) {
		JLabel lbl = new JLabel(label);
		GridBagConstraints gbc_lbl = new GridBagConstraints();
		gbc_lbl.anchor = GridBagConstraints.EAST;
		gbc_lbl.insets = new Insets(0, 0, 5, 5);
		gbc_lbl.gridx = 0;
		gbc_lbl.gridy = row;
		panel.add(lbl, gbc_lbl);

		JTextField field = new JTextField();
		field.setEditable(false);
		GridBagConstraints gbc_field = new GridBagConstraints();
		gbc_field.insets = new Insets(0, 0, 5, 0);
		gbc_field.fill = GridBagConstraints.HORIZONTAL;
		gbc_field.gridx = 1;
		gbc_field.gridy = row;
		panel.add(field, gbc_field);
		field.setColumns(10);

		return field;
	}

	private void addLabelField(JLabel label, JTextField field, JPanel panel, int row) {
		GridBagConstraints gbc_lbl = new GridBagConstraints();
		gbc_lbl.anchor = GridBagConstraints.EAST;
		gbc_lbl.insets = new Insets(0, 0, 5, 5);
		gbc_lbl.gridx = 0;
		gbc_lbl.gridy = row;
		panel.add(label, gbc_lbl);

		GridBagConstraints gbc_field = new GridBagConstraints();
		gbc_field.insets = new Insets(0, 0, 5, 0);
		gbc_field.fill = GridBagConstraints.HORIZONTAL;
		gbc_field.gridx = 1;
		gbc_field.gridy = row;
		panel.add(field, gbc_field);
	}

	public void cargarUsuarios() {
		List<Usuario> usuarios = controlUsuario.listarUsuarios();
		java.util.List<String> nicks = new java.util.ArrayList<>();
		for (Usuario u : usuarios) {
			nicks.add(u.getNickname());
		}
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nicks.toArray(new String[0]));
		comboBoxUsuario.setModel(model);
	}

	private void cargarEdiciones(List<Edicion> ediciones) {
		comboBoxEdiciones.removeAllItems();
		if (ediciones == null || ediciones.isEmpty()) {
			comboBoxEdiciones.addItem("Sin ediciones");
			comboBoxEdiciones.setEnabled(false);
		} else {
			for (Edicion e : ediciones) {
				comboBoxEdiciones.addItem(e.getNombre());
			}
			comboBoxEdiciones.setEnabled(true);
		}
	}

	private void limpiarCampos() {
		textFieldNickname.setText("");
		textFieldCorreo.setText("");
		textFieldNombre.setText("");
		textFieldApellido.setText("");
		textFieldFechaNacimiento.setText("");
		textFieldDescripcion.setText("");
		textFieldURL.setText("");
		comboBoxEdiciones.removeAllItems();
	}
}
