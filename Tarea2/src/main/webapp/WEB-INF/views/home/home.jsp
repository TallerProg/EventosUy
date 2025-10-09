<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="ServidorCentral.logica.Evento"%>
<%@ page import="ServidorCentral.logica.Categoria"%>

<%@ page import="java.util.List"%>



<!DOCTYPE html>
<html lang="es">
<head>
<jsp:include page="../template/head.jsp" />
</head>
<body class="index-page">
	<!-- Header -->
	<header id="header" class="header d-flex align-items-center fixed-top">
		<jsp:include page="../template/header.jsp" />

	</header>
	<main class="main">
		<!-- Carrusel -->
		<%
		Evento[] eventos = (Evento[]) request.getAttribute("LISTA_EVENTOS");
		if (eventos != null && eventos.length > 0) {
		%>

		<section id="hero-carousel" class="section mt-5 pt-4">
			<div class="container">
				<div id="eventCarousel" class="carousel slide"
					data-bs-ride="carousel">
					<div class="carousel-indicators">
						<button type="button" data-bs-target="#eventCarousel"
							data-bs-slide-to="0" class="active"></button>
						<button type="button" data-bs-target="#eventCarousel"
							data-bs-slide-to="1"></button>
					</div>
					<div class="carousel-inner">
					<%
					for (Evento e : eventos) {
					%>
					
						<!-- Elemento carrusel -->
						<div class="carousel-item active">
							<img src="media/img/default.png"
								class="d-block w-100 carousel-img" alt="<%=e.getNombre()%>">
							<div class="carousel-caption d-none d-md-block">
								<h5 class="carousel-title"><%=e.getNombre()%></h5>
								<p class="carousel-text"><%=e.getDescripcion()%></p>
								<a href="/tprog-webapp/consultz"
									class="btn btn-primary btn-lg me-3">Ver ediciones</a>
							</div>
						</div>
						<%
						}
						%>
						<button class="carousel-control-prev" type="button"
							data-bs-target="#eventCarousel" data-bs-slide="prev">
							<span class="carousel-control-prev-icon"></span>
						</button>
						<button class="carousel-control-next" type="button"
							data-bs-target="#eventCarousel" data-bs-slide="next">
							<span class="carousel-control-next-icon"></span>
						</button>
					</div>
				</div>
		</section>
		<!-- Eventos -->
		<section id="speakers" class="speakers section">
			<div class="container section-title">
				<h2>Eventos</h2>
			</div>
			<!-- Carrusel de Categorías -->
			<section id="categories-carousel">
				<div class="container">
					<div id="categoriesCarousel" class="carousel slide"
						data-bs-interval="false">
						<div class="carousel-inner">
							<div class="carousel-item active">
								<div class="d-flex flex-wrap gap-2 justify-content-center">
									<button type="button"
										class="btn btn-outline-primary category-btn"
										data-category="tecnologia">
										<i class="bi bi-cpu"></i> Tecnología
									</button>
									<button type="button"
										class="btn btn-outline-primary category-btn"
										data-category="innovacion">
										<i class="bi bi-lightbulb"></i> Innovación
									</button>
									<button type="button"
										class="btn btn-outline-primary category-btn"
										data-category="deporte">
										<i class="bi bi-trophy"></i> Deporte
									</button>
									<button type="button"
										class="btn btn-outline-primary category-btn"
										data-category="salud">
										<i class="bi bi-heart-pulse"></i> Salud
									</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</section>
			<div class="container">
				<div class="row g-4 justify-content-center">
					<%
					for (Evento e : eventos) {

						List<Categoria> categorias = e.getCategoria();
						String catString = "";
						for (int i = 0; i < categorias.size(); i++) {
							catString += categorias.get(i).getNombre();
							if (i < categorias.size() - 1) {
						catString += ",";
							}
						}
					%>
					<!-- EV01 -->
					<div class="col-lg-3 col-md-6">
						<a href="">
							<div class="speaker-card" data-categories="<%=catString%>">
								<div class="speaker-image">
									<img src="img/default.png" class="img-fluid">
								</div>
								<div class="speaker-content">
									<p class="speaker-title"><%=e.getNombre()%></p>
									<p class="speaker-company"><%=e.getDescripcion()%></p>
									<div class="categories">

										<%
										if (catString.contains("tecnologia")) {
										%><i
											class="bi bi-cpu" title="Tecnología"></i>
										<%
										}
										if (catString.contains("innovacion")) {
										%><i
											class="bi bi-lightbulb" title="Innovación"></i>
										<%
										}
										if (catString.contains("deporte")) {
										%><i
											class="bi bi-trophy" title="Deporte"></i>
										<%
										}
										if (catString.contains("salud")) {
										%><i
											class="bi bi-heart-pulse" title="Salud"></i>
										<%
										}
										%>
									</div>
									<a href="" class="btn btn-primary btn-lg me-3">Ver
										ediciones</a>
								</div>
							</div>
						</a>
					</div>
					<%
					}
					%>

					<%
					}
					%>
				
	</main>
	<!-- Footer -->
	<hr class="mt-5 mb-4"
		style="border: 0; height: 3px; background: #bbb; border-radius: 2px;">
	<footer id="footer" class="footer position-relative light-background">
		<jsp:include page="../template/footer.jsp" />
	</footer>
	<!-- Scroll Top -->
	<a href="#" id="scroll-top"
		class="scroll-top d-flex align-items-center justify-content-center">
		<i class="bi bi-arrow-up-short"></i>
	</a>
	<!-- Preloader -->
	<div id="preloader"></div>
	<!-- Bootstrap JS -->
	<script src="media/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- Main JS -->
	<script src="media/js/main.js"></script>
</body>
</html>
