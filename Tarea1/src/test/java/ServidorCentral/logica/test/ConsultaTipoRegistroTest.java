package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ServidorCentral.excepciones.UsuarioRepetidoException;
import ServidorCentral.logica.DTTipoRegistro;
import ServidorCentral.logica.Edicion;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.Organizador;
import ServidorCentral.logica.TipoRegistro;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConsultaTipoRegistroTest {
	private static IControllerEvento controllerE;
	private static IControllerUsuario controlerUSR;
	

	@BeforeEach
	public  void iniciar() {
		Factory fabrica = Factory.getInstance();
		controlerUSR = fabrica.getIControllerUsuario();
		controllerE = fabrica.getIControllerEvento();
	}
	
	@Test
	@Order(1)
	void testConsultaValoresDTTipoRegistro() {
	    String nombreEvento = "EventoTesteo1";
	    String descripcionEvento = "Estamos testeando evento1";
	    LocalDate fechaEvento = LocalDate.of(2025, 05, 01);
	    String siglaEvento = "TEST1";
	    try {
	    	controllerE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, null);
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	    Evento evento = controllerE.getEvento(nombreEvento);

	    String nickOrg = "organizadorTesteo1";
	    String mailOrg = "org@test.com";
	    String nombreOrg = "Testeador";
	    String descripcionOrg = "Organización de testeos";
	    String webOrg = "https://testeo.com";
	    try {
	    	controlerUSR.altaOrganizador(nickOrg, mailOrg, nombreOrg, descripcionOrg, webOrg, "1234");
	    } catch (UsuarioRepetidoException e) {
	        fail(e.getMessage());
	        e.printStackTrace();
	    }
	    Organizador org = controlerUSR.getOrganizador(nickOrg);

	    String nombreEdicion = "Edicion test1";
	    LocalDate fechaInicioEd = LocalDate.of(2025, 03, 01);
	    LocalDate fechaFinEd = LocalDate.of(2025, 04, 01);;
	    LocalDate fechaAlta = LocalDate.of(2024, 04, 01);;
	    String lugarEdicion = "El testeador";
	    String ciudadEdicion = "test";
	    try {
	    	controllerE.altaEdicionDeEvento(nombreEdicion, siglaEvento, ciudadEdicion, lugarEdicion, fechaInicioEd,
	                fechaFinEd, fechaAlta, evento, org, "imagenTest");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    Edicion edicion = controllerE.findEdicion(nombreEdicion);

	    String nombreTipoReg = "Registro Test";
	    String descripcionTipoReg = "Registro para testear";
	    float costoTipoReg = 100.0f;
	    int limiteTipoReg = 14;
	    try {
	    	controllerE.altaTipoRegistro(nombreTipoReg, descripcionTipoReg, costoTipoReg, limiteTipoReg, edicion);
	    } catch (Exception e2) {
	        e2.printStackTrace();
	    } 

	    DTTipoRegistro dt = controllerE.consultaTipoRegistro(nombreEdicion, nombreTipoReg);
        assertNotNull(dt);
        assertEquals("Registro Test", dt.getNombre());
        assertEquals("Registro para testear", dt.getDescripcion());
        assertEquals(100.0f, dt.getCosto());
        assertEquals(14, dt.getCupo());
	    
	    
	}
	
	@Test
	@Order(2)
	void testcargarDatosCombo() {
		try {
	        String nombreEvento = "Maratón de Montevideo";
	        String descripcionEvento = "Carrera anual de 42K, 21K y 10K por las calles de Montevideo.";
	        LocalDate fechaEvento = LocalDate.of(2025, 4, 6);
	        String siglaEvento = "MMM2025";

	        controllerE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, null);
	        Evento evento = controllerE.getEvento(nombreEvento);

	        String nickOrg = "orgMontevideoRunners";
	        String mailOrg = "contacto@montevideorunners.uy";
	        String nombreOrg = "Montevideo Runners Club";
	        String descripcionOrg = "Organización de eventos deportivos en Uruguay.";
	        String webOrg = "https://montevideorunners.uy";

	        controlerUSR.altaOrganizador(nickOrg, mailOrg, nombreOrg, descripcionOrg, webOrg, "1234");
	        Organizador org = controlerUSR.getOrganizador(nickOrg);

	        String nombreEdicion = "Edición 2025";
	        LocalDate fechaInicioEd = LocalDate.of(2025, 4, 6);
	        LocalDate fechaFinEd = LocalDate.of(2025, 4, 6);
		    LocalDate fechaAlta= LocalDate.of(2024, 04, 01);;
	        String lugarEdicion = "Rambla de Montevideo";
	        String ciudadEdicion = "Montevideo";

	        controllerE.altaEdicionDeEvento(nombreEdicion, siglaEvento, ciudadEdicion, lugarEdicion,
	                fechaInicioEd, fechaFinEd, fechaAlta, evento, org, "imagenEdicion");
	        Edicion edicion = controllerE.findEdicion(nombreEdicion);

	        String nombreTipoReg = "Inscripción General 42K";
	        String descripcionTipoReg = "Registro para la maratón completa de 42 kilómetros.";
	        float costoTipoReg = 1500.0f;
	        int limiteTipoReg = 5000;

	        controllerE.altaTipoRegistro(nombreTipoReg, descripcionTipoReg, costoTipoReg, limiteTipoReg, edicion);


	        List<Evento> eventos = controllerE.listarEventos();
	        assertNotNull(eventos);
	        assertTrue(eventos.stream().anyMatch(e -> e.getNombre().equals(nombreEvento)));

	        List<String> ediciones = controllerE.listarEdicionesDeEvento(nombreEvento);
	        assertNotNull(ediciones);
	        assertTrue(ediciones.contains(nombreEdicion));

	        List<TipoRegistro> tiposReg = edicion.getTipoRegistros();
	        assertNotNull(tiposReg);
	        assertTrue(tiposReg.stream().anyMatch(tr -> tr.getNombre().equals(nombreTipoReg)));

		} catch (Exception e) {
		    e.printStackTrace();
		}
		
			
	}



}
			
	


