package ServidorCentral.logica;

import ServidorCentral.excepciones.*;

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
}
