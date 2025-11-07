package com.controllers;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.RolUsuario;
import cliente.ws.sc.DTevento;
import cliente.ws.sc.DtEdicionArray;
import cliente.ws.sc.DtEdicion;
import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ConsultaEveSvt", urlPatterns = { "/ConsultaEvento" })
@MultipartConfig(
    fileSizeThreshold = 1 * 1024 * 1024,
    maxFileSize = 10 * 1024 * 1024,
    maxRequestSize = 15 * 1024 * 1024
)
public class ConsultaEveMovilSvt extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String WSDL_URL = "http://127.0.0.1:9128/webservices?wsdl";
    private static final String VIEW = "/WEB-INF/views/ConsultaEvento.jsp";

    // ---------- Helpers ----------
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

    private WebServices getPort() throws IOException {
        try {
            URL wsdl = new URL(WSDL_URL);
            WebServicesService svc = new WebServicesService(wsdl);
            return svc.getWebServicesPort();
        } catch (Exception e) {
            throw new IOException("No se pudo crear el cliente del WebService: " + e.getMessage(), e);
        }
    }

    private DtSesionUsuario sesion(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return (s != null) ? (DtSesionUsuario) s.getAttribute("usuario_logueado") : null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombreEvento = req.getParameter("evento");
        if (isBlank(nombreEvento)) {
            req.setAttribute("msgError", "Falta el parámetro 'evento'.");
            req.setAttribute("LISTA_EDICIONES", java.util.Collections.emptyList());
            req.getRequestDispatcher(VIEW).forward(req, resp);
            return;
        }

        try {
            WebServices port = getPort();

            DTevento evento = port.consultaEventoPorNombre(nombreEvento);
            if (evento == null) {
                req.setAttribute("msgError", "No se encontró el evento '" + nombreEvento + "'.");
                req.setAttribute("LISTA_EDICIONES", java.util.Collections.emptyList());
                req.getRequestDispatcher(VIEW).forward(req, resp);
                return;
            }
            req.setAttribute("EVENTO", evento);

            DtSesionUsuario sesUser = sesion(req);
            boolean esOrgGlobal = (sesUser != null && sesUser.getRol() == RolUsuario.ORGANIZADOR);
            req.setAttribute("ES_ORG", esOrgGlobal);

            DtEdicionArray edicionDTA = port.listarDTEdicion();
            List<DtEdicion> ediciones = (edicionDTA != null && edicionDTA.getItem() != null)
                    ? edicionDTA.getItem()
                    : java.util.Collections.emptyList();

            List<DtEdicion> edicionesCompletas = new ArrayList<>();
            for (DtEdicion dto : ediciones) {
                if (dto == null) continue;

                boolean esOrgDeEsta = false;
                if (sesUser != null && dto.getOrganizadores() != null) {
                    String nickSesion = sesUser.getNickname();
                    esOrgDeEsta = dto.getOrganizadores().stream()
                            .filter(o -> o != null && o.getNickname() != null)
                            .anyMatch(o -> o.getNickname().equals(nickSesion));
                }

                boolean aceptada = "Aceptada".equalsIgnoreCase(dto.getEstado());

                if (aceptada || esOrgDeEsta) {
                    DtEdicion dte = port.consultaEdicionDeEvento(nombreEvento, dto.getNombre());
                    if (dte != null && !"NO_ENCONTRADA".equals(dte.getEstado())) {
                        edicionesCompletas.add(dte);
                    }
                }
            }

            req.setAttribute("LISTA_EDICIONES", edicionesCompletas);

        } catch (Exception e) {
            req.setAttribute("msgError", "No se pudo cargar la lista de ediciones: " + e.getMessage());
            req.setAttribute("LISTA_EDICIONES", java.util.Collections.emptyList());
        }

        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");
        String nombreEvento = req.getParameter("evento");

        if (!"finalizar".equalsIgnoreCase(accion)) {
            doGet(req, resp);
            return;
        }

        if (isBlank(nombreEvento)) {
            String err = URLEncoder.encode("Evento inválido.", StandardCharsets.UTF_8);
            resp.sendRedirect(req.getContextPath() + "/Eventos?err=" + err);
            return;
        }

        DtSesionUsuario user = sesion(req);
        if (user == null || user.getRol() != RolUsuario.ORGANIZADOR) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "No tenés permisos para finalizar el evento.");
            return;
        }

        try {
            WebServices port = getPort();
            port.finalizarEvento(nombreEvento);

            String ok = URLEncoder.encode("Evento finalizado correctamente.", StandardCharsets.UTF_8);
            resp.sendRedirect(req.getContextPath() + "/Eventos?ok=" + ok);

        } catch (Exception ex) {
            ex.printStackTrace();
            String err = URLEncoder.encode("No se pudo finalizar el evento: " + ex.getMessage(), StandardCharsets.UTF_8);
            resp.sendRedirect(req.getContextPath() + "/Eventos?err=" + err);
        }
    }
}
