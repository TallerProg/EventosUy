package ServidorCentral.presentacion;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import ServidorCentral.logica.*;
import net.miginfocom.swing.MigLayout;

public class AltaPatrocinio extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldCodigo;
	private IControllerEvento ice;
	private IControllerUsuario icu;
	private JComboBox<String> comboBoxEvento;
	private JComboBox<String> comboBoxEdicion;
	private JComboBox<String> comboBoxRegistro;
	private JComboBox<String> comboBoxAsistente;
	private JTextField textField;

	public AltaPatrocinio(IControllerEvento ice, IControllerUsuario icu) {

		super("Registro a Edicion de Evento", false, false, false, false);
		this.ice = ice;
		this.icu = icu;

		setSize(838, 403);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[115px][][][][][][][10px][][][28px][24px][][][130px,grow]",
				"[14px][22px][14px][22px][14px][22px][14px][22px][][][30px][][][][33px]"));

		JLabel lblEvento = new JLabel("Seleccione un evento:");
		getContentPane().add(lblEvento, "cell 0 0,alignx left,aligny center");
		comboBoxEvento = new JComboBox<>();
		getContentPane().add(comboBoxEvento, "cell 14 0,alignx left,aligny center");

		JLabel lblEdicion = new JLabel("Seleccione una edición:");
		getContentPane().add(lblEdicion, "cell 0 2,alignx left,aligny center");
		comboBoxEdicion = new JComboBox<>();
		getContentPane().add(comboBoxEdicion, "cell 14 2,alignx left,aligny center");

		JLabel lblTipoRegistro = new JLabel("Seleccione un Tipo de registro:");
		getContentPane().add(lblTipoRegistro, "cell 0 4 11 1,alignx left,aligny center");
		comboBoxRegistro = new JComboBox<>();
		getContentPane().add(comboBoxRegistro, "cell 14 4,alignx left,aligny center");

		JLabel lblAsistente = new JLabel("Seleccione institucion:");
		getContentPane().add(lblAsistente, "cell 0 6,alignx left,aligny center");
		comboBoxAsistente = new JComboBox<>();
		getContentPane().add(comboBoxAsistente, "cell 14 6,alignx left,aligny center");

		JLabel lblNewLabel_1 = new JLabel("Nivel:");
		getContentPane().add(lblNewLabel_1, "cell 0 8");

		JComboBox comboBoxNivel = new JComboBox();
		comboBoxNivel.setEnabled(false);
		getContentPane().add(comboBoxNivel, "cell 14 8,alignx left");
		comboBoxNivel.addItem("Platino");
		comboBoxNivel.addItem("Oro");
		comboBoxNivel.addItem("Plata");
		comboBoxNivel.addItem("Bronce");

		JLabel lblCodigo = new JLabel("Aporte Económico:");
		getContentPane().add(lblCodigo, "cell 0 9");

		// Formato para números decimales
		NumberFormat aporteEconomico = NumberFormat.getNumberInstance();
		aporteEconomico.setGroupingUsed(false); // sin separador de miles

		NumberFormatter formatter = new NumberFormatter(aporteEconomico);
		formatter.setAllowsInvalid(true); 
		formatter.setAllowsInvalid(false); // no permite letras ni símbolos
		formatter.setMinimum(0.0); // solo positivos
		formatter.setMaximum(Double.MAX_VALUE); // máximo permitido

		// Campo de texto con formato
		textFieldCodigo = new JFormattedTextField(formatter);
		getContentPane().add(textFieldCodigo, "cell 14 9,growx");
		textFieldCodigo.setColumns(10);

		JLabel lblNewLabel = new JLabel("Cantidad de cupos:");
		getContentPane().add(lblNewLabel, "cell 0 10");

		// Formato para enteros
		NumberFormat numeroRegistro = NumberFormat.getIntegerInstance();
		numeroRegistro.setGroupingUsed(false); // sin separadores de miles

		NumberFormatter formatterEntero = new NumberFormatter(numeroRegistro);
		formatterEntero.setAllowsInvalid(true); 
		formatterEntero.setAllowsInvalid(false); // no permite letras ni símbolos
		formatterEntero.setMinimum(0); // solo positivos
		formatterEntero.setMaximum(Integer.MAX_VALUE); // máximo permitido

		// Campo de texto con formato
		textField = new JFormattedTextField(formatterEntero);
		getContentPane().add(textField, "cell 14 10,growx");
		textField.setColumns(10);

		JButton btnRegistrar = new JButton("Registrar asistente");
		btnRegistrar.setEnabled(false);
		getContentPane().add(btnRegistrar, "cell 0 13");

		JButton btnCancelar = new JButton("Cancelar");
		getContentPane().add(btnCancelar, "cell 14 13");

		comboBoxEdicion.setEnabled(false);
		comboBoxRegistro.setEnabled(false);
		comboBoxAsistente.setEnabled(false);

		comboBoxEvento.addActionListener(e -> {
			String nombreEventoSeleccionado = (String) comboBoxEvento.getSelectedItem();
			if (nombreEventoSeleccionado != null && !nombreEventoSeleccionado.equals("Sin eventos")) {
				comboBoxEdicion.setEnabled(true);
				try {
					cargarEdiciones(nombreEventoSeleccionado);
				} catch (Exception e1) {

					e1.printStackTrace();
				}

				comboBoxRegistro.setEnabled(false);
				comboBoxRegistro.removeAllItems();
				comboBoxAsistente.setEnabled(false);
				comboBoxAsistente.removeAllItems();
				comboBoxNivel.setEnabled(false);
			} else {
				comboBoxEdicion.setEnabled(false);
				comboBoxEdicion.removeAllItems();
				comboBoxRegistro.setEnabled(false);
				comboBoxRegistro.removeAllItems();
				comboBoxAsistente.setEnabled(false);
				comboBoxAsistente.removeAllItems();
				comboBoxNivel.setEnabled(false);

			}
		});
		comboBoxEdicion.addActionListener(e -> {
			String nombreEedicionSeleccionada = (String) comboBoxEdicion.getSelectedItem();
			if (nombreEedicionSeleccionada != null && !nombreEedicionSeleccionada.equals("Sin ediciones")) {
				comboBoxRegistro.setEnabled(true);
				try {
					cargarRegistros(nombreEedicionSeleccionada);
				} catch (Exception e1) {

					e1.printStackTrace();
				}

				comboBoxAsistente.setEnabled(false);
				comboBoxAsistente.removeAllItems();
				comboBoxNivel.setEnabled(false);
			} else {

				comboBoxRegistro.setEnabled(false);
				comboBoxRegistro.removeAllItems();
				comboBoxAsistente.setEnabled(false);
				comboBoxAsistente.removeAllItems();
				comboBoxNivel.setEnabled(false);
			}
		});

		comboBoxRegistro.addActionListener(e -> {
			String nombreRegistroSeleccionado = (String) comboBoxRegistro.getSelectedItem();
			if (nombreRegistroSeleccionado != null && !nombreRegistroSeleccionado.equals("Sin tipos de registros")) {
				comboBoxAsistente.setEnabled(true);
				try {
					cargarInstituciones();

				} catch (Exception e1) {

					e1.printStackTrace();
				}
				comboBoxNivel.setEnabled(false);
			} else {

				comboBoxRegistro.setEnabled(false);
				comboBoxRegistro.removeAllItems();
				comboBoxAsistente.setEnabled(false);
				comboBoxAsistente.removeAllItems();
				comboBoxNivel.setEnabled(false);
			}
		});
		comboBoxAsistente.addActionListener(e -> {
			String nombreAsistente = (String) comboBoxAsistente.getSelectedItem();
			if (nombreAsistente != null && !nombreAsistente.equals("Sin asistentes")) {
				comboBoxNivel.setEnabled(true);
			}else {
				comboBoxNivel.setEnabled(false);
			}

		});
		comboBoxNivel.addActionListener(e -> {
			String nivel = (String) comboBoxNivel.getSelectedItem();
			if (nivel != null && !nivel.equals("")) {
				btnRegistrar.setEnabled(true);
			}

		});

		// Acción botón Registrar

		btnRegistrar.addActionListener(e -> {
			String eventoNombre = (String) comboBoxEvento.getSelectedItem();
			String edicionNombre = (String) comboBoxEdicion.getSelectedItem();
			String institucionNombre = (String) comboBoxAsistente.getSelectedItem();
			String tipoRegistroNombre = (String) comboBoxRegistro.getSelectedItem();
			String nivelS = (String) comboBoxNivel.getSelectedItem();
			ETipoNivel nivel =  ETipoNivel.valueOf(nivelS);  
			String aporteEconomic = textFieldCodigo.getText().trim();
			String cuposGrati = textField.getText().trim();
			String codigo = UUID.randomUUID().toString();

			if ((aporteEconomic == null) || (cuposGrati == null)) {
				JOptionPane.showMessageDialog(this, "Debe completar todos los campos antes de registrar.",
						"Campos incompletos", JOptionPane.WARNING_MESSAGE);
				return;
			}

			try {

				ice.altaPatrocinio(
					    codigo,
					    LocalDate.now(),
					    cuposGrati.equals("") ? 0 : Integer.parseInt(cuposGrati),
					    aporteEconomic.equals("") ? 0f : Float.parseFloat(aporteEconomic),
					    nivel,
					    institucionNombre,
					    edicionNombre,
					    tipoRegistroNombre
					);
				JOptionPane.showMessageDialog(this, "Registro exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				
				// Limpiar campos después de registrar
				cargarEventos();
				comboBoxEdicion.setModel(new DefaultComboBoxModel<>());
				comboBoxRegistro.setModel(new DefaultComboBoxModel<>());
				comboBoxAsistente.setModel(new DefaultComboBoxModel<>());
				comboBoxNivel.setSelectedIndex(-1); // deselecciona nivel
				textField.setText(null);
				textFieldCodigo.setText(null);
				comboBoxEdicion.setEnabled(false);
				comboBoxRegistro.setEnabled(false);
				comboBoxAsistente.setEnabled(false);
				comboBoxNivel.setEnabled(false);
				btnRegistrar.setEnabled(false);


			} catch (Exception ex) {
				textField.setText(null);
				textFieldCodigo.setText(null);
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				
			}
		});

		// Acción botón Cancelar
		btnCancelar.addActionListener(e -> this.dispose());
	}

	public void cargarEventos() {
		List<Evento> eventos = ice.listarEventos();
		if (eventos.isEmpty()) {

			comboBoxEvento.addItem("Sin eventos");
			comboBoxEvento.setEnabled(false);
		} else {
			comboBoxEvento.setEnabled(true);
			List<String> nombres = new java.util.ArrayList<>();
			for (Evento e : eventos) {
				nombres.add(e.getNombre());
			}

			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
			comboBoxEvento.setModel(model);
		}

	}

	public void cargarEdiciones(String nombreEvento) {
		Evento evento = ice.getEvento(nombreEvento);
		if (evento != null) {
			List<Edicion> ediciones = evento.getEdiciones();
			if (ediciones.isEmpty()) {
				comboBoxEdicion.removeAllItems();
				comboBoxEdicion.addItem("Sin ediciones");
				comboBoxEdicion.setEnabled(false);
			} else {
				List<String> nombresEdiciones = new java.util.ArrayList<>();
				for (Edicion ed : ediciones) {
					nombresEdiciones.add(ed.getNombre());
				}

				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(
						nombresEdiciones.toArray(new String[0]));
				comboBoxEdicion.setModel(model);
			}
		}
	}

	public void cargarRegistros(String nombreEedicion) {
		List<TipoRegistro> registros = ice.findEdicion(nombreEedicion).getTipoRegistros();
		if (registros != null) {
			comboBoxRegistro.setEnabled(true);
			List<String> nombres = new java.util.ArrayList<>();
			for (TipoRegistro r : registros) {
				nombres.add(r.getNombre());
			}

			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
			comboBoxRegistro.setModel(model);
		} else {
			comboBoxRegistro.removeAllItems();
			comboBoxRegistro.addItem("Sin tipos de registros");
			comboBoxRegistro.setEnabled(false);
		}

	}

	public void cargarAsistentes() {
		List<Asistente> asistentes = icu.getAsistentes();
		if (asistentes != null) {
			comboBoxAsistente.setEnabled(true);
			List<String> nombres = new java.util.ArrayList<>();
			for (Asistente a : asistentes) {
				nombres.add(a.getNickname());
			}

			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
			comboBoxAsistente.setModel(model);

		} else {
			comboBoxAsistente.removeAllItems();
			comboBoxAsistente.addItem("Sin asistentes");
			comboBoxAsistente.setEnabled(false);

		}
	}

	public void cargarInstituciones() {
		ManejadorInstitucion mI = ManejadorInstitucion.getInstance();
		List<Institucion> instituciones = mI.listarInstituciones();
		if (instituciones != null) {
			comboBoxAsistente.setEnabled(true);
			List<String> nombres = new java.util.ArrayList<>();
			for (Institucion i : instituciones) {
				nombres.add(i.getNombre());
			}

			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(nombres.toArray(new String[0]));
			comboBoxAsistente.setModel(model);

		} else {
			comboBoxAsistente.removeAllItems();
			comboBoxAsistente.addItem("Sin asistentes");
			comboBoxAsistente.setEnabled(false);
		}
	}

}
