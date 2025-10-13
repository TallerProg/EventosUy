package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControllerEvento implements IControllerEvento {

	public void altaEdicionDeEvento(String nombre, String sigla, String ciudad, String pais,
            LocalDate fInicio, LocalDate fFin, LocalDate fAlta,
            Evento evento, Organizador org,
            String imagenWebPath) throws Exception {

		if (fFin.isBefore(fInicio)) throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
		if (evento.tieneEdicion(nombre) || evento.tieneEdicionSigla(sigla)) throw new IllegalArgumentException("Ya existe una edición con ese nombre o sigla");
		
		Edicion ed = new Edicion(nombre, sigla, fInicio, fFin, fAlta, ciudad, pais, evento);
		ed.setImagenWebPath(imagenWebPath); 
		
		ed.getOrganizadores().add(org);
		evento.agregarEdicion(ed);
		ManejadorEvento manejadorEvento = ManejadorEvento.getInstancia();
		manejadorEvento.agregarEdicion(ed);
		org.agregarEdicionOrg(ed);
		}
	

	public void altaTipoRegistro(String nombreTR, String descripcion, Float costo, Integer cupo, Edicion edicion)
			throws Exception { 
		boolean e = edicion.existeTR(nombreTR);
		if (e)
			throw new Exception("El nombre de tipo de registro \"" + nombreTR + "\" ya fue utilizado");
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
		if (e != null) {
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

	public Edicion findEdicion(String nombre) {
		ManejadorEvento me = ManejadorEvento.getInstancia();
		return me.findEdicion(nombre);
	}


	public List<Categoria> getCategorias() {
		ManejadorEvento me = ManejadorEvento.getInstancia();

		return me.listarCategorias();
	}

	public boolean existeEvento(String nombre) {
		ManejadorEvento me = ManejadorEvento.getInstancia();

		return me.existeEvento(nombre);
	}

	public List<Organizador> listarOrganizadores() {
		ManejadorUsuario mu = ManejadorUsuario.getInstance();

		return mu.listarOrganizadores();
	}

	public List<Edicion> listarEdiciones() {
		ManejadorEvento me = ManejadorEvento.getInstancia();

		return me.listarEdiciones();
	}

	public void altaCategoria(String nombre) throws Exception {
		ManejadorEvento mE = ManejadorEvento.getInstancia();
		if (!mE.existeCategoria(nombre)) {
			Categoria cat = new Categoria(nombre);
			mE.agregarCategoria(cat);
		} else {
			throw new Exception("Ya existe una categoria" + nombre);
		}
	}

	public void altaEvento(String nombre, String desc, LocalDate fAlta, String sigla, List<Categoria> categorias, String img)
			throws Exception {
		ManejadorEvento me = ManejadorEvento.getInstancia();

		if (me.existeEvento(nombre)) {
			throw new Exception("El evento" + nombre + "ya esta registrado");
		} else {
			Evento Ev = new Evento(nombre, sigla, desc, fAlta, categorias, img);
			me.agregarEvento(Ev);

		}
	}

	@Override
	public void altaRegistro(String nombreEdicion, String nickAsistente, String nombreTR, String codigo)
			throws Exception {
		ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstance();
		ManejadorEvento manejadorEvento = ManejadorEvento.getInstancia();
		if (!manejadorEvento.existeEdicion(nombreEdicion)) {
			throw new Exception("Edición no existe");
		}
		Edicion edicion = manejadorEvento.findEdicion(nombreEdicion);
		if (!manejadorUsuario.existeAsistente(nickAsistente)) {
			throw new Exception("Asistente no existe");
		}
		Asistente asistente = manejadorUsuario.findAsistente(nickAsistente);
		
		if (asistente.getInstitucion() == null) {
			throw new Exception("Asistente no asociado a institución");
		}
		if (asistente != null && asistente.getInstitucion().findPatrocinio(codigo) == null) {
				throw new Exception("Ese código no es válido para este asistente");
		}
	
		Patrocinio pa = asistente.getInstitucion().findPatrocinio(codigo);
		if (!pa.consultarRegistros()) {
			throw new Exception("Ya no quedan cupos gratuitos para ese código");
		}
		if (pa.getTipoRegistro().getNombre() != nombreTR) {
			throw new Exception("El código no es válido para ese tipo de registro");
		}
		if (edicion.habilitadoAsistente(nombreTR, asistente)) {
			TipoRegistro tr = edicion.getEdicionTR(nombreTR);
			if (tr.soldOutTipReg()) {
				throw new Exception("Ya no quedan cupos para ese tipo de registro");
			}
			float costo = 0;
			Registro reg = new Registro(costo, edicion, asistente, tr);
			
				reg.setPatrocinio(pa);
				pa.agregarRegistro(reg);
				edicion.addLinkRegistro(reg);
				tr.addLinkRegistro(reg);
				asistente.addRegistro(reg);
				asistente.addPatrocinio(pa);
			
		}
	}
	
    public void altaRegistro(String nombreEdicion, String nickAsistente, String nombreTR, String codigo, LocalDate fecha) throws Exception{
    	ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstance();
		ManejadorEvento manejadorEvento = ManejadorEvento.getInstancia();
		if (!manejadorEvento.existeEdicion(nombreEdicion)) {
			throw new Exception("Edición no existe");
		}
		Edicion edicion = manejadorEvento.findEdicion(nombreEdicion);
		if (!manejadorUsuario.existeAsistente(nickAsistente)) {
			throw new Exception("Asistente no existe");
		}
		Asistente asistente = manejadorUsuario.findAsistente(nickAsistente);
		if (asistente.getInstitucion() == null) {
			throw new Exception("Asistente no asociado a institución");
		}
		if (asistente != null && asistente.getInstitucion().findPatrocinio(codigo) == null) {
				throw new Exception("Ese código no es válido para este asistente");
		}
	
		Patrocinio pa = asistente.getInstitucion().findPatrocinio(codigo);
		if (pa.consultarRegistros()) {
			throw new Exception("Ya no quedan cupos gratuitos");
		}

		if (edicion.habilitadoAsistente(nombreTR, asistente)) {
			TipoRegistro tr = edicion.getEdicionTR(nombreTR);
			if (tr.soldOutTipReg()) {
				throw new Exception("Ya no quedan cupos para ese tipo de registro");
			}
			float costo = 0;
			Registro reg = new Registro(costo, edicion, asistente, tr, fecha);
			
				reg.setPatrocinio(pa);
				pa.agregarRegistro(reg);
				edicion.addLinkRegistro(reg);
				tr.addLinkRegistro(reg);
				asistente.addRegistro(reg);
				asistente.addPatrocinio(pa);
				pa.getAsistentes().add(asistente);
		}
    }
    
    
    
    public void altaRegistro(String nombreEdicion, String nickAsistente, String nombreTR, LocalDate fecha) throws Exception{
    	ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstance();
		ManejadorEvento manejadorEvento = ManejadorEvento.getInstancia();
		if (!manejadorEvento.existeEdicion(nombreEdicion)) {
			throw new Exception(nombreEdicion + " Edición no existe");
		}
		Edicion edicion = manejadorEvento.findEdicion(nombreEdicion);
		if (!manejadorUsuario.existeAsistente(nickAsistente)) {
			throw new Exception("Asistente no existe");
		}
		Asistente asistente = manejadorUsuario.findAsistente(nickAsistente);

		if (edicion.habilitadoAsistente(nombreTR, asistente)) {
			TipoRegistro tr = edicion.getEdicionTR(nombreTR);
			if (tr.soldOutTipReg()) {
				throw new Exception("Ya no quedan cupos para ese tipo de registro");
			}
			float costo = tr.getCosto();
			Registro reg = new Registro(costo, edicion, asistente, tr, fecha);
			edicion.addLinkRegistro(reg);
			tr.addLinkRegistro(reg);
			asistente.addRegistro(reg);
		} else {
			throw new Exception(asistente.getNickname() + " Ya esta registrado");
		}
    }


	@Override
	public void altaRegistro(String nombreEdicion, String nickAsistente, String nombreTR) throws Exception {
		ManejadorUsuario manejadorUsuario = ManejadorUsuario.getInstance();
		ManejadorEvento manejadorEvento = ManejadorEvento.getInstancia();
		if (!manejadorEvento.existeEdicion(nombreEdicion)) {
			throw new Exception(nombreEdicion + " Edición no existe");
		}
		Edicion edicion = manejadorEvento.findEdicion(nombreEdicion);
		if (!manejadorUsuario.existeAsistente(nickAsistente)) {
			throw new Exception("Asistente no existe");
		}
		Asistente asistente = manejadorUsuario.findAsistente(nickAsistente);

		if (edicion.habilitadoAsistente(nombreTR, asistente)) {
			TipoRegistro tr = edicion.getEdicionTR(nombreTR);
			if (tr.soldOutTipReg()) {
				throw new Exception("Ya no quedan cupos para ese tipo de registro");
			}
			float costo = tr.getCosto();
			Registro reg = new Registro(costo, edicion, asistente, tr);
			edicion.addLinkRegistro(reg);
			tr.addLinkRegistro(reg);
			asistente.addRegistro(reg);
		} else {
			throw new Exception(asistente.getNickname() + " Ya esta registrado");
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

	public Evento obtenerEventoPorNombre(String nombreEvento) {
	    if (nombreEvento == null) return null;
	    ManejadorEvento me = ManejadorEvento.getInstancia();
	    return me.findEvento(nombreEvento); 
	}

	public Organizador obtenerOrganizadorPorNombre(String nombreOrganizador) {
	    if (nombreOrganizador == null) return null;
	    ManejadorUsuario mu = ManejadorUsuario.getInstance();
	    for (Organizador o : mu.listarOrganizadores()) {
	        if (o.getNombre().equals(nombreOrganizador)) {
	            return o;
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
	public List<String> listarNombresEventos() {
	    List<String> nombres = new ArrayList<>();
	    for (Evento e : listarEventos()) {           
	        if (e != null && e.getNombre() != null) {
	            nombres.add(e.getNombre());
	        }
	    }
	    return nombres;
	}


	public Categoria findCategoria(String nom) {
		ManejadorEvento me = ManejadorEvento.getInstancia();
		return me.findCategoria(nom);
	}
	public void altaPatrocinio(String codigo, LocalDate fInicio, int registrosGratuitos, Float monto, 
            ETipoNivel nivel, String nombreInstitucion, String nombreEdicion, String nombreTipoRegistro) throws Exception {
			ManejadorEvento me = ManejadorEvento.getInstancia();
			ManejadorInstitucion mi = ManejadorInstitucion.getInstance();
			
			Edicion edicion = me.findEdicion(nombreEdicion);
			if (edicion == null) {
			throw new Exception("La edición " + nombreEdicion + " no existe");
			}
			
			Institucion institucion = mi.findInstitucion(nombreInstitucion);
			if (institucion == null) {
				throw new Exception("La institución " + nombreInstitucion + " no existe");
			}
			
			TipoRegistro tr = edicion.getEdicionTR(nombreTipoRegistro);
			if (tr == null) {
				throw new Exception("El tipo de registro " + nombreTipoRegistro + " no existe en la edición");
			}
			
			for (Patrocinio p : edicion.getPatrocinios()) {
			if (p.getInstitucion() != null && p.getInstitucion().getNombre().equals(nombreInstitucion)) {
				throw new Exception("Ya existe un patrocinio de la institución " + nombreInstitucion + " para esta edición");
				}
			}
			
			float costoTotalRegistros = registrosGratuitos * tr.getCosto();
			if (costoTotalRegistros > monto * 0.20f) {
				throw new Exception("El costo de los registros gratuitos supera el 20% del aporte económico");
			}
			
			Patrocinio nuevo = new Patrocinio(codigo, fInicio, registrosGratuitos, monto, nivel, institucion, edicion, tr);
			edicion.getPatrocinios().add(nuevo);
			institucion.getPatrocinios().add(nuevo);
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
	
	public void aceptarRechazarEdicion(String nombreEdicion, boolean Aceptar ) throws Exception {
        ManejadorEvento me = ManejadorEvento.getInstancia();
        Edicion ed = me.findEdicion(nombreEdicion);
        if (ed == null) {
            throw new Exception("La edición " + nombreEdicion + " no existe");
        }
        if (Aceptar ) {
            ed.setEstado(EstadoEdicion.Aceptada);
        } else {
            ed.setEstado(EstadoEdicion.Rechazada);
        }
	}
}
