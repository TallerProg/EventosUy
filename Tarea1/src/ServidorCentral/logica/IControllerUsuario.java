package src.ServidorCentral.logica;

import src.ServidorCentral.excepciones.*;
import src.ServidorCentral.logica.ControllerUsuario.DTSesionUsuario;

import java.time.LocalDate;
import java.util.List;

public interface IControllerUsuario {

	void AltaAsistente(String nicknameUsu, String correo, String nombre, String apellido, LocalDate fNacimiento,
			Institucion ins, String contrasena) throws UsuarioRepetidoException;

	void AltaOrganizador(String nicknameUsu, String correo, String nombre, String descripcion, String url, String contrasena)
			throws UsuarioRepetidoException;

	public DTRegistro getDTRegistro(String tipNEdicion, String nickAsistente);
	void modificarUsuario(String nickname, String nombre, String apellido, LocalDate fNac, String descripcion,
			String url) throws UsuarioNoExisteException;


	public Organizador getOrganizador(String nicknameOrg);

	public DTUsuarioListaConsulta ConsultaDeUsuario(String nicknameUsu);

	public List<DTUsuarioLista> getUsuarios();

	public List<Asistente> getAsistentes();

	public List<String> getAsistenteRegistro(String nickname);

	public DTRegistroDetallado getRegistroDetalle(String nregistro, String nickAsistente);

	public List<Usuario> listarUsuarios();

	public void modificarUsuario1(Usuario u);

	public Asistente getAsistente(String nicknameTest);
	public Usuario getUsuario(String nicknameTest);
    public void cerrarSesion(DTSesionUsuario sesion);
    public DTSesionUsuario iniciarSesion(String login, String contrasena)
            throws UsuarioNoExisteException, CredencialesInvalidasException;
}
