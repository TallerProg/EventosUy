package ServidorCentral.presentacion;

import ServidorCentral.excepciones.UsuarioRepetidoException;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.Institucion;
import ServidorCentral.logica.ManejadorInstitucion;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.*;

public class AltaUsuario extends JInternalFrame {

    private IControllerUsuario controlUsr;

    private JTextField textFieldNickName;
    private JTextField textFieldCorreo;
    private JTextField textFieldNombre;
    private JTextField textFieldApellido;
    private JTextField textFieldFechaNacimiento;
    private JTextField textFieldDescripcion;
    private JTextField textFieldURL;
    private JComboBox<String> comboTipoUsuario;
    private JComboBox<String> comboInstitucion;
    private JButton btnAceptar;
    private JButton btnCancelar;
    
    private int row = 0;



        public AltaUsuario(IControllerUsuario icu) {
            controlUsr = icu;

            setResizable(true);
            setIconifiable(true);
            setMaximizable(true);
            setClosable(true);
            setTitle("Alta de Usuario");
            setBounds(10, 10, 400, 450);

            GridBagLayout gridBagLayout = new GridBagLayout();
            gridBagLayout.columnWidths = new int[]{120, 200, 0};
            gridBagLayout.rowHeights = new int[]{30, 30, 30, 30, 30, 30, 30, 30, 30};
            gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
            gridBagLayout.rowWeights = new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
            getContentPane().setLayout(gridBagLayout);


            // Tipo de usuario
            JLabel lblTipo = new JLabel("Tipo de Usuario:");
            GridBagConstraints gbc_lblTipo = new GridBagConstraints();
            gbc_lblTipo.insets = new Insets(5, 5, 5, 5);
            gbc_lblTipo.gridx = 0;
            gbc_lblTipo.gridy = row;
            gbc_lblTipo.anchor = GridBagConstraints.EAST;
            getContentPane().add(lblTipo, gbc_lblTipo);

            comboTipoUsuario = new JComboBox<>(new String[]{"Asistente", "Organizador"});
            GridBagConstraints gbc_comboTipo = new GridBagConstraints();
            gbc_comboTipo.insets = new Insets(5, 5, 5, 5);
            gbc_comboTipo.fill = GridBagConstraints.HORIZONTAL;
            gbc_comboTipo.gridx = 1;
            gbc_comboTipo.gridy = row;
            getContentPane().add(comboTipoUsuario, gbc_comboTipo);
            row++;

            // Nickname
            JLabel lblNick = new JLabel("Nickname:");
            GridBagConstraints gbc_lblNick = new GridBagConstraints();
            gbc_lblNick.insets = new Insets(5, 5, 5, 5);
            gbc_lblNick.gridx = 0;
            gbc_lblNick.gridy = row;
            gbc_lblNick.anchor = GridBagConstraints.EAST;
            getContentPane().add(lblNick, gbc_lblNick);

            textFieldNickName = new JTextField();
            GridBagConstraints gbc_textNick = new GridBagConstraints();
            gbc_textNick.insets = new Insets(5, 5, 5, 5);
            gbc_textNick.fill = GridBagConstraints.HORIZONTAL;
            gbc_textNick.gridx = 1;
            gbc_textNick.gridy = row;
            getContentPane().add(textFieldNickName, gbc_textNick);
            row++;
            
            // Correo
            JLabel lblCorreo = new JLabel("Correo:");
            GridBagConstraints gbc_lblCorreo = new GridBagConstraints();
            gbc_lblCorreo.insets = new Insets(5, 5, 5, 5);
            gbc_lblCorreo.gridx = 0;
            gbc_lblCorreo.gridy = row;
            gbc_lblCorreo.anchor = GridBagConstraints.EAST;
            getContentPane().add(lblCorreo, gbc_lblCorreo);

            textFieldCorreo = new JTextField();
            GridBagConstraints gbc_textCorreo = new GridBagConstraints();
            gbc_textCorreo.insets = new Insets(5, 5, 5, 5);
            gbc_textCorreo.fill = GridBagConstraints.HORIZONTAL;
            gbc_textCorreo.gridx = 1;
            gbc_textCorreo.gridy = row;
            getContentPane().add(textFieldCorreo, gbc_textCorreo);
            row++;

            // Nombre
            JLabel lblNombre = new JLabel("Nombre:");
            GridBagConstraints gbc_lblNombre = new GridBagConstraints();
            gbc_lblNombre.insets = new Insets(5, 5, 5, 5);
            gbc_lblNombre.gridx = 0;
            gbc_lblNombre.gridy = row;
            gbc_lblNombre.anchor = GridBagConstraints.EAST;
            getContentPane().add(lblNombre, gbc_lblNombre);

            textFieldNombre = new JTextField();
            GridBagConstraints gbc_textNombre = new GridBagConstraints();
            gbc_textNombre.insets = new Insets(5, 5, 5, 5);
            gbc_textNombre.fill = GridBagConstraints.HORIZONTAL;
            gbc_textNombre.gridx = 1;
            gbc_textNombre.gridy = row;
            getContentPane().add(textFieldNombre, gbc_textNombre);
            row++;

            // Campos dinámicos
            JLabel lblApellido = new JLabel("Apellido:");
            textFieldApellido = new JTextField();
            JLabel lblFecha = new JLabel("Fecha de Nac.:");
            textFieldFechaNacimiento = new JTextField();

            JLabel lblDescripcion = new JLabel("Descripción:");
            textFieldDescripcion = new JTextField();
            JLabel lblURL = new JLabel("URL (opcional):");
            textFieldURL = new JTextField();

            JLabel lblInstitucion = new JLabel("Institución :");
            comboInstitucion = new JComboBox<>();
            comboInstitucion.addItem("Ninguna");
            for (Institucion ins : ManejadorInstitucion.getInstance().listarInstituciones()) {
                comboInstitucion.addItem(ins.getNombre());
            }

            // Botón Aceptar
            btnAceptar = new JButton("Aceptar");
            GridBagConstraints gbc_btnAceptar = new GridBagConstraints();
            gbc_btnAceptar.insets = new Insets(10, 5, 5, 5);
            gbc_btnAceptar.gridx = 1;
            gbc_btnAceptar.gridy = 8;
            getContentPane().add(btnAceptar, gbc_btnAceptar);
            
         // Botón Cancelar
            btnCancelar = new JButton("Cancelar");
            GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
            gbc_btnCancelar.insets = new Insets(10, 5, 5, 5);
            gbc_btnCancelar.gridx = 2; 
            gbc_btnCancelar.gridy = 8;
            getContentPane().add(btnCancelar, gbc_btnCancelar);
            
            // Acción del botón Cancelar
            btnCancelar.addActionListener(e -> this.setVisible(false));

            // Listener tipo de usuario
            comboTipoUsuario.addActionListener(e -> actualizarCampos(row, lblApellido, textFieldApellido,
                    lblFecha, textFieldFechaNacimiento, lblDescripcion, textFieldDescripcion, lblURL, textFieldURL,
                    lblInstitucion, comboInstitucion));

            comboTipoUsuario.setSelectedIndex(0);

            btnAceptar.addActionListener(e -> {
                String tipo = (String) comboTipoUsuario.getSelectedItem();
                String nick = textFieldNickName.getText().trim();
                String nombre = textFieldNombre.getText().trim();
                String correo = textFieldCorreo.getText().trim();

                try {
                    if (tipo.equals("Asistente")) {
                        String apellido = textFieldApellido.getText().trim();
                        String fechaStr = textFieldFechaNacimiento.getText().trim();
                        LocalDate fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                        String nombreInstitucion = (String) comboInstitucion.getSelectedItem();
                        Institucion inst = null;
                        if (!nombreInstitucion.equals("Ninguna")) {
                            inst = ManejadorInstitucion.getInstance().buscarPorNombre(nombreInstitucion);
                        }

                        controlUsr.AltaAsistente(nick, correo, nombre, apellido, fecha, inst);
                    } else {
                        String descripcion = textFieldDescripcion.getText().trim();
                        String url = textFieldURL.getText().trim();
                        if (url.isEmpty()) url = null;
                        controlUsr.AltaOrganizador(nick, correo, nombre, descripcion, url);
                    }

                    JOptionPane.showMessageDialog(this, "Usuario creado correctamente");
                    this.setVisible(false);

                } catch (DateTimeParseException dtpe) {
                    JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use dd/MM/yyyy", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (UsuarioRepetidoException ure) {
                    JOptionPane.showMessageDialog(this, ure.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }

        private void actualizarCampos(int row,
                                      JLabel lblApellido, JTextField textApellido,
                                      JLabel lblFecha, JTextField textFecha,
                                      JLabel lblDesc, JTextField textDesc,
                                      JLabel lblURL, JTextField textURL,
                                      JLabel lblInstitucion, JComboBox<String> comboIns) {
            getContentPane().remove(lblApellido);
            getContentPane().remove(textApellido);
            getContentPane().remove(lblFecha);
            getContentPane().remove(textFecha);
            getContentPane().remove(lblDesc);
            getContentPane().remove(textDesc);
            getContentPane().remove(lblURL);
            getContentPane().remove(textURL);
            getContentPane().remove(lblInstitucion);
            getContentPane().remove(comboIns);

            int dynamicRow = row;

            if (comboTipoUsuario.getSelectedItem().equals("Asistente")) {
                // Apellido
                GridBagConstraints gbc_lblAp = new GridBagConstraints();
                gbc_lblAp.insets = new Insets(5, 5, 5, 5);
                gbc_lblAp.gridx = 0;
                gbc_lblAp.gridy = dynamicRow;
                gbc_lblAp.anchor = GridBagConstraints.EAST;
                getContentPane().add(lblApellido, gbc_lblAp);

                GridBagConstraints gbc_textAp = new GridBagConstraints();
                gbc_textAp.insets = new Insets(5, 5, 5, 5);
                gbc_textAp.fill = GridBagConstraints.HORIZONTAL;
                gbc_textAp.gridx = 1;
                gbc_textAp.gridy = dynamicRow;
                getContentPane().add(textApellido, gbc_textAp);
                dynamicRow++;

                // Fecha
                GridBagConstraints gbc_lblFec = new GridBagConstraints();
                gbc_lblFec.insets = new Insets(5, 5, 5, 5);
                gbc_lblFec.gridx = 0;
                gbc_lblFec.gridy = dynamicRow;
                gbc_lblFec.anchor = GridBagConstraints.EAST;
                getContentPane().add(lblFecha, gbc_lblFec);

                GridBagConstraints gbc_textFec = new GridBagConstraints();
                gbc_textFec.insets = new Insets(5, 5, 5, 5);
                gbc_textFec.fill = GridBagConstraints.HORIZONTAL;
                gbc_textFec.gridx = 1;
                gbc_textFec.gridy = dynamicRow;
                getContentPane().add(textFecha, gbc_textFec);
                dynamicRow++;

                // Institución
                GridBagConstraints gbc_lblInst = new GridBagConstraints();
                gbc_lblInst.insets = new Insets(5, 5, 5, 5);
                gbc_lblInst.gridx = 0;
                gbc_lblInst.gridy = dynamicRow;
                gbc_lblInst.anchor = GridBagConstraints.EAST;
                getContentPane().add(lblInstitucion, gbc_lblInst);

                GridBagConstraints gbc_comboInst = new GridBagConstraints();
                gbc_comboInst.insets = new Insets(5, 5, 5, 5);
                gbc_comboInst.fill = GridBagConstraints.HORIZONTAL;
                gbc_comboInst.gridx = 1;
                gbc_comboInst.gridy = dynamicRow;
                getContentPane().add(comboIns, gbc_comboInst);

            } else {
                // Organizador
                GridBagConstraints gbc_lblDesc = new GridBagConstraints();
                gbc_lblDesc.insets = new Insets(5, 5, 5, 5);
                gbc_lblDesc.gridx = 0;
                gbc_lblDesc.gridy = dynamicRow;
                gbc_lblDesc.anchor = GridBagConstraints.EAST;
                getContentPane().add(lblDesc, gbc_lblDesc);

                GridBagConstraints gbc_textDesc = new GridBagConstraints();
                gbc_textDesc.insets = new Insets(5, 5, 5, 5);
                gbc_textDesc.fill = GridBagConstraints.HORIZONTAL;
                gbc_textDesc.gridx = 1;
                gbc_textDesc.gridy = dynamicRow;
                getContentPane().add(textDesc, gbc_textDesc);
                dynamicRow++;

                GridBagConstraints gbc_lblUrl = new GridBagConstraints();
                gbc_lblUrl.insets = new Insets(5, 5, 5, 5);
                gbc_lblUrl.gridx = 0;
                gbc_lblUrl.gridy = dynamicRow;
                gbc_lblUrl.anchor = GridBagConstraints.EAST;
                getContentPane().add(lblURL, gbc_lblUrl);

                GridBagConstraints gbc_textUrl = new GridBagConstraints();
                gbc_textUrl.insets = new Insets(5, 5, 5, 5);
                gbc_textUrl.fill = GridBagConstraints.HORIZONTAL;
                gbc_textUrl.gridx = 1;
                gbc_textUrl.gridy = dynamicRow;
                getContentPane().add(textURL, gbc_textUrl);
            }

            getContentPane().revalidate();
            getContentPane().repaint();
        }

        private static final long serialVersionUID = 1L;
}