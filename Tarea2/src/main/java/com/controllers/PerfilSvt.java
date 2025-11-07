package com.controllers;
import cliente.ws.sc.DtSesionUsuario;

import cliente.ws.sc.DtUsuarioListaConsulta;
import cliente.ws.sc.RolUsuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/perfil")
public class PerfilSvt extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private String buildVersionedUrl(HttpServletRequest req, String fileOrPath) {
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

        DtSesionUsuario usuario = (DtSesionUsuario) session.getAttribute("usuario_logueado");
        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
        cliente.ws.sc.WebServices port = service.getWebServicesPort();
        
        DtUsuarioListaConsulta dtusuario = port.consultarUsuarioPorNickname(usuario.getNickname());
        req.setAttribute("USUARIO", dtusuario);
        req.setAttribute("USUARIOROL", usuario);

        RolUsuario rol = usuario.getRol(); // enum
        boolean esOrg  = rol==RolUsuario.ORGANIZADOR;
        boolean esAsis = rol==RolUsuario.ASISTENTE;
        req.setAttribute("ES_ORG", esOrg);
        req.setAttribute("ES_ASIS", esAsis);

        String imgDominio = null;
        try {
            if (esAsis) {
            	
                req.setAttribute("INSTITUCION", dtusuario.getIns().getNombre());
                try { imgDominio = dtusuario.getImg(); } catch (Exception ignore) {
                    try { imgDominio = dtusuario.getImg(); } catch (Exception ignore2) {}
                }
            } else {
                try { imgDominio = dtusuario.getImg(); } catch (Exception ignore) {
                    try { imgDominio = dtusuario.getImg(); } catch (Exception ignore2) {}
                }
            }
        } catch (Exception e) {
            req.setAttribute("INSTITUCION", null);
        }

        String imgSesion = (String) session.getAttribute("IMAGEN_LOGUEADO");
        String elegido = (imgDominio != null && !imgDominio.isBlank()) ? imgDominio : imgSesion;

        String urlVersionada = buildVersionedUrl(req, elegido);
        req.setAttribute("IMAGEN", urlVersionada);

        req.getRequestDispatcher("/WEB-INF/views/Perfil.jsp").forward(req, resp);
    }
}
