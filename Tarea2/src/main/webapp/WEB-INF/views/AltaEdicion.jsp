<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Alta de Edición de Evento</title>

  <!-- Bootstrap / Icons (CDN de respaldo) -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

  <!-- Vendors locales -->
  <link href="<%=ctx%>/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
  <link href="<%=ctx%>/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">

  <!-- Tu CSS -->
  <link href="<%=ctx%>/media/css/main.css" rel="stylesheet">

  <!-- Favicons -->
  <link href="<%=ctx%>/media/img/logoeuy.png" rel="icon">
  <link href="<%=ctx%>/media/img/logoeuy.png" rel="apple-touch-icon">
</head>

<body class="index-page">

  <!-- ===== Header ORIGINAL (rutas corregidas) ===== -->
  <header id="header" class="header d-flex align-items-center fixed-top">
    <div class="header-container container-fluid container-xl position-relative d-flex align-items-center justify-content-between">
      <nav id="navmenu" class="navmenu">
        <ul>
          <a href="indexOrg.html" class="logo d-flex align-items-center">
            <img src="<%=ctx%>/media/img/logoeuy.png" alt="">
          </a>
          <li><a href="usuariosOrgAsiME.html">Usuarios</a></li>
          <li><a href="listaEventosOrg.html">Eventos</a></li>
          <li><a href="listaInstitucionesOrg.html">Instituciones</a></li>
          <li class="nav-item search-item ms-auto">
            <form class="search-form" action="search.html" method="get">
              <input type="text" name="q" class="search-input" placeholder="Buscar eventos...">
              <button type="submit" class="search-btn"><i class="bi bi-search"></i></button>
            </form>
          </li>
          <li class="ms-auto d-flex align-items-center gap-2">
            <a href="perfil_US04.html" class="user-info text-decoration-none d-flex align-items-center gap-2">
              <img src="<%=ctx%>/media/img/usuarios/misEventos.jpeg" alt="">
              <span class="fw-semibold">MisEventos</span>
            </a>
            <a href="../indexVis.html" class="btn p-0 border-0 bg-transparent">
              <i class="login-icon bi bi-box-arrow-in-right fs-3" title="Cerrar Sesión"></i>
            </a>
          </li>
        </ul>
        <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
      </nav>
    </div>
  </header>

  <!-- ===== Cuerpo “lindo” tal cual lo querías ===== -->
  <main class="main mt-5 pt-5">
    <section class="container">
      <div class="mb-4 text-center">
        <h2 class="mb-1">Alta de Edición de Evento</h2>
        <p class="text-muted mb-0">Completá los datos para asociar una nueva edición a un evento.</p>
      </div>

      <!-- Alertas inline -->
      <%
        Object okMsg  = request.getAttribute("msgOk");
        Object errMsg = request.getAttribute("msgError");
        if (okMsg != null) {
      %>
        <div class="alert alert-success d-flex align-items-center" role="alert">
          <i class="bi bi-check-circle me-2"></i><div><%= okMsg %></div>
        </div>
      <% } %>
      <%
        if (errMsg != null) {
      %>
        <div class="alert alert-danger d-flex align-items-center" role="alert">
          <i class="bi bi-exclamation-triangle me-2"></i><div><%= errMsg %></div>
        </div>
      <% } %>

      <div class="card shadow-sm">
        <div class="card-body">
          <form action="<%= ctx %>/organizador/ediciones/alta"
                method="post" enctype="multipart/form-data" class="row g-3">

            <!-- Evento -->
            <div class="col-md-6">
              <label for="evento" class="form-label">Evento <span class="text-danger">*</span></label>
              <select id="evento" name="evento" class="form-select" required>
                <option value="">-- Seleccione un evento --</option>
                <%
                  Object evosObj = request.getAttribute("LISTA_EVENTOS");
                  if (evosObj instanceof String[]) {
                    String sel = (String) request.getAttribute("form_evento");
                    for (String ev : (String[]) evosObj) {
                      boolean selected = sel != null && sel.equals(ev);
                %>
                      <option value="<%=ev%>" <%= selected ? "selected" : "" %>><%=ev%></option>
                <%
                    }
                  }
                %>
              </select>
            </div>

            <!-- Nombre -->
            <div class="col-md-6">
              <label for="nombre" class="form-label">Nombre de edición <span class="text-danger">*</span></label>
              <input type="text" id="nombre" name="nombre" class="form-control"
                     value="<%= request.getAttribute("form_nombre")!=null?request.getAttribute("form_nombre"):"" %>"
                     placeholder="Ej: Tecnología Punta del Este 2026" required>
            </div>

            <!-- Sigla -->
            <div class="col-md-4">
              <label for="sigla" class="form-label">Sigla <span class="text-danger">*</span></label>
              <input type="text" id="sigla" name="sigla" class="form-control"
                     value="<%= request.getAttribute("form_sigla")!=null?request.getAttribute("form_sigla"):"" %>"
                     placeholder="Ej: CONFTECH26" required>
            </div>

            <!-- Ciudad -->
            <div class="col-md-4">
              <label for="ciudad" class="form-label">Ciudad <span class="text-danger">*</span></label>
              <input type="text" id="ciudad" name="ciudad" class="form-control"
                     value="<%= request.getAttribute("form_ciudad")!=null?request.getAttribute("form_ciudad"):"" %>"
                     placeholder="Montevideo" required>
            </div>

            <!-- País -->
            <div class="col-md-4">
              <label for="pais" class="form-label">País <span class="text-danger">*</span></label>
              <input type="text" id="pais" name="pais" class="form-control"
                     value="<%= request.getAttribute("form_pais")!=null?request.getAttribute("form_pais"):"" %>"
                     placeholder="Uruguay" required>
            </div>

            <!-- Fechas -->
            <div class="col-md-4">
              <label for="fechaIni" class="form-label">Fecha inicio <span class="text-danger">*</span></label>
              <input type="date" id="fechaIni" name="fechaIni" class="form-control"
                     value="<%= request.getAttribute("form_fechaIni")!=null?request.getAttribute("form_fechaIni"):"" %>" required>
            </div>

            <div class="col-md-4">
              <label for="fechaFin" class="form-label">Fecha fin <span class="text-danger">*</span></label>
              <input type="date" id="fechaFin" name="fechaFin" class="form-control"
                     value="<%= request.getAttribute("form_fechaFin")!=null?request.getAttribute("form_fechaFin"):"" %>" required>
            </div>

            <div class="col-md-4">
              <label for="fechaAlta" class="form-label">Fecha alta <span class="text-danger">*</span></label>
              <input type="date" id="fechaAlta" name="fechaAlta" class="form-control
                     " value="<%= request.getAttribute("form_fechaAlta")!=null?request.getAttribute("form_fechaAlta"):"" %>" required>
            </div>

            <!-- Imagen -->
            <div class="col-12">
              <label for="imagen" class="form-label">Imagen (opcional)</label>
              <input type="file" id="imagen" name="imagen" accept="image/*" class="form-control">
              <small class="text-muted">Formatos sugeridos: JPG/PNG · Máx 10MB</small>
              <div class="preview-imagenes mt-2" id="preview"></div>
            </div>

            <!-- Botones -->
            <div class="col-12 d-flex gap-2">
              <button type="submit" class="btn btn-primary">
                <i class="bi bi-check2-circle me-1"></i> Aceptar
              </button>
              <a href="<%=ctx%>/" class="btn btn-outline-secondary">Cancelar</a>
            </div>

          </form>
        </div>
      </div>
    </section>
  </main>

  <!-- ===== Footer ORIGINAL (rutas corregidas) ===== -->
  <hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
  <footer id="footer" class="footer position-relative light-background">
    <div class="container mt-4">
      <div class="row">
        <!-- Izquierda -->
        <div class="col-lg-6 col-md-6 mb-3">
          <h5>Sobre eventos.uy</h5>
          <p class="text-muted small">
            En <strong>eventos.uy</strong> nos apasiona conectar personas a través de conferencias, 
            talleres, hackatones y todo tipo de encuentros. Queremos hacer más fácil organizar, 
            descubrir y disfrutar eventos. <br>
            Si sos organizador, vas a encontrar herramientas para gestionar tus ediciones, 
            inscripciones y patrocinios. Y si sos asistente, podes
            consultar por tus eventos favoritos, registrarte en segundos y luego queda disfrutarlos!.
          </p>
        </div>
        <!-- Derecha -->
        <div class="col-lg-6 col-md-6 mb-3 text-lg-end text-md-end text-center">
          <h5>Contacto</h5>
          <p class="small">
            📧 <a href="mailto:contacto@eventos.uy">contacto@eventos.uy</a><br>
            ☎ +598 2400 0000
          </p>
          <div class="social-links d-flex justify-content-lg-end justify-content-md-end justify-content-center">
            <a href=""><i class="bi bi-twitter-x"></i></a>
            <a href=""><i class="bi bi-facebook"></i></a>
            <a href=""><i class="bi bi-instagram"></i></a>
            <a href=""><i class="bi bi-linkedin"></i></a>
            <a href="https://gitlab.fing.edu.uy/tprog/tpgr27"><i class="bi bi-gitlab"></i></a>
          </div>
        </div>
      </div>
      <div class="credits text-center mt-3">
        © 2025 <strong>eventos.uy</strong> · Taller de Programación FING G27
      </div>
    </div>
  </footer>

  <!-- JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="<%=ctx%>/vendor/swiper/swiper-bundle.min.js"></script>
  <script src="<%=ctx%>/vendor/glightbox/js/glightbox.min.js"></script>
  <script>
    // Preview simple de imagen
    const inp = document.getElementById('imagen');
    const prev = document.getElementById('preview');
    if (inp && prev) {
      inp.addEventListener('change', () => {
        prev.innerHTML = '';
        const f = inp.files && inp.files[0];
        if (!f) return;
        const url = URL.createObjectURL(f);
        const img = document.createElement('img');
        img.src = url; img.alt = 'Previsualización';
        img.className = 'img-fluid';
        prev.appendChild(img);
      });
    }
  </script>
</body>
</html>
