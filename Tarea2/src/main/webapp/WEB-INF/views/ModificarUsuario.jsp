<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="servidorcentral.logica.Usuario,servidorcentral.logica.Asistente,servidorcentral.logica.Institucion"%>
<%
  String ctx = request.getContextPath();
  String img = (String) session.getAttribute("IMAGEN_LOGUEADO");
  String imagen = (img != null && !img.isBlank()) ? (ctx + img) : (ctx + "/media/img/default.png"); 

%>
<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="template/head.jsp" />
  <link rel="stylesheet" href="<%=ctx%>/media/css/main.css">
</head>
<body class="index-page">

<header id="header" class="header d-flex align-items-center fixed-top">
  <jsp:include page="template/header.jsp" />
</header>

<%
  Usuario us = (Usuario) request.getAttribute("USUARIO");
  String tipo = (String) request.getAttribute("TIPO_USUARIO");
  Institucion[] instituciones = (Institucion[]) request.getAttribute("LISTA_INSTITUCION");
  boolean esAsistente = "asistente".equalsIgnoreCase(tipo) && us instanceof Asistente;

  String nick = (us != null) ? us.getNickname() : "";
  String correo = (us != null) ? us.getCorreo() : "";
  String nombre = (us != null) ? us.getNombre() : "";
  String apellido = "";
  String fechaISO = "";
  String instUser = "";
  if (esAsistente) {
    Asistente a = (Asistente) us;
    apellido = (a.getApellido() != null) ? a.getApellido() : "";
    fechaISO = (a.getfNacimiento() != null) ? a.getfNacimiento().toString() : "";
    if (a.getInstitucion() != null) instUser = a.getInstitucion().getNombre();
  }
%>

<main class="main">
  <section id="modificar-usuario" class="section">	
    <div class="container">
      <div class="section-title">
        <h2>Modificar datos de usuario</h2>
      </div>
<%
  String msgError = (String) request.getAttribute("msgError");
  if (msgError != null && !msgError.isBlank()) {
%>
  <div class="alert alert-danger"><%= msgError %></div>
<% } %>

      <form action="<%=ctx%>/editarperfil" method="post" enctype="multipart/form-data" id="form-modificar">
        <input type="hidden" id="tipoUsuario" value="<%= tipo %>">
        <div class="profile-image text-center mb-4">
          <img id="preview" src="<%=imagen%>"
               alt="Foto de perfil" class="rounded-circle" width="120">
        </div>

        <div class="mb-3">
          <label for="imagen">Imagen de perfil</label>
          <input type="file" id="imagen" name="imagen" class="form-control" accept="image/*">
        </div>

        <div class="mb-3">
          <label>Nickname</label>
          <input type="text" name="nickname" class="form-control" value="<%= nick %>" readonly>
        </div>

        <div class="mb-3">
          <label>Correo</label>
          <input type="email" name="correo" class="form-control" value="<%= correo %>" readonly>
        </div>

        <div class="mb-3">
          <label>Nombre</label>
          <input type="text" name="nombre" class="form-control" value="<%= nombre %>">
        </div>

        <% if (esAsistente) { %>
        <div id="asistente-fields">
          <div class="mb-3">
            <label>Apellido</label>
            <input type="text" name="apellido" class="form-control" value="<%= apellido %>">
          </div>
          <div class="mb-3">
            <label>Fecha de nacimiento</label>
            <input type="date" name="fechaNacimiento" class="form-control" value="<%= fechaISO %>">
          </div>
          <div class="mb-3">
            <label>Institución</label>
            <select name="institucion" class="form-control">
              <option value="">-- Seleccione institución --</option>
              <% if (instituciones != null) {
                   for (Institucion i : instituciones) {
                     String nombreInst = i.getNombre();
                     String sel = nombreInst.equals(instUser) ? "selected" : "";
              %>
                <option value="<%= nombreInst %>" <%= sel %>><%= nombreInst %></option>
              <%   }
                 } %>
            </select>
          </div>
        </div>
        <% } %>

        <div class="mb-3">
          <label>Nueva contraseña</label>
          <input type="password" id="password" name="password" class="form-control" placeholder="Dejar vacío si no cambia">

        </div>
		<div class="mb-3">
  <label>Confirmar nueva contraseña</label>
  <input type="password" name="confirmPassword" id="confirmPassword" class="form-control" placeholder="Repite la nueva contraseña">
</div>
        <button type="submit" class="btn btn-primary">Aceptar</button>
        <a href="<%=ctx%>/perfil" class="btn btn-secondary">Cancelar</a>
      </form>
    </div>
  </section>
</main>

<footer class="footer mt-5">
  <jsp:include page="template/footer.jsp" />
</footer>

<div id="preloader"></div>

<script src="<%=ctx%>/media/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="<%=ctx%>/media/js/main.js"></script>
<script src="<%=ctx%>/media/js/ModificarUsuario.js"></script>
</body>
</html>


