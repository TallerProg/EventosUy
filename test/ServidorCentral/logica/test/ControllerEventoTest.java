package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.CargarDatos;
import ServidorCentral.logica.Categoria;
import ServidorCentral.logica.Evento;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerEventoTest {
	
	private static IControllerEvento controladorEve;
	private static IControllerUsuario cu;

	
	@BeforeAll
	public static void iniciar() {
		Factory fabrica = Factory.getInstance();
		controladorEve = fabrica.getIControllerEvento();
		cu = fabrica.getIControllerUsuario();
	}
	
	@Test
	@Order(1)
	
	void testRegistrarEventoOK() {
		String nomTest = "ev1";
		String descTest = "desc";
		LocalDate fAltaTest = LocalDate.parse("12/03/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String siglaTest = "ev";
		List<Categoria> catTest = new ArrayList<>();
		
		try {
			controladorEve.altaEvento(nomTest, descTest, fAltaTest, siglaTest, catTest);
			Evento e = controladorEve.getEvento(nomTest);
			
			assertEquals(e.getNombre(),nomTest);
			assertEquals(e.getDescripcion(),descTest);
			assertEquals(e.getFAlta(),fAltaTest);
			assertEquals(e.getSigla(),siglaTest);
			assertEquals(e.getCategoria(),catTest);
			
		} catch (Exception e){
			fail(e.getMessage());
			e.printStackTrace();

		};
		
		
	}
	
	@Test
	@Order(1)
	
	void testRegistrarEventoRep() {
		String nomTest = "ev2";
		String descTest = "desc";
		LocalDate fAltaTest = LocalDate.parse("12/03/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String siglaTest = "ev";
		List<Categoria> catTest = new ArrayList<>();
		
		try {
			controladorEve.altaEvento(nomTest, descTest, fAltaTest, siglaTest, catTest);
			
		} catch (Exception e){
			fail(e.getMessage());
			e.printStackTrace();

		};
		assertThrows(Exception.class, ()->{controladorEve.altaEvento(nomTest, descTest, fAltaTest, siglaTest, catTest);});
		
	}
	

	
}