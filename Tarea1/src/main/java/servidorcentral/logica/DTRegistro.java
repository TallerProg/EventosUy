package servidorcentral.logica;

import java.time.LocalDate;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTRegistro {

	private LocalDate fInicio;
	private String fInicioS;
	private Float costo;
	private String tipoRegistroNombre;
	private String asistenteNickname;
	private String patrocinioCodigo;
	private Edicion edicion;
	public DTRegistro(LocalDate fInicio, Float costo, String tipoRegistroNombre, String asistenteNickname,
			String patrocinioCodigo, Edicion edicion) {
		this.fInicio = fInicio;
		this.fInicioS = (fInicio != null) ? fInicio.toString() : null;
		this.costo = costo;
		this.tipoRegistroNombre = tipoRegistroNombre;
		this.asistenteNickname = asistenteNickname;
		this.patrocinioCodigo = patrocinioCodigo;
		this.edicion = edicion;
	}

	public LocalDate getfInicio() {
		return fInicio;
	}
	public String getfInicioS() {
		return fInicioS;
	}

	public Float getCosto() {
		return costo;
	}

	public String getTipoRegistroNombre() {
		return tipoRegistroNombre;
	}

	public String getAsistenteNickname() {
		return asistenteNickname;
	}

	public String getPatrocinioCodigo() {
		return patrocinioCodigo;
	}
	
	public Edicion getEdicion() {
		return edicion;
	}
}
