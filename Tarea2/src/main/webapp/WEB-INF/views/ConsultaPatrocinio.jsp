<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="ServidorCentral.logica.DTPatrocinio" %>

<%
  @SuppressWarnings("unchecked")
  List<DTPatrocinio> pats = (List<DTPatrocinio>) request.getAttribute("patrocinios");
  boolean hayPats = (pats != null && !pats.isEmpty());
  DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  String ctx = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <title>Consulta Patrocinio</title>

  <link href="<%=ctx%>/media/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link href="<%=ctx%>/media/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">
  <link href="<%=ctx%>/media/css/main.css" rel="stylesheet">
  <link href="<%=ctx%>/media/css/consultaPatrocini.css" rel="stylesheet">
</head>

<body>
  <main class="main container mt-5 pt-4">
    <div id="patroCarousel" class="carousel slide" data-bs-ride="carousel">
      <div class="carousel-inner">

        <% if (!hayPats) { %>
          <div class="alert alert-info">Aún no hay patrocinios para esta edición.</div>
        <% } else {
             for (int i = 0; i < pats.size(); i++) {
               DTPatrocinio p = pats.get(i);
               String active = (i == 0) ? " active" : "";
        %>

        <div class="carousel-item<%= active %>">
          <div class="row justify-content-center">
            <div class="col-lg-10 col-md-12">
              <div class="patro-card">
                <!-- Imagen (no hay imagen en el DT, uso una por defecto) -->
                <div class="patro-image">
                  <img src="<%=ctx%>/media/img/default.png" alt="Patrocinio <%= (p.getInstitucion()!=null? p.getInstitucion() : "N/D") %>">
                </div>

                <!-- Contenido -->
                <div class="patro-content">
                  <p class="patro-title"><%= (p.getInstitucion()!=null? p.getInstitucion() : "Institución N/D") %></p>
                  <p class="patro-company">
                    Patrocinio · <%= (p.getEdicion()!=null? p.getEdicion() : "Edición N/D") %>
                  </p>

                  <div class="detail-item">
                    <i class="bi bi-award"></i> Nivel:
                    <%= (p.getNivel()!=null? p.getNivel().name() : "N/D") %>
                  </div>

                  <div class="detail-item">
                    <i class="bi bi-cash"></i> Aporte:
                    <%= (p.getMonto()!=null? "$" + String.format(Locale.US, "%,.2f", p.getMonto()) : "—") %>
                  </div>

                  <div class="detail-item">
                    <i class="bi bi-calendar-event"></i>
                    <%= (p.getFInicio()!=null? p.getFInicio().format(fmt) : "Sin fecha") %>
                  </div>

                  <div class="detail-item">
                    <i class="bi bi-123"></i> Cantidad:
                    <%= p.getRegistroGratuito() %>
                  </div>

                  <div class="detail-item">
                    <i class="bi bi-code"></i> Código:
                    <%= (p.getCodigo()!=null? p.getCodigo() : "—") %>
                  </div>

                  <!-- Extra -->
                  <div class="patro-extra">
                    <div class="card card-body">
                      <p><i class="bi bi-building"></i> <strong>Institución:</strong>
                        <%= (p.getInstitucion()!=null? p.getInstitucion() : "N/D") %></p>
                      <p><i class="bi bi-calendar-check"></i> <strong>Edición:</strong>
                        <%= (p.getEdicion()!=null? p.getEdicion() : "N/D") %></p>
                      <p><i class="bi bi-ticket-perforated"></i> <strong>Tipo de Registro:</strong>
                        <%= (p.getTipoRegistro()!=null? p.getTipoRegistro() : "N/D") %></p>
                    </div>
                  </div>

                </div>
              </div>
            </div>
          </div>
        </div>

        <%   } 
           } %>

      </div>

      <% if (hayPats && pats.size() > 1) { %>
      <!-- Controles solo si hay más de un item -->
      <button class="carousel-control-prev" type="button" data-bs-target="#patroCarousel" data-bs-slide="prev">
        <span class="bi bi-chevron-left fs-1 text-dark"></span>
      </button>
      <button class="carousel-control-next" type="button" data-bs-target="#patroCarousel" data-bs-slide="next">
        <span class="bi bi-chevron-right fs-1 text-dark"></span>
      </button>
      <% } %>

    </div>
  </main>

  <!-- JS -->
  <script src="<%=ctx%>/media/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
