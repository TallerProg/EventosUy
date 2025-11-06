package com.controllers;

import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;
import cliente.ws.sc.CredencialesInvalidasException_Exception;
import cliente.ws.sc.UsuarioNoExisteException_Exception;
import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.RolUsuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "LoginMovilSvt", urlPatterns = { "/login" })
public class LoginMovilSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final String FORM_JSP = "/WEB-INF/views/login.jsp";
  private static final String MOBILE_HOME = "/home"; 

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.getRequestDispatcher(FORM_JSP).forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    req.setCharacterEncoding(StandardCharsets.UTF_8.name());

    String identifier = trim(req.getParameter("identifier"));
    String password   = trim(req.getParameter("password"));

    if (identifier.isEmpty() || password.isEmpty()) {
      req.setAttribute("msgError", "Usuario y contraseña son obligatorios.");
      req.getRequestDispatcher(FORM_JSP).forward(req, resp);
      return;
    }

    try {
      WebServicesService svc = new WebServicesService();
      WebServices port = svc.getWebServicesPort();
      DtSesionUsuario sesion = port.iniciarSesion(identifier, password);

      HttpSession httpSes = req.getSession(true);

      httpSes.setAttribute("MOVIL_SESION", sesion);
      httpSes.setAttribute("usuario_logueado", sesion);

      RolUsuario rol = null;
      try { rol = sesion.getRol(); } catch (Throwable ignore) {}
      boolean esAsistente = (rol == RolUsuario.ASISTENTE);

      httpSes.setAttribute("usuarioAsistente", esAsistente ? Boolean.TRUE : Boolean.FALSE);


      String nick = null;
      try { nick = sesion.getNickname(); } catch (Throwable ignore) {}
      if (nick == null || nick.isBlank()) {
        try { nick = sesion.getCorreo(); } catch (Throwable ignore) {}
      }
      if (nick == null || nick.isBlank()) nick = identifier;
      httpSes.setAttribute("MOVIL_NICK", nick);

      String next = (String) httpSes.getAttribute("MOVIL_NEXT");
      if (next != null) {
        httpSes.removeAttribute("MOVIL_NEXT");
        if (!next.startsWith(req.getContextPath())) {
          next = req.getContextPath() + MOBILE_HOME;
        }
        resp.sendRedirect(resp.encodeRedirectURL(next));
        return;
      }
     resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + MOBILE_HOME));

    } catch (UsuarioNoExisteException_Exception e) {
      req.setAttribute("msgError", "Usuario no existe.");
      req.getRequestDispatcher(FORM_JSP).forward(req, resp);
    } catch (CredencialesInvalidasException_Exception e) {
      req.setAttribute("msgError", "Credenciales inválidas.");
      req.getRequestDispatcher(FORM_JSP).forward(req, resp);
    } catch (Exception e) {
      req.setAttribute("msgError", "Error al iniciar sesión: " + e.getMessage());
      req.getRequestDispatcher(FORM_JSP).forward(req, resp);
    }
  }

  private static String trim(String s) { return s == null ? "" : s.trim(); }
}

