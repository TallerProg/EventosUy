package ServidorCentral.logica;

import java.util.ArrayList;
import java.util.List;

public class ManejadorEvento {
    private static ManejadorEvento instancia;
    private static List<Evento> eventos = new ArrayList<>();
    private static List<Categoria> categorias = new ArrayList<>();

    public static ManejadorEvento getInstancia() {
        if (instancia == null) {
            instancia = new ManejadorEvento();
        }
        return instancia;
    }

    public static List<Evento> listarEventos() {
        return new ArrayList<>(eventos);
    }

    public static List<Categoria> listarCategorias() {
        return new ArrayList<>(categorias);
    }

    public static boolean existeEvento(String nombre) {
        for (Evento e : eventos) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    public static void agregarEvento(Evento e) {
        eventos.add(e);
    }

    public static void agregarEdicion(Evento evento, Edicion edicion) {
        evento.agregarEdicion(edicion);
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
}


