package com.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.RolUsuario;
import cliente.ws.sc.DtInstitucion;
import cliente.ws.sc.DtInstitucionArray;

@WebServlet("/Instituciones")
public class ListaInstitucionesSvt extends HttpServlet {

	private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Flag de rol para el JSP 
    boolean esOrg = false;
    HttpSession session = req.getSession(false);
    if (session != null) {
      Object o = session.getAttribute("usuario_logueado");
      if (o instanceof DtSesionUsuario u && u.getRol() == RolUsuario.ORGANIZADOR) {
        esOrg = true;
      }
    }
    req.setAttribute("ES_ORG", esOrg);

    cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
    cliente.ws.sc.WebServices port = service.getWebServicesPort();
    
    // Carga de instituciones 
    try {
      DtInstitucionArray institucionesArray = port.listarDTInstituciones();
      List<DtInstitucion> instituciones = institucionesArray.getItem();
      req.setAttribute("INSTITUCIONES", instituciones);
    } catch (Exception e) {
      req.setAttribute("INSTITUCIONES", java.util.Collections.emptyList());
      req.setAttribute("msgError", "No se pudo cargar la lista de instituciones: " + e.getMessage());
    }

    // Forward a la vista 
    req.getRequestDispatcher("/WEB-INF/views/ListaInstituciones.jsp").forward(req, resp);
  }
}
