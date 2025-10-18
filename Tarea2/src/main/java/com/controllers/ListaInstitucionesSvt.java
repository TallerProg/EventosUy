package com.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerInstitucion;
import servidorcentral.logica.DTInstitucion;
import servidorcentral.logica.DTSesionUsuario;
import servidorcentral.logica.RolUsuario;

@WebServlet(name = "ListaInstitucionesSvt", urlPatterns = {"/Instituciones"})
public class ListaInstitucionesSvt extends HttpServlet {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Flag de rol para el JSP 
    boolean esOrg = false;
    HttpSession session = req.getSession(false);
    if (session != null) {
      Object o = session.getAttribute("usuario_logueado");
      if (o instanceof DTSesionUsuario u && u.getRol() == RolUsuario.ORGANIZADOR) {
        esOrg = true;
      }
    }
    req.setAttribute("ES_ORG", esOrg);

    // Carga de instituciones 
    try {
      IControllerInstitucion ctrl = Factory.getInstance().getIControllerInstitucion();
      List<DTInstitucion> instituciones = ctrl.getDTInstituciones();
      req.setAttribute("INSTITUCIONES", instituciones);
    } catch (Exception e) {
      req.setAttribute("INSTITUCIONES", java.util.Collections.emptyList());
      req.setAttribute("msgError", "No se pudo cargar la lista de instituciones: " + e.getMessage());
    }

    // Forward a la vista 
    req.getRequestDispatcher("/WEB-INF/views/ListaInstituciones.jsp").forward(req, resp);
  }
}
