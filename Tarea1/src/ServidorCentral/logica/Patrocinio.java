package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Patrocinio {

	private String codigo;
	private LocalDate fInicio;
	private int registroGratuito;
	private Float monto;
	private ETipoNivel nivel;

	private Institucion institucion;
	private Edicion edicion; 
	private TipoRegistro tipoRegistro; 
	private List<Asistente> asistentes; 
	private List<Registro> registros; 


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
        this.registros= new ArrayList<>();
        this.asistentes = new ArrayList<>();   
    }



	public String getCodigo() {
		return codigo;
	}

	public LocalDate getFInicio() {
		return fInicio;
	}

	public int getRegistroGratuito() {
		return registroGratuito;
	}

	public Float getMonto() {
		return monto;
	}

	public ETipoNivel getNivel() {
		return nivel;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public Edicion getEdicion() {
		return edicion;
	}
	
	public void setEdicion(Edicion edicion) {
		this.edicion = edicion;
	}

	public TipoRegistro getTipoRegistro() {
		return tipoRegistro;
	}

	public List<Asistente> getAsistentes() {
		return asistentes;
	}

	public List<Registro> getRegistros() {
		return registros;
	}
	//retorna tru si se pueden seguir agregando registros gratuitos
	public boolean consultarRegistros() {
		long cantidadGratis = registros.stream().filter(r -> r.getCosto() == 0).count();
		return cantidadGratis < registroGratuito;
	}

	public boolean agregarRegistro(Registro reg) {
		long gratuitosActuales = registros.stream().filter(r -> r.getCosto() == 0).count();
		if (reg.getCosto() == 0 && gratuitosActuales >= registroGratuito) {
			return false; 
		}
		registros.add(reg);
		return true;
	}

	public DTPatrocinio getDTPatrocinio() {
		String nombreInstitucion = (institucion != null) ? institucion.getNombre() : null;
		String nombreEdicion = (edicion != null) ? edicion.getNombre() : null;
		String nombreTipoRegistro = (tipoRegistro != null) ? tipoRegistro.getNombre() : null;

		return new DTPatrocinio(codigo, fInicio, registroGratuito, monto, nivel, nombreInstitucion, nombreEdicion,
				nombreTipoRegistro);
	}

}
