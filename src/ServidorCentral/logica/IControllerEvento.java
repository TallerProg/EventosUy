package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.List;

public interface IControllerEvento {

	
    public void altaEdicionDeEvento(String nombre, String sigla, String ciudad, String pais,
            LocalDate fInicio, LocalDate fFin,
            Evento evento, Organizador org) throws Exception;
	
	public Edicion consultaEdicionDeEvento(String nombreEvento, String nombreEdicion);
	
	public DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoR);
	
	public List<Evento> listarEventos();
	
    public List<Categoria> getCategorias();
    
    public List<Organizador> listarOrganizadores();
    
    public List<Edicion> listarEdiciones();
    
    public boolean existeEvento(String nombre);


    public void altaEvento(String nombre, String descripcion, LocalDate fecha, String sigla, List<Categoria> categorias) throws Exception;
}