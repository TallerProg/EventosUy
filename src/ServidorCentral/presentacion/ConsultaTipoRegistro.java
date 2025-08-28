package ServidorCentral.presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ConsultaTipoRegistro extends JInternalFrame {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public ConsultaTipoRegistro() {
			setResizable(true);
	        setIconifiable(true);
	        setMaximizable(true);
	        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	        setClosable(true);
			setTitle("Consultar tipo registro");
 	        setSize(700, 400);
			getContentPane().setLayout(new BorderLayout(0, 0));
			
			JPanel panelCombos = new JPanel();
			getContentPane().add(panelCombos, BorderLayout.NORTH);
			panelCombos.setLayout(new GridLayout(1, 3, 0, 0));
			
			JPanel panel = new JPanel();
			panelCombos.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			
			JLabel lblSelecioneUnEvento = new JLabel("Selecione un evento:");
			panel.add(lblSelecioneUnEvento, BorderLayout.NORTH);
			
			JComboBox comboBoxEvento = new JComboBox();
			panel.add(comboBoxEvento, BorderLayout.SOUTH);
			
			JPanel panel_1 = new JPanel();
			panelCombos.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			JLabel lblSelecioneUnaEdicin = new JLabel("Selecione una edición:");
			panel_1.add(lblSelecioneUnaEdicin, BorderLayout.NORTH);
			
			JComboBox combroBoxEdicion = new JComboBox();
			panel_1.add(combroBoxEdicion, BorderLayout.SOUTH);
			
			JPanel panel_2 = new JPanel();
			panelCombos.add(panel_2);
			panel_2.setLayout(new BorderLayout(0, 0));
			
			JLabel lblSelecioneElTipo = new JLabel("Selecione el tipo de registro:");
			panel_2.add(lblSelecioneElTipo, BorderLayout.NORTH);
			
			JComboBox comboBoxTipoRegistro = new JComboBox();
			panel_2.add(comboBoxTipoRegistro, BorderLayout.SOUTH);
			
			JTextArea informacionTipoRegistro = new JTextArea();
			informacionTipoRegistro.setEditable(false);
			informacionTipoRegistro.setText("Aca irian los datos del tipo registro");
			getContentPane().add(informacionTipoRegistro, BorderLayout.CENTER);

	}
}