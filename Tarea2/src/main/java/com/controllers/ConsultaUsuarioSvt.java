package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;
import cliente.ws.sc.DtUsuarioListaConsulta;
import cliente.ws.sc.DtUsuarioListaConsultaArray;
import cliente.ws.sc.DtRegistroArray;
import cliente.ws.sc.DtEdicionArray;

import jakarta.xml.ws.Binding;
import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.soap.SOAPBinding;

@WebServlet("/ConsultaUsuario")
public class ConsultaUsuarioSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;

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

    String nickParam = req.getParameter("nick");
    if (nickParam == null || nickParam.isBlank()) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parametro 'nick'.");
      return;
    }
    final String nick = nickParam.trim();

    try {
      WebServices port = getPort(req);

      String rol = "v"; 
      boolean esAsistente = false;
      boolean esOrganizador = false;

      try {
        DtUsuarioListaConsultaArray asisArr = port.listarDTAsistentes();
        if (asisArr != null && asisArr.getItem() != null) {
          esAsistente = asisArr.getItem().stream()
              .anyMatch(u -> u != null && nick.equalsIgnoreCase(safe(u.getNickname())));
        }
      } catch (Exception ignore) {}

      if (!esAsistente) {
        try {
          DtUsuarioListaConsultaArray orgArr = port.listarDTOrganizadores();
          if (orgArr != null && orgArr.getItem() != null) {
            esOrganizador = orgArr.getItem().stream()
                .anyMatch(u -> u != null && nick.equalsIgnoreCase(safe(u.getNickname())));
          }
        } catch (Exception ignore) {}
      }

      if (esAsistente) rol = "A";
      else if (esOrganizador) rol = "O";

      if ("v".equals(rol)) {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado.");
        return;
      }

      DtUsuarioListaConsulta usuario = port.consultaDeUsuario(nick);
      if (usuario == null) {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado.");
        return;
      }

      HttpSession session = req.getSession(false);
      DtSesionUsuario sesUser = (session != null) ? (DtSesionUsuario) session.getAttribute("usuario_logueado") : null;
      boolean esSuPerfil = (sesUser != null) && nick.equalsIgnoreCase(sesUser.getNickname());

      try {
        String img = usuario.getImg();
        if (img != null && !img.isBlank()) req.setAttribute("IMAGEN", img);
      } catch (Exception ignore) {}

      Map<String,String> eventoPorEdicion = new HashMap<>();

      if ("A".equals(rol) && esSuPerfil) {
        try {
          DtRegistroArray ra = port.listarRegistrosDeAsistente(nick);
          List<cliente.ws.sc.DtRegistro> lista =
              (ra != null && ra.getItem() != null) ? ra.getItem() : java.util.List.of();
          req.setAttribute("Registros", lista);

          for (cliente.ws.sc.DtRegistro r : lista) {
            if (r == null || r.getEdicion() == null) continue;

            Object e = r.getEdicion();
            String ed = safe(getNombreEdicion(e));
            if (!ed.isBlank() && !eventoPorEdicion.containsKey(ed)) {
              String ev = obtenerNombreEventoDesdeEdicion(e);
              eventoPorEdicion.put(ed, ev);
            }
          }
        } catch (Exception ignore) {}
      }

      if ("O".equals(rol)) {
        try {
          DtEdicionArray ea = port.listarEdicionesDeOrganizador(nick);
          List<cliente.ws.sc.DtEdicion> lista =
              (ea != null && ea.getItem() != null) ? ea.getItem() : java.util.List.of();
          req.setAttribute("Ediciones", lista);

          for (cliente.ws.sc.DtEdicion e : lista) {
            if (e == null) continue;
            String ed = safe(e.getNombre());
            if (!ed.isBlank() && !eventoPorEdicion.containsKey(ed)) {
              String ev = obtenerNombreEventoDesdeEdicion(e);
              eventoPorEdicion.put(ed, ev);
            }
          }
        } catch (Exception ignore) {}
      }

      req.setAttribute("EDICION_EVENTO", eventoPorEdicion);
      req.setAttribute("esSuPerfil", esSuPerfil);
      req.setAttribute("rol",   rol);
      req.setAttribute("usuario", usuario);

      req.getRequestDispatcher("/WEB-INF/views/ConsultaUsuario.jsp").forward(req, resp);

    } catch (Exception e) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          "Error consultando usuario: " + safe(e.getMessage()));
    }
  }

  private static String getNombreEdicion(Object e) {
    if (e == null) return "";
    try {
      if (e instanceof cliente.ws.sc.DtEdicion de) {
        String s = de.getNombre();
        return (s == null) ? "" : s;
      }
    } catch (Throwable ignore) {}

    try {
      Object v = e.getClass().getMethod("getNombre").invoke(e);
      if (v instanceof String s && !s.isBlank()) return s;
    } catch (Throwable ignore) {}

    try {
      Object v = e.getClass().getMethod("getName").invoke(e);
      if (v instanceof String s && !s.isBlank()) return s;
    } catch (Throwable ignore) {}

    try {
      Object v = e.getClass().getMethod("getTitulo").invoke(e);
      if (v instanceof String s && !s.isBlank()) return s;
    } catch (Throwable ignore) {}

    return "";
  }

  private static String obtenerNombreEventoDesdeEdicion(Object e) {
    if (e == null) return "Evento no disponible";
    try {
      var m = e.getClass().getMethod("getEventoNombre");
      Object v = m.invoke(e);
      if (v instanceof String s && !s.isBlank()) return s;
    } catch (Throwable ignore) {}

    try {
      var m = e.getClass().getMethod("getNombreEvento");
      Object v = m.invoke(e);
      if (v instanceof String s && !s.isBlank()) return s;
    } catch (Throwable ignore) {}

    try {
      var m = e.getClass().getMethod("getEventoSigla");
      Object v = m.invoke(e);
      if (v instanceof String s && !s.isBlank()) return s;
    } catch (Throwable ignore) {}

    return "Evento no disponible";
  }

  private static String safe(String s) { return (s == null) ? "" : s; }
}
