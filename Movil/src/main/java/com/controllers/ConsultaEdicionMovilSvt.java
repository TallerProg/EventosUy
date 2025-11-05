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

import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.RolUsuario;

import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;
import cliente.ws.sc.DtEvento;
import cliente.ws.sc.DtEdicion;
import cliente.ws.sc.DtOrganizador;
import cliente.ws.sc.DtRegistro;
import cliente.ws.sc.DtTipoRegistro;
import cliente.ws.sc.DtPatrocinio;

import jakarta.xml.ws.Binding;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.soap.SOAPBinding;

import javax.xml.datatype.XMLGregorianCalendar;

@WebServlet(name = "ConsultaEdicionMobileSvt", urlPatterns = {"/ConsultaEdicionMobile"})
public class ConsultaEdicionMobileSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  private WebServices getPort(HttpServletRequest req) {
    WebServicesService svc = new WebServicesService();
    WebServices port = svc.getWebServicesPort();
    Binding b = ((BindingProvider) port).getBinding();
    if (b instanceof SOAPBinding sb) sb.setMTOMEnabled(true);
    String wsUrl = req.getServletContext().getInitParameter("WS_URL");
    if (wsUrl != null && !wsUrl.isBlank()) {
      ((BindingProvider) port).getRequestContext().put(
          BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsUrl
      );
    }
    return port;
  }

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
      WebServices port = getPort(req);

      if (isBlank(nombreEvento)) {
        List<Map<String,String>> eventosVM = new ArrayList<>();

        List<DtEvento> eventos = listOrEmpty(callListarEventos(port));

        for (DtEvento e : eventos) {
          if (e == null) continue;
          Map<String,String> row = new LinkedHashMap<>();
          row.put("nombre", nz(e.getNombre()));
          String img = nz(getEventoImagenWebPath(e));
          row.put("imagen", img.isBlank() ? null : (req.getContextPath() + img));
          eventosVM.add(row);
        }

        req.setAttribute("EVENTOS", eventosVM);
        forwardMobile(req, resp);
        return;
      }

      if (!isBlank(nombreEvento) && isBlank(nombreEdicion)) {
        List<Map<String,String>> edsVM = new ArrayList<>();

        List<DtEdicion> eds = listOrEmpty(callListarEdicionesDeEvento(port, nombreEvento));

        for (DtEdicion ed : eds) {
          if (ed == null) continue;
          if (!"Aprobada".equalsIgnoreCase(nz(ed.getEstado()))) continue;

          Map<String,String> row = new LinkedHashMap<>();
          row.put("nombre", nz(ed.getNombre()));
          row.put("estado", nz(ed.getEstado()));
          row.put("fechaIni", format(toLocalDate(ed.getFInicio())));
          row.put("fechaFin", format(toLocalDate(ed.getFFin())));

          List<DtOrganizador> orgs = listOrEmpty(ed.getOrganizadores());
          row.put("organizadorNombre", joinOrganizadores(orgs));

          String img = nz(ed.getImagenWebPath());
          row.put("imagen", img.isBlank() ? null : (req.getContextPath() + img));

          edsVM.add(row);
        }

        Map<String,Object> VMmin = new HashMap<>();
        VMmin.put("eventoNombre", nombreEvento);
        req.setAttribute("VM", VMmin);
        req.setAttribute("EDICIONES", edsVM);
        // Paso 2 → JSP móvil
        forwardMobile(req, resp);
        return;
      }

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

      LocalDate fIni = toLocalDate(ed.getFInicio());
      LocalDate fFin = toLocalDate(ed.getFFin());
      VM.put("fechaIni", format(fIni));
      VM.put("fechaFin", format(fFin));
      VM.put("ciudad",   nz(ed.getCiudad()));
      VM.put("pais",     nz(ed.getPais()));
      VM.put("estado",   nz(ed.getEstado()));

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
            miRegVM.put("fecha", format(toLocalDate(r.getFInicio())));
            miRegVM.put("estado", "");
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
      // Paso 3 → JSP móvil
      forwardMobile(req, resp);

    } catch (Exception e) {
      req.setAttribute("msgError", "Error al consultar: " + e.getMessage());
      forwardMobile(req, resp);
    }
  }

  private List<DtEvento> callListarEventos(WebServices port) {
    List<DtEvento> out = new ArrayList<>();
    try {
      List<DtEvento> tmp = (List<DtEvento>) port.listarEventos(); 
      if (tmp != null) out.addAll(tmp);
    } catch (Throwable ignore) { }
    return out;
  }

  private List<DtEdicion> callListarEdicionesDeEvento(WebServices port, String nombreEvento) {
    List<DtEdicion> out = new ArrayList<>();
    try {
      List<DtEdicion> tmp = (List<DtEdicion>) port.listarEdiciones(nombreEvento); 
      if (tmp != null) out.addAll(tmp);
    } catch (Throwable ignore) { }
    return out;
  }


  private static void forwardMobile(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/views/mobile/ConsultaEdicionMobile.jsp").forward(req, resp);
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
      return xc.toGregorianCalendar().toZonedDateTime().withZoneSameInstant(ZoneId.systemDefault()).toLocalDate();
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

  private static String getEventoImagenWebPath(DtEvento e) {
    try {
      return String.valueOf(DtEvento.class.getMethod("getImagenWebPath").invoke(e));
    } catch (Throwable ignore) {
      try { return String.valueOf(DtEvento.class.getMethod("getImg").invoke(e)); }
      catch (Throwable ignore2) { return ""; }
    }
  }
}
