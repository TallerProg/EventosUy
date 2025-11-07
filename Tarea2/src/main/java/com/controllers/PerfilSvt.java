package com.controllers;

import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.DtUsuarioListaConsulta;
import cliente.ws.sc.RolUsuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        // Datos principales del usuario logueado
        DtUsuarioListaConsulta dtusuario =
            port.consultarUsuarioPorNickname(usuario.getNickname());
        req.setAttribute("USUARIO", dtusuario);
        req.setAttribute("USUARIOROL", usuario);

        RolUsuario rol = usuario.getRol();
        boolean esOrg  = rol == RolUsuario.ORGANIZADOR;
        boolean esAsis = rol == RolUsuario.ASISTENTE;
        req.setAttribute("ES_ORG", esOrg);
        req.setAttribute("ES_ASIS", esAsis);

        // Institución e imagen
        String imgDominio = null;
        try {
            imgDominio = dtusuario.getImg();
            if (esAsis && dtusuario.getIns() != null) {
                req.setAttribute("INSTITUCION", dtusuario.getIns().getNombre());
            }
        } catch (Exception e) {
            req.setAttribute("INSTITUCION", null);
        }

        String imgSesion = (String) session.getAttribute("IMAGEN_LOGUEADO");
        String elegido = (imgDominio != null && !imgDominio.isBlank()) ? imgDominio : imgSesion;
        String urlVersionada = buildVersionedUrl(req, elegido);
        req.setAttribute("IMAGEN", urlVersionada);

        // -------- Seguidores y Seguidos (usando WS que devuelven String[]) --------
        String nick = usuario.getNickname();

        // Seguidores
        List<DtUsuarioListaConsulta> seguidoresDto = new ArrayList<>();
        try {
            List<String> seguidoresNicks = port.listarSeguidores(nick).getItem();
            if (seguidoresNicks != null) {
                for (String segNick : seguidoresNicks) {
                    if (segNick == null || segNick.isBlank()) continue;
                    try {
                        DtUsuarioListaConsulta seg =
                            port.consultarUsuarioPorNickname(segNick);
                        if (seg != null) seguidoresDto.add(seg);
                    } catch (Exception ignore) {}
                }
            }
        } catch (Exception ignore) {}
        req.setAttribute("SEGUIDORES", seguidoresDto);

        // Seguidos
        List<DtUsuarioListaConsulta> seguidosDto = new ArrayList<>();
        try {
            List<String> seguidosNicks = port.listarSeguidos(nick).getItem();
            if (seguidosNicks != null) {
                for (String sNick : seguidosNicks) {
                    if (sNick == null || sNick.isBlank()) continue;
                    try {
                        DtUsuarioListaConsulta s =
                            port.consultarUsuarioPorNickname(sNick);
                        if (s != null) seguidosDto.add(s);
                    } catch (Exception ignore) {}
                }
            }
        } catch (Exception ignore) {}
        req.setAttribute("SEGUIDOS", seguidosDto);

        // -----------------------------------------------------------------------

        req.getRequestDispatcher("/WEB-INF/views/Perfil.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        Object o = session.getAttribute("usuario_logueado");
        if (!(o instanceof DtSesionUsuario u)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String principal = u.getNickname();
        String objetivo  = req.getParameter("nick");
        String accion    = req.getParameter("accion");

        if (principal == null || principal.isBlank()
            || objetivo == null || objetivo.isBlank()
            || principal.equals(objetivo)) {
            resp.sendRedirect(req.getContextPath() + "/perfil");
            return;
        }

        try {
            cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
            cliente.ws.sc.WebServices port = service.getWebServicesPort();

            // Solo necesitamos dejar de seguir desde el perfil
            if ("dejar".equalsIgnoreCase(accion)) {
                port.sacarSeguirPersona(principal, objetivo);
            }
        } catch (Exception ignore) {
        }

        resp.sendRedirect(req.getContextPath() + "/perfil");
    }
}