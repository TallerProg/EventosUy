package ServidorCentral.logica.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ServidorCentral.logica.*;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AltaTipoRegistroTest {

    private Evento evento1;
    private Edicion edicion1;
    private ControllerEvento controllerEvento;

    @BeforeEach
    void setUp() throws Exception {
        controllerEvento = new ControllerEvento();

        // Crear evento
        evento1 = new Evento("ConferenciaX", "C-2025", "Evento de prueba",
                LocalDate.now(), new ArrayList<>());
        ManejadorEvento.getInstancia().agregarEvento(evento1);

        // Crear edición
        edicion1 = new Edicion("Edicion2025", "ED2025",
                LocalDate.of(2025,5,1), LocalDate.of(2025,5,10),
                "Montevideo","Uruguay", evento1);

        // Asociar edición al evento
        evento1.agregarEdicion(edicion1);
    }

    @Test
    void testAltaTipoRegistroValido() throws Exception {
        // Dar de alta un tipo de registro válido
        controllerEvento.altaTipoRegistro("VIP", "Acceso VIP", 200f, 50, edicion1);

        // Verificar que se agregó correctamente
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
        // Dar de alta un tipo de registro
        controllerEvento.altaTipoRegistro("VIP", "Acceso VIP", 200f, 50, edicion1);

        // Intentar dar de alta otro con el mismo nombre
        Exception exception = assertThrows(Exception.class, () -> {
            controllerEvento.altaTipoRegistro("VIP", "Acceso VIP Diferente", 250f, 30, edicion1);
        });

        // Verificar el mensaje de excepción
        assertEquals("El nombre de tipo de registro \"VIP\" ya fue utilizado", exception.getMessage());

        // Verificar que no se haya agregado el duplicado
        assertEquals(1, edicion1.getTipoRegistros().size());
    }
}
