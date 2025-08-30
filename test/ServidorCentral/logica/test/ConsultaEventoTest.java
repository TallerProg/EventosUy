
package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.time.LocalDate;

import ServidorCentral.logica.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConsultaEventoTest {
	private static IControllerEvento controllerE;
	

	@BeforeEach
	public  void iniciar() {
		Factory fabrica = Factory.getInstance();
		controllerE = fabrica.getIControllerEvento();
	}
	@Test
	@Order(1)
	void ConsultaEventoExistente() {
		try {
		String nombreEvento = "Existe";
        String descripcionEvento = "Carrera de existencia";
        LocalDate fechaEvento = LocalDate.of(2025, 4, 6);
        String siglaEvento = "EX2025";
        controllerE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, null);;
        assertTrue(controllerE.existeEvento(nombreEvento));
        assertFalse(controllerE.existeEvento("NoExiste"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Order(2)
	void TestConsultarValoresDTEvento() {
		try {
		 	String nombreEvento = "Maratón de Panama";
	        String descripcionEvento = "Carrera anual de 10K";
	        LocalDate fechaEvento = LocalDate.of(2025, 4, 6);
	        String siglaEvento = "PNM2025";

	        controllerE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, null);
	        DTevento dt = controllerE.consultaEvento("Maratón de Panama");
	        assertNotNull(dt);
	        assertEquals("Maratón de Panama", dt.getNombre());
	        assertEquals("Carrera anual de 10K", dt.getDescripcion());
	        assertEquals(LocalDate.of(2025, 4, 6), dt.getFAlta());
	        assertEquals("PNM2025", dt.getSigla());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
	
	
		



