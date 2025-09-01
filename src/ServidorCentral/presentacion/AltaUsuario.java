package ServidorCentral.presentacion;

import ServidorCentral.excepciones.UsuarioRepetidoException;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.Institucion;
import ServidorCentral.logica.IControllerInstitucion;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.*;

public class AltaUsuario extends JInternalFrame {

	private IControllerUsuario controlUsr;
	private IControllerInstitucion controlIns;

	private JTextField textFieldNickName;
	private JTextField textFieldCorreo;
	private JTextField textFieldNombre;

	private JLabel lblApellido;
	private JTextField textFieldApellido;
	private JLabel lblFecha;
	private JTextField textFieldFechaNacimiento;

	private JLabel lblDescripcion;
	private JTextField textFieldDescripcion;
	private JLabel lblURL;
	private JTextField textFieldURL;

	private JLabel lblInstitucion;
	private JComboBox<String> comboInstitucion;

	private JComboBox<String> comboTipoUsuario;
	private JButton btnAceptar;
	private JButton btnCancelar;

	private int row = 0;

	public AltaUsuario(IControllerUsuario icu, IControllerInstitucion ici) {
		controlUsr = icu;
		controlIns = ici;

		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setTitle("Alta de Usuario");
		setBounds(10, 10, 500, 450);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 120, 250, 0 };
		gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblTipo = new JLabel("Tipo de Usuario:");
		GridBagConstraints gbc_lblTipo = new GridBagConstraints();
		gbc_lblTipo.insets = new Insets(5, 5, 5, 5);
		gbc_lblTipo.gridx = 0;
		gbc_lblTipo.gridy = row;
		gbc_lblTipo.anchor = GridBagConstraints.EAST;
		getContentPane().add(lblTipo, gbc_lblTipo);

		comboTipoUsuario = new JComboBox<>(new String[] { "Asistente", "Organizador" });
		GridBagConstraints gbc_comboTipo = new GridBagConstraints();
		gbc_comboTipo.insets = new Insets(5, 5, 5, 5);
		gbc_comboTipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboTipo.gridx = 1;
		gbc_comboTipo.gridy = row;
		getContentPane().add(comboTipoUsuario, gbc_comboTipo);
		row++;

		JLabel lblNick = new JLabel("Nickname:");
		GridBagConstraints gbc_lblNick = new GridBagConstraints();
		gbc_lblNick.insets = new Insets(5, 5, 5, 5);
		gbc_lblNick.gridx = 0;
		gbc_lblNick.gridy = row;
		gbc_lblNick.anchor = GridBagConstraints.EAST;
		getContentPane().add(lblNick, gbc_lblNick);

		textFieldNickName = new JTextField();
		GridBagConstraints gbc_textNick = new GridBagConstraints();
		gbc_textNick.insets = new Insets(5, 5, 5, 5);
		gbc_textNick.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNick.gridx = 1;
		gbc_textNick.gridy = row;
		getContentPane().add(textFieldNickName, gbc_textNick);
		row++;

		JLabel lblCorreo = new JLabel("Correo:");
		GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
		gbc_lblCorreo.insets = new Insets(5, 5, 5, 5);
		gbc_lblCorreo.gridx = 0;
		gbc_lblCorreo.gridy = row;
		gbc_lblCorreo.anchor = GridBagConstraints.EAST;
		getContentPane().add(lblCorreo, gbc_lblCorreo);

		textFieldCorreo = new JTextField();
		GridBagConstraints gbc_textCorreo = new GridBagConstraints();
		gbc_textCorreo.insets = new Insets(5, 5, 5, 5);
		gbc_textCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textCorreo.gridx = 1;
		gbc_textCorreo.gridy = row;
		getContentPane().add(textFieldCorreo, gbc_textCorreo);
		row++;

		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(5, 5, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = row;
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		getContentPane().add(lblNombre, gbc_lblNombre);

		textFieldNombre = new JTextField();
		GridBagConstraints gbc_textNombre = new GridBagConstraints();
		gbc_textNombre.insets = new Insets(5, 5, 5, 5);
		gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textNombre.gridx = 1;
		gbc_textNombre.gridy = row;
		getContentPane().add(textFieldNombre, gbc_textNombre);
		row++;

		lblApellido = new JLabel("Apellido:");
		textFieldApellido = new JTextField();
		lblFecha = new JLabel("Fecha de Nac.:");
		textFieldFechaNacimiento = new JTextField();

		lblDescripcion = new JLabel("Descripción:");
		textFieldDescripcion = new JTextField();
		lblURL = new JLabel("URL (opcional):");
		textFieldURL = new JTextField();

		lblInstitucion = new JLabel("Institución :");
		comboInstitucion = new JComboBox<>();
		comboInstitucion.addItem("Ninguna");
		for (Institucion ins : controlIns.getInstituciones()) {
			comboInstitucion.addItem(ins.getNombre());
		}

		btnAceptar = new JButton("Aceptar");
		GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
		gbc_btnAceptar.insets = new Insets(10, 50, 5, 20);
		gbc_btnAceptar.gridx = 1;
		gbc_btnAceptar.gridy = 8;
		gbc_btnAceptar.anchor = GridBagConstraints.LINE_START;
		Dimension mismoTamaño = new Dimension(100, 25);
		btnAceptar.setPreferredSize(mismoTamaño);
		getContentPane().add(btnAceptar, gbc_btnAceptar);

		btnCancelar = new JButton("Cancelar");
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(10, 20, 5, 80);
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 8;
		gbc_btnCancelar.anchor = GridBagConstraints.LINE_END;
		btnCancelar.setPreferredSize(mismoTamaño);
		getContentPane().add(btnCancelar, gbc_btnCancelar);

		btnCancelar.addActionListener(e -> {
			textFieldNickName.setText("");
			textFieldCorreo.setText("");
			textFieldNombre.setText("");
			textFieldFechaNacimiento.setText("");
			textFieldApellido.setText("");
			textFieldDescripcion.setText("");
			textFieldURL.setText("");
			this.setVisible(false);
		});

		comboTipoUsuario.addActionListener(e -> actualizarCampos(row));
		comboTipoUsuario.setSelectedIndex(0);

		btnAceptar.addActionListener(e -> {
			String tipo = (String) comboTipoUsuario.getSelectedItem();
			String nick = textFieldNickName.getText().trim();
			String nombre = textFieldNombre.getText().trim();
			String correo = textFieldCorreo.getText().trim();

			try {
				if (nick.isEmpty() || nombre.isEmpty() || correo.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Nickname, Nombre y Correo son obligatorios.", "Campos vacíos",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (tipo.equals("Asistente")) {
					String apellido = textFieldApellido.getText().trim();
					String fechaStr = textFieldFechaNacimiento.getText().trim();

					if (apellido.isEmpty() || fechaStr.isEmpty()) {
						JOptionPane.showMessageDialog(this, "Apellido y Fecha de nacimiento son obligatorios.",
								"Campos vacíos", JOptionPane.WARNING_MESSAGE);
						return;
					}

					LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					
					if (fecha.isAfter(LocalDate.now())||fecha.isEqual(LocalDate.now())) {
						JOptionPane.showMessageDialog(this, "La fecha de nacimiento debe ser menor a la fecha actual","Fecha de nacimiento no valida", JOptionPane.WARNING_MESSAGE);
						return;
					}

					String nombreInstitucion = (String) comboInstitucion.getSelectedItem();
					Institucion inst = null;
					if (!nombreInstitucion.equals("Ninguna")) {
						inst = controlIns.findInstitucion(nombreInstitucion);
					}

					controlUsr.AltaAsistente(nick, correo, nombre, apellido, fecha, inst);
					textFieldNickName.setText("");
					textFieldCorreo.setText("");
					textFieldNombre.setText("");
					textFieldFechaNacimiento.setText("");
					textFieldApellido.setText("");

				} else {
					String descripcion = textFieldDescripcion.getText().trim();
					if (descripcion.isEmpty()) {
						JOptionPane.showMessageDialog(this, "La descripción es obligatoria para un Organizador.",
								"Campos vacíos", JOptionPane.WARNING_MESSAGE);
						return;
					}

					String url = textFieldURL.getText().trim();
					if (url.isEmpty())
						url = null;

					controlUsr.AltaOrganizador(nick, correo, nombre, descripcion, url);

					textFieldNickName.setText("");
					textFieldCorreo.setText("");
					textFieldNombre.setText("");
					textFieldDescripcion.setText("");
					textFieldURL.setText("");
				}

				JOptionPane.showMessageDialog(this, "Usuario creado correctamente");
				this.setVisible(false);

			} catch (DateTimeParseException dtpe) {
				JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy", "Error",
						JOptionPane.ERROR_MESSAGE);
			} catch (UsuarioRepetidoException ure) {
				JOptionPane.showMessageDialog(this, ure.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void actualizarCampos(int row) {
		getContentPane().remove(lblApellido);
		getContentPane().remove(textFieldApellido);
		getContentPane().remove(lblFecha);
		getContentPane().remove(textFieldFechaNacimiento);
		getContentPane().remove(lblDescripcion);
		getContentPane().remove(textFieldDescripcion);
		getContentPane().remove(lblURL);
		getContentPane().remove(textFieldURL);
		getContentPane().remove(lblInstitucion);
		getContentPane().remove(comboInstitucion);

		int dynamicRow = row;

		if (comboTipoUsuario.getSelectedItem().equals("Asistente")) {
			comboInstitucion.removeAllItems();
			comboInstitucion.addItem("Ninguna");
			for (Institucion ins : controlIns.getInstituciones()) {
				comboInstitucion.addItem(ins.getNombre());
			}

			GridBagConstraints gbc_lblAp = new GridBagConstraints();
			gbc_lblAp.insets = new Insets(5, 5, 5, 5);
			gbc_lblAp.gridx = 0;
			gbc_lblAp.gridy = dynamicRow;
			gbc_lblAp.anchor = GridBagConstraints.EAST;
			getContentPane().add(lblApellido, gbc_lblAp);

			GridBagConstraints gbc_textAp = new GridBagConstraints();
			gbc_textAp.insets = new Insets(5, 5, 5, 5);
			gbc_textAp.fill = GridBagConstraints.HORIZONTAL;
			gbc_textAp.gridx = 1;
			gbc_textAp.gridy = dynamicRow;
			getContentPane().add(textFieldApellido, gbc_textAp);
			dynamicRow++;

			GridBagConstraints gbc_lblFec = new GridBagConstraints();
			gbc_lblFec.insets = new Insets(5, 5, 5, 5);
			gbc_lblFec.gridx = 0;
			gbc_lblFec.gridy = dynamicRow;
			gbc_lblFec.anchor = GridBagConstraints.EAST;
			getContentPane().add(lblFecha, gbc_lblFec);

			GridBagConstraints gbc_textFec = new GridBagConstraints();
			gbc_textFec.insets = new Insets(5, 5, 5, 5);
			gbc_textFec.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFec.gridx = 1;
			gbc_textFec.gridy = dynamicRow;
			getContentPane().add(textFieldFechaNacimiento, gbc_textFec);
			dynamicRow++;

			GridBagConstraints gbc_lblInst = new GridBagConstraints();
			gbc_lblInst.insets = new Insets(5, 5, 5, 5);
			gbc_lblInst.gridx = 0;
			gbc_lblInst.gridy = dynamicRow;
			gbc_lblInst.anchor = GridBagConstraints.EAST;
			getContentPane().add(lblInstitucion, gbc_lblInst);

			GridBagConstraints gbc_comboInst = new GridBagConstraints();
			gbc_comboInst.insets = new Insets(5, 5, 5, 5);
			gbc_comboInst.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboInst.gridx = 1;
			gbc_comboInst.gridy = dynamicRow;
			getContentPane().add(comboInstitucion, gbc_comboInst);

		} else {
			// Organizador
			GridBagConstraints gbc_lblDesc = new GridBagConstraints();
			gbc_lblDesc.insets = new Insets(5, 5, 5, 5);
			gbc_lblDesc.gridx = 0;
			gbc_lblDesc.gridy = dynamicRow;
			gbc_lblDesc.anchor = GridBagConstraints.EAST;
			getContentPane().add(lblDescripcion, gbc_lblDesc);

			GridBagConstraints gbc_textDesc = new GridBagConstraints();
			gbc_textDesc.insets = new Insets(5, 5, 5, 5);
			gbc_textDesc.fill = GridBagConstraints.HORIZONTAL;
			gbc_textDesc.gridx = 1;
			gbc_textDesc.gridy = dynamicRow;
			getContentPane().add(textFieldDescripcion, gbc_textDesc);
			dynamicRow++;

			GridBagConstraints gbc_lblUrl = new GridBagConstraints();
			gbc_lblUrl.insets = new Insets(5, 5, 5, 5);
			gbc_lblUrl.gridx = 0;
			gbc_lblUrl.gridy = dynamicRow;
			gbc_lblUrl.anchor = GridBagConstraints.EAST;
			getContentPane().add(lblURL, gbc_lblUrl);

			GridBagConstraints gbc_textUrl = new GridBagConstraints();
			gbc_textUrl.insets = new Insets(5, 5, 5, 5);
			gbc_textUrl.fill = GridBagConstraints.HORIZONTAL;
			gbc_textUrl.gridx = 1;
			gbc_textUrl.gridy = dynamicRow;
			getContentPane().add(textFieldURL, gbc_textUrl);
		}

		getContentPane().revalidate();
		getContentPane().repaint();
	}

	public void recargarCampos() {
		actualizarCampos(row);
	}

	private static final long serialVersionUID = 1L;
}
