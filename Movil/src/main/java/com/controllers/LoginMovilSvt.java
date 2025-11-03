package com.movil.controllers;

import com.movil.ws.WsClient;
import cliente.ws.sc.WebServices;
import cliente.ws.sc.CredencialesInvalidasException_Exception;
import cliente.ws.sc.UsuarioNoExisteException_Exception;
import cliente.ws.sc.DtSesionUsuario; 

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "LoginMovilSvt", urlPatterns = {"/login"})
public class LoginMovilSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final String FORM_JSP = "/WEB-INF/login.jsp";

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
      WebServices port = WsClient.getPort(getServletContext());
      DtSesionUsuario sesion = port.iniciarSesion(identifier, password);

      HttpSession httpSes = req.getSession(true);
      httpSes.setAttribute("MOVIL_SESION", sesion);

      try {
        String nick = null;
        try { nick = (String)DtReflection.safeCall(sesion, "getNickname"); } catch (Throwable ignore) {}
        if (nick == null || nick.isBlank()) nick = identifier;
        httpSes.setAttribute("MOVIL_NICK", nick);
      } catch (Throwable ignore) {}

      String next = (String) httpSes.getAttribute("MOVIL_NEXT");
      if (next != null) {
        httpSes.removeAttribute("MOVIL_NEXT");
        resp.sendRedirect(next);
      } else {
        resp.sendRedirect(req.getContextPath() + "/m/home");
      }
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

  static class DtReflection {
    static Object safeCall(Object obj, String getter) throws Exception {
      if (obj == null) return null;
      return obj.getClass().getMethod(getter).invoke(obj);
    }
  }
}
