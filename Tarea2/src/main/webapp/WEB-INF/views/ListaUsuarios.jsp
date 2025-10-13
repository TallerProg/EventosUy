<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="ServidorCentral.logica.Asistente" %>
<%@ page import="ServidorCentral.logica.Organizador" %>
<%@ page import="java.util.List" %>

<%
    String ctx = request.getContextPath();
    List<Asistente> asistentes = (List<Asistente>) request.getAttribute("LISTA_ASISTENTES");
    List<Organizador> organizadores = (List<Organizador>) request.getAttribute("LISTA_ORGANIZADORES");
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Lista Usuarios</title>
</head>

<body class="index-page">
<header id="header" class="header d-flex align-items-center fixed-top">
  <jsp:include page="/WEB-INF/views/template/header.jsp" />
</header>

<main class="main mt-5 pt-5">

  <!-- Asistentes -->
  <section id="asistentes" class="speakers section">
    <div class="container section-title">
      <h2>Asistentes</h2>
    </div>
    <div class="container">
      <div class="row g-4 justify-content-center">

        <%
          if (asistentes != null) {
            for (Asistente asistente : asistentes) {
              String raw = (asistente != null) ? asistente.getImg() : null; // puede ser "nick.jpg" o "/media/img/usuarios/nick.jpg"
              String rel = (raw != null && !raw.isBlank())
                         ? (raw.startsWith("/") ? raw : "/media/img/usuarios/" + raw)
                         : "/media/img/default.png";
              String abs = application.getRealPath(rel);
              long ver = 0L;
              if (abs != null) {
                java.io.File f = new java.io.File(abs);
                if (f.exists()) ver = f.lastModified();
              }
              String img = ctx + rel + (ver > 0 ? "?v=" + ver : "");
        %>
          <div class="col-lg-3 col-md-6">
            <a href="<%= ctx %>/ConsultaUsuario?nick=<%= java.net.URLEncoder.encode(asistente.getNickname(), java.nio.charset.StandardCharsets.UTF_8) %>" class="text-decoration-none">
              <div class="speaker-card text-center">
                <div class="speaker-image">
                  <img src="<%= img %>"
                       alt="<%= asistente.getNickname() %>"
                       class="img-fluid rounded-circle p-3"
                       onerror="this.onerror=null;this.src='<%= ctx %>/media/img/default.png'">
                </div>
                <div class="speaker-content">
                  <p class="speaker-title"><%= asistente.getNombre() %></p>
                </div>
              </div>
            </a>
          </div>
        <%
            }
          }
        %>

      </div>
    </div>
  </section>

  <!-- Organizadores -->
  <section id="organizadores" class="speakers section">
    <div class="container section-title">
      <h2>Organizadores</h2>
    </div>
    <div class="container">
      <div class="row g-4 justify-content-center">

        <%
          if (organizadores != null) {
            for (Organizador organizador : organizadores) {
              String raw = (organizador != null) ? organizador.getImg() : null;
              String rel = (raw != null && !raw.isBlank())
                         ? (raw.startsWith("/") ? raw : "/media/img/usuarios/" + raw)
                         : "/media/img/default.png";
              String abs = application.getRealPath(rel);
              long ver = 0L;
              if (abs != null) {
                java.io.File f = new java.io.File(abs);
                if (f.exists()) ver = f.lastModified();
              }
              String img = ctx + rel + (ver > 0 ? "?v=" + ver : "");
        %>
          <div class="col-lg-3 col-md-6">
            <a href="<%= ctx %>/ConsultaUsuario?nick=<%= java.net.URLEncoder.encode(organizador.getNickname(), java.nio.charset.StandardCharsets.UTF_8) %>" class="text-decoration-none">
              <div class="speaker-card text-center">
                <div class="speaker-image">
                  <img src="<%= img %>"
                       alt="<%= organizador.getNickname() %>"
                       class="img-fluid rounded-circle p-3"
                       onerror="this.onerror=null;this.src='<%= ctx %>/media/img/default.png'">
                </div>
                <div class="speaker-content">
                  <p class="speaker-title"><%= organizador.getNombre() %></p>
                </div>
              </div>
            </a>
          </div>
        <%
            }
          }
        %>

      </div>
    </div>
  </section>
</main>

<hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
<footer id="footer" class="footer position-relative light-background">
  <jsp:include page="/WEB-INF/views/template/footer.jsp" />
</footer>

<!-- Scripts -->
<script src="<%= ctx %>/media/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="<%= ctx %>/media/js/main.js"></script>
</body>
</html>
