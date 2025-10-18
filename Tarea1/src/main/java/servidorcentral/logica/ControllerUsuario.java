package servidorcentral.logica;

import java.time.LocalDate;
import java.time.LocalDateTime;     
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import servidorcentral.excepciones.CredencialesInvalidasException;
import servidorcentral.excepciones.UsuarioNoExisteException;
import servidorcentral.excepciones.UsuarioRepetidoException;

public class ControllerUsuario implements IControllerUsuario {

   
    public ControllerUsuario() {}


    /**
     * Inicia sesión con nickname o correo + contraseña.
     * @throws UsuarioNoExisteException si no existe el usuario
     * @throws CredencialesInvalidasException si la contraseña es incorrecta o faltan datos
     */
    public DTSesionUsuario iniciarSesion(String login, String contrasena)
            throws UsuarioNoExisteException, CredencialesInvalidasException {

        if (isBlank(login) || isBlank(contrasena)) {
            throw new CredencialesInvalidasException("Debe ingresar usuario (nickname o correo) y contraseña.");
        }

        ManejadorUsuario manusu = ManejadorUsuario.getInstance();

        Usuario usu = manusu.findUsuario(login);
        if (usu == null) {
            usu = manusu.findCorreo(login);
        }
        if (usu == null) {
            throw new UsuarioNoExisteException("No existe un usuario con ese nickname/correo.");
        }

        if (!passwordCoincide(contrasena, usu.getContrasena())) {
            throw new CredencialesInvalidasException("Credenciales inválidas.");
        }

        RolUsuario rol = (usu instanceof Asistente) ? RolUsuario.ASISTENTE
                        : (usu instanceof Organizador) ? RolUsuario.ORGANIZADOR
                        : RolUsuario.VISITANTE;

        return new DTSesionUsuario(usu.getNickname(), usu.getCorreo(), rol, LocalDateTime.now());
    }
    public void cerrarSesion(DTSesionUsuario sesion) {
        Objects.requireNonNull(sesion);
    }

    private static boolean isBlank(String str) { return str == null || str.trim().isEmpty(); }
    /** Si usás hash, adaptá esta comparación. */
    private boolean passwordCoincide(String ingresada, String almacenada) {
        return Objects.equals(ingresada, almacenada);
    }


    public DTUsuarioListaConsulta consultaDeUsuario(String nicknameUsu) {
        ManejadorUsuario manejador = ManejadorUsuario.getInstance();
        Usuario usuario = manejador.findUsuario(nicknameUsu);
        if (usuario == null)
            return null;
        DTUsuarioListaConsulta dtu = new DTUsuarioListaConsulta();
        dtu.setNickname(usuario.getNickname());
        dtu.setCorreo(usuario.getCorreo());
        dtu.setNombre(usuario.getNombre());
        if (usuario instanceof Asistente) {
            Asistente asi = (Asistente) usuario;
            dtu.setApellido(asi.getApellido());
            dtu.setFNacimiento(asi.getfNacimiento());
            dtu.setRegistros(asi.getRegistros());
            List<Edicion> edicionesDeRegistros = asi.getRegistros().stream().map(r -> r.getEdicion()).toList();
            Institucion inst=asi.getInstitucion();
            DTInstitucion dti = null;
            if (inst != null) {
                ControllerInstitucion ctrlInst = new ControllerInstitucion();
                dti = ctrlInst.getDTInstitucion(inst.getNombre());
            }
            dtu.setIns(dti);
            dtu.setEdiciones(edicionesDeRegistros);
            dtu.setDescripcion(null);
            dtu.setUrl(null);
        } else if (usuario instanceof Organizador) {
            Organizador org = (Organizador) usuario;
            dtu.setDescripcion(org.getDescripcion());
            dtu.setUrl(org.getUrl());
            dtu.setEdiciones(org.getEdiciones());
            dtu.setApellido(null);
            dtu.setFNacimiento(null);
            dtu.setRegistros(null);
        }
        return dtu;
    }

    public void altaAsistente(String nicknameUsu, String correo, String nombre, String apellido,
                              LocalDate fNacimiento, Institucion ins, String contrasena, String img)
            throws UsuarioRepetidoException {
        ManejadorUsuario mus = ManejadorUsuario.getInstance();
        Usuario usu = mus.findUsuario(nicknameUsu);
        Usuario ucorreo = mus.findCorreo(correo);
        if (usu != null || ucorreo != null)
            throw new UsuarioRepetidoException("El usuario " + nicknameUsu + " ya esta registrado");
        if (ins == null) {
            Asistente asi = new Asistente(nicknameUsu, correo, nombre, apellido, fNacimiento, contrasena, img);
            mus.agregarAsistente(asi);
            mus.agregarUsuario(asi);
        } else {
            Asistente asi = new Asistente(nicknameUsu, correo, nombre, apellido, fNacimiento, ins, contrasena, img);
            mus.agregarAsistente(asi);
            mus.agregarUsuario(asi);
            ins.addAsistente(asi);
        }
    }

    public List<Usuario> listarUsuarios() {
        ManejadorUsuario manejador = ManejadorUsuario.getInstance();
        return manejador.listarUsuarios();
    }

    public void altaOrganizador(String nicknameUsu, String correo, String nombre, String descripcion, String url, String contrasena, String img)
            throws UsuarioRepetidoException {
        ManejadorUsuario mus = ManejadorUsuario.getInstance();
        Usuario usu = mus.findUsuario(nicknameUsu);
        Usuario ucorreo = mus.findUsuario(correo);
        if (usu != null || ucorreo != null)
            throw new UsuarioRepetidoException("El usuario " + nicknameUsu + " ya esta registrado");
        if (url != null) {
            Organizador org = new Organizador(nicknameUsu, correo, nombre, descripcion, url, contrasena, img);
            mus.agregarOrganizador(org);
            mus.agregarUsuario(org);
        } else {
            Organizador org = new Organizador(nicknameUsu, correo, nombre, descripcion, contrasena, img);
            mus.agregarOrganizador(org);
            mus.agregarUsuario(org);
        }
    }

    public List<DTUsuarioLista> getUsuarios() {
        List<DTUsuarioLista> lista = new ArrayList<>();
        ManejadorUsuario mus = ManejadorUsuario.getInstance();
        for (Usuario usu : mus.listarUsuarios()) {
            List<Edicion> ediciones = new ArrayList<>();
            List<Registro> registros = new ArrayList<>();
            if (usu instanceof Asistente) {
                Asistente asi = (Asistente) usu;
                registros = asi.getRegistros();
            } else if (usu instanceof Organizador) {
                Organizador org = (Organizador) usu;
                ediciones = org.getEdiciones();
            }
            lista.add(new DTUsuarioLista(usu.getNickname(), usu.getCorreo(), usu.getNombre(), ediciones, registros));
        }
        return lista;
    }

    public List<Asistente> getAsistentes() {
        ManejadorUsuario mus = ManejadorUsuario.getInstance();
        return mus.listarAsistentes();
    }

    public List<Organizador> getOrganizadores() {
        ManejadorUsuario mus = ManejadorUsuario.getInstance();
        return mus.listarOrganizadores();
    }
    
    public Asistente getAsistente(String nicknameAsis) {
        ManejadorUsuario mus = ManejadorUsuario.getInstance();
        return mus.findAsistente(nicknameAsis);
    }

    public Organizador getOrganizador(String nicknameOrg) {
        ManejadorUsuario mus = ManejadorUsuario.getInstance();
        return mus.findOrganizador(nicknameOrg);
    }

    public List<String> getAsistenteRegistro(String nickname) {
        ManejadorUsuario mUs = ManejadorUsuario.getInstance();
        List<Registro> regg = mUs.findAsistente(nickname).getRegistros();
        List<String> nombres = new ArrayList<>();
        for (Registro reg : regg) {
            nombres.add(reg.getTipoRegistro().getEdicion().getNombre());
        }
        return nombres;
    }

    public DTRegistroDetallado getRegistroDetalle(String tipNEdicion, String nickAsistente) {
        ManejadorUsuario mUs = ManejadorUsuario.getInstance();
        List<Registro> registros = mUs.findAsistente(nickAsistente).getRegistros();
        for (Registro reg : registros) {
            if (reg.getTipoRegistro().getEdicion().getNombre().equals(tipNEdicion)) {
                return reg.getDTRegistroDetallado();
            }
        }
        return null;
    }

    public DTRegistro getDTRegistro(String tipNEdicion, String nickAsistente) {
        ManejadorUsuario mUs = ManejadorUsuario.getInstance();
        List<Registro> registros = mUs.findAsistente(nickAsistente).getRegistros();
        for (Registro reg : registros) {
            if (reg.getTipoRegistro().getEdicion().getNombre().equals(tipNEdicion)) {
                return reg.getDTRegistro();
            }
        }
        return null;
    }

    public void modificarUsuario(String nickname, String nombre, String apellido, LocalDate fNac, String descripcion, String url)
            throws UsuarioNoExisteException {
        ManejadorUsuario mus = ManejadorUsuario.getInstance();
        Usuario usu = mus.findUsuario(nickname);
        if (usu == null)
            throw new UsuarioNoExisteException("No existe el usuario " + nickname);
        usu.setNombre(nombre);
        usu.setCorreo(usu.getCorreo());
        if (usu instanceof Asistente) {
            Asistente asi = (Asistente) usu;
            asi.setApellido(apellido);
            asi.setfNacimiento(fNac);
        } else if (usu instanceof Organizador) {
            Organizador org = (Organizador) usu;
            org.setDescripcion(descripcion);
            org.setUrl(url);
        }
    }

    public void modificarUsuario1(Usuario usu) {
        ManejadorUsuario mus = ManejadorUsuario.getInstance();
        Usuario existente = mus.findUsuario(usu.getNickname());
        existente.setNombre(usu.getNombre());
        existente.setCorreo(usu.getCorreo());
        if (existente instanceof Asistente && usu instanceof Asistente) {
            Asistente aExistente = (Asistente) existente;
            Asistente aNuevo = (Asistente) usu;
            aExistente.setApellido(aNuevo.getApellido());
            aExistente.setfNacimiento(aNuevo.getfNacimiento());
            aExistente.setInstitucion(aNuevo.getInstitucion());
        } else if (existente instanceof Organizador && usu instanceof Organizador) {
            Organizador oExistente = (Organizador) existente;
            Organizador oNuevo = (Organizador) usu;
            oExistente.setDescripcion(oNuevo.getDescripcion());
            oExistente.setUrl(oNuevo.getUrl());
        }
    }

    public Usuario getUsuario(String nickname) {
        ManejadorUsuario mus = ManejadorUsuario.getInstance();
        return mus.findUsuario(nickname);
    }
    
    public List<DTUsuarioListaConsulta> getDTAsistentes(){
		ManejadorUsuario mus = ManejadorUsuario.getInstance();
		List<DTUsuarioListaConsulta> listaDTAsistentes = new ArrayList<>();
		for (Asistente asi : mus.listarAsistentes()) {
			DTUsuarioListaConsulta dtu = new DTUsuarioListaConsulta();
			dtu.setNickname(asi.getNickname());
			dtu.setCorreo(asi.getCorreo());
			dtu.setNombre(asi.getNombre());
			dtu.setApellido(asi.getApellido());
			dtu.setFNacimiento(asi.getfNacimiento());
			dtu.setRegistros(asi.getRegistros());
			List<Edicion> edicionesDeRegistros = asi.getRegistros().stream().map(r -> r.getEdicion()).toList();
			dtu.setEdiciones(edicionesDeRegistros);
			dtu.setImg(asi.getImg());
			listaDTAsistentes.add(dtu);
		}
		return listaDTAsistentes;
    }
    
    public List<DTUsuarioListaConsulta> getDTOrganizadores(){
    	        ManejadorUsuario mus = ManejadorUsuario.getInstance();
    	        List<DTUsuarioListaConsulta> listaDTOrganizadores = new ArrayList<>();
				for (Organizador org : mus.listarOrganizadores()) {
					DTUsuarioListaConsulta dtu = new DTUsuarioListaConsulta();
					dtu.setNickname(org.getNickname());
					dtu.setCorreo(org.getCorreo());
					dtu.setNombre(org.getNombre());
					dtu.setDescripcion(org.getDescripcion());
					dtu.setUrl(org.getUrl());
					dtu.setEdiciones(org.getEdiciones());
					dtu.setImg(org.getImg());
					listaDTOrganizadores.add(dtu);
				}
				return listaDTOrganizadores;
    }
}
