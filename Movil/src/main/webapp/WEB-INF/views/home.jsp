<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String ctx = request.getContextPath();
  String nick = (String) session.getAttribute("MOVIL_NICK");
%>
<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <title>Home — Movil</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-3">
  <nav class="navbar navbar-expand-lg bg-body-tertiary mb-3">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">EventUY Movil</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#nav"
              aria-controls="nav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="nav">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item"><a class="nav-link" href="<%=ctx%>/m/home">Inicio</a></li>
          <li class="nav-item"><a class="nav-link" href="<%=ctx%>/m/ediciones">Ediciones</a></li>
          <li class="nav-item"><a class="nav-link" href="<%=ctx%>/m/registro">Mi registro</a></li>
          <li class="nav-item"><a class="nav-link" href="<%=ctx%>/m/asistencia">Asistencia</a></li>
        </ul>
        <div class="d-flex">
          <span class="me-3">Hola <%= (nick == null ? "" : nick) %></span>
          <a class="btn btn-outline-danger btn-sm" href="<%=ctx%>/logout">Salir</a>
        </div>
      </div>
    </div>
  </nav>

  <div class="container">
    <h4>Bienvenido</h4>
    <p class="text-muted">Pantalla base para probar que la sesión móvil funciona.</p>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
