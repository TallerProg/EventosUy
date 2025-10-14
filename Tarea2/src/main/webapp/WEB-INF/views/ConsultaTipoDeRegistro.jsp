<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="servidorcentral.logica.Evento" %>
<%@ page import="servidorcentral.logica.DTEdicion" %>
<%@ page import="servidorcentral.logica.DTTipoRegistro" %>
<%@ page import="servidorcentral.logica.Organizador" %>
<%
  String ctx = request.getContextPath();

  // Datos enviados por el servlet
  Evento evento               = (Evento) request.getAttribute("EVENTO");
  DTEdicion edicion           = (DTEdicion) request.getAttribute("EDICION");
  DTTipoRegistro tipoRegistro = (DTTipoRegistro) request.getAttribute("TIPO_REGISTRO");

  Object errMsg = request.getAttribute("msgError");
  String img = (edicion != null && edicion.getImagenWebPath() != null && !edicion.getImagenWebPath().isBlank()) ? (ctx + edicion.getImagenWebPath()): (ctx + "/media/img/default.png");

%>
<!DOCTYPE html>
<html lang="es">
<head>
   <jsp:include page="template/head.jsp" />
  <title>Consulta Tipo de Registro</title>

</head>

<body class="index-page">

  <!-- Header -->
  <header id="header" class="header d-flex align-items-center fixed-top">
  		<jsp:include page="template/header.jsp" />
  </header>

  <!-- Main -->
  <main class="main mt-5 pt-5">
    <section id="consulta-registro" class="section">
      <div class="container">

        <div class="section-title text-center">
          <h2>Consulta Tipo de Registro</h2>
        </div>

        <% if (errMsg != null) { %>
          <div class="alert alert-danger d-flex align-items-center" role="alert">
            <i class="bi bi-exclamation-triangle me-2"></i>
            <div><%= errMsg %></div>
          </div>
        <% } %>

        <% if (evento != null && edicion != null && tipoRegistro != null) { %>

          <!-- Imagen (si no tenés imagen en DT uso default) -->
          <div class="text-center mb-4">
            <img src="<%=img%>"
                 alt="<%= edicion.getNombre() %>"
                 class="img-fluid rounded shadow-sm"
                 style="max-width: 450px;">
          </div>

          <!-- Datos -->
          <div class="card shadow-sm p-4">
            <h5 class="text-muted mb-2">Evento</h5>
            <h4 class="mb-3"><i class="bi bi-calendar-event"></i> <%= evento.getNombre() %></h4>

            <h5 class="text-muted mb-2">Edición</h5>
            <p class="mb-3">
              <strong><%= edicion.getNombre() %></strong>
              — <%= edicion.getCiudad() %>, <%= edicion.getPais() %>
              · <small><%= edicion.getfInicio() %></small>
            </p>

            <h5 class="mb-3"><i class="bi bi-ticket-perforated"></i> Tipo de Registro</h5>
            <p><strong>Tipo:</strong> <%= tipoRegistro.getNombre() %></p>
            <p><strong>Descripción:</strong> <%= tipoRegistro.getDescripcion() %></p>
            <p><strong>Costo:</strong> $<%= tipoRegistro.getCosto() %></p>
            <p><strong>Cupo:</strong> <%= tipoRegistro.getCupo() %></p>
          </div>

        <% } else { %>
  			<div class="alert alert-warning">No hay datos para mostrar.</div>
		<% } %>
      </div>
    </section>
  </main>

  <!-- Footer -->
  <hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
  <footer id="footer" class="footer position-relative light-background">
  	<jsp:include page="template/footer.jsp" />
  </footer>

  <!-- JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
