package servidorcentral.logica;

import java.util.List;

public interface IControllerInstitucion {
	
	public void altaInstitucion(String nombreIns, String desc, String url, String img)throws Exception;
	
	public Institucion findInstitucion(String nombreIns);
	
	public List<Institucion> getInstituciones();
	
	public List<DTInstitucion> getDTInstituciones();
	public DTInstitucion getDTInstitucion(String nombreInst);
}