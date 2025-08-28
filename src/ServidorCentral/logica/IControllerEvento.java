package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.List;

public interface IControllerEvento {

	
    public void altaEdicionDeEvento(Evento evento, Organizador org, String  nombreEvento, String nombre, LocalDate fechaIni, LocalDate fechaFin,
    String ciudad, String pais) throws Exception ;
	
	public Edicion consultaEdicionDeEvento(String nombreEvento, String nombreEdicion);
	
	public DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoR);
	
	public List<Evento> listarEventos();
	
    public List<Categoria> getCategorias();

    public boolean existeEvento(String nombre);


    public void altaEvento(String nombre, String descripcion, LocalDate fecha, String sigla, List<Categoria> categorias) throws Exception;
}