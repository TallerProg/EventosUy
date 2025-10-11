package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Edicion {

    private final String nombre;
    private final LocalDate fInicio;
    private final LocalDate fFin;
    private LocalDate fAlta;
    private final String ciudad;
    private final String pais;
    private final String sigla;
    private final Evento evento;

    private final List<TipoRegistro> tipoRegistros;
    private final List<Organizador> organizadores;
    private final List<Registro> registros;
    private final List<Patrocinio> patrocinios;

    private EstadoEdicion estado;
    private String imagenWebPath;

    public Edicion(
            String nombre,
            String sigla,
            LocalDate fInicio,
            LocalDate fFin,
            LocalDate fAlta,
            String ciudad,
            String pais,
            Evento evento
    ) {
        this.nombre = nombre;
        this.fInicio = fInicio;
        this.fFin = fFin;
        this.ciudad = ciudad;
        this.pais = pais;
        this.sigla = sigla;
        this.fAlta = fAlta;
        this.evento = evento;

        this.tipoRegistros = new ArrayList<>();
        this.organizadores = new ArrayList<>();
        this.registros = new ArrayList<>();
        this.patrocinios = new ArrayList<>();

        this.estado = EstadoEdicion.Ingresada;
    }


    public LocalDate getFechaAlta() {
        return fAlta;
    }

    public void setFechaAlta(LocalDate fAlta) {
        this.fAlta = fAlta;
    }

    public Evento getEvento() {
        return evento;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaInicio() {
        return fInicio;
    }

    public LocalDate getFechaFin() {
        return fFin;
    }

    @Deprecated
    public LocalDate getfInicio() {
        return getFechaInicio();
    }

    @Deprecated
    public LocalDate getfFin() {
        return getFechaFin();
    }

    public LocalDate getFAlta() {
        return fAlta;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }

    public String getSigla() {
        return sigla;
    }

    public List<TipoRegistro> getTipoRegistros() {
        return tipoRegistros;
    }

    public List<Organizador> getOrganizadores() {
        return organizadores;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    public List<Patrocinio> getPatrocinios() {
        return patrocinios;
    }

    public String getImagenWebPath() {
        return imagenWebPath;
    }

    public void setImagenWebPath(String imagenWebPath) {
        this.imagenWebPath = imagenWebPath;
    }


    public void addLinkRegistro(Registro reg) {
        if (reg != null && !this.registros.contains(reg)) {
            this.registros.add(reg);
            reg.setEdicion(this);
        }
    }

    public void addLinkPatrocinio(Patrocinio pat) {
        if (pat != null) {
            this.patrocinios.add(pat);
            pat.setEdicion(this);
        }
    }

    public void agregarTipoRegistro(TipoRegistro tr) {
        if (tr != null) {
            tipoRegistros.add(tr);
        }
    }

    public TipoRegistro getEdicionTR(String nombreTR) {
        if (nombreTR == null) {
            return null;
        }
        for (TipoRegistro tr : tipoRegistros) {
            if (tr.getNombre().equalsIgnoreCase(nombreTR)) {
                return tr;
            }
        }
        return null;
    }

    public boolean registrado(Asistente asistente) {
        for (Registro reg : registros) {
            if (reg.getAsistente().equals(asistente)) {
                return true;
            }
        }
        return false;
    }

    public boolean habilitadoAsistente(String nombreTR, Asistente asistente) throws Exception {
        TipoRegistro tr = getEdicionTR(nombreTR);
        if (tr == null) {
            throw new Exception("tipo registro no existe");
        }
        if (registrado(asistente)) {
            throw new Exception(asistente.getNickname() + " Ya esta registrado");
        }
        if (tr.soldOutTipReg()) {
            throw new Exception("No hay mas lugares disponibles para el tipo de registro " + nombreTR);
        }
        return true;
    }

    @SuppressWarnings("PMD.AvoidReturningNull")
    public DTTipoRegistro datosTipoRegistroEdicion(String nombreTipoR) {
        TipoRegistro r = getEdicionTR(nombreTipoR);
        if (r != null) {
            return r.getDTTipoRegistro();
        }
        return null; 
    }

    public DTEdicion getDTEdicion() {
        List<DTTipoRegistro> trs = new ArrayList<>();
        for (TipoRegistro tr : this.tipoRegistros) {
            trs.add(tr.getDTTipoRegistro());
        }

        List<DTOrganizador> orgs = new ArrayList<>();
        for (Organizador o : this.organizadores) {
            orgs.add(o.getDTOrganizador());
        }

        List<DTRegistro> regs = new ArrayList<>();
        for (Registro r : this.registros) {
            regs.add(r.getDTRegistro());
        }

        List<DTPatrocinio> pats = new ArrayList<>();
        for (Patrocinio p : this.patrocinios) {
            pats.add(p.getDTPatrocinio());
        }

        String estadoStr = (estado == null) ? null : estado.name();

        return new DTEdicion(
            nombre, sigla, fInicio, fFin, fAlta, ciudad, pais,
            trs, orgs, regs, pats,
            estadoStr  
        );
    }


    public boolean existeTR(String nombreTR) {
        for (TipoRegistro tr : tipoRegistros) {
            if (tr.getNombre().equalsIgnoreCase(nombreTR)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getNombre();
    }

    public void setEstado(EstadoEdicion estado) {
        this.estado = estado;
    }

    public EstadoEdicion getEstado() {
        return estado;
    }
}
