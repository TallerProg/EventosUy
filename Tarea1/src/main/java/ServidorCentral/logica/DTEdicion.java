package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.List;

public class DTEdicion {
	private String nombre;
	private String sigla;
	private LocalDate fInicio;
	private LocalDate fFin;
	private LocalDate fAlta;
	private String ciudad;
	private String pais;
    private String estado;
    private String imagenWebPath;
    
	private List<DTTipoRegistro> tipoRegistros;
	private List<DTOrganizador> organizadores;
	private List<DTRegistro> registros;
	private List<DTPatrocinio> patrocinios;

	public DTEdicion(String nombre, String sigla, LocalDate fInicio, LocalDate fFin, LocalDate fAlta, String ciudad,
			String pais, List<DTTipoRegistro> tipoRegistros, List<DTOrganizador> organizadores,
			List<DTRegistro> registros, List<DTPatrocinio> patrocinios, String estado, String imagenWebPath ) {
		this.nombre = nombre;
		this.sigla = sigla;
		this.fInicio = fInicio;
		this.fFin = fFin;
		this.fAlta = fAlta;
		this.ciudad = ciudad;
		this.pais = pais;
		this.tipoRegistros = tipoRegistros;
		this.organizadores = organizadores;
		this.registros = registros;
		this.patrocinios = patrocinios;
        this.estado = estado; 
        this.imagenWebPath=imagenWebPath;
	}

	public String getNombre() {
		return nombre;
	}

	public String getSigla() {
		return sigla;
	}

	public LocalDate getfInicio() {
		return fInicio;
	}

	public LocalDate getfFin() {
		return fFin;
	}

	public LocalDate getfAlta() {
		return fAlta;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getPais() {
		return pais;
	}

	public List<DTTipoRegistro> getTipoRegistros() {
		return tipoRegistros;
	}

	public List<DTOrganizador> getOrganizadores() {
		return organizadores;
	}

	public List<DTRegistro> getRegistros() {
		return registros;
	}

	public List<DTPatrocinio> getPatrocinios() {
		return patrocinios;
	}
    
    public String getEstado() { 
    	return estado; }
    
    public String getImagenWebPath() {
    	return imagenWebPath;
    }
}


