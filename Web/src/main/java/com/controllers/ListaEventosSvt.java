package com.controllers;

import com.config.WSClientProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;
import cliente.ws.sc.DTevento;
import cliente.ws.sc.DTeventoArray;
import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.RolUsuario;

@WebServlet("/Eventos")
public class ListaEventosSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final String JSP_LISTA_EVENTOS = "/WEB-INF/views/ListarEventos.jsp";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Siempre seteamos un valor por defecto
    boolean esOrg = false;

    // Sesión opcional
    HttpSession session = req.getSession(false);
    if (session != null) {
      Object o = session.getAttribute("usuario_logueado");
      if (o instanceof DtSesionUsuario u) {
        esOrg = (u.getRol() == RolUsuario.ORGANIZADOR);
      }
    }
    req.setAttribute("ES_ORG", esOrg);

    try {
        WebServicesService service = WSClientProvider.newService();
      WebServices port = service.getWebServicesPort();

      DTeventoArray arr = port.listarDTEventos();
      List<DTevento> eventos = (arr != null && arr.getItem() != null)
          ? arr.getItem()
          : java.util.List.of();

      req.setAttribute("LISTA_EVENTOS", eventos);

      // Evitar cache en navegador
      resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
      resp.setHeader("Pragma", "no-cache");

      req.getRequestDispatcher(JSP_LISTA_EVENTOS).forward(req, resp);

    } catch (Exception e) {
      // En caso de error, mandamos lista vacía para que el JSP no truene
      req.setAttribute("error", (e.getMessage() != null) ? e.getMessage() : "Error al listar eventos.");
      req.setAttribute("LISTA_EVENTOS", java.util.List.of());
      req.getRequestDispatcher(JSP_LISTA_EVENTOS).forward(req, resp);
    }
  }
}

