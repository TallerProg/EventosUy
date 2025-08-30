package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ServidorCentral.excepciones.*;

import ServidorCentral.logica.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConsultaRegistroTest {
	private static IControllerEvento iCE;
	private static IControllerUsuario iCU;
	

	@BeforeEach
	public  void iniciar() {
		Factory fabrica = Factory.getInstance();
		iCU = fabrica.getIControllerUsuario();
		iCE = fabrica.getIControllerEvento();
	}
 
	@Test
	@Order(1)
	void testConsultaRegistroAsistenPatrocinio() {
		// Crear Institucion
		String descripcionI = "Facultad de Ingeniería de la Universidad de la República";
		String correoI = "https://www.fing.edu.uy";
		String nombreI = "Facultad de Ingeniería";

		Institucion INS01 = new Institucion(nombreI, correoI, descripcionI);
		// Crear Asistente
		String nicknameTest = "gusgui02";
		String correoTest = "gustavoguimerans02@gmail.com";
		String nombreTest = "Gustavo";
		String apellidoTest = "Guimerans";
		LocalDate fNacimientoTest = LocalDate.parse("12/03/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		try {
			iCU.AltaAsistente(nicknameTest, correoTest, nombreTest, apellidoTest, fNacimientoTest, INS01);
		} catch (UsuarioRepetidoException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		}
		;
		Asistente ASI01 = iCU.getAsistente(nicknameTest);
		// Crear Evento
		String nombreE = "Evento Test";
		String descripcionE = "Descripcion del Evento Test";
		LocalDate fechaE = LocalDate.parse("20/12/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String siglaE = "ET2024";
		try {
			iCE.altaEvento(nombreE, descripcionE, fechaE, siglaE, null);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Evento EVE01 = iCE.getEvento(nombreE);
		// Crear Organizador
		
		String nicknameO = "org2341";
		String correoO = "contacto@miseventos.com";
		String nombreO = "MisEventos";
		String descripcionO = "Empresa de organización de eventos.";
		String paginaWebO = "https://miseventos.com";
		try {
			iCU.AltaOrganizador(nicknameO, correoO, nombreO, descripcionO, paginaWebO);
		} catch (UsuarioRepetidoException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		}
		Organizador ORG01 = iCU.getOrganizador(nicknameO);
		// Crear edicion
		String nombreEd = "Edicion Test";
		LocalDate fechaInicioEd = LocalDate.parse("01/11/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate fechaFinEd = LocalDate.parse("10/12/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String lugarEd = "Lugar Test";
		String ciudad = "Ciudad Test";
		try {
			iCE.altaEdicionDeEvento(nombreEd, siglaE, ciudad, lugarEd, fechaInicioEd,
			fechaFinEd, EVE01, ORG01);
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Edicion EDI01 = iCE.findEdicion(nombreEd);
		// Crear Tipo de Registro
		String nombreTR = "Tipo Registro Test";
		String descripcionTR = "Descripcion del Tipo de Registro Test";
		float costoTR = 250.0f;
		int limiteTR = 100;
		try {
			iCE.altaTipoRegistro(nombreTR, descripcionTR, costoTR, limiteTR, EDI01);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		TipoRegistro TRI01 = EDI01.getEdicionTR(nombreTR);
		// Crear Patrocinio
		String codigoP = "PAT2024";
		LocalDate fechaInicioP = LocalDate.parse("01/10/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Float montoP = 5000.0f;
		Integer cupoP = 50;
		ETipoNivel nivelP = ETipoNivel.Bronce;
		Patrocinio PAT01 = new Patrocinio(codigoP, fechaInicioP, cupoP, montoP, nivelP, INS01, EDI01, TRI01);
		EDI01.addLinkPatrocinio(PAT01);
		INS01.agregarPatrocinio(PAT01);
		// Alta de Registro
		try {
			iCE.altaRegistro(nombreEd, nicknameTest, nombreTR, codigoP);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		// Consulta de Registro

		try {
	DTRegistroDetallado dTRD = iCU.getRegistroDetalle(EDI01.getNombre(), ASI01.getNickname());
			
			
			assertEquals(dTRD.getNombreEvento(), nombreE);
			assertEquals(dTRD.getNombreEdicion(), nombreEd);
			assertEquals(dTRD.getTipoRegistro(), nombreTR);
			assertEquals(dTRD.getCosto(), 0.0f);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		}


	};
	
	@Test
	@Order(2)
	void testConsultaRegistroAsistenteSinPatrocinio() {
	    // Crear Asistente
	    String nickAsist = "martin90";
	    String mailAsist = "martin90@gmail.com";
	    String nombreAsist = "Martin";
	    String apellidoAsist = "Rodriguez";
	    LocalDate fechaNacAsist = LocalDate.parse("05/07/1990", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    try {
	        iCU.AltaAsistente(nickAsist, mailAsist, nombreAsist, apellidoAsist, fechaNacAsist, null);
	    } catch (UsuarioRepetidoException e) {
	        fail(e.getMessage());
	        e.printStackTrace();
	    }
	    Asistente asist02 = iCU.getAsistente(nickAsist);

	    // Crear Evento
	    String nombreEvento = "Conferencia Salud";
	    String descripcionEvento = "Conferencia internacional de salud 2024";
	    LocalDate fechaEvento = LocalDate.parse("15/09/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    String siglaEvento = "CS2024";
	    try {
	        iCE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, null);
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	    Evento evento02 = iCE.getEvento(nombreEvento);

	    // Crear Organizador
	    String nickOrg = "orgSalud";
	    String mailOrg = "salud@eventos.com";
	    String nombreOrg = "EventosSalud";
	    String descripcionOrg = "Organización de eventos médicos";
	    String webOrg = "https://saludeventos.com";
	    try {
	        iCU.AltaOrganizador(nickOrg, mailOrg, nombreOrg, descripcionOrg, webOrg);
	    } catch (UsuarioRepetidoException e) {
	        fail(e.getMessage());
	        e.printStackTrace();
	    }
	    Organizador organizador02 = iCU.getOrganizador(nickOrg);

	    // Crear Edición
	    String nombreEdicion = "Edicion Salud 2024";
	    LocalDate fechaInicioEd = LocalDate.parse("01/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    LocalDate fechaFinEd = LocalDate.parse("20/09/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    String lugarEdicion = "Palacio Legislativo";
	    String ciudadEdicion = "Montevideo";
	    try {
	        iCE.altaEdicionDeEvento(nombreEdicion, siglaEvento, ciudadEdicion, lugarEdicion, fechaInicioEd,
	                fechaFinEd, evento02, organizador02);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    Edicion ED03 = iCE.findEdicion(nombreEdicion);

	    // Crear Tipo de Registro
	    String nombreTipoReg = "Registro General";
	    String descripcionTipoReg = "Registro estándar con acceso completo";
	    float costoTipoReg = 150.0f;
	    int limiteTipoReg = 200;
	    try {
	        iCE.altaTipoRegistro(nombreTipoReg, descripcionTipoReg, costoTipoReg, limiteTipoReg, ED03);
	    } catch (Exception e2) {
	        e2.printStackTrace();
	    } 
	    TipoRegistro tipoReg02 = ED03.getEdicionTR(nombreTipoReg);
	    // Alta de Registro sin Patrocinio
	    try {
	        iCE.altaRegistro(nombreEdicion, nickAsist, nombreTipoReg);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    // Consulta de Registro
	    try {
	        DTRegistroDetallado regDet = iCU.getRegistroDetalle(ED03.getNombre(), asist02.getNickname());

	        
	        assertEquals(regDet.getNombreEvento(), nombreEvento);
	        assertEquals(regDet.getNombreEdicion(), nombreEdicion);
       		assertEquals(regDet.getTipoRegistro(), nombreTipoReg);
	        assertEquals(regDet.getCosto(), costoTipoReg);

	    } catch (Exception e) {
	        fail(e.getMessage());
	        e.printStackTrace();
	    }
	}





}
