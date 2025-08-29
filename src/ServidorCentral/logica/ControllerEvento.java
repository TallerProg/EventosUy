package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.List;

public class ControllerEvento implements IControllerEvento {

	public void altaEdicionDeEvento(String nombre, String sigla, String ciudad, String pais,
            LocalDate fInicio, LocalDate fFin,
            Evento evento, Organizador org) throws Exception {

			ManejadorEvento mE = ManejadorEvento.getInstancia();
			if (mE.existeEdicion(nombre)) {
			throw new IllegalArgumentException("Ya existe una edición con ese nombre");
			}
			
			Edicion ed = new Edicion(nombre, sigla, fInicio, fFin, ciudad, pais);
			
			ed.getOrganizadores().add(org);
			mE.agregarEdicion(evento, ed);
		}

    public Edicion consultaEdicionDeEvento(String nombreEvento, String nombreEdicion) {
        ManejadorEvento manejador = ManejadorEvento.getInstancia();
        Evento evento = manejador.findEvento(nombreEvento);

        if (evento != null) {
            return evento.findEdicion(nombreEdicion);
        }

        return null;
    }
    
    public DTevento consultaEvento(String nombreEvento) {
        ManejadorEvento manejador = ManejadorEvento.getInstancia();
        Evento e = manejador.findEvento(nombreEvento);
        if(e!=null){
            return e.getDTevento();
        }
        return null;
    }

	
	public DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoR) {
        ManejadorEvento manejador = ManejadorEvento.getInstancia();
        Edicion e = manejador.findEdicion(nombreEdicion);
        if (e != null) {
            return e.datosTipoRegistroEdicion(nombreTipoR);
        }
        return null;
		
	}


	public List<Evento> listarEventos() {
	       ManejadorEvento me = ManejadorEvento.getInstancia();

	    return me.listarEventos();
	}
	
	public Evento findEvento(String nombre) {
	       ManejadorEvento me = ManejadorEvento.getInstancia();
	       return me.findEvento(nombre);
	}
	
	public Edicion findEdicion(String nombre) {
	       ManejadorEvento me = ManejadorEvento.getInstancia();
	       return me.findEdicion(nombre);
	}
	
    public List<Categoria> getCategorias(){
        ManejadorEvento me = ManejadorEvento.getInstancia();

        return me.listarCategorias();
    }
    

    public boolean existeEvento(String nombre) {
        ManejadorEvento me = ManejadorEvento.getInstancia();

        return me.existeEvento(nombre);
    }
    public List<Organizador> listarOrganizadores(){
    	ManejadorUsuario mu = ManejadorUsuario.getinstance();
    	
    	return mu.listarOrganizadores();
    }
    public List<Edicion> listarEdiciones(){
    	ManejadorEvento me = ManejadorEvento.getInstancia();
    	 
    	return me.listarEdiciones();
    }

    public void altaEvento(String nombre, String desc, LocalDate fAlta, String sigla, List<Categoria> categorias) throws Exception{
        ManejadorEvento me = ManejadorEvento.getInstancia();

    	boolean e = me.existeEvento(nombre);
    	
    	if(e)
    		throw new Exception("El evento"+ nombre + "ya esta registrado");
    	Evento Ev = new Evento(nombre, sigla, desc, fAlta, categorias);
    	me.agregarEvento(Ev);
    }
    /*
	public void altaRegistro(String nombreEdicion, String nickAsistente, String nombreTR, String codigo) throws Exception {
		ManejadorUsuario manejadorUsuario = ManejadorUsuario.getinstance()
		ManejadorEvento manejadorEvento = ManejadorEvento.getinstance()
	    if (!manejadorEvento.existeEedicion(nombreEdicion)) {
	        throw new Exception("Edición no existe");
	    }
	    Edicion edicion = manejadorEvento.findEdicion(nombreEdicion);
	    if (!manejadorUsuario.existeAsistente(nickAsistente)) {
	        throw new Exception("Asistente no existe");
	    }
	    Asistente asistente = manejadorUsuario.findAsistente(nickAsistente);

	    
	    
	}*/

}
	

