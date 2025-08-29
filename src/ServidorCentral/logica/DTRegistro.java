package ServidorCentral.logica;

import java.time.LocalDate;

public class DTRegistro {

    private LocalDate fInicio;
    private Float costo;
    private String tipoRegistroNombre;
    private String asistenteNickname; // <-- agrega esto
    private String patrocinioCodigo;   // opcional, si quieres mostrarlo

    public DTRegistro(LocalDate fInicio, Float costo, String tipoRegistroNombre, String asistenteNickname, String patrocinioCodigo) {
        this.fInicio = fInicio;
        this.costo = costo;
        this.tipoRegistroNombre = tipoRegistroNombre;
        this.asistenteNickname = asistenteNickname;
        this.patrocinioCodigo = patrocinioCodigo;
    }

    public LocalDate getfInicio() { return fInicio; }
    public Float getCosto() { return costo; }
    public String getTipoRegistroNombre() { return tipoRegistroNombre; }
    public String getAsistenteNickname() { return asistenteNickname; } // <-- aquí
    public String getPatrocinioCodigo() { return patrocinioCodigo; }
}
