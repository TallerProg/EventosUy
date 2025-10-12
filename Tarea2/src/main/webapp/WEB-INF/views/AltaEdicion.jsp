<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>EventUY - Alta Edicion</title>
</head>

<body class="index-page">
<header id="header" class="header d-flex align-items-center fixed-top">
          <jsp:include page="/WEB-INF/views/template/header.jsp" />
</header>

  <main class="main mt-5 pt-5">
    <section class="container">
      <div class="mb-4 text-center">
        <h2 class="mb-1">Alta de Edición de Evento</h2>
        <p class="text-muted mb-0">Completá los datos para asociar una nueva edición a un evento.</p>
      </div>

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
          <form action="<%= ctx %>/organizador/ediciones-alta"
                method="post" enctype="multipart/form-data" class="row g-3">

            <!-- Evento (fijo, sin combo) -->
			<div class="col-md-6">
			  <label class="form-label">Evento <span class="text-danger">*</span></label>
			  <input type="text"
			         class="form-control"
			         value="<%= (request.getAttribute("form_evento")!=null? request.getAttribute("form_evento") : "") %>"
			         readonly>
			  <input type="hidden" name="evento"
			         value="<%= (request.getAttribute("form_evento")!=null? request.getAttribute("form_evento") : "") %>">
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

<hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
<footer id="footer" class="footer position-relative light-background">
  <jsp:include page="/WEB-INF/views/template/footer.jsp" />
</footer>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
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
