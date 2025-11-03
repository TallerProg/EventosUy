package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cliente.ws.sc.DTevento;
import cliente.ws.sc.DtEdicionArray;
import cliente.ws.sc.DtEdicion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servidorcentral.logica.DTSesionUsuario;
import servidorcentral.logica.RolUsuario;

@WebServlet(name = "ConsultaEveSvt", urlPatterns = { "/ConsultaEvento" })
@MultipartConfig(fileSizeThreshold = 1 * 1024 * 1024, maxFileSize = 10 * 1024 * 1024, maxRequestSize = 15 * 1024 * 1024)
public class ConsultaEveSvt extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nombreEvento = req.getParameter("evento");
        if (isBlank(nombreEvento)) {
            req.setAttribute("msgError", "Falta el parámetro 'evento'.");
            req.setAttribute("LISTA_EDICIONES", java.util.Collections.emptyList());
            req.getRequestDispatcher("/WEB-INF/views/ConsultaEvento.jsp").forward(req, resp);
            return;
        }

        try {   
            cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
            cliente.ws.sc.WebServices port = service.getWebServicesPort();
            
            DTevento evento = port.consultaEventoPorNombre(nombreEvento);
            if (evento == null) {
                req.setAttribute("msgError", "No se encontró el evento '" + nombreEvento + "'.");
                req.setAttribute("LISTA_EDICIONES", java.util.Collections.emptyList());
                req.getRequestDispatcher("/WEB-INF/views/ConsultaEvento.jsp").forward(req, resp);
                return;
            }
            req.setAttribute("EVENTO", evento);

            HttpSession session = req.getSession(false);
            DTSesionUsuario sesUser = (session != null)
                    ? (DTSesionUsuario) session.getAttribute("usuario_logueado")
                    : null;

            boolean esOrgGlobal = (sesUser != null && sesUser.getRol() == RolUsuario.ORGANIZADOR);
            req.setAttribute("ES_ORG", esOrgGlobal);

            // lista de ediciones del evento (puede ser null)
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
                    if (dte != null&& !"NO_ENCONTRADA".equals(dte.getEstado())) {
                    	edicionesCompletas.add(dte);

                    }
                }
            }


            req.setAttribute("LISTA_EDICIONES", edicionesCompletas);

        } catch (Exception e) {
            req.setAttribute("msgError", "No se pudo cargar la lista de ediciones: " + e.getMessage());
            req.setAttribute("LISTA_EDICIONES", java.util.Collections.emptyList()); // <— nunca null
        }

        req.getRequestDispatcher("/WEB-INF/views/ConsultaEvento.jsp").forward(req, resp);
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
