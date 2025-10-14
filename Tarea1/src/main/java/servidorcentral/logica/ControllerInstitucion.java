package servidorcentral.logica;

import java.util.List;

public class ControllerInstitucion implements IControllerInstitucion {

	public void altaInstitucion(String nombreIns, String url, String descripcion, String img) throws Exception {
		ManejadorInstitucion min = ManejadorInstitucion.getInstance();
		Institucion institucionExistente = min.findInstitucion(nombreIns);
		if (institucionExistente != null) {
			throw new Exception("La institución ya existe.");
		}
		Institucion nuevaInstitucion = new Institucion(nombreIns, url, descripcion, img);
		min.agregarInstitucion(nuevaInstitucion);
	}
		
	
	public Institucion findInstitucion(String nombreIns) {
		ManejadorInstitucion min = ManejadorInstitucion.getInstance();
		return min.findInstitucion(nombreIns);
	}
	
	public List<Institucion> getInstituciones() {
		ManejadorInstitucion min = ManejadorInstitucion.getInstance();
		return min.listarInstituciones();
	}

}
