package servidorcentral.logica;

import servidorcentral.excepciones.CredencialesInvalidasException;
import servidorcentral.excepciones.UsuarioNoExisteException;
import servidorcentral.excepciones.UsuarioRepetidoException;
import servidorcentral.logica.DTSesionUsuario;

import java.time.LocalDate;
import java.util.List;

public interface IControllerUsuario {

	void altaAsistente(String nicknameUsu, String correo, String nombre, String apellido, LocalDate fNacimiento,
			Institucion ins, String contrasena, String img) throws UsuarioRepetidoException;

	void altaOrganizador(String nicknameUsu, String correo, String nombre, String descripcion, String url, String contrasena, String img)
			throws UsuarioRepetidoException;

	public DTRegistro getDTRegistro(String tipNEdicion, String nickAsistente);
	void modificarUsuario(String nickname, String nombre, String apellido, LocalDate fNac, String descripcion,
			String url) throws UsuarioNoExisteException;


	public Organizador getOrganizador(String nicknameOrg);

	public DTUsuarioListaConsulta consultaDeUsuario(String nicknameUsu);
    public void seguirPersona(String principal, String seguido);
    public void sacarSeguirPersona(String principal, String seguido);

	public DTAsistente getDTAsistente(String nick);
	public DTOrganizadorDetallado getDTOrganizadorDetallado(String nick);

	public List<DTUsuarioLista> getUsuarios();
	
	public void setContrasena(String nickname, String contrasenaNueva)
			throws UsuarioNoExisteException, CredencialesInvalidasException;

	public List<Asistente> getAsistentes();
	
	public List<DTUsuarioListaConsulta> getDTAsistentes();
	
	public List<Organizador> getOrganizadores();
	
	public List<DTUsuarioListaConsulta> getDTOrganizadores();
	
	public boolean esOrganizador(String identifier);

	public List<String> getAsistenteRegistro(String nickname);

	public DTRegistroDetallado getRegistroDetalle(String nregistro, String nickAsistente);

	public List<Usuario> listarUsuarios();
	public void modificarUsuarioDT(DTUsuarioListaConsulta dto);
	public void modificarUsuario1(Usuario usu);
	public void aneadirInstitucion(String nicknAsis, String nombreIns);
	public Asistente getAsistente(String nicknameTest);
	public Usuario getUsuario(String nicknameTest);
    public void cerrarSesion(DTSesionUsuario sesion);
    public DTSesionUsuario iniciarSesion(String login, String contrasena)
            throws UsuarioNoExisteException, CredencialesInvalidasException;
    public void actualizarImagenUsuario(String nickname, String imgPath);
}
