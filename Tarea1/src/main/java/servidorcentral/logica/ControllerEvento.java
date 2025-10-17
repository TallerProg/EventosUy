package servidorcentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControllerEvento implements IControllerEvento {

	public void altaEdicionDeEvento(String nombre, String sigla, String ciudad, String pais,
            LocalDate fInicio, LocalDate fFin, LocalDate fAlta,
            Evento evento, Organizador org,
            String imagenWebPath) throws Exception {

		if (fFin.isBefore(fInicio)) throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
		ManejadorEvento manejadorEvento = ManejadorEvento.getInstancia();
		if (manejadorEvento.existeEdicion(nombre) || manejadorEvento.tieneEdicionSigla(sigla)) throw new IllegalArgumentException("Ya existe una edición con ese nombre o sigla");
		
		Edicion edicion = new Edicion(nombre, sigla, fInicio, fFin, fAlta, ciudad, pais, evento);
		edicion.setImagenWebPath(imagenWebPath); 
		
		edicion.getOrganizadores().add(org);
		evento.agregarEdicion(edicion);
		manejadorEvento.agregarEdicion(edicion);
		org.agregarEdicionOrg(edicion);
		}
	

	public void altaTipoRegistro(String nombreTR, String descripcion, Float costo, Integer cupo, Edicion edicion)
			throws Exception { 
		boolean existeEdi = edicion.existeTR(nombreTR);
		if (existeEdi)
			throw new Exception("El nombre de tipo de registro \"" + nombreTR + "\" ya fue utilizado");
		TipoRegistro NuevoTR = new TipoRegistro(nombreTR, descripcion, costo, cupo, edicion);
		edicion.agregarTipoRegistro(NuevoTR);
	}

	public Evento getEvento(String nombreEvento) {
		ManejadorEvento mEve = ManejadorEvento.getInstancia();
		return mEve.findEvento(nombreEvento);
	}

	public DTEdicion consultaEdicionDeEvento(String nombreEvento, String nombreEdicion) {
		ManejadorEvento manejador = ManejadorEvento.getInstancia();
		Evento evento = manejador.findEvento(nombreEvento);

		if (evento != null) {
			Edicion edicion = evento.findEdicion(nombreEdicion);
			if (edicion != null) {
				return edicion.getDTEdicion();
			}
		}
		return null;
	}

	public DTevento consultaEvento(String nombreEvento) {
		ManejadorEvento manejador = ManejadorEvento.getInstancia();
		Evento evento = manejador.findEvento(nombreEvento);
		if (evento != null) {
			return evento.getDTevento();
		}
		return null;
	}

	public DTTipoRegistro consultaTipoRegistro(String nombreEdicion, String nombreTipoR) {
		ManejadorEvento manejador = ManejadorEvento.getInstancia();
		Edicion edi = manejador.findEdicion(nombreEdicion);
		if (edi != null) {
			return edi.datosTipoRegistroEdicion(nombreTipoR);
		}
		return null;

	}

	public List<Evento> listarEventos() {
	    ManejadorEvento mev = ManejadorEvento.getInstancia();
	    return mev.listarEventos();
	}

	public Edicion findEdicion(String nombre) {
		ManejadorEvento mev = ManejadorEvento.getInstancia();
		return mev.findEdicion(nombre);
	}


	public List<Categoria> getCategorias() {
		ManejadorEvento mev = ManejadorEvento.getInstancia();

		return mev.listarCategorias();
	}

	public boolean existeEvento(String nombre) {
		ManejadorEvento mev = ManejadorEvento.getInstancia();

		return mev.existeEvento(nombre);
	}

	public List<Organizador> listarOrganizadores() {
		ManejadorUsuario mus = ManejadorUsuario.getInstance();

		return mus.listarOrganizadores();
	}

	public List<Edicion> listarEdiciones() {
		ManejadorEvento mev = ManejadorEvento.getInstancia();

		return mev.listarEdiciones();
	}

	public void altaCategoria(String nombre) throws Exception {
		ManejadorEvento mEv = ManejadorEvento.getInstancia();
		if (!mEv.existeCategoria(nombre)) {
			Categoria cat = new Categoria(nombre);
			mEv.agregarCategoria(cat);
		} else {
			throw new Exception("Ya existe una categoria" + nombre);
		}
	}

	public void altaEvento(String nombre, String desc, LocalDate fAlta, String sigla, List<Categoria> categorias, String img)
			throws Exception {
		ManejadorEvento mev = ManejadorEvento.getInstancia();

		if (mev.existeEvento(nombre)) {
			throw new Exception("El evento" + nombre + "ya esta registrado");
		} else {
			Evento Eve = new Evento(nombre, sigla, desc, fAlta, categorias, img);
			mev.agregarEvento(Eve);

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
	
		Patrocinio pat = asistente.getInstitucion().findPatrocinio(codigo);
		if (!pat.consultarRegistros()) {
			throw new Exception("Ya no quedan cupos gratuitos para ese código");
		}
		if (!pat.getTipoRegistro().getNombre().equals(nombreTR)) {
			throw new Exception("El código no es válido para ese tipo de registro");
		}
		if (edicion.habilitadoAsistente(nombreTR, asistente)) {
			TipoRegistro tre = edicion.getEdicionTR(nombreTR);
			if (tre.soldOutTipReg()) {
				throw new Exception("Ya no quedan cupos para ese tipo de registro");
			}
			float costo = 0;
			Registro reg = new Registro(costo, edicion, asistente, tre);
			
				reg.setPatrocinio(pat);
				pat.agregarRegistro(reg);
				edicion.addLinkRegistro(reg);
				tre.addLinkRegistro(reg);
				asistente.addRegistro(reg);
				asistente.addPatrocinio(pat);
			
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
	
		Patrocinio pat = asistente.getInstitucion().findPatrocinio(codigo);
		if (pat.consultarRegistros()) {
			throw new Exception("Ya no quedan cupos gratuitos");
		}

		if (edicion.habilitadoAsistente(nombreTR, asistente)) {
			TipoRegistro tre = edicion.getEdicionTR(nombreTR);
			if (tre.soldOutTipReg()) {
				throw new Exception("Ya no quedan cupos para ese tipo de registro");
			}
			float costo = 0;
			Registro reg = new Registro(costo, edicion, asistente, tre, fecha);
			
				reg.setPatrocinio(pat);
				pat.agregarRegistro(reg);
				edicion.addLinkRegistro(reg);
				tre.addLinkRegistro(reg);
				asistente.addRegistro(reg);
				asistente.addPatrocinio(pat);
				pat.getAsistentes().add(asistente);
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
			TipoRegistro tre = edicion.getEdicionTR(nombreTR);
			if (tre.soldOutTipReg()) {
				throw new Exception("Ya no quedan cupos para ese tipo de registro");
			}
			float costo = tre.getCosto();
			Registro reg = new Registro(costo, edicion, asistente, tre, fecha);
			edicion.addLinkRegistro(reg);
			tre.addLinkRegistro(reg);
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
			TipoRegistro tre = edicion.getEdicionTR(nombreTR);
			if (tre.soldOutTipReg()) {
				throw new Exception("Ya no quedan cupos para ese tipo de registro");
			}
			float costo = tre.getCosto();
			Registro reg = new Registro(costo, edicion, asistente, tre);
			edicion.addLinkRegistro(reg);
			tre.addLinkRegistro(reg);
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
	    ManejadorEvento mev = ManejadorEvento.getInstancia();
	    return mev.findEvento(nombreEvento); 
	}

	public Organizador obtenerOrganizadorPorNombre(String nombreOrganizador) {
	    if (nombreOrganizador == null) return null;
	    ManejadorUsuario mus = ManejadorUsuario.getInstance();
	    for (Organizador org : mus.listarOrganizadores()) {
	        if (org.getNombre().equals(nombreOrganizador)) {
	            return org;
	        }
	    }
	    return null;
	}

	public List<DTPatrocinio> listarPatrociniosDeEdicion(String nombreEdicion) {
	    ManejadorEvento mev = ManejadorEvento.getInstancia();
	    Edicion edi = mev.findEdicion(nombreEdicion);
	    List<DTPatrocinio> res = new ArrayList<>();
	    if (edi != null) {
	        for (Patrocinio pat : edi.getPatrocinios()) {
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
		ManejadorEvento mev = ManejadorEvento.getInstancia();
		return mev.findCategoria(nom);
	}
	public void altaPatrocinio(String codigo, LocalDate fInicio, int registrosGratuitos, Float monto, 
            ETipoNivel nivel, String nombreInstitucion, String nombreEdicion, String nombreTipoRegistro) throws Exception {
			ManejadorEvento mev = ManejadorEvento.getInstancia();
			ManejadorInstitucion min = ManejadorInstitucion.getInstance();
			
			
			
		        for (Edicion edi : mev.listarEdiciones()) {
		            for (Patrocinio p : edi.getPatrocinios()) {
		                if (p.getCodigo().equalsIgnoreCase(codigo)) {
		                    throw new Exception("Ya existe un patrocinio con el código " + codigo + " en el sistema.");
		                }
		            }
		        }
		    
			Edicion edicion = mev.findEdicion(nombreEdicion);
			if (edicion == null) {
			throw new Exception("La edición " + nombreEdicion + " no existe");
			}
			
			Institucion institucion = min.findInstitucion(nombreInstitucion);
			if (institucion == null) {
				throw new Exception("La institución " + nombreInstitucion + " no existe");
			}
			
			TipoRegistro tre = edicion.getEdicionTR(nombreTipoRegistro);
			if (tre == null) {
				throw new Exception("El tipo de registro " + nombreTipoRegistro + " no existe en la edición");
			}
			
			for (Patrocinio p : edicion.getPatrocinios()) {
			if (p.getInstitucion() != null && p.getInstitucion().getNombre().equals(nombreInstitucion)) {
				throw new Exception("Ya existe un patrocinio de la institución " + nombreInstitucion + " para esta edición");
				}
			}
			
			float costoTotalRegistros = registrosGratuitos * tre.getCosto();
			if (costoTotalRegistros > monto * 0.20f) {
				throw new Exception("El costo de los registros gratuitos supera el 20% del aporte económico");
			}
			
			Patrocinio nuevo = new Patrocinio(codigo, fInicio, registrosGratuitos, monto, nivel, institucion, edicion, tre);
			edicion.getPatrocinios().add(nuevo);
			institucion.getPatrocinios().add(nuevo);
}

	public DTPatrocinio consultaPatrocinio(String nombreEdicion, String codigoPat) {
		ManejadorEvento mev = ManejadorEvento.getInstancia();
		Edicion edi = mev.findEdicion(nombreEdicion);
		if (edi != null) {
			for (Patrocinio pat : edi.getPatrocinios()) {
				if (pat.getCodigo().equals(codigoPat)) {
					return pat.getDTPatrocinio();
				}
			}
		}
		return null;
	}
	
	public void aceptarRechazarEdicion(String nombreEdicion, boolean Aceptar ) throws Exception {
        ManejadorEvento mev = ManejadorEvento.getInstancia();
        Edicion edi = mev.findEdicion(nombreEdicion);
        if (edi == null) {
            throw new Exception("La edición " + nombreEdicion + " no existe");
        }
        if (Aceptar ) {
            edi.setEstado(EstadoEdicion.Aceptada);
        } else {
            edi.setEstado(EstadoEdicion.Rechazada);
        }
	}
	
	public List<DTevento> listarDTEventos(){
		List<DTevento> dtEventos = new ArrayList<>();
		ManejadorEvento mev = ManejadorEvento.getInstancia();
		for (Evento e : mev.listarEventos()) {
			dtEventos.add(e.getDTevento());
		}
		return dtEventos;
	}
	
	public List<DTCategoria> listarDTCategorias(){
		List<DTCategoria> dtCategorias = new ArrayList<>();
		ManejadorEvento mev = ManejadorEvento.getInstancia();
		for (Categoria c : mev.listarCategorias()) {
			dtCategorias.add(c.getDTCategoria());
		}
		return dtCategorias;
	}



public boolean existeEdicionPorNombre(String nombreEvento, String nombreEdicion) {
	 if (nombreEvento == null || nombreEdicion == null) return false;
	 ManejadorEvento mev = ManejadorEvento.getInstancia();
	 Evento ev = mev.findEvento(nombreEvento);
	 if (ev == null) return false;
	 return ev.findEdicion(nombreEdicion) != null;
}

public boolean existeEdicionPorSiglaDTO(String sigla) {
	 if (sigla == null) return false;
	 ManejadorEvento mev = ManejadorEvento.getInstancia();
	 try {
	     return mev.tieneEdicionSigla(sigla);
	 } catch (Throwable t) {
	     for (Edicion e : mev.listarEdiciones()) {
	         if (e != null && sigla.equals(e.getSigla())) return true;
	     }
	     return false;
	 }
}

public void altaEdicionDeEventoDTO(String nombreEvento,
                                String nickOrganizador,
                                String nombreEdicion,
                                String sigla,
                                String ciudad,
                                String pais,
                                LocalDate fInicio,
                                LocalDate fFin,
                                LocalDate fAlta,
                                String imagenWebPath) throws Exception {
	 if (fFin.isBefore(fInicio)) {
	     throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
	 }
	 ManejadorEvento manejadorEvento = ManejadorEvento.getInstancia();
	 if (manejadorEvento.existeEdicion(nombreEdicion) || existeEdicionPorSiglaDTO(sigla)) {
	     throw new IllegalArgumentException("Ya existe una edición con ese nombre o sigla");
	 }
	
	 Evento evento = obtenerEventoPorNombre(nombreEvento);
	 if (evento == null) {
	     throw new Exception("El evento '" + nombreEvento + "' no existe");
	 }
	
	 ManejadorUsuario mu = ManejadorUsuario.getInstance();
	 Organizador org = mu.findOrganizador(nickOrganizador); 
	 if (org == null) {
	     org = obtenerOrganizadorPorNombre(nickOrganizador);
	 }
	 if (org == null) {
	     throw new Exception("No se pudo resolver el organizador '" + nickOrganizador + "'");
	 }
	
	 altaEdicionDeEvento(nombreEdicion, sigla, ciudad, pais, fInicio, fFin, fAlta, evento, org, imagenWebPath);
	}
}
