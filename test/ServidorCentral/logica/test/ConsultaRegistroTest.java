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
		String descripcionI = "Facultad de Ingeniería de la Universidad de la República";
		String correoI = "https://www.fing.edu.uy";
		String nombreI = "Facultad de Ingeniería";

		Institucion INS01 = new Institucion(nombreI, correoI, descripcionI);
		String nicknameTest = "gusgui02";
		String correoTest = "gustavoguimerans02@gmail.com";
		String nombreTest = "Gustavo";
		String apellidoTest = "Guimerans";
		LocalDate fNacimientoTest = LocalDate.parse("12/03/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		try {
			iCU.AltaAsistente(nicknameTest, correoTest, nombreTest, apellidoTest, fNacimientoTest, INS01);
		} catch (UsuarioRepetidoException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		;
		Asistente ASI01 = iCU.getAsistente(nicknameTest);
		String nombreE = "Evento Test";
		String descripcionE = "Descripcion del Evento Test";
		LocalDate fechaE = LocalDate.parse("20/12/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String siglaE = "ET2024";
		try {
			iCE.altaEvento(nombreE, descripcionE, fechaE, siglaE, null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Evento EVE01 = iCE.getEvento(nombreE);
		
		String nicknameO = "org2341";
		String correoO = "contacto@miseventos.com";
		String nombreO = "MisEventos";
		String descripcionO = "Empresa de organización de eventos.";
		String paginaWebO = "https://miseventos.com";
		try {
			iCU.AltaOrganizador(nicknameO, correoO, nombreO, descripcionO, paginaWebO);
		} catch (UsuarioRepetidoException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}
		Organizador ORG01 = iCU.getOrganizador(nicknameO);
		String nombreEd = "Edicion Test";
		LocalDate fechaInicioEd = LocalDate.parse("01/11/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate fechaFinEd = LocalDate.parse("10/12/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate fechaAlta = LocalDate.parse("01/10/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String lugarEd = "Lugar Test";
		String ciudad = "Ciudad Test";
		try {
			iCE.altaEdicionDeEvento(nombreEd, siglaE, ciudad, lugarEd, fechaInicioEd,
			fechaFinEd,fechaAlta, EVE01, ORG01);
		} catch (Exception e) { 
			e.printStackTrace();
		}

		Edicion EDI01 = iCE.findEdicion(nombreEd);
		String nombreTR = "Tipo Registro Test";
		String descripcionTR = "Descripcion del Tipo de Registro Test";
		float costoTR = 250.0f;
		int limiteTR = 100;
		try {
			iCE.altaTipoRegistro(nombreTR, descripcionTR, costoTR, limiteTR, EDI01);
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		TipoRegistro TRI01 = EDI01.getEdicionTR(nombreTR);
		String codigoP = "PAT2024";
		LocalDate fechaInicioP = LocalDate.parse("01/10/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Float montoP = 5000.0f;
		Integer cupoP = 50;
		ETipoNivel nivelP = ETipoNivel.Bronce;
		Patrocinio PAT01 = new Patrocinio(codigoP, fechaInicioP, cupoP, montoP, nivelP, INS01, EDI01, TRI01);
		EDI01.addLinkPatrocinio(PAT01);
		INS01.agregarPatrocinio(PAT01);
		try {
			iCE.altaRegistro(nombreEd, nicknameTest, nombreTR, codigoP);
		} catch (Exception e) {
			e.printStackTrace();
		} 

		try {
	DTRegistroDetallado dTRD = iCU.getRegistroDetalle(EDI01.getNombre(), ASI01.getNickname());
			
			
			assertEquals(dTRD.getNombreEvento(), nombreE);
			assertEquals(dTRD.getNombreEdicion(), nombreEd);
			assertEquals(dTRD.getTipoRegistro(), nombreTR);
			assertEquals(dTRD.getCosto(), 0.0f);
			
		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
		}


	};
	
	@Test
	@Order(2)
	void testConsultaRegistroAsistenteSinPatrocinio() {
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

	    String nombreEdicion = "Edicion Salud 2024";
	    LocalDate fechaInicioEd = LocalDate.parse("01/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    LocalDate fechaFinEd = LocalDate.parse("20/09/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate fechaAlta = LocalDate.parse("01/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    String lugarEdicion = "Palacio Legislativo";
	    String ciudadEdicion = "Montevideo";
	    try {
	        iCE.altaEdicionDeEvento(nombreEdicion, siglaEvento, ciudadEdicion, lugarEdicion, fechaInicioEd,
	                fechaFinEd,fechaAlta, evento02, organizador02);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    Edicion ED03 = iCE.findEdicion(nombreEdicion);

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
	    try {
	        iCE.altaRegistro(nombreEdicion, nickAsist, nombreTipoReg);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    try {
	        DTRegistroDetallado regDet = iCU.getRegistroDetalle(ED03.getNombre(), asist02.getNickname());
	        DTRegistro dTRe = iCU.getDTRegistro(ED03.getNombre(), asist02.getNickname());
	        
	        assertEquals(dTRe.getCosto(), costoTipoReg);
	        assertEquals(dTRe.getfInicio(), LocalDate.now());
	        assertEquals(regDet.getNombreEvento(), nombreEvento);
	        assertEquals(regDet.getNombreEdicion(), nombreEdicion);
       		assertEquals(regDet.getTipoRegistro(), nombreTipoReg);
	        assertEquals(regDet.getCosto(), costoTipoReg);

	    } catch (Exception e) {
	        fail(e.getMessage());
	        e.printStackTrace();
	    }
	}

	@Test
	@Order(3)
	void testConsultaRegistroAsistenteSinPatrocinio2() {
	    // ==== Asistente ====
	    String nickAsist = "lucasg90";
	    String mailAsist = "lucasg90@gmail.com";
	    String nombreAsist = "Lucas";
	    String apellidoAsist = "Gimenez";
	    LocalDate fechaNacAsist = LocalDate.parse("12/08/1990", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    try {
	        iCU.AltaAsistente(nickAsist, mailAsist, nombreAsist, apellidoAsist, fechaNacAsist, null);
	    } catch (UsuarioRepetidoException e) {
	        fail(e.getMessage());
	        e.printStackTrace();
	    }
	    Asistente asist02 = iCU.getAsistente(nickAsist);

	    // ==== Evento ====
	    String nombreEvento = "Congreso Medicina";
	    String descripcionEvento = "Congreso internacional de medicina 2024";
	    LocalDate fechaEvento = LocalDate.parse("22/09/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    String siglaEvento = "CMED24";
	    try {
	        iCE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, null);
	    } catch (Exception e1) {
	        e1.printStackTrace();
	    }
	    Evento evento02 = iCE.getEvento(nombreEvento);

	    // ==== Organizador ====
	    String nickOrg = "orgMed";
	    String mailOrg = "orgmed@eventos.com";
	    String nombreOrg = "EventosMedicos";
	    String descripcionOrg = "Organización de congresos médicos";
	    String webOrg = "https://eventosmedicos.com";
	    try {
	        iCU.AltaOrganizador(nickOrg, mailOrg, nombreOrg, descripcionOrg, webOrg);
	    } catch (UsuarioRepetidoException e) {
	        fail(e.getMessage());
	        e.printStackTrace();
	    }
	    Organizador organizador02 = iCU.getOrganizador(nickOrg);

	    // ==== Edición ====
	    String nombreEdicion = "Edicion Medicina 2024";
	    LocalDate fechaInicioEd = LocalDate.parse("05/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    LocalDate fechaFinEd = LocalDate.parse("25/09/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    LocalDate fechaAlta = LocalDate.parse("01/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	    String lugarEdicion = "Centro de Convenciones";
	    String ciudadEdicion = "Montevideo";
	    try {
	        iCE.altaEdicionDeEvento(nombreEdicion, siglaEvento, ciudadEdicion, lugarEdicion, fechaInicioEd,
	                fechaFinEd, fechaAlta, evento02, organizador02);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    Edicion ed02 = iCE.findEdicion(nombreEdicion);

	    // ==== Tipo Registro ====
	    String nombreTipoReg = "Registro Completo";
	    String descripcionTipoReg = "Acceso total a todas las charlas";
	    float costoTipoReg = 250.0f;
	    int limiteTipoReg = 300;
	    try {
	        iCE.altaTipoRegistro(nombreTipoReg, descripcionTipoReg, costoTipoReg, limiteTipoReg, ed02);
	    } catch (Exception e2) {
	        e2.printStackTrace();
	    } 
	    TipoRegistro tipoReg02 = ed02.getEdicionTR(nombreTipoReg);

	    // ==== Registro del Asistente ====
	    try {
		    LocalDate fechaAltaTR = LocalDate.parse("01/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));

	        iCE.altaRegistro(nombreEdicion, nickAsist, nombreTipoReg, fechaAltaTR);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // ==== Validaciones ====
	    try {
	        DTRegistroDetallado regDet = iCU.getRegistroDetalle(ed02.getNombre(), asist02.getNickname());
	        DTRegistro dTRe = iCU.getDTRegistro(ed02.getNombre(), asist02.getNickname());
	        
	        assertEquals(dTRe.getCosto(), costoTipoReg);
	        assertEquals(dTRe.getfInicio(),LocalDate.parse("01/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	        assertEquals(regDet.getNombreEvento(), nombreEvento);
	        assertEquals(regDet.getNombreEdicion(), nombreEdicion);
	        assertEquals(regDet.getTipoRegistro(), nombreTipoReg);
	        assertEquals(regDet.getCosto(), costoTipoReg);

	    } catch (Exception e) {
	        fail(e.getMessage());
	        e.printStackTrace();
	    }





}
