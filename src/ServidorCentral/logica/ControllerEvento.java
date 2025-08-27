package ServidorCentral.logica;

import java.util.Date;

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

	public Edicion ConsultaEdicionDeEvento(Evento evento, String nombreEdicion) {
	    if (evento == null || nombreEdicion == null) return null;
	
	    for (Edicion ed : evento.getEdiciones()) {
	        if (ed.getNombre().equalsIgnoreCase(nombreEdicion)) {
	            return ed;
	        }
	    }
	
	    return null; 
	}
}
