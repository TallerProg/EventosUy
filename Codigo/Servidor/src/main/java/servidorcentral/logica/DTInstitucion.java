package servidorcentral.logica;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTInstitucion{
	private String nombre;
	private String descripcion;
	private String url;
	private String imagen;

	public DTInstitucion() {
		
	}
	public DTInstitucion(String nombre, String descripcion, String url, String imagen) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.url = url;
		this.imagen = imagen;
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

	public String getImagen() {
		return imagen;
	}
}