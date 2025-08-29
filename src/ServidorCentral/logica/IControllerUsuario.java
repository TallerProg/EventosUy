package ServidorCentral.logica;

import ServidorCentral.excepciones.*;
import java.time.LocalDate;
import java.util.List;


public interface IControllerUsuario{
	
	 void AltaAsistente(String nicknameUsu, String correo, String nombre, String apellido, LocalDate fNacimiento,
	Institucion ins) throws UsuarioRepetidoException;

	 // Alta de un organizador
	 void AltaOrganizador(String nicknameUsu, String correo, String nombre, String descripcion, String url) 
	throws UsuarioRepetidoException;
	 
	 // Modificación de usuario
	 void modificarUsuario(String nickname, String nombre, String apellido,
         LocalDate fNac, String descripcion, String url)
         throws UsuarioNoExisteException;

	 // Consulta de usuario por nickname

	 public Organizador getOrganizador(String nicknameOrg);
	 public DTUsuarioLista ConsultaDeUsuario(String nicknameUsu) throws UsuarioNoExisteException;


	 // Listado de todos los usuarios
	 public List<DTUsuarioLista> getUsuarios();
	 
	 public List<Asistente> getAsistentes();
	 public List<String> getAsistenteRegistro(String nickname);
	 public DTRegistroDetallado getRegistroDetalle(String nregistro, String nickAsistente);

	 public List<Usuario> listarUsuarios();

	 public void modificarUsuario1(Usuario u);

	
}

