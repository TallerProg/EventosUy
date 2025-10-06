package src.ServidorCentral.logica;

import java.util.List;
import java.util.ArrayList;

public class TipoRegistro {

	private String nombre; 
	private String descripcion;
	private Float costo;
	private int cupo;

	private Edicion edicion;

	private List<Patrocinio> patrocinioList;

	private List<Registro> registros;

	public TipoRegistro(String nombre, String descripcion, Float costo, int cupo, Edicion edicion) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.costo = costo;
		this.cupo = cupo;
		this.edicion = edicion;
		this.patrocinioList = new ArrayList<>();
		this.registros = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Float getCosto() {
		return costo;
	}

	public int getCupo() {
		return cupo;
	}
	public Edicion getEdicion() {
		return edicion;
	}

	public void setEdicion(Edicion edicion) {
		this.edicion = edicion;
	}

	public List<Patrocinio> getPatrocinioList() {
		return patrocinioList;
	}
	
	public List<Registro> getRegistros() {
		return registros;
	}

	public void setPatrocinioList(List<Patrocinio> patrocinioList) {
		this.patrocinioList = patrocinioList;
	}

	public void addLinkRegistro(Registro reg) {
		this.registros.add(reg);
	}

	public void addLinkPatrocinio(Patrocinio reg) {
		this.patrocinioList.add(reg);
	}

	public boolean soldOutTipReg() {
		return registros.size() >= cupo;
	}

	public DTTipoRegistro getDTTipoRegistro() {
		return new DTTipoRegistro(nombre, descripcion, costo, cupo);
	}
	
	public List<String> registrosFechas() {
		List<String> registrosFechas = new ArrayList<>();
		for (Registro r : registros) {
			registrosFechas.add(r.getFInicio().toString());
		}

		return registrosFechas;
	}
}
