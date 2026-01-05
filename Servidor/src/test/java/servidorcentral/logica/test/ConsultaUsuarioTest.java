package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import servidorcentral.logica.Asistente;
import servidorcentral.logica.ControllerUsuario;
import servidorcentral.logica.DTUsuarioListaConsulta;
import servidorcentral.logica.Edicion;
import servidorcentral.logica.Evento;
import servidorcentral.logica.ManejadorEvento;
import servidorcentral.logica.ManejadorUsuario;
import servidorcentral.logica.Organizador;
import servidorcentral.logica.Registro;
import servidorcentral.logica.TipoRegistro;

class ConsultaUsuarioTest {

    private ManejadorUsuario manejadorUsuario;
    private ControllerUsuario controllerUsuario;
    private Organizador org1;
    private Asistente asist1;
    private Evento evento1;
    private Edicion edicion1;

    @BeforeEach
    void setUp() throws Exception {
        manejadorUsuario = ManejadorUsuario.getInstance();
        controllerUsuario = new ControllerUsuario();

        // Organizador (constructor con imagen; sin URL)
        org1 = new Organizador(
            "org90",
            "org1@mail.com",
            "Org Principal",
            "Organizador Principal",
            "1234",
            "org.png"
        );
        manejadorUsuario.agregarUsuario(org1);

        // Asistente (constructor con imagen)
        asist1 = new Asistente(
            "asist1",
            "asist1@mail.com",
            "Asistente 1",
            "Apellido",
            LocalDate.of(2000, 5, 1),
            "1234",
            "asis.png"
        );
        manejadorUsuario.agregarUsuario(asist1);

        // Evento (constructor con imagen)
        evento1 = new Evento(
            "ConferenciaX",
            "C-2025",
            "Evento de prueba",
            LocalDate.now(),
            new ArrayList<>(),
            "evento.png"
        );
        ManejadorEvento.getInstancia().agregarEvento(evento1);

        edicion1 = new Edicion(
            "Edicion2025",
            "ED2025",
            LocalDate.of(2025, 5, 1),
            LocalDate.of(2025, 5, 10),
            LocalDate.of(2024, 5, 10),
            "Montevideo",
            "Uruguay",
            evento1
        );

        edicion1.getOrganizadores().add(org1);
        org1.agregarEdicionOrg(edicion1);
        evento1.agregarEdicion(edicion1);

        TipoRegistro tre = new TipoRegistro("General", "Entrada general", 100f, 100, edicion1);
        edicion1.agregarTipoRegistro(tre);

        Registro reg = new Registro(tre.getCosto(), edicion1, asist1, tre);
        edicion1.addLinkRegistro(reg);
        tre.addLinkRegistro(reg);
        asist1.addRegistro(reg);
    }

    @Test
    void testConsultaOrganizador() {
        DTUsuarioListaConsulta dtu = controllerUsuario.consultaDeUsuario("org90");
        assertNotNull(dtu);
        assertEquals("org90", dtu.getNickname());
        assertEquals("org1@mail.com", dtu.getCorreo());
        assertEquals("Org Principal", dtu.getNombre());
        assertEquals("Organizador Principal", dtu.getDescripcion());
        assertEquals(1, dtu.getEdiciones().size());
        assertEquals("Edicion2025", dtu.getEdiciones().get(0).getNombre());

        assertNull(dtu.getApellido());
        assertNull(dtu.getFNacimiento());
        assertNull(dtu.getRegistros());
    }

    @Test
    void testConsultaAsistente() {
        DTUsuarioListaConsulta dtu = controllerUsuario.consultaDeUsuario("asist1");
        assertNotNull(dtu);
        assertEquals("asist1", dtu.getNickname());
        assertEquals("asist1@mail.com", dtu.getCorreo());
        assertEquals("Asistente 1", dtu.getNombre());
        assertEquals("Apellido", dtu.getApellido());
        assertEquals(LocalDate.of(2000, 5, 1), dtu.getFNacimiento());
        assertEquals(1, dtu.getRegistros().size());
        assertEquals("Edicion2025", dtu.getRegistros().get(0).getEdicion().getNombre());
        assertEquals(1, dtu.getEdiciones().size());
        assertEquals("Edicion2025", dtu.getEdiciones().get(0).getNombre());

        assertNull(dtu.getDescripcion());
        assertNull(dtu.getUrl());
    }

    @Test
    void testUsuarioInexistente() {
        DTUsuarioListaConsulta dtu = controllerUsuario.consultaDeUsuario("usuarioInexistente");
        assertNull(dtu);
    }
}
