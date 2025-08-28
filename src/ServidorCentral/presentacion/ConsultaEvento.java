package ServidorCentral.presentacion;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class ConsultaEvento extends JInternalFrame {
	public ConsultaEvento() {
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setClosable(true);
		setTitle("Consultar Evento");
	        setSize(700, 400);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panelCombos = new JPanel();
		getContentPane().add(panelCombos, BorderLayout.NORTH);
		panelCombos.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblSelecioneUnEvento = new JLabel("Selecione un evento:");
		panelCombos.add(lblSelecioneUnEvento);
		
		JComboBox comboBoxEvento = new JComboBox();
		comboBoxEvento.setPreferredSize(new Dimension(200, 25));
		panelCombos.add(comboBoxEvento);

		JPanel panel_3 = new JPanel();
		getContentPane().add(panel_3, BorderLayout.CENTER);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{71, 579}; 
		gbl_panel_3.rowHeights = new int[]{50, 50, 50, 50, 50, 50}; 
		gbl_panel_3.columnWeights = new double[]{0.3, 1.0}; 
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 0;
		panel_3.add(lblNombre, gbc_lblNombre);
		
		textField_Nombre = new JTextField();
		textField_Nombre.setEditable(false);
		GridBagConstraints gbc_textField_Nombre = new GridBagConstraints();
		gbc_textField_Nombre.insets = new Insets(0, 0, 5, 0);
		gbc_textField_Nombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Nombre.gridx = 1;
		gbc_textField_Nombre.gridy = 0;
		panel_3.add(textField_Nombre, gbc_textField_Nombre);
		textField_Nombre.setColumns(10);
		
		JLabel lblSigla = new JLabel("Sigla:");
		GridBagConstraints gbc_lblSigla = new GridBagConstraints();
		gbc_lblSigla.anchor = GridBagConstraints.EAST;
		gbc_lblSigla.insets = new Insets(0, 0, 5, 5);
		gbc_lblSigla.gridx = 0;
		gbc_lblSigla.gridy = 1;
		panel_3.add(lblSigla, gbc_lblSigla);
		
		textField_Sigla = new JTextField();
		textField_Sigla.setEditable(false);
		GridBagConstraints gbc_textField_Sigla = new GridBagConstraints();
		gbc_textField_Sigla.insets = new Insets(0, 0, 5, 0);
		gbc_textField_Sigla.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Sigla.gridx = 1;
		gbc_textField_Sigla.gridy = 1;
		panel_3.add(textField_Sigla, gbc_textField_Sigla);
		textField_Sigla.setColumns(10);
		
		JLabel lblDes = new JLabel("Descripción:");
		GridBagConstraints gbc_lblDes = new GridBagConstraints();
		gbc_lblDes.anchor = GridBagConstraints.EAST;
		gbc_lblDes.insets = new Insets(0, 0, 5, 5);
		gbc_lblDes.gridx = 0;
		gbc_lblDes.gridy = 2;
		panel_3.add(lblDes, gbc_lblDes);
		
		textField_Descripcion = new JTextField();
		textField_Descripcion.setEditable(false);
		GridBagConstraints gbc_textField_Descripcion = new GridBagConstraints();
		gbc_textField_Descripcion.insets = new Insets(0, 0, 5, 0);
		gbc_textField_Descripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Descripcion.gridx = 1;
		gbc_textField_Descripcion.gridy = 2;
		panel_3.add(textField_Descripcion, gbc_textField_Descripcion);
		textField_Descripcion.setColumns(10);
		
		JLabel lblFechaDeAlta = new JLabel("Fecha de alta:");
		GridBagConstraints gbc_lblFechaDeAlta = new GridBagConstraints();
		gbc_lblFechaDeAlta.anchor = GridBagConstraints.EAST;
		gbc_lblFechaDeAlta.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaDeAlta.gridx = 0;
		gbc_lblFechaDeAlta.gridy = 3;
		panel_3.add(lblFechaDeAlta, gbc_lblFechaDeAlta);
		
		textField_FAlta = new JTextField();
		textField_FAlta.setEditable(false);
		GridBagConstraints gbc_textField_FAlta = new GridBagConstraints();
		gbc_textField_FAlta.insets = new Insets(0, 0, 5, 0);
		gbc_textField_FAlta.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_FAlta.gridx = 1;
		gbc_textField_FAlta.gridy = 3;
		panel_3.add(textField_FAlta, gbc_textField_FAlta);
		textField_FAlta.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Categorias:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 4;
		panel_3.add(lblNewLabel, gbc_lblNewLabel);
		
		JComboBox comboBox_Categorias = new JComboBox();
		GridBagConstraints gbc_comboBox_Categorias = new GridBagConstraints();
		gbc_comboBox_Categorias.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox_Categorias.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_Categorias.gridx = 1;
		gbc_comboBox_Categorias.gridy = 4;
		panel_3.add(comboBox_Categorias, gbc_comboBox_Categorias);
		
		JLabel lblNewLabel_1 = new JLabel("Ediciones:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 5;
		panel_3.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JComboBox comboBox_Ediciones = new JComboBox();
		GridBagConstraints gbc_comboBox_Ediciones = new GridBagConstraints();
		gbc_comboBox_Ediciones.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_Ediciones.gridx = 1;
		gbc_comboBox_Ediciones.gridy = 5;
		panel_3.add(comboBox_Ediciones, gbc_comboBox_Ediciones);
		
		JPanel panel_btn = new JPanel();
		getContentPane().add(panel_btn, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Ver detalles edición");
		panel_btn.add(btnNewButton);

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_Nombre;
	private JTextField textField_Sigla;
	private JTextField textField_Descripcion;
	private JTextField textField_FAlta;
	
}