package com.controllers;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.config.WSClientProvider;
import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.RolUsuario;

import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;
import cliente.ws.sc.DtEdicion;
import cliente.ws.sc.DtOrganizador;
import cliente.ws.sc.DtRegistro;
import cliente.ws.sc.DtTipoRegistro;
import cliente.ws.sc.DtPatrocinio;

import jakarta.xml.ws.Binding;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.soap.SOAPBinding;

import javax.xml.datatype.XMLGregorianCalendar;

@WebServlet(name = "ConsultaEdicionMobileSvt", urlPatterns = {"/ediciones"})
public class ConsultaEdicionMovilSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");



  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    final String nombreEvento  = decode(req.getParameter("evento"));
    final String nombreEdicion = decode(req.getParameter("edicion"));

    if (req.getParameter("msgOk") != null && req.getAttribute("msgOk") == null) {
      req.setAttribute("msgOk", decode(req.getParameter("msgOk")));
    }
    if (req.getParameter("msgError") != null && req.getAttribute("msgError") == null) {
      req.setAttribute("msgError", decode(req.getParameter("msgError")));
    }

    if (isBlank(nombreEvento) || isBlank(nombreEdicion)) {
      req.setAttribute("msgError", "Faltan parámetros: evento y/o edición.");
      forwardMobile(req, resp);
      return;
    }

    HttpSession session = req.getSession(false);
    boolean esOrganizador = false;
    boolean esAsistente   = false;
    String  nickSesion    = null;

    if (session != null) {
      Object dto = session.getAttribute("usuario_logueado");
      if (dto instanceof DtSesionUsuario u) {
        if (u.getRol() != null) {
          esOrganizador = (u.getRol() == RolUsuario.ORGANIZADOR);
          esAsistente   = (u.getRol() == RolUsuario.ASISTENTE);
        }
        nickSesion = nz(u.getNickname());
        if (isBlank(nickSesion)) nickSesion = nz(u.getCorreo());
      }
      if (isBlank(nickSesion)) {
        Object asis = session.getAttribute("usuarioAsistente");
        if (asis instanceof String s) { esAsistente = true; nickSesion = s; }
      }
      if (isBlank(nickSesion)) {
        Object org = session.getAttribute("usuarioOrganizador");
        if (org instanceof String s) { esOrganizador = true; nickSesion = s; }
      }
      if (isBlank(nickSesion)) {
        Object nickAttr = session.getAttribute("NICKNAME");
        if (nickAttr != null && !String.valueOf(nickAttr).isBlank()) {
          nickSesion = String.valueOf(nickAttr);
          if (!esOrganizador) esAsistente = true;
        }
      }
    }

    try {
        WebServicesService service = WSClientProvider.newService();
      WebServices port = service.getWebServicesPort();

      DtEdicion ed = port.consultaEdicionDeEvento(nombreEvento, nombreEdicion);
      if (ed == null || "NO_ENCONTRADA".equalsIgnoreCase(nz(ed.getEstado()))) {
        req.setAttribute("msgError", "No se encontró la edición solicitada.");
        forwardMobile(req, resp);
        return;
      }

      Map<String, Object> VM = new HashMap<>();
      VM.put("eventoNombre", nombreEvento);
      VM.put("nombre", nz(ed.getNombre()));
      VM.put("sigla",  nz(ed.getSigla()));

      String fIni = ed.getFInicioS();
      String fFinS = ed.getFFinS();
      VM.put("fechaIni", fIni);
      VM.put("fechaFin", fFinS);
      VM.put("ciudad",   nz(ed.getCiudad()));
      VM.put("pais",     nz(ed.getPais()));
      VM.put("estado",   nz(ed.getEstado()));
      LocalDate fFin = toLocalDate(ed.getFFin());

      boolean finalizado = (fFin != null) && LocalDate.now().isAfter(fFin);
      VM.put("finalizado", finalizado);

      String img = nz(ed.getImagenWebPath());
      VM.put("imagen", !img.isBlank() ? (req.getContextPath() + img) : null);

      List<DtOrganizador> orgs = listOrEmpty(ed.getOrganizadores());
      VM.put("organizadorNombre", joinOrganizadores(orgs));

      List<Map<String,String>> regsVM = new ArrayList<>();
      List<DtRegistro> regs = listOrEmpty(ed.getRegistros());
      for (DtRegistro r : regs) {
        Map<String,String> row = new LinkedHashMap<>();
        row.put("asistente", nz(r.getAsistenteNickname()));
        row.put("tipo",      nz(r.getTipoRegistroNombre()));
        row.put("fecha",     format(toLocalDate(r.getFInicio())));
        regsVM.add(row);
      }
      VM.put("registros", regsVM);

      List<Map<String,String>> tiposVM = new ArrayList<>();
      List<DtTipoRegistro> tregs = listOrEmpty(ed.getTipoRegistros());
      for (DtTipoRegistro tr : tregs) {
        Map<String,String> row = new LinkedHashMap<>();
        row.put("nombre", nz(tr.getNombre()));
        row.put("costo",  toStr(tr.getCosto()));
        row.put("cupos",  toStr(tr.getCupo()));
        row.put("cupoTotal", row.get("cupos"));
        tiposVM.add(row);
      }
      VM.put("tipos", tiposVM);

      Map<String,String> miRegVM = null;
      boolean esAsistenteInscriptoEd = false;
      if (esAsistente && nickSesion != null) {
        String nickNorm = nickSesion.trim().toLowerCase();
        for (DtRegistro r : regs) {
          String nickReg = nz(r.getAsistenteNickname());
          if (!nickReg.isBlank() && nickReg.trim().toLowerCase().equals(nickNorm)) {
            esAsistenteInscriptoEd = true;
            miRegVM = new LinkedHashMap<>();
            miRegVM.put("tipo",  nz(r.getTipoRegistroNombre()));
            miRegVM.put("fecha", r.getFInicioS());
            break;
          }
        }
      }
      VM.put("miRegistro", miRegVM);

      List<DtPatrocinio> pats = listOrEmpty(ed.getPatrocinios());
      req.setAttribute("patrocinios", pats);

      boolean esOrganizadorDeEstaEdicion = false;
      if (esOrganizador && orgs != null && nickSesion != null) {
        String nickNorm = nickSesion.trim().toLowerCase();
        for (DtOrganizador o : orgs) {
          String id = prefer(nz(o.getNickname()), nz(o.getNombre()));
          if (!id.isBlank() && id.trim().toLowerCase().equals(nickNorm)) {
            esOrganizadorDeEstaEdicion = true; break;
          }
        }
      }

      req.setAttribute("ES_ORGANIZADOR", esOrganizador);
      req.setAttribute("ES_ASISTENTE",   esAsistente);
      req.setAttribute("ES_ORG",         esOrganizador);
      req.setAttribute("ES_ASIS",        esAsistente);
      req.setAttribute("ES_ORGANIZADOR_ED",         esOrganizadorDeEstaEdicion);
      req.setAttribute("ES_ORG_ED",                 esOrganizadorDeEstaEdicion);
      req.setAttribute("ES_ASISTENTE_INSCRIPTO_ED", esAsistenteInscriptoEd);
      req.setAttribute("ES_ASIS_ED",                esAsistenteInscriptoEd);

      req.setAttribute("VM", VM);
      forwardMobile(req, resp);

    } catch (Exception e) {
      req.setAttribute("msgError", "Error al consultar: " + e.getMessage());
      forwardMobile(req, resp);
    }
  }

  private static void forwardMobile(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/views/ediciones.jsp").forward(req, resp);
  }

  private static String decode(String s) { return (s == null) ? null : URLDecoder.decode(s, StandardCharsets.UTF_8); }
  private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
  private static String nz(String s) { return (s == null) ? "" : s; }
  private static String format(LocalDate d) { return (d == null) ? null : d.format(FMT); }
  private static String toStr(Number n) { return (n == null) ? null : String.valueOf(n); }
  private static String prefer(String a, String b) { return !isBlank(a) ? a : nz(b); }
  private static <T> List<T> listOrEmpty(List<T> xs) { return (xs == null) ? java.util.List.of() : xs; }

  private static LocalDate toLocalDate(Object d) {
    if (d == null) return null;
    if (d instanceof LocalDate ld) return ld;
    if (d instanceof XMLGregorianCalendar xc) {
      return xc.toGregorianCalendar().toZonedDateTime()
               .withZoneSameInstant(ZoneId.systemDefault()).toLocalDate();
    }
    return null;
  }

  private static String joinOrganizadores(List<DtOrganizador> orgs) {
    if (orgs == null || orgs.isEmpty()) return null;
    List<String> nombres = new ArrayList<>();
    for (DtOrganizador o : orgs) {
      if (o == null) continue;
      String nom = prefer(nz(o.getNombre()), nz(o.getNickname()));
      if (!isBlank(nom)) nombres.add(nom);
    }
    return nombres.isEmpty() ? null : String.join(", ", nombres);
  }
}

