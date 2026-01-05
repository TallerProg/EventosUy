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
import servidorcentral.logica.DTUsuarioListaConsulta;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerUsuario;
import servidorcentral.logica.Institucion;
import servidorcentral.logica.ManejadorUsuario;
import servidorcentral.logica.Organizador;
import servidorcentral.logica.Registro;
import servidorcentral.logica.TipoRegistro;
import servidorcentral.logica.Usuario;
import servidorcentral.logica.DTAsistente;
import servidorcentral.logica.DTInstitucion;
import servidorcentral.logica.DTOrganizadorDetallado;
import servidorcentral.logica.ManejadorInstitucion;
import servidorcentral.excepciones.UsuarioNoExisteException;
import servidorcentral.excepciones.CredencialesInvalidasException;


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
    
 // =================== Tests extra: DTOs y vinculación de institución ===================

    @Test
    @Order(11)
    void testGetDTAsistente_OK_minimo() {
        String nick = "asis_dt_ok_min";
        LocalDate fnac = LocalDate.parse("05/06/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        try {
            controladorUsu.altaAsistente(
                nick, "asis_dt_ok_min@test.com", "Ana", "Lopez",
                fnac, null, "1234", "ana.png"
            );
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }

        DTAsistente dto = controladorUsu.getDTAsistente(nick);
        assertNotNull(dto, "Debe devolver un DTAsistente no nulo");
        assertEquals(nick, dto.getNickname());
        assertEquals("Ana", dto.getNombre());
        assertEquals("Lopez", dto.getApellido());
    }

    @Test
    @Order(12)
    void testGetDTOrganizadorDetallado_OK_minimo() {
        String nick = "org_dt_ok_min";
        try {
            controladorUsu.altaOrganizador(
                nick, "org_dt_ok_min@test.com", "Carlos", "Org desc",
                "https://url.org", "1234", "org.png"
            );
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }

        DTOrganizadorDetallado dto = controladorUsu.getDTOrganizadorDetallado(nick);
        assertNotNull(dto, "Debe devolver un DTOrganizadorDetallado no nulo");
        assertEquals(nick, dto.getNickname());
        assertEquals("Carlos", dto.getNombre());
    }

    @Test
    @Order(13)
    void testAneadirInstitucion_OK_minimo() {
        // Crear asistente sin institución
        String nick = "asis_vinc_ok_min";
        LocalDate fnac = LocalDate.parse("10/10/1999", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        try {
            controladorUsu.altaAsistente(
                nick, "asis_vinc_ok_min@test.com", "Lucia", "Perez",
                fnac, null, "1234", "lucia.png"
            );
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }

        // Registrar institución en el manejador (ajustá el nombre del método si difiere)
        String nombreIns = "FacultadIngenieria";
        Institucion ins = new Institucion(nombreIns, "https://fi.edu.uy", "FI", "fi.png");
        try {
            // Si en tu proyecto se llama distinto (alta/registrar/add/addInstitucion), cambialo acá.
            ManejadorInstitucion.getInstance().agregarInstitucion(ins);
        } catch (Throwable t) {
            fail("Registrá la institución usando el método real de ManejadorInstitucion (p. ej. agregar/alta/registrar).");
        }

        // Vincular
        controladorUsu.aneadirInstitucion(nick, nombreIns);

        // Verificar
        Asistente a = controladorUsu.getAsistente(nick);
        assertNotNull(a.getInstitucion(), "El asistente debe quedar con institución");
        assertEquals(nombreIns, a.getInstitucion().getNombre(), "Nombre de institución esperado");
    }
    
    @Test
    @Order(14)
    void testSetContrasena_minimo() {
        String nick = "asis_pass_min";
        LocalDate fnac = LocalDate.parse("05/06/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        try {
            controladorUsu.altaAsistente(
                nick, "asis_pass_min@test.com", "Ana", "Lopez",
                fnac, null, "1234", "ana.png"
            );
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }

        try {
            controladorUsu.setContrasena(nick, "NuevaSegura!123");
        } catch (Exception e) {
            fail("No debería lanzar excepción en el caso OK: " + e.getMessage());
        }


        // Caso inválido: contraseña vacía/blanca
        assertThrows(CredencialesInvalidasException.class,
            () -> controladorUsu.setContrasena(nick, "   ")
        );

        // Caso inexistente: usuario no existe
        assertThrows(UsuarioNoExisteException.class,
            () -> controladorUsu.setContrasena("no_existe_x", "otra123")
        );
    }

    @Test
    @Order(15)
    void testListarUsuarios_minimo() {
        // Prepara: 1 asistente + 1 organizador
        String nickAsis = "asis_list_min";
        String nickOrg  = "org_list_min";

        LocalDate fnac = LocalDate.parse("01/01/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        try {
            controladorUsu.altaAsistente(
                nickAsis, "asis_list_min@test.com", "Ana", "Perez",
                fnac, null, "1234", "ana.png"
            );
            controladorUsu.altaOrganizador(
                nickOrg, "org_list_min@test.com", "Carlos", "Desc Org",
                null, "1234", "carlos.png"
            );
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }

        // Ejecuta
        List<Usuario> usuarios = controladorUsu.listarUsuarios();

        // Verifica: hay 2 y están los dos nicks
        assertNotNull(usuarios, "La lista no debe ser nula");
        assertEquals(2, usuarios.size(), "Debe listar exactamente 2 usuarios");

        boolean tieneAsis = usuarios.stream().anyMatch(u -> u.getNickname().equals(nickAsis));
        boolean tieneOrg  = usuarios.stream().anyMatch(u -> u.getNickname().equals(nickOrg));

        assertTrue(tieneAsis, "Debe incluir al asistente creado");
        assertTrue(tieneOrg,  "Debe incluir al organizador creado");
    }

    

    @Test
    @Order(18)
    void testModificarUsuarioDT_Asistente_minimo() {
        // Arrange: crear asistente sin institución
        String nick = "asis_mod_dt_min";
        LocalDate fnac = LocalDate.parse("01/01/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        try {
            controladorUsu.altaAsistente(
                nick, "asis_mod_dt_min@test.com", "Ana", "Perez",
                fnac, null, "1234", "ana.png"
            );
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }

        // Registrar institución a asignar
        String nombreIns = "FacultadIngenieria";
        Institucion ins = new Institucion(nombreIns, "https://fi.edu.uy", "FI", "fi.png");
        try {
            // Cambiá "agregar" si tu ManejadorInstitucion usa otro nombre (alta/registrar/add/addInstitucion)
            ManejadorInstitucion.getInstance().agregarInstitucion(ins);
        } catch (Throwable t) {
            fail("Registrá la institución usando el método real de ManejadorInstitucion.");
        }

        // DTO con cambios (nombre, correo, apellido, fecha nac) y nueva institución
        DTUsuarioListaConsulta dto = new DTUsuarioListaConsulta();
        // setters mínimos esperados; si tu DTO no tiene setters, adaptá a tu constructor/builder
        dto.setNickname(nick);
        dto.setNombre("AnaMod");
        dto.setCorreo("ana.mod@test.com");
        dto.setApellido("PerezMod");
        dto.setFNacimiento(LocalDate.parse("02/02/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dto.setIns(new DTInstitucion(nombreIns, "FI", "https://fi.edu.uy", "fi.png"));

        // Act
        controladorUsu.modificarUsuarioDT(dto);

        // Assert
        Asistente a = controladorUsu.getAsistente(nick);
        assertNotNull(a, "El asistente debe existir");
        assertEquals("AnaMod", a.getNombre());
        assertEquals("ana.mod@test.com", a.getCorreo());
        assertEquals("PerezMod", a.getApellido());
        assertEquals(LocalDate.parse("02/02/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")), a.getfNacimiento());
        assertNotNull(a.getInstitucion(), "Debe haberse asignado la institución");
        assertEquals(nombreIns, a.getInstitucion().getNombre());

        // Verifica relación bidireccional
        Institucion insMgr = ManejadorInstitucion.getInstance().findInstitucion(nombreIns);
        assertNotNull(insMgr);
        assertTrue(insMgr.getAsistentes().stream().anyMatch(x -> x.getNickname().equals(nick)),
            "La institución debe contener al asistente");
    }

    @Test
    @Order(19)
    void testModificarUsuarioDT_Organizador_minimo() {
        // Arrange: crear organizador
        String nick = "org_mod_dt_min";
        try {
            controladorUsu.altaOrganizador(
                nick, "org_mod_dt_min@test.com", "Carlos", "Desc Org",
                "https://org.old", "1234", "org.png"
            );
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }

        // DTO con cambios para organizador (nombre, correo, descripcion, url)
        DTUsuarioListaConsulta dto = new DTUsuarioListaConsulta();
        dto.setNickname(nick);
        dto.setNombre("CarlosMod");
        dto.setCorreo("carlos.mod@test.com");
        dto.setDescripcion("Desc Org Mod");
        dto.setUrl("https://org.new");

        // Act
        controladorUsu.modificarUsuarioDT(dto);

        // Assert
        Organizador o = controladorUsu.getOrganizador(nick);
        assertNotNull(o, "El organizador debe existir");
        assertEquals("CarlosMod", o.getNombre());
        assertEquals("carlos.mod@test.com", o.getCorreo());
        assertEquals("Desc Org Mod", o.getDescripcion());
        assertEquals("https://org.new", o.getUrl());
    }

    
    
}