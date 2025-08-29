package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.List;

public interface IControllerEvento {

	
	public void altaEdicionDeEvento(String nombre, String sigla, String ciudad, String pais,LocalDate fInicio, LocalDate fFin,Evento evento, Organizador org) throws Exception;
	
	public DTEdicion consultaEdicionDeEvento(String nombreEvento, String nombreEdicion);
	
	public DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoR);
	
	public List<Evento> listarEventos();
    public List<Categoria> getCategorias();
    public Edicion findEdicion(String nombre);
    public Evento findEvento(String nombre);
    public boolean existeEvento(String nombre);
   
    public Evento getEvento(String nombreEvento);
    public void altaRegistro(String nombreEdicion, String nickAsistente, String nombreTR, String codigo) throws Exception;
    public void altaRegistro(String nombreEdicion, String nickAsistente, String nombreTR) throws Exception;

    public List<Organizador> listarOrganizadores();
    
    public List<String> listarEdicionesDeEvento(String nombreEvento);

    public List<Edicion> listarEdiciones();
    
    public DTevento consultaEvento(String nombreEvento);
    
    public void altaTipoRegistro(String nombreTR, String descripcion, Float costo, Integer cupo, Edicion edicion) throws Exception;


    
    public void altaEvento(String nombre, String descripcion, LocalDate fecha, String sigla, List<Categoria> categorias) throws Exception;

	public String obtenerNombreEdicionPorEvento(String nombreEvento);

	public List<DTPatrocinio> listarPatrociniosDeEdicion(String nombreEdicion);

	public DTPatrocinio consultaPatrocinio(String nombreEdicion, String codigoPat);
	
}