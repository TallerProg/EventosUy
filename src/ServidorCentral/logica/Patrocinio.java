package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.List;

public class Patrocinio {

    private String codigo;
    private LocalDate fInicio;
    private int registroGratuito;
    private Float monto;
    private ETipoNivel nivel;

    // Relaciones
    private Institucion institucion;          // muchos a uno
    private Edicion edicion;                  // muchos a uno
    private TipoRegistro tipoRegistro;        // muchos a uno
    private List<Asistente> asistentes;      // muchos a muchos
    private List<Registro> registros;        // uno a muchos

    // Constructor
    public Patrocinio(String codigo, LocalDate fInicio, int registroGratuito, Float monto, ETipoNivel nivel,
      Institucion institucion, Edicion edicion, TipoRegistro tipoRegistro) {
        this.codigo = codigo;
        this.fInicio = fInicio;
        this.registroGratuito = registroGratuito;
        this.monto = monto;
        this.nivel = nivel;
        this.institucion = institucion;
        this.edicion = edicion;
        this.tipoRegistro = tipoRegistro;
    }

    // Getters y setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public LocalDate getFInicio() { return fInicio; }
    public void setFInicio(LocalDate fInicio) { this.fInicio = fInicio; }

    public int getRegistroGratuito() { return registroGratuito; }
    public void setRegistroGratuito(int registroGratuito) { this.registroGratuito = registroGratuito; }

    public Float getMonto() { return monto; }
    public void setMonto(Float monto) { this.monto = monto; }

    public ETipoNivel getNivel() { return nivel; }
    public void setNivel(ETipoNivel nivel) { this.nivel = nivel; }

    public Institucion getInstitucion() { return institucion; }
    public void setInstitucion(Institucion institucion) { this.institucion = institucion; }

    public Edicion getEdicion() { return edicion; }
    public void setEdicion(Edicion edicion) { this.edicion = edicion; }

    public TipoRegistro getTipoRegistro() { return tipoRegistro; }
    public void setTipoRegistro(TipoRegistro tipoRegistro) { this.tipoRegistro = tipoRegistro; }

    public List<Asistente> getAsistentes() { return asistentes; }
    public void setAsistentes(List<Asistente> asistentes) { this.asistentes = asistentes; }

    public List<Registro> getRegistros() { return registros; }
    public void setRegistros(List<Registro> registros) { this.registros = registros; }

    public boolean consultarRegistros() {
        long cantidadGratis = registros.stream().filter(r -> r.getCosto() == 0).count();
        return cantidadGratis < registroGratuito;
    }

    public boolean agregarRegistro(Registro reg) {
        long gratuitosActuales = registros.stream().filter(r -> r.getCosto() == 0).count();
        if (reg.getCosto() == 0 && gratuitosActuales >= registroGratuito) {
            return false; // no se puede agregar
        }
        registros.add(reg);
        return true;
    }
    
    public DTPatrocinio getDTPatrocinio() {
        String nombreInstitucion = (institucion != null) ? institucion.getNombre() : null;
        String nombreEdicion = (edicion != null) ? edicion.getNombre() : null;
        String nombreTipoRegistro = (tipoRegistro != null) ? tipoRegistro.getNombre() : null;

        return new DTPatrocinio(
            codigo,
            fInicio,
            registroGratuito,
            monto,
            nivel,
            nombreInstitucion,
            nombreEdicion,
            nombreTipoRegistro
        );
    }

}
