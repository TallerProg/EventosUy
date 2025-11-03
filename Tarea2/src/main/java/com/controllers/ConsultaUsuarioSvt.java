package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import servidorcentral.logica.DTSesionUsuario;

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

    String nick = req.getParameter("nick");
    if (nick == null || nick.isBlank()) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parametro 'nick'.");
      return;
    }
    nick = nick.trim();

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
      DTSesionUsuario sesUser = (session != null) ? (DTSesionUsuario) session.getAttribute("usuario_logueado") : null;
      boolean esSuPerfil = (sesUser != null) && nick.equalsIgnoreCase(sesUser.getNickname());

      try {
        String img = usuario.getImg();
        if (img != null && !img.isBlank()) req.setAttribute("IMAGEN", img);
      } catch (Exception ignore) {}

      if ("A".equals(rol) && esSuPerfil) {
        try {
          DtRegistroArray ra = port.listarRegistrosDeAsistente(nick);
          var lista = (ra != null && ra.getItem() != null) ? ra.getItem() : java.util.List.of();
          req.setAttribute("Registros", lista);
        } catch (Exception ignore) {}
      }

      if ("O".equals(rol)) {
        try {
          DtEdicionArray ea = port.listarEdicionesDeOrganizador(nick);
          var lista = (ea != null && ea.getItem() != null) ? ea.getItem() : java.util.List.of();
          req.setAttribute("Ediciones", lista);
        } catch (Exception ignore) {}
      }

      req.setAttribute("esSuPerfil", esSuPerfil);
      req.setAttribute("rol",   rol);
      req.setAttribute("usuario", usuario);

      req.getRequestDispatcher("/WEB-INF/views/ConsultaUsuario.jsp").forward(req, resp);

    } catch (Exception e) {
      resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error consultando usuario: " + safe(e.getMessage()));
    }
  }

  private static String safe(String s) { return (s == null) ? "" : s; }
}
