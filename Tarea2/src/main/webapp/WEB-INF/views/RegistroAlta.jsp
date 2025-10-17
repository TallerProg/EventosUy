<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="servidorcentral.logica.DTevento" %>
<%@ page import="servidorcentral.logica.DTEdicion" %>
<%@ page import="servidorcentral.logica.DTTipoRegistro" %>

<%
  String ctx = request.getContextPath();

  DTevento evento         = (DTevento)   request.getAttribute("EVENTO");
  DTEdicion edicion     = (DTEdicion)request.getAttribute("EDICION");
  DTTipoRegistro tipo   = (DTTipoRegistro) request.getAttribute("TIPO_REGISTRO");

  Object msgErr = request.getAttribute("msgError");
  Boolean success = (Boolean) request.getAttribute("success");

  Object costoTipo = request.getAttribute("COSTO_TIPO");
  Object cupoTipo  = request.getAttribute("CUPO_TIPO");

  String encEvento  = (String) request.getAttribute("ENC_EVENTO");
  String encEdicion = (String) request.getAttribute("ENC_EDICION");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Registro a Edición</title>
</head>
<body class="index-page">

<header id="header" class="header d-flex align-items-center fixed-top">
  <jsp:include page="/WEB-INF/views/template/header.jsp" />
</header>

<main class="main mt-5 pt-5">
  <section class="container">

    <div class="section-title text-center">
      <h2>Registro a Edición de Evento</h2>
    </div>

    <% if (msgErr != null) { %>
      <div class="alert alert-danger d-flex align-items-center" role="alert">
        <i class="bi bi-exclamation-triangle me-2"></i><div><%= msgErr %></div>
      </div>
    <% } %>

    <% if (success != null && success) { %>
      <div class="alert alert-success d-flex align-items-center" role="alert">
        <i class="bi bi-check2-circle me-2"></i>
        <div>¡Registro realizado con éxito!</div>
      </div>

      <div class="card shadow-sm p-4 mb-4">
        <h5 class="mb-3"><i class="bi bi-receipt"></i> Resumen</h5>
        <p class="mb-1"><strong>Evento:</strong> <%= (evento != null ? evento.getNombre() : "—") %></p>
        <p class="mb-1"><strong>Edición:</strong> <%= (edicion != null ? edicion.getNombre() : "—") %></p>
        <p class="mb-1"><strong>Tipo:</strong> <%= (tipo != null ? tipo.getNombre() : "—") %></p>
        <p class="mb-1"><strong>Modalidad:</strong> <%= String.valueOf(request.getAttribute("MODALIDAD")) %></p>
      </div>

      <a href="<%= ctx %>/ediciones-consulta?evento=<%= encEvento %>&edicion=<%= encEdicion %>" class="btn btn-primary">
        Volver a la edición
      </a>
    <% } else { %>

      <% if (evento != null && edicion != null && tipo != null) { %>
      <div class="row g-4">
        <div class="col-lg-6">
          <div class="card shadow-sm p-4 h-100">
            <h5 class="text-muted mb-2">Datos seleccionados</h5>
            <p class="mb-1"><strong>Evento:</strong> <%= evento.getNombre() %></p>
            <p class="mb-1"><strong>Edición:</strong> <%= edicion.getNombre() %></p>
            <p class="mb-1"><strong>Tipo:</strong> <%= tipo.getNombre() %></p>
            <p class="mb-1"><strong>Costo:</strong> $<%= costoTipo != null ? costoTipo : "—" %></p>
            <p class="mb-0"><strong>Cupo total:</strong> <%= cupoTipo != null ? cupoTipo : "—" %></p>
          </div>
        </div>

        <div class="col-lg-6">
          <div class="card shadow-sm p-4 h-100">
            <h5 class="mb-3"><i class="bi bi-pencil-square"></i> Completar registro</h5>

            <form method="post" action="<%= ctx %>/RegistroAlta" class="needs-validation" novalidate>
              <input type="hidden" name="evento"  value="<%= evento.getNombre() %>">
              <input type="hidden" name="edicion" value="<%= edicion.getNombre() %>">
              <input type="hidden" name="tipo"    value="<%= tipo.getNombre() %>">

              <div class="mb-3">
                <label class="form-label d-block">Modalidad</label>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" name="modalidad" id="modGeneral" value="general" checked>
                  <label class="form-check-label" for="modGeneral">General</label>
                </div>
                <div class="form-check form-check-inline">
                  <input class="form-check-input" type="radio" name="modalidad" id="modPatro" value="patrocinio">
                  <label class="form-check-label" for="modPatro">Con código de patrocinio</label>
                </div>
              </div>

              <div class="mb-3" id="grupoCodigo" style="display:none;">
                <label for="codigo" class="form-label">Código de patrocinio</label>
                <input type="text" class="form-control" id="codigo" name="codigo" placeholder="Ej: ABCD-1234">
                <div class="form-text">Debe ser válido para esta edición y tipo.</div>
              </div>

              <div class="d-flex gap-2">
                <a class="btn btn-outline-secondary"
                   href="<%= ctx %>/ediciones-consulta?evento=<%= encEvento %>&edicion=<%= encEdicion %>">
                  Cancelar
                </a>
                <button type="submit" class="btn btn-success">Confirmar registro</button>
              </div>
            </form>
          </div>
        </div>
      </div>
      <% } else { %>
        <div class="alert alert-warning">No hay datos para mostrar.</div>
      <% } %>

    <% } %>

  </section>
</main>

<hr class="mt-5 mb-4" style="border:0; height:3px; background:#bbb; border-radius:2px;">
<footer id="footer" class="footer position-relative light-background">
  <jsp:include page="/WEB-INF/views/template/footer.jsp" />
</footer>

<script>
  // Mostrar/ocultar campo de código según modalidad
  const modGeneral = document.getElementById('modGeneral');
  const modPatro   = document.getElementById('modPatro');
  const grupoCodigo = document.getElementById('grupoCodigo');

  function toggleCodigo() {
    if (modPatro && modPatro.checked) {
      grupoCodigo.style.display = '';
    } else {
      grupoCodigo.style.display = 'none';
    }
  }
  if (modGeneral && modPatro) {
    modGeneral.addEventListener('change', toggleCodigo);
    modPatro.addEventListener('change', toggleCodigo);
    toggleCodigo();
  }
</script>

</body>
</html>
