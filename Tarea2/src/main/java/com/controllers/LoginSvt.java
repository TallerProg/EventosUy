package com.controllers;

import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerUsuario;
import servidorcentral.logica.Usuario;
import servidorcentral.logica.ControllerUsuario.DTSesionUsuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import servidorcentral.excepciones.CredencialesInvalidasException;
import servidorcentral.excepciones.UsuarioNoExisteException;

import java.io.IOException;

@WebServlet("/login")
public class LoginSvt extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IControllerUsuario ICU;

    @Override
    public void init() {
        Factory fabrica = Factory.getInstance();
        this.ICU = fabrica.getIControllerUsuario();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/InicioSesion.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String identifier = trim(req.getParameter("identifier"));
        String password   = trim(req.getParameter("password"));

        if (isEmpty(identifier) || isEmpty(password)) {
            req.setAttribute("error", "Completá usuario y contraseña.");
            req.setAttribute("identifier_prev", identifier);
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        try {
            DTSesionUsuario usuario = ICU.iniciarSesion(identifier, password);
            Factory fabrica = Factory.getInstance();
            IControllerUsuario ctrl = fabrica.getIControllerUsuario();
            Usuario usr=ctrl.getUsuario(usuario.getNickname());
            HttpSession ses = req.getSession(true);
            ses.setAttribute("usuario_logueado", usuario);
            ses.setAttribute("IMAGEN_LOGUEADO", usr.getImg());

            // Cambia "/home" si tu landing es otra (por ejemplo, "/IndexLoggeado.jsp")
            resp.sendRedirect(req.getContextPath() + "/home");
        } catch (UsuarioNoExisteException | CredencialesInvalidasException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("identifier_prev", identifier);
            req.getRequestDispatcher("/WEB-INF/views/InicioSesion.jsp").forward(req, resp);
        } catch (Exception e) {
            req.setAttribute("error", "Ocurrió un error al iniciar sesión.");
            req.setAttribute("identifier_prev", identifier);
            req.getRequestDispatcher("/WEB-INF/views/InicioSesion.jsp").forward(req, resp);
        }
    }

    private String trim(String s) { return s == null ? null : s.trim(); }
    private boolean isEmpty(String s) { return s == null || s.isEmpty(); }
}
