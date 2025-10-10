<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% 
    String ctx = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alta Institución</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="<%=ctx%>/vendor/swiper/swiper-bundle.min.css" rel="stylesheet">
    <link href="<%=ctx%>/vendor/glightbox/css/glightbox.min.css" rel="stylesheet">
    <link href="<%=ctx%>/media/css/main.css" rel="stylesheet">
    <link href="<%=ctx%>/media/img/logoeuy.png" rel="icon">
    <link href="<%=ctx%>/media/img/logoeuy.png" rel="apple-touch-icon">
</head>

<body class="index-page">
<header id="header" class="header d-flex align-items-center fixed-top">
    <div class="header-container container-fluid container-xl position-relative d-flex align-items-center justify-content-between">
        <nav id="navmenu" class="navmenu">
            <ul>
                <a href="indexOrg.html" class="logo d-flex align-items-center">
                    <img src="../img/logoeuy.png" alt="">
                </a>
                <li><a href="usuariosOrgAsiME.html">Usuarios</a></li>
                <li><a href="listaEventosOrg.html">Eventos</a></li>
                <li><a href="listaInstitucionesOrg.html">Instituciones</a></li>
                <li class="nav-item search-item ms-auto">
                    <form class="search-form" action="search.html" method="get">
                        <input type="text" name="q" class="search-input" placeholder="Buscar eventos...">
                        <button type="submit" class="search-btn"><i class="bi bi-search"></i></button>
                    </form>
                </li>
                <li class="ms-auto d-flex align-items-center gap-2">
                    <a href="perfil_US04.html" class="user-info text-decoration-none d-flex align-items-center gap-2">
                        <img src="../img/usuarios/misEventos.jpeg" alt=" ">
                        <span class="fw-semibold">MisEventos</span>
                    </a>
                    <a href="../indexVis.html" class="btn p-0 border-0 bg-transparent">
                        <i class="login-icon bi bi-box-arrow-in-right fs-3" title="Cerrar Sesión"></i>
                    </a>
                </li>
            </ul>
            <i class="mobile-nav-toggle d-xl-none bi bi-list"></i>
        </nav>
    </div>
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

<hr class="mt-5 mb-4" style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
<footer id="footer" class="footer position-relative light-background">
    <div class="container mt-4">
        <div class="row">
            <div class="col-lg-6 col-md-6 mb-3">
                <h5>Sobre eventos.uy</h5>
                <p class="text-muted small">
                    En <strong>eventos.uy</strong> nos apasiona conectar personas a través de conferencias,
                    talleres, hackatones y todo tipo de encuentros. Queremos hacer más fácil organizar,
                    descubrir y disfrutar eventos. <br>
                    Si sos organizador, vas a encontrar herramientas para gestionar tus ediciones,
                    inscripciones y patrocinios. Y si sos asistente, podes
                    consultar por tus eventos favoritos, registrarte en segundos y luego queda disfrutarlos! .
                </p>
            </div>

            <div class="col-lg-6 col-md-6 mb-3 text-lg-end text-md-end text-center">
                <h5>Contacto</h5>
                <p class="small">
                    📧 <a href="mailto:contacto@eventos.uy">contacto@eventos.uy</a><br>
                    ☎ +598 2400 0000
                </p>
                <div class="social-links d-flex justify-content-lg-end justify-content-md-end justify-content-center">
                    <a href=""><i class="bi bi-twitter-x"></i></a>
                    <a href=""><i class="bi bi-facebook"></i></a>
                    <a href=""><i class="bi bi-instagram"></i></a>
                    <a href=""><i class="bi bi-linkedin"></i></a>
                    <a href="https://gitlab.fing.edu.uy/tprog/tpgr27"><i class="bi bi-gitlab"></i></a>
                </div>
            </div>
        </div>

        <div class="credits text-center mt-3">
            © 2025 <strong>eventos.uy</strong> · Taller de Programación FING G27
        </div>
    </div>
</footer>

<script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="../js/main.js"></script>
</body>
</html>
