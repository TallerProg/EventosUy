package servidorcentral.logica;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


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

	public static List<Categoria> fromDtoList(List<DTCategoria> dtCategorias) {
	    if (dtCategorias == null || dtCategorias.isEmpty()) {
	        return Collections.emptyList();
	    }

	    List<Categoria> resultado = new ArrayList<>();
	    Set<String> vistos = new LinkedHashSet<>();

	    for (DTCategoria dt : dtCategorias) {
	        if (dt == null) continue;
	        String nombre = dt.getNombre();
	        if (nombre == null) continue;

	        nombre = nombre.trim();
	        if (nombre.isEmpty()) continue;

	        if (vistos.add(nombre)) {
	            resultado.add(new Categoria(nombre));
	        }
	    }
	    return resultado;
	}


}
