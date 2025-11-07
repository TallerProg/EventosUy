<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String ctx  = request.getContextPath();
  String movNick = (String) session.getAttribute("MOVIL_NICK");
  String img = (String) session.getAttribute("IMAGEN_LOGUEADO");
  String imagen = (img != null && !img.isBlank()) ? (ctx + img) : (ctx + "/media/img/default.png");
%>

<div class="header-container container-fluid container-xl position-relative d-flex align-items-center justify-content-between mb-3">
  <nav id="navmenu" class="navmenu">
    <ul>
      <li>
		  <a href="<%= ctx %>/home" class="logo d-flex align-items-center">
		    <img src="<%= ctx %>/media/img/logoeuy.png"
		         alt="EventUY"
		         style="height:40px;max-height:40px;width:auto;object-fit:contain;">
		  </a>
		</li>

      <!-- Ítems principales -->
      <li><a href="<%= ctx %>/home">Inicio</a></li>
      <li><a href="<%= ctx %>/ediciones">Ediciones</a></li>
      <li><a href="<%= ctx %>/misRegistros">Asistencia</a></li>

      <!-- Saludo + salir (alineado a la derecha) -->
      <li class="ms-auto d-flex align-items-center gap-2">
        <a href="<%= ctx %>/home" class="user-info text-decoration-none d-flex align-items-center gap-2">
          <img src="<%= imagen %>" alt=""
               style="width:32px;height:32px;border-radius:50%;object-fit:cover;">
          <span class="fw-semibold"><%= (movNick == null ? "" : movNick) %></span>
        </a>
        <a href="<%= ctx %>/logout" class="btn p-0 border-0 bg-transparent"
           title="Salir" aria-label="Salir">
          <i class="login-icon bi bi-box-arrow-right fs-4"></i>
        </a>
      </li>
    </ul>

    <!-- Hamburguesa -->
    <i class="mobile-nav-toggle d-xl-none bi bi-list" aria-label="Abrir menú"></i>
  </nav>
</div>