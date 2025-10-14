package servidorcentral.logica;

import java.time.LocalDate;

public class DTPatrocinio {

	private String codigo;
	private LocalDate fInicio;
	private int registroGratuito;
	private Float monto;
	private ETipoNivel nivel;

	private String institucion;
	private String edicion;
	private String tipoRegistro;

	public DTPatrocinio(String codigo, LocalDate fInicio, int registroGratuito, Float monto, ETipoNivel nivel,
			String institucion, String edicion, String tipoRegistro) {
		this.codigo = codigo;
		this.fInicio = fInicio;
		this.registroGratuito = registroGratuito;
		this.monto = monto;
		this.nivel = nivel;
		this.institucion = institucion;
		this.edicion = edicion;
		this.tipoRegistro = tipoRegistro;
	}

	public String getCodigo() {
		return codigo;
	}

	public LocalDate getFInicio() {
		return fInicio;
	}

	public int getRegistroGratuito() {
		return registroGratuito;
	}

	public Float getMonto() {
		return monto;
	}

	public ETipoNivel getNivel() {
		return nivel;
	}

	public String getInstitucion() {
		return institucion;
	}

	public String getEdicion() {
		return edicion;
	}

	public String getTipoRegistro() {
		return tipoRegistro;
	}

}
