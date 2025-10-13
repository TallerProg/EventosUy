<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%!
  private static String esc(Object o) {
    if (o == null) return "";
    String s = String.valueOf(o);
    return s.replace("&","&amp;")
            .replace("<","&lt;")
            .replace(">","&gt;")
            .replace("\"","&quot;")
            .replace("'","&#x27;");
  }
%>
<%@ page import="java.util.List" %>
<%@ page import="ServidorCentral.logica.Institucion" %>
<!doctype html>
<html lang="es">
<head>
  <jsp:include page="template/head.jsp" />
  <title>Registro</title>
</head>
<body class="bg-light">
  <header id="header" class="header d-flex align-items-center fixed-top">
     <jsp:include page="template/header.jsp" />
  </header>

  <main class="container py-5">
    <% if (request.getAttribute("error") != null) { %>
      <div class="alert alert-danger" role="alert" style="margin-top: 120px;"><%= esc(request.getAttribute("error")) %></div>
    <% } %>
    <% if (request.getAttribute("mensaje") != null) { %>
      <div class="alert alert-success" role="alert" style="margin-top: 120px;"><%= esc(request.getAttribute("mensaje")) %></div>
    <% } %>

    <div class="row justify-content-center">
      <div class="col-12 col-md-8 col-lg-6">
        <div class="card shadow-lg p-4 rounded-4">
          <div class="text-center mb-4">
            <i class="bi bi-person-plus fs-1 text-primary"></i>
            <h4 class="mt-2 text-primary">Registro de Usuario</h4>
          </div>

          <form id="registroForm"
                method="post"
                action="<%= request.getContextPath() %>/Registrarse"
                enctype="multipart/form-data"
                novalidate>

            <div class="mb-3">
              <label class="form-label">Tipo de Usuario</label>
              <select class="form-select" id="tipoUsuario" name="tipoUsuario" required>
                <option value="" disabled <%= request.getParameter("tipoUsuario")==null ? "selected" : "" %> >Seleccione...</option>
                <option value="asistente"  <%= "asistente".equalsIgnoreCase(request.getParameter("tipoUsuario")) ? "selected" : "" %> >Asistente</option>
                <option value="organizador"<%= "organizador".equalsIgnoreCase(request.getParameter("tipoUsuario")) ? "selected" : "" %> >Organizador</option>
              </select>
              <div class="invalid-feedback">Seleccione un tipo de usuario.</div>
            </div>

            <div class="mb-3">
              <label for="nickname" class="form-label">Nickname (único)</label>
              <input type="text" class="form-control" id="nickname" name="nickname"
                     value="<%= esc(request.getParameter("nickname")) %>" required>
              <div class="invalid-feedback">Ingrese un nickname.</div>
            </div>
            <div class="mb-3">
              <label for="email" class="form-label">Correo electrónico (único)</label>
              <input type="email" class="form-control" id="email" name="email"
                     value="<%= esc(request.getParameter("email")) %>" required>
              <div class="invalid-feedback">Ingrese un correo válido.</div>
            </div>

            <div class="mb-3">
              <label for="nombre" class="form-label">Nombre</label>
              <input type="text" class="form-control" id="nombre" name="nombre"
                     value="<%= esc(request.getParameter("nombre")) %>" required>
              <div class="invalid-feedback">Ingrese su nombre.</div>
            </div>

            <div id="apellidoAsistente" class="mb-3" style="display: none;">
              <label for="apellido" class="form-label">Apellido</label>
              <input type="text" class="form-control" id="apellido" name="apellido"
                     value="<%= esc(request.getParameter("apellido")) %>">
              <div class="invalid-feedback">Ingrese su apellido.</div>
            </div>

            <div class="mb-3">
              <label for="password" class="form-label">Contraseña</label>
              <input type="password" class="form-control" id="password" name="password" required>
              <div class="invalid-feedback">Ingrese una contraseña.</div>
            </div>
            <div class="mb-3">
              <label for="confirmPassword" class="form-label">Confirmar Contraseña</label>
              <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
              <div class="invalid-feedback">Confirme su contraseña.</div>
            </div>

            <div class="mb-3">
              <label for="imagen" class="form-label">Imagen de perfil (opcional)</label>
              <input type="file" class="form-control" id="imagen" name="imagen" accept="image/*">
            </div>

            <div id="organizadorFields" style="display: none;">
              <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción General</label>
                <textarea class="form-control" id="descripcion" name="descripcion" rows="3"><%= esc(request.getParameter("descripcion")) %></textarea>
                <div class="invalid-feedback">Ingrese una descripción.</div>
              </div>
              <div class="mb-3">
                <label for="sitioWeb" class="form-label">Sitio Web (opcional)</label>
                <input type="url" class="form-control" id="sitioWeb" name="sitioWeb"
                       value="<%= esc(request.getParameter("sitioWeb")) %>" placeholder="https://...">
              </div>
            </div>

            <div id="asistenteFields" style="display: none;">
              <div class="mb-3">
                <label for="fechaNacimiento" class="form-label">Fecha de Nacimiento</label>
                <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento"
                       max="<%= esc(request.getAttribute("hoy")) %>"
                       value="<%= esc(request.getParameter("fechaNacimiento")) %>">
                <div class="invalid-feedback">Ingrese una fecha válida.</div>
              </div>

              <div class="mb-3">
                <label for="institucion" class="form-label">Institución (opcional)</label>
                <select class="form-select" id="institucion" name="institucion">
                  <%
                    // value y comparación por NOMBRE
                    List<Institucion> instituciones = (List<Institucion>) request.getAttribute("instituciones");
                    String instSel = request.getParameter("institucion");
                  %>
                  <option value="" <%= (instSel==null || instSel.isEmpty()) ? "selected" : "" %> >Ninguna</option>
                  <%
                    if (instituciones != null) {
                      for (Institucion inst : instituciones) {
                        String nombreInst = esc(inst.getNombre()); // ← usa el NOMBRE
                        String sel = (instSel != null && instSel.equals(inst.getNombre())) ? " selected" : "";
                  %>
                        <option value="<%= nombreInst %>" <%= sel %>><%= nombreInst %></option>
                  <%
                      }
                    }
                  %>
                </select>
              </div>
            </div>

            <button type="submit" class="btn btn-primary w-100">Registrarse</button>
          </form>

          <div class="text-center mt-3">
            <p class="mb-0">
              ¿Ya tienes cuenta?
              <a href="<%= request.getContextPath() %>/login" class="text-decoration-none">Inicia sesión</a>
            </p>
          </div>
        </div>
      </div>
    </div>
  </main>

  <hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
  <footer id="footer" class="footer position-relative light-background">
    <jsp:include page="/WEB-INF/views/template/footer.jsp" />
  </footer>

  <script src="<%= request.getContextPath() %>/js/AltaUsuario.js"></script>
  <script>
    (function () {
      const tipo = document.getElementById('tipoUsuario');
      const org  = document.getElementById('organizadorFields');
      const asis = document.getElementById('asistenteFields');
      const ape  = document.getElementById('apellidoAsistente');
      function toggle() {
        const v = (tipo && tipo.value) || "";
        org.style.display  = (v === 'organizador') ? 'block' : 'none';
        asis.style.display = (v === 'asistente')   ? 'block' : 'none';
        ape.style.display  = (v === 'asistente')   ? 'block' : 'none';
      }
      if (tipo) {
        tipo.addEventListener('change', toggle);
        const prev = '<%= esc(request.getParameter("tipoUsuario")) %>';
        if (prev) tipo.value = prev;
        toggle();
      }
    })();
  </script>
</body>
</html>
