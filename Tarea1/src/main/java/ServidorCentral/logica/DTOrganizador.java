package ServidorCentral.logica;

public class DTOrganizador {
	private String nickname;
	private String correo;
	private String nombre;
	private String descripcion;
	private String url;

	public DTOrganizador(String nickname, String correo, String nombre, String descripcion, String url) {
		this.nickname = nickname;
		this.correo = correo;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.url = url;
	}

	public String getNickname() {
		return nickname;
	}

	public String getCorreo() {
		return correo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getUrl() {
		return url;
	}
}
