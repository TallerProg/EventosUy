package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
        DTInstitucion dti = new DTInstitucion(
            "Fing-Udelar",
            "Facultad de Ingeniería",
            "https://fing.edu.uy",
            "fing.png"
        );

        assertEquals("Fing-Udelar", dti.getNombre());
        assertEquals("Facultad de Ingeniería", dti.getDescripcion());
        assertEquals("https://fing.edu.uy", dti.getUrl());
        assertEquals("fing.png", dti.getImagen());
    }

    @Test
    @DisplayName("DTInstitucion: strings vacíos no rompen")
    void dtInstitucion_cadenasVacias() {
        assertDoesNotThrow(() -> {
            DTInstitucion dti = new DTInstitucion("", "", "", "");
            assertEquals("", dti.getNombre());
            assertEquals("", dti.getDescripcion());
            assertEquals("", dti.getUrl());
            assertEquals("", dti.getImagen());
        });
    }

    @Test
    @DisplayName("DTInstitucion: nulls aceptados (ajustar si tu diseño los prohíbe)")
    void dtInstitucion_nullsPermitidos() {
        DTInstitucion dti = new DTInstitucion(null, null, null, null);
        assertNull(dti.getNombre());
        assertNull(dti.getDescripcion());
        assertNull(dti.getUrl());
        assertNull(dti.getImagen());
    }

    // ------------------------------ DTCategoria ------------------------------

    @Test
    @DisplayName("DTCategoria: constructor y getter")
    void dtCategoria_constructorYGetter() {
        DTCategoria dti = new DTCategoria("Tecnología");
        assertEquals("Tecnología", dti.getNombre());
    }

    @Test
    @DisplayName("DTCategoria: cadena vacía")
    void dtCategoria_vacio() {
        DTCategoria dti = new DTCategoria("");
        assertEquals("", dti.getNombre());
    }

    @Test
    @DisplayName("DTCategoria: null aceptado (ajustar si tu diseño lo prohíbe)")
    void dtCategoria_nullPermitido() {
        DTCategoria dti = new DTCategoria(null);
        assertNull(dti.getNombre());
    }



    @Test
    @DisplayName("aceptarRechazarEdicion lanza excepción si la edición no existe")
    void lanzarExcepcionSiEdicionNoExiste() {
        Exception exe = assertThrows(Exception.class, () -> {
            controller.aceptarRechazarEdicion("NO_EXISTE", true);
        });
        assertTrue(exe.getMessage().toLowerCase().contains("no existe"));
    }
}
