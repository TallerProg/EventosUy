
package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;

import ServidorCentral.logica.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConsultaEventoTest {
	private static IControllerEvento controllerE;
	private static IControllerUsuario controlerUSR;


	@BeforeEach
	public  void iniciar() {
		Factory fabrica = Factory.getInstance();
		controllerE = fabrica.getIControllerEvento();
		controlerUSR = fabrica.getIControllerUsuario();

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
	
	@Test
	@Order(3)
	void verificarDatosComboCategoria() {
		try {
			 List<String> nombresCategorias = List.of("VIP test", "Entrada test General", "Backstage test Pass");
			    for (String nombre : nombresCategorias) {
			            controllerE.altaCategoria(nombre);
			    }
			List<Categoria> categorias = controllerE.getCategorias();
			String nombreEvento = "eventoo";
	        String descripcionEvento = "Carrera anual de 42K, 21K y 10K por las calles de Montevideo.";
	        LocalDate fechaEvento = LocalDate.of(2025, 4, 6);
	        String siglaEvento = "MMM2025";

	        controllerE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, categorias);
	        Evento evento = controllerE.getEvento(nombreEvento);

	        String nickOrg = "orgtestCEvento";
	        String mailOrg = "orgtestCEvento@montevideorunners.uy";
	        String nombreOrg = "Montevideo Runners Club";
	        String descripcionOrg = "Organización de eventos deportivos en Uruguay.";
	        String webOrg = "https://montevideorunners.uy";

	        controlerUSR.AltaOrganizador(nickOrg, mailOrg, nombreOrg, descripcionOrg, webOrg);
	        Organizador org = controlerUSR.getOrganizador(nickOrg);

	        String nombreEdicion = "Edición test de 2025";
	        LocalDate fechaInicioEd = LocalDate.of(2025, 4, 6);
	        LocalDate fechaFinEd = LocalDate.of(2025, 4, 6);
	        String lugarEdicion = "Rambla de Montevideo";
	        String ciudadEdicion = "Montevideo";

	        controllerE.altaEdicionDeEvento(nombreEdicion, siglaEvento, ciudadEdicion, lugarEdicion,fechaInicioEd, fechaFinEd, evento, org);
	        
	     // Listar Categorias (para el combo de las categorias)
	        List<Categoria> categoriasEvento =evento.getCategoria();
	        for (String nombreCat : nombresCategorias) {
	        	assertTrue(categoriasEvento.stream().anyMatch(c -> c.getNombre().equals(nombreCat)));	        
	        }
		     // Listar ediciones (para el combo de las categorias)
	        List<String> ediciones = controllerE.listarEdicionesDeEvento(nombreEvento);
	        assertNotNull(ediciones);
	        assertTrue(ediciones.contains(nombreEdicion));
	        
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
	
	
		



