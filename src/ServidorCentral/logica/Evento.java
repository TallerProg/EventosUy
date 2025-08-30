package ServidorCentral.logica;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

public class Evento {

	private String nombre;
	private String sigla;
	private String descripcion;
	private LocalDate fAlta;
	private List<Categoria> categorias = new ArrayList<>();
	private List<Edicion> ediciones = new ArrayList<>();

	public Evento(String nombre, String sigla, String descripcion, LocalDate fAlta, List<Categoria> categorias) {
		this.nombre = nombre;
		this.sigla = sigla;
		this.descripcion = descripcion;
		this.fAlta = fAlta;
		this.categorias = categorias;
	}

	// Getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	// SOBRECARGO TOSTRING PARA MOSTRAR NOMBRE EN COMBOBOX
	public String toString() {
		return nombre;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFAlta() {
		return fAlta;
	}

	public void setFAlta(LocalDate fAlta) {
		this.fAlta = fAlta;
	}

	public List<Categoria> getCategoria() {
		return categorias;
	}

	// Métodos para ediciones
	public void agregarEdicion(Edicion edicion) {
		if (edicion != null && !ediciones.contains(edicion)) {
			ediciones.add(edicion);
		}
	}

	public boolean tieneEdicion(String nombreEdicion) {
		for (Edicion e : ediciones) {
			if (e.getNombre().equalsIgnoreCase(nombreEdicion)) {
				return true;
			}
		}
		return false;
	}

	public Edicion findEdicion(String nombreEdicion) {
		for (Edicion ed : ediciones) {
			if (ed.getNombre().equalsIgnoreCase(nombreEdicion)) {
				return ed;
			}
		}
		return null;
	}

	public List<Edicion> getEdiciones() {
		return new ArrayList<>(ediciones);
	}

	public DTevento getDTevento() {
		return new DTevento(nombre, sigla, descripcion, fAlta, categorias, ediciones);
	}
}
