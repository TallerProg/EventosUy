package ServidorCentral.presentacion;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.IControllerInstitucion;
import ServidorCentral.logica.IControllerUsuario;
import dataset.CargarDatos;

public class Principal {

    private JFrame frmEventosUy;
    private JDesktopPane desktopPane;

    private IControllerUsuario icu;
    private IControllerEvento ice;
    private IControllerInstitucion ici;

    private AltaUsuario creUsrInternalFrame;
    private ConsultarUsuario conUsrInternalFrame;
    private AltaEdicionEvento creEdiEveInternalFrame;
    private AltaEvento creEveInternalFrame;
    private AltaTipoRegistro creTRegInternalFrame;
    private ConsultaEvento conEveInternalFrame;
    private ConsultaRegistro conRegInternalFrame;
    private ConsultaTipoRegistro conTRegInternalFrame;
    private RegistroEdicionEvento regEdiEveInternalFrame;
    private ConsultaEdicionEvento conEdiEveInternalFrame;
    private AltaInstitucion creInsInternalFrame;
    private AltaPatrocinio altaPatrocinioInternalFrame;
    private ConsultaPatrocinio conPatrocinioInternalFrame;
    private ModificarUsuario modUsuarioInternalFrame;
    private AceptarRechazarEdicion aceptarRechazarEdicionFrame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Principal window = new Principal();
                window.frmEventosUy.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Principal() {
        initialize();

        Factory fabrica = Factory.getInstance();
        icu = fabrica.getIControllerUsuario();
        ice = fabrica.getIControllerEvento();
        ici = fabrica.getIControllerInstitucion();

        creUsrInternalFrame = new AltaUsuario(icu, ici);
        conUsrInternalFrame = new ConsultarUsuario(icu, ice, desktopPane);
        creEdiEveInternalFrame = new AltaEdicionEvento(ice);
        creEveInternalFrame = new AltaEvento(ice);
        creTRegInternalFrame = new AltaTipoRegistro(ice);
        conEveInternalFrame = new ConsultaEvento(ice, desktopPane);
        conRegInternalFrame = new ConsultaRegistro(icu);
        regEdiEveInternalFrame = new RegistroEdicionEvento(ice, icu);
        conTRegInternalFrame = new ConsultaTipoRegistro(ice);
        conEdiEveInternalFrame = new ConsultaEdicionEvento(ice);
        creInsInternalFrame = new AltaInstitucion(ici);
        altaPatrocinioInternalFrame = new AltaPatrocinio(ice, icu);
        conPatrocinioInternalFrame = new ConsultaPatrocinio(ice);
        modUsuarioInternalFrame = new ModificarUsuario(icu, ici);
        aceptarRechazarEdicionFrame = new AceptarRechazarEdicion(ice);

        desktopPane.add(creUsrInternalFrame);
        desktopPane.add(conUsrInternalFrame);
        desktopPane.add(creEdiEveInternalFrame);
        desktopPane.add(creTRegInternalFrame);
        desktopPane.add(conEveInternalFrame);
        desktopPane.add(conRegInternalFrame);
        desktopPane.add(conTRegInternalFrame);
        desktopPane.add(regEdiEveInternalFrame);
        desktopPane.add(conEdiEveInternalFrame);
        desktopPane.add(creInsInternalFrame);
        desktopPane.add(altaPatrocinioInternalFrame);
        desktopPane.add(conPatrocinioInternalFrame);
        desktopPane.add(modUsuarioInternalFrame);
        desktopPane.add(aceptarRechazarEdicionFrame);
    }

    private void initialize() {
        frmEventosUy = new JFrame();
        frmEventosUy.setTitle("EventosUY");
        frmEventosUy.setBounds(100, 100, 1000, 700);
        frmEventosUy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        desktopPane = new JDesktopPane();
        frmEventosUy.setContentPane(desktopPane);

        JMenuBar menuBar = new JMenuBar();
        frmEventosUy.setJMenuBar(menuBar);

        JMenu menuSistema = new JMenu("Sistema");
        menuBar.add(menuSistema);

        JMenuItem menuSalir = new JMenuItem("Salir");
        menuSalir.addActionListener(e -> System.exit(0));

        JMenuItem menuInitDatos = new JMenuItem("Inicializar datos del sistema");
        // Antes llamaba a CargarDatos; ahora solo informa que no está disponible
        menuInitDatos.addActionListener(e -> {
			try {
				CargarDatos.inicializar(icu, ice);
				JOptionPane.showMessageDialog(frmEventosUy, "La carga de datos fue exitosa",
						"Información", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
        });

        menuSistema.add(menuInitDatos);
        menuSistema.add(menuSalir);

        JMenu menuUsuarios = new JMenu("Usuarios");
        menuBar.add(menuUsuarios);

        JMenuItem menuAltaUsuario = new JMenuItem("Alta de Usuario");
        menuAltaUsuario.addActionListener(e -> {
            creUsrInternalFrame.recargarCampos();
            mostrarInternalFrame(creUsrInternalFrame);
        });
        menuUsuarios.add(menuAltaUsuario);

        JMenuItem menuConsultaUsuario = new JMenuItem("Consulta de Usuario");
        menuConsultaUsuario.addActionListener(e -> {
            conUsrInternalFrame.cargarUsuarios();
            conUsrInternalFrame.setVisible(true);
        });
        menuUsuarios.add(menuConsultaUsuario);

        JMenuItem menuModificarUsuario = new JMenuItem("Modificar Usuario");
        menuModificarUsuario.addActionListener(e -> {
            modUsuarioInternalFrame.cargarUsuarios();
            modUsuarioInternalFrame.setVisible(true);
        });
        menuUsuarios.add(menuModificarUsuario);

        JMenuItem menuConsultaRegistro = new JMenuItem("Consulta de Registro");
        menuConsultaRegistro.addActionListener(e -> {
            conRegInternalFrame.cargarUsuarios();
            mostrarInternalFrame(conRegInternalFrame);
        });
        menuUsuarios.add(menuConsultaRegistro);

        JMenu menuEventos = new JMenu("Eventos");
        menuBar.add(menuEventos);

        JMenuItem menuAltaEvento = new JMenuItem("Alta de Evento");
        menuAltaEvento.addActionListener(e -> mostrarInternalFrame(creEveInternalFrame));
        menuEventos.add(menuAltaEvento);

        JMenuItem menuConsultaEvento = new JMenuItem("Consulta de Evento");
        menuConsultaEvento.addActionListener(e -> {
            mostrarInternalFrame(conEveInternalFrame);
            conEveInternalFrame.consultaEventocargar();
            conEveInternalFrame.setVisible(true);
        });
        menuEventos.add(menuConsultaEvento);

        JMenuItem menuAltaEdicion = new JMenuItem("Alta de Edición de Evento");
        menuAltaEdicion.addActionListener(e -> {
            mostrarInternalFrame(creEdiEveInternalFrame);
            creEdiEveInternalFrame.cargarEventos();
            creEdiEveInternalFrame.cargarOrganizadores();
            creEdiEveInternalFrame.setVisible(true);
        });
        menuEventos.add(menuAltaEdicion);

        JMenuItem menuConsultaEdicion = new JMenuItem("Consulta de Edición de Evento");
        menuConsultaEdicion.addActionListener(e -> {
            conEdiEveInternalFrame.cargarEventos();
            mostrarInternalFrame(conEdiEveInternalFrame);
        });
        menuEventos.add(menuConsultaEdicion);

        JMenuItem menuAceptarRechazarEdicion = new JMenuItem("Aceptar/Rechazar Edición de Evento");
        menuAceptarRechazarEdicion.addActionListener(e -> {
            aceptarRechazarEdicionFrame.cargarEventos();
            mostrarInternalFrame(aceptarRechazarEdicionFrame);
        });
        menuEventos.add(menuAceptarRechazarEdicion);

        JMenuItem menuAltaTipoReg = new JMenuItem("Alta de Tipo de Registro");
        menuAltaTipoReg.addActionListener(e -> {
            creTRegInternalFrame.cargarEventos();
            mostrarInternalFrame(creTRegInternalFrame);
        });
        menuEventos.add(menuAltaTipoReg);

        JMenuItem menuConsultaTipoReg = new JMenuItem("Consulta de Tipo de Registro");
        menuConsultaTipoReg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                conTRegInternalFrame.cargarEventos();
                mostrarInternalFrame(conTRegInternalFrame);
            }
        });
        menuEventos.add(menuConsultaTipoReg);

        JMenuItem menuRegistroEdicion = new JMenuItem("Registro a Edición de Evento");
        menuRegistroEdicion.addActionListener(e -> {
            regEdiEveInternalFrame.cargarEventos();
            mostrarInternalFrame(regEdiEveInternalFrame);
        });
        menuEventos.add(menuRegistroEdicion);

        JMenu menuInstitucion = new JMenu("Institucion");
        menuBar.add(menuInstitucion);

        JMenuItem menuAltaInstitucion = new JMenuItem("Alta de Institución");
        menuAltaInstitucion.addActionListener(e -> mostrarInternalFrame(creInsInternalFrame));
        menuInstitucion.add(menuAltaInstitucion);

        JMenuItem menuAltaPatrocinio = new JMenuItem("Alta de Patrocinio");
        menuAltaPatrocinio.addActionListener(e -> {
            altaPatrocinioInternalFrame.cargarEventos();
            mostrarInternalFrame(altaPatrocinioInternalFrame);
        });
        menuInstitucion.add(menuAltaPatrocinio);

        JMenuItem menuConsultaPatrocinio = new JMenuItem("Consulta de Patrocinio");
        menuConsultaPatrocinio.addActionListener(e -> {
            conPatrocinioInternalFrame.cargarEventos();
            mostrarInternalFrame(conPatrocinioInternalFrame);
        });
        menuInstitucion.add(menuConsultaPatrocinio);
    }

    private void mostrarInternalFrame(JInternalFrame frame) {
        if (frame.getParent() == null) {
            desktopPane.add(frame);
        }
        frame.setVisible(true);
        frame.toFront();
        frame.setLocation(
            (desktopPane.getWidth() - frame.getWidth()) / 2,
            (desktopPane.getHeight() - frame.getHeight()) / 2
        );
    }
}
