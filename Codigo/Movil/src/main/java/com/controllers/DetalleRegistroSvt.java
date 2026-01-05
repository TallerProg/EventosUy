package com.controllers;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.DtRegistro;
import cliente.ws.sc.DtRegistroArray;
import cliente.ws.sc.WebServices;
import cliente.ws.sc.WebServicesService;
import com.config.WSClientProvider;

@WebServlet("/detalleRegistro")
public class DetalleRegistroSvt extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final String JSP_DETALLE = "/WEB-INF/views/detalleRegistro.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	HttpSession session = req.getSession(false);
        DtSesionUsuario usuarioLogueado = (DtSesionUsuario) session.getAttribute("usuario_logueado");
        String nickname = usuarioLogueado.getNickname();

        String nombreEdicion = req.getParameter("edicion");

        if (nombreEdicion == null || nombreEdicion.isBlank()) {
            req.setAttribute("error", "Edición no especificada.");
            req.getRequestDispatcher(JSP_DETALLE).forward(req, resp);
            return;
        }

        try {
            WebServicesService service = WSClientProvider.newService();
            WebServices port = service.getWebServicesPort();

            DtRegistroArray registrosArray = port.listarRegistrosDeAsistente(nickname);
            List<DtRegistro> registros = registrosArray.getItem();
            DtRegistro reg = null;
            for (DtRegistro regAux : registros) {
				if (regAux.getNombreEdicion().equals(nombreEdicion)) {
				 reg = regAux;
					break;
				}
			}

            if (reg == null) {
                req.setAttribute("error", "No se encontró un registro para esta edición.");
            } else {
                req.setAttribute("REGISTRO", reg);
            }

            req.getRequestDispatcher(JSP_DETALLE).forward(req, resp);

        } catch (Exception e) {
            req.setAttribute("error",
                    (e.getMessage() != null) ? e.getMessage() : "Error al obtener el detalle del registro.");
            req.getRequestDispatcher(JSP_DETALLE).forward(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        cliente.ws.sc.DtSesionUsuario usu =
                (cliente.ws.sc.DtSesionUsuario) session.getAttribute("usuario_logueado");

        if (usu == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String nombreEdicion = req.getParameter("nombreEdicion");
        String nicknameAsistente = usu.getNickname();

        try {
            cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
            cliente.ws.sc.WebServices port = service.getWebServicesPort();

            // 1) Marcar asistencia en el servidor central
            port.marcarAsistido(nombreEdicion, nicknameAsistente);

            // 2) Mensaje para el JSP
            req.setAttribute("mensaje", "Asistencia registrada correctamente.");

        } catch (Exception e) {
            req.setAttribute("error", "No se pudo marcar la asistencia: "
                    + (e.getMessage() != null ? e.getMessage() : "Error desconocido."));
        }

        // 3) Recargar los mismos datos que en el GET (detalle actualizado)
        //    Reutilizamos la lógica existente del doGet:
        doGet(req, resp);
    }

}
