package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Edicion {

	private String nombre;
	private LocalDate fInicio;
	private LocalDate fFin;
	private LocalDate fAlta;
	private String ciudad;
	private String pais;
	private String sigla;
	private List<TipoRegistro> tipoRegistros;
	private List<Organizador> organizadores;
	private List<Registro> registros;
	private List<Patrocinio> patrocinios;
	private Evento evento;


	public Edicion(String nombre, String sigla, LocalDate fInicio, LocalDate fFin,LocalDate fAlta, String ciudad, String pais,
			Evento evento) {
		this.nombre = nombre;
		this.fInicio = fInicio;
		this.fFin = fFin;
		this.ciudad = ciudad;
		this.pais = pais;
		this.sigla = sigla;
		this.fAlta = fAlta;
		this.evento = evento;
		this.tipoRegistros = new ArrayList<>();
		this.organizadores = new ArrayList<>();
		this.registros = new ArrayList<>();
		this.patrocinios = new ArrayList<>();
	}

	public LocalDate getFechaAlta() { return fAlta; }
    public void setFechaAlta(LocalDate fAlta) { this.fAlta = fAlta; }

	public Evento getEvento() {
		return evento;
	}

	public String getNombre() {
		return nombre;
	}

	public LocalDate getfInicio() {
		return fInicio;
	}

	public LocalDate getfFin() {
		return fFin;
	}


	public LocalDate getFAlta() {
		return fAlta;
	}


	public String getCiudad() {
		return ciudad;
	}



	public String getPais() {
		return pais;
	}



	public String getSigla() {
		return sigla;
	}


	public List<TipoRegistro> getTipoRegistros() {
		return tipoRegistros;
	}


	public List<Organizador> getOrganizadores() {
		return organizadores;
	}


	public List<Registro> getRegistros() {
		return registros;
	}


	public List<Patrocinio> getPatrocinios() {
		return patrocinios;
	}

	public void addLinkRegistro(Registro reg) {
		if (reg != null && !this.registros.contains(reg)) {
			this.registros.add(reg);
			reg.setEdicion(this);
		}
	}

	public void addLinkPatrocinio(Patrocinio reg) {
		if (reg != null) {
			this.patrocinios.add(reg);
			reg.setEdicion(this);
		}
	}

	public void agregarTipoRegistro(TipoRegistro tr) { 
		tipoRegistros.add(tr);
	}

	public TipoRegistro getEdicionTR(String nombreTR) {
		if (nombreTR == null)
			return null;
		for (TipoRegistro tr : tipoRegistros) {
			if (tr.getNombre().equalsIgnoreCase(nombreTR)) {
				return tr;
			}
		}
		return null;
	}

	public boolean registrado(Asistente asistente) {
		for (Registro reg : registros) {
			if (reg.getAsistente().equals(asistente)) {
				return true;
			}
		}
		return false; 
	}

	public boolean habilitadoAsistente(String nombreTR, Asistente asistente) throws Exception {
		TipoRegistro tr = getEdicionTR(nombreTR);
		if (tr == null) {
			throw new Exception("tipo registro no existe");
		}
		if (tr != null && !registrado(asistente)) {
			return !tr.soldOutTipReg();
		} else {
			throw new Exception(asistente.getNickname() + " Ya esta registrado");
		}
	}

	public DTTipoRegistro datosTipoRegistroEdicion(String nombreTipoR) {
		TipoRegistro r = getEdicionTR(nombreTipoR);
		if (r != null) {
			return r.getDTTipoRegistro();
		}
		return null;
	}

	public DTEdicion getDTEdicion() {
		List<DTTipoRegistro> trs = new ArrayList<>();
		for (TipoRegistro tr : this.tipoRegistros) {
			trs.add(tr.getDTTipoRegistro());
		}

		List<DTOrganizador> orgs = new ArrayList<>();
		for (Organizador o : this.organizadores) {
			orgs.add(o.getDTOrganizador());
		}

		List<DTRegistro> regs = new ArrayList<>();
		for (Registro r : this.registros) {
			regs.add(r.getDTRegistro());
		}

		List<DTPatrocinio> pats = new ArrayList<>();
		for (Patrocinio p : this.patrocinios) {
			pats.add(p.getDTPatrocinio());
		}

		return new DTEdicion(nombre, sigla, fInicio, fFin, fAlta, ciudad, pais, trs, orgs, regs, pats);
	}

	public boolean existeTR(String nombreTR) { 
		for (TipoRegistro tr : tipoRegistros) {
			if (tr.getNombre().equalsIgnoreCase(nombreTR)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
	    return this.getNombre();
	}
}
