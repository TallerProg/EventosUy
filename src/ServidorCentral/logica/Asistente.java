package ServidorCentral.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Asistente extends Usuario {

    private String apellido;
    private LocalDate fNacimiento;
    
    // Relación con registros
    private List<Registro> registros;
    
    // Relación con institución
    private Institucion institucion;
    
    // Relación con patrocinio (opcional)
    private Patrocinio patrocinio;

    // Constructor
    public Asistente(String nickname, String correo, String nombre, String apellido, LocalDate fNacimiento, Institucion ins) {
    	
        super(nickname, correo, nombre);
        this.apellido = apellido;
        this.fNacimiento = fNacimiento;
        this.registros = new ArrayList<>();
        this.institucion = ins;
        this.patrocinio = null;
    }
    public Asistente(String nickname, String correo, String nombre, String apellido, LocalDate fNacimiento) {
    	
        super(nickname, correo, nombre);
        this.apellido = apellido;
        this.fNacimiento = fNacimiento;
        this.registros = new ArrayList<>();
        this.institucion = null;
        this.patrocinio = null;
    }

    // Getters y Setters
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    
    public LocalDate getfNacimiento() { return fNacimiento; }
    public void setfNacimiento(LocalDate fNacimiento) { this.fNacimiento = fNacimiento; }

    public List<Registro> getRegistros() { return registros; }

    public Institucion getInstitucion() { return institucion; }
    public void setInstitucion(Institucion institucion) { this.institucion = institucion; }

    public Patrocinio getPatrocinio() { return patrocinio; }
    public void setPatrocinio(Patrocinio patrocinio) { this.patrocinio = patrocinio; }
    
    public List<String> registrosFechas() {
        List<String> registrosFechas = new ArrayList<>();
        for (Registro r : registros) {
            registrosFechas.add(r.getFInicio().toString());
        }

        return registrosFechas;
    }


    // Métodos
    
    /*
    public void editarDatosAsistente(String nombre, String apellido, LocalDate fNacimiento) {

    }

    @Override
    public DTUsuarioLista obtenerDataAsis() {
        
    }


    public void addLinkRegistro(Registro reg) {
        
    }
    */
}
