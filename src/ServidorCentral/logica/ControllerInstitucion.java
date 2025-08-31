package ServidorCentral.logica;

import java.util.List;

public class ControllerInstitucion implements IControllerInstitucion {

	public void altaInstitucion(String nombreIns, String url, String descripcion) throws Exception {
		ManejadorInstitucion mi = ManejadorInstitucion.getInstance();
		Institucion institucionExistente = mi.findInstitucion(nombreIns);
		if (institucionExistente != null) {
			throw new Exception("La institución ya existe.");
		}
		Institucion nuevaInstitucion = new Institucion(nombreIns, url, descripcion);
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

	/*
	 * // Método de alta de patrocinio public boolean AltaPatrocinio(String
	 * nombreEdi, String nombreIns, TNivel nivel, float monto, TipoRegistro tipo,
	 * int regGratuito, String codigo) { // Retorno temporal return false; }
	 * 
	 * // Método para cancelar un patrocinio public boolean QuiereCancelar() {
	 * return false; }
	 * 
	 * // Método para editar patrocinio public void EditarPatrocinio() { // Vacío
	 * por ahora }
	 * 
	 * // Método de consulta de patrocinio public void ConsultaPatrocinio(String
	 * codigo) { // Vacío por ahora }
	 */
}
