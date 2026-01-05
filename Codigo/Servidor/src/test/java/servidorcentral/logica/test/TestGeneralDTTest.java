package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import servidorcentral.logica.Categoria;

import servidorcentral.logica.ControllerEvento;
import servidorcentral.logica.DTCategoria;
import servidorcentral.logica.DTInstitucion;

import servidorcentral.logica.Evento;
import servidorcentral.logica.IControllerEvento;
import servidorcentral.logica.Institucion;
import servidorcentral.logica.ManejadorInstitucion;
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
        Evento eve = eventoBase;

        // Organizador base
        organizador = new Organizador(
            "org1",
            "org1@mail.com",
            "Org Principal",
            "Organizador Principal",
            "1234",
            "org.png"
        );
        Organizador org = organizador;
    }


    @Test
    @DisplayName("DTInstitucion: constructor y getters")
    void dtInstitucionconstructorYGetters() {
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
    void dtInstitucioncadenasVacias() {
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
    void dtInstitucionnullsPermitidos() {
        DTInstitucion dti = new DTInstitucion(null, null, null, null);
        assertNull(dti.getNombre());
        assertNull(dti.getDescripcion());
        assertNull(dti.getUrl());
        assertNull(dti.getImagen());
    }

    // ------------------------------ DTCategoria ------------------------------

    @Test
    @DisplayName("DTCategoria: constructor y getter")
    void dtCategoriaconstructorYGetter() {
        DTCategoria dti = new DTCategoria("Tecnología");
        assertEquals("Tecnología", dti.getNombre());
    }

    @Test
    @DisplayName("DTCategoria: cadena vacía")
    void dtCategoriavacio() {
        DTCategoria dti = new DTCategoria("");
        assertEquals("", dti.getNombre());
    }

    @Test
    @DisplayName("DTCategoria: null aceptado (ajustar si tu diseño lo prohíbe)")
    void dtCategorianullPermitido() {
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
    
    
    
    
    @Test
    @DisplayName("getDTInstitucion: devuelve DTO cuando existe")
    void getDTInstitucion_ok() {
        // Arrange: registrar institución directamente en el manejador
        String nombre = "Inst_Test_DTI_01";
        String desc   = "Institución de prueba";
        String url    = "https://inst.test";
        String img    = "inst.png";

        Institucion ins = new Institucion(nombre, url, desc, img);
        try {
            ManejadorInstitucion.getInstance().agregarInstitucion(ins); // ajustá el nombre si difiere
        } catch (Throwable t) {
            throw new AssertionError("Registrá la institución con el método real del ManejadorInstitucion.", t);
        }

      
        // Act
        DTInstitucion dto = ManejadorInstitucion.getInstance().findInstitucion(nombre).getDTInstitucion();

        // Assert
        org.junit.jupiter.api.Assertions.assertNotNull(dto, "Debe devolver un DTInstitucion no nulo");
        assertEquals(nombre, dto.getNombre());
        assertEquals(desc,   dto.getDescripcion());
        assertEquals(url,    dto.getUrl());
        // En tu DTO usaste getImagen() en otros tests
        assertEquals(img,    dto.getImagen());
    }
    
    @Test
    @DisplayName("fromDtoList: null o vacío => lista vacía inmutable")
    void fromDtoList_nullOVacio() {
        List<Categoria> resNull = Categoria.fromDtoList(null);
        List<Categoria> resVacio = Categoria.fromDtoList(Collections.emptyList());

        assertEquals(0, resNull.size(), "Null debe devolver lista vacía");
        assertEquals(0, resVacio.size(), "Lista vacía debe devolver lista vacía");
    }

    @Test
    @DisplayName("fromDtoList: ignora nulls, nombres null y blancos; trimea")
    void fromDtoList_filtraYTrimea() {
        List<DTCategoria> entrada = Arrays.asList(
            null,                                // null dt
            new DTCategoria(null),               // nombre null
            new DTCategoria("   "),              // blanco
            new DTCategoria("  Rock  "),         // trimeable
            new DTCategoria("Tecnología")
        );

        List<Categoria> res = Categoria.fromDtoList(entrada);

        assertEquals(2, res.size(), "Deben quedar 2 categorías válidas");
        assertEquals("Rock", res.get(0).getNombre(), "Debe trimear nombre");
        assertEquals("Tecnología", res.get(1).getNombre());
    }

    @Test
    @DisplayName("fromDtoList: elimina duplicados preservando orden de primera aparición")
    void fromDtoList_sinDuplicadosOrdenPreservado() {
        List<DTCategoria> entrada = Arrays.asList(
            new DTCategoria("Rock"),
            new DTCategoria("Tecnología"),
            new DTCategoria("Rock"),          // duplicado exacto
            new DTCategoria("Tecnología"),    // duplicado exacto
            new DTCategoria("Cine")
        );

        List<Categoria> res = Categoria.fromDtoList(entrada);

        assertEquals(3, res.size(), "Debe eliminar duplicados");
        assertEquals("Rock", res.get(0).getNombre(), "Preserva orden de primera aparición");
        assertEquals("Tecnología", res.get(1).getNombre());
        assertEquals("Cine", res.get(2).getNombre());
    }

    @Test
    @DisplayName("fromDtoList: sensibilidad a mayúsculas/minúsculas (\"Tech\" != \"tech\")")
    void fromDtoList_caseSensitive() {
        List<DTCategoria> entrada = Arrays.asList(
            new DTCategoria("Tech"),
            new DTCategoria("tech"),   // distinto por case
            new DTCategoria("TECH")    // distinto por case
        );

        List<Categoria> res = Categoria.fromDtoList(entrada);

        assertEquals(3, res.size(), "Valores con distinto casing deben considerarse distintos");
        assertEquals("Tech", res.get(0).getNombre());
        assertEquals("tech", res.get(1).getNombre());
        assertEquals("TECH", res.get(2).getNombre());
    }


}
