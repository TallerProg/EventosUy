package ServidorCentral.logica;

import java.util.ArrayList;
import java.util.List;

public class ManejadorInstitucion {

    private static ManejadorInstitucion instancia = null;
    private List<Institucion> instituciones;

    // Constructor privado para singleton
    private ManejadorInstitucion() {
        instituciones = new ArrayList<>();
    }

    // Obtener instancia única
    public static ManejadorInstitucion getInstance() {
        if (instancia == null) {
            instancia = new ManejadorInstitucion();
        }
        return instancia;
    }

    // Agregar una institución
    public void agregarInstitucion(Institucion institucion) {
        if (!instituciones.contains(institucion)) {
            instituciones.add(institucion);
        }
    }

    // Buscar institución por nombre
    public Institucion buscarPorNombre(String nombre) {
        for (Institucion ins : instituciones) {
            if (ins.getNombre().equalsIgnoreCase(nombre)) {
                return ins;
            }
        }
        return null;
    }

    // Listar todas las instituciones
    public List<Institucion> listarInstituciones() {
        return new ArrayList<>(instituciones);
    }
}
