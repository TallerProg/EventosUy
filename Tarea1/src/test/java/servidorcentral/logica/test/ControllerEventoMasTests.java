package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import servidorcentral.logica.Categoria;
import servidorcentral.logica.ControllerEvento;
import servidorcentral.logica.DTAsistente;
import servidorcentral.logica.DTCategoria;
import servidorcentral.logica.DTEdicion;
import servidorcentral.logica.DTeventoOedicion;
import servidorcentral.logica.DTInstitucion;
import servidorcentral.logica.DTOrganizador;
import servidorcentral.logica.DTOrganizadorDetallado;
import servidorcentral.logica.DTRegistro;
import servidorcentral.logica.DTPatrocinio;
import servidorcentral.logica.ETipoNivel;
import servidorcentral.logica.Edicion;
import servidorcentral.logica.EstadoEdicion;
import servidorcentral.logica.Evento;
import servidorcentral.logica.Factory;
import servidorcentral.logica.Institucion;
import servidorcentral.logica.ManejadorEvento;
import servidorcentral.logica.ManejadorInstitucion;
import servidorcentral.logica.ManejadorUsuario;
import servidorcentral.logica.Organizador;
import servidorcentral.logica.TipoRegistro;

class ControllerEventoMoreTests {

    private ControllerEvento controller;
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @BeforeEach
    void setUp() {
        controller = new ControllerEvento();

        // Limpiar singletons para que los tests sean deterministas
        ManejadorEvento.getInstancia().limpiar();
        ManejadorUsuario.getInstance().limpiar();
        // Si ManejadorInstitucion tiene limpiar(), podés agregarlo:
        // ManejadorInstitucion.getInstance().limpiar();
    }

    // ======================================================
    // altaCategoria / findCategoria
    // ======================================================

    @Test
    void testAltaCategoriaOkYFindCategoria() throws Exception {
        controller.altaCategoria("Conferencias");
        Categoria c = controller.findCategoria("Conferencias");
        assertNotNull(c);
        assertEquals("Conferencias", c.getNombre());
    }

    @Test
    void testAltaCategoriaRepetidaLanzaExcepcion() throws Exception {
        controller.altaCategoria("Workshops");
        assertThrows(Exception.class, () -> controller.altaCategoria("Workshops"));
    }

    // ======================================================
    // altaEvento / existeEvento / listarNombresEventos / getEvento / consultaEvento
    // ======================================================

    @Test
    void testAltaEventoYConsultasBasicas() throws Exception {
        controller.altaCategoria("Cat1");
        Categoria cat1 = controller.findCategoria("Cat1");

        LocalDate f1 = LocalDate.parse("01/01/2024", fmt);
        LocalDate f2 = LocalDate.parse("02/01/2024", fmt);

        controller.altaEvento("Evento A", "Desc A", f1, "EVA", List.of(cat1), "eva.png");
        controller.altaEvento("Evento B", "Desc B", f2, "EVB", List.of(), "evb.png");

        // existeEvento
        assertTrue(controller.existeEvento("Evento A"));
        assertFalse(controller.existeEvento("NoExiste"));

        // listarNombresEventos
        List<String> nombres = controller.listarNombresEventos();
        assertEquals(2, nombres.size());
        assertTrue(nombres.contains("Evento A"));
        assertTrue(nombres.contains("Evento B"));

        // getEvento
        Evento evA = controller.getEvento("Evento A");
        assertNotNull(evA);
        assertEquals("Evento A", evA.getNombre());

        // consultaEvento (DTevento)
        var dtA = controller.consultaEvento("Evento A");
        assertNotNull(dtA);
        assertEquals("Evento A", dtA.getNombre());
        assertEquals("Desc A", dtA.getDescripcion());
    }

    // ======================================================
    // listarEdicionesDeEvento / obtenerNombreEdicionPorEvento
    // ======================================================

    @Test
    void testListarEdicionesYObtenerNombreEdicionPorEvento() throws Exception {
        // Evento
        controller.altaCategoria("CatX");
        Categoria catX = controller.findCategoria("CatX");
        LocalDate f = LocalDate.parse("10/10/2024", fmt);
        controller.altaEvento("EventoX", "DescX", f, "EVX", List.of(catX), "x.png");
        Evento ev = controller.getEvento("EventoX");

        // Ediciones
        Edicion e1 = new Edicion("Ed1", "E1",
                f, f.plusDays(1), f, "MVD", "UY", ev);
        Edicion e2 = new Edicion("Ed2", "E2",
                f.plusDays(2), f.plusDays(3), f, "MVD", "UY", ev);

        ev.agregarEdicion(e1);
        ev.agregarEdicion(e2);
        ManejadorEvento.getInstancia().agregarEdicion(e1);
        ManejadorEvento.getInstancia().agregarEdicion(e2);

        List<String> eds = controller.listarEdicionesDeEvento("EventoX");
        assertEquals(2, eds.size());
        assertTrue(eds.contains("Ed1"));
        assertTrue(eds.contains("Ed2"));

        String primera = controller.obtenerNombreEdicionPorEvento("EventoX");
        assertNotNull(primera);
        assertTrue(primera.equals("Ed1") || primera.equals("Ed2"));
    }

    // ======================================================
    // aceptarRechazarEdicion
    // ======================================================

    @Test
    void testAceptarYRechazarEdicion() throws Exception {
        Evento ev = new Evento("Ev", "EV", "d", LocalDate.now(), List.of(), "ev.png");
        ManejadorEvento.getInstancia().agregarEvento(ev);

        Edicion ed = new Edicion("Ed", "ED",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now(),
                "MVD", "UY", ev);
        ev.agregarEdicion(ed);
        ManejadorEvento.getInstancia().agregarEdicion(ed);

        controller.aceptarRechazarEdicion("Ed", true);
        assertEquals(EstadoEdicion.Aceptada, ed.getEstado());

        controller.aceptarRechazarEdicion("Ed", false);
        assertEquals(EstadoEdicion.Rechazada, ed.getEstado());
    }

    // ======================================================
    // altaPatrocinio / consultaPatrocinio / listarPatrociniosDeEdicion
    // ======================================================

    @Test
    void testAltaPatrocinioOkYConsulta() throws Exception {
        // Evento + edición
        Evento ev = new Evento("EventoPat", "EP", "desc", LocalDate.now(), List.of(), "ev.png");
        ManejadorEvento.getInstancia().agregarEvento(ev);

        Edicion ed = new Edicion(
                "EdPat",
                "EDP",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now(),
                "Montevideo",
                "UY",
                ev
        );
        ev.agregarEdicion(ed);
        ManejadorEvento.getInstancia().agregarEdicion(ed);

        // TipoRegistro
        TipoRegistro tr = new TipoRegistro("General", "Acceso", 100f, 10, ed);
        ed.agregarTipoRegistro(tr);

        // Institución
        Institucion inst = new Institucion("InstPat", "http://inst.uy", "desc", "inst.png");
        ManejadorInstitucion.getInstance().agregarInstitucion(inst);

        // Alta patrocinio
        controller.altaPatrocinio(
                "CODPAT1",
                LocalDate.now(),
                2,
                2000f,
                ETipoNivel.Oro,
                "InstPat",
                "EdPat",
                "General"
        );

        DTPatrocinio dtp = controller.consultaPatrocinio("EdPat", "CODPAT1");
        assertNotNull(dtp);
        assertEquals("CODPAT1", dtp.getCodigo());

        List<DTPatrocinio> lista = controller.listarPatrociniosDeEdicion("EdPat");
        assertEquals(1, lista.size());
        assertEquals("CODPAT1", lista.get(0).getCodigo());
    }

    @Test
    void testAltaPatrocinioCodigoDuplicadoLanzaExcepcion() throws Exception {
        Evento ev = new Evento("Ev", "EV", "d", LocalDate.now(), List.of(), "ev.png");
        ManejadorEvento.getInstancia().agregarEvento(ev);

        Edicion ed = new Edicion("Ed", "ED",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now(),
                "MVD", "UY", ev);
        ev.agregarEdicion(ed);
        ManejadorEvento.getInstancia().agregarEdicion(ed);

        TipoRegistro tr = new TipoRegistro("TR", "t", 100f, 5, ed);
        ed.agregarTipoRegistro(tr);

        Institucion inst = new Institucion("Inst", "u", "d", "i.png");
        ManejadorInstitucion.getInstance().agregarInstitucion(inst);

        controller.altaPatrocinio(
                "REPCOD",
                LocalDate.now(),
                1,
                1000f,
                ETipoNivel.Platino,
                "Inst",
                "Ed",
                "TR"
        );

        assertThrows(Exception.class, () -> controller.altaPatrocinio(
                "REPCOD",
                LocalDate.now(),
                1,
                1000f,
                ETipoNivel.Oro,
                "Inst",
                "Ed",
                "TR"
        ));
    }

    // ======================================================
    // existeEdicionPorNombre / existeEdicionPorSiglaDTO
    // ======================================================

    @Test
    void testExisteEdicionPorNombreYSigla() {
        Evento ev = new Evento("Ev", "EV", "d", LocalDate.now(), List.of(), "ev.png");
        ManejadorEvento.getInstancia().agregarEvento(ev);

        Edicion ed = new Edicion("EdX", "EDX",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now(),
                "MVD", "UY", ev);
        ev.agregarEdicion(ed);
        ManejadorEvento.getInstancia().agregarEdicion(ed);

        assertTrue(controller.existeEdicionPorNombre("Ev", "EdX"));
        assertFalse(controller.existeEdicionPorNombre("Ev", "Otra"));

        assertTrue(controller.existeEdicionPorSiglaDTO("EDX"));
        assertFalse(controller.existeEdicionPorSiglaDTO("ZZZ"));
    }

    // ======================================================
    // listarNombresTiposRegistroDTO
    // ======================================================

    @Test
    void testListarNombresTiposRegistroDTO() {
        Evento ev = new Evento("EvTR", "EVT", "d", LocalDate.now(), List.of(), "ev.png");
        ManejadorEvento.getInstancia().agregarEvento(ev);

        Edicion ed = new Edicion("EdTR", "EDT",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now(),
                "MVD", "UY", ev);
        ev.agregarEdicion(ed);
        ManejadorEvento.getInstancia().agregarEdicion(ed);

        TipoRegistro tr1 = new TipoRegistro("General", "g", 100f, 10, ed);
        TipoRegistro tr2 = new TipoRegistro("VIP", "v", 200f, 5, ed);
        ed.agregarTipoRegistro(tr1);
        ed.agregarTipoRegistro(tr2);

        List<String> nombres = controller.listarNombresTiposRegistroDTO("EdTR");
        assertEquals(2, nombres.size());
        assertTrue(nombres.contains("General"));
        assertTrue(nombres.contains("VIP"));
    }

    // ======================================================
    // finalizarEvento
    // ======================================================

    @Test
    void testFinalizarEvento() throws Exception {
        Evento ev = new Evento("FinEv", "FEV", "d", LocalDate.now(), List.of(), "ev.png");
        ManejadorEvento.getInstancia().agregarEvento(ev);

        assertFalse(ev.isFinalizado());
        controller.finalizarEvento("FinEv");
        assertTrue(ev.isFinalizado());
    }

    // ======================================================
    // listarEventosYEdicionesBusqueda + DTEventoOedicion
    // ======================================================

    @Test
    void testListarEventosYEdicionesBusquedaIncluyeEventosYEdiciones() throws Exception {
        // Evento 1 con descripción
        controller.altaCategoria("BusqCat");
        Categoria c = controller.findCategoria("BusqCat");
        controller.altaEvento("JavaConf", "evento de java", LocalDate.now(), "JC", List.of(c), "j.png");
        controller.altaEvento("PythonDays", "evento de python", LocalDate.now(), "PD", List.of(), "p.png");

        Evento javaEv = controller.getEvento("JavaConf");

        // Edición aceptada
        Edicion edOk = new Edicion(
                "JavaConf 2025",
                "JC25",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now(),
                "MVD",
                "UY",
                javaEv
        );
        javaEv.agregarEdicion(edOk);
        ManejadorEvento.getInstancia().agregarEdicion(edOk);
        edOk.setEstado(EstadoEdicion.Aceptada);

        // Edición rechazada (no debería entrar)
        Edicion edRech = new Edicion(
                "JavaConf OLD",
                "JCOLD",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now(),
                "MVD",
                "UY",
                javaEv
        );
        javaEv.agregarEdicion(edRech);
        ManejadorEvento.getInstancia().agregarEdicion(edRech);
        edRech.setEstado(EstadoEdicion.Rechazada);

        List<DTeventoOedicion> res = controller.listarEventosYEdicionesBusqueda("java");
        assertFalse(res.isEmpty());

        // Debe haber al menos un evento JavaConf y la edición aceptada
        assertTrue(res.stream().anyMatch(d -> "evento".equals(d.getTipo())
                && "JavaConf".equals(d.getNombreEvento())));

        assertTrue(res.stream().anyMatch(d -> "edicion".equals(d.getTipo())
                && "JavaConf 2025".equals(d.getNombreEdicion())));

        // La edición rechazada no debe aparecer
        assertFalse(res.stream().anyMatch(d -> "JavaConf OLD".equals(d.getNombreEdicion())));
    }

    // ======================================================
    // nombreEventoDeEdicion (usa Factory + IControllerEvento)
    // ======================================================

    @Test
    void testNombreEventoDeEdicion() {
        // Usamos el Factory para asegurarnos que usa el mismo controller
        Factory fabrica = Factory.getInstance();
        ControllerEvento ctrl = (ControllerEvento) fabrica.getIControllerEvento();

        ManejadorEvento.getInstancia().limpiar();

        Evento ev = new Evento("EvRoot", "ER", "d", LocalDate.now(), List.of(), "ev.png");
        ManejadorEvento.getInstancia().agregarEvento(ev);

        Edicion ed = new Edicion(
                "EdRoot",
                "EDR",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now(),
                "MVD", "UY",
                ev
        );
        ev.agregarEdicion(ed);
        ManejadorEvento.getInstancia().agregarEdicion(ed);

        String nom = ctrl.nombreEventoDeEdicion("EdRoot");
        assertEquals("EvRoot", nom);

        assertNull(ctrl.nombreEventoDeEdicion("EdInexistente"));
    }

    // ======================================================
    // DTO: DTEventoOedicion
    // ======================================================

    @Test
    void testDTEventoOedicionDesdeEventoYEdicion() throws Exception {
        // Evento + edición para generar DTevento y DTeEdicion indirectamente
        controller.altaCategoria("CatDTO");
        Categoria c = controller.findCategoria("CatDTO");
        LocalDate f = LocalDate.parse("05/05/2024", fmt);
        controller.altaEvento("EventoDTO", "Desc DTO", f, "EDTO", List.of(c), "img.png");

        Evento ev = controller.getEvento("EventoDTO");

        Edicion ed = new Edicion(
                "EdDTO",
                "EDTDTO",
                f.plusDays(1),
                f.plusDays(2),
                f,
                "MVD",
                "UY",
                ev
        );
        ev.agregarEdicion(ed);
        ManejadorEvento.getInstancia().agregarEdicion(ed);

        // Construcción manual usando los DT ya generados por el modelo
        var dtEv = ev.getDTevento();
        var dtoEv = new DTeventoOedicion(dtEv);
        assertEquals("evento", dtoEv.getTipo());
        assertEquals("EventoDTO", dtoEv.getNombreEvento());
        assertEquals("Desc DTO", dtoEv.getDescripcion());
        assertEquals(f, dtoEv.getFechaAlta());
        assertEquals("img.png", dtoEv.getImg());
        assertEquals("evento", dtoEv.getEstado());

        var dtEd = ed.getDTEdicion();
        var dtoEd = new DTeventoOedicion(dtEd);
        assertEquals("edicion", dtoEd.getTipo());
        assertEquals("EventoDTO", dtoEd.getNombreEvento());
        assertEquals("EdDTO", dtoEd.getNombreEdicion());
        assertEquals(ed.getImagenWebPath(), dtoEd.getImg());
        assertEquals(dtEd.getEstado(), dtoEd.getEstado());
    }

    // ======================================================
    // DTO: DTOrganizadorDetallado
    // ======================================================

    @Test
    void testDTOrganizadorDetalladoDefaultYSetters() {
        DTOrganizadorDetallado dt = new DTOrganizadorDetallado();
        assertNotNull(dt.getEdiciones());
        assertTrue(dt.getEdiciones().isEmpty());

        dt.setNickname(null);
        dt.setCorreo(null);
        dt.setNombre(null);
        dt.setDescripcion(null);
        dt.setUrl(null);
        dt.setImg(null);
        dt.setEdiciones(null);

        assertEquals("", dt.getNickname());
        assertEquals("", dt.getCorreo());
        assertEquals("", dt.getNombre());
        assertEquals("", dt.getDescripcion());
        assertEquals("", dt.getUrl());
        assertEquals("", dt.getImg());
        assertNotNull(dt.getEdiciones());
        assertTrue(dt.getEdiciones().isEmpty());
    }

    @Test
    void testDTOrganizadorDetalladoConstructorDefensiveCopy() {
        List<DTEdicion> lista = new ArrayList<>();
        lista.add(new DTEdicion());
        DTOrganizadorDetallado dt = new DTOrganizadorDetallado(
                "nick",
                "mail@test.com",
                "Nombre",
                "Desc",
                "url",
                "img.png",
                lista
        );

        assertEquals("nick", dt.getNickname());
        assertEquals("mail@test.com", dt.getCorreo());
        assertEquals("Nombre", dt.getNombre());
        assertEquals("Desc", dt.getDescripcion());
        assertEquals("url", dt.getUrl());
        assertEquals("img.png", dt.getImg());
        assertEquals(1, dt.getEdiciones().size());

        // Mutar lista original no debe afectar
        lista.clear();
        assertEquals(1, dt.getEdiciones().size());
    }

    // ======================================================
    // DTO: DTAsistente
    // ======================================================

    @Test
    void testDTAsistenteConstructorCompleto() {
        LocalDate fecha = LocalDate.of(2000, 1, 15);
        DTInstitucion inst = new DTInstitucion();
        List<DTRegistro> regs = new ArrayList<>();
        regs.add(new DTRegistro(fecha, null, null, null, null, null));

        DTAsistente dt = new DTAsistente(
                "nick",
                "mail@test.com",
                "Nombre",
                "Apellido",
                fecha,
                "img.png",
                inst,
                regs
        );

        assertEquals("nick", dt.getNickname());
        assertEquals("mail@test.com", dt.getCorreo());
        assertEquals("Nombre", dt.getNombre());
        assertEquals("Apellido", dt.getApellido());
        assertEquals(fecha, dt.getFNacimiento());
        assertEquals(fecha.toString(), dt.getFNacimientoS());
        assertEquals("img.png", dt.getImg());
        assertEquals(inst, dt.getInstitucion());
        assertEquals(1, dt.getRegistros().size());

        // Defensive copy
        regs.clear();
        assertEquals(1, dt.getRegistros().size());
    }

    @Test
    void testDTAsistenteSetFNacimientoActualizaString() {
        DTAsistente dt = new DTAsistente();
        LocalDate fecha = LocalDate.of(1999, 12, 31);
        dt.setFNacimiento(fecha);
        assertEquals(fecha, dt.getFNacimiento());
        assertEquals("1999-12-31", dt.getFNacimientoS());

        dt.setFNacimiento(null);
        assertNull(dt.getFNacimiento());
        assertNull(dt.getFNacimientoS());
    }

    @Test
    void testDTAsistenteSetFNacimientoSActualizaFecha() {
        DTAsistente dt = new DTAsistente();
        dt.setFNacimientoS("2001-05-10");
        assertEquals(LocalDate.of(2001, 5, 10), dt.getFNacimiento());
        assertEquals("2001-05-10", dt.getFNacimientoS());

        dt.setFNacimientoS(null);
        assertNull(dt.getFNacimiento());
        assertNull(dt.getFNacimientoS());
    }
}

