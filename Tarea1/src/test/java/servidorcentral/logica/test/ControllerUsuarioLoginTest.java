package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import servidorcentral.excepciones.CredencialesInvalidasException;
import servidorcentral.excepciones.UsuarioNoExisteException;
import servidorcentral.excepciones.UsuarioRepetidoException;
import servidorcentral.logica.ControllerUsuario;
import servidorcentral.logica.ManejadorUsuario;
import servidorcentral.logica.RolUsuario;
import servidorcentral.logica.DTSesionUsuario;
import java.util.List;

import servidorcentral.logica.Asistente;
import servidorcentral.logica.TipoRegistro;
import servidorcentral.logica.Registro;


class ControllerUsuarioLoginTest {

    private ControllerUsuario controller;

    @BeforeEach
    void setUp() throws UsuarioRepetidoException {
        controller = new ControllerUsuario();

        // Limpieza
        ManejadorUsuario.getInstance().limpiarUsuarios();

        ControllerUsuario cus = controller;

        cus.altaAsistente(
                "asist1",
                "asist1@mail.com",
                "Juan",
                "Perez",
                LocalDate.of(2000, 1, 1),
                null,           
                "1234",
                "asist1.png"    
        );

  
        cus.altaOrganizador(
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
       DTSesionUsuario ses = controller.iniciarSesion("asist1", "1234");
        assertNotNull(ses);
        assertEquals("asist1", ses.getNickname());
        assertEquals("asist1@mail.com", ses.getCorreo());
        assertEquals(RolUsuario.ASISTENTE, ses.getRol());
        assertNotNull(ses.getFechaHoraInicio());
        assertTrue(ses.getFechaHoraInicio().isBefore(LocalDateTime.now().plusSeconds(2)));
    }

    @Test
    void loginPorCorreoOrganizadorOK() throws Exception {
        DTSesionUsuario ses = controller.iniciarSesion("org1@mail.com", "abcd");
        assertNotNull(ses);
        assertEquals("org1", ses.getNickname());
        assertEquals("org1@mail.com", ses.getCorreo());
        assertEquals(RolUsuario.ORGANIZADOR, ses.getRol());
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
        DTSesionUsuario ses =
                new DTSesionUsuario(
                        "asist1", "asist1@mail.com",
                        RolUsuario.ASISTENTE,
                        LocalDateTime.now()
                );
        assertDoesNotThrow(() -> controller.cerrarSesion(ses));
    }
    @Test
    void asistenteRegistrosFechas_OK() throws Exception {
        Asistente a = controller.getAsistente("asist1");
        assertNotNull(a, "Debe existir el asistente asist1");

        TipoRegistro tipo = new TipoRegistro("General", "Acceso general", 100f, 50, null);

        LocalDate f1 = LocalDate.now();
        LocalDate f2 = LocalDate.now().plusDays(1);

        // Ojo al orden del constructor: (costo, edicion, asistente, tipoRegistro, fInicio)
        Registro r1 = new Registro(100f, null, a, tipo, f1);
        Registro r2 = new Registro(200f, null, a, tipo, f2);

        a.addRegistro(r1);
        a.addRegistro(r2);

        var fechas = a.registrosFechas();

        assertEquals(2, fechas.size(), "Debe devolver 2 fechas");
        assertEquals(f1.toString(), fechas.get(0), "Debe preservar el orden de inserción");
        assertEquals(f2.toString(), fechas.get(1));
    }

}

