package com.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidorcentral.excepciones.CredencialesInvalidasException;
import servidorcentral.excepciones.UsuarioNoExisteException;
import servidorcentral.logica.ControllerUsuario.DTSesionUsuario;
import servidorcentral.logica.ControllerUsuario.RolUsuario;
import servidorcentral.logica.DTUsuarioListaConsulta;
import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerUsuario;

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
            req.getRequestDispatcher("/WEB-INF/views/InicioSesion.jsp").forward(req, resp);
            return;
        }

        try {
            DTSesionUsuario sesion = ICU.iniciarSesion(identifier, password);

            DTUsuarioListaConsulta dtUser = ICU.consultaDeUsuario(sesion.getNickname());

            HttpSession httpSes = req.getSession(true);
            httpSes.setAttribute("usuario_logueado", sesion);

            if (dtUser != null) {
                httpSes.setAttribute("IMAGEN_LOGUEADO", dtUser.getImg());
            } else {
                httpSes.setAttribute("IMAGEN_LOGUEADO", null);
            }

            if (sesion.getRol() == RolUsuario.ORGANIZADOR) {
                httpSes.setAttribute("usuarioOrganizador", sesion.getNickname());
            } else if (sesion.getRol() == RolUsuario.ASISTENTE) {
                httpSes.setAttribute("usuarioAsistente", sesion.getNickname());
            }

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

