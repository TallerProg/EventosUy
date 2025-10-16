package ServidorCentral.presentacion;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.IControllerEvento;

import javax.swing.JMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Principal{
	
	private JFrame frmEventosUy;
	private IControllerUsuario ICU;
	private IControllerEvento ICE;
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
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Principal window = new Principal();
                    window.frmEventosUy.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	
	public Principal() {
		//para que compile nomas
		  frmEventosUy = new JFrame();
		    frmEventosUy.setTitle("EventosUY");
		    frmEventosUy.setBounds(100, 100, 800, 600);
		    frmEventosUy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   //---------
        Factory fabrica = Factory.getInstance();
        ICU = fabrica.getIControllerUsuario();
        ICE = fabrica.getIControllerEvento();

	}

        
        
	private static final long serialVersionUID = 7048783952493771091L;

}
