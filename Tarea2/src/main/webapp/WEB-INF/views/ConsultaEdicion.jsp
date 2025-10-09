<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String ctx = request.getContextPath();

  @SuppressWarnings("unchecked")
  java.util.Map<String, Object> VM = (java.util.Map<String, Object>) request.getAttribute("VM");
  boolean ES_ORGANIZADOR = Boolean.TRUE.equals(request.getAttribute("ES_ORGANIZADOR"));
  boolean ES_ASISTENTE   = Boolean.TRUE.equals(request.getAttribute("ES_ASISTENTE"));

  String msgOk  = (String) request.getAttribute("msgOk");
  String msgErr = (String) request.getAttribute("msgError");

  String nombre = VM != null ? (String) VM.get("nombre") : null;
  String sigla  = VM != null ? (String) VM.get("sigla")  : null;
  String orgNom = VM != null ? (String) VM.get("organizadorNombre") : null;
  String fIni   = VM != null ? (String) VM.get("fechaIni") : null;
  String fFin   = VM != null ? (String) VM.get("fechaFin") : null;
  String ciudad = VM != null ? (String) VM.get("ciudad") : null;
  String pais   = VM != null ? (String) VM.get("pais")   : null;
  String imagen = VM != null ? (String) VM.get("imagen") : null;
  if (imagen == null || imagen.isEmpty()) {
    imagen = ctx + "/media/img/ediciones/default.jpg";
  }

  java.util.List<java.util.Map<String,String>> tipos =
    VM != null ? (java.util.List<java.util.Map<String,String>>) VM.get("tipos") : java.util.Collections.emptyList();

  java.util.List<java.util.Map<String,String>> regs =
    VM != null ? (java.util.List<java.util.Map<String,String>>) VM.get("registros") : java.util.Collections.emptyList();

  @SuppressWarnings("unchecked")
  java.util.Map<String,String> miReg =
    VM != null ? (java.util.Map<String,String>) VM.get("miRegistro") : null;

  java.util.List<String> pats =
    VM != null ? (java.util.List<String>) VM.get("patrocinios") : java.util.Collections.emptyList();
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Consulta de Edición de Evento</title>

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

<main class="main mt-5 pt-5">
  <section class="container">

    <% if (msgOk != null) { %>
      <div class="alert alert-success d-flex align-items-center"><i class="bi bi-check2-circle me-2"></i><%= msgOk %></div>
    <% } %>
    <% if (msgErr != null) { %>
      <div class="alert alert-danger d-flex align-items-center"><i class="bi bi-exclamation-triangle me-2"></i><%= msgErr %></div>
    <% } %>

    <% if (nombre == null) { %>
      <div class="alert alert-warning">No hay datos para mostrar.</div>
    <% } else { %>

      <div class="section-title"><h2><%= nombre %></h2></div>

      <div class="row">
        <div class="col-md-6">
          <img src="<%= imagen %>" alt="<%= nombre %>"
               class="img-fluid rounded shadow"
               style="max-width:100%; height:380px; object-fit:cover;">
        </div>
        <div class="col-md-6">
          <p><strong>Sigla:</strong> <%= nv(sigla) %></p>
          <p><strong>Organizador:</strong> <%= nv(orgNom) %></p>
          <p><strong>Fechas:</strong> <%= nv(fIni) %> a <%= nv(fFin) %></p>
          <p><strong>Ciudad:</strong> <%= nv(ciudad) %></p>
          <p><strong>País:</strong> <%= nv(pais) %></p>

          <h5 class="mt-3">
            <i class="bi bi-ticket-perforated"></i> Tipos de Registro
          </h5>
          <ul class="mb-3">
            <% if (tipos != null && !tipos.isEmpty()) {
                 for (java.util.Map<String,String> tr : tipos) {
                   String tn = tr.get("nombre");
                   String tc = tr.get("costo"); // puede venir null/"" si el DTO no lo tiene
                   String cp = tr.get("cupos");
            %>
              <li>
                <%= nv(tn) %>
                (<% if (tc != null && !tc.isEmpty()) { %>costo: $<%= tc %><% } %>
                 <% if (cp != null && !cp.isEmpty()) { %><%= (tc!=null && !tc.isEmpty()) ? ", " : "" %>cupos: <%= cp %><% } %>)
              </li>
            <%   }
               } else { %>
              <li>No hay tipos de registro.</li>
            <% } %>
          </ul>
        </div>
      </div>

      <% if (ES_ORGANIZADOR) { %>
      <div class="mt-4">
        <h5><i class="bi bi-list"></i> Registros de Asistentes</h5>
        <table class="table table-bordered">
          <thead><tr><th>Nombre</th><th>Tipo Registro</th><th>Fecha</th><th>Estado</th></tr></thead>
          <tbody>
            <% if (regs != null && !regs.isEmpty()) {
                 for (java.util.Map<String,String> r : regs) { %>
              <tr>
                <td><%= nv(r.get("asistente")) %></td>
                <td><%= nv(r.get("tipo")) %></td>
                <td><%= nv(r.get("fecha")) %></td>
                <td><%= nv(r.get("estado")) %></td>
              </tr>
            <% } } else { %>
              <tr><td colspan="4" class="text-center text-muted">No hay registros aún.</td></tr>
            <% } %>
          </tbody>
        </table>
      </div>
      <% } %>

      <% if (ES_ASISTENTE && miReg != null) { %>
      <div class="mt-4">
        <h5><i class="bi bi-person-badge"></i> Mi registro</h5>
        <ul class="mb-0">
          <li><strong>Tipo:</strong> <%= nv(miReg.get("tipo")) %></li>
          <li><strong>Fecha:</strong> <%= nv(miReg.get("fecha")) %></li>
          <li><strong>Estado:</strong> <%= nv(miReg.get("estado")) %></li>
        </ul>
      </div>
      <% } %>

      <div class="mt-5">
        <h5><i class="bi bi-people"></i> Patrocinios</h5>
        <ul class="mb-0">
          <% if (pats != null && !pats.isEmpty()) {
               for (String p : pats) { %>
            <li><%= nv(p) %></li>
          <% } } else { %>
            <li class="text-muted">No hay patrocinios cargados.</li>
          <% } %>
        </ul>
      </div>

    <% } %>
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


<script src="<%=ctx%>/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<%! // helper JSP para “null value”
    private static String nv(Object o){ return (o==null)?"—":String.valueOf(o); }
%>
</body>
</html>

