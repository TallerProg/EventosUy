package ServidorCentral.logica;

import java.util.Date;

public class Registro {

    private Date fInicio;
    private Float costo;

    // Relación con Edicion (muchos a uno)
    private Edicion edicion;

    // Relación con Asistente (muchos a uno)
    private Asistente asistente;

    // Relación opcional con Patrocinio (muchos a uno)
    private Patrocinio patrocinio;

    // Constructor
    public Registro(Date fInicio, Float costo, Edicion edicion, Asistente asistente) {
        this.fInicio = fInicio;
        this.costo = costo;
        this.edicion = edicion;
        this.asistente = asistente;
    }

    // Getters y Setters
    public Date getFInicio() { return fInicio; }
    public void setFInicio(Date fInicio) { this.fInicio = fInicio; }

    public Float getCosto() { return costo; }
    public void setCosto(Float costo) { this.costo = costo; }

    public Edicion getEdicion() { return edicion; }
    public void setEdicion(Edicion edicion) { this.edicion = edicion; }

    public Asistente getAsistente() { return asistente; }
    public void setAsistente(Asistente asistente) { this.asistente = asistente; }

    public Patrocinio getPatrocinio() { return patrocinio; }
    public void setPatrocinio(Patrocinio patrocinio) { this.patrocinio = patrocinio; }

    // Métodos relacionados (comentados)
}
