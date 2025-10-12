package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ServidorCentral.logica.ControllerEvento;
import ServidorCentral.logica.DTEdicion;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.ManejadorEvento;
import ServidorCentral.logica.Organizador;

class ConsultaEdicionEventoTest {

    private IControllerEvento controller;
    private Evento eventoBase;
    private Organizador organizador;

    private String nombreEvento;
    private String nombreEdicion;
    private String siglaEdicion;

    @BeforeEach
    void setUp() throws Exception {
        controller = new ControllerEvento();

        String suffix = UUID.randomUUID().toString().substring(0, 8);
        nombreEvento   = "ConferenciaX_" + suffix;
        nombreEdicion  = "Edicion2025_"   + suffix;
        siglaEdicion   = "ED2025_"        + suffix;

        // Evento con imagen (tu constructor actual)
        eventoBase = new Evento(
                nombreEvento,
                "C-2025-" + suffix,
                "Evento de prueba",
                LocalDate.now(),
                new ArrayList<>(),
                "evento.png"
        );
        ManejadorEvento.getInstancia().agregarEvento(eventoBase);

        // Organizador con (nickname, correo, nombre, descripcion, contrasena, img)
        organizador = new Organizador(
                "org1_" + suffix,
                "org1_" + suffix + "@mail.com",
                "Org Principal " + suffix,
                "Organizador Principal",
                "1234",
                "org.png"
        );

        controller.altaEdicionDeEvento(
                nombreEdicion,
                siglaEdicion,
                "Montevideo",
                "Uruguay",
                LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 10),
                LocalDate.of(2024, 10, 1),
                eventoBase,
                organizador,
                "LugarX" // imagenWebPath
        );
    }

    @Test
    void testConsultaValida() {
        DTEdicion dt = controller.consultaEdicionDeEvento(nombreEvento, nombreEdicion);
        assertNotNull(dt);
        assertEquals(nombreEdicion, dt.getNombre());
        assertEquals(siglaEdicion, dt.getSigla());
        assertEquals("Montevideo", dt.getCiudad());
        assertEquals("Uruguay", dt.getPais());
        assertEquals("LugarX", dt.getImagenWebPath());
    }

    @Test
    void testEventoInexistente() {
        DTEdicion dt = controller.consultaEdicionDeEvento("EventoInexistente_" + UUID.randomUUID(), nombreEdicion);
        assertNull(dt);
    }

    @Test
    void testEdicionInexistente() {
        DTEdicion dt = controller.consultaEdicionDeEvento(nombreEvento, "EdicionInexistente_" + UUID.randomUUID());
        assertNull(dt);
    }

    @Test
    void testAltaEdicionConFechasInvalidasDebeFallar() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> controller.altaEdicionDeEvento(
                        "EdicionBadDates_" + UUID.randomUUID(),
                        "BAD_" + UUID.randomUUID().toString().substring(0, 4),
                        "Mvd",
                        "Uy",
                        LocalDate.of(2025, 5, 10), // inicio
                        LocalDate.of(2025, 5, 1),  // fin antes de inicio
                        LocalDate.of(2024, 10, 1),
                        eventoBase,
                        organizador,
                        "imgPath"
                )
        );
        assertTrue(ex.getMessage().toLowerCase().contains("fin"));
    }

    @Test
    void testAltaEdicionDuplicadaPorNombreOSiglaDebeFallar() throws Exception {
        // Duplicado por nombre
        Exception exNombre = assertThrows(
                Exception.class,
                () -> controller.altaEdicionDeEvento(
                        nombreEdicion, // mismo nombre
                        "SIGLA_NUEVA_" + UUID.randomUUID().toString().substring(0, 4),
                        "Montevideo",
                        "Uruguay",
                        LocalDate.of(2025, 6, 1),
                        LocalDate.of(2025, 6, 5),
                        LocalDate.of(2024, 11, 1),
                        eventoBase,
                        organizador,
                        "otraImg"
                )
        );
        assertTrue(exNombre.getMessage().toLowerCase().contains("ya existe"));

        // Duplicado por sigla
        Exception exSigla = assertThrows(
                Exception.class,
                () -> controller.altaEdicionDeEvento(
                        "NombreNuevo_" + UUID.randomUUID().toString().substring(0, 4),
                        siglaEdicion, // misma sigla
                        "Montevideo",
                        "Uruguay",
                        LocalDate.of(2025, 7, 1),
                        LocalDate.of(2025, 7, 5),
                        LocalDate.of(2024, 12, 1),
                        eventoBase,
                        organizador,
                        "otraImg2"
                )
        );
        assertTrue(exSigla.getMessage().toLowerCase().contains("ya existe"));
    }
}

