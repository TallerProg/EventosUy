<%@page import="cliente.ws.sc.DtInstitucionArray"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="cliente.ws.sc.DtInstitucion" %>
<%
  String ctx = request.getContextPath(); 
  boolean ES_ORG = Boolean.TRUE.equals(request.getAttribute("ES_ORG"));

  @SuppressWarnings("unchecked")
  java.util.List<DtInstitucion> instituciones =
      (java.util.List<DtInstitucion>) request.getAttribute("INSTITUCIONES");

  Object errMsg = request.getAttribute("msgError");
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="template/head.jsp" />
  <title>Lista de Instituciones</title>
</head>

<body class="index-page">

<header id="header" class="header d-flex align-items-center fixed-top">
  <jsp:include page="template/header.jsp" />
</header>

<main class="main mt-5 pt-5">

  <section id="instituciones" class="speakers section">
    <div class="container section-title d-flex justify-content-between align-items-center">
      <h2>Instituciones</h2>

      <!-- Botón visible solo para organizador -->
      <% if (ES_ORG) { %>
    	<a href="<%= ctx %>/AltaInstitucion" class="btn btn-primary" title="Alta de Institución" aria-label="Alta de Institución">
      	<i class="bi bi-plus-circle me-1"></i> Alta de Institución
    	</a>
      <% } %>
    </div>

    <div class="container">

      <% if (errMsg != null) { %>
        <div class="alert alert-danger d-flex align-items-center">
          <i class="bi bi-exclamation-triangle me-2"></i><%= errMsg %>
        </div>
      <% } %>

      <% if (instituciones == null || instituciones.isEmpty()) { %>
        <div class="alert alert-warning">No hay instituciones para mostrar.</div>
      <% } else { %>
        <div class="row g-4 justify-content-center">
          <% for (DtInstitucion i : instituciones) {
        	  String img = (i != null && i.getImagen() != null && !i.getImagen().isBlank()) ? (ctx + i.getImagen()): (ctx + "/media/img/default.png");
               String nombre = (i != null && i.getNombre() != null) ? i.getNombre() : "";
               String desc   = (i != null && i.getDescripcion() != null) ? i.getDescripcion() : "";
               String web    = (i != null && i.getUrl() != null) ? i.getUrl() : "";
          %>

          <!-- Columna flexible para igualar alturas -->
          <div class="col-lg-3 col-md-6 d-flex">
            <!-- Card flexible a 100% de la altura disponible -->
            <div class="speaker-card text-center h-100 d-flex flex-column position-relative w-100">
              <div class="speaker-image">
                <img src="<%= img %>" alt="<%= nombre %>" class="img-fluid">
              </div>
              <div class="speaker-content d-flex flex-column">
                <p class="speaker-title mb-1"><%= nombre %></p>
                <p class="speaker-company mb-3 flex-grow-1"><%= desc %></p>

                <% if (!web.isBlank()) { %>
                  <a href="<%= web %>" target="_blank" rel="noopener" class="btn btn-primary btn-ver-ediciones mt-auto">
                    Visitar sitio
                  </a>
                <% } %>
              </div>
            </div>
          </div>

          <% } %>
        </div>
      <% } %>
    </div>
  </section>

</main>

<hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
<footer id="footer" class="footer position-relative light-background">
  <jsp:include page="template/footer.jsp" />
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
