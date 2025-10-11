package ServidorCentral.logica;

import java.util.List;

public class ControllerInstitucion implements IControllerInstitucion {

	public void altaInstitucion(String nombreIns, String url, String descripcion,String img) throws Exception {
		ManejadorInstitucion mi = ManejadorInstitucion.getInstance();
		Institucion institucionExistente = mi.findInstitucion(nombreIns);
		if (institucionExistente != null) {
			throw new Exception("La institución ya existe.");
		}
		Institucion nuevaInstitucion = new Institucion(nombreIns, url, descripcion,img);
		mi.agregarInstitucion(nuevaInstitucion);
	}
		
	
	public Institucion findInstitucion(String nombreIns) {
		ManejadorInstitucion mi = ManejadorInstitucion.getInstance();
		return mi.findInstitucion(nombreIns);
	}
	
	public List<Institucion> getInstituciones() {
		ManejadorInstitucion mi = ManejadorInstitucion.getInstance();
		return mi.listarInstituciones();
	}

}
