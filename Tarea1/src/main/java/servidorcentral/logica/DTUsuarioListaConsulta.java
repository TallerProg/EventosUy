package servidorcentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTUsuarioListaConsulta {
	private String nickname;
	private String correo;
	private String nombre;
	private String apellido;
	private LocalDate fNacimiento;
	private String descripcion;
	private String url;
	@XmlTransient
	private List<Edicion> ediciones;
	@XmlTransient
	private List<Registro> registros;
	private List<DTEdicion> dtEdiciones;
	private List<DTRegistro> dtRegistros;
	private List<String> seguidores;
	private List<String> seguidos;
	private String img; 
	private DTInstitucion ins;

	
	public DTUsuarioListaConsulta() {
		this.nickname = null;
		this.correo = null;
		this.nombre = null;
		this.apellido = null;
		this.fNacimiento = null;
		this.descripcion = null;
		this.url = null;
		this.img = null;
		this.ins = null;
		this.ediciones = new ArrayList<>();
		this.registros = new ArrayList<>();
		this.seguidores = new ArrayList<>();
		this.seguidos = new ArrayList<>();
	}

	public DTUsuarioListaConsulta(String nick, String correo, String nombre, String apellido, LocalDate fNacimiento,
			String descripcion, String url, List<Edicion> ediciones, List<Registro> registros, String img, DTInstitucion ins) {
		this.nickname = nick;
		this.correo = correo;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fNacimiento = fNacimiento;
		this.descripcion = descripcion;
		this.url = url;
		this.ediciones = ediciones;
		this.registros = registros;
		this.img = img;
		this.ins = ins;
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
	
	public String getImg() {
		return img;
	}
	
	public void setImg(String img) {
		this.img = img;
	}
	
	public DTInstitucion getIns() {
		return ins;
	}
	
	public void setIns(DTInstitucion ins) {
		this.ins = ins;
	}
	
	 public  List<DTEdicion> getDtEdicion() {
	        return dtEdiciones;
	    }

	 public  List<DTRegistro> getDtRegistro() {
	        return dtRegistros;
	 }
	 
	public void setSeguidores(List<String> seguidores) {
		this.seguidores = seguidores;
	}

	public void setSeguidos(List<String> seguidos) {
		this.seguidos = seguidos;
	}

}