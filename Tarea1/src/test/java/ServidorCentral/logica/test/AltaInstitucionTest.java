package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerInstitucion;
import ServidorCentral.logica.Institucion;
import ServidorCentral.logica.ManejadorInstitucion;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AltaInstitucionTest {

    private static IControllerInstitucion controllerI;

    @BeforeAll
    static void iniciar() {
        Factory fabrica = Factory.getInstance();
        controllerI = fabrica.getIControllerInstitucion();
    }

    @BeforeEach
    void limpiar() {
        // Limpiar el manejador de instituciones, no el de usuarios
        ManejadorInstitucion.getInstance().limpiar();
    }

    @Test
    void testAltaInstitucionOK() {
        String nombreIns = "ins1";
        String urlIns    = "www.ins.com";
        String descIns   = "institucion1";
        String imgIns    = "institucion.png";

        try {
            controllerI.altaInstitucion(nombreIns, urlIns, descIns, imgIns);

            Institucion insRecuperada = controllerI.findInstitucion(nombreIns);

            assertEquals(nombreIns, insRecuperada.getNombre());
            assertEquals(urlIns,    insRecuperada.getUrl());
            assertEquals(descIns,   insRecuperada.getDescripcion());
            assertEquals(imgIns,    insRecuperada.getImg());
            assertEquals(0, insRecuperada.getAsistentes().size());
            assertEquals(0, insRecuperada.getPatrocinios().size());

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
