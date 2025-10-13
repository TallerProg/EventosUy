package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;

@WebServlet(name = "EdicionEstadoSvt", urlPatterns = {"/ediciones-estado"})
public class EdicionEstadoSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Permitir solo desde localhost (IPv4/IPv6)
    String ip = req.getRemoteAddr();
    boolean isLocal = "127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip);

    if (!isLocal) {
      resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Solo disponible desde localhost.");
      return;
    }

    String evento  = trim(req.getParameter("evento"));
    String edicion = trim(req.getParameter("edicion"));
    String accion  = trim(req.getParameter("accion")); // "aceptar" o "rechazar"

    if (isBlank(evento) || isBlank(edicion) || isBlank(accion)) {
      resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos.");
      return;
    }

    boolean aceptar = "aceptar".equalsIgnoreCase(accion);

    try {
      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();
      ctrl.aceptarRechazarEdicion(edicion, aceptar);

      String msg = aceptar ? "Edición aceptada correctamente." : "Edición rechazada correctamente.";
      String url = req.getContextPath() + "/ediciones-consulta"
          + "?evento="  + URLEncoder.encode(evento,  StandardCharsets.UTF_8.name())
          + "&edicion=" + URLEncoder.encode(edicion, StandardCharsets.UTF_8.name())
          + "&msgOk="   + URLEncoder.encode(msg,     StandardCharsets.UTF_8.name());
      resp.sendRedirect(url);

    } catch (Exception e) {
      String url = req.getContextPath() + "/ediciones-consulta"
          + "?evento="  + URLEncoder.encode(evento,  StandardCharsets.UTF_8.name())
          + "&edicion=" + URLEncoder.encode(edicion, StandardCharsets.UTF_8.name())
          + "&msgError="+ URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8.name());
      resp.sendRedirect(url);
    }
  }

  private static boolean isBlank(String s){ return s == null || s.trim().isEmpty(); }
  private static String trim(String s){ return s == null ? null : s.trim(); }
}
