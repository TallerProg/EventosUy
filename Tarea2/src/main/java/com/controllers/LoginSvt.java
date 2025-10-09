package com.controllers;

import ServidorCentral.excepciones.CredencialesInvalidasException;
import ServidorCentral.excepciones.UsuarioNoExisteException;
import ServidorCentral.logica.*;
import ServidorCentral.logica.ControllerUsuario.DTSesionUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Locale;

@WebServlet("/login")
public class LoginSvt extends HttpServlet {

    private IControllerUsuario ICU ;

    @Override
    public void init() {
    	Factory fabrica = Factory.getInstance();
        this.ICU = fabrica.getIControllerUsuario();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String identifier = trim(req.getParameter("identifier"));
        String password   = trim(req.getParameter("password"));

        if (isEmpty(identifier) || isEmpty(password)) {
            req.setAttribute("error", "Completá usuario y contraseña.");
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }

        DTSesionUsuario usuario = null;
		try {
			usuario = ICU.iniciarSesion(identifier, password);
		} catch (UsuarioNoExisteException | CredencialesInvalidasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if (usuario == null) {
            req.setAttribute("error", "Credenciales inválidas.");
            // preservamos el identifier para no hacerlo reescribir
            req.setAttribute("identifier_prev", identifier);
            req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
            return;
        }

        HttpSession ses = req.getSession(true);
        ses.setAttribute("usuario_logueado", usuario);
        resp.sendRedirect(req.getContextPath() + "/home");
    }

    private String trim(String s) { return s == null ? null : s.trim(); }
    private boolean isEmpty(String s) { return s == null || s.isEmpty(); }
}
