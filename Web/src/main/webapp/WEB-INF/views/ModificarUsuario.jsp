<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="cliente.ws.sc.DtInstitucion"%>
<% %>
<%@ page import="cliente.ws.sc.DtInstitucionArray"%>
<%@ page import="cliente.ws.sc.DtUsuarioListaConsulta" %>

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
  DtUsuarioListaConsulta us = (DtUsuarioListaConsulta) request.getAttribute("USUARIO");
  String tipo = (String) request.getAttribute("TIPO_USUARIO");

  
  boolean ES_ASIS = Boolean.TRUE.equals(request.getAttribute("ES_ASIS"));

  String nick = (us != null) ? us.getNickname() : "";
  String correo = (us != null) ? us.getCorreo() : "";
  String nombre = (us != null) ? us.getNombre() : "";
  String uRL = (us != null) ? us.getUrl() : "";
  String descripcion = (us != null) ? us.getDescripcion() : "";
  String apellido = "";
  String fechaISO = "";
  String instUser = "";
  if (ES_ASIS) {
    DtUsuarioListaConsulta a = (DtUsuarioListaConsulta) us;
    apellido = (a != null && a.getApellido() != null) ? a.getApellido() : "";
    fechaISO = (a != null && a.getFNacimientoS() != null) ? a.getFNacimientoS(): "";
    if (a != null && a.getIns() != null) instUser = a.getIns().getNombre();
  }

  // ------ Sticky values si hubo error de contraseña ------
  String formNombre = (String) request.getAttribute("form_nombre");
  String formApellido = (String) request.getAttribute("form_apellido");
  String formFecha = (String) request.getAttribute("form_fechaNacimiento");
  String formInst = (String) request.getAttribute("form_institucion");
  String formURL = (String) request.getAttribute("form_url");
  String formDesc = (String) request.getAttribute("form_descripcion");

  if (formNombre != null) nombre = formNombre;
  if (formURL != null)    uRL = formURL;
  if (formDesc != null)   descripcion = formDesc;

  if (ES_ASIS) {
    if (formApellido != null) apellido = formApellido;
    if (formFecha != null)    fechaISO = formFecha; // viene como yyyy-MM-dd
    if (formInst != null)     instUser = formInst;
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
<input type="hidden" id="tipoUsuario" name="tipoUsuario" value="<%= tipo %>">
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

        <% if (ES_ASIS) { %>
        <div id="asistente-fields">
          <div class="mb-3">
            <label>Apellido</label>
            <input type="text" name="apellido" class="form-control" value="<%= apellido %>">
          </div>
          <div class="mb-3">
            <label>Fecha de nacimiento</label>
            <input type="date" name="fechaNacimiento" class="form-control" value="<%= fechaISO %>">
          </div>
          
        </div>
        <% } %>
        <% if (tipo != null && tipo.equals("organizador")) { %>
        <div id="asistente-fields">
          <div class="mb-3">
            <label> URL</label>
            <input type="text" name="uRL" class="form-control" value="<%= uRL %>">
          </div>
          <div class="mb-3">
            <label> descripcion</label>
            <input type="text" name="descripcion" class="form-control" value="<%= descripcion %>">
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
