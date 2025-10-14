package servidorcentral.presentacion;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import servidorcentral.logica.IControllerInstitucion;

public class AltaInstitucion extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private IControllerInstitucion controllerInstitucion;

	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JTextField txtURL;
	private JButton btnAceptar ;
	private JButton btnCancelar;

	public AltaInstitucion(IControllerInstitucion controller) {
		this.controllerInstitucion = controller;

		setResizable(true);
		setIconifiable(true);
		setMaximizable(true);
		setClosable(true);
		setTitle("Alta de Institución");
		setBounds(10, 10, 500, 300);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 120, 250, 0 };
		gridBagLayout.rowHeights = new int[] { 30, 30, 30, 30, 40, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0, 0, 0, 0, 0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(5, 5, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 0;
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		getContentPane().add(lblNombre, gbc_lblNombre);

		txtNombre = new JTextField();
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.insets = new Insets(5, 5, 5, 5);
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 0;
		getContentPane().add(txtNombre, gbc_txtNombre);

		JLabel lblDescripcion = new JLabel("Descripción:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.insets = new Insets(5, 5, 5, 5);
		gbc_lblDescripcion.gridx = 0;
		gbc_lblDescripcion.gridy = 1;
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);

		txtDescripcion = new JTextField();
		GridBagConstraints gbc_txtDescripcion = new GridBagConstraints();
		gbc_txtDescripcion.insets = new Insets(5, 5, 5, 5);
		gbc_txtDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescripcion.gridx = 1;
		gbc_txtDescripcion.gridy = 1;
		getContentPane().add(txtDescripcion, gbc_txtDescripcion);

		JLabel lblURL = new JLabel("URL:");
		GridBagConstraints gbc_lblURL = new GridBagConstraints();
		gbc_lblURL.insets = new Insets(5, 5, 5, 5);
		gbc_lblURL.gridx = 0;
		gbc_lblURL.gridy = 2;
		gbc_lblURL.anchor = GridBagConstraints.EAST;
		getContentPane().add(lblURL, gbc_lblURL);

		txtURL = new JTextField();
		GridBagConstraints gbc_txtURL = new GridBagConstraints();
		gbc_txtURL.insets = new Insets(5, 5, 5, 5);
		gbc_txtURL.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtURL.gridx = 1;
		gbc_txtURL.gridy = 2;
		getContentPane().add(txtURL, gbc_txtURL);

		btnAceptar  = new JButton("Aceptar ");
		GridBagConstraints gbc_btnAceptar  = new GridBagConstraints();
		gbc_btnAceptar .insets = new Insets(10, 50, 5, 20);
		gbc_btnAceptar .gridx = 1;
		gbc_btnAceptar .gridy = 4;
		gbc_btnAceptar .weightx = 0;
		gbc_btnAceptar .anchor = GridBagConstraints.LINE_START;
		Dimension mismoTamaño = new Dimension(100, 25);
		btnAceptar .setPreferredSize(mismoTamaño);
		getContentPane().add(btnAceptar , gbc_btnAceptar );

		btnCancelar = new JButton("Cancelar");
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(10, 20, 5, 80);
		gbc_btnCancelar.gridx = 1;
		gbc_btnCancelar.gridy = 4;
		gbc_btnCancelar.weightx = 0;
		gbc_btnCancelar.anchor = GridBagConstraints.LINE_END;
		btnCancelar.setPreferredSize(mismoTamaño);
		getContentPane().add(btnCancelar, gbc_btnCancelar);

		btnCancelar.addActionListener(e -> {
			txtNombre.setText("");
			txtDescripcion.setText("");
			txtURL.setText("");
			this.setVisible(false);
		});

		btnAceptar .addActionListener(e -> {
			String nombre = txtNombre.getText().trim();
			String descripcion = txtDescripcion.getText().trim();
			String url = txtURL.getText().trim();

			if (nombre.isEmpty() || descripcion.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Nombre y Descripción son obligatorios.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				controllerInstitucion.altaInstitucion(nombre, descripcion, url,null);
				JOptionPane.showMessageDialog(this, "Institución creada correctamente");
				txtNombre.setText("");
				txtDescripcion.setText("");
				txtURL.setText("");
				this.setVisible(false);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
