package ServidorCentral.logica;

import java.time.LocalDate;

public class Registro {

    private LocalDate fInicio;
    private Float costo;

    // Relación con Edicion (muchos a uno)
    private Edicion edicion;

    // Relación con Asistente (muchos a uno)
    private Asistente asistente;

    // Relación opcional con Patrocinio (muchos a uno)
    private Patrocinio patrocinio;

    private TipoRegistro tipoRegistro;
    // Constructor
    public Registro( Float costo, Edicion edicion, Asistente asistente, TipoRegistro tipoRegistro) {
        this.fInicio = LocalDate.now();
        this.costo = costo;
        this.edicion = edicion;
        this.asistente = asistente;
        this.tipoRegistro = tipoRegistro;
    }

    // Getters y Setters
    public LocalDate getFInicio() { return fInicio; }
    public void setFInicio(LocalDate fInicio) { this.fInicio = fInicio; }

    public Float getCosto() { return costo; }
    public void setCosto(Float costo) { this.costo = costo; }

    public Edicion getEdicion() { return edicion; }
    public void setEdicion(Edicion edicion) { this.edicion = edicion; }

    public Asistente getAsistente() { return asistente; }
    public void setAsistente(Asistente asistente) { this.asistente = asistente; }

    public Patrocinio getPatrocinio() { return patrocinio; }
    public void setPatrocinio(Patrocinio patrocinio) { this.patrocinio = patrocinio; }
    
    public TipoRegistro getTipoRegistro() { return tipoRegistro; }
    public void setTipoRegistro(TipoRegistro tipoRegistro) { this.tipoRegistro = tipoRegistro; }
    // Métodos relacionados (comentados)
}
