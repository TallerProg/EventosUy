package ServidorCentral.logica;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Edicion extends Evento {

    // Atributos 
    private String nombre;
    private Date fInicio;
    private Date fFin;
    private String ciudad;
    private String pais;

    // Relaciones
    private List<TipoRegistro> tipoRegistros;
    private List<Organizador> organizadores;
    private List<Registro> registros;
    private List<Patrocinio> patrocinios;

    // Constructor
    public Edicion(String nombre, String sigla, String descripcion, Date fAlta, List<Categoria> categorias,
    		Date fInicio, Date fFin, String ciudad, String pais) {
    	super(nombre, sigla, descripcion, fAlta, categorias);
    	this.fInicio = fInicio;
    	this.fFin = fFin;
    	this.ciudad = ciudad;
    	this.pais = pais;

    	this.tipoRegistros = new ArrayList<>();
    	this.organizadores = new ArrayList<>();
    	this.registros = new ArrayList<>();
    	this.patrocinios = new ArrayList<>();
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Date getfInicio() { return fInicio; }
    public void setfInicio(Date fInicio) { this.fInicio = fInicio; }

    public Date getfFin() { return fFin; }
    public void setfFin(Date fFin) { this.fFin = fFin; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public List<TipoRegistro> getTipoRegistros() { return tipoRegistros; }
    public void setTipoRegistros(List<TipoRegistro> tipoRegistros) { this.tipoRegistros = tipoRegistros; }

    public List<Organizador> getOrganizadores() { return organizadores; }
    public void setOrganizadores(List<Organizador> organizadores) { this.organizadores = organizadores; }

    public List<Registro> getRegistros() { return registros; }
    public void setRegistros(List<Registro> registros) { this.registros = registros; }

    public List<Patrocinio> getPatrocinios() { return patrocinios; }
    public void setPatrocinios(List<Patrocinio> patrocinios) { this.patrocinios = patrocinios; }

    // Métodos 
    public void addLinkRegistro(Registro reg) {
        if (reg != null && !this.registros.contains(reg)) {
            this.registros.add(reg);
            reg.setEdicion(this);
        }
    }
    public TipoRegistro getEdicionTR(String nombreTR) {
        if (nombreTR == null) return null;
        for (TipoRegistro tr : tipoRegistros) {
            if (tr.getNombre().equalsIgnoreCase(nombreTR)) {
                return tr;
            }
        }
        return null;
    }
    public boolean habilitadoAsistente(String nombreTR, Asistente asistente) {
        TipoRegistro tr = getEdicionTR(nombreTR);
        if (tr == null) {
            return false;
        }
        return tr.habilitaAsistente(asistente);
    }

    public DTTipoRegistro datosTipoRegistroEdicion(String nombreTipoR) {
        TipoRegistro r = getEdicionTR(nombreTipoR);
        if (r != null) {
            return r.getDTTipoRegistro();
        }
        return null;
    }
}

