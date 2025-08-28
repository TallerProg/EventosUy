package ServidorCentral.presentacion;

import ServidorCentral.logica.*;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public class AltaEdicionEvento extends JInternalFrame {

    private JComboBox<Evento> comboEvento;
    private JComboBox<Organizador> comboOrganizador;
    private JTextField txtNombre, txtSigla, txtCiudad, txtPais;
    private JFormattedTextField txtFechaIni, txtFechaFin, txtFechaAlta;
    private JButton btnAceptar, btnCancelar, btnVerificar;
    private JLabel lblErrorNombre;

    private IControllerEvento controller;
    private List<Evento> eventos;
    private List<Organizador> organizadores;

    public AltaEdicionEvento(IControllerEvento ice) {
        this.controller = ice;
        
        setClosable(true);   
        setIconifiable(true); 
        setMaximizable(true); 
        setResizable(true);  
        // Obtener listas desde el controlador
        this.eventos = controller.listarEventos();
        this.organizadores = controller.listarOrganizadores();

        setTitle("Alta de Edición de Evento");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        // ComboEvento
        JLabel labelEvento = new JLabel("Evento:");
        labelEvento.setBounds(10, 46, 63, 32);
        labelEvento.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        getContentPane().add(labelEvento);

        comboEvento = new JComboBox<>(eventos.toArray(new Evento[0]));
        comboEvento.setBounds(70, 47, 239, 32);
        getContentPane().add(comboEvento);

        // ComboOrganizador
        JLabel labelOrg = new JLabel("Organizador:");
        labelOrg.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelOrg.setBounds(10, 85, 89, 32);
        getContentPane().add(labelOrg);

        comboOrganizador = new JComboBox<>(organizadores.toArray(new Organizador[0]));
        comboOrganizador.setBounds(100, 86, 239, 32);
        getContentPane().add(comboOrganizador);

        // Nombre
        JLabel labelNombre = new JLabel("Nombre:");
        labelNombre.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelNombre.setBounds(14, 127, 63, 32);
        getContentPane().add(labelNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(70, 129, 147, 32);
        getContentPane().add(txtNombre);

        btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(230, 129, 100, 32);
        getContentPane().add(btnVerificar);

        lblErrorNombre = new JLabel("");
        lblErrorNombre.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        lblErrorNombre.setBounds(70, 160, 300, 20);
        getContentPane().add(lblErrorNombre);

        // Otros campos
        JLabel labelSigla = new JLabel("Sigla:");
        labelSigla.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelSigla.setBounds(14, 190, 52, 32);
        getContentPane().add(labelSigla);

        txtSigla = new JTextField();
        txtSigla.setBounds(70, 190, 89, 32);
        getContentPane().add(txtSigla);

        JLabel labelCiudad = new JLabel("Ciudad:");
        labelCiudad.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelCiudad.setBounds(180, 190, 73, 32);
        getContentPane().add(labelCiudad);

        txtCiudad = new JTextField();
        txtCiudad.setBounds(240, 190, 147, 32);
        getContentPane().add(txtCiudad);

        JLabel labelPais = new JLabel("País:");
        labelPais.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        labelPais.setBounds(14, 230, 41, 32);
        getContentPane().add(labelPais);

        txtPais = new JTextField();
        txtPais.setBounds(70, 230, 153, 32);
        getContentPane().add(txtPais);

        // --- FECHAS ---
        txtFechaIni = crearCampoFecha("##/##/####", 140, 270, 120, 25);
        getContentPane().add(txtFechaIni);
        JLabel lblFechaIni = new JLabel("Fecha Inicio (DD/MM/AAAA):");
        lblFechaIni.setBounds(14, 270, 203, 25);
        lblFechaIni.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        getContentPane().add(lblFechaIni);

        txtFechaFin = crearCampoFecha("##/##/####", 140, 310, 120, 25);
        getContentPane().add(txtFechaFin);
        JLabel lblFechaFin = new JLabel("Fecha Fin (DD/MM/AAAA):");
        lblFechaFin.setBounds(14, 310, 180, 25);
        lblFechaFin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        getContentPane().add(lblFechaFin);

        txtFechaAlta = crearCampoFecha("##/##/####", 140, 350, 120, 25);
        getContentPane().add(txtFechaAlta);
        JLabel lblFechaAlta = new JLabel("Fecha Alta (DD/MM/AAAA):");
        lblFechaAlta.setBounds(14, 350, 180, 25);
        lblFechaAlta.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        getContentPane().add(lblFechaAlta);

        // Botones
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(10, 400, 196, 32);
        getContentPane().add(btnAceptar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(258, 400, 196, 32);
        getContentPane().add(btnCancelar);

        // Título
        JTextArea titulo = new JTextArea();
        titulo.setBackground(new Color(240, 240, 240));
        titulo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        titulo.setText("Crear Edicion de Evento ");
        titulo.setBounds(136, 10, 280, 37);
        getContentPane().add(titulo);

        // Acciones
        btnCancelar.addActionListener(e -> dispose());

        btnVerificar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                lblErrorNombre.setText("Debe ingresar un nombre");
                lblErrorNombre.setForeground(Color.RED);
                return;
            }

            // Verificar nombre manualmente
            boolean existe = false;
            for (Edicion e1 : controller.listarEdiciones()) {
                if (e1.getNombre().equalsIgnoreCase(nombre)) {
                    existe = true;
                    break;
                }
            }

            if (existe) {
                lblErrorNombre.setText("El nombre ya está en uso");
                lblErrorNombre.setForeground(Color.RED);
            } else {
                lblErrorNombre.setText("Nombre disponible");
                lblErrorNombre.setForeground(new Color(0,128,0));
            }
        });

        btnAceptar.addActionListener(e -> {
            try {
                String nombre = txtNombre.getText().trim();
                String sigla = txtSigla.getText().trim();
                String ciudad = txtCiudad.getText().trim();
                String pais = txtPais.getText().trim();

                LocalDate fIni = parseFecha(txtFechaIni.getText());
                LocalDate fFin = parseFecha(txtFechaFin.getText());
                LocalDate fAlta = parseFecha(txtFechaAlta.getText());

                Evento evento = (Evento) comboEvento.getSelectedItem();
                Organizador org = (Organizador) comboOrganizador.getSelectedItem();

                // Orden de argumentos compatible con el controlador
                controller.altaEdicionDeEvento(
                    nombre,
                    sigla,
                    ciudad,
                    pais,
                    fIni,
                    fFin,
                    evento,
                    org
                );

                JOptionPane.showMessageDialog(this, "Edición creada con éxito");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto o error: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private JFormattedTextField crearCampoFecha(String formato, int x, int y, int ancho, int alto) {
        try {
            MaskFormatter mascara = new MaskFormatter(formato);
            mascara.setPlaceholderCharacter('_');
            JFormattedTextField campo = new JFormattedTextField(mascara);
            campo.setBounds(x, y, ancho, alto);
            campo.setEditable(true);
            campo.setFocusLostBehavior(JFormattedTextField.PERSIST);
            return campo;
        } catch (ParseException e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    private LocalDate parseFecha(String texto) {
        String[] partes = texto.split("/");
        if (partes.length != 3) throw new IllegalArgumentException("Fecha inválida");
        int dia = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int anio = Integer.parseInt(partes[2]);
        return LocalDate.of(anio, mes, dia);
    }

    private static final long serialVersionUID = 1L;
}
