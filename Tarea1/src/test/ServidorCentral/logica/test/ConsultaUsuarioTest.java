package test.ServidorCentral.logica.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.ServidorCentral.logica.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaUsuarioTest {

    private ManejadorUsuario manejadorUsuario;
    private ControllerUsuario controllerUsuario;
    private Organizador org1;
    private Asistente asist1;
    private Evento evento1;
    private Edicion edicion1;

    @BeforeEach
    void setUp() throws Exception {
        manejadorUsuario = ManejadorUsuario.getinstance();
        controllerUsuario = new ControllerUsuario();

        org1 = new Organizador("org90", "org1@mail.com", "Org Principal", "Organizador Principal","1234");
        manejadorUsuario.agregarUsuario(org1);

        asist1 = new Asistente("asist1", "asist1@mail.com", "Asistente 1", "Apellido",
                LocalDate.of(2000, 5, 1),"1234");
        manejadorUsuario.agregarUsuario(asist1);

        evento1 = new Evento("ConferenciaX", "C-2025", "Evento de prueba",
                LocalDate.now(), new ArrayList<>());
        ManejadorEvento.getInstancia().agregarEvento(evento1);

        edicion1 = new Edicion("Edicion2025", "ED2025",
                LocalDate.of(2025,5,1), LocalDate.of(2025,5,10),LocalDate.of(2024,5,10),
                "Montevideo","Uruguay", evento1);

        edicion1.getOrganizadores().add(org1);
        org1.agregarEdicionOrg(edicion1);

        evento1.agregarEdicion(edicion1);

        TipoRegistro tr = new TipoRegistro("General", "Entrada general", 100f, 100, edicion1);
        edicion1.agregarTipoRegistro(tr);

        Registro reg = new Registro(tr.getCosto(), edicion1, asist1, tr);
        edicion1.addLinkRegistro(reg);
        tr.addLinkRegistro(reg);
        asist1.addRegistro(reg);
    }

    @Test
    void testConsultaOrganizador() {
        DTUsuarioListaConsulta dt = controllerUsuario.ConsultaDeUsuario("org90");
        assertNotNull(dt);
        assertEquals("org90", dt.getNickname());
        assertEquals("org1@mail.com", dt.getCorreo());
        assertEquals("Org Principal", dt.getNombre());
        assertEquals("Organizador Principal", dt.getDescripcion());
        assertEquals(1, dt.getEdiciones().size());
        assertEquals("Edicion2025", dt.getEdiciones().get(0).getNombre());

        assertNull(dt.getApellido());
        assertNull(dt.getFNacimiento());
        assertNull(dt.getRegistros());
    }

    @Test
    void testConsultaAsistente() {
        DTUsuarioListaConsulta dt = controllerUsuario.ConsultaDeUsuario("asist1");
        assertNotNull(dt);
        assertEquals("asist1", dt.getNickname());
        assertEquals("asist1@mail.com", dt.getCorreo());
        assertEquals("Asistente 1", dt.getNombre());
        assertEquals("Apellido", dt.getApellido());
        assertEquals(LocalDate.of(2000,5,1), dt.getFNacimiento());
        assertEquals(1, dt.getRegistros().size());
        assertEquals("Edicion2025", dt.getRegistros().get(0).getEdicion().getNombre());
        assertEquals(1, dt.getEdiciones().size());
        assertEquals("Edicion2025", dt.getEdiciones().get(0).getNombre());

        assertNull(dt.getDescripcion());
        assertNull(dt.getUrl());
    }

    @Test
    void testUsuarioInexistente() {
        DTUsuarioListaConsulta dt = controllerUsuario.ConsultaDeUsuario("usuarioInexistente");
        assertNull(dt);
    }
}
