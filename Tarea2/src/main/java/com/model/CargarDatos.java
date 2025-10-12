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
		Categoria CA12 = Ice.findCategoria("Investigación");

		// ------------------
		// Crear eventos
		// ------------------
		try {
			Ice.altaEvento("Conferencia de Tecnología", "Evento sobre innovación tecnológica", LocalDate.parse("10/01/2025", formatter), "CONFTEC", Arrays.asList(CA01, CA02), null);
			Ice.altaEvento("Feria del Libro", "Encuentro anual de literatura", LocalDate.parse("01/02/2025", formatter), "FERLIB", Arrays.asList(CA03, CA04), "/media/img/eventos/Feria_del_Libro.jpeg");
			Ice.altaEvento("Montevideo Rock", "Festival de rock con artistas nacionales e internacionales", LocalDate.parse("15/03/2023", formatter), "MONROCK", Arrays.asList(CA04, CA05), "/media/img/eventos/Montevideo_Rock.jpeg");
			Ice.altaEvento("Maratón de Montevideo", "Competencia deportiva anual en la capital", LocalDate.parse("01/01/2022", formatter), "MARATON", Arrays.asList(CA06, CA07), "/media/img/eventos/Maratón_de_Montevideo.png");
			Ice.altaEvento("Montevideo Comics", "Convención de historietas, cine y cultura geek", LocalDate.parse("10/04/2024", formatter), "COMICS", Arrays.asList(CA04, CA08), "/media/img/eventos/Montevideo_Comics.png");
			Ice.altaEvento("Expointer Uruguay", "Exposición internacional agropecuaria y ganadera", LocalDate.parse("12/12/2024", formatter), "EXPOAGRO", Arrays.asList(CA09, CA10), "/media/img/eventos/Expointer_Uruguay.png");
			Ice.altaEvento("Montevideo Fashion Week", "Pasarela de moda uruguaya e internacional", LocalDate.parse("20/07/2025", formatter), "MFASHION", Arrays.asList(CA04, CA11), null);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		ManejadorInstitucion mI = ManejadorInstitucion.getInstance();
		// ------------------
		// Crear Instituciones
		// ------------------
		Institucion INS01 = new Institucion("Facultad de Ingeniería", "https://www.fing.edu.uy", "Facultad de Ingenería de la Universidad de la República", null);
		mI.agregarInstitucion(INS01);
		Institucion INS02 = new Institucion("ORT Uruguay", "https://ort.edu.uy", "Universidad privada enfocada en tecnología y gestión", null);
		mI.agregarInstitucion(INS02);
		Institucion INS03 = new Institucion("Universidad Católica del Uruguay", "https://ucu.edu.uy", "Institución de educación superior privada", null);
		mI.agregarInstitucion(INS03);
		Institucion INS04 = new Institucion("Antel", "https://antel.com.uy", "Empresa estatal de telecomunicaciones", null);
		mI.agregarInstitucion(INS04);
		Institucion INS05 = new Institucion("Agencia Nacional de Investigación e Innovación (ANII)", "https://anii.org.uy", "Fomenta la investigación y la innovación en Uruguay", null);
		mI.agregarInstitucion(INS05);

		// ------------------
		// Crear Asistentes
		// ------------------
		try {
			ICU.altaAsistente("atorres", "atorres@gmail.com", "Ana", "Torres", LocalDate.parse("12/05/1990", formatter), INS01, "123.torres","/media/img/usuarios/atorres.jpg");
			ICU.altaAsistente("msilva", "martin.silva@fing.edu.uy", "Martin", "Silva", LocalDate.parse("21/08/1987", formatter), INS01, "msilva2025",null);
			ICU.altaAsistente("sofirod", "srodriguez@outlook.com", "Sofia", "Rodriguez", LocalDate.parse("03/02/1995", formatter), INS03, "srod.abc1","/media/img/usuarios/sofirod.jpeg");
			ICU.altaAsistente("vale23", "valentina.costa@mail.com", "Valentina", "Costa", LocalDate.parse("01/12/1992", formatter), null, "valen11c","/media/img/usuarios/vale23.jpeg");
			ICU.altaAsistente("luciag", "lucia.garcia@mail.com", "Lucía", "García", LocalDate.parse("09/11/1993", formatter), null, "garcia.22l","/media/img/usuarios/luciag.jpeg");
			ICU.altaAsistente("andrearod", "andrea.rod@mail.com", "Andrea", "Rodríguez", LocalDate.parse("10/06/2000", formatter), INS05, "rod77and","/media/img/usuarios/andrearod.jpeg");
			ICU.altaAsistente("AnaG", "ana.gomez@hotmail.com", "Ana", "Gómez", LocalDate.parse("15/03/1998", formatter), null, "gomez88a","/media/img/usuarios/AnaG.png");
			ICU.altaAsistente("JaviL", "javier.lopez@outlook.com", "Javier", "López", LocalDate.parse("22/07/1995", formatter), null, "jl99lopez","/media/img/usuarios/JaviL.jpeg");
			ICU.altaAsistente("MariR", "maria.rodriguez@gmail.com", "María", "Rodríguez", LocalDate.parse("10/11/2000", formatter), null, "maria55r","/media/img/usuarios/MariR.jpeg");
			ICU.altaAsistente("SofiM", "sofia.martinez@yahoo.com", "Sofía", "Martínez", LocalDate.parse("05/02/1997", formatter), null, "smarti99z","/media/img/usuarios/SofiM.jpeg");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		// ------------------
		// Crear Organizadores
		// ------------------
		try {
			ICU.altaOrganizador("miseventos", "contacto@miseventos.com", "MisEventos", "Empresa de organización de eventos.", "https://miseventos.com", "22miseventos","/media/img/usuarios/miseventos.jpeg");
			ICU.altaOrganizador("techcorp", "info@techcorp.com", "Corporación Tecnológica", "Empresa líder en tecnologías de la información.", null, "tech25corp",null);
			ICU.altaOrganizador("imm", "contacto@imm.gub.uy", "Intendencia de Montevideo", "Gobierno departamental de Montevideo.", "https://montevideo.gub.uy", "imm2025","/media/img/usuarios/imm.png");
			ICU.altaOrganizador("udelar", "contacto@udelar.edu.uy", "Universidad de la República", "Universidad pública de Uruguay.", "https://udelar.edu.uy", "25udelar",null);
			ICU.altaOrganizador("mec", "mec@mec.gub.uy", "Ministerio de Educación y Cultura", "Institución pública promotora de cultura.", "https://mec.gub.uy", "mec2025ok","/media/img/usuarios/mec.png");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		// ------------------
		// Crear Ediciones de Eventos
		// ------------------
		try {
		    Ice.altaEdicionDeEvento("Montevideo Rock 2025", "MONROCK25", "Montevideo", "Uruguay",
		            LocalDate.parse("20/11/2025", formatter), LocalDate.parse("22/11/2025", formatter), LocalDate.parse("12/03/2025", formatter),
		            Ice.getEvento("Montevideo Rock"), ICU.getOrganizador("imm"), "/media/img/ediciones/Montevideo_Rock_2025.jpeg");
		    Ice.findEdicion("Montevideo Rock 2025").setEstado(EstadoEdicion.Aceptada);

		    Ice.altaEdicionDeEvento("Maratón de Montevideo 2025", "MARATON25", "Montevideo", "Uruguay",
		            LocalDate.parse("14/09/2025", formatter), LocalDate.parse("14/09/2025", formatter), LocalDate.parse("05/02/2025", formatter),
		            Ice.getEvento("Maratón de Montevideo"), ICU.getOrganizador("imm"), "/media/img/ediciones/Maratón_de_Montevideo_2025.png");
		    Ice.findEdicion("Maratón de Montevideo 2025").setEstado(EstadoEdicion.Aceptada);

		    Ice.altaEdicionDeEvento("Maratón de Montevideo 2024", "MARATON24", "Montevideo", "Uruguay",
		            LocalDate.parse("14/09/2024", formatter), LocalDate.parse("14/09/2024", formatter), LocalDate.parse("21/04/2024", formatter),
		            Ice.getEvento("Maratón de Montevideo"), ICU.getOrganizador("imm"), "/media/img/ediciones/Maratón_de_Montevideo_2024.jpeg");
		    Ice.findEdicion("Maratón de Montevideo 2024").setEstado(EstadoEdicion.Aceptada);

		    Ice.altaEdicionDeEvento("Maratón de Montevideo 2022", "MARATON22", "Montevideo", "Uruguay",
		            LocalDate.parse("14/09/2022", formatter), LocalDate.parse("14/09/2022", formatter), LocalDate.parse("21/05/2022", formatter),
		            Ice.getEvento("Maratón de Montevideo"), ICU.getOrganizador("imm"), "/media/img/ediciones/Maratón_de_Montevideo_2022.jpeg");
		    Ice.findEdicion("Maratón de Montevideo 2022").setEstado(EstadoEdicion.Rechazada);

		    Ice.altaEdicionDeEvento("Montevideo Comics 2024", "COMICS24", "Montevideo", "Uruguay",
		            LocalDate.parse("18/07/2024", formatter), LocalDate.parse("21/07/2024", formatter), LocalDate.parse("20/06/2024", formatter),
		            Ice.getEvento("Montevideo Comics"), ICU.getOrganizador("miseventos"), "/media/img/ediciones/Montevideo_Comics_2024.jpeg");
		    Ice.findEdicion("Montevideo Comics 2024").setEstado(EstadoEdicion.Aceptada);

		    Ice.altaEdicionDeEvento("Montevideo Comics 2025", "COMICS25", "Montevideo", "Uruguay",
		            LocalDate.parse("04/08/2025", formatter), LocalDate.parse("06/08/2025", formatter), LocalDate.parse("04/07/2025", formatter),
		            Ice.getEvento("Montevideo Comics"), ICU.getOrganizador("miseventos"), "/media/img/ediciones/Montevideo_Comics_2025.jpeg");
		    Ice.findEdicion("Montevideo Comics 2025").setEstado(EstadoEdicion.Aceptada);

		    Ice.altaEdicionDeEvento("Expointer Uruguay 2025", "EXPOAGRO25", "Durazno", "Uruguay",
		            LocalDate.parse("11/09/2025", formatter), LocalDate.parse("17/09/2025", formatter), LocalDate.parse("01/02/2025", formatter),
		            Ice.getEvento("Expointer Uruguay"), ICU.getOrganizador("miseventos"), "/media/img/ediciones/Expointer_Uruguay_2025.jpeg");
		    Ice.findEdicion("Expointer Uruguay 2025").setEstado(EstadoEdicion.Ingresada);

		    Ice.altaEdicionDeEvento("Tecnología Punta del Este 2026", "CONFTECH26", "Punta del Este", "Uruguay",
		            LocalDate.parse("06/04/2026", formatter), LocalDate.parse("10/04/2026", formatter), LocalDate.parse("01/08/2025", formatter),
		            Ice.getEvento("Conferencia de Tecnología"), ICU.getOrganizador("udelar"), "/media/img/ediciones/Tecnología_Punta_del_Este_2026.jpeg");
		    Ice.findEdicion("Tecnología Punta del Este 2026").setEstado(EstadoEdicion.Aceptada);

		    Ice.altaEdicionDeEvento("Mobile World Congress 2025", "MWC", "Barcelona", "España",
		            LocalDate.parse("12/12/2025", formatter), LocalDate.parse("15/12/2025", formatter), LocalDate.parse("21/08/2025", formatter),
		            Ice.getEvento("Conferencia de Tecnología"), ICU.getOrganizador("techcorp"), null);
		    Ice.findEdicion("Mobile World Congress 2025").setEstado(EstadoEdicion.Aceptada);

		    Ice.altaEdicionDeEvento("Web Summit 2026", "WS26", "Lisboa", "Portugal",
		            LocalDate.parse("13/01/2026", formatter), LocalDate.parse("01/02/2026", formatter), LocalDate.parse("04/06/2025", formatter),
		            Ice.getEvento("Conferencia de Tecnología"), ICU.getOrganizador("techcorp"), null);
		    Ice.findEdicion("Web Summit 2026").setEstado(EstadoEdicion.Aceptada);

		    Ice.altaEdicionDeEvento("Montevideo Fashion Week 2026", "MFW26", "Nueva York", "Estados Unidos",
		            LocalDate.parse("16/02/2026", formatter), LocalDate.parse("20/02/2026", formatter), LocalDate.parse("02/10/2025", formatter),
		            Ice.getEvento("Montevideo Fashion Week"), ICU.getOrganizador("mec"), null);
		    Ice.findEdicion("Montevideo Fashion Week 2026").setEstado(EstadoEdicion.Ingresada);
		} catch (Exception e) {
		    JOptionPane.showMessageDialog(null, e.getMessage());
		}

		// ------------------
		// Crear TipoRegistro
		// ------------------
		try {
			Ice.altaTipoRegistro("General", "Acceso general a Montevideo Rock (2 días)", 1500.0f, 2000, Ice.findEdicion("Montevideo Rock 2025"));
			Ice.altaTipoRegistro("VIP", "Incluye backstage + acceso preferencial", 4000.0f, 200, Ice.findEdicion("Montevideo Rock 2025"));
			Ice.altaTipoRegistro("Corredor 42K", "Inscripción a la maratón completa", 1200.0f, 499, Ice.findEdicion("Maratón de Montevideo 2025"));
			Ice.altaTipoRegistro("Corredor 21K", "Inscripción a la media maratón", 800.0f, 700, Ice.findEdicion("Maratón de Montevideo 2025"));
			Ice.altaTipoRegistro("Corredor 10K", "Inscripción a la carrera 10K", 500.0f, 1000, Ice.findEdicion("Maratón de Montevideo 2025"));
			Ice.altaTipoRegistro("Corredor 42K", "Inscripción a la maratón completa", 1000.0f, 300, Ice.findEdicion("Maratón de Montevideo 2024"));
			Ice.altaTipoRegistro("Corredor 21K", "Inscripción a la media maratón", 500.0f, 500, Ice.findEdicion("Maratón de Montevideo 2024"));
			Ice.altaTipoRegistro("Corredor 42K", "Inscripción a la maratón completa", 1100.0f, 450, Ice.findEdicion("Maratón de Montevideo 2022"));
			Ice.altaTipoRegistro("Corredor 21K", "Inscripción a la media maratón", 900.0f, 750, Ice.findEdicion("Maratón de Montevideo 2022"));
			Ice.altaTipoRegistro("Corredor 10K", "Inscripción a la carrera 10K", 650.0f, 1400, Ice.findEdicion("Maratón de Montevideo 2022"));
			Ice.altaTipoRegistro("General", "Entrada para los 4 días de Montevideo Comics", 600.0f, 1500, Ice.findEdicion("Montevideo Comics 2024"));
			Ice.altaTipoRegistro("Cosplayer", "Entrada especial con acreditación para concurso cosplay", 300.0f, 50, Ice.findEdicion("Montevideo Comics 2024"));
			Ice.altaTipoRegistro("General", "Entrada para los 4 días de Montevideo Comics", 800.0f, 1000, Ice.findEdicion("Montevideo Comics 2025"));
			Ice.altaTipoRegistro("Cosplayer", "Entrada especial con acreditación para concurso cosplay", 500.0f, 100, Ice.findEdicion("Montevideo Comics 2025"));
			Ice.altaTipoRegistro("General", "Acceso a la exposición agropecuaria", 300.0f, 5000, Ice.findEdicion("Expointer Uruguay 2025"));
			Ice.altaTipoRegistro("Empresarial", "Acceso para empresas + networking", 2000.0f, 5, Ice.findEdicion("Expointer Uruguay 2025"));
			Ice.altaTipoRegistro("Full", "Acceso ilimitado + Cena de gala", 1800.0f, 300, Ice.findEdicion("Tecnología Punta del Este 2026"));
			Ice.altaTipoRegistro("General", "Acceso general", 1500.0f, 500, Ice.findEdicion("Tecnología Punta del Este 2026"));
			Ice.altaTipoRegistro("Estudiante", "Acceso para estudiantes", 1000.0f, 50, Ice.findEdicion("Tecnología Punta del Este 2026"));
			Ice.altaTipoRegistro("Full", "Acceso ilimitado + Cena de gala", 750.0f, 550, Ice.findEdicion("Mobile World Congress 2025"));
			Ice.altaTipoRegistro("General", "Acceso general", 500.0f, 400, Ice.findEdicion("Mobile World Congress 2025"));
			Ice.altaTipoRegistro("Estudiante", "Acceso para estudiantes", 250.0f, 400, Ice.findEdicion("Mobile World Congress 2025"));
			Ice.altaTipoRegistro("Full", "Acceso ilimitado + Cena de gala", 900.0f, 30, Ice.findEdicion("Web Summit 2026"));
			Ice.altaTipoRegistro("General", "Acceso general", 650.0f, 5, Ice.findEdicion("Web Summit 2026"));
			Ice.altaTipoRegistro("Estudiante", "Acceso para estudiantes", 300.0f, 1, Ice.findEdicion("Web Summit 2026"));
			Ice.altaTipoRegistro("Full", "Acceso a todos los eventos de la semana", 450.0f, 50, Ice.findEdicion("Montevideo Fashion Week 2026"));
			Ice.altaTipoRegistro("Visitante", "Acceso parcial a los eventos de la semana", 150.0f, 25, Ice.findEdicion("Montevideo Fashion Week 2026"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		// ------------------
		// Crear Patrocinios
		// ------------------
		try {
			Patrocinio PAT1 = new Patrocinio("TECHUDELAR", LocalDate.parse("21/08/2025", formatter), 4, 20000.0f, ETipoNivel.Oro, INS01, Ice.findEdicion("Tecnología Punta del Este 2026"), Ice.findEdicion("Tecnología Punta del Este 2026").getEdicionTR("Estudiante"));
			Ice.findEdicion("Tecnología Punta del Este 2026").addLinkPatrocinio(PAT1);
			Ice.findEdicion("Tecnología Punta del Este 2026").getEdicionTR("Estudiante").addLinkPatrocinio(PAT1);
			INS01.agregarPatrocinio(PAT1);
			Patrocinio PAT2 = new Patrocinio("TECHANII", LocalDate.parse("20/08/2025", formatter), 1, 10000.0f, ETipoNivel.Plata, INS05, Ice.findEdicion("Tecnología Punta del Este 2026"), Ice.findEdicion("Tecnología Punta del Este 2026").getEdicionTR("General"));
			Ice.findEdicion("Tecnología Punta del Este 2026").addLinkPatrocinio(PAT2);
			Ice.findEdicion("Tecnología Punta del Este 2026").getEdicionTR("General").addLinkPatrocinio(PAT2);
			INS05.agregarPatrocinio(PAT2);
			Patrocinio PAT3 = new Patrocinio("CORREANTEL", LocalDate.parse("04/03/2025", formatter), 10, 25000.0f, ETipoNivel.Platino, INS04, Ice.findEdicion("Maratón de Montevideo 2025"), Ice.findEdicion("Maratón de Montevideo 2025").getEdicionTR("Corredor 10K"));
			Ice.findEdicion("Maratón de Montevideo 2025").addLinkPatrocinio(PAT3);
			Ice.findEdicion("Maratón de Montevideo 2025").getEdicionTR("Corredor 10K").addLinkPatrocinio(PAT3);
			INS04.agregarPatrocinio(PAT3);
			Patrocinio PAT4 = new Patrocinio("EXPOCAT", LocalDate.parse("05/05/2025", formatter), 10, 15000.0f, ETipoNivel.Bronce, INS03, Ice.findEdicion("Expointer Uruguay 2025"), Ice.findEdicion("Expointer Uruguay 2025").getEdicionTR("General"));
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
			Ice.altaRegistro("Montevideo Rock 2025", "sofirod", "VIP", LocalDate.parse("14/05/2025", formatter));
			Ice.altaRegistro("Maratón de Montevideo 2024", "sofirod", "Corredor 21K", LocalDate.parse("30/07/2024", formatter));
			Ice.altaRegistro("Web Summit 2026", "andrearod", "Estudiante", LocalDate.parse("21/08/2025", formatter));
			Ice.altaRegistro("Maratón de Montevideo 2025", "sofirod", "Corredor 42K", LocalDate.parse("03/03/2025", formatter));
			Ice.altaRegistro("Mobile World Congress 2025", "vale23", "Full", LocalDate.parse("22/08/2025", formatter));
			Ice.altaRegistro("Maratón de Montevideo 2025", "AnaG", "Corredor 10K", LocalDate.parse("09/04/2025", formatter));
			Ice.altaRegistro("Maratón de Montevideo 2025", "JaviL", "Corredor 21K", LocalDate.parse("10/04/2025", formatter));
			Ice.altaRegistro("Montevideo Comics 2025", "MariR", "Cosplayer", LocalDate.parse("03/08/2025", formatter));
			Ice.altaRegistro("Montevideo Comics 2024", "SofiM", "General", LocalDate.parse("16/07/2024", formatter));
			Ice.altaRegistro("Tecnología Punta del Este 2026", "msilva", "Estudiante", LocalDate.parse("01/10/2025", formatter));
			Ice.altaRegistro("Tecnología Punta del Este 2026", "andrearod", "General", LocalDate.parse("06/10/2025", formatter));
			Ice.altaRegistro("Tecnología Punta del Este 2026", "MariR", "General", LocalDate.parse("10/10/2025", formatter));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}
}