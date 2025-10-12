package ServidorCentral.logica;

import java.util.ArrayList;
import java.util.List;

public class Organizador extends Usuario {

	private String descripcion;
	private String url;
	private List<Edicion> ediciones = new ArrayList<>();

    public Organizador(String nickname, String correo, String nombre, String descripcion, String url, String contrasena,String img) {
        super(nickname, correo, nombre, contrasena,img);
        this.descripcion = descripcion;
        this.url = url;
    }
    
    public Organizador(String nickname, String correo, String nombre, String descripcion, String contrasena,String img) {
    	super(nickname, correo, nombre, contrasena,img);
    	this.descripcion=descripcion;
    	this.url=null;
    }
    
    public  List<Edicion> getEdiciones(){
    	return new ArrayList<>(ediciones);
    }
    
    public void agregarEdicionOrg(Edicion ed) {
        this.ediciones.add(ed);
        if (!ed.getOrganizadores().contains(this)) {
            ed.getOrganizadores().add(this);
        }
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

	public DTOrganizador getDTOrganizador() {
		return new DTOrganizador(getNickname(), getCorreo(), getNombre(), descripcion, url);
	}

}
