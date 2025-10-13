package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Asistente extends Usuario {

	private String apellido;
	private LocalDate fNacimiento;

	private List<Registro> registros;

	private Institucion institucion;

	private List<Patrocinio> patrocinios;

	public Asistente(String nickname, String correo, String nombre, String apellido, LocalDate fNacimiento,
			Institucion ins, String contrasena, String img) {

		super(nickname, correo, nombre, contrasena, img);
		this.apellido = apellido;
		this.fNacimiento = fNacimiento;
		this.registros = new ArrayList<>();
		this.institucion = ins;
		this.patrocinios = new ArrayList<>();
		
	}

	public Asistente(String nickname, String correo, String nombre, String apellido, LocalDate fNacimiento, String contrasena, String img) {

		super(nickname, correo, nombre, contrasena, img);
		this.apellido = apellido;
		this.fNacimiento = fNacimiento;
		this.registros = new ArrayList<>();
		this.institucion = null;
		this.patrocinios = new ArrayList<>();
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public LocalDate getfNacimiento() {
		return fNacimiento;
	}

	public void setfNacimiento(LocalDate fNacimiento) {
		this.fNacimiento = fNacimiento;
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<Patrocinio> getPatrocinios() {
		return patrocinios;
	}

	public void addPatrocinio(Patrocinio patrocinio) {
		this.patrocinios.add(patrocinio);
	}

	public List<String> registrosFechas() {
		List<String> registrosFechas = new ArrayList<>();
		for (Registro r : registros) {
			registrosFechas.add(r.getFInicio().toString());
		}

		return registrosFechas;
	}

	public void addRegistro(Registro r) {
		this.registros.add(r);
	}

}
