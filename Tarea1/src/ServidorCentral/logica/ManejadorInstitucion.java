package src.ServidorCentral.logica;

import java.util.ArrayList;
import java.util.List;

public class ManejadorInstitucion {

	private static ManejadorInstitucion instancia = null;
	private List<Institucion> instituciones;

	private ManejadorInstitucion() {
		instituciones = new ArrayList<>();
	}

	public static ManejadorInstitucion getInstance() {
		if (instancia == null) {
			instancia = new ManejadorInstitucion();
		}
		return instancia;
	}

	public void agregarInstitucion(Institucion institucion) {
		if (!instituciones.contains(institucion)) {
			instituciones.add(institucion);
		}
	}

	public void limpiar() {
	    instituciones.clear();
	}
	public Institucion findInstitucion(String nombreInstitucion) {
		for (Institucion o : instituciones) {
			if (o.getNombre().equalsIgnoreCase(nombreInstitucion)) {
				return o;
			}
		}
		return null;
	}

	public List<Institucion> listarInstituciones() {
		return new ArrayList<>(instituciones);
	}
}
