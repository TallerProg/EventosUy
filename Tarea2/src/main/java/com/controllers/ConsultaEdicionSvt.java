package com.controllers;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import servidorcentral.logica.DTSesionUsuario; 
import servidorcentral.logica.RolUsuario;      

import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;
import cliente.ws.sc.DTEdicion;
import cliente.ws.sc.DTOrganizador;
import cliente.ws.sc.DTRegistro;
import cliente.ws.sc.DTTipoRegistro;
import cliente.ws.sc.DTPatrocinio;

import jakarta.xml.ws.Binding;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.soap.SOAPBinding;

@WebServlet(name = "ConsultaEdicionSvt", urlPatterns = {"/ediciones-consulta"})
public class ConsultaEdicionSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  /** Obtiene el port y setea MTOM + WS_URL desde web.xml */
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

    String nombreEvento  = decode(req.getParameter("evento"));
    String nombreEdicion = decode(req.getParameter("edicion"));

    if (isBlank(nombreEvento) || isBlank(nombreEdicion)) {
      req.setAttribute("msgError", "Faltan parametros: evento y/o edicion.");
      forward(req, resp);
      return;
    }

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
      if (dto instanceof DTSesionUsuario u) {
        if (u.getRol() != null) {
          esOrganizador = (u.getRol() == RolUsuario.ORGANIZADOR);
          esAsistente   = (u.getRol() == RolUsuario.ASISTENTE);
        }
        nickSesion = safe(u.getNickname());
        if (isBlank(nickSesion)) nickSesion = safe(u.getCorreo());
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

      DTEdicion ed = port.consultaEdicionDeEvento(nombreEvento, nombreEdicion);
      if (ed == null) {
        req.setAttribute("msgError", "No se encontro la edicion solicitada.");
        forward(req, resp);
        return;
      }

      Map<String, Object> VM = new HashMap<>();
      VM.put("eventoNombre", nombreEvento);
      VM.put("nombre",   safe(ed.getNombre()));
      VM.put("sigla",    safe(ed.getSigla()));
      VM.put("fechaIni", format(ed.getFInicio()));
      VM.put("fechaFin", format(ed.getFFin()));
      VM.put("ciudad",   safe(ed.getCiudad()));
      VM.put("pais",     safe(ed.getPais()));
      VM.put("estado",   safe(ed.getEstado()));

      boolean finalizado = (ed.getFFin() != null) && LocalDate.now().isAfter(ed.getFFin());
      VM.put("finalizado", finalizado);

      String img = safe(ed.getImagenWebPath());
      VM.put("imagen", (img != null && !img.isBlank()) ? (req.getContextPath() + img) : null);

      List<DTOrganizador> orgs = listOrEmpty(ed.getOrganizadores());
      VM.put("organizadorNombre", joinOrganizadores(orgs));

      List<Map<String,String>> regsVM = new ArrayList<>();
      List<DTRegistro> regs = listOrEmpty(ed.getRegistros());
      for (DTRegistro r : regs) {
        Map<String,String> row = new LinkedHashMap<>();
        row.put("asistente", safe(r.getAsistenteNickname()));
        row.put("tipo",      safe(r.getTipoRegistroNombre()));
        row.put("fecha",     formatSafe(r.getFInicio()));
        row.put("estado",    "");
        regsVM.add(row);
      }
      VM.put("registros", regsVM);

      List<Map<String,String>> tiposVM = new ArrayList<>();
      List<DTTipoRegistro> tregs = listOrEmpty(ed.getTipoRegistros());
      for (DTTipoRegistro tr : tregs) {
        Map<String,String> row = new LinkedHashMap<>();
        row.put("nombre", safe(tr.getNombre()));
        row.put("costo",  toStr(tr.getCosto()));
        row.put("cupos",  toStr(tr.getCupo()));
        row.put("cupoTotal", row.get("cupos"));
        tiposVM.add(row);
      }
      VM.put("tipos", tiposVM);

      Map<String,String> miRegVM = null;
      boolean esAsistenteInscriptoEd = false;
      if (esAsistente && nickSesion != null) {
        for (DTRegistro r : regs) {
          String nickReg = safe(r.getAsistenteNickname());
          if (!isBlank(nickReg) && nickSesion.equalsIgnoreCase(nickReg)) {
            esAsistenteInscriptoEd = true;
            miRegVM = new LinkedHashMap<>();
            miRegVM.put("tipo",  safe(r.getTipoRegistroNombre()));
            miRegVM.put("fecha", formatSafe(r.getFInicio()));
            miRegVM.put("estado", "");
            break;
          }
        }
      }
      VM.put("miRegistro", miRegVM);

      List<DTPatrocinio> pats = listOrEmpty(ed.getPatrocinios());
      req.setAttribute("patrocinios", pats);

      boolean esOrganizadorDeEstaEdicion = false;
      if (esOrganizador && orgs != null && nickSesion != null) {
        String nickNorm = nickSesion.trim().toLowerCase();
        for (DTOrganizador o : orgs) {
          String id = prefer(safe(o.getNickname()), safe(o.getNombre()));
          if (!isBlank(id) && id.trim().toLowerCase().equals(nickNorm)) {
            esOrganizadorDeEstaEdicion = true;
            break;
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
      forward(req, resp);

    } catch (Exception e) {
      req.setAttribute("msgError", "Error al consultar la edicion: " + e.getMessage());
      forward(req, resp);
    }
  }

  private static void forward(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/views/ConsultaEdicion.jsp").forward(req, resp);
  }

  private static String decode(String s) { return (s == null) ? null : URLDecoder.decode(s, StandardCharsets.UTF_8); }
  private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
  private static String safe(Object o) { return (o == null) ? null : String.valueOf(o); }
  private static String format(LocalDate d) { return (d == null) ? null : d.format(FMT); }
  private static String formatSafe(LocalDate d) { try { return format(d); } catch (Throwable ignore) { return null; } }
  private static String toStr(Number n) { return (n == null) ? null : String.valueOf(n); }
  private static String prefer(String a, String b) { return !isBlank(a) ? a : b; }
  private static <T> List<T> listOrEmpty(List<T> xs) { return (xs == null) ? java.util.List.of() : xs; }

  /** Une nombres de organizadores en una cadena amigable. */
  private static String joinOrganizadores(List<DTOrganizador> orgs) {
    if (orgs == null || orgs.isEmpty()) return null;
    List<String> nombres = new ArrayList<>();
    for (DTOrganizador o : orgs) {
      if (o == null) continue;
      String nom = prefer(safe(o.getNombre()), safe(o.getNickname()));
      if (!isBlank(nom)) nombres.add(nom);
    }
    return nombres.isEmpty() ? null : String.join(", ", nombres);
  }
}

