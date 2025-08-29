package ServidorCentral.presentacion;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.ManejadorEvento;
import ServidorCentral.logica.Categoria;
import ServidorCentral.logica.Edicion;


import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.awt.event.ActionEvent;

public class AltaTipoRegistro extends JInternalFrame {
	
	private IControllerEvento controlEvento;
	
	private JComboBox<Evento> comboBoxEvento;
	private JComboBox<Edicion> comboBoxEdicion;
	private JTextArea textAreaDescripcion;
	private JTextField textFieldNombre;
	private JFormattedTextField textFieldCosto;
	private JFormattedTextField textFieldCupo;
	private JButton btnAceptar;
    private JButton btnCancelar;
    		
	public AltaTipoRegistro(IControllerEvento ice) {
		controlEvento = ice;
		
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setTitle("Alta de Tipo de Registro");
        setBounds(10, 10, 505, 460);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100, 292, 0, 120, 0};
		gridBagLayout.rowHeights = new int[]{60, 5, 0, 0, 65, 0, 0, 0, 60, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblEvento = new JLabel("Evento:");
		GridBagConstraints gbc_lblEvento = new GridBagConstraints();
		gbc_lblEvento.anchor = GridBagConstraints.EAST;
		gbc_lblEvento.fill = GridBagConstraints.VERTICAL;
		gbc_lblEvento.insets = new Insets(0, 0, 5, 5);
		gbc_lblEvento.gridx = 0;
		gbc_lblEvento.gridy = 1;
		getContentPane().add(lblEvento, gbc_lblEvento);
		
		comboBoxEvento = new JComboBox();
		GridBagConstraints gbc_comboBoxEvento = new GridBagConstraints();
		gbc_comboBoxEvento.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEvento.fill = GridBagConstraints.BOTH;
		gbc_comboBoxEvento.gridx = 1;
		gbc_comboBoxEvento.gridy = 1;
		getContentPane().add(comboBoxEvento, gbc_comboBoxEvento);		
		
		JLabel lblEdicion = new JLabel("Edición:");
		GridBagConstraints gbc_lblEdicion = new GridBagConstraints();
		gbc_lblEdicion.anchor = GridBagConstraints.EAST;
		gbc_lblEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_lblEdicion.gridx = 0;
		gbc_lblEdicion.gridy = 2;
		getContentPane().add(lblEdicion, gbc_lblEdicion);
		
		comboBoxEdicion = new JComboBox();
		GridBagConstraints gbc_comboBoxEdicion = new GridBagConstraints();
		gbc_comboBoxEdicion.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxEdicion.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxEdicion.gridx = 1;
		gbc_comboBoxEdicion.gridy = 2;
		getContentPane().add(comboBoxEdicion, gbc_comboBoxEdicion);
		
		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 3;
		getContentPane().add(lblNombre, gbc_lblNombre);
		
		textFieldNombre = new JTextField();
		GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
		gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNombre.gridx = 1;
		gbc_textFieldNombre.gridy = 3;
		getContentPane().add(textFieldNombre, gbc_textFieldNombre);
		textFieldNombre.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripción:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 0;
		gbc_lblDescripcion.gridy = 4;
		getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		JScrollPane scrollPaneDescripcion = new JScrollPane();
		GridBagConstraints gbc_scrollPaneDescripcion = new GridBagConstraints();
		gbc_scrollPaneDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneDescripcion.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneDescripcion.gridx = 1;
		gbc_scrollPaneDescripcion.gridy = 4;
		getContentPane().add(scrollPaneDescripcion, gbc_scrollPaneDescripcion);
		
		textAreaDescripcion = new JTextArea();
		scrollPaneDescripcion.setViewportView(textAreaDescripcion);
		
		JLabel lblCosto = new JLabel("Costo:");
		GridBagConstraints gbc_lblCosto = new GridBagConstraints();
		gbc_lblCosto.anchor = GridBagConstraints.EAST;
		gbc_lblCosto.insets = new Insets(0, 0, 5, 5);
		gbc_lblCosto.gridx = 0;
		gbc_lblCosto.gridy = 5;
		getContentPane().add(lblCosto, gbc_lblCosto);
		
		NumberFormat formatoFloat = NumberFormat.getNumberInstance();
		NumberFormatter formatter = new NumberFormatter(formatoFloat);
		formatter.setValueClass(Float.class); 
		formatter.setAllowsInvalid(false);     
		formatter.setMinimum(0.0);          
		textFieldCosto = new JFormattedTextField();
		GridBagConstraints gbc_formattedTextFieldCosto = new GridBagConstraints();
		gbc_formattedTextFieldCosto.insets = new Insets(0, 0, 5, 5);
		gbc_formattedTextFieldCosto.fill = GridBagConstraints.HORIZONTAL;
		gbc_formattedTextFieldCosto.gridx = 1;
		gbc_formattedTextFieldCosto.gridy = 5;
		getContentPane().add(textFieldCosto, gbc_formattedTextFieldCosto);
		
		JLabel lblCupo = new JLabel("Cupo:");
		GridBagConstraints gbc_lblCupo = new GridBagConstraints();
		gbc_lblCupo.anchor = GridBagConstraints.EAST;
		gbc_lblCupo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCupo.gridx = 0;
		gbc_lblCupo.gridy = 6;
		getContentPane().add(lblCupo, gbc_lblCupo);
		
		NumberFormat formatoInteger = NumberFormat.getIntegerInstance();
		NumberFormatter formatter2 = new NumberFormatter(formatoInteger);
		formatter2.setValueClass(Integer.class); 
		formatter2.setAllowsInvalid(false);     
		formatter2.setMinimum(0); 
		textFieldCupo = new JFormattedTextField();
        GridBagConstraints gbc_formattedTextFieldCupo = new GridBagConstraints();
        gbc_formattedTextFieldCupo.insets = new Insets(0, 0, 5, 5);
        gbc_formattedTextFieldCupo.fill = GridBagConstraints.HORIZONTAL;
        gbc_formattedTextFieldCupo.gridx = 1;
        gbc_formattedTextFieldCupo.gridy = 6;
        getContentPane().add(textFieldCupo, gbc_formattedTextFieldCupo);
		
		btnCancelar = new JButton("Cancelar");
        GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
        gbc_btnCancelar.insets = new Insets(10, 20, 5, 80); 
        gbc_btnCancelar.gridx = 1;  
        gbc_btnCancelar.gridy = 7;
        gbc_btnCancelar.weightx = 0; 
        gbc_btnCancelar.anchor = GridBagConstraints.LINE_END; 
        Dimension mismoTamaño = new Dimension(100, 25);
        btnCancelar.setPreferredSize(mismoTamaño);
        getContentPane().add(btnCancelar, gbc_btnCancelar);

        btnAceptar = new JButton("Aceptar");
        GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
        gbc_btnAceptar.insets = new Insets(10, 50, 5, 20);
        gbc_btnAceptar.gridx = 1; 
        gbc_btnAceptar.gridy = 7;
        gbc_btnAceptar.weightx = 0;
        gbc_btnAceptar.anchor = GridBagConstraints.LINE_START;
        btnAceptar.setPreferredSize(mismoTamaño);
        getContentPane().add(btnAceptar, gbc_btnAceptar);
		
        //COMBOBOXES CON LISTENERS
        for (Evento e : ManejadorEvento.getInstancia().listarEventos()) {
		    comboBoxEvento.addItem(e);
		}
        
        comboBoxEvento.addActionListener(click -> {
        	Evento eventoSeleccionado = (Evento) comboBoxEvento.getSelectedItem();
        	comboBoxEdicion.removeAllItems();
        	if (eventoSeleccionado != null) {
        		for (Edicion ed : eventoSeleccionado.getEdiciones()) {
        			comboBoxEdicion.addItem(ed);
        		}
        	}
        });
        
        //LISTENERS
        btnCancelar.addActionListener(click -> this.setVisible(false));
        btnAceptar.addActionListener(click -> {
            String nombre = textFieldNombre.getText().trim();
            String descripcion = textAreaDescripcion.getText().trim();
            Float costo = (Float) textFieldCosto.getValue();
            Integer cupo = (Integer) textFieldCupo.getValue();
            Evento eventoSeleccionado = (Evento) comboBoxEvento.getSelectedItem();
            Edicion edicionSeleccionada = (Edicion) comboBoxEdicion.getSelectedItem();
            
            //CAMPOS VACÍOS
            if (nombre.isEmpty() || descripcion.isEmpty() || costo == null || cupo == null || eventoSeleccionado == null || edicionSeleccionada == null) {
            	JOptionPane.showMessageDialog(this, "Debe seleccionar un evento, una edición y completar todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //ALTA
            try {
                controlEvento.altaTipoRegistro(nombre, descripcion, costo, cupo, edicionSeleccionada);
                JOptionPane.showMessageDialog(this, "Tipo de registro creado correctamente");
                this.setVisible(false);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}