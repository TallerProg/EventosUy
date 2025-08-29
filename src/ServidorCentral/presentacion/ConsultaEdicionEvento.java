package ServidorCentral.presentacion;

import javax.swing.JInternalFrame;

import ServidorCentral.logica.IControllerEvento;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

	public class ConsultaEdicionEvento extends JInternalFrame{

	private IControllerEvento controller;
	
		
		public ConsultaEdicionEvento(IControllerEvento ice) {
	        this.controller = ice; 
	        getContentPane().setLayout(null);
	        
	        setClosable(true);   
	        setIconifiable(true); 
	        setMaximizable(true); 
	        setResizable(true);  
	        
	        JComboBox comboBox = new JComboBox();
	        comboBox.setBounds(171, 21, 219, 28);
	        getContentPane().add(comboBox);
	        
	        JLabel lblNewLabel_1 = new JLabel("Datos de la Edicion ");
	        lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 17));
	        lblNewLabel_1.setBounds(181, 54, 261, 28);
	        getContentPane().add(lblNewLabel_1);
	        
	        JLabel lblNewLabel = new JLabel("Seleccionar Evento");
	        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	        lblNewLabel.setBounds(28, 24, 120, 20);
	        getContentPane().add(lblNewLabel);
	        
	        JLabel lblNombre = new JLabel("Nombre :\r\n");
	        lblNombre.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	        lblNombre.setBounds(28, 102, 69, 20);
	        getContentPane().add(lblNombre);
	        
	        JLabel lblSigla = new JLabel("Sigla :");
	        lblSigla.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	        lblSigla.setBounds(280, 102, 45, 20);
	        getContentPane().add(lblSigla);
	        
	        JLabel lblCiudad = new JLabel("Ciudad :\r\n\r\n");
	        lblCiudad.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	        lblCiudad.setBounds(28, 153, 69, 20);
	        getContentPane().add(lblCiudad);
	        
	        JLabel lblFechaDeAlta = new JLabel("Fecha de Alta :");
	        lblFechaDeAlta.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	        lblFechaDeAlta.setBounds(28, 204, 101, 20);
	        getContentPane().add(lblFechaDeAlta);
	        
	        JLabel lblPais = new JLabel("Pais :");
	        lblPais.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	        lblPais.setBounds(280, 153, 45, 20);
	        getContentPane().add(lblPais);
	        
	        JLabel lblFechaDeInicio = new JLabel("Fecha de Inicio :");
	        lblFechaDeInicio.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	        lblFechaDeInicio.setBounds(280, 204, 107, 20);
	        getContentPane().add(lblFechaDeInicio);
	        
	        JLabel lblFechaDeFin = new JLabel("Fecha de Fin :\r\n");
	        lblFechaDeFin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	        lblFechaDeFin.setBounds(28, 255, 101, 20);
	        getContentPane().add(lblFechaDeFin);
	        
	        NombreEdicion = new JTextField();
	        NombreEdicion.setEditable(false);
	        NombreEdicion.setBounds(95, 100, 144, 28);
	        getContentPane().add(NombreEdicion);
	        NombreEdicion.setColumns(10);
	        
	        CiudadEdicion = new JTextField();
	        CiudadEdicion.setEditable(false);
	        CiudadEdicion.setColumns(10);
	        CiudadEdicion.setBounds(95, 145, 144, 28);
	        getContentPane().add(CiudadEdicion);
	        
	        SiglaEdicion = new JTextField();
	        SiglaEdicion.setEditable(false);
	        SiglaEdicion.setColumns(10);
	        SiglaEdicion.setBounds(334, 100, 86, 28);
	        getContentPane().add(SiglaEdicion);
	        
	        PaisEdicion = new JTextField();
	        PaisEdicion.setEditable(false);
	        PaisEdicion.setColumns(10);
	        PaisEdicion.setBounds(323, 151, 144, 28);
	        getContentPane().add(PaisEdicion);
	        
	        FAltaEdicion = new JTextField();
	        FAltaEdicion.setEditable(false);
	        FAltaEdicion.setColumns(10);
	        FAltaEdicion.setBounds(123, 202, 130, 28);
	        getContentPane().add(FAltaEdicion);
	        
	        FInicioEdicion = new JTextField();
	        FInicioEdicion.setEditable(false);
	        FInicioEdicion.setColumns(10);
	        FInicioEdicion.setBounds(390, 196, 120, 28);
	        getContentPane().add(FInicioEdicion);
	        
	        FFinEdicion = new JTextField();
	        FFinEdicion.setEditable(false);
	        FFinEdicion.setColumns(10);
	        FFinEdicion.setBounds(123, 247, 130, 28);
	        getContentPane().add(FFinEdicion);
	        
	        JLabel lblOrganizador = new JLabel("Organizador :\r\n");
	        lblOrganizador.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	        lblOrganizador.setBounds(280, 255, 101, 20);
	        getContentPane().add(lblOrganizador);
	        
	        JLabel lblTipoDeRegistro = new JLabel("Tipos de Registro:\r\n");
	        lblTipoDeRegistro.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	        lblTipoDeRegistro.setBounds(28, 298, 120, 20);
	        getContentPane().add(lblTipoDeRegistro);
	        
	        JLabel lblRegistros = new JLabel("Registros :\r\n");
	        lblRegistros.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	        lblRegistros.setBounds(299, 298, 101, 20);
	        getContentPane().add(lblRegistros);
	        
	        JLabel lblPatrocinios = new JLabel("Patrocinios :\r\n");
	        lblPatrocinios.setFont(new Font("Times New Roman", Font.PLAIN, 15));
	        lblPatrocinios.setBounds(28, 349, 120, 20);
	        getContentPane().add(lblPatrocinios);
	        
	        textField_1 = new JTextField();
	        textField_1.setEditable(false);
	        textField_1.setColumns(10);
	        textField_1.setBounds(374, 251, 130, 28);
	        getContentPane().add(textField_1);
	        
	        JComboBox comboBox_1 = new JComboBox();
	        comboBox_1.setBounds(143, 295, 137, 28);
	        getContentPane().add(comboBox_1);
	        
	        JComboBox comboBox_1_1 = new JComboBox();
	        comboBox_1_1.setBounds(373, 290, 161, 28);
	        getContentPane().add(comboBox_1_1);
	        
	        JComboBox comboBox_1_2 = new JComboBox();
	        comboBox_1_2.setBounds(116, 341, 164, 28);
	        getContentPane().add(comboBox_1_2);}
	/**
	 * 
	 */
	private static final long serialVersionUID = -2318682568078151267L;
	private JTextField NombreEdicion;
	private JTextField CiudadEdicion;
	private JTextField SiglaEdicion;
	private JTextField PaisEdicion;
	private JTextField FAltaEdicion;
	private JTextField FInicioEdicion;
	private JTextField FFinEdicion;
	private JTextField textField_1;
}