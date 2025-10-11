package com.model;
import ServidorCentral.logica.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class CargarDatos {


	public static void inicializar(IControllerUsuario ICU, IControllerEvento Ice) throws Exception {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		// ------------------
		// Crear categorías
		// ------------------
		Ice.altaCategoria("Tecnología");
		Ice.altaCategoria("Innovación");
		Ice.altaCategoria("Literatura");
		Ice.altaCategoria("Cultura");
		Ice.altaCategoria("Música");
		Ice.altaCategoria("Deporte");
		Ice.altaCategoria("Salud");
		Ice.altaCategoria("Entretenimiento");
		Ice.altaCategoria("Agro");
		Ice.altaCategoria("Negocios");
		Ice.altaCategoria("Moda");
		Ice.altaCategoria("Investigación");

		Categoria CA01 = Ice.findCategoria("Tecnología");
		Categoria CA02 = Ice.findCategoria("Innovación");
		Categoria CA03 = Ice.findCategoria("Literatura");
		Categoria CA04 = Ice.findCategoria("Cultura");
		Categoria CA05 = Ice.findCategoria("Música");
		Categoria CA06 = Ice.findCategoria("Deporte");
		Categoria CA07 = Ice.findCategoria("Salud");
		Categoria CA08 = Ice.findCategoria("Entretenimiento");
		Categoria CA09 = Ice.findCategoria("Agro");
		Categoria CA10 = Ice.findCategoria("Negocios");
		Categoria CA11 = Ice.findCategoria("Moda");
		// ------------------
		// Crear eventos
		// ------------------
		try {
			// EV01
			Ice.altaEvento("Conferencia de Tecnología", "Evento sobre innovación tecnológica",
					LocalDate.parse("10/01/2025", formatter), "CONFTEC", Arrays.asList(CA01, CA02));

			// EV02
			Ice.altaEvento("Feria del Libro", "Encuentro anual de literatura", LocalDate.parse("01/02/2025", formatter),
					"FERLIB", Arrays.asList(CA03, CA04));

			// EV03
			Ice.altaEvento("Montevideo Rock", "Festival de rock con artistas nacionales e internacionales",
					LocalDate.parse("15/03/2023", formatter), "MONROCK", Arrays.asList(CA04, CA05));

			// EV04
			Ice.altaEvento("Maratón de Montevideo", "Competencia deportiva anual en la capital",
					LocalDate.parse("01/01/2022", formatter), "MARATON", Arrays.asList(CA06, CA07));

			// EV05
			Ice.altaEvento("Montevideo Comics", "Convención de historietas, cine y cultura geek",
					LocalDate.parse("10/04/2024", formatter), "COMICS", Arrays.asList(CA04, CA08));

			// EV06
			Ice.altaEvento("Expointer Uruguay", "Exposición internacional agropecuaria y ganadera",
					LocalDate.parse("12/12/2024", formatter), "EXPOAGRO", Arrays.asList(CA09, CA10));

			// EV07
			Ice.altaEvento("Montevideo Fashion Week", "Pasarela de moda uruguaya e internacional",
					LocalDate.parse("20/07/2025", formatter), "MFASHION", Arrays.asList(CA04, CA11));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		ManejadorInstitucion mI = ManejadorInstitucion.getInstance();
		// ------------------
		// Crear Instituciones
		// ------------------
		// INS01
		Institucion INS01 = new Institucion("Facultad de Ingeniería", "https://www.fing.edu.uy",
				"Facultad de Ingeniería de la Universidad de la República",null);
		mI.agregarInstitucion(INS01);

		// INS02
		Institucion INS02 = new Institucion("ORT Uruguay", "https://ort.edu.uy",
				"Universidad privada enfocada en tecnología y gestión",null);
		mI.agregarInstitucion(INS02);

		// INS03
		Institucion INS03 = new Institucion("Universidad Católica del Uruguay", "https://ucu.edu.uy",
				"Institución de educación superior privada",null);
		mI.agregarInstitucion(INS03);

		// INS04
		Institucion INS04 = new Institucion("Antel", "https://antel.com.uy", "Empresa estatal de telecomunicaciones",null);
		mI.agregarInstitucion(INS04);

		// INS05
		Institucion INS05 = new Institucion("Agencia Nacional de Investigación e Innovación (ANII)",
				"https://anii.org.uy", "Fomenta la investigación y la innovación en Uruguay",null);
		mI.agregarInstitucion(INS05);

		// ------------------
		// Crear Asistentes
		// ------------------
		try {
			ICU.altaAsistente("atorres", "atorres@gmail.com", "Ana", "Torres",
			        LocalDate.parse("12/05/1990", formatter), INS01, "12345678");

			ICU.altaAsistente("msilva", "martin.silva@fing.edu.uy", "Martin", "Silva",
			        LocalDate.parse("21/08/1987", formatter), INS01, "12345678");

			ICU.altaAsistente("sofirod", "srodriguez@outlook.com", "Sofia", "Rodriguez",
			        LocalDate.parse("03/02/1995", formatter), INS03, "12345678");

			ICU.altaAsistente("vale23", "valentina.costa@mail.com", "Valentina", "Costa",
			        LocalDate.parse("01/12/1992", formatter), null, "12345678");

			ICU.altaAsistente("luciag", "lucia.garcia@mail.com", "Lucía", "García",
			        LocalDate.parse("09/11/1993", formatter), null, "12345678");

			ICU.altaAsistente("andrearod", "andrea.rod@mail.com", "Andrea", "Rodríguez",
			        LocalDate.parse("10/06/2000", formatter), INS05, "12345678");

			ICU.altaAsistente("AnaG", "ana.gomez@hotmail.com", "Ana", "Gómez",
			        LocalDate.parse("15/03/1998", formatter), null, "12345678");

			ICU.altaAsistente("JaviL", "javier.lopez@outlook.com", "Javier", "López",
			        LocalDate.parse("22/07/1995", formatter), null, "12345678");

			ICU.altaAsistente("MariR", "maria.rodriguez@gmail.com", "María", "Rodríguez",
			        LocalDate.parse("10/11/2000", formatter), null, "12345678");

			ICU.altaAsistente("SofiM", "sofia.martinez@yahoo.com", "Sofía", "Martínez",
			        LocalDate.parse("05/02/1997", formatter), null, "12345678");


		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		// ------------------
		// Crear Organizadores
		// ------------------
		try {
			ICU.altaOrganizador("miseventos", "contacto@miseventos.com", "MisEventos",
					"Empresa de organización de eventos.", "https://miseventos.com", "12345678");

			ICU.altaOrganizador("techcorp", "info@techcorp.com", "Corporación Tecnológica",
					"Empresa líder en tecnologías de la información.", null, "12345678");

			ICU.altaOrganizador("imm", "contacto@imm.gub.uy", "Intendencia de Montevideo",
					"Gobierno departamental de Montevideo.", "https://montevideo.gub.uy", "12345678");

			ICU.altaOrganizador("udelar", "contacto@udelar.edu.uy", "Universidad de la República",
					"Universidad pública de Uruguay.", "https://udelar.edu.uy", "12345678");

			ICU.altaOrganizador("mec", "mec@mec.gub.uy", "Ministerio de Educación y Cultura",
					"Institución pública promotora de cultura.", "https://mec.gub.uy", "12345678");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		// ------------------
		// Crear Ediciones de Eventos
		// ------------------
		try {
			// EDEV01
			Ice.altaEdicionDeEvento("Montevideo Rock 2025", "MONROCK25", "Montevideo", "Uruguay",
					LocalDate.parse("20/11/2025", formatter), LocalDate.parse("22/11/2025", formatter),LocalDate.parse("12/03/2025",formatter),
					Ice.getEvento("Montevideo Rock"), ICU.getOrganizador("imm"), "Rambla de Montevideo");

			// EDEV02 
			Ice.altaEdicionDeEvento("Maratón de Montevideo 2025", "MARATON25", "Montevideo", "Uruguay",
					LocalDate.parse("14/09/2025", formatter), LocalDate.parse("14/09/2025", formatter),LocalDate.parse("05/02/2025", formatter),
					Ice.getEvento("Maratón de Montevideo"), ICU.getOrganizador("imm"), "Rambla de Montevideo");

			// EDEV03 
			Ice.altaEdicionDeEvento("Maratón de Montevideo 2024", "MARATON24", "Montevideo", "Uruguay",
					LocalDate.parse("14/09/2024", formatter), LocalDate.parse("14/09/2024", formatter),LocalDate.parse("21/04/2024", formatter),
					Ice.getEvento("Maratón de Montevideo"), ICU.getOrganizador("imm"), "Rambla de Montevideo");

			// EDEV04 

			Ice.altaEdicionDeEvento("Maratón de Montevideo 2022", "MARATON22", "Montevideo", "Uruguay",
					LocalDate.parse("14/09/2022", formatter), LocalDate.parse("14/09/2022", formatter),LocalDate.parse("21/05/2022", formatter),
					Ice.getEvento("Maratón de Montevideo"), ICU.getOrganizador("imm"), "Rambla de Montevideo");

			// EDEV05 
			Ice.altaEdicionDeEvento("Montevideo Comics 2024", "COMICS24", "Montevideo", "Uruguay",
					LocalDate.parse("18/07/2024", formatter), LocalDate.parse("21/07/2024", formatter),LocalDate.parse("20/06/2024", formatter),
					Ice.getEvento("Montevideo Comics"), ICU.getOrganizador("miseventos"), "centro de Convenciones");

			// EDEV06 
			Ice.altaEdicionDeEvento("Montevideo Comics 2025", "COMICS25", "Montevideo", "Uruguay",
					LocalDate.parse("04/08/2025", formatter), LocalDate.parse("06/08/2025", formatter),LocalDate.parse("04/07/2025", formatter),
					Ice.getEvento("Montevideo Comics"), ICU.getOrganizador("miseventos"), "centro de Convenciones");

			// EDEV07 
			Ice.altaEdicionDeEvento("Expointer Uruguay 2025", "EXPOAGRO25", "Durazno", "Uruguay",
					LocalDate.parse("11/09/2025", formatter), LocalDate.parse("17/09/2025", formatter),LocalDate.parse("01/02/2025", formatter),
					Ice.getEvento("Expointer Uruguay"), ICU.getOrganizador("miseventos"), "Predio Ferial");

			// EDEV08 
			Ice.altaEdicionDeEvento("Tecnología Punta del Este 2026", "CONFTECH26", "Punta del Este", "Uruguay",
					LocalDate.parse("06/04/2026", formatter), LocalDate.parse("10/04/2026", formatter),LocalDate.parse("01/08/2025", formatter),
					Ice.getEvento("Conferencia de Tecnología"), ICU.getOrganizador("udelar"), "Hotel Conrad");

			// EDEV09 
			Ice.altaEdicionDeEvento("Mobile World Congress 2025", "MWC", "Barcelona", "España",
					LocalDate.parse("12/12/2025", formatter), LocalDate.parse("15/12/2025", formatter),LocalDate.parse("21/08/2025", formatter),
					Ice.getEvento("Conferencia de Tecnología"), ICU.getOrganizador("techcorp"), "Fira Gran Via");

			// EDEV10 
			Ice.altaEdicionDeEvento("Web Summit 2026", "WS26", "Lisboa", "Portugal",
					LocalDate.parse("13/01/2026", formatter), LocalDate.parse("01/02/2026", formatter),LocalDate.parse("04/06/2025", formatter),
					Ice.getEvento("Conferencia de Tecnología"), ICU.getOrganizador("techcorp"), "Parque das Nações");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		// ------------------
		// Crear TipoRegistro
		// ------------------

		try {
			// EDEV01 - Montevideo Rock 2025
			Ice.altaTipoRegistro("General", "Acceso general a Montevideo Rock (2 días)", 1500f, 2000,
					Ice.findEdicion("Montevideo Rock 2025"));
			Ice.altaTipoRegistro("VIP", "Incluye backstage + acceso preferencial", 4000f, 200,
					Ice.findEdicion("Montevideo Rock 2025"));

			// EDEV02 - Maratón de Montevideo 2025
			Ice.altaTipoRegistro("Corredor 42K", "Inscripción a la maratón completa", 1200f, 499,
					Ice.findEdicion("Maratón de Montevideo 2025"));
			Ice.altaTipoRegistro("Corredor 21K", "Inscripción a la media maratón", 800f, 700,
					Ice.findEdicion("Maratón de Montevideo 2025"));
			Ice.altaTipoRegistro("Corredor 10K", "Inscripción a la carrera 10K", 500f, 1000,
					Ice.findEdicion("Maratón de Montevideo 2025"));

			// EDEV03 - Maratón de Montevideo 2024
			Ice.altaTipoRegistro("Corredor 42K", "Inscripción a la maratón completa", 1000f, 300,
					Ice.findEdicion("Maratón de Montevideo 2024"));
			Ice.altaTipoRegistro("Corredor 21K", "Inscripción a la media maratón", 500f, 500,
					Ice.findEdicion("Maratón de Montevideo 2024"));

			// EDEV04 - Maratón de Montevideo 2022
			Ice.altaTipoRegistro("Corredor 42K", "Inscripción a la maratón completa", 1100f, 450,
					Ice.findEdicion("Maratón de Montevideo 2022"));
			Ice.altaTipoRegistro("Corredor 21K", "Inscripción a la media maratón", 900f, 750,
					Ice.findEdicion("Maratón de Montevideo 2022"));
			Ice.altaTipoRegistro("Corredor 10K", "Inscripción a la carrera 10K", 650f, 1400,
					Ice.findEdicion("Maratón de Montevideo 2022"));

			// EDEV05 - Montevideo Comics 2024
			Ice.altaTipoRegistro("General", "Entrada para los 4 días de Montevideo Comics", 600f, 1500,
					Ice.findEdicion("Montevideo Comics 2024"));
			Ice.altaTipoRegistro("Cosplayer", "Entrada especial con acreditación para concurso cosplay", 300f, 50,
					Ice.findEdicion("Montevideo Comics 2024"));

			// EDEV06 - Montevideo Comics 2025
			Ice.altaTipoRegistro("General", "Entrada para los 4 días de Montevideo Comics", 800f, 1000,
					Ice.findEdicion("Montevideo Comics 2025"));
			Ice.altaTipoRegistro("Cosplayer", "Entrada especial con acreditación para concurso cosplay", 500f, 100,
					Ice.findEdicion("Montevideo Comics 2025"));

			// EDEV07 - Expointer Uruguay 2025
			Ice.altaTipoRegistro("General", "Acceso a la exposición agropecuaria", 300f, 5000,
					Ice.findEdicion("Expointer Uruguay 2025"));
			Ice.altaTipoRegistro("Empresarial", "Acceso para empresas + networking", 2000f, 5,
					Ice.findEdicion("Expointer Uruguay 2025"));

			// EDEV08 - Tecnología Punta del Este 2026
			Ice.altaTipoRegistro("Full", "Acceso ilimitado + cena de gala", 1800f, 300,
					Ice.findEdicion("Tecnología Punta del Este 2026"));
			Ice.altaTipoRegistro("General", "Acceso general", 1500f, 500,
					Ice.findEdicion("Tecnología Punta del Este 2026"));
			Ice.altaTipoRegistro("Estudiante", "Acceso para estudiantes", 1000f, 50,
					Ice.findEdicion("Tecnología Punta del Este 2026"));

			// EDEV09 - Mobile World Congress 2025
			Ice.altaTipoRegistro("Full", "Acceso ilimitado + cena de gala", 750f, 550,
					Ice.findEdicion("Mobile World Congress 2025"));
			Ice.altaTipoRegistro("General", "Acceso general", 500f, 400, Ice.findEdicion("Mobile World Congress 2025"));
			Ice.altaTipoRegistro("Estudiante", "Acceso para estudiantes", 250f, 400,
					Ice.findEdicion("Mobile World Congress 2025"));

			// EDEV10 - Web Summit 2026
			Ice.altaTipoRegistro("Full", "Acceso ilimitado + cena de gala", 900f, 30,
					Ice.findEdicion("Web Summit 2026"));
			Ice.altaTipoRegistro("General", "Acceso general", 650f, 5, Ice.findEdicion("Web Summit 2026"));
			Ice.altaTipoRegistro("Estudiante", "Acceso para estudiantes", 300f, 1, Ice.findEdicion("Web Summit 2026"));

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		// ------------------
		// Crear Patrocinios
		// ------------------

		try {
			// PAT1
			Patrocinio PAT1 = new Patrocinio("TECHFING", LocalDate.parse("21/08/2025", formatter), 4, 20000f,
					ETipoNivel.Oro, INS01, Ice.findEdicion("Tecnología Punta del Este 2026"),
					Ice.findEdicion("Tecnología Punta del Este 2026").getEdicionTR("Estudiante"));
			Ice.findEdicion("Tecnología Punta del Este 2026").addLinkPatrocinio(PAT1);
			Ice.findEdicion("Tecnología Punta del Este 2026").getEdicionTR("Estudiante").addLinkPatrocinio(PAT1);
			INS01.agregarPatrocinio(PAT1);

			// PAT2
			Patrocinio PAT2 = new Patrocinio("TECHANII", LocalDate.parse("20/08/2025", formatter), 1, 10000f,
					ETipoNivel.Plata, INS05, Ice.findEdicion("Tecnología Punta del Este 2026"),
					Ice.findEdicion("Tecnología Punta del Este 2026").getEdicionTR("General"));
			Ice.findEdicion("Tecnología Punta del Este 2026").addLinkPatrocinio(PAT2);
			Ice.findEdicion("Tecnología Punta del Este 2026").getEdicionTR("General").addLinkPatrocinio(PAT2);
			INS05.agregarPatrocinio(PAT2);

			// PAT3
			Patrocinio PAT3 = new Patrocinio("CORREANTEL", LocalDate.parse("04/03/2025", formatter), 10, 25000f,
					ETipoNivel.Platino, INS04, Ice.findEdicion("Maratón de Montevideo 2025"),
					Ice.findEdicion("Maratón de Montevideo 2025").getEdicionTR("Corredor 10K"));
			Ice.findEdicion("Maratón de Montevideo 2025").addLinkPatrocinio(PAT3);
			Ice.findEdicion("Maratón de Montevideo 2025").getEdicionTR("Corredor 10K").addLinkPatrocinio(PAT3);
			INS04.agregarPatrocinio(PAT3);

			// PAT4
			Patrocinio PAT4 = new Patrocinio("EXPOCAT", LocalDate.parse("05/05/2025", formatter), 10, 15000f,
					ETipoNivel.Bronce, INS03, Ice.findEdicion("Expointer Uruguay 2025"),
					Ice.findEdicion("Expointer Uruguay 2025").getEdicionTR("General"));
			Ice.findEdicion("Expointer Uruguay 2025").addLinkPatrocinio(PAT4);
			Ice.findEdicion("Expointer Uruguay 2025").getEdicionTR("General").addLinkPatrocinio(PAT4);
			INS03.agregarPatrocinio(PAT4);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		// ------------------
		// Crear Registros
		// ------------------

		try {
			// RE01 - Montevideo Rock 2025
			Ice.altaRegistro("Montevideo Rock 2025", "sofirod", "VIP", LocalDate.parse("14/05/2025", formatter)); // TR02

			// RE02 - Maratón de Montevideo 2024
			Ice.altaRegistro("Maratón de Montevideo 2024", "sofirod", "Corredor 21K", LocalDate.parse("30/07/2024", formatter)); // TR07

			// RE03 - Web Summit 2026
			Ice.altaRegistro("Web Summit 2026", "MariR", "Estudiante", LocalDate.parse("21/08/2025", formatter)); // TR25

			// RE04 - Maratón de Montevideo 2025
			Ice.altaRegistro("Maratón de Montevideo 2025", "sofirod", "Corredor 42K", LocalDate.parse("03/03/2025", formatter)); // TR03

			// RE05 - Mobile World Congress 2025
			Ice.altaRegistro("Mobile World Congress 2025", "vale23", "Full", LocalDate.parse("22/08/2025", formatter)); // TR20

			// RE06 - Maratón de Montevideo 2025
			Ice.altaRegistro("Maratón de Montevideo 2025", "AnaG", "Corredor 10K", LocalDate.parse("09/04/2025", formatter)); // TR05

			// RE07 - Maratón de Montevideo 2025
			Ice.altaRegistro("Maratón de Montevideo 2025", "JaviL", "Corredor 21K", LocalDate.parse("10/04/2025", formatter)); // TR04

			// RE08 - Montevideo Comics 2025
			Ice.altaRegistro("Montevideo Comics 2025", "andrearod", "Cosplayer", LocalDate.parse("03/08/2025", formatter)); // TR14

			// RE09 - Montevideo Comics 2024
			Ice.altaRegistro("Montevideo Comics 2024", "SofiM", "General", LocalDate.parse("16/07/2024", formatter)); // TR11


		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}
}
