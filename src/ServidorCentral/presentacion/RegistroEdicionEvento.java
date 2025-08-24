import javax.swing.*;
import java.awt.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import net.miginfocom.swing.MigLayout;

public class RegistroEdicionEvento extends JInternalFrame {
	private JTextField textField;
	private JTextField textField_1;

    public RegistroEdicionEvento() {
        super("Registro a Edicion de Evento", true, true, true, true); 
        setSize(400, 300);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        getContentPane().setLayout(new MigLayout("", "[115px][10px][28px][24px][130px,grow]", "[14px][22px][14px][22px][14px][22px][14px][22px][][30px][33px]"));

        JLabel lblNewLabel = new JLabel("Seleccione un evento:");
        getContentPane().add(lblNewLabel, "cell 0 0,alignx left,aligny center");
        
                JComboBox<String> comboBox = new JComboBox<>();
                getContentPane().add(comboBox, "cell 4 0,alignx right,aligny center");

        JLabel lblNewLabel_1 = new JLabel("Seleccione una edición:");
        getContentPane().add(lblNewLabel_1, "cell 0 2,alignx left,aligny center");
        
                JComboBox<String> comboBox_1 = new JComboBox<>();
                getContentPane().add(comboBox_1, "cell 4 2,alignx right,aligny center");

        JLabel lblNewLabel_3 = new JLabel("Seleccione un Tipo de registro:");
        getContentPane().add(lblNewLabel_3, "cell 0 4 3 1,alignx left,aligny center");
        
                JComboBox<String> comboBox_3 = new JComboBox<>();
                getContentPane().add(comboBox_3, "cell 3 4 2 1,alignx right,aligny center");

        JLabel lblNewLabel_2 = new JLabel("Seleccione asistente:");
        getContentPane().add(lblNewLabel_2, "cell 0 6,alignx left,aligny center");
        
                JComboBox<String> comboBox_4 = new JComboBox<>();
                getContentPane().add(comboBox_4, "cell 4 6,alignx right,aligny center");
        
        JLabel lblNewLabel_4 = new JLabel("Codigo:");
        getContentPane().add(lblNewLabel_4, "cell 0 8");
        
        textField_1 = new JTextField();
        getContentPane().add(textField_1, "cell 4 8,growx");
        textField_1.setColumns(10);
        
       
        

        JButton btnNewButton = new JButton("Registrar asistente");
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnNewButton);
        getContentPane().add(panelBoton, "cell 0 10 5 1,alignx center,aligny center");
        
        JButton btnCancelar = new JButton("Cancelar");
        panelBoton.add(btnCancelar);
    }

}
