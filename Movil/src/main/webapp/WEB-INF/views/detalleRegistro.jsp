<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="cliente.ws.sc.DtRegistro" %>

<!DOCTYPE html>
<html lang="es">
<head>
  <jsp:include page="/WEB-INF/views/template/head.jsp" />
  <title>Detalle de Registro</title>
</head>
<body class="index-page">

<header id="header" class="header d-flex align-items-center fixed-top">
  <jsp:include page="/WEB-INF/views/template/navbar.jsp" />
</header>

<main class="main mt-5 pt-5">
  <div class="container mt-5">
    <h2 class="mb-4 text-center">Detalle de registro</h2>

    <%
      String ctx = request.getContextPath();
      DtRegistro reg = (DtRegistro) request.getAttribute("REGISTRO");
      String error = (String) request.getAttribute("error");

      if (error != null) {
    %>
        <div class="alert alert-danger text-center"><%= error %></div>
        <div class="text-center mt-3">
          <a href="<%= ctx %>/misRegistros" class="btn btn-secondary">Volver a mis registros</a>
        </div>
    <%
      } else if (reg == null) {
    %>
        <div class="alert alert-warning text-center">
          No se encontraron datos del registro para la edición seleccionada.
        </div>
        <div class="text-center mt-3">
          <a href="<%= ctx %>/misRegistros" class="btn btn-secondary">Volver a mis registros</a>
        </div>
    <%
      } else {
        String fechaReg      = reg.getFInicioS();
        String tipoReg       =  reg.getTipoRegistroNombre();
        float costo         =  reg.getCosto();
        boolean asistio       = reg.isAsistio();
        
    %>

            <div class="card shadow-sm mb-4">
        <div class="card-body">
          <h5 class="card-title"><i class="bi bi-person-badge"></i> Mi registro</h5>
          <div class="row g-2 align-items-center">
            <div class="col-md-3"><strong>Tipo:</strong> <%= tipoReg %></div>
            <div class="col-md-3"><strong>Fecha:</strong> <%= fechaReg %></div>
            <% if (asistio) { %>
  <div class="col-md-3"><strong>Asistencia:</strong> ok</div>
<% } else { %>
  <div class="col-md-3">
    <form method="post">
      <input type="hidden" name="nombreEdicion" value="<%= reg.getNombreEdicion() %>">
      <button type="submit" class="btn btn-success btn-sm">
        Marcar asistencia
      </button>
    </form>
  </div>
<% } %>
          </div>
        </div>
      </div>

    <%
      }
    %>
  </div>
</main>

<footer id="footer" class="footer position-relative light-background mt-5">
  <jsp:include page="/WEB-INF/views/template/footer.jsp" />
  <script src="<%= ctx %>/media/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="<%= ctx %>/media/js/main.js"></script>
</footer>

</body>
</html>

<%! 
// Helper para evitar null/errores si algún getter no existe
private String safe(java.util.concurrent.Callable<String> c) {
  try {
    String v = c.call();
    return (v != null) ? v : "";
  } catch (Exception e) {
    return "";
  }
}
%>
