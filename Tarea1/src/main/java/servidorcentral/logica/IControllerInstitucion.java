package servidorcentral.logica;

import java.util.List;

public interface IControllerInstitucion {
	
	public void altaInstitucion(String nombreIns, String url, String descripcion, String img)throws Exception;
	
	public Institucion findInstitucion(String nombreIns);
	
	public List<Institucion> getInstituciones();
	
}