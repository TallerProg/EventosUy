package ServidorCentral.presentacion;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import java.awt.Component;

public class RegistroEdicionEvento extends JFrame{
	public RegistroEdicionEvento() {
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuSistema = new JMenu("Sistema");
		menuBar.add(menuSistema);
		
		JMenuItem Salir = new JMenuItem("Salir");
		menuSistema.add(Salir);
		
		JMenu menuUsuario = new JMenu("Usuarios");
		menuBar.add(menuUsuario);
		
		JMenuItem AltadeUsuario = new JMenuItem("Crear Usuario");
		menuUsuario.add(AltadeUsuario);
		
		JMenuItem ConsultadeUsuario = new JMenuItem("Consulta de Usuario");
		menuUsuario.add(ConsultadeUsuario);
		
		JMenuItem ModificarDatosUsuario = new JMenuItem("Modificar Datos de Usuario");
		menuUsuario.add(ModificarDatosUsuario);
		
		JMenuItem RegistroEdicionEvento = new JMenuItem("Registro a Edicion de Evento");
		menuUsuario.add(RegistroEdicionEvento);
		
		JMenuItem ConsultaRegistro = new JMenuItem("Consulta de Registro");
		menuUsuario.add(ConsultaRegistro);
		
		JMenu menuEvento = new JMenu("Eventos");
		menuBar.add(menuEvento);
		
		JMenuItem AltaEvento = new JMenuItem("Crear Evento");
		menuEvento.add(AltaEvento);
		
		JMenuItem ConsultadeEvento = new JMenuItem("Consulta de Evento");
		menuEvento.add(ConsultadeEvento);
		
		JMenuItem AltaEdicionEvento = new JMenuItem("Crear Edicion de Evento");
		menuEvento.add(AltaEdicionEvento);
		
		JMenuItem ConsultaEdicionEvento = new JMenuItem("Consulta de Edicion de Evento");
		menuEvento.add(ConsultaEdicionEvento);
		
		JMenuItem AltaTipoRegistro = new JMenuItem("Crear Tipo de Registro");
		menuEvento.add(AltaTipoRegistro);
		
		JMenuItem ConsultaTipodeRegistro = new JMenuItem("Consulta de Tipo de Registro");
		menuEvento.add(ConsultaTipodeRegistro);
		
		JMenuItem AltaCategoria = new JMenuItem("Crear Categoria");
		menuEvento.add(AltaCategoria);
		
		JMenu menuInstitucion = new JMenu("Institucion");
		menuBar.add(menuInstitucion);
		
		JMenuItem AltaInstitucion = new JMenuItem("Crear Institucion");
		menuInstitucion.add(AltaInstitucion);
		
		JMenuItem AltaPatrocinio = new JMenuItem("Crear Patrocinio");
		menuInstitucion.add(AltaPatrocinio);
		
		JMenuItem ConsultaPatrocinio = new JMenuItem("Consulta de Patrocinio");
		menuInstitucion.add(ConsultaPatrocinio);
		
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel("Selecione un evento:");
		getContentPane().add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		getContentPane().add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Selecione una edicion");
		getContentPane().add(lblNewLabel_1);
		
		JComboBox comboBox_1 = new JComboBox();
		getContentPane().add(comboBox_1);
		
		JLabel lblNewLabel_3 = new JLabel("Selecione un Tipo de registro");
		getContentPane().add(lblNewLabel_3);
		
		JComboBox comboBox_3 = new JComboBox();
		getContentPane().add(comboBox_3);
		
		JLabel lblNewLabel_2 = new JLabel("Selecione asistente");
		getContentPane().add(lblNewLabel_2);
		
		JComboBox comboBox_4 = new JComboBox();
		getContentPane().add(comboBox_4);
		
		JButton btnNewButton = new JButton("Registrar asistente");
		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelBoton.add(btnNewButton);
		getContentPane().add(panelBoton);
		
				
		

	
	}
	private static final long serialVersionUID = 7995911936507748724L;
}