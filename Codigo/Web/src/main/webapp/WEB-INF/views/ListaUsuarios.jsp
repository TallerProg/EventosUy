<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cliente.ws.sc.DtUsuarioListaConsulta" %>
<%@ page import="cliente.ws.sc.DtSesionUsuario" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>

<%
    String ctx = request.getContextPath();
    List<DtUsuarioListaConsulta> asistentes =
        (List<DtUsuarioListaConsulta>) request.getAttribute("LISTA_ASISTENTES");
    List<DtUsuarioListaConsulta> organizadores =
        (List<DtUsuarioListaConsulta>) request.getAttribute("LISTA_ORGANIZADORES");

    boolean ES_VIS = Boolean.TRUE.equals(request.getAttribute("ES_VIS"));
    Set<String> SEGUIDOS_SET =
        (Set<String>) request.getAttribute("SEGUIDOS_SET");

    // Nick del usuario logueado (para ocultar botón en su propia tarjeta; no usamos rol acá)
    jakarta.servlet.http.HttpSession ses = request.getSession(false);
    String LOG_NICK = null;
    if (ses != null) {
      Object o = ses.getAttribute("usuario_logueado");
      if (o instanceof DtSesionUsuario u) {
        LOG_NICK = u.getNickname();
      }
    }
%>

<%!
  // Chequea si la lista contiene el nick dado
  private boolean contiene(java.util.List<?> lst, String nick) {
    if (lst == null || nick == null) return false;
    for (Object o : lst) if (nick.equals(String.valueOf(o))) return true;
    return false;
  }
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Lista Usuarios</title>
  <style>
    .speaker-card { position: relative; }
    .follow-toggle { position: absolute; top: .5rem; right: .5rem; z-index: 2; }
    .follow-toggle .btn { padding: .25rem .5rem; line-height: 1; border-radius: .375rem; }
    .icon-bell { font-size: 1.1rem; vertical-align: -1px; }
    .icon-following { color: #0d6efd; } /*azul*/
    .icon-not-following { color: #6c757d; } /*gris*/
  </style>
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
            for (DtUsuarioListaConsulta asistente : asistentes) {
              String raw = (asistente != null) ? asistente.getImg() : null; 
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

              String cardNick = (asistente != null) ? asistente.getNickname() : null;

              boolean mostrarToggle = !ES_VIS && cardNick != null && LOG_NICK != null && !cardNick.equals(LOG_NICK);

              boolean yaSigo = (SEGUIDOS_SET != null)
                                 ? SEGUIDOS_SET.contains(cardNick)
                                 : ((asistente != null) && contiene(asistente.getSeguidores(), LOG_NICK));
        %>
          <div class="col-lg-3 col-md-6">
            <div class="speaker-card text-center">

              <% if (mostrarToggle) { %>
                <div class="follow-toggle">
                  <form method="post" action="<%= ctx %>/ListaUsuarios">
                    <input type="hidden" name="accion" value="<%= yaSigo ? "dejar" : "seguir" %>"/>
                    <input type="hidden" name="nick" value="<%= cardNick %>"/>
                    <button type="submit"
					        class="btn btn-sm btn-outline-secondary"
					        title="<%= yaSigo ? "Dejar de seguir" : "Seguir" %>"
					        aria-label="<%= yaSigo ? "Dejar de seguir a " + cardNick : "Seguir a " + cardNick %>">
					  <i class="bi bi-bell-fill icon-bell <%= yaSigo ? "icon-following" : "icon-not-following" %>"></i>
					</button>
                  </form>
                </div>
              <% } %>

              <a href="<%= ctx %>/ConsultaUsuario?nick=<%= java.net.URLEncoder.encode(cardNick, java.nio.charset.StandardCharsets.UTF_8) %>" class="text-decoration-none">
                <div class="speaker-image">
                  <img src="<%= img %>"
                       alt="<%= cardNick %>"
                       class="img-fluid rounded-circle p-3"
                       onerror="this.onerror=null;this.src='<%= ctx %>/media/img/default.png'">
                </div>
                <div class="speaker-content">
                  <p class="speaker-title"><%= asistente.getNombre() %></p>
                </div>
              </a>
            </div>
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
            for (DtUsuarioListaConsulta organizador : organizadores) {
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

              String cardNick = (organizador != null) ? organizador.getNickname() : null;

              boolean mostrarToggle = !ES_VIS && cardNick != null && LOG_NICK != null && !cardNick.equals(LOG_NICK);

              boolean yaSigo = (SEGUIDOS_SET != null)
                                 ? SEGUIDOS_SET.contains(cardNick)
                                 : ((organizador != null) && contiene(organizador.getSeguidores(), LOG_NICK));
        %>
          <div class="col-lg-3 col-md-6">
            <div class="speaker-card text-center">

              <% if (mostrarToggle) { %>
                <div class="follow-toggle">
                  <form method="post" action="<%= ctx %>/ListaUsuarios">
                    <input type="hidden" name="accion" value="<%= yaSigo ? "dejar" : "seguir" %>"/>
                    <input type="hidden" name="nick" value="<%= cardNick %>"/>
                    <button type="submit"
					        class="btn btn-sm btn-outline-secondary"
					        title="<%= yaSigo ? "Dejar de seguir" : "Seguir" %>"
					        aria-label="<%= yaSigo ? "Dejar de seguir a " + cardNick : "Seguir a " + cardNick %>">
					  <i class="bi bi-bell-fill icon-bell <%= yaSigo ? "icon-following" : "icon-not-following" %>"></i>
					</button>
                  </form>
                </div>
              <% } %>

              <a href="<%= ctx %>/ConsultaUsuario?nick=<%= java.net.URLEncoder.encode(cardNick, java.nio.charset.StandardCharsets.UTF_8) %>" class="text-decoration-none">
                <div class="speaker-image">
                  <img src="<%= img %>"
                       alt="<%= cardNick %>"
                       class="img-fluid rounded-circle p-3"
                       onerror="this.onerror=null;this.src='<%= ctx %>/media/img/default.png'">
                </div>
                <div class="speaker-content">
                  <p class="speaker-title"><%= organizador.getNombre() %></p>
                </div>
              </a>
            </div>
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
