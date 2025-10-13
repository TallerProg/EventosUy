<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% 
    String ctx = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Alta institucion</title>
</head>

<body class="index-page">
<header id="header" class="header d-flex align-items-center fixed-top">
  		<jsp:include page="/WEB-INF/views/template/header.jsp" />
</header>

<main class="main container py-5">
    <div class="row justify-content-center">
        <div class="col-12 col-md-8 col-lg-6">
            <div class="card shadow-lg p-4 rounded-4">
                <div class="container section-title">
                    <h2>Alta de Institución</h2>
                </div>

                <%-- Mostrar mensaje de éxito si existe --%>
                <%
                    String successMessage = (String) session.getAttribute("success_message");
                    if (successMessage != null) {
                %>
                    <div class="alert alert-success">
                        <%= successMessage %>
                    </div>
                <%
                    // Limpiar el mensaje después de mostrarlo
                    session.removeAttribute("success_message");
                    }
                %>

                <%-- Mostrar mensaje de error si existe --%>
                <%
                    String errorMessage = (String) session.getAttribute("error_message");
                    if (errorMessage != null) {
                %>
                    <div class="alert alert-danger">
                        <%= errorMessage %>
                    </div>
                <%
                    // Limpiar el mensaje después de mostrarlo
                    session.removeAttribute("error_message");
                    }
                %>

                <form method="POST" enctype="multipart/form-data" novalidate>
                    <!-- Nombre -->
                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre (único)</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" required>
                    </div>
                    <!-- Descripción -->
                    <div class="mb-3">
                        <label for="descripcion" class="form-label">Descripción</label>
                        <input type="text" class="form-control" id="descripcion" name="descripcion" required>
                    </div>
                    <!-- URL -->
                    <div class="mb-3">
                        <label for="url" class="form-label">URL de la web</label>
                        <input type="url" class="form-control" id="url" name="url" required>
                    </div>
                    <!-- Imagen -->
                    <div class="mb-3">
                        <label for="imagen" class="form-label">Imagen de perfil (opcional)</label>
                        <input type="file" class="form-control" id="imagen" name="imagen" accept="image/*">
                    </div>
                    <!-- Botón -->
                    <button type="submit" class="btn btn-primary w-100">Registrar</button>
                </form>

            </div>
        </div>
    </div>
</main>

 <!-- Footer -->
<hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
<footer id="footer" class="footer position-relative light-background">
  <jsp:include page="/WEB-INF/views/template/footer.jsp" />
</footer>
<script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="../js/main.js"></script>
</body>
</html>
