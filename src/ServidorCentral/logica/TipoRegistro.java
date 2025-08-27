package ServidorCentral.logica;

import java.util.List;
import java.util.ArrayList;

public class TipoRegistro {

    private String nombre;      // único 
    private String descripcion;
    private Float costo;
    private int cupo;

    // Relación muchos a uno con Edicion
    private Edicion edicion;

    // Relación muchos a muchos con Patrocinio
    private List<Patrocinio> patrocinioList;

    // Constructor
    public TipoRegistro(String nombre, String descripcion, Float costo, int cupo, Edicion edicion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costo = costo;
        this.cupo = cupo;
        this.edicion = edicion;
        this.patrocinioList = new ArrayList<>();
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Float getCosto() { return costo; }
    public void setCosto(Float costo) { this.costo = costo; }

    public int getCupo() { return cupo; }
    public void setCupo(int cupo) { this.cupo = cupo; }

    public Edicion getEdicion() { return edicion; }
    public void setEdicion(Edicion edicion) { this.edicion = edicion; }

    public List<Patrocinio> getPatrocinioList() { return patrocinioList; }
    public void setPatrocinioList(List<Patrocinio> patrocinioList) { this.patrocinioList = patrocinioList; }
    
    public boolean habilitaAsistente(Asistente asistente) {
        if (asistente == null) return false;
        if (this.cupo <= 0) return false;

        this.cupo--;

        return true;
    }


    // Métodos relacionados (comentados)
    // public void addLinkRegistro(Registro reg) { /* implementar */ }
    // public boolean soldOutTipReg() { /* implementar */ return false; }
    // public boolean registradoTipReg(Asistente asistente) { /* implementar */ return false; }
    // public DTTipoRegistro getDTTipoRegistro() { /* implementar */ return null; }
}
