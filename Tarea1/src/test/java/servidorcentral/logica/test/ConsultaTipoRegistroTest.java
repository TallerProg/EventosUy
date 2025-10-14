package servidorcentral.logica.test;

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

import servidorcentral.excepciones.UsuarioRepetidoException;
import servidorcentral.logica.DTTipoRegistro;
import servidorcentral.logica.Edicion;
import servidorcentral.logica.Evento;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerEvento;
import servidorcentral.logica.IControllerUsuario;
import servidorcentral.logica.Organizador;
import servidorcentral.logica.TipoRegistro;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConsultaTipoRegistroTest {
    private static IControllerEvento controllerE;
    private static IControllerUsuario controlerUSR;

    @BeforeEach
    public void iniciar() {
        Factory fabrica = Factory.getInstance();
        controlerUSR = fabrica.getIControllerUsuario();
        controllerE = fabrica.getIControllerEvento();
    }

    @Test
    @Order(1)
    void testConsultaValoresDTTipoRegistro() {
        String nombreEvento = "EventoTesteo1";
        String descripcionEvento = "Estamos testeando evento1";
        LocalDate fechaEvento = LocalDate.of(2025, 5, 1);
        String siglaEvento = "TEST1";
        try {
            controllerE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, null, "evento1.png");
        } catch (Exception e1) {
            e1.printStackTrace();
            fail("No debería fallar altaEvento: " + e1.getMessage());
        }
        Evento evento = controllerE.getEvento(nombreEvento);

        String nickOrg = "organizadorTesteo1";
        String mailOrg = "org@test.com";
        String nombreOrg = "Testeador";
        String descripcionOrg = "Organización de testeos";
        String webOrg = "https://testeo.com";
        try {
            // <- ahora con imagen del organizador
            controlerUSR.altaOrganizador(nickOrg, mailOrg, nombreOrg, descripcionOrg, webOrg, "1234", "org1.png");
        } catch (UsuarioRepetidoException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        Organizador org = controlerUSR.getOrganizador(nickOrg);

        String nombreEdicion = "Edicion test1";
        LocalDate fechaInicioEd = LocalDate.of(2025, 3, 1);
        LocalDate fechaFinEd   = LocalDate.of(2025, 4, 1);
        LocalDate fechaAlta    = LocalDate.of(2024, 4, 1);
        String lugarEdicion    = "El testeador";
        String ciudadEdicion   = "test";
        try {
            controllerE.altaEdicionDeEvento(
                nombreEdicion, siglaEvento, ciudadEdicion, lugarEdicion,
                fechaInicioEd, fechaFinEd, fechaAlta,
                evento, org, "imagenTest"
            );
        } catch (Exception e) {
            e.printStackTrace();
            fail("No debería fallar altaEdicionDeEvento: " + e.getMessage());
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
            fail("No debería fallar altaTipoRegistro: " + e2.getMessage());
        }

        DTTipoRegistro dtr = controllerE.consultaTipoRegistro(nombreEdicion, nombreTipoReg);
        assertNotNull(dtr);
        assertEquals("Registro Test", dtr.getNombre());
        assertEquals("Registro para testear", dtr.getDescripcion());
        assertEquals(100.0f, dtr.getCosto());
        assertEquals(14, dtr.getCupo());
    }

    @Test
    @Order(2)
    void testcargarDatosCombo() {
        try {
            String nombreEvento = "Maratón de Montevideo";
            String descripcionEvento = "Carrera anual de 42K, 21K y 10K por las calles de Montevideo.";
            LocalDate fechaEvento = LocalDate.of(2025, 4, 6);
            String siglaEvento = "MMM2025";

            controllerE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, null, "maraton.png");
            Evento evento = controllerE.getEvento(nombreEvento);

            String nickOrg = "orgMontevideoRunners";
            String mailOrg = "contacto@montevideorunners.uy";
            String nombreOrg = "Montevideo Runners Club";
            String descripcionOrg = "Organización de eventos deportivos en Uruguay.";
            String webOrg = "https://montevideorunners.uy";

            // <- también con imagen del organizador
            controlerUSR.altaOrganizador(nickOrg, mailOrg, nombreOrg, descripcionOrg, webOrg, "1234", "org2.png");
            Organizador org = controlerUSR.getOrganizador(nickOrg);

            String nombreEdicion = "Edición 2025";
            LocalDate fechaInicioEd = LocalDate.of(2025, 4, 6);
            LocalDate fechaFinEd   = LocalDate.of(2025, 4, 6);
            LocalDate fechaAlta    = LocalDate.of(2024, 4, 1);
            String lugarEdicion    = "Rambla de Montevideo";
            String ciudadEdicion   = "Montevideo";

            controllerE.altaEdicionDeEvento(
                nombreEdicion, siglaEvento, ciudadEdicion, lugarEdicion,
                fechaInicioEd, fechaFinEd, fechaAlta,
                evento, org, "imagenEdicion"
            );
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
            fail("No debería fallar el flujo completo: " + e.getMessage());
        }
    }
}
