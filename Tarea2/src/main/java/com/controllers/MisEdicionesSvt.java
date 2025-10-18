package com.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import servidorcentral.logica.DTOrganizadorDetallado;
import servidorcentral.logica.IControllerUsuario;
import servidorcentral.logica.Factory;
import servidorcentral.logica.DTEdicion;
import servidorcentral.logica.DTSesionUsuario;

@WebServlet("/MisEdiciones")
public class MisEdicionesSvt extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
    private static final String JSP_LISTA_EDICIONES = "/WEB-INF/views/MisEdiciones.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String nickname = null;
        boolean esOrg = false;

        // Obtener usuario logueado 
        HttpSession session = req.getSession(false);
        if (session != null) {
            Object o = session.getAttribute("usuario_logueado");
            if (o instanceof DTSesionUsuario u && u.getRolString().equals("ORGANIZADOR")) {
                esOrg = true;
                nickname = u.getNickname();
            }else {
            	resp.sendRedirect(req.getContextPath() + "/home");
            }
        }else {
        	resp.sendRedirect(req.getContextPath() + "/home");
        }
        req.setAttribute("ES_ORG", esOrg);

        try {
            // Obtener controlador y organizador 
            IControllerUsuario icu = Factory.getInstance().getIControllerUsuario();
            DTOrganizadorDetallado org = icu.getDTOrganizadorDetallado(nickname);

            // Convertir las ediciones del organizador a DTEdiciones 
            List<DTEdicion> ediciones = org.getEdiciones();
            if (ediciones == null) {
                ediciones = new ArrayList<>();
            }
            List<DTEdicion> dtEdiciones = new ArrayList<>();
            

            for (DTEdicion e : ediciones) {
                if (e != null) {
                    DTEdicion dto = e;
                    if (dto != null) {
                        dtEdiciones.add(dto);
                    }
                }
            }

            // Pasar la lista al JSP 
            req.setAttribute("LISTA_EDICIONES", dtEdiciones);

           
            // Enviar al JSP
            req.getRequestDispatcher(JSP_LISTA_EDICIONES).forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", (e.getMessage() != null) ? e.getMessage() : "Error al listar ediciones.");
            req.getRequestDispatcher(JSP_LISTA_EDICIONES).forward(req, resp);
        }
    }


}
