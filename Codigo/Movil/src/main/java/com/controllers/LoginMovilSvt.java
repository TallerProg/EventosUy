package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URL;

import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;
import cliente.ws.sc.CredencialesInvalidasException_Exception;
import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.DtUsuarioListaConsulta;
import cliente.ws.sc.UsuarioNoExisteException_Exception;
import com.config.WSClientProvider;

@WebServlet("/login")
public class LoginMovilSvt extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String VIEW = "/WEB-INF/views/login.jsp";


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher(VIEW).forward(req, resp);
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
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }
        WebServicesService service = WSClientProvider.newService();
        WebServices port = service.getWebServicesPort();
        if(port.esOrganizador(identifier)) {
    		req.setAttribute("error", "Los organizadorees no pueden iniciar sesión desde la aplicación móvil.");
    		req.getRequestDispatcher(VIEW).forward(req, resp);
    		return;
    	}
        try {

            DtSesionUsuario sesion = port.iniciarSesion(identifier, password);
            
            DtUsuarioListaConsulta usr = port.consultaDeUsuario(sesion.getNickname());

            HttpSession httpSes = req.getSession(true);
            httpSes.setAttribute("usuario_logueado", sesion);
            httpSes.setAttribute("IMAGEN_LOGUEADO", usr.getImg());

            resp.sendRedirect(req.getContextPath() + "/home");

        } catch (UsuarioNoExisteException_Exception e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("identifier_prev", identifier);
            req.getRequestDispatcher(VIEW).forward(req, resp);

        } catch (CredencialesInvalidasException_Exception e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("identifier_prev", identifier);
            req.getRequestDispatcher(VIEW).forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", "Ocurrió un error al iniciar sesión.");
            req.setAttribute("identifier_prev", identifier);
            req.getRequestDispatcher(VIEW).forward(req, resp);
        }
    }

    private String trim(String s) { return s == null ? null : s.trim(); }
    private boolean isEmpty(String s) { return s == null || s.isEmpty(); }
}

