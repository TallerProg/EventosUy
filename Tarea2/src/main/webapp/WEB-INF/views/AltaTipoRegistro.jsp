<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String ctx = request.getContextPath();

  Object okMsg  = request.getAttribute("msgOk");
  Object errMsg = request.getAttribute("msgError");

  Object flash  = null;
  if (request.getSession(false) != null) {
    flash = request.getSession(false).getAttribute("flashOk");
    if (flash != null) request.getSession(false).removeAttribute("flashOk");
  }
  
  String edValor  = (String) request.getAttribute("form_edicion");     
  String edNombre = (String) request.getAttribute("form_edicion_nombre");  
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="template/head.jsp" />
  <title>Alta de Tipo de Registro</title>
</head>

<body class="index-page">

<header id="header" class="header d-flex align-items-center fixed-top">
  <jsp:include page="template/header.jsp" />
</header>

<main class="main mt-5 pt-5">
  <section class="container">
    <div class="mb-4 text-center">
      <h2 class="mb-1">Alta de Tipo de Registro</h2>
      <p class="text-muted mb-0">Completá los datos del nuevo tipo de registro.</p>
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
        <form action="<%= ctx %>/organizador-tipos-registro-alta" method="post" class="row g-3" novalidate>

		  <!-- Edición -->
            <div class="col-12">
              <label class="form-label">Edición de evento</label>
              <div class="form-control-plaintext"><%= (edNombre != null && !edNombre.isBlank()) ? edNombre : "—" %></div>
              <input type="hidden" name="edicion" value="<%= edValor != null ? edValor : "" %>">
            </div>
            
          <!-- Nombre -->
          <div class="col-md-6">
            <label for="nombre" class="form-label">Nombre del tipo <span class="text-danger">*</span></label>
            <input type="text" id="nombre" name="nombre" class="form-control"
                   value="<%= request.getAttribute("form_nombre")!=null?request.getAttribute("form_nombre"):"" %>"
                   placeholder="Ej: Full / Estudiante / VIP" maxlength="60" required>
          </div>

          <!-- Descripción -->
          <div class="col-12">
            <label for="descripcion" class="form-label">Descripción <span class="text-danger">*</span></label>
            <textarea id="descripcion" name="descripcion" class="form-control" rows="4" maxlength="400" required><%=
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
            <a href="<%= ctx %>/" class="btn btn-outline-secondary">Cancelar</a>
          </div>

        </form>
      </div>
    </div>
  </section>
</main>

<hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
<footer id="footer" class="footer position-relative light-background">
  <jsp:include page="template/footer.jsp" />
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

