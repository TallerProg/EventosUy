package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import servidorcentral.excepciones.UsuarioRepetidoException;
import servidorcentral.logica.Asistente;
import servidorcentral.logica.ControllerEvento;
import servidorcentral.logica.ControllerUsuario;
import servidorcentral.logica.DTCategoria;
import servidorcentral.logica.DTUsuarioListaConsulta;
import servidorcentral.logica.DTevento;
import servidorcentral.logica.Edicion;
import servidorcentral.logica.Institucion;
import servidorcentral.logica.ManejadorEvento;
import servidorcentral.logica.ManejadorUsuario;
import servidorcentral.logica.Organizador;

@DisplayName("Tests de servicios EventUY (listar/consulta/registro) — CE/CU correctos y sin colisiones")
public class TestGenerales2Test {

    private ControllerEvento controllerEvento;     
    private ControllerUsuario controllerUsuario;   
    private ManejadorEvento mev;
    private ManejadorUsuario mus;

    private String nombreEventoBase;
    private String nombreEdicionBase;


    private static String randToken() {
        long t = System.nanoTime();
        long r = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        return Long.toHexString(t ^ r);
    }

    /** Genera un nombre de evento que no exista en el manejador */
    private String genNombreEventoUnico() {
        String name;
        do {
            name = "EV_" + randToken();
        } while (mev != null && mev.existeEvento(name));
        return name;
    }

    /** Genera una sigla que no esté usada por ninguna edición (usa tieneEdicionSigla si existe) */
    private String genSiglaEdicionUnica() {
        String sg;
        do {
            sg = ("S" + randToken()).replaceAll("[^A-Za-z0-9]", "");
            if (sg.length() > 8) sg = sg.substring(0, 8);
        } while (mev != null && tieneEdicionConSigla(sg));
        return sg;
    }

    /** Wrapper defensivo por si el método no existe en tu manejador */
    private boolean tieneEdicionConSigla(String sigla) {
        try {
            return mev.tieneEdicionSigla(sigla);
        } catch (Throwable ignore) {
            for (Edicion e : mev.listarEdiciones()) {
                if (sigla != null && sigla.equals(e.getSigla())) return true;
            }
            return false;
        }
    }

    /** Genera un nombre de edición que no exista en el manejador */
    private String genNombreEdicionUnico() {
        String name;
        do {
            name = "ED_" + randToken();
        } while (mev != null && mev.existeEdicion(name));
        return name;
    }

    /** Genera un nickname de usuario único (para orgs/asis) */
    private String genNickUnico(String pref) {
        String nk;
        do {
            nk = pref + "_" + randToken();
        } while (mus != null && mus.findUsuario(nk) != null);
        return nk;
    }


    @BeforeEach
    void setUp() throws Exception {
        controllerEvento  = new ControllerEvento();
        controllerUsuario = new ControllerUsuario();
        mev = ManejadorEvento.getInstancia();
        mus = ManejadorUsuario.getInstance();

        nombreEventoBase = genNombreEventoUnico();
        String siglaEvento = genSiglaEdicionUnica(); 

        assertDoesNotThrow(() ->
            controllerEvento.altaEvento(
                nombreEventoBase,
                "Evento de prueba",
                LocalDate.now(),
                siglaEvento,
                new ArrayList<>(),
                "evento.png"
            )
        );

        String nickOrg = genNickUnico("org");
        assertDoesNotThrow(() ->
            controllerUsuario.altaOrganizador(
                nickOrg,
                nickOrg + "@mail.com",
                "Org Base",
                "Organizador Base",
                "https://org.example",
                "1234",
                "org.png"
            )
        );
        Organizador orgBase = controllerUsuario.getOrganizador(nickOrg);
        assertNotNull(orgBase);

        nombreEdicionBase = genNombreEdicionUnico();
        String siglaEd = genSiglaEdicionUnica();

        assertDoesNotThrow(() ->
            controllerEvento.altaEdicionDeEvento(
                nombreEdicionBase,
                siglaEd,
                "Montevideo",
                "Uruguay",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(11),
                LocalDate.now(),
                controllerEvento.getEvento(nombreEventoBase),
                orgBase,
                "img/edicion.png"
            )
        );

        assertNotNull(mev.findEdicion(nombreEdicionBase));
    }


    private void altaCategoriaPorControllerEvento(String nombreCat) {
        assertDoesNotThrow(() -> controllerEvento.altaCategoria(nombreCat));
    }

    private Asistente altaAsistentePorControllerUsuario(boolean conInstitucion) {
        String nick = genNickUnico("asis");
        Institucion inst = null;
        if (conInstitucion) {
            inst = new Institucion("Inst_" + randToken(), "desc", "https://inst.example", "logo.png");
        }
        try {
            controllerUsuario.altaAsistente(
                nick,
                nick + "@mail.com",
                "Nombre " + nick,
                "Apellido " + nick,
                LocalDate.of(2000,1,1),
                inst,
                "1234",
                null
            );
        } catch (UsuarioRepetidoException e) {
            fail(e);
        }
        Asistente a = controllerUsuario.getAsistente(nick);
        assertNotNull(a);
        return a;
    }

    private Organizador altaOrganizadorPorControllerUsuario() {
        String nickOrg = genNickUnico("orgTest");
        try {
            controllerUsuario.altaOrganizador(
                nickOrg,
                nickOrg + "@mail.com",
                "NombreOrg " + nickOrg,
                "Desc " + nickOrg,
                null,
                "1234",
                null
            );
        } catch (UsuarioRepetidoException e) {
            fail(e);
        }
        Organizador o = controllerUsuario.getOrganizador(nickOrg);
        assertNotNull(o);
        return o;
    }

    private String tomarUnaEdicionCualquiera() {
        List<Edicion> eds = controllerEvento.listarEdiciones();
        if (eds == null || eds.isEmpty()) return null;
        return eds.get(0).getNombre();
    }


    @Test
    @DisplayName("listarDTEventos incluye el evento dado de alta (ControllerEvento)")
    void listarDTEventos_devuelveEventos() {
        List<DTevento> lista = controllerEvento.listarDTEventos();
        assertNotNull(lista);
        assertTrue(lista.size() >= 1);
        assertTrue(lista.stream().anyMatch(dt -> nombreEventoBase.equals(dt.getNombre())));
    }

    @Test
    @DisplayName("listarDTCategorias incluye la categoría recién creada (ControllerEvento)")
    void listarDTCategorias_devuelveCategorias() {
        String cat = "CAT_" + randToken();
        altaCategoriaPorControllerEvento(cat);

        List<DTCategoria> lista = controllerEvento.listarDTCategorias();
        assertNotNull(lista);
        assertTrue(lista.size() >= 1);
        assertTrue(lista.stream().anyMatch(dt -> cat.equals(dt.getNombre())));
    }


    @Test
    @DisplayName("altaRegistro(fecha) falla si la edición no existe (ControllerEvento)")
    void altaRegistroConFecha_fallaEdicionNoExiste() {
        String edicionInexistente = "NOEX_" + randToken();
        String nickRandom = "nickX_" + randToken();
        Exception ex = assertThrows(Exception.class, () ->
            controllerEvento.altaRegistro(edicionInexistente, nickRandom, "TR-Gen", "COD-XYZ", LocalDate.now())
        );
        String msg = ex.getMessage() == null ? "" : ex.getMessage().toLowerCase();
        assertTrue(msg.contains("edición no existe") || msg.contains("edicion no existe"));
    }

    @Test
    @DisplayName("altaRegistro(fecha) falla si el asistente no existe (ControllerEvento)")
    void altaRegistroConFecha_fallaAsistenteNoExiste() {
        String edicionExistente = tomarUnaEdicionCualquiera();
        assertNotNull(edicionExistente);

        Exception ex = assertThrows(Exception.class, () ->
            controllerEvento.altaRegistro(edicionExistente, "NO_ASISTE_" + randToken(), "TR-Gen", "COD-XYZ", LocalDate.now())
        );
        assertTrue((ex.getMessage() + "").toLowerCase().contains("asistente no existe"));
    }

    @Test
    @DisplayName("altaRegistro(fecha) falla si el asistente no tiene institución (ControllerEvento)")
    void altaRegistroConFecha_fallaAsistenteSinInstitucion() {
        String edicionExistente = tomarUnaEdicionCualquiera();
        assertNotNull(edicionExistente);

        Asistente a = altaAsistentePorControllerUsuario(false);

        Exception ex = assertThrows(Exception.class, () ->
            controllerEvento.altaRegistro(edicionExistente, a.getNickname(), "TR-Gen", "COD-XYZ", LocalDate.now())
        );
        String msg = (ex.getMessage() + "").toLowerCase();
        assertTrue(msg.contains("asistente no asociado a institución") || msg.contains("asistente no asociado a institucion"));
    }


    @Test
    @DisplayName("listarOrganizadores incluye un organizador dado de alta (ControllerEvento)")
    void listarOrganizadores_ok() {
        Organizador o = altaOrganizadorPorControllerUsuario();
        List<Organizador> lista = controllerEvento.listarOrganizadores();
        assertNotNull(lista);
        assertTrue(lista.size() >= 1);
        assertTrue(lista.stream().anyMatch(x -> x.getNickname().equals(o.getNickname())));
    }

    @Test
    @DisplayName("listarEdiciones contiene la edición creada en el setup (ControllerEvento)")
    void listarEdiciones_ok() {
        List<Edicion> lista = controllerEvento.listarEdiciones();
        assertNotNull(lista);
        assertTrue(lista.size() >= 1);
        assertTrue(lista.stream().anyMatch(e -> nombreEdicionBase.equals(e.getNombre())));
    }


    @Test
    @DisplayName("getAsistenteRegistro (CU): asistente sin registros -> lista vacía")
    void getAsistenteRegistro_sinRegistros() {
        Asistente a = altaAsistentePorControllerUsuario(true);
        List<String> ediciones = controllerUsuario.getAsistenteRegistro(a.getNickname());
        assertNotNull(ediciones);
        assertTrue(ediciones.isEmpty());
    }

    @Test
    @DisplayName("getUsuario (CU): devuelve el usuario si existe (organizador)")
    void getUsuario_ok() {
        Organizador o = altaOrganizadorPorControllerUsuario();
        assertNotNull(controllerUsuario.getUsuario(o.getNickname()));
    }


    @Test
    @DisplayName("getDTAsistentes (CU): mapea campos básicos del asistente")
    void getDTAsistentes_ok() {
        Asistente a = altaAsistentePorControllerUsuario(true);

        List<DTUsuarioListaConsulta> lista = controllerUsuario.getDTAsistentes();
        assertNotNull(lista);
        assertTrue(lista.size() >= 1);

        DTUsuarioListaConsulta match = lista.stream()
                .filter(d -> a.getNickname().equals(d.getNickname()))
                .findFirst()
                .orElse(null);

        assertNotNull(match);
        assertEquals(a.getCorreo(), match.getCorreo());
        assertEquals(a.getNombre(), match.getNombre());
        assertEquals(a.getApellido(), match.getApellido());
        assertNotNull(match.getEdiciones());
        assertNotNull(match.getRegistros());
    }

    @Test
    @DisplayName("getDTOrganizadores (CU): mapea campos básicos del organizador")
    void getDTOrganizadores_ok() {
        Organizador o = altaOrganizadorPorControllerUsuario();

        List<DTUsuarioListaConsulta> lista = controllerUsuario.getDTOrganizadores();
        assertNotNull(lista);
        assertTrue(lista.size() >= 1);

        DTUsuarioListaConsulta match = lista.stream()
                .filter(d -> o.getNickname().equals(d.getNickname()))
                .findFirst()
                .orElse(null);

        assertNotNull(match);
        assertEquals(o.getCorreo(), match.getCorreo());
        assertEquals(o.getNombre(), match.getNombre());
        assertEquals(o.getDescripcion(), match.getDescripcion());
        assertNotNull(match.getEdiciones());
    }
}
