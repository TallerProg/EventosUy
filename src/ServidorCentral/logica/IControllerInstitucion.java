package ServidorCentral.logica;

public interface IControllerInstitucion {
	
	public void altaInstitucion(String nombreIns, String url, String descripcion)throws Exception;
	
	public Institucion findInstitucion(String nombreIns);
	
}