<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="ServidorCentral.logica.Evento"%>
<%@ page import="ServidorCentral.logica.Categoria"%>
<%@ page import="java.util.List"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>

<%
  String ctx = request.getContextPath();
  Evento[] eventos = (Evento[]) request.getAttribute("LISTA_EVENTOS");
  Categoria[] categorias = (Categoria[]) request.getAttribute("LISTA_CATEGORIAS");
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="../template/head.jsp" />
  <title>EventUY — Inicio</title>

  <!-- Estilos puntuales para asegurar el botón al fondo de la card -->
  <style>
    .speaker-card { display: flex; flex-direction: column; height: 100%; }
    .speaker-content { display: flex; flex-direction: column; flex: 1; }
    .speaker-card .btn-ver-ediciones { margin-top: auto; }
  </style>
</head>

<body class="index-page">

  <header id="header" class="header d-flex align-items-center fixed-top">
    <jsp:include page="../template/header.jsp" />
  </header>

  <main class="main">

    <!-- HERO / CARRUSEL -->
    <section id="hero-carousel" class="section mt-5 pt-4">
      <div class="container">
        <%
          if (eventos != null && eventos.length > 0) {
        %>
        <div id="eventCarousel" class="carousel slide" data-bs-ride="carousel">
          <!-- Indicadores -->
          <div class="carousel-indicators">
            <%
              for (int i = 0; i < eventos.length; i++) {
            %>
              <button type="button"
                      data-bs-target="#eventCarousel"
                      data-bs-slide-to="<%= i %>"
                      class="<%= (i == 0 ? "active" : "") %>"></button>
            <%
              }
            %>
          </div>

          <!-- Slides -->
          <div class="carousel-inner">
            <%
              for (int i = 0; i < eventos.length; i++) {
                Evento e = eventos[i];
                String encNombre = URLEncoder.encode(e.getNombre(), StandardCharsets.UTF_8.name());
                String detalleHref = ctx + "/ConsultaEvento?evento=" + encNombre; // igual que ListarEventos
            %>
              <div class="carousel-item <%= (i == 0) ? "active" : "" %>">
                <img src="<%= (e != null && e.getImg() != null && !e.getImg().isBlank()) ? (ctx + e.getImg()): (ctx + "/media/img/default.png") %>"
                     class="d-block w-100 carousel-img"
                     alt="<%= e.getNombre() %>">
                <div class="carousel-caption d-none d-md-block">
                  <h5 class="carousel-title"><%= e.getNombre() %></h5>
                  <p class="carousel-text"><%= e.getDescripcion() %></p>
                  <a href="<%= detalleHref %>" class="btn btn-primary btn-lg me-3">Ver ediciones</a>
                </div>
              </div>
            <%
              } 
            %>
          </div>

          <!-- Controles -->
          <button class="carousel-control-prev" type="button" data-bs-target="#eventCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Anterior</span>
          </button>
          <button class="carousel-control-next" type="button" data-bs-target="#eventCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Siguiente</span>
          </button>
        </div>
        <%
          } else {
        %>
          <div class="text-center py-5">
            <h3 class="mb-3">Todavía no hay eventos para mostrar</h3>
            <p class="text-muted">Cuando se carguen eventos, aparecerán aquí.</p>
          </div>
        <%
          }
        %>
      </div>
    </section>

    <!-- categorias -->
    <section id="categories-carousel" class="py-3">
      <div class="container">
        <div id="categoriesCarousel" class="carousel slide" data-bs-interval="false">
          <div class="carousel-inner">
            <div class="carousel-item active">
              <div class="d-flex flex-wrap gap-2 justify-content-center">
              	<%
              		if (categorias != null && categorias.length > 0){
              			for (Categoria c : categorias) {
              				String nomCat = c.getNombre();
              				// normalizar igual que en data-categories de las cards
              				String normCat = nomCat.toLowerCase()
              				  .replace("á","a").replace("é","e").replace("í","i")
              				  .replace("ó","o").replace("ú","u");
              	%>
                <button type="button"
                        class="btn btn-outline-primary category-btn"
                        data-category="<%= normCat %>">
                  <% if (nomCat.contains("Tecnología")) { %><i class="bi bi-cpu" title="Tecnología"></i><% } %>
                  <% if (nomCat.contains("Innovación")) { %><i class="bi bi-lightbulb" title="Innovación"></i><% } %>
                  <% if (nomCat.contains("Literatura")) { %><i class="bi bi-book" title="Literatura"></i><% } %>
                  <% if (nomCat.contains("Cultura")) { %><i class="bi bi-bank" title="Cultura"></i><% } %>
                  <% if (nomCat.contains("Musica")) { %><i class="bi bi-music-note-beamed" title="Música"></i><% } %>
                  <% if (nomCat.contains("Deporte")) { %><i class="bi bi-trophy" title="Deporte"></i><% } %>
                  <% if (nomCat.contains("Salud")) { %><i class="bi bi-heart-pulse" title="Salud"></i><% } %>
                  <% if (nomCat.contains("Entretenimiento")) { %><i class="bi bi-film" title="Entretenimiento"></i><% } %>
                  <% if (nomCat.contains("Agro")) { %><i class="bi bi-tree" title="Agro"></i><% } %>
                  <% if (nomCat.contains("Negocios")) { %><i class="bi bi-briefcase" title="Negocios"></i><% } %>
                  <% if (nomCat.contains("Moda")) { %><i class="bi bi-scissors" title="Moda"></i><% } %>
                  <% if (nomCat.contains("Investigación")) { %><i class="bi bi-flask" title="Investigación"></i><% } %>
                  <%= nomCat %>
                </button>
                <%
                      }
                    } else {
                %>
                <div class="col-12">
	              <div class="alert alert-info text-center" role="alert">
	                No hay categorias para listar.
	              </div>
	            </div>
	            <%
                    }
	            %>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- grid de eventos (maximo 4) -->
    <section id="speakers" class="speakers section">
      <div class="container section-title">
        <h2>Eventos</h2>
      </div>

      <div class="container">
        <div class="row g-4 justify-content-center">
          <%
            if (eventos != null && eventos.length > 0) {
              int max = Math.min(4, eventos.length);
              for (int i = 0; i < max; i++) {
                Evento e = eventos[i];

                String encNombre = URLEncoder.encode(e.getNombre(), StandardCharsets.UTF_8.name());
                String detalleHref = ctx + "/ConsultaEvento?evento=" + encNombre;

                List<Categoria> categoriaseve = e.getCategoria();
                StringBuilder catBuilder = new StringBuilder();
                for (int j = 0; j < categoriaseve.size(); j++) {
                  String nombre = categoriaseve.get(j).getNombre()
                    .toLowerCase()
                    .replace("á","a").replace("é","e").replace("í","i")
                    .replace("ó","o").replace("ú","u");
                  catBuilder.append(nombre);
                  if (j < categoriaseve.size() - 1) catBuilder.append(",");
                }
                String catString = catBuilder.toString();
          %>

          <div class="col-lg-3 col-md-6 d-flex">
            <div class="speaker-card h-100 d-flex flex-column w-100 position-relative"
                 data-categories="<%= catString %>">
              <div class="speaker-image">
                <img src="<%= (e != null && e.getImg() != null && !e.getImg().isBlank()) ? (ctx + e.getImg()): (ctx + "/media/img/default.png") %>" class="img-fluid" alt="<%= e.getNombre() %>">
              </div>

              <div class="speaker-content d-flex flex-column">
                <p class="speaker-title mb-1"><%= e.getNombre() %></p>
                <p class="speaker-company mb-2 flex-grow-1"><%= e.getDescripcion() %></p>

                <div class="categories mb-3">
                  <% if (catString.contains("tecnologia")) { %><i class="bi bi-cpu" title="Tecnología"></i><% } %>
                  <% if (catString.contains("innovacion")) { %><i class="bi bi-lightbulb" title="Innovación"></i><% } %>
                  <% if (catString.contains("literatura")) { %><i class="bi bi-book" title="Literatura"></i><% } %>
                  <% if (catString.contains("cultura")) { %><i class="bi bi-bank" title="Cultura"></i><% } %>
                  <% if (catString.contains("musica")) { %><i class="bi bi-music-note-beamed" title="Música"></i><% } %>
                  <% if (catString.contains("deporte")) { %><i class="bi bi-trophy" title="Deporte"></i><% } %>
                  <% if (catString.contains("salud")) { %><i class="bi bi-heart-pulse" title="Salud"></i><% } %>
                  <% if (catString.contains("entretenimiento")) { %><i class="bi bi-film" title="Entretenimiento"></i><% } %>
                  <% if (catString.contains("agro")) { %><i class="bi bi-tree" title="Agro"></i><% } %>
                  <% if (catString.contains("negocios")) { %><i class="bi bi-briefcase" title="Negocios"></i><% } %>
                  <% if (catString.contains("moda")) { %><i class="bi bi-scissors" title="Moda"></i><% } %>
                  <% if (catString.contains("investigacion")) { %><i class="bi bi-flask" title="Investigación"></i><% } %>
                </div>

                <!-- Boton fijo abajo -->
                <a href="<%= detalleHref %>" class="btn btn-primary mt-auto btn-ver-ediciones">Ver ediciones</a>
                <a href="<%= detalleHref %>" class="stretched-link" aria-label="Ver ediciones de <%= e.getNombre() %>"></a>
              </div>
            </div>
          </div>

          <%
              } 
            } else {
          %>
            <div class="col-12">
              <div class="alert alert-info text-center" role="alert">
                No hay eventos para listar.
              </div>
            </div>
          <%
            } 
          %>
        </div>

        <!-- Mensaje de "sin resultados" para el filtro -->
        <div id="no-results-msg" class="alert alert-warning text-center mt-3 d-none">
          No hay eventos que coincidan con el filtro.
        </div>
      </div>
    </section>
  </main>

  <hr class="mt-5 mb-4" style="border:0;height:3px;background:#bbb;border-radius:2px;">
  <footer id="footer" class="footer position-relative light-background">
    <jsp:include page="../template/footer.jsp" />
  </footer>

  <a href="#" id="scroll-top" class="scroll-top d-flex align-items-center justify-content-center">
    <i class="bi bi-arrow-up-short"></i>
  </a>

  <div id="preloader"></div>

  <script src="<%= ctx %>/media/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="<%= ctx %>/media/js/main.js"></script>

  <!-- Filtro por categorías -->
  <script>
    (function() {
      const buttons = document.querySelectorAll('.category-btn');
      // cada card vive dentro de una col; nos conviene ocultar la col
      const cols = document.querySelectorAll('#speakers .row > [class*="col-"]');
      const cards = document.querySelectorAll('#speakers [data-categories]');
      const noRes = document.getElementById('no-results-msg');

      function getCol(el){
        return el.closest('[class*="col-"]') || el.parentElement;
      }

      function applyFilter(activeCat) {
        let visibles = 0;
        cards.forEach(card => {
          const cats = (card.getAttribute('data-categories') || '')
                        .split(',')
                        .map(s => s.trim())
                        .filter(Boolean);
          const show = !activeCat || cats.includes(activeCat);
          const col = getCol(card);
          if (col) col.classList.toggle('d-none', !show);
          if (show) visibles++;
        });
        if (noRes) noRes.classList.toggle('d-none', visibles > 0);
      }

      buttons.forEach(btn => {
        btn.addEventListener('click', () => {
          const wasActive = btn.classList.contains('active');
          buttons.forEach(b => b.classList.remove('active'));
          if (!wasActive) btn.classList.add('active');
          const activeBtn = document.querySelector('.category-btn.active');
          const activeCat = activeBtn ? activeBtn.getAttribute('data-category') : null;
          applyFilter(activeCat);
        });
      });

      // Estado inicial: sin filtro
      applyFilter(null);
    })();
  </script>
</body>
</html>
