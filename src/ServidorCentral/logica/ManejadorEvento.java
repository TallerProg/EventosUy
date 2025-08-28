package ServidorCentral.logica;

import java.util.ArrayList;
import java.util.List;

public class ManejadorEvento {
    private static ManejadorEvento instancia;
    private  List<Evento> eventos = new ArrayList<>();
    private  List<Categoria> categorias = new ArrayList<>();
    private  List<Edicion> ediciones = new ArrayList<>();
    public static ManejadorEvento getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorEvento();
        }
        return instancia;
    }

    public List<Evento> listarEventos() {
        return new ArrayList<>(eventos);
    }

    public List<Categoria> listarCategorias() {
        return new ArrayList<>(categorias);
    }

    public boolean existeEvento(String nombre) {
        for (Evento e : eventos) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    public void agregarEvento(Evento e) {
        eventos.add(e);
    }

    public void agregarEdicion(Evento evento, Edicion edicion) {
    	ediciones.add(edicion);
    }

    public Edicion findEdicion(String nombreEdicion) {
        for (Evento ev : eventos) {
            if (ev.tieneEdicion(nombreEdicion)) {
                Edicion ed = ev.findEdicion(nombreEdicion);
                if (ed != null) {
                    return ed;
                }
            }
        }
        return null;
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
    
    public void altaCategoria(String nombre) {
    	Categoria c = new Categoria(nombre);
    	categorias.add(c);
    }

	public boolean existeEdicion(String nombreEdicion) {
		for (Edicion e : ediciones) {
            if (e.getNombre().equalsIgnoreCase(nombreEdicion)) {
                return true;
            }
        }
        return false;
    }
	
}


