<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="ServidorCentral.logica.Institucion"%>
    
<!DOCTYPE html>
<html lang="es">
<head>
 		<jsp:include page="template/head.jsp" />

</head>
<body class="index-page">

  <!-- Header -->
  <header id="header" class="header d-flex align-items-center fixed-top">
    		<jsp:include page="template/header.jsp" />
  </header>

  <!-- Main -->
  <main class="main">
    <section id="modificar-usuario" class="section">
      <div class="container">
        <div class="section-title">
          <h2>Modificar Datos de Usuario</h2>
          <p class="text-muted">Actualiza tu información personal</p>
        </div>

        <div class="profile-card">
          <form id="form-modificar" class="register-form" action="#" method="post" enctype="multipart/form-data">

            <!-- Imagen de perfil -->
            <div class="profile-image">
              <img id="preview" src="../img/usuarios/anaTorres.jpg" alt="Foto de perfil" class="profile-avatar">
            </div>

            <div class="form-group mb-3">
              <label for="imagen">Imagen de perfil</label>
              <input type="file" id="imagen" name="imagen" class="form-control" accept="image/*">
            </div>

            <!-- Nickname y correo (solo lectura) -->
            <div class="form-group mb-3">
              <label for="nickname">Nickname</label>
              <input type="text" id="nickname" name="nickname" class="form-control" value="anatorres" readonly>
            </div>
            <div class="form-group mb-3">
              <label for="correo">Correo electrónico</label>
              <input type="email" id="correo" name="correo" class="form-control" value="atorres@mail.com" readonly>
            </div>

            <!-- Datos comunes -->
            <div class="form-group mb-3">
              <label for="nombre">Nombre</label>
              <input type="text" id="nombre" name="nombre" class="form-control" value="Ana">
            </div>

            <!-- Campos exclusivos de Asistente -->            <div id="asistente-fields" class="hidden">
              <div class="form-group mb-3">
                <label for="apellido">Apellido</label>
                <input type="text" id="apellido" name="apellido" class="form-control" value="Torres">
              </div>
              <div class="form-group mb-3">
                <label for="fechaNacimiento">Fecha de nacimiento</label>
                <input type="date" id="fechaNacimiento" name="fechaNacimiento" class="form-control" value="12-05-1990">
              </div>
              <div class="form-group mb-3">
                <label for="institucion">Institución</label>
                <select id="institucion" name="institucion" class="form-control">
                  <option value="">-- Seleccione institución --</option>
                  <%
                  
                  	Institucion[] instituciones = (Institucion[]) request.getAttribute("LISTA_INSTITUCION");
                	if (instituciones != null){
                		for (Institucion i : instituciones) {
                  %>
                  	<option value="<%= i.getNombre()%>"><%= i.getNombre()%></option>
                  <%}} %>   
                </select>
              </div>
            </div>


            <!-- Contraseña -->
            <div class="form-group mb-3">
              <label for="password">Nueva contraseña</label>
              <input type="password" id="password" name="password" class="form-control" placeholder="Dejar vacío si no cambia">
            </div>

            <!-- Botones -->
            <button type="submit" class="btn-register">Aceptar</button>
            <button type="reset" class="btn-register-secondary">Cancelar</button>

          </form>
        </div>
      </div>
    </section>
  </main>

<!-- Footer -->
<hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">

<footer id="footer" class="footer position-relative light-background">
  			<jsp:include page="template/footer.jsp" />
</footer>

  <!-- Scroll Top -->
  <a href="#" id="scroll-top" class="scroll-top d-flex align-items-center justify-content-center">
    <i class="bi bi-arrow-up-short"></i>
  </a>

  <!-- Preloader -->
  <div id="preloader"></div>

  <!-- Vendor JS -->
  <script src="media/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Main JS -->
  <script src="media/js/main.js"></script>
  <script src="media/js/ModificarUsuario.js"></script>

</body>
</html>
