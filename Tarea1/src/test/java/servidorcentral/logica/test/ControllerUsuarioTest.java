package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import servidorcentral.excepciones.UsuarioRepetidoException;
import servidorcentral.logica.Asistente;
import servidorcentral.logica.DTUsuarioLista;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerUsuario;
import servidorcentral.logica.Institucion;
import servidorcentral.logica.ManejadorUsuario;
import servidorcentral.logica.Organizador;
import servidorcentral.logica.Registro;
import servidorcentral.logica.TipoRegistro;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerUsuarioTest {

    private static IControllerUsuario controladorUsu;

    @BeforeAll
    public static void iniciar() {
        Factory fabrica = Factory.getInstance();
        controladorUsu = fabrica.getIControllerUsuario();
    }

    @BeforeEach
    void limpiarUsuarios() {
        // Si tu manejador tiene limpiarUsuarios(), usalo en su lugar
        ManejadorUsuario.getInstance().limpiar();
    }

    @Test
    @Order(1)
    void testRegistrarAsistenteConInsOK() {

        String nicknameTest = "gusgui01";
        String correoTest   = "gustavoguimerans01@gmail.com";
        String nombreTest   = "Gustavo";
        String apellidoTest = "Guimerans";
        LocalDate fNacTest  = LocalDate.parse("12/03/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        // Institución con imagen
        Institucion insTest = new Institucion("ins1", "www.ins.com", "institucion1", "ins1.png");

        try {
            controladorUsu.altaAsistente(
                nicknameTest, correoTest, nombreTest, apellidoTest,
                fNacTest, insTest, "1234", "asist01.png"
            );
            Asistente asi = controladorUsu.getAsistente(nicknameTest);

            assertEquals(nicknameTest, asi.getNickname());
            assertEquals(correoTest,   asi.getCorreo());
            assertEquals(nombreTest,   asi.getNombre());
            assertEquals(apellidoTest, asi.getApellido());
            assertEquals(fNacTest,     asi.getfNacimiento());
            assertEquals(insTest,      asi.getInstitucion());

        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testRegistrarAsistenteSinInsOK() {

        String nicknameTest = "gusgui02";
        String correoTest   = "gustavoguimerans02@gmail.com";
        String nombreTest   = "Gustavo";
        String apellidoTest = "Guimerans";
        LocalDate fNacTest  = LocalDate.parse("12/03/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        try {
            controladorUsu.altaAsistente(
                nicknameTest, correoTest, nombreTest, apellidoTest,
                fNacTest, null, "1234", "asist02.png"
            );
            Asistente asi = controladorUsu.getAsistente(nicknameTest);

            assertEquals(nicknameTest, asi.getNickname());
            assertEquals(correoTest,   asi.getCorreo());
            assertEquals(nombreTest,   asi.getNombre());
            assertEquals(apellidoTest, asi.getApellido());
            assertEquals(fNacTest,     asi.getfNacimiento());
            assertNull(asi.getInstitucion());

        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(3)
    void testRegistrarOrganizadorConUrlOK() {

        String nicknameTest = "gusgui03";
        String correoTest   = "gustavoguimerans03@gmail.com";
        String nombreTest   = "Gustavo";
        String descTest     = "descripcion";
        String urlTest      = "www.url.com";

        try {
            controladorUsu.altaOrganizador(
                nicknameTest, correoTest, nombreTest, descTest, urlTest, "1234", "org03.png"
            );
            Organizador org = controladorUsu.getOrganizador(nicknameTest);

            assertEquals(nicknameTest, org.getNickname());
            assertEquals(correoTest,   org.getCorreo());
            assertEquals(nombreTest,   org.getNombre());
            assertEquals(descTest,     org.getDescripcion());
            assertEquals(urlTest,      org.getUrl());

        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(4)
    void testRegistrarOrganizadorSinUrlOK() {

        String nicknameTest = "gusgui04";
        String correoTest   = "gustavoguimerans04@gmail.com";
        String nombreTest   = "Gustavo";
        String descTest     = "descripcion";

        try {
            controladorUsu.altaOrganizador(
                nicknameTest, correoTest, nombreTest, descTest, null, "1234", "org04.png"
            );
            Organizador org = controladorUsu.getOrganizador(nicknameTest);

            assertEquals(nicknameTest, org.getNickname());
            assertEquals(correoTest,   org.getCorreo());
            assertEquals(nombreTest,   org.getNombre());
            assertEquals(descTest,     org.getDescripcion());
            assertNull(org.getUrl());

        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(6)
    void testRegistrarOrganizadorRep() {

        String nicknameTest = "gusgui05";
        String correoTest   = "gustavoguimerans05@gmail.com";
        String nombreTest   = "Gustavo";
        String descTest     = "descripcion";
        String urlTest      = "www.url.com";

        try {
            controladorUsu.altaOrganizador(
                nicknameTest, correoTest, nombreTest, descTest, urlTest, "1234", "org05.png"
            );
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }

        assertThrows(UsuarioRepetidoException.class, () ->
            controladorUsu.altaOrganizador(
                nicknameTest, correoTest, nombreTest, descTest, urlTest, "1234", "org05.png"
            )
        );
    }

    @Test
    @Order(7)
    void testRegistrarAsistenteRep() {

        String nicknameTest = "gusgui06";
        String correoTest   = "gustavoguimerans06@gmail.com";
        String nombreTest   = "Gustavo";
        String apellidoTest = "Guimerans";
        LocalDate fNacTest  = LocalDate.parse("12/03/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Institucion insTest = new Institucion("ins1", "www.ins.com", "institucion1", "ins1.png");

        try {
            controladorUsu.altaAsistente(
                nicknameTest, correoTest, nombreTest, apellidoTest,
                fNacTest, insTest, "1234", "asist06.png"
            );
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }

        assertThrows(UsuarioRepetidoException.class, () ->
            controladorUsu.altaAsistente(
                nicknameTest, correoTest, nombreTest, apellidoTest,
                fNacTest, insTest, "1234", "asist06.png"
            )
        );
    }

    @Test
    @Order(8)
    void testGetUsuarios() {
        Institucion ins = new Institucion("insTest", "www.test.com", "Institucion Test", "insTest.png");
        try {
            controladorUsu.altaAsistente(
                "asist01", "asist01@test.com", "Ana", "Perez",
                LocalDate.parse("01/01/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                ins, "1234", "ana.png"
            );
            controladorUsu.altaOrganizador(
                "org01", "org01@test.com", "Carlos", "Desc Organizador",
                null, "1234", "carlos.png"
            );
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }

        List<DTUsuarioLista> listaUsuarios = controladorUsu.getUsuarios();
        assertEquals(2, listaUsuarios.size(), "Debería haber 2 usuarios en la lista");

        boolean foundAsistente = false;
        boolean foundOrganizador = false;

        for (DTUsuarioLista dt : listaUsuarios) {
            assertNotNull(dt);
            if (dt.getNickname().equals("asist01")) {
                foundAsistente = true;
                assertEquals("Ana", dt.getNombre());
                assertEquals(0, dt.getEdiciones().size(), "El asistente no debería tener ediciones");
            } else if (dt.getNickname().equals("org01")) {
                foundOrganizador = true;
                assertEquals("Carlos", dt.getNombre());
                assertEquals(0, dt.getRegistros().size(), "El organizador no debería tener registros");
            }
        }

        assertTrue(foundAsistente, "No se encontró el Asistente en la lista");
        assertTrue(foundOrganizador, "No se encontró el Organizador en la lista");
    }

    @Test
    @Order(9)
    void testGetAsistentes() {
        Institucion ins = new Institucion("insTest2", "www.test2.com", "Institucion Test2", "insTest2.png");
        try {
            controladorUsu.altaAsistente(
                "asist02", "asist02@test.com", "Luis", "Gomez",
                LocalDate.parse("02/02/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                ins, "1234", "luis.png"
            );
            controladorUsu.altaAsistente(
                "asist03", "asist03@test.com", "Marta", "Lopez",
                LocalDate.parse("03/03/2003", DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                null, "1234", "marta.png"
            );
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }

        List<Asistente> asistentes = controladorUsu.getAsistentes();
        assertEquals(2, asistentes.size(), "Debería haber 2 asistentes en la lista");

        boolean foundLuis = false;
        boolean foundMarta = false;

        for (Asistente a : asistentes) {
            if (a.getNickname().equals("asist02")) {
                foundLuis = true;
                assertEquals("Luis", a.getNombre());
                assertNotNull(a.getInstitucion());
            } else if (a.getNickname().equals("asist03")) {
                foundMarta = true;
                assertEquals("Marta", a.getNombre());
                assertNull(a.getInstitucion());
            }
        }

        assertTrue(foundLuis, "No se encontró el Asistente Luis");
        assertTrue(foundMarta, "No se encontró la Asistente Marta");
    }

    @Test
    @Order(10)
    void testRegistrosFechasEnTipoRegistro() {
        TipoRegistro tipo = new TipoRegistro("General", "Acceso general", 100f, 50, null);

        Asistente asistente = new Asistente(
            "nickTest", "correo@test.com", "Nombre", "Apellido",
            LocalDate.of(1999, 5, 5), null, "avatar.png"
        );

        String hoy = LocalDate.now().toString();

        Registro re1 = new Registro(100f, null, asistente, tipo);
        Registro re2 = new Registro(200f, null, asistente, tipo);

        tipo.getRegistros().add(re1);
        tipo.getRegistros().add(re2);

        List<String> fechas = tipo.registrosFechas();

        assertEquals(2, fechas.size(), "Debe devolver 2 fechas");
        assertTrue(fechas.stream().allMatch(f -> f.equals(hoy)),
                "Todas las fechas deben ser iguales a la fecha de hoy: " + hoy);
    }
}