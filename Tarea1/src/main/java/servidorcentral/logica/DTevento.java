package servidorcentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTevento {

	private String nombre;
	private String sigla;
	private String descripcion;
	private LocalDate fAlta;
	private String img;
	private Boolean finalizado;
	@XmlTransient
	private List<Categoria> categorias = new ArrayList<>();
	@XmlTransient
	private List<Edicion> ediciones = new ArrayList<>();
	private List<DTCategoria> dtCategorias = new ArrayList<>();
	private List<DTEdicion> dtEdiciones = new ArrayList<>();

	public DTevento() {
	}

	public DTevento(String nombre, String sigla, String descripcion, LocalDate fAlta, List<Categoria> categorias,
			List<Edicion> ediciones, String img, Boolean finalizado) {
		this.nombre = nombre;
		this.sigla = sigla;
		this.descripcion = descripcion;
		this.fAlta = fAlta;
		this.img = img;
		this.finalizado = finalizado;
		if (categorias != null) {
			this.categorias = categorias;
			for (Categoria c : categorias)
				this.dtCategorias.add(new DTCategoria(c.getNombre()));
		}
		if (ediciones != null) {
			this.ediciones = ediciones;
			for (Edicion e : ediciones)
				this.dtEdiciones.add(e.getDTEdicion());
		}
	}

	public String getNombre() {
		return nombre;
	}

	public String getSigla() {
		return sigla;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public LocalDate getFAlta() {
		return fAlta;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	

	public List<Edicion> getEdiciones() {
		return ediciones;
	}

	public String getImg() {
		return img;
	}


    public List<DTCategoria> getDtCategorias() {
        return dtCategorias;
    }

    public List<DTEdicion> getDtEdiciones() {
        return dtEdiciones;
    }

	public Boolean getFinalizado() {
		return finalizado;
	}
}
