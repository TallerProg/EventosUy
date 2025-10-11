<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="ServidorCentral.logica.Evento" %>
<%@ page import="ServidorCentral.logica.Categoria" %>

<%!
  // ==== Helpers declarados (válidos en JSP) ====

  // Devuelve s o cadena vacía si es null
  private String orEmpty(String s) {
    return (s == null) ? "" : s;
  }

  // Normaliza nombre de categoría a un ícono de Bootstrap Icons
  private String iconFor(String catNombre) {
    if (catNombre == null) return "bi-bookmark";
    String n = catNombre.toLowerCase();
    if (n.contains("tecno")) return "bi-cpu";
    if (n.contains("inno"))  return "bi-lightbulb";
    if (n.contains("deport"))return "bi-trophy";
    if (n.contains("salud")) return "bi-heart-pulse";
    if (n.contains("mús") || n.contains("mus")) return "bi-music-note-beamed";
    if (n.contains("cultura")) return "bi-brush";
    if (n.contains("negoc") || n.contains("empresa")) return "bi-briefcase";
    if (n.contains("investig")) return "bi-beaker";
    if (n.contains("agro")) return "bi-tree";
    if (n.contains("entreten")) return "bi-emoji-smile";
    if (n.contains("moda")) return "bi-bag";
    if (n.contains("literat") || n.contains("libro")) return "bi-book";
    return "bi-bookmark";
  }
%>

<%
  final String ctx = request.getContextPath();

  @SuppressWarnings("unchecked")
  List<Evento> eventos = (List<Evento>) request.getAttribute("LISTA_EVENTOS");
  if (eventos == null) eventos = new ArrayList<>();
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>EventUY - Eventos</title>
</head>
<body class="index-page">

  <header id="header" class="header d-flex align-items-center fixed-top">
    <jsp:include page="./template/header.jsp" />
  </header>

  <main class="main">
    <section id="eventos" class="speakers section">
      <div class="container section-title d-flex justify-content-between align-items-center">
        <h2>Eventos</h2>
      </div>

      <div class="container">
        <div class="row g-4 justify-content-center">

          <%
            if (!eventos.isEmpty()) {
              for (Evento ev : eventos) {
                String nombre  = (ev != null) ? orEmpty(ev.getNombre()) : "";
                String desc    = (ev != null) ? orEmpty(ev.getDescripcion()) : "";

                String encNombre = URLEncoder.encode(nombre, StandardCharsets.UTF_8.name());
                // Ajustá endpoint de detalle si corresponde
                String detalleHref = ctx + "/ConsultaEvento?evento=" + encNombre;

                List<Categoria> cats = (ev != null) ? ev.getCategoria() : null;
          %>

          <!-- Card uniforme -->
          <div class="col-lg-3 col-md-6 d-flex">
            <div class="speaker-card text-center h-100 d-flex flex-column position-relative w-100">
              <div class="speaker-image">
                <img src="<%= ctx %>/media/img/default.png" alt="<%= nombre %>" class="img-fluid">
              </div>

              <div class="speaker-content d-flex flex-column">
                <p class="speaker-title mb-1"><%= nombre %></p>
                <p class="speaker-company mb-2 flex-grow-1"><%= desc %></p>

                <div class="categories mb-3">
                  <%
                    if (cats != null && !cats.isEmpty()) {
                      int shown = 0;
                      for (Categoria c : cats) {
                        if (c == null) continue;
                        String cn = orEmpty(c.getNombre());
                        String icon = iconFor(cn);
                  %>
                        <span class="me-1" title="<%= cn %>"><i class="bi <%= icon %>"></i></span>
                  <%
                        shown++;
                        if (shown >= 3) break; // máximo 3 íconos
                      }
                    }
                  %>
                </div>

                <!-- Link principal: hace clickeable toda la card -->
                <a href="<%= detalleHref %>" class="stretched-link" aria-label="Ver <%= nombre %>"></a>
              </div>
            </div>
          </div>

          <%
              } // for
            } else {
          %>
            <div class="col-12">
              <div class="alert alert-info text-center">
                No hay eventos disponibles por el momento.
              </div>
            </div>
          <%
            }
          %>

        </div>
      </div>
    </section>
  </main>

  <jsp:include page="/WEB-INF/views/template/footer.jsp" />
</body>
</html>
