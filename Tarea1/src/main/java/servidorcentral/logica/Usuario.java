package servidorcentral.logica;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private String nickname;
	private String correo;
	private String nombre;
	private String contrasena;
	private String img;
	
	private final List<String> seguidores;
    private final List<String> seguidos;

	public Usuario(String nick, String correo, String nom, String contrasena, String img) {
		this.nickname = nick;
		this.correo = correo;
		this.nombre = nom;
		this.contrasena = contrasena;
		this.img=img;
		
		this.seguidores = new ArrayList<>();
        this.seguidos = new ArrayList<>();
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
	
	public List<String> getSeguidores() {
        return seguidores;
    }

    public List<String> getSeguidos() {
        return seguidos;
    }

    public void agregarSeguidor(String seguidor) {
        if (seguidor != null) {
            seguidores.add(seguidor);
        }
    }
    
    public void agregarSeguido(String seguido) {
        if (seguido != null) {
            seguidos.add(seguido);
        }
    }  
    
    public void sacarSeguidor(String seguidor) {
        if (seguidor != null) {
            seguidores.remove(seguidor);
        }
    }
    
    public void sacarSeguido(String seguido) {
        if (seguido != null) {
            seguidos.remove(seguido);
        }
    }
}
