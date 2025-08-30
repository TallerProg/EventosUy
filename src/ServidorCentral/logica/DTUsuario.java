package ServidorCentral.logica;

public class DTUsuario {

	private String nickname;
	private String correo;
	private String nombre;

	public DTUsuario(String nick, String correo, String nom) {
		this.nickname = nick;
		this.correo = correo;
		this.nombre = nom;
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

}
