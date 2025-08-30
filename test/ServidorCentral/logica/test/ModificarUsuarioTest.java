package ServidorCentral.logica.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ServidorCentral.logica.*;
import ServidorCentral.excepciones.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ModificarUsuarioTest {

    private ControllerUsuario controller;
    private Asistente asistente;
    private Organizador organizador;

    @BeforeEach
    void setUp() throws UsuarioRepetidoException {
        controller = new ControllerUsuario();
        
        ManejadorUsuario.getinstance().limpiarUsuarios();

        controller.AltaAsistente(
                "asist1",
                "asist1@mail.com",
                "Juan",
                "Perez",
                LocalDate.of(2000, 1, 1),
                null
        );
        asistente = controller.getAsistente("asist1");

        controller.AltaOrganizador(
                "org1",
                "org1@mail.com",
                "Org Principal",
                "Descripcion inicial",
                "www.url.com"
        );
        organizador = controller.getOrganizador("org1");
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
                null,
                null,
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
    void testModificarUsuario1_Asistente() {
        // Crear un Asistente con cambios
        Asistente actualizado = new Asistente(
                "asist1",                     // mismo nickname
                "asist1_mod@mail.com",        // correo modificado
                "JuanMod",                    // nombre modificado
                "PerezMod",                   // apellido modificado
                LocalDate.of(2002, 3, 3)     // fecha de nacimiento modificada
        );

        // Llamar a modificarUsuario1
        controller.modificarUsuario1(actualizado);

        // Verificar que los cambios se reflejaron
        Asistente modificado = controller.getAsistente("asist1");
        assertEquals("JuanMod", modificado.getNombre());
        assertEquals("PerezMod", modificado.getApellido());
        assertEquals(LocalDate.of(2002, 3, 3), modificado.getfNacimiento());
        assertEquals("asist1_mod@mail.com", modificado.getCorreo());
    }

    @Test
    void testModificarUsuario1_Organizador() {
        // Crear un Organizador con cambios
        Organizador actualizado = new Organizador(
                "org1",                       // mismo nickname
                "org1_mod@mail.com",          // correo modificado
                "OrgMod",                     // nombre modificado
                "Nueva descripcion",          // descripción modificada
                "www.nuevaurl.com"            // url modificada
        );

        // Llamar a modificarUsuario1
        controller.modificarUsuario1(actualizado);

        // Verificar cambios
        Organizador modificado = controller.getOrganizador("org1");
        assertEquals("OrgMod", modificado.getNombre());
        assertEquals("Nueva descripcion", modificado.getDescripcion());
        assertEquals("www.nuevaurl.com", modificado.getUrl());
        assertEquals("org1_mod@mail.com", modificado.getCorreo());
    }

}
