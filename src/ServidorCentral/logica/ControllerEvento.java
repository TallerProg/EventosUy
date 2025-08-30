package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControllerEvento implements IControllerEvento {

	public void altaEdicionDeEvento(String nombre, String sigla, String ciudad, String pais,
            LocalDate fInicio, LocalDate fFin,
            Evento evento, Organizador org) throws Exception {

			if (fFin.isBefore(fInicio)) {
				throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
			}
			
			if (evento.tieneEdicion(nombre) || evento.tieneEdicion(sigla)) {
				throw new IllegalArgumentException("Ya existe una edición con ese nombre o sigla");
			}
			
			Edicion ed = new Edicion(nombre, sigla, fInicio, fFin, ciudad, pais, evento);
			ed.getOrganizadores().add(org);
			evento.agregarEdicion(ed);
	}

	
	public void altaTipoRegistro(String nombreTR, String descripcion, Float costo, Integer cupo, Edicion edicion) throws Exception{ //VICHAR MATEO
    	boolean e = edicion.existeTR(nombreTR);
    	if (e) throw new Exception("El nombre de tipo de registro \""+ nombreTR + "\" ya fue utilizado");
    	TipoRegistro NuevoTR = new TipoRegistro(nombreTR, descripcion, costo, cupo, edicion);
    	edicion.agregarTipoRegistro(NuevoTR);
    }
	
	 public Evento getEvento(String nombreEvento) {
		 ManejadorEvento mE = ManejadorEvento.getInstancia();
		 return mE.findEvento(nombreEvento);
	 }
	 
	 
	 
	 public DTEdicion consultaEdicionDeEvento(String nombreEvento, String nombreEdicion) {
		    ManejadorEvento manejador = ManejadorEvento.getInstancia();
		    Evento evento = manejador.findEvento(nombreEvento);

		    if (evento != null) {
		        Edicion ed = evento.findEdicion(nombreEdicion);
		        if (ed != null) {
		            return ed.getDTEdicion();
		        }
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
 
    public void altaCategoria(String nombre)throws Exception {
    	ManejadorEvento mE = ManejadorEvento.getInstancia();
    	if(!mE.existeCategoria(nombre)) {
    		Categoria cat = new Categoria(nombre);
    		mE.agregarCategoria(cat);
    	}else {
    		throw new Exception("Ya existe una categoria"+ nombre);
    	}
    }
    
    public void altaEvento(String nombre, String desc, LocalDate fAlta, String sigla, List<Categoria> categorias) throws Exception{
        ManejadorEvento me = ManejadorEvento.getInstancia();

    	if(me.existeEvento(nombre)){
    		throw new Exception("El evento"+ nombre + "ya esta registrado");
    	}else {
    	Evento Ev = new Evento(nombre, sigla, desc, fAlta, categorias);
    	me.agregarEvento(Ev);
    	
    }
    }
    @Override
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
	    if ((asistente.getPatrocinio() == null)) {
	        throw new Exception("Patrocinio no encontrado");
	    }
	    if (asistente != null && asistente.getPatrocinio() != null) {
	        String codigoPatrocinio = asistente.getPatrocinio().getCodigo();
	        if (codigoPatrocinio == null || !codigoPatrocinio.trim().equals(codigo.trim())) {
	            throw new Exception("Ese código no es válido para este asistente");
	        }
	    }

	   
	    if ((asistente.getPatrocinio() != null)&&(!asistente.getPatrocinio().consultarRegistros())) {
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
	    	if ((asistente.getPatrocinio() != null)) {
	    	reg.setPatrocinio(pa);
	    	asistente.getPatrocinio().agregarRegistro(reg);
	    	edicion.addLinkRegistro(reg);
	    	tr.addLinkRegistro(reg);
	    	asistente.addRegistro(reg);
	    }}
	   }
	@Override
	public void altaRegistro(String nombreEdicion, String nickAsistente, String nombreTR) throws Exception {
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

	    if (edicion.habilitadoAsistente(nombreTR, asistente)) {
	    	TipoRegistro tr = edicion.getEdicionTR(nombreTR);
	    	if(tr.soldOutTipReg()) {
	    		throw new Exception("Ya no quedan cupos para ese tipo de registro");
	    	}
	    	float costo = tr.getCosto(); 
	    	Registro reg = new Registro(costo, edicion, asistente, tr);
	    	edicion.addLinkRegistro(reg);
	    	tr.addLinkRegistro(reg);
	    	asistente.addRegistro(reg);
	    }else {
	    		throw new Exception(asistente.getNickname() +" Ya esta registrado");
	    }
	   }
	public List<String> listarEdicionesDeEvento(String nombreEvento) {
	    ManejadorEvento manejador = ManejadorEvento.getInstancia();
	    Evento evento = manejador.findEvento(nombreEvento);
	    List<String> nombresEdiciones = new ArrayList<>();
	    if (evento != null) {
	        for (Edicion ed : evento.getEdiciones()) {
	            nombresEdiciones.add(ed.getNombre());
	        }
	    }
	    return nombresEdiciones;
	}

	public String obtenerNombreEdicionPorEvento(String nombreEvento) {
	    List<String> ediciones = listarEdicionesDeEvento(nombreEvento);
	    if (!ediciones.isEmpty()) {
	        return ediciones.get(0); 
	    }
	    return null;
	}
	
	public DTPatrocinio consultaPatrocinio(String nombreEdicion, String codigoPat) {
	    ManejadorEvento me = ManejadorEvento.getInstancia();
	    Edicion ed = me.findEdicion(nombreEdicion);
	    if (ed != null) {
	        for (Patrocinio pat : ed.getPatrocinios()) {
	            if (pat.getCodigo().equals(codigoPat)) {
	                return pat.getDTPatrocinio();
	            }
	        }
	    }
	    return null;
	}

	
	public List<DTPatrocinio> listarPatrociniosDeEdicion(String nombreEdicion) {
	    ManejadorEvento me = ManejadorEvento.getInstancia();
	    Edicion ed = me.findEdicion(nombreEdicion);
	    List<DTPatrocinio> res = new ArrayList<>();
	    if (ed != null) {
	        for (Patrocinio pat : ed.getPatrocinios()) {
	            res.add(pat.getDTPatrocinio());
	        }
	    }
	    return res;
	}
	
	public Categoria findCategoria(String nom) {
		ManejadorEvento me = ManejadorEvento.getInstancia();
		return me.findCategoria(nom);
	}
	



}
	


