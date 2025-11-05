package servidorcentral.logica;

import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTOrganizadorDetallado {
    private  String nickname;
    private  String correo;
    private  String nombre;
    private  String descripcion;
    private  String url;
    private  String img;
    private  List<DTEdicion> ediciones; // nunca null
    
    public DTOrganizadorDetallado() {
		this.nickname = "";
		this.correo = "";
		this.nombre = "";
		this.descripcion = "";
		this.url = "";
		this.img = "";
		this.ediciones = new ArrayList<>();
	}

    public DTOrganizadorDetallado(String nickname,
                                String correo,
                                String nombre,
                                String descripcion,
                                String url,
                                String img,
                                List<DTEdicion> ediciones) {
        this.nickname = nickname;
        this.correo = correo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.url = url;
        this.img = img;
        // copia defensiva
        this.ediciones = (ediciones == null) ? new ArrayList<>() : new ArrayList<>(ediciones);
    }

    public String getNickname()   { return nickname; }
    public String getCorreo()     { return correo; }
    public String getNombre()     { return nombre; }
    public String getDescripcion(){ return descripcion; }
    public String getUrl()        { return url; }
    public String getImg()        { return img; }
    public List<DTEdicion> getEdiciones() { return new ArrayList<>(ediciones); }
    public void setEdiciones(List<DTEdicion> ediciones) {
		this.ediciones = (ediciones == null) ? new ArrayList<>() : new ArrayList<>(ediciones);
	}
    public void setNickname(String nickname) {
        this.nickname = (nickname == null) ? "" : nickname;
    }

    public void setCorreo(String correo) {
        this.correo = (correo == null) ? "" : correo;
    }

    public void setNombre(String nombre) {
        this.nombre = (nombre == null) ? "" : nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = (descripcion == null) ? "" : descripcion;
    }

    public void setUrl(String url) {
        this.url = (url == null) ? "" : url;
    }

    public void setImg(String img) {
        this.img = (img == null) ? "" : img;
    }

}
