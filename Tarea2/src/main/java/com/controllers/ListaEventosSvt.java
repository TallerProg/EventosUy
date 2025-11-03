package com.controllers;

import java.io.IOException;
import java.net.URL;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;



import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;
import cliente.ws.sc.DTeventoArray;
import cliente.ws.sc.DtSesionUsuario; 
import cliente.ws.sc.RolUsuario;      

@WebServlet("/Eventos")
public class ListaEventosSvt extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private static final String JSP_LISTA_EVENTOS = "/WEB-INF/views/ListarEventos.jsp";
  private static final String WSDL_URL = "http://127.0.0.1:9128/webservices?wsdl";

  private WebServices getPort() throws IOException {
      try {
          URL wsdl = new URL(WSDL_URL);
          WebServicesService svc = new WebServicesService(wsdl);
          return svc.getWebServicesPort();
      } catch (Exception e) {
          throw new IOException("No se pudo crear el cliente del WebService: " + e.getMessage(), e);
      }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    boolean esOrg = false;
    HttpSession session = req.getSession(false);

    if (session != null) {
      Object o = session.getAttribute("usuario_logueado");

      if (o instanceof DtSesionUsuario u) {
        esOrg = (u.getRol() == RolUsuario.ORGANIZADOR);
    }
    req.setAttribute("ES_ORG", esOrg);

    try {
    	
    	WebServices port = getPort();
    	DTeventoArray eventos = port.listarDTEventos(); 


      req.setAttribute("LISTA_EVENTOS", eventos);

      resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
      resp.setHeader("Pragma", "no-cache");

      req.getRequestDispatcher(JSP_LISTA_EVENTOS).forward(req, resp);

    } catch (Exception e) {
      req.setAttribute("error", (e.getMessage() != null) ? e.getMessage() : "Error al listar eventos.");
      req.getRequestDispatcher(JSP_LISTA_EVENTOS).forward(req, resp);
    }
    }
  }
}

  


