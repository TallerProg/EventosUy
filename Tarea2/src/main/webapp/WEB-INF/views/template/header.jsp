<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="servidorcentral.logica.DTSesionUsuario" %>
<%@ page import="cliente.ws.sc.DtSesionUsuario" %>
<%
  String ctx = request.getContextPath(); 

  Object sesObj = session.getAttribute("usuario_logueado");
  String sesNick = null;
  String sesRol  = null;

  if (sesObj instanceof servidorcentral.logica.DTSesionUsuario s) {
    sesNick = s.getNickname();
    try {

      sesRol = (s.getRolString() != null) ? s.getRolString()
              : (s.getRol() != null ? s.getRol().name() : null);
    } catch (Throwable ignore) { sesRol = null; }
  } else if (sesObj instanceof cliente.ws.sc.DtSesionUsuario s) {
    sesNick = s.getNickname();
    try {
      sesRol = String.valueOf(s.getRol());
    } catch (Throwable ignore) { sesRol = null; }
  }

  String img = (String) session.getAttribute("IMAGEN_LOGUEADO");
  String imagen = (img != null && !img.isBlank()) ? (ctx + img) : (ctx + "/media/img/default.png");
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
      <% if (sesRol != null && sesRol.trim().equalsIgnoreCase("ORGANIZADOR")) { %>
        <li><a href="<%= ctx %>/MisEdiciones">Mis Ediciones</a></li>
      <% } %>

      <li class="nav-item search-item ms-auto">
        <form class="search-form" action="<%= ctx %>/search" method="get">
          <input type="text" name="q" class="search-input" placeholder="Buscar eventos...">
          <button type="submit" class="search-btn"><i class="bi bi-search"></i></button>
        </form>
      </li>

      <% if (sesNick == null || sesNick.isBlank()) { %>
        <li class="ms-auto d-flex gap-2">
          <a href="<%= ctx %>/login" class="btn btn-primary btn-sm text-white">Iniciar Sesion</a>
          <a href="<%= ctx %>/Registrarse" class="btn btn-outline-primary btn-sm">Registrarse</a>
        </li>
      <% } else { %>
        <li class="ms-auto d-flex align-items-center gap-2">
          <a href="<%= ctx %>/perfil" class="user-info text-decoration-none d-flex align-items-center gap-2">
            <img src="<%= imagen %>" alt="">
            <span class="fw-semibold"><%= sesNick %></span>
          </a>
          <a href="<%= ctx %>/logout" class="btn p-0 border-0 bg-transparent">
            <i class="login-icon bi bi-box-arrow-in-right fs-3" title="Cerrar Sesi├│n"></i>
          </a>
        </li>
      <% } %>
    </ul>
    <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
  </nav>
</div>

