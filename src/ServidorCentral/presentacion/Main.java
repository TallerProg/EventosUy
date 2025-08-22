package ServidorCentral.presentacion;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Main extends JFrame{
	public Main() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		
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
	
	}
	private static final long serialVersionUID = 7995911936507748724L;

}