<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="ServidorCentral.logica.ControllerUsuario.DTSesionUsuario" %>
<%@ page import="ServidorCentral.logica.ControllerUsuario.RolUsuario" %>
<%
  String ctx = request.getContextPath(); // "/tprog-webapp"
  DTSesionUsuario ses = (DTSesionUsuario) session.getAttribute("usuario_logueado");
%>

<div class="header-container container-fluid container-xl position-relative d-flex align-items-center justify-content-between">
  <nav id="navmenu" class="navmenu">
    <ul>
      <a href="<%= ctx %>/home" class="logo d-flex align-items-center">
        <img src="<%= ctx %>/media/img/logoeuy.png" alt="">
      </a>
      <li><a href="<%= ctx %>/ListaUsuarios">Usuarios</a></li>
      <li><a href="<%= ctx %>/Eventos">Eventos</a></li>
      <li><a href="<%= ctx %>/Instituciones">Instituciones</a></li>
     <% if (ses !=null && ses.getRol() == RolUsuario.ORGANIZADOR){ %>
      <li><a href="<%= ctx %>/MisEdiciones">Mis Ediciones</a></li>
    <%} %> 

      <li class="nav-item search-item ms-auto">
        <form class="search-form" action="<%= ctx %>/search" method="get">
          <input type="text" name="q" class="search-input" placeholder="Buscar eventos...">
          <button type="submit" class="search-btn"><i class="bi bi-search"></i></button>
        </form>
      </li>

      <% if (ses == null) { %>
        <li class="ms-auto d-flex gap-2">
          <a href="<%= ctx %>/login" class="btn btn-primary btn-sm text-white">Iniciar Sesión</a>
          <a href="<%= ctx %>/Registrarse" class="btn btn-outline-primary btn-sm">Registrarse</a>
        </li>
     <% } else {
    	 
    	 
    	  String nick = ses.getNickname();

    	  // Rutas posibles (chequeás cuál existe)
    	  String[] extensiones = {".png", ".jpg", ".jpeg"};
    	  String imgSrc = ctx + "/media/img/default.png"; // imagen por defecto

    	  String realPath = application.getRealPath("/media/img/usuarios/");
    	  java.io.File carpeta = new java.io.File(realPath);

    	  for (String ext : extensiones) {
    	    java.io.File f = new java.io.File(carpeta, nick + ext);
    	    if (f.exists()) {
    	      imgSrc = ctx + "/media/img/usuarios/" + nick + ext;
    	      break;
    	    }
    	  }
    	
    	%>
  <li class="ms-auto d-flex align-items-center gap-2">
    <a href="<%= ctx %>/perfil" class="user-info text-decoration-none d-flex align-items-center gap-2">
      <img src="<%= imgSrc %>" alt="Foto de <%= nick %>"
           style="width:32px;height:32px;object-fit:cover;border-radius:50%">
      <span class="fw-semibold"><%= nick %></span>
    </a>
    <a href="<%= ctx %>/logout" class="btn p-0 border-0 bg-transparent">
      <i class="login-icon bi bi-box-arrow-in-right fs-3" title="Cerrar Sesión"></i>
    </a>
  </li>
<% } %>

      
      
      
    </ul>
    <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
  </nav>
</div>
