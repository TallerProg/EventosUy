package ServidorCentral.logica;


import java.util.List;

public class DTUsuarioLista{
	private String nickname;
	private String correo;
	private String nombre;
	private List<Edicion> ediciones;
	private List<Registro> registros;
	
	
	public DTUsuarioLista(String nick, String correo, String nombre, List<Edicion> ediciones, List<Registro> registros) {
		this.nickname=nick;
		this.correo=correo;
		this.nombre=nombre;
		this.ediciones=ediciones;
		this.registros=registros;
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
	
	public List<Edicion> getEdiciones(){
		return ediciones;
	}
	
	public List<Registro> getRegistros(){
		return registros;
	}
}