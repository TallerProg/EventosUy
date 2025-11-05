package servidorcentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class DTAsistente {
    private  String nickname;
    private  String correo;
    private  String nombre;
    private  String apellido;
	@XmlTransient
    private  LocalDate fNacimiento;
    private  String fNacimientoS;
    private  String img;
    private  DTInstitucion institucion;     // puede ser null
    private  List<DTRegistro> registros;    // nunca null (lista vacía si no hay)

    public DTAsistente() {
		this.nickname = null;
		this.correo = null;
		this.nombre = null;
		this.apellido = null;
		this.fNacimiento = null;
		this.fNacimientoS = null;
		this.img = null;
		this.institucion = null;
		this.registros = new ArrayList<>();
	}
    public DTAsistente(String nickname,
                       String correo,
                       String nombre,
                       String apellido,
                       LocalDate fNacimiento,
                       String img,
                       DTInstitucion institucion,
                       List<DTRegistro> registros) {
        this.nickname = nickname;
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fNacimiento = fNacimiento;
        this.fNacimientoS = (fNacimiento != null) ? fNacimiento.toString() : null;
        this.img = img;
        this.institucion = institucion;
        this.registros = (registros == null) ? new ArrayList<>() : new ArrayList<>(registros);
    }

    public String getNickname() { return nickname; }
    public String getCorreo() { return correo; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public LocalDate getFNacimiento() { return fNacimiento; }
    public String getFNacimientoS() { return fNacimientoS; }
    public String getImg() { return img; }
    public DTInstitucion getInstitucion() { return institucion; }
    public List<DTRegistro> getRegistros() { return new ArrayList<>(registros); }
    
    public void setNickname(String nickname) { this.nickname = nickname; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setFNacimiento(LocalDate fNacimiento) {
        this.fNacimiento = fNacimiento;
        this.fNacimientoS = (fNacimiento == null) ? null : fNacimiento.toString();
    }
    public void setFNacimientoS(String fNacimientoS) {
        this.fNacimientoS = fNacimientoS;
        this.fNacimiento = (fNacimientoS == null || fNacimientoS.isBlank())
                ? null : LocalDate.parse(fNacimientoS);
    }
    public void setImg(String img) { this.img = img; }
    public void setInstitucion(DTInstitucion institucion) { this.institucion = institucion; }
    public void setRegistros(List<DTRegistro> registros) {
        this.registros = (registros == null) ? new ArrayList<>() : new ArrayList<>(registros);
    }
}
