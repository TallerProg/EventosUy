package com.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerEvento;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.Organizador;
import ServidorCentral.logica.Edicion;
import ServidorCentral.logica.ControllerUsuario.DTSesionUsuario;
import ServidorCentral.logica.ControllerUsuario.RolUsuario;

@WebServlet(name = "AltaTipoRegistroSvt", urlPatterns = { "/organizador-tipos-registro-alta" })
public class AltaTRegSvt extends HttpServlet {

  private IControllerUsuario cu() { return Factory.getInstance().getIControllerUsuario(); }
  private IControllerEvento  ce() { return Factory.getInstance().getIControllerEvento(); }

  private Organizador getOrganizadorEnSesion(HttpServletRequest req) {
    HttpSession ses = req.getSession(false);
    if (ses == null) return null;
    Object o = ses.getAttribute("usuario_logueado");
    if (!(o instanceof DTSesionUsuario du)) return null;
    if (du.getRol() != RolUsuario.ORGANIZADOR) return null;
    try { return cu().getOrganizador(du.getNickname()); }
    catch (Exception ignore) { return null; }
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    Organizador org = getOrganizadorEnSesion(req);
    if (org == null) { // Por si de alguna forma no llega organizador
      resp.sendRedirect(req.getContextPath() + "/login");
      return;
    }

    try {
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

    // Datos del form
    String edSel    = req.getParameter("edicion");
    String nombreTR = req.getParameter("nombre");
    String descr    = req.getParameter("descripcion");
    String sCosto   = req.getParameter("costo");
    String sCupo    = req.getParameter("cupo");

    // Para que se vuelva a ver ante error
    req.setAttribute("form_edicion", edSel);
    req.setAttribute("form_nombre", nombreTR);
    req.setAttribute("form_descripcion", descr);
    req.setAttribute("form_costo", sCosto);
    req.setAttribute("form_cupo", sCupo);

    // Organizador en sesión 
    Organizador org = getOrganizadorEnSesion(req);
    if (org == null) {
      resp.sendRedirect(req.getContextPath() + "/login");
      return;
    }

    // Combo por si hay que re-renderizar
    req.setAttribute("LISTA_EDICIONES", org.getEdiciones());

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
      // Buscar edición elegida entre las del organizador
      Edicion edicion = null;
      for (Edicion e : org.getEdiciones()) {
        if (e.getNombre() != null && e.getNombre().equals(edSel)) {
          edicion = e; break;
        }
      }
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
      resp.sendRedirect(req.getContextPath() + "/organizador-tipos-registro-alta");

    } catch (Exception e) {
      req.setAttribute("msgError", e.getMessage());
      req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
    }
  }

  private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}

