package com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import cliente.ws.sc.DTeventoOedicion;
import cliente.ws.sc.DTeventoOedicionArray;
import cliente.ws.sc.DtOrganizador;
import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.RolUsuario;





@WebServlet("/Search")
public class SearchSvt extends HttpServlet {
  private static final long serialVersionUID = 1L;

  private static final String JSP_LISTA_EVENTOS = "/WEB-INF/views/Search.jsp";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  String busqueda = req.getParameter("q");
	  cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
      cliente.ws.sc.WebServices port = service.getWebServicesPort();
      DTeventoOedicionArray evenoediDTA= port.listarEventosYEdicionesBusqueda(busqueda);
      List<DTeventoOedicion> evenOedi=evenoediDTA.getItem();
      HttpSession session = req.getSession(false);
      final DtSesionUsuario usuario = (session != null) ? (DtSesionUsuario) session.getAttribute("usuario_logueado") : null;
      boolean esOrg = (usuario != null && usuario.getRol() == RolUsuario.ORGANIZADOR);
      
      evenOedi = evenOedi.stream()
    	        .filter(ev -> {
    	            if ((ev.getTipo().equals("edicion") && esEdicionIngresada(ev)) && !esOrganizadorDeEdicion(ev, usuario, esOrg)) {
    	                return false; 
    	            }
    	            return true; 
    	        })
    	        .collect(Collectors.toList());

      
      req.setAttribute("LISTA_EVENTOS_EDICIONES", evenOedi);
      req.getRequestDispatcher(JSP_LISTA_EVENTOS).forward(req, resp);

    
  }
  
  private static boolean esEdicionIngresada(DTeventoOedicion edicion) {
    return "ingresada".equalsIgnoreCase(edicion.getEstado());
  }

  private static boolean esOrganizadorDeEdicion(DTeventoOedicion edicion, DtSesionUsuario usuario, boolean esOrg) {
    if (edicion == null || usuario == null || !esOrg) return false;

    List<DtOrganizador> organizadores = edicion.getOrganizadores();
    if (organizadores == null || organizadores.isEmpty()) return false;

    return organizadores.stream().anyMatch(o -> Objects.equals(o.getNickname(), usuario.getNickname()));
  }
}

