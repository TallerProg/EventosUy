package com.controllers;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
// import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerEvento;
import servidorcentral.logica.DTEdicion;
import servidorcentral.logica.DTTipoRegistro;
import servidorcentral.logica.DTevento;

import servidorcentral.logica.ControllerUsuario.DTSesionUsuario;
import servidorcentral.logica.ControllerUsuario.RolUsuario;

@WebServlet(name = "RegistroAltaSvt", urlPatterns = {"/RegistroAlta"})
public class RegistroAltaSvt extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Autenticacion rol: debe ser asistente 
    HttpSession session = req.getSession(false);
    DTSesionUsuario usuario = (session != null) ? (DTSesionUsuario) session.getAttribute("usuario_logueado") : null;

    if (usuario == null || usuario.getRol() != RolUsuario.ASISTENTE) {
      req.setAttribute("msgError", "Debés iniciar sesión como Asistente para registrarte.");
      req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
      return;
    }

    String nomEvento  = req.getParameter("evento");
    String nomEdicion = req.getParameter("edicion");
    String nomTipo    = req.getParameter("tipo");

    if (isBlank(nomEvento) || isBlank(nomEdicion) || isBlank(nomTipo)) {
      req.setAttribute("msgError", "Faltan parámetros: evento, edición y tipo.");
      req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
      return;
    }

    try {
      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();

      DTevento evento     = ctrl.getEvento(nomEvento).getDTevento();
      DTEdicion ed      = ctrl.consultaEdicionDeEvento(nomEvento, nomEdicion);
      DTTipoRegistro tr = ctrl.consultaTipoRegistro(nomEdicion, nomTipo);

      if (evento == null || ed == null || tr == null) {
        req.setAttribute("msgError", "No se pudo cargar la información solicitada.");
      } else {
        req.setAttribute("EVENTO", evento);
        req.setAttribute("EDICION", ed);
        req.setAttribute("TIPO_REGISTRO", tr);
        req.setAttribute("COSTO_TIPO", tr.getCosto());
        req.setAttribute("CUPO_TIPO",  tr.getCupo());

        req.setAttribute("ENC_EVENTO",  URLEncoder.encode(nomEvento,  StandardCharsets.UTF_8.name()));
        req.setAttribute("ENC_EDICION", URLEncoder.encode(nomEdicion, StandardCharsets.UTF_8.name()));
      }
    } catch (Exception e) {
      req.setAttribute("msgError", "Error al cargar datos: " + e.getMessage());
    }

    req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    // Autenticacion rol: debe ser asistente
    HttpSession session = req.getSession(false);
    DTSesionUsuario usuario = (session != null) ? (DTSesionUsuario) session.getAttribute("usuario_logueado") : null;

    if (usuario == null || usuario.getRol() != RolUsuario.ASISTENTE) {
      req.setAttribute("msgError", "Debés iniciar sesión como Asistente para registrarte.");
      req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
      return;
    }

    String nomEvento  = req.getParameter("evento");
    String nomEdicion = req.getParameter("edicion");
    String nomTipo    = req.getParameter("tipo");

    String modalidad  = req.getParameter("modalidad"); // "general" | "patrocinio"
    String codigoPat  = req.getParameter("codigo");    // puede ser null

    // Nick del asistente de sesión 
    String nickAsistente = extraerNickSeguro(usuario);

    if (isBlank(nomEvento) || isBlank(nomEdicion) || isBlank(nomTipo) || isBlank(nickAsistente)) {
      req.setAttribute("msgError", "Parámetros inválidos para registrar (nick de asistente no disponible).");
      req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
      return;
    }

    try {
      IControllerEvento ctrl = Factory.getInstance().getIControllerEvento();

      DTevento evento     = ctrl.getEvento(nomEvento).getDTevento();
      DTEdicion ed      = ctrl.consultaEdicionDeEvento(nomEvento, nomEdicion);
      DTTipoRegistro tr = ctrl.consultaTipoRegistro(nomEdicion, nomTipo);

      if (evento == null || ed == null || tr == null) {
        req.setAttribute("msgError", "No se pudo cargar la información solicitada.");
        req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
        return;
      }

      // Ya registrado
      boolean yaRegistrado = ed.getRegistros() != null && ed.getRegistros().stream()
          .anyMatch(r -> nickAsistente.equalsIgnoreCase(String.valueOf(r.getAsistenteNickname())));
      if (yaRegistrado) {
        req.setAttribute("msgError", "Ya estás registrado en esta edición.");
        rellenar(req, evento, ed, tr);
        req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
        return;
      }

      // Cupo
      Integer cupoTipo = tr.getCupo(); 
      if (cupoTipo != null) {
        long usados = (ed.getRegistros() == null) ? 0 :
            ed.getRegistros().stream()
              .filter(r -> nomTipo.equalsIgnoreCase(String.valueOf(r.getTipoRegistroNombre())))
              .count();
        if (usados >= cupoTipo) {
          req.setAttribute("msgError", "Se alcanzó el cupo para este tipo de registro.");
          rellenar(req, evento, ed, tr);
          req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
          return;
        }
      }

      // Alta usando firmas existentes
      boolean conPatro = "patrocinio".equalsIgnoreCase(modalidad);

      if (!conPatro) {
        ctrl.altaRegistro(nomEdicion, nickAsistente, nomTipo);
        
      } else {
        if (isBlank(codigoPat)) {
          req.setAttribute("msgError", "Debes ingresar un código de patrocinio.");
          rellenar(req, evento, ed, tr);
          req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
          return;
        }
        ctrl.altaRegistro(nomEdicion, nickAsistente, nomTipo, codigoPat);
      }

      // Éxito
      req.setAttribute("success", true);
      req.setAttribute("EVENTO", evento);
      req.setAttribute("EDICION", ed);
      req.setAttribute("TIPO_REGISTRO", tr);
      req.setAttribute("COSTO_TIPO", tr.getCosto());
      req.setAttribute("CUPO_TIPO",  tr.getCupo());
      req.setAttribute("MODALIDAD", conPatro ? "Con patrocinio (costo $0)" : "General");
      req.setAttribute("ENC_EVENTO",  URLEncoder.encode(nomEvento,  StandardCharsets.UTF_8.name()));
      req.setAttribute("ENC_EDICION", URLEncoder.encode(nomEdicion, StandardCharsets.UTF_8.name()));

      req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);

    } catch (Exception e) {
      req.setAttribute("msgError", e.getMessage());
      req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
    }
  }

  private static boolean isBlank(String s) {
    return s == null || s.trim().isEmpty();
  }

  private static void rellenar(HttpServletRequest req, DTevento evento, DTEdicion ed, DTTipoRegistro tr) {
    req.setAttribute("EVENTO", evento);
    req.setAttribute("EDICION", ed);
    req.setAttribute("TIPO_REGISTRO", tr);
    req.setAttribute("COSTO_TIPO", tr.getCosto());
    req.setAttribute("CUPO_TIPO",  tr.getCupo());
  }

  // Igual que en otros servlets: saca el nick de DTSesionUsuario
  private static String extraerNickSeguro(DTSesionUsuario u) {
    if (u == null) return null;
    try {
      Object nick = u.getClass().getMethod("getNickname").invoke(u);
      if (nick != null && !String.valueOf(nick).isBlank()) return String.valueOf(nick);
    } catch (Exception ignore) {}

    try {
      Object nom = u.getClass().getMethod("getNombre").invoke(u);
      if (nom != null && !String.valueOf(nom).isBlank()) return String.valueOf(nom);
    } catch (Exception ignore) {}

    try {
      Object usuario = u.getClass().getMethod("getUsuario").invoke(u);
      if (usuario != null) {
        Object nick = usuario.getClass().getMethod("getNickname").invoke(usuario);
        if (nick != null && !String.valueOf(nick).isBlank()) return String.valueOf(nick);
      }
    } catch (Exception ignore) {}

    return null;
  }
}


