package ServidorCentral.logica;

import java.time.LocalDate;

public class DTPatrocinio {

    private String codigo;
    private LocalDate fInicio;
    private int registroGratuito;
    private Float monto;
    private ETipoNivel nivel;

    // Para no acoplar demasiado, se suelen poner solo nombres o ids de relaciones
    private String institucion;    // nombre o código de la institucion
    private String edicion;        // nombre de la edicion
    private String tipoRegistro;   // nombre o código del tipo de registro

    // Constructor completo
    public DTPatrocinio(String codigo, LocalDate fInicio, int registroGratuito, Float monto, ETipoNivel nivel,
                        String institucion, String edicion, String tipoRegistro) {
        this.codigo = codigo;
        this.fInicio = fInicio;
        this.registroGratuito = registroGratuito;
        this.monto = monto;
        this.nivel = nivel;
        this.institucion = institucion;
        this.edicion = edicion;
        this.tipoRegistro = tipoRegistro;
    }

    // Getters
    public String getCodigo() { return codigo; }
    public LocalDate getFInicio() { return fInicio; }
    public int getRegistroGratuito() { return registroGratuito; }
    public Float getMonto() { return monto; }
    public ETipoNivel getNivel() { return nivel; }
    public String getInstitucion() { return institucion; }
    public String getEdicion() { return edicion; }
    public String getTipoRegistro() { return tipoRegistro; }

    
}
