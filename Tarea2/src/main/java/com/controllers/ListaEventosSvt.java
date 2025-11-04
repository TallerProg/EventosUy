package com.controllers;

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
  private static final String DEFAULT_WSDL_URL  = "http://127.0.0.1:9128/webservices?wsdl";

  private WebServices getPort(HttpServletRequest req) throws IOException {
    try {
      // Permití override por web.xml (context-param <param-name>WS_URL</param-name>)
      String wsUrl = req.getServletContext().getInitParameter("WS_URL");
      URL wsdl = new URL((wsUrl != null && !wsUrl.isBlank()) ? (wsUrl + "?wsdl") : DEFAULT_WSDL_URL);
      WebServicesService svc = new WebServicesService(wsdl);
      WebServices port = svc.getWebServicesPort();
      // Si definiste WS_URL, también seteamos el endpoint (por si el WSDL apunta a otra dirección)
      if (wsUrl != null && !wsUrl.isBlank()) {
        ((jakarta.xml.ws.BindingProvider) port)
            .getRequestContext()
            .put(jakarta.xml.ws.BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsUrl);
      }
      return port;
    } catch (Exception e) {
      throw new IOException("No se pudo crear el cliente del WebService: " + e.getMessage(), e);
    }
  }

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
      WebServices port = getPort(req);

      DTeventoArray arr = port.listarDTEventos();
      List<DTevento> eventos = (arr != null && arr.getItem() != null)
          ? arr.getItem()
          : java.util.List.of();

      // NORMALIZAMOS: el JSP recibe SIEMPRE una List<DTevento>
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

