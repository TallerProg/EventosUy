package src.ServidorCentral.logica;

import src.ServidorCentral.excepciones.*;

import java.time.LocalDate;
import java.time.LocalDateTime;     // ⟵ NUEVO (solo si querés timestamp de login)
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControllerUsuario implements IControllerUsuario {

    // ====== NUEVO: tipos auxiliares para sesión ======
    public enum RolUsuario { ASISTENTE, ORGANIZADOR, VISITANTE }

    /** DTO mínimo para guardar en HttpSession desde la capa web */
    public static class DTSesionUsuario {
        private final String nickname;
        private final String correo;
        private final RolUsuario rol;
        private final LocalDateTime fechaHoraInicio; // opcional

        public DTSesionUsuario(String nickname, String correo, RolUsuario rol, LocalDateTime fechaHoraInicio) {
            this.nickname = nickname;
            this.correo = correo;
            this.rol = rol;
            this.fechaHoraInicio = fechaHoraInicio;
        }
        public String getNickname() { return nickname; }
        public String getCorreo() { return correo; }
        public RolUsuario getRol() { return rol; }
        public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    }
    // ==================================================

    public ControllerUsuario() {}

    // ================== NUEVO: INICIAR / CERRAR SESIÓN ==================

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

        ManejadorUsuario mu = ManejadorUsuario.getinstance();

        // buscar por nickname, si no, por correo
        Usuario u = mu.findUsuario(login);
        if (u == null) {
            u = mu.findCorreo(login);
        }
        if (u == null) {
            throw new UsuarioNoExisteException("No existe un usuario con ese nickname/correo.");
        }

        if (!passwordCoincide(contrasena, u.getContrasena())) {
            throw new CredencialesInvalidasException("Credenciales inválidas.");
        }

        RolUsuario rol = (u instanceof Asistente) ? RolUsuario.ASISTENTE
                        : (u instanceof Organizador) ? RolUsuario.ORGANIZADOR
                        : RolUsuario.VISITANTE;

        return new DTSesionUsuario(u.getNickname(), u.getCorreo(), rol, LocalDateTime.now());
    }

    /**
     * Cierra sesión. A nivel dominio no invalida nada (eso es propio de la HttpSession).
     * Se deja para auditoría o lógica adicional si se necesitara.
     */
    public void cerrarSesion(DTSesionUsuario sesion) {
        Objects.requireNonNull(sesion);
        // opcional: registrar auditoría, métricas, etc.
    }

    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
    /** Si usás hash, adaptá esta comparación. */
    private boolean passwordCoincide(String ingresada, String almacenada) {
        return Objects.equals(ingresada, almacenada);
    }

    // ================== TU CÓDIGO EXISTENTE ==================

    public DTUsuarioListaConsulta ConsultaDeUsuario(String nicknameUsu) {
        ManejadorUsuario manejador = ManejadorUsuario.getinstance();
        Usuario usuario = manejador.findUsuario(nicknameUsu);
        if (usuario == null)
            return null;
        DTUsuarioListaConsulta dt = new DTUsuarioListaConsulta();
        dt.setNickname(usuario.getNickname());
        dt.setCorreo(usuario.getCorreo());
        dt.setNombre(usuario.getNombre());
        if (usuario instanceof Asistente) {
            Asistente a = (Asistente) usuario;
            dt.setApellido(a.getApellido());
            dt.setFNacimiento(a.getfNacimiento());
            dt.setRegistros(a.getRegistros());
            List<Edicion> edicionesDeRegistros = a.getRegistros().stream().map(r -> r.getEdicion()).toList();
            dt.setEdiciones(edicionesDeRegistros);
            dt.setDescripcion(null);
            dt.setUrl(null);
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
                              LocalDate fNacimiento, Institucion ins, String contrasena)
            throws UsuarioRepetidoException {
        ManejadorUsuario mu = ManejadorUsuario.getinstance();
        Usuario u = mu.findUsuario(nicknameUsu);
        Usuario ucorreo = mu.findCorreo(correo);
        if (u != null || ucorreo != null)
            throw new UsuarioRepetidoException("El usuario " + nicknameUsu + " ya esta registrado");
        if (ins == null) {
            Asistente a = new Asistente(nicknameUsu, correo, nombre, apellido, fNacimiento, contrasena);
            mu.agregarAsistente(a);
            mu.agregarUsuario(a);
        } else {
            Asistente a = new Asistente(nicknameUsu, correo, nombre, apellido, fNacimiento, ins, contrasena);
            mu.agregarAsistente(a);
            mu.agregarUsuario(a);
            ins.addAsistente(a);
        }
    }

    public List<Usuario> listarUsuarios() {
        ManejadorUsuario manejador = ManejadorUsuario.getinstance();
        return manejador.listarUsuarios();
    }

    public void AltaOrganizador(String nicknameUsu, String correo, String nombre, String descripcion, String url, String contrasena)
            throws UsuarioRepetidoException {
        ManejadorUsuario mu = ManejadorUsuario.getinstance();
        Usuario u = mu.findUsuario(nicknameUsu);
        Usuario ucorreo = mu.findUsuario(correo);
        if (u != null || ucorreo != null)
            throw new UsuarioRepetidoException("El usuario " + nicknameUsu + " ya esta registrado");
        if (url != null) {
            Organizador o = new Organizador(nicknameUsu, correo, nombre, descripcion, url, contrasena);
            mu.agregarOrganizador(o);
            mu.agregarUsuario(o);
        } else {
            Organizador o = new Organizador(nicknameUsu, correo, nombre, descripcion, contrasena);
            mu.agregarOrganizador(o);
            mu.agregarUsuario(o);
        }
    }

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

    public DTRegistro getDTRegistro(String tipNEdicion, String nickAsistente) {
        ManejadorUsuario mU = ManejadorUsuario.getinstance();
        List<Registro> registros = mU.findAsistente(nickAsistente).getRegistros();
        for (Registro re : registros) {
            if (re.getTipoRegistro().getEdicion().getNombre().equals(tipNEdicion)) {
                return re.getDTRegistro();
            }
        }
        return null;
    }

    public void modificarUsuario(String nickname, String nombre, String apellido, LocalDate fNac, String descripcion, String url)
            throws UsuarioNoExisteException {
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
