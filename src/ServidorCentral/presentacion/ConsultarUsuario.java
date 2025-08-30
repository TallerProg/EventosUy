package ServidorCentral.presentacion;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ServidorCentral.logica.Asistente;
import ServidorCentral.logica.DTUsuarioLista;
import ServidorCentral.logica.DTUsuarioListaConsulta;
import ServidorCentral.logica.Edicion;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.ManejadorEvento;
import ServidorCentral.logica.ManejadorUsuario;
import ServidorCentral.logica.Organizador;
import ServidorCentral.logica.Registro;
import ServidorCentral.logica.Usuario;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsultarUsuario extends JInternalFrame{
	
	private IControllerUsuario controlUsuario;
	private IControllerEvento controlEvento;
	
	private JComboBox<Usuario> comboBoxUsuario;
	private JComboBox<Edicion> comboBoxRegistros;
	private JComboBox<Edicion> comboBoxOrganizadas;
	private JTextField textFieldNickname;
	private JTextField textFieldCorreo;
	private JTextField textFieldNombre;
	private JTextField textFieldApellido;
	private JTextField textFieldFechaNacimiento;
	private JTextField textFieldDescripcion;
	private JTextField textFieldURL;
	
    private Integer row = 1;
    
	public ConsultarUsuario(IControllerUsuario icu, IControllerEvento ice) {
		controlUsuario = icu;
		controlEvento = ice;
		
		setResizable(true);
        setIconifiable(true);
        setMaximizable(true);
        setClosable(true);
        setTitle("Consulta de usuario");
        setBounds(10, 10, 505, 460);
		
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{39, 182, 280, 400, -80, 0};
		gridBagLayout.rowHeights = new int[]{153, 15, 0, 0, 0, 0, 0, 0, 167, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.anchor = GridBagConstraints.EAST;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = row;
		getContentPane().add(lblUsuario, gbc_lblUsuario);
		
		comboBoxUsuario = new JComboBox();
		GridBagConstraints gbc_comboBoxUsuario = new GridBagConstraints();
		gbc_comboBoxUsuario.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxUsuario.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxUsuario.gridx = 2;
		gbc_comboBoxUsuario.gridy = row;
		getContentPane().add(comboBoxUsuario, gbc_comboBoxUsuario);
		row++;
		
		JLabel lblNickname = new JLabel("Nickname:");
		GridBagConstraints gbc_lblNickname = new GridBagConstraints();
		gbc_lblNickname.anchor = GridBagConstraints.EAST;
		gbc_lblNickname.insets = new Insets(0, 0, 5, 5);
		gbc_lblNickname.gridx = 1;
		gbc_lblNickname.gridy = row;
		getContentPane().add(lblNickname, gbc_lblNickname);
		
		textFieldNickname = new JTextField();
		textFieldNickname.setEditable(false);
		GridBagConstraints gbc_textFieldNickname = new GridBagConstraints();
		gbc_textFieldNickname.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldNickname.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldNickname.gridx = 2;
		gbc_textFieldNickname.gridy = row;
		getContentPane().add(textFieldNickname, gbc_textFieldNickname);
		textFieldNickname.setColumns(10);
		row++;
		
		JLabel lblCorreo = new JLabel("Correo:");
		GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
		gbc_lblCorreo.anchor = GridBagConstraints.EAST;
		gbc_lblCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_lblCorreo.gridx = 1;
		gbc_lblCorreo.gridy = row;
		getContentPane().add(lblCorreo, gbc_lblCorreo);
		
		textFieldCorreo = new JTextField();
		textFieldCorreo.setEditable(false);
		GridBagConstraints gbc_textFieldCorreo = new GridBagConstraints();
		gbc_textFieldCorreo.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldCorreo.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldCorreo.gridx = 2;
		gbc_textFieldCorreo.gridy = row;
		getContentPane().add(textFieldCorreo, gbc_textFieldCorreo);
		textFieldCorreo.setColumns(10);
		row++;
		
		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = row;
		getContentPane().add(lblNombre, gbc_lblNombre);
		setMaximizable(true);
		setIconifiable(true);
		setClosable(true);
		
		textFieldNombre = new JTextField();
        textFieldNombre.setEditable(false);
        GridBagConstraints gbc_textFieldNombre = new GridBagConstraints();
        gbc_textFieldNombre.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldNombre.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldNombre.gridx = 2;
        gbc_textFieldNombre.gridy = row;
        getContentPane().add(textFieldNombre, gbc_textFieldNombre);
        textFieldNombre.setColumns(10);
        row++;
        
        //CAMPOS DEPENDIENTES
		JLabel lblApellido = new JLabel("Apellido:");
        textFieldApellido = new JTextField();
        JLabel lblFecha = new JLabel("Fecha de Nac.:");
        textFieldFechaNacimiento = new JTextField();
        JLabel lblListaRegistros = new JLabel("Lista de registros:");
        comboBoxRegistros = new JComboBox<>();
        
        JLabel lblDescripcion = new JLabel("Descripción:");
        textFieldDescripcion = new JTextField();
        JLabel lblURL = new JLabel("URL (opcional):");
        textFieldURL = new JTextField();
        JLabel lblListaOrganizadas = new JLabel("Eventos organizados:");
        comboBoxOrganizadas = new JComboBox<>();
        
        //COMBOBOXES CON LISTENERS
        List<Usuario> usuarios = ManejadorUsuario.getinstance().listarUsuarios();
        if (usuarios.isEmpty()) {
            comboBoxUsuario.setEnabled(false);
            comboBoxUsuario.setEnabled(false);
        } else {
        	comboBoxUsuario.setEnabled(true);
            DefaultComboBoxModel<Usuario> modelUsuarios = new DefaultComboBoxModel<>();
            for (Usuario u : usuarios) {
                modelUsuarios.addElement(u);
            }
            comboBoxUsuario.setModel(modelUsuarios);
        }
        
        comboBoxUsuario.addActionListener(e -> {
            actualizarCampos(row, lblApellido, textFieldApellido, lblFecha, textFieldFechaNacimiento,
                             lblListaRegistros, comboBoxRegistros, lblDescripcion, textFieldDescripcion,
                             lblURL, textFieldURL, lblListaOrganizadas, comboBoxOrganizadas);

            Usuario usuarioSeleccionado = (Usuario) comboBoxUsuario.getSelectedItem();
            if (usuarioSeleccionado == null) return;

            DTUsuarioListaConsulta dt = controlUsuario.ConsultaDeUsuario(usuarioSeleccionado.getNickname());
            if (dt == null) return;
            //CASO ASIS
            if (usuarioSeleccionado instanceof Asistente) {
                textFieldApellido.setText(dt.getApellido());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                textFieldFechaNacimiento.setText(dt.getFNacimiento().format(formatter));
                comboBoxOrganizadas.removeAllItems();
                List<Edicion> ediciones = dt.getEdiciones();
                if (ediciones != null) {
                    for (Edicion ed : ediciones) {
                        comboBoxOrganizadas.addItem(ed); //Es organizadas pero en realidad referencia a registradas
                    }
                }
            //CASO ORG    	
            } else if (usuarioSeleccionado instanceof Organizador) {
                textFieldDescripcion.setText(dt.getDescripcion());
                textFieldURL.setText(dt.getUrl());
                comboBoxOrganizadas.removeAllItems();
                List<Edicion> ediciones = dt.getEdiciones();
                if (ediciones != null) {
                    for (Edicion ed : ediciones) {
                        comboBoxOrganizadas.addItem(ed);
                    }
                }              
            }
        });
        
        comboBoxOrganizadas.addActionListener(e -> {
            Usuario usuarioSeleccionado = (Usuario) comboBoxUsuario.getSelectedItem();
            Edicion edicionSeleccionada = (Edicion) comboBoxOrganizadas.getSelectedItem();
            
            if (usuarioSeleccionado == null || edicionSeleccionada == null) return;
            
            //CASO ASIS
            if (usuarioSeleccionado instanceof Asistente) {                
                ConsultaRegistro consultaReg = ConsultaRegistro.crearYMostrar(controlUsuario, edicionSeleccionada.getEvento().getNombre(), usuarioSeleccionado.getNombre());              
            //CASO ORG
            } else if (usuarioSeleccionado instanceof Organizador) {                
                ConsultaEdicionEvento consultaEd = ConsultaEdicionEvento.crearYMostrar(controlEvento, edicionSeleccionada.getEvento().getNombre(), edicionSeleccionada.getNombre());                       
            }
        });
        
		}
	
		//DISTINCION ASIS ORG
        private void actualizarCampos(Integer row, JLabel lblApellido, JTextField textApellido, JLabel lblFechaNacimiento, JTextField textFechaNacimiento, 
        		JLabel lblListaRegistros, JComboBox  comboBoxRegistros, JLabel lblDescripcion, JTextField textDescripcion, JLabel lblURL, 
        		JTextField textURL, JLabel lblListaOrganizadas, JComboBox  comboBoxOrganizadas) {
			getContentPane().remove(lblApellido);
			getContentPane().remove(textApellido);
			getContentPane().remove(lblFechaNacimiento);
			getContentPane().remove(textFechaNacimiento);
			getContentPane().remove(lblDescripcion);
			getContentPane().remove(textDescripcion);
			getContentPane().remove(lblURL);
			getContentPane().remove(textURL);
			getContentPane().remove(lblListaRegistros);
			getContentPane().remove(comboBoxRegistros);
			getContentPane().remove(lblListaOrganizadas);
			getContentPane().remove(comboBoxOrganizadas);
			
			int dynamicRow = row;
			
			if (comboBoxUsuario.getSelectedItem() instanceof Asistente) {
			//ASISTENTE
			GridBagConstraints gbc_lblAp = new GridBagConstraints();
			gbc_lblAp.insets = new Insets(5, 5, 5, 5);
			gbc_lblAp.gridx = 1;
			gbc_lblAp.gridy = dynamicRow;
			gbc_lblAp.anchor = GridBagConstraints.EAST;
			getContentPane().add(lblApellido, gbc_lblAp);
			
			GridBagConstraints gbc_textAp = new GridBagConstraints();
			gbc_textAp.insets = new Insets(5, 5, 5, 5);
			gbc_textAp.fill = GridBagConstraints.HORIZONTAL;
			gbc_textAp.gridx = 2;
			gbc_textAp.gridy = dynamicRow;
			getContentPane().add(textApellido, gbc_textAp);
			dynamicRow++;
			
			GridBagConstraints gbc_lblFec = new GridBagConstraints();
			gbc_lblFec.insets = new Insets(5, 5, 5, 5);
			gbc_lblFec.gridx = 1;
			gbc_lblFec.gridy = dynamicRow;
			gbc_lblFec.anchor = GridBagConstraints.EAST;
			getContentPane().add(lblFechaNacimiento, gbc_lblFec);
			
			GridBagConstraints gbc_textFec = new GridBagConstraints();
			gbc_textFec.insets = new Insets(5, 5, 5, 5);
			gbc_textFec.fill = GridBagConstraints.HORIZONTAL;
			gbc_textFec.gridx = 2;
			gbc_textFec.gridy = dynamicRow;
			getContentPane().add(textFechaNacimiento, gbc_textFec);
			dynamicRow++;
			
			GridBagConstraints gbc_lblInst = new GridBagConstraints();
			gbc_lblInst.insets = new Insets(5, 5, 5, 5);
			gbc_lblInst.gridx = 1;
			gbc_lblInst.gridy = dynamicRow;
			gbc_lblInst.anchor = GridBagConstraints.EAST;
			getContentPane().add(lblListaRegistros, gbc_lblInst);
			
			GridBagConstraints gbc_comboInst = new GridBagConstraints();
			gbc_comboInst.insets = new Insets(5, 5, 5, 5);
			gbc_comboInst.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboInst.gridx = 2;
			gbc_comboInst.gridy = dynamicRow;
			getContentPane().add(comboBoxOrganizadas, gbc_comboInst);
			
			} else {
			//ORGANIZADOR
			GridBagConstraints gbc_lblDesc = new GridBagConstraints();
			gbc_lblDesc.insets = new Insets(5, 5, 5, 5);
			gbc_lblDesc.gridx = 1;
			gbc_lblDesc.gridy = dynamicRow;
			gbc_lblDesc.anchor = GridBagConstraints.EAST;
			getContentPane().add(lblDescripcion, gbc_lblDesc);
			
			GridBagConstraints gbc_textDesc = new GridBagConstraints();
			gbc_textDesc.insets = new Insets(5, 5, 5, 5);
			gbc_textDesc.fill = GridBagConstraints.HORIZONTAL;
			gbc_textDesc.gridx = 2;
			gbc_textDesc.gridy = dynamicRow;
			getContentPane().add(textDescripcion, gbc_textDesc);
			dynamicRow++;
			
			GridBagConstraints gbc_lblUrl = new GridBagConstraints();
			gbc_lblUrl.insets = new Insets(5, 5, 5, 5);
			gbc_lblUrl.gridx = 1;
			gbc_lblUrl.gridy = dynamicRow;
			gbc_lblUrl.anchor = GridBagConstraints.EAST;
			getContentPane().add(lblURL, gbc_lblUrl);
			
			GridBagConstraints gbc_textUrl = new GridBagConstraints();
			gbc_textUrl.insets = new Insets(5, 5, 5, 5);
			gbc_textUrl.fill = GridBagConstraints.HORIZONTAL;
			gbc_textUrl.gridx = 2;
			gbc_textUrl.gridy = dynamicRow;
			getContentPane().add(textURL, gbc_textUrl);
			
			GridBagConstraints gbc_lblInst = new GridBagConstraints();
			gbc_lblInst.insets = new Insets(5, 5, 5, 5);
			gbc_lblInst.gridx = 1;
			gbc_lblInst.gridy = dynamicRow;
			gbc_lblInst.anchor = GridBagConstraints.EAST;
			getContentPane().add(lblListaOrganizadas, gbc_lblInst);
			
			GridBagConstraints gbc_comboInst = new GridBagConstraints();
			gbc_comboInst.insets = new Insets(5, 5, 5, 5);
			gbc_comboInst.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboInst.gridx = 2;
			gbc_comboInst.gridy = dynamicRow;
			getContentPane().add(comboBoxOrganizadas, gbc_comboInst);
			}
			
			getContentPane().revalidate();
			getContentPane().repaint();
			
	}
}
