package ServidorCentral.logica;

import ServidorCentral.excepciones.UsuarioNoExisteException;
import ServidorCentral.excepciones.UsuarioRepetidoException;
import java.time.LocalDate;

public interface IControllerUsuario{
	
	public abstract void AltaUsuarioAsistente(String nickname, String correo, String nombre, String apellido, LocalDate fnacimiento) throws UsuarioRepetidoException;
	
	
	public abstract void AltaUsuarioOrganizador(String nickname, String correo, String nombre, String desc, String url) throws UsuarioRepetidoException;
	
	
	//consulta usuario
	
}

