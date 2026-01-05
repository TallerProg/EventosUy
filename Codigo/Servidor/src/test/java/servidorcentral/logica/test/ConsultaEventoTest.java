package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import servidorcentral.logica.Categoria;
import servidorcentral.logica.DTevento;
import servidorcentral.logica.Evento;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerEvento;
import servidorcentral.logica.IControllerUsuario;
import servidorcentral.logica.Organizador;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConsultaEventoTest {

    private static IControllerEvento controllerE;
    private static IControllerUsuario controlerUSR;

    @BeforeEach
    public void iniciar() {
        Factory fabrica = Factory.getInstance();
        controllerE = fabrica.getIControllerEvento();
        controlerUSR = fabrica.getIControllerUsuario();
    }

    @Test
    @Order(1)
    void consultaEventoExistente() {
        try {
            String nombreEvento = "Existe";
            String descripcionEvento = "Carrera de existencia";
            LocalDate fechaEvento = LocalDate.of(2025, 4, 6);
            String siglaEvento = "EX2025";

            // altaEvento(nombre, desc, fAlta, sigla, categorias, img)
            controllerE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, null, "evento.png");

            assertTrue(controllerE.existeEvento(nombreEvento));
            assertFalse(controllerE.existeEvento("NoExiste"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(2)
    void testConsultarValoresDTEvento() {
        try {
            String nombreEvento = "Maratón de Panama";
            String descripcionEvento = "Carrera anual de 10K";
            LocalDate fechaEvento = LocalDate.of(2025, 4, 6);
            String siglaEvento = "PNM2025";

            controllerE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, null, "evento.png");

            DTevento dte = controllerE.consultaEvento("Maratón de Panama");
            assertNotNull(dte);
            assertEquals("Maratón de Panama", dte.getNombre());
            assertEquals("Carrera anual de 10K", dte.getDescripcion());
            assertEquals(LocalDate.of(2025, 4, 6), dte.getFAlta());
            assertEquals("PNM2025", dte.getSigla());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @Order(3)
    void verificarDatosComboCategoria() {
        try {
            List<String> nombresCategorias = List.of("VIP test", "Entrada test General", "Backstage test Pass");
            for (String nombre : nombresCategorias) {
                controllerE.altaCategoria(nombre);
            }

            List<Categoria> categorias = controllerE.getCategorias();

            String nombreEvento = "eventoo";
            String descripcionEvento = "Carrera anual de 42K, 21K y 10K por las calles de Montevideo.";
            LocalDate fechaEvento = LocalDate.of(2025, 4, 6);
            String siglaEvento = "MMM2025";

            controllerE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, categorias, "evento.png");
            Evento evento = controllerE.getEvento(nombreEvento);

            String nickOrg = "orgtestCEvento";
            String mailOrg = "orgtestCEvento@montevideorunners.uy";
            String nombreOrg = "Montevideo Runners Club";
            String descripcionOrg = "Organización de eventos deportivos en Uruguay.";
            String webOrg = "https://montevideorunners.uy";

            // Asumiendo firma: altaOrganizador(nick, mail, nombre, descripcion, web, password, img)
            controlerUSR.altaOrganizador(nickOrg, mailOrg, nombreOrg, descripcionOrg, webOrg, "1234", "org.png");
            Organizador org = controlerUSR.getOrganizador(nickOrg);

            String nombreEdicion = "Edición test de 2025";
            LocalDate fechaInicioEd = LocalDate.of(2025, 4, 6);
            LocalDate fechaFinEd = LocalDate.of(2025, 4, 6);
            LocalDate fechaAlta = LocalDate.of(2025, 4, 1);
            String lugarEdicion = "Rambla de Montevideo";
            String ciudadEdicion = "Montevideo";

            controllerE.altaEdicionDeEvento(
                nombreEdicion,
                siglaEvento,
                ciudadEdicion,
                lugarEdicion,
                fechaInicioEd,
                fechaFinEd,
                fechaAlta,
                evento,
                org,
                "imagen.jpg"
            );

            List<Categoria> categoriasEvento = evento.getCategoria();
            for (String nombreCat : nombresCategorias) {
                assertTrue(categoriasEvento.stream().anyMatch(c -> c.getNombre().equals(nombreCat)));
            }

            List<String> ediciones = controllerE.listarEdicionesDeEvento(nombreEvento);
            assertNotNull(ediciones);
            assertTrue(ediciones.contains(nombreEdicion));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

		



