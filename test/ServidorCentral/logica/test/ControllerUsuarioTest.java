package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import ServidorCentral.excepciones.*;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.Institucion;
import ServidorCentral.logica.Organizador;
import ServidorCentral.logica.Asistente;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerUsuarioTest {
	
	private static IControllerUsuario controladorUsu;
	
	@BeforeAll
	public static void iniciar() {
		Factory fabrica = Factory.getInstance();
		controladorUsu = fabrica.getIControllerUsuario();
	}
	
	@Test
	@Order(1)
	void testRegistrarAsistenteConInsOK() {

		String nicknameTest = "gusgui01";
		String correoTest = "gustavoguimerans01@gmail.com";
		String nombreTest = "Gustavo";
		String apellidoTest = "Guimerans";
		LocalDate fNacimientoTest = LocalDate.parse("12/03/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Institucion insTest = new Institucion("ins1", "www.ins.com", "institucion1");
		
		try {
			controladorUsu.AltaAsistente(nicknameTest, correoTest, nombreTest, apellidoTest, fNacimientoTest, insTest);
			Asistente a =controladorUsu.getAsistente(nicknameTest);
			
			assertEquals(a.getNickname(), nicknameTest);
			assertEquals(a.getCorreo(), correoTest);
			assertEquals(a.getNombre(), nombreTest);
			assertEquals(a.getApellido(), apellidoTest);
			assertEquals(a.getfNacimiento(), fNacimientoTest);
			assertEquals(a.getInstitucion(), insTest);
			
		} catch (UsuarioRepetidoException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	
	@Test
	@Order(2)
	void testRegistrarAsistenteSinInsOK() {

		String nicknameTest = "gusgui02";
		String correoTest = "gustavoguimerans02@gmail.com";
		String nombreTest = "Gustavo";
		String apellidoTest = "Guimerans";
		LocalDate fNacimientoTest = LocalDate.parse("12/03/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				
		try {
			controladorUsu.AltaAsistente(nicknameTest, correoTest, nombreTest, apellidoTest, fNacimientoTest, null);
			Asistente a =controladorUsu.getAsistente(nicknameTest);
			
			assertEquals(a.getNickname(), nicknameTest);
			assertEquals(a.getCorreo(), correoTest);
			assertEquals(a.getNombre(), nombreTest);
			assertEquals(a.getApellido(), apellidoTest);
			assertEquals(a.getfNacimiento(), fNacimientoTest);
			assertEquals(a.getInstitucion(), null);
			assertEquals(a.getPatrocinio(),null);
			
		} catch (UsuarioRepetidoException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		};
	}

	@Test
	@Order(3)
	void testRegistrarOrganizadorConUrlOK() {

		String nicknameTest = "gusgui03";
		String correoTest = "gustavoguimerans03@gmail.com";
		String nombreTest = "Gustavo";
		String descTest = "descripcion";
		String urlTest = "www.url.com";
		
		try {
			controladorUsu.AltaOrganizador(nicknameTest, correoTest, nombreTest, descTest, urlTest);
			Organizador o =controladorUsu.getOrganizador(nicknameTest);
			
			assertEquals(o.getNickname(), nicknameTest);
			assertEquals(o.getCorreo(), correoTest);
			assertEquals(o.getNombre(), nombreTest);
			assertEquals(o.getDescripcion(), descTest);
			assertEquals(o.getUrl(), urlTest);

			
		} catch (UsuarioRepetidoException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	
	@Test
	@Order(4)
	void testRegistrarOrganizadorSinUrlOK() {

		String nicknameTest = "gusgui04";
		String correoTest = "gustavoguimerans04@gmail.com";
		String nombreTest = "Gustavo";
		String descTest = "descripcion";
		
		try {
			controladorUsu.AltaOrganizador(nicknameTest, correoTest, nombreTest, descTest, null);
			Organizador o =controladorUsu.getOrganizador(nicknameTest);
			
			assertEquals(o.getNickname(), nicknameTest);
			assertEquals(o.getCorreo(), correoTest);
			assertEquals(o.getNombre(), nombreTest);
			assertEquals(o.getDescripcion(), descTest);
			assertEquals(o.getUrl(), null);

			
		} catch (UsuarioRepetidoException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		};
	}
	
	@Test
	@Order(6)
	void testRegistrarOrganizadorRep() {

		String nicknameTest = "gusgui05";
		String correoTest = "gustavoguimerans05@gmail.com";
		String nombreTest = "Gustavo";
		String descTest = "descripcion";
		String urlTest = "www.url.com";
		
		try {
			controladorUsu.AltaOrganizador(nicknameTest, correoTest, nombreTest, descTest, urlTest);
			
		} catch (UsuarioRepetidoException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		};
		assertThrows(UsuarioRepetidoException.class, ()->{controladorUsu.AltaOrganizador(nicknameTest, correoTest, nombreTest, descTest, urlTest);});
	}
	
	
	@Test
	@Order(7)
	void testRegistrarAsistenteRep() {

		String nicknameTest = "gusgui06";
		String correoTest = "gustavoguimerans06@gmail.com";
		String nombreTest = "Gustavo";
		String apellidoTest = "Guimerans";
		LocalDate fNacimientoTest = LocalDate.parse("12/03/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		Institucion insTest = new Institucion("ins1", "www.ins.com", "institucion1");
		
		try {
			controladorUsu.AltaAsistente(nicknameTest, correoTest, nombreTest, apellidoTest, fNacimientoTest, insTest);
			
		} catch (UsuarioRepetidoException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		};
		assertThrows(UsuarioRepetidoException.class, ()->{controladorUsu.AltaAsistente(nicknameTest, correoTest, nombreTest, apellidoTest, fNacimientoTest, insTest);});
	}
	
		
	}
	

