<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String ctx = request.getContextPath();
  ServidorCentral.logica.Organizador org =
  	(ServidorCentral.logica.Organizador) session.getAttribute("usuarioOrganizador");

  // mensajes
  Object okMsg  = request.getAttribute("msgOk");
  Object errMsg = request.getAttribute("msgError");

  // flash para PRG
  Object flash  = null;
  if (request.getSession(false) != null) {
    flash = request.getSession(false).getAttribute("flashOk");
    if (flash != null) request.getSession(false).removeAttribute("flashOk");
  }
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Alta de Tipo de Registro</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

  <link href="<%=ctx%>/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
  <link href="<%=ctx%>/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">

  <link href="<%=ctx%>/media/css/main.css" rel="stylesheet">

  <link href="<%=ctx%>/media/img/logoeuy.png" rel="icon">
  <link href="<%=ctx%>/media/img/logoeuy.png" rel="apple-touch-icon">
</head>

<body class="index-page">

  <header id="header" class="header d-flex align-items-center fixed-top">
  <div class="header-container container-fluid container-xl position-relative d-flex align-items-center justify-content-between">
    <nav id="navmenu" class="navmenu">
      <ul>
        <!-- Logo -->
        <a href="<%=ctx%>/" class="logo d-flex align-items-center">
          <img src="<%=ctx%>/media/img/logoeuy.png" alt="">
        </a>

        <!-- Menú común -->
        <li><a href="<%=ctx%>/usuariosOrgAsiME.html">Usuarios</a></li>
        <li><a href="<%=ctx%>/listaEventosOrg.html">Eventos</a></li>
        <li><a href="<%=ctx%>/listaInstitucionesOrg.html">Instituciones</a></li>

        <!-- Buscador -->
        <li class="nav-item search-item ms-auto">
          <form class="search-form" action="<%=ctx%>/search.html" method="get">
            <input type="text" name="q" class="search-input" placeholder="Buscar eventos...">
            <button type="submit" class="search-btn"><i class="bi bi-search"></i></button>
          </form>
        </li>

        <!-- Zona derecha: depende de sesión -->
        <% if (org != null) { %>
          <!-- MOSTRAR si hay organizador logueado -->
          <li class="ms-auto d-flex align-items-center gap-2">
            <a href="<%=ctx%>/perfil_US04.html" class="user-info text-decoration-none d-flex align-items-center gap-2">
              <img src="<%=ctx%>/media/img/usuarios/misEventos.jpeg" alt="">
              <span class="fw-semibold"><%= org.getNickname() != null ? org.getNickname() : "Organizador" %></span>
            </a>
            <!-- TODO: apunta tu acción real de logout -->
            <a href="<%=ctx%>/logout" class="btn p-0 border-0 bg-transparent">
              <i class="login-icon bi bi-box-arrow-right fs-3" title="Cerrar Sesión"></i>
            </a>
          </li>
        <% } else { %>
          <!-- MOSTRAR si NO hay sesión (visitante) -->
          <li class="ms-auto d-flex align-items-center gap-2">
            <!-- TODO: apunta tu login real -->
            <a href="<%=ctx%>/login" class="btn btn-outline-primary btn-sm">Iniciar sesión</a>
            <a href="<%=ctx%>/indexVis.html" class="btn p-0 border-0 bg-transparent">
              <i class="login-icon bi bi-box-arrow-in-right fs-3" title="Ir a inicio"></i>
            </a>
          </li>
        <% } %>
      </ul>
      <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
    </nav>
  </div>
</header>

  <!-- ====== MAIN ====== -->
  <main class="main mt-5 pt-5">
    <section class="container">
      <div class="mb-4 text-center">
        <h2 class="mb-1">Alta de Tipo de Registro</h2>
        <p class="text-muted mb-0">Elegí la edición y completá los datos del nuevo tipo de registro.</p>
      </div>

      <% if (flash != null) { %>
        <div class="alert alert-success d-flex align-items-center" role="alert">
          <i class="bi bi-check-circle me-2"></i><div><%= flash %></div>
        </div>
      <% } %>
      <% if (okMsg != null) { %>
        <div class="alert alert-success d-flex align-items-center" role="alert">
          <i class="bi bi-check-circle me-2"></i><div><%= okMsg %></div>
        </div>
      <% } %>
      <% if (errMsg != null) { %>
        <div class="alert alert-danger d-flex align-items-center" role="alert">
          <i class="bi bi-exclamation-triangle me-2"></i><div><%= errMsg %></div>
        </div>
      <% } %>

      <div class="card shadow-sm">
        <div class="card-body">
          <form action="<%= ctx %>/organizador/tipos-registro/alta" method="post" class="row g-3" novalidate>

            <!-- Edición (por nombre) -->
            <div class="col-md-6">
              <label for="edicion" class="form-label">Edición de evento <span class="text-danger">*</span></label>
              <select id="edicion" name="edicion" class="form-control" required>
                <%
                  Object edSel = request.getAttribute("form_edicion");
                  Object lista = request.getAttribute("LISTA_EDICIONES");
                  if (lista instanceof java.util.List<?> l) {
                    for (Object o : l) {
                      ServidorCentral.logica.Edicion e = (ServidorCentral.logica.Edicion) o;
                      String value = e.getNombre();   // value = nombre de la edición
                      String label = e.getNombre();
                      boolean selected = (edSel != null && value.equals(String.valueOf(edSel)));
                %>
                      <option value="<%=value%>" <%= selected ? "selected" : "" %>><%= label %></option>
                <%
                    }
                  }
                %>
              </select>
            </div>

            <!-- Nombre -->
            <div class="col-md-6">
              <label for="nombre" class="form-label">Nombre del tipo <span class="text-danger">*</span></label>
              <input type="text" id="nombre" name="nombre" class="form-control"
                     value="<%= request.getAttribute("form_nombre")!=null?request.getAttribute("form_nombre"):"" %>"
                     placeholder="Ej: Full / Estudiante / VIP" required>
            </div>

            <!-- Descripción -->
            <div class="col-12">
              <label for="descripcion" class="form-label">Descripción <span class="text-danger">*</span></label>
              <textarea id="descripcion" name="descripcion" class="form-control" rows="4" required><%= 
                request.getAttribute("form_descripcion")!=null?request.getAttribute("form_descripcion"):"" %></textarea>
            </div>

            <!-- Costo -->
            <div class="col-md-6">
              <label for="costo" class="form-label">Costo <span class="text-danger">*</span></label>
              <input type="number" id="costo" name="costo" class="form-control" step="0.01" min="0"
                     value="<%= request.getAttribute("form_costo")!=null?request.getAttribute("form_costo"):"" %>"
                     placeholder="0.00" required>
            </div>

            <!-- Cupo -->
            <div class="col-md-6">
              <label for="cupo" class="form-label">Cupo <span class="text-danger">*</span></label>
              <input type="number" id="cupo" name="cupo" class="form-control" min="0"
                     value="<%= request.getAttribute("form_cupo")!=null?request.getAttribute("form_cupo"):"" %>"
                     placeholder="0" required>
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

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
  <script src="<%=ctx%>/vendor/swiper/swiper-bundle.min.js"></script>
  <script src="<%=ctx%>/vendor/glightbox/js/glightbox.min.js"></script>
</body>
</html>
