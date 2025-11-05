<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="cliente.ws.sc.DTevento" %>
<%@ page import="cliente.ws.sc.DtCategoria" %>

<%!
  // Sanitiza para HTML
  private static String esc(Object o) {
    if (o == null) return "";
    String s = String.valueOf(o);
    return s.replace("&","&amp;")
            .replace("<","&lt;")
            .replace(">","&gt;")
            .replace("\"","&quot;")
            .replace("'","&#x27;");
  }

  private static String orEmpty(String s) { return (s == null) ? "" : s; }

  // Ícono aproximado por nombre de categoría
  private static String iconFor(String catNombre) {
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
  boolean ES_ORG = Boolean.TRUE.equals(request.getAttribute("ES_ORG"));

  @SuppressWarnings("unchecked")
  List<DTevento> eventos = (List<DTevento>) request.getAttribute("LISTA_EVENTOS");
  if (eventos == null) eventos = new ArrayList<>();
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Eventos</title>
</head>
<body class="index-page">

  <header id="header" class="header d-flex align-items-center fixed-top">
    <!-- incluye el header común -->
    <jsp:include page="/WEB-INF/views/template/header.jsp" />
  </header>

  <main class="main mt-5 pt-5">
    <section id="eventos" class="speakers section">
      <div class="container section-title d-flex justify-content-between align-items-center">
        <h2>Eventos</h2>
        <% if (ES_ORG) { %>
          <a href="<%= ctx %>/alta-evento" class="btn btn-primary" title="Alta de Evento" aria-label="Alta de Evento">
            <i class="bi bi-plus-circle me-1"></i> Alta de Evento
          </a>
        <% } %>
      </div>

      <div class="container">
        <% if (eventos.isEmpty()) { %>
          <div class="alert alert-info text-center">No hay eventos disponibles por el momento.</div>
        <% } else { %>
          <div class="row g-4 justify-content-center">
            <% for (DTevento ev : eventos) {
                 if (ev == null) continue;
                 if (ev.isFinalizado()) continue;

                 String nombre = orEmpty(ev.getNombre());
                 String desc   = orEmpty(ev.getDescripcion());
                 String encNombre = URLEncoder.encode(nombre, StandardCharsets.UTF_8.name());
                 String detalleHref = ctx + "/ConsultaEvento?evento=" + encNombre;

                 String rawImg = ev.getImg();
                 String img = (rawImg != null && !rawImg.isBlank())
                              ? (ctx + rawImg)
                              : (ctx + "/media/img/default.png");

                 List<DtCategoria> cats = ev.getDtCategorias();
            %>
              <!-- Columna flexible para igualar alturas -->
              <div class="col-lg-3 col-md-6 d-flex">
                <!-- Toda la card es el enlace -->
                <a href="<%= detalleHref %>"
                   class="speaker-card text-center h-100 d-flex flex-column position-relative w-100 text-reset text-decoration-none">

                  <div class="speaker-image">
                    <img src="<%= esc(img) %>" alt="<%= esc(nombre) %>" class="img-fluid">
                  </div>

                  <div class="speaker-content d-flex flex-column">
                    <p class="speaker-title mb-1"><%= esc(nombre) %></p>
                    <p class="speaker-company mb-2 flex-grow-1"><%= esc(desc) %></p>

                    <div class="categories mb-3">
                      <% if (cats != null && !cats.isEmpty()) {
                           int shown = 0;
                           for (DtCategoria c : cats) {
                             if (c == null) continue;
                             String cn = orEmpty(c.getNombre());
                             if (cn.isEmpty()) continue;
                             String icon = iconFor(cn);
                      %>
                        <span class="me-1" title="<%= esc(cn) %>"><i class="bi <%= icon %>"></i></span>
                      <%   if (++shown >= 3) break;
                           }
                         } %>
                    </div>
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
</body>
</html>

