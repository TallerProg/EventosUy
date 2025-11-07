package com.controllers;

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
import cliente.ws.sc.DtEdicionArray;
import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.RolUsuario;

@WebServlet("/misRegistros")
public class ConsultaRegistroMovilSvt extends HttpServlet {
	
    private static final long serialVersionUID = 1L;
    private static final String JSP_LISTA_EDICIONES = "/WEB-INF/views/misRegistros.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        
        // Obtener usuario logueado 
        HttpSession session = req.getSession(false);
        DtSesionUsuario usuarioLogueado = (DtSesionUsuario) session.getAttribute("usuario_logueado");
        String movNick = usuarioLogueado.getNickname();

        try {
        	
        	cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
            cliente.ws.sc.WebServices port = service.getWebServicesPort();

            // Convertir las ediciones del organizador a DTEdiciones 
            DtEdicionArray edicionesA  =(DtEdicionArray) port.listarEdicionesConRegistroUsuario(movNick);
            List<DtEdicion> ediciones = edicionesA.getItem();
            Map<String,String> eventoNombreMap = new HashMap<>();
            for (DtEdicion e : ediciones) {
                if (e != null) {
                    DtEdicion dto = e;
                    if (dto != null) {
                        eventoNombreMap.put(dto.getNombre(), port.nombreEventoDeEdicion(dto.getNombre()));
                    }
                }
            }

            // Pasar la lista al JSP 
            req.setAttribute("EDICION_EVENTO", eventoNombreMap);
            req.setAttribute("LISTA_EDICIONES", ediciones);

           
            // Enviar al JSP
            req.getRequestDispatcher(JSP_LISTA_EDICIONES).forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error", (e.getMessage() != null) ? e.getMessage() : "Error al listar ediciones.");
            req.getRequestDispatcher(JSP_LISTA_EDICIONES).forward(req, resp);
        }
    }


}
