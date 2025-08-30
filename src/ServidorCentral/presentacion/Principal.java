package ServidorCentral.presentacion;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import cargardatospk.CargarDatos;
import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.IControllerEvento;

import javax.swing.JMenu;


public class Principal {

    private JFrame frmEventosUy;
    private JDesktopPane desktopPane;   

    private IControllerUsuario ICU;
    private IControllerEvento ICE;

    // InternalFrames
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

        // Inicialización de la fábrica y controladores
        Factory fabrica = Factory.getInstance();
        ICU = fabrica.getIControllerUsuario();
        ICE = fabrica.getIControllerEvento();

        // Inicializar los InternalFrames (inicialmente ocultos)
        creUsrInternalFrame = new AltaUsuario(ICU);
        conUsrInternalFrame = new ConsultarUsuario(ICU, ICE, desktopPane);
        creEdiEveInternalFrame = new AltaEdicionEvento(ICE);
        creEveInternalFrame = new AltaEvento(ICE);
        creTRegInternalFrame = new AltaTipoRegistro(ICE);
        conEveInternalFrame = new ConsultaEvento(ICE,desktopPane);
        conRegInternalFrame = new ConsultaRegistro(ICU);
        regEdiEveInternalFrame = new RegistroEdicionEvento(ICE, ICU);
        conTRegInternalFrame = new ConsultaTipoRegistro(ICE);
        conEdiEveInternalFrame = new ConsultaEdicionEvento(ICE);

        // Agregar los InternalFrames al DesktopPane
        desktopPane.add(creUsrInternalFrame);
        desktopPane.add(conUsrInternalFrame);
        desktopPane.add(creEdiEveInternalFrame);
        desktopPane.add(creTRegInternalFrame);
        desktopPane.add(conEveInternalFrame);
        desktopPane.add(conRegInternalFrame);
        desktopPane.add(conTRegInternalFrame);
        desktopPane.add(regEdiEveInternalFrame);
        desktopPane.add(conEdiEveInternalFrame);
    }

    private void initialize() {
        frmEventosUy = new JFrame();
        frmEventosUy.setTitle("EventosUY");
        frmEventosUy.setBounds(100, 100, 1000, 700);
        frmEventosUy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // DesktopPane
        desktopPane = new JDesktopPane();
        frmEventosUy.setContentPane(desktopPane);

        // Menú principal
        JMenuBar menuBar = new JMenuBar();
        frmEventosUy.setJMenuBar(menuBar);

        // Menú Sistema
        JMenu menuSistema = new JMenu("Sistema");
        menuBar.add(menuSistema);

        JMenuItem menuSalir = new JMenuItem("Salir");
        menuSalir.addActionListener(e -> System.exit(0));
        
        JMenuItem mntmNewMenuItem = new JMenuItem("Inicializar datos del sistema");
        menuSistema.add(mntmNewMenuItem);
        menuSistema.add(menuSalir);
        mntmNewMenuItem.addActionListener(e -> {
            try {
				CargarDatos.inicializar(ICU,ICE);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
            JOptionPane.showMessageDialog(frmEventosUy, "Datos del sistema inicializados correctamente.");
            creEveInternalFrame = new AltaEvento(ICE);
            desktopPane.add(creEveInternalFrame);
        });

        // Menú Usuarios
        JMenu menuUsuarios = new JMenu("Usuarios");
        menuBar.add(menuUsuarios);
        

        JMenuItem menuAltaUsuario = new JMenuItem("Alta de Usuario");
        menuAltaUsuario.addActionListener(e -> mostrarInternalFrame(creUsrInternalFrame));
        menuUsuarios.add(menuAltaUsuario);

        JMenuItem menuConsultaUsuario = new JMenuItem("Consulta de Usuario");
        menuConsultaUsuario.addActionListener(e -> {
        	conUsrInternalFrame.cargarUsuarios();
        	conUsrInternalFrame.setVisible(true);
        });
        menuUsuarios.add(menuConsultaUsuario);
        
                JMenuItem menuConsultaRegistro = new JMenuItem("Consulta de Registro");
                menuUsuarios.add(menuConsultaRegistro);
                menuConsultaRegistro.addActionListener(e -> {
                	conRegInternalFrame.cargarUsuarios();
                mostrarInternalFrame(conRegInternalFrame);});
         
                
                menuUsuarios.add(menuConsultaRegistro);

        // Menú Eventos
        JMenu menuEventos = new JMenu("Eventos");
        menuBar.add(menuEventos);

        JMenuItem menuAltaEvento = new JMenuItem("Alta de Evento");
        menuAltaEvento.addActionListener(e -> mostrarInternalFrame(creEveInternalFrame));
        menuEventos.add(menuAltaEvento);

        JMenuItem menuConsultaEvento = new JMenuItem("Consulta de Evento");
        menuConsultaEvento.addActionListener(e -> {
            mostrarInternalFrame(conEveInternalFrame);
            conEveInternalFrame.ConsultaEventocargar();
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

        
        JMenuItem menuAltaTipoReg = new JMenuItem("Alta de Tipo de Registro");
        menuAltaTipoReg.addActionListener(e -> {
            creTRegInternalFrame.setVisible(true);
            creTRegInternalFrame.cargarEventos();
        });
        menuEventos.add(menuAltaTipoReg);
                
         JMenuItem menuConsultaTipoReg = new JMenuItem("Consulta de Tipo de Registro");
          menuConsultaTipoReg.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
           conTRegInternalFrame.cargarEventos();
           conTRegInternalFrame.setVisible(true);
               }
           });
            menuEventos.add(menuConsultaTipoReg);
                        
            JMenuItem menuRegistroEdicion = new JMenuItem("Registro a Edición de Evento");
            menuRegistroEdicion.addActionListener(e -> {
                regEdiEveInternalFrame.cargarEventos();
                mostrarInternalFrame(regEdiEveInternalFrame);
            });
            menuEventos.add(menuRegistroEdicion);

        menuEventos.add(menuRegistroEdicion);
        menuRegistroEdicion.addActionListener(e -> mostrarInternalFrame(regEdiEveInternalFrame));
                                
                        menuConsultaTipoReg.addActionListener(e -> mostrarInternalFrame(conTRegInternalFrame));
                menuAltaTipoReg.addActionListener(e -> mostrarInternalFrame(creTRegInternalFrame));
    }

    // Método auxiliar para mostrar InternalFrames centrados
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