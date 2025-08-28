package ServidorCentral.logica;

import java.util.ArrayList;
import java.util.List;


public class ManejadorUsuario {

    private static List<Usuario> usuarios = new ArrayList<>();
    private static List<Organizador> organizadores = new ArrayList<>();
    private static List<Asistente> asistentes = new ArrayList<>();
    private static ManejadorUsuario instancia = null;
    
    private ManejadorUsuario() {}
    
    public static ManejadorUsuario getinstance() {
        if (instancia == null)
            instancia = new ManejadorUsuario();
        return instancia;
    }

    public static List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }
    
    public static List<Organizador> listarOrganizadores(){
    	return new ArrayList<>(organizadores);
    }
    
    public static List<Asistente> listarAsistentes(){
    	return new ArrayList<>(asistentes);
    }


    public static Usuario findUsuario(String nickname) {
        for (Usuario u : usuarios) {
            if (u.getNickname().equalsIgnoreCase(nickname)) {
                return u;
            }
        }
        return null;
    }

    public static void agregarUsuario(Usuario u) {
        usuarios.add(u);
    }
    
    public static void agregarAsistente(Asistente a) {
    	asistentes.add(a);
    }
    
    public static void agregarOrganizador(Organizador o) {
    	organizadores.add(o);
    }
    
    
}
