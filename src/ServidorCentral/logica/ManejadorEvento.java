package ServidorCentral.logica;

import java.util.ArrayList;
import java.util.List;

public class ManejadorEvento {
    private static ManejadorEvento instancia;
    private static List<Evento> eventos = new ArrayList<>();
    private static List<Organizador> organizadores = new ArrayList<>();

    public static List<Evento> listarEventos() {
        return new ArrayList<>(eventos);
    }

    public static List<Organizador> listarOrganizadores() {
        return new ArrayList<>(organizadores);
    }

    public static boolean existeEvento(String nombre) {
        for (Evento e : eventos) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    public static boolean existeEdicionEvento(String nombreEdicion) {
        for (Evento e : eventos) {
            if (e.tieneEdicion(nombreEdicion)) {
                return true;
            }
        }
        return false;
    }

    public static void agregarEdicion(Evento evento, Edicion edicion) {
        evento.agregarEdicion(edicion);
    }
    
    public static ManejadorEvento getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorEvento();
        }
        return instancia;
    }
    
    public Edicion findEdicion(String nombreEdicion) {
        for (Evento ev : eventos) {
        	if(ev.tieneEdicion(nombreEdicion)) {
        		Edicion ed = ev.findEdicion(nombreEdicion);
        		if (ed != null) {
        			return ed;
        		}
        	}
        }
        	return null;
    	}
	}

	public Evento findEvento(String nombreEvento) {
	    if (nombreEvento == null) return null;
	
	    for (Evento e : eventos) {
	        if (e.getNombre().equalsIgnoreCase(nombreEvento)) {
	            return e;
	        }
	    }
	    return null;
	}


