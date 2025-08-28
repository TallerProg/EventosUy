package ServidorCentral.presentacion;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.*;

public class ConsultaRegistro extends JInternalFrame {
	private JTextField textField_finicio;
	private JTextField textField_1_costo;
	private JComboBox<String> comboBoxUsuario;
	private JComboBox<String> comboBoxRegistro;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ConsultaRegistro() {
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
		gbl_panel_3.columnWidths = new int[]{71, 579};     
		gbl_panel_3.rowHeights = new int[]{50, 50, 50, 50}; 
		gbl_panel_3.columnWeights = new double[]{0.3, 1.0}; 
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};

		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblNewLabel = new JLabel("Fecha inicio:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel_3.add(lblNewLabel, gbc_lblNewLabel);
		
		textField_finicio = new JTextField();
		textField_finicio.setEditable(false);
		GridBagConstraints gbc_textField_finicio = new GridBagConstraints();
		gbc_textField_finicio.insets = new Insets(0, 0, 5, 0);
		gbc_textField_finicio.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_finicio.gridx = 1;
		gbc_textField_finicio.gridy = 0;
		panel_3.add(textField_finicio, gbc_textField_finicio);
		textField_finicio.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Costo:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		panel_3.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textField_1_costo = new JTextField();
		textField_1_costo.setEditable(false);
		GridBagConstraints gbc_textField_1_costo = new GridBagConstraints();
		gbc_textField_1_costo.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1_costo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1_costo.gridx = 1;
		gbc_textField_1_costo.gridy = 1;
		panel_3.add(textField_1_costo, gbc_textField_1_costo);
		textField_1_costo.setColumns(10);
	}
}