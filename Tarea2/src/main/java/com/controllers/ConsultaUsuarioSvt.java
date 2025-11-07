package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;
import cliente.ws.sc.DtUsuarioListaConsulta;
import cliente.ws.sc.DtUsuarioListaConsultaArray;
import cliente.ws.sc.RolUsuario;
import cliente.ws.sc.DtEdicion;
import cliente.ws.sc.DtEdicionArray;

@WebServlet("/ConsultaUsuario")
public class ConsultaUsuarioSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Flag de visitante
    boolean esVis = false;
    HttpSession sessionCampana = req.getSession(false);
    DtSesionUsuario sesUserCampana = null;
    if (sessionCampana != null) {
      Object o = sessionCampana.getAttribute("usuario_logueado");
      if (o instanceof DtSesionUsuario u) {
        sesUserCampana = u;
        if (u.getRol() == RolUsuario.VISITANTE) {
          esVis = true;
        }
      }
    }
    req.setAttribute("ES_VIS", esVis);

    // Nick a consultar
    String nickParam = req.getParameter("nick");
    if (nickParam == null || nickParam.isBlank()) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parametro 'nick'.");
      return;
    }
    final String nick = nickParam.trim();

    try {
      WebServicesService service = new WebServicesService();
      WebServices port = service.getWebServicesPort();

      // ---- Determinar rol del usuario consultado ----
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

      // ---- Datos del usuario ----
      DtUsuarioListaConsulta usuario = port.consultaDeUsuario(nick);
      if (usuario == null) {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado.");
        return;
      }

      HttpSession session = req.getSession(false);
      DtSesionUsuario sesUser = (session != null)
          ? (DtSesionUsuario) session.getAttribute("usuario_logueado")
          : null;
      boolean esSuPerfil = (sesUser != null) && nick.equalsIgnoreCase(sesUser.getNickname());

      // Imagen
      try {
        String img = usuario.getImg();
        if (img != null && !img.isBlank()) {
          req.setAttribute("IMAGEN", img);
        }
      } catch (Exception ignore) {}

      // ---- Ediciones según rol ----

      // Organizador: ediciones que organiza
      if ("O".equals(rol)) {
        try {
          DtEdicionArray ea = port.listarEdicionesDeOrganizador(nick);
          List<DtEdicion> lista = (ea != null && ea.getItem() != null)
              ? ea.getItem()
              : java.util.Collections.emptyList();
          req.setAttribute("Ediciones", lista);
        } catch (Exception ignore) {
          req.setAttribute("Ediciones", java.util.Collections.emptyList());
        }
      }

      // Asistente (solo su propio perfil): ediciones donde está registrado
      if ("A".equals(rol) && esSuPerfil) {
        try {
          DtEdicionArray arr = port.listarEdicionesConRegistroUsuario(nick);
          List<DtEdicion> lista = (arr != null && arr.getItem() != null)
              ? arr.getItem()
              : java.util.Collections.emptyList();
          req.setAttribute("listaEdicion", lista);
        } catch (Exception ignore) {
          req.setAttribute("listaEdicion", java.util.Collections.emptyList());
        }
      }

      // ---- SEGUIDOS_SET para el botón seguir/dejar ----
      Set<String> seguidosSet = null;
      if (!esVis
          && sesUserCampana != null
          && sesUserCampana.getNickname() != null
          && !sesUserCampana.getNickname().isBlank()) {
        try {
          DtUsuarioListaConsulta yo =
              port.consultaDeUsuario(sesUserCampana.getNickname());
          List<String> seguidos = (yo != null) ? yo.getSeguidos() : null;
          if (seguidos != null) seguidosSet = new HashSet<>(seguidos);
        } catch (Exception ignore) {}
      }
      req.setAttribute("SEGUIDOS_SET", seguidosSet);

      // ---- Atributos finales y forward ----
      req.setAttribute("esSuPerfil", esSuPerfil);
      req.setAttribute("rol", rol);
      req.setAttribute("usuario", usuario);

      req.getRequestDispatcher("/WEB-INF/views/ConsultaUsuario.jsp")
         .forward(req, resp);

    } catch (Exception e) {
      req.setAttribute("SEGUIDOS_SET", null);
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          "Error consultando usuario: " + safe(e.getMessage()));
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String accion = req.getParameter("accion");   // "seguir" | "dejar"
    String objetivoNick = req.getParameter("nick");

    String returnUrl = req.getContextPath() + "/ConsultaUsuario";
    if (objetivoNick != null && !objetivoNick.isBlank()) {
      returnUrl += "?nick=" + java.net.URLEncoder.encode(
          objetivoNick, java.nio.charset.StandardCharsets.UTF_8);
    }

    HttpSession sesCampana = req.getSession(false);
    if (sesCampana == null) {
      resp.sendRedirect(returnUrl);
      return;
    }

    Object o = sesCampana.getAttribute("usuario_logueado");
    if (!(o instanceof DtSesionUsuario u)) {
      resp.sendRedirect(returnUrl);
      return;
    }

    String principal = u.getNickname();
    if (principal == null || principal.isBlank()
        || objetivoNick == null || objetivoNick.isBlank()
        || principal.equals(objetivoNick)) {
      resp.sendRedirect(returnUrl);
      return;
    }

    try {
      WebServicesService service = new WebServicesService();
      WebServices port = service.getWebServicesPort();

      if ("seguir".equalsIgnoreCase(accion)) {
        port.seguirPersona(principal, objetivoNick);
      } else if ("dejar".equalsIgnoreCase(accion)) {
        port.sacarSeguirPersona(principal, objetivoNick);
      }
    } catch (Exception ignore) {}

    resp.sendRedirect(returnUrl);
  }

  private static String safe(String s) {
    return (s == null) ? "" : s.trim();
  }
}

