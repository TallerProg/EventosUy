package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import servidorcentral.logica.ControllerEvento;
import servidorcentral.logica.DTCategoria;
import servidorcentral.logica.DTInstitucion;
import servidorcentral.logica.Edicion;
import servidorcentral.logica.EstadoEdicion;
import servidorcentral.logica.Evento;
import servidorcentral.logica.IControllerEvento;
import servidorcentral.logica.ManejadorEvento;
import servidorcentral.logica.Organizador;

/**
 * Tests unificados:
 *  - DTInstitucion (constructor + getters, vacíos y null)
 *  - DTCategoria   (constructor + getter, vacío y null)
 *  - aceptarRechazarEdicion(...) (aceptar, rechazar, inexistente)
 *
 * Ajustá los comentarios indicados si tu diseño prohíbe valores null en DTOs.
 */
public class TestGeneralDTTest {

    private IControllerEvento controller;
    private Evento eventoBase;
    private Organizador organizador;

    @BeforeEach
    void setUp() {
        controller = new ControllerEvento();

        eventoBase = new Evento(
            "ConferenciaX",
            "C-2025",
            "Evento de prueba",
            LocalDate.now(),
            new ArrayList<>(),
            "evento.png"
        );

        // Organizador base
        organizador = new Organizador(
            "org1",
            "org1@mail.com",
            "Org Principal",
            "Organizador Principal",
            "1234",
            "org.png"
        );
    }


    @Test
    @DisplayName("DTInstitucion: constructor y getters")
    void dtInstitucion_constructorYGetters() {
        DTInstitucion dt = new DTInstitucion(
            "Fing-Udelar",
            "Facultad de Ingeniería",
            "https://fing.edu.uy",
            "fing.png"
        );

        assertEquals("Fing-Udelar", dt.getNombre());
        assertEquals("Facultad de Ingeniería", dt.getDescripcion());
        assertEquals("https://fing.edu.uy", dt.getUrl());
        assertEquals("fing.png", dt.getImagen());
    }

    @Test
    @DisplayName("DTInstitucion: strings vacíos no rompen")
    void dtInstitucion_cadenasVacias() {
        assertDoesNotThrow(() -> {
            DTInstitucion dt = new DTInstitucion("", "", "", "");
            assertEquals("", dt.getNombre());
            assertEquals("", dt.getDescripcion());
            assertEquals("", dt.getUrl());
            assertEquals("", dt.getImagen());
        });
    }

    @Test
    @DisplayName("DTInstitucion: nulls aceptados (ajustar si tu diseño los prohíbe)")
    void dtInstitucion_nullsPermitidos() {
        DTInstitucion dt = new DTInstitucion(null, null, null, null);
        assertNull(dt.getNombre());
        assertNull(dt.getDescripcion());
        assertNull(dt.getUrl());
        assertNull(dt.getImagen());
    }

    // ------------------------------ DTCategoria ------------------------------

    @Test
    @DisplayName("DTCategoria: constructor y getter")
    void dtCategoria_constructorYGetter() {
        DTCategoria dt = new DTCategoria("Tecnología");
        assertEquals("Tecnología", dt.getNombre());
    }

    @Test
    @DisplayName("DTCategoria: cadena vacía")
    void dtCategoria_vacio() {
        DTCategoria dt = new DTCategoria("");
        assertEquals("", dt.getNombre());
    }

    @Test
    @DisplayName("DTCategoria: null aceptado (ajustar si tu diseño lo prohíbe)")
    void dtCategoria_nullPermitido() {
        DTCategoria dt = new DTCategoria(null);
        assertNull(dt.getNombre());
    }


    @Test
    @DisplayName("Aceptar edición -> estado Aceptada")
    void aceptarCambiaEstadoAAceptada() throws Exception {
        String nombreEd = "Edicion2025-Aceptar";

        controller.altaEdicionDeEvento(
            nombreEd,
            "ED-A1",
            "Montevideo",
            "Uruguay",
            LocalDate.of(2025, 5, 1),
            LocalDate.of(2025, 5, 10),
            LocalDate.of(2025, 4, 20),
            eventoBase,
            organizador,
            "ruta/img1.png"
        );

        assertDoesNotThrow(() -> {
            try {
                controller.aceptarRechazarEdicion(nombreEd, true);
            } catch (Exception e) {
                fail(e);
            }
        });

        ManejadorEvento mev = ManejadorEvento.getInstancia();
        Edicion edi = mev.findEdicion(nombreEd);
        assertNotNull(edi, "La edición debería existir luego del alta");
        assertEquals(EstadoEdicion.Aceptada, edi.getEstado(), "La edición debería quedar Aceptada");
    }

    @Test
    @DisplayName("Rechazar edición -> estado Rechazada")
    void rechazarCambiaEstadoARechazada() throws Exception {
        String nombreEd = "Edicion2025-Rechazar";

        controller.altaEdicionDeEvento(
            nombreEd,
            "ED-R1",
            "Montevideo",
            "Uruguay",
            LocalDate.of(2025, 6, 1),
            LocalDate.of(2025, 6, 10),
            LocalDate.of(2025, 5, 20),
            eventoBase,
            organizador,
            "ruta/img2.png"
        );

        assertDoesNotThrow(() -> {
            try {
                controller.aceptarRechazarEdicion(nombreEd, false);
            } catch (Exception e) {
                fail(e);
            }
        });

        ManejadorEvento mev = ManejadorEvento.getInstancia();
        Edicion edi = mev.findEdicion(nombreEd);
        assertNotNull(edi);
        assertEquals(EstadoEdicion.Rechazada, edi.getEstado(), "La edición debería quedar Rechazada");
    }

    @Test
    @DisplayName("aceptarRechazarEdicion lanza excepción si la edición no existe")
    void lanzarExcepcionSiEdicionNoExiste() {
        Exception ex = assertThrows(Exception.class, () -> {
            controller.aceptarRechazarEdicion("NO_EXISTE", true);
        });
        assertTrue(ex.getMessage().toLowerCase().contains("no existe"));
    }
}
