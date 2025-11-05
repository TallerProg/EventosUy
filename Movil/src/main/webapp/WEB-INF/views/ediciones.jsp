<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.net.URLEncoder, java.nio.charset.StandardCharsets, servidorcentral.logica.DTPatrocinio" %>

<%
String ctx = request.getContextPath();

@SuppressWarnings("unchecked")
List<Map<String,String>> EVENTOS = (List<Map<String,String>>) request.getAttribute("EVENTOS");
@SuppressWarnings("unchecked")
List<Map<String,String>> EDICIONES = (List<Map<String,String>>) request.getAttribute("EDICIONES");
@SuppressWarnings("unchecked")
Map<String,Object> VM = (Map<String,Object>) request.getAttribute("VM");

boolean ES_ORGANIZADOR = (session != null && session.getAttribute("usuarioOrganizador") != null);
boolean ES_ASISTENTE   = (session != null && session.getAttribute("usuarioAsistente")   != null);
if (!ES_ORGANIZADOR) {
  Object v = request.getAttribute("ES_ORGANIZADOR");
  if (v == null) v = request.getAttribute("ES_ORG");
  ES_ORGANIZADOR = Boolean.TRUE.equals(v) || "true".equalsIgnoreCase(String.valueOf(v));
}
if (!ES_ASISTENTE) {
  Object v = request.getAttribute("ES_ASISTENTE");
  if (v == null) v = request.getAttribute("ES_ASIS");
  ES_ASISTENTE = Boolean.TRUE.equals(v) || "true".equalsIgnoreCase(String.valueOf(v));
}
boolean ES_ORGANIZADOR_ED = false;
Object _orgEd = request.getAttribute("ES_ORGANIZADOR_ED");
if (_orgEd == null) _orgEd = request.getAttribute("ES_ORG_ED");
ES_ORGANIZADOR_ED = Boolean.TRUE.equals(_orgEd) || "true".equalsIgnoreCase(String.valueOf(_orgEd));

boolean ES_ASISTENTE_INSCRIPTO_ED = false;
Object _asiEd = request.getAttribute("ES_ASISTENTE_INSCRIPTO_ED");
if (_asiEd == null) _asiEd = request.getAttribute("ES_ASIS_ED");
ES_ASISTENTE_INSCRIPTO_ED = Boolean.TRUE.equals(_asiEd) || "true".equalsIgnoreCase(String.valueOf(_asiEd));

String msgOk  = (String) request.getAttribute("msgOk");
String msgErr = (String) request.getAttribute("msgError");

String nombreEd = VM != null ? (String) VM.get("nombre") : null;
String evNom    = VM != null ? (String) VM.get("eventoNombre") : null;
String sigla    = VM != null ? (String) VM.get("sigla") : null;
String orgNom   = VM != null ? (String) VM.get("organizadorNombre") : null;
String fIni     = VM != null ? (String) VM.get("fechaIni") : null;
String fFin     = VM != null ? (String) VM.get("fechaFin") : null;
String ciudad   = VM != null ? (String) VM.get("ciudad") : null;
String pais     = VM != null ? (String) VM.get("pais") : null;
String estado   = VM != null ? (String) VM.get("estado") : null;
String imagen   = VM != null ? (String) VM.get("imagen") : null;
String videoUrl = VM != null ? (String) VM.get("videoUrl") : null;
Boolean finalizado = VM != null ? (Boolean) VM.get("finalizado") : Boolean.FALSE;

if (imagen == null || imagen.isBlank()) imagen = ctx + "/media/img/default.png";

@SuppressWarnings("unchecked")
List<Map<String,String>> tipos =
  VM != null ? (List<Map<String,String>>) VM.getOrDefault("tipos", Collections.emptyList())
             : Collections.emptyList();

@SuppressWarnings("unchecked")
List<Map<String,String>> regs =
  VM != null ? (List<Map<String,String>>) VM.getOrDefault("registros", Collections.emptyList())
             : Collections.emptyList();

@SuppressWarnings("unchecked")
Map<String,String> miReg =
  VM != null ? (Map<String,String>) VM.get("miRegistro") : null;

@SuppressWarnings("unchecked")
List<DTPatrocinio> pats = (List<DTPatrocinio>) request.getAttribute("patrocinios");

String encEd = (nombreEd != null) ? URLEncoder.encode(nombreEd, StandardCharsets.UTF_8.name()) : "";
String encEv = (evNom    != null) ? URLEncoder.encode(evNom,    StandardCharsets.UTF_8.name()) : "";

boolean hayDetalleSeleccionado = (nombreEd != null && !nombreEd.isBlank());
boolean hayEventoSeleccionado  = (evNom != null && !evNom.isBlank());
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Consulta Edición — Móvil</title>
  <style>
    .touch-card { border-radius: 14px; overflow: hidden; }
    .touch-img { height: 180px; object-fit: cover; }
    .btn-wide { width: 100%; }
    .pill-badge { border-radius: 999px; padding: .35rem .7rem; font-size: .85rem; }
    .sticky-actions { position: sticky; bottom: 0; z-index: 1020; }
    .video-wrap { position: relative; width: 100%; padding-top: 56.25%; }
    .video-wrap iframe, .video-wrap video {
      position: absolute; top:0; left:0; width:100%; height:100%;
      border: 0; border-radius: 12px;
    }
    @media (min-width: 576px) { .touch-img { height: 220px; } }
  </style>
</head>

<body class="index-page">
<header id="header" class="header d-flex align-items-center fixed-top">
  <jsp:include page="/WEB-INF/views/template/header.jsp" />
</header>

<main class="main mt-5 pt-4">
  <section class="container">
    <% if (msgOk != null) { %>
      <div class="alert alert-success d-flex align-items-center"><i class="bi bi-check2-circle me-2"></i><%= msgOk %></div>
    <% } %>
    <% if (msgErr != null) { %>
      <div class="alert alert-danger d-flex align-items-center"><i class="bi bi-exclamation-triangle me-2"></i><%= msgErr %></div>
    <% } %>

    <% if (!hayEventoSeleccionado && !hayDetalleSeleccionado) { %>
      <div class="section-title"><h2>Elegí un evento</h2></div>
      <% if (EVENTOS == null || EVENTOS.isEmpty()) { %>
        <div class="alert alert-warning">No hay eventos disponibles.</div>
      <% } else { %>
        <div class="row g-3">
          <% for (Map<String,String> ev : EVENTOS) {
               String eNom = ev.get("nombre");
               String eImg = ev.get("imagen");
               if (eImg == null || eImg.isBlank()) eImg = ctx + "/media/img/default.png";
               String encENom = eNom != null ? URLEncoder.encode(eNom, StandardCharsets.UTF_8.name()) : "";
          %>
            <div class="col-12 col-sm-6 col-md-4">
              <div class="card touch-card shadow-sm h-100">
                <img class="touch-img w-100" src="<%= eImg %>" alt="<%= nv(eNom) %>">
                <div class="card-body d-flex flex-column">
                  <h6 class="mb-3"><%= nv(eNom) %></h6>
                  <a class="btn btn-primary btn-wide mt-auto" href="<%= ctx %>/ConsultaEdicionMobile?evento=<%= encENom %>">Ver ediciones</a>
                </div>
              </div>
            </div>
          <% } %>
        </div>
      <% } %>
    <% } %>

    <% if (hayEventoSeleccionado && !hayDetalleSeleccionado) { %>
      <div class="d-flex align-items-center mb-3">
        <a class="btn btn-link p-0 me-2" href="<%= ctx %>/ConsultaEdicionMobile"><i class="bi bi-arrow-left"></i> Eventos</a>
        <h2 class="h5 mb-0">Ediciones de <span class="fw-semibold"><%= nv(evNom) %></span></h2>
      </div>
      <%
        List<Map<String,String>> aprobadas = new ArrayList<>();
        if (EDICIONES != null) {
          for (Map<String,String> ed : EDICIONES) {
            if ("Aprobada".equalsIgnoreCase(String.valueOf(ed.get("estado")))) aprobadas.add(ed);
          }
        }
      %>
      <% if (aprobadas.isEmpty()) { %>
        <div class="alert alert-info">No hay ediciones en estado Aprobada.</div>
      <% } else { %>
        <div class="row g-3">
          <% for (Map<String,String> ed : aprobadas) {
               String edNom = ed.get("nombre");
               String edImg = ed.get("imagen");
               if (edImg == null || edImg.isBlank()) edImg = ctx + "/media/img/default.png";
               String eIni = ed.get("fechaIni");
               String eFin = ed.get("fechaFin");
               String org  = ed.get("organizadorNombre");
               String encEdNom = edNom != null ? URLEncoder.encode(edNom, StandardCharsets.UTF_8.name()) : "";
               String encEvNom = evNom != null ? URLEncoder.encode(evNom, StandardCharsets.UTF_8.name()) : "";
          %>
            <div class="col-12">
              <div class="card touch-card shadow-sm">
                <img class="touch-img w-100" src="<%= edImg %>" alt="<%= nv(edNom) %>">
                <div class="card-body">
                  <div class="d-flex align-items-center justify-content-between">
                    <h6 class="mb-0"><%= nv(edNom) %></h6>
                    <span class="badge pill-badge bg-primary">Aprobada</span>
                  </div>
                  <div class="small text-muted mt-1"><i class="bi bi-calendar-event"></i> <%= nv(eIni) %> — <%= nv(eFin) %></div>
                  <div class="small text-muted"><i class="bi bi-person-workspace"></i> <%= nv(org) %></div>
                  <a class="btn btn-success btn-wide mt-3" href="<%= ctx %>/ConsultaEdicionMobile?evento=<%= encEvNom %>&edicion=<%= encEdNom %>">Ver detalles</a>
                </div>
              </div>
            </div>
          <% } %>
        </div>
      <% } %>
    <% } %>

    <% if (hayDetalleSeleccionado) { %>
      <div class="d-flex align-items-center mb-3">
        <a class="btn btn-link p-0 me-2" href="<%= ctx %>/ConsultaEdicionMobile<%= evNom!=null?("?evento="+encEv):"" %>"><i class="bi bi-arrow-left"></i> <%= evNom != null ? "Ediciones" : "Eventos" %></a>
        <h2 class="h5 mb-0"><%= nv(nombreEd) %></h2>
      </div>

      <div class="card shadow-sm touch-card mb-3">
        <img src="<%= imagen %>" alt="<%= nv(nombreEd) %>" class="touch-img w-100">
        <div class="card-body">
          <div class="row gy-2">
            <div class="col-12"><strong>Evento:</strong> <%= nv(evNom) %></div>
            <div class="col-6"><strong>Sigla:</strong> <%= nv(sigla) %></div>
            <div class="col-6"><strong>Estado:</strong> <span class="badge <%= estadoBadge(estado) %>"><%= nv(estado) %></span></div>
            <div class="col-12"><strong>Organizador:</strong> <%= nv(orgNom) %></div>
            <div class="col-12"><strong>Fechas:</strong> <%= nv(fIni) %> — <%= nv(fFin) %></div>
            <div class="col-6"><strong>Ciudad:</strong> <%= nv(ciudad) %></div>
            <div class="col-6"><strong>País:</strong> <%= nv(pais) %></div>
          </div>
        </div>
      </div>

      <% if (videoUrl != null && !videoUrl.isBlank()) { %>
        <div class="mb-3">
          <h6 class="mb-2"><i class="bi bi-camera-reels"></i> Video</h6>
          <div class="video-wrap">
            <%
              String vu = videoUrl.trim();
              boolean isYT = vu.contains("youtube.com") || vu.contains("youtu.be");
              boolean isVimeo = vu.contains("vimeo.com");
              if (isYT || isVimeo) {
                String src = vu;
            %>
              <iframe src="<%= src %>" allowfullscreen></iframe>
            <% } else { %>
              <video controls playsinline src="<%= vu %>"></video>
            <% } %>
          </div>
        </div>
      <% } %>

      <div class="card shadow-sm mb-3">
        <div class="card-body">
          <div class="d-flex align-items-center gap-2 mb-2">
            <h6 class="mb-0"><i class="bi bi-ticket-perforated"></i> Tipos de Registro</h6>
            <% if (ES_ORGANIZADOR_ED && !Boolean.TRUE.equals(finalizado)) { %>
              <a href="<%= ctx %>/organizador-tipos-registro-alta?evento=<%= encEv %>&edicion=<%= encEd %>" class="btn btn-sm btn-primary ms-auto d-inline-flex align-items-center justify-content-center" style="width:36px; height:36px; border-radius:10px;" title="Alta de Tipo de Registro" aria-label="Alta de Tipo de Registro"><i class="bi bi-plus"></i></a>
            <% } %>
          </div>

          <%
            boolean sinRegistro = (miReg == null) || miReg.isEmpty() || miReg.get("tipo") == null || String.valueOf(miReg.get("tipo")).isBlank();
            boolean hayTipos = (tipos != null && !tipos.isEmpty());
            boolean puedeInscribirse = ES_ASISTENTE && sinRegistro && hayTipos && !Boolean.TRUE.equals(finalizado);
            if (puedeInscribirse) {
              if (tipos.size() == 1) {
                Map<String,String> tr0 = tipos.get(0);
                String t0n = tr0.get("nombre");
                String encT0 = (t0n != null) ? URLEncoder.encode(t0n, StandardCharsets.UTF_8.name()) : "";
          %>
              <a class="btn btn-success btn-wide mb-2" href="<%= ctx %>/RegistroAlta?evento=<%= encEv %>&edicion=<%= encEd %>&tipo=<%= encT0 %>"><i class="bi bi-check2-circle me-1"></i> Inscribirme</a>
          <%
              } else {
          %>
              <div class="dropdown mb-2">
                <button class="btn btn-success btn-wide dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false"><i class="bi bi-check2-circle me-1"></i> Inscribirme</button>
                <ul class="dropdown-menu dropdown-menu-end w-100">
                  <% for (Map<String,String> tr : tipos) {
                       String tnom = tr.get("nombre");
                       String tenc = (tnom != null) ? URLEncoder.encode(tnom, StandardCharsets.UTF_8.name()) : "";
                       String tcosto = tr.get("costo");
                       String tcupo  = tr.get("cupos"); if (tcupo == null) tcupo = tr.get("cupoTotal");
                  %>
                    <li>
                      <a class="dropdown-item d-flex justify-content-between align-items-center" href="<%= ctx %>/RegistroAlta?evento=<%= encEv %>&edicion=<%= encEd %>&tipo=<%= tenc %>">
                        <span><strong><%= nv(tnom) %></strong></span>
                        <small class="text-muted">Costo: <%= nv(tcosto) %> | Cupos: <%= nv(tcupo) %></small>
                      </a>
                    </li>
                  <% } %>
                </ul>
              </div>
          <%
              }
            }
          %>

          <% if (tipos != null && !tipos.isEmpty()) { %>
            <div class="list-group">
              <% for (Map<String,String> tr : tipos) {
                   String tn   = tr.get("nombre");
                   String cost = tr.get("costo");
                   String cup  = tr.get("cupos"); if (cup == null) cup = tr.get("cupoTotal");
                   String encTn = (tn != null) ? URLEncoder.encode(tn, StandardCharsets.UTF_8.name()) : "";
                   boolean yaInscripto = (miReg != null && tn != null && tn.equalsIgnoreCase(String.valueOf(miReg.get("tipo"))));
              %>
                <div class="list-group-item d-flex flex-column flex-sm-row justify-content-between align-items-start align-items-sm-center gap-2">
                  <div>
                    <div class="fw-semibold"><%= nv(tn) %></div>
                    <div class="small text-muted">Costo: <%= nv(cost) %> — Cupos: <%= nv(cup) %></div>
                  </div>
                  <div class="d-flex gap-2 w-100 w-sm-auto">
                    <a class="btn btn-sm btn-outline-primary flex-fill" href="<%= ctx %>/ConsultaTipoRegistro?evento=<%= encEv %>&edicion=<%= encEd %>&tipo=<%= encTn %>">Ver detalles</a>
                    <% if (ES_ASISTENTE && !Boolean.TRUE.equals(finalizado)) { %>
                      <% if (yaInscripto) { %>
                        <button class="btn btn-sm btn-secondary flex-fill" disabled>Ya inscripto</button>
                      <% } else { %>
                        <a class="btn btn-sm btn-success flex-fill" href="<%= ctx %>/RegistroAlta?evento=<%= encEv %>&edicion=<%= encEd %>&tipo=<%= encTn %>">Inscribirme</a>
                      <% } %>
                    <% } %>
                  </div>
                </div>
              <% } %>
            </div>
          <% } else { %>
            <div class="text-muted">No hay tipos de registro.</div>
          <% } %>
        </div>
      </div>

      <% if (ES_ASISTENTE && miReg != null) { %>
      <div class="card shadow-sm mb-3">
        <div class="card-body">
          <h6 class="card-title mb-2"><i class="bi bi-person-badge"></i> Mi registro</h6>
          <div class="row gy-2">
            <div class="col-12"><strong>Tipo:</strong> <%= nv(miReg.get("tipo")) %></div>
            <div class="col-12"><strong>Fecha:</strong> <%= nv(miReg.get("fecha")) %></div>
          </div>
        </div>
      </div>
      <% } %>

      <% if (ES_ORGANIZADOR_ED) { %>
      <div class="card shadow-sm mb-3">
        <div class="card-body">
          <h6 class="card-title mb-2"><i class="bi bi-list"></i> Registros de Asistentes</h6>
          <div class="table-responsive">
            <table class="table table-bordered align-middle mb-0">
              <thead class="table-light">
                <tr><th>Nombre</th><th>Tipo</th><th>Fecha</th></tr>
              </thead>
              <tbody>
                <% if (regs != null && !regs.isEmpty()) {
                     for (Map<String,String> r : regs) { %>
                  <tr>
                    <td><%= nv(r.get("asistente")) %></td>
                    <td><%= nv(r.get("tipo")) %></td>
                    <td><%= nv(r.get("fecha")) %></td>
                  </tr>
                <% } } else { %>
                  <tr><td colspan="3" class="text-center text-muted">No hay registros aún.</td></tr>
                <% } %>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <% } %>

      <div class="card shadow-sm mb-3">
        <div class="card-body">
          <div class="d-flex align-items-center gap-2 mb-2">
            <h6 class="card-title mb-0"><i class="bi bi-people"></i> Patrocinios</h6>
            <% if (ES_ORGANIZADOR_ED && !Boolean.TRUE.equals(finalizado)) { %>
              <a href="<%= ctx %>/organizador-patrocinios-alta?evento=<%= encEv %>&edicion=<%= encEd %>" class="btn btn-sm btn-primary ms-auto d-inline-flex align-items-center justify-content-center" style="width:36px; height:36px; border-radius:10px;" title="Alta de Patrocinio" aria-label="Alta de Patrocinio"><i class="bi bi-plus"></i></a>
            <% } %>
          </div>
          <% if (pats != null) { %>
            <div id="contenido-patrocinios"><jsp:include page="/WEB-INF/views/ConsultaPatrocinio.jsp" /></div>
          <% } else { %>
            <div class="text-muted">No hay patrocinios cargados.</div>
          <% } %>
        </div>
      </div>

      <% if (Boolean.TRUE.equals(finalizado)) { %>
        <div class="alert alert-secondary">Esta edición está finalizada.</div>
      <% } %>
    <% } %>
  </section>
</main>

<footer id="footer" class="footer position-relative light-background mt-4">
  <jsp:include page="/WEB-INF/views/template/footer.jsp" />
</footer>

<script src="<%=ctx%>/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<%!
  private static String nv(Object o){ return (o==null || String.valueOf(o).isBlank()) ? "—" : String.valueOf(o); }
  private static String estadoBadge(String e) {
    if (e == null) return "bg-secondary";
    switch (e) {
      case "Ingresada": return "bg-warning text-dark";
      case "Aprobada":
      case "Aceptada":  return "bg-primary";
      case "Rechazada": return "bg-danger";
      case "Finalizada":return "bg-secondary";
      default:          return "bg-secondary";
    }
  }
%>
</body>
</html>
