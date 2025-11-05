package com.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import cliente.ws.sc.DtSesionUsuario;
import cliente.ws.sc.DtUsuarioListaConsulta;
import cliente.ws.sc.DtUsuarioListaConsultaArray;
import cliente.ws.sc.RolUsuario;


@WebServlet(name = "ListaUsuariosSvt", urlPatterns = {"/ListaUsuarios"})
@MultipartConfig(
    fileSizeThreshold = 1 * 1024 * 1024,
    maxFileSize = 10 * 1024 * 1024,
    maxRequestSize = 15 * 1024 * 1024
)
public class ListaUsuariosSvt extends HttpServlet {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
	  
	// Flag de rol para el JSP 
	  boolean esVis = false;
	    HttpSession session = req.getSession(false);
	    DtSesionUsuario sesUser = null;
	    if (session != null) {
	      Object o = session.getAttribute("usuario_logueado");
	      if (o instanceof DtSesionUsuario u) {
	        sesUser = u;
	        if (u.getRol() == RolUsuario.VISITANTE) {
	          esVis = true;
	        }
	      }
	    }
	    req.setAttribute("ES_VIS", esVis);
	 
    try {

      cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
      cliente.ws.sc.WebServices port = service.getWebServicesPort();
      DtUsuarioListaConsultaArray asisDTA= port.listarDTAsistentes();
      DtUsuarioListaConsultaArray orgDTA= port.listarDTOrganizadores();

      List<DtUsuarioListaConsulta> Asistentes=asisDTA.getItem();
      List<DtUsuarioListaConsulta> Organizadores=orgDTA.getItem();

      req.setAttribute("LISTA_ASISTENTES", Asistentes);
      req.setAttribute("LISTA_ORGANIZADORES", Organizadores);
      
      Set<String> seguidosSet = null;
      if (!esVis && sesUser != null && sesUser.getNickname() != null && !sesUser.getNickname().isBlank()) {
        try {
          DtUsuarioListaConsulta yo = port.consultaDeUsuario(sesUser.getNickname());
          List<String> seguidos = (yo != null) ? yo.getSeguidos() : null;
          if (seguidos != null) seguidosSet = new HashSet<>(seguidos);
        } catch (Exception ignore) {
          // fallback en JSP usando getSeguidores() del usuario tarjeta
        }
      }
      req.setAttribute("SEGUIDOS_SET", seguidosSet);

    } catch (Exception e) {
      req.setAttribute("msgError", "No se pudo cargar la lista de usuarios: " + e.getMessage());
      req.setAttribute("LISTA_ASISTENTES", null);
      req.setAttribute("LISTA_ORGANIZADORES", null);
      req.setAttribute("SEGUIDOS_SET", null);
    }

    req.getRequestDispatcher("/WEB-INF/views/ListaUsuarios.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String accion = req.getParameter("accion");  
    String objetivoNick = req.getParameter("nick");

    jakarta.servlet.http.HttpSession ses = req.getSession(false);
    if (ses == null) {
      resp.sendRedirect(req.getContextPath() + "/ListaUsuarios");
      return;
    }

    Object o = ses.getAttribute("usuario_logueado");
    if (!(o instanceof cliente.ws.sc.DtSesionUsuario u)) {
      resp.sendRedirect(req.getContextPath() + "/ListaUsuarios");
      return;
    }

    String principal = u.getNickname(); // quien realiza la acción
    if (principal == null || principal.isBlank()
        || objetivoNick == null || objetivoNick.isBlank()
        || principal.equals(objetivoNick)) {
      resp.sendRedirect(req.getContextPath() + "/ListaUsuarios");
      return;
    }

    try {
      cliente.ws.sc.WebServicesService service = new cliente.ws.sc.WebServicesService();
      cliente.ws.sc.WebServices port = service.getWebServicesPort();

      if ("seguir".equalsIgnoreCase(accion)) {
        port.seguirPersona(principal, objetivoNick);
      } else if ("dejar".equalsIgnoreCase(accion)) {
        port.sacarSeguirPersona(principal, objetivoNick);
      }
    } catch (Exception ignore) {
    }

    resp.sendRedirect(req.getContextPath() + "/ListaUsuarios");
  }
}


