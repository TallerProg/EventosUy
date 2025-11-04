<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cliente.ws.sc.DtUsuarioListaConsulta"%>
<%@ page import="cliente.ws.sc.DtEdicion"%>
<%@ page import="cliente.ws.sc.DtRegistro"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder, java.nio.charset.StandardCharsets" %>

<%
  String ctx = request.getContextPath();
  DtUsuarioListaConsulta u = (DtUsuarioListaConsulta) request.getAttribute("usuario");
  String rol = (String) request.getAttribute("rol");
  String img = (String) request.getAttribute("IMAGEN");

  // s == true solo si es su propio perfil
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
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Consulta Usuario</title>
</head>
<body class="index-page">

  <!-- Header -->
  <header id="header" class="header d-flex align-items-center fixed-top">
    <jsp:include page="/WEB-INF/views/template/header.jsp" />
  </header>

  <main class="main">
    <section id="user-profile" class="speakers section">
      <div class="container section-title">
        <h2>Perfil de Usuario</h2>
      </div>

      <div class="container">
        <div class="row g-4">
          <!-- Columna izquierda: Datos -->
          <div class="col-lg-6">
            <div class="card p-4 h-100">
              <div class="d-flex">
                <div class="me-3">
                  <img src="<%= imagenPerfil %>" alt="<%= u.getNickname() %>" class="img-fluid"
                       style="max-width:150px; border-radius:8px;">
                </div>
                <div>
                  <p class="fw-bold fs-4 mb-1"><%= u.getNickname() %></p>
                  <p class="text-muted mb-1"><%= u.getCorreo() %></p>

                  <p class="mb-1">Nombre: <%= u.getNombre() %></p>

                  <% if ("A".equals(rol)) { %>
                    <p class="mb-1">Apellido: <%= u.getApellido() %></p>
                    <p class="mb-1">
                      Fecha de Nacimiento:
                      <%= u.getFNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %>
                    </p>
                  <% } %>

                  <% if ("O".equals(rol)) { %>
                    <p class="mb-1">Descripción: <%= u.getDescripcion() %></p>
                    <p class="mb-1">
                      URL: <a href="<%= u.getUrl() %>" target="_blank"><%= u.getUrl() %></a>
                    </p>
                  <% } %>
                </div>
              </div>
            </div>
          </div>

          <!-- Columna derecha: Ediciones como CARDS (Organizador) -->
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
                        // Si es su propio perfil, ve todas
                        visibles.add(e);
                      } else {
                        // Si es público, solo Aceptadas (comparación por nombre para no depender del enum real)
                        String estado = (e.getEstado() != null) ? e.getEstado() : null;
                        if ("Aceptada".equals(String.valueOf(estado))) {
                          visibles.add(e);
                        }
                      }
                    }
                  }
                %>

                <% if (visibles != null && !visibles.isEmpty()) { %>
                  <div class="row g-3">
                    <%
                      for (DtEdicion e : visibles) {
                        String nombre = e.getNombre();
                        String nombreEvento = (e.getEvento() != null) ? e.getEvento().getNombre() : "Evento no disponible";
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

          <!-- Columna derecha: Ediciones como CARDS (Asistente, solo su perfil) -->
          <% if ("A".equals(rol) && s) { %>
            <%
              @SuppressWarnings("unchecked")
              List<DtRegistro> registros = (List<DtRegistro>) request.getAttribute("Registros");
            %>
            <div class="col-lg-6">
              <div class="card p-4 h-100">
                <h4 class="mb-3">Ediciones</h4>

                <% if (registros != null && !registros.isEmpty()) { %>
                  <div class="row g-3">
                    <%
                      for (DtRegistro r : registros) {
                        if (r == null || r.getEdicion() == null) continue;
                        DtEdicion e = r.getEdicion().getDTEdicion();
                        String nombreEd = e.getNombre();
                        String nombreEvento = (e.getEvento() != null) ? e.getEvento().getNombre() : "Evento no disponible";
                        String imagenEdicion = (e.getImagenWebPath() != null && !e.getImagenWebPath().isBlank())
                                              ? (ctx + e.getImagenWebPath())
                                              : (ctx + "/media/img/default.png");
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
