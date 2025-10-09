package ServidorCentral.logica.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ServidorCentral.logica.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaEdicionEventoTest {

    private IControllerEvento controller;
    private Evento eventoBase;
    private Organizador organizador;

    
    @BeforeEach
    void setUp() throws Exception {
        controller = new ControllerEvento();
        
        eventoBase = new Evento("ConferenciaX", "C-2025", "Evento de prueba", LocalDate.now(), new ArrayList<>());
        ManejadorEvento.getInstancia().agregarEvento(eventoBase);

        organizador = new Organizador("org1", "org1@mail.com", "Org Principal", "Organizador Principal","1234");

        controller.altaEdicionDeEvento(
            "Edicion2025",
            "ED2025",
            "Montevideo",
            "Uruguay",
            LocalDate.of(2025, 5, 1),
            LocalDate.of(2025, 5, 10),
            LocalDate.of(2024, 10, 01),
            eventoBase,
            organizador,
            "LugarX"
        );
    }


    @Test
    void testConsultaValida() {
        DTEdicion dt = controller.consultaEdicionDeEvento("ConferenciaX", "Edicion2025");
        assertNotNull(dt);
        assertEquals("Edicion2025", dt.getNombre());
        assertEquals("ED2025", dt.getSigla());
        assertEquals("Montevideo", dt.getCiudad());
        assertEquals("Uruguay", dt.getPais());
    }

    @Test
    void testEventoInexistente() {
        DTEdicion dt = controller.consultaEdicionDeEvento("EventoInexistente", "Edicion2025");
        assertNull(dt);
    }

    @Test
    void testEdicionInexistente() {
        DTEdicion dt = controller.consultaEdicionDeEvento("ConferenciaX", "EdicionInexistente");
        assertNull(dt);
    }
}
