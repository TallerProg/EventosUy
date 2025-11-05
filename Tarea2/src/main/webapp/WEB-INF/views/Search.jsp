<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="cliente.ws.sc.DTeventoOedicion" %>

<%
  final String ctx = request.getContextPath();
  List<DTeventoOedicion> eventos_edicion = (List<DTeventoOedicion>) request.getAttribute("LISTA_EVENTOS_EDICIONES");
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Eventos</title>
</head>
<body class="index-page">

  <header id="header" class="header d-flex align-items-center fixed-top">
    <jsp:include page="/WEB-INF/views/template/header.jsp" />
  </header>

  <main class="main mt-5 pt-5">
    <section id="eventos" class="speakers section">
      <div class="container">
        <% if (eventos_edicion.isEmpty()) { %>
          <div class="alert alert-info text-center">No hay eventos disponibles por el momento.</div>
        <% } else { %>
          <div class="row g-4 justify-content-center">
            <% for (DTeventoOedicion ev : eventos_edicion) {
                 if (ev == null) continue;
                 String detalleHref ="";
                 boolean esEv="evento".equals(ev.getTipo());
                 String nombreEv = (ev.getNombreEvento() == null) ? "" : ev.getNombreEvento();
                 String encNombreEv = URLEncoder.encode(nombreEv, StandardCharsets.UTF_8.name());

                 if(esEv){
                     if (ev.isFinalizado()) continue;
                     String desc   = (ev.getDescripcion() == null) ? "" : ev.getDescripcion();
                     detalleHref = ctx + "/ConsultaEvento?evento=" + encNombreEv;

                 }else{
                     String nombreEd = (ev.getNombreEdicion() == null) ? "" : ev.getNombreEdicion();
                     String encNombreEd = URLEncoder.encode(nombreEd, StandardCharsets.UTF_8.name());
                     detalleHref = ctx + "/ediciones-consulta?evento=" + encNombreEv + "&edicion="+encNombreEd;

                 }

                 String rawImg = ev.getImg();
                 String img = (rawImg != null && !rawImg.isBlank())
                              ? (ctx + rawImg)
                              : (ctx + "/media/img/default.png");

            %>
              <!-- Columna flexible para igualar alturas -->
              <div class="col-lg-3 col-md-6 d-flex">
                <!-- Toda la card es el enlace -->
                <a href="<%= detalleHref %>"
                   class="speaker-card text-center h-100 d-flex flex-column position-relative w-100 text-reset text-decoration-none">
                    <%
                      String nombre = "";
                      String desc = "";
                      String tipo="";
                      if (esEv) { 
                          nombre = (ev.getNombreEvento() == null) ? "" : ev.getNombreEvento();
                          desc = (ev.getDescripcion() == null) ? "" : ev.getDescripcion();
                          tipo=ev.getTipo();
                      } else { 
                          nombre = (ev.getNombreEdicion() == null) ? "" : ev.getNombreEdicion();
                          tipo=ev.getTipo();
                      }
                    %>
                    <div class="speaker-image">
                      <img src="<%= img %>" alt="<%= nombre %>" class="img-fluid">
                    </div>

                    <div class="speaker-content d-flex flex-column">
                      <p class="speaker-title mb-1"><%= nombre %></p>
                      <% if (esEv) { %>
                          <p class="speaker-company mb-2 flex-grow-1"><%= desc %></p>
                      <% } %>
						  <p class="speaker-company mb-3 flex-grow-1" style="font-weight: bold; text-transform: uppercase;"><%= tipo %></p>
                    </div>
                </a>
              </div>
            <% } %>
          </div>
        <% } %>
      </div>
    </section>
  </main>

  <hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
  <footer id="footer" class="footer position-relative light-background">
    <jsp:include page="/WEB-INF/views/template/footer.jsp" />
  </footer>
<script>
    function ordenarEventos(criterio) {
      const cards = document.querySelectorAll('.col-lg-3.col-md-6.d-flex');
      const container = document.querySelector('.row.g-4.justify-content-center');
      
      const cardsArray = Array.from(cards);
      
      cardsArray.sort((a, b) => {
        const nombreA = a.querySelector('.speaker-title').textContent.trim().toLowerCase();
        const nombreB = b.querySelector('.speaker-title').textContent.trim().toLowerCase();
        
        switch(criterio) {
          case 'alphabetical-asc':
            return nombreA.localeCompare(nombreB);
          case 'alphabetical-desc':
            return nombreB.localeCompare(nombreA);
          default:
            return 0;
        }
      });
      
      container.innerHTML = '';
      
      cardsArray.forEach(card => {
        container.appendChild(card);
      });
    }
    
    document.addEventListener('DOMContentLoaded', function() {
      const section = document.getElementById('eventos');
      const container = document.querySelector('.container');
      
      const orderByHtml = `
        <div class="container mb-4">
          <label for="orderBy" class="form-label">Ordenar por:</label>
          <select id="orderBy" class="form-select" aria-label="Ordenar por" onchange="ordenarEventos(this.value)">
            <option value="default">Por defecto (Fecha)</option>
            <option value="alphabetical-asc">Alfabéticamente (A-Z)</option>
            <option value="alphabetical-desc">Alfabéticamente (Z-A)</option>
          </select>
        </div>
      `;
      
      container.insertAdjacentHTML('afterbegin', orderByHtml);
    });
  </script>
</body>
</html>