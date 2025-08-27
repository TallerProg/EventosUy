package ServidorCentral.logica;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evento {

    private String nombre;
    private String sigla;
    private String descripcion;
    private Date fAlta;
    private Categoria categoria; 
    private List<Edicion> ediciones = new ArrayList<>();

    public Evento(String nombre, String sigla, String descripcion, Date fAlta, Categoria categoria) {
        this.nombre = nombre;
        this.sigla = sigla;
        this.descripcion = descripcion;
        this.fAlta = fAlta;
        this.categoria = categoria;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Date getFAlta() { return fAlta; }
    public void setFAlta(Date fAlta) { this.fAlta = fAlta; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    // Métodos para ediciones
    public void agregarEdicion(Edicion edicion) {
        if (edicion != null && !ediciones.contains(edicion)) {
            ediciones.add(edicion);
        }
    }

    public boolean tieneEdicion(String nombreEdicion) {
        for (Edicion e : ediciones) {
            if (e.getNombre().equalsIgnoreCase(nombreEdicion)) {
                return true;
            }
        }
        return false;
    }

    public List<Edicion> getEdiciones() {
        return new ArrayList<>(ediciones);
    }
}
