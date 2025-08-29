package ServidorCentral.logica;

import java.time.LocalDate;

public class DTRegistro {
    private LocalDate fRegistro;
    private String nombreEvento;
    private String nombreEdicion;
    private String tipoRegistro;
    private float costo;

    
    
    public DTRegistro(LocalDate fRegistro, String nombreEvento, String nombreEdicion, String tipoRegistro, String nombrePatrocinador, float costo) {
        this.fRegistro = fRegistro;
        this.nombreEvento = nombreEvento;
        this.nombreEdicion = nombreEdicion;
        this.tipoRegistro = tipoRegistro;
        this.costo = costo;
    }

    // Getters
    public LocalDate getfRegistro() { return fRegistro; }
    public String getNombreEvento() { return nombreEvento; }
    public String getNombreEdicion() { return nombreEdicion; }
    public String getTipoRegistro() { return tipoRegistro; }
    public float getCosto() { return costo; }

}

