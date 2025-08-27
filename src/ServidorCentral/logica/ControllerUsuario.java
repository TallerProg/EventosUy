package ServidorCentral.logica;

import ServidorCentral.excepciones.*;

import java.time.LocalDate;

public class ControllerUsuario implements IControllerUsuario {

    public ControllerUsuario() {
        // Constructor vacío
    }

    // Método de consulta
    public DTUsuarioLista ConsultaDeUsuario(String nicknameUsu) throws UsuarioNoExisteException {
        return null; // placeholder
    }

    // Métodos de alta
    public void AltaUsuarioAsistente(String nicknameUsu, String correo, String nombre,
                                     String apellido, java.util.Date fNacimiento) throws UsuarioRepetidoException {
        // placeholder
    }

    public void AltaUsuarioOrganizador(String nicknameUsu, String correo, String nombre,
                                       String descripcion, String url) throws UsuarioRepetidoException {
        // placeholder
    }

    // Método de modificación
    //public void ModificarDatosUsuario() {
        // placeholder
    //}

	public List<DTUsuarioLista> listarUsuarios() {
	    List<DTUsuarioLista> lista = new ArrayList<>();
	    for (Usuario u : ManejadorUsuario.listarUsuarios()) {
	        lista.add(new DTUsuarioLista(u.getNickname(), u.getCorreo(), u.getNombre()));
	    }
	    return lista;
	}
	
    public void modificarUsuario(String nickname, String nombre, String apellido,
            LocalDate fNac, String descripcion, String url)
				throws UsuarioNoExisteException {
				
				Usuario u = ManejadorUsuario.findUsuario(nickname);
				if (u == null) throw new UsuarioNoExisteException("No existe el usuario " + nickname);
				
				u.setNombre(nombre); 
				
				if (u instanceof Asistente) {
					Asistente a = (Asistente) u;
					a.setApellido(apellido);
					a.setfNacimiento(fNac);
				} else if (u instanceof Organizador) {
					Organizador o = (Organizador) u;
					o.setDescripcion(descripcion);
					o.setUrl(url);
				}
			}
   }	
