package ServidorCentral.logica.test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import ServidorCentral.excepciones.CredencialesInvalidasException;
import ServidorCentral.excepciones.UsuarioNoExisteException;
import ServidorCentral.excepciones.UsuarioRepetidoException;
import ServidorCentral.logica.*;

class ControllerUsuarioLoginTest {

    private ControllerUsuario controller;

    @BeforeEach
    void setUp() throws UsuarioRepetidoException {
        controller = new ControllerUsuario();

        // Limpieza
        ManejadorUsuario.getinstance().limpiarUsuarios();

        // Alta de un asistente y un organizador (con contraseñas en texto plano)
        ControllerUsuario cu = controller;

        cu.AltaAsistente(
                "asist1",
                "asist1@mail.com",
                "Juan",
                "Perez",
                LocalDate.of(2000, 1, 1),
                null,            // avatar
                "1234"
        );

        cu.AltaOrganizador(
                "org1",
                "org1@mail.com",
                "Org Uno",
                "Desc org uno",
                "https://org1.example",
                "abcd"           // password organizador
        );
    }

    @Test
    void loginPorNickname_Asistente_OK() throws Exception {
        ControllerUsuario.DTSesionUsuario s = controller.iniciarSesion("asist1", "1234");
        assertNotNull(s);
        assertEquals("asist1", s.getNickname());
        assertEquals("asist1@mail.com", s.getCorreo());
        assertEquals(ControllerUsuario.RolUsuario.ASISTENTE, s.getRol());
        assertNotNull(s.getFechaHoraInicio());
        assertTrue(s.getFechaHoraInicio().isBefore(LocalDateTime.now().plusSeconds(2)));
    }

    @Test
    void loginPorCorreo_Organizador_OK() throws Exception {
        ControllerUsuario.DTSesionUsuario s = controller.iniciarSesion("org1@mail.com", "abcd");
        assertNotNull(s);
        assertEquals("org1", s.getNickname());
        assertEquals("org1@mail.com", s.getCorreo());
        assertEquals(ControllerUsuario.RolUsuario.ORGANIZADOR, s.getRol());
    }

    @Test
    void loginUsuarioInexistente_falla() {
        assertThrows(UsuarioNoExisteException.class, () ->
                controller.iniciarSesion("noexiste", "1234"));
    }

    @Test
    void loginPasswordInvalida_falla() {
        assertThrows(CredencialesInvalidasException.class, () ->
                controller.iniciarSesion("asist1", "mal_pass"));
    }

    @Test
    void loginParametrosVacios_falla() {
        assertThrows(CredencialesInvalidasException.class, () ->
                controller.iniciarSesion("", "1234"));

        assertThrows(CredencialesInvalidasException.class, () ->
                controller.iniciarSesion("asist1", ""));

        assertThrows(CredencialesInvalidasException.class, () ->
                controller.iniciarSesion(null, "1234"));
    }

    @Test
    void cerrarSesion_NoNPE() {
        // Simplemente verificamos que no tire NPE (usa Objects.requireNonNull)
        ControllerUsuario.DTSesionUsuario ses =
                new ControllerUsuario.DTSesionUsuario("asist1", "asist1@mail.com",
                        ControllerUsuario.RolUsuario.ASISTENTE, LocalDateTime.now());

        assertDoesNotThrow(() -> controller.cerrarSesion(ses));
    }
}
