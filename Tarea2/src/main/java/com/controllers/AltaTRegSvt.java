package com.controllers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.jws.WebMethod;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import servidorcentral.excepciones.NombreTRUsadoException;
import servidorcentral.logica.Edicion;
import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;

@WebServlet("/organizador-tipos-registro-alta")
public class AltaTRegSvt extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String edSel = req.getParameter("edicion");

        req.setAttribute("form_edicion", edSel != null ? edSel : "");
        req.setAttribute("form_edicion_nombre", edSel != null ? edSel : "—");

        req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // Datos del form
        String edSel    = trim(req.getParameter("edicion"));
        String nombreTR = trim(req.getParameter("nombre"));
        String descr    = trim(req.getParameter("descripcion"));
        String sCosto   = trim(req.getParameter("costo"));
        String sCupo    = trim(req.getParameter("cupo"));

        // Re-muestro en JSP ante error
        req.setAttribute("form_edicion", edSel != null ? edSel : "");
        req.setAttribute("form_edicion_nombre", edSel != null ? edSel : "—");
        req.setAttribute("form_nombre", nombreTR);
        req.setAttribute("form_descripcion", descr);
        req.setAttribute("form_costo", sCosto);
        req.setAttribute("form_cupo", sCupo);

        // Validaciones de requeridos
        if (isBlank(edSel) || isBlank(nombreTR) || isBlank(descr) || isBlank(sCosto) || isBlank(sCupo)) {
            req.setAttribute("msgError", "Todos los campos son obligatorios.");
            req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
            return;
        }

        // Parseo numérico con mensajes consistentes
        try {
            Integer.parseInt(sCupo);
        } catch (Exception e) {
            req.setAttribute("msgError", "El cupo debe ser un número sin coma.");
            req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
            return;
        }
        Float costo;
        costo = Float.valueOf(sCosto);
        if (costo < 0) {
            req.setAttribute("msgError", "El costo no puede ser negativo.");
            req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
            return;
        }
        Integer cupo;
        cupo  = Integer.valueOf(sCupo);
        if (cupo < 0) {
            req.setAttribute("msgError", "El cupo no puede ser negativo.");
            req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
            return;
        }

        cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
        cliente.ws.sc.WebServices port = service.getWebServicesPort();
        
        try {         
            boolean yaExiste = port.existeTRNombre(edSel, nombreTR);
            if (yaExiste) {
                req.setAttribute("msgError", "El nombre de tipo de registro \"" + nombreTR + "\" ya fue utilizado en esa edición.");
                req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);
                return;
            }

            // 2) Alta remota
            port.altaTipoRegistroDT(nombreTR, descr, costo, cupo, edSel);

            // PRG + flash
            req.getSession().setAttribute("flashOk",
                "Tipo de registro \"" + nombreTR + "\" creado en la edición \"" + edSel + "\".");
            resp.sendRedirect(req.getContextPath() + "/organizador-tipos-registro-alta?edicion=" + java.net.URLEncoder.encode(edSel, java.nio.charset.StandardCharsets.UTF_8));


        } catch (Exception e) {
            req.setAttribute("msgError", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/AltaTipoRegistro.jsp").forward(req, resp);

        }
    }
    
    // Helpers 
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
    private static String trim(String s) { return s == null ? "" : s.trim(); }
}
