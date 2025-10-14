package com.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.Edicion;

@WebServlet(name = "AltaTipoRegistroSvt", urlPatterns = { "/organizador-tipos-registro-alta" })
public class AltaTRegSvt extends HttpServlet {

  private IControllerEvento  ce() { return Factory.getInstance().getIControllerEvento(); }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

	// Mostramos el nombre de edición que viene por parámetro
	    String edSel = req.getParameter("edicion");

	// Para el JSP: edición fija (mismo valor para técnico y visual)
	    req.setAttribute("form_edicion", edSel != null ? edSel : "");
	    req.setAttribute("form_edicion_nombre", edSel != null ? edSel : "—");

	    req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");

    // Datos del form
    String edSel    = req.getParameter("edicion");
    String nombreTR = req.getParameter("nombre");
    String descr    = req.getParameter("descripcion");
    String sCosto   = req.getParameter("costo");
    String sCupo    = req.getParameter("cupo");

    // Para que se vuelva a ver ante error
    req.setAttribute("form_edicion", edSel != null ? edSel : "");
    req.setAttribute("form_edicion_nombre", edSel != null ? edSel : "—");
    req.setAttribute("form_nombre", nombreTR);
    req.setAttribute("form_descripcion", descr);
    req.setAttribute("form_costo", sCosto);
    req.setAttribute("form_cupo", sCupo);

    // Chequeos
    if (isBlank(edSel) || isBlank(nombreTR) || isBlank(descr) || isBlank(sCosto) || isBlank(sCupo)) {
      req.setAttribute("msgError", "Todos los campos son obligatorios.");
      req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
      return;
    }

    Float costo; Integer cupo;
    try {
      costo = Float.valueOf(sCosto);
      if (costo < 0) throw new NumberFormatException("costo negativo");
      cupo  = Integer.valueOf(sCupo);
      if (cupo < 0) throw new NumberFormatException("cupo negativo");
    } catch (NumberFormatException nfe) {
      req.setAttribute("msgError", "Costo o cupo inválidos.");
      req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
      return;
    }

    try {
    	// Buscar la edición por nombre
        Edicion edicion = ce().findEdicion(edSel);
        if (edicion == null) {
          req.setAttribute("msgError", "No se encontró la edición seleccionada.");
          req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
          return;
        }

      // Si ya existe un tr con mismo nombre
      if (edicion.existeTR(nombreTR)) {
        req.setAttribute("msgError", "El nombre de tipo de registro \"" + nombreTR + "\" ya fue utilizado en esa edición.");
        req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
        return;
      }

      // Alta
      ce().altaTipoRegistro(nombreTR, descr, costo, cupo, edicion);

      // Si hay exito
      req.getSession().setAttribute("flashOk",
              "Tipo de registro \"" + nombreTR + "\" creado en la edición \"" + edSel + "\".");
          resp.sendRedirect(req.getContextPath() + "/organizador-tipos-registro-alta?edicion=" + java.net.URLEncoder.encode(edSel, java.nio.charset.StandardCharsets.UTF_8));

    } catch (Exception e) {
      req.setAttribute("msgError", e.getMessage());
      req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
    }
  }

  private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}

