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

    public List<Edicion> listarEdiciones(){
    	
    	return new ArrayList<>(ediciones);
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

    public void agregarEdicion( Edicion edicion) {
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
    public boolean existeCategoria(String nombreCategoria) {
		for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombreCategoria)) {
                return true;
            }
        }
        return false;
    }
    
    
    public void agregarCategoria(Categoria categoria) {
    	categorias.add(categoria);
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


