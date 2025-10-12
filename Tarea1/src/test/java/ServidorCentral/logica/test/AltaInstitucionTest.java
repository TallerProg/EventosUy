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
import ServidorCentral.logica.ManejadorUsuario;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AltaInstitucionTest {

	private static IControllerInstitucion controllerI;

	@BeforeAll
	public static void iniciar() {
		Factory fabrica = Factory.getInstance();
		controllerI = fabrica.getIControllerInstitucion();
	}

	@BeforeEach
	void limpiarUsuarios() {
		ManejadorUsuario.getInstance().limpiar();
	}

	@Test
	void testAltaInstitucionOK() {
		String nombreIns = "ins1";
		String urlIns = "www.ins.com";
		String descIns = "institucion1";
		;

		try {
			controllerI.altaInstitucion(nombreIns, urlIns, descIns);

			Institucion insRecuperada = controllerI.findInstitucion(nombreIns);

			assertEquals(insRecuperada.getNombre(), nombreIns);
			assertEquals(insRecuperada.getUrl(), urlIns);
			assertEquals(insRecuperada.getDescripcion(), descIns);
			assertEquals(insRecuperada.getAsistentes().size(), 0);
			assertEquals(insRecuperada.getPatrocinios().size(), 0);
			
		} catch (Exception e) {
			fail(e.getMessage()); 
			}
		;
	}
}