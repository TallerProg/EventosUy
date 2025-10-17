package com.controllers;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidorcentral.logica.ControllerUsuario.DTSesionUsuario;
import servidorcentral.logica.ControllerUsuario.RolUsuario;
import servidorcentral.logica.DTEdicion;
import servidorcentral.logica.DTOrganizador;
import servidorcentral.logica.DTPatrocinio;
import servidorcentral.logica.DTRegistro;
import servidorcentral.logica.DTTipoRegistro;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerEvento;

@WebServlet(name = "ConsultaEdicionSvt", urlPatterns = {"/ediciones-consulta"})
public class ConsultaEdicionSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String nombreEvento  = decode(req.getParameter("evento"));
    String nombreEdicion = decode(req.getParameter("edicion"));

    if (isBlank(nombreEvento) || isBlank(nombreEdicion)) {
      req.setAttribute("msgError", "Faltan parámetros: evento y/o edición.");
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
      if (dto instanceof DTSesionUsuario) {
        DTSesionUsuario u = (DTSesionUsuario) dto;
        if (u.getRol() != null) {
          esOrganizador = (u.getRol() == RolUsuario.ORGANIZADOR);
          esAsistente   = (u.getRol() == RolUsuario.ASISTENTE);
        }
        nickSesion = safe(u.getNickname());
        if (isBlank(nickSesion)) nickSesion = safe(u.getCorreo()); 
      }
      if (isBlank(nickSesion)) {
        Object asis = session.getAttribute("usuarioAsistente");
        if (asis instanceof String) { esAsistente = true; nickSesion = (String) asis; }
      }
      if (isBlank(nickSesion)) {
        Object org = session.getAttribute("usuarioOrganizador");
        if (org instanceof String) { esOrganizador = true; nickSesion = (String) org; }
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
      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();

      DTEdicion ed = ctrl.consultaEdicionDeEvento(nombreEvento, nombreEdicion);
      if (ed == null) {
        req.setAttribute("msgError", "No se encontró la edición solicitada.");
        forward(req, resp);
        return;
      }

      Map<String, Object> VM = new HashMap<>();
      VM.put("eventoNombre", nombreEvento);
      VM.put("nombre",   safe(ed.getNombre()));
      VM.put("sigla",    safe(ed.getSigla()));
      VM.put("fechaIni", format(ed.getfInicio()));
      VM.put("fechaFin", format(ed.getfFin()));
      VM.put("ciudad",   safe(ed.getCiudad()));
      VM.put("pais",     safe(ed.getPais()));
      VM.put("estado",   safe(ed.getEstado()));

      boolean finalizado = (ed.getfFin() != null) && LocalDate.now().isAfter(ed.getfFin());
      VM.put("finalizado", finalizado);

      String img = safe(ed.getImagenWebPath());
      VM.put("imagen", (img != null && !img.isBlank()) ? (req.getContextPath() + img) : null);

      VM.put("organizadorNombre", joinOrganizadores(ed.getOrganizadores()));

      List<Map<String,String>> regsVM = new ArrayList<>();
      List<DTRegistro> regs = ed.getRegistros();
      if (regs != null) {
        for (DTRegistro r : regs) {
          Map<String,String> row = new LinkedHashMap<>();
          row.put("asistente", safe(r.getAsistenteNickname()));
          row.put("tipo",      safe(r.getTipoRegistroNombre()));
          row.put("fecha",     formatSafe(r.getfInicio())); 
          row.put("estado",    ""); 
          regsVM.add(row);
        }
      }
      VM.put("registros", regsVM);

      List<Map<String,String>> tiposVM = new ArrayList<>();
      List<DTTipoRegistro> tregs = ed.getTipoRegistros();
      if (tregs != null) {
        for (DTTipoRegistro tr : tregs) {
          Map<String,String> row = new LinkedHashMap<>();
          row.put("nombre", safe(tr.getNombre()));
          row.put("costo",  toStr(tr.getCosto()));
          row.put("cupos",  toStr(tr.getCupo()));
          row.put("cupoTotal", row.get("cupos"));
          tiposVM.add(row);
        }
      }
      VM.put("tipos", tiposVM);

      Map<String,String> miRegVM = null;
      boolean esAsistenteInscriptoEd = false;
      if (esAsistente && nickSesion != null && regs != null) {
        for (DTRegistro r : regs) {
          String nickReg = safe(r.getAsistenteNickname());
          if (!isBlank(nickReg) && nickSesion.equalsIgnoreCase(nickReg)) {
            esAsistenteInscriptoEd = true;
            miRegVM = new LinkedHashMap<>();
            miRegVM.put("tipo",  safe(r.getTipoRegistroNombre()));
            miRegVM.put("fecha", formatSafe(r.getfInicio()));
            miRegVM.put("estado", "");
            break;
          }
        }
      }
      VM.put("miRegistro", miRegVM);

      List<DTPatrocinio> pats = ed.getPatrocinios();
      req.setAttribute("patrocinios", pats);

      boolean esOrganizadorDeEstaEdicion = false;
      if (esOrganizador && ed.getOrganizadores() != null && nickSesion != null) {
        String nickNorm = nickSesion.trim().toLowerCase();
        for (DTOrganizador o : ed.getOrganizadores()) {
          String id = prefer(o.getNickname(), o.getNombre());
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
      req.setAttribute("msgError", "Error al consultar la edición: " + e.getMessage());
      forward(req, resp);
    }
  }


  private static void forward(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher("/WEB-INF/views/ConsultaEdicion.jsp").forward(req, resp);
  }

  private static String decode(String s) {
    return (s == null) ? null : URLDecoder.decode(s, StandardCharsets.UTF_8);
  }

  private static boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }

  private static String safe(Object o) {
    return (o == null) ? null : String.valueOf(o);
  }

  private static String format(LocalDate d) {
    return (d == null) ? null : d.format(FMT);
  }

  private static String formatSafe(LocalDate d) {
    try { return format(d); } catch (Throwable ignore) { return null; }
  }

  private static String toStr(Number n) {
    return (n == null) ? null : String.valueOf(n);
  }

  private static String prefer(String a, String b) {
    return !isBlank(a) ? a : b;
  }

  private static String joinOrganizadores(List<DTOrganizador> orgs) {
    if (orgs == null || orgs.isEmpty()) return null;
    List<String> nombres = new ArrayList<>();
    for (DTOrganizador o : orgs) {
      if (o == null) continue;
      String nom = prefer(o.getNombre(), o.getNickname());
      if (!isBlank(nom)) nombres.add(nom);
    }
    return nombres.isEmpty() ? null : String.join(", ", nombres);
  }
}

