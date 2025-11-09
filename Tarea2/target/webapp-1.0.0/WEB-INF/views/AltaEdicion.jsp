<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder, java.nio.charset.StandardCharsets" %>
<%
  String ctx = request.getContextPath();

  // Mensajes que setea el servlet
  Object okMsg  = request.getAttribute("msgOk");
  Object errMsg = request.getAttribute("msgError");

  // Valores del formulario (para persistencia tras error)
  String formEvento   = (String) request.getAttribute("form_evento");
  String formNombre   = (String) request.getAttribute("form_nombre");
  String formSigla    = (String) request.getAttribute("form_sigla");
  String formCiudad   = (String) request.getAttribute("form_ciudad");
  String formPais     = (String) request.getAttribute("form_pais");
  String formFechaIni = (String) request.getAttribute("form_fechaIni");
  String formFechaFin = (String) request.getAttribute("form_fechaFin");
  String formFechaAlta= (String) request.getAttribute("form_fechaAlta"); // solo visual

  String volverConsultaEvento = (formEvento != null && !formEvento.isBlank())
    ? (ctx + "/ConsultaEvento?evento=" + URLEncoder.encode(formEvento, StandardCharsets.UTF_8))
    : (ctx + "/Eventos");
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Alta Edición</title>
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
        <!-- IMPORTANTE: coincide con @WebServlet("/ediciones-alta") -->
        <form action="<%= ctx %>/ediciones-alta" method="post" enctype="multipart/form-data" class="row g-3" id="form-edicion">

          <!-- Evento (fijo, sin combo) -->
          <div class="col-md-6">
            <label class="form-label">Evento <span class="text-danger">*</span></label>
            <input type="text" class="form-control" value="<%= (formEvento!=null?formEvento:"") %>" readonly>
            <input type="hidden" name="evento" value="<%= (formEvento!=null?formEvento:"") %>">
          </div>

          <!-- Nombre -->
          <div class="col-md-6">
            <label for="nombre" class="form-label">Nombre de edición <span class="text-danger">*</span></label>
            <input type="text" id="nombre" name="nombre" class="form-control"
                   value="<%= (formNombre!=null?formNombre:"") %>"
                   placeholder="Ej: Tecnología Punta del Este 2026" required>
          </div>

          <!-- Sigla -->
          <div class="col-md-4">
            <label for="sigla" class="form-label">Sigla <span class="text-danger">*</span></label>
            <input type="text" id="sigla" name="sigla" class="form-control"
                   value="<%= (formSigla!=null?formSigla:"") %>"
                   placeholder="Ej: CONFTECH26" required>
          </div>

          <!-- Ciudad -->
          <div class="col-md-4">
            <label for="ciudad" class="form-label">Ciudad <span class="text-danger">*</span></label>
            <input type="text" id="ciudad" name="ciudad" class="form-control"
                   value="<%= (formCiudad!=null?formCiudad:"") %>"
                   placeholder="Montevideo" required>
          </div>

          <!-- País -->
          <div class="col-md-4">
            <label for="pais" class="form-label">País <span class="text-danger">*</span></label>
            <input type="text" id="pais" name="pais" class="form-control"
                   value="<%= (formPais!=null?formPais:"") %>"
                   placeholder="Uruguay" required>
          </div>

          <!-- Fechas -->
          <div class="col-md-4">
            <label for="fechaIni" class="form-label">Fecha inicio <span class="text-danger">*</span></label>
            <input type="date" id="fechaIni" name="fechaIni" class="form-control"
                   value="<%= (formFechaIni!=null?formFechaIni:"") %>" required>
          </div>

          <div class="col-md-4">
            <label for="fechaFin" class="form-label">Fecha fin <span class="text-danger">*</span></label>
            <input type="date" id="fechaFin" name="fechaFin" class="form-control"
                   value="<%= (formFechaFin!=null?formFechaFin:"") %>" required>
          </div>

          <!-- Fecha de alta -->
          <div class="col-md-4">
            <label class="form-label">Fecha alta (automática)</label>
            <input type="text" id="fechaAltaVista" class="form-control" value="<%= (formFechaAlta!=null?formFechaAlta:"") %>" readonly>
            <input type="hidden" id="fechaAlta" name="fechaAlta" value="<%= (formFechaAlta!=null?formFechaAlta:"") %>">
            <div class="form-text">Se completa automáticamente con la fecha de hoy.</div>
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
            <a href="<%= volverConsultaEvento %>" class="btn btn-outline-secondary">Cancelar</a>
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

<script>
  // === Fecha alta automática (hoy) ===
  (function setFechaAlta() {
    const inputOculto = document.getElementById('fechaAlta');
    const inputVista  = document.getElementById('fechaAltaVista');
    const hoy = new Date();
    const yyyy = hoy.getFullYear();
    const mm   = String(hoy.getMonth() + 1).padStart(2, '0');
    const dd   = String(hoy.getDate()).padStart(2, '0');
    const iso  = `${yyyy}-${mm}-${dd}`;

    const ddmmyyyy = `${dd}/${mm}/${yyyy}`;

    if (inputOculto && !inputOculto.value) inputOculto.value = iso;
    if (inputVista  && !inputVista.value)  inputVista.value  = ddmmyyyy;
  })();

  // === Fechas: inicio >= hoy y fin >= inicio (cliente) ===
  const fIni = document.getElementById('fechaIni');
  const fFin = document.getElementById('fechaFin');

  function todayIso() {
    const d = new Date();
    const yyyy = d.getFullYear();
    const mm   = String(d.getMonth() + 1).padStart(2, '0');
    const dd   = String(d.getDate()).padStart(2, '0');
    return `${yyyy}-${mm}-${dd}`;
  }

  function syncMinInicio() {
    if (fIni) {
      fIni.min = todayIso();
      if (fIni.value && fIni.value < fIni.min) fIni.value = fIni.min;
    }
  }
  function syncMinFin() {
    if (fIni && fFin) {
      fFin.min = fIni.value || todayIso();
      if (fFin.value && fFin.value < fFin.min) fFin.value = fFin.min;
    }
  }

  if (fIni && fFin) {
    window.addEventListener('DOMContentLoaded', () => {
      syncMinInicio();
      syncMinFin();
    });
    fIni.addEventListener('change', () => {
      syncMinInicio();
      syncMinFin();
    });
  }

  // Bloquear submit si fin < inicio
  document.getElementById('form-edicion')?.addEventListener('submit', function (e) {
    if (fIni && fFin && fIni.value && fFin.value && fFin.value < fIni.value) {
      e.preventDefault();
      alert('La fecha de fin no puede ser anterior a la fecha de inicio.');
    }
  });

  // === Previsualización de imagen ===
  const inp = document.getElementById('imagen');
  const prev = document.getElementById('preview');
  if (inp && prev) {
    inp.addEventListener('change', () => {
      prev.innerHTML = '';
      const f = inp.files && inp.files[0];
      if (!f) return;
      const url = URL.createObjectURL(f);
      const img = document.createElement('img');
      img.src = url; img.alt = 'Previsualización'; img.className = 'img-fluid';
      prev.appendChild(img);
    });
  }
</script>
</body>
</html>
