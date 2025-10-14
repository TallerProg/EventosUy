package servidorcentral.logica;

import java.util.List;
import java.util.ArrayList;

public class Institucion {

    private String nombre;
    private String url;
    private String descripcion;
    private String img;
    
    private List<Patrocinio> patrocinios;
    private List<Asistente> asistentes;

    public Institucion(String nombre, String url, String descripcion, String img) {
        this.nombre = nombre;
        this.url = url;
        this.descripcion = descripcion;
        this.img = img;
        this.patrocinios = new ArrayList<>();
        this.asistentes = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Patrocinio> getPatrocinios() { return patrocinios; }
    public void setPatrocinios(List<Patrocinio> patrocinios) { this.patrocinios = patrocinios; }

    public List<Asistente> getAsistentes() { return asistentes; }
    public void setAsistentes(List<Asistente> asistentes) { this.asistentes = asistentes; }

    public void agregarPatrocinio(Patrocinio pat) {
        if (asistentes != null) {
            for (Asistente a : asistentes) {
                if (a != null)
                    a.addPatrocinio(pat);
            }
        }
        patrocinios.add(pat);
    }
    
    public Patrocinio findPatrocinio(String codigo) {
		for (Patrocinio p : patrocinios) {
			if (p.getCodigo().equals(codigo)) {
				return p;
			}
		}
		return null;
	}

    public void addAsistente(Asistente asi) {
        this.asistentes.add(asi);
    }
}
