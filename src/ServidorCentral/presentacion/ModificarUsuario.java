package ServidorCentral.presentacion;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ModificarUsuario extends JInternalFrame {
    private static final long serialVersionUID = 1L;
    private JTextField textField;      
    private JTextField textField_1;    
    private JTextField textField_2;    
    private JTextField textField_3;    
    private JTextField textField_4;    
    private JTextField textField_5;    
    private JFormattedTextField campoFecha; 

    public ModificarUsuario() {
        getContentPane().setFont(new Font("Times New Roman", Font.PLAIN, 10));
        getContentPane().setBackground(new Color(255, 255, 255));
        setTitle("Modificar Usuario");
        setSize(552, 420);
        setClosable(true);
        setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        getContentPane().setLayout(null);

        JTextArea txtrSeleccioneUnUsuario = new JTextArea();
        txtrSeleccioneUnUsuario.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        txtrSeleccioneUnUsuario.setText("Seleccione un Usuario");
        txtrSeleccioneUnUsuario.setBounds(10, 13, 138, 27);
        getContentPane().add(txtrSeleccioneUnUsuario);

        JComboBox comboBox = new JComboBox();
        comboBox.setBounds(158, 10, 166, 30);
        getContentPane().add(comboBox);

        JTextArea txtrDatosDelUsuario = new JTextArea();
        txtrDatosDelUsuario.setText("Datos del Usuario");
        txtrDatosDelUsuario.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        txtrDatosDelUsuario.setBounds(182, 50, 158, 27);
        getContentPane().add(txtrDatosDelUsuario);

        textField = new JTextField();  
        textField.setEditable(false);
        textField.setBounds(100, 85, 158, 19);
        getContentPane().add(textField);
        textField.setColumns(10);

        textField_1 = new JTextField(); 
        textField_1.setEditable(false);
        textField_1.setColumns(10);
        textField_1.setBounds(151, 110, 211, 19);
        getContentPane().add(textField_1);

        textField_2 = new JTextField(); 
        textField_2.setEditable(false);
        textField_2.setBounds(107, 139, 166, 19);
        getContentPane().add(textField_2);
        textField_2.setColumns(10);

        textField_3 = new JTextField(); 
        textField_3.setEditable(false);
        textField_3.setBounds(90, 168, 234, 19);
        getContentPane().add(textField_3);
        textField_3.setColumns(10);

        textField_4 = new JTextField(); 
        textField_4.setEditable(false);
        textField_4.setBounds(107, 237, 380, 68);
        getContentPane().add(textField_4);
        textField_4.setColumns(10);

        JLabel lblNewLabel = new JLabel("Nombre :");
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblNewLabel.setBounds(21, 80, 69, 27);
        getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Correo Electronico :");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(21, 110, 122, 16);
        getContentPane().add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Nickname :");
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblNewLabel_2.setBounds(21, 136, 76, 19);
        getContentPane().add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Apellido :");
        lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblNewLabel_3.setBounds(21, 166, 69, 19);
        getContentPane().add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Fecha de nacimiento :");
        lblNewLabel_4.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblNewLabel_4.setBounds(21, 192, 132, 35);
        getContentPane().add(lblNewLabel_4);

        try {
            MaskFormatter mascaraFecha = new MaskFormatter("##/##/####");
            mascaraFecha.setPlaceholderCharacter('_');
            campoFecha = new JFormattedTextField(mascaraFecha);
            campoFecha.setEditable(false);
            campoFecha.setBounds(160, 195, 100, 25);
            getContentPane().add(campoFecha);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        JLabel lblNewLabel_5 = new JLabel("Descripcion :");
        lblNewLabel_5.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblNewLabel_5.setBounds(21, 227, 98, 42);
        getContentPane().add(lblNewLabel_5);

        JLabel lblNewLabel_6 = new JLabel("URL :");
        lblNewLabel_6.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        lblNewLabel_6.setBounds(21, 310, 54, 35);
        getContentPane().add(lblNewLabel_6);

        textField_5 = new JTextField(); 
        textField_5.setEditable(false);
        textField_5.setBounds(66, 315, 286, 28);
        getContentPane().add(textField_5);
        textField_5.setColumns(10);

        JButton btnNewButton = new JButton("Modificar");
        btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        btnNewButton.setBounds(31, 355, 195, 25);
        getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Cancelar");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });
        btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 17));
        btnNewButton_1.setBounds(303, 355, 195, 25);
        getContentPane().add(btnNewButton_1);
    }
}
