package com.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.Evento;
import ServidorCentral.logica.DTEdicion;
import ServidorCentral.logica.DTTipoRegistro;

@WebServlet(name = "ConsultaTRegSvt", urlPatterns = {"/ConsultaTipoRegistro"})
public class ConsultaTRegSvt extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String nomEvento  = req.getParameter("evento");
    String nomEdicion = req.getParameter("edicion");
    String nomTipo    = req.getParameter("tipo");

    if (isBlank(nomEvento) || isBlank(nomEdicion) || isBlank(nomTipo)) {
      req.setAttribute("msgError",
          "Faltan parámetros: evento, edicion, y/o tipo de registro.");
      req.getRequestDispatcher("/WEB-INF/views/ConsultaTipoDeRegistro.jsp").forward(req, resp);
      return;
    }

    try {
      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();

      // Evento (para título / breadcrumbs)
      Evento evento = ctrl.getEvento(nomEvento);
      if (evento == null) {
        throw new IllegalArgumentException("El evento '" + nomEvento + "' no existe.");
      }

      // Edición (DTO)
      DTEdicion ed = ctrl.consultaEdicionDeEvento(nomEvento, nomEdicion);
      if (ed == null) {
        throw new IllegalArgumentException("La edición '" + nomEdicion + "' no existe para el evento '" + nomEvento + "'.");
      }

      // Tipo de registro (DTO)
      DTTipoRegistro tr = ctrl.consultaTipoRegistro(nomEdicion, nomTipo);
      if (tr == null) {
        throw new IllegalArgumentException("El tipo de registro '" + nomTipo + "' no existe en la edición '" + nomEdicion + "'.");
      }

      // Atributos para el JSP
      req.setAttribute("EVENTO", evento);
      req.setAttribute("EDICION", ed);
      req.setAttribute("TIPO_REGISTRO", tr);

    } catch (Exception e) {
      req.setAttribute("msgError", e.getMessage());
    }

    req.getRequestDispatcher("/WEB-INF/views/ConsultaTipoDeRegistro.jsp").forward(req, resp);
  }

  private static boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }
}
