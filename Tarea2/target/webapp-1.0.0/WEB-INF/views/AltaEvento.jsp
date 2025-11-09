<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%!
  private static String esc(String s) {
    if (s == null) return "";
    return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;")
            .replace("\"","&quot;").replace("'","&#x27;");
  }
  private static boolean isChecked(java.util.Set<String> sel, String code) {
    if (sel == null || code == null) return false;
    return sel.contains(code);
  }
%>
<%
  final String ctx = request.getContextPath();

  String aNombre = (String) request.getAttribute("nombre");
  String aDesc   = (String) request.getAttribute("descripcion");
  String aSigla  = (String) request.getAttribute("sigla");
  java.util.Set<String> catsSel = (java.util.Set<String>) request.getAttribute("categoriasSeleccionadas");

  String pNombre = (aNombre != null) ? aNombre : request.getParameter("nombre");
  String pDescripcion = (aDesc != null) ? aDesc : request.getParameter("descripcion");
  String pSigla = (aSigla != null) ? aSigla : request.getParameter("sigla");

  if (pNombre == null) pNombre = "";
  if (pDescripcion == null) pDescripcion = "";
  if (pSigla == null) pSigla = "";

  if (catsSel == null) {
    String[] pv = request.getParameterValues("categorias");
    catsSel = new java.util.HashSet<>();
    if (pv != null) for (String v : pv) catsSel.add(v);
  }

  java.util.List<String> errores = (java.util.List<String>) request.getAttribute("errores");
  boolean ok = "1".equals(request.getParameter("ok"));
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

        <% if (ok) { %>
          <div class="alert alert-success">El evento fue creado correctamente.</div>
        <% } %>

        <% if (errores != null && !errores.isEmpty()) { %>
          <div class="alert alert-danger">
            <strong>No se pudo completar el alta:</strong>
            <ul class="mb-0">
              <% for (String e : errores) { %>
                <li><%= esc(e) %></li>
              <% } %>
            </ul>
          </div>
        <% } %>

        <form id="altaEventoForm"
              class="p-4 border rounded bg-light shadow-sm"
              action="<%= ctx %>/alta-evento"
              method="post" enctype="multipart/form-data">

          <div class="mb-3">
            <label for="nombre" class="form-label">Nombre del Evento *</label>
            <input type="text" class="form-control" id="nombre" name="nombre" required
                   value="<%= esc(pNombre) %>">
            <div class="form-text">Debe ser único.</div>
          </div>

          <div class="mb-3">
            <label for="descripcion" class="form-label">Descripción *</label>
            <textarea class="form-control" id="descripcion" name="descripcion" rows="3" required><%= esc(pDescripcion) %></textarea>
          </div>

          <div class="mb-3">
            <label for="sigla" class="form-label">Sigla *</label>
            <input type="text" class="form-control" id="sigla" name="sigla" required
                   value="<%= esc(pSigla) %>">
          </div>

          <div class="mb-3">
            <label class="form-label">Categorías *</label>
            <div class="row">
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA01" <%= isChecked(catsSel,"CA01")?"checked":"" %>> Tecnología</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA02" <%= isChecked(catsSel,"CA02")?"checked":"" %>> Innovación</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA03" <%= isChecked(catsSel,"CA03")?"checked":"" %>> Literatura</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA04" <%= isChecked(catsSel,"CA04")?"checked":"" %>> Cultura</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA05" <%= isChecked(catsSel,"CA05")?"checked":"" %>> Música</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA06" <%= isChecked(catsSel,"CA06")?"checked":"" %>> Deporte</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA07" <%= isChecked(catsSel,"CA07")?"checked":"" %>> Salud</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA08" <%= isChecked(catsSel,"CA08")?"checked":"" %>> Entretenimiento</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA09" <%= isChecked(catsSel,"CA09")?"checked":"" %>> Agro</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA10" <%= isChecked(catsSel,"CA10")?"checked":"" %>> Negocios</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA11" <%= isChecked(catsSel,"CA11")?"checked":"" %>> Moda</label></div>
              <div class="col-md-4"><label><input type="checkbox" name="categorias" value="CA12" <%= isChecked(catsSel,"CA12")?"checked":"" %>> Investigación</label></div>
            </div>
            <div class="form-text">Seleccione al menos una categoría.</div>
          </div>

          <div class="mb-3">
            <label for="imagen" class="form-label">Imagen (opcional)</label>
            <input type="file" class="form-control" id="imagen" name="imagen" accept="image/*">
          </div>

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
