package servidorcentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DTevento {

	private String nombre;
	private String sigla;
	private String descripcion;
	private LocalDate fAlta;
	private List<Categoria> categorias = new ArrayList<>();
	private List<Edicion> ediciones = new ArrayList<>();

	public DTevento(String nombre, String sigla, String descripcion, LocalDate fAlta, List<Categoria> categorias,
			List<Edicion> ediciones) {
		this.nombre = nombre;
		this.sigla = sigla;
		this.descripcion = descripcion;
		this.fAlta = fAlta;
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

	public List<Edicion> getEdiciones() {
		return ediciones;
	}
}
