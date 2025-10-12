<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="ServidorCentral.logica.DTEdicion" %>
<%@ page import="ServidorCentral.logica.Categoria" %>
<%@ page import="ServidorCentral.logica.ManejadorEvento" %>
<%@ page import="ServidorCentral.logica.Evento" %>
<%@ page import="ServidorCentral.logica.Edicion" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>EventUY </title>
</head>
<body class="index-page">

  <header id="header" class="header d-flex align-items-center fixed-top">
    <jsp:include page="./template/header.jsp" />
  </header>

  <main class="main mt-5 pt-5">
  <section id="eventEditions" class="event-editions mt-5">
            <div class="container">
              <div id="editionsList" class="row g-4 justify-content-center">

                <%
                String ctx = request.getContextPath(); 	
                List<DTEdicion> ediciones = (List<DTEdicion>) request.getAttribute("LISTA_EDICIONES");
                for (DTEdicion edicion : ediciones) {
                	String imge = (edicion != null && edicion.getImagenWebPath() != null && !edicion.getImagenWebPath().isBlank()) ? (ctx + edicion.getImagenWebPath()): (ctx + "/media/img/default.png");
                	String nomEvento = "";
                	ManejadorEvento manejador = ManejadorEvento.getInstancia();
                    for (Evento evento : manejador.listarEventos()) {
                        for (Edicion ed : evento.getEdiciones()) {
                            if (ed.getNombre().equalsIgnoreCase(edicion.getNombre())) {
                                 nomEvento = evento.getNombre(); // devuelve el evento que contiene esa edición
                            }
                        }
                    }
                %>
                <div class="col-lg-4 col-md-6">
                  <div class="edition-card card shadow-sm h-100">
                    <img src="<%=imge%>" class="card-img-top" alt="Tecnología Punta del Este 2026" height="250px">
                    <div class="card-body">
                      <h5 class="card-title"><%= edicion.getNombre() %></h5>
                      <p class="card-text">📍 <%= edicion.getCiudad() %>, <%= edicion.getPais() %></p>
                      <p class="card-text"><small class="text-muted"><%= edicion.getfInicio() %></small></p>
					  <a href="<%= ctx %>/ediciones-consulta?evento=<%= URLEncoder.encode( nomEvento, StandardCharsets.UTF_8) %>&edicion=<%= URLEncoder.encode(edicion.getNombre(), StandardCharsets.UTF_8) %>"class="btn btn-primary">Ver detalles de la edición</a>
                    </div>
                  </div>
                </div>
                <% } %>

              </div>
            </div>
          </section>
  </main>

<hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
<footer id="footer" class="footer position-relative light-background">
  <jsp:include page="/WEB-INF/views/template/footer.jsp" />
</footer></body>
</html>
