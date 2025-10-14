package com.controllers;

import servidorcentral.logica.Factory;
import servidorcentral.logica.IControllerUsuario;
import servidorcentral.logica.Institucion;
import servidorcentral.logica.Organizador;
import servidorcentral.logica.Asistente;
import servidorcentral.logica.ControllerUsuario;
import servidorcentral.logica.ControllerUsuario.DTSesionUsuario;
import servidorcentral.logica.ControllerUsuario.RolUsuario;
import servidorcentral.logica.DTUsuarioListaConsulta;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/perfil")
public class PerfilSvt extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private String buildVersionedUrl(HttpServletRequest req, String fileOrPath) {
        // fileOrPath puede ser nombre ("nick.jpg") o ruta relativa ("/media/img/usuarios/nick.jpg")
        String rel = (fileOrPath == null || fileOrPath.isBlank())
            ? "/media/img/default.png"
            : (fileOrPath.startsWith("/") ? fileOrPath : "/media/img/usuarios/" + fileOrPath);

        String abs = getServletContext().getRealPath(rel);
        long ver = 0L;
        if (abs != null) {
            File f = new File(abs);
            if (f.exists()) ver = f.lastModified();
        }
        return req.getContextPath() + rel + (ver > 0 ? "?v=" + ver : "");
    }

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

        DTUsuarioListaConsulta dtusuario = ctrl.consultaDeUsuario(usuario.getNickname());
        req.setAttribute("USUARIO", dtusuario);
        req.setAttribute("USUARIOROL", usuario);

        RolUsuario rol = usuario.getRol();
        boolean esOrg  = (rol == ControllerUsuario.RolUsuario.ORGANIZADOR);
        boolean esAsis = (rol == ControllerUsuario.RolUsuario.ASISTENTE);
        req.setAttribute("ES_ORG", esOrg);
        req.setAttribute("ES_ASIS", esAsis);

        String imgDominio = null;
        try {
            if (esAsis) {
                Asistente asist = ctrl.getAsistente(dtusuario.getNickname());
                req.setAttribute("INSTITUCION", asist.getInstitucion());
                try { imgDominio = asist.getImg(); } catch (Exception ignore) {
                    try { imgDominio = asist.getImg(); } catch (Exception ignore2) {}
                }
            } else {
                Organizador org = ctrl.getOrganizador(dtusuario.getNickname());
                try { imgDominio = org.getImg(); } catch (Exception ignore) {
                    try { imgDominio = org.getImg(); } catch (Exception ignore2) {}
                }
            }
        } catch (Exception e) {
            req.setAttribute("INSTITUCION", null);
        }

        // Preferir dominio; si no hay, usar lo recién subido en sesión
        String imgSesion = (String) session.getAttribute("IMAGEN_LOGUEADO");
        String elegido = (imgDominio != null && !imgDominio.isBlank()) ? imgDominio : imgSesion;

        String urlVersionada = buildVersionedUrl(req, elegido);
        req.setAttribute("IMAGEN", urlVersionada);

        req.getRequestDispatcher("/WEB-INF/views/Perfil.jsp").forward(req, resp);
    }
}
