package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ServidorCentral.logica.ControllerEvento;
import ServidorCentral.logica.DTPatrocinio;
import ServidorCentral.logica.ETipoNivel;
import ServidorCentral.logica.Edicion;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.Institucion;
import ServidorCentral.logica.ManejadorEvento;
import ServidorCentral.logica.ManejadorInstitucion;
import ServidorCentral.logica.Patrocinio;
import ServidorCentral.logica.TipoRegistro;

class PatrocinioGeneralTest {
    private ControllerEvento controller;
    private Evento evento;
    private Edicion edicion;
    private Institucion institucion;
    private TipoRegistro tipoRegistro;

    @BeforeEach
    void setUp() {
        controller = new ControllerEvento();

        ManejadorEvento.getInstancia().limpiar();
        ManejadorInstitucion.getInstance().limpiar();

        evento = new Evento("EventoTest", "EVT", "desc", LocalDate.now(), new ArrayList<>());
        edicion = new Edicion("EdicionTest", "EDT", LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 10), LocalDate.of(2024, 5, 10), "Montevideo", "UY", evento);
        tipoRegistro = new TipoRegistro("General", "Acceso general", 100f, 50, edicion);
        edicion.agregarTipoRegistro(tipoRegistro);
        evento.agregarEdicion(edicion);

        institucion = new Institucion("InstitutoTest", "http://inst.test", "desc inst");

        ManejadorEvento.getInstancia().agregarEvento(evento);
        ManejadorEvento.getInstancia().agregarEdicion(edicion);
        ManejadorInstitucion.getInstance().agregarInstitucion(institucion);
    }

    @AfterEach
    void tearDown() {
        ManejadorEvento.getInstancia().limpiar();
        ManejadorInstitucion.getInstance().limpiar();
    }

    @Test
    void testAltaPatrocinioExitoso() throws Exception {
        controller.altaPatrocinio("PAT001", LocalDate.now(), 2, 1000f, ETipoNivel.Oro,
                institucion.getNombre(), edicion.getNombre(), tipoRegistro.getNombre());

        assertEquals(1, edicion.getPatrocinios().size());
        assertEquals(1, institucion.getPatrocinios().size());

        Patrocinio p = edicion.getPatrocinios().get(0);
        assertEquals("PAT001", p.getCodigo());
        assertEquals(ETipoNivel.Oro, p.getNivel());
    }

    @Test
    void testAltaPatrocinioEdicionNoExiste() {
        Exception ex = assertThrows(Exception.class, () -> {
            controller.altaPatrocinio("PAT002", LocalDate.now(), 1, 500f, ETipoNivel.Plata,
                    institucion.getNombre(), "EdicionFake", tipoRegistro.getNombre());
        });
        assertTrue(ex.getMessage().toLowerCase().contains("no existe"));
    }

    @Test
    void testAltaPatrocinioInstitucionNoExiste() {
        Exception ex = assertThrows(Exception.class, () -> {
            controller.altaPatrocinio("PAT003", LocalDate.now(), 1, 500f, ETipoNivel.Plata,
                    "InstitucionFake", edicion.getNombre(), tipoRegistro.getNombre());
        });
        assertTrue(ex.getMessage().toLowerCase().contains("no existe"));
    }

    @Test
    void testAltaPatrocinioTipoRegistroNoExiste() {
        Exception ex = assertThrows(Exception.class, () -> {
            controller.altaPatrocinio("PAT004", LocalDate.now(), 1, 500f, ETipoNivel.Plata,
                    institucion.getNombre(), edicion.getNombre(), "TRFake");
        });
        assertTrue(ex.getMessage().toLowerCase().contains("no existe"));
    }

    @Test
    void testAltaPatrocinioDuplicadoInstitucion() throws Exception {
        controller.altaPatrocinio("PAT005", LocalDate.now(), 1, 1000f, ETipoNivel.Oro,
                institucion.getNombre(), edicion.getNombre(), tipoRegistro.getNombre());

        Exception ex = assertThrows(Exception.class, () -> {
            controller.altaPatrocinio("PAT006", LocalDate.now(), 1, 1500f, ETipoNivel.Bronce,
                    institucion.getNombre(), edicion.getNombre(), tipoRegistro.getNombre());
        });
        assertTrue(ex.getMessage().toLowerCase().contains("ya existe un patrocinio"));
    }

    @Test
    void testAltaPatrocinioRegistrosGratisExceden20Porciento() {
        Exception ex = assertThrows(Exception.class, () -> {
            controller.altaPatrocinio("PAT007", LocalDate.now(), 30, 1000f, ETipoNivel.Oro,
                    institucion.getNombre(), edicion.getNombre(), tipoRegistro.getNombre());
        });
        assertTrue(ex.getMessage().toLowerCase().contains("20%"));
    }

    @Test
    void testConsultaPatrocinioExistente() throws Exception {
        controller.altaPatrocinio("PAT008", LocalDate.now(), 1, 1000f, ETipoNivel.Plata,
                institucion.getNombre(), edicion.getNombre(), tipoRegistro.getNombre());

        DTPatrocinio dt = controller.consultaPatrocinio(edicion.getNombre(), "PAT008");
        assertNotNull(dt);
        assertEquals("PAT008", dt.getCodigo());
        assertEquals(ETipoNivel.Plata, dt.getNivel());
    }

    @Test
    void testConsultaPatrocinioInexistente() {
        DTPatrocinio dt = controller.consultaPatrocinio(edicion.getNombre(), "NO_EXISTE");
        assertNull(dt);
    }
}

