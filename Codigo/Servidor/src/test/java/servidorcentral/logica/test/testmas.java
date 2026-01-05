package servidorcentral.logica.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerEvento;
import servidorcentral.logica.ManejadorEvento;
import servidorcentral.logica.Edicion;
import servidorcentral.logica.Evento;
import servidorcentral.logica.TipoRegistro;
import java.time.LocalDate;

class AltaTipoRegistroDTTest {

    private IControllerEvento controller;
    private ManejadorEvento manejador;
    private Edicion edicion;

    @BeforeEach
    void setUp() {
    	Factory fabrica = Factory.getInstance();
    	controller = fabrica.getIControllerEvento();
        manejador = ManejadorEvento.getInstancia();
        try {
        controller.altaEvento("Event", "Desc", LocalDate.now(), "sigla", null, null);
        }catch(Exception e) {
        	
        }
        Evento Event = manejador.findEvento("Event");
        try {
        controller.altaEdicionDeEvento("EdicionTest", "sigla", "ciudad", "pais",LocalDate.now(), LocalDate.now(),LocalDate.now(),Event,null,"img");
        }catch(Exception e) {
        	
        }
        edicion= manejador.findEdicion("EdicionTest");
        manejador.agregarEdicion(edicion);
    }

    @Test
    void testAltaTipoRegistroDT_CreaNuevo() throws Exception {
        // Caso feliz: nombre no usado
        controller.altaTipoRegistroDT("VIP", "Acceso premium", 500f, 100, "EdicionTest");

        // Verificamos que la edición ahora tenga el tipo de registro
        assertTrue(edicion.existeTR("VIP"));
        TipoRegistro tr = edicion.getEdicionTR("VIP");
        assertNotNull(tr);
        assertEquals("Acceso premium", tr.getDescripcion());
        assertEquals(500f, tr.getCosto());
        assertEquals(100, tr.getCupo());
    }

}

