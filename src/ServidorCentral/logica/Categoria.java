package ServidorCentral.logica;
import java.util.ArrayList;
import java.util.List;

public class Categoria {
	private static List<Categoria> categorias = new ArrayList<>();
	private String nombre;
   
   
    
    public Categoria(String nombre) {
        this.nombre = nombre;
        Categoria.categorias.add(this);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
