package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import ServidorCentral.excepciones.UsuarioRepetidoException;
import ServidorCentral.logica.Asistente;
import ServidorCentral.logica.DTUsuarioLista;
import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.Institucion;
import ServidorCentral.logica.Organizador;
import ServidorCentral.logica.Registro;
import ServidorCentral.logica.TipoRegistro;
import ServidorCentral.logica.ManejadorUsuario;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerUsuarioTest {
	
	private static IControllerUsuario controladorUsu;
	
	@BeforeAll
	public static void iniciar() {
		Factory fabrica = Factory.getInstance();
		controladorUsu = fabrica.getIControllerUsuario();
	}

	    @BeforeEach
	    void limpiarUsuarios() {
	        ManejadorUsuario.getinstance().limpiar();}
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
			Asistente a = controladorUsu.getAsistente(nicknameTest);
			
			assertEquals(a.getNickname(), nicknameTest);
			assertEquals(a.getCorreo(), correoTest);
			assertEquals(a.getNombre(), nombreTest);
			assertEquals(a.getApellido(), apellidoTest);
			assertEquals(a.getfNacimiento(), fNacimientoTest);
			assertEquals(a.getInstitucion(), insTest);
			
		} catch (UsuarioRepetidoException e) {
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
			fail(e.getMessage());
			e.printStackTrace();
		};
		assertThrows(UsuarioRepetidoException.class, ()->{controladorUsu.AltaAsistente(nicknameTest, correoTest, nombreTest, apellidoTest, fNacimientoTest, insTest);});
	}
	
	 @Test
	    @Order(8)
	    void testGetUsuarios() {
	        Institucion ins = new Institucion("insTest", "www.test.com", "Institucion Test");
	        try {
	            controladorUsu.AltaAsistente("asist01", "asist01@test.com", "Ana", "Perez",
	                    LocalDate.parse("01/01/2000", DateTimeFormatter.ofPattern("dd/MM/yyyy")), ins);
	            controladorUsu.AltaOrganizador("org01", "org01@test.com", "Carlos", "Desc Organizador", null);
	        } catch (UsuarioRepetidoException e) {
	            fail(e.getMessage());
	        }

	        List<DTUsuarioLista> listaUsuarios = controladorUsu.getUsuarios();
	        assertEquals(2, listaUsuarios.size(), "Debería haber 2 usuarios en la lista");

	        boolean foundAsistente = false;
	        boolean foundOrganizador = false;

	        for (DTUsuarioLista dt : listaUsuarios) {
	            assertNotNull(dt);
	            if (dt.getNickname().equals("asist01")) {
	                foundAsistente = true;
	                assertEquals("Ana", dt.getNombre());
	                assertEquals(0, dt.getEdiciones().size(), "El asistente no debería tener ediciones");
	            } else if (dt.getNickname().equals("org01")) {
	                foundOrganizador = true;
	                assertEquals("Carlos", dt.getNombre());
	                assertEquals(0, dt.getRegistros().size(), "El organizador no debería tener registros");
	            }
	        }

	        assertTrue(foundAsistente, "No se encontró el Asistente en la lista");
	        assertTrue(foundOrganizador, "No se encontró el Organizador en la lista");
	    }

	    @Test
	    @Order(9)
	    void testGetAsistentes() {
	        Institucion ins = new Institucion("insTest2", "www.test2.com", "Institucion Test2");
	        try {
	            controladorUsu.AltaAsistente("asist02", "asist02@test.com", "Luis", "Gomez",
	                    LocalDate.parse("02/02/2002", DateTimeFormatter.ofPattern("dd/MM/yyyy")), ins);
	            controladorUsu.AltaAsistente("asist03", "asist03@test.com", "Marta", "Lopez",
	                    LocalDate.parse("03/03/2003", DateTimeFormatter.ofPattern("dd/MM/yyyy")), null);
	        } catch (UsuarioRepetidoException e) {
	            fail(e.getMessage());
	        }

	        List<Asistente> asistentes = controladorUsu.getAsistentes();
	        assertEquals(2, asistentes.size(), "Debería haber 2 asistentes en la lista");

	        boolean foundLuis = false;
	        boolean foundMarta = false;

	        for (Asistente a : asistentes) {
	            if (a.getNickname().equals("asist02")) {
	                foundLuis = true;
	                assertEquals("Luis", a.getNombre());
	                assertNotNull(a.getInstitucion());
	            } else if (a.getNickname().equals("asist03")) {
	                foundMarta = true;
	                assertEquals("Marta", a.getNombre());
	                assertNull(a.getInstitucion());
	            }
	        }

	        assertTrue(foundLuis, "No se encontró el Asistente Luis");
	        assertTrue(foundMarta, "No se encontró la Asistente Marta");
	    }
	    
	    
	    @Test
	    @Order(10)
	    void testRegistrosFechasEnTipoRegistro() {
	        TipoRegistro tipo = new TipoRegistro("General", "Acceso general", 100f, 50, null);

	        Asistente asistente = new Asistente("nickTest", "correo@test.com", "Nombre", "Apellido",
	                                            LocalDate.of(1999, 5, 5), null);

	        String hoy = LocalDate.now().toString();

	        Registro r1 = new Registro(100f, null, asistente, tipo);
	        Registro r2 = new Registro(200f, null, asistente, tipo);

	        tipo.getRegistros().add(r1);
	        tipo.getRegistros().add(r2);

	        List<String> fechas = tipo.registrosFechas();

	        assertEquals(2, fechas.size(), "Debe devolver 2 fechas");
	        assertTrue(fechas.stream().allMatch(f -> f.equals(hoy)),
	                "Todas las fechas deben ser iguales a la fecha de hoy: " + hoy);
	    }

	}