package ServidorCentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ServidorCentral.logica.Categoria;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.IControllerUsuario;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ControllerEventoTest {

    private static IControllerEvento controladorEve;
    private static IControllerUsuario cu;

    @BeforeAll
    public static void iniciar() {
        Factory fabrica = Factory.getInstance();
        controladorEve = fabrica.getIControllerEvento();
        cu = fabrica.getIControllerUsuario();
    }

    @Test
    @Order(1)
    void testRegistrarEventoOK() {
        String nomTest   = "ev1_ok";
        String descTest  = "desc";
        LocalDate fAlta  = LocalDate.parse("12/03/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String sigla     = "EV1OK";
        List<Categoria> categorias = new ArrayList<>();
        String imagen    = "evento_ok.png";

        try {
            // NUEVA FIRMA: incluye imagen
            controladorEve.altaEvento(nomTest, descTest, fAlta, sigla, categorias, imagen);

            Evento e = controladorEve.getEvento(nomTest);
            assertEquals(nomTest,  e.getNombre());
            assertEquals(descTest, e.getDescripcion());
            assertEquals(fAlta,    e.getFAlta());
            assertEquals(sigla,    e.getSigla());
            assertEquals(categorias, e.getCategoria());
            // si tu clase Evento expone getImg():
            assertEquals(imagen,   e.getImg());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testRegistrarEventoRep() {
        String nomTest   = "ev2_rep";
        String descTest  = "desc";
        LocalDate fAlta  = LocalDate.parse("12/03/2022", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String sigla     = "EV2REP";
        List<Categoria> categorias = new ArrayList<>();
        String imagen    = "evento_rep.png";

        try {
            // primer alta OK
            controladorEve.altaEvento(nomTest, descTest, fAlta, sigla, categorias, imagen);
        } catch (Exception e) {
            fail("No debería fallar el primer alta: " + e.getMessage());
        }

        // segundo alta con el mismo nombre debe lanzar excepción
        assertThrows(Exception.class, () -> {
            controladorEve.altaEvento(nomTest, descTest, fAlta, sigla, categorias, imagen);
        });
    }
}
