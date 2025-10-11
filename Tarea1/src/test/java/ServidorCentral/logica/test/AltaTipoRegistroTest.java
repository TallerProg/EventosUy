package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ServidorCentral.logica.ControllerEvento;
import ServidorCentral.logica.Edicion;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.ManejadorEvento;
import ServidorCentral.logica.TipoRegistro;
class AltaTipoRegistroTest {

    private Evento evento1;
    private Edicion edicion1;
    private ControllerEvento controllerEvento;

    @BeforeEach
    void setUp() throws Exception {
        controllerEvento = new ControllerEvento();

        evento1 = new Evento("ConferenciaX", "C-2025", "Evento de prueba",
                LocalDate.now(), new ArrayList<>());
        ManejadorEvento.getInstancia().agregarEvento(evento1);

        edicion1 = new Edicion(
                "Edicion2025",
                "ED2025",
                LocalDate.of(2025, 5, 1),   
                LocalDate.of(2025, 5, 10),  
                LocalDate.of(2025, 4, 20),  
                "Montevideo",
                "Uruguay",
                evento1
        );

        evento1.agregarEdicion(edicion1);
    }

    @Test
    void testAltaTipoRegistroValido() throws Exception {
        controllerEvento.altaTipoRegistro("VIP", "Acceso VIP", 200f, 50, edicion1);

        assertEquals(1, edicion1.getTipoRegistros().size());

        TipoRegistro tr = edicion1.getTipoRegistros().get(0);
        assertEquals("VIP", tr.getNombre());
        assertEquals("Acceso VIP", tr.getDescripcion());
        assertEquals(200f, tr.getCosto());
        assertEquals(50, tr.getCupo());
        assertEquals(edicion1, tr.getEdicion());
    }

    @Test
    void testAltaTipoRegistroNombreDuplicado() throws Exception {
        controllerEvento.altaTipoRegistro("VIP", "Acceso VIP", 200f, 50, edicion1);

        Exception exception = assertThrows(Exception.class, () -> {
            controllerEvento.altaTipoRegistro("VIP", "Acceso VIP Diferente", 250f, 30, edicion1);
        });

        assertEquals("El nombre de tipo de registro \"VIP\" ya fue utilizado", exception.getMessage());

        assertEquals(1, edicion1.getTipoRegistros().size());
    }
}
