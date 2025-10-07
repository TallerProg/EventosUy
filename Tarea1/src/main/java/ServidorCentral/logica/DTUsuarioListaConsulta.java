package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DTUsuarioListaConsulta {
	private String nickname;
	private String correo;
	private String nombre;
	private String apellido;
	private LocalDate fNacimiento;
	private String descripcion;
	private String url;
	private List<Edicion> ediciones;
	private List<Registro> registros;

	public DTUsuarioListaConsulta() {
		this.nickname = null;
		this.correo = null;
		this.nombre = null;
		this.apellido = null;
		this.fNacimiento = null;
		this.descripcion = null;
		this.url = null;
		this.ediciones = new ArrayList<>();
		this.registros = new ArrayList<>();
	}

	public DTUsuarioListaConsulta(String nick, String correo, String nombre, String apellido, LocalDate fNacimiento,
			String descripcion, String url, List<Edicion> ediciones, List<Registro> registros) {
		this.nickname = nick;
		this.correo = correo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fNacimiento = fNacimiento;
		this.descripcion = descripcion;
		this.url = url;
		this.ediciones = ediciones;
		this.registros = registros;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public LocalDate getFNacimiento() {
		return fNacimiento;
	}

	public void setFNacimiento(LocalDate fNacimiento) {
		this.fNacimiento = fNacimiento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Edicion> getEdiciones() {
		return ediciones;
	}

	public void setEdiciones(List<Edicion> ediciones) {
		this.ediciones = ediciones;
	}

	public List<Registro> getRegistros() {
		return registros;
	}

	public void setRegistros(List<Registro> registros) {
		this.registros = registros;
	}
}