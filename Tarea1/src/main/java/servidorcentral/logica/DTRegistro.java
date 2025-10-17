package servidorcentral.logica;

import java.time.LocalDate;

public class DTRegistro {

	private LocalDate fInicio;
	private Float costo;
	private String tipoRegistroNombre;
	private String asistenteNickname;
	private String patrocinioCodigo;
	private DTEdicion edicionDT;
	public DTRegistro(LocalDate fInicio, Float costo, String tipoRegistroNombre, String asistenteNickname,
			String patrocinioCodigo, DTEdicion edicionDT) {
		this.fInicio = fInicio;
		this.costo = costo;
		this.tipoRegistroNombre = tipoRegistroNombre;
		this.asistenteNickname = asistenteNickname;
		this.patrocinioCodigo = patrocinioCodigo;
		this.edicionDT = edicionDT;
	}

	public LocalDate getfInicio() {
		return fInicio;
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
	
	public DTEdicion getEdicion() {
		return edicionDT;
	}
}
