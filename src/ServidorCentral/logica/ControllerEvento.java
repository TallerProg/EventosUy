package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.List;

public class ControllerEvento implements IControllerEvento {

    public void altaEdicionDeEvento(Evento evento, Organizador org, String  nombreEvento, String nombre, LocalDate fechaIni, LocalDate fechaFin,
    String ciudad, String pais) throws Exception {
    	ManejadorEvento mE = ManejadorEvento.getInstancia();
        if (mE.existeEdicion(nombre)) {
        	throw new IllegalArgumentException("Ya existe una edición con ese nombre");
    	}
        
        
        Edicion ed = new Edicion(nombre, fechaIni, fechaFin, ciudad, pais);

        ed.getOrganizadores().add(org);
       ManejadorEvento me = ManejadorEvento.getInstancia();
       me.agregarEdicion(evento, ed);
    }
    public Evento getEvento(String nombreEvento) {
    	ManejadorEvento mE = ManejadorEvento.getInstancia();
    	return mE.findEvento(nombreEvento);
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
	       ManejadorEvento me = ManejadorEvento.getInstancia();

	    return me.listarEventos();
	}
	
    public List<Categoria> getCategorias(){
        ManejadorEvento me = ManejadorEvento.getInstancia();

        return me.listarCategorias();
    }
    

    public boolean existeEvento(String nombre) {
        ManejadorEvento me = ManejadorEvento.getInstancia();

        return me.existeEvento(nombre);

    }

    public void altaEvento(String nombre, String desc, LocalDate fAlta, String sigla, List<Categoria> categorias) throws Exception{
        ManejadorEvento me = ManejadorEvento.getInstancia();

    	boolean e = me.existeEvento(nombre);
    	
    	if(e)
    		throw new Exception("El evento"+ nombre + "ya esta registrado");
    	Evento Ev = new Evento(nombre, sigla, desc, fAlta, categorias);
    	me.agregarEvento(Ev);
    }
  
	public void altaRegistro(String nombreEdicion, String nickAsistente, String nombreTR, String codigo) throws Exception {
		ManejadorUsuario manejadorUsuario = ManejadorUsuario.getinstance();
		ManejadorEvento manejadorEvento = ManejadorEvento.getInstancia();
	    if (!manejadorEvento.existeEdicion(nombreEdicion)) {
	        throw new Exception("Edición no existe");
	    }
	    Edicion edicion = manejadorEvento.findEdicion(nombreEdicion);
	    if (!manejadorUsuario.existeAsistente(nickAsistente)) {
	        throw new Exception("Asistente no existe");
	    }
	    Asistente asistente = manejadorUsuario.findAsistente(nickAsistente);
	    if ((asistente.getPatrocinio() != null )&&(!asistente.getPatrocinio().getCodigo().equals(codigo))) {
	        throw new Exception("Ese codigo no es valido para este asistente");
	    }
	    if (!asistente.getPatrocinio().consultarRegistros()) {
	        throw new Exception("Ya no quedan cupos gratuitos");
	    }
	    
	    
	    if (edicion.habilitadoAsistente(nombreTR, asistente)) {
	    	TipoRegistro tr = edicion.getEdicionTR(nombreTR);
	    	if(tr.soldOutTipReg()) {
	    		throw new Exception("Ya no quedan cupos para ese tipo de registro");
	    	}
	    	float costo = 0; 
	    	Registro reg = new Registro(costo, edicion, asistente, tr);
	    	Patrocinio pa = asistente.getPatrocinio();
	    	reg.setPatrocinio(pa);
	    	asistente.getPatrocinio().agregarRegistro(reg);
	    	edicion.addLinkRegistro(reg);
	    	tr.addLinkRegistro(reg);
	    }
	   }

}
	
