<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <title>Login — Movil</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    body { background: #f8f9fa; }
    .login-card { max-width: 380px; margin: 10vh auto; }
  </style>
</head>
<body>
  <div class="card login-card shadow-sm">
    <div class="card-body">
      <h5 class="card-title mb-3">Iniciar sesión</h5>

      <%
        String msgError = (String) request.getAttribute("msgError");
        if (msgError != null) {
      %>
      <div class="alert alert-danger"><%= msgError %></div>
      <% } %>

      <form method="post" action="<%= request.getContextPath() %>/login">
        <div class="mb-3">
          <label class="form-label">Usuario o Email</label>
          <input name="identifier" class="form-control" autocomplete="username" required>
        </div>
        <div class="mb-3">
          <label class="form-label">Contraseña</label>
          <input type="password" name="password" class="form-control" autocomplete="current-password" required>
        </div>
        <button class="btn btn-primary w-100" type="submit">Entrar</button>
      </form>
    </div>
  </div>
</body>
</html>
