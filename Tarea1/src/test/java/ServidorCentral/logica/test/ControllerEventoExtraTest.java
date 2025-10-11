package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import ServidorCentral.logica.ManejadorUsuario;
import ServidorCentral.logica.Organizador;
import ServidorCentral.logica.TipoRegistro;

class ControllerEventoExtrasTest {
    private ControllerEvento controller;
    private Evento evento;
    private Edicion edicion;
    private Organizador organizador;
    
    @BeforeEach
    void setUp() {
        controller = new ControllerEvento();
        
        ManejadorEvento.getInstancia().limpiar();
        ManejadorUsuario.getInstance().limpiar();
        
        evento = new Evento("EventoTest", "EVT", "desc", LocalDate.now(), new ArrayList<>());
        edicion = new Edicion("EdicionTest", "EDT", LocalDate.of(2025, 5, 1), LocalDate.of(2025, 5, 10), LocalDate.of(2024, 5, 10),
                "Montevideo", "UY", evento);
        evento.agregarEdicion(edicion);
        ManejadorEvento.getInstancia().agregarEvento(evento);
        ManejadorEvento.getInstancia().agregarEdicion(edicion);

        organizador = new Organizador(
        	    "nickOrgTest",     
        	    "org@test.com",    
        	    "OrgTest",         
        	    "Descripción test",
        	    "1234"
        	);
        	ManejadorUsuario.getInstance().agregarOrganizador(organizador);

    }

    @Test
    void testObtenerNombreEdicionPorEventoExistente() {
        String nombreEd = controller.obtenerNombreEdicionPorEvento(evento.getNombre());
        assertEquals("EdicionTest", nombreEd);
    }

    @Test
    void testObtenerNombreEdicionPorEventoNoExistente() {
        String nombreEd = controller.obtenerNombreEdicionPorEvento("EventoFake");
        assertNull(nombreEd);
    }

    @Test
    void testObtenerEventoPorNombreExistente() {
        Evento ev = controller.obtenerEventoPorNombre(evento.getNombre());
        assertNotNull(ev);
        assertEquals("EventoTest", ev.getNombre());
    }

    @Test
    void testObtenerEventoPorNombreNoExistente() {
        Evento ev = controller.obtenerEventoPorNombre("EventoFake");
        assertNull(ev);
    }

    @Test
    void testObtenerOrganizadorPorNombreExistente() {
        Organizador org = controller.obtenerOrganizadorPorNombre("OrgTest");
        assertNotNull(org);
        assertEquals("OrgTest", org.getNombre());
    }

    @Test
    void testObtenerOrganizadorPorNombreNoExistente() {
        Organizador org = controller.obtenerOrganizadorPorNombre("OrgFake");
        assertNull(org);
    }

    @Test
    void testListarPatrociniosDeEdicionVacia() {
        List<DTPatrocinio> lista = controller.listarPatrociniosDeEdicion(edicion.getNombre());
        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }
    
    @Test
    void testListarPatrociniosDeEdicionConPatrocinio() throws Exception {
        Institucion institucion = new Institucion("InstTest", "url", "desc");
        ManejadorInstitucion.getInstance().agregarInstitucion(institucion);
        TipoRegistro tr = new TipoRegistro("General", "Acceso", 100f, 10, edicion);
        edicion.agregarTipoRegistro(tr);
        controller.altaPatrocinio("PAT001", LocalDate.now(), 1, 500f, ETipoNivel.Oro,
                institucion.getNombre(), edicion.getNombre(), tr.getNombre());
        
        List<DTPatrocinio> lista = controller.listarPatrociniosDeEdicion(edicion.getNombre());
        assertEquals(1, lista.size());
        assertEquals("PAT001", lista.get(0).getCodigo());
    }
}
