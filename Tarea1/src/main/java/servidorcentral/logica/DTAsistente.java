package servidorcentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DTAsistente {
    private final String nickname;
    private final String correo;
    private final String nombre;
    private final String apellido;
    private final LocalDate fNacimiento;
    private final String img;
    private final DTInstitucion institucion;     // puede ser null
    private final List<DTRegistro> registros;    // nunca null (lista vacía si no hay)

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
        this.img = img;
        this.institucion = institucion;
        // defensiva para evitar NPE
        this.registros = (registros == null) ? new ArrayList<>() : new ArrayList<>(registros);
    }

    public String getNickname() { return nickname; }
    public String getCorreo() { return correo; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public LocalDate getFNacimiento() { return fNacimiento; }
    public String getImg() { return img; }
    public DTInstitucion getInstitucion() { return institucion; }
    public List<DTRegistro> getRegistros() { return new ArrayList<>(registros); }
}
