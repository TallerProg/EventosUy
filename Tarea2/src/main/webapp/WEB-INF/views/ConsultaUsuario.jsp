<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cliente.ws.sc.DtUsuarioListaConsulta"%>
<%@ page import="cliente.ws.sc.DtEdicion"%>
<%@ page import="cliente.ws.sc.DtRegistro"%>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder, java.nio.charset.StandardCharsets" %>

<%
  String ctx = request.getContextPath();
  DtUsuarioListaConsulta u = (DtUsuarioListaConsulta) request.getAttribute("usuario");
  String rol = (String) request.getAttribute("rol");
  String img = (String) request.getAttribute("IMAGEN");

  Object sObj = request.getAttribute("esSuPerfil");
  boolean s = (sObj instanceof Boolean) ? ((Boolean) sObj).booleanValue()
                                        : "true".equalsIgnoreCase(String.valueOf(sObj));

  // Imagen de perfil con cache-busting
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

  // nick del logueado
  jakarta.servlet.http.HttpSession ses2 = request.getSession(false);
  String LOG_NICK = null;
  if (ses2 != null) {
    Object o2 = ses2.getAttribute("usuario_logueado");
    if (o2 instanceof cliente.ws.sc.DtSesionUsuario su) {
      LOG_NICK = su.getNickname();
    }
  }

  // dueño del perfil
  String TARGET_NICK = (u != null) ? u.getNickname() : null;

  // Mostrar botón: no visitante, no es su propio perfil, nicks válidos
  boolean mostrarToggle = !ES_VIS && !s && TARGET_NICK != null && LOG_NICK != null && !TARGET_NICK.equals(LOG_NICK);

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
    .icon-following { color: #0d6efd; }      /* azul: siguiendo */
    .icon-not-following { color: #6c757d; }  /* gris: no siguiendo */
  </style>  
</head>
<body class="index-page">

  <!-- Header -->
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
	                            title="<%= yaSigo ? "Dejar de seguir" : "Seguir" %>"
	                            aria-label="<%= yaSigo ? "Dejar de seguir a " + TARGET_NICK : "Seguir a " + TARGET_NICK %>">
	                      <i class="bi bi-bell-fill icon-bell <%= yaSigo ? "icon-following" : "icon-not-following" %>"></i>
	                    </button>
	                  </form>
	                </div>
                <% } %>
           	
              <div class="d-flex">
                <div class="me-3">
                  <img src="<%= imagenPerfil %>" alt="<%= (u!=null?u.getNickname():"") %>" class="img-fluid"
                       style="max-width:150px; border-radius:8px;">
                </div>
                <div>
                  <% if (u != null) { %>
                    <p class="fw-bold fs-4 mb-1"><%= u.getNickname() %></p>
                    <p class="text-muted mb-1"><%= u.getCorreo() %></p>

                    <p class="mb-1">Nombre: <%= u.getNombre() %></p>

                    <% if ("A".equals(rol)) { %>
                      <p class="mb-1">Apellido: <%= u.getApellido() %></p>
                      <%
						  // ---- Fecha de nacimiento (soporta varios tipos sin romper la compilación) ----
						  String fnacStr = "";
						  Object fn = u.getFNacimiento();
						  try {
						    if (fn instanceof java.time.LocalDate ld) {
						      // java.time.LocalDate
						      fnacStr = ld.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
						    } else if (fn instanceof javax.xml.datatype.XMLGregorianCalendar xc) {
						      // XMLGregorianCalendar clásico de JAX-WS
						      int d = xc.getDay();
						      int m = xc.getMonth();
						      int y = xc.getYear();
						      if (y > 0 && m > 0 && d > 0) {
						        fnacStr = String.format("%02d/%02d/%04d", d, m, y);
						      }
						    } else if (fn != null) {
						      // Fallback genérico por reflexión: intenta métodos getYear/getMonth/getDay (o variantes)
						      Integer y = null, m = null, d = null;
						      try { y = ((Number) fn.getClass().getMethod("getYear").invoke(fn)).intValue(); } catch (Throwable ignore) {}
						      try { m = ((Number) fn.getClass().getMethod("getMonth").invoke(fn)).intValue(); } catch (Throwable ignore) {}
						      try { d = ((Number) fn.getClass().getMethod("getDay").invoke(fn)).intValue(); } catch (Throwable ignore) {}
						
						      // Si no hubo suerte con esos, probamos otros nombres comunes
						      try { if (m == null) m = ((Number) fn.getClass().getMethod("getMonthValue").invoke(fn)).intValue(); } catch (Throwable ignore) {}
						      try { if (d == null) d = ((Number) fn.getClass().getMethod("getDayOfMonth").invoke(fn)).intValue(); } catch (Throwable ignore) {}
						
						      if (y != null && m != null && d != null && y > 0 && m > 0 && d > 0) {
						        fnacStr = String.format("%02d/%02d/%04d", d, m, y);
						      }
						    }
						  } catch (Throwable ignore) {
						    // deja fnacStr vacío si algo falla
						  }
						%>
						<p class="mb-1">Fecha de Nacimiento: <%= fnacStr %></p>

                      
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
          <% if ("O".equals(rol)) { %>
            <div class="col-lg-6">
              <div class="card p-4 h-100">
                <h4 class="mb-3">Ediciones</h4>

                <%
                  @SuppressWarnings("unchecked")
                  List<DtEdicion> ediciones = (List<DtEdicion>) request.getAttribute("Ediciones");
                  java.util.List<DtEdicion> visibles = new java.util.ArrayList<>();
                  if (ediciones != null) {
                    for (DtEdicion e : ediciones) {
                      if (e == null) continue;
                      if (s) {
                        visibles.add(e); // propio perfil: todas
                      } else {
                        String estado = (e.getEstado() != null) ? e.getEstado() : null;
                        if ("Aceptada".equals(String.valueOf(estado))) {
                          visibles.add(e); // público: solo aceptadas
                        }
                      }
                    }
                  }
                  @SuppressWarnings("unchecked")
                  java.util.Map<String,String> eventoNombreMap =
                      (java.util.Map<String,String>) request.getAttribute("EDICION_EVENTO");
                  if (eventoNombreMap == null) eventoNombreMap = java.util.Collections.emptyMap();
                %>

                <% if (!visibles.isEmpty()) { %>
                  <div class="row g-3">
                    <%
                      for (DtEdicion e : visibles) {
                        String nombre = e.getNombre();
                        String nombreEvento = eventoNombreMap.getOrDefault(nombre, "Evento no disponible");
                        String imagenEdicion = (e.getImagenWebPath() != null && !e.getImagenWebPath().isBlank())
                                              ? (ctx + e.getImagenWebPath())
                                              : (ctx + "/media/img/default.png");
                        String href = ctx + "/ediciones-consulta?evento="
                                      + URLEncoder.encode(nombreEvento, StandardCharsets.UTF_8)
                                      + "&edicion=" + URLEncoder.encode(nombre, StandardCharsets.UTF_8);
                    %>
                      <div class="col-12 col-sm-6 col-md-4">
                        <div class="card h-100">
                          <img src="<%= imagenEdicion %>" class="card-img-top" alt="<%= nombre %>">
                          <div class="card-body d-flex flex-column">
                            <h6 class="card-title mb-2"><%= nombre %></h6>
                            <p class="card-text text-muted small mb-3"><%= nombreEvento %></p>
                            <div class="mt-auto">
                              <a href="<%= href %>" class="btn btn-primary btn-sm w-100">Ver detalles</a>
                            </div>
                          </div>
                        </div>
                      </div>
                    <% } %>
                  </div>
                <% } else { %>
                  <div class="text-center p-5 text-muted">Sin ediciones</div>
                <% } %>
              </div>
            </div>
          <% } %>

          <!-- Columna derecha: Ediciones (Asistente, solo su perfil) -->
          <% if ("A".equals(rol) && s) { %>
            <%
              @SuppressWarnings("unchecked")
              List<DtRegistro> registros = (List<DtRegistro>) request.getAttribute("Registros");
              @SuppressWarnings("unchecked")
              java.util.Map<String,String> eventoNombreMapA =
                  (java.util.Map<String,String>) request.getAttribute("EDICION_EVENTO");
              if (eventoNombreMapA == null) eventoNombreMapA = java.util.Collections.emptyMap();
            %>
            <div class="col-lg-6">
              <div class="card p-4 h-100">
                <h4 class="mb-3">Ediciones</h4>

                <% if (registros != null && !registros.isEmpty()) { %>
                  <div class="row g-3">
                    <%
                      for (DtRegistro r : registros) {
                        if (r == null || r.getEdicion() == null) continue;

                        // Tolerante a Edicion (modelo) o DtEdicion (DTO)
                        Object eo = r.getEdicion();
                        String nombreEd = "";
                        String imagenEdicion = ctx + "/media/img/default.png";

                        if (eo instanceof cliente.ws.sc.DtEdicion de) {
                          nombreEd = de.getNombre();
                          if (de.getImagenWebPath() != null && !de.getImagenWebPath().isBlank()) {
                            imagenEdicion = ctx + de.getImagenWebPath();
                          }
                        } else if (eo != null) {
                          try {
                            Object v = eo.getClass().getMethod("getNombre").invoke(eo);
                            if (v instanceof String s2 && !s2.isBlank()) nombreEd = s2;
                          } catch (Throwable ignore) {}
                          if (nombreEd == null || nombreEd.isBlank()) {
                            try {
                              Object v = eo.getClass().getMethod("getName").invoke(eo);
                              if (v instanceof String s2 && !s2.isBlank()) nombreEd = s2;
                            } catch (Throwable ignore) {}
                          }
                          try {
                            Object v = eo.getClass().getMethod("getImagenWebPath").invoke(eo);
                            if (v instanceof String s2 && !s2.isBlank()) imagenEdicion = ctx + s2;
                          } catch (Throwable ignore) {}
                        }

                        String nombreEvento = eventoNombreMapA.getOrDefault(nombreEd, "Evento no disponible");
                        String href = ctx + "/ediciones-consulta?evento="
                                      + URLEncoder.encode(nombreEvento, StandardCharsets.UTF_8)
                                      + "&edicion=" + URLEncoder.encode(nombreEd, StandardCharsets.UTF_8);
                    %>
                      <div class="col-12 col-sm-6 col-md-4">
                        <div class="card h-100">
                          <img src="<%= imagenEdicion %>" class="card-img-top" alt="<%= nombreEd %>">
                          <div class="card-body d-flex flex-column">
                            <h6 class="card-title mb-2"><%= nombreEd %></h6>
                            <p class="card-text text-muted small mb-3"><%= nombreEvento %></p>
                            <div class="mt-auto">
                              <a href="<%= href %>" class="btn btn-primary btn-sm w-100">Ver detalles</a>
                            </div>
                          </div>
                        </div>
                      </div>
                    <% } %>
                  </div>
                <% } else { %>
                  <div class="text-center p-5 text-muted">Sin registros</div>
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


