<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cliente.ws.sc.DtUsuarioListaConsulta"%>
<%@ page import="cliente.ws.sc.DtEdicion"%>
<%@ page import="cliente.ws.sc.DtSesionUsuario"%>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder" %>

<%
  String ctx = request.getContextPath();
  DtUsuarioListaConsulta u = (DtUsuarioListaConsulta) request.getAttribute("usuario");
  String rol = (String) request.getAttribute("rol");
  String img = (String) request.getAttribute("IMAGEN");

  Object sObj = request.getAttribute("esSuPerfil");
  boolean s = (sObj instanceof Boolean) ? ((Boolean) sObj)
                                        : "true".equalsIgnoreCase(String.valueOf(sObj));

  String rel = (img != null && !img.isBlank())
             ? (img.startsWith("/") ? img : "/media/img/usuarios/" + img)
             : "/media/img/default.png";
  String abs = application.getRealPath(rel);
  long ver = 0L;
  if (abs != null) {
    java.io.File f = new java.io.File(abs);
    if (f.exists()) ver = f.lastModified();
  }
  String imagenPerfil = ctx + rel + (ver > 0 ? "?v=" + ver : "");

  boolean ES_VIS = Boolean.TRUE.equals(request.getAttribute("ES_VIS"));

  @SuppressWarnings("unchecked")
  java.util.Set<String> SEGUIDOS_SET =
      (java.util.Set<String>) request.getAttribute("SEGUIDOS_SET");

  jakarta.servlet.http.HttpSession ses2 = request.getSession(false);
  String LOG_NICK = null;
  if (ses2 != null) {
    Object o2 = ses2.getAttribute("usuario_logueado");
    if (o2 instanceof DtSesionUsuario su) {
      LOG_NICK = su.getNickname();
    }
  }

  String TARGET_NICK = (u != null) ? u.getNickname() : null;

  boolean mostrarToggle = !ES_VIS && !s
                          && TARGET_NICK != null
                          && LOG_NICK != null
                          && !TARGET_NICK.equals(LOG_NICK);

  boolean yaSigo = (SEGUIDOS_SET != null)
                     ? SEGUIDOS_SET.contains(TARGET_NICK)
                     : (u != null && u.getSeguidores() != null && u.getSeguidores().contains(LOG_NICK));
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Consulta Usuario</title>
  <style>
    .user-card { position: relative; }
    .follow-toggle { position: absolute; top: .5rem; right: .5rem; z-index: 2; }
    .follow-toggle .btn { padding: .25rem .5rem; line-height: 1; border-radius: .375rem; }
    .icon-bell { font-size: 1.1rem; vertical-align: -1px; }
    .icon-following { color: #0d6efd; }
    .icon-not-following { color: #6c757d; }
  </style>
</head>
<body class="index-page">

<header id="header" class="header d-flex align-items-center fixed-top">
  <jsp:include page="/WEB-INF/views/template/header.jsp" />
</header>

<main class="main mt-5 pt-5">
  <section id="user-profile" class="speakers section">
    <div class="container section-title">
      <h2>Perfil de Usuario</h2>
    </div>

    <div class="container">
      <div class="row g-4">

        <!-- Columna izquierda: Datos -->
        <div class="col-lg-6">
          <div class="card p-4 h-100 user-card">
            <% if (mostrarToggle) { %>
              <div class="follow-toggle">
                <form method="post" action="<%= ctx %>/ConsultaUsuario">
                  <input type="hidden" name="accion" value="<%= yaSigo ? "dejar" : "seguir" %>"/>
                  <input type="hidden" name="nick" value="<%= TARGET_NICK %>"/>
                  <button type="submit"
                          class="btn btn-sm btn-outline-secondary"
                          title="<%= yaSigo ? "Dejar de seguir" : "Seguir" %>">
                    <i class="bi bi-bell-fill icon-bell <%= yaSigo ? "icon-following" : "icon-not-following" %>"></i>
                  </button>
                </form>
              </div>
            <% } %>

            <div class="d-flex">
              <div class="me-3">
                <img src="<%= imagenPerfil %>" alt="<%= (u!=null?u.getNickname():"") %>"
                     class="img-fluid" style="max-width:150px; border-radius:8px;">
              </div>
              <div>
                <% if (u != null) { %>
                  <p class="fw-bold fs-4 mb-1"><%= u.getNickname() %></p>
                  <p class="text-muted mb-1"><%= u.getCorreo() %></p>
                  <p class="mb-1">Nombre: <%= u.getNombre() %></p>

                  <% if ("A".equals(rol)) { %>
                    <p class="mb-1">Apellido: <%= u.getApellido() %></p>
                  <% } %>

                  <% if ("O".equals(rol)) { %>
                    <p class="mb-1">Descripción: <%= u.getDescripcion() %></p>
                    <p class="mb-1">
                      URL:
                      <%
                        String url = u.getUrl();
                        if (url != null && !url.isBlank()) {
                      %>
                          <a href="<%= url %>" target="_blank" rel="noopener noreferrer">
                            <%= url %>
                          </a>
                      <%
                        } else {
                      %>
                          <span class="text-muted">No tiene URL registrada</span>
                      <%
                        }
                      %>
                    </p>
                  <% } %>
                <% } else { %>
                  <div class="text-danger">Usuario no disponible.</div>
                <% } %>
              </div>
            </div>
          </div>
        </div>

        <!-- Columna derecha: Ediciones (Organizador) -->
        <% if ("O".equals(rol)) {
             @SuppressWarnings("unchecked")
             List<DtEdicion> ediciones = (List<DtEdicion>) request.getAttribute("Ediciones");
             if (ediciones == null) ediciones = java.util.Collections.emptyList();
        %>
        <div class="col-lg-6">
          <div class="card p-4 h-100">
            <h4 class="mb-3">Ediciones</h4>

            <% if (ediciones.isEmpty()) { %>
              <div class="text-center p-5 text-muted">Sin ediciones</div>
            <% } else { %>
              <div class="row g-3">
                <% for (DtEdicion e : ediciones) {
                     if (e == null) continue;
                     String edNom = e.getNombre();
                     String evNom = e.getNombreEvento(); // del WS
                     boolean tieneEvento = (evNom != null && !evNom.isBlank());

                     String imgEd = (e.getImagenWebPath() != null && !e.getImagenWebPath().isBlank())
                                     ? (ctx + e.getImagenWebPath())
                                     : (ctx + "/media/img/default.png");
                %>
                  <div class="col-12 col-sm-6 col-md-4">
                    <div class="card h-100">
                      <img src="<%= imgEd %>" class="card-img-top" alt="<%= edNom %>">
                      <div class="card-body d-flex flex-column">
                        <h6 class="card-title mb-2"><%= edNom %></h6>
                        <p class="card-text text-muted small mb-3">
                          <%= tieneEvento ? evNom : "Evento no disponible" %>
                        </p>
                        <div class="mt-auto">
                          <% if (tieneEvento) { %>
                            <a
                              href="<%= ctx %>/ediciones-consulta?evento=<%= URLEncoder.encode(evNom, "UTF-8") %>&edicion=<%= URLEncoder.encode(edNom, "UTF-8") %>"
                              class="btn btn-primary btn-sm w-100">
                              Ver detalles
                            </a>
                          <% } else { %>
                            <button class="btn btn-secondary btn-sm w-100" disabled>
                              Ver detalles
                            </button>
                          <% } %>
                        </div>
                      </div>
                    </div>
                  </div>
                <% } %>
              </div>
            <% } %>
          </div>
        </div>
        <% } %>

        <!-- Columna derecha: Ediciones (Asistente, solo su perfil) -->
        <% if ("A".equals(rol) && s) {
             @SuppressWarnings("unchecked")
             List<DtEdicion> listaEdicion = (List<DtEdicion>) request.getAttribute("listaEdicion");
             if (listaEdicion == null) listaEdicion = java.util.Collections.emptyList();
        %>
        <div class="col-lg-6">
          <div class="card p-4 h-100">
            <h4 class="mb-3">Ediciones</h4>

            <% if (listaEdicion.isEmpty()) { %>
              <div class="text-center p-5 text-muted">Sin registros</div>
            <% } else { %>
              <div class="row g-3">
                <% for (DtEdicion ed : listaEdicion) {
                     if (ed == null) continue;
                     String edNom = ed.getNombre();
                     String evNom = ed.getNombreEvento();
                     boolean tieneEvento = (evNom != null && !evNom.isBlank());

                     String imgEd = (ed.getImagenWebPath() != null && !ed.getImagenWebPath().isBlank())
                                     ? (ctx + ed.getImagenWebPath())
                                     : (ctx + "/media/img/default.png");
                %>
                  <div class="col-12 col-sm-6 col-md-4">
                    <div class="card h-100">
                      <img src="<%= imgEd %>" class="card-img-top" alt="<%= edNom %>">
                      <div class="card-body d-flex flex-column">
                        <h6 class="card-title mb-2"><%= edNom %></h6>
                        <p class="card-text text-muted small mb-3">
                          <%= tieneEvento ? evNom : "Evento no disponible" %>
                        </p>
                        <div class="mt-auto">
                          <% if (tieneEvento) { %>
                            <a
                              href="<%= ctx %>/ediciones-consulta?evento=<%= URLEncoder.encode(evNom, "UTF-8") %>&edicion=<%= URLEncoder.encode(edNom, "UTF-8") %>"
                              class="btn btn-primary btn-sm w-100">
                              Ver detalles
                            </a>
                          <% } else { %>
                            <button class="btn btn-secondary btn-sm w-100" disabled>
                              Ver detalles
                            </button>
                          <% } %>
                        </div>
                      </div>
                    </div>
                  </div>
                <% } %>
              </div>
            <% } %>
          </div>
        </div>
        <% } %>

      </div>
    </div>
  </section>
</main>

<footer id="footer" class="footer position-relative light-background">
  <jsp:include page="/WEB-INF/views/template/footer.jsp" />
</footer>

<script src="media/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>




