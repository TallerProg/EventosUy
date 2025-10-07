package ServidorCentral.logica;

public class Usuario {
	private String nickname;
	private String correo;
	private String nombre;
	private String contrasena;

	public Usuario(String nick, String correo, String nom, String contrasena) {
		this.nickname = nick;
		this.correo = correo;
		this.nombre = nom;
		this.contrasena = contrasena;
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
	public String getContrasena() {
		return contrasena;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

}
