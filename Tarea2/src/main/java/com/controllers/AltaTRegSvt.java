package com.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.Organizador;
import ServidorCentral.logica.Edicion;

@WebServlet(name = "AltaTipoRegistroSvt", urlPatterns = { "/organizador-tipos-registro-alta" })
public class AltaTRegSvt extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      Organizador org = (Organizador) req.getSession().getAttribute("usuarioOrganizador");
      if (org == null) throw new IllegalStateException("No hay organizador en sesión.");

      List<Edicion> ediciones = org.getEdiciones();
      req.setAttribute("LISTA_EDICIONES", ediciones);

    } catch (Exception e) {
      req.setAttribute("msgError", "No se pudieron cargar tus ediciones: " + e.getMessage());
      req.setAttribute("LISTA_EDICIONES", java.util.Collections.emptyList());
    }

    req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    req.setCharacterEncoding("UTF-8");

    String edSel    = req.getParameter("edicion");     // nombre de la edición
    String nombreTR = req.getParameter("nombre");
    String descr    = req.getParameter("descripcion");
    String sCosto   = req.getParameter("costo");
    String sCupo    = req.getParameter("cupo");

    // Echo para re-render si hay error
    req.setAttribute("form_edicion", edSel);
    req.setAttribute("form_nombre", nombreTR);
    req.setAttribute("form_descripcion", descr);
    req.setAttribute("form_costo", sCosto);
    req.setAttribute("form_cupo", sCupo);

    // Combo siempre cargado para el re-render
    try {
      Organizador org = (Organizador) req.getSession().getAttribute("usuarioOrganizador");
      req.setAttribute("LISTA_EDICIONES", org != null ? org.getEdiciones() : java.util.Collections.emptyList());
    } catch (Exception ignore) { }

    // Validación de requeridos (servidor)
    if (isBlank(edSel) || isBlank(nombreTR) || isBlank(descr) || isBlank(sCosto) || isBlank(sCupo)) {
      req.setAttribute("msgError", "Todos los campos son obligatorios.");
      req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
      return;
    }

    // Tipos numéricos
    Float costo;
    Integer cupo;
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
      Organizador org = (Organizador) req.getSession().getAttribute("usuarioOrganizador");
      if (org == null) throw new IllegalStateException("No hay organizador en sesión.");

      // Buscar edición seleccionada por nombre
      Edicion edicion = null;
      for (Edicion e : org.getEdiciones()) {
        if (e.getNombre() != null && e.getNombre().equals(edSel)) {
          edicion = e; break;
        }
      }
      if (edicion == null) throw new IllegalArgumentException("No se encontró la edición seleccionada.");

      // Chequeo de duplicado en la propia edición (defensa temprana)
      if (edicion.existeTR(nombreTR)) {
        req.setAttribute("msgError", "El nombre de tipo de registro \"" + nombreTR + "\" ya fue utilizado en esa edición.");
        req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
        return;
      }

      // Lógica de negocio
      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();
      ctrl.altaTipoRegistro(nombreTR, descr, costo, cupo, edicion);

      // PRG + flash
      req.getSession().setAttribute("flashOk",
          "Tipo de registro \"" + nombreTR + "\" creado en la edición \"" + edSel + "\".");
      resp.sendRedirect(req.getContextPath() + "/organizador-tipos-registro-alta");
      return;

    } catch (Exception e) {
      req.setAttribute("msgError", e.getMessage());
      req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
    }
  }

  private static boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }
}
