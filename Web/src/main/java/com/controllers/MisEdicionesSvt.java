package com.controllers;

import com.config.WSClientProvider;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import cliente.ws.sc.DtOrganizadorDetallado;
import cliente.ws.sc.DtEdicion;
import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.RolUsuario;

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
            if (o instanceof DtSesionUsuario u && u.getRol() == RolUsuario.ORGANIZADOR) {
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
        	cliente.ws.sc.WebServicesService service = WSClientProvider.newService();
            cliente.ws.sc.WebServices port = service.getWebServicesPort();
            DtOrganizadorDetallado org = port.getDTOrganizadorDetallado(nickname);

            // Convertir las ediciones del organizador a DTEdiciones 
            List<DtEdicion> ediciones = org.getEdiciones();
            if (ediciones == null) {
                ediciones = new ArrayList<>();
            }
            List<DtEdicion> dtEdiciones = new ArrayList<>();
            
            Map<String,String> eventoNombreMap = new HashMap<>();
            for (DtEdicion e : ediciones) {
                if (e != null) {
                    DtEdicion dto = e;
                    if (dto != null) {
                        dtEdiciones.add(dto);

                        eventoNombreMap.put(dto.getNombre(), port.nombreEventoDeEdicion(dto.getNombre()));
                    }
                }
            }

            // Pasar la lista al JSP 
            req.setAttribute("EDICION_EVENTO", eventoNombreMap);
            req.setAttribute("LISTA_EDICIONES", dtEdiciones);

           
            // Enviar al JSP
            req.getRequestDispatcher(JSP_LISTA_EDICIONES).forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", (e.getMessage() != null) ? e.getMessage() : "Error al listar ediciones.");
            req.getRequestDispatcher(JSP_LISTA_EDICIONES).forward(req, resp);
        }
    }


}
