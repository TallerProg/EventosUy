package ServidorCentral.logica;

import ServidorCentral.excepciones.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControllerUsuario implements IControllerUsuario {

    public ControllerUsuario() {
        // Constructor vacío
    }

    // Método de consulta
    public DTUsuarioLista ConsultaDeUsuario(String nicknameUsu) throws UsuarioNoExisteException {
        return null; // placeholder
    }

    // Métodos de alta
    public void AltaAsistente(String nicknameUsu, String correo, String nombre,
                                     String apellido, LocalDate fNacimiento,Institucion ins) throws UsuarioRepetidoException {
        Usuario u = ManejadorUsuario.findUsuario(nicknameUsu);
        Usuario ucorreo = ManejadorUsuario.findUsuario(correo);
        if (u != null || ucorreo!=null)
            throw new UsuarioRepetidoException("El usuario " + nicknameUsu + " ya esta registrado");
        if(ins==null) {
        	Asistente a = new Asistente(nicknameUsu, correo, nombre, apellido, fNacimiento);
        	ManejadorUsuario.agregarAsistente(a);
        	ManejadorUsuario.agregarUsuario(a);
        }else {
            Asistente a = new Asistente(nicknameUsu, correo, nombre, apellido, fNacimiento,ins);
            ManejadorUsuario.agregarAsistente(a);
            ManejadorUsuario.agregarUsuario(a);
        }
    }

    public void AltaOrganizador(String nicknameUsu, String correo, String nombre,
                                       String descripcion, String url) throws UsuarioRepetidoException {
        Usuario u = ManejadorUsuario.findUsuario(nicknameUsu);
        Usuario ucorreo = ManejadorUsuario.findUsuario(correo);
        if (u != null || ucorreo!=null)
            throw new UsuarioRepetidoException("El usuario " + nicknameUsu + " ya esta registrado");
        if (url!=null) {
        	Organizador o = new Organizador(nicknameUsu, correo, nombre, descripcion, url);
            ManejadorUsuario.agregarOrganizador(o);
            ManejadorUsuario.agregarUsuario(o);
        }else {
        	Organizador o = new Organizador(nicknameUsu, correo, nombre, descripcion);
            ManejadorUsuario.agregarOrganizador(o);
            ManejadorUsuario.agregarUsuario(o);
        }

    }

    // Método de modificación
    //public void ModificarDatosUsuario() {
        // placeholder
    //}

    public List<DTUsuarioLista> getUsuarios() {
        List<DTUsuarioLista> lista = new ArrayList<>();
        for (Usuario u : ManejadorUsuario.listarUsuarios()) {
            List<Edicion> ediciones = new ArrayList<>();
            List<Registro> registros = new ArrayList<>();
            
            if (u instanceof Asistente) {
                Asistente a = (Asistente) u;
                registros = a.getRegistros();
            } else if (u instanceof Organizador) {
                Organizador o = (Organizador) u;
                ediciones = o.getEdiciones(); 
            }

            lista.add(new DTUsuarioLista(u.getNickname(), u.getCorreo(), u.getNombre(), ediciones, registros));
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
