package com.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerInstitucion;
import ServidorCentral.logica.Institucion;

@WebServlet(name = "ListaInstitucionesSvt", urlPatterns = {"/instituciones"})
public class ListaInstitucionesSvt extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      IControllerInstitucion ctrl = Factory.getInstance().getIControllerInstitucion();

      List<Institucion> instituciones = ctrl.getInstituciones();

      req.setAttribute("INSTITUCIONES", instituciones);
    } catch (Exception e) {
      req.setAttribute("INSTITUCIONES", java.util.Collections.emptyList());
      req.setAttribute("msgError", "No se pudo cargar la lista de instituciones: " + e.getMessage());
    }

    req.getRequestDispatcher("/WEB-INF/views/ListaInstituciones.jsp").forward(req, resp);
  }
}
