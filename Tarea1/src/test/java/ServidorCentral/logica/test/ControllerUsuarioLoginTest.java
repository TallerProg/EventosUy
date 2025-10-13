package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ServidorCentral.excepciones.CredencialesInvalidasException;
import ServidorCentral.excepciones.UsuarioNoExisteException;
import ServidorCentral.excepciones.UsuarioRepetidoException;
import ServidorCentral.logica.ControllerUsuario;
import ServidorCentral.logica.ManejadorUsuario;

class ControllerUsuarioLoginTest {

    private ControllerUsuario controller;

    @BeforeEach
    void setUp() throws UsuarioRepetidoException {
        controller = new ControllerUsuario();

        // Limpieza
        ManejadorUsuario.getInstance().limpiarUsuarios();

        ControllerUsuario cu = controller;

        // ⬇️ OJO: se agrega el parámetro Institucion (null en este caso) DESPUÉS de la fecha
        cu.altaAsistente(
                "asist1",
                "asist1@mail.com",
                "Juan",
                "Perez",
                LocalDate.of(2000, 1, 1),
                null,           // Institucion (sin institución asociada)
                "1234",
                "asist1.png"    // imagen/avatar
        );

        // Alta de organizador (con imagen al final)
        cu.altaOrganizador(
                "org1",
                "org1@mail.com",
                "Org Uno",
                "Desc org uno",
                "https://org1.example",
                "abcd",
                "org1.png"
        );
    }

    @Test
    void loginPorNicknameAsistenteOK() throws Exception {
        ControllerUsuario.DTSesionUsuario s = controller.iniciarSesion("asist1", "1234");
        assertNotNull(s);
        assertEquals("asist1", s.getNickname());
        assertEquals("asist1@mail.com", s.getCorreo());
        assertEquals(ControllerUsuario.RolUsuario.ASISTENTE, s.getRol());
        assertNotNull(s.getFechaHoraInicio());
        assertTrue(s.getFechaHoraInicio().isBefore(LocalDateTime.now().plusSeconds(2)));
    }

    @Test
    void loginPorCorreoOrganizadorOK() throws Exception {
        ControllerUsuario.DTSesionUsuario s = controller.iniciarSesion("org1@mail.com", "abcd");
        assertNotNull(s);
        assertEquals("org1", s.getNickname());
        assertEquals("org1@mail.com", s.getCorreo());
        assertEquals(ControllerUsuario.RolUsuario.ORGANIZADOR, s.getRol());
    }

    @Test
    void loginUsuarioInexistentefalla() {
        assertThrows(UsuarioNoExisteException.class, () ->
                controller.iniciarSesion("noexiste", "1234"));
    }

    @Test
    void loginPasswordInvalidafalla() {
        assertThrows(CredencialesInvalidasException.class, () ->
                controller.iniciarSesion("asist1", "mal_pass"));
    }

    @Test
    void loginParametrosVaciosfalla() {
        assertThrows(CredencialesInvalidasException.class, () ->
                controller.iniciarSesion("", "1234"));
        assertThrows(CredencialesInvalidasException.class, () ->
                controller.iniciarSesion("asist1", ""));
        assertThrows(CredencialesInvalidasException.class, () ->
                controller.iniciarSesion(null, "1234"));
    }

    @Test
    void cerrarSesionNoNPE() {
        ControllerUsuario.DTSesionUsuario ses =
                new ControllerUsuario.DTSesionUsuario(
                        "asist1", "asist1@mail.com",
                        ControllerUsuario.RolUsuario.ASISTENTE,
                        LocalDateTime.now()
                );
        assertDoesNotThrow(() -> controller.cerrarSesion(ses));
    }
}

