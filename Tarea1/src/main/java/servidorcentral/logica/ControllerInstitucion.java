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
	
	public List<DTInstitucion> getDTInstituciones() {
		ManejadorInstitucion min = ManejadorInstitucion.getInstance();
		return min.listarDTInstituciones();
	}
	
	public DTInstitucion getDTInstitucion(String nombreInst) {
	    ManejadorInstitucion manejador = ManejadorInstitucion.getInstance();
	    Institucion inst = manejador.findInstitucion(nombreInst);
	    if (inst == null)
	        return null;

	    DTInstitucion dti = new DTInstitucion(
	            inst.getNombre(),
	            inst.getDescripcion(),
	            inst.getUrl(),
	            inst.getImg()
	        );
	    return dti;
	}

}
