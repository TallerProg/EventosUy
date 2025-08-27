package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Edicion extends Evento {

    // Atributos 
    private String nombre;
    private LocalDate fInicio;
    private LocalDate fFin;
    private String ciudad;
    private String pais;

    // Relaciones
    private List<TipoRegistro> tipoRegistros;
    private List<Organizador> organizadores;
    private List<Registro> registros;
    private List<Patrocinio> patrocinios;

    // Constructor
    public Edicion(String nombre, String sigla, String descripcion, java.util.Date fAlta, Categoria categoria,
    LocalDate fInicio, LocalDate fFin, String ciudad, String pais) {
    	super(nombre, sigla, descripcion, fAlta, categoria);
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

    public LocalDate getfInicio() { return fInicio; }
    public void setfInicio(LocalDate fInicio) { this.fInicio = fInicio; }

    public LocalDate getfFin() { return fFin; }
    public void setfFin(LocalDate fFin) { this.fFin = fFin; }

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
    // public void addLinkRegistro(Registro reg) { }
    // public TipoRegistro getEdicionTR(String nombreTR) { return null; }
    // public boolean habilitadoAsistente(String nombreTR, Asistente asistente) { return false; }

}
