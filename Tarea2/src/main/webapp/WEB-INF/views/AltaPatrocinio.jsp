<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
  String ctx = request.getContextPath();

  String msgOk   = (String) request.getAttribute("msgOk");
  String msgErr  = (String) request.getAttribute("msgError");

  String eventoSel  = (String) request.getAttribute("EVENTO_SEL");
  String edicionSel = (String) request.getAttribute("EDICION_SEL");

  String form_institucion = (String) request.getAttribute("form_institucion");
  String form_nivel       = (String) request.getAttribute("form_nivel");
  String form_aporte      = (String) request.getAttribute("form_aporte");
  String form_tipoReg     = (String) request.getAttribute("form_tipoRegistro");
  String form_cant        = (String) request.getAttribute("form_cantidad");
  String form_codigo      = (String) request.getAttribute("form_codigo");
  String encEvento = java.net.URLEncoder.encode(eventoSel == null ? "" : eventoSel, java.nio.charset.StandardCharsets.UTF_8.name());
  String encEdicion = java.net.URLEncoder.encode(edicionSel == null ? "" : edicionSel, java.nio.charset.StandardCharsets.UTF_8.name());
  String encMsg = java.net.URLEncoder.encode("Operación cancelada.", java.nio.charset.StandardCharsets.UTF_8.name());
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>EventUY - Alta Patrocinio</title>
</head>

<body class="index-page">
<header id="header" class="header d-flex align-items-center fixed-top">
  <jsp:include page="/WEB-INF/views/template/header.jsp" />
</header>

<main class="main mt-5 pt-5">
  <section class="container" style="max-width: 860px;">

    <div class="mb-4 text-center">
      <h2 class="mb-1">Alta de Patrocinio</h2>
      <p class="text-muted mb-0">Ingresá los datos para registrar un nuevo patrocinio.</p>
    </div>

    <% if (msgOk != null) { %>
      <div class="alert alert-success d-flex align-items-center"><i class="bi bi-check2-circle me-2"></i><%= msgOk %></div>
    <% } %>
    <% if (msgErr != null) { %>
      <div class="alert alert-danger d-flex align-items-center"><i class="bi bi-exclamation-triangle me-2"></i><%= msgErr %></div>
    <% } %>

    <div class="card shadow-sm">
      <div class="card-body">

        <!-- ACTION CORREGIDO -->
        <form action="<%=ctx%>/organizador-patrocinios-alta" method="post" class="row g-3" id="form-patro">
          <div class="col-md-6">
            <label class="form-label">Evento</label>
            <input type="text" class="form-control" value="<%= (eventoSel!=null?eventoSel:"") %>" readonly>
            <input type="hidden" name="evento" id="evento" value="<%= (eventoSel!=null?eventoSel:"") %>">
          </div>

          <div class="col-md-6">
            <label class="form-label">Edición</label>
            <input type="text" class="form-control" value="<%= (edicionSel!=null?edicionSel:"") %>" readonly>
            <input type="hidden" name="edicion" id="edicion" value="<%= (edicionSel!=null?edicionSel:"") %>">
          </div>

          <div class="col-md-6">
            <label class="form-label">Institución</label>
            <select name="institucion" class="form-select" required>
              <option value="">-- Seleccione --</option>
              <%
                String[] insts = (String[]) request.getAttribute("INSTITUCIONES");
                if (insts != null) {
                  for (String ins : insts) {
                    boolean sel = (form_institucion != null && form_institucion.equals(ins));
              %>
                <option value="<%=ins%>" <%= (sel?"selected":"") %>><%=ins%></option>
              <% } } %>
            </select>
          </div>

          <div class="col-md-6">
            <label class="form-label">Nivel</label>
            <select name="nivel" class="form-select" required>
              <%
                String[] niveles = {"Platino","Oro","Plata","Bronce"};
                for (String n : niveles) {
                  boolean sel = (form_nivel != null && form_nivel.equals(n));
              %>
                <option value="<%=n%>" <%= (sel?"selected":"") %>><%=n%></option>
              <% } %>
            </select>
          </div>

          <div class="col-md-6">
            <label class="form-label">Aporte Económico (UYU)</label>
            <input type="number" name="aporte" id="aporte" min="1" class="form-control"
                   value="<%= (form_aporte!=null?form_aporte:"") %>" required>
            <small class="text-muted">Hasta el 20 % del aporte se usa para registros gratuitos.</small>
          </div>

          <div class="col-md-6">
            <label class="form-label">Tipo de Registro Gratuito</label>
            <input type="text" name="tipoRegistro" id="tipoRegistro" class="form-control"
                   value="<%= (form_tipoReg!=null?form_tipoReg:"") %>" required>
            <small class="text-muted">Escribí el nombre exacto del tipo de registro.</small>
            <!-- Info de costo calculado (cuando exista) -->
            <div class="form-text" id="costoTRinfo" style="min-height:1.2rem;"></div>
          </div>

          <div class="col-md-6">
            <label class="form-label">Cantidad de Registros Gratuitos</label>
            <input type="number" name="cantidadRegistros" id="cantidadRegistros" class="form-control"
                   value="<%= (form_cant!=null?form_cant:"") %>" readonly>
            <small class="text-muted">Se calcula automáticamente según el 20 % del aporte.</small>
          </div>

          <div class="col-md-6">
            <label class="form-label">Código de Patrocinio</label>
            <input type="text" name="codigo" class="form-control"
                   value="<%= (form_codigo!=null?form_codigo:"") %>" required>
          </div>

          <div class="col-12 d-flex gap-2">
            <button type="submit" class="btn btn-primary">Registrar</button>

            <!-- CANCELAR: vuelve a la edición con mensaje "Operación cancelada" -->
            <a class="btn btn-outline-secondary"
			   href="<%= ctx %>/ediciones-consulta?evento=<%= encEvento %>&edicion=<%= encEdicion %>&msgOk=<%= encMsg %>">
			  Cancelar
			</a>
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

<script src="<%=ctx%>/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Cálculo en vivo de cantidad (20% del aporte / costo del tipo) -->
<script>
(function(){
  const ctx = '<%=ctx%>';
  const edicion = document.getElementById('edicion')?.value || '';
  const aporteEl = document.getElementById('aporte');
  const tipoEl   = document.getElementById('tipoRegistro');
  const cantEl   = document.getElementById('cantidadRegistros');
  const costoInfo= document.getElementById('costoTRinfo');

  async function recalcular(){
    const aporte = aporteEl?.value || '';
    const tipo   = tipoEl?.value || '';
    if(!aporte || !tipo) { 
      if (cantEl) cantEl.value='';
      if (costoInfo) costoInfo.innerText='';
      return; 
    }

    try {
      const params = new URLSearchParams({
        calc:'1',
        edicion: edicion,
        tipoRegistro: tipo,
        aporte: aporte
      });
      const res = await fetch(`${ctx}/organizador-patrocinios-alta?`+params.toString(), {cache:'no-store'});
      if(!res.ok) throw new Error('calc failed');
      const data = await res.json();
      if (cantEl)  cantEl.value = (data && typeof data.cantidad === 'number') ? data.cantidad : '';
      if (costoInfo) {
        if (data && data.costo != null) {
          costoInfo.innerText = `Costo del tipo: $${data.costo}`;
        } else {
          costoInfo.innerText = '';
        }
      }
    } catch(e) {
      if (cantEl) cantEl.value = '';
      if (costoInfo) costoInfo.innerText = '';
    }
  }

  aporteEl?.addEventListener('input', recalcular);
  tipoEl?.addEventListener('input',  recalcular);
})();
</script>
</body>
</html>
