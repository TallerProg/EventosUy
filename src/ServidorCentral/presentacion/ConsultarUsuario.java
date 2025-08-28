package ServidorCentral.presentacion;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;

public class ConsultarUsuario extends JInternalFrame{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	public ConsultarUsuario() {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Ingrese su nickname:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 21, 150, 13);
		getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField.setBounds(152, 18, 164, 19);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 127, 434, 143);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Apellido:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4.setBounds(23, 10, 40, 13);
		panel.add(lblNewLabel_4);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_3.setEditable(false);
		textField_3.setBounds(80, 7, 330, 19);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_4_1 = new JLabel("Fecha Nacimiento:");
		lblNewLabel_4_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_4_1.setBounds(23, 37, 122, 13);
		panel.add(lblNewLabel_4_1);
		
		textField_4 = new JTextField();
		textField_4.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		textField_4.setBounds(126, 33, 284, 19);
		panel.add(textField_4);
		
		JLabel lblNewLabel_5 = new JLabel("Lista de registros a ediciones de eventos:");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_5.setBounds(23, 74, 387, 13);
		panel.add(lblNewLabel_5);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(23, 97, 387, 19);
		panel.add(comboBox);
				
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(326, 17, 84, 21);
		getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Información de Usuario");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(51, 57, 164, 13);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Nombre:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(23, 80, 45, 13);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Correo:");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNewLabel_3.setBounds(23, 106, 45, 13);
		getContentPane().add(lblNewLabel_3);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_1.setEditable(false);
		textField_1.setBounds(80, 77, 330, 19);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField_2.setEditable(false);
		textField_2.setBounds(80, 103, 330, 19);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
	}}
