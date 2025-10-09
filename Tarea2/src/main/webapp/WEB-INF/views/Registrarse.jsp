<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:formatDate value="${now}" pattern="yyyy-MM-dd" var="hoy"/>

<!doctype html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/common/head.jsp" />
  <title>Registro - EventUY</title>

</head>
<body class="bg-light">
  <jsp:include page="/WEB-INF/views/common/header.jsp" />

  <main class="container py-5">
    <!-- Mensajes del servidor -->
    <c:if test="${not empty error}">
      <div class="alert alert-danger" role="alert">${error}</div>
    </c:if>
    <c:if test="${not empty mensaje}">
      <div class="alert alert-success" role="alert">${mensaje}</div>
    </c:if>

    <div class="row justify-content-center">
      <div class="col-12 col-md-8 col-lg-6">
        <div class="card shadow-lg p-4 rounded-4">
          <div class="text-center mb-4">
            <i class="bi bi-person-plus fs-1 text-primary"></i>
            <h4 class="mt-2 text-primary">Registro de Usuario</h4>
          </div>

          <!-- IMPORTANTE: multipart para imagen -->
          <form id="registroForm"
                method="post"
                action="${pageContext.request.contextPath}/registrarse"
                enctype="multipart/form-data"
                novalidate>

            <!-- Tipo de usuario -->
            <div class="mb-3">
              <label class="form-label">Tipo de Usuario</label>
              <select class="form-select" id="tipoUsuario" name="tipoUsuario" required>
                <option value="" disabled selected>Seleccione...</option>
                <option value="asistente">Asistente</option>
                <option value="organizador">Organizador</option>
              </select>
              <div class="invalid-feedback">Seleccione un tipo de usuario.</div>
            </div>

            <!-- Nickname y Correo -->
            <div class="mb-3">
              <label for="nickname" class="form-label">Nickname (único)</label>
              <input type="text" class="form-control" id="nickname" name="nickname" required>
              <div class="invalid-feedback">Ingrese un nickname.</div>
            </div>
            <div class="mb-3">
              <label for="email" class="form-label">Correo electrónico (único)</label>
              <input type="email" class="form-control" id="email" name="email" required>
              <div class="invalid-feedback">Ingrese un correo válido.</div>
            </div>

            <!-- Nombre -->
            <div class="mb-3">
              <label for="nombre" class="form-label">Nombre</label>
              <input type="text" class="form-control" id="nombre" name="nombre" required>
              <div class="invalid-feedback">Ingrese su nombre.</div>
            </div>

            <!-- Apellido (solo asistentes) -->
            <div id="apellidoAsistente" class="mb-3" style="display: none;">
              <label for="apellido" class="form-label">Apellido</label>
              <input type="text" class="form-control" id="apellido" name="apellido">
              <div class="invalid-feedback">Ingrese su apellido.</div>
            </div>

            <!-- Contraseña -->
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

            <!-- Imagen -->
            <div class="mb-3">
              <label for="imagen" class="form-label">Imagen de perfil (opcional)</label>
              <input type="file" class="form-control" id="imagen" name="imagen" accept="image/*">
            </div>

            <!-- Campos solo para organizador -->
            <div id="organizadorFields" style="display: none;">
              <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción General</label>
                <textarea class="form-control" id="descripcion" name="descripcion" rows="3"></textarea>
                <div class="invalid-feedback">Ingrese una descripción.</div>
              </div>
              <div class="mb-3">
                <label for="sitioWeb" class="form-label">Sitio Web (opcional)</label>
                <input type="url" class="form-control" id="sitioWeb" name="sitioWeb" placeholder="https://...">
              </div>
            </div>

            <!-- Campos solo para asistente -->
            <div id="asistenteFields" style="display: none;">
              <div class="mb-3">
                <label for="fechaNacimiento" class="form-label">Fecha de Nacimiento</label>
                <input type="date" class="form-control" id="fechaNacimiento" name="fechaNacimiento" max="${hoy}">
                <div class="invalid-feedback">Ingrese una fecha válida.</div>
              </div>
              <div class="mb-3">
                <label for="institucion" class="form-label">Institución (opcional)</label>
                <select class="form-select" id="institucion" name="institucion">
                  <option value="" selected>Ninguna</option>
                  <option value="INS01">Facultad de Ingeniería</option>
                  <option value="INS02">ORT Uruguay</option>
                  <option value="INS03">Universidad Católica del Uruguay</option>
                  <option value="INS04">Antel</option>
                  <option value="INS05">Agencia Nacional de Investigación e Innovación (ANII)</option>
                </select>
              </div>
            </div>

            <!-- Botón -->
            <button type="submit" class="btn btn-primary w-100">Registrarse</button>
          </form>

          <div class="text-center mt-3">
            <p class="mb-0">
              ¿Ya tienes cuenta?
              <a href="${pageContext.request.contextPath}/login" class="text-decoration-none">Inicia sesión</a>
            </p>
          </div>
        </div>
      </div>
    </div>
  </main>

  <jsp:include page="/WEB-INF/views/common/footer.jsp" />



  <script src="${pageContext.request.contextPath}/js/AltaUsuario.js"></script>


  <script>
    (function () {
      const tipo = document.getElementById('tipoUsuario');
      const org = document.getElementById('organizadorFields');
      const asis = document.getElementById('asistenteFields');
      const ape = document.getElementById('apellidoAsistente');

      function toggle() {
        const v = tipo.value;
        org.style.display  = (v === 'organizador') ? 'block' : 'none';
        asis.style.display = (v === 'asistente')   ? 'block' : 'none';
        ape.style.display  = (v === 'asistente')   ? 'block' : 'none';
      }

      if (tipo) {
        tipo.addEventListener('change', toggle);
        toggle();
      }
    })();
  </script>
</body>
</html>
