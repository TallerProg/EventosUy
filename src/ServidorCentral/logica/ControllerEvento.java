package ServidorCentral.logica;

import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControllerEvento implements IControllerEvento {

    public void altaEdicionDeEvento(String nombre, String sigla, String ciudad, String pais,
                                    Date fechaIni, Date fechaFin, Date fechaAlta,
                                    Evento evento, Organizador org) throws Exception {

        if (evento.tieneEdicion(nombre)) {
            throw new Exception("Ya existe una edición con ese nombre para este evento");
        }

        Edicion ed = new Edicion(nombre, sigla, evento.getDescripcion(), fechaAlta,
                                 evento.getCategoria(), fechaIni, fechaFin, ciudad, pais);

        ed.getOrganizadores().add(org);

       ManejadorEvento.agregarEdicion(evento, ed);
    }

    public Edicion consultaEdicionDeEvento(String nombreEvento, String nombreEdicion) {
        ManejadorEvento manejador = ManejadorEvento.getInstancia();
        Evento evento = manejador.findEvento(nombreEvento);

        if (evento != null) {
            return evento.findEdicion(nombreEdicion);
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
	    return ManejadorEvento.listarEventos();
	}
	
    public List<Categoria> getCategorias(){
        return ManejadorEvento.listarCategorias();
    }
    

    public boolean existeEvento(String nombre) {
        return ManejadorEvento.existeEvento(nombre);

    }

    public void altaEvento(String nombre, String desc, Date fAlta, String sigla, List<Categoria> categorias) throws Exception{
    	boolean e = ManejadorEvento.existeEvento(nombre);
    	
    	if(e)
    		throw new Exception("El evento"+ nombre + "ya esta registrado");
    	Evento Ev = new Evento(nombre, sigla, desc, fAlta, categorias);
    	ManejadorEvento.agregarEvento(Ev);
    }

}
	
