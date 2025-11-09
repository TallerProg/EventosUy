<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="cliente.ws.sc.DtSesionUsuario" %>
<%@ page import="cliente.ws.sc.DtUsuarioListaConsulta" %>

<%
  String ctx = request.getContextPath();
  DtSesionUsuario dtrol = (DtSesionUsuario) request.getAttribute("USUARIOROL");
  DtUsuarioListaConsulta dtusuario = (DtUsuarioListaConsulta) request.getAttribute("USUARIO");
  String urlImg = (String) request.getAttribute("IMAGEN");

  boolean ES_ORG  = Boolean.TRUE.equals(request.getAttribute("ES_ORG"));
  boolean ES_ASIS = Boolean.TRUE.equals(request.getAttribute("ES_ASIS"));

  @SuppressWarnings("unchecked")
  List<DtUsuarioListaConsulta> seguidores = (List<DtUsuarioListaConsulta>) request.getAttribute("SEGUIDORES");

  @SuppressWarnings("unchecked")
  List<DtUsuarioListaConsulta> seguidos = (List<DtUsuarioListaConsulta>) request.getAttribute("SEGUIDOS");
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Perfil</title>
  <link href="<%= ctx %>/media/css/perfil.css" rel="stylesheet">

  <style>
    .follow-list { max-height: 400px; overflow-y: auto; }
    .follow-item { display: flex; align-items: center; justify-content: space-between;
                   border-bottom: 1px solid #ddd; padding: .5rem 0; }
    .follow-user { display: flex; align-items: center; gap: .5rem; text-decoration: none; color: inherit; }
    .follow-user img { width: 40px; height: 40px; border-radius: 50%; object-fit: cover; }
    .follow-btn { white-space: nowrap; }
  </style>
</head>

<body class="index-page">
  <header id="header" class="header d-flex align-items-center fixed-top">
    <jsp:include page="/WEB-INF/views/template/header.jsp" />
  </header>

  <main class="main mt-5 pt-5">
    <section id="perfil-usuario" class="section">
      <div class="container section-title text-center">
        <h2>Perfil de Usuario</h2>
      </div>

      <div class="container">
        <div class="row justify-content-center align-items-center">
          <div class="col-lg-4 text-center">
            <div class="profile-avatar">
              <img src="<%= (urlImg != null && !urlImg.isBlank()) ? urlImg : (ctx + "/media/img/default.png") %>"
                   alt="<%= dtusuario.getNickname() %>"
                   onerror="this.onerror=null;this.src='<%= ctx %>/media/img/default.png'">
            </div>
            <h4 class="mt-3"><%= dtusuario.getNombre() %></h4>
            <p class="text-muted"><%= dtrol.getRol() %></p>
          </div>

          <div class="col-lg-6">
            <div class="profile-info">
              <h5>Detalles del Perfil</h5>
              <ul class="list-unstyled">
                <li><strong>Nickname:</strong> <%= dtusuario.getNickname() %></li>
                <li><strong>Email:</strong> <%= dtusuario.getCorreo() %></li>

                <% if (ES_ORG) { %>
                  <li><strong>Descripción:</strong> <%= dtusuario.getDescripcion() %></li>
                  <li><strong>Sitio Web:</strong> <%= dtusuario.getUrl() %></li>
                <% } else if (ES_ASIS) { 
                     String ins = (String) request.getAttribute("INSTITUCION"); %>
                  <li><strong>Fecha de Nacimiento:</strong> <%= dtusuario.getFNacimientoS() %></li>
                  <li><strong>Institución:</strong> <%= (ins != null) ? ins : "(sin institución)" %></li>
                <% } %>
              </ul>
              <a href="<%= ctx %>/editarperfil" class="btn btn-primary mt-4">Editar Perfil</a>
            </div>
          </div>
        </div>
      </div>
    </section>

    <section id="seguimiento" class="section mt-5">
      <div class="container">
        <div class="row">
          <!-- Seguidores -->
          <div class="col-md-6 mb-4">
            <h5 class="text-center mb-3">Seguidores</h5>
            <div class="follow-list">
              <% if (seguidores != null && !seguidores.isEmpty()) {
                   for (DtUsuarioListaConsulta seg : seguidores) {
                     String img = (seg.getImg() != null && !seg.getImg().isBlank())
                                  ? (ctx + seg.getImg())
                                  : (ctx + "/media/img/default.png");
              %>
                <div class="follow-item">
                  <a class="follow-user" href="<%= ctx %>/ConsultaUsuario?nick=<%= seg.getNickname() %>">
                    <img src="<%= img %>" alt="">
                    <span><%= seg.getNickname() %></span>
                  </a>
                </div>
              <% } } else { %>
                <p class="text-muted text-center">Aún no tienes seguidores.</p>
              <% } %>
            </div>
          </div>

          <!-- Seguidos -->
          <div class="col-md-6 mb-4">
            <h5 class="text-center mb-3">Seguidos</h5>
            <div class="follow-list">
              <% if (seguidos != null && !seguidos.isEmpty()) {
                   for (DtUsuarioListaConsulta seg : seguidos) {
                     String img = (seg.getImg() != null && !seg.getImg().isBlank())
                                  ? (ctx + seg.getImg())
                                  : (ctx + "/media/img/default.png");
              %>
                <div class="follow-item">
                  <a class="follow-user" href="<%= ctx %>/ConsultaUsuario?nick=<%= seg.getNickname() %>">
                    <img src="<%= img %>" alt="">
                    <span><%= seg.getNickname() %></span>
                  </a>
                  <form method="post" action="<%= ctx %>/perfil" class="m-0">
                    <input type="hidden" name="accion" value="dejar">
                    <input type="hidden" name="nick" value="<%= seg.getNickname() %>">
                    <button type="submit" class="btn btn-outline-danger btn-sm follow-btn">
                      Dejar de seguir
                    </button>
                  </form>
                </div>
              <% } } else { %>
                <p class="text-muted text-center">No sigues a nadie.</p>
              <% } %>
            </div>
          </div>
        </div>
      </div>
    </section>
  </main>

  <hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
  <footer id="footer" class="footer position-relative light-background">
    <jsp:include page="/WEB-INF/views/template/footer.jsp" />
  </footer>

  <script src="<%= ctx %>/media/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
