package ServidorCentral.logica;

import java.util.ArrayList;
import java.util.List;


public class ManejadorUsuario {

    private List<Usuario> usuarios = new ArrayList<>();
    private List<Organizador> organizadores = new ArrayList<>();
    private List<Asistente> asistentes = new ArrayList<>();
    private static ManejadorUsuario instancia = null;
    
    private ManejadorUsuario() {}
    
    public static ManejadorUsuario getinstance() {
        if (instancia == null)
            instancia = new ManejadorUsuario();
        return instancia;
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }
    
    public List<Organizador> listarOrganizadores(){
    	return new ArrayList<>(organizadores);
    }
    
    public List<Asistente> listarAsistentes(){
    	return new ArrayList<>(asistentes);
    }


    public Usuario findUsuario(String nickname) {
        for (Usuario u : usuarios) {
            if (u.getNickname().equalsIgnoreCase(nickname)) {
                return u;
            }
        }
        return null;
    }

    public void agregarUsuario(Usuario u) {
        usuarios.add(u);
    }
    
    public void agregarAsistente(Asistente a) {
    	asistentes.add(a);
    }
    
    public void agregarOrganizador(Organizador o) {
    	organizadores.add(o);
    }
    
    
}
