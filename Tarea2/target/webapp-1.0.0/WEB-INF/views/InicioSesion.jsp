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
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
  <link href="media/img/logoeuy.png" rel="icon">
  <link href="media/img/logoeuy.png" rel="apple-touch-icon">
</head>
<body class="bg-light d-flex justify-content-center align-items-center vh-100">

  <div class="card shadow-lg p-4 rounded-4" style="max-width: 400px; width: 100%;">
    <div class="text-center mb-4">
      <i class="bi bi-person-circle fs-1 text-primary"></i>
      <h4 class="mt-2">Iniciar Sesión</h4>
    </div>

    <% if (request.getAttribute("error") != null) { %>
      <div class="alert alert-danger" role="alert"><%= esc(request.getAttribute("error")) %></div>
    <% } %>

    <form action="<%= request.getContextPath() %>/login" method="post" novalidate>
      <div class="mb-3">
        <label for="identifier" class="form-label">Correo o Nickname</label>
        <input type="text"
               class="form-control"
               id="identifier"
               name="identifier"
               placeholder="ej: pepe23 o pepe@email.com"
               required
               value="<%
                        Object prev = request.getAttribute("identifier_prev");
                        String val = prev != null ? prev.toString() : request.getParameter("identifier");
                        out.print(esc(val));
                      %>">
      </div>

      <div class="mb-3">
        <label for="password" class="form-label">Contraseña</label>
        <input type="password" class="form-control" id="password" name="password" placeholder="********" required>
      </div>

      <button type="submit" class="btn btn-primary w-100">Iniciar Sesión</button>
    </form>

    <div class="text-center mt-3">
      <p class="mb-0">¿No tienes cuenta?
        <a href="<%= request.getContextPath() %>/Registrarse" class="text-decoration-none">Regístrate</a>
      </p>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

