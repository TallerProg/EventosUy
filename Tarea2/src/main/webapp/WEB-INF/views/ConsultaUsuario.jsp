<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="ServidorCentral.logica.DTUsuarioListaConsulta"%>
<%@ page import="ServidorCentral.logica.Edicion"%>
<%@ page import="ServidorCentral.logica.Registro"%>
<%@ page import="ServidorCentral.logica.EstadoEdicion"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder, java.nio.charset.StandardCharsets" %>

<%
  String ctx = request.getContextPath();
  DTUsuarioListaConsulta u = (DTUsuarioListaConsulta) request.getAttribute("usuario");
  String rol = (String) request.getAttribute("rol");
  String img = (String) request.getAttribute("IMAGEN");

  // s == true solo si es su propio perfil
  Object sObj = request.getAttribute("esSuPerfil");
  boolean s = (sObj instanceof Boolean) ? ((Boolean) sObj).booleanValue()
                                        : "true".equalsIgnoreCase(String.valueOf(sObj));

  // IDs únicos carruseles
  String safeNick = (u != null && u.getNickname()!=null) ? u.getNickname().replaceAll("[^A-Za-z0-9_-]", "-") : "user";
  String carouselIdOrg  = "org-editions-"  + safeNick;
  String carouselIdAsis = "asis-editions-" + safeNick;
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>EventUY - Consulta Usuario</title>
  <style>
    /* Asegura que los controles no queden tapados */
    .carousel-control-prev, .carousel-control-next { z-index: 1050; }
  </style>
</head>
<body class="index-page">

  <!-- Header como lo tenías -->
  <header id="header" class="header d-flex align-items-center fixed-top">
    <jsp:include page="/WEB-INF/views/template/header.jsp" />
  </header>

  <main class="main">
    <section id="user-profile" class="speakers section">
      <div class="container section-title">
        <h2>Perfil de Usuario</h2>
      </div>

      <div class="container">
        <div class="row">
 		<% String imagen = (img != null && !img.isBlank()) ? (ctx + img) : (ctx + "/media/img/default.png"); %>
          <!-- Columna izquierda: Datos -->
          <div class="col-lg-6">
            <div class="card p-4 h-100">
              <div class="d-flex">
                <div class="me-3">
                  <img src="<%= imagen %>" alt="<%= u.getNickname() %>" class="img-fluid"
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

          <!-- Columna derecha: Carrusel ORGANIZADOR -->
          <% if ("O".equals(rol)) { %>
            <div class="col-lg-6">
              <div class="card p-4 h-100">
                <h4>Ediciones</h4>

                <div id="<%= carouselIdOrg %>" class="carousel slide" data-bs-ride="carousel" data-bs-interval="5000">
                  <div class="carousel-inner">
                    <%
                      @SuppressWarnings("unchecked")
                      List<Edicion> ediciones = (List<Edicion>) request.getAttribute("Ediciones");

                      java.util.List<Edicion> visibles = new java.util.ArrayList<>();
                      if (ediciones != null) {
                        for (Edicion e : ediciones) {
                          if (e == null) continue;
                          if (s) {
                            visibles.add(e);
                          } else {
                            String estado = (e.getEstado() != null) ? e.getEstado().name() : null;
                            if ("Aceptada".equals(String.valueOf(estado))) {
                              visibles.add(e);
                            }
                          }
                        }
                      }

                      if (!visibles.isEmpty()) {
                     
                        for (int i = 0; i < visibles.size(); i++) {
                          Edicion e = visibles.get(i);
                          String nombre = e.getNombre();
                          
                    %>
                      <div class="carousel-item <%= (i == 0) ? "active" : "" %>">
                        <div class="text-center">
                          <img src="<%= ctx %>/media/img/default.png"
                               class="d-block w-100 edition-carousel-img"
                               alt="<%= nombre %>">
                          <div class="mt-3">
                            <h5><%= nombre %></h5>
                            <a href="" class="btn btn-primary btn-sm">Ver detalles</a>
                          </div>
                        </div>
                      </div>
                    <%
                        }
                      } else {
                    %>
                      <div class="carousel-item active">
                        <div class="text-center p-5 text-muted">Sin ediciones</div>
                      </div>
                    <% } %>
                  </div>

                  <!-- Controles (apuntan al ID único). Además, data-target-id para fallback JS -->
                  <button class="carousel-control-prev" type="button"
                          data-bs-target="#<%= carouselIdOrg %>" data-bs-slide="prev"
                          data-target-id="<%= carouselIdOrg %>" data-dir="prev" aria-label="Anterior">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                  </button>
                  <button class="carousel-control-next" type="button"
                          data-bs-target="#<%= carouselIdOrg %>" data-bs-slide="next"
                          data-target-id="<%= carouselIdOrg %>" data-dir="next" aria-label="Siguiente">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                  </button>
                </div>
              </div>
            </div>
          <% } %>

          <!-- Columna derecha: Carrusel ASISTENTE (solo si es su propio perfil) -->
          <% if ("A".equals(rol) && s) { %>
            <%
              @SuppressWarnings("unchecked")
              List<Registro> registros = (List<Registro>) request.getAttribute("Registros");
            %>
            <div class="col-lg-6">
              <div class="card p-4 h-100">
                <h4>Ediciones</h4>

                <div id="<%= carouselIdAsis %>" class="carousel slide" data-bs-ride="carousel" data-bs-interval="5000">
                  <div class="carousel-inner">
                    <%
                      if (registros != null && !registros.isEmpty()) {
                        for (int i = 0; i < registros.size(); i++) {
                          Registro r = registros.get(i);
                          Edicion e = (r != null) ? r.getEdicion() : null;
                          if (e == null) continue;

                          String nombreEd = e.getNombre();
                          String href = ctx + "/consultaRegistroAsis?edicion=" +
                                        URLEncoder.encode(nombreEd, StandardCharsets.UTF_8.name());
                    %>
                      <div class="carousel-item <%= (i == 0) ? "active" : "" %>">
                        <div class="text-center">
                          <img src="<%= ctx %>/media/img/default.png"
                               class="d-block w-100 edition-carousel-img"
                               alt="<%= nombreEd %>">
                          <div class="mt-3">
                            <h5><%= nombreEd %></h5>
                            <a href="<%= href %>" class="btn btn-primary btn-sm">Ver detalles</a>
                          </div>
                        </div>
                      </div>
                    <%
                        }
                      } else {
                    %>
                      <div class="carousel-item active">
                        <div class="text-center p-5 text-muted">Sin registros</div>
                      </div>
                    <% } %>
                  </div>

                  <button class="carousel-control-prev" type="button"
                          data-bs-target="#<%= carouselIdAsis %>" data-bs-slide="prev"
                          data-target-id="<%= carouselIdAsis %>" data-dir="prev" aria-label="Anterior">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                  </button>
                  <button class="carousel-control-next" type="button"
                          data-bs-target="#<%= carouselIdAsis %>" data-bs-slide="next"
                          data-target-id="<%= carouselIdAsis %>" data-dir="next" aria-label="Siguiente">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                  </button>
                </div>
              </div>
            </div>
          <% } %>

        </div><!-- row -->
      </div><!-- container -->
    </section>
  </main>

  <footer id="footer" class="footer position-relative light-background">
    <jsp:include page="/WEB-INF/views/template/footer.jsp" />
  </footer>

<script src="media/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

</body>
</html>
