<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.model.EstadoSesion" %>
<%@ page import="ServidorCentral.logica.Usuario" %>

<%
    EstadoSesion est = (EstadoSesion) session.getAttribute("estado_sesion");

    if (est == EstadoSesion.NO_LOGIN) {
%>

<div class="header-container container-fluid container-xl position-relative d-flex align-items-center justify-content-between">
  <nav id="navmenu" class="navmenu">
    <ul>
      <a href="../indexVis.html" class="logo d-flex align-items-center">
        <img src="../img/logoeuy.png" alt="">
      </a>
      <li><a href="../html/usuariosVVis.html">Usuarios</a></li>
      <li><a href="../html/listaEventosVVis.html">Eventos</a></li>
      <li><a href="../html/listaInstitucionesVVis.html">Instituciones</a></li>
      <li class="nav-item search-item ms-auto">
        <form class="search-form" action="search.html" method="get">
          <input type="text" name="q" class="search-input" placeholder="Buscar eventos...">
          <button type="submit" class="search-btn"><i class="bi bi-search"></i></button>
        </form>
      </li>
      <li class="ms-auto d-flex gap-2">
        <a href="../html/login.html" class="btn btn-primary btn-sm text-white">Iniciar Sesión</a>
        <a href="../html/AltaUsuario.html" class="btn btn-outline-primary btn-sm">Registrarse</a>
      </li>
    </ul>
    <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
  </nav>
</div>

<% } else {  
	Usuario usr = (Usuario) session.getAttribute("usuario_logueado");
	String nick = usr.getNickname(); 
%>
	 <div
      class="header-container container-fluid container-xl position-relative d-flex align-items-center justify-content-between">

      <nav id="navmenu" class="navmenu">
        <ul>
          <a href="indexAsis.html" class="logo d-flex align-items-center">
            <img src="../img/logoeuy.png" alt="">
          </a>
          <li><a href="usuariosOrgAsiAT.html">Usuarios</a></li>
          <li><a href="listaEventosAsis.html">Eventos</a></li>
          <li><a href="listaInstitucionesAsis.html">Instituciones</a></li>
          <li class="nav-item search-item ms-auto">
            <form class="search-form" action="search.html" method="get">
              <input type="text" name="q" class="search-input" placeholder="Buscar eventos...">
              <button type="submit" class="search-btn"><i class="bi bi-search"></i></button>
            </form>
          </li>
          <li class="ms-auto d-flex align-items-center gap-2">
            <a href="perfil_US01.html" class="user-info text-decoration-none d-flex align-items-center gap-2">
              <img src="../media/img/default.png" alt=" ">
              <span class="fw-semibold"><%= nick %></span>
            </a>
		   <a href="../indexVis.html" class="btn p-0 border-0 bg-transparent">
			  <i class="login-icon bi bi-box-arrow-in-right fs-3" title="Cerrar Sesión"></i>
		   </a>

          </li>
        </ul>

        <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
      </nav>
    </div>
    <%} %>
