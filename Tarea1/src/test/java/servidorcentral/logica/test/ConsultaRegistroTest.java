package servidorcentral.logica.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import servidorcentral.excepciones.UsuarioRepetidoException;
import servidorcentral.logica.Asistente;
import servidorcentral.logica.DTRegistro;
import servidorcentral.logica.DTRegistroDetallado;
import servidorcentral.logica.ETipoNivel;
import servidorcentral.logica.Edicion;
import servidorcentral.logica.Evento;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerEvento;
import servidorcentral.logica.IControllerUsuario;
import servidorcentral.logica.Institucion;
import servidorcentral.logica.Organizador;
import servidorcentral.logica.Patrocinio;
import servidorcentral.logica.TipoRegistro;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ConsultaRegistroTest {

    private static IControllerEvento iCE;
    private static IControllerUsuario iCU;

    @BeforeEach
    public void iniciar() {
        Factory fabrica = Factory.getInstance();
        iCU = fabrica.getIControllerUsuario();
        iCE = fabrica.getIControllerEvento();
    }

    @Test
    @Order(1)
    void testConsultaRegistroAsistenPatrocinio() {
        String descripcionI = "Facultad de Ingeniería de la Universidad de la República";
        String urlI = "https://www.fing.edu.uy";
        String nombreI = "Facultad de Ingeniería";

        // Institucion con imagen
        Institucion INS01 = new Institucion(nombreI, urlI, descripcionI, "institucion.png");

        String nicknameTest = "gusgui02";
        String correoTest = "gustavoguimerans02@gmail.com";
        String nombreTest = "Gustavo";
        String apellidoTest = "Guimerans";
        LocalDate fNacimientoTest = LocalDate.parse("12/03/2001", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        try {
            // altaAsistente con imagen
            iCU.altaAsistente(nicknameTest, correoTest, nombreTest, apellidoTest, fNacimientoTest, INS01, "1234", "asis.png");
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }
        Asistente ASI01 = iCU.getAsistente(nicknameTest);

        String nombreE = "Evento Test";
        String descripcionE = "Descripcion del Evento Test";
        LocalDate fechaE = LocalDate.parse("20/12/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String siglaE = "ET2024";
        try {
            // altaEvento con imagen
            iCE.altaEvento(nombreE, descripcionE, fechaE, siglaE, null, "evento.png");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Evento EVE01 = iCE.getEvento(nombreE);

        String nicknameO = "org2341";
        String correoO = "contacto@miseventos.com";
        String nombreO = "MisEventos";
        String descripcionO = "Empresa de organización de eventos.";
        String paginaWebO = "https://miseventos.com";
        try {
            // altaOrganizador con imagen
            iCU.altaOrganizador(nicknameO, correoO, nombreO, descripcionO, paginaWebO, "1234", "org.png");
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }
        Organizador ORG01 = iCU.getOrganizador(nicknameO);

        String nombreEd = "Edicion Test";
        LocalDate fechaInicioEd = LocalDate.parse("01/11/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate fechaFinEd = LocalDate.parse("10/12/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate fechaAlta = LocalDate.parse("01/10/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String lugarEd = "Lugar Test";
        String ciudad = "Ciudad Test";
        try {
            iCE.altaEdicionDeEvento(nombreEd, siglaE, ciudad, lugarEd, fechaInicioEd, fechaFinEd, fechaAlta, EVE01, ORG01, "imagenEd.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Edicion EDI01 = iCE.findEdicion(nombreEd);

        String nombreTR = "Tipo Registro Test";
        String descripcionTR = "Descripcion del Tipo de Registro Test";
        float costoTR = 250.0f;
        int limiteTR = 100;
        try {
            iCE.altaTipoRegistro(nombreTR, descripcionTR, costoTR, limiteTR, EDI01);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        TipoRegistro TRI01 = EDI01.getEdicionTR(nombreTR);

        String codigoP = "PAT2024";
        LocalDate fechaInicioP = LocalDate.parse("01/10/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Float montoP = 5000.0f;
        Integer cupoP = 50;
        ETipoNivel nivelP = ETipoNivel.Bronce;
        Patrocinio PAT01 = new Patrocinio(codigoP, fechaInicioP, cupoP, montoP, nivelP, INS01, EDI01, TRI01);
        EDI01.addLinkPatrocinio(PAT01);
        INS01.agregarPatrocinio(PAT01);

        try {
            iCE.altaRegistro(nombreEd, nicknameTest, nombreTR, codigoP);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            DTRegistroDetallado dTRD = iCU.getRegistroDetalle(EDI01.getNombre(), ASI01.getNickname());
            assertEquals(dTRD.getNombreEvento(), nombreE);
            assertEquals(dTRD.getNombreEdicion(), nombreEd);
            assertEquals(dTRD.getTipoRegistro(), nombreTR);
            assertEquals(dTRD.getCosto(), 0.0f); // patrocinio => costo 0
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(2)
    void testConsultaRegistroAsistenteSinPatrocinio() {
        String nickAsist = "martin90";
        String mailAsist = "martin90@gmail.com";
        String nombreAsist = "Martin";
        String apellidoAsist = "Rodriguez";
        LocalDate fechaNacAsist = LocalDate.parse("05/07/1990", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        try {
            iCU.altaAsistente(nickAsist, mailAsist, nombreAsist, apellidoAsist, fechaNacAsist, null, "1234", "asis2.png");
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }
        Asistente asist02 = iCU.getAsistente(nickAsist);

        String nombreEvento = "Conferencia Salud";
        String descripcionEvento = "Conferencia internacional de salud 2024";
        LocalDate fechaEvento = LocalDate.parse("15/09/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String siglaEvento = "CS2024";
        try {
            iCE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, null, "evento2.png");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Evento evento02 = iCE.getEvento(nombreEvento);

        String nickOrg = "orgSalud";
        String mailOrg = "salud@eventos.com";
        String nombreOrg = "EventosSalud";
        String descripcionOrg = "Organización de eventos médicos";
        String webOrg = "https://saludeventos.com";
        try {
            iCU.altaOrganizador(nickOrg, mailOrg, nombreOrg, descripcionOrg, webOrg, "1234", "org2.png");
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }
        Organizador organizador02 = iCU.getOrganizador(nickOrg);

        String nombreEdicion = "Edicion Salud 2024";
        LocalDate fechaInicioEd = LocalDate.parse("01/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate fechaFinEd = LocalDate.parse("20/09/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate fechaAlta = LocalDate.parse("01/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String lugarEdicion = "Palacio Legislativo";
        String ciudadEdicion = "Montevideo";
        try {
            iCE.altaEdicionDeEvento(nombreEdicion, siglaEvento, ciudadEdicion, lugarEdicion, fechaInicioEd, fechaFinEd, fechaAlta, evento02, organizador02, "imgEd2.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Edicion ED03 = iCE.findEdicion(nombreEdicion);

        String nombreTipoReg = "Registro General";
        String descripcionTipoReg = "Registro estándar con acceso completo";
        float costoTipoReg = 150.0f;
        int limiteTipoReg = 200;
        try {
            iCE.altaTipoRegistro(nombreTipoReg, descripcionTipoReg, costoTipoReg, limiteTipoReg, ED03);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        // TipoRegistro tipoReg02 = ED03.getEdicionTR(nombreTipoReg); // no se usa

        try {
            iCE.altaRegistro(nombreEdicion, nickAsist, nombreTipoReg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            DTRegistroDetallado regDet = iCU.getRegistroDetalle(ED03.getNombre(), asist02.getNickname());
            DTRegistro dTRe = iCU.getDTRegistro(ED03.getNombre(), asist02.getNickname());

            assertEquals(dTRe.getCosto(), costoTipoReg);
            assertEquals(dTRe.getfInicio(), LocalDate.now());
            assertEquals(regDet.getNombreEvento(), nombreEvento);
            assertEquals(regDet.getNombreEdicion(), nombreEdicion);
            assertEquals(regDet.getTipoRegistro(), nombreTipoReg);
            assertEquals(regDet.getCosto(), costoTipoReg);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(3)
    void testConsultaRegistroAsistenteSinPatrocinio2() {
        String nickAsist = "lucasg90";
        String mailAsist = "lucasg90@gmail.com";
        String nombreAsist = "Lucas";
        String apellidoAsist = "Gimenez";
        LocalDate fechaNacAsist = LocalDate.parse("12/08/1990", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        try {
            iCU.altaAsistente(nickAsist, mailAsist, nombreAsist, apellidoAsist, fechaNacAsist, null, "1234", "asis3.png");
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }
        Asistente asist02 = iCU.getAsistente(nickAsist);

        String nombreEvento = "Congreso Medicina";
        String descripcionEvento = "Congreso internacional de medicina 2024";
        LocalDate fechaEvento = LocalDate.parse("22/09/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String siglaEvento = "CMED24";
        try {
            iCE.altaEvento(nombreEvento, descripcionEvento, fechaEvento, siglaEvento, null, "evento3.png");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Evento evento02 = iCE.getEvento(nombreEvento);

        String nickOrg = "orgMed";
        String mailOrg = "orgmed@eventos.com";
        String nombreOrg = "EventosMedicos";
        String descripcionOrg = "Organización de congresos médicos";
        String webOrg = "https://eventosmedicos.com";
        try {
            iCU.altaOrganizador(nickOrg, mailOrg, nombreOrg, descripcionOrg, webOrg, "1234", "org3.png");
        } catch (UsuarioRepetidoException e) {
            fail(e.getMessage());
        }
        Organizador organizador02 = iCU.getOrganizador(nickOrg);

        String nombreEdicion = "Edicion Medicina 2024";
        LocalDate fechaInicioEd = LocalDate.parse("05/08/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate fechaFinEd = LocalDate.parse("25/09/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate fechaAlta = LocalDate.parse("01/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        String lugarEdicion = "Centro de Convenciones";
        String ciudadEdicion = "Montevideo";
        try {
            iCE.altaEdicionDeEvento(nombreEdicion, siglaEvento, ciudadEdicion, lugarEdicion, fechaInicioEd, fechaFinEd, fechaAlta, evento02, organizador02, "imgEd3.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Edicion ed02 = iCE.findEdicion(nombreEdicion);

        String nombreTipoReg = "Registro Completo";
        String descripcionTipoReg = "Acceso total a todas las charlas";
        float costoTipoReg = 250.0f;
        int limiteTipoReg = 300;
        try {
            iCE.altaTipoRegistro(nombreTipoReg, descripcionTipoReg, costoTipoReg, limiteTipoReg, ed02);
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        try {
            LocalDate fechaAltaTR = LocalDate.parse("01/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            iCE.altaRegistro(nombreEdicion, nickAsist, nombreTipoReg, fechaAltaTR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            DTRegistroDetallado regDet = iCU.getRegistroDetalle(ed02.getNombre(), asist02.getNickname());
            DTRegistro dTRe = iCU.getDTRegistro(ed02.getNombre(), asist02.getNickname());

            assertEquals(dTRe.getCosto(), costoTipoReg);
            assertEquals(dTRe.getfInicio(), LocalDate.parse("01/07/2024", DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            assertEquals(regDet.getNombreEvento(), nombreEvento);
            assertEquals(regDet.getNombreEdicion(), nombreEdicion);
            assertEquals(regDet.getTipoRegistro(), nombreTipoReg);
            assertEquals(regDet.getCosto(), costoTipoReg);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
