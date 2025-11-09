<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="cliente.ws.sc.DtCategoria" %>
<%@ page import="cliente.ws.sc.DtEdicion" %>
<%@ page import="cliente.ws.sc.DTevento"%>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder, java.nio.charset.StandardCharsets" %>

<%
  String ctx = request.getContextPath();
  boolean ES_ORG  = Boolean.TRUE.equals(request.getAttribute("ES_ORG"));
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Detalles del evento</title>
</head>

<body class="index-page">

  <!-- Header -->
<header id="header" class="header d-flex align-items-center fixed-top">
  		<jsp:include page="/WEB-INF/views/template/navbar.jsp" />
</header>

  <main class="main mt-5 pt-5">

    <%
      DTevento evento = (DTevento) request.getAttribute("EVENTO");
      if (evento != null) {
    	  String img = (evento != null && evento.getImg() != null && !evento.getImg().isBlank()) ? (ctx + evento.getImg()): (ctx + "/media/img/default.png");
    %>
      <section id="evento-detalles" class="section">
        <div class="container section-title text-center">
          <h2 id="eventTitle"><%= evento.getNombre() %></h2>
        </div>

        <div class="container">
          <div class="row justify-content-center align-items-center">
            <div class="col-lg-6 text-center">
              <div id="eventImage" class="event-image">
                <img src="<%=img%>" alt="<%= evento.getNombre() %>" class="img-fluid rounded shadow">
              </div>
            </div>

            <div class="col-lg-6">
              <div id="eventInfo" class="event-info">
                <p><%= evento.getDescripcion() %></p>
                <p><strong>Sigla:</strong> <%= evento.getSigla() %></p>
                <p><strong>Fecha de alta:</strong> <%= evento.getFAltaS() %></p>
                <h4>Categorías:</h4>
                <ul>
                   <%
                    List<DtCategoria> categorias = evento.getDtCategorias();
                    for (DtCategoria categoria : categorias) {
                  %>
                    <li><%= categoria.getNombre() %></li>
                  <% } %>
                </ul>
              </div>
            </div>
          </div>

          <!-- Ediciones del Evento -->
          <section id="eventEditions" class="event-editions mt-5">
            <div class="container">
              <div id="editionsList" class="row g-4 justify-content-center">

              
                
                <%
List<DtEdicion> ediciones = (List<DtEdicion>) request.getAttribute("LISTA_EDICIONES");
if (ediciones == null) { ediciones = java.util.Collections.emptyList(); }
%>

<% if (ediciones.isEmpty()) { %>
  <div class="alert alert-info text-center">Este evento no tiene ediciones visibles aún.</div>
<% } %>

<% for (DtEdicion edicion : ediciones) {
     String imge = (edicion != null && edicion.getImagenWebPath() != null && !edicion.getImagenWebPath().isBlank())
                   ? (ctx + edicion.getImagenWebPath())
                   : (ctx + "/media/img/default.png");
%>

                
                <div class="col-lg-4 col-md-6">
                  <div class="edition-card card shadow-sm h-100">
                    <img src="<%=imge%>" class="card-img-top" alt="Tecnología Punta del Este 2026" height="250px">
                    <div class="card-body">
                      <h5 class="card-title"><%= edicion.getNombre() %></h5>
                      <p class="card-text">📍 <%= edicion.getCiudad() %>, <%= edicion.getPais() %></p>
                      <p class="card-text"><small class="text-muted"><%= edicion.getFInicioS() %></small></p>
					  <a href="<%= ctx %>/ediciones?evento=<%= URLEncoder.encode(evento.getNombre(), StandardCharsets.UTF_8) %>&edicion=<%= URLEncoder.encode(edicion.getNombre(), StandardCharsets.UTF_8) %>"class="btn btn-primary">Ver detalles de la edición</a>
                    </div>
                  </div>
                </div>
                <% } %>

              </div>
            </div>
          </section>
        </div>
      </section>
    <% 
      } else { 
    %>
      <div class="alert alert-danger" role="alert">
        <strong>Error:</strong> No se pudo cargar el evento. El evento solicitado no está disponible.
      </div>
    <% 
      } 
    %>

   <% if (ES_ORG) { %>
  <div class="text-center mt-4 d-flex gap-2 justify-content-center">
    <a href="<%=ctx%>/ediciones-alta?evento=<%= URLEncoder.encode(evento.getNombre(), StandardCharsets.UTF_8) %>" 
       class="btn btn-primary">
       <i class="bi bi-plus-circle me-1"></i> Nueva Edición
    </a>

    <form id="finalizarEventoForm" action="<%= ctx %>/ConsultaEvento" method="post" class="d-inline">
      <input type="hidden" name="accion" value="finalizar">
      <input type="hidden" name="evento" value="<%= evento.getNombre() %>">
      <button type="submit" class="btn btn-danger">
        <i class="bi bi-exclamation-octagon me-1"></i> Finalizar evento
      </button>
    </form>
  </div>
<% } %>



  </main>

  <!-- Footer -->

<hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
<footer id="footer" class="footer position-relative light-background">
  <jsp:include page="/WEB-INF/views/template/footer.jsp" />
</footer>   
<script src="<%=ctx%>/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="media/js/main.js"></script>

</body>
</html>
