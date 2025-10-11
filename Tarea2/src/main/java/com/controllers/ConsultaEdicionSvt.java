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

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.DTEdicion;
import ServidorCentral.logica.DTTipoRegistro;
import ServidorCentral.logica.DTRegistro;
import ServidorCentral.logica.DTPatrocinio;
import ServidorCentral.logica.DTOrganizador;

// >>> imports del módulo de usuarios (como en tu otro servlet)
import ServidorCentral.logica.ControllerUsuario.DTSesionUsuario;
import ServidorCentral.logica.ControllerUsuario.RolUsuario;

@WebServlet(name = "ConsultaEdicionSvt", urlPatterns = {"/ediciones-consulta"})
public class ConsultaEdicionSvt extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // ----- Parámetros obligatorios -----
    String nombreEvento  = decode(req.getParameter("evento"));
    String nombreEdicion = decode(req.getParameter("edicion"));

    if (isBlank(nombreEvento) || isBlank(nombreEdicion)) {
      req.setAttribute("msgError", "Faltan parámetros: evento y/o edición.");
      forward(req, resp);
      return;
    }

    // ----- Flags y nick a partir de usuario_logueado (como en ConsultaEveSvt) -----
    HttpSession session = req.getSession(false);
    boolean esOrganizador = false;
    boolean esAsistente   = false;
    String  nickSesion = null;

    if (session != null) {
      DTSesionUsuario u = (DTSesionUsuario) session.getAttribute("usuario_logueado");
      if (u != null && u.getRol() != null) {
        RolUsuario rol = u.getRol();
        esOrganizador = (rol == RolUsuario.ORGANIZADOR);
        esAsistente   = (rol == RolUsuario.ASISTENTE);
        // intentar obtener nickname del usuario logueado
        nickSesion = extraerNickSeguro(u);
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

      // ================= VM que el JSP espera =================
      Map<String, Object> VM = new HashMap<>();

      // Datos básicos
      VM.put("eventoNombre", nombreEvento);
      VM.put("nombre",   ed.getNombre());
      VM.put("sigla",    ed.getSigla());
      VM.put("fechaIni", format(ed.getfInicio()));
      VM.put("fechaFin", format(ed.getfFin()));
      VM.put("ciudad",   safe(ed.getCiudad()));
      VM.put("pais",     safe(ed.getPais()));
      VM.put("imagen",   null); 
      VM.put("estado", ed.getEstado()); 


      // Organizadores
      VM.put("organizadorNombre", joinOrganizadores(ed.getOrganizadores()));

      // ===== Registros (DTRegistro) =====
      List<DTRegistro> regs = ed.getRegistros();
      List<Map<String,String>> regsVM = new ArrayList<>();
      if (regs != null) {
        for (DTRegistro r : regs) {
          Map<String,String> row = new LinkedHashMap<>();
          row.put("asistente", safe(r.getAsistenteNickname()));
          row.put("tipo",      safe(r.getTipoRegistroNombre()));
          row.put("fecha",     safe(format(r.getfInicio())));
          row.put("estado",    ""); // si tu DTO no trae estado
          regsVM.add(row);
        }
      }
      VM.put("registros", regsVM);

      // ===== Tipos de registro (costo + cupos) =====
      List<Map<String,String>> tiposVM = new ArrayList<>();
      List<DTTipoRegistro> tregs = ed.getTipoRegistros();
      if (tregs != null) {
        for (DTTipoRegistro tr : tregs) {
          Map<String,String> row = new LinkedHashMap<>();
          row.put("nombre", safe(tr.getNombre()));

          // costo
          Float costo = null;
          try { costo = tr.getCosto(); } catch (Throwable ignore) {}
          row.put("costo", (costo != null) ? String.valueOf(costo) : null);

          // cupo
          Integer cupo = null;
          try { cupo = tr.getCupo(); } catch (Throwable ignore) {}
          if (cupo == null) {
            try { cupo = (Integer) tr.getClass().getMethod("getCupo").invoke(tr); } catch (Exception ignore2) {}
          }
          String cupoStr = (cupo != null) ? String.valueOf(cupo) : null;

          row.put("cupos",     cupoStr);
          row.put("cupoTotal", cupoStr);

          tiposVM.add(row);
        }
      }
      VM.put("tipos", tiposVM);

      // ===== "Mi registro" (si hay asistente en sesión) =====
      Map<String,String> miRegVM = null;
      if (esAsistente && nickSesion != null && regs != null) {
        for (DTRegistro r : regs) {
          if (nickSesion.equalsIgnoreCase(safe(r.getAsistenteNickname()))) {
            miRegVM = new LinkedHashMap<>();
            miRegVM.put("tipo",   safe(r.getTipoRegistroNombre()));
            miRegVM.put("fecha",  safe(format(r.getfInicio())));
            miRegVM.put("estado", "");
            break;
          }
        }
      }
      VM.put("miRegistro", miRegVM);

      // ===== Patrocinios =====
      List<DTPatrocinio> pats = ed.getPatrocinios();
      req.setAttribute("patrocinios", pats);

      // ===== NUEVO: permisos por edición =====
      boolean esOrganizadorDeEstaEdicion = false;
      if (esOrganizador && ed.getOrganizadores() != null && nickSesion != null) {
        for (DTOrganizador o : ed.getOrganizadores()) {
          String orgId = null;
          try { orgId = String.valueOf(o.getClass().getMethod("getNickname").invoke(o)); }
          catch (Exception ignore) {}
          if (orgId == null || orgId.isBlank()) {
            try { orgId = String.valueOf(o.getClass().getMethod("getNombre").invoke(o)); }
            catch (Exception ignore2) {}
          }
          if (orgId != null && orgId.equalsIgnoreCase(nickSesion)) {
            esOrganizadorDeEstaEdicion = true;
            break;
          }
        }
      }

      boolean esAsistenteInscriptoEd = (miRegVM != null);

      // Flags + VM (seteamos ambos nombres para compatibilidad)
      req.setAttribute("ES_ORGANIZADOR", esOrganizador);
      req.setAttribute("ES_ASISTENTE",   esAsistente);
      req.setAttribute("ES_ORG",  esOrganizador);  // compat
      req.setAttribute("ES_ASIS", esAsistente);    // compat

      // NUEVOS flags por edición
      req.setAttribute("ES_ORGANIZADOR_ED",        esOrganizadorDeEstaEdicion);
      req.setAttribute("ES_ORG_ED",                esOrganizadorDeEstaEdicion);   // alias
      req.setAttribute("ES_ASISTENTE_INSCRIPTO_ED", esAsistenteInscriptoEd);
      req.setAttribute("ES_ASIS_ED",               esAsistenteInscriptoEd);       // alias

      req.setAttribute("VM", VM);

      forward(req, resp);

    } catch (Exception e) {
      req.setAttribute("msgError", "Error al consultar la edición: " + e.getMessage());
      forward(req, resp);
    }
  }

  // ===================== Helpers =====================

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

  private static String joinOrganizadores(List<DTOrganizador> orgs) {
    if (orgs == null || orgs.isEmpty()) return null;
    List<String> nombres = new ArrayList<>();
    for (DTOrganizador o : orgs) {
      if (o == null) continue;
      String nom = null;
      try { nom = (String) o.getClass().getMethod("getNombre").invoke(o); }
      catch (Exception e1) {
        try { nom = (String) o.getClass().getMethod("getNickname").invoke(o); }
        catch (Exception ignore) {}
      }
      if (nom != null && !nom.isBlank()) nombres.add(nom);
    }
    return nombres.isEmpty() ? null : String.join(", ", nombres);
  }

  // Extrae nickname de forma defensiva desde DTSesionUsuario
  private static String extraerNickSeguro(DTSesionUsuario u) {
    if (u == null) return null;
    // 1) Si DTSesionUsuario tiene getNickname():
    try {
      Object nick = u.getClass().getMethod("getNickname").invoke(u);
      if (nick != null && !String.valueOf(nick).isBlank()) return String.valueOf(nick);
    } catch (Exception ignore) {}

    // 2) Si expone getNombre():
    try {
      Object nom = u.getClass().getMethod("getNombre").invoke(u);
      if (nom != null && !String.valueOf(nom).isBlank()) return String.valueOf(nom);
    } catch (Exception ignore) {}

    // 3) Si tiene getUsuario() y ese tiene getNickname():
    try {
      Object usuario = u.getClass().getMethod("getUsuario").invoke(u);
      if (usuario != null) {
        Object nick = usuario.getClass().getMethod("getNickname").invoke(usuario);
        if (nick != null && !String.valueOf(nick).isBlank()) return String.valueOf(nick);
      }
    } catch (Exception ignore) {}

    return null;
  }
}

