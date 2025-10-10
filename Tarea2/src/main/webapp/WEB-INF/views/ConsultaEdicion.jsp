<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, ServidorCentral.logica.DTPatrocinio" %>


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

  java.util.List<DTPatrocinio> pats = (List<DTPatrocinio>) request.getAttribute("patrocinios");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>EventUY - Consulta Edicion de Evento</title>
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

          <h5 class="mt-3 d-flex align-items-center gap-2">
			  <i class="bi bi-ticket-perforated"></i> Tipos de Registro
			
			  <% if (ES_ORGANIZADOR) { %>
			    <a href="<%= ctx %>/organizador-tipos-registro-alta"
			       class="btn btn-sm btn-primary ms-auto d-inline-flex align-items-center justify-content-center"
			       style="width: 32px; height: 32px; border-radius: 8px;"
			       title="Alta de Tipo de Registro" aria-label="Alta de Tipo de Registro">
			      <i class="bi bi-plus"></i>
			    </a>
			  <% } %>
		</h5>

			<ul class="mb-3">
			  <% if (tipos != null && !tipos.isEmpty()) {
			       for (java.util.Map<String,String> tr : tipos) {
			         String tn = tr.get("nombre"); // solo el nombre
			         String encEd = java.net.URLEncoder.encode(nombre, java.nio.charset.StandardCharsets.UTF_8);
			         String encTn = java.net.URLEncoder.encode(tn,      java.nio.charset.StandardCharsets.UTF_8);
			  %>
			    <li class="d-flex align-items-center justify-content-between flex-wrap gap-2">
			      <span><%= nv(tn) %></span>
			      <a class="btn btn-sm btn-outline-primary"
			         href="<%= ctx %>/ConsultaTipoRegistro?edicion=<%= encEd %>&tipo=<%= encTn %>">
			        Ver detalles
			      </a>
			    </li>
			  <% } } else { %>
			    <li class="text-muted">No hay tipos de registro.</li>
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
          <% if (pats != null ) { %>
     <div class="mt-5" id="patrocinios">
		  <h5><i class="bi bi-people"></i> Patrocinios</h5>
		  <div id="contenido-patrocinios">
							<jsp:include page="/WEB-INF/views/ConsultaPatrocinio.jsp" />
						</div>
		</div>		
          <%  } else { %>
            <li class="text-muted">No hay patrocinios cargados.</li>
          <% } %>
        </ul>
      </div>

    <% } %>
  </section>
</main>

<hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
<footer id="footer" class="footer position-relative light-background">
  <jsp:include page="/WEB-INF/views/template/footer.jsp" />
</footer>

<script src="<%=ctx%>/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<%! // helper JSP para “null value”
    private static String nv(Object o){ return (o==null)?"—":String.valueOf(o); }
%>
</body>
</html>

