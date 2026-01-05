package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import servidorcentral.excepciones.UsuarioNoExisteException;
import servidorcentral.excepciones.UsuarioRepetidoException;
import servidorcentral.logica.Asistente;
import servidorcentral.logica.ControllerUsuario;
import servidorcentral.logica.Institucion;
import servidorcentral.logica.ManejadorUsuario;
import servidorcentral.logica.Organizador;

class ModificarUsuarioTest {

    private ControllerUsuario controller;
    private Asistente asistente;
    private Organizador organizador;
    private Institucion institucion;

    @BeforeEach
    void setUp() throws UsuarioRepetidoException {
        controller = new ControllerUsuario();

        ManejadorUsuario.getInstance().limpiar();


        institucion = new Institucion("insTest", "www.ins.com", "Institución Test", "ins.png");


        controller.altaAsistente(
                "asist1",
                "asist1@mail.com",
                "Juan",
                "Perez",
                LocalDate.of(2000, 1, 1),
                institucion,
                "1234",
                "asist1.png"
        );
        asistente = controller.getAsistente("asist1");
        Asistente asi=asistente;


        controller.altaOrganizador(
                "org1",
                "org1@mail.com",
                "Org Principal",
                "Descripcion inicial",
                "www.url.com",
                "1234",
                "org1.png"
        );
        organizador = controller.getOrganizador("org1");
        Organizador org=organizador;
    }

    @Test
    void testModificarAsistenteValido() throws UsuarioNoExisteException {
        controller.modificarUsuario(
                "asist1",
                "Juan Modificado",
                "Perez Modificado",
                LocalDate.of(2001, 2, 2),
                null,      
                null       
        );

        Asistente modificado = controller.getAsistente("asist1");
        assertEquals("Juan Modificado", modificado.getNombre());
        assertEquals("Perez Modificado", modificado.getApellido());
        assertEquals(LocalDate.of(2001, 2, 2), modificado.getfNacimiento());
        assertEquals("asist1@mail.com", modificado.getCorreo());
    }

    @Test
    void testModificarOrganizadorValido() throws UsuarioNoExisteException {
        controller.modificarUsuario(
                "org1",
                "Org Modificado",
                null,      // apellido (no aplica a Org)
                null,      // fecha nac (no aplica a Org)
                "Nueva descripcion",
                "www.nuevaurl.com"
        );

        Organizador modificado = controller.getOrganizador("org1");
        assertEquals("Org Modificado", modificado.getNombre());
        assertEquals("Nueva descripcion", modificado.getDescripcion());
        assertEquals("www.nuevaurl.com", modificado.getUrl());
        assertEquals("org1@mail.com", modificado.getCorreo());
    }

    @Test
    void testModificarUsuarioInexistente() {
        assertThrows(UsuarioNoExisteException.class, () -> {
            controller.modificarUsuario(
                    "usuarioInexistente",
                    "Nombre",
                    "Apellido",
                    LocalDate.of(2000, 1, 1),
                    "Descripcion",
                    "url.com"
            );
        });
    }

    @Test
    void testModificarUsuario1Asistente() {
        // Constructor nuevo de Asistente: incluye Institucion + imagen
        Asistente actualizado = new Asistente(
                "asist1",
                "asist1_mod@mail.com",
                "JuanMod",
                "PerezMod",
                LocalDate.of(2002, 3, 3),
                "asist1_mod.png", null
        );

        controller.modificarUsuario1(actualizado);

        Asistente modificado = controller.getAsistente("asist1");
        assertEquals("JuanMod", modificado.getNombre());
        assertEquals("PerezMod", modificado.getApellido());
        assertEquals(LocalDate.of(2002, 3, 3), modificado.getfNacimiento());
        assertEquals("asist1_mod@mail.com", modificado.getCorreo());
    }

    @Test
    void testModificarUsuario1Organizador() {
        // Constructor nuevo de Organizador: incluye imagen
        Organizador actualizado = new Organizador(
                "org1",
                "org1_mod@mail.com",
                "OrgMod",
                "Nueva descripcion",
                "www.nuevaurl.com",
                "org1_mod.png"
        );

        controller.modificarUsuario1(actualizado);

        Organizador modificado = controller.getOrganizador("org1");
        assertEquals("OrgMod", modificado.getNombre());
        assertEquals("Nueva descripcion", modificado.getDescripcion());
        assertEquals(null, modificado.getUrl());
        assertEquals("org1_mod@mail.com", modificado.getCorreo());
    }
}
