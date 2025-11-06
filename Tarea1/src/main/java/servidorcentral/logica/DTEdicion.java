	package servidorcentral.logica;

import java.time.LocalDate;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;
@XmlAccessorType(XmlAccessType.FIELD)

public class DTEdicion {
	private String nombre;
	private String sigla;
	private LocalDate fInicio;
	private String fInicioS;
	private LocalDate fFin;
	private String fFinS;
	private LocalDate fAlta;
	private String ciudad;
	private String pais;
    private String estado;
    private String imagenWebPath;
    
	private List<DTTipoRegistro> tipoRegistros;
	private List<DTOrganizador> organizadores;
	private List<DTRegistro> registros;
	private List<DTPatrocinio> patrocinios;
	@XmlTransient
	private DTevento evento;
	
	public DTEdicion() {}

	public DTEdicion(String nombre, String sigla, LocalDate fInicio, LocalDate fFin, LocalDate fAlta, String ciudad,
			String pais, List<DTTipoRegistro> tipoRegistros, List<DTOrganizador> organizadores,
			List<DTRegistro> registros, List<DTPatrocinio> patrocinios, String estado, String imagenWebPath, DTevento evento) {
		this.nombre = nombre;
		this.sigla = sigla;
		this.fInicio = fInicio;
        this.fInicioS = (fInicio != null) ? fInicio.toString() : null;
		this.fFin = fFin;
	    this.fFinS = (fFin != null) ? fFin.toString() : null;
		this.fAlta = fAlta;
		this.ciudad = ciudad;
		this.pais = pais;
		this.tipoRegistros = tipoRegistros;
		this.organizadores = organizadores;
		this.registros = registros;
		this.patrocinios = patrocinios;
        this.estado = estado; 
        this.imagenWebPath=imagenWebPath;
        this.evento = evento;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setEstado(String estado) {
        this.estado = estado;
	}

	public String getSigla() {
		return sigla;
	}

	public LocalDate getfInicio() {
		return fInicio;
	}
	public String getfInicioS() {
		return fInicioS;
	}

	public LocalDate getfFin() {
		return fFin;
	}
	public String getfFinS() {
		return fFinS;
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
	public DTevento getEvento() {
		return evento;
	}
    
    public String getEstado() { 
    	return estado; }
    
    public String getImagenWebPath() {
    	return imagenWebPath;
    }
}


