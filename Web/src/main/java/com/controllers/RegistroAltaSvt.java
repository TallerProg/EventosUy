package com.controllers;

import com.config.WSClientProvider;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
// import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import cliente.ws.sc.DtEdicion;
import cliente.ws.sc.DtTipoRegistro;
import cliente.ws.sc.DTevento;
import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.RolUsuario;

@WebServlet(name = "RegistroAltaSvt", urlPatterns = {"/RegistroAlta"})
public class RegistroAltaSvt extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    HttpSession session = req.getSession(false);
    DtSesionUsuario usuario = (session != null) ? (DtSesionUsuario) session.getAttribute("usuario_logueado") : null;

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
    cliente.ws.sc.WebServicesService service = WSClientProvider.newService();
    cliente.ws.sc.WebServices port = service.getWebServicesPort();
    try {

      DTevento evento     = port.getDTEvento(nomEvento);
      DtEdicion ed      = port.consultaEdicionDeEvento(nomEvento, nomEdicion);
      DtTipoRegistro tr = port.consultaTipoRegistro(nomEdicion, nomTipo);

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


    HttpSession session = req.getSession(false);
    DtSesionUsuario usuario = (session != null) ? (DtSesionUsuario) session.getAttribute("usuario_logueado") : null;

    if (usuario == null || usuario.getRol() != RolUsuario.ASISTENTE) {
      req.setAttribute("msgError", "Debés iniciar sesión como Asistente para registrarte.");
      req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
      return;
    }

    String nomEvento  = req.getParameter("evento");
    String nomEdicion = req.getParameter("edicion");
    String nomTipo    = req.getParameter("tipo");

    String modalidad  = req.getParameter("modalidad"); 
    String codigoPat  = req.getParameter("codigo");    

    String nickAsistente = extraerNickSeguro(usuario);

    if (isBlank(nomEvento) || isBlank(nomEdicion) || isBlank(nomTipo) || isBlank(nickAsistente)) {
      req.setAttribute("msgError", "Parámetros inválidos para registrar (nick de asistente no disponible).");
      req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
      return;
    }
    
    cliente.ws.sc.WebServicesService service = WSClientProvider.newService();
    cliente.ws.sc.WebServices port = service.getWebServicesPort();
    try {

      DTevento evento     = port.getDTEvento(nomEvento);
      DtEdicion ed      = port.consultaEdicionDeEvento(nomEvento, nomEdicion);
      DtTipoRegistro tr = port.consultaTipoRegistro(nomEdicion, nomTipo);

      if (evento == null || ed == null || tr == null) {
        req.setAttribute("msgError", "No se pudo cargar la información solicitada.");
        req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
        return;
      }

      boolean yaRegistrado = ed.getRegistros() != null && ed.getRegistros().stream()
          .anyMatch(r -> nickAsistente.equalsIgnoreCase(String.valueOf(r.getAsistenteNickname())));
      if (yaRegistrado) {
        req.setAttribute("msgError", "Ya estás registrado en esta edición.");
        rellenar(req, evento, ed, tr);
        req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
        return;
      }

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

      boolean conPatro = "patrocinio".equalsIgnoreCase(modalidad);

      if (!conPatro) {
        port.altaRegistro(nomEdicion, nickAsistente, nomTipo);
        
      } else {
        if (isBlank(codigoPat)) {
          req.setAttribute("msgError", "Debes ingresar un código de patrocinio.");
          rellenar(req, evento, ed, tr);
          req.getRequestDispatcher("/WEB-INF/views/RegistroAlta.jsp").forward(req, resp);
          return;
        }
        port.altaRegistroP(nomEdicion, nickAsistente, nomTipo, codigoPat);
      }

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

  private static void rellenar(HttpServletRequest req, DTevento evento, DtEdicion ed, DtTipoRegistro tr) {
    req.setAttribute("EVENTO", evento);
    req.setAttribute("EDICION", ed);
    req.setAttribute("TIPO_REGISTRO", tr);
    req.setAttribute("COSTO_TIPO", tr.getCosto());
    req.setAttribute("CUPO_TIPO",  tr.getCupo());
  }

  private static String extraerNickSeguro(DtSesionUsuario u) {
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


