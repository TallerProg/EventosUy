<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="ServidorCentral.logica.Evento" %>
<%@ page import="ServidorCentral.logica.DTEdicion" %>
<%@ page import="ServidorCentral.logica.DTTipoRegistro" %>
<%@ page import="ServidorCentral.logica.Organizador" %>
<%
  String ctx = request.getContextPath();

  // Usuario logueado (si aplica, para header dinámico simple)
  Organizador org = (Organizador) session.getAttribute("usuarioOrganizador");

  // Datos enviados por el servlet
  Evento evento               = (Evento) request.getAttribute("EVENTO");
  DTEdicion edicion           = (DTEdicion) request.getAttribute("EDICION");
  DTTipoRegistro tipoRegistro = (DTTipoRegistro) request.getAttribute("TIPO_REGISTRO");

  Object errMsg = request.getAttribute("msgError");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta content="width=device-width, initial-scale=1.0" name="viewport">
  <title>Consulta Tipo de Registro - EventUY</title>

  <!-- Bootstrap (CDN) -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

  <!-- Main CSS de tu web -->
  <link href="<%=ctx%>/media/css/main.css" rel="stylesheet">

  <!-- Favicons -->
  <link href="<%=ctx%>/media/img/logoeuy.png" rel="icon">
  <link href="<%=ctx%>/media/img/logoeuy.png" rel="apple-touch-icon">
</head>

<body class="index-page">

  <!-- ===== Header (igual estilo que otros JSPs del proyecto) ===== -->
  <header id="header" class="header d-flex align-items-center fixed-top">
    <div class="header-container container-fluid container-xl position-relative d-flex align-items-center justify-content-between">
      <nav id="navmenu" class="navmenu">
        <ul>
          <a href="<%=ctx%>/" class="logo d-flex align-items-center">
            <img src="<%=ctx%>/media/img/logoeuy.png" alt="">
          </a>
          <li><a href="<%=ctx%>/usuariosOrgAsiME.html">Usuarios</a></li>
          <li><a href="<%=ctx%>/listaEventosOrg.html">Eventos</a></li>
          <li><a href="<%=ctx%>/listaInstitucionesOrg.html">Instituciones</a></li>

          <li class="nav-item search-item ms-auto">
            <form class="search-form" action="<%=ctx%>/search.html" method="get">
              <input type="text" name="q" class="search-input" placeholder="Buscar eventos...">
              <button type="submit" class="search-btn"><i class="bi bi-search"></i></button>
            </form>
          </li>

          <% if (org != null) { %>
            <li class="ms-auto d-flex align-items-center gap-2">
              <a href="<%=ctx%>/perfil_US04.html" class="user-info text-decoration-none d-flex align-items-center gap-2">
                <img src="<%=ctx%>/media/img/usuarios/misEventos.jpeg" alt="">
                <span class="fw-semibold"><%= org.getNickname() != null ? org.getNickname() : "Organizador" %></span>
              </a>
              <a href="<%=ctx%>/logout" class="btn p-0 border-0 bg-transparent">
                <i class="login-icon bi bi-box-arrow-right fs-3" title="Cerrar Sesión"></i>
              </a>
            </li>
          <% } else { %>
            <li class="ms-auto d-flex align-items-center gap-2">
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

  <!-- ===== Main ===== -->
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

          <!-- Imagen (si no tenés imagen en DTO uso default) -->
          <div class="text-center mb-4">
            <img src="<%=ctx%>/media/img/default.png"
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

  <!-- ===== Footer ===== -->
  <hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
  <footer id="footer" class="footer position-relative light-background">
    <div class="container mt-4">
      <div class="row">
        <div class="col-lg-6 col-md-6 mb-3">
          <h5>Sobre eventos.uy</h5>
          <p class="text-muted small">
            En <strong>eventos.uy</strong> nos apasiona conectar personas a través de conferencias,
            talleres, hackatones y todo tipo de encuentros. Queremos hacer más fácil organizar,
            descubrir y disfrutar eventos. <br>
            Si sos organizador, vas a encontrar herramientas para gestionar tus ediciones,
            inscripciones y patrocinios. Y si sos asistente, podés
            consultar por tus eventos favoritos, registrarte en segundos y luego queda disfrutarlos!.
          </p>
        </div>
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
</body>
</html>
