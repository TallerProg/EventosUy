package ServidorCentral.logica;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import ServidorCentral.excepciones.UsuarioRepetidoException;

public class CargarDatos {
	
		    public static void inicializar(IControllerUsuario ICU, IControllerEvento ICE) throws Exception {
		    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		    	// ------------------
		    	// Crear categorías
		    	// ------------------
		    	ICE.altaCategoria("Tecnología");
		    	ICE.altaCategoria("Innovación");
		    	ICE.altaCategoria("Literatura");
		    	ICE.altaCategoria("Cultura");
		    	ICE.altaCategoria("Música");
		    	ICE.altaCategoria("Deporte");
		    	ICE.altaCategoria("Salud");
		    	ICE.altaCategoria("Entretenimiento");
		    	ICE.altaCategoria("Agro");
		    	ICE.altaCategoria("Negocios");
		    	ICE.altaCategoria("Moda");
		    	ICE.altaCategoria("Investigación");

		    	Categoria CA01 = ICE.findCategoria("Tecnología");
		    	Categoria CA02 = ICE.findCategoria("Innovación");
		    	Categoria CA03 = ICE.findCategoria("Literatura");
		    	Categoria CA04 = ICE.findCategoria("Cultura");
		    	Categoria CA05 = ICE.findCategoria("Música");
		    	Categoria CA06 = ICE.findCategoria("Deporte");
		    	Categoria CA07 = ICE.findCategoria("Salud");
		    	Categoria CA08 = ICE.findCategoria("Entretenimiento");
		    	Categoria CA09 = ICE.findCategoria("Agro");
		    	Categoria CA10 = ICE.findCategoria("Negocios");
		    	Categoria CA11 = ICE.findCategoria("Moda");
		    	Categoria CA12 = ICE.findCategoria("Investigación");
		    	
		    	// ------------------
		    	// Crear eventos
		    	// ------------------
		    	try {
		    	    // EV01
		    	    ICE.altaEvento(
		    	        "Conferencia de Tecnología",
		    	        "Evento sobre innovación tecnológica",
		    	        LocalDate.parse("10/01/2025", formatter),
		    	        "CONFTEC",
		    	        Arrays.asList(CA01, CA02)
		    	    );

		    	    // EV02
		    	    ICE.altaEvento(
		    	        "Feria del Libro",
		    	        "Encuentro anual de literatura",
		    	        LocalDate.parse("01/02/2025", formatter),
		    	        "FERLIB",
		    	        Arrays.asList(CA03, CA04)
		    	    );

		    	    // EV03
		    	    ICE.altaEvento(
		    	        "Montevideo Rock",
		    	        "Festival de rock con artistas nacionales e internacionales",
		    	        LocalDate.parse("15/03/2023", formatter),
		    	        "MONROCK",
		    	        Arrays.asList(CA04, CA05)
		    	    );

		    	    // EV04
		    	    ICE.altaEvento(
		    	        "Maratón de Montevideo",
		    	        "Competencia deportiva anual en la capital",
		    	        LocalDate.parse("01/01/2022", formatter),
		    	        "MARATON",
		    	        Arrays.asList(CA06, CA07)
		    	    );

		    	    // EV05
		    	    ICE.altaEvento(
		    	        "Montevideo Comics",
		    	        "Convención de historietas, cine y cultura geek",
		    	        LocalDate.parse("10/04/2024", formatter),
		    	        "COMICS",
		    	        Arrays.asList(CA04, CA08)
		    	    );

		    	    // EV06
		    	    ICE.altaEvento(
		    	        "Expointer Uruguay",
		    	        "Exposición internacional agropecuaria y ganadera",
		    	        LocalDate.parse("12/12/2024", formatter),
		    	        "EXPOAGRO",
		    	        Arrays.asList(CA09, CA10)
		    	    );

		    	    // EV07
		    	    ICE.altaEvento(
		    	        "Montevideo Fashion Week",
		    	        "Pasarela de moda uruguaya e internacional",
		    	        LocalDate.parse("20/07/2025", formatter),
		    	        "MFASHION",
		    	        Arrays.asList(CA04, CA11)
		    	    );

		    	} catch(Exception e) {
		    	    JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
		    	ManejadorInstitucion mI = ManejadorInstitucion.getInstance();
		    	// ------------------
		    	// Crear Instituciones
		    	// ------------------
		    	// INS01
		    	Institucion INS01 = new Institucion(
		    	    "Facultad de Ingeniería",
		    	    "https://www.fing.edu.uy",
		    	    "Facultad de Ingeniería de la Universidad de la República"
		    	);
		    	mI.agregarInstitucion(INS01);

		    	// INS02
		    	Institucion INS02 = new Institucion(
		    	    "ORT Uruguay",
		    	    "https://ort.edu.uy",
		    	    "Universidad privada enfocada en tecnología y gestión"
		    	);
		    	mI.agregarInstitucion(INS02);

		    	// INS03
		    	Institucion INS03 = new Institucion(
		    	    "Universidad Católica del Uruguay",
		    	    "https://ucu.edu.uy",
		    	    "Institución de educación superior privada"
		    	);
		    	mI.agregarInstitucion(INS03);

		    	// INS04
		    	Institucion INS04 = new Institucion(
		    	    "Antel",
		    	    "https://antel.com.uy",
		    	    "Empresa estatal de telecomunicaciones"
		    	);
		    	mI.agregarInstitucion(INS04);

		    	// INS05
		    	Institucion INS05 = new Institucion(
		    	    "Agencia Nacional de Investigación e Innovación (ANII)",
		    	    "https://anii.org.uy",
		    	    "Fomenta la investigación y la innovación en Uruguay"
		    	);
		    	mI.agregarInstitucion(INS05);
		    
		    	// ------------------
		    	// Crear Asistentes
		    	// ------------------
		    	try {
		    	    ICU.AltaAsistente(
		    	        "atorres",
		    	        "atorres@gmail.com",
		    	        "Ana",
		    	        "Torres",
		    	        LocalDate.parse("12/05/1990", formatter),
		    	        INS01
		    	    );

		    	    ICU.AltaAsistente(
		    	        "msilva",
		    	        "martin.silva@fing.edu.uy",
		    	        "Martin",
		    	        "Silva",
		    	        LocalDate.parse("21/08/1987", formatter),
		    	        INS01
		    	    );

		    	    ICU.AltaAsistente(
		    	        "sofirod",
		    	        "srodriguez@outlook.com",
		    	        "Sofia",
		    	        "Rodriguez",
		    	        LocalDate.parse("03/02/1995", formatter),
		    	        INS03
		    	    );

		    	    ICU.AltaAsistente(
		    	        "vale23",
		    	        "valentina.costa@mail.com",
		    	        "Valentina",
		    	        "Costa",
		    	        LocalDate.parse("01/12/1992", formatter),
		    	        null
		    	    );

		    	    ICU.AltaAsistente(
		    	        "luciag",
		    	        "lucia.garcia@mail.com",
		    	        "Lucía",
		    	        "García",
		    	        LocalDate.parse("09/11/1993", formatter),
		    	        null
		    	    );

		    	    ICU.AltaAsistente(
		    	        "andrearod",
		    	        "andrea.rod@mail.com",
		    	        "Andrea",
		    	        "Rodríguez",
		    	        LocalDate.parse("10/06/2000", formatter),
		    	        INS05
		    	    );

		    	    ICU.AltaAsistente(
		    	        "AnaG",
		    	        "ana.gomez@hotmail.com",
		    	        "Ana",
		    	        "Gómez",
		    	        LocalDate.parse("15/03/1998", formatter),
		    	        null
		    	    );

		    	    ICU.AltaAsistente(
		    	        "JaviL",
		    	        "javier.lopez@outlook.com",
		    	        "Javier",
		    	        "López",
		    	        LocalDate.parse("22/07/1995", formatter),
		    	        null
		    	    );

		    	    ICU.AltaAsistente(
		    	        "MariR",
		    	        "maria.rodriguez@gmail.com",
		    	        "María",
		    	        "Rodríguez",
		    	        LocalDate.parse("10/11/2000", formatter),
		    	        null
		    	    );

		    	    ICU.AltaAsistente(
		    	        "SofiM",
		    	        "sofia.martinez@yahoo.com",
		    	        "Sofía",
		    	        "Martínez",
		    	        LocalDate.parse("05/02/1997", formatter),
		    	        null
		    	    );

		    	} catch(Exception e) {
		    	    JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
		    	
		    	// ------------------
		    	// Crear Organizadores
		    	// ------------------
		    	try {
		    	    ICU.AltaOrganizador(
		    	        "miseventos",
		    	        "contacto@miseventos.com",
		    	        "MisEventos",
		    	        "Empresa de organización de eventos.",
		    	        "https://miseventos.com"
		    	    );

		    	    ICU.AltaOrganizador(
		    	        "techcorp",
		    	        "info@techcorp.com",
		    	        "Corporación Tecnológica",
		    	        "Empresa líder en tecnologías de la información.",
		    	        null
		    	    );

		    	    ICU.AltaOrganizador(
		    	        "imm",
		    	        "contacto@imm.gub.uy",
		    	        "Intendencia de Montevideo",
		    	        "Gobierno departamental de Montevideo.",
		    	        "https://montevideo.gub.uy"
		    	    );

		    	    ICU.AltaOrganizador(
		    	        "udelar",
		    	        "contacto@udelar.edu.uy",
		    	        "Universidad de la República",
		    	        "Universidad pública de Uruguay.",
		    	        "https://udelar.edu.uy"
		    	    );

		    	    ICU.AltaOrganizador(
		    	        "mec",
		    	        "mec@mec.gub.uy",
		    	        "Ministerio de Educación y Cultura",
		    	        "Institución pública promotora de cultura.",
		    	        "https://mec.gub.uy"
		    	    );

		    	} catch(Exception e) {
		    	    JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
		    	
		    	
		    	// ------------------
		    	// Crear Ediciones de Eventos
		    	// ------------------
		    	try {
		    	    // EDEV01
		    	    ICE.altaEdicionDeEvento(
		    	        "Montevideo Rock 2025",
		    	        "MONROCK25",
		    	        "Montevideo",
		    	        "Uruguay",
		    	        LocalDate.parse("20/11/2025", formatter),
		    	        LocalDate.parse("22/11/2025", formatter),
		    	        ICE.getEvento("Montevideo Rock"),
		    	        ICU.getOrganizador("imm")
		    	    );

		    	    // EDEV02
		    	    ICE.altaEdicionDeEvento(
		    	        "Maratón de Montevideo 2025",
		    	        "MARATON25",
		    	        "Montevideo",
		    	        "Uruguay",
		    	        LocalDate.parse("14/09/2025", formatter),
		    	        LocalDate.parse("14/09/2025", formatter),
		    	        ICE.getEvento("Maratón de Montevideo"),
		    	        ICU.getOrganizador("imm")
		    	    );

		    	    // EDEV03
		    	    ICE.altaEdicionDeEvento(
		    	        "Maratón de Montevideo 2024",
		    	        "MARATON24",
		    	        "Montevideo",
		    	        "Uruguay",
		    	        LocalDate.parse("14/09/2024", formatter),
		    	        LocalDate.parse("14/09/2024", formatter),
		    	        ICE.getEvento("Maratón de Montevideo"),
		    	        ICU.getOrganizador("imm")
		    	    );

		    	    // EDEV04
		    	    ICE.altaEdicionDeEvento(
		    	        "Maratón de Montevideo 2022",
		    	        "MARATON22",
		    	        "Montevideo",
		    	        "Uruguay",
		    	        LocalDate.parse("14/09/2022", formatter),
		    	        LocalDate.parse("14/09/2022", formatter),
		    	        ICE.getEvento("Maratón de Montevideo"),
		    	        ICU.getOrganizador("imm")
		    	    );

		    	    // EDEV05
		    	    ICE.altaEdicionDeEvento(
		    	        "Montevideo Comics 2024",
		    	        "COMICS24",
		    	        "Montevideo",
		    	        "Uruguay",
		    	        LocalDate.parse("18/07/2024", formatter),
		    	        LocalDate.parse("21/07/2024", formatter),
		    	        ICE.getEvento("Montevideo Comics"),
		    	        ICU.getOrganizador("miseventos")
		    	    );

		    	    // EDEV06
		    	    ICE.altaEdicionDeEvento(
		    	        "Montevideo Comics 2025",
		    	        "COMICS25",
		    	        "Montevideo",
		    	        "Uruguay",
		    	        LocalDate.parse("04/08/2025", formatter),
		    	        LocalDate.parse("06/08/2025", formatter),
		    	        ICE.getEvento("Montevideo Comics"),
		    	        ICU.getOrganizador("miseventos")
		    	    );

		    	    // EDEV07
		    	    ICE.altaEdicionDeEvento(
		    	        "Expointer Uruguay 2025",
		    	        "EXPOAGRO25",
		    	        "Durazno",
		    	        "Uruguay",
		    	        LocalDate.parse("11/09/2025", formatter),
		    	        LocalDate.parse("17/09/2025", formatter),
		    	        ICE.getEvento("Expointer Uruguay"),
		    	        ICU.getOrganizador("miseventos")
		    	    );

		    	    // EDEV08
		    	    ICE.altaEdicionDeEvento(
		    	        "Tecnología Punta del Este 2026",
		    	        "CONFTECH26",
		    	        "Punta del Este",
		    	        "Uruguay",
		    	        LocalDate.parse("06/04/2026", formatter),
		    	        LocalDate.parse("10/04/2026", formatter),
		    	        ICE.getEvento("Conferencia de Tecnología"),
		    	        ICU.getOrganizador("udelar")
		    	    );

		    	    // EDEV09
		    	    ICE.altaEdicionDeEvento(
		    	        "Mobile World Congress 2025",
		    	        "MWC",
		    	        "Barcelona",
		    	        "España",
		    	        LocalDate.parse("12/12/2025", formatter),
		    	        LocalDate.parse("15/12/2025", formatter),
		    	        ICE.getEvento("Conferencia de Tecnología"),
		    	        ICU.getOrganizador("techcorp")
		    	    );

		    	    // EDEV10
		    	    ICE.altaEdicionDeEvento(
		    	        "Web Summit 2026",
		    	        "WS26",
		    	        "Lisboa",
		    	        "Portugal",
		    	        LocalDate.parse("13/01/2026", formatter),
		    	        LocalDate.parse("01/02/2026", formatter),
		    	        ICE.getEvento("Conferencia de Tecnología"),
		    	        ICU.getOrganizador("techcorp")
		    	    );

		    	} catch(Exception e) {
		    	    JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
		    	// ------------------
		    	// Crear TipoRegistro
		    	// ------------------
		    	
		    	try {
		    	    // EDEV01 - Montevideo Rock 2025
		    	    ICE.altaTipoRegistro("General", "Acceso general a Montevideo Rock (2 días)", 1500f, 2000, ICE.findEdicion("Montevideo Rock 2025"));
		    	    ICE.altaTipoRegistro("VIP", "Incluye backstage + acceso preferencial", 4000f, 200, ICE.findEdicion("Montevideo Rock 2025"));

		    	    // EDEV02 - Maratón de Montevideo 2025
		    	    ICE.altaTipoRegistro("Corredor 42K", "Inscripción a la maratón completa", 1200f, 499, ICE.findEdicion("Maratón de Montevideo 2025"));
		    	    ICE.altaTipoRegistro("Corredor 21K", "Inscripción a la media maratón", 800f, 700, ICE.findEdicion("Maratón de Montevideo 2025"));
		    	    ICE.altaTipoRegistro("Corredor 10K", "Inscripción a la carrera 10K", 500f, 1000, ICE.findEdicion("Maratón de Montevideo 2025"));

		    	    // EDEV03 - Maratón de Montevideo 2024
		    	    ICE.altaTipoRegistro("Corredor 42K", "Inscripción a la maratón completa", 1000f, 300, ICE.findEdicion("Maratón de Montevideo 2024"));
		    	    ICE.altaTipoRegistro("Corredor 21K", "Inscripción a la media maratón", 500f, 500, ICE.findEdicion("Maratón de Montevideo 2024"));

		    	    // EDEV04 - Maratón de Montevideo 2022
		    	    ICE.altaTipoRegistro("Corredor 42K", "Inscripción a la maratón completa", 1100f, 450, ICE.findEdicion("Maratón de Montevideo 2022"));
		    	    ICE.altaTipoRegistro("Corredor 21K", "Inscripción a la media maratón", 900f, 750, ICE.findEdicion("Maratón de Montevideo 2022"));
		    	    ICE.altaTipoRegistro("Corredor 10K", "Inscripción a la carrera 10K", 650f, 1400, ICE.findEdicion("Maratón de Montevideo 2022"));

		    	    // EDEV05 - Montevideo Comics 2024
		    	    ICE.altaTipoRegistro("General", "Entrada para los 4 días de Montevideo Comics", 600f, 1500, ICE.findEdicion("Montevideo Comics 2024"));
		    	    ICE.altaTipoRegistro("Cosplayer", "Entrada especial con acreditación para concurso cosplay", 300f, 50, ICE.findEdicion("Montevideo Comics 2024"));

		    	    // EDEV06 - Montevideo Comics 2025
		    	    ICE.altaTipoRegistro("General", "Entrada para los 4 días de Montevideo Comics", 800f, 1000, ICE.findEdicion("Montevideo Comics 2025"));
		    	    ICE.altaTipoRegistro("Cosplayer", "Entrada especial con acreditación para concurso cosplay", 500f, 100, ICE.findEdicion("Montevideo Comics 2025"));

		    	    // EDEV07 - Expointer Uruguay 2025
		    	    ICE.altaTipoRegistro("General", "Acceso a la exposición agropecuaria", 300f, 5000, ICE.findEdicion("Expointer Uruguay 2025"));
		    	    ICE.altaTipoRegistro("Empresarial", "Acceso para empresas + networking", 2000f, 5, ICE.findEdicion("Expointer Uruguay 2025"));

		    	    // EDEV08 - Tecnología Punta del Este 2026
		    	    ICE.altaTipoRegistro("Full", "Acceso ilimitado + Cena de gala", 1800f, 300, ICE.findEdicion("Tecnología Punta del Este 2026"));
		    	    ICE.altaTipoRegistro("General", "Acceso general", 1500f, 500, ICE.findEdicion("Tecnología Punta del Este 2026"));
		    	    ICE.altaTipoRegistro("Estudiante", "Acceso para estudiantes", 1000f, 50, ICE.findEdicion("Tecnología Punta del Este 2026"));

		    	    // EDEV09 - Mobile World Congress 2025
		    	    ICE.altaTipoRegistro("Full", "Acceso ilimitado + Cena de gala", 750f, 550, ICE.findEdicion("Mobile World Congress 2025"));
		    	    ICE.altaTipoRegistro("General", "Acceso general", 500f, 400, ICE.findEdicion("Mobile World Congress 2025"));
		    	    ICE.altaTipoRegistro("Estudiante", "Acceso para estudiantes", 250f, 400, ICE.findEdicion("Mobile World Congress 2025"));

		    	    // EDEV10 - Web Summit 2026
		    	    ICE.altaTipoRegistro("Full", "Acceso ilimitado + Cena de gala", 900f, 30, ICE.findEdicion("Web Summit 2026"));
		    	    ICE.altaTipoRegistro("General", "Acceso general", 650f, 5, ICE.findEdicion("Web Summit 2026"));
		    	    ICE.altaTipoRegistro("Estudiante", "Acceso para estudiantes", 300f, 1, ICE.findEdicion("Web Summit 2026"));

		    	} catch(Exception e) {
		    	    JOptionPane.showMessageDialog(null, e.getMessage());
		    	}

		        // ------------------
		    	// Crear Patrocinios
		    	// ------------------
		    	
		    	try {
		    	    // PAT1
		    	    Patrocinio PAT1 = new Patrocinio(
		    	        "TECHFING", 
		    	        LocalDate.parse("21/08/2025", formatter), 
		    	        4, 
		    	        20000f, 
		    	        ETipoNivel.Oro, 
		    	        INS01, 
		    	        ICE.findEdicion("Tecnología Punta del Este 2026"), 
		    	        ICE.findEdicion("Tecnología Punta del Este 2026").getEdicionTR("Estudiante")
		    	    );
		    	    ICE.findEdicion("Tecnología Punta del Este 2026").addLinkPatrocinio(PAT1);
		    	    ICE.findEdicion("Tecnología Punta del Este 2026").getEdicionTR("Estudiante").addLinkPatrocinio(PAT1);
		    	    INS01.agregarPatrocinio(PAT1);

		    	    // PAT2
		    	    Patrocinio PAT2 = new Patrocinio(
		    	        "TECHANII", 
		    	        LocalDate.parse("20/08/2025", formatter), 
		    	        1, 
		    	        10000f, 
		    	        ETipoNivel.Plata, 
		    	        INS05, 
		    	        ICE.findEdicion("Tecnología Punta del Este 2026"), 
		    	        ICE.findEdicion("Tecnología Punta del Este 2026").getEdicionTR("General")
		    	    );
		    	    ICE.findEdicion("Tecnología Punta del Este 2026").addLinkPatrocinio(PAT2);
		    	    ICE.findEdicion("Tecnología Punta del Este 2026").getEdicionTR("General").addLinkPatrocinio(PAT2);
		    	    INS05.agregarPatrocinio(PAT2);

		    	    // PAT3
		    	    Patrocinio PAT3 = new Patrocinio(
		    	        "CORREANTEL", 
		    	        LocalDate.parse("04/03/2025", formatter), 
		    	        10, 
		    	        25000f, 
		    	        ETipoNivel.Platino, 
		    	        INS04, 
		    	        ICE.findEdicion("Maratón de Montevideo 2025"), 
		    	        ICE.findEdicion("Maratón de Montevideo 2025").getEdicionTR("Corredor 10K")
		    	    );
		    	    ICE.findEdicion("Maratón de Montevideo 2025").addLinkPatrocinio(PAT3);
		    	    ICE.findEdicion("Maratón de Montevideo 2025").getEdicionTR("Corredor 10K").addLinkPatrocinio(PAT3);
		    	    INS04.agregarPatrocinio(PAT3);

		    	    // PAT4
		    	    Patrocinio PAT4 = new Patrocinio(
		    	        "EXPOCAT", 
		    	        LocalDate.parse("05/05/2025", formatter), 
		    	        10, 
		    	        15000f, 
		    	        ETipoNivel.Bronce, 
		    	        INS03, 
		    	        ICE.findEdicion("Expointer Uruguay 2025"), 
		    	        ICE.findEdicion("Expointer Uruguay 2025").getEdicionTR("General")
		    	    );
		    	    ICE.findEdicion("Expointer Uruguay 2025").addLinkPatrocinio(PAT4);
		    	    ICE.findEdicion("Expointer Uruguay 2025").getEdicionTR("General").addLinkPatrocinio(PAT4);
		    	    INS03.agregarPatrocinio(PAT4);

		    	} catch(Exception e) {
		    	    JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
		    	
		    	// ------------------
		    	// Crear Registros
		    	// ------------------
		    	
		    	try {
		    	    // RE01 - Montevideo Rock 2025
		    	    ICE.altaRegistro("Montevideo Rock 2025", "sofirod", "VIP"); // TR02

		    	    // RE02 - Maratón de Montevideo 2024
		    	    ICE.altaRegistro("Maratón de Montevideo 2024", "sofirod", "Corredor 21K"); // TR07

		    	    // RE03 - Web Summit 2026
		    	    ICE.altaRegistro("Web Summit 2026", "MariR", "Estudiante"); // TR25

		    	    // RE04 - Maratón de Montevideo 2025
		    	    ICE.altaRegistro("Maratón de Montevideo 2025", "sofirod", "Corredor 42K"); // TR03

		    	    // RE05 - Mobile World Congress 2025
		    	    ICE.altaRegistro("Mobile World Congress 2025", "vale23", "Full"); // TR20

		    	    // RE06 - Maratón de Montevideo 2025
		    	    ICE.altaRegistro("Maratón de Montevideo 2025", "AnaG", "Corredor 10K"); // TR05

		    	    // RE07 - Maratón de Montevideo 2025
		    	    ICE.altaRegistro("Maratón de Montevideo 2025", "JaviL", "Corredor 21K"); // TR04

		    	    // RE08 - Montevideo Comics 2025
		    	    ICE.altaRegistro("Montevideo Comics 2025", "andrearod", "Cosplayer"); // TR14

		    	    // RE09 - Montevideo Comics 2024
		    	    ICE.altaRegistro("Montevideo Comics 2024", "SofiM", "General"); // TR11

		    	} catch(Exception e) {
		    	    JOptionPane.showMessageDialog(null, e.getMessage());
		    	}
				
		    
		    	
		    }
}
		    
