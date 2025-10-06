package src.ServidorCentral.logica;

public class DTTipoRegistro {

	private String nombre;
	private String descripcion;
	private float costo;
	private int cupo;

	public DTTipoRegistro(String nomb, String descr, float costo, int cupo) {
		this.nombre = nomb;
		this.descripcion = descr;
		this.costo = costo;
		this.cupo = cupo;
	}

	public String getNombre() {
		return nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public float getCosto() {
		return costo;
	}

	public int getCupo() {
		return cupo;
	}

}
