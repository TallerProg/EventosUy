package ServidorCentral.logica;

import ServidorCentral.excepciones.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControllerUsuario implements IControllerUsuario {

	public ControllerUsuario() {
		// Constructor vacío
	}


    public DTUsuarioListaConsulta ConsultaDeUsuario(String nicknameUsu) {
    	ManejadorUsuario manejador = ManejadorUsuario.getinstance();
	    Usuario usuario = manejador.findUsuario(nicknameUsu);
	    if (usuario == null) 
	    	return null;
	    DTUsuarioListaConsulta dt = new DTUsuarioListaConsulta();
	    dt.setNickname(usuario.getNickname());
	    dt.setCorreo(usuario.getCorreo());
	    dt.setNombre(usuario.getNombre());
	    //CASO ASIS
	    if (usuario instanceof Asistente) {
	    	Asistente a = (Asistente) usuario;
	        dt.setApellido(a.getApellido());
	        dt.setFNacimiento(a.getfNacimiento());
	        dt.setRegistros(a.getRegistros());
	        List<Edicion> edicionesDeRegistros = a.getRegistros().stream().map(r -> r.getEdicion()).toList();
	        dt.setEdiciones(edicionesDeRegistros);
	        dt.setDescripcion(null);
	        dt.setUrl(null);	        
	    //CASO ORG    
	    } else if (usuario instanceof Organizador) {
	        Organizador o = (Organizador) usuario;
	        dt.setDescripcion(o.getDescripcion());
	        dt.setUrl(o.getUrl());
	        dt.setEdiciones(o.getEdiciones());
	        dt.setApellido(null);
	        dt.setFNacimiento(null);
	        dt.setRegistros(null);
	    }
	    return dt;
	}     

    public void AltaAsistente(String nicknameUsu, String correo, String nombre, String apellido, 
    LocalDate fNacimiento,Institucion ins) throws UsuarioRepetidoException {
    	ManejadorUsuario mu = ManejadorUsuario.getinstance();
        Usuario u = mu.findUsuario(nicknameUsu);
        Usuario ucorreo = mu.findCorreo(correo);
        if (u != null || ucorreo!=null)
            throw new UsuarioRepetidoException("El usuario " + nicknameUsu + " ya esta registrado");
        if(ins==null) {
        	Asistente a = new Asistente(nicknameUsu, correo, nombre, apellido, fNacimiento);
        	mu.agregarAsistente(a);
        	mu.agregarUsuario(a);
        }else {
            Asistente a = new Asistente(nicknameUsu, correo, nombre, apellido, fNacimiento,ins);
            mu.agregarAsistente(a);
            mu.agregarUsuario(a);
            ins.addAsistente(a);
        }
    }


	public List<Usuario> listarUsuarios() {
		ManejadorUsuario manejador = ManejadorUsuario.getinstance();
		return manejador.listarUsuarios();
	}

	public void AltaOrganizador(String nicknameUsu, String correo, String nombre, String descripcion, String url)
			throws UsuarioRepetidoException {
		ManejadorUsuario mu = ManejadorUsuario.getinstance();
		Usuario u = mu.findUsuario(nicknameUsu);
		Usuario ucorreo = mu.findUsuario(correo);
		if (u != null || ucorreo != null)
			throw new UsuarioRepetidoException("El usuario " + nicknameUsu + " ya esta registrado");
		if (url != null) {
			Organizador o = new Organizador(nicknameUsu, correo, nombre, descripcion, url);
			mu.agregarOrganizador(o);
			mu.agregarUsuario(o);
		} else {
			Organizador o = new Organizador(nicknameUsu, correo, nombre, descripcion);
			mu.agregarOrganizador(o);
			mu.agregarUsuario(o);
		}

	}

	// Método de modificación
	// public void ModificarDatosUsuario() {
	// placeholder

	public List<DTUsuarioLista> getUsuarios() {
		List<DTUsuarioLista> lista = new ArrayList<>();
		ManejadorUsuario mu = ManejadorUsuario.getinstance();
		for (Usuario u : mu.listarUsuarios()) {
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

	public List<Asistente> getAsistentes() {
		ManejadorUsuario mu = ManejadorUsuario.getinstance();
		return mu.listarAsistentes();

	}

	public Asistente getAsistente(String nicknameAsis) {
		ManejadorUsuario mu = ManejadorUsuario.getinstance();
		return mu.findAsistente(nicknameAsis);
	}

	public Organizador getOrganizador(String nicknameOrg) {
		ManejadorUsuario mu = ManejadorUsuario.getinstance();
		return mu.findOrganizador(nicknameOrg);
	}

	public List<String> getAsistenteRegistro(String nickname) {
		ManejadorUsuario mU = ManejadorUsuario.getinstance();

		List<Registro> regg = mU.findAsistente(nickname).getRegistros();
		List<String> nombres = new ArrayList<>();
		for (Registro r : regg) {
			nombres.add(r.getTipoRegistro().getEdicion().getNombre());
		}
		return nombres;
	}

	public DTRegistroDetallado getRegistroDetalle(String tipNEdicion, String nickAsistente) {
		ManejadorUsuario mU = ManejadorUsuario.getinstance();
		List<Registro> registros = mU.findAsistente(nickAsistente).getRegistros();
		for (Registro re : registros) {
			if (re.getTipoRegistro().getEdicion().getNombre().equals(tipNEdicion)) {
				return re.getDTRegistroDetallado();
			}
		}

		return null;
	}

	public void modificarUsuario(String nickname, String nombre, String apellido, LocalDate fNac, String descripcion,
			String url) throws UsuarioNoExisteException {

		ManejadorUsuario mu = ManejadorUsuario.getinstance();
		Usuario u = mu.findUsuario(nickname);

		if (u == null)
			throw new UsuarioNoExisteException("No existe el usuario " + nickname);

		u.setNombre(nombre);
		u.setCorreo(u.getCorreo());

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

	public void modificarUsuario1(Usuario u) {
		ManejadorUsuario mu = ManejadorUsuario.getinstance();
		Usuario existente = mu.findUsuario(u.getNickname());

		existente.setNombre(u.getNombre());
		existente.setCorreo(u.getCorreo());

		if (existente instanceof Asistente && u instanceof Asistente) {
			Asistente aExistente = (Asistente) existente;
			Asistente aNuevo = (Asistente) u;
			aExistente.setApellido(aNuevo.getApellido());
			aExistente.setfNacimiento(aNuevo.getfNacimiento());
			aExistente.setInstitucion(aNuevo.getInstitucion());
		} else if (existente instanceof Organizador && u instanceof Organizador) {
			Organizador oExistente = (Organizador) existente;
			Organizador oNuevo = (Organizador) u;
			oExistente.setDescripcion(oNuevo.getDescripcion());
			oExistente.setUrl(oNuevo.getUrl());
		}
	}
	
	public Usuario getUsuario(String nickname) {
		ManejadorUsuario mu = ManejadorUsuario.getinstance();
		return mu.findUsuario(nickname);
	}

}
