package test.ServidorCentral.logica.test;

import org.junit.jupiter.api.*;
import src.ServidorCentral.logica.*;
import src.ServidorCentral.excepciones.*;

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
                null,
                "1234"
        );
        asistente = controller.getAsistente("asist1");

        controller.AltaOrganizador(
                "org1",
                "org1@mail.com",
                "Org Principal",
                "Descripcion inicial",
                "www.url.com",
                "1234"
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
        Asistente actualizado = new Asistente(
                "asist1",                    
                "asist1_mod@mail.com",        
                "JuanMod",                    
                "PerezMod",                   
                LocalDate.of(2002, 3, 3),
                 "12345"
        );

        controller.modificarUsuario1(actualizado);

        Asistente modificado = controller.getAsistente("asist1");
        assertEquals("JuanMod", modificado.getNombre());
        assertEquals("PerezMod", modificado.getApellido());
        assertEquals(LocalDate.of(2002, 3, 3), modificado.getfNacimiento());
        assertEquals("asist1_mod@mail.com", modificado.getCorreo());
    }

    @Test
    void testModificarUsuario1_Organizador() {
        Organizador actualizado = new Organizador(
                "org1",                       
                "org1_mod@mail.com",          
                "OrgMod",                    
                "Nueva descripcion",          
                "www.nuevaurl.com"            
        );

        controller.modificarUsuario1(actualizado);

        Organizador modificado = controller.getOrganizador("org1");
        assertEquals("OrgMod", modificado.getNombre());
        assertEquals("Nueva descripcion", modificado.getDescripcion());
        assertEquals("www.nuevaurl.com", modificado.getUrl());
        assertEquals("org1_mod@mail.com", modificado.getCorreo());
    }

}
