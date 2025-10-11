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
boolean s = (boolean) request.getAttribute("esSuPerfil");
%>
<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="/WEB-INF/views/template/head.jsp" />
<title>EventUY - Consulta Usuario</title>
</head>
<body class="index-page">
	<header id="header" class="header d-flex align-items-center fixed-top">
		<jsp:include page="/WEB-INF/views/template/header.jsp" />
	</header>

	<section id="user-profile" class="speakers section">
		<div class="container section-title">
			<h2>Perfil de Usuario</h2>
		</div>

		<div class="container">
			<div class="row">

				<!-- Datos -->
				<div class="col-lg-6">
					<div class="card p-4 h-100">
						<div class="d-flex">
							<div class="me-3">
								<img src="" alt="" class="img-fluid"
									style="max-width: 150px; border-radius: 8px;">
							</div>
							<div>
								<p class="fw-bold fs-4 mb-1"><%=u.getNickname()%>
								</p>
								<p class="text-muted mb-1">
									<%=u.getCorreo()%>
								</p>
								<p class="mb-1">
									Nombre:
									<%=u.getNombre()%>
								</p>
								<%
								if (rol.equals("A")) {
								%>
								<p class="mb-1">
									Apellido:
									<%=u.getApellido()%></p>
								<p class="mb-1">
									Fecha de Nacimiento:
									<%=u.getFNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%>
								</p>
								<%
								}
								%>
								<%
								if (rol.equals("O")) {
								%>
								<p class="mb-1">
									Descripción:
									<%=u.getDescripcion()%></p>
								<p class="mb-1">
									URL: <a href="<%=u.getUrl()%>" target="_blank"><%=u.getUrl()%></a>
								</p>
								<%
								}
								%>
								<!-- Organizador -->
								<% if (rol.equals("O")) { %>

  <%
    @SuppressWarnings("unchecked")
    List<Edicion> ediciones = (List<Edicion>) request.getAttribute("Ediciones");
    if (ediciones != null && !ediciones.isEmpty()) {
  %>

  <section id="hero-carousel" class="section mt-5 pt-4">
    <div class="container">
      <div id="eventCarousel" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
          <%
            int shown = 0;
            for (Edicion e : ediciones) {
              // Si s es true: mostrar todas. Si s es false: solo Aceptada.
              if (s || e.getEstado() == EstadoEdicion.Aceptada) {
          %>
              <div class="carousel-item <%= (shown == 0) ? "active" : "" %>">
                <div class="text-center">
                  <img
                    src="../img/ediciones/<%= e.getNombre() %>.png"
                    alt="<%= e.getNombre() %>"
                    class="d-block w-100 edition-carousel-img">
                  <div class="mt-3">
                    <h5><%= e.getNombre() %></h5>
                    <a
                      href="<%= ctx + "/detalleedicion?edicion=" + URLEncoder.encode(e.getNombre(), StandardCharsets.UTF_8.name()) %>"
                      class="btn btn-primary btn-sm">
                      Ver detalles
                    </a>
                  </div>
                </div>
              </div>
          <%
                shown++;
              }
            }
          %>
        </div>

        <!-- Controles -->
        <button class="carousel-control-prev" type="button" data-bs-target="#eventCarousel" data-bs-slide="prev">
          <span class="carousel-control-prev-icon"></span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#eventCarousel" data-bs-slide="next">
          <span class="carousel-control-next-icon"></span>
        </button>
      </div>
    </div>
  </section>

  <%
    } // ediciones != null
  %>

<% } %>

<%
if (rol.equals("A") && s){
%>
  @SuppressWarnings("unchecked")
  List<Registro> registros = (List<Registro>) request.getAttribute("Registros");
%>

<div class="col-lg-6">
  <div class="card p-4 h-100">
    <h4>Ediciones</h4>

    <div id="user-editions" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-inner">
        <%
          if (registros != null && !registros.isEmpty()) {
            int shown = 0;
            for (Registro r : registros) {
              Edicion e = (r != null) ? r.getEdicion() : null;
              if (e == null) continue;

              String nombreEd = e.getNombre();
              String href = ctx + "/consultaRegistroAsis?edicion=" +
                            URLEncoder.encode(nombreEd, StandardCharsets.UTF_8.name());
        %>
          <div class="carousel-item <%= (shown == 0) ? "active" : "" %>">
            <div class="text-center">
              <img src="" class="d-block w-100 edition-carousel-img" alt="<%= nombreEd %>">
              <div class="mt-3">
                <h5><%= nombreEd %></h5>
                <a href="<%= href %>" class="btn btn-primary btn-sm">Ver detalles</a>
              </div>
            </div>
          </div>
        <%
              shown++;
            } // for
          } // if
        %>
      </div>

      <button class="carousel-control-prev" type="button" data-bs-target="#user-editions" data-bs-slide="prev">
        <span class="carousel-control-prev-icon"></span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#user-editions" data-bs-slide="next">
        <span class="carousel-control-next-icon"></span>
      </button>
    </div>
  </div>
</div>

<% } %>





							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</section>

	<jsp:include page="/WEB-INF/views/template/footer.jsp" />
	<script
		src="<%=request.getContextPath()%>/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
