package ServidorCentral.presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import ServidorCentral.logica.Edicion;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.TipoRegistro;
import ServidorCentral.logica.IControllerEvento;

public class ConsultaTipoRegistro extends JInternalFrame {
	private JTextField textField_nombre;
	private JTextField textField_1_desc;
	private JTextField textField_2_costo;
	private JTextField textField_3_cupo;
	private JComboBox<String> comboBoxEvento;
	private JComboBox<String> comboBoxEdicion;
	private JComboBox<String> comboBoxTipoRegistro;
    private IControllerEvento controlEvento;

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public ConsultaTipoRegistro(IControllerEvento icu) {
			controlEvento = icu;
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
			
			comboBoxEvento = new JComboBox<>();
			panel.add(comboBoxEvento, BorderLayout.SOUTH);
			
			JPanel panel_1 = new JPanel();
			panelCombos.add(panel_1);
			panel_1.setLayout(new BorderLayout(0, 0));
			
			JLabel lblSelecioneUnaEdicin = new JLabel("Selecione una edición:");
			panel_1.add(lblSelecioneUnaEdicin, BorderLayout.NORTH);
			
			comboBoxEdicion = new JComboBox<>();
			comboBoxEdicion.setEnabled(false);
			panel_1.add(comboBoxEdicion, BorderLayout.SOUTH);
			
			JPanel panel_2 = new JPanel();
			panelCombos.add(panel_2);
			panel_2.setLayout(new BorderLayout(0, 0));
			
			JLabel lblSelecioneElTipo = new JLabel("Selecione el tipo de registro:");
			panel_2.add(lblSelecioneElTipo, BorderLayout.NORTH);
			
			comboBoxTipoRegistro = new JComboBox<>();
			comboBoxTipoRegistro.setEnabled(false);
			panel_2.add(comboBoxTipoRegistro, BorderLayout.SOUTH);
			
			JPanel panel_3 = new JPanel();
			getContentPane().add(panel_3, BorderLayout.CENTER);
			GridBagLayout gbl_panel_3 = new GridBagLayout();
			gbl_panel_3.columnWidths = new int[]{71, 579};     
			gbl_panel_3.rowHeights = new int[]{50, 50, 50, 50}; 
			gbl_panel_3.columnWeights = new double[]{0.3, 1.0}; 
			gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0};

			panel_3.setLayout(gbl_panel_3);
			
			JLabel lblNewLabel = new JLabel("Nombre:");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			panel_3.add(lblNewLabel, gbc_lblNewLabel);
			
			textField_nombre = new JTextField();
			textField_nombre.setEditable(false);
			GridBagConstraints gbc_textField_nombre = new GridBagConstraints();
			gbc_textField_nombre.insets = new Insets(0, 0, 5, 0);
			gbc_textField_nombre.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_nombre.gridx = 1;
			gbc_textField_nombre.gridy = 0;
			panel_3.add(textField_nombre, gbc_textField_nombre);
			textField_nombre.setColumns(10);
			
			JLabel lblNewLabel_1 = new JLabel("Descripción:");
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 0;
			gbc_lblNewLabel_1.gridy = 1;
			panel_3.add(lblNewLabel_1, gbc_lblNewLabel_1);
			
			textField_1_desc = new JTextField();
			textField_1_desc.setEditable(false);
			GridBagConstraints gbc_textField_1_desc = new GridBagConstraints();
			gbc_textField_1_desc.insets = new Insets(0, 0, 5, 0);
			gbc_textField_1_desc.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_1_desc.gridx = 1;
			gbc_textField_1_desc.gridy = 1;
			panel_3.add(textField_1_desc, gbc_textField_1_desc);
			textField_1_desc.setColumns(10);
			
			JLabel lblNewLabel_2 = new JLabel("Costo:");
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_2.gridx = 0;
			gbc_lblNewLabel_2.gridy = 2;
			panel_3.add(lblNewLabel_2, gbc_lblNewLabel_2);
			
			textField_2_costo = new JTextField();
			textField_2_costo.setEditable(false);
			GridBagConstraints gbc_textField_2_costo = new GridBagConstraints();
			gbc_textField_2_costo.insets = new Insets(0, 0, 5, 0);
			gbc_textField_2_costo.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_2_costo.gridx = 1;
			gbc_textField_2_costo.gridy = 2;
			panel_3.add(textField_2_costo, gbc_textField_2_costo);
			textField_2_costo.setColumns(10);
			
			JLabel lblNewLabel_3 = new JLabel("Cupo:");
			GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
			gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_3.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel_3.gridx = 0;
			gbc_lblNewLabel_3.gridy = 3;
			panel_3.add(lblNewLabel_3, gbc_lblNewLabel_3);
			
			textField_3_cupo = new JTextField();
			textField_3_cupo.setEditable(false);
			GridBagConstraints gbc_textField_3_cupo = new GridBagConstraints();
			gbc_textField_3_cupo.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_3_cupo.gridx = 1;
			gbc_textField_3_cupo.gridy = 3;
			panel_3.add(textField_3_cupo, gbc_textField_3_cupo);
			textField_3_cupo.setColumns(10);
			
			comboBoxEvento.addActionListener(e -> {
				 String nombreEventoSeleccionado = (String) comboBoxEvento.getSelectedItem();
				 if (nombreEventoSeleccionado != null) {
					 	comboBoxEdicion.setEnabled(true);
				        try {
							cargarEdiciones(nombreEventoSeleccionado);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

				 }else{
					 	comboBoxEdicion.setEnabled(false);
				        comboBoxEdicion.removeAllItems();
				 }
			});
			
			comboBoxEdicion.addActionListener(e -> {
				 String nombreEdicionSeleccionado = (String) comboBoxEdicion.getSelectedItem();
				 if (nombreEdicionSeleccionado != null) {
					 	comboBoxTipoRegistro.setEnabled(true);
				        try {
							cargarTipoRegistros(nombreEdicionSeleccionado);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

				 }else{
					 	comboBoxTipoRegistro.setEnabled(false);
					 	comboBoxTipoRegistro.removeAllItems();
				 }
			});	
			}
		
		public void cargarEventos() {
			List<Evento> eventos = controlEvento.listarEventos(); 
			List<String> nombres = new java.util.ArrayList<>();
		    for (Evento e : eventos) {
		        nombres.add(e.getNombre());
		    }
		    
		    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
		        nombres.toArray(new String[0])
		    );
		    comboBoxEvento.setModel(model);
		    
		    
		}
		
		public void cargarEdiciones(String nombreEvento){
			Evento evento = controlEvento.findEvento(nombreEvento);
			if (evento != null) {
				List<Edicion> ediciones = evento.getEdiciones();
				
				List<String> nombresEdiciones = new java.util.ArrayList<>();
				for (Edicion ed : ediciones) {
					nombresEdiciones.add(ed.getNombre());
				}

				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
						nombresEdiciones.toArray(new String[0])
				);
				comboBoxEdicion.setModel(model);
			}
		}
		
		public void cargarTipoRegistros(String nombreEdicion) {
			Edicion edicion = controlEvento.findEdicion(nombreEdicion);
			if (edicion != null) {
				List<TipoRegistro> tipoR = edicion.getTipoRegistros();			
				List<String> nombreRegistros = new java.util.ArrayList<>();
				for (TipoRegistro tr : tipoR) {
				    nombreRegistros.add(tr.getNombre());
				}

				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
						nombreRegistros.toArray(new String[0])
				);
				comboBoxTipoRegistro.setModel(model);
			}
			
		}
		
}