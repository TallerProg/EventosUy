package ServidorCentral.logica;

import java.util.List;
import java.util.ArrayList;
public class Institucion {

    private String nombre;
    private String url;
    private String descripcion;

    // Relaciones
    private List<Patrocinio> patrocinio;  
    private List<Asistente> asistentes;   

    // Constructor
    

    public Institucion(String nombre, String url, String descripcion) {
        this.nombre = nombre;
        this.url = url;
        this.descripcion = descripcion;
        this.patrocinio = new ArrayList<>();
        this.asistentes = new ArrayList<>();
    }


    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Patrocinio> getPatrocinio() { return patrocinio; }
    public void setPatrocinio(List<Patrocinio> patrocinio) { this.patrocinio = patrocinio; }

    public List<Asistente> getAsistentes() { return asistentes; }
    public void setAsistentes(List<Asistente> asistentes) { this.asistentes = asistentes; }

    public void agregarPatrocinio(Patrocinio pat) {
  
        if (asistentes != null) {
            for (Asistente a : asistentes) {
                if(a != null) a.setPatrocinio(pat);
            }
        }

        
          patrocinio.add(pat);
   
    }
    
    public void addAsistente(Asistente a){
    	this.asistentes.add(a);
    }

    // Métodos de relación
    /*
    public Patrocinio getPatrocinioInstitucion(String codigo) {
        for (Patrocinio p : patrocinio) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public boolean addLinkRegistroPatrocinio(Registro reg) {
        Patrocinio p = getPatrocinioInstitucion(reg.getPatrocinio().getCodigo());
        if (p != null) {
            return p.agregarRegistro(reg);
        }
        return false;
    }

    public Patrocinio crearPatrocinio(String codigo, float monto, TNivel nivel, TipoRegistro tipo, int regGratuito) {
        Patrocinio p = new Patrocinio(codigo, null, regGratuito, monto, nivel, this, null, tipo);
        patrocinio.add(p);
        return p;
    }
    */
}
