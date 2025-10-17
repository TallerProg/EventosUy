package servidorcentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DTevento {

	private String nombre;
	private String sigla;
	private String descripcion;
	private LocalDate fAlta;
	private String img;
	private List<Categoria> categorias = new ArrayList<>();
	private List<Edicion> ediciones = new ArrayList<>();

	public DTevento(String nombre, String sigla, String descripcion, LocalDate fAlta, List<Categoria> categorias,
			List<Edicion> ediciones,String img) {
		this.nombre = nombre;
		this.sigla = sigla;
		this.descripcion = descripcion;
		this.fAlta = fAlta;
		this.img = img;
		if (categorias != null)
			this.categorias = categorias;
		if (ediciones != null)
			this.ediciones = ediciones;
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
	
	public List<DTCategoria> getDTCategorias() {
		List<DTCategoria> dtCategorias = new ArrayList<>();
		for (Categoria cat : categorias) {
			dtCategorias.add(new DTCategoria(cat.getNombre()));
		}
		return dtCategorias;
	}

	public List<Edicion> getEdiciones() {
		return ediciones;
	}
	public String getImg() {
		return img;
	}
}
