package ServidorCentral.presentacion;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;


public class RegistroEdicionEvento extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField_1;

    public RegistroEdicionEvento() {
        super("Registro a Edicion de Evento", true, true, true, true); 
        setSize(400, 300);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        getContentPane().setLayout(new MigLayout("", "[115px][][][][][][][10px][][][28px][24px][][][130px,grow]", "[14px][22px][14px][22px][14px][22px][14px][22px][][30px][][][][33px]"));

        JLabel lblNewLabel = new JLabel("Seleccione un evento:");
        getContentPane().add(lblNewLabel, "cell 0 0,alignx left,aligny center");
        
                JComboBox<String> comboBox = new JComboBox<>();
                getContentPane().add(comboBox, "cell 14 0,alignx right,aligny center");

        JLabel lblNewLabel_1 = new JLabel("Seleccione una edición:");
        getContentPane().add(lblNewLabel_1, "cell 0 2,alignx left,aligny center");
        
                JComboBox<String> comboBox_1 = new JComboBox<>();
                getContentPane().add(comboBox_1, "cell 14 2,alignx right,aligny center");

        JLabel lblNewLabel_3 = new JLabel("Seleccione un Tipo de registro:");
        getContentPane().add(lblNewLabel_3, "cell 0 4 11 1,alignx left,aligny center");
        
                JComboBox<String> comboBox_3 = new JComboBox<>();
                getContentPane().add(comboBox_3, "cell 11 4 4 1,alignx right,aligny center");

        JLabel lblNewLabel_2 = new JLabel("Seleccione asistente:");
        getContentPane().add(lblNewLabel_2, "cell 0 6,alignx left,aligny center");
        
                JComboBox<String> comboBox_4 = new JComboBox<>();
                getContentPane().add(comboBox_4, "cell 14 6,alignx right,aligny center");
        
        JLabel lblNewLabel_4 = new JLabel("Codigo:");
        getContentPane().add(lblNewLabel_4, "cell 0 8");
        
        textField_1 = new JTextField();
        getContentPane().add(textField_1, "cell 14 8,growx");
        textField_1.setColumns(10);
        
        JButton btnNewButton = new JButton("Registrar asistente");
        getContentPane().add(btnNewButton, "cell 0 12");
        
        JButton btnNewButton_1 = new JButton("Cancelar");
        getContentPane().add(btnNewButton_1, "cell 14 12");
    }

}
