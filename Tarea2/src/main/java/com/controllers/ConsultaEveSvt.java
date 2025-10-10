package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.DTEdicion;
import ServidorCentral.logica.Evento;

@WebServlet(name = "ConsultaEveSvt", urlPatterns = {"/ConsultaEvento"})
@MultipartConfig(
    fileSizeThreshold = 1 * 1024 * 1024,
    maxFileSize = 10 * 1024 * 1024,
    maxRequestSize = 15 * 1024 * 1024
)
public class ConsultaEveSvt extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  String nombreEvento = req.getParameter("evento"); 

	    if (isBlank(nombreEvento)) {
	      req.setAttribute("msgError", "Falta el parámetro 'evento'.");
	      req.getRequestDispatcher("/WEB-INF/views/ConsultaEvento.jsp").forward(req, resp);
	      return;
	    }

    try {
      Factory fabrica = Factory.getInstance();
      IControllerEvento ctrl = fabrica.getIControllerEvento();
      Evento evento = ctrl.getEvento(nombreEvento); 
      req.setAttribute("EVENTO", evento);
      
      List<String> ediciones = ctrl.listarEdicionesDeEvento(nombreEvento);
      List<DTEdicion> edicionesCompletas = new ArrayList<>();
      for (String nombreEdicion : ediciones) {
          DTEdicion edicion = ctrl.consultaEdicionDeEvento(nombreEvento, nombreEdicion); 
          edicionesCompletas.add(edicion); 
        }
      req.setAttribute("LISTA_EDICIONES", edicionesCompletas);
    } catch (Exception e) {
      req.setAttribute("msgError", "No se pudo cargar la lista de ediciones: " + e.getMessage());
      req.setAttribute("LISTA_EDICIONES", null);
    }

    req.getRequestDispatcher("/WEB-INF/views/ConsultaEvento.jsp").forward(req, resp);
  }
  private static boolean isBlank(String s) {
	    return s == null || s.trim().isEmpty();
	  }

}
