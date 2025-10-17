package servidorcentral.logica;



public class Categoria {
	private String nombre;

	public Categoria(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
	public DTCategoria getDTCategoria() {
		return new DTCategoria(this.nombre);
	}

}
