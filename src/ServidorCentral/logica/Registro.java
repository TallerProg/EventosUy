package ServidorCentral.logica;

import java.time.LocalDate;

public class Registro {

	private LocalDate fInicio;
	private Float costo;

	// Relación con Edicion (muchos a uno)
	private Edicion edicion;

	// Relación con Asistente (muchos a uno)
	private Asistente asistente;

	// Relación opcional con Patrocinio (muchos a uno)
	private Patrocinio patrocinio;

	private TipoRegistro tipoRegistro;

	// Constructor
	public Registro(Float costo, Edicion edicion, Asistente asistente, TipoRegistro tipoRegistro) {
		this.fInicio = LocalDate.now();
		this.costo = costo;
		this.edicion = edicion;
		this.asistente = asistente;
		this.tipoRegistro = tipoRegistro;
	}
	public Registro(Float costo, Edicion edicion, Asistente asistente, TipoRegistro tipoRegistro, LocalDate fInicio) {
		this.fInicio = fInicio;
		this.costo = costo;
		this.edicion = edicion;
		this.asistente = asistente;
		this.tipoRegistro = tipoRegistro;
		
	}

	// Getters y Setters
	public LocalDate getFInicio() {
		return fInicio;
	}


	public Float getCosto() {
		return costo;
	}


	public Edicion getEdicion() {
		return edicion;
	}
	
	public void setEdicion(Edicion edicion) {
		this.edicion = edicion;
	}


	public Asistente getAsistente() {
		return asistente;
	}


	public Patrocinio getPatrocinio() {
		return patrocinio;
	}


	public TipoRegistro getTipoRegistro() {
		return tipoRegistro;
	}
	
	public void setPatrocinio(Patrocinio patrocinio) {
		this.patrocinio = patrocinio;
	}

	public DTRegistroDetallado getDTRegistroDetallado() {
		String nombreEvento = (edicion != null && edicion.getEvento() != null) ? edicion.getEvento().getNombre() : "";
		String nombreEdicion = (edicion != null) ? edicion.getNombre() : "";
		String tipoReg = (tipoRegistro != null) ? tipoRegistro.getNombre() : "";
		String nombrePatrocinador = (patrocinio != null) ? patrocinio.getInstitucion().getNombre() : null;

		return new DTRegistroDetallado(fInicio, nombreEvento, nombreEdicion, tipoReg, nombrePatrocinador, costo);
	}

	public DTRegistro getDTRegistro() {
		String nomAsistente = (asistente != null) ? asistente.getNombre() : null;
		String nomTipo = (tipoRegistro != null) ? tipoRegistro.getNombre() : null;
		String codigo = (patrocinio != null) ? patrocinio.getCodigo() : null;

		return new DTRegistro(fInicio, costo, nomAsistente, nomTipo, codigo);

	}

	// Métodos relacionados (comentados)
}
