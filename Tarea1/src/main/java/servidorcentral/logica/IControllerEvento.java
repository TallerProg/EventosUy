package servidorcentral.logica;

import servidorcentral.excepciones.NombreTRUsadoException;

import java.time.LocalDate;
import java.util.List;

public interface IControllerEvento {

	public void altaEdicionDeEvento(String nombre, String sigla, String ciudad, String pais, LocalDate fInicio, 
			LocalDate fFin, LocalDate fAlta, Evento evento, Organizador org, String imagenWebPath) throws Exception;

	public DTEdicion consultaEdicionDeEvento(String nombreEvento, String nombreEdicion);

	public DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoR);

	public List<Evento> listarEventos();
    public List<Categoria> getCategorias();	
    public Edicion findEdicion(String nombre);
    public boolean existeTR(Edicion edicion, String nombreTR);
    public boolean existeTRNombre(String nombreEd, String nombreTR);
    public boolean existeEvento(String nombre);
    public Evento getEvento(String nombreEvento);
    public void altaRegistro(String nombreEdicion, String nickAsistente, String nombreTR, String codigo) throws Exception;
    public void altaRegistro(String nombreEdicion, String nickAsistente, String nombreTR) throws Exception;
    public void altaRegistro(String nombreEdicion, String nickAsistente, String nombreTR, String codigo, LocalDate fecha) throws Exception;
    public void altaRegistro(String nombreEdicion, String nickAsistente, String nombreTR, LocalDate fecha) throws Exception;

	public List<Organizador> listarOrganizadores();

	public List<String> listarEdicionesDeEvento(String nombreEvento);

	public List<Edicion> listarEdiciones();

	public DTevento consultaEvento(String nombreEvento);

	public void altaTipoRegistro(String nombreTR, String descripcion, Float costo, Integer cupo, Edicion edicion)
			throws NombreTRUsadoException;
	public void altaTipoRegistroDT(String nombreTR, String descripcion, Float costo, Integer cupo, String edicion)
			throws NombreTRUsadoException;

	public void altaEvento(String nombre, String descripcion, LocalDate fecha, String sigla, List<Categoria> categorias, String img)
			throws Exception;
	
	public void altaEventoDT(String nombre, String descripcion, LocalDate fecha, String sigla, List<DTCategoria> categorias, String img)
			throws Exception;

	public String obtenerNombreEdicionPorEvento(String nombreEvento);

	public List<DTPatrocinio> listarPatrociniosDeEdicion(String nombreEdicion);

	public DTPatrocinio consultaPatrocinio(String nombreEdicion, String codigoPat);

	public void altaCategoria(String nombre) throws Exception;
	public void altaPatrocinio(String codigo, LocalDate fInicio, int registrosGratuitos, Float monto, 
            ETipoNivel nivel, String nombreInstitucion, String nombreEdicion, String nombreTipoRegistro) throws Exception;
	public Categoria findCategoria(String nom);

	public Evento obtenerEventoPorNombre(String nombreEventoSeleccionado);

	public Organizador obtenerOrganizadorPorNombre(String nombreOrganizadorSeleccionado);
	public List<String> listarNombresEventos() ;

	public void aceptarRechazarEdicion(String nombreEdicion, boolean Aceptar ) throws Exception;
	
	public List<DTevento> listarDTEventos();
	public List<DTCategoria> listarDTCategorias();
	public List<DTEdicion> listarDTEdicion();
	public void altaEdicionDeEventoDTO(String nombreEvento,
	        String nickOrganizador,
	        String nombreEdicion,
	        String sigla,
	        String ciudad,
	        String pais,
	        LocalDate fInicio,
	        LocalDate fFin,
	        LocalDate fAlta,
	        String imagenWebPath) throws Exception;
	public boolean existeEdicionPorNombre(String nombreEvento, String nombreEdicion);
	public boolean existeEdicionPorSiglaDTO(String sigla);
	public List<String> listarNombresTiposRegistroDTO(String nombreEdicion);
	public void finalizarEvento(String nombreEvento) throws Exception;
	public List<DTeventoOedicion> listarEventosYEdicionesBusqueda(String busqueda);

}