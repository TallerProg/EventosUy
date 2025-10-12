package ServidorCentral.presentacion;

import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ServidorCentral.logica.Asistente;
import ServidorCentral.logica.Edicion;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.TipoRegistro;
import net.miginfocom.swing.MigLayout;

public class RegistroEdicionEvento extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField textFieldCodigo;
	private IControllerEvento ice;
	private IControllerUsuario icu;
	private JComboBox<String> comboBoxEvento;
	private JComboBox<String> comboBoxEdicion;
	private JComboBox<String> comboBoxRegistro;
	private JComboBox<String> comboBoxAsistente;

	public RegistroEdicionEvento(IControllerEvento ice, IControllerUsuario icu) {

		super("Registro a Edicion de Evento", false, false, false, false);
		this.ice = ice;
		this.icu = icu;
		setSize(466, 349);
		setClosable(true);
		setIconifiable(true);
		setMaximizable(true);
		setResizable(true);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		getContentPane().setLayout(new MigLayout("", "[115px][][][][][][][10px][][][28px][24px][][][130px,grow]",
				"[14px][22px][14px][22px][14px][22px][14px][22px][][30px][][][][33px]"));

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

		JLabel lblAsistente = new JLabel("Seleccione asistente:");
		getContentPane().add(lblAsistente, "cell 0 6,alignx left,aligny center");
		comboBoxAsistente = new JComboBox<>();
		getContentPane().add(comboBoxAsistente, "cell 14 6,alignx left,aligny center");

		JLabel lblCodigo = new JLabel("Codigo:");
		getContentPane().add(lblCodigo, "cell 0 8");
		textFieldCodigo = new JTextField();
		getContentPane().add(textFieldCodigo, "cell 14 8,growx");
		textFieldCodigo.setColumns(10);

		JButton btnRegistrar = new JButton("Registrar asistente");
		btnRegistrar.setEnabled(false);
		getContentPane().add(btnRegistrar, "cell 0 12");

		JButton btnCancelar = new JButton("Cancelar");
		getContentPane().add(btnCancelar, "cell 14 12");

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
			} else {
				comboBoxEdicion.setEnabled(false);
				comboBoxEdicion.removeAllItems();
				comboBoxRegistro.setEnabled(false);
				comboBoxRegistro.removeAllItems();
				comboBoxAsistente.setEnabled(false);
				comboBoxAsistente.removeAllItems();
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
			} else {

				comboBoxRegistro.setEnabled(false);
				comboBoxRegistro.removeAllItems();
				comboBoxAsistente.setEnabled(false);
				comboBoxAsistente.removeAllItems();
			}
		});

		comboBoxRegistro.addActionListener(e -> {
			String nombreRegistroSeleccionado = (String) comboBoxRegistro.getSelectedItem();
			if (nombreRegistroSeleccionado != null && !nombreRegistroSeleccionado.equals("Sin tipos de registros")) {
				comboBoxAsistente.setEnabled(true);
				try {
					cargarAsistentes();

				} catch (Exception e1) {

					e1.printStackTrace();
				}

			} else {

				comboBoxRegistro.setEnabled(false);
				comboBoxRegistro.removeAllItems();
				comboBoxAsistente.setEnabled(false);
				comboBoxAsistente.removeAllItems();
			}
		});
		comboBoxAsistente.addActionListener(e -> {
			String nombreAsistente = (String) comboBoxAsistente.getSelectedItem();
			if (nombreAsistente != null && !nombreAsistente.equals("Sin asistentes")) {
				btnRegistrar.setEnabled(true);
			}

		});


		btnRegistrar.addActionListener(e -> {
			String edicionSel = (String) comboBoxEdicion.getSelectedItem();
			String tipoRegistroSel = (String) comboBoxRegistro.getSelectedItem();
			String asistenteSel = (String) comboBoxAsistente.getSelectedItem();
			String codigo = textFieldCodigo.getText().trim();

			if (edicionSel == null || tipoRegistroSel == null || asistenteSel == null) {
				JOptionPane.showMessageDialog(this, "Debe completar todos los campos antes de registrar.",
						"Campos incompletos", JOptionPane.WARNING_MESSAGE);
				return;
			}

			try {
				if (codigo.isEmpty()) {
					ice.altaRegistro(edicionSel, asistenteSel, tipoRegistroSel);
				} else {
					ice.altaRegistro(edicionSel, asistenteSel, tipoRegistroSel, codigo);
				}

				JOptionPane.showMessageDialog(this, "Registro exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);

				cargarEventos();
				comboBoxEdicion.removeAllItems();
				comboBoxRegistro.removeAllItems();
				comboBoxAsistente.removeAllItems();
				textFieldCodigo.setText("");

				comboBoxEdicion.setEnabled(false);
				comboBoxRegistro.setEnabled(false);
				comboBoxAsistente.setEnabled(false);

				this.setVisible(false);

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnCancelar.addActionListener(e -> {
			cargarEventos();
			comboBoxEdicion.removeAllItems();
			comboBoxRegistro.removeAllItems();
			comboBoxAsistente.removeAllItems();
			textFieldCodigo.setText("");

			comboBoxEdicion.setEnabled(false);
			comboBoxRegistro.setEnabled(false);
			comboBoxAsistente.setEnabled(false);

			this.setVisible(false);
		});
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
}
