<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%

  final String ctx = request.getContextPath();
  String pNombre = request.getParameter("nombre");
  String pDescripcion = request.getParameter("descripcion");
  String pSigla = request.getParameter("sigla");
  if (pNombre == null) pNombre = "";
  if (pDescripcion == null) pDescripcion = "";
  if (pSigla == null) pSigla = "";
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Alta de Evento</title>
</head>
<body class="index-page">
	<header id="header" class="header d-flex align-items-center fixed-top">
  		<jsp:include page="/WEB-INF/views/template/header.jsp" />
	</header>
  <main class="main mt-5 pt-5">
    <section class="section">
      <div class="container">
        <div class="section-title d-flex justify-content-between align-items-center">
          <h2>Alta de Evento</h2>
          <a href="<%= ctx %>/Eventos" class="btn btn-outline-secondary">
            <i class="bi bi-arrow-left me-1"></i> Volver a la lista
          </a>
        </div>

        <form id="altaEventoForm"
              class="p-4 border rounded bg-light shadow-sm"
              action="<%= ctx %>/alta-evento"
              method="post" enctype="multipart/form-data">

          <!-- Nombre -->
          <div class="mb-3">
            <label for="nombre" class="form-label">Nombre del Evento *</label>
            <input type="text" class="form-control" id="nombre" name="nombre" required
                   value="<%= pNombre %>">
            <div class="invalid-feedback">Debe ingresar un nombre único para el evento.</div>
          </div>

          <!-- Descripción -->
          <div class="mb-3">
            <label for="descripcion" class="form-label">Descripción *</label>
            <textarea class="form-control" id="descripcion" name="descripcion" rows="3" required><%= pDescripcion %></textarea>
          </div>

          <!-- Sigla -->
          <div class="mb-3">
            <label for="sigla" class="form-label">Sigla *</label>
            <input type="text" class="form-control" id="sigla" name="sigla" required
                   value="<%= pSigla %>">
          </div>

          <!-- Categorías (estáticas) -->
          <div class="mb-3">
            <label class="form-label">Categorías *</label>
            <div class="row">
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA01"> Tecnología</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA02"> Innovación</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA03"> Literatura</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA04"> Cultura</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA05"> Música</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA06"> Deporte</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA07"> Salud</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA08"> Entretenimiento</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA09"> Agro</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA10"> Negocios</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA11"> Moda</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA12"> Investigación</label></div>
            </div>
            <div class="form-text">Seleccione al menos una categoría.</div>
          </div>

          <!-- Imagen -->
          <div class="mb-3">
            <label for="imagen" class="form-label">Imagen (opcional)</label>
            <input type="file" class="form-control" id="imagen" name="imagen" accept="image/*">
          </div>

          <!-- Botón -->
          <div class="d-flex justify-content-end gap-2">
            <button type="submit" class="btn btn-primary">Dar de Alta</button>
          </div>
        </form>
      </div>
    </section>
  </main>

<hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
<footer id="footer" class="footer position-relative light-background">
  <jsp:include page="/WEB-INF/views/template/footer.jsp" />
</footer>

  <script>
    // Validación mínima de categorías
    document.getElementById('altaEventoForm').addEventListener('submit', function (e) {
      var seleccionadas = document.querySelectorAll('input[name="categorias"]:checked');
      if (seleccionadas.length === 0) {
        e.preventDefault();
        alert("Debe seleccionar al menos una categoría.");
      }
    });
  </script>
</body>
</html>
