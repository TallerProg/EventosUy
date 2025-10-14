package servidorcentral.logica;

public class Usuario {
	private String nickname;
	private String correo;
	private String nombre;
	private String contrasena;
	private String img;

	public Usuario(String nick, String correo, String nom, String contrasena, String img) {
		this.nickname = nick;
		this.correo = correo;
		this.nombre = nom;
		this.contrasena = contrasena;
		this.img=img;
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

	public String getImg() {
		return img;
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
	public void setImg(String img) {
		this.img = img;
	}

}
