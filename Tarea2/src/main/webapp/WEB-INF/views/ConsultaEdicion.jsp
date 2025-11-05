<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, java.net.URLEncoder, java.nio.charset.StandardCharsets, servidorcentral.logica.DTPatrocinio" %>

<%
String ctx = request.getContextPath();

@SuppressWarnings("unchecked")
java.util.Map<String, Object> VM = (java.util.Map<String, Object>) request.getAttribute("VM");

// Flags (acepta de sesión o de request)
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

String nombre = VM != null ? (String) VM.get("nombre") : null;
String sigla  = VM != null ? (String) VM.get("sigla")  : null;
String orgNom = VM != null ? (String) VM.get("organizadorNombre") : null;
String fIni   = VM != null ? (String) VM.get("fechaIni") : null;
String fFin   = VM != null ? (String) VM.get("fechaFin") : null;
String ciudad = VM != null ? (String) VM.get("ciudad") : null;
String pais   = VM != null ? (String) VM.get("pais")   : null;
String estado = VM != null ? (String) VM.get("estado") : null;
String imagen = VM != null ? (String) VM.get("imagen") : null;
String evNom  = VM != null ? (String) VM.get("eventoNombre") : null;
//finalizada es false si la fecha actual es mayor a la fecha de fin de la edicion 
Boolean finalizado = VM != null ? (Boolean) VM.get("finalizado") : Boolean.FALSE;

if (imagen == null || imagen.isEmpty()) {
  imagen = ctx + "/media/img/default.png";
}

@SuppressWarnings("unchecked")
java.util.List<java.util.Map<String,String>> tipos =
  VM != null ? (java.util.List<java.util.Map<String,String>>) VM.get("tipos") : java.util.Collections.emptyList();

@SuppressWarnings("unchecked")
java.util.List<java.util.Map<String,String>> regs =
  VM != null ? (java.util.List<java.util.Map<String,String>>) VM.get("registros") : java.util.Collections.emptyList();

@SuppressWarnings("unchecked")
java.util.Map<String,String> miReg =
  VM != null ? (java.util.Map<String,String>) VM.get("miRegistro") : null;

@SuppressWarnings("unchecked")
java.util.List<DTPatrocinio> pats = (List<DTPatrocinio>) request.getAttribute("patrocinios");

// Ayuda encoding
String encEd = (nombre != null) ? URLEncoder.encode(nombre, StandardCharsets.UTF_8.name()) : "";
String encEv = (evNom  != null) ? URLEncoder.encode(evNom,  StandardCharsets.UTF_8.name()) : "";
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Consulta Edición</title>
</head>

<body class="index-page">
<header id="header" class="header d-flex align-items-center fixed-top">
  <jsp:include page="/WEB-INF/views/template/header.jsp" />
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

      <!-- Cabecera -->
      <div class="row g-4">
        <div class="col-lg-6">
          <img src="<%= imagen %>" alt="<%= nombre %>"
               class="img-fluid rounded shadow w-100"
               style="height:380px; object-fit:cover;">
        </div>

        <div class="col-lg-6">
          <div class="card shadow-sm p-3 h-100">
            <p class="mb-1"><strong>Sigla:</strong> <%= nv(sigla) %></p>
            <p class="mb-1"><strong>Organizador:</strong> <%= nv(orgNom) %></p>
			<p class="mb-1"><strong>Fechas:</strong>
			  <%= (VM.get("fechaIni")!=null ? VM.get("fechaIni") : "—") %>
			  a
			  <%= (VM.get("fechaFin")!=null ? VM.get("fechaFin") : "—") %>
			</p>
            <p class="mb-1"><strong>Ciudad:</strong> <%= nv(ciudad) %></p>
            <p class="mb-3"><strong>País:</strong> <%= nv(pais) %></p>
            <% if (estado != null) { %>
              <p class="mb-3"><strong>Estado:</strong> <span class="badge <%= estadoBadge(estado) %>"><%= estado %></span></p>
            <% } %>

            <div class="d-flex align-items-center gap-2 mb-2">
              <h5 class="mb-0"><i class="bi bi-ticket-perforated"></i> Tipos de Registro</h5>
              <% if (ES_ORGANIZADOR_ED && !finalizado) { %>
                <a href="<%= ctx %>/organizador-tipos-registro-alta?evento=<%= encEv %>&edicion=<%= encEd %>"
                   class="btn btn-sm btn-primary ms-auto d-inline-flex align-items-center justify-content-center"
                   style="width:32px; height:32px; border-radius:8px;"
                   title="Alta de Tipo de Registro" aria-label="Alta de Tipo de Registro">
                  <i class="bi bi-plus"></i>
                </a>
              <% } %>
            </div>

            <%-- Botón Inscribirme (global) --%>
            <%
              boolean sinRegistro = (miReg == null) || miReg.isEmpty() || miReg.get("tipo") == null
                                    || String.valueOf(miReg.get("tipo")).isBlank();
              boolean hayTipos = (tipos != null && !tipos.isEmpty());
              boolean puedeMostrarInscribirme = ES_ASISTENTE && sinRegistro && hayTipos && !finalizado;


              if (puedeMostrarInscribirme) {
                int cantTipos = tipos.size();
                if (cantTipos == 1) {
                  java.util.Map<String,String> tr0 = tipos.get(0);
                  String t0n = tr0.get("nombre");
                  String encT0 = (t0n != null) ? URLEncoder.encode(t0n, StandardCharsets.UTF_8.name()) : "";
            %>
                  <div class="d-flex mb-2">
                    <a class="btn btn-success btn-sm ms-auto"
                       href="<%= ctx %>/RegistroAlta?evento=<%= encEv %>&edicion=<%= encEd %>&tipo=<%= encT0 %>">
                      <i class="bi bi-check2-circle me-1"></i> Inscribirme
                    </a>
                  </div>
            <%
                } else {
            %>
                  <div class="d-flex mb-2">
                    <div class="btn-group ms-auto">
                      <button type="button" class="btn btn-success btn-sm dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-check2-circle me-1"></i> Inscribirme
                      </button>
                      <ul class="dropdown-menu dropdown-menu-end">
                        <%
                          for (java.util.Map<String,String> tr : tipos) {
                            String tnom = tr.get("nombre");
                            String tenc = (tnom != null) ? URLEncoder.encode(tnom, StandardCharsets.UTF_8.name()) : "";
                            String tcosto = tr.get("costo");
                            String tcupo  = tr.get("cupos"); if (tcupo == null) tcupo = tr.get("cupoTotal");
                        %>
                          <li>
                            <a class="dropdown-item"
                               href="<%= ctx %>/RegistroAlta?evento=<%= encEv %>&edicion=<%= encEd %>&tipo=<%= tenc %>">
                               <strong><%= nv(tnom) %></strong>
                               <small class="text-muted">
                                 &nbsp;—&nbsp;Costo: <%= nv(tcosto) %> | Cupos: <%= nv(tcupo) %>
                               </small>
                            </a>
                          </li>
                        <% } %>
                      </ul>
                    </div>
                  </div>
            <%
                }
              }
            %>

            <div class="list-group">
            <%
              if (tipos != null && !tipos.isEmpty()) {
                for (java.util.Map<String,String> tr : tipos) {
                  String tn   = tr.get("nombre");
                  String cost = tr.get("costo");
                  String cup  = tr.get("cupos"); if (cup == null) cup = tr.get("cupoTotal");
                  String encTn = (tn != null) ? URLEncoder.encode(tn, StandardCharsets.UTF_8.name()) : "";
                  boolean yaInscriptoEnEsteTipo =
                    (miReg != null && tn != null && tn.equalsIgnoreCase(String.valueOf(miReg.get("tipo"))));
            %>
              <div class="list-group-item d-flex flex-column flex-md-row align-items-start align-items-md-center justify-content-between gap-2">
                <div class="me-3">
                  <div class="fw-semibold"><%= nv(tn) %></div>
                  <div class="small text-muted">Costo: <%= nv(cost) %> &nbsp;|&nbsp; Cupos: <%= nv(cup) %></div>
                </div>
                <div class="d-flex gap-2 ms-md-auto">
                  <a class="btn btn-sm btn-outline-primary"
                     href="<%= ctx %>/ConsultaTipoRegistro?evento=<%= encEv %>&edicion=<%= encEd %>&tipo=<%= encTn %>">
                    Ver detalles
                  </a>
                  <% if (ES_ASISTENTE && !finalizado) { %>
                    <% if (yaInscriptoEnEsteTipo) { %>
                      <button class="btn btn-sm btn-secondary" type="button" disabled>Ya inscripto</button>
                    <% } else { %>
                      <a class="btn btn-sm btn-success"
                         href="<%= ctx %>/RegistroAlta?evento=<%= encEv %>&edicion=<%= encEd %>&tipo=<%= encTn %>">
                        Inscribirme
                      </a>
                    <% } %>
                  <% } %>
                </div>
              </div>
            <%
                }
              } else {
            %>
              <div class="list-group-item text-muted">No hay tipos de registro.</div>
            <% } %>
            </div>
          </div>
        </div>
      </div>

      <hr class="my-4">

      <!-- Mi registro (solo si asistente inscripto en esta edición) -->
<% if (ES_ASISTENTE && miReg != null) { %>
      <div class="card shadow-sm mb-4">
        <div class="card-body">
          <h5 class="card-title"><i class="bi bi-person-badge"></i> Mi registro</h5>
          <div class="row">
            <div class="col-md-4"><strong>Tipo:</strong> <%= nv(miReg.get("tipo")) %></div>
            <div class="col-md-4"><strong>Fecha:</strong> <%= nv(miReg.get("fecha")) %></div>
          </div>
        </div>
      </div>
      <% } %>

      <!-- Registros (solo si organiza esta edición) -->
      <% if (ES_ORGANIZADOR_ED ) { %>
      <div class="card shadow-sm mb-4">
        <div class="card-body">
          <h5 class="card-title"><i class="bi bi-list"></i> Registros de Asistentes</h5>
          <div class="table-responsive">
            <table class="table table-bordered align-middle mb-0">
              <thead class="table-light">
                <tr><th>Nombre</th><th>Tipo Registro</th><th>Fecha</th></tr>
              </thead>
              <tbody>
                <% if (regs != null && !regs.isEmpty()) {
                     for (java.util.Map<String,String> r : regs) { %>
                  <tr>
                    <td><%= nv(r.get("asistente")) %></td>
                    <td><%= nv(r.get("tipo")) %></td>
                    <td><%= nv(r.get("fecha")) %></td>
                  </tr>
                <% } } else { %>
                  <tr><td colspan="4" class="text-center text-muted">No hay registros aún.</td></tr>
                <% } %>
              </tbody>
            </table>
          </div>
        </div>
      </div>
      <% } %>

      <!-- Patrocinios -->
      <div class="card shadow-sm">
        <div class="card-body">
          <div class="d-flex align-items-center gap-2 mb-2">
            <h5 class="card-title mb-0"><i class="bi bi-people"></i> Patrocinios</h5>
            <% if (ES_ORGANIZADOR_ED && !finalizado) { %>
              <a href="<%= ctx %>/organizador-patrocinios-alta?evento=<%= encEv %>&edicion=<%= encEd %>"
                 class="btn btn-sm btn-primary ms-auto d-inline-flex align-items-center justify-content-center"
                 style="width:32px; height:32px; border-radius:8px;"
                 title="Alta de Patrocinio" aria-label="Alta de Patrocinio">
                <i class="bi bi-plus"></i>
              </a>
            <% } %>
          </div>

          <% if (pats != null) { %>
            <div id="contenido-patrocinios">
              <jsp:include page="/WEB-INF/views/ConsultaPatrocinio.jsp" />
            </div>
          <% } else { %>
            <div class="text-muted">No hay patrocinios cargados.</div>
          <% } %>
        </div>
      </div>

    <% } %>
  </section>
</main>

<hr class="mt-5 mb-4" style="border:0; height:3px; background:#bbb; border-radius:3px;">
<footer id="footer" class="footer position-relative light-background">
  <jsp:include page="/WEB-INF/views/template/footer.jsp" />
</footer>

<script src="<%=ctx%>/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<%! 
  private static String nv(Object o){ return (o==null)?"—":String.valueOf(o); }
  private static String estadoBadge(String e) {
    if (e == null) return "bg-secondary";
    switch (e) {
      case "Ingresada": return "bg-success";
      case "Aceptada":  return "bg-primary";
      case "Rechazada": return "bg-danger";
      default:          return "bg-secondary";
    }
  }
%>
</body>
</html>
