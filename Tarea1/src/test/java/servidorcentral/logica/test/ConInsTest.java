package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerInstitucion;
import servidorcentral.logica.DTInstitucion;
import servidorcentral.logica.Institucion;
import servidorcentral.logica.ManejadorInstitucion;

class ControllerInstitucionTest {

    private IControllerInstitucion controller;

    @BeforeEach
    void setUp() {
    	Factory fabrica = Factory.getInstance();
        controller = fabrica.getIControllerInstitucion();

    }

    private String nuevoNombre() {
        return "Inst_" + UUID.randomUUID().toString().substring(0, 8);
    }

    @Test
    @DisplayName("altaInstitucion: crea una nueva institución")
    void testAltaInstitucionOk() throws Exception {
        String nombre = nuevoNombre();
        String url = "https://ejemplo.org";
        String desc = "Institución de prueba";
        String img = "img1.png";

        ManejadorInstitucion min = ManejadorInstitucion.getInstance();
        int sizeBefore = min.listarInstituciones().size();

        controller.altaInstitucion(nombre, url, desc, img);

        Institucion creada = min.findInstitucion(nombre);
        assertNotNull(creada, "La institución debería existir tras el alta");
        assertEquals(nombre, creada.getNombre());
        assertEquals(url, creada.getUrl());
        assertEquals(desc, creada.getDescripcion());
        assertEquals(img, creada.getImg());

        int sizeAfter = min.listarInstituciones().size();
        assertEquals(sizeBefore + 1, sizeAfter, "El total debería aumentar en 1");
    }

    @Test
    @DisplayName("altaInstitucion: nombre repetido lanza Exception")
    void testAltaInstitucionRepetida() throws Exception {
        String nombre = nuevoNombre();

        controller.altaInstitucion(nombre, "https://a.com", "desc", "a.png");
        Exception ex = assertThrows(Exception.class, () ->
            controller.altaInstitucion(nombre, "https://b.com", "desc2", "b.png")
        );
        // Mensaje esperado según tu implementación
        assertTrue(ex.getMessage() != null && ex.getMessage().toLowerCase().contains("ya existe"),
                "Debe indicar que la institución ya existe");
    }

    @Test
    @DisplayName("findInstitucion: encuentra por nombre")
    void testFindInstitucion() throws Exception {
        String nombre = nuevoNombre();
        controller.altaInstitucion(nombre, "https://find.com", "para find", "f.png");

        Institucion encontrada = controller.findInstitucion(nombre);
        assertNotNull(encontrada);
        assertEquals(nombre, encontrada.getNombre());
    }

    @Test
    @DisplayName("getInstituciones: lista contiene la creada")
    void testGetInstituciones() throws Exception {
        String nombre = nuevoNombre();
        String url = "https://lista.com";
        String desc = "en lista";
        String img = "l.png";

        controller.altaInstitucion(nombre, url, desc, img);

        List<Institucion> todas = controller.getInstituciones();
        assertNotNull(todas);
        assertFalse(todas.isEmpty(), "La lista no debería estar vacía");

        Optional<Institucion> match = todas.stream()
                .filter(i -> nombre.equals(i.getNombre()))
                .findFirst();

        assertTrue(match.isPresent(), "La lista debería incluir la institución recién creada");
        Institucion i = match.get();
        assertEquals(url, i.getUrl());
        assertEquals(desc, i.getDescripcion());
        assertEquals(img, i.getImg());
    }

    @Test
    @DisplayName("getDTInstituciones: devuelve DTOs correctos")
    void testGetDTInstituciones() throws Exception {
        String nombre = nuevoNombre();
        String url = "https://dto.com";
        String desc = "dto ok";
        String img = "dto.png";

        controller.altaInstitucion(nombre, url, desc, img);

        List<DTInstitucion> dts = controller.getDTInstituciones();
        assertNotNull(dts);
        assertFalse(dts.isEmpty(), "La lista de DTOs no debería estar vacía");

        Optional<DTInstitucion> match = dts.stream()
                .filter(dt -> nombre.equals(dt.getNombre()))
                .findFirst();

        assertTrue(match.isPresent(), "Debe existir un DTInstitucion con el nombre insertado");
        DTInstitucion dt = match.get();
        assertEquals(url, dt.getUrl());
        assertEquals(desc, dt.getDescripcion());
        assertEquals(img, dt.getImagen());
    }
}
