package com.controllers;

import ServidorCentral.logica.Factory;
import ServidorCentral.logica.IControllerUsuario;
import ServidorCentral.logica.Institucion;
import ServidorCentral.logica.Organizador;
import ServidorCentral.logica.Asistente;
import ServidorCentral.logica.ControllerUsuario;
import ServidorCentral.logica.ControllerUsuario.DTSesionUsuario;
import ServidorCentral.logica.ControllerUsuario.RolUsuario;
import ServidorCentral.logica.DTUsuarioListaConsulta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/perfil")
public class PerfilSvt extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false); 
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/login"); 
            return;
        }

        DTSesionUsuario usuario = (DTSesionUsuario) session.getAttribute("usuario_logueado");
        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login"); 
            return;
        }

        Factory fabrica = Factory.getInstance();
        IControllerUsuario ctrl = fabrica.getIControllerUsuario();
        
        DTUsuarioListaConsulta dtusuario =ctrl.consultaDeUsuario(usuario.getNickname());
        req.setAttribute("USUARIO", dtusuario);
        req.setAttribute("USUARIOROL", usuario);

        RolUsuario rol = usuario.getRol(); // enum
        boolean esOrg  = rol == ControllerUsuario.RolUsuario.ORGANIZADOR;
        boolean esAsis = rol == ControllerUsuario.RolUsuario.ASISTENTE;
        req.setAttribute("ES_ORG", esOrg);
        req.setAttribute("ES_ASIS", esAsis);

        try {
            if (esAsis) {
                Asistente asist =ctrl.getAsistente(dtusuario.getNickname()); // <-- AJUSTAR NOMBRE
                Institucion inst = asist.getInstitucion();
            	String img = asist.getImg();
                req.setAttribute("INSTITUCION", inst);
                req.setAttribute("IMAGEN", img);

            }else {
                Organizador organ =ctrl.getOrganizador(dtusuario.getNickname()); // <-- AJUSTAR NOMBRE
            	String img = organ.getImg();
                req.setAttribute("IMAGEN", img);

            }
        } catch (Exception e) {
            req.setAttribute("INSTITUCION", null);
        }

        req.getRequestDispatcher("/WEB-INF/views/Perfil.jsp").forward(req, resp);
    }
}
